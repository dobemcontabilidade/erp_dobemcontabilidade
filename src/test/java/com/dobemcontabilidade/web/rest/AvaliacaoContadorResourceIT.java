package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.AvaliacaoContadorAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Avaliacao;
import com.dobemcontabilidade.domain.AvaliacaoContador;
import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.repository.AvaliacaoContadorRepository;
import com.dobemcontabilidade.service.AvaliacaoContadorService;
import com.dobemcontabilidade.service.dto.AvaliacaoContadorDTO;
import com.dobemcontabilidade.service.mapper.AvaliacaoContadorMapper;
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
 * Integration tests for the {@link AvaliacaoContadorResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AvaliacaoContadorResourceIT {

    private static final Double DEFAULT_PONTUACAO = 1D;
    private static final Double UPDATED_PONTUACAO = 2D;

    private static final String ENTITY_API_URL = "/api/avaliacao-contadors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AvaliacaoContadorRepository avaliacaoContadorRepository;

    @Mock
    private AvaliacaoContadorRepository avaliacaoContadorRepositoryMock;

    @Autowired
    private AvaliacaoContadorMapper avaliacaoContadorMapper;

    @Mock
    private AvaliacaoContadorService avaliacaoContadorServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvaliacaoContadorMockMvc;

    private AvaliacaoContador avaliacaoContador;

    private AvaliacaoContador insertedAvaliacaoContador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvaliacaoContador createEntity(EntityManager em) {
        AvaliacaoContador avaliacaoContador = new AvaliacaoContador().pontuacao(DEFAULT_PONTUACAO);
        // Add required entity
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            contador = ContadorResourceIT.createEntity(em);
            em.persist(contador);
            em.flush();
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        avaliacaoContador.setContador(contador);
        // Add required entity
        Avaliacao avaliacao;
        if (TestUtil.findAll(em, Avaliacao.class).isEmpty()) {
            avaliacao = AvaliacaoResourceIT.createEntity(em);
            em.persist(avaliacao);
            em.flush();
        } else {
            avaliacao = TestUtil.findAll(em, Avaliacao.class).get(0);
        }
        avaliacaoContador.setAvaliacao(avaliacao);
        return avaliacaoContador;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvaliacaoContador createUpdatedEntity(EntityManager em) {
        AvaliacaoContador avaliacaoContador = new AvaliacaoContador().pontuacao(UPDATED_PONTUACAO);
        // Add required entity
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            contador = ContadorResourceIT.createUpdatedEntity(em);
            em.persist(contador);
            em.flush();
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        avaliacaoContador.setContador(contador);
        // Add required entity
        Avaliacao avaliacao;
        if (TestUtil.findAll(em, Avaliacao.class).isEmpty()) {
            avaliacao = AvaliacaoResourceIT.createUpdatedEntity(em);
            em.persist(avaliacao);
            em.flush();
        } else {
            avaliacao = TestUtil.findAll(em, Avaliacao.class).get(0);
        }
        avaliacaoContador.setAvaliacao(avaliacao);
        return avaliacaoContador;
    }

    @BeforeEach
    public void initTest() {
        avaliacaoContador = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAvaliacaoContador != null) {
            avaliacaoContadorRepository.delete(insertedAvaliacaoContador);
            insertedAvaliacaoContador = null;
        }
    }

    @Test
    @Transactional
    void createAvaliacaoContador() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AvaliacaoContador
        AvaliacaoContadorDTO avaliacaoContadorDTO = avaliacaoContadorMapper.toDto(avaliacaoContador);
        var returnedAvaliacaoContadorDTO = om.readValue(
            restAvaliacaoContadorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(avaliacaoContadorDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AvaliacaoContadorDTO.class
        );

        // Validate the AvaliacaoContador in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAvaliacaoContador = avaliacaoContadorMapper.toEntity(returnedAvaliacaoContadorDTO);
        assertAvaliacaoContadorUpdatableFieldsEquals(returnedAvaliacaoContador, getPersistedAvaliacaoContador(returnedAvaliacaoContador));

        insertedAvaliacaoContador = returnedAvaliacaoContador;
    }

    @Test
    @Transactional
    void createAvaliacaoContadorWithExistingId() throws Exception {
        // Create the AvaliacaoContador with an existing ID
        avaliacaoContador.setId(1L);
        AvaliacaoContadorDTO avaliacaoContadorDTO = avaliacaoContadorMapper.toDto(avaliacaoContador);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvaliacaoContadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(avaliacaoContadorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AvaliacaoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAvaliacaoContadors() throws Exception {
        // Initialize the database
        insertedAvaliacaoContador = avaliacaoContadorRepository.saveAndFlush(avaliacaoContador);

        // Get all the avaliacaoContadorList
        restAvaliacaoContadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avaliacaoContador.getId().intValue())))
            .andExpect(jsonPath("$.[*].pontuacao").value(hasItem(DEFAULT_PONTUACAO.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAvaliacaoContadorsWithEagerRelationshipsIsEnabled() throws Exception {
        when(avaliacaoContadorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAvaliacaoContadorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(avaliacaoContadorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAvaliacaoContadorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(avaliacaoContadorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAvaliacaoContadorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(avaliacaoContadorRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAvaliacaoContador() throws Exception {
        // Initialize the database
        insertedAvaliacaoContador = avaliacaoContadorRepository.saveAndFlush(avaliacaoContador);

        // Get the avaliacaoContador
        restAvaliacaoContadorMockMvc
            .perform(get(ENTITY_API_URL_ID, avaliacaoContador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(avaliacaoContador.getId().intValue()))
            .andExpect(jsonPath("$.pontuacao").value(DEFAULT_PONTUACAO.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingAvaliacaoContador() throws Exception {
        // Get the avaliacaoContador
        restAvaliacaoContadorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAvaliacaoContador() throws Exception {
        // Initialize the database
        insertedAvaliacaoContador = avaliacaoContadorRepository.saveAndFlush(avaliacaoContador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the avaliacaoContador
        AvaliacaoContador updatedAvaliacaoContador = avaliacaoContadorRepository.findById(avaliacaoContador.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAvaliacaoContador are not directly saved in db
        em.detach(updatedAvaliacaoContador);
        updatedAvaliacaoContador.pontuacao(UPDATED_PONTUACAO);
        AvaliacaoContadorDTO avaliacaoContadorDTO = avaliacaoContadorMapper.toDto(updatedAvaliacaoContador);

        restAvaliacaoContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, avaliacaoContadorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(avaliacaoContadorDTO))
            )
            .andExpect(status().isOk());

        // Validate the AvaliacaoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAvaliacaoContadorToMatchAllProperties(updatedAvaliacaoContador);
    }

    @Test
    @Transactional
    void putNonExistingAvaliacaoContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        avaliacaoContador.setId(longCount.incrementAndGet());

        // Create the AvaliacaoContador
        AvaliacaoContadorDTO avaliacaoContadorDTO = avaliacaoContadorMapper.toDto(avaliacaoContador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvaliacaoContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, avaliacaoContadorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(avaliacaoContadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AvaliacaoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAvaliacaoContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        avaliacaoContador.setId(longCount.incrementAndGet());

        // Create the AvaliacaoContador
        AvaliacaoContadorDTO avaliacaoContadorDTO = avaliacaoContadorMapper.toDto(avaliacaoContador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvaliacaoContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(avaliacaoContadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AvaliacaoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAvaliacaoContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        avaliacaoContador.setId(longCount.incrementAndGet());

        // Create the AvaliacaoContador
        AvaliacaoContadorDTO avaliacaoContadorDTO = avaliacaoContadorMapper.toDto(avaliacaoContador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvaliacaoContadorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(avaliacaoContadorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AvaliacaoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAvaliacaoContadorWithPatch() throws Exception {
        // Initialize the database
        insertedAvaliacaoContador = avaliacaoContadorRepository.saveAndFlush(avaliacaoContador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the avaliacaoContador using partial update
        AvaliacaoContador partialUpdatedAvaliacaoContador = new AvaliacaoContador();
        partialUpdatedAvaliacaoContador.setId(avaliacaoContador.getId());

        restAvaliacaoContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAvaliacaoContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAvaliacaoContador))
            )
            .andExpect(status().isOk());

        // Validate the AvaliacaoContador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAvaliacaoContadorUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAvaliacaoContador, avaliacaoContador),
            getPersistedAvaliacaoContador(avaliacaoContador)
        );
    }

    @Test
    @Transactional
    void fullUpdateAvaliacaoContadorWithPatch() throws Exception {
        // Initialize the database
        insertedAvaliacaoContador = avaliacaoContadorRepository.saveAndFlush(avaliacaoContador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the avaliacaoContador using partial update
        AvaliacaoContador partialUpdatedAvaliacaoContador = new AvaliacaoContador();
        partialUpdatedAvaliacaoContador.setId(avaliacaoContador.getId());

        partialUpdatedAvaliacaoContador.pontuacao(UPDATED_PONTUACAO);

        restAvaliacaoContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAvaliacaoContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAvaliacaoContador))
            )
            .andExpect(status().isOk());

        // Validate the AvaliacaoContador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAvaliacaoContadorUpdatableFieldsEquals(
            partialUpdatedAvaliacaoContador,
            getPersistedAvaliacaoContador(partialUpdatedAvaliacaoContador)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAvaliacaoContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        avaliacaoContador.setId(longCount.incrementAndGet());

        // Create the AvaliacaoContador
        AvaliacaoContadorDTO avaliacaoContadorDTO = avaliacaoContadorMapper.toDto(avaliacaoContador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvaliacaoContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, avaliacaoContadorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(avaliacaoContadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AvaliacaoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAvaliacaoContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        avaliacaoContador.setId(longCount.incrementAndGet());

        // Create the AvaliacaoContador
        AvaliacaoContadorDTO avaliacaoContadorDTO = avaliacaoContadorMapper.toDto(avaliacaoContador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvaliacaoContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(avaliacaoContadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AvaliacaoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAvaliacaoContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        avaliacaoContador.setId(longCount.incrementAndGet());

        // Create the AvaliacaoContador
        AvaliacaoContadorDTO avaliacaoContadorDTO = avaliacaoContadorMapper.toDto(avaliacaoContador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvaliacaoContadorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(avaliacaoContadorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AvaliacaoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAvaliacaoContador() throws Exception {
        // Initialize the database
        insertedAvaliacaoContador = avaliacaoContadorRepository.saveAndFlush(avaliacaoContador);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the avaliacaoContador
        restAvaliacaoContadorMockMvc
            .perform(delete(ENTITY_API_URL_ID, avaliacaoContador.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return avaliacaoContadorRepository.count();
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

    protected AvaliacaoContador getPersistedAvaliacaoContador(AvaliacaoContador avaliacaoContador) {
        return avaliacaoContadorRepository.findById(avaliacaoContador.getId()).orElseThrow();
    }

    protected void assertPersistedAvaliacaoContadorToMatchAllProperties(AvaliacaoContador expectedAvaliacaoContador) {
        assertAvaliacaoContadorAllPropertiesEquals(expectedAvaliacaoContador, getPersistedAvaliacaoContador(expectedAvaliacaoContador));
    }

    protected void assertPersistedAvaliacaoContadorToMatchUpdatableProperties(AvaliacaoContador expectedAvaliacaoContador) {
        assertAvaliacaoContadorAllUpdatablePropertiesEquals(
            expectedAvaliacaoContador,
            getPersistedAvaliacaoContador(expectedAvaliacaoContador)
        );
    }
}
