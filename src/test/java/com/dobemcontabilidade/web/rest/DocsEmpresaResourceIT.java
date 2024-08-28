package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.DocsEmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.DocsEmpresa;
import com.dobemcontabilidade.domain.Pessoajuridica;
import com.dobemcontabilidade.repository.DocsEmpresaRepository;
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
 * Integration tests for the {@link DocsEmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DocsEmpresaResourceIT {

    private static final String DEFAULT_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_EMISSAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_EMISSAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_ENCERRAMENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ENCERRAMENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ORGAO_EMISSOR = "AAAAAAAAAA";
    private static final String UPDATED_ORGAO_EMISSOR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/docs-empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DocsEmpresaRepository docsEmpresaRepository;

    @Mock
    private DocsEmpresaRepository docsEmpresaRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocsEmpresaMockMvc;

    private DocsEmpresa docsEmpresa;

    private DocsEmpresa insertedDocsEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocsEmpresa createEntity(EntityManager em) {
        DocsEmpresa docsEmpresa = new DocsEmpresa()
            .documento(DEFAULT_DOCUMENTO)
            .descricao(DEFAULT_DESCRICAO)
            .url(DEFAULT_URL)
            .dataEmissao(DEFAULT_DATA_EMISSAO)
            .dataEncerramento(DEFAULT_DATA_ENCERRAMENTO)
            .orgaoEmissor(DEFAULT_ORGAO_EMISSOR);
        // Add required entity
        Pessoajuridica pessoajuridica;
        if (TestUtil.findAll(em, Pessoajuridica.class).isEmpty()) {
            pessoajuridica = PessoajuridicaResourceIT.createEntity(em);
            em.persist(pessoajuridica);
            em.flush();
        } else {
            pessoajuridica = TestUtil.findAll(em, Pessoajuridica.class).get(0);
        }
        docsEmpresa.setPessoaJuridica(pessoajuridica);
        return docsEmpresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocsEmpresa createUpdatedEntity(EntityManager em) {
        DocsEmpresa docsEmpresa = new DocsEmpresa()
            .documento(UPDATED_DOCUMENTO)
            .descricao(UPDATED_DESCRICAO)
            .url(UPDATED_URL)
            .dataEmissao(UPDATED_DATA_EMISSAO)
            .dataEncerramento(UPDATED_DATA_ENCERRAMENTO)
            .orgaoEmissor(UPDATED_ORGAO_EMISSOR);
        // Add required entity
        Pessoajuridica pessoajuridica;
        if (TestUtil.findAll(em, Pessoajuridica.class).isEmpty()) {
            pessoajuridica = PessoajuridicaResourceIT.createUpdatedEntity(em);
            em.persist(pessoajuridica);
            em.flush();
        } else {
            pessoajuridica = TestUtil.findAll(em, Pessoajuridica.class).get(0);
        }
        docsEmpresa.setPessoaJuridica(pessoajuridica);
        return docsEmpresa;
    }

    @BeforeEach
    public void initTest() {
        docsEmpresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedDocsEmpresa != null) {
            docsEmpresaRepository.delete(insertedDocsEmpresa);
            insertedDocsEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createDocsEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DocsEmpresa
        var returnedDocsEmpresa = om.readValue(
            restDocsEmpresaMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(docsEmpresa))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DocsEmpresa.class
        );

        // Validate the DocsEmpresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDocsEmpresaUpdatableFieldsEquals(returnedDocsEmpresa, getPersistedDocsEmpresa(returnedDocsEmpresa));

        insertedDocsEmpresa = returnedDocsEmpresa;
    }

    @Test
    @Transactional
    void createDocsEmpresaWithExistingId() throws Exception {
        // Create the DocsEmpresa with an existing ID
        docsEmpresa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocsEmpresaMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(docsEmpresa)))
            .andExpect(status().isBadRequest());

        // Validate the DocsEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDocsEmpresas() throws Exception {
        // Initialize the database
        insertedDocsEmpresa = docsEmpresaRepository.saveAndFlush(docsEmpresa);

        // Get all the docsEmpresaList
        restDocsEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(docsEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].dataEmissao").value(hasItem(DEFAULT_DATA_EMISSAO.toString())))
            .andExpect(jsonPath("$.[*].dataEncerramento").value(hasItem(DEFAULT_DATA_ENCERRAMENTO.toString())))
            .andExpect(jsonPath("$.[*].orgaoEmissor").value(hasItem(DEFAULT_ORGAO_EMISSOR)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDocsEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(docsEmpresaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDocsEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(docsEmpresaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDocsEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(docsEmpresaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDocsEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(docsEmpresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDocsEmpresa() throws Exception {
        // Initialize the database
        insertedDocsEmpresa = docsEmpresaRepository.saveAndFlush(docsEmpresa);

        // Get the docsEmpresa
        restDocsEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, docsEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(docsEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.documento").value(DEFAULT_DOCUMENTO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.dataEmissao").value(DEFAULT_DATA_EMISSAO.toString()))
            .andExpect(jsonPath("$.dataEncerramento").value(DEFAULT_DATA_ENCERRAMENTO.toString()))
            .andExpect(jsonPath("$.orgaoEmissor").value(DEFAULT_ORGAO_EMISSOR));
    }

    @Test
    @Transactional
    void getNonExistingDocsEmpresa() throws Exception {
        // Get the docsEmpresa
        restDocsEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDocsEmpresa() throws Exception {
        // Initialize the database
        insertedDocsEmpresa = docsEmpresaRepository.saveAndFlush(docsEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the docsEmpresa
        DocsEmpresa updatedDocsEmpresa = docsEmpresaRepository.findById(docsEmpresa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDocsEmpresa are not directly saved in db
        em.detach(updatedDocsEmpresa);
        updatedDocsEmpresa
            .documento(UPDATED_DOCUMENTO)
            .descricao(UPDATED_DESCRICAO)
            .url(UPDATED_URL)
            .dataEmissao(UPDATED_DATA_EMISSAO)
            .dataEncerramento(UPDATED_DATA_ENCERRAMENTO)
            .orgaoEmissor(UPDATED_ORGAO_EMISSOR);

        restDocsEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDocsEmpresa.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDocsEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the DocsEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDocsEmpresaToMatchAllProperties(updatedDocsEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingDocsEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        docsEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocsEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, docsEmpresa.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(docsEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocsEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocsEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        docsEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocsEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(docsEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocsEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocsEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        docsEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocsEmpresaMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(docsEmpresa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocsEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocsEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedDocsEmpresa = docsEmpresaRepository.saveAndFlush(docsEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the docsEmpresa using partial update
        DocsEmpresa partialUpdatedDocsEmpresa = new DocsEmpresa();
        partialUpdatedDocsEmpresa.setId(docsEmpresa.getId());

        partialUpdatedDocsEmpresa.documento(UPDATED_DOCUMENTO).descricao(UPDATED_DESCRICAO).dataEmissao(UPDATED_DATA_EMISSAO);

        restDocsEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocsEmpresa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDocsEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the DocsEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDocsEmpresaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDocsEmpresa, docsEmpresa),
            getPersistedDocsEmpresa(docsEmpresa)
        );
    }

    @Test
    @Transactional
    void fullUpdateDocsEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedDocsEmpresa = docsEmpresaRepository.saveAndFlush(docsEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the docsEmpresa using partial update
        DocsEmpresa partialUpdatedDocsEmpresa = new DocsEmpresa();
        partialUpdatedDocsEmpresa.setId(docsEmpresa.getId());

        partialUpdatedDocsEmpresa
            .documento(UPDATED_DOCUMENTO)
            .descricao(UPDATED_DESCRICAO)
            .url(UPDATED_URL)
            .dataEmissao(UPDATED_DATA_EMISSAO)
            .dataEncerramento(UPDATED_DATA_ENCERRAMENTO)
            .orgaoEmissor(UPDATED_ORGAO_EMISSOR);

        restDocsEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocsEmpresa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDocsEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the DocsEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDocsEmpresaUpdatableFieldsEquals(partialUpdatedDocsEmpresa, getPersistedDocsEmpresa(partialUpdatedDocsEmpresa));
    }

    @Test
    @Transactional
    void patchNonExistingDocsEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        docsEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocsEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, docsEmpresa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(docsEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocsEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocsEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        docsEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocsEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(docsEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocsEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocsEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        docsEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocsEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(docsEmpresa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocsEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocsEmpresa() throws Exception {
        // Initialize the database
        insertedDocsEmpresa = docsEmpresaRepository.saveAndFlush(docsEmpresa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the docsEmpresa
        restDocsEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, docsEmpresa.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return docsEmpresaRepository.count();
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

    protected DocsEmpresa getPersistedDocsEmpresa(DocsEmpresa docsEmpresa) {
        return docsEmpresaRepository.findById(docsEmpresa.getId()).orElseThrow();
    }

    protected void assertPersistedDocsEmpresaToMatchAllProperties(DocsEmpresa expectedDocsEmpresa) {
        assertDocsEmpresaAllPropertiesEquals(expectedDocsEmpresa, getPersistedDocsEmpresa(expectedDocsEmpresa));
    }

    protected void assertPersistedDocsEmpresaToMatchUpdatableProperties(DocsEmpresa expectedDocsEmpresa) {
        assertDocsEmpresaAllUpdatablePropertiesEquals(expectedDocsEmpresa, getPersistedDocsEmpresa(expectedDocsEmpresa));
    }
}
