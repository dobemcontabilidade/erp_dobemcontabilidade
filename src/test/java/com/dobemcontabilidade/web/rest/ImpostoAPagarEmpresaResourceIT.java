package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.ImpostoAPagarEmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.ImpostoAPagarEmpresa;
import com.dobemcontabilidade.domain.ImpostoEmpresa;
import com.dobemcontabilidade.domain.enumeration.SituacaoPagamentoImpostoEnum;
import com.dobemcontabilidade.repository.ImpostoAPagarEmpresaRepository;
import com.dobemcontabilidade.service.ImpostoAPagarEmpresaService;
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
 * Integration tests for the {@link ImpostoAPagarEmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ImpostoAPagarEmpresaResourceIT {

    private static final Instant DEFAULT_DATA_VENCIMENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_VENCIMENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_PAGAMENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_PAGAMENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_VALOR = 1D;
    private static final Double UPDATED_VALOR = 2D;

    private static final Integer DEFAULT_VALOR_MULTA = 1;
    private static final Integer UPDATED_VALOR_MULTA = 2;

    private static final String DEFAULT_URL_ARQUIVO_PAGAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_URL_ARQUIVO_PAGAMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_URL_ARQUIVO_COMPROVANTE = "AAAAAAAAAA";
    private static final String UPDATED_URL_ARQUIVO_COMPROVANTE = "BBBBBBBBBB";

    private static final SituacaoPagamentoImpostoEnum DEFAULT_SITUACAO_PAGAMENTO_IMPOSTO_ENUM = SituacaoPagamentoImpostoEnum.ABERTO;
    private static final SituacaoPagamentoImpostoEnum UPDATED_SITUACAO_PAGAMENTO_IMPOSTO_ENUM = SituacaoPagamentoImpostoEnum.VENCIDO;

    private static final String ENTITY_API_URL = "/api/imposto-a-pagar-empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ImpostoAPagarEmpresaRepository impostoAPagarEmpresaRepository;

    @Mock
    private ImpostoAPagarEmpresaRepository impostoAPagarEmpresaRepositoryMock;

    @Mock
    private ImpostoAPagarEmpresaService impostoAPagarEmpresaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restImpostoAPagarEmpresaMockMvc;

    private ImpostoAPagarEmpresa impostoAPagarEmpresa;

    private ImpostoAPagarEmpresa insertedImpostoAPagarEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImpostoAPagarEmpresa createEntity(EntityManager em) {
        ImpostoAPagarEmpresa impostoAPagarEmpresa = new ImpostoAPagarEmpresa()
            .dataVencimento(DEFAULT_DATA_VENCIMENTO)
            .dataPagamento(DEFAULT_DATA_PAGAMENTO)
            .valor(DEFAULT_VALOR)
            .valorMulta(DEFAULT_VALOR_MULTA)
            .urlArquivoPagamento(DEFAULT_URL_ARQUIVO_PAGAMENTO)
            .urlArquivoComprovante(DEFAULT_URL_ARQUIVO_COMPROVANTE)
            .situacaoPagamentoImpostoEnum(DEFAULT_SITUACAO_PAGAMENTO_IMPOSTO_ENUM);
        // Add required entity
        ImpostoEmpresa impostoEmpresa;
        if (TestUtil.findAll(em, ImpostoEmpresa.class).isEmpty()) {
            impostoEmpresa = ImpostoEmpresaResourceIT.createEntity(em);
            em.persist(impostoEmpresa);
            em.flush();
        } else {
            impostoEmpresa = TestUtil.findAll(em, ImpostoEmpresa.class).get(0);
        }
        impostoAPagarEmpresa.setImposto(impostoEmpresa);
        return impostoAPagarEmpresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImpostoAPagarEmpresa createUpdatedEntity(EntityManager em) {
        ImpostoAPagarEmpresa impostoAPagarEmpresa = new ImpostoAPagarEmpresa()
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .dataPagamento(UPDATED_DATA_PAGAMENTO)
            .valor(UPDATED_VALOR)
            .valorMulta(UPDATED_VALOR_MULTA)
            .urlArquivoPagamento(UPDATED_URL_ARQUIVO_PAGAMENTO)
            .urlArquivoComprovante(UPDATED_URL_ARQUIVO_COMPROVANTE)
            .situacaoPagamentoImpostoEnum(UPDATED_SITUACAO_PAGAMENTO_IMPOSTO_ENUM);
        // Add required entity
        ImpostoEmpresa impostoEmpresa;
        if (TestUtil.findAll(em, ImpostoEmpresa.class).isEmpty()) {
            impostoEmpresa = ImpostoEmpresaResourceIT.createUpdatedEntity(em);
            em.persist(impostoEmpresa);
            em.flush();
        } else {
            impostoEmpresa = TestUtil.findAll(em, ImpostoEmpresa.class).get(0);
        }
        impostoAPagarEmpresa.setImposto(impostoEmpresa);
        return impostoAPagarEmpresa;
    }

    @BeforeEach
    public void initTest() {
        impostoAPagarEmpresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedImpostoAPagarEmpresa != null) {
            impostoAPagarEmpresaRepository.delete(insertedImpostoAPagarEmpresa);
            insertedImpostoAPagarEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createImpostoAPagarEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ImpostoAPagarEmpresa
        var returnedImpostoAPagarEmpresa = om.readValue(
            restImpostoAPagarEmpresaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(impostoAPagarEmpresa)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ImpostoAPagarEmpresa.class
        );

        // Validate the ImpostoAPagarEmpresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertImpostoAPagarEmpresaUpdatableFieldsEquals(
            returnedImpostoAPagarEmpresa,
            getPersistedImpostoAPagarEmpresa(returnedImpostoAPagarEmpresa)
        );

        insertedImpostoAPagarEmpresa = returnedImpostoAPagarEmpresa;
    }

    @Test
    @Transactional
    void createImpostoAPagarEmpresaWithExistingId() throws Exception {
        // Create the ImpostoAPagarEmpresa with an existing ID
        impostoAPagarEmpresa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restImpostoAPagarEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(impostoAPagarEmpresa)))
            .andExpect(status().isBadRequest());

        // Validate the ImpostoAPagarEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDataVencimentoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        impostoAPagarEmpresa.setDataVencimento(null);

        // Create the ImpostoAPagarEmpresa, which fails.

        restImpostoAPagarEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(impostoAPagarEmpresa)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDataPagamentoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        impostoAPagarEmpresa.setDataPagamento(null);

        // Create the ImpostoAPagarEmpresa, which fails.

        restImpostoAPagarEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(impostoAPagarEmpresa)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllImpostoAPagarEmpresas() throws Exception {
        // Initialize the database
        insertedImpostoAPagarEmpresa = impostoAPagarEmpresaRepository.saveAndFlush(impostoAPagarEmpresa);

        // Get all the impostoAPagarEmpresaList
        restImpostoAPagarEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(impostoAPagarEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataVencimento").value(hasItem(DEFAULT_DATA_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].dataPagamento").value(hasItem(DEFAULT_DATA_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].valorMulta").value(hasItem(DEFAULT_VALOR_MULTA)))
            .andExpect(jsonPath("$.[*].urlArquivoPagamento").value(hasItem(DEFAULT_URL_ARQUIVO_PAGAMENTO)))
            .andExpect(jsonPath("$.[*].urlArquivoComprovante").value(hasItem(DEFAULT_URL_ARQUIVO_COMPROVANTE)))
            .andExpect(jsonPath("$.[*].situacaoPagamentoImpostoEnum").value(hasItem(DEFAULT_SITUACAO_PAGAMENTO_IMPOSTO_ENUM.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllImpostoAPagarEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(impostoAPagarEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restImpostoAPagarEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(impostoAPagarEmpresaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllImpostoAPagarEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(impostoAPagarEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restImpostoAPagarEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(impostoAPagarEmpresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getImpostoAPagarEmpresa() throws Exception {
        // Initialize the database
        insertedImpostoAPagarEmpresa = impostoAPagarEmpresaRepository.saveAndFlush(impostoAPagarEmpresa);

        // Get the impostoAPagarEmpresa
        restImpostoAPagarEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, impostoAPagarEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(impostoAPagarEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.dataVencimento").value(DEFAULT_DATA_VENCIMENTO.toString()))
            .andExpect(jsonPath("$.dataPagamento").value(DEFAULT_DATA_PAGAMENTO.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()))
            .andExpect(jsonPath("$.valorMulta").value(DEFAULT_VALOR_MULTA))
            .andExpect(jsonPath("$.urlArquivoPagamento").value(DEFAULT_URL_ARQUIVO_PAGAMENTO))
            .andExpect(jsonPath("$.urlArquivoComprovante").value(DEFAULT_URL_ARQUIVO_COMPROVANTE))
            .andExpect(jsonPath("$.situacaoPagamentoImpostoEnum").value(DEFAULT_SITUACAO_PAGAMENTO_IMPOSTO_ENUM.toString()));
    }

    @Test
    @Transactional
    void getNonExistingImpostoAPagarEmpresa() throws Exception {
        // Get the impostoAPagarEmpresa
        restImpostoAPagarEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingImpostoAPagarEmpresa() throws Exception {
        // Initialize the database
        insertedImpostoAPagarEmpresa = impostoAPagarEmpresaRepository.saveAndFlush(impostoAPagarEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the impostoAPagarEmpresa
        ImpostoAPagarEmpresa updatedImpostoAPagarEmpresa = impostoAPagarEmpresaRepository
            .findById(impostoAPagarEmpresa.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedImpostoAPagarEmpresa are not directly saved in db
        em.detach(updatedImpostoAPagarEmpresa);
        updatedImpostoAPagarEmpresa
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .dataPagamento(UPDATED_DATA_PAGAMENTO)
            .valor(UPDATED_VALOR)
            .valorMulta(UPDATED_VALOR_MULTA)
            .urlArquivoPagamento(UPDATED_URL_ARQUIVO_PAGAMENTO)
            .urlArquivoComprovante(UPDATED_URL_ARQUIVO_COMPROVANTE)
            .situacaoPagamentoImpostoEnum(UPDATED_SITUACAO_PAGAMENTO_IMPOSTO_ENUM);

        restImpostoAPagarEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedImpostoAPagarEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedImpostoAPagarEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the ImpostoAPagarEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedImpostoAPagarEmpresaToMatchAllProperties(updatedImpostoAPagarEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingImpostoAPagarEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impostoAPagarEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImpostoAPagarEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, impostoAPagarEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(impostoAPagarEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImpostoAPagarEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchImpostoAPagarEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impostoAPagarEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImpostoAPagarEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(impostoAPagarEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImpostoAPagarEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamImpostoAPagarEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impostoAPagarEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImpostoAPagarEmpresaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(impostoAPagarEmpresa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ImpostoAPagarEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateImpostoAPagarEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedImpostoAPagarEmpresa = impostoAPagarEmpresaRepository.saveAndFlush(impostoAPagarEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the impostoAPagarEmpresa using partial update
        ImpostoAPagarEmpresa partialUpdatedImpostoAPagarEmpresa = new ImpostoAPagarEmpresa();
        partialUpdatedImpostoAPagarEmpresa.setId(impostoAPagarEmpresa.getId());

        partialUpdatedImpostoAPagarEmpresa
            .dataPagamento(UPDATED_DATA_PAGAMENTO)
            .valor(UPDATED_VALOR)
            .urlArquivoPagamento(UPDATED_URL_ARQUIVO_PAGAMENTO)
            .situacaoPagamentoImpostoEnum(UPDATED_SITUACAO_PAGAMENTO_IMPOSTO_ENUM);

        restImpostoAPagarEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImpostoAPagarEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedImpostoAPagarEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the ImpostoAPagarEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertImpostoAPagarEmpresaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedImpostoAPagarEmpresa, impostoAPagarEmpresa),
            getPersistedImpostoAPagarEmpresa(impostoAPagarEmpresa)
        );
    }

    @Test
    @Transactional
    void fullUpdateImpostoAPagarEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedImpostoAPagarEmpresa = impostoAPagarEmpresaRepository.saveAndFlush(impostoAPagarEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the impostoAPagarEmpresa using partial update
        ImpostoAPagarEmpresa partialUpdatedImpostoAPagarEmpresa = new ImpostoAPagarEmpresa();
        partialUpdatedImpostoAPagarEmpresa.setId(impostoAPagarEmpresa.getId());

        partialUpdatedImpostoAPagarEmpresa
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .dataPagamento(UPDATED_DATA_PAGAMENTO)
            .valor(UPDATED_VALOR)
            .valorMulta(UPDATED_VALOR_MULTA)
            .urlArquivoPagamento(UPDATED_URL_ARQUIVO_PAGAMENTO)
            .urlArquivoComprovante(UPDATED_URL_ARQUIVO_COMPROVANTE)
            .situacaoPagamentoImpostoEnum(UPDATED_SITUACAO_PAGAMENTO_IMPOSTO_ENUM);

        restImpostoAPagarEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImpostoAPagarEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedImpostoAPagarEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the ImpostoAPagarEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertImpostoAPagarEmpresaUpdatableFieldsEquals(
            partialUpdatedImpostoAPagarEmpresa,
            getPersistedImpostoAPagarEmpresa(partialUpdatedImpostoAPagarEmpresa)
        );
    }

    @Test
    @Transactional
    void patchNonExistingImpostoAPagarEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impostoAPagarEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImpostoAPagarEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, impostoAPagarEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(impostoAPagarEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImpostoAPagarEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchImpostoAPagarEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impostoAPagarEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImpostoAPagarEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(impostoAPagarEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImpostoAPagarEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamImpostoAPagarEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impostoAPagarEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImpostoAPagarEmpresaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(impostoAPagarEmpresa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ImpostoAPagarEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteImpostoAPagarEmpresa() throws Exception {
        // Initialize the database
        insertedImpostoAPagarEmpresa = impostoAPagarEmpresaRepository.saveAndFlush(impostoAPagarEmpresa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the impostoAPagarEmpresa
        restImpostoAPagarEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, impostoAPagarEmpresa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return impostoAPagarEmpresaRepository.count();
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

    protected ImpostoAPagarEmpresa getPersistedImpostoAPagarEmpresa(ImpostoAPagarEmpresa impostoAPagarEmpresa) {
        return impostoAPagarEmpresaRepository.findById(impostoAPagarEmpresa.getId()).orElseThrow();
    }

    protected void assertPersistedImpostoAPagarEmpresaToMatchAllProperties(ImpostoAPagarEmpresa expectedImpostoAPagarEmpresa) {
        assertImpostoAPagarEmpresaAllPropertiesEquals(
            expectedImpostoAPagarEmpresa,
            getPersistedImpostoAPagarEmpresa(expectedImpostoAPagarEmpresa)
        );
    }

    protected void assertPersistedImpostoAPagarEmpresaToMatchUpdatableProperties(ImpostoAPagarEmpresa expectedImpostoAPagarEmpresa) {
        assertImpostoAPagarEmpresaAllUpdatablePropertiesEquals(
            expectedImpostoAPagarEmpresa,
            getPersistedImpostoAPagarEmpresa(expectedImpostoAPagarEmpresa)
        );
    }
}
