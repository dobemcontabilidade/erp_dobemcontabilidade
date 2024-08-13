package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.ParcelamentoImpostoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.domain.Imposto;
import com.dobemcontabilidade.domain.ParcelamentoImposto;
import com.dobemcontabilidade.domain.enumeration.SituacaoSolicitacaoParcelamentoEnum;
import com.dobemcontabilidade.repository.ParcelamentoImpostoRepository;
import com.dobemcontabilidade.service.ParcelamentoImpostoService;
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
 * Integration tests for the {@link ParcelamentoImpostoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ParcelamentoImpostoResourceIT {

    private static final Integer DEFAULT_DIA_VENCIMENTO = 1;
    private static final Integer UPDATED_DIA_VENCIMENTO = 2;

    private static final Integer DEFAULT_NUMERO_PARCELAS = 1;
    private static final Integer UPDATED_NUMERO_PARCELAS = 2;

    private static final String DEFAULT_URL_ARQUIVO_NEGOCIACAO = "AAAAAAAAAA";
    private static final String UPDATED_URL_ARQUIVO_NEGOCIACAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_PARCELAS_PAGAS = 1;
    private static final Integer UPDATED_NUMERO_PARCELAS_PAGAS = 2;

    private static final Integer DEFAULT_NUMERO_PARCELAS_REGATANTES = 1;
    private static final Integer UPDATED_NUMERO_PARCELAS_REGATANTES = 2;

    private static final SituacaoSolicitacaoParcelamentoEnum DEFAULT_SITUACAO_SOLICITACAO_PARCELAMENTO_ENUM =
        SituacaoSolicitacaoParcelamentoEnum.ABERTA;
    private static final SituacaoSolicitacaoParcelamentoEnum UPDATED_SITUACAO_SOLICITACAO_PARCELAMENTO_ENUM =
        SituacaoSolicitacaoParcelamentoEnum.PROCESSADA;

    private static final String ENTITY_API_URL = "/api/parcelamento-impostos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ParcelamentoImpostoRepository parcelamentoImpostoRepository;

    @Mock
    private ParcelamentoImpostoRepository parcelamentoImpostoRepositoryMock;

    @Mock
    private ParcelamentoImpostoService parcelamentoImpostoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParcelamentoImpostoMockMvc;

    private ParcelamentoImposto parcelamentoImposto;

    private ParcelamentoImposto insertedParcelamentoImposto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParcelamentoImposto createEntity(EntityManager em) {
        ParcelamentoImposto parcelamentoImposto = new ParcelamentoImposto()
            .diaVencimento(DEFAULT_DIA_VENCIMENTO)
            .numeroParcelas(DEFAULT_NUMERO_PARCELAS)
            .urlArquivoNegociacao(DEFAULT_URL_ARQUIVO_NEGOCIACAO)
            .numeroParcelasPagas(DEFAULT_NUMERO_PARCELAS_PAGAS)
            .numeroParcelasRegatantes(DEFAULT_NUMERO_PARCELAS_REGATANTES)
            .situacaoSolicitacaoParcelamentoEnum(DEFAULT_SITUACAO_SOLICITACAO_PARCELAMENTO_ENUM);
        // Add required entity
        Imposto imposto;
        if (TestUtil.findAll(em, Imposto.class).isEmpty()) {
            imposto = ImpostoResourceIT.createEntity(em);
            em.persist(imposto);
            em.flush();
        } else {
            imposto = TestUtil.findAll(em, Imposto.class).get(0);
        }
        parcelamentoImposto.setImposto(imposto);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        parcelamentoImposto.setEmpresa(empresa);
        return parcelamentoImposto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParcelamentoImposto createUpdatedEntity(EntityManager em) {
        ParcelamentoImposto parcelamentoImposto = new ParcelamentoImposto()
            .diaVencimento(UPDATED_DIA_VENCIMENTO)
            .numeroParcelas(UPDATED_NUMERO_PARCELAS)
            .urlArquivoNegociacao(UPDATED_URL_ARQUIVO_NEGOCIACAO)
            .numeroParcelasPagas(UPDATED_NUMERO_PARCELAS_PAGAS)
            .numeroParcelasRegatantes(UPDATED_NUMERO_PARCELAS_REGATANTES)
            .situacaoSolicitacaoParcelamentoEnum(UPDATED_SITUACAO_SOLICITACAO_PARCELAMENTO_ENUM);
        // Add required entity
        Imposto imposto;
        if (TestUtil.findAll(em, Imposto.class).isEmpty()) {
            imposto = ImpostoResourceIT.createUpdatedEntity(em);
            em.persist(imposto);
            em.flush();
        } else {
            imposto = TestUtil.findAll(em, Imposto.class).get(0);
        }
        parcelamentoImposto.setImposto(imposto);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createUpdatedEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        parcelamentoImposto.setEmpresa(empresa);
        return parcelamentoImposto;
    }

    @BeforeEach
    public void initTest() {
        parcelamentoImposto = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedParcelamentoImposto != null) {
            parcelamentoImpostoRepository.delete(insertedParcelamentoImposto);
            insertedParcelamentoImposto = null;
        }
    }

    @Test
    @Transactional
    void createParcelamentoImposto() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ParcelamentoImposto
        var returnedParcelamentoImposto = om.readValue(
            restParcelamentoImpostoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parcelamentoImposto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ParcelamentoImposto.class
        );

        // Validate the ParcelamentoImposto in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertParcelamentoImpostoUpdatableFieldsEquals(
            returnedParcelamentoImposto,
            getPersistedParcelamentoImposto(returnedParcelamentoImposto)
        );

        insertedParcelamentoImposto = returnedParcelamentoImposto;
    }

    @Test
    @Transactional
    void createParcelamentoImpostoWithExistingId() throws Exception {
        // Create the ParcelamentoImposto with an existing ID
        parcelamentoImposto.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParcelamentoImpostoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parcelamentoImposto)))
            .andExpect(status().isBadRequest());

        // Validate the ParcelamentoImposto in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllParcelamentoImpostos() throws Exception {
        // Initialize the database
        insertedParcelamentoImposto = parcelamentoImpostoRepository.saveAndFlush(parcelamentoImposto);

        // Get all the parcelamentoImpostoList
        restParcelamentoImpostoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parcelamentoImposto.getId().intValue())))
            .andExpect(jsonPath("$.[*].diaVencimento").value(hasItem(DEFAULT_DIA_VENCIMENTO)))
            .andExpect(jsonPath("$.[*].numeroParcelas").value(hasItem(DEFAULT_NUMERO_PARCELAS)))
            .andExpect(jsonPath("$.[*].urlArquivoNegociacao").value(hasItem(DEFAULT_URL_ARQUIVO_NEGOCIACAO)))
            .andExpect(jsonPath("$.[*].numeroParcelasPagas").value(hasItem(DEFAULT_NUMERO_PARCELAS_PAGAS)))
            .andExpect(jsonPath("$.[*].numeroParcelasRegatantes").value(hasItem(DEFAULT_NUMERO_PARCELAS_REGATANTES)))
            .andExpect(
                jsonPath("$.[*].situacaoSolicitacaoParcelamentoEnum").value(
                    hasItem(DEFAULT_SITUACAO_SOLICITACAO_PARCELAMENTO_ENUM.toString())
                )
            );
    }

    @SuppressWarnings({ "unchecked" })
    void getAllParcelamentoImpostosWithEagerRelationshipsIsEnabled() throws Exception {
        when(parcelamentoImpostoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restParcelamentoImpostoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(parcelamentoImpostoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllParcelamentoImpostosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(parcelamentoImpostoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restParcelamentoImpostoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(parcelamentoImpostoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getParcelamentoImposto() throws Exception {
        // Initialize the database
        insertedParcelamentoImposto = parcelamentoImpostoRepository.saveAndFlush(parcelamentoImposto);

        // Get the parcelamentoImposto
        restParcelamentoImpostoMockMvc
            .perform(get(ENTITY_API_URL_ID, parcelamentoImposto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parcelamentoImposto.getId().intValue()))
            .andExpect(jsonPath("$.diaVencimento").value(DEFAULT_DIA_VENCIMENTO))
            .andExpect(jsonPath("$.numeroParcelas").value(DEFAULT_NUMERO_PARCELAS))
            .andExpect(jsonPath("$.urlArquivoNegociacao").value(DEFAULT_URL_ARQUIVO_NEGOCIACAO))
            .andExpect(jsonPath("$.numeroParcelasPagas").value(DEFAULT_NUMERO_PARCELAS_PAGAS))
            .andExpect(jsonPath("$.numeroParcelasRegatantes").value(DEFAULT_NUMERO_PARCELAS_REGATANTES))
            .andExpect(jsonPath("$.situacaoSolicitacaoParcelamentoEnum").value(DEFAULT_SITUACAO_SOLICITACAO_PARCELAMENTO_ENUM.toString()));
    }

    @Test
    @Transactional
    void getNonExistingParcelamentoImposto() throws Exception {
        // Get the parcelamentoImposto
        restParcelamentoImpostoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingParcelamentoImposto() throws Exception {
        // Initialize the database
        insertedParcelamentoImposto = parcelamentoImpostoRepository.saveAndFlush(parcelamentoImposto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parcelamentoImposto
        ParcelamentoImposto updatedParcelamentoImposto = parcelamentoImpostoRepository.findById(parcelamentoImposto.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedParcelamentoImposto are not directly saved in db
        em.detach(updatedParcelamentoImposto);
        updatedParcelamentoImposto
            .diaVencimento(UPDATED_DIA_VENCIMENTO)
            .numeroParcelas(UPDATED_NUMERO_PARCELAS)
            .urlArquivoNegociacao(UPDATED_URL_ARQUIVO_NEGOCIACAO)
            .numeroParcelasPagas(UPDATED_NUMERO_PARCELAS_PAGAS)
            .numeroParcelasRegatantes(UPDATED_NUMERO_PARCELAS_REGATANTES)
            .situacaoSolicitacaoParcelamentoEnum(UPDATED_SITUACAO_SOLICITACAO_PARCELAMENTO_ENUM);

        restParcelamentoImpostoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedParcelamentoImposto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedParcelamentoImposto))
            )
            .andExpect(status().isOk());

        // Validate the ParcelamentoImposto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedParcelamentoImpostoToMatchAllProperties(updatedParcelamentoImposto);
    }

    @Test
    @Transactional
    void putNonExistingParcelamentoImposto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parcelamentoImposto.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParcelamentoImpostoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parcelamentoImposto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(parcelamentoImposto))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParcelamentoImposto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchParcelamentoImposto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parcelamentoImposto.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParcelamentoImpostoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(parcelamentoImposto))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParcelamentoImposto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamParcelamentoImposto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parcelamentoImposto.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParcelamentoImpostoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parcelamentoImposto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ParcelamentoImposto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateParcelamentoImpostoWithPatch() throws Exception {
        // Initialize the database
        insertedParcelamentoImposto = parcelamentoImpostoRepository.saveAndFlush(parcelamentoImposto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parcelamentoImposto using partial update
        ParcelamentoImposto partialUpdatedParcelamentoImposto = new ParcelamentoImposto();
        partialUpdatedParcelamentoImposto.setId(parcelamentoImposto.getId());

        partialUpdatedParcelamentoImposto.diaVencimento(UPDATED_DIA_VENCIMENTO);

        restParcelamentoImpostoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParcelamentoImposto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedParcelamentoImposto))
            )
            .andExpect(status().isOk());

        // Validate the ParcelamentoImposto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertParcelamentoImpostoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedParcelamentoImposto, parcelamentoImposto),
            getPersistedParcelamentoImposto(parcelamentoImposto)
        );
    }

    @Test
    @Transactional
    void fullUpdateParcelamentoImpostoWithPatch() throws Exception {
        // Initialize the database
        insertedParcelamentoImposto = parcelamentoImpostoRepository.saveAndFlush(parcelamentoImposto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parcelamentoImposto using partial update
        ParcelamentoImposto partialUpdatedParcelamentoImposto = new ParcelamentoImposto();
        partialUpdatedParcelamentoImposto.setId(parcelamentoImposto.getId());

        partialUpdatedParcelamentoImposto
            .diaVencimento(UPDATED_DIA_VENCIMENTO)
            .numeroParcelas(UPDATED_NUMERO_PARCELAS)
            .urlArquivoNegociacao(UPDATED_URL_ARQUIVO_NEGOCIACAO)
            .numeroParcelasPagas(UPDATED_NUMERO_PARCELAS_PAGAS)
            .numeroParcelasRegatantes(UPDATED_NUMERO_PARCELAS_REGATANTES)
            .situacaoSolicitacaoParcelamentoEnum(UPDATED_SITUACAO_SOLICITACAO_PARCELAMENTO_ENUM);

        restParcelamentoImpostoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParcelamentoImposto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedParcelamentoImposto))
            )
            .andExpect(status().isOk());

        // Validate the ParcelamentoImposto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertParcelamentoImpostoUpdatableFieldsEquals(
            partialUpdatedParcelamentoImposto,
            getPersistedParcelamentoImposto(partialUpdatedParcelamentoImposto)
        );
    }

    @Test
    @Transactional
    void patchNonExistingParcelamentoImposto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parcelamentoImposto.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParcelamentoImpostoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, parcelamentoImposto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(parcelamentoImposto))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParcelamentoImposto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchParcelamentoImposto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parcelamentoImposto.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParcelamentoImpostoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(parcelamentoImposto))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParcelamentoImposto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamParcelamentoImposto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parcelamentoImposto.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParcelamentoImpostoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(parcelamentoImposto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ParcelamentoImposto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteParcelamentoImposto() throws Exception {
        // Initialize the database
        insertedParcelamentoImposto = parcelamentoImpostoRepository.saveAndFlush(parcelamentoImposto);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the parcelamentoImposto
        restParcelamentoImpostoMockMvc
            .perform(delete(ENTITY_API_URL_ID, parcelamentoImposto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return parcelamentoImpostoRepository.count();
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

    protected ParcelamentoImposto getPersistedParcelamentoImposto(ParcelamentoImposto parcelamentoImposto) {
        return parcelamentoImpostoRepository.findById(parcelamentoImposto.getId()).orElseThrow();
    }

    protected void assertPersistedParcelamentoImpostoToMatchAllProperties(ParcelamentoImposto expectedParcelamentoImposto) {
        assertParcelamentoImpostoAllPropertiesEquals(
            expectedParcelamentoImposto,
            getPersistedParcelamentoImposto(expectedParcelamentoImposto)
        );
    }

    protected void assertPersistedParcelamentoImpostoToMatchUpdatableProperties(ParcelamentoImposto expectedParcelamentoImposto) {
        assertParcelamentoImpostoAllUpdatablePropertiesEquals(
            expectedParcelamentoImposto,
            getPersistedParcelamentoImposto(expectedParcelamentoImposto)
        );
    }
}
