package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.DescontoPeriodoPagamentoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.DescontoPeriodoPagamento;
import com.dobemcontabilidade.domain.PeriodoPagamento;
import com.dobemcontabilidade.domain.PlanoAssinaturaContabil;
import com.dobemcontabilidade.repository.DescontoPeriodoPagamentoRepository;
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
 * Integration tests for the {@link DescontoPeriodoPagamentoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DescontoPeriodoPagamentoResourceIT {

    private static final Double DEFAULT_PERCENTUAL = 1D;
    private static final Double UPDATED_PERCENTUAL = 2D;

    private static final String ENTITY_API_URL = "/api/desconto-periodo-pagamentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DescontoPeriodoPagamentoRepository descontoPeriodoPagamentoRepository;

    @Mock
    private DescontoPeriodoPagamentoRepository descontoPeriodoPagamentoRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDescontoPeriodoPagamentoMockMvc;

    private DescontoPeriodoPagamento descontoPeriodoPagamento;

    private DescontoPeriodoPagamento insertedDescontoPeriodoPagamento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DescontoPeriodoPagamento createEntity(EntityManager em) {
        DescontoPeriodoPagamento descontoPeriodoPagamento = new DescontoPeriodoPagamento().percentual(DEFAULT_PERCENTUAL);
        // Add required entity
        PeriodoPagamento periodoPagamento;
        if (TestUtil.findAll(em, PeriodoPagamento.class).isEmpty()) {
            periodoPagamento = PeriodoPagamentoResourceIT.createEntity(em);
            em.persist(periodoPagamento);
            em.flush();
        } else {
            periodoPagamento = TestUtil.findAll(em, PeriodoPagamento.class).get(0);
        }
        descontoPeriodoPagamento.setPeriodoPagamento(periodoPagamento);
        // Add required entity
        PlanoAssinaturaContabil planoAssinaturaContabil;
        if (TestUtil.findAll(em, PlanoAssinaturaContabil.class).isEmpty()) {
            planoAssinaturaContabil = PlanoAssinaturaContabilResourceIT.createEntity(em);
            em.persist(planoAssinaturaContabil);
            em.flush();
        } else {
            planoAssinaturaContabil = TestUtil.findAll(em, PlanoAssinaturaContabil.class).get(0);
        }
        descontoPeriodoPagamento.setPlanoAssinaturaContabil(planoAssinaturaContabil);
        return descontoPeriodoPagamento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DescontoPeriodoPagamento createUpdatedEntity(EntityManager em) {
        DescontoPeriodoPagamento descontoPeriodoPagamento = new DescontoPeriodoPagamento().percentual(UPDATED_PERCENTUAL);
        // Add required entity
        PeriodoPagamento periodoPagamento;
        if (TestUtil.findAll(em, PeriodoPagamento.class).isEmpty()) {
            periodoPagamento = PeriodoPagamentoResourceIT.createUpdatedEntity(em);
            em.persist(periodoPagamento);
            em.flush();
        } else {
            periodoPagamento = TestUtil.findAll(em, PeriodoPagamento.class).get(0);
        }
        descontoPeriodoPagamento.setPeriodoPagamento(periodoPagamento);
        // Add required entity
        PlanoAssinaturaContabil planoAssinaturaContabil;
        if (TestUtil.findAll(em, PlanoAssinaturaContabil.class).isEmpty()) {
            planoAssinaturaContabil = PlanoAssinaturaContabilResourceIT.createUpdatedEntity(em);
            em.persist(planoAssinaturaContabil);
            em.flush();
        } else {
            planoAssinaturaContabil = TestUtil.findAll(em, PlanoAssinaturaContabil.class).get(0);
        }
        descontoPeriodoPagamento.setPlanoAssinaturaContabil(planoAssinaturaContabil);
        return descontoPeriodoPagamento;
    }

    @BeforeEach
    public void initTest() {
        descontoPeriodoPagamento = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedDescontoPeriodoPagamento != null) {
            descontoPeriodoPagamentoRepository.delete(insertedDescontoPeriodoPagamento);
            insertedDescontoPeriodoPagamento = null;
        }
    }

    @Test
    @Transactional
    void createDescontoPeriodoPagamento() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DescontoPeriodoPagamento
        var returnedDescontoPeriodoPagamento = om.readValue(
            restDescontoPeriodoPagamentoMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(descontoPeriodoPagamento))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DescontoPeriodoPagamento.class
        );

        // Validate the DescontoPeriodoPagamento in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDescontoPeriodoPagamentoUpdatableFieldsEquals(
            returnedDescontoPeriodoPagamento,
            getPersistedDescontoPeriodoPagamento(returnedDescontoPeriodoPagamento)
        );

        insertedDescontoPeriodoPagamento = returnedDescontoPeriodoPagamento;
    }

    @Test
    @Transactional
    void createDescontoPeriodoPagamentoWithExistingId() throws Exception {
        // Create the DescontoPeriodoPagamento with an existing ID
        descontoPeriodoPagamento.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDescontoPeriodoPagamentoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(descontoPeriodoPagamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the DescontoPeriodoPagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDescontoPeriodoPagamentos() throws Exception {
        // Initialize the database
        insertedDescontoPeriodoPagamento = descontoPeriodoPagamentoRepository.saveAndFlush(descontoPeriodoPagamento);

        // Get all the descontoPeriodoPagamentoList
        restDescontoPeriodoPagamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(descontoPeriodoPagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].percentual").value(hasItem(DEFAULT_PERCENTUAL.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDescontoPeriodoPagamentosWithEagerRelationshipsIsEnabled() throws Exception {
        when(descontoPeriodoPagamentoRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDescontoPeriodoPagamentoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(descontoPeriodoPagamentoRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDescontoPeriodoPagamentosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(descontoPeriodoPagamentoRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDescontoPeriodoPagamentoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(descontoPeriodoPagamentoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDescontoPeriodoPagamento() throws Exception {
        // Initialize the database
        insertedDescontoPeriodoPagamento = descontoPeriodoPagamentoRepository.saveAndFlush(descontoPeriodoPagamento);

        // Get the descontoPeriodoPagamento
        restDescontoPeriodoPagamentoMockMvc
            .perform(get(ENTITY_API_URL_ID, descontoPeriodoPagamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(descontoPeriodoPagamento.getId().intValue()))
            .andExpect(jsonPath("$.percentual").value(DEFAULT_PERCENTUAL.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingDescontoPeriodoPagamento() throws Exception {
        // Get the descontoPeriodoPagamento
        restDescontoPeriodoPagamentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDescontoPeriodoPagamento() throws Exception {
        // Initialize the database
        insertedDescontoPeriodoPagamento = descontoPeriodoPagamentoRepository.saveAndFlush(descontoPeriodoPagamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the descontoPeriodoPagamento
        DescontoPeriodoPagamento updatedDescontoPeriodoPagamento = descontoPeriodoPagamentoRepository
            .findById(descontoPeriodoPagamento.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedDescontoPeriodoPagamento are not directly saved in db
        em.detach(updatedDescontoPeriodoPagamento);
        updatedDescontoPeriodoPagamento.percentual(UPDATED_PERCENTUAL);

        restDescontoPeriodoPagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDescontoPeriodoPagamento.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDescontoPeriodoPagamento))
            )
            .andExpect(status().isOk());

        // Validate the DescontoPeriodoPagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDescontoPeriodoPagamentoToMatchAllProperties(updatedDescontoPeriodoPagamento);
    }

    @Test
    @Transactional
    void putNonExistingDescontoPeriodoPagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        descontoPeriodoPagamento.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDescontoPeriodoPagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, descontoPeriodoPagamento.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(descontoPeriodoPagamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the DescontoPeriodoPagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDescontoPeriodoPagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        descontoPeriodoPagamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDescontoPeriodoPagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(descontoPeriodoPagamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the DescontoPeriodoPagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDescontoPeriodoPagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        descontoPeriodoPagamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDescontoPeriodoPagamentoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(descontoPeriodoPagamento))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DescontoPeriodoPagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDescontoPeriodoPagamentoWithPatch() throws Exception {
        // Initialize the database
        insertedDescontoPeriodoPagamento = descontoPeriodoPagamentoRepository.saveAndFlush(descontoPeriodoPagamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the descontoPeriodoPagamento using partial update
        DescontoPeriodoPagamento partialUpdatedDescontoPeriodoPagamento = new DescontoPeriodoPagamento();
        partialUpdatedDescontoPeriodoPagamento.setId(descontoPeriodoPagamento.getId());

        restDescontoPeriodoPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDescontoPeriodoPagamento.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDescontoPeriodoPagamento))
            )
            .andExpect(status().isOk());

        // Validate the DescontoPeriodoPagamento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDescontoPeriodoPagamentoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDescontoPeriodoPagamento, descontoPeriodoPagamento),
            getPersistedDescontoPeriodoPagamento(descontoPeriodoPagamento)
        );
    }

    @Test
    @Transactional
    void fullUpdateDescontoPeriodoPagamentoWithPatch() throws Exception {
        // Initialize the database
        insertedDescontoPeriodoPagamento = descontoPeriodoPagamentoRepository.saveAndFlush(descontoPeriodoPagamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the descontoPeriodoPagamento using partial update
        DescontoPeriodoPagamento partialUpdatedDescontoPeriodoPagamento = new DescontoPeriodoPagamento();
        partialUpdatedDescontoPeriodoPagamento.setId(descontoPeriodoPagamento.getId());

        partialUpdatedDescontoPeriodoPagamento.percentual(UPDATED_PERCENTUAL);

        restDescontoPeriodoPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDescontoPeriodoPagamento.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDescontoPeriodoPagamento))
            )
            .andExpect(status().isOk());

        // Validate the DescontoPeriodoPagamento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDescontoPeriodoPagamentoUpdatableFieldsEquals(
            partialUpdatedDescontoPeriodoPagamento,
            getPersistedDescontoPeriodoPagamento(partialUpdatedDescontoPeriodoPagamento)
        );
    }

    @Test
    @Transactional
    void patchNonExistingDescontoPeriodoPagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        descontoPeriodoPagamento.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDescontoPeriodoPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, descontoPeriodoPagamento.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(descontoPeriodoPagamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the DescontoPeriodoPagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDescontoPeriodoPagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        descontoPeriodoPagamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDescontoPeriodoPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(descontoPeriodoPagamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the DescontoPeriodoPagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDescontoPeriodoPagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        descontoPeriodoPagamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDescontoPeriodoPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(descontoPeriodoPagamento))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DescontoPeriodoPagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDescontoPeriodoPagamento() throws Exception {
        // Initialize the database
        insertedDescontoPeriodoPagamento = descontoPeriodoPagamentoRepository.saveAndFlush(descontoPeriodoPagamento);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the descontoPeriodoPagamento
        restDescontoPeriodoPagamentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, descontoPeriodoPagamento.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return descontoPeriodoPagamentoRepository.count();
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

    protected DescontoPeriodoPagamento getPersistedDescontoPeriodoPagamento(DescontoPeriodoPagamento descontoPeriodoPagamento) {
        return descontoPeriodoPagamentoRepository.findById(descontoPeriodoPagamento.getId()).orElseThrow();
    }

    protected void assertPersistedDescontoPeriodoPagamentoToMatchAllProperties(DescontoPeriodoPagamento expectedDescontoPeriodoPagamento) {
        assertDescontoPeriodoPagamentoAllPropertiesEquals(
            expectedDescontoPeriodoPagamento,
            getPersistedDescontoPeriodoPagamento(expectedDescontoPeriodoPagamento)
        );
    }

    protected void assertPersistedDescontoPeriodoPagamentoToMatchUpdatableProperties(
        DescontoPeriodoPagamento expectedDescontoPeriodoPagamento
    ) {
        assertDescontoPeriodoPagamentoAllUpdatablePropertiesEquals(
            expectedDescontoPeriodoPagamento,
            getPersistedDescontoPeriodoPagamento(expectedDescontoPeriodoPagamento)
        );
    }
}
