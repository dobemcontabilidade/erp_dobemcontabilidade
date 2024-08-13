package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.ContadorResponsavelOrdemServicoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.domain.ContadorResponsavelOrdemServico;
import com.dobemcontabilidade.domain.TarefaOrdemServicoExecucao;
import com.dobemcontabilidade.repository.ContadorResponsavelOrdemServicoRepository;
import com.dobemcontabilidade.service.ContadorResponsavelOrdemServicoService;
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
 * Integration tests for the {@link ContadorResponsavelOrdemServicoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ContadorResponsavelOrdemServicoResourceIT {

    private static final Instant DEFAULT_DATA_ATRIBUICAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ATRIBUICAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_REVOGACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_REVOGACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/contador-responsavel-ordem-servicos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ContadorResponsavelOrdemServicoRepository contadorResponsavelOrdemServicoRepository;

    @Mock
    private ContadorResponsavelOrdemServicoRepository contadorResponsavelOrdemServicoRepositoryMock;

    @Mock
    private ContadorResponsavelOrdemServicoService contadorResponsavelOrdemServicoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContadorResponsavelOrdemServicoMockMvc;

    private ContadorResponsavelOrdemServico contadorResponsavelOrdemServico;

    private ContadorResponsavelOrdemServico insertedContadorResponsavelOrdemServico;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContadorResponsavelOrdemServico createEntity(EntityManager em) {
        ContadorResponsavelOrdemServico contadorResponsavelOrdemServico = new ContadorResponsavelOrdemServico()
            .dataAtribuicao(DEFAULT_DATA_ATRIBUICAO)
            .dataRevogacao(DEFAULT_DATA_REVOGACAO);
        // Add required entity
        TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao;
        if (TestUtil.findAll(em, TarefaOrdemServicoExecucao.class).isEmpty()) {
            tarefaOrdemServicoExecucao = TarefaOrdemServicoExecucaoResourceIT.createEntity(em);
            em.persist(tarefaOrdemServicoExecucao);
            em.flush();
        } else {
            tarefaOrdemServicoExecucao = TestUtil.findAll(em, TarefaOrdemServicoExecucao.class).get(0);
        }
        contadorResponsavelOrdemServico.setTarefaOrdemServicoExecucao(tarefaOrdemServicoExecucao);
        // Add required entity
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            contador = ContadorResourceIT.createEntity(em);
            em.persist(contador);
            em.flush();
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        contadorResponsavelOrdemServico.setContador(contador);
        return contadorResponsavelOrdemServico;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContadorResponsavelOrdemServico createUpdatedEntity(EntityManager em) {
        ContadorResponsavelOrdemServico contadorResponsavelOrdemServico = new ContadorResponsavelOrdemServico()
            .dataAtribuicao(UPDATED_DATA_ATRIBUICAO)
            .dataRevogacao(UPDATED_DATA_REVOGACAO);
        // Add required entity
        TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao;
        if (TestUtil.findAll(em, TarefaOrdemServicoExecucao.class).isEmpty()) {
            tarefaOrdemServicoExecucao = TarefaOrdemServicoExecucaoResourceIT.createUpdatedEntity(em);
            em.persist(tarefaOrdemServicoExecucao);
            em.flush();
        } else {
            tarefaOrdemServicoExecucao = TestUtil.findAll(em, TarefaOrdemServicoExecucao.class).get(0);
        }
        contadorResponsavelOrdemServico.setTarefaOrdemServicoExecucao(tarefaOrdemServicoExecucao);
        // Add required entity
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            contador = ContadorResourceIT.createUpdatedEntity(em);
            em.persist(contador);
            em.flush();
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        contadorResponsavelOrdemServico.setContador(contador);
        return contadorResponsavelOrdemServico;
    }

    @BeforeEach
    public void initTest() {
        contadorResponsavelOrdemServico = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedContadorResponsavelOrdemServico != null) {
            contadorResponsavelOrdemServicoRepository.delete(insertedContadorResponsavelOrdemServico);
            insertedContadorResponsavelOrdemServico = null;
        }
    }

    @Test
    @Transactional
    void createContadorResponsavelOrdemServico() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ContadorResponsavelOrdemServico
        var returnedContadorResponsavelOrdemServico = om.readValue(
            restContadorResponsavelOrdemServicoMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(contadorResponsavelOrdemServico))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ContadorResponsavelOrdemServico.class
        );

        // Validate the ContadorResponsavelOrdemServico in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertContadorResponsavelOrdemServicoUpdatableFieldsEquals(
            returnedContadorResponsavelOrdemServico,
            getPersistedContadorResponsavelOrdemServico(returnedContadorResponsavelOrdemServico)
        );

        insertedContadorResponsavelOrdemServico = returnedContadorResponsavelOrdemServico;
    }

    @Test
    @Transactional
    void createContadorResponsavelOrdemServicoWithExistingId() throws Exception {
        // Create the ContadorResponsavelOrdemServico with an existing ID
        contadorResponsavelOrdemServico.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContadorResponsavelOrdemServicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contadorResponsavelOrdemServico))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContadorResponsavelOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllContadorResponsavelOrdemServicos() throws Exception {
        // Initialize the database
        insertedContadorResponsavelOrdemServico = contadorResponsavelOrdemServicoRepository.saveAndFlush(contadorResponsavelOrdemServico);

        // Get all the contadorResponsavelOrdemServicoList
        restContadorResponsavelOrdemServicoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contadorResponsavelOrdemServico.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataAtribuicao").value(hasItem(DEFAULT_DATA_ATRIBUICAO.toString())))
            .andExpect(jsonPath("$.[*].dataRevogacao").value(hasItem(DEFAULT_DATA_REVOGACAO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllContadorResponsavelOrdemServicosWithEagerRelationshipsIsEnabled() throws Exception {
        when(contadorResponsavelOrdemServicoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContadorResponsavelOrdemServicoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(contadorResponsavelOrdemServicoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllContadorResponsavelOrdemServicosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(contadorResponsavelOrdemServicoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContadorResponsavelOrdemServicoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(contadorResponsavelOrdemServicoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getContadorResponsavelOrdemServico() throws Exception {
        // Initialize the database
        insertedContadorResponsavelOrdemServico = contadorResponsavelOrdemServicoRepository.saveAndFlush(contadorResponsavelOrdemServico);

        // Get the contadorResponsavelOrdemServico
        restContadorResponsavelOrdemServicoMockMvc
            .perform(get(ENTITY_API_URL_ID, contadorResponsavelOrdemServico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contadorResponsavelOrdemServico.getId().intValue()))
            .andExpect(jsonPath("$.dataAtribuicao").value(DEFAULT_DATA_ATRIBUICAO.toString()))
            .andExpect(jsonPath("$.dataRevogacao").value(DEFAULT_DATA_REVOGACAO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingContadorResponsavelOrdemServico() throws Exception {
        // Get the contadorResponsavelOrdemServico
        restContadorResponsavelOrdemServicoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingContadorResponsavelOrdemServico() throws Exception {
        // Initialize the database
        insertedContadorResponsavelOrdemServico = contadorResponsavelOrdemServicoRepository.saveAndFlush(contadorResponsavelOrdemServico);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contadorResponsavelOrdemServico
        ContadorResponsavelOrdemServico updatedContadorResponsavelOrdemServico = contadorResponsavelOrdemServicoRepository
            .findById(contadorResponsavelOrdemServico.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedContadorResponsavelOrdemServico are not directly saved in db
        em.detach(updatedContadorResponsavelOrdemServico);
        updatedContadorResponsavelOrdemServico.dataAtribuicao(UPDATED_DATA_ATRIBUICAO).dataRevogacao(UPDATED_DATA_REVOGACAO);

        restContadorResponsavelOrdemServicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedContadorResponsavelOrdemServico.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedContadorResponsavelOrdemServico))
            )
            .andExpect(status().isOk());

        // Validate the ContadorResponsavelOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedContadorResponsavelOrdemServicoToMatchAllProperties(updatedContadorResponsavelOrdemServico);
    }

    @Test
    @Transactional
    void putNonExistingContadorResponsavelOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contadorResponsavelOrdemServico.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContadorResponsavelOrdemServicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contadorResponsavelOrdemServico.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contadorResponsavelOrdemServico))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContadorResponsavelOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContadorResponsavelOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contadorResponsavelOrdemServico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContadorResponsavelOrdemServicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contadorResponsavelOrdemServico))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContadorResponsavelOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContadorResponsavelOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contadorResponsavelOrdemServico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContadorResponsavelOrdemServicoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contadorResponsavelOrdemServico))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContadorResponsavelOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContadorResponsavelOrdemServicoWithPatch() throws Exception {
        // Initialize the database
        insertedContadorResponsavelOrdemServico = contadorResponsavelOrdemServicoRepository.saveAndFlush(contadorResponsavelOrdemServico);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contadorResponsavelOrdemServico using partial update
        ContadorResponsavelOrdemServico partialUpdatedContadorResponsavelOrdemServico = new ContadorResponsavelOrdemServico();
        partialUpdatedContadorResponsavelOrdemServico.setId(contadorResponsavelOrdemServico.getId());

        restContadorResponsavelOrdemServicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContadorResponsavelOrdemServico.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContadorResponsavelOrdemServico))
            )
            .andExpect(status().isOk());

        // Validate the ContadorResponsavelOrdemServico in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContadorResponsavelOrdemServicoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedContadorResponsavelOrdemServico, contadorResponsavelOrdemServico),
            getPersistedContadorResponsavelOrdemServico(contadorResponsavelOrdemServico)
        );
    }

    @Test
    @Transactional
    void fullUpdateContadorResponsavelOrdemServicoWithPatch() throws Exception {
        // Initialize the database
        insertedContadorResponsavelOrdemServico = contadorResponsavelOrdemServicoRepository.saveAndFlush(contadorResponsavelOrdemServico);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contadorResponsavelOrdemServico using partial update
        ContadorResponsavelOrdemServico partialUpdatedContadorResponsavelOrdemServico = new ContadorResponsavelOrdemServico();
        partialUpdatedContadorResponsavelOrdemServico.setId(contadorResponsavelOrdemServico.getId());

        partialUpdatedContadorResponsavelOrdemServico.dataAtribuicao(UPDATED_DATA_ATRIBUICAO).dataRevogacao(UPDATED_DATA_REVOGACAO);

        restContadorResponsavelOrdemServicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContadorResponsavelOrdemServico.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContadorResponsavelOrdemServico))
            )
            .andExpect(status().isOk());

        // Validate the ContadorResponsavelOrdemServico in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContadorResponsavelOrdemServicoUpdatableFieldsEquals(
            partialUpdatedContadorResponsavelOrdemServico,
            getPersistedContadorResponsavelOrdemServico(partialUpdatedContadorResponsavelOrdemServico)
        );
    }

    @Test
    @Transactional
    void patchNonExistingContadorResponsavelOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contadorResponsavelOrdemServico.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContadorResponsavelOrdemServicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contadorResponsavelOrdemServico.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(contadorResponsavelOrdemServico))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContadorResponsavelOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContadorResponsavelOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contadorResponsavelOrdemServico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContadorResponsavelOrdemServicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(contadorResponsavelOrdemServico))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContadorResponsavelOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContadorResponsavelOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contadorResponsavelOrdemServico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContadorResponsavelOrdemServicoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(contadorResponsavelOrdemServico))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContadorResponsavelOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContadorResponsavelOrdemServico() throws Exception {
        // Initialize the database
        insertedContadorResponsavelOrdemServico = contadorResponsavelOrdemServicoRepository.saveAndFlush(contadorResponsavelOrdemServico);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the contadorResponsavelOrdemServico
        restContadorResponsavelOrdemServicoMockMvc
            .perform(delete(ENTITY_API_URL_ID, contadorResponsavelOrdemServico.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return contadorResponsavelOrdemServicoRepository.count();
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

    protected ContadorResponsavelOrdemServico getPersistedContadorResponsavelOrdemServico(
        ContadorResponsavelOrdemServico contadorResponsavelOrdemServico
    ) {
        return contadorResponsavelOrdemServicoRepository.findById(contadorResponsavelOrdemServico.getId()).orElseThrow();
    }

    protected void assertPersistedContadorResponsavelOrdemServicoToMatchAllProperties(
        ContadorResponsavelOrdemServico expectedContadorResponsavelOrdemServico
    ) {
        assertContadorResponsavelOrdemServicoAllPropertiesEquals(
            expectedContadorResponsavelOrdemServico,
            getPersistedContadorResponsavelOrdemServico(expectedContadorResponsavelOrdemServico)
        );
    }

    protected void assertPersistedContadorResponsavelOrdemServicoToMatchUpdatableProperties(
        ContadorResponsavelOrdemServico expectedContadorResponsavelOrdemServico
    ) {
        assertContadorResponsavelOrdemServicoAllUpdatablePropertiesEquals(
            expectedContadorResponsavelOrdemServico,
            getPersistedContadorResponsavelOrdemServico(expectedContadorResponsavelOrdemServico)
        );
    }
}
