package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.EnquadramentoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Enquadramento;
import com.dobemcontabilidade.repository.EnquadramentoRepository;
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
 * Integration tests for the {@link EnquadramentoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EnquadramentoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    private static final Double DEFAULT_LIMITE_INICIAL = 1D;
    private static final Double UPDATED_LIMITE_INICIAL = 2D;

    private static final Double DEFAULT_LIMITE_FINAL = 1D;
    private static final Double UPDATED_LIMITE_FINAL = 2D;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/enquadramentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EnquadramentoRepository enquadramentoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnquadramentoMockMvc;

    private Enquadramento enquadramento;

    private Enquadramento insertedEnquadramento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enquadramento createEntity(EntityManager em) {
        Enquadramento enquadramento = new Enquadramento()
            .nome(DEFAULT_NOME)
            .sigla(DEFAULT_SIGLA)
            .limiteInicial(DEFAULT_LIMITE_INICIAL)
            .limiteFinal(DEFAULT_LIMITE_FINAL)
            .descricao(DEFAULT_DESCRICAO);
        return enquadramento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enquadramento createUpdatedEntity(EntityManager em) {
        Enquadramento enquadramento = new Enquadramento()
            .nome(UPDATED_NOME)
            .sigla(UPDATED_SIGLA)
            .limiteInicial(UPDATED_LIMITE_INICIAL)
            .limiteFinal(UPDATED_LIMITE_FINAL)
            .descricao(UPDATED_DESCRICAO);
        return enquadramento;
    }

    @BeforeEach
    public void initTest() {
        enquadramento = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedEnquadramento != null) {
            enquadramentoRepository.delete(insertedEnquadramento);
            insertedEnquadramento = null;
        }
    }

    @Test
    @Transactional
    void createEnquadramento() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Enquadramento
        var returnedEnquadramento = om.readValue(
            restEnquadramentoMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enquadramento))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Enquadramento.class
        );

        // Validate the Enquadramento in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEnquadramentoUpdatableFieldsEquals(returnedEnquadramento, getPersistedEnquadramento(returnedEnquadramento));

        insertedEnquadramento = returnedEnquadramento;
    }

    @Test
    @Transactional
    void createEnquadramentoWithExistingId() throws Exception {
        // Create the Enquadramento with an existing ID
        enquadramento.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnquadramentoMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enquadramento)))
            .andExpect(status().isBadRequest());

        // Validate the Enquadramento in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEnquadramentos() throws Exception {
        // Initialize the database
        insertedEnquadramento = enquadramentoRepository.saveAndFlush(enquadramento);

        // Get all the enquadramentoList
        restEnquadramentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enquadramento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)))
            .andExpect(jsonPath("$.[*].limiteInicial").value(hasItem(DEFAULT_LIMITE_INICIAL.doubleValue())))
            .andExpect(jsonPath("$.[*].limiteFinal").value(hasItem(DEFAULT_LIMITE_FINAL.doubleValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    void getEnquadramento() throws Exception {
        // Initialize the database
        insertedEnquadramento = enquadramentoRepository.saveAndFlush(enquadramento);

        // Get the enquadramento
        restEnquadramentoMockMvc
            .perform(get(ENTITY_API_URL_ID, enquadramento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(enquadramento.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA))
            .andExpect(jsonPath("$.limiteInicial").value(DEFAULT_LIMITE_INICIAL.doubleValue()))
            .andExpect(jsonPath("$.limiteFinal").value(DEFAULT_LIMITE_FINAL.doubleValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEnquadramento() throws Exception {
        // Get the enquadramento
        restEnquadramentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEnquadramento() throws Exception {
        // Initialize the database
        insertedEnquadramento = enquadramentoRepository.saveAndFlush(enquadramento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the enquadramento
        Enquadramento updatedEnquadramento = enquadramentoRepository.findById(enquadramento.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEnquadramento are not directly saved in db
        em.detach(updatedEnquadramento);
        updatedEnquadramento
            .nome(UPDATED_NOME)
            .sigla(UPDATED_SIGLA)
            .limiteInicial(UPDATED_LIMITE_INICIAL)
            .limiteFinal(UPDATED_LIMITE_FINAL)
            .descricao(UPDATED_DESCRICAO);

        restEnquadramentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEnquadramento.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEnquadramento))
            )
            .andExpect(status().isOk());

        // Validate the Enquadramento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEnquadramentoToMatchAllProperties(updatedEnquadramento);
    }

    @Test
    @Transactional
    void putNonExistingEnquadramento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enquadramento.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnquadramentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, enquadramento.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(enquadramento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enquadramento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEnquadramento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enquadramento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnquadramentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(enquadramento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enquadramento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEnquadramento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enquadramento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnquadramentoMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enquadramento)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Enquadramento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEnquadramentoWithPatch() throws Exception {
        // Initialize the database
        insertedEnquadramento = enquadramentoRepository.saveAndFlush(enquadramento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the enquadramento using partial update
        Enquadramento partialUpdatedEnquadramento = new Enquadramento();
        partialUpdatedEnquadramento.setId(enquadramento.getId());

        partialUpdatedEnquadramento.nome(UPDATED_NOME).limiteInicial(UPDATED_LIMITE_INICIAL);

        restEnquadramentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnquadramento.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEnquadramento))
            )
            .andExpect(status().isOk());

        // Validate the Enquadramento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEnquadramentoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEnquadramento, enquadramento),
            getPersistedEnquadramento(enquadramento)
        );
    }

    @Test
    @Transactional
    void fullUpdateEnquadramentoWithPatch() throws Exception {
        // Initialize the database
        insertedEnquadramento = enquadramentoRepository.saveAndFlush(enquadramento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the enquadramento using partial update
        Enquadramento partialUpdatedEnquadramento = new Enquadramento();
        partialUpdatedEnquadramento.setId(enquadramento.getId());

        partialUpdatedEnquadramento
            .nome(UPDATED_NOME)
            .sigla(UPDATED_SIGLA)
            .limiteInicial(UPDATED_LIMITE_INICIAL)
            .limiteFinal(UPDATED_LIMITE_FINAL)
            .descricao(UPDATED_DESCRICAO);

        restEnquadramentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnquadramento.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEnquadramento))
            )
            .andExpect(status().isOk());

        // Validate the Enquadramento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEnquadramentoUpdatableFieldsEquals(partialUpdatedEnquadramento, getPersistedEnquadramento(partialUpdatedEnquadramento));
    }

    @Test
    @Transactional
    void patchNonExistingEnquadramento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enquadramento.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnquadramentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, enquadramento.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(enquadramento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enquadramento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEnquadramento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enquadramento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnquadramentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(enquadramento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enquadramento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEnquadramento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enquadramento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnquadramentoMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(enquadramento))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Enquadramento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEnquadramento() throws Exception {
        // Initialize the database
        insertedEnquadramento = enquadramentoRepository.saveAndFlush(enquadramento);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the enquadramento
        restEnquadramentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, enquadramento.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return enquadramentoRepository.count();
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

    protected Enquadramento getPersistedEnquadramento(Enquadramento enquadramento) {
        return enquadramentoRepository.findById(enquadramento.getId()).orElseThrow();
    }

    protected void assertPersistedEnquadramentoToMatchAllProperties(Enquadramento expectedEnquadramento) {
        assertEnquadramentoAllPropertiesEquals(expectedEnquadramento, getPersistedEnquadramento(expectedEnquadramento));
    }

    protected void assertPersistedEnquadramentoToMatchUpdatableProperties(Enquadramento expectedEnquadramento) {
        assertEnquadramentoAllUpdatablePropertiesEquals(expectedEnquadramento, getPersistedEnquadramento(expectedEnquadramento));
    }
}
