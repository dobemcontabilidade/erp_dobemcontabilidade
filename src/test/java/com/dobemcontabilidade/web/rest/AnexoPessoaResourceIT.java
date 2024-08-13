package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.AnexoPessoaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AnexoPessoa;
import com.dobemcontabilidade.domain.Pessoa;
import com.dobemcontabilidade.repository.AnexoPessoaRepository;
import com.dobemcontabilidade.service.AnexoPessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link AnexoPessoaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AnexoPessoaResourceIT {

    private static final String DEFAULT_URL_ARQUIVO = "AAAAAAAAAA";
    private static final String UPDATED_URL_ARQUIVO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/anexo-pessoas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AnexoPessoaRepository anexoPessoaRepository;

    @Mock
    private AnexoPessoaRepository anexoPessoaRepositoryMock;

    @Mock
    private AnexoPessoaService anexoPessoaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnexoPessoaMockMvc;

    private AnexoPessoa anexoPessoa;

    private AnexoPessoa insertedAnexoPessoa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnexoPessoa createEntity(EntityManager em) {
        AnexoPessoa anexoPessoa = new AnexoPessoa().urlArquivo(DEFAULT_URL_ARQUIVO).descricao(DEFAULT_DESCRICAO);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        anexoPessoa.setPessoa(pessoa);
        return anexoPessoa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnexoPessoa createUpdatedEntity(EntityManager em) {
        AnexoPessoa anexoPessoa = new AnexoPessoa().urlArquivo(UPDATED_URL_ARQUIVO).descricao(UPDATED_DESCRICAO);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createUpdatedEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        anexoPessoa.setPessoa(pessoa);
        return anexoPessoa;
    }

    @BeforeEach
    public void initTest() {
        anexoPessoa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAnexoPessoa != null) {
            anexoPessoaRepository.delete(insertedAnexoPessoa);
            insertedAnexoPessoa = null;
        }
    }

    @Test
    @Transactional
    void createAnexoPessoa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AnexoPessoa
        var returnedAnexoPessoa = om.readValue(
            restAnexoPessoaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoPessoa)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AnexoPessoa.class
        );

        // Validate the AnexoPessoa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAnexoPessoaUpdatableFieldsEquals(returnedAnexoPessoa, getPersistedAnexoPessoa(returnedAnexoPessoa));

        insertedAnexoPessoa = returnedAnexoPessoa;
    }

    @Test
    @Transactional
    void createAnexoPessoaWithExistingId() throws Exception {
        // Create the AnexoPessoa with an existing ID
        anexoPessoa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnexoPessoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoPessoa)))
            .andExpect(status().isBadRequest());

        // Validate the AnexoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAnexoPessoas() throws Exception {
        // Initialize the database
        insertedAnexoPessoa = anexoPessoaRepository.saveAndFlush(anexoPessoa);

        // Get all the anexoPessoaList
        restAnexoPessoaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anexoPessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].urlArquivo").value(hasItem(DEFAULT_URL_ARQUIVO.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAnexoPessoasWithEagerRelationshipsIsEnabled() throws Exception {
        when(anexoPessoaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAnexoPessoaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(anexoPessoaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAnexoPessoasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(anexoPessoaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAnexoPessoaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(anexoPessoaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAnexoPessoa() throws Exception {
        // Initialize the database
        insertedAnexoPessoa = anexoPessoaRepository.saveAndFlush(anexoPessoa);

        // Get the anexoPessoa
        restAnexoPessoaMockMvc
            .perform(get(ENTITY_API_URL_ID, anexoPessoa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(anexoPessoa.getId().intValue()))
            .andExpect(jsonPath("$.urlArquivo").value(DEFAULT_URL_ARQUIVO.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAnexoPessoa() throws Exception {
        // Get the anexoPessoa
        restAnexoPessoaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAnexoPessoa() throws Exception {
        // Initialize the database
        insertedAnexoPessoa = anexoPessoaRepository.saveAndFlush(anexoPessoa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoPessoa
        AnexoPessoa updatedAnexoPessoa = anexoPessoaRepository.findById(anexoPessoa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAnexoPessoa are not directly saved in db
        em.detach(updatedAnexoPessoa);
        updatedAnexoPessoa.urlArquivo(UPDATED_URL_ARQUIVO).descricao(UPDATED_DESCRICAO);

        restAnexoPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAnexoPessoa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAnexoPessoa))
            )
            .andExpect(status().isOk());

        // Validate the AnexoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAnexoPessoaToMatchAllProperties(updatedAnexoPessoa);
    }

    @Test
    @Transactional
    void putNonExistingAnexoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoPessoa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anexoPessoa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anexoPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnexoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoPessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anexoPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnexoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoPessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoPessoaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoPessoa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnexoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnexoPessoaWithPatch() throws Exception {
        // Initialize the database
        insertedAnexoPessoa = anexoPessoaRepository.saveAndFlush(anexoPessoa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoPessoa using partial update
        AnexoPessoa partialUpdatedAnexoPessoa = new AnexoPessoa();
        partialUpdatedAnexoPessoa.setId(anexoPessoa.getId());

        partialUpdatedAnexoPessoa.urlArquivo(UPDATED_URL_ARQUIVO);

        restAnexoPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnexoPessoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnexoPessoa))
            )
            .andExpect(status().isOk());

        // Validate the AnexoPessoa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnexoPessoaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAnexoPessoa, anexoPessoa),
            getPersistedAnexoPessoa(anexoPessoa)
        );
    }

    @Test
    @Transactional
    void fullUpdateAnexoPessoaWithPatch() throws Exception {
        // Initialize the database
        insertedAnexoPessoa = anexoPessoaRepository.saveAndFlush(anexoPessoa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoPessoa using partial update
        AnexoPessoa partialUpdatedAnexoPessoa = new AnexoPessoa();
        partialUpdatedAnexoPessoa.setId(anexoPessoa.getId());

        partialUpdatedAnexoPessoa.urlArquivo(UPDATED_URL_ARQUIVO).descricao(UPDATED_DESCRICAO);

        restAnexoPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnexoPessoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnexoPessoa))
            )
            .andExpect(status().isOk());

        // Validate the AnexoPessoa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnexoPessoaUpdatableFieldsEquals(partialUpdatedAnexoPessoa, getPersistedAnexoPessoa(partialUpdatedAnexoPessoa));
    }

    @Test
    @Transactional
    void patchNonExistingAnexoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoPessoa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, anexoPessoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anexoPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnexoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoPessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anexoPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnexoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoPessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoPessoaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(anexoPessoa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnexoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnexoPessoa() throws Exception {
        // Initialize the database
        insertedAnexoPessoa = anexoPessoaRepository.saveAndFlush(anexoPessoa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the anexoPessoa
        restAnexoPessoaMockMvc
            .perform(delete(ENTITY_API_URL_ID, anexoPessoa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return anexoPessoaRepository.count();
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

    protected AnexoPessoa getPersistedAnexoPessoa(AnexoPessoa anexoPessoa) {
        return anexoPessoaRepository.findById(anexoPessoa.getId()).orElseThrow();
    }

    protected void assertPersistedAnexoPessoaToMatchAllProperties(AnexoPessoa expectedAnexoPessoa) {
        assertAnexoPessoaAllPropertiesEquals(expectedAnexoPessoa, getPersistedAnexoPessoa(expectedAnexoPessoa));
    }

    protected void assertPersistedAnexoPessoaToMatchUpdatableProperties(AnexoPessoa expectedAnexoPessoa) {
        assertAnexoPessoaAllUpdatablePropertiesEquals(expectedAnexoPessoa, getPersistedAnexoPessoa(expectedAnexoPessoa));
    }
}
