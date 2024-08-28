package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.FormaDePagamentoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.FormaDePagamento;
import com.dobemcontabilidade.repository.FormaDePagamentoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link FormaDePagamentoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FormaDePagamentoResourceIT {

    private static final String DEFAULT_FORMA = "AAAAAAAAAA";
    private static final String UPDATED_FORMA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DISPONIVEL = false;
    private static final Boolean UPDATED_DISPONIVEL = true;

    private static final String ENTITY_API_URL = "/api/forma-de-pagamentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FormaDePagamentoRepository formaDePagamentoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFormaDePagamentoMockMvc;

    private FormaDePagamento formaDePagamento;

    private FormaDePagamento insertedFormaDePagamento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormaDePagamento createEntity(EntityManager em) {
        FormaDePagamento formaDePagamento = new FormaDePagamento()
            .forma(DEFAULT_FORMA)
            .descricao(DEFAULT_DESCRICAO)
            .disponivel(DEFAULT_DISPONIVEL);
        return formaDePagamento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormaDePagamento createUpdatedEntity(EntityManager em) {
        FormaDePagamento formaDePagamento = new FormaDePagamento()
            .forma(UPDATED_FORMA)
            .descricao(UPDATED_DESCRICAO)
            .disponivel(UPDATED_DISPONIVEL);
        return formaDePagamento;
    }

    @BeforeEach
    public void initTest() {
        formaDePagamento = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedFormaDePagamento != null) {
            formaDePagamentoRepository.delete(insertedFormaDePagamento);
            insertedFormaDePagamento = null;
        }
    }

    @Test
    @Transactional
    void createFormaDePagamento() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FormaDePagamento
        var returnedFormaDePagamento = om.readValue(
            restFormaDePagamentoMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(formaDePagamento))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FormaDePagamento.class
        );

        // Validate the FormaDePagamento in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFormaDePagamentoUpdatableFieldsEquals(returnedFormaDePagamento, getPersistedFormaDePagamento(returnedFormaDePagamento));

        insertedFormaDePagamento = returnedFormaDePagamento;
    }

    @Test
    @Transactional
    void createFormaDePagamentoWithExistingId() throws Exception {
        // Create the FormaDePagamento with an existing ID
        formaDePagamento.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormaDePagamentoMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formaDePagamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormaDePagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFormaDePagamentos() throws Exception {
        // Initialize the database
        insertedFormaDePagamento = formaDePagamentoRepository.saveAndFlush(formaDePagamento);

        // Get all the formaDePagamentoList
        restFormaDePagamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formaDePagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].forma").value(hasItem(DEFAULT_FORMA)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].disponivel").value(hasItem(DEFAULT_DISPONIVEL.booleanValue())));
    }

    @Test
    @Transactional
    void getFormaDePagamento() throws Exception {
        // Initialize the database
        insertedFormaDePagamento = formaDePagamentoRepository.saveAndFlush(formaDePagamento);

        // Get the formaDePagamento
        restFormaDePagamentoMockMvc
            .perform(get(ENTITY_API_URL_ID, formaDePagamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formaDePagamento.getId().intValue()))
            .andExpect(jsonPath("$.forma").value(DEFAULT_FORMA))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.disponivel").value(DEFAULT_DISPONIVEL.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingFormaDePagamento() throws Exception {
        // Get the formaDePagamento
        restFormaDePagamentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFormaDePagamento() throws Exception {
        // Initialize the database
        insertedFormaDePagamento = formaDePagamentoRepository.saveAndFlush(formaDePagamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formaDePagamento
        FormaDePagamento updatedFormaDePagamento = formaDePagamentoRepository.findById(formaDePagamento.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFormaDePagamento are not directly saved in db
        em.detach(updatedFormaDePagamento);
        updatedFormaDePagamento.forma(UPDATED_FORMA).descricao(UPDATED_DESCRICAO).disponivel(UPDATED_DISPONIVEL);

        restFormaDePagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFormaDePagamento.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFormaDePagamento))
            )
            .andExpect(status().isOk());

        // Validate the FormaDePagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFormaDePagamentoToMatchAllProperties(updatedFormaDePagamento);
    }

    @Test
    @Transactional
    void putNonExistingFormaDePagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formaDePagamento.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormaDePagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, formaDePagamento.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(formaDePagamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormaDePagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFormaDePagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formaDePagamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormaDePagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(formaDePagamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormaDePagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFormaDePagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formaDePagamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormaDePagamentoMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formaDePagamento))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FormaDePagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFormaDePagamentoWithPatch() throws Exception {
        // Initialize the database
        insertedFormaDePagamento = formaDePagamentoRepository.saveAndFlush(formaDePagamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formaDePagamento using partial update
        FormaDePagamento partialUpdatedFormaDePagamento = new FormaDePagamento();
        partialUpdatedFormaDePagamento.setId(formaDePagamento.getId());

        partialUpdatedFormaDePagamento.disponivel(UPDATED_DISPONIVEL);

        restFormaDePagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormaDePagamento.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFormaDePagamento))
            )
            .andExpect(status().isOk());

        // Validate the FormaDePagamento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFormaDePagamentoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFormaDePagamento, formaDePagamento),
            getPersistedFormaDePagamento(formaDePagamento)
        );
    }

    @Test
    @Transactional
    void fullUpdateFormaDePagamentoWithPatch() throws Exception {
        // Initialize the database
        insertedFormaDePagamento = formaDePagamentoRepository.saveAndFlush(formaDePagamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formaDePagamento using partial update
        FormaDePagamento partialUpdatedFormaDePagamento = new FormaDePagamento();
        partialUpdatedFormaDePagamento.setId(formaDePagamento.getId());

        partialUpdatedFormaDePagamento.forma(UPDATED_FORMA).descricao(UPDATED_DESCRICAO).disponivel(UPDATED_DISPONIVEL);

        restFormaDePagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormaDePagamento.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFormaDePagamento))
            )
            .andExpect(status().isOk());

        // Validate the FormaDePagamento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFormaDePagamentoUpdatableFieldsEquals(
            partialUpdatedFormaDePagamento,
            getPersistedFormaDePagamento(partialUpdatedFormaDePagamento)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFormaDePagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formaDePagamento.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormaDePagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, formaDePagamento.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(formaDePagamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormaDePagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFormaDePagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formaDePagamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormaDePagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(formaDePagamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormaDePagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFormaDePagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formaDePagamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormaDePagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(formaDePagamento))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FormaDePagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFormaDePagamento() throws Exception {
        // Initialize the database
        insertedFormaDePagamento = formaDePagamentoRepository.saveAndFlush(formaDePagamento);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the formaDePagamento
        restFormaDePagamentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, formaDePagamento.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return formaDePagamentoRepository.count();
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

    protected FormaDePagamento getPersistedFormaDePagamento(FormaDePagamento formaDePagamento) {
        return formaDePagamentoRepository.findById(formaDePagamento.getId()).orElseThrow();
    }

    protected void assertPersistedFormaDePagamentoToMatchAllProperties(FormaDePagamento expectedFormaDePagamento) {
        assertFormaDePagamentoAllPropertiesEquals(expectedFormaDePagamento, getPersistedFormaDePagamento(expectedFormaDePagamento));
    }

    protected void assertPersistedFormaDePagamentoToMatchUpdatableProperties(FormaDePagamento expectedFormaDePagamento) {
        assertFormaDePagamentoAllUpdatablePropertiesEquals(
            expectedFormaDePagamento,
            getPersistedFormaDePagamento(expectedFormaDePagamento)
        );
    }
}
