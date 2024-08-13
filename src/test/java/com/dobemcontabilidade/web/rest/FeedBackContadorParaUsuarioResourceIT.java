package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.FeedBackContadorParaUsuarioAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.domain.CriterioAvaliacaoAtor;
import com.dobemcontabilidade.domain.FeedBackContadorParaUsuario;
import com.dobemcontabilidade.domain.OrdemServico;
import com.dobemcontabilidade.domain.UsuarioEmpresa;
import com.dobemcontabilidade.repository.FeedBackContadorParaUsuarioRepository;
import com.dobemcontabilidade.service.FeedBackContadorParaUsuarioService;
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
 * Integration tests for the {@link FeedBackContadorParaUsuarioResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FeedBackContadorParaUsuarioResourceIT {

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    private static final Double DEFAULT_PONTUACAO = 1D;
    private static final Double UPDATED_PONTUACAO = 2D;

    private static final String ENTITY_API_URL = "/api/feed-back-contador-para-usuarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FeedBackContadorParaUsuarioRepository feedBackContadorParaUsuarioRepository;

    @Mock
    private FeedBackContadorParaUsuarioRepository feedBackContadorParaUsuarioRepositoryMock;

    @Mock
    private FeedBackContadorParaUsuarioService feedBackContadorParaUsuarioServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeedBackContadorParaUsuarioMockMvc;

    private FeedBackContadorParaUsuario feedBackContadorParaUsuario;

    private FeedBackContadorParaUsuario insertedFeedBackContadorParaUsuario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeedBackContadorParaUsuario createEntity(EntityManager em) {
        FeedBackContadorParaUsuario feedBackContadorParaUsuario = new FeedBackContadorParaUsuario()
            .comentario(DEFAULT_COMENTARIO)
            .pontuacao(DEFAULT_PONTUACAO);
        // Add required entity
        CriterioAvaliacaoAtor criterioAvaliacaoAtor;
        if (TestUtil.findAll(em, CriterioAvaliacaoAtor.class).isEmpty()) {
            criterioAvaliacaoAtor = CriterioAvaliacaoAtorResourceIT.createEntity(em);
            em.persist(criterioAvaliacaoAtor);
            em.flush();
        } else {
            criterioAvaliacaoAtor = TestUtil.findAll(em, CriterioAvaliacaoAtor.class).get(0);
        }
        feedBackContadorParaUsuario.setCriterioAvaliacaoAtor(criterioAvaliacaoAtor);
        // Add required entity
        UsuarioEmpresa usuarioEmpresa;
        if (TestUtil.findAll(em, UsuarioEmpresa.class).isEmpty()) {
            usuarioEmpresa = UsuarioEmpresaResourceIT.createEntity(em);
            em.persist(usuarioEmpresa);
            em.flush();
        } else {
            usuarioEmpresa = TestUtil.findAll(em, UsuarioEmpresa.class).get(0);
        }
        feedBackContadorParaUsuario.setUsuarioEmpresa(usuarioEmpresa);
        // Add required entity
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            contador = ContadorResourceIT.createEntity(em);
            em.persist(contador);
            em.flush();
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        feedBackContadorParaUsuario.setContador(contador);
        // Add required entity
        OrdemServico ordemServico;
        if (TestUtil.findAll(em, OrdemServico.class).isEmpty()) {
            ordemServico = OrdemServicoResourceIT.createEntity(em);
            em.persist(ordemServico);
            em.flush();
        } else {
            ordemServico = TestUtil.findAll(em, OrdemServico.class).get(0);
        }
        feedBackContadorParaUsuario.setOrdemServico(ordemServico);
        return feedBackContadorParaUsuario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeedBackContadorParaUsuario createUpdatedEntity(EntityManager em) {
        FeedBackContadorParaUsuario feedBackContadorParaUsuario = new FeedBackContadorParaUsuario()
            .comentario(UPDATED_COMENTARIO)
            .pontuacao(UPDATED_PONTUACAO);
        // Add required entity
        CriterioAvaliacaoAtor criterioAvaliacaoAtor;
        if (TestUtil.findAll(em, CriterioAvaliacaoAtor.class).isEmpty()) {
            criterioAvaliacaoAtor = CriterioAvaliacaoAtorResourceIT.createUpdatedEntity(em);
            em.persist(criterioAvaliacaoAtor);
            em.flush();
        } else {
            criterioAvaliacaoAtor = TestUtil.findAll(em, CriterioAvaliacaoAtor.class).get(0);
        }
        feedBackContadorParaUsuario.setCriterioAvaliacaoAtor(criterioAvaliacaoAtor);
        // Add required entity
        UsuarioEmpresa usuarioEmpresa;
        if (TestUtil.findAll(em, UsuarioEmpresa.class).isEmpty()) {
            usuarioEmpresa = UsuarioEmpresaResourceIT.createUpdatedEntity(em);
            em.persist(usuarioEmpresa);
            em.flush();
        } else {
            usuarioEmpresa = TestUtil.findAll(em, UsuarioEmpresa.class).get(0);
        }
        feedBackContadorParaUsuario.setUsuarioEmpresa(usuarioEmpresa);
        // Add required entity
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            contador = ContadorResourceIT.createUpdatedEntity(em);
            em.persist(contador);
            em.flush();
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        feedBackContadorParaUsuario.setContador(contador);
        // Add required entity
        OrdemServico ordemServico;
        if (TestUtil.findAll(em, OrdemServico.class).isEmpty()) {
            ordemServico = OrdemServicoResourceIT.createUpdatedEntity(em);
            em.persist(ordemServico);
            em.flush();
        } else {
            ordemServico = TestUtil.findAll(em, OrdemServico.class).get(0);
        }
        feedBackContadorParaUsuario.setOrdemServico(ordemServico);
        return feedBackContadorParaUsuario;
    }

    @BeforeEach
    public void initTest() {
        feedBackContadorParaUsuario = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedFeedBackContadorParaUsuario != null) {
            feedBackContadorParaUsuarioRepository.delete(insertedFeedBackContadorParaUsuario);
            insertedFeedBackContadorParaUsuario = null;
        }
    }

    @Test
    @Transactional
    void createFeedBackContadorParaUsuario() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FeedBackContadorParaUsuario
        var returnedFeedBackContadorParaUsuario = om.readValue(
            restFeedBackContadorParaUsuarioMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(feedBackContadorParaUsuario))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FeedBackContadorParaUsuario.class
        );

        // Validate the FeedBackContadorParaUsuario in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFeedBackContadorParaUsuarioUpdatableFieldsEquals(
            returnedFeedBackContadorParaUsuario,
            getPersistedFeedBackContadorParaUsuario(returnedFeedBackContadorParaUsuario)
        );

        insertedFeedBackContadorParaUsuario = returnedFeedBackContadorParaUsuario;
    }

    @Test
    @Transactional
    void createFeedBackContadorParaUsuarioWithExistingId() throws Exception {
        // Create the FeedBackContadorParaUsuario with an existing ID
        feedBackContadorParaUsuario.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeedBackContadorParaUsuarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(feedBackContadorParaUsuario))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedBackContadorParaUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFeedBackContadorParaUsuarios() throws Exception {
        // Initialize the database
        insertedFeedBackContadorParaUsuario = feedBackContadorParaUsuarioRepository.saveAndFlush(feedBackContadorParaUsuario);

        // Get all the feedBackContadorParaUsuarioList
        restFeedBackContadorParaUsuarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedBackContadorParaUsuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO)))
            .andExpect(jsonPath("$.[*].pontuacao").value(hasItem(DEFAULT_PONTUACAO.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFeedBackContadorParaUsuariosWithEagerRelationshipsIsEnabled() throws Exception {
        when(feedBackContadorParaUsuarioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFeedBackContadorParaUsuarioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(feedBackContadorParaUsuarioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFeedBackContadorParaUsuariosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(feedBackContadorParaUsuarioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFeedBackContadorParaUsuarioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(feedBackContadorParaUsuarioRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getFeedBackContadorParaUsuario() throws Exception {
        // Initialize the database
        insertedFeedBackContadorParaUsuario = feedBackContadorParaUsuarioRepository.saveAndFlush(feedBackContadorParaUsuario);

        // Get the feedBackContadorParaUsuario
        restFeedBackContadorParaUsuarioMockMvc
            .perform(get(ENTITY_API_URL_ID, feedBackContadorParaUsuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(feedBackContadorParaUsuario.getId().intValue()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO))
            .andExpect(jsonPath("$.pontuacao").value(DEFAULT_PONTUACAO.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingFeedBackContadorParaUsuario() throws Exception {
        // Get the feedBackContadorParaUsuario
        restFeedBackContadorParaUsuarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFeedBackContadorParaUsuario() throws Exception {
        // Initialize the database
        insertedFeedBackContadorParaUsuario = feedBackContadorParaUsuarioRepository.saveAndFlush(feedBackContadorParaUsuario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the feedBackContadorParaUsuario
        FeedBackContadorParaUsuario updatedFeedBackContadorParaUsuario = feedBackContadorParaUsuarioRepository
            .findById(feedBackContadorParaUsuario.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedFeedBackContadorParaUsuario are not directly saved in db
        em.detach(updatedFeedBackContadorParaUsuario);
        updatedFeedBackContadorParaUsuario.comentario(UPDATED_COMENTARIO).pontuacao(UPDATED_PONTUACAO);

        restFeedBackContadorParaUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFeedBackContadorParaUsuario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFeedBackContadorParaUsuario))
            )
            .andExpect(status().isOk());

        // Validate the FeedBackContadorParaUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFeedBackContadorParaUsuarioToMatchAllProperties(updatedFeedBackContadorParaUsuario);
    }

    @Test
    @Transactional
    void putNonExistingFeedBackContadorParaUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        feedBackContadorParaUsuario.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedBackContadorParaUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, feedBackContadorParaUsuario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(feedBackContadorParaUsuario))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedBackContadorParaUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFeedBackContadorParaUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        feedBackContadorParaUsuario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedBackContadorParaUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(feedBackContadorParaUsuario))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedBackContadorParaUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFeedBackContadorParaUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        feedBackContadorParaUsuario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedBackContadorParaUsuarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(feedBackContadorParaUsuario)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FeedBackContadorParaUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFeedBackContadorParaUsuarioWithPatch() throws Exception {
        // Initialize the database
        insertedFeedBackContadorParaUsuario = feedBackContadorParaUsuarioRepository.saveAndFlush(feedBackContadorParaUsuario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the feedBackContadorParaUsuario using partial update
        FeedBackContadorParaUsuario partialUpdatedFeedBackContadorParaUsuario = new FeedBackContadorParaUsuario();
        partialUpdatedFeedBackContadorParaUsuario.setId(feedBackContadorParaUsuario.getId());

        partialUpdatedFeedBackContadorParaUsuario.comentario(UPDATED_COMENTARIO).pontuacao(UPDATED_PONTUACAO);

        restFeedBackContadorParaUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeedBackContadorParaUsuario.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFeedBackContadorParaUsuario))
            )
            .andExpect(status().isOk());

        // Validate the FeedBackContadorParaUsuario in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFeedBackContadorParaUsuarioUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFeedBackContadorParaUsuario, feedBackContadorParaUsuario),
            getPersistedFeedBackContadorParaUsuario(feedBackContadorParaUsuario)
        );
    }

    @Test
    @Transactional
    void fullUpdateFeedBackContadorParaUsuarioWithPatch() throws Exception {
        // Initialize the database
        insertedFeedBackContadorParaUsuario = feedBackContadorParaUsuarioRepository.saveAndFlush(feedBackContadorParaUsuario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the feedBackContadorParaUsuario using partial update
        FeedBackContadorParaUsuario partialUpdatedFeedBackContadorParaUsuario = new FeedBackContadorParaUsuario();
        partialUpdatedFeedBackContadorParaUsuario.setId(feedBackContadorParaUsuario.getId());

        partialUpdatedFeedBackContadorParaUsuario.comentario(UPDATED_COMENTARIO).pontuacao(UPDATED_PONTUACAO);

        restFeedBackContadorParaUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeedBackContadorParaUsuario.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFeedBackContadorParaUsuario))
            )
            .andExpect(status().isOk());

        // Validate the FeedBackContadorParaUsuario in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFeedBackContadorParaUsuarioUpdatableFieldsEquals(
            partialUpdatedFeedBackContadorParaUsuario,
            getPersistedFeedBackContadorParaUsuario(partialUpdatedFeedBackContadorParaUsuario)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFeedBackContadorParaUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        feedBackContadorParaUsuario.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedBackContadorParaUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, feedBackContadorParaUsuario.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(feedBackContadorParaUsuario))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedBackContadorParaUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFeedBackContadorParaUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        feedBackContadorParaUsuario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedBackContadorParaUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(feedBackContadorParaUsuario))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedBackContadorParaUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFeedBackContadorParaUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        feedBackContadorParaUsuario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedBackContadorParaUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(feedBackContadorParaUsuario))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FeedBackContadorParaUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFeedBackContadorParaUsuario() throws Exception {
        // Initialize the database
        insertedFeedBackContadorParaUsuario = feedBackContadorParaUsuarioRepository.saveAndFlush(feedBackContadorParaUsuario);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the feedBackContadorParaUsuario
        restFeedBackContadorParaUsuarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, feedBackContadorParaUsuario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return feedBackContadorParaUsuarioRepository.count();
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

    protected FeedBackContadorParaUsuario getPersistedFeedBackContadorParaUsuario(FeedBackContadorParaUsuario feedBackContadorParaUsuario) {
        return feedBackContadorParaUsuarioRepository.findById(feedBackContadorParaUsuario.getId()).orElseThrow();
    }

    protected void assertPersistedFeedBackContadorParaUsuarioToMatchAllProperties(
        FeedBackContadorParaUsuario expectedFeedBackContadorParaUsuario
    ) {
        assertFeedBackContadorParaUsuarioAllPropertiesEquals(
            expectedFeedBackContadorParaUsuario,
            getPersistedFeedBackContadorParaUsuario(expectedFeedBackContadorParaUsuario)
        );
    }

    protected void assertPersistedFeedBackContadorParaUsuarioToMatchUpdatableProperties(
        FeedBackContadorParaUsuario expectedFeedBackContadorParaUsuario
    ) {
        assertFeedBackContadorParaUsuarioAllUpdatablePropertiesEquals(
            expectedFeedBackContadorParaUsuario,
            getPersistedFeedBackContadorParaUsuario(expectedFeedBackContadorParaUsuario)
        );
    }
}
