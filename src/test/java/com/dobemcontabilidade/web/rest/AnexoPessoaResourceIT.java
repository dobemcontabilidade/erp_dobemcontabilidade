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
import com.dobemcontabilidade.service.dto.AnexoPessoaDTO;
import com.dobemcontabilidade.service.mapper.AnexoPessoaMapper;
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

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

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

    @Autowired
    private AnexoPessoaMapper anexoPessoaMapper;

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
        AnexoPessoa anexoPessoa = new AnexoPessoa().urlArquivo(DEFAULT_URL_ARQUIVO).tipo(DEFAULT_TIPO).descricao(DEFAULT_DESCRICAO);
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
        AnexoPessoa anexoPessoa = new AnexoPessoa().urlArquivo(UPDATED_URL_ARQUIVO).tipo(UPDATED_TIPO).descricao(UPDATED_DESCRICAO);
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
        AnexoPessoaDTO anexoPessoaDTO = anexoPessoaMapper.toDto(anexoPessoa);
        var returnedAnexoPessoaDTO = om.readValue(
            restAnexoPessoaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoPessoaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AnexoPessoaDTO.class
        );

        // Validate the AnexoPessoa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAnexoPessoa = anexoPessoaMapper.toEntity(returnedAnexoPessoaDTO);
        assertAnexoPessoaUpdatableFieldsEquals(returnedAnexoPessoa, getPersistedAnexoPessoa(returnedAnexoPessoa));

        insertedAnexoPessoa = returnedAnexoPessoa;
    }

    @Test
    @Transactional
    void createAnexoPessoaWithExistingId() throws Exception {
        // Create the AnexoPessoa with an existing ID
        anexoPessoa.setId(1L);
        AnexoPessoaDTO anexoPessoaDTO = anexoPessoaMapper.toDto(anexoPessoa);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnexoPessoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoPessoaDTO)))
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
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
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
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getAnexoPessoasByIdFiltering() throws Exception {
        // Initialize the database
        insertedAnexoPessoa = anexoPessoaRepository.saveAndFlush(anexoPessoa);

        Long id = anexoPessoa.getId();

        defaultAnexoPessoaFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultAnexoPessoaFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultAnexoPessoaFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAnexoPessoasByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAnexoPessoa = anexoPessoaRepository.saveAndFlush(anexoPessoa);

        // Get all the anexoPessoaList where tipo equals to
        defaultAnexoPessoaFiltering("tipo.equals=" + DEFAULT_TIPO, "tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    void getAllAnexoPessoasByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAnexoPessoa = anexoPessoaRepository.saveAndFlush(anexoPessoa);

        // Get all the anexoPessoaList where tipo in
        defaultAnexoPessoaFiltering("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO, "tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    void getAllAnexoPessoasByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAnexoPessoa = anexoPessoaRepository.saveAndFlush(anexoPessoa);

        // Get all the anexoPessoaList where tipo is not null
        defaultAnexoPessoaFiltering("tipo.specified=true", "tipo.specified=false");
    }

    @Test
    @Transactional
    void getAllAnexoPessoasByTipoContainsSomething() throws Exception {
        // Initialize the database
        insertedAnexoPessoa = anexoPessoaRepository.saveAndFlush(anexoPessoa);

        // Get all the anexoPessoaList where tipo contains
        defaultAnexoPessoaFiltering("tipo.contains=" + DEFAULT_TIPO, "tipo.contains=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    void getAllAnexoPessoasByTipoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAnexoPessoa = anexoPessoaRepository.saveAndFlush(anexoPessoa);

        // Get all the anexoPessoaList where tipo does not contain
        defaultAnexoPessoaFiltering("tipo.doesNotContain=" + UPDATED_TIPO, "tipo.doesNotContain=" + DEFAULT_TIPO);
    }

    @Test
    @Transactional
    void getAllAnexoPessoasByPessoaIsEqualToSomething() throws Exception {
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            anexoPessoaRepository.saveAndFlush(anexoPessoa);
            pessoa = PessoaResourceIT.createEntity(em);
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        em.persist(pessoa);
        em.flush();
        anexoPessoa.setPessoa(pessoa);
        anexoPessoaRepository.saveAndFlush(anexoPessoa);
        Long pessoaId = pessoa.getId();
        // Get all the anexoPessoaList where pessoa equals to pessoaId
        defaultAnexoPessoaShouldBeFound("pessoaId.equals=" + pessoaId);

        // Get all the anexoPessoaList where pessoa equals to (pessoaId + 1)
        defaultAnexoPessoaShouldNotBeFound("pessoaId.equals=" + (pessoaId + 1));
    }

    private void defaultAnexoPessoaFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultAnexoPessoaShouldBeFound(shouldBeFound);
        defaultAnexoPessoaShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAnexoPessoaShouldBeFound(String filter) throws Exception {
        restAnexoPessoaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anexoPessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].urlArquivo").value(hasItem(DEFAULT_URL_ARQUIVO.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restAnexoPessoaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAnexoPessoaShouldNotBeFound(String filter) throws Exception {
        restAnexoPessoaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAnexoPessoaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
        updatedAnexoPessoa.urlArquivo(UPDATED_URL_ARQUIVO).tipo(UPDATED_TIPO).descricao(UPDATED_DESCRICAO);
        AnexoPessoaDTO anexoPessoaDTO = anexoPessoaMapper.toDto(updatedAnexoPessoa);

        restAnexoPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anexoPessoaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anexoPessoaDTO))
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

        // Create the AnexoPessoa
        AnexoPessoaDTO anexoPessoaDTO = anexoPessoaMapper.toDto(anexoPessoa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anexoPessoaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anexoPessoaDTO))
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

        // Create the AnexoPessoa
        AnexoPessoaDTO anexoPessoaDTO = anexoPessoaMapper.toDto(anexoPessoa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anexoPessoaDTO))
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

        // Create the AnexoPessoa
        AnexoPessoaDTO anexoPessoaDTO = anexoPessoaMapper.toDto(anexoPessoa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoPessoaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoPessoaDTO)))
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

        partialUpdatedAnexoPessoa.urlArquivo(UPDATED_URL_ARQUIVO).tipo(UPDATED_TIPO).descricao(UPDATED_DESCRICAO);

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

        // Create the AnexoPessoa
        AnexoPessoaDTO anexoPessoaDTO = anexoPessoaMapper.toDto(anexoPessoa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, anexoPessoaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anexoPessoaDTO))
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

        // Create the AnexoPessoa
        AnexoPessoaDTO anexoPessoaDTO = anexoPessoaMapper.toDto(anexoPessoa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anexoPessoaDTO))
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

        // Create the AnexoPessoa
        AnexoPessoaDTO anexoPessoaDTO = anexoPessoaMapper.toDto(anexoPessoa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoPessoaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(anexoPessoaDTO)))
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
