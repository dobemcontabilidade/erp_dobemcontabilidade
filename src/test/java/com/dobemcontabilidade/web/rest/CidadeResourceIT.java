package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.CidadeAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Cidade;
import com.dobemcontabilidade.domain.Estado;
import com.dobemcontabilidade.repository.CidadeRepository;
import com.dobemcontabilidade.service.CidadeService;
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
 * Integration tests for the {@link CidadeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CidadeResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CONTRATACAO = false;
    private static final Boolean UPDATED_CONTRATACAO = true;

    private static final Boolean DEFAULT_ABERTURA = false;
    private static final Boolean UPDATED_ABERTURA = true;

    private static final String ENTITY_API_URL = "/api/cidades";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Mock
    private CidadeRepository cidadeRepositoryMock;

    @Mock
    private CidadeService cidadeServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCidadeMockMvc;

    private Cidade cidade;

    private Cidade insertedCidade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cidade createEntity(EntityManager em) {
        Cidade cidade = new Cidade().nome(DEFAULT_NOME).contratacao(DEFAULT_CONTRATACAO).abertura(DEFAULT_ABERTURA);
        // Add required entity
        Estado estado;
        if (TestUtil.findAll(em, Estado.class).isEmpty()) {
            estado = EstadoResourceIT.createEntity(em);
            em.persist(estado);
            em.flush();
        } else {
            estado = TestUtil.findAll(em, Estado.class).get(0);
        }
        cidade.setEstado(estado);
        return cidade;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cidade createUpdatedEntity(EntityManager em) {
        Cidade cidade = new Cidade().nome(UPDATED_NOME).contratacao(UPDATED_CONTRATACAO).abertura(UPDATED_ABERTURA);
        // Add required entity
        Estado estado;
        if (TestUtil.findAll(em, Estado.class).isEmpty()) {
            estado = EstadoResourceIT.createUpdatedEntity(em);
            em.persist(estado);
            em.flush();
        } else {
            estado = TestUtil.findAll(em, Estado.class).get(0);
        }
        cidade.setEstado(estado);
        return cidade;
    }

    @BeforeEach
    public void initTest() {
        cidade = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedCidade != null) {
            cidadeRepository.delete(insertedCidade);
            insertedCidade = null;
        }
    }

    @Test
    @Transactional
    void createCidade() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Cidade
        var returnedCidade = om.readValue(
            restCidadeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cidade)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Cidade.class
        );

        // Validate the Cidade in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCidadeUpdatableFieldsEquals(returnedCidade, getPersistedCidade(returnedCidade));

        insertedCidade = returnedCidade;
    }

    @Test
    @Transactional
    void createCidadeWithExistingId() throws Exception {
        // Create the Cidade with an existing ID
        cidade.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCidadeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cidade)))
            .andExpect(status().isBadRequest());

        // Validate the Cidade in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cidade.setNome(null);

        // Create the Cidade, which fails.

        restCidadeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cidade)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCidades() throws Exception {
        // Initialize the database
        insertedCidade = cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList
        restCidadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].contratacao").value(hasItem(DEFAULT_CONTRATACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].abertura").value(hasItem(DEFAULT_ABERTURA.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCidadesWithEagerRelationshipsIsEnabled() throws Exception {
        when(cidadeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCidadeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(cidadeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCidadesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(cidadeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCidadeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(cidadeRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCidade() throws Exception {
        // Initialize the database
        insertedCidade = cidadeRepository.saveAndFlush(cidade);

        // Get the cidade
        restCidadeMockMvc
            .perform(get(ENTITY_API_URL_ID, cidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cidade.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.contratacao").value(DEFAULT_CONTRATACAO.booleanValue()))
            .andExpect(jsonPath("$.abertura").value(DEFAULT_ABERTURA.booleanValue()));
    }

    @Test
    @Transactional
    void getCidadesByIdFiltering() throws Exception {
        // Initialize the database
        insertedCidade = cidadeRepository.saveAndFlush(cidade);

        Long id = cidade.getId();

        defaultCidadeFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultCidadeFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultCidadeFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCidadesByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCidade = cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where nome equals to
        defaultCidadeFiltering("nome.equals=" + DEFAULT_NOME, "nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllCidadesByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCidade = cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where nome in
        defaultCidadeFiltering("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME, "nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllCidadesByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCidade = cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where nome is not null
        defaultCidadeFiltering("nome.specified=true", "nome.specified=false");
    }

    @Test
    @Transactional
    void getAllCidadesByNomeContainsSomething() throws Exception {
        // Initialize the database
        insertedCidade = cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where nome contains
        defaultCidadeFiltering("nome.contains=" + DEFAULT_NOME, "nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllCidadesByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedCidade = cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where nome does not contain
        defaultCidadeFiltering("nome.doesNotContain=" + UPDATED_NOME, "nome.doesNotContain=" + DEFAULT_NOME);
    }

    @Test
    @Transactional
    void getAllCidadesByContratacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCidade = cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where contratacao equals to
        defaultCidadeFiltering("contratacao.equals=" + DEFAULT_CONTRATACAO, "contratacao.equals=" + UPDATED_CONTRATACAO);
    }

    @Test
    @Transactional
    void getAllCidadesByContratacaoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCidade = cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where contratacao in
        defaultCidadeFiltering(
            "contratacao.in=" + DEFAULT_CONTRATACAO + "," + UPDATED_CONTRATACAO,
            "contratacao.in=" + UPDATED_CONTRATACAO
        );
    }

    @Test
    @Transactional
    void getAllCidadesByContratacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCidade = cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where contratacao is not null
        defaultCidadeFiltering("contratacao.specified=true", "contratacao.specified=false");
    }

    @Test
    @Transactional
    void getAllCidadesByAberturaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCidade = cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where abertura equals to
        defaultCidadeFiltering("abertura.equals=" + DEFAULT_ABERTURA, "abertura.equals=" + UPDATED_ABERTURA);
    }

    @Test
    @Transactional
    void getAllCidadesByAberturaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCidade = cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where abertura in
        defaultCidadeFiltering("abertura.in=" + DEFAULT_ABERTURA + "," + UPDATED_ABERTURA, "abertura.in=" + UPDATED_ABERTURA);
    }

    @Test
    @Transactional
    void getAllCidadesByAberturaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCidade = cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where abertura is not null
        defaultCidadeFiltering("abertura.specified=true", "abertura.specified=false");
    }

    @Test
    @Transactional
    void getAllCidadesByEstadoIsEqualToSomething() throws Exception {
        Estado estado;
        if (TestUtil.findAll(em, Estado.class).isEmpty()) {
            cidadeRepository.saveAndFlush(cidade);
            estado = EstadoResourceIT.createEntity(em);
        } else {
            estado = TestUtil.findAll(em, Estado.class).get(0);
        }
        em.persist(estado);
        em.flush();
        cidade.setEstado(estado);
        cidadeRepository.saveAndFlush(cidade);
        Long estadoId = estado.getId();
        // Get all the cidadeList where estado equals to estadoId
        defaultCidadeShouldBeFound("estadoId.equals=" + estadoId);

        // Get all the cidadeList where estado equals to (estadoId + 1)
        defaultCidadeShouldNotBeFound("estadoId.equals=" + (estadoId + 1));
    }

    private void defaultCidadeFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultCidadeShouldBeFound(shouldBeFound);
        defaultCidadeShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCidadeShouldBeFound(String filter) throws Exception {
        restCidadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].contratacao").value(hasItem(DEFAULT_CONTRATACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].abertura").value(hasItem(DEFAULT_ABERTURA.booleanValue())));

        // Check, that the count call also returns 1
        restCidadeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCidadeShouldNotBeFound(String filter) throws Exception {
        restCidadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCidadeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCidade() throws Exception {
        // Get the cidade
        restCidadeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCidade() throws Exception {
        // Initialize the database
        insertedCidade = cidadeRepository.saveAndFlush(cidade);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cidade
        Cidade updatedCidade = cidadeRepository.findById(cidade.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCidade are not directly saved in db
        em.detach(updatedCidade);
        updatedCidade.nome(UPDATED_NOME).contratacao(UPDATED_CONTRATACAO).abertura(UPDATED_ABERTURA);

        restCidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCidade.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCidade))
            )
            .andExpect(status().isOk());

        // Validate the Cidade in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCidadeToMatchAllProperties(updatedCidade);
    }

    @Test
    @Transactional
    void putNonExistingCidade() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cidade.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCidadeMockMvc
            .perform(put(ENTITY_API_URL_ID, cidade.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cidade)))
            .andExpect(status().isBadRequest());

        // Validate the Cidade in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCidade() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cidade.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cidade))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cidade in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCidade() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cidade.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCidadeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cidade)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cidade in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCidadeWithPatch() throws Exception {
        // Initialize the database
        insertedCidade = cidadeRepository.saveAndFlush(cidade);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cidade using partial update
        Cidade partialUpdatedCidade = new Cidade();
        partialUpdatedCidade.setId(cidade.getId());

        partialUpdatedCidade.nome(UPDATED_NOME);

        restCidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCidade.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCidade))
            )
            .andExpect(status().isOk());

        // Validate the Cidade in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCidadeUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCidade, cidade), getPersistedCidade(cidade));
    }

    @Test
    @Transactional
    void fullUpdateCidadeWithPatch() throws Exception {
        // Initialize the database
        insertedCidade = cidadeRepository.saveAndFlush(cidade);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cidade using partial update
        Cidade partialUpdatedCidade = new Cidade();
        partialUpdatedCidade.setId(cidade.getId());

        partialUpdatedCidade.nome(UPDATED_NOME).contratacao(UPDATED_CONTRATACAO).abertura(UPDATED_ABERTURA);

        restCidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCidade.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCidade))
            )
            .andExpect(status().isOk());

        // Validate the Cidade in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCidadeUpdatableFieldsEquals(partialUpdatedCidade, getPersistedCidade(partialUpdatedCidade));
    }

    @Test
    @Transactional
    void patchNonExistingCidade() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cidade.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cidade.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cidade))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cidade in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCidade() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cidade.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cidade))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cidade in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCidade() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cidade.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCidadeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cidade)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cidade in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCidade() throws Exception {
        // Initialize the database
        insertedCidade = cidadeRepository.saveAndFlush(cidade);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cidade
        restCidadeMockMvc
            .perform(delete(ENTITY_API_URL_ID, cidade.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cidadeRepository.count();
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

    protected Cidade getPersistedCidade(Cidade cidade) {
        return cidadeRepository.findById(cidade.getId()).orElseThrow();
    }

    protected void assertPersistedCidadeToMatchAllProperties(Cidade expectedCidade) {
        assertCidadeAllPropertiesEquals(expectedCidade, getPersistedCidade(expectedCidade));
    }

    protected void assertPersistedCidadeToMatchUpdatableProperties(Cidade expectedCidade) {
        assertCidadeAllUpdatablePropertiesEquals(expectedCidade, getPersistedCidade(expectedCidade));
    }
}
