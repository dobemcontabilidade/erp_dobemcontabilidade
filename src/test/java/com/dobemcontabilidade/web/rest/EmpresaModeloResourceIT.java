package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.EmpresaModeloAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Cidade;
import com.dobemcontabilidade.domain.EmpresaModelo;
import com.dobemcontabilidade.domain.Enquadramento;
import com.dobemcontabilidade.domain.Ramo;
import com.dobemcontabilidade.domain.Tributacao;
import com.dobemcontabilidade.repository.EmpresaModeloRepository;
import com.dobemcontabilidade.service.EmpresaModeloService;
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
 * Integration tests for the {@link EmpresaModeloResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EmpresaModeloResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/empresa-modelos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EmpresaModeloRepository empresaModeloRepository;

    @Mock
    private EmpresaModeloRepository empresaModeloRepositoryMock;

    @Mock
    private EmpresaModeloService empresaModeloServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmpresaModeloMockMvc;

    private EmpresaModelo empresaModelo;

    private EmpresaModelo insertedEmpresaModelo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmpresaModelo createEntity(EntityManager em) {
        EmpresaModelo empresaModelo = new EmpresaModelo().nome(DEFAULT_NOME).observacao(DEFAULT_OBSERVACAO);
        // Add required entity
        Ramo ramo;
        if (TestUtil.findAll(em, Ramo.class).isEmpty()) {
            ramo = RamoResourceIT.createEntity(em);
            em.persist(ramo);
            em.flush();
        } else {
            ramo = TestUtil.findAll(em, Ramo.class).get(0);
        }
        empresaModelo.setRamo(ramo);
        // Add required entity
        Enquadramento enquadramento;
        if (TestUtil.findAll(em, Enquadramento.class).isEmpty()) {
            enquadramento = EnquadramentoResourceIT.createEntity(em);
            em.persist(enquadramento);
            em.flush();
        } else {
            enquadramento = TestUtil.findAll(em, Enquadramento.class).get(0);
        }
        empresaModelo.setEnquadramento(enquadramento);
        // Add required entity
        Tributacao tributacao;
        if (TestUtil.findAll(em, Tributacao.class).isEmpty()) {
            tributacao = TributacaoResourceIT.createEntity(em);
            em.persist(tributacao);
            em.flush();
        } else {
            tributacao = TestUtil.findAll(em, Tributacao.class).get(0);
        }
        empresaModelo.setTributacao(tributacao);
        // Add required entity
        Cidade cidade;
        if (TestUtil.findAll(em, Cidade.class).isEmpty()) {
            cidade = CidadeResourceIT.createEntity(em);
            em.persist(cidade);
            em.flush();
        } else {
            cidade = TestUtil.findAll(em, Cidade.class).get(0);
        }
        empresaModelo.setCidade(cidade);
        return empresaModelo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmpresaModelo createUpdatedEntity(EntityManager em) {
        EmpresaModelo empresaModelo = new EmpresaModelo().nome(UPDATED_NOME).observacao(UPDATED_OBSERVACAO);
        // Add required entity
        Ramo ramo;
        if (TestUtil.findAll(em, Ramo.class).isEmpty()) {
            ramo = RamoResourceIT.createUpdatedEntity(em);
            em.persist(ramo);
            em.flush();
        } else {
            ramo = TestUtil.findAll(em, Ramo.class).get(0);
        }
        empresaModelo.setRamo(ramo);
        // Add required entity
        Enquadramento enquadramento;
        if (TestUtil.findAll(em, Enquadramento.class).isEmpty()) {
            enquadramento = EnquadramentoResourceIT.createUpdatedEntity(em);
            em.persist(enquadramento);
            em.flush();
        } else {
            enquadramento = TestUtil.findAll(em, Enquadramento.class).get(0);
        }
        empresaModelo.setEnquadramento(enquadramento);
        // Add required entity
        Tributacao tributacao;
        if (TestUtil.findAll(em, Tributacao.class).isEmpty()) {
            tributacao = TributacaoResourceIT.createUpdatedEntity(em);
            em.persist(tributacao);
            em.flush();
        } else {
            tributacao = TestUtil.findAll(em, Tributacao.class).get(0);
        }
        empresaModelo.setTributacao(tributacao);
        // Add required entity
        Cidade cidade;
        if (TestUtil.findAll(em, Cidade.class).isEmpty()) {
            cidade = CidadeResourceIT.createUpdatedEntity(em);
            em.persist(cidade);
            em.flush();
        } else {
            cidade = TestUtil.findAll(em, Cidade.class).get(0);
        }
        empresaModelo.setCidade(cidade);
        return empresaModelo;
    }

    @BeforeEach
    public void initTest() {
        empresaModelo = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedEmpresaModelo != null) {
            empresaModeloRepository.delete(insertedEmpresaModelo);
            insertedEmpresaModelo = null;
        }
    }

    @Test
    @Transactional
    void createEmpresaModelo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EmpresaModelo
        var returnedEmpresaModelo = om.readValue(
            restEmpresaModeloMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empresaModelo)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EmpresaModelo.class
        );

        // Validate the EmpresaModelo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEmpresaModeloUpdatableFieldsEquals(returnedEmpresaModelo, getPersistedEmpresaModelo(returnedEmpresaModelo));

        insertedEmpresaModelo = returnedEmpresaModelo;
    }

    @Test
    @Transactional
    void createEmpresaModeloWithExistingId() throws Exception {
        // Create the EmpresaModelo with an existing ID
        empresaModelo.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpresaModeloMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empresaModelo)))
            .andExpect(status().isBadRequest());

        // Validate the EmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmpresaModelos() throws Exception {
        // Initialize the database
        insertedEmpresaModelo = empresaModeloRepository.saveAndFlush(empresaModelo);

        // Get all the empresaModeloList
        restEmpresaModeloMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empresaModelo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEmpresaModelosWithEagerRelationshipsIsEnabled() throws Exception {
        when(empresaModeloServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEmpresaModeloMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(empresaModeloServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEmpresaModelosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(empresaModeloServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEmpresaModeloMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(empresaModeloRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEmpresaModelo() throws Exception {
        // Initialize the database
        insertedEmpresaModelo = empresaModeloRepository.saveAndFlush(empresaModelo);

        // Get the empresaModelo
        restEmpresaModeloMockMvc
            .perform(get(ENTITY_API_URL_ID, empresaModelo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(empresaModelo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO));
    }

    @Test
    @Transactional
    void getNonExistingEmpresaModelo() throws Exception {
        // Get the empresaModelo
        restEmpresaModeloMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmpresaModelo() throws Exception {
        // Initialize the database
        insertedEmpresaModelo = empresaModeloRepository.saveAndFlush(empresaModelo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the empresaModelo
        EmpresaModelo updatedEmpresaModelo = empresaModeloRepository.findById(empresaModelo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEmpresaModelo are not directly saved in db
        em.detach(updatedEmpresaModelo);
        updatedEmpresaModelo.nome(UPDATED_NOME).observacao(UPDATED_OBSERVACAO);

        restEmpresaModeloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmpresaModelo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEmpresaModelo))
            )
            .andExpect(status().isOk());

        // Validate the EmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEmpresaModeloToMatchAllProperties(updatedEmpresaModelo);
    }

    @Test
    @Transactional
    void putNonExistingEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empresaModelo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpresaModeloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, empresaModelo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(empresaModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empresaModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpresaModeloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(empresaModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empresaModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpresaModeloMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empresaModelo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmpresaModeloWithPatch() throws Exception {
        // Initialize the database
        insertedEmpresaModelo = empresaModeloRepository.saveAndFlush(empresaModelo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the empresaModelo using partial update
        EmpresaModelo partialUpdatedEmpresaModelo = new EmpresaModelo();
        partialUpdatedEmpresaModelo.setId(empresaModelo.getId());

        restEmpresaModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpresaModelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmpresaModelo))
            )
            .andExpect(status().isOk());

        // Validate the EmpresaModelo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmpresaModeloUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEmpresaModelo, empresaModelo),
            getPersistedEmpresaModelo(empresaModelo)
        );
    }

    @Test
    @Transactional
    void fullUpdateEmpresaModeloWithPatch() throws Exception {
        // Initialize the database
        insertedEmpresaModelo = empresaModeloRepository.saveAndFlush(empresaModelo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the empresaModelo using partial update
        EmpresaModelo partialUpdatedEmpresaModelo = new EmpresaModelo();
        partialUpdatedEmpresaModelo.setId(empresaModelo.getId());

        partialUpdatedEmpresaModelo.nome(UPDATED_NOME).observacao(UPDATED_OBSERVACAO);

        restEmpresaModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpresaModelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmpresaModelo))
            )
            .andExpect(status().isOk());

        // Validate the EmpresaModelo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmpresaModeloUpdatableFieldsEquals(partialUpdatedEmpresaModelo, getPersistedEmpresaModelo(partialUpdatedEmpresaModelo));
    }

    @Test
    @Transactional
    void patchNonExistingEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empresaModelo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpresaModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, empresaModelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(empresaModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empresaModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpresaModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(empresaModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empresaModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpresaModeloMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(empresaModelo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmpresaModelo() throws Exception {
        // Initialize the database
        insertedEmpresaModelo = empresaModeloRepository.saveAndFlush(empresaModelo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the empresaModelo
        restEmpresaModeloMockMvc
            .perform(delete(ENTITY_API_URL_ID, empresaModelo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return empresaModeloRepository.count();
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

    protected EmpresaModelo getPersistedEmpresaModelo(EmpresaModelo empresaModelo) {
        return empresaModeloRepository.findById(empresaModelo.getId()).orElseThrow();
    }

    protected void assertPersistedEmpresaModeloToMatchAllProperties(EmpresaModelo expectedEmpresaModelo) {
        assertEmpresaModeloAllPropertiesEquals(expectedEmpresaModelo, getPersistedEmpresaModelo(expectedEmpresaModelo));
    }

    protected void assertPersistedEmpresaModeloToMatchUpdatableProperties(EmpresaModelo expectedEmpresaModelo) {
        assertEmpresaModeloAllUpdatablePropertiesEquals(expectedEmpresaModelo, getPersistedEmpresaModelo(expectedEmpresaModelo));
    }
}
