package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.CobrancaEmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AssinaturaEmpresa;
import com.dobemcontabilidade.domain.CobrancaEmpresa;
import com.dobemcontabilidade.domain.FormaDePagamento;
import com.dobemcontabilidade.domain.enumeration.SituacaoCobrancaEnum;
import com.dobemcontabilidade.repository.CobrancaEmpresaRepository;
import com.dobemcontabilidade.service.CobrancaEmpresaService;
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
 * Integration tests for the {@link CobrancaEmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CobrancaEmpresaResourceIT {

    private static final Instant DEFAULT_DATA_COBRANCA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_COBRANCA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_VALOR_PAGO = 1D;
    private static final Double UPDATED_VALOR_PAGO = 2D;

    private static final String DEFAULT_URL_COBRANCA = "AAAAAAAAAA";
    private static final String UPDATED_URL_COBRANCA = "BBBBBBBBBB";

    private static final String DEFAULT_URL_ARQUIVO = "AAAAAAAAAA";
    private static final String UPDATED_URL_ARQUIVO = "BBBBBBBBBB";

    private static final Double DEFAULT_VALOR_COBRADO = 1D;
    private static final Double UPDATED_VALOR_COBRADO = 2D;

    private static final SituacaoCobrancaEnum DEFAULT_SITUACAO_COBRANCA = SituacaoCobrancaEnum.ABERTA;
    private static final SituacaoCobrancaEnum UPDATED_SITUACAO_COBRANCA = SituacaoCobrancaEnum.FECHADA;

    private static final String ENTITY_API_URL = "/api/cobranca-empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CobrancaEmpresaRepository cobrancaEmpresaRepository;

    @Mock
    private CobrancaEmpresaRepository cobrancaEmpresaRepositoryMock;

    @Mock
    private CobrancaEmpresaService cobrancaEmpresaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCobrancaEmpresaMockMvc;

    private CobrancaEmpresa cobrancaEmpresa;

    private CobrancaEmpresa insertedCobrancaEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CobrancaEmpresa createEntity(EntityManager em) {
        CobrancaEmpresa cobrancaEmpresa = new CobrancaEmpresa()
            .dataCobranca(DEFAULT_DATA_COBRANCA)
            .valorPago(DEFAULT_VALOR_PAGO)
            .urlCobranca(DEFAULT_URL_COBRANCA)
            .urlArquivo(DEFAULT_URL_ARQUIVO)
            .valorCobrado(DEFAULT_VALOR_COBRADO)
            .situacaoCobranca(DEFAULT_SITUACAO_COBRANCA);
        // Add required entity
        AssinaturaEmpresa assinaturaEmpresa;
        if (TestUtil.findAll(em, AssinaturaEmpresa.class).isEmpty()) {
            assinaturaEmpresa = AssinaturaEmpresaResourceIT.createEntity(em);
            em.persist(assinaturaEmpresa);
            em.flush();
        } else {
            assinaturaEmpresa = TestUtil.findAll(em, AssinaturaEmpresa.class).get(0);
        }
        cobrancaEmpresa.setAssinaturaEmpresa(assinaturaEmpresa);
        // Add required entity
        FormaDePagamento formaDePagamento;
        if (TestUtil.findAll(em, FormaDePagamento.class).isEmpty()) {
            formaDePagamento = FormaDePagamentoResourceIT.createEntity(em);
            em.persist(formaDePagamento);
            em.flush();
        } else {
            formaDePagamento = TestUtil.findAll(em, FormaDePagamento.class).get(0);
        }
        cobrancaEmpresa.setFormaDePagamento(formaDePagamento);
        return cobrancaEmpresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CobrancaEmpresa createUpdatedEntity(EntityManager em) {
        CobrancaEmpresa cobrancaEmpresa = new CobrancaEmpresa()
            .dataCobranca(UPDATED_DATA_COBRANCA)
            .valorPago(UPDATED_VALOR_PAGO)
            .urlCobranca(UPDATED_URL_COBRANCA)
            .urlArquivo(UPDATED_URL_ARQUIVO)
            .valorCobrado(UPDATED_VALOR_COBRADO)
            .situacaoCobranca(UPDATED_SITUACAO_COBRANCA);
        // Add required entity
        AssinaturaEmpresa assinaturaEmpresa;
        if (TestUtil.findAll(em, AssinaturaEmpresa.class).isEmpty()) {
            assinaturaEmpresa = AssinaturaEmpresaResourceIT.createUpdatedEntity(em);
            em.persist(assinaturaEmpresa);
            em.flush();
        } else {
            assinaturaEmpresa = TestUtil.findAll(em, AssinaturaEmpresa.class).get(0);
        }
        cobrancaEmpresa.setAssinaturaEmpresa(assinaturaEmpresa);
        // Add required entity
        FormaDePagamento formaDePagamento;
        if (TestUtil.findAll(em, FormaDePagamento.class).isEmpty()) {
            formaDePagamento = FormaDePagamentoResourceIT.createUpdatedEntity(em);
            em.persist(formaDePagamento);
            em.flush();
        } else {
            formaDePagamento = TestUtil.findAll(em, FormaDePagamento.class).get(0);
        }
        cobrancaEmpresa.setFormaDePagamento(formaDePagamento);
        return cobrancaEmpresa;
    }

    @BeforeEach
    public void initTest() {
        cobrancaEmpresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedCobrancaEmpresa != null) {
            cobrancaEmpresaRepository.delete(insertedCobrancaEmpresa);
            insertedCobrancaEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createCobrancaEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CobrancaEmpresa
        var returnedCobrancaEmpresa = om.readValue(
            restCobrancaEmpresaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cobrancaEmpresa)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CobrancaEmpresa.class
        );

        // Validate the CobrancaEmpresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCobrancaEmpresaUpdatableFieldsEquals(returnedCobrancaEmpresa, getPersistedCobrancaEmpresa(returnedCobrancaEmpresa));

        insertedCobrancaEmpresa = returnedCobrancaEmpresa;
    }

    @Test
    @Transactional
    void createCobrancaEmpresaWithExistingId() throws Exception {
        // Create the CobrancaEmpresa with an existing ID
        cobrancaEmpresa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCobrancaEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cobrancaEmpresa)))
            .andExpect(status().isBadRequest());

        // Validate the CobrancaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCobrancaEmpresas() throws Exception {
        // Initialize the database
        insertedCobrancaEmpresa = cobrancaEmpresaRepository.saveAndFlush(cobrancaEmpresa);

        // Get all the cobrancaEmpresaList
        restCobrancaEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cobrancaEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataCobranca").value(hasItem(DEFAULT_DATA_COBRANCA.toString())))
            .andExpect(jsonPath("$.[*].valorPago").value(hasItem(DEFAULT_VALOR_PAGO.doubleValue())))
            .andExpect(jsonPath("$.[*].urlCobranca").value(hasItem(DEFAULT_URL_COBRANCA)))
            .andExpect(jsonPath("$.[*].urlArquivo").value(hasItem(DEFAULT_URL_ARQUIVO)))
            .andExpect(jsonPath("$.[*].valorCobrado").value(hasItem(DEFAULT_VALOR_COBRADO.doubleValue())))
            .andExpect(jsonPath("$.[*].situacaoCobranca").value(hasItem(DEFAULT_SITUACAO_COBRANCA.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCobrancaEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(cobrancaEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCobrancaEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(cobrancaEmpresaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCobrancaEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(cobrancaEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCobrancaEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(cobrancaEmpresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCobrancaEmpresa() throws Exception {
        // Initialize the database
        insertedCobrancaEmpresa = cobrancaEmpresaRepository.saveAndFlush(cobrancaEmpresa);

        // Get the cobrancaEmpresa
        restCobrancaEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, cobrancaEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cobrancaEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.dataCobranca").value(DEFAULT_DATA_COBRANCA.toString()))
            .andExpect(jsonPath("$.valorPago").value(DEFAULT_VALOR_PAGO.doubleValue()))
            .andExpect(jsonPath("$.urlCobranca").value(DEFAULT_URL_COBRANCA))
            .andExpect(jsonPath("$.urlArquivo").value(DEFAULT_URL_ARQUIVO))
            .andExpect(jsonPath("$.valorCobrado").value(DEFAULT_VALOR_COBRADO.doubleValue()))
            .andExpect(jsonPath("$.situacaoCobranca").value(DEFAULT_SITUACAO_COBRANCA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCobrancaEmpresa() throws Exception {
        // Get the cobrancaEmpresa
        restCobrancaEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCobrancaEmpresa() throws Exception {
        // Initialize the database
        insertedCobrancaEmpresa = cobrancaEmpresaRepository.saveAndFlush(cobrancaEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cobrancaEmpresa
        CobrancaEmpresa updatedCobrancaEmpresa = cobrancaEmpresaRepository.findById(cobrancaEmpresa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCobrancaEmpresa are not directly saved in db
        em.detach(updatedCobrancaEmpresa);
        updatedCobrancaEmpresa
            .dataCobranca(UPDATED_DATA_COBRANCA)
            .valorPago(UPDATED_VALOR_PAGO)
            .urlCobranca(UPDATED_URL_COBRANCA)
            .urlArquivo(UPDATED_URL_ARQUIVO)
            .valorCobrado(UPDATED_VALOR_COBRADO)
            .situacaoCobranca(UPDATED_SITUACAO_COBRANCA);

        restCobrancaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCobrancaEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCobrancaEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the CobrancaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCobrancaEmpresaToMatchAllProperties(updatedCobrancaEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingCobrancaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cobrancaEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCobrancaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cobrancaEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cobrancaEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the CobrancaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCobrancaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cobrancaEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCobrancaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cobrancaEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the CobrancaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCobrancaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cobrancaEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCobrancaEmpresaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cobrancaEmpresa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CobrancaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCobrancaEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedCobrancaEmpresa = cobrancaEmpresaRepository.saveAndFlush(cobrancaEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cobrancaEmpresa using partial update
        CobrancaEmpresa partialUpdatedCobrancaEmpresa = new CobrancaEmpresa();
        partialUpdatedCobrancaEmpresa.setId(cobrancaEmpresa.getId());

        partialUpdatedCobrancaEmpresa
            .dataCobranca(UPDATED_DATA_COBRANCA)
            .urlArquivo(UPDATED_URL_ARQUIVO)
            .valorCobrado(UPDATED_VALOR_COBRADO)
            .situacaoCobranca(UPDATED_SITUACAO_COBRANCA);

        restCobrancaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCobrancaEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCobrancaEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the CobrancaEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCobrancaEmpresaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCobrancaEmpresa, cobrancaEmpresa),
            getPersistedCobrancaEmpresa(cobrancaEmpresa)
        );
    }

    @Test
    @Transactional
    void fullUpdateCobrancaEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedCobrancaEmpresa = cobrancaEmpresaRepository.saveAndFlush(cobrancaEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cobrancaEmpresa using partial update
        CobrancaEmpresa partialUpdatedCobrancaEmpresa = new CobrancaEmpresa();
        partialUpdatedCobrancaEmpresa.setId(cobrancaEmpresa.getId());

        partialUpdatedCobrancaEmpresa
            .dataCobranca(UPDATED_DATA_COBRANCA)
            .valorPago(UPDATED_VALOR_PAGO)
            .urlCobranca(UPDATED_URL_COBRANCA)
            .urlArquivo(UPDATED_URL_ARQUIVO)
            .valorCobrado(UPDATED_VALOR_COBRADO)
            .situacaoCobranca(UPDATED_SITUACAO_COBRANCA);

        restCobrancaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCobrancaEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCobrancaEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the CobrancaEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCobrancaEmpresaUpdatableFieldsEquals(
            partialUpdatedCobrancaEmpresa,
            getPersistedCobrancaEmpresa(partialUpdatedCobrancaEmpresa)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCobrancaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cobrancaEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCobrancaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cobrancaEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cobrancaEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the CobrancaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCobrancaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cobrancaEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCobrancaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cobrancaEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the CobrancaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCobrancaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cobrancaEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCobrancaEmpresaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cobrancaEmpresa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CobrancaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCobrancaEmpresa() throws Exception {
        // Initialize the database
        insertedCobrancaEmpresa = cobrancaEmpresaRepository.saveAndFlush(cobrancaEmpresa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cobrancaEmpresa
        restCobrancaEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, cobrancaEmpresa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cobrancaEmpresaRepository.count();
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

    protected CobrancaEmpresa getPersistedCobrancaEmpresa(CobrancaEmpresa cobrancaEmpresa) {
        return cobrancaEmpresaRepository.findById(cobrancaEmpresa.getId()).orElseThrow();
    }

    protected void assertPersistedCobrancaEmpresaToMatchAllProperties(CobrancaEmpresa expectedCobrancaEmpresa) {
        assertCobrancaEmpresaAllPropertiesEquals(expectedCobrancaEmpresa, getPersistedCobrancaEmpresa(expectedCobrancaEmpresa));
    }

    protected void assertPersistedCobrancaEmpresaToMatchUpdatableProperties(CobrancaEmpresa expectedCobrancaEmpresa) {
        assertCobrancaEmpresaAllUpdatablePropertiesEquals(expectedCobrancaEmpresa, getPersistedCobrancaEmpresa(expectedCobrancaEmpresa));
    }
}
