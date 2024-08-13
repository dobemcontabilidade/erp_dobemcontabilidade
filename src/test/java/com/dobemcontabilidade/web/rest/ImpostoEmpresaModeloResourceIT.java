package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.ImpostoEmpresaModeloAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.EmpresaModelo;
import com.dobemcontabilidade.domain.Imposto;
import com.dobemcontabilidade.domain.ImpostoEmpresaModelo;
import com.dobemcontabilidade.repository.ImpostoEmpresaModeloRepository;
import com.dobemcontabilidade.service.ImpostoEmpresaModeloService;
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
 * Integration tests for the {@link ImpostoEmpresaModeloResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ImpostoEmpresaModeloResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/imposto-empresa-modelos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ImpostoEmpresaModeloRepository impostoEmpresaModeloRepository;

    @Mock
    private ImpostoEmpresaModeloRepository impostoEmpresaModeloRepositoryMock;

    @Mock
    private ImpostoEmpresaModeloService impostoEmpresaModeloServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restImpostoEmpresaModeloMockMvc;

    private ImpostoEmpresaModelo impostoEmpresaModelo;

    private ImpostoEmpresaModelo insertedImpostoEmpresaModelo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImpostoEmpresaModelo createEntity(EntityManager em) {
        ImpostoEmpresaModelo impostoEmpresaModelo = new ImpostoEmpresaModelo().nome(DEFAULT_NOME).observacao(DEFAULT_OBSERVACAO);
        // Add required entity
        EmpresaModelo empresaModelo;
        if (TestUtil.findAll(em, EmpresaModelo.class).isEmpty()) {
            empresaModelo = EmpresaModeloResourceIT.createEntity(em);
            em.persist(empresaModelo);
            em.flush();
        } else {
            empresaModelo = TestUtil.findAll(em, EmpresaModelo.class).get(0);
        }
        impostoEmpresaModelo.setEmpresaModelo(empresaModelo);
        // Add required entity
        Imposto imposto;
        if (TestUtil.findAll(em, Imposto.class).isEmpty()) {
            imposto = ImpostoResourceIT.createEntity(em);
            em.persist(imposto);
            em.flush();
        } else {
            imposto = TestUtil.findAll(em, Imposto.class).get(0);
        }
        impostoEmpresaModelo.setImposto(imposto);
        return impostoEmpresaModelo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImpostoEmpresaModelo createUpdatedEntity(EntityManager em) {
        ImpostoEmpresaModelo impostoEmpresaModelo = new ImpostoEmpresaModelo().nome(UPDATED_NOME).observacao(UPDATED_OBSERVACAO);
        // Add required entity
        EmpresaModelo empresaModelo;
        if (TestUtil.findAll(em, EmpresaModelo.class).isEmpty()) {
            empresaModelo = EmpresaModeloResourceIT.createUpdatedEntity(em);
            em.persist(empresaModelo);
            em.flush();
        } else {
            empresaModelo = TestUtil.findAll(em, EmpresaModelo.class).get(0);
        }
        impostoEmpresaModelo.setEmpresaModelo(empresaModelo);
        // Add required entity
        Imposto imposto;
        if (TestUtil.findAll(em, Imposto.class).isEmpty()) {
            imposto = ImpostoResourceIT.createUpdatedEntity(em);
            em.persist(imposto);
            em.flush();
        } else {
            imposto = TestUtil.findAll(em, Imposto.class).get(0);
        }
        impostoEmpresaModelo.setImposto(imposto);
        return impostoEmpresaModelo;
    }

    @BeforeEach
    public void initTest() {
        impostoEmpresaModelo = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedImpostoEmpresaModelo != null) {
            impostoEmpresaModeloRepository.delete(insertedImpostoEmpresaModelo);
            insertedImpostoEmpresaModelo = null;
        }
    }

    @Test
    @Transactional
    void createImpostoEmpresaModelo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ImpostoEmpresaModelo
        var returnedImpostoEmpresaModelo = om.readValue(
            restImpostoEmpresaModeloMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(impostoEmpresaModelo)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ImpostoEmpresaModelo.class
        );

        // Validate the ImpostoEmpresaModelo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertImpostoEmpresaModeloUpdatableFieldsEquals(
            returnedImpostoEmpresaModelo,
            getPersistedImpostoEmpresaModelo(returnedImpostoEmpresaModelo)
        );

        insertedImpostoEmpresaModelo = returnedImpostoEmpresaModelo;
    }

    @Test
    @Transactional
    void createImpostoEmpresaModeloWithExistingId() throws Exception {
        // Create the ImpostoEmpresaModelo with an existing ID
        impostoEmpresaModelo.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restImpostoEmpresaModeloMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(impostoEmpresaModelo)))
            .andExpect(status().isBadRequest());

        // Validate the ImpostoEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllImpostoEmpresaModelos() throws Exception {
        // Initialize the database
        insertedImpostoEmpresaModelo = impostoEmpresaModeloRepository.saveAndFlush(impostoEmpresaModelo);

        // Get all the impostoEmpresaModeloList
        restImpostoEmpresaModeloMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(impostoEmpresaModelo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllImpostoEmpresaModelosWithEagerRelationshipsIsEnabled() throws Exception {
        when(impostoEmpresaModeloServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restImpostoEmpresaModeloMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(impostoEmpresaModeloServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllImpostoEmpresaModelosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(impostoEmpresaModeloServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restImpostoEmpresaModeloMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(impostoEmpresaModeloRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getImpostoEmpresaModelo() throws Exception {
        // Initialize the database
        insertedImpostoEmpresaModelo = impostoEmpresaModeloRepository.saveAndFlush(impostoEmpresaModelo);

        // Get the impostoEmpresaModelo
        restImpostoEmpresaModeloMockMvc
            .perform(get(ENTITY_API_URL_ID, impostoEmpresaModelo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(impostoEmpresaModelo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO));
    }

    @Test
    @Transactional
    void getNonExistingImpostoEmpresaModelo() throws Exception {
        // Get the impostoEmpresaModelo
        restImpostoEmpresaModeloMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingImpostoEmpresaModelo() throws Exception {
        // Initialize the database
        insertedImpostoEmpresaModelo = impostoEmpresaModeloRepository.saveAndFlush(impostoEmpresaModelo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the impostoEmpresaModelo
        ImpostoEmpresaModelo updatedImpostoEmpresaModelo = impostoEmpresaModeloRepository
            .findById(impostoEmpresaModelo.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedImpostoEmpresaModelo are not directly saved in db
        em.detach(updatedImpostoEmpresaModelo);
        updatedImpostoEmpresaModelo.nome(UPDATED_NOME).observacao(UPDATED_OBSERVACAO);

        restImpostoEmpresaModeloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedImpostoEmpresaModelo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedImpostoEmpresaModelo))
            )
            .andExpect(status().isOk());

        // Validate the ImpostoEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedImpostoEmpresaModeloToMatchAllProperties(updatedImpostoEmpresaModelo);
    }

    @Test
    @Transactional
    void putNonExistingImpostoEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impostoEmpresaModelo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImpostoEmpresaModeloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, impostoEmpresaModelo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(impostoEmpresaModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImpostoEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchImpostoEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impostoEmpresaModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImpostoEmpresaModeloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(impostoEmpresaModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImpostoEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamImpostoEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impostoEmpresaModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImpostoEmpresaModeloMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(impostoEmpresaModelo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ImpostoEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateImpostoEmpresaModeloWithPatch() throws Exception {
        // Initialize the database
        insertedImpostoEmpresaModelo = impostoEmpresaModeloRepository.saveAndFlush(impostoEmpresaModelo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the impostoEmpresaModelo using partial update
        ImpostoEmpresaModelo partialUpdatedImpostoEmpresaModelo = new ImpostoEmpresaModelo();
        partialUpdatedImpostoEmpresaModelo.setId(impostoEmpresaModelo.getId());

        partialUpdatedImpostoEmpresaModelo.nome(UPDATED_NOME);

        restImpostoEmpresaModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImpostoEmpresaModelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedImpostoEmpresaModelo))
            )
            .andExpect(status().isOk());

        // Validate the ImpostoEmpresaModelo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertImpostoEmpresaModeloUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedImpostoEmpresaModelo, impostoEmpresaModelo),
            getPersistedImpostoEmpresaModelo(impostoEmpresaModelo)
        );
    }

    @Test
    @Transactional
    void fullUpdateImpostoEmpresaModeloWithPatch() throws Exception {
        // Initialize the database
        insertedImpostoEmpresaModelo = impostoEmpresaModeloRepository.saveAndFlush(impostoEmpresaModelo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the impostoEmpresaModelo using partial update
        ImpostoEmpresaModelo partialUpdatedImpostoEmpresaModelo = new ImpostoEmpresaModelo();
        partialUpdatedImpostoEmpresaModelo.setId(impostoEmpresaModelo.getId());

        partialUpdatedImpostoEmpresaModelo.nome(UPDATED_NOME).observacao(UPDATED_OBSERVACAO);

        restImpostoEmpresaModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImpostoEmpresaModelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedImpostoEmpresaModelo))
            )
            .andExpect(status().isOk());

        // Validate the ImpostoEmpresaModelo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertImpostoEmpresaModeloUpdatableFieldsEquals(
            partialUpdatedImpostoEmpresaModelo,
            getPersistedImpostoEmpresaModelo(partialUpdatedImpostoEmpresaModelo)
        );
    }

    @Test
    @Transactional
    void patchNonExistingImpostoEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impostoEmpresaModelo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImpostoEmpresaModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, impostoEmpresaModelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(impostoEmpresaModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImpostoEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchImpostoEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impostoEmpresaModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImpostoEmpresaModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(impostoEmpresaModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImpostoEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamImpostoEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impostoEmpresaModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImpostoEmpresaModeloMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(impostoEmpresaModelo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ImpostoEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteImpostoEmpresaModelo() throws Exception {
        // Initialize the database
        insertedImpostoEmpresaModelo = impostoEmpresaModeloRepository.saveAndFlush(impostoEmpresaModelo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the impostoEmpresaModelo
        restImpostoEmpresaModeloMockMvc
            .perform(delete(ENTITY_API_URL_ID, impostoEmpresaModelo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return impostoEmpresaModeloRepository.count();
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

    protected ImpostoEmpresaModelo getPersistedImpostoEmpresaModelo(ImpostoEmpresaModelo impostoEmpresaModelo) {
        return impostoEmpresaModeloRepository.findById(impostoEmpresaModelo.getId()).orElseThrow();
    }

    protected void assertPersistedImpostoEmpresaModeloToMatchAllProperties(ImpostoEmpresaModelo expectedImpostoEmpresaModelo) {
        assertImpostoEmpresaModeloAllPropertiesEquals(
            expectedImpostoEmpresaModelo,
            getPersistedImpostoEmpresaModelo(expectedImpostoEmpresaModelo)
        );
    }

    protected void assertPersistedImpostoEmpresaModeloToMatchUpdatableProperties(ImpostoEmpresaModelo expectedImpostoEmpresaModelo) {
        assertImpostoEmpresaModeloAllUpdatablePropertiesEquals(
            expectedImpostoEmpresaModelo,
            getPersistedImpostoEmpresaModelo(expectedImpostoEmpresaModelo)
        );
    }
}
