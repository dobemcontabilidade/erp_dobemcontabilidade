package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.AnexoServicoContabilEmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AnexoRequerido;
import com.dobemcontabilidade.domain.AnexoServicoContabilEmpresa;
import com.dobemcontabilidade.domain.ServicoContabilAssinaturaEmpresa;
import com.dobemcontabilidade.repository.AnexoServicoContabilEmpresaRepository;
import com.dobemcontabilidade.service.AnexoServicoContabilEmpresaService;
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
 * Integration tests for the {@link AnexoServicoContabilEmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AnexoServicoContabilEmpresaResourceIT {

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_HORA_UPLOAD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_HORA_UPLOAD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/anexo-servico-contabil-empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AnexoServicoContabilEmpresaRepository anexoServicoContabilEmpresaRepository;

    @Mock
    private AnexoServicoContabilEmpresaRepository anexoServicoContabilEmpresaRepositoryMock;

    @Mock
    private AnexoServicoContabilEmpresaService anexoServicoContabilEmpresaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnexoServicoContabilEmpresaMockMvc;

    private AnexoServicoContabilEmpresa anexoServicoContabilEmpresa;

    private AnexoServicoContabilEmpresa insertedAnexoServicoContabilEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnexoServicoContabilEmpresa createEntity(EntityManager em) {
        AnexoServicoContabilEmpresa anexoServicoContabilEmpresa = new AnexoServicoContabilEmpresa()
            .link(DEFAULT_LINK)
            .dataHoraUpload(DEFAULT_DATA_HORA_UPLOAD);
        // Add required entity
        AnexoRequerido anexoRequerido;
        if (TestUtil.findAll(em, AnexoRequerido.class).isEmpty()) {
            anexoRequerido = AnexoRequeridoResourceIT.createEntity(em);
            em.persist(anexoRequerido);
            em.flush();
        } else {
            anexoRequerido = TestUtil.findAll(em, AnexoRequerido.class).get(0);
        }
        anexoServicoContabilEmpresa.setAnexoRequerido(anexoRequerido);
        // Add required entity
        ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa;
        if (TestUtil.findAll(em, ServicoContabilAssinaturaEmpresa.class).isEmpty()) {
            servicoContabilAssinaturaEmpresa = ServicoContabilAssinaturaEmpresaResourceIT.createEntity(em);
            em.persist(servicoContabilAssinaturaEmpresa);
            em.flush();
        } else {
            servicoContabilAssinaturaEmpresa = TestUtil.findAll(em, ServicoContabilAssinaturaEmpresa.class).get(0);
        }
        anexoServicoContabilEmpresa.setServicoContabilAssinaturaEmpresa(servicoContabilAssinaturaEmpresa);
        return anexoServicoContabilEmpresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnexoServicoContabilEmpresa createUpdatedEntity(EntityManager em) {
        AnexoServicoContabilEmpresa anexoServicoContabilEmpresa = new AnexoServicoContabilEmpresa()
            .link(UPDATED_LINK)
            .dataHoraUpload(UPDATED_DATA_HORA_UPLOAD);
        // Add required entity
        AnexoRequerido anexoRequerido;
        if (TestUtil.findAll(em, AnexoRequerido.class).isEmpty()) {
            anexoRequerido = AnexoRequeridoResourceIT.createUpdatedEntity(em);
            em.persist(anexoRequerido);
            em.flush();
        } else {
            anexoRequerido = TestUtil.findAll(em, AnexoRequerido.class).get(0);
        }
        anexoServicoContabilEmpresa.setAnexoRequerido(anexoRequerido);
        // Add required entity
        ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa;
        if (TestUtil.findAll(em, ServicoContabilAssinaturaEmpresa.class).isEmpty()) {
            servicoContabilAssinaturaEmpresa = ServicoContabilAssinaturaEmpresaResourceIT.createUpdatedEntity(em);
            em.persist(servicoContabilAssinaturaEmpresa);
            em.flush();
        } else {
            servicoContabilAssinaturaEmpresa = TestUtil.findAll(em, ServicoContabilAssinaturaEmpresa.class).get(0);
        }
        anexoServicoContabilEmpresa.setServicoContabilAssinaturaEmpresa(servicoContabilAssinaturaEmpresa);
        return anexoServicoContabilEmpresa;
    }

    @BeforeEach
    public void initTest() {
        anexoServicoContabilEmpresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAnexoServicoContabilEmpresa != null) {
            anexoServicoContabilEmpresaRepository.delete(insertedAnexoServicoContabilEmpresa);
            insertedAnexoServicoContabilEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createAnexoServicoContabilEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AnexoServicoContabilEmpresa
        var returnedAnexoServicoContabilEmpresa = om.readValue(
            restAnexoServicoContabilEmpresaMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoServicoContabilEmpresa))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AnexoServicoContabilEmpresa.class
        );

        // Validate the AnexoServicoContabilEmpresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAnexoServicoContabilEmpresaUpdatableFieldsEquals(
            returnedAnexoServicoContabilEmpresa,
            getPersistedAnexoServicoContabilEmpresa(returnedAnexoServicoContabilEmpresa)
        );

        insertedAnexoServicoContabilEmpresa = returnedAnexoServicoContabilEmpresa;
    }

    @Test
    @Transactional
    void createAnexoServicoContabilEmpresaWithExistingId() throws Exception {
        // Create the AnexoServicoContabilEmpresa with an existing ID
        anexoServicoContabilEmpresa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnexoServicoContabilEmpresaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoServicoContabilEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoServicoContabilEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAnexoServicoContabilEmpresas() throws Exception {
        // Initialize the database
        insertedAnexoServicoContabilEmpresa = anexoServicoContabilEmpresaRepository.saveAndFlush(anexoServicoContabilEmpresa);

        // Get all the anexoServicoContabilEmpresaList
        restAnexoServicoContabilEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anexoServicoContabilEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].dataHoraUpload").value(hasItem(DEFAULT_DATA_HORA_UPLOAD.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAnexoServicoContabilEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(anexoServicoContabilEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAnexoServicoContabilEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(anexoServicoContabilEmpresaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAnexoServicoContabilEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(anexoServicoContabilEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAnexoServicoContabilEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(anexoServicoContabilEmpresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAnexoServicoContabilEmpresa() throws Exception {
        // Initialize the database
        insertedAnexoServicoContabilEmpresa = anexoServicoContabilEmpresaRepository.saveAndFlush(anexoServicoContabilEmpresa);

        // Get the anexoServicoContabilEmpresa
        restAnexoServicoContabilEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, anexoServicoContabilEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(anexoServicoContabilEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK))
            .andExpect(jsonPath("$.dataHoraUpload").value(DEFAULT_DATA_HORA_UPLOAD.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAnexoServicoContabilEmpresa() throws Exception {
        // Get the anexoServicoContabilEmpresa
        restAnexoServicoContabilEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAnexoServicoContabilEmpresa() throws Exception {
        // Initialize the database
        insertedAnexoServicoContabilEmpresa = anexoServicoContabilEmpresaRepository.saveAndFlush(anexoServicoContabilEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoServicoContabilEmpresa
        AnexoServicoContabilEmpresa updatedAnexoServicoContabilEmpresa = anexoServicoContabilEmpresaRepository
            .findById(anexoServicoContabilEmpresa.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAnexoServicoContabilEmpresa are not directly saved in db
        em.detach(updatedAnexoServicoContabilEmpresa);
        updatedAnexoServicoContabilEmpresa.link(UPDATED_LINK).dataHoraUpload(UPDATED_DATA_HORA_UPLOAD);

        restAnexoServicoContabilEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAnexoServicoContabilEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAnexoServicoContabilEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the AnexoServicoContabilEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAnexoServicoContabilEmpresaToMatchAllProperties(updatedAnexoServicoContabilEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingAnexoServicoContabilEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoServicoContabilEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoServicoContabilEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anexoServicoContabilEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anexoServicoContabilEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoServicoContabilEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnexoServicoContabilEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoServicoContabilEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoServicoContabilEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anexoServicoContabilEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoServicoContabilEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnexoServicoContabilEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoServicoContabilEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoServicoContabilEmpresaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoServicoContabilEmpresa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnexoServicoContabilEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnexoServicoContabilEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedAnexoServicoContabilEmpresa = anexoServicoContabilEmpresaRepository.saveAndFlush(anexoServicoContabilEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoServicoContabilEmpresa using partial update
        AnexoServicoContabilEmpresa partialUpdatedAnexoServicoContabilEmpresa = new AnexoServicoContabilEmpresa();
        partialUpdatedAnexoServicoContabilEmpresa.setId(anexoServicoContabilEmpresa.getId());

        partialUpdatedAnexoServicoContabilEmpresa.link(UPDATED_LINK).dataHoraUpload(UPDATED_DATA_HORA_UPLOAD);

        restAnexoServicoContabilEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnexoServicoContabilEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnexoServicoContabilEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the AnexoServicoContabilEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnexoServicoContabilEmpresaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAnexoServicoContabilEmpresa, anexoServicoContabilEmpresa),
            getPersistedAnexoServicoContabilEmpresa(anexoServicoContabilEmpresa)
        );
    }

    @Test
    @Transactional
    void fullUpdateAnexoServicoContabilEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedAnexoServicoContabilEmpresa = anexoServicoContabilEmpresaRepository.saveAndFlush(anexoServicoContabilEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoServicoContabilEmpresa using partial update
        AnexoServicoContabilEmpresa partialUpdatedAnexoServicoContabilEmpresa = new AnexoServicoContabilEmpresa();
        partialUpdatedAnexoServicoContabilEmpresa.setId(anexoServicoContabilEmpresa.getId());

        partialUpdatedAnexoServicoContabilEmpresa.link(UPDATED_LINK).dataHoraUpload(UPDATED_DATA_HORA_UPLOAD);

        restAnexoServicoContabilEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnexoServicoContabilEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnexoServicoContabilEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the AnexoServicoContabilEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnexoServicoContabilEmpresaUpdatableFieldsEquals(
            partialUpdatedAnexoServicoContabilEmpresa,
            getPersistedAnexoServicoContabilEmpresa(partialUpdatedAnexoServicoContabilEmpresa)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAnexoServicoContabilEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoServicoContabilEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoServicoContabilEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, anexoServicoContabilEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anexoServicoContabilEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoServicoContabilEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnexoServicoContabilEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoServicoContabilEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoServicoContabilEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anexoServicoContabilEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoServicoContabilEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnexoServicoContabilEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoServicoContabilEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoServicoContabilEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(anexoServicoContabilEmpresa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnexoServicoContabilEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnexoServicoContabilEmpresa() throws Exception {
        // Initialize the database
        insertedAnexoServicoContabilEmpresa = anexoServicoContabilEmpresaRepository.saveAndFlush(anexoServicoContabilEmpresa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the anexoServicoContabilEmpresa
        restAnexoServicoContabilEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, anexoServicoContabilEmpresa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return anexoServicoContabilEmpresaRepository.count();
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

    protected AnexoServicoContabilEmpresa getPersistedAnexoServicoContabilEmpresa(AnexoServicoContabilEmpresa anexoServicoContabilEmpresa) {
        return anexoServicoContabilEmpresaRepository.findById(anexoServicoContabilEmpresa.getId()).orElseThrow();
    }

    protected void assertPersistedAnexoServicoContabilEmpresaToMatchAllProperties(
        AnexoServicoContabilEmpresa expectedAnexoServicoContabilEmpresa
    ) {
        assertAnexoServicoContabilEmpresaAllPropertiesEquals(
            expectedAnexoServicoContabilEmpresa,
            getPersistedAnexoServicoContabilEmpresa(expectedAnexoServicoContabilEmpresa)
        );
    }

    protected void assertPersistedAnexoServicoContabilEmpresaToMatchUpdatableProperties(
        AnexoServicoContabilEmpresa expectedAnexoServicoContabilEmpresa
    ) {
        assertAnexoServicoContabilEmpresaAllUpdatablePropertiesEquals(
            expectedAnexoServicoContabilEmpresa,
            getPersistedAnexoServicoContabilEmpresa(expectedAnexoServicoContabilEmpresa)
        );
    }
}
