package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.GrupoAcessoUsuarioContadorAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.GrupoAcessoPadrao;
import com.dobemcontabilidade.domain.GrupoAcessoUsuarioContador;
import com.dobemcontabilidade.domain.UsuarioContador;
import com.dobemcontabilidade.repository.GrupoAcessoUsuarioContadorRepository;
import com.dobemcontabilidade.service.GrupoAcessoUsuarioContadorService;
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
 * Integration tests for the {@link GrupoAcessoUsuarioContadorResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class GrupoAcessoUsuarioContadorResourceIT {

    private static final Instant DEFAULT_DATA_EXPIRACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_EXPIRACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ILIMITADO = false;
    private static final Boolean UPDATED_ILIMITADO = true;

    private static final Boolean DEFAULT_DESABILITAR = false;
    private static final Boolean UPDATED_DESABILITAR = true;

    private static final String ENTITY_API_URL = "/api/grupo-acesso-usuario-contadors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GrupoAcessoUsuarioContadorRepository grupoAcessoUsuarioContadorRepository;

    @Mock
    private GrupoAcessoUsuarioContadorRepository grupoAcessoUsuarioContadorRepositoryMock;

    @Mock
    private GrupoAcessoUsuarioContadorService grupoAcessoUsuarioContadorServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGrupoAcessoUsuarioContadorMockMvc;

    private GrupoAcessoUsuarioContador grupoAcessoUsuarioContador;

    private GrupoAcessoUsuarioContador insertedGrupoAcessoUsuarioContador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoAcessoUsuarioContador createEntity(EntityManager em) {
        GrupoAcessoUsuarioContador grupoAcessoUsuarioContador = new GrupoAcessoUsuarioContador()
            .dataExpiracao(DEFAULT_DATA_EXPIRACAO)
            .ilimitado(DEFAULT_ILIMITADO)
            .desabilitar(DEFAULT_DESABILITAR);
        // Add required entity
        UsuarioContador usuarioContador;
        if (TestUtil.findAll(em, UsuarioContador.class).isEmpty()) {
            usuarioContador = UsuarioContadorResourceIT.createEntity(em);
            em.persist(usuarioContador);
            em.flush();
        } else {
            usuarioContador = TestUtil.findAll(em, UsuarioContador.class).get(0);
        }
        grupoAcessoUsuarioContador.setUsuarioContador(usuarioContador);
        // Add required entity
        GrupoAcessoPadrao grupoAcessoPadrao;
        if (TestUtil.findAll(em, GrupoAcessoPadrao.class).isEmpty()) {
            grupoAcessoPadrao = GrupoAcessoPadraoResourceIT.createEntity(em);
            em.persist(grupoAcessoPadrao);
            em.flush();
        } else {
            grupoAcessoPadrao = TestUtil.findAll(em, GrupoAcessoPadrao.class).get(0);
        }
        grupoAcessoUsuarioContador.setGrupoAcessoPadrao(grupoAcessoPadrao);
        return grupoAcessoUsuarioContador;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoAcessoUsuarioContador createUpdatedEntity(EntityManager em) {
        GrupoAcessoUsuarioContador grupoAcessoUsuarioContador = new GrupoAcessoUsuarioContador()
            .dataExpiracao(UPDATED_DATA_EXPIRACAO)
            .ilimitado(UPDATED_ILIMITADO)
            .desabilitar(UPDATED_DESABILITAR);
        // Add required entity
        UsuarioContador usuarioContador;
        if (TestUtil.findAll(em, UsuarioContador.class).isEmpty()) {
            usuarioContador = UsuarioContadorResourceIT.createUpdatedEntity(em);
            em.persist(usuarioContador);
            em.flush();
        } else {
            usuarioContador = TestUtil.findAll(em, UsuarioContador.class).get(0);
        }
        grupoAcessoUsuarioContador.setUsuarioContador(usuarioContador);
        // Add required entity
        GrupoAcessoPadrao grupoAcessoPadrao;
        if (TestUtil.findAll(em, GrupoAcessoPadrao.class).isEmpty()) {
            grupoAcessoPadrao = GrupoAcessoPadraoResourceIT.createUpdatedEntity(em);
            em.persist(grupoAcessoPadrao);
            em.flush();
        } else {
            grupoAcessoPadrao = TestUtil.findAll(em, GrupoAcessoPadrao.class).get(0);
        }
        grupoAcessoUsuarioContador.setGrupoAcessoPadrao(grupoAcessoPadrao);
        return grupoAcessoUsuarioContador;
    }

    @BeforeEach
    public void initTest() {
        grupoAcessoUsuarioContador = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedGrupoAcessoUsuarioContador != null) {
            grupoAcessoUsuarioContadorRepository.delete(insertedGrupoAcessoUsuarioContador);
            insertedGrupoAcessoUsuarioContador = null;
        }
    }

    @Test
    @Transactional
    void createGrupoAcessoUsuarioContador() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the GrupoAcessoUsuarioContador
        var returnedGrupoAcessoUsuarioContador = om.readValue(
            restGrupoAcessoUsuarioContadorMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoAcessoUsuarioContador))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            GrupoAcessoUsuarioContador.class
        );

        // Validate the GrupoAcessoUsuarioContador in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGrupoAcessoUsuarioContadorUpdatableFieldsEquals(
            returnedGrupoAcessoUsuarioContador,
            getPersistedGrupoAcessoUsuarioContador(returnedGrupoAcessoUsuarioContador)
        );

        insertedGrupoAcessoUsuarioContador = returnedGrupoAcessoUsuarioContador;
    }

    @Test
    @Transactional
    void createGrupoAcessoUsuarioContadorWithExistingId() throws Exception {
        // Create the GrupoAcessoUsuarioContador with an existing ID
        grupoAcessoUsuarioContador.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrupoAcessoUsuarioContadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoAcessoUsuarioContador)))
            .andExpect(status().isBadRequest());

        // Validate the GrupoAcessoUsuarioContador in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGrupoAcessoUsuarioContadors() throws Exception {
        // Initialize the database
        insertedGrupoAcessoUsuarioContador = grupoAcessoUsuarioContadorRepository.saveAndFlush(grupoAcessoUsuarioContador);

        // Get all the grupoAcessoUsuarioContadorList
        restGrupoAcessoUsuarioContadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupoAcessoUsuarioContador.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataExpiracao").value(hasItem(DEFAULT_DATA_EXPIRACAO.toString())))
            .andExpect(jsonPath("$.[*].ilimitado").value(hasItem(DEFAULT_ILIMITADO.booleanValue())))
            .andExpect(jsonPath("$.[*].desabilitar").value(hasItem(DEFAULT_DESABILITAR.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGrupoAcessoUsuarioContadorsWithEagerRelationshipsIsEnabled() throws Exception {
        when(grupoAcessoUsuarioContadorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGrupoAcessoUsuarioContadorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(grupoAcessoUsuarioContadorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGrupoAcessoUsuarioContadorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(grupoAcessoUsuarioContadorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGrupoAcessoUsuarioContadorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(grupoAcessoUsuarioContadorRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getGrupoAcessoUsuarioContador() throws Exception {
        // Initialize the database
        insertedGrupoAcessoUsuarioContador = grupoAcessoUsuarioContadorRepository.saveAndFlush(grupoAcessoUsuarioContador);

        // Get the grupoAcessoUsuarioContador
        restGrupoAcessoUsuarioContadorMockMvc
            .perform(get(ENTITY_API_URL_ID, grupoAcessoUsuarioContador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grupoAcessoUsuarioContador.getId().intValue()))
            .andExpect(jsonPath("$.dataExpiracao").value(DEFAULT_DATA_EXPIRACAO.toString()))
            .andExpect(jsonPath("$.ilimitado").value(DEFAULT_ILIMITADO.booleanValue()))
            .andExpect(jsonPath("$.desabilitar").value(DEFAULT_DESABILITAR.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingGrupoAcessoUsuarioContador() throws Exception {
        // Get the grupoAcessoUsuarioContador
        restGrupoAcessoUsuarioContadorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGrupoAcessoUsuarioContador() throws Exception {
        // Initialize the database
        insertedGrupoAcessoUsuarioContador = grupoAcessoUsuarioContadorRepository.saveAndFlush(grupoAcessoUsuarioContador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grupoAcessoUsuarioContador
        GrupoAcessoUsuarioContador updatedGrupoAcessoUsuarioContador = grupoAcessoUsuarioContadorRepository
            .findById(grupoAcessoUsuarioContador.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedGrupoAcessoUsuarioContador are not directly saved in db
        em.detach(updatedGrupoAcessoUsuarioContador);
        updatedGrupoAcessoUsuarioContador
            .dataExpiracao(UPDATED_DATA_EXPIRACAO)
            .ilimitado(UPDATED_ILIMITADO)
            .desabilitar(UPDATED_DESABILITAR);

        restGrupoAcessoUsuarioContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGrupoAcessoUsuarioContador.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGrupoAcessoUsuarioContador))
            )
            .andExpect(status().isOk());

        // Validate the GrupoAcessoUsuarioContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGrupoAcessoUsuarioContadorToMatchAllProperties(updatedGrupoAcessoUsuarioContador);
    }

    @Test
    @Transactional
    void putNonExistingGrupoAcessoUsuarioContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoUsuarioContador.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoAcessoUsuarioContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, grupoAcessoUsuarioContador.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(grupoAcessoUsuarioContador))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoAcessoUsuarioContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGrupoAcessoUsuarioContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoUsuarioContador.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoAcessoUsuarioContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(grupoAcessoUsuarioContador))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoAcessoUsuarioContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGrupoAcessoUsuarioContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoUsuarioContador.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoAcessoUsuarioContadorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoAcessoUsuarioContador)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GrupoAcessoUsuarioContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGrupoAcessoUsuarioContadorWithPatch() throws Exception {
        // Initialize the database
        insertedGrupoAcessoUsuarioContador = grupoAcessoUsuarioContadorRepository.saveAndFlush(grupoAcessoUsuarioContador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grupoAcessoUsuarioContador using partial update
        GrupoAcessoUsuarioContador partialUpdatedGrupoAcessoUsuarioContador = new GrupoAcessoUsuarioContador();
        partialUpdatedGrupoAcessoUsuarioContador.setId(grupoAcessoUsuarioContador.getId());

        partialUpdatedGrupoAcessoUsuarioContador.desabilitar(UPDATED_DESABILITAR);

        restGrupoAcessoUsuarioContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrupoAcessoUsuarioContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGrupoAcessoUsuarioContador))
            )
            .andExpect(status().isOk());

        // Validate the GrupoAcessoUsuarioContador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGrupoAcessoUsuarioContadorUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGrupoAcessoUsuarioContador, grupoAcessoUsuarioContador),
            getPersistedGrupoAcessoUsuarioContador(grupoAcessoUsuarioContador)
        );
    }

    @Test
    @Transactional
    void fullUpdateGrupoAcessoUsuarioContadorWithPatch() throws Exception {
        // Initialize the database
        insertedGrupoAcessoUsuarioContador = grupoAcessoUsuarioContadorRepository.saveAndFlush(grupoAcessoUsuarioContador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grupoAcessoUsuarioContador using partial update
        GrupoAcessoUsuarioContador partialUpdatedGrupoAcessoUsuarioContador = new GrupoAcessoUsuarioContador();
        partialUpdatedGrupoAcessoUsuarioContador.setId(grupoAcessoUsuarioContador.getId());

        partialUpdatedGrupoAcessoUsuarioContador
            .dataExpiracao(UPDATED_DATA_EXPIRACAO)
            .ilimitado(UPDATED_ILIMITADO)
            .desabilitar(UPDATED_DESABILITAR);

        restGrupoAcessoUsuarioContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrupoAcessoUsuarioContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGrupoAcessoUsuarioContador))
            )
            .andExpect(status().isOk());

        // Validate the GrupoAcessoUsuarioContador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGrupoAcessoUsuarioContadorUpdatableFieldsEquals(
            partialUpdatedGrupoAcessoUsuarioContador,
            getPersistedGrupoAcessoUsuarioContador(partialUpdatedGrupoAcessoUsuarioContador)
        );
    }

    @Test
    @Transactional
    void patchNonExistingGrupoAcessoUsuarioContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoUsuarioContador.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoAcessoUsuarioContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, grupoAcessoUsuarioContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(grupoAcessoUsuarioContador))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoAcessoUsuarioContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGrupoAcessoUsuarioContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoUsuarioContador.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoAcessoUsuarioContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(grupoAcessoUsuarioContador))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoAcessoUsuarioContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGrupoAcessoUsuarioContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoUsuarioContador.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoAcessoUsuarioContadorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(grupoAcessoUsuarioContador))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GrupoAcessoUsuarioContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGrupoAcessoUsuarioContador() throws Exception {
        // Initialize the database
        insertedGrupoAcessoUsuarioContador = grupoAcessoUsuarioContadorRepository.saveAndFlush(grupoAcessoUsuarioContador);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the grupoAcessoUsuarioContador
        restGrupoAcessoUsuarioContadorMockMvc
            .perform(delete(ENTITY_API_URL_ID, grupoAcessoUsuarioContador.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return grupoAcessoUsuarioContadorRepository.count();
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

    protected GrupoAcessoUsuarioContador getPersistedGrupoAcessoUsuarioContador(GrupoAcessoUsuarioContador grupoAcessoUsuarioContador) {
        return grupoAcessoUsuarioContadorRepository.findById(grupoAcessoUsuarioContador.getId()).orElseThrow();
    }

    protected void assertPersistedGrupoAcessoUsuarioContadorToMatchAllProperties(
        GrupoAcessoUsuarioContador expectedGrupoAcessoUsuarioContador
    ) {
        assertGrupoAcessoUsuarioContadorAllPropertiesEquals(
            expectedGrupoAcessoUsuarioContador,
            getPersistedGrupoAcessoUsuarioContador(expectedGrupoAcessoUsuarioContador)
        );
    }

    protected void assertPersistedGrupoAcessoUsuarioContadorToMatchUpdatableProperties(
        GrupoAcessoUsuarioContador expectedGrupoAcessoUsuarioContador
    ) {
        assertGrupoAcessoUsuarioContadorAllUpdatablePropertiesEquals(
            expectedGrupoAcessoUsuarioContador,
            getPersistedGrupoAcessoUsuarioContador(expectedGrupoAcessoUsuarioContador)
        );
    }
}
