package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.FuncionalidadeGrupoAcessoPadraoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Funcionalidade;
import com.dobemcontabilidade.domain.FuncionalidadeGrupoAcessoPadrao;
import com.dobemcontabilidade.domain.GrupoAcessoPadrao;
import com.dobemcontabilidade.domain.Permisao;
import com.dobemcontabilidade.repository.FuncionalidadeGrupoAcessoPadraoRepository;
import com.dobemcontabilidade.service.FuncionalidadeGrupoAcessoPadraoService;
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
 * Integration tests for the {@link FuncionalidadeGrupoAcessoPadraoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FuncionalidadeGrupoAcessoPadraoResourceIT {

    private static final Boolean DEFAULT_AUTORIZADO = false;
    private static final Boolean UPDATED_AUTORIZADO = true;

    private static final Instant DEFAULT_DATA_EXPIRACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_EXPIRACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_ATRIBUICAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ATRIBUICAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/funcionalidade-grupo-acesso-padraos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FuncionalidadeGrupoAcessoPadraoRepository funcionalidadeGrupoAcessoPadraoRepository;

    @Mock
    private FuncionalidadeGrupoAcessoPadraoRepository funcionalidadeGrupoAcessoPadraoRepositoryMock;

    @Mock
    private FuncionalidadeGrupoAcessoPadraoService funcionalidadeGrupoAcessoPadraoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFuncionalidadeGrupoAcessoPadraoMockMvc;

    private FuncionalidadeGrupoAcessoPadrao funcionalidadeGrupoAcessoPadrao;

    private FuncionalidadeGrupoAcessoPadrao insertedFuncionalidadeGrupoAcessoPadrao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuncionalidadeGrupoAcessoPadrao createEntity(EntityManager em) {
        FuncionalidadeGrupoAcessoPadrao funcionalidadeGrupoAcessoPadrao = new FuncionalidadeGrupoAcessoPadrao()
            .autorizado(DEFAULT_AUTORIZADO)
            .dataExpiracao(DEFAULT_DATA_EXPIRACAO)
            .dataAtribuicao(DEFAULT_DATA_ATRIBUICAO);
        // Add required entity
        Funcionalidade funcionalidade;
        if (TestUtil.findAll(em, Funcionalidade.class).isEmpty()) {
            funcionalidade = FuncionalidadeResourceIT.createEntity(em);
            em.persist(funcionalidade);
            em.flush();
        } else {
            funcionalidade = TestUtil.findAll(em, Funcionalidade.class).get(0);
        }
        funcionalidadeGrupoAcessoPadrao.setFuncionalidade(funcionalidade);
        // Add required entity
        GrupoAcessoPadrao grupoAcessoPadrao;
        if (TestUtil.findAll(em, GrupoAcessoPadrao.class).isEmpty()) {
            grupoAcessoPadrao = GrupoAcessoPadraoResourceIT.createEntity(em);
            em.persist(grupoAcessoPadrao);
            em.flush();
        } else {
            grupoAcessoPadrao = TestUtil.findAll(em, GrupoAcessoPadrao.class).get(0);
        }
        funcionalidadeGrupoAcessoPadrao.setGrupoAcessoPadrao(grupoAcessoPadrao);
        // Add required entity
        Permisao permisao;
        if (TestUtil.findAll(em, Permisao.class).isEmpty()) {
            permisao = PermisaoResourceIT.createEntity(em);
            em.persist(permisao);
            em.flush();
        } else {
            permisao = TestUtil.findAll(em, Permisao.class).get(0);
        }
        funcionalidadeGrupoAcessoPadrao.setPermisao(permisao);
        return funcionalidadeGrupoAcessoPadrao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuncionalidadeGrupoAcessoPadrao createUpdatedEntity(EntityManager em) {
        FuncionalidadeGrupoAcessoPadrao funcionalidadeGrupoAcessoPadrao = new FuncionalidadeGrupoAcessoPadrao()
            .autorizado(UPDATED_AUTORIZADO)
            .dataExpiracao(UPDATED_DATA_EXPIRACAO)
            .dataAtribuicao(UPDATED_DATA_ATRIBUICAO);
        // Add required entity
        Funcionalidade funcionalidade;
        if (TestUtil.findAll(em, Funcionalidade.class).isEmpty()) {
            funcionalidade = FuncionalidadeResourceIT.createUpdatedEntity(em);
            em.persist(funcionalidade);
            em.flush();
        } else {
            funcionalidade = TestUtil.findAll(em, Funcionalidade.class).get(0);
        }
        funcionalidadeGrupoAcessoPadrao.setFuncionalidade(funcionalidade);
        // Add required entity
        GrupoAcessoPadrao grupoAcessoPadrao;
        if (TestUtil.findAll(em, GrupoAcessoPadrao.class).isEmpty()) {
            grupoAcessoPadrao = GrupoAcessoPadraoResourceIT.createUpdatedEntity(em);
            em.persist(grupoAcessoPadrao);
            em.flush();
        } else {
            grupoAcessoPadrao = TestUtil.findAll(em, GrupoAcessoPadrao.class).get(0);
        }
        funcionalidadeGrupoAcessoPadrao.setGrupoAcessoPadrao(grupoAcessoPadrao);
        // Add required entity
        Permisao permisao;
        if (TestUtil.findAll(em, Permisao.class).isEmpty()) {
            permisao = PermisaoResourceIT.createUpdatedEntity(em);
            em.persist(permisao);
            em.flush();
        } else {
            permisao = TestUtil.findAll(em, Permisao.class).get(0);
        }
        funcionalidadeGrupoAcessoPadrao.setPermisao(permisao);
        return funcionalidadeGrupoAcessoPadrao;
    }

    @BeforeEach
    public void initTest() {
        funcionalidadeGrupoAcessoPadrao = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedFuncionalidadeGrupoAcessoPadrao != null) {
            funcionalidadeGrupoAcessoPadraoRepository.delete(insertedFuncionalidadeGrupoAcessoPadrao);
            insertedFuncionalidadeGrupoAcessoPadrao = null;
        }
    }

    @Test
    @Transactional
    void createFuncionalidadeGrupoAcessoPadrao() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FuncionalidadeGrupoAcessoPadrao
        var returnedFuncionalidadeGrupoAcessoPadrao = om.readValue(
            restFuncionalidadeGrupoAcessoPadraoMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(funcionalidadeGrupoAcessoPadrao))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FuncionalidadeGrupoAcessoPadrao.class
        );

        // Validate the FuncionalidadeGrupoAcessoPadrao in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFuncionalidadeGrupoAcessoPadraoUpdatableFieldsEquals(
            returnedFuncionalidadeGrupoAcessoPadrao,
            getPersistedFuncionalidadeGrupoAcessoPadrao(returnedFuncionalidadeGrupoAcessoPadrao)
        );

        insertedFuncionalidadeGrupoAcessoPadrao = returnedFuncionalidadeGrupoAcessoPadrao;
    }

    @Test
    @Transactional
    void createFuncionalidadeGrupoAcessoPadraoWithExistingId() throws Exception {
        // Create the FuncionalidadeGrupoAcessoPadrao with an existing ID
        funcionalidadeGrupoAcessoPadrao.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuncionalidadeGrupoAcessoPadraoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(funcionalidadeGrupoAcessoPadrao))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuncionalidadeGrupoAcessoPadrao in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFuncionalidadeGrupoAcessoPadraos() throws Exception {
        // Initialize the database
        insertedFuncionalidadeGrupoAcessoPadrao = funcionalidadeGrupoAcessoPadraoRepository.saveAndFlush(funcionalidadeGrupoAcessoPadrao);

        // Get all the funcionalidadeGrupoAcessoPadraoList
        restFuncionalidadeGrupoAcessoPadraoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funcionalidadeGrupoAcessoPadrao.getId().intValue())))
            .andExpect(jsonPath("$.[*].autorizado").value(hasItem(DEFAULT_AUTORIZADO.booleanValue())))
            .andExpect(jsonPath("$.[*].dataExpiracao").value(hasItem(DEFAULT_DATA_EXPIRACAO.toString())))
            .andExpect(jsonPath("$.[*].dataAtribuicao").value(hasItem(DEFAULT_DATA_ATRIBUICAO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFuncionalidadeGrupoAcessoPadraosWithEagerRelationshipsIsEnabled() throws Exception {
        when(funcionalidadeGrupoAcessoPadraoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFuncionalidadeGrupoAcessoPadraoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(funcionalidadeGrupoAcessoPadraoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFuncionalidadeGrupoAcessoPadraosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(funcionalidadeGrupoAcessoPadraoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFuncionalidadeGrupoAcessoPadraoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(funcionalidadeGrupoAcessoPadraoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getFuncionalidadeGrupoAcessoPadrao() throws Exception {
        // Initialize the database
        insertedFuncionalidadeGrupoAcessoPadrao = funcionalidadeGrupoAcessoPadraoRepository.saveAndFlush(funcionalidadeGrupoAcessoPadrao);

        // Get the funcionalidadeGrupoAcessoPadrao
        restFuncionalidadeGrupoAcessoPadraoMockMvc
            .perform(get(ENTITY_API_URL_ID, funcionalidadeGrupoAcessoPadrao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(funcionalidadeGrupoAcessoPadrao.getId().intValue()))
            .andExpect(jsonPath("$.autorizado").value(DEFAULT_AUTORIZADO.booleanValue()))
            .andExpect(jsonPath("$.dataExpiracao").value(DEFAULT_DATA_EXPIRACAO.toString()))
            .andExpect(jsonPath("$.dataAtribuicao").value(DEFAULT_DATA_ATRIBUICAO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFuncionalidadeGrupoAcessoPadrao() throws Exception {
        // Get the funcionalidadeGrupoAcessoPadrao
        restFuncionalidadeGrupoAcessoPadraoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFuncionalidadeGrupoAcessoPadrao() throws Exception {
        // Initialize the database
        insertedFuncionalidadeGrupoAcessoPadrao = funcionalidadeGrupoAcessoPadraoRepository.saveAndFlush(funcionalidadeGrupoAcessoPadrao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the funcionalidadeGrupoAcessoPadrao
        FuncionalidadeGrupoAcessoPadrao updatedFuncionalidadeGrupoAcessoPadrao = funcionalidadeGrupoAcessoPadraoRepository
            .findById(funcionalidadeGrupoAcessoPadrao.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedFuncionalidadeGrupoAcessoPadrao are not directly saved in db
        em.detach(updatedFuncionalidadeGrupoAcessoPadrao);
        updatedFuncionalidadeGrupoAcessoPadrao
            .autorizado(UPDATED_AUTORIZADO)
            .dataExpiracao(UPDATED_DATA_EXPIRACAO)
            .dataAtribuicao(UPDATED_DATA_ATRIBUICAO);

        restFuncionalidadeGrupoAcessoPadraoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFuncionalidadeGrupoAcessoPadrao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFuncionalidadeGrupoAcessoPadrao))
            )
            .andExpect(status().isOk());

        // Validate the FuncionalidadeGrupoAcessoPadrao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFuncionalidadeGrupoAcessoPadraoToMatchAllProperties(updatedFuncionalidadeGrupoAcessoPadrao);
    }

    @Test
    @Transactional
    void putNonExistingFuncionalidadeGrupoAcessoPadrao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        funcionalidadeGrupoAcessoPadrao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncionalidadeGrupoAcessoPadraoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, funcionalidadeGrupoAcessoPadrao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(funcionalidadeGrupoAcessoPadrao))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuncionalidadeGrupoAcessoPadrao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFuncionalidadeGrupoAcessoPadrao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        funcionalidadeGrupoAcessoPadrao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionalidadeGrupoAcessoPadraoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(funcionalidadeGrupoAcessoPadrao))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuncionalidadeGrupoAcessoPadrao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFuncionalidadeGrupoAcessoPadrao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        funcionalidadeGrupoAcessoPadrao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionalidadeGrupoAcessoPadraoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(funcionalidadeGrupoAcessoPadrao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuncionalidadeGrupoAcessoPadrao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFuncionalidadeGrupoAcessoPadraoWithPatch() throws Exception {
        // Initialize the database
        insertedFuncionalidadeGrupoAcessoPadrao = funcionalidadeGrupoAcessoPadraoRepository.saveAndFlush(funcionalidadeGrupoAcessoPadrao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the funcionalidadeGrupoAcessoPadrao using partial update
        FuncionalidadeGrupoAcessoPadrao partialUpdatedFuncionalidadeGrupoAcessoPadrao = new FuncionalidadeGrupoAcessoPadrao();
        partialUpdatedFuncionalidadeGrupoAcessoPadrao.setId(funcionalidadeGrupoAcessoPadrao.getId());

        partialUpdatedFuncionalidadeGrupoAcessoPadrao.dataExpiracao(UPDATED_DATA_EXPIRACAO).dataAtribuicao(UPDATED_DATA_ATRIBUICAO);

        restFuncionalidadeGrupoAcessoPadraoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuncionalidadeGrupoAcessoPadrao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFuncionalidadeGrupoAcessoPadrao))
            )
            .andExpect(status().isOk());

        // Validate the FuncionalidadeGrupoAcessoPadrao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFuncionalidadeGrupoAcessoPadraoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFuncionalidadeGrupoAcessoPadrao, funcionalidadeGrupoAcessoPadrao),
            getPersistedFuncionalidadeGrupoAcessoPadrao(funcionalidadeGrupoAcessoPadrao)
        );
    }

    @Test
    @Transactional
    void fullUpdateFuncionalidadeGrupoAcessoPadraoWithPatch() throws Exception {
        // Initialize the database
        insertedFuncionalidadeGrupoAcessoPadrao = funcionalidadeGrupoAcessoPadraoRepository.saveAndFlush(funcionalidadeGrupoAcessoPadrao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the funcionalidadeGrupoAcessoPadrao using partial update
        FuncionalidadeGrupoAcessoPadrao partialUpdatedFuncionalidadeGrupoAcessoPadrao = new FuncionalidadeGrupoAcessoPadrao();
        partialUpdatedFuncionalidadeGrupoAcessoPadrao.setId(funcionalidadeGrupoAcessoPadrao.getId());

        partialUpdatedFuncionalidadeGrupoAcessoPadrao
            .autorizado(UPDATED_AUTORIZADO)
            .dataExpiracao(UPDATED_DATA_EXPIRACAO)
            .dataAtribuicao(UPDATED_DATA_ATRIBUICAO);

        restFuncionalidadeGrupoAcessoPadraoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuncionalidadeGrupoAcessoPadrao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFuncionalidadeGrupoAcessoPadrao))
            )
            .andExpect(status().isOk());

        // Validate the FuncionalidadeGrupoAcessoPadrao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFuncionalidadeGrupoAcessoPadraoUpdatableFieldsEquals(
            partialUpdatedFuncionalidadeGrupoAcessoPadrao,
            getPersistedFuncionalidadeGrupoAcessoPadrao(partialUpdatedFuncionalidadeGrupoAcessoPadrao)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFuncionalidadeGrupoAcessoPadrao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        funcionalidadeGrupoAcessoPadrao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncionalidadeGrupoAcessoPadraoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, funcionalidadeGrupoAcessoPadrao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(funcionalidadeGrupoAcessoPadrao))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuncionalidadeGrupoAcessoPadrao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFuncionalidadeGrupoAcessoPadrao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        funcionalidadeGrupoAcessoPadrao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionalidadeGrupoAcessoPadraoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(funcionalidadeGrupoAcessoPadrao))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuncionalidadeGrupoAcessoPadrao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFuncionalidadeGrupoAcessoPadrao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        funcionalidadeGrupoAcessoPadrao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionalidadeGrupoAcessoPadraoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(funcionalidadeGrupoAcessoPadrao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuncionalidadeGrupoAcessoPadrao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFuncionalidadeGrupoAcessoPadrao() throws Exception {
        // Initialize the database
        insertedFuncionalidadeGrupoAcessoPadrao = funcionalidadeGrupoAcessoPadraoRepository.saveAndFlush(funcionalidadeGrupoAcessoPadrao);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the funcionalidadeGrupoAcessoPadrao
        restFuncionalidadeGrupoAcessoPadraoMockMvc
            .perform(delete(ENTITY_API_URL_ID, funcionalidadeGrupoAcessoPadrao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return funcionalidadeGrupoAcessoPadraoRepository.count();
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

    protected FuncionalidadeGrupoAcessoPadrao getPersistedFuncionalidadeGrupoAcessoPadrao(
        FuncionalidadeGrupoAcessoPadrao funcionalidadeGrupoAcessoPadrao
    ) {
        return funcionalidadeGrupoAcessoPadraoRepository.findById(funcionalidadeGrupoAcessoPadrao.getId()).orElseThrow();
    }

    protected void assertPersistedFuncionalidadeGrupoAcessoPadraoToMatchAllProperties(
        FuncionalidadeGrupoAcessoPadrao expectedFuncionalidadeGrupoAcessoPadrao
    ) {
        assertFuncionalidadeGrupoAcessoPadraoAllPropertiesEquals(
            expectedFuncionalidadeGrupoAcessoPadrao,
            getPersistedFuncionalidadeGrupoAcessoPadrao(expectedFuncionalidadeGrupoAcessoPadrao)
        );
    }

    protected void assertPersistedFuncionalidadeGrupoAcessoPadraoToMatchUpdatableProperties(
        FuncionalidadeGrupoAcessoPadrao expectedFuncionalidadeGrupoAcessoPadrao
    ) {
        assertFuncionalidadeGrupoAcessoPadraoAllUpdatablePropertiesEquals(
            expectedFuncionalidadeGrupoAcessoPadrao,
            getPersistedFuncionalidadeGrupoAcessoPadrao(expectedFuncionalidadeGrupoAcessoPadrao)
        );
    }
}
