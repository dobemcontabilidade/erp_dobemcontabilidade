package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.FeedBackUsuarioParaContadorAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.CriterioAvaliacaoAtor;
import com.dobemcontabilidade.domain.FeedBackUsuarioParaContador;
import com.dobemcontabilidade.domain.OrdemServico;
import com.dobemcontabilidade.domain.UsuarioContador;
import com.dobemcontabilidade.domain.UsuarioEmpresa;
import com.dobemcontabilidade.repository.FeedBackUsuarioParaContadorRepository;
import com.dobemcontabilidade.service.FeedBackUsuarioParaContadorService;
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
 * Integration tests for the {@link FeedBackUsuarioParaContadorResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FeedBackUsuarioParaContadorResourceIT {

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    private static final Double DEFAULT_PONTUACAO = 1D;
    private static final Double UPDATED_PONTUACAO = 2D;

    private static final String ENTITY_API_URL = "/api/feed-back-usuario-para-contadors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FeedBackUsuarioParaContadorRepository feedBackUsuarioParaContadorRepository;

    @Mock
    private FeedBackUsuarioParaContadorRepository feedBackUsuarioParaContadorRepositoryMock;

    @Mock
    private FeedBackUsuarioParaContadorService feedBackUsuarioParaContadorServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeedBackUsuarioParaContadorMockMvc;

    private FeedBackUsuarioParaContador feedBackUsuarioParaContador;

    private FeedBackUsuarioParaContador insertedFeedBackUsuarioParaContador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeedBackUsuarioParaContador createEntity(EntityManager em) {
        FeedBackUsuarioParaContador feedBackUsuarioParaContador = new FeedBackUsuarioParaContador()
            .comentario(DEFAULT_COMENTARIO)
            .pontuacao(DEFAULT_PONTUACAO);
        // Add required entity
        UsuarioEmpresa usuarioEmpresa;
        if (TestUtil.findAll(em, UsuarioEmpresa.class).isEmpty()) {
            usuarioEmpresa = UsuarioEmpresaResourceIT.createEntity(em);
            em.persist(usuarioEmpresa);
            em.flush();
        } else {
            usuarioEmpresa = TestUtil.findAll(em, UsuarioEmpresa.class).get(0);
        }
        feedBackUsuarioParaContador.setUsuarioEmpresa(usuarioEmpresa);
        // Add required entity
        UsuarioContador usuarioContador;
        if (TestUtil.findAll(em, UsuarioContador.class).isEmpty()) {
            usuarioContador = UsuarioContadorResourceIT.createEntity(em);
            em.persist(usuarioContador);
            em.flush();
        } else {
            usuarioContador = TestUtil.findAll(em, UsuarioContador.class).get(0);
        }
        feedBackUsuarioParaContador.setUsuarioContador(usuarioContador);
        // Add required entity
        CriterioAvaliacaoAtor criterioAvaliacaoAtor;
        if (TestUtil.findAll(em, CriterioAvaliacaoAtor.class).isEmpty()) {
            criterioAvaliacaoAtor = CriterioAvaliacaoAtorResourceIT.createEntity(em);
            em.persist(criterioAvaliacaoAtor);
            em.flush();
        } else {
            criterioAvaliacaoAtor = TestUtil.findAll(em, CriterioAvaliacaoAtor.class).get(0);
        }
        feedBackUsuarioParaContador.setCriterioAvaliacaoAtor(criterioAvaliacaoAtor);
        // Add required entity
        OrdemServico ordemServico;
        if (TestUtil.findAll(em, OrdemServico.class).isEmpty()) {
            ordemServico = OrdemServicoResourceIT.createEntity(em);
            em.persist(ordemServico);
            em.flush();
        } else {
            ordemServico = TestUtil.findAll(em, OrdemServico.class).get(0);
        }
        feedBackUsuarioParaContador.setOrdemServico(ordemServico);
        return feedBackUsuarioParaContador;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeedBackUsuarioParaContador createUpdatedEntity(EntityManager em) {
        FeedBackUsuarioParaContador feedBackUsuarioParaContador = new FeedBackUsuarioParaContador()
            .comentario(UPDATED_COMENTARIO)
            .pontuacao(UPDATED_PONTUACAO);
        // Add required entity
        UsuarioEmpresa usuarioEmpresa;
        if (TestUtil.findAll(em, UsuarioEmpresa.class).isEmpty()) {
            usuarioEmpresa = UsuarioEmpresaResourceIT.createUpdatedEntity(em);
            em.persist(usuarioEmpresa);
            em.flush();
        } else {
            usuarioEmpresa = TestUtil.findAll(em, UsuarioEmpresa.class).get(0);
        }
        feedBackUsuarioParaContador.setUsuarioEmpresa(usuarioEmpresa);
        // Add required entity
        UsuarioContador usuarioContador;
        if (TestUtil.findAll(em, UsuarioContador.class).isEmpty()) {
            usuarioContador = UsuarioContadorResourceIT.createUpdatedEntity(em);
            em.persist(usuarioContador);
            em.flush();
        } else {
            usuarioContador = TestUtil.findAll(em, UsuarioContador.class).get(0);
        }
        feedBackUsuarioParaContador.setUsuarioContador(usuarioContador);
        // Add required entity
        CriterioAvaliacaoAtor criterioAvaliacaoAtor;
        if (TestUtil.findAll(em, CriterioAvaliacaoAtor.class).isEmpty()) {
            criterioAvaliacaoAtor = CriterioAvaliacaoAtorResourceIT.createUpdatedEntity(em);
            em.persist(criterioAvaliacaoAtor);
            em.flush();
        } else {
            criterioAvaliacaoAtor = TestUtil.findAll(em, CriterioAvaliacaoAtor.class).get(0);
        }
        feedBackUsuarioParaContador.setCriterioAvaliacaoAtor(criterioAvaliacaoAtor);
        // Add required entity
        OrdemServico ordemServico;
        if (TestUtil.findAll(em, OrdemServico.class).isEmpty()) {
            ordemServico = OrdemServicoResourceIT.createUpdatedEntity(em);
            em.persist(ordemServico);
            em.flush();
        } else {
            ordemServico = TestUtil.findAll(em, OrdemServico.class).get(0);
        }
        feedBackUsuarioParaContador.setOrdemServico(ordemServico);
        return feedBackUsuarioParaContador;
    }

    @BeforeEach
    public void initTest() {
        feedBackUsuarioParaContador = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedFeedBackUsuarioParaContador != null) {
            feedBackUsuarioParaContadorRepository.delete(insertedFeedBackUsuarioParaContador);
            insertedFeedBackUsuarioParaContador = null;
        }
    }

    @Test
    @Transactional
    void createFeedBackUsuarioParaContador() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FeedBackUsuarioParaContador
        var returnedFeedBackUsuarioParaContador = om.readValue(
            restFeedBackUsuarioParaContadorMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(feedBackUsuarioParaContador))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FeedBackUsuarioParaContador.class
        );

        // Validate the FeedBackUsuarioParaContador in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFeedBackUsuarioParaContadorUpdatableFieldsEquals(
            returnedFeedBackUsuarioParaContador,
            getPersistedFeedBackUsuarioParaContador(returnedFeedBackUsuarioParaContador)
        );

        insertedFeedBackUsuarioParaContador = returnedFeedBackUsuarioParaContador;
    }

    @Test
    @Transactional
    void createFeedBackUsuarioParaContadorWithExistingId() throws Exception {
        // Create the FeedBackUsuarioParaContador with an existing ID
        feedBackUsuarioParaContador.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeedBackUsuarioParaContadorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(feedBackUsuarioParaContador))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedBackUsuarioParaContador in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFeedBackUsuarioParaContadors() throws Exception {
        // Initialize the database
        insertedFeedBackUsuarioParaContador = feedBackUsuarioParaContadorRepository.saveAndFlush(feedBackUsuarioParaContador);

        // Get all the feedBackUsuarioParaContadorList
        restFeedBackUsuarioParaContadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedBackUsuarioParaContador.getId().intValue())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO)))
            .andExpect(jsonPath("$.[*].pontuacao").value(hasItem(DEFAULT_PONTUACAO.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFeedBackUsuarioParaContadorsWithEagerRelationshipsIsEnabled() throws Exception {
        when(feedBackUsuarioParaContadorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFeedBackUsuarioParaContadorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(feedBackUsuarioParaContadorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFeedBackUsuarioParaContadorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(feedBackUsuarioParaContadorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFeedBackUsuarioParaContadorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(feedBackUsuarioParaContadorRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getFeedBackUsuarioParaContador() throws Exception {
        // Initialize the database
        insertedFeedBackUsuarioParaContador = feedBackUsuarioParaContadorRepository.saveAndFlush(feedBackUsuarioParaContador);

        // Get the feedBackUsuarioParaContador
        restFeedBackUsuarioParaContadorMockMvc
            .perform(get(ENTITY_API_URL_ID, feedBackUsuarioParaContador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(feedBackUsuarioParaContador.getId().intValue()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO))
            .andExpect(jsonPath("$.pontuacao").value(DEFAULT_PONTUACAO.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingFeedBackUsuarioParaContador() throws Exception {
        // Get the feedBackUsuarioParaContador
        restFeedBackUsuarioParaContadorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFeedBackUsuarioParaContador() throws Exception {
        // Initialize the database
        insertedFeedBackUsuarioParaContador = feedBackUsuarioParaContadorRepository.saveAndFlush(feedBackUsuarioParaContador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the feedBackUsuarioParaContador
        FeedBackUsuarioParaContador updatedFeedBackUsuarioParaContador = feedBackUsuarioParaContadorRepository
            .findById(feedBackUsuarioParaContador.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedFeedBackUsuarioParaContador are not directly saved in db
        em.detach(updatedFeedBackUsuarioParaContador);
        updatedFeedBackUsuarioParaContador.comentario(UPDATED_COMENTARIO).pontuacao(UPDATED_PONTUACAO);

        restFeedBackUsuarioParaContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFeedBackUsuarioParaContador.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFeedBackUsuarioParaContador))
            )
            .andExpect(status().isOk());

        // Validate the FeedBackUsuarioParaContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFeedBackUsuarioParaContadorToMatchAllProperties(updatedFeedBackUsuarioParaContador);
    }

    @Test
    @Transactional
    void putNonExistingFeedBackUsuarioParaContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        feedBackUsuarioParaContador.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedBackUsuarioParaContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, feedBackUsuarioParaContador.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(feedBackUsuarioParaContador))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedBackUsuarioParaContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFeedBackUsuarioParaContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        feedBackUsuarioParaContador.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedBackUsuarioParaContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(feedBackUsuarioParaContador))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedBackUsuarioParaContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFeedBackUsuarioParaContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        feedBackUsuarioParaContador.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedBackUsuarioParaContadorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(feedBackUsuarioParaContador)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FeedBackUsuarioParaContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFeedBackUsuarioParaContadorWithPatch() throws Exception {
        // Initialize the database
        insertedFeedBackUsuarioParaContador = feedBackUsuarioParaContadorRepository.saveAndFlush(feedBackUsuarioParaContador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the feedBackUsuarioParaContador using partial update
        FeedBackUsuarioParaContador partialUpdatedFeedBackUsuarioParaContador = new FeedBackUsuarioParaContador();
        partialUpdatedFeedBackUsuarioParaContador.setId(feedBackUsuarioParaContador.getId());

        partialUpdatedFeedBackUsuarioParaContador.comentario(UPDATED_COMENTARIO).pontuacao(UPDATED_PONTUACAO);

        restFeedBackUsuarioParaContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeedBackUsuarioParaContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFeedBackUsuarioParaContador))
            )
            .andExpect(status().isOk());

        // Validate the FeedBackUsuarioParaContador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFeedBackUsuarioParaContadorUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFeedBackUsuarioParaContador, feedBackUsuarioParaContador),
            getPersistedFeedBackUsuarioParaContador(feedBackUsuarioParaContador)
        );
    }

    @Test
    @Transactional
    void fullUpdateFeedBackUsuarioParaContadorWithPatch() throws Exception {
        // Initialize the database
        insertedFeedBackUsuarioParaContador = feedBackUsuarioParaContadorRepository.saveAndFlush(feedBackUsuarioParaContador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the feedBackUsuarioParaContador using partial update
        FeedBackUsuarioParaContador partialUpdatedFeedBackUsuarioParaContador = new FeedBackUsuarioParaContador();
        partialUpdatedFeedBackUsuarioParaContador.setId(feedBackUsuarioParaContador.getId());

        partialUpdatedFeedBackUsuarioParaContador.comentario(UPDATED_COMENTARIO).pontuacao(UPDATED_PONTUACAO);

        restFeedBackUsuarioParaContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeedBackUsuarioParaContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFeedBackUsuarioParaContador))
            )
            .andExpect(status().isOk());

        // Validate the FeedBackUsuarioParaContador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFeedBackUsuarioParaContadorUpdatableFieldsEquals(
            partialUpdatedFeedBackUsuarioParaContador,
            getPersistedFeedBackUsuarioParaContador(partialUpdatedFeedBackUsuarioParaContador)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFeedBackUsuarioParaContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        feedBackUsuarioParaContador.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedBackUsuarioParaContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, feedBackUsuarioParaContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(feedBackUsuarioParaContador))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedBackUsuarioParaContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFeedBackUsuarioParaContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        feedBackUsuarioParaContador.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedBackUsuarioParaContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(feedBackUsuarioParaContador))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedBackUsuarioParaContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFeedBackUsuarioParaContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        feedBackUsuarioParaContador.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedBackUsuarioParaContadorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(feedBackUsuarioParaContador))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FeedBackUsuarioParaContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFeedBackUsuarioParaContador() throws Exception {
        // Initialize the database
        insertedFeedBackUsuarioParaContador = feedBackUsuarioParaContadorRepository.saveAndFlush(feedBackUsuarioParaContador);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the feedBackUsuarioParaContador
        restFeedBackUsuarioParaContadorMockMvc
            .perform(delete(ENTITY_API_URL_ID, feedBackUsuarioParaContador.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return feedBackUsuarioParaContadorRepository.count();
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

    protected FeedBackUsuarioParaContador getPersistedFeedBackUsuarioParaContador(FeedBackUsuarioParaContador feedBackUsuarioParaContador) {
        return feedBackUsuarioParaContadorRepository.findById(feedBackUsuarioParaContador.getId()).orElseThrow();
    }

    protected void assertPersistedFeedBackUsuarioParaContadorToMatchAllProperties(
        FeedBackUsuarioParaContador expectedFeedBackUsuarioParaContador
    ) {
        assertFeedBackUsuarioParaContadorAllPropertiesEquals(
            expectedFeedBackUsuarioParaContador,
            getPersistedFeedBackUsuarioParaContador(expectedFeedBackUsuarioParaContador)
        );
    }

    protected void assertPersistedFeedBackUsuarioParaContadorToMatchUpdatableProperties(
        FeedBackUsuarioParaContador expectedFeedBackUsuarioParaContador
    ) {
        assertFeedBackUsuarioParaContadorAllUpdatablePropertiesEquals(
            expectedFeedBackUsuarioParaContador,
            getPersistedFeedBackUsuarioParaContador(expectedFeedBackUsuarioParaContador)
        );
    }
}
