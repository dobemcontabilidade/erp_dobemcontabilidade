package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.ParcelaImpostoAPagarAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.ParcelaImpostoAPagar;
import com.dobemcontabilidade.domain.ParcelamentoImposto;
import com.dobemcontabilidade.domain.enumeration.MesCompetenciaEnum;
import com.dobemcontabilidade.repository.ParcelaImpostoAPagarRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ParcelaImpostoAPagarResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParcelaImpostoAPagarResourceIT {

    private static final Integer DEFAULT_NUMERO_PARCELA = 1;
    private static final Integer UPDATED_NUMERO_PARCELA = 2;

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

    private static final MesCompetenciaEnum DEFAULT_MES_COMPETENCIA = MesCompetenciaEnum.JANEIRO;
    private static final MesCompetenciaEnum UPDATED_MES_COMPETENCIA = MesCompetenciaEnum.FEVEREIRO;

    private static final String ENTITY_API_URL = "/api/parcela-imposto-a-pagars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ParcelaImpostoAPagarRepository parcelaImpostoAPagarRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParcelaImpostoAPagarMockMvc;

    private ParcelaImpostoAPagar parcelaImpostoAPagar;

    private ParcelaImpostoAPagar insertedParcelaImpostoAPagar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParcelaImpostoAPagar createEntity(EntityManager em) {
        ParcelaImpostoAPagar parcelaImpostoAPagar = new ParcelaImpostoAPagar()
            .numeroParcela(DEFAULT_NUMERO_PARCELA)
            .dataVencimento(DEFAULT_DATA_VENCIMENTO)
            .dataPagamento(DEFAULT_DATA_PAGAMENTO)
            .valor(DEFAULT_VALOR)
            .valorMulta(DEFAULT_VALOR_MULTA)
            .urlArquivoPagamento(DEFAULT_URL_ARQUIVO_PAGAMENTO)
            .urlArquivoComprovante(DEFAULT_URL_ARQUIVO_COMPROVANTE)
            .mesCompetencia(DEFAULT_MES_COMPETENCIA);
        // Add required entity
        ParcelamentoImposto parcelamentoImposto;
        if (TestUtil.findAll(em, ParcelamentoImposto.class).isEmpty()) {
            parcelamentoImposto = ParcelamentoImpostoResourceIT.createEntity(em);
            em.persist(parcelamentoImposto);
            em.flush();
        } else {
            parcelamentoImposto = TestUtil.findAll(em, ParcelamentoImposto.class).get(0);
        }
        parcelaImpostoAPagar.setParcelamentoImposto(parcelamentoImposto);
        return parcelaImpostoAPagar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParcelaImpostoAPagar createUpdatedEntity(EntityManager em) {
        ParcelaImpostoAPagar parcelaImpostoAPagar = new ParcelaImpostoAPagar()
            .numeroParcela(UPDATED_NUMERO_PARCELA)
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .dataPagamento(UPDATED_DATA_PAGAMENTO)
            .valor(UPDATED_VALOR)
            .valorMulta(UPDATED_VALOR_MULTA)
            .urlArquivoPagamento(UPDATED_URL_ARQUIVO_PAGAMENTO)
            .urlArquivoComprovante(UPDATED_URL_ARQUIVO_COMPROVANTE)
            .mesCompetencia(UPDATED_MES_COMPETENCIA);
        // Add required entity
        ParcelamentoImposto parcelamentoImposto;
        if (TestUtil.findAll(em, ParcelamentoImposto.class).isEmpty()) {
            parcelamentoImposto = ParcelamentoImpostoResourceIT.createUpdatedEntity(em);
            em.persist(parcelamentoImposto);
            em.flush();
        } else {
            parcelamentoImposto = TestUtil.findAll(em, ParcelamentoImposto.class).get(0);
        }
        parcelaImpostoAPagar.setParcelamentoImposto(parcelamentoImposto);
        return parcelaImpostoAPagar;
    }

    @BeforeEach
    public void initTest() {
        parcelaImpostoAPagar = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedParcelaImpostoAPagar != null) {
            parcelaImpostoAPagarRepository.delete(insertedParcelaImpostoAPagar);
            insertedParcelaImpostoAPagar = null;
        }
    }

    @Test
    @Transactional
    void createParcelaImpostoAPagar() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ParcelaImpostoAPagar
        var returnedParcelaImpostoAPagar = om.readValue(
            restParcelaImpostoAPagarMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parcelaImpostoAPagar)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ParcelaImpostoAPagar.class
        );

        // Validate the ParcelaImpostoAPagar in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertParcelaImpostoAPagarUpdatableFieldsEquals(
            returnedParcelaImpostoAPagar,
            getPersistedParcelaImpostoAPagar(returnedParcelaImpostoAPagar)
        );

        insertedParcelaImpostoAPagar = returnedParcelaImpostoAPagar;
    }

    @Test
    @Transactional
    void createParcelaImpostoAPagarWithExistingId() throws Exception {
        // Create the ParcelaImpostoAPagar with an existing ID
        parcelaImpostoAPagar.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParcelaImpostoAPagarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parcelaImpostoAPagar)))
            .andExpect(status().isBadRequest());

        // Validate the ParcelaImpostoAPagar in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllParcelaImpostoAPagars() throws Exception {
        // Initialize the database
        insertedParcelaImpostoAPagar = parcelaImpostoAPagarRepository.saveAndFlush(parcelaImpostoAPagar);

        // Get all the parcelaImpostoAPagarList
        restParcelaImpostoAPagarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parcelaImpostoAPagar.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroParcela").value(hasItem(DEFAULT_NUMERO_PARCELA)))
            .andExpect(jsonPath("$.[*].dataVencimento").value(hasItem(DEFAULT_DATA_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].dataPagamento").value(hasItem(DEFAULT_DATA_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].valorMulta").value(hasItem(DEFAULT_VALOR_MULTA)))
            .andExpect(jsonPath("$.[*].urlArquivoPagamento").value(hasItem(DEFAULT_URL_ARQUIVO_PAGAMENTO)))
            .andExpect(jsonPath("$.[*].urlArquivoComprovante").value(hasItem(DEFAULT_URL_ARQUIVO_COMPROVANTE)))
            .andExpect(jsonPath("$.[*].mesCompetencia").value(hasItem(DEFAULT_MES_COMPETENCIA.toString())));
    }

    @Test
    @Transactional
    void getParcelaImpostoAPagar() throws Exception {
        // Initialize the database
        insertedParcelaImpostoAPagar = parcelaImpostoAPagarRepository.saveAndFlush(parcelaImpostoAPagar);

        // Get the parcelaImpostoAPagar
        restParcelaImpostoAPagarMockMvc
            .perform(get(ENTITY_API_URL_ID, parcelaImpostoAPagar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parcelaImpostoAPagar.getId().intValue()))
            .andExpect(jsonPath("$.numeroParcela").value(DEFAULT_NUMERO_PARCELA))
            .andExpect(jsonPath("$.dataVencimento").value(DEFAULT_DATA_VENCIMENTO.toString()))
            .andExpect(jsonPath("$.dataPagamento").value(DEFAULT_DATA_PAGAMENTO.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()))
            .andExpect(jsonPath("$.valorMulta").value(DEFAULT_VALOR_MULTA))
            .andExpect(jsonPath("$.urlArquivoPagamento").value(DEFAULT_URL_ARQUIVO_PAGAMENTO))
            .andExpect(jsonPath("$.urlArquivoComprovante").value(DEFAULT_URL_ARQUIVO_COMPROVANTE))
            .andExpect(jsonPath("$.mesCompetencia").value(DEFAULT_MES_COMPETENCIA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingParcelaImpostoAPagar() throws Exception {
        // Get the parcelaImpostoAPagar
        restParcelaImpostoAPagarMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingParcelaImpostoAPagar() throws Exception {
        // Initialize the database
        insertedParcelaImpostoAPagar = parcelaImpostoAPagarRepository.saveAndFlush(parcelaImpostoAPagar);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parcelaImpostoAPagar
        ParcelaImpostoAPagar updatedParcelaImpostoAPagar = parcelaImpostoAPagarRepository
            .findById(parcelaImpostoAPagar.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedParcelaImpostoAPagar are not directly saved in db
        em.detach(updatedParcelaImpostoAPagar);
        updatedParcelaImpostoAPagar
            .numeroParcela(UPDATED_NUMERO_PARCELA)
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .dataPagamento(UPDATED_DATA_PAGAMENTO)
            .valor(UPDATED_VALOR)
            .valorMulta(UPDATED_VALOR_MULTA)
            .urlArquivoPagamento(UPDATED_URL_ARQUIVO_PAGAMENTO)
            .urlArquivoComprovante(UPDATED_URL_ARQUIVO_COMPROVANTE)
            .mesCompetencia(UPDATED_MES_COMPETENCIA);

        restParcelaImpostoAPagarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedParcelaImpostoAPagar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedParcelaImpostoAPagar))
            )
            .andExpect(status().isOk());

        // Validate the ParcelaImpostoAPagar in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedParcelaImpostoAPagarToMatchAllProperties(updatedParcelaImpostoAPagar);
    }

    @Test
    @Transactional
    void putNonExistingParcelaImpostoAPagar() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parcelaImpostoAPagar.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParcelaImpostoAPagarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parcelaImpostoAPagar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(parcelaImpostoAPagar))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParcelaImpostoAPagar in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchParcelaImpostoAPagar() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parcelaImpostoAPagar.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParcelaImpostoAPagarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(parcelaImpostoAPagar))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParcelaImpostoAPagar in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamParcelaImpostoAPagar() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parcelaImpostoAPagar.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParcelaImpostoAPagarMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parcelaImpostoAPagar)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ParcelaImpostoAPagar in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateParcelaImpostoAPagarWithPatch() throws Exception {
        // Initialize the database
        insertedParcelaImpostoAPagar = parcelaImpostoAPagarRepository.saveAndFlush(parcelaImpostoAPagar);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parcelaImpostoAPagar using partial update
        ParcelaImpostoAPagar partialUpdatedParcelaImpostoAPagar = new ParcelaImpostoAPagar();
        partialUpdatedParcelaImpostoAPagar.setId(parcelaImpostoAPagar.getId());

        partialUpdatedParcelaImpostoAPagar
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .dataPagamento(UPDATED_DATA_PAGAMENTO)
            .valor(UPDATED_VALOR)
            .valorMulta(UPDATED_VALOR_MULTA)
            .urlArquivoComprovante(UPDATED_URL_ARQUIVO_COMPROVANTE);

        restParcelaImpostoAPagarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParcelaImpostoAPagar.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedParcelaImpostoAPagar))
            )
            .andExpect(status().isOk());

        // Validate the ParcelaImpostoAPagar in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertParcelaImpostoAPagarUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedParcelaImpostoAPagar, parcelaImpostoAPagar),
            getPersistedParcelaImpostoAPagar(parcelaImpostoAPagar)
        );
    }

    @Test
    @Transactional
    void fullUpdateParcelaImpostoAPagarWithPatch() throws Exception {
        // Initialize the database
        insertedParcelaImpostoAPagar = parcelaImpostoAPagarRepository.saveAndFlush(parcelaImpostoAPagar);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parcelaImpostoAPagar using partial update
        ParcelaImpostoAPagar partialUpdatedParcelaImpostoAPagar = new ParcelaImpostoAPagar();
        partialUpdatedParcelaImpostoAPagar.setId(parcelaImpostoAPagar.getId());

        partialUpdatedParcelaImpostoAPagar
            .numeroParcela(UPDATED_NUMERO_PARCELA)
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .dataPagamento(UPDATED_DATA_PAGAMENTO)
            .valor(UPDATED_VALOR)
            .valorMulta(UPDATED_VALOR_MULTA)
            .urlArquivoPagamento(UPDATED_URL_ARQUIVO_PAGAMENTO)
            .urlArquivoComprovante(UPDATED_URL_ARQUIVO_COMPROVANTE)
            .mesCompetencia(UPDATED_MES_COMPETENCIA);

        restParcelaImpostoAPagarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParcelaImpostoAPagar.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedParcelaImpostoAPagar))
            )
            .andExpect(status().isOk());

        // Validate the ParcelaImpostoAPagar in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertParcelaImpostoAPagarUpdatableFieldsEquals(
            partialUpdatedParcelaImpostoAPagar,
            getPersistedParcelaImpostoAPagar(partialUpdatedParcelaImpostoAPagar)
        );
    }

    @Test
    @Transactional
    void patchNonExistingParcelaImpostoAPagar() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parcelaImpostoAPagar.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParcelaImpostoAPagarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, parcelaImpostoAPagar.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(parcelaImpostoAPagar))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParcelaImpostoAPagar in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchParcelaImpostoAPagar() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parcelaImpostoAPagar.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParcelaImpostoAPagarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(parcelaImpostoAPagar))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParcelaImpostoAPagar in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamParcelaImpostoAPagar() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parcelaImpostoAPagar.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParcelaImpostoAPagarMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(parcelaImpostoAPagar)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ParcelaImpostoAPagar in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteParcelaImpostoAPagar() throws Exception {
        // Initialize the database
        insertedParcelaImpostoAPagar = parcelaImpostoAPagarRepository.saveAndFlush(parcelaImpostoAPagar);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the parcelaImpostoAPagar
        restParcelaImpostoAPagarMockMvc
            .perform(delete(ENTITY_API_URL_ID, parcelaImpostoAPagar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return parcelaImpostoAPagarRepository.count();
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

    protected ParcelaImpostoAPagar getPersistedParcelaImpostoAPagar(ParcelaImpostoAPagar parcelaImpostoAPagar) {
        return parcelaImpostoAPagarRepository.findById(parcelaImpostoAPagar.getId()).orElseThrow();
    }

    protected void assertPersistedParcelaImpostoAPagarToMatchAllProperties(ParcelaImpostoAPagar expectedParcelaImpostoAPagar) {
        assertParcelaImpostoAPagarAllPropertiesEquals(
            expectedParcelaImpostoAPagar,
            getPersistedParcelaImpostoAPagar(expectedParcelaImpostoAPagar)
        );
    }

    protected void assertPersistedParcelaImpostoAPagarToMatchUpdatableProperties(ParcelaImpostoAPagar expectedParcelaImpostoAPagar) {
        assertParcelaImpostoAPagarAllUpdatablePropertiesEquals(
            expectedParcelaImpostoAPagar,
            getPersistedParcelaImpostoAPagar(expectedParcelaImpostoAPagar)
        );
    }
}
