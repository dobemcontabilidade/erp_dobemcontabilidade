package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.TermoContratoAssinaturaEmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AssinaturaEmpresa;
import com.dobemcontabilidade.domain.TermoContratoAssinaturaEmpresa;
import com.dobemcontabilidade.domain.TermoContratoContabil;
import com.dobemcontabilidade.domain.enumeration.SituacaoTermoContratoAssinadoEnum;
import com.dobemcontabilidade.repository.TermoContratoAssinaturaEmpresaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TermoContratoAssinaturaEmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TermoContratoAssinaturaEmpresaResourceIT {

    private static final Instant DEFAULT_DATA_ASSINATURA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ASSINATURA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_ENVIO_EMAIL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ENVIO_EMAIL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_URL_DOCUMENTO_ASSINADO = "AAAAAAAAAA";
    private static final String UPDATED_URL_DOCUMENTO_ASSINADO = "BBBBBBBBBB";

    private static final SituacaoTermoContratoAssinadoEnum DEFAULT_SITUACAO = SituacaoTermoContratoAssinadoEnum.ASSINADO;
    private static final SituacaoTermoContratoAssinadoEnum UPDATED_SITUACAO = SituacaoTermoContratoAssinadoEnum.PENDENTE;

    private static final String ENTITY_API_URL = "/api/termo-contrato-assinatura-empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TermoContratoAssinaturaEmpresaRepository termoContratoAssinaturaEmpresaRepository;

    @Mock
    private TermoContratoAssinaturaEmpresaRepository termoContratoAssinaturaEmpresaRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTermoContratoAssinaturaEmpresaMockMvc;

    private TermoContratoAssinaturaEmpresa termoContratoAssinaturaEmpresa;

    private TermoContratoAssinaturaEmpresa insertedTermoContratoAssinaturaEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TermoContratoAssinaturaEmpresa createEntity(EntityManager em) {
        TermoContratoAssinaturaEmpresa termoContratoAssinaturaEmpresa = new TermoContratoAssinaturaEmpresa()
            .dataAssinatura(DEFAULT_DATA_ASSINATURA)
            .dataEnvioEmail(DEFAULT_DATA_ENVIO_EMAIL)
            .urlDocumentoAssinado(DEFAULT_URL_DOCUMENTO_ASSINADO)
            .situacao(DEFAULT_SITUACAO);
        // Add required entity
        TermoContratoContabil termoContratoContabil;
        if (TestUtil.findAll(em, TermoContratoContabil.class).isEmpty()) {
            termoContratoContabil = TermoContratoContabilResourceIT.createEntity(em);
            em.persist(termoContratoContabil);
            em.flush();
        } else {
            termoContratoContabil = TestUtil.findAll(em, TermoContratoContabil.class).get(0);
        }
        termoContratoAssinaturaEmpresa.setTermoContratoContabil(termoContratoContabil);
        // Add required entity
        AssinaturaEmpresa assinaturaEmpresa;
        if (TestUtil.findAll(em, AssinaturaEmpresa.class).isEmpty()) {
            assinaturaEmpresa = AssinaturaEmpresaResourceIT.createEntity(em);
            em.persist(assinaturaEmpresa);
            em.flush();
        } else {
            assinaturaEmpresa = TestUtil.findAll(em, AssinaturaEmpresa.class).get(0);
        }
        termoContratoAssinaturaEmpresa.setEmpresa(assinaturaEmpresa);
        return termoContratoAssinaturaEmpresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TermoContratoAssinaturaEmpresa createUpdatedEntity(EntityManager em) {
        TermoContratoAssinaturaEmpresa termoContratoAssinaturaEmpresa = new TermoContratoAssinaturaEmpresa()
            .dataAssinatura(UPDATED_DATA_ASSINATURA)
            .dataEnvioEmail(UPDATED_DATA_ENVIO_EMAIL)
            .urlDocumentoAssinado(UPDATED_URL_DOCUMENTO_ASSINADO)
            .situacao(UPDATED_SITUACAO);
        // Add required entity
        TermoContratoContabil termoContratoContabil;
        if (TestUtil.findAll(em, TermoContratoContabil.class).isEmpty()) {
            termoContratoContabil = TermoContratoContabilResourceIT.createUpdatedEntity(em);
            em.persist(termoContratoContabil);
            em.flush();
        } else {
            termoContratoContabil = TestUtil.findAll(em, TermoContratoContabil.class).get(0);
        }
        termoContratoAssinaturaEmpresa.setTermoContratoContabil(termoContratoContabil);
        // Add required entity
        AssinaturaEmpresa assinaturaEmpresa;
        if (TestUtil.findAll(em, AssinaturaEmpresa.class).isEmpty()) {
            assinaturaEmpresa = AssinaturaEmpresaResourceIT.createUpdatedEntity(em);
            em.persist(assinaturaEmpresa);
            em.flush();
        } else {
            assinaturaEmpresa = TestUtil.findAll(em, AssinaturaEmpresa.class).get(0);
        }
        termoContratoAssinaturaEmpresa.setEmpresa(assinaturaEmpresa);
        return termoContratoAssinaturaEmpresa;
    }

    @BeforeEach
    public void initTest() {
        termoContratoAssinaturaEmpresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTermoContratoAssinaturaEmpresa != null) {
            termoContratoAssinaturaEmpresaRepository.delete(insertedTermoContratoAssinaturaEmpresa);
            insertedTermoContratoAssinaturaEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createTermoContratoAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TermoContratoAssinaturaEmpresa
        var returnedTermoContratoAssinaturaEmpresa = om.readValue(
            restTermoContratoAssinaturaEmpresaMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(termoContratoAssinaturaEmpresa))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TermoContratoAssinaturaEmpresa.class
        );

        // Validate the TermoContratoAssinaturaEmpresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTermoContratoAssinaturaEmpresaUpdatableFieldsEquals(
            returnedTermoContratoAssinaturaEmpresa,
            getPersistedTermoContratoAssinaturaEmpresa(returnedTermoContratoAssinaturaEmpresa)
        );

        insertedTermoContratoAssinaturaEmpresa = returnedTermoContratoAssinaturaEmpresa;
    }

    @Test
    @Transactional
    void createTermoContratoAssinaturaEmpresaWithExistingId() throws Exception {
        // Create the TermoContratoAssinaturaEmpresa with an existing ID
        termoContratoAssinaturaEmpresa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTermoContratoAssinaturaEmpresaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(termoContratoAssinaturaEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermoContratoAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTermoContratoAssinaturaEmpresas() throws Exception {
        // Initialize the database
        insertedTermoContratoAssinaturaEmpresa = termoContratoAssinaturaEmpresaRepository.saveAndFlush(termoContratoAssinaturaEmpresa);

        // Get all the termoContratoAssinaturaEmpresaList
        restTermoContratoAssinaturaEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(termoContratoAssinaturaEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataAssinatura").value(hasItem(DEFAULT_DATA_ASSINATURA.toString())))
            .andExpect(jsonPath("$.[*].dataEnvioEmail").value(hasItem(DEFAULT_DATA_ENVIO_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].urlDocumentoAssinado").value(hasItem(DEFAULT_URL_DOCUMENTO_ASSINADO.toString())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTermoContratoAssinaturaEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(termoContratoAssinaturaEmpresaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTermoContratoAssinaturaEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(termoContratoAssinaturaEmpresaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTermoContratoAssinaturaEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(termoContratoAssinaturaEmpresaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTermoContratoAssinaturaEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(termoContratoAssinaturaEmpresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTermoContratoAssinaturaEmpresa() throws Exception {
        // Initialize the database
        insertedTermoContratoAssinaturaEmpresa = termoContratoAssinaturaEmpresaRepository.saveAndFlush(termoContratoAssinaturaEmpresa);

        // Get the termoContratoAssinaturaEmpresa
        restTermoContratoAssinaturaEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, termoContratoAssinaturaEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(termoContratoAssinaturaEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.dataAssinatura").value(DEFAULT_DATA_ASSINATURA.toString()))
            .andExpect(jsonPath("$.dataEnvioEmail").value(DEFAULT_DATA_ENVIO_EMAIL.toString()))
            .andExpect(jsonPath("$.urlDocumentoAssinado").value(DEFAULT_URL_DOCUMENTO_ASSINADO.toString()))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTermoContratoAssinaturaEmpresa() throws Exception {
        // Get the termoContratoAssinaturaEmpresa
        restTermoContratoAssinaturaEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTermoContratoAssinaturaEmpresa() throws Exception {
        // Initialize the database
        insertedTermoContratoAssinaturaEmpresa = termoContratoAssinaturaEmpresaRepository.saveAndFlush(termoContratoAssinaturaEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the termoContratoAssinaturaEmpresa
        TermoContratoAssinaturaEmpresa updatedTermoContratoAssinaturaEmpresa = termoContratoAssinaturaEmpresaRepository
            .findById(termoContratoAssinaturaEmpresa.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedTermoContratoAssinaturaEmpresa are not directly saved in db
        em.detach(updatedTermoContratoAssinaturaEmpresa);
        updatedTermoContratoAssinaturaEmpresa
            .dataAssinatura(UPDATED_DATA_ASSINATURA)
            .dataEnvioEmail(UPDATED_DATA_ENVIO_EMAIL)
            .urlDocumentoAssinado(UPDATED_URL_DOCUMENTO_ASSINADO)
            .situacao(UPDATED_SITUACAO);

        restTermoContratoAssinaturaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTermoContratoAssinaturaEmpresa.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTermoContratoAssinaturaEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the TermoContratoAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTermoContratoAssinaturaEmpresaToMatchAllProperties(updatedTermoContratoAssinaturaEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingTermoContratoAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoContratoAssinaturaEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTermoContratoAssinaturaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, termoContratoAssinaturaEmpresa.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(termoContratoAssinaturaEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermoContratoAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTermoContratoAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoContratoAssinaturaEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermoContratoAssinaturaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(termoContratoAssinaturaEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermoContratoAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTermoContratoAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoContratoAssinaturaEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermoContratoAssinaturaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(termoContratoAssinaturaEmpresa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TermoContratoAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTermoContratoAssinaturaEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedTermoContratoAssinaturaEmpresa = termoContratoAssinaturaEmpresaRepository.saveAndFlush(termoContratoAssinaturaEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the termoContratoAssinaturaEmpresa using partial update
        TermoContratoAssinaturaEmpresa partialUpdatedTermoContratoAssinaturaEmpresa = new TermoContratoAssinaturaEmpresa();
        partialUpdatedTermoContratoAssinaturaEmpresa.setId(termoContratoAssinaturaEmpresa.getId());

        partialUpdatedTermoContratoAssinaturaEmpresa
            .dataEnvioEmail(UPDATED_DATA_ENVIO_EMAIL)
            .urlDocumentoAssinado(UPDATED_URL_DOCUMENTO_ASSINADO)
            .situacao(UPDATED_SITUACAO);

        restTermoContratoAssinaturaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTermoContratoAssinaturaEmpresa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTermoContratoAssinaturaEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the TermoContratoAssinaturaEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTermoContratoAssinaturaEmpresaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTermoContratoAssinaturaEmpresa, termoContratoAssinaturaEmpresa),
            getPersistedTermoContratoAssinaturaEmpresa(termoContratoAssinaturaEmpresa)
        );
    }

    @Test
    @Transactional
    void fullUpdateTermoContratoAssinaturaEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedTermoContratoAssinaturaEmpresa = termoContratoAssinaturaEmpresaRepository.saveAndFlush(termoContratoAssinaturaEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the termoContratoAssinaturaEmpresa using partial update
        TermoContratoAssinaturaEmpresa partialUpdatedTermoContratoAssinaturaEmpresa = new TermoContratoAssinaturaEmpresa();
        partialUpdatedTermoContratoAssinaturaEmpresa.setId(termoContratoAssinaturaEmpresa.getId());

        partialUpdatedTermoContratoAssinaturaEmpresa
            .dataAssinatura(UPDATED_DATA_ASSINATURA)
            .dataEnvioEmail(UPDATED_DATA_ENVIO_EMAIL)
            .urlDocumentoAssinado(UPDATED_URL_DOCUMENTO_ASSINADO)
            .situacao(UPDATED_SITUACAO);

        restTermoContratoAssinaturaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTermoContratoAssinaturaEmpresa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTermoContratoAssinaturaEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the TermoContratoAssinaturaEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTermoContratoAssinaturaEmpresaUpdatableFieldsEquals(
            partialUpdatedTermoContratoAssinaturaEmpresa,
            getPersistedTermoContratoAssinaturaEmpresa(partialUpdatedTermoContratoAssinaturaEmpresa)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTermoContratoAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoContratoAssinaturaEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTermoContratoAssinaturaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, termoContratoAssinaturaEmpresa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(termoContratoAssinaturaEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermoContratoAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTermoContratoAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoContratoAssinaturaEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermoContratoAssinaturaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(termoContratoAssinaturaEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermoContratoAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTermoContratoAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoContratoAssinaturaEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermoContratoAssinaturaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(termoContratoAssinaturaEmpresa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TermoContratoAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTermoContratoAssinaturaEmpresa() throws Exception {
        // Initialize the database
        insertedTermoContratoAssinaturaEmpresa = termoContratoAssinaturaEmpresaRepository.saveAndFlush(termoContratoAssinaturaEmpresa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the termoContratoAssinaturaEmpresa
        restTermoContratoAssinaturaEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, termoContratoAssinaturaEmpresa.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return termoContratoAssinaturaEmpresaRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected TermoContratoAssinaturaEmpresa getPersistedTermoContratoAssinaturaEmpresa(
        TermoContratoAssinaturaEmpresa termoContratoAssinaturaEmpresa
    ) {
        return termoContratoAssinaturaEmpresaRepository.findById(termoContratoAssinaturaEmpresa.getId()).orElseThrow();
    }

    protected void assertPersistedTermoContratoAssinaturaEmpresaToMatchAllProperties(
        TermoContratoAssinaturaEmpresa expectedTermoContratoAssinaturaEmpresa
    ) {
        assertTermoContratoAssinaturaEmpresaAllPropertiesEquals(
            expectedTermoContratoAssinaturaEmpresa,
            getPersistedTermoContratoAssinaturaEmpresa(expectedTermoContratoAssinaturaEmpresa)
        );
    }

    protected void assertPersistedTermoContratoAssinaturaEmpresaToMatchUpdatableProperties(
        TermoContratoAssinaturaEmpresa expectedTermoContratoAssinaturaEmpresa
    ) {
        assertTermoContratoAssinaturaEmpresaAllUpdatablePropertiesEquals(
            expectedTermoContratoAssinaturaEmpresa,
            getPersistedTermoContratoAssinaturaEmpresa(expectedTermoContratoAssinaturaEmpresa)
        );
    }
}
