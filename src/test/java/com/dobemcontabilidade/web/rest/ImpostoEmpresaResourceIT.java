package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.ImpostoEmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.domain.Imposto;
import com.dobemcontabilidade.domain.ImpostoEmpresa;
import com.dobemcontabilidade.repository.ImpostoEmpresaRepository;
import com.dobemcontabilidade.service.ImpostoEmpresaService;
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
 * Integration tests for the {@link ImpostoEmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ImpostoEmpresaResourceIT {

    private static final Integer DEFAULT_DIA_VENCIMENTO = 1;
    private static final Integer UPDATED_DIA_VENCIMENTO = 2;

    private static final String ENTITY_API_URL = "/api/imposto-empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ImpostoEmpresaRepository impostoEmpresaRepository;

    @Mock
    private ImpostoEmpresaRepository impostoEmpresaRepositoryMock;

    @Mock
    private ImpostoEmpresaService impostoEmpresaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restImpostoEmpresaMockMvc;

    private ImpostoEmpresa impostoEmpresa;

    private ImpostoEmpresa insertedImpostoEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImpostoEmpresa createEntity(EntityManager em) {
        ImpostoEmpresa impostoEmpresa = new ImpostoEmpresa().diaVencimento(DEFAULT_DIA_VENCIMENTO);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        impostoEmpresa.setEmpresa(empresa);
        // Add required entity
        Imposto imposto;
        if (TestUtil.findAll(em, Imposto.class).isEmpty()) {
            imposto = ImpostoResourceIT.createEntity(em);
            em.persist(imposto);
            em.flush();
        } else {
            imposto = TestUtil.findAll(em, Imposto.class).get(0);
        }
        impostoEmpresa.setImposto(imposto);
        return impostoEmpresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImpostoEmpresa createUpdatedEntity(EntityManager em) {
        ImpostoEmpresa impostoEmpresa = new ImpostoEmpresa().diaVencimento(UPDATED_DIA_VENCIMENTO);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createUpdatedEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        impostoEmpresa.setEmpresa(empresa);
        // Add required entity
        Imposto imposto;
        if (TestUtil.findAll(em, Imposto.class).isEmpty()) {
            imposto = ImpostoResourceIT.createUpdatedEntity(em);
            em.persist(imposto);
            em.flush();
        } else {
            imposto = TestUtil.findAll(em, Imposto.class).get(0);
        }
        impostoEmpresa.setImposto(imposto);
        return impostoEmpresa;
    }

    @BeforeEach
    public void initTest() {
        impostoEmpresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedImpostoEmpresa != null) {
            impostoEmpresaRepository.delete(insertedImpostoEmpresa);
            insertedImpostoEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createImpostoEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ImpostoEmpresa
        var returnedImpostoEmpresa = om.readValue(
            restImpostoEmpresaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(impostoEmpresa)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ImpostoEmpresa.class
        );

        // Validate the ImpostoEmpresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertImpostoEmpresaUpdatableFieldsEquals(returnedImpostoEmpresa, getPersistedImpostoEmpresa(returnedImpostoEmpresa));

        insertedImpostoEmpresa = returnedImpostoEmpresa;
    }

    @Test
    @Transactional
    void createImpostoEmpresaWithExistingId() throws Exception {
        // Create the ImpostoEmpresa with an existing ID
        impostoEmpresa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restImpostoEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(impostoEmpresa)))
            .andExpect(status().isBadRequest());

        // Validate the ImpostoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllImpostoEmpresas() throws Exception {
        // Initialize the database
        insertedImpostoEmpresa = impostoEmpresaRepository.saveAndFlush(impostoEmpresa);

        // Get all the impostoEmpresaList
        restImpostoEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(impostoEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].diaVencimento").value(hasItem(DEFAULT_DIA_VENCIMENTO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllImpostoEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(impostoEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restImpostoEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(impostoEmpresaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllImpostoEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(impostoEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restImpostoEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(impostoEmpresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getImpostoEmpresa() throws Exception {
        // Initialize the database
        insertedImpostoEmpresa = impostoEmpresaRepository.saveAndFlush(impostoEmpresa);

        // Get the impostoEmpresa
        restImpostoEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, impostoEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(impostoEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.diaVencimento").value(DEFAULT_DIA_VENCIMENTO));
    }

    @Test
    @Transactional
    void getNonExistingImpostoEmpresa() throws Exception {
        // Get the impostoEmpresa
        restImpostoEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingImpostoEmpresa() throws Exception {
        // Initialize the database
        insertedImpostoEmpresa = impostoEmpresaRepository.saveAndFlush(impostoEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the impostoEmpresa
        ImpostoEmpresa updatedImpostoEmpresa = impostoEmpresaRepository.findById(impostoEmpresa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedImpostoEmpresa are not directly saved in db
        em.detach(updatedImpostoEmpresa);
        updatedImpostoEmpresa.diaVencimento(UPDATED_DIA_VENCIMENTO);

        restImpostoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedImpostoEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedImpostoEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the ImpostoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedImpostoEmpresaToMatchAllProperties(updatedImpostoEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingImpostoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impostoEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImpostoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, impostoEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(impostoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImpostoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchImpostoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impostoEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImpostoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(impostoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImpostoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamImpostoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impostoEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImpostoEmpresaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(impostoEmpresa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ImpostoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateImpostoEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedImpostoEmpresa = impostoEmpresaRepository.saveAndFlush(impostoEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the impostoEmpresa using partial update
        ImpostoEmpresa partialUpdatedImpostoEmpresa = new ImpostoEmpresa();
        partialUpdatedImpostoEmpresa.setId(impostoEmpresa.getId());

        partialUpdatedImpostoEmpresa.diaVencimento(UPDATED_DIA_VENCIMENTO);

        restImpostoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImpostoEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedImpostoEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the ImpostoEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertImpostoEmpresaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedImpostoEmpresa, impostoEmpresa),
            getPersistedImpostoEmpresa(impostoEmpresa)
        );
    }

    @Test
    @Transactional
    void fullUpdateImpostoEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedImpostoEmpresa = impostoEmpresaRepository.saveAndFlush(impostoEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the impostoEmpresa using partial update
        ImpostoEmpresa partialUpdatedImpostoEmpresa = new ImpostoEmpresa();
        partialUpdatedImpostoEmpresa.setId(impostoEmpresa.getId());

        partialUpdatedImpostoEmpresa.diaVencimento(UPDATED_DIA_VENCIMENTO);

        restImpostoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImpostoEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedImpostoEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the ImpostoEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertImpostoEmpresaUpdatableFieldsEquals(partialUpdatedImpostoEmpresa, getPersistedImpostoEmpresa(partialUpdatedImpostoEmpresa));
    }

    @Test
    @Transactional
    void patchNonExistingImpostoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impostoEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImpostoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, impostoEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(impostoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImpostoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchImpostoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impostoEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImpostoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(impostoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImpostoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamImpostoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impostoEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImpostoEmpresaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(impostoEmpresa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ImpostoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteImpostoEmpresa() throws Exception {
        // Initialize the database
        insertedImpostoEmpresa = impostoEmpresaRepository.saveAndFlush(impostoEmpresa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the impostoEmpresa
        restImpostoEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, impostoEmpresa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return impostoEmpresaRepository.count();
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

    protected ImpostoEmpresa getPersistedImpostoEmpresa(ImpostoEmpresa impostoEmpresa) {
        return impostoEmpresaRepository.findById(impostoEmpresa.getId()).orElseThrow();
    }

    protected void assertPersistedImpostoEmpresaToMatchAllProperties(ImpostoEmpresa expectedImpostoEmpresa) {
        assertImpostoEmpresaAllPropertiesEquals(expectedImpostoEmpresa, getPersistedImpostoEmpresa(expectedImpostoEmpresa));
    }

    protected void assertPersistedImpostoEmpresaToMatchUpdatableProperties(ImpostoEmpresa expectedImpostoEmpresa) {
        assertImpostoEmpresaAllUpdatablePropertiesEquals(expectedImpostoEmpresa, getPersistedImpostoEmpresa(expectedImpostoEmpresa));
    }
}
