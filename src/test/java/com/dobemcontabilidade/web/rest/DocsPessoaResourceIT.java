package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.DocsPessoaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.DocsPessoa;
import com.dobemcontabilidade.domain.PessoaFisica;
import com.dobemcontabilidade.repository.DocsPessoaRepository;
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
 * Integration tests for the {@link DocsPessoaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DocsPessoaResourceIT {

    private static final String DEFAULT_URL_ARQUIVO = "AAAAAAAAAA";
    private static final String UPDATED_URL_ARQUIVO = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/docs-pessoas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DocsPessoaRepository docsPessoaRepository;

    @Mock
    private DocsPessoaRepository docsPessoaRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocsPessoaMockMvc;

    private DocsPessoa docsPessoa;

    private DocsPessoa insertedDocsPessoa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocsPessoa createEntity(EntityManager em) {
        DocsPessoa docsPessoa = new DocsPessoa().urlArquivo(DEFAULT_URL_ARQUIVO).tipo(DEFAULT_TIPO).descricao(DEFAULT_DESCRICAO);
        // Add required entity
        PessoaFisica pessoaFisica;
        if (TestUtil.findAll(em, PessoaFisica.class).isEmpty()) {
            pessoaFisica = PessoaFisicaResourceIT.createEntity(em);
            em.persist(pessoaFisica);
            em.flush();
        } else {
            pessoaFisica = TestUtil.findAll(em, PessoaFisica.class).get(0);
        }
        docsPessoa.setPessoa(pessoaFisica);
        return docsPessoa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocsPessoa createUpdatedEntity(EntityManager em) {
        DocsPessoa docsPessoa = new DocsPessoa().urlArquivo(UPDATED_URL_ARQUIVO).tipo(UPDATED_TIPO).descricao(UPDATED_DESCRICAO);
        // Add required entity
        PessoaFisica pessoaFisica;
        if (TestUtil.findAll(em, PessoaFisica.class).isEmpty()) {
            pessoaFisica = PessoaFisicaResourceIT.createUpdatedEntity(em);
            em.persist(pessoaFisica);
            em.flush();
        } else {
            pessoaFisica = TestUtil.findAll(em, PessoaFisica.class).get(0);
        }
        docsPessoa.setPessoa(pessoaFisica);
        return docsPessoa;
    }

    @BeforeEach
    public void initTest() {
        docsPessoa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedDocsPessoa != null) {
            docsPessoaRepository.delete(insertedDocsPessoa);
            insertedDocsPessoa = null;
        }
    }

    @Test
    @Transactional
    void createDocsPessoa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DocsPessoa
        var returnedDocsPessoa = om.readValue(
            restDocsPessoaMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(docsPessoa))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DocsPessoa.class
        );

        // Validate the DocsPessoa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDocsPessoaUpdatableFieldsEquals(returnedDocsPessoa, getPersistedDocsPessoa(returnedDocsPessoa));

        insertedDocsPessoa = returnedDocsPessoa;
    }

    @Test
    @Transactional
    void createDocsPessoaWithExistingId() throws Exception {
        // Create the DocsPessoa with an existing ID
        docsPessoa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocsPessoaMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(docsPessoa)))
            .andExpect(status().isBadRequest());

        // Validate the DocsPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDocsPessoas() throws Exception {
        // Initialize the database
        insertedDocsPessoa = docsPessoaRepository.saveAndFlush(docsPessoa);

        // Get all the docsPessoaList
        restDocsPessoaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(docsPessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].urlArquivo").value(hasItem(DEFAULT_URL_ARQUIVO.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDocsPessoasWithEagerRelationshipsIsEnabled() throws Exception {
        when(docsPessoaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDocsPessoaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(docsPessoaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDocsPessoasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(docsPessoaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDocsPessoaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(docsPessoaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDocsPessoa() throws Exception {
        // Initialize the database
        insertedDocsPessoa = docsPessoaRepository.saveAndFlush(docsPessoa);

        // Get the docsPessoa
        restDocsPessoaMockMvc
            .perform(get(ENTITY_API_URL_ID, docsPessoa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(docsPessoa.getId().intValue()))
            .andExpect(jsonPath("$.urlArquivo").value(DEFAULT_URL_ARQUIVO.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDocsPessoa() throws Exception {
        // Get the docsPessoa
        restDocsPessoaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDocsPessoa() throws Exception {
        // Initialize the database
        insertedDocsPessoa = docsPessoaRepository.saveAndFlush(docsPessoa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the docsPessoa
        DocsPessoa updatedDocsPessoa = docsPessoaRepository.findById(docsPessoa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDocsPessoa are not directly saved in db
        em.detach(updatedDocsPessoa);
        updatedDocsPessoa.urlArquivo(UPDATED_URL_ARQUIVO).tipo(UPDATED_TIPO).descricao(UPDATED_DESCRICAO);

        restDocsPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDocsPessoa.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDocsPessoa))
            )
            .andExpect(status().isOk());

        // Validate the DocsPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDocsPessoaToMatchAllProperties(updatedDocsPessoa);
    }

    @Test
    @Transactional
    void putNonExistingDocsPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        docsPessoa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocsPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, docsPessoa.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(docsPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocsPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocsPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        docsPessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocsPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(docsPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocsPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocsPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        docsPessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocsPessoaMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(docsPessoa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocsPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocsPessoaWithPatch() throws Exception {
        // Initialize the database
        insertedDocsPessoa = docsPessoaRepository.saveAndFlush(docsPessoa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the docsPessoa using partial update
        DocsPessoa partialUpdatedDocsPessoa = new DocsPessoa();
        partialUpdatedDocsPessoa.setId(docsPessoa.getId());

        partialUpdatedDocsPessoa.tipo(UPDATED_TIPO);

        restDocsPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocsPessoa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDocsPessoa))
            )
            .andExpect(status().isOk());

        // Validate the DocsPessoa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDocsPessoaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDocsPessoa, docsPessoa),
            getPersistedDocsPessoa(docsPessoa)
        );
    }

    @Test
    @Transactional
    void fullUpdateDocsPessoaWithPatch() throws Exception {
        // Initialize the database
        insertedDocsPessoa = docsPessoaRepository.saveAndFlush(docsPessoa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the docsPessoa using partial update
        DocsPessoa partialUpdatedDocsPessoa = new DocsPessoa();
        partialUpdatedDocsPessoa.setId(docsPessoa.getId());

        partialUpdatedDocsPessoa.urlArquivo(UPDATED_URL_ARQUIVO).tipo(UPDATED_TIPO).descricao(UPDATED_DESCRICAO);

        restDocsPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocsPessoa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDocsPessoa))
            )
            .andExpect(status().isOk());

        // Validate the DocsPessoa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDocsPessoaUpdatableFieldsEquals(partialUpdatedDocsPessoa, getPersistedDocsPessoa(partialUpdatedDocsPessoa));
    }

    @Test
    @Transactional
    void patchNonExistingDocsPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        docsPessoa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocsPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, docsPessoa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(docsPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocsPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocsPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        docsPessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocsPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(docsPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocsPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocsPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        docsPessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocsPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(docsPessoa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocsPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocsPessoa() throws Exception {
        // Initialize the database
        insertedDocsPessoa = docsPessoaRepository.saveAndFlush(docsPessoa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the docsPessoa
        restDocsPessoaMockMvc
            .perform(delete(ENTITY_API_URL_ID, docsPessoa.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return docsPessoaRepository.count();
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

    protected DocsPessoa getPersistedDocsPessoa(DocsPessoa docsPessoa) {
        return docsPessoaRepository.findById(docsPessoa.getId()).orElseThrow();
    }

    protected void assertPersistedDocsPessoaToMatchAllProperties(DocsPessoa expectedDocsPessoa) {
        assertDocsPessoaAllPropertiesEquals(expectedDocsPessoa, getPersistedDocsPessoa(expectedDocsPessoa));
    }

    protected void assertPersistedDocsPessoaToMatchUpdatableProperties(DocsPessoa expectedDocsPessoa) {
        assertDocsPessoaAllUpdatablePropertiesEquals(expectedDocsPessoa, getPersistedDocsPessoa(expectedDocsPessoa));
    }
}
