package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.ProfissaoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Profissao;
import com.dobemcontabilidade.domain.Socio;
import com.dobemcontabilidade.repository.ProfissaoRepository;
import com.dobemcontabilidade.service.ProfissaoService;
import com.dobemcontabilidade.service.dto.ProfissaoDTO;
import com.dobemcontabilidade.service.mapper.ProfissaoMapper;
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
 * Integration tests for the {@link ProfissaoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProfissaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/profissaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProfissaoRepository profissaoRepository;

    @Mock
    private ProfissaoRepository profissaoRepositoryMock;

    @Autowired
    private ProfissaoMapper profissaoMapper;

    @Mock
    private ProfissaoService profissaoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfissaoMockMvc;

    private Profissao profissao;

    private Profissao insertedProfissao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profissao createEntity(EntityManager em) {
        Profissao profissao = new Profissao().nome(DEFAULT_NOME).descricao(DEFAULT_DESCRICAO);
        // Add required entity
        Socio socio;
        if (TestUtil.findAll(em, Socio.class).isEmpty()) {
            socio = SocioResourceIT.createEntity(em);
            em.persist(socio);
            em.flush();
        } else {
            socio = TestUtil.findAll(em, Socio.class).get(0);
        }
        profissao.setSocio(socio);
        return profissao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profissao createUpdatedEntity(EntityManager em) {
        Profissao profissao = new Profissao().nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);
        // Add required entity
        Socio socio;
        if (TestUtil.findAll(em, Socio.class).isEmpty()) {
            socio = SocioResourceIT.createUpdatedEntity(em);
            em.persist(socio);
            em.flush();
        } else {
            socio = TestUtil.findAll(em, Socio.class).get(0);
        }
        profissao.setSocio(socio);
        return profissao;
    }

    @BeforeEach
    public void initTest() {
        profissao = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedProfissao != null) {
            profissaoRepository.delete(insertedProfissao);
            insertedProfissao = null;
        }
    }

    @Test
    @Transactional
    void createProfissao() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Profissao
        ProfissaoDTO profissaoDTO = profissaoMapper.toDto(profissao);
        var returnedProfissaoDTO = om.readValue(
            restProfissaoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(profissaoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ProfissaoDTO.class
        );

        // Validate the Profissao in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedProfissao = profissaoMapper.toEntity(returnedProfissaoDTO);
        assertProfissaoUpdatableFieldsEquals(returnedProfissao, getPersistedProfissao(returnedProfissao));

        insertedProfissao = returnedProfissao;
    }

    @Test
    @Transactional
    void createProfissaoWithExistingId() throws Exception {
        // Create the Profissao with an existing ID
        profissao.setId(1L);
        ProfissaoDTO profissaoDTO = profissaoMapper.toDto(profissao);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfissaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(profissaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profissao in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProfissaos() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        // Get all the profissaoList
        restProfissaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profissao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProfissaosWithEagerRelationshipsIsEnabled() throws Exception {
        when(profissaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProfissaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(profissaoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProfissaosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(profissaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProfissaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(profissaoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getProfissao() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        // Get the profissao
        restProfissaoMockMvc
            .perform(get(ENTITY_API_URL_ID, profissao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(profissao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getProfissaosByIdFiltering() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        Long id = profissao.getId();

        defaultProfissaoFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultProfissaoFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultProfissaoFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProfissaosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        // Get all the profissaoList where nome equals to
        defaultProfissaoFiltering("nome.equals=" + DEFAULT_NOME, "nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllProfissaosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        // Get all the profissaoList where nome in
        defaultProfissaoFiltering("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME, "nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllProfissaosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        // Get all the profissaoList where nome is not null
        defaultProfissaoFiltering("nome.specified=true", "nome.specified=false");
    }

    @Test
    @Transactional
    void getAllProfissaosByNomeContainsSomething() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        // Get all the profissaoList where nome contains
        defaultProfissaoFiltering("nome.contains=" + DEFAULT_NOME, "nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllProfissaosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        // Get all the profissaoList where nome does not contain
        defaultProfissaoFiltering("nome.doesNotContain=" + UPDATED_NOME, "nome.doesNotContain=" + DEFAULT_NOME);
    }

    @Test
    @Transactional
    void getAllProfissaosBySocioIsEqualToSomething() throws Exception {
        Socio socio;
        if (TestUtil.findAll(em, Socio.class).isEmpty()) {
            profissaoRepository.saveAndFlush(profissao);
            socio = SocioResourceIT.createEntity(em);
        } else {
            socio = TestUtil.findAll(em, Socio.class).get(0);
        }
        em.persist(socio);
        em.flush();
        profissao.setSocio(socio);
        profissaoRepository.saveAndFlush(profissao);
        Long socioId = socio.getId();
        // Get all the profissaoList where socio equals to socioId
        defaultProfissaoShouldBeFound("socioId.equals=" + socioId);

        // Get all the profissaoList where socio equals to (socioId + 1)
        defaultProfissaoShouldNotBeFound("socioId.equals=" + (socioId + 1));
    }

    private void defaultProfissaoFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultProfissaoShouldBeFound(shouldBeFound);
        defaultProfissaoShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProfissaoShouldBeFound(String filter) throws Exception {
        restProfissaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profissao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restProfissaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProfissaoShouldNotBeFound(String filter) throws Exception {
        restProfissaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProfissaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProfissao() throws Exception {
        // Get the profissao
        restProfissaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProfissao() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the profissao
        Profissao updatedProfissao = profissaoRepository.findById(profissao.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProfissao are not directly saved in db
        em.detach(updatedProfissao);
        updatedProfissao.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);
        ProfissaoDTO profissaoDTO = profissaoMapper.toDto(updatedProfissao);

        restProfissaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, profissaoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(profissaoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Profissao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProfissaoToMatchAllProperties(updatedProfissao);
    }

    @Test
    @Transactional
    void putNonExistingProfissao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profissao.setId(longCount.incrementAndGet());

        // Create the Profissao
        ProfissaoDTO profissaoDTO = profissaoMapper.toDto(profissao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfissaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, profissaoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(profissaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profissao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProfissao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profissao.setId(longCount.incrementAndGet());

        // Create the Profissao
        ProfissaoDTO profissaoDTO = profissaoMapper.toDto(profissao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfissaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(profissaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profissao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProfissao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profissao.setId(longCount.incrementAndGet());

        // Create the Profissao
        ProfissaoDTO profissaoDTO = profissaoMapper.toDto(profissao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfissaoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(profissaoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Profissao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProfissaoWithPatch() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the profissao using partial update
        Profissao partialUpdatedProfissao = new Profissao();
        partialUpdatedProfissao.setId(profissao.getId());

        partialUpdatedProfissao.descricao(UPDATED_DESCRICAO);

        restProfissaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfissao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProfissao))
            )
            .andExpect(status().isOk());

        // Validate the Profissao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProfissaoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProfissao, profissao),
            getPersistedProfissao(profissao)
        );
    }

    @Test
    @Transactional
    void fullUpdateProfissaoWithPatch() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the profissao using partial update
        Profissao partialUpdatedProfissao = new Profissao();
        partialUpdatedProfissao.setId(profissao.getId());

        partialUpdatedProfissao.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restProfissaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfissao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProfissao))
            )
            .andExpect(status().isOk());

        // Validate the Profissao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProfissaoUpdatableFieldsEquals(partialUpdatedProfissao, getPersistedProfissao(partialUpdatedProfissao));
    }

    @Test
    @Transactional
    void patchNonExistingProfissao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profissao.setId(longCount.incrementAndGet());

        // Create the Profissao
        ProfissaoDTO profissaoDTO = profissaoMapper.toDto(profissao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfissaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, profissaoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(profissaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profissao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProfissao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profissao.setId(longCount.incrementAndGet());

        // Create the Profissao
        ProfissaoDTO profissaoDTO = profissaoMapper.toDto(profissao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfissaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(profissaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profissao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProfissao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profissao.setId(longCount.incrementAndGet());

        // Create the Profissao
        ProfissaoDTO profissaoDTO = profissaoMapper.toDto(profissao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfissaoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(profissaoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Profissao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProfissao() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the profissao
        restProfissaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, profissao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return profissaoRepository.count();
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

    protected Profissao getPersistedProfissao(Profissao profissao) {
        return profissaoRepository.findById(profissao.getId()).orElseThrow();
    }

    protected void assertPersistedProfissaoToMatchAllProperties(Profissao expectedProfissao) {
        assertProfissaoAllPropertiesEquals(expectedProfissao, getPersistedProfissao(expectedProfissao));
    }

    protected void assertPersistedProfissaoToMatchUpdatableProperties(Profissao expectedProfissao) {
        assertProfissaoAllUpdatablePropertiesEquals(expectedProfissao, getPersistedProfissao(expectedProfissao));
    }
}
