package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.ImpostoParceladoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.ImpostoAPagarEmpresa;
import com.dobemcontabilidade.domain.ImpostoParcelado;
import com.dobemcontabilidade.domain.ParcelamentoImposto;
import com.dobemcontabilidade.repository.ImpostoParceladoRepository;
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
 * Integration tests for the {@link ImpostoParceladoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ImpostoParceladoResourceIT {

    private static final Integer DEFAULT_DIAS_ATRASO = 1;
    private static final Integer UPDATED_DIAS_ATRASO = 2;

    private static final String ENTITY_API_URL = "/api/imposto-parcelados";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ImpostoParceladoRepository impostoParceladoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restImpostoParceladoMockMvc;

    private ImpostoParcelado impostoParcelado;

    private ImpostoParcelado insertedImpostoParcelado;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImpostoParcelado createEntity(EntityManager em) {
        ImpostoParcelado impostoParcelado = new ImpostoParcelado().diasAtraso(DEFAULT_DIAS_ATRASO);
        // Add required entity
        ParcelamentoImposto parcelamentoImposto;
        if (TestUtil.findAll(em, ParcelamentoImposto.class).isEmpty()) {
            parcelamentoImposto = ParcelamentoImpostoResourceIT.createEntity(em);
            em.persist(parcelamentoImposto);
            em.flush();
        } else {
            parcelamentoImposto = TestUtil.findAll(em, ParcelamentoImposto.class).get(0);
        }
        impostoParcelado.setParcelamentoImposto(parcelamentoImposto);
        // Add required entity
        ImpostoAPagarEmpresa impostoAPagarEmpresa;
        if (TestUtil.findAll(em, ImpostoAPagarEmpresa.class).isEmpty()) {
            impostoAPagarEmpresa = ImpostoAPagarEmpresaResourceIT.createEntity(em);
            em.persist(impostoAPagarEmpresa);
            em.flush();
        } else {
            impostoAPagarEmpresa = TestUtil.findAll(em, ImpostoAPagarEmpresa.class).get(0);
        }
        impostoParcelado.setImpostoAPagarEmpresa(impostoAPagarEmpresa);
        return impostoParcelado;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImpostoParcelado createUpdatedEntity(EntityManager em) {
        ImpostoParcelado impostoParcelado = new ImpostoParcelado().diasAtraso(UPDATED_DIAS_ATRASO);
        // Add required entity
        ParcelamentoImposto parcelamentoImposto;
        if (TestUtil.findAll(em, ParcelamentoImposto.class).isEmpty()) {
            parcelamentoImposto = ParcelamentoImpostoResourceIT.createUpdatedEntity(em);
            em.persist(parcelamentoImposto);
            em.flush();
        } else {
            parcelamentoImposto = TestUtil.findAll(em, ParcelamentoImposto.class).get(0);
        }
        impostoParcelado.setParcelamentoImposto(parcelamentoImposto);
        // Add required entity
        ImpostoAPagarEmpresa impostoAPagarEmpresa;
        if (TestUtil.findAll(em, ImpostoAPagarEmpresa.class).isEmpty()) {
            impostoAPagarEmpresa = ImpostoAPagarEmpresaResourceIT.createUpdatedEntity(em);
            em.persist(impostoAPagarEmpresa);
            em.flush();
        } else {
            impostoAPagarEmpresa = TestUtil.findAll(em, ImpostoAPagarEmpresa.class).get(0);
        }
        impostoParcelado.setImpostoAPagarEmpresa(impostoAPagarEmpresa);
        return impostoParcelado;
    }

    @BeforeEach
    public void initTest() {
        impostoParcelado = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedImpostoParcelado != null) {
            impostoParceladoRepository.delete(insertedImpostoParcelado);
            insertedImpostoParcelado = null;
        }
    }

    @Test
    @Transactional
    void createImpostoParcelado() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ImpostoParcelado
        var returnedImpostoParcelado = om.readValue(
            restImpostoParceladoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(impostoParcelado)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ImpostoParcelado.class
        );

        // Validate the ImpostoParcelado in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertImpostoParceladoUpdatableFieldsEquals(returnedImpostoParcelado, getPersistedImpostoParcelado(returnedImpostoParcelado));

        insertedImpostoParcelado = returnedImpostoParcelado;
    }

    @Test
    @Transactional
    void createImpostoParceladoWithExistingId() throws Exception {
        // Create the ImpostoParcelado with an existing ID
        impostoParcelado.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restImpostoParceladoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(impostoParcelado)))
            .andExpect(status().isBadRequest());

        // Validate the ImpostoParcelado in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllImpostoParcelados() throws Exception {
        // Initialize the database
        insertedImpostoParcelado = impostoParceladoRepository.saveAndFlush(impostoParcelado);

        // Get all the impostoParceladoList
        restImpostoParceladoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(impostoParcelado.getId().intValue())))
            .andExpect(jsonPath("$.[*].diasAtraso").value(hasItem(DEFAULT_DIAS_ATRASO)));
    }

    @Test
    @Transactional
    void getImpostoParcelado() throws Exception {
        // Initialize the database
        insertedImpostoParcelado = impostoParceladoRepository.saveAndFlush(impostoParcelado);

        // Get the impostoParcelado
        restImpostoParceladoMockMvc
            .perform(get(ENTITY_API_URL_ID, impostoParcelado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(impostoParcelado.getId().intValue()))
            .andExpect(jsonPath("$.diasAtraso").value(DEFAULT_DIAS_ATRASO));
    }

    @Test
    @Transactional
    void getNonExistingImpostoParcelado() throws Exception {
        // Get the impostoParcelado
        restImpostoParceladoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingImpostoParcelado() throws Exception {
        // Initialize the database
        insertedImpostoParcelado = impostoParceladoRepository.saveAndFlush(impostoParcelado);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the impostoParcelado
        ImpostoParcelado updatedImpostoParcelado = impostoParceladoRepository.findById(impostoParcelado.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedImpostoParcelado are not directly saved in db
        em.detach(updatedImpostoParcelado);
        updatedImpostoParcelado.diasAtraso(UPDATED_DIAS_ATRASO);

        restImpostoParceladoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedImpostoParcelado.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedImpostoParcelado))
            )
            .andExpect(status().isOk());

        // Validate the ImpostoParcelado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedImpostoParceladoToMatchAllProperties(updatedImpostoParcelado);
    }

    @Test
    @Transactional
    void putNonExistingImpostoParcelado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impostoParcelado.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImpostoParceladoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, impostoParcelado.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(impostoParcelado))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImpostoParcelado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchImpostoParcelado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impostoParcelado.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImpostoParceladoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(impostoParcelado))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImpostoParcelado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamImpostoParcelado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impostoParcelado.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImpostoParceladoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(impostoParcelado)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ImpostoParcelado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateImpostoParceladoWithPatch() throws Exception {
        // Initialize the database
        insertedImpostoParcelado = impostoParceladoRepository.saveAndFlush(impostoParcelado);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the impostoParcelado using partial update
        ImpostoParcelado partialUpdatedImpostoParcelado = new ImpostoParcelado();
        partialUpdatedImpostoParcelado.setId(impostoParcelado.getId());

        partialUpdatedImpostoParcelado.diasAtraso(UPDATED_DIAS_ATRASO);

        restImpostoParceladoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImpostoParcelado.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedImpostoParcelado))
            )
            .andExpect(status().isOk());

        // Validate the ImpostoParcelado in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertImpostoParceladoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedImpostoParcelado, impostoParcelado),
            getPersistedImpostoParcelado(impostoParcelado)
        );
    }

    @Test
    @Transactional
    void fullUpdateImpostoParceladoWithPatch() throws Exception {
        // Initialize the database
        insertedImpostoParcelado = impostoParceladoRepository.saveAndFlush(impostoParcelado);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the impostoParcelado using partial update
        ImpostoParcelado partialUpdatedImpostoParcelado = new ImpostoParcelado();
        partialUpdatedImpostoParcelado.setId(impostoParcelado.getId());

        partialUpdatedImpostoParcelado.diasAtraso(UPDATED_DIAS_ATRASO);

        restImpostoParceladoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImpostoParcelado.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedImpostoParcelado))
            )
            .andExpect(status().isOk());

        // Validate the ImpostoParcelado in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertImpostoParceladoUpdatableFieldsEquals(
            partialUpdatedImpostoParcelado,
            getPersistedImpostoParcelado(partialUpdatedImpostoParcelado)
        );
    }

    @Test
    @Transactional
    void patchNonExistingImpostoParcelado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impostoParcelado.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImpostoParceladoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, impostoParcelado.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(impostoParcelado))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImpostoParcelado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchImpostoParcelado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impostoParcelado.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImpostoParceladoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(impostoParcelado))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImpostoParcelado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamImpostoParcelado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impostoParcelado.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImpostoParceladoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(impostoParcelado)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ImpostoParcelado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteImpostoParcelado() throws Exception {
        // Initialize the database
        insertedImpostoParcelado = impostoParceladoRepository.saveAndFlush(impostoParcelado);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the impostoParcelado
        restImpostoParceladoMockMvc
            .perform(delete(ENTITY_API_URL_ID, impostoParcelado.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return impostoParceladoRepository.count();
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

    protected ImpostoParcelado getPersistedImpostoParcelado(ImpostoParcelado impostoParcelado) {
        return impostoParceladoRepository.findById(impostoParcelado.getId()).orElseThrow();
    }

    protected void assertPersistedImpostoParceladoToMatchAllProperties(ImpostoParcelado expectedImpostoParcelado) {
        assertImpostoParceladoAllPropertiesEquals(expectedImpostoParcelado, getPersistedImpostoParcelado(expectedImpostoParcelado));
    }

    protected void assertPersistedImpostoParceladoToMatchUpdatableProperties(ImpostoParcelado expectedImpostoParcelado) {
        assertImpostoParceladoAllUpdatablePropertiesEquals(
            expectedImpostoParcelado,
            getPersistedImpostoParcelado(expectedImpostoParcelado)
        );
    }
}
