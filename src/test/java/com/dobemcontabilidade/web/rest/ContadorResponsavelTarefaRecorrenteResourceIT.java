package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.ContadorResponsavelTarefaRecorrenteAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.domain.ContadorResponsavelTarefaRecorrente;
import com.dobemcontabilidade.domain.TarefaRecorrenteExecucao;
import com.dobemcontabilidade.repository.ContadorResponsavelTarefaRecorrenteRepository;
import com.dobemcontabilidade.service.ContadorResponsavelTarefaRecorrenteService;
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
 * Integration tests for the {@link ContadorResponsavelTarefaRecorrenteResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ContadorResponsavelTarefaRecorrenteResourceIT {

    private static final Instant DEFAULT_DATA_ATRIBUICAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ATRIBUICAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_REVOGACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_REVOGACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_CONCLUIDA = false;
    private static final Boolean UPDATED_CONCLUIDA = true;

    private static final String ENTITY_API_URL = "/api/contador-responsavel-tarefa-recorrentes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ContadorResponsavelTarefaRecorrenteRepository contadorResponsavelTarefaRecorrenteRepository;

    @Mock
    private ContadorResponsavelTarefaRecorrenteRepository contadorResponsavelTarefaRecorrenteRepositoryMock;

    @Mock
    private ContadorResponsavelTarefaRecorrenteService contadorResponsavelTarefaRecorrenteServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContadorResponsavelTarefaRecorrenteMockMvc;

    private ContadorResponsavelTarefaRecorrente contadorResponsavelTarefaRecorrente;

    private ContadorResponsavelTarefaRecorrente insertedContadorResponsavelTarefaRecorrente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContadorResponsavelTarefaRecorrente createEntity(EntityManager em) {
        ContadorResponsavelTarefaRecorrente contadorResponsavelTarefaRecorrente = new ContadorResponsavelTarefaRecorrente()
            .dataAtribuicao(DEFAULT_DATA_ATRIBUICAO)
            .dataRevogacao(DEFAULT_DATA_REVOGACAO)
            .concluida(DEFAULT_CONCLUIDA);
        // Add required entity
        TarefaRecorrenteExecucao tarefaRecorrenteExecucao;
        if (TestUtil.findAll(em, TarefaRecorrenteExecucao.class).isEmpty()) {
            tarefaRecorrenteExecucao = TarefaRecorrenteExecucaoResourceIT.createEntity(em);
            em.persist(tarefaRecorrenteExecucao);
            em.flush();
        } else {
            tarefaRecorrenteExecucao = TestUtil.findAll(em, TarefaRecorrenteExecucao.class).get(0);
        }
        contadorResponsavelTarefaRecorrente.setTarefaRecorrenteExecucao(tarefaRecorrenteExecucao);
        // Add required entity
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            contador = ContadorResourceIT.createEntity(em);
            em.persist(contador);
            em.flush();
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        contadorResponsavelTarefaRecorrente.setContador(contador);
        return contadorResponsavelTarefaRecorrente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContadorResponsavelTarefaRecorrente createUpdatedEntity(EntityManager em) {
        ContadorResponsavelTarefaRecorrente contadorResponsavelTarefaRecorrente = new ContadorResponsavelTarefaRecorrente()
            .dataAtribuicao(UPDATED_DATA_ATRIBUICAO)
            .dataRevogacao(UPDATED_DATA_REVOGACAO)
            .concluida(UPDATED_CONCLUIDA);
        // Add required entity
        TarefaRecorrenteExecucao tarefaRecorrenteExecucao;
        if (TestUtil.findAll(em, TarefaRecorrenteExecucao.class).isEmpty()) {
            tarefaRecorrenteExecucao = TarefaRecorrenteExecucaoResourceIT.createUpdatedEntity(em);
            em.persist(tarefaRecorrenteExecucao);
            em.flush();
        } else {
            tarefaRecorrenteExecucao = TestUtil.findAll(em, TarefaRecorrenteExecucao.class).get(0);
        }
        contadorResponsavelTarefaRecorrente.setTarefaRecorrenteExecucao(tarefaRecorrenteExecucao);
        // Add required entity
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            contador = ContadorResourceIT.createUpdatedEntity(em);
            em.persist(contador);
            em.flush();
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        contadorResponsavelTarefaRecorrente.setContador(contador);
        return contadorResponsavelTarefaRecorrente;
    }

    @BeforeEach
    public void initTest() {
        contadorResponsavelTarefaRecorrente = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedContadorResponsavelTarefaRecorrente != null) {
            contadorResponsavelTarefaRecorrenteRepository.delete(insertedContadorResponsavelTarefaRecorrente);
            insertedContadorResponsavelTarefaRecorrente = null;
        }
    }

    @Test
    @Transactional
    void createContadorResponsavelTarefaRecorrente() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ContadorResponsavelTarefaRecorrente
        var returnedContadorResponsavelTarefaRecorrente = om.readValue(
            restContadorResponsavelTarefaRecorrenteMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(contadorResponsavelTarefaRecorrente))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ContadorResponsavelTarefaRecorrente.class
        );

        // Validate the ContadorResponsavelTarefaRecorrente in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertContadorResponsavelTarefaRecorrenteUpdatableFieldsEquals(
            returnedContadorResponsavelTarefaRecorrente,
            getPersistedContadorResponsavelTarefaRecorrente(returnedContadorResponsavelTarefaRecorrente)
        );

        insertedContadorResponsavelTarefaRecorrente = returnedContadorResponsavelTarefaRecorrente;
    }

    @Test
    @Transactional
    void createContadorResponsavelTarefaRecorrenteWithExistingId() throws Exception {
        // Create the ContadorResponsavelTarefaRecorrente with an existing ID
        contadorResponsavelTarefaRecorrente.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContadorResponsavelTarefaRecorrenteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contadorResponsavelTarefaRecorrente))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContadorResponsavelTarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllContadorResponsavelTarefaRecorrentes() throws Exception {
        // Initialize the database
        insertedContadorResponsavelTarefaRecorrente = contadorResponsavelTarefaRecorrenteRepository.saveAndFlush(
            contadorResponsavelTarefaRecorrente
        );

        // Get all the contadorResponsavelTarefaRecorrenteList
        restContadorResponsavelTarefaRecorrenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contadorResponsavelTarefaRecorrente.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataAtribuicao").value(hasItem(DEFAULT_DATA_ATRIBUICAO.toString())))
            .andExpect(jsonPath("$.[*].dataRevogacao").value(hasItem(DEFAULT_DATA_REVOGACAO.toString())))
            .andExpect(jsonPath("$.[*].concluida").value(hasItem(DEFAULT_CONCLUIDA.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllContadorResponsavelTarefaRecorrentesWithEagerRelationshipsIsEnabled() throws Exception {
        when(contadorResponsavelTarefaRecorrenteServiceMock.findAllWithEagerRelationships(any())).thenReturn(
            new PageImpl(new ArrayList<>())
        );

        restContadorResponsavelTarefaRecorrenteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(contadorResponsavelTarefaRecorrenteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllContadorResponsavelTarefaRecorrentesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(contadorResponsavelTarefaRecorrenteServiceMock.findAllWithEagerRelationships(any())).thenReturn(
            new PageImpl(new ArrayList<>())
        );

        restContadorResponsavelTarefaRecorrenteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(contadorResponsavelTarefaRecorrenteRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getContadorResponsavelTarefaRecorrente() throws Exception {
        // Initialize the database
        insertedContadorResponsavelTarefaRecorrente = contadorResponsavelTarefaRecorrenteRepository.saveAndFlush(
            contadorResponsavelTarefaRecorrente
        );

        // Get the contadorResponsavelTarefaRecorrente
        restContadorResponsavelTarefaRecorrenteMockMvc
            .perform(get(ENTITY_API_URL_ID, contadorResponsavelTarefaRecorrente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contadorResponsavelTarefaRecorrente.getId().intValue()))
            .andExpect(jsonPath("$.dataAtribuicao").value(DEFAULT_DATA_ATRIBUICAO.toString()))
            .andExpect(jsonPath("$.dataRevogacao").value(DEFAULT_DATA_REVOGACAO.toString()))
            .andExpect(jsonPath("$.concluida").value(DEFAULT_CONCLUIDA.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingContadorResponsavelTarefaRecorrente() throws Exception {
        // Get the contadorResponsavelTarefaRecorrente
        restContadorResponsavelTarefaRecorrenteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingContadorResponsavelTarefaRecorrente() throws Exception {
        // Initialize the database
        insertedContadorResponsavelTarefaRecorrente = contadorResponsavelTarefaRecorrenteRepository.saveAndFlush(
            contadorResponsavelTarefaRecorrente
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contadorResponsavelTarefaRecorrente
        ContadorResponsavelTarefaRecorrente updatedContadorResponsavelTarefaRecorrente = contadorResponsavelTarefaRecorrenteRepository
            .findById(contadorResponsavelTarefaRecorrente.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedContadorResponsavelTarefaRecorrente are not directly saved in db
        em.detach(updatedContadorResponsavelTarefaRecorrente);
        updatedContadorResponsavelTarefaRecorrente
            .dataAtribuicao(UPDATED_DATA_ATRIBUICAO)
            .dataRevogacao(UPDATED_DATA_REVOGACAO)
            .concluida(UPDATED_CONCLUIDA);

        restContadorResponsavelTarefaRecorrenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedContadorResponsavelTarefaRecorrente.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedContadorResponsavelTarefaRecorrente))
            )
            .andExpect(status().isOk());

        // Validate the ContadorResponsavelTarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedContadorResponsavelTarefaRecorrenteToMatchAllProperties(updatedContadorResponsavelTarefaRecorrente);
    }

    @Test
    @Transactional
    void putNonExistingContadorResponsavelTarefaRecorrente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contadorResponsavelTarefaRecorrente.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContadorResponsavelTarefaRecorrenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contadorResponsavelTarefaRecorrente.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contadorResponsavelTarefaRecorrente))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContadorResponsavelTarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContadorResponsavelTarefaRecorrente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contadorResponsavelTarefaRecorrente.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContadorResponsavelTarefaRecorrenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contadorResponsavelTarefaRecorrente))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContadorResponsavelTarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContadorResponsavelTarefaRecorrente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contadorResponsavelTarefaRecorrente.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContadorResponsavelTarefaRecorrenteMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contadorResponsavelTarefaRecorrente))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContadorResponsavelTarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContadorResponsavelTarefaRecorrenteWithPatch() throws Exception {
        // Initialize the database
        insertedContadorResponsavelTarefaRecorrente = contadorResponsavelTarefaRecorrenteRepository.saveAndFlush(
            contadorResponsavelTarefaRecorrente
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contadorResponsavelTarefaRecorrente using partial update
        ContadorResponsavelTarefaRecorrente partialUpdatedContadorResponsavelTarefaRecorrente = new ContadorResponsavelTarefaRecorrente();
        partialUpdatedContadorResponsavelTarefaRecorrente.setId(contadorResponsavelTarefaRecorrente.getId());

        partialUpdatedContadorResponsavelTarefaRecorrente.dataRevogacao(UPDATED_DATA_REVOGACAO);

        restContadorResponsavelTarefaRecorrenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContadorResponsavelTarefaRecorrente.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContadorResponsavelTarefaRecorrente))
            )
            .andExpect(status().isOk());

        // Validate the ContadorResponsavelTarefaRecorrente in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContadorResponsavelTarefaRecorrenteUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedContadorResponsavelTarefaRecorrente, contadorResponsavelTarefaRecorrente),
            getPersistedContadorResponsavelTarefaRecorrente(contadorResponsavelTarefaRecorrente)
        );
    }

    @Test
    @Transactional
    void fullUpdateContadorResponsavelTarefaRecorrenteWithPatch() throws Exception {
        // Initialize the database
        insertedContadorResponsavelTarefaRecorrente = contadorResponsavelTarefaRecorrenteRepository.saveAndFlush(
            contadorResponsavelTarefaRecorrente
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contadorResponsavelTarefaRecorrente using partial update
        ContadorResponsavelTarefaRecorrente partialUpdatedContadorResponsavelTarefaRecorrente = new ContadorResponsavelTarefaRecorrente();
        partialUpdatedContadorResponsavelTarefaRecorrente.setId(contadorResponsavelTarefaRecorrente.getId());

        partialUpdatedContadorResponsavelTarefaRecorrente
            .dataAtribuicao(UPDATED_DATA_ATRIBUICAO)
            .dataRevogacao(UPDATED_DATA_REVOGACAO)
            .concluida(UPDATED_CONCLUIDA);

        restContadorResponsavelTarefaRecorrenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContadorResponsavelTarefaRecorrente.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContadorResponsavelTarefaRecorrente))
            )
            .andExpect(status().isOk());

        // Validate the ContadorResponsavelTarefaRecorrente in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContadorResponsavelTarefaRecorrenteUpdatableFieldsEquals(
            partialUpdatedContadorResponsavelTarefaRecorrente,
            getPersistedContadorResponsavelTarefaRecorrente(partialUpdatedContadorResponsavelTarefaRecorrente)
        );
    }

    @Test
    @Transactional
    void patchNonExistingContadorResponsavelTarefaRecorrente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contadorResponsavelTarefaRecorrente.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContadorResponsavelTarefaRecorrenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contadorResponsavelTarefaRecorrente.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(contadorResponsavelTarefaRecorrente))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContadorResponsavelTarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContadorResponsavelTarefaRecorrente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contadorResponsavelTarefaRecorrente.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContadorResponsavelTarefaRecorrenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(contadorResponsavelTarefaRecorrente))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContadorResponsavelTarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContadorResponsavelTarefaRecorrente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contadorResponsavelTarefaRecorrente.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContadorResponsavelTarefaRecorrenteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(contadorResponsavelTarefaRecorrente))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContadorResponsavelTarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContadorResponsavelTarefaRecorrente() throws Exception {
        // Initialize the database
        insertedContadorResponsavelTarefaRecorrente = contadorResponsavelTarefaRecorrenteRepository.saveAndFlush(
            contadorResponsavelTarefaRecorrente
        );

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the contadorResponsavelTarefaRecorrente
        restContadorResponsavelTarefaRecorrenteMockMvc
            .perform(delete(ENTITY_API_URL_ID, contadorResponsavelTarefaRecorrente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return contadorResponsavelTarefaRecorrenteRepository.count();
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

    protected ContadorResponsavelTarefaRecorrente getPersistedContadorResponsavelTarefaRecorrente(
        ContadorResponsavelTarefaRecorrente contadorResponsavelTarefaRecorrente
    ) {
        return contadorResponsavelTarefaRecorrenteRepository.findById(contadorResponsavelTarefaRecorrente.getId()).orElseThrow();
    }

    protected void assertPersistedContadorResponsavelTarefaRecorrenteToMatchAllProperties(
        ContadorResponsavelTarefaRecorrente expectedContadorResponsavelTarefaRecorrente
    ) {
        assertContadorResponsavelTarefaRecorrenteAllPropertiesEquals(
            expectedContadorResponsavelTarefaRecorrente,
            getPersistedContadorResponsavelTarefaRecorrente(expectedContadorResponsavelTarefaRecorrente)
        );
    }

    protected void assertPersistedContadorResponsavelTarefaRecorrenteToMatchUpdatableProperties(
        ContadorResponsavelTarefaRecorrente expectedContadorResponsavelTarefaRecorrente
    ) {
        assertContadorResponsavelTarefaRecorrenteAllUpdatablePropertiesEquals(
            expectedContadorResponsavelTarefaRecorrente,
            getPersistedContadorResponsavelTarefaRecorrente(expectedContadorResponsavelTarefaRecorrente)
        );
    }
}
