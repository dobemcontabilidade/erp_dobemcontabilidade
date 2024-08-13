package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.ServicoContabilEmpresaModeloAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.EmpresaModelo;
import com.dobemcontabilidade.domain.ServicoContabil;
import com.dobemcontabilidade.domain.ServicoContabilEmpresaModelo;
import com.dobemcontabilidade.repository.ServicoContabilEmpresaModeloRepository;
import com.dobemcontabilidade.service.ServicoContabilEmpresaModeloService;
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
 * Integration tests for the {@link ServicoContabilEmpresaModeloResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ServicoContabilEmpresaModeloResourceIT {

    private static final Boolean DEFAULT_OBRIGATORIO = false;
    private static final Boolean UPDATED_OBRIGATORIO = true;

    private static final String ENTITY_API_URL = "/api/servico-contabil-empresa-modelos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ServicoContabilEmpresaModeloRepository servicoContabilEmpresaModeloRepository;

    @Mock
    private ServicoContabilEmpresaModeloRepository servicoContabilEmpresaModeloRepositoryMock;

    @Mock
    private ServicoContabilEmpresaModeloService servicoContabilEmpresaModeloServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServicoContabilEmpresaModeloMockMvc;

    private ServicoContabilEmpresaModelo servicoContabilEmpresaModelo;

    private ServicoContabilEmpresaModelo insertedServicoContabilEmpresaModelo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServicoContabilEmpresaModelo createEntity(EntityManager em) {
        ServicoContabilEmpresaModelo servicoContabilEmpresaModelo = new ServicoContabilEmpresaModelo().obrigatorio(DEFAULT_OBRIGATORIO);
        // Add required entity
        EmpresaModelo empresaModelo;
        if (TestUtil.findAll(em, EmpresaModelo.class).isEmpty()) {
            empresaModelo = EmpresaModeloResourceIT.createEntity(em);
            em.persist(empresaModelo);
            em.flush();
        } else {
            empresaModelo = TestUtil.findAll(em, EmpresaModelo.class).get(0);
        }
        servicoContabilEmpresaModelo.setEmpresaModelo(empresaModelo);
        // Add required entity
        ServicoContabil servicoContabil;
        if (TestUtil.findAll(em, ServicoContabil.class).isEmpty()) {
            servicoContabil = ServicoContabilResourceIT.createEntity(em);
            em.persist(servicoContabil);
            em.flush();
        } else {
            servicoContabil = TestUtil.findAll(em, ServicoContabil.class).get(0);
        }
        servicoContabilEmpresaModelo.setServicoContabil(servicoContabil);
        return servicoContabilEmpresaModelo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServicoContabilEmpresaModelo createUpdatedEntity(EntityManager em) {
        ServicoContabilEmpresaModelo servicoContabilEmpresaModelo = new ServicoContabilEmpresaModelo().obrigatorio(UPDATED_OBRIGATORIO);
        // Add required entity
        EmpresaModelo empresaModelo;
        if (TestUtil.findAll(em, EmpresaModelo.class).isEmpty()) {
            empresaModelo = EmpresaModeloResourceIT.createUpdatedEntity(em);
            em.persist(empresaModelo);
            em.flush();
        } else {
            empresaModelo = TestUtil.findAll(em, EmpresaModelo.class).get(0);
        }
        servicoContabilEmpresaModelo.setEmpresaModelo(empresaModelo);
        // Add required entity
        ServicoContabil servicoContabil;
        if (TestUtil.findAll(em, ServicoContabil.class).isEmpty()) {
            servicoContabil = ServicoContabilResourceIT.createUpdatedEntity(em);
            em.persist(servicoContabil);
            em.flush();
        } else {
            servicoContabil = TestUtil.findAll(em, ServicoContabil.class).get(0);
        }
        servicoContabilEmpresaModelo.setServicoContabil(servicoContabil);
        return servicoContabilEmpresaModelo;
    }

    @BeforeEach
    public void initTest() {
        servicoContabilEmpresaModelo = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedServicoContabilEmpresaModelo != null) {
            servicoContabilEmpresaModeloRepository.delete(insertedServicoContabilEmpresaModelo);
            insertedServicoContabilEmpresaModelo = null;
        }
    }

    @Test
    @Transactional
    void createServicoContabilEmpresaModelo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ServicoContabilEmpresaModelo
        var returnedServicoContabilEmpresaModelo = om.readValue(
            restServicoContabilEmpresaModeloMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicoContabilEmpresaModelo))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ServicoContabilEmpresaModelo.class
        );

        // Validate the ServicoContabilEmpresaModelo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertServicoContabilEmpresaModeloUpdatableFieldsEquals(
            returnedServicoContabilEmpresaModelo,
            getPersistedServicoContabilEmpresaModelo(returnedServicoContabilEmpresaModelo)
        );

        insertedServicoContabilEmpresaModelo = returnedServicoContabilEmpresaModelo;
    }

    @Test
    @Transactional
    void createServicoContabilEmpresaModeloWithExistingId() throws Exception {
        // Create the ServicoContabilEmpresaModelo with an existing ID
        servicoContabilEmpresaModelo.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServicoContabilEmpresaModeloMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicoContabilEmpresaModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabilEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllServicoContabilEmpresaModelos() throws Exception {
        // Initialize the database
        insertedServicoContabilEmpresaModelo = servicoContabilEmpresaModeloRepository.saveAndFlush(servicoContabilEmpresaModelo);

        // Get all the servicoContabilEmpresaModeloList
        restServicoContabilEmpresaModeloMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servicoContabilEmpresaModelo.getId().intValue())))
            .andExpect(jsonPath("$.[*].obrigatorio").value(hasItem(DEFAULT_OBRIGATORIO.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllServicoContabilEmpresaModelosWithEagerRelationshipsIsEnabled() throws Exception {
        when(servicoContabilEmpresaModeloServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restServicoContabilEmpresaModeloMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(servicoContabilEmpresaModeloServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllServicoContabilEmpresaModelosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(servicoContabilEmpresaModeloServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restServicoContabilEmpresaModeloMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(servicoContabilEmpresaModeloRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getServicoContabilEmpresaModelo() throws Exception {
        // Initialize the database
        insertedServicoContabilEmpresaModelo = servicoContabilEmpresaModeloRepository.saveAndFlush(servicoContabilEmpresaModelo);

        // Get the servicoContabilEmpresaModelo
        restServicoContabilEmpresaModeloMockMvc
            .perform(get(ENTITY_API_URL_ID, servicoContabilEmpresaModelo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(servicoContabilEmpresaModelo.getId().intValue()))
            .andExpect(jsonPath("$.obrigatorio").value(DEFAULT_OBRIGATORIO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingServicoContabilEmpresaModelo() throws Exception {
        // Get the servicoContabilEmpresaModelo
        restServicoContabilEmpresaModeloMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServicoContabilEmpresaModelo() throws Exception {
        // Initialize the database
        insertedServicoContabilEmpresaModelo = servicoContabilEmpresaModeloRepository.saveAndFlush(servicoContabilEmpresaModelo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicoContabilEmpresaModelo
        ServicoContabilEmpresaModelo updatedServicoContabilEmpresaModelo = servicoContabilEmpresaModeloRepository
            .findById(servicoContabilEmpresaModelo.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedServicoContabilEmpresaModelo are not directly saved in db
        em.detach(updatedServicoContabilEmpresaModelo);
        updatedServicoContabilEmpresaModelo.obrigatorio(UPDATED_OBRIGATORIO);

        restServicoContabilEmpresaModeloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedServicoContabilEmpresaModelo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedServicoContabilEmpresaModelo))
            )
            .andExpect(status().isOk());

        // Validate the ServicoContabilEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedServicoContabilEmpresaModeloToMatchAllProperties(updatedServicoContabilEmpresaModelo);
    }

    @Test
    @Transactional
    void putNonExistingServicoContabilEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilEmpresaModelo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicoContabilEmpresaModeloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, servicoContabilEmpresaModelo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(servicoContabilEmpresaModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabilEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServicoContabilEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilEmpresaModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicoContabilEmpresaModeloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(servicoContabilEmpresaModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabilEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServicoContabilEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilEmpresaModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicoContabilEmpresaModeloMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicoContabilEmpresaModelo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServicoContabilEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServicoContabilEmpresaModeloWithPatch() throws Exception {
        // Initialize the database
        insertedServicoContabilEmpresaModelo = servicoContabilEmpresaModeloRepository.saveAndFlush(servicoContabilEmpresaModelo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicoContabilEmpresaModelo using partial update
        ServicoContabilEmpresaModelo partialUpdatedServicoContabilEmpresaModelo = new ServicoContabilEmpresaModelo();
        partialUpdatedServicoContabilEmpresaModelo.setId(servicoContabilEmpresaModelo.getId());

        partialUpdatedServicoContabilEmpresaModelo.obrigatorio(UPDATED_OBRIGATORIO);

        restServicoContabilEmpresaModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicoContabilEmpresaModelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServicoContabilEmpresaModelo))
            )
            .andExpect(status().isOk());

        // Validate the ServicoContabilEmpresaModelo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServicoContabilEmpresaModeloUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedServicoContabilEmpresaModelo, servicoContabilEmpresaModelo),
            getPersistedServicoContabilEmpresaModelo(servicoContabilEmpresaModelo)
        );
    }

    @Test
    @Transactional
    void fullUpdateServicoContabilEmpresaModeloWithPatch() throws Exception {
        // Initialize the database
        insertedServicoContabilEmpresaModelo = servicoContabilEmpresaModeloRepository.saveAndFlush(servicoContabilEmpresaModelo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicoContabilEmpresaModelo using partial update
        ServicoContabilEmpresaModelo partialUpdatedServicoContabilEmpresaModelo = new ServicoContabilEmpresaModelo();
        partialUpdatedServicoContabilEmpresaModelo.setId(servicoContabilEmpresaModelo.getId());

        partialUpdatedServicoContabilEmpresaModelo.obrigatorio(UPDATED_OBRIGATORIO);

        restServicoContabilEmpresaModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicoContabilEmpresaModelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServicoContabilEmpresaModelo))
            )
            .andExpect(status().isOk());

        // Validate the ServicoContabilEmpresaModelo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServicoContabilEmpresaModeloUpdatableFieldsEquals(
            partialUpdatedServicoContabilEmpresaModelo,
            getPersistedServicoContabilEmpresaModelo(partialUpdatedServicoContabilEmpresaModelo)
        );
    }

    @Test
    @Transactional
    void patchNonExistingServicoContabilEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilEmpresaModelo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicoContabilEmpresaModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, servicoContabilEmpresaModelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(servicoContabilEmpresaModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabilEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServicoContabilEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilEmpresaModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicoContabilEmpresaModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(servicoContabilEmpresaModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabilEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServicoContabilEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilEmpresaModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicoContabilEmpresaModeloMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(servicoContabilEmpresaModelo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServicoContabilEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServicoContabilEmpresaModelo() throws Exception {
        // Initialize the database
        insertedServicoContabilEmpresaModelo = servicoContabilEmpresaModeloRepository.saveAndFlush(servicoContabilEmpresaModelo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the servicoContabilEmpresaModelo
        restServicoContabilEmpresaModeloMockMvc
            .perform(delete(ENTITY_API_URL_ID, servicoContabilEmpresaModelo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return servicoContabilEmpresaModeloRepository.count();
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

    protected ServicoContabilEmpresaModelo getPersistedServicoContabilEmpresaModelo(
        ServicoContabilEmpresaModelo servicoContabilEmpresaModelo
    ) {
        return servicoContabilEmpresaModeloRepository.findById(servicoContabilEmpresaModelo.getId()).orElseThrow();
    }

    protected void assertPersistedServicoContabilEmpresaModeloToMatchAllProperties(
        ServicoContabilEmpresaModelo expectedServicoContabilEmpresaModelo
    ) {
        assertServicoContabilEmpresaModeloAllPropertiesEquals(
            expectedServicoContabilEmpresaModelo,
            getPersistedServicoContabilEmpresaModelo(expectedServicoContabilEmpresaModelo)
        );
    }

    protected void assertPersistedServicoContabilEmpresaModeloToMatchUpdatableProperties(
        ServicoContabilEmpresaModelo expectedServicoContabilEmpresaModelo
    ) {
        assertServicoContabilEmpresaModeloAllUpdatablePropertiesEquals(
            expectedServicoContabilEmpresaModelo,
            getPersistedServicoContabilEmpresaModelo(expectedServicoContabilEmpresaModelo)
        );
    }
}
