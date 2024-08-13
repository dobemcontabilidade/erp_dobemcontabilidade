package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.FuncionalidadeGrupoAcessoEmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Funcionalidade;
import com.dobemcontabilidade.domain.FuncionalidadeGrupoAcessoEmpresa;
import com.dobemcontabilidade.domain.GrupoAcessoEmpresa;
import com.dobemcontabilidade.domain.Permisao;
import com.dobemcontabilidade.repository.FuncionalidadeGrupoAcessoEmpresaRepository;
import com.dobemcontabilidade.service.FuncionalidadeGrupoAcessoEmpresaService;
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
 * Integration tests for the {@link FuncionalidadeGrupoAcessoEmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FuncionalidadeGrupoAcessoEmpresaResourceIT {

    private static final String DEFAULT_ATIVA = "AAAAAAAAAA";
    private static final String UPDATED_ATIVA = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_EXPIRACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_EXPIRACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ILIMITADO = false;
    private static final Boolean UPDATED_ILIMITADO = true;

    private static final Boolean DEFAULT_DESABILITAR = false;
    private static final Boolean UPDATED_DESABILITAR = true;

    private static final String ENTITY_API_URL = "/api/funcionalidade-grupo-acesso-empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FuncionalidadeGrupoAcessoEmpresaRepository funcionalidadeGrupoAcessoEmpresaRepository;

    @Mock
    private FuncionalidadeGrupoAcessoEmpresaRepository funcionalidadeGrupoAcessoEmpresaRepositoryMock;

    @Mock
    private FuncionalidadeGrupoAcessoEmpresaService funcionalidadeGrupoAcessoEmpresaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFuncionalidadeGrupoAcessoEmpresaMockMvc;

    private FuncionalidadeGrupoAcessoEmpresa funcionalidadeGrupoAcessoEmpresa;

    private FuncionalidadeGrupoAcessoEmpresa insertedFuncionalidadeGrupoAcessoEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuncionalidadeGrupoAcessoEmpresa createEntity(EntityManager em) {
        FuncionalidadeGrupoAcessoEmpresa funcionalidadeGrupoAcessoEmpresa = new FuncionalidadeGrupoAcessoEmpresa()
            .ativa(DEFAULT_ATIVA)
            .dataExpiracao(DEFAULT_DATA_EXPIRACAO)
            .ilimitado(DEFAULT_ILIMITADO)
            .desabilitar(DEFAULT_DESABILITAR);
        // Add required entity
        Funcionalidade funcionalidade;
        if (TestUtil.findAll(em, Funcionalidade.class).isEmpty()) {
            funcionalidade = FuncionalidadeResourceIT.createEntity(em);
            em.persist(funcionalidade);
            em.flush();
        } else {
            funcionalidade = TestUtil.findAll(em, Funcionalidade.class).get(0);
        }
        funcionalidadeGrupoAcessoEmpresa.setFuncionalidade(funcionalidade);
        // Add required entity
        GrupoAcessoEmpresa grupoAcessoEmpresa;
        if (TestUtil.findAll(em, GrupoAcessoEmpresa.class).isEmpty()) {
            grupoAcessoEmpresa = GrupoAcessoEmpresaResourceIT.createEntity(em);
            em.persist(grupoAcessoEmpresa);
            em.flush();
        } else {
            grupoAcessoEmpresa = TestUtil.findAll(em, GrupoAcessoEmpresa.class).get(0);
        }
        funcionalidadeGrupoAcessoEmpresa.setGrupoAcessoEmpresa(grupoAcessoEmpresa);
        // Add required entity
        Permisao permisao;
        if (TestUtil.findAll(em, Permisao.class).isEmpty()) {
            permisao = PermisaoResourceIT.createEntity(em);
            em.persist(permisao);
            em.flush();
        } else {
            permisao = TestUtil.findAll(em, Permisao.class).get(0);
        }
        funcionalidadeGrupoAcessoEmpresa.setPermisao(permisao);
        return funcionalidadeGrupoAcessoEmpresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuncionalidadeGrupoAcessoEmpresa createUpdatedEntity(EntityManager em) {
        FuncionalidadeGrupoAcessoEmpresa funcionalidadeGrupoAcessoEmpresa = new FuncionalidadeGrupoAcessoEmpresa()
            .ativa(UPDATED_ATIVA)
            .dataExpiracao(UPDATED_DATA_EXPIRACAO)
            .ilimitado(UPDATED_ILIMITADO)
            .desabilitar(UPDATED_DESABILITAR);
        // Add required entity
        Funcionalidade funcionalidade;
        if (TestUtil.findAll(em, Funcionalidade.class).isEmpty()) {
            funcionalidade = FuncionalidadeResourceIT.createUpdatedEntity(em);
            em.persist(funcionalidade);
            em.flush();
        } else {
            funcionalidade = TestUtil.findAll(em, Funcionalidade.class).get(0);
        }
        funcionalidadeGrupoAcessoEmpresa.setFuncionalidade(funcionalidade);
        // Add required entity
        GrupoAcessoEmpresa grupoAcessoEmpresa;
        if (TestUtil.findAll(em, GrupoAcessoEmpresa.class).isEmpty()) {
            grupoAcessoEmpresa = GrupoAcessoEmpresaResourceIT.createUpdatedEntity(em);
            em.persist(grupoAcessoEmpresa);
            em.flush();
        } else {
            grupoAcessoEmpresa = TestUtil.findAll(em, GrupoAcessoEmpresa.class).get(0);
        }
        funcionalidadeGrupoAcessoEmpresa.setGrupoAcessoEmpresa(grupoAcessoEmpresa);
        // Add required entity
        Permisao permisao;
        if (TestUtil.findAll(em, Permisao.class).isEmpty()) {
            permisao = PermisaoResourceIT.createUpdatedEntity(em);
            em.persist(permisao);
            em.flush();
        } else {
            permisao = TestUtil.findAll(em, Permisao.class).get(0);
        }
        funcionalidadeGrupoAcessoEmpresa.setPermisao(permisao);
        return funcionalidadeGrupoAcessoEmpresa;
    }

    @BeforeEach
    public void initTest() {
        funcionalidadeGrupoAcessoEmpresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedFuncionalidadeGrupoAcessoEmpresa != null) {
            funcionalidadeGrupoAcessoEmpresaRepository.delete(insertedFuncionalidadeGrupoAcessoEmpresa);
            insertedFuncionalidadeGrupoAcessoEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createFuncionalidadeGrupoAcessoEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FuncionalidadeGrupoAcessoEmpresa
        var returnedFuncionalidadeGrupoAcessoEmpresa = om.readValue(
            restFuncionalidadeGrupoAcessoEmpresaMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(funcionalidadeGrupoAcessoEmpresa))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FuncionalidadeGrupoAcessoEmpresa.class
        );

        // Validate the FuncionalidadeGrupoAcessoEmpresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFuncionalidadeGrupoAcessoEmpresaUpdatableFieldsEquals(
            returnedFuncionalidadeGrupoAcessoEmpresa,
            getPersistedFuncionalidadeGrupoAcessoEmpresa(returnedFuncionalidadeGrupoAcessoEmpresa)
        );

        insertedFuncionalidadeGrupoAcessoEmpresa = returnedFuncionalidadeGrupoAcessoEmpresa;
    }

    @Test
    @Transactional
    void createFuncionalidadeGrupoAcessoEmpresaWithExistingId() throws Exception {
        // Create the FuncionalidadeGrupoAcessoEmpresa with an existing ID
        funcionalidadeGrupoAcessoEmpresa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuncionalidadeGrupoAcessoEmpresaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(funcionalidadeGrupoAcessoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuncionalidadeGrupoAcessoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFuncionalidadeGrupoAcessoEmpresas() throws Exception {
        // Initialize the database
        insertedFuncionalidadeGrupoAcessoEmpresa = funcionalidadeGrupoAcessoEmpresaRepository.saveAndFlush(
            funcionalidadeGrupoAcessoEmpresa
        );

        // Get all the funcionalidadeGrupoAcessoEmpresaList
        restFuncionalidadeGrupoAcessoEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funcionalidadeGrupoAcessoEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].ativa").value(hasItem(DEFAULT_ATIVA)))
            .andExpect(jsonPath("$.[*].dataExpiracao").value(hasItem(DEFAULT_DATA_EXPIRACAO.toString())))
            .andExpect(jsonPath("$.[*].ilimitado").value(hasItem(DEFAULT_ILIMITADO.booleanValue())))
            .andExpect(jsonPath("$.[*].desabilitar").value(hasItem(DEFAULT_DESABILITAR.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFuncionalidadeGrupoAcessoEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(funcionalidadeGrupoAcessoEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFuncionalidadeGrupoAcessoEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(funcionalidadeGrupoAcessoEmpresaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFuncionalidadeGrupoAcessoEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(funcionalidadeGrupoAcessoEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFuncionalidadeGrupoAcessoEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(funcionalidadeGrupoAcessoEmpresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getFuncionalidadeGrupoAcessoEmpresa() throws Exception {
        // Initialize the database
        insertedFuncionalidadeGrupoAcessoEmpresa = funcionalidadeGrupoAcessoEmpresaRepository.saveAndFlush(
            funcionalidadeGrupoAcessoEmpresa
        );

        // Get the funcionalidadeGrupoAcessoEmpresa
        restFuncionalidadeGrupoAcessoEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, funcionalidadeGrupoAcessoEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(funcionalidadeGrupoAcessoEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.ativa").value(DEFAULT_ATIVA))
            .andExpect(jsonPath("$.dataExpiracao").value(DEFAULT_DATA_EXPIRACAO.toString()))
            .andExpect(jsonPath("$.ilimitado").value(DEFAULT_ILIMITADO.booleanValue()))
            .andExpect(jsonPath("$.desabilitar").value(DEFAULT_DESABILITAR.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingFuncionalidadeGrupoAcessoEmpresa() throws Exception {
        // Get the funcionalidadeGrupoAcessoEmpresa
        restFuncionalidadeGrupoAcessoEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFuncionalidadeGrupoAcessoEmpresa() throws Exception {
        // Initialize the database
        insertedFuncionalidadeGrupoAcessoEmpresa = funcionalidadeGrupoAcessoEmpresaRepository.saveAndFlush(
            funcionalidadeGrupoAcessoEmpresa
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the funcionalidadeGrupoAcessoEmpresa
        FuncionalidadeGrupoAcessoEmpresa updatedFuncionalidadeGrupoAcessoEmpresa = funcionalidadeGrupoAcessoEmpresaRepository
            .findById(funcionalidadeGrupoAcessoEmpresa.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedFuncionalidadeGrupoAcessoEmpresa are not directly saved in db
        em.detach(updatedFuncionalidadeGrupoAcessoEmpresa);
        updatedFuncionalidadeGrupoAcessoEmpresa
            .ativa(UPDATED_ATIVA)
            .dataExpiracao(UPDATED_DATA_EXPIRACAO)
            .ilimitado(UPDATED_ILIMITADO)
            .desabilitar(UPDATED_DESABILITAR);

        restFuncionalidadeGrupoAcessoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFuncionalidadeGrupoAcessoEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFuncionalidadeGrupoAcessoEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the FuncionalidadeGrupoAcessoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFuncionalidadeGrupoAcessoEmpresaToMatchAllProperties(updatedFuncionalidadeGrupoAcessoEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingFuncionalidadeGrupoAcessoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        funcionalidadeGrupoAcessoEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncionalidadeGrupoAcessoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, funcionalidadeGrupoAcessoEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(funcionalidadeGrupoAcessoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuncionalidadeGrupoAcessoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFuncionalidadeGrupoAcessoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        funcionalidadeGrupoAcessoEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionalidadeGrupoAcessoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(funcionalidadeGrupoAcessoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuncionalidadeGrupoAcessoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFuncionalidadeGrupoAcessoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        funcionalidadeGrupoAcessoEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionalidadeGrupoAcessoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(funcionalidadeGrupoAcessoEmpresa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuncionalidadeGrupoAcessoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFuncionalidadeGrupoAcessoEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedFuncionalidadeGrupoAcessoEmpresa = funcionalidadeGrupoAcessoEmpresaRepository.saveAndFlush(
            funcionalidadeGrupoAcessoEmpresa
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the funcionalidadeGrupoAcessoEmpresa using partial update
        FuncionalidadeGrupoAcessoEmpresa partialUpdatedFuncionalidadeGrupoAcessoEmpresa = new FuncionalidadeGrupoAcessoEmpresa();
        partialUpdatedFuncionalidadeGrupoAcessoEmpresa.setId(funcionalidadeGrupoAcessoEmpresa.getId());

        partialUpdatedFuncionalidadeGrupoAcessoEmpresa.ilimitado(UPDATED_ILIMITADO);

        restFuncionalidadeGrupoAcessoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuncionalidadeGrupoAcessoEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFuncionalidadeGrupoAcessoEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the FuncionalidadeGrupoAcessoEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFuncionalidadeGrupoAcessoEmpresaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFuncionalidadeGrupoAcessoEmpresa, funcionalidadeGrupoAcessoEmpresa),
            getPersistedFuncionalidadeGrupoAcessoEmpresa(funcionalidadeGrupoAcessoEmpresa)
        );
    }

    @Test
    @Transactional
    void fullUpdateFuncionalidadeGrupoAcessoEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedFuncionalidadeGrupoAcessoEmpresa = funcionalidadeGrupoAcessoEmpresaRepository.saveAndFlush(
            funcionalidadeGrupoAcessoEmpresa
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the funcionalidadeGrupoAcessoEmpresa using partial update
        FuncionalidadeGrupoAcessoEmpresa partialUpdatedFuncionalidadeGrupoAcessoEmpresa = new FuncionalidadeGrupoAcessoEmpresa();
        partialUpdatedFuncionalidadeGrupoAcessoEmpresa.setId(funcionalidadeGrupoAcessoEmpresa.getId());

        partialUpdatedFuncionalidadeGrupoAcessoEmpresa
            .ativa(UPDATED_ATIVA)
            .dataExpiracao(UPDATED_DATA_EXPIRACAO)
            .ilimitado(UPDATED_ILIMITADO)
            .desabilitar(UPDATED_DESABILITAR);

        restFuncionalidadeGrupoAcessoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuncionalidadeGrupoAcessoEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFuncionalidadeGrupoAcessoEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the FuncionalidadeGrupoAcessoEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFuncionalidadeGrupoAcessoEmpresaUpdatableFieldsEquals(
            partialUpdatedFuncionalidadeGrupoAcessoEmpresa,
            getPersistedFuncionalidadeGrupoAcessoEmpresa(partialUpdatedFuncionalidadeGrupoAcessoEmpresa)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFuncionalidadeGrupoAcessoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        funcionalidadeGrupoAcessoEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncionalidadeGrupoAcessoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, funcionalidadeGrupoAcessoEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(funcionalidadeGrupoAcessoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuncionalidadeGrupoAcessoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFuncionalidadeGrupoAcessoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        funcionalidadeGrupoAcessoEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionalidadeGrupoAcessoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(funcionalidadeGrupoAcessoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuncionalidadeGrupoAcessoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFuncionalidadeGrupoAcessoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        funcionalidadeGrupoAcessoEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionalidadeGrupoAcessoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(funcionalidadeGrupoAcessoEmpresa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuncionalidadeGrupoAcessoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFuncionalidadeGrupoAcessoEmpresa() throws Exception {
        // Initialize the database
        insertedFuncionalidadeGrupoAcessoEmpresa = funcionalidadeGrupoAcessoEmpresaRepository.saveAndFlush(
            funcionalidadeGrupoAcessoEmpresa
        );

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the funcionalidadeGrupoAcessoEmpresa
        restFuncionalidadeGrupoAcessoEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, funcionalidadeGrupoAcessoEmpresa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return funcionalidadeGrupoAcessoEmpresaRepository.count();
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

    protected FuncionalidadeGrupoAcessoEmpresa getPersistedFuncionalidadeGrupoAcessoEmpresa(
        FuncionalidadeGrupoAcessoEmpresa funcionalidadeGrupoAcessoEmpresa
    ) {
        return funcionalidadeGrupoAcessoEmpresaRepository.findById(funcionalidadeGrupoAcessoEmpresa.getId()).orElseThrow();
    }

    protected void assertPersistedFuncionalidadeGrupoAcessoEmpresaToMatchAllProperties(
        FuncionalidadeGrupoAcessoEmpresa expectedFuncionalidadeGrupoAcessoEmpresa
    ) {
        assertFuncionalidadeGrupoAcessoEmpresaAllPropertiesEquals(
            expectedFuncionalidadeGrupoAcessoEmpresa,
            getPersistedFuncionalidadeGrupoAcessoEmpresa(expectedFuncionalidadeGrupoAcessoEmpresa)
        );
    }

    protected void assertPersistedFuncionalidadeGrupoAcessoEmpresaToMatchUpdatableProperties(
        FuncionalidadeGrupoAcessoEmpresa expectedFuncionalidadeGrupoAcessoEmpresa
    ) {
        assertFuncionalidadeGrupoAcessoEmpresaAllUpdatablePropertiesEquals(
            expectedFuncionalidadeGrupoAcessoEmpresa,
            getPersistedFuncionalidadeGrupoAcessoEmpresa(expectedFuncionalidadeGrupoAcessoEmpresa)
        );
    }
}
