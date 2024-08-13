package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.GatewayAssinaturaEmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AssinaturaEmpresa;
import com.dobemcontabilidade.domain.GatewayAssinaturaEmpresa;
import com.dobemcontabilidade.domain.GatewayPagamento;
import com.dobemcontabilidade.repository.GatewayAssinaturaEmpresaRepository;
import com.dobemcontabilidade.service.GatewayAssinaturaEmpresaService;
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
 * Integration tests for the {@link GatewayAssinaturaEmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class GatewayAssinaturaEmpresaResourceIT {

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final String DEFAULT_CODIGO_ASSINATURA = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_ASSINATURA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/gateway-assinatura-empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GatewayAssinaturaEmpresaRepository gatewayAssinaturaEmpresaRepository;

    @Mock
    private GatewayAssinaturaEmpresaRepository gatewayAssinaturaEmpresaRepositoryMock;

    @Mock
    private GatewayAssinaturaEmpresaService gatewayAssinaturaEmpresaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGatewayAssinaturaEmpresaMockMvc;

    private GatewayAssinaturaEmpresa gatewayAssinaturaEmpresa;

    private GatewayAssinaturaEmpresa insertedGatewayAssinaturaEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GatewayAssinaturaEmpresa createEntity(EntityManager em) {
        GatewayAssinaturaEmpresa gatewayAssinaturaEmpresa = new GatewayAssinaturaEmpresa()
            .ativo(DEFAULT_ATIVO)
            .codigoAssinatura(DEFAULT_CODIGO_ASSINATURA);
        // Add required entity
        AssinaturaEmpresa assinaturaEmpresa;
        if (TestUtil.findAll(em, AssinaturaEmpresa.class).isEmpty()) {
            assinaturaEmpresa = AssinaturaEmpresaResourceIT.createEntity(em);
            em.persist(assinaturaEmpresa);
            em.flush();
        } else {
            assinaturaEmpresa = TestUtil.findAll(em, AssinaturaEmpresa.class).get(0);
        }
        gatewayAssinaturaEmpresa.setAssinaturaEmpresa(assinaturaEmpresa);
        // Add required entity
        GatewayPagamento gatewayPagamento;
        if (TestUtil.findAll(em, GatewayPagamento.class).isEmpty()) {
            gatewayPagamento = GatewayPagamentoResourceIT.createEntity(em);
            em.persist(gatewayPagamento);
            em.flush();
        } else {
            gatewayPagamento = TestUtil.findAll(em, GatewayPagamento.class).get(0);
        }
        gatewayAssinaturaEmpresa.setGatewayPagamento(gatewayPagamento);
        return gatewayAssinaturaEmpresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GatewayAssinaturaEmpresa createUpdatedEntity(EntityManager em) {
        GatewayAssinaturaEmpresa gatewayAssinaturaEmpresa = new GatewayAssinaturaEmpresa()
            .ativo(UPDATED_ATIVO)
            .codigoAssinatura(UPDATED_CODIGO_ASSINATURA);
        // Add required entity
        AssinaturaEmpresa assinaturaEmpresa;
        if (TestUtil.findAll(em, AssinaturaEmpresa.class).isEmpty()) {
            assinaturaEmpresa = AssinaturaEmpresaResourceIT.createUpdatedEntity(em);
            em.persist(assinaturaEmpresa);
            em.flush();
        } else {
            assinaturaEmpresa = TestUtil.findAll(em, AssinaturaEmpresa.class).get(0);
        }
        gatewayAssinaturaEmpresa.setAssinaturaEmpresa(assinaturaEmpresa);
        // Add required entity
        GatewayPagamento gatewayPagamento;
        if (TestUtil.findAll(em, GatewayPagamento.class).isEmpty()) {
            gatewayPagamento = GatewayPagamentoResourceIT.createUpdatedEntity(em);
            em.persist(gatewayPagamento);
            em.flush();
        } else {
            gatewayPagamento = TestUtil.findAll(em, GatewayPagamento.class).get(0);
        }
        gatewayAssinaturaEmpresa.setGatewayPagamento(gatewayPagamento);
        return gatewayAssinaturaEmpresa;
    }

    @BeforeEach
    public void initTest() {
        gatewayAssinaturaEmpresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedGatewayAssinaturaEmpresa != null) {
            gatewayAssinaturaEmpresaRepository.delete(insertedGatewayAssinaturaEmpresa);
            insertedGatewayAssinaturaEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createGatewayAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the GatewayAssinaturaEmpresa
        var returnedGatewayAssinaturaEmpresa = om.readValue(
            restGatewayAssinaturaEmpresaMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gatewayAssinaturaEmpresa))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            GatewayAssinaturaEmpresa.class
        );

        // Validate the GatewayAssinaturaEmpresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGatewayAssinaturaEmpresaUpdatableFieldsEquals(
            returnedGatewayAssinaturaEmpresa,
            getPersistedGatewayAssinaturaEmpresa(returnedGatewayAssinaturaEmpresa)
        );

        insertedGatewayAssinaturaEmpresa = returnedGatewayAssinaturaEmpresa;
    }

    @Test
    @Transactional
    void createGatewayAssinaturaEmpresaWithExistingId() throws Exception {
        // Create the GatewayAssinaturaEmpresa with an existing ID
        gatewayAssinaturaEmpresa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGatewayAssinaturaEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gatewayAssinaturaEmpresa)))
            .andExpect(status().isBadRequest());

        // Validate the GatewayAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGatewayAssinaturaEmpresas() throws Exception {
        // Initialize the database
        insertedGatewayAssinaturaEmpresa = gatewayAssinaturaEmpresaRepository.saveAndFlush(gatewayAssinaturaEmpresa);

        // Get all the gatewayAssinaturaEmpresaList
        restGatewayAssinaturaEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gatewayAssinaturaEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].codigoAssinatura").value(hasItem(DEFAULT_CODIGO_ASSINATURA)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGatewayAssinaturaEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(gatewayAssinaturaEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGatewayAssinaturaEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(gatewayAssinaturaEmpresaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGatewayAssinaturaEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(gatewayAssinaturaEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGatewayAssinaturaEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(gatewayAssinaturaEmpresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getGatewayAssinaturaEmpresa() throws Exception {
        // Initialize the database
        insertedGatewayAssinaturaEmpresa = gatewayAssinaturaEmpresaRepository.saveAndFlush(gatewayAssinaturaEmpresa);

        // Get the gatewayAssinaturaEmpresa
        restGatewayAssinaturaEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, gatewayAssinaturaEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gatewayAssinaturaEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.codigoAssinatura").value(DEFAULT_CODIGO_ASSINATURA));
    }

    @Test
    @Transactional
    void getNonExistingGatewayAssinaturaEmpresa() throws Exception {
        // Get the gatewayAssinaturaEmpresa
        restGatewayAssinaturaEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGatewayAssinaturaEmpresa() throws Exception {
        // Initialize the database
        insertedGatewayAssinaturaEmpresa = gatewayAssinaturaEmpresaRepository.saveAndFlush(gatewayAssinaturaEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gatewayAssinaturaEmpresa
        GatewayAssinaturaEmpresa updatedGatewayAssinaturaEmpresa = gatewayAssinaturaEmpresaRepository
            .findById(gatewayAssinaturaEmpresa.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedGatewayAssinaturaEmpresa are not directly saved in db
        em.detach(updatedGatewayAssinaturaEmpresa);
        updatedGatewayAssinaturaEmpresa.ativo(UPDATED_ATIVO).codigoAssinatura(UPDATED_CODIGO_ASSINATURA);

        restGatewayAssinaturaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGatewayAssinaturaEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGatewayAssinaturaEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the GatewayAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGatewayAssinaturaEmpresaToMatchAllProperties(updatedGatewayAssinaturaEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingGatewayAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gatewayAssinaturaEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGatewayAssinaturaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gatewayAssinaturaEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gatewayAssinaturaEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the GatewayAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGatewayAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gatewayAssinaturaEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGatewayAssinaturaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gatewayAssinaturaEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the GatewayAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGatewayAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gatewayAssinaturaEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGatewayAssinaturaEmpresaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gatewayAssinaturaEmpresa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GatewayAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGatewayAssinaturaEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedGatewayAssinaturaEmpresa = gatewayAssinaturaEmpresaRepository.saveAndFlush(gatewayAssinaturaEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gatewayAssinaturaEmpresa using partial update
        GatewayAssinaturaEmpresa partialUpdatedGatewayAssinaturaEmpresa = new GatewayAssinaturaEmpresa();
        partialUpdatedGatewayAssinaturaEmpresa.setId(gatewayAssinaturaEmpresa.getId());

        partialUpdatedGatewayAssinaturaEmpresa.codigoAssinatura(UPDATED_CODIGO_ASSINATURA);

        restGatewayAssinaturaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGatewayAssinaturaEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGatewayAssinaturaEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the GatewayAssinaturaEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGatewayAssinaturaEmpresaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGatewayAssinaturaEmpresa, gatewayAssinaturaEmpresa),
            getPersistedGatewayAssinaturaEmpresa(gatewayAssinaturaEmpresa)
        );
    }

    @Test
    @Transactional
    void fullUpdateGatewayAssinaturaEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedGatewayAssinaturaEmpresa = gatewayAssinaturaEmpresaRepository.saveAndFlush(gatewayAssinaturaEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gatewayAssinaturaEmpresa using partial update
        GatewayAssinaturaEmpresa partialUpdatedGatewayAssinaturaEmpresa = new GatewayAssinaturaEmpresa();
        partialUpdatedGatewayAssinaturaEmpresa.setId(gatewayAssinaturaEmpresa.getId());

        partialUpdatedGatewayAssinaturaEmpresa.ativo(UPDATED_ATIVO).codigoAssinatura(UPDATED_CODIGO_ASSINATURA);

        restGatewayAssinaturaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGatewayAssinaturaEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGatewayAssinaturaEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the GatewayAssinaturaEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGatewayAssinaturaEmpresaUpdatableFieldsEquals(
            partialUpdatedGatewayAssinaturaEmpresa,
            getPersistedGatewayAssinaturaEmpresa(partialUpdatedGatewayAssinaturaEmpresa)
        );
    }

    @Test
    @Transactional
    void patchNonExistingGatewayAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gatewayAssinaturaEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGatewayAssinaturaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gatewayAssinaturaEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gatewayAssinaturaEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the GatewayAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGatewayAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gatewayAssinaturaEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGatewayAssinaturaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gatewayAssinaturaEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the GatewayAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGatewayAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gatewayAssinaturaEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGatewayAssinaturaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(gatewayAssinaturaEmpresa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GatewayAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGatewayAssinaturaEmpresa() throws Exception {
        // Initialize the database
        insertedGatewayAssinaturaEmpresa = gatewayAssinaturaEmpresaRepository.saveAndFlush(gatewayAssinaturaEmpresa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the gatewayAssinaturaEmpresa
        restGatewayAssinaturaEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, gatewayAssinaturaEmpresa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return gatewayAssinaturaEmpresaRepository.count();
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

    protected GatewayAssinaturaEmpresa getPersistedGatewayAssinaturaEmpresa(GatewayAssinaturaEmpresa gatewayAssinaturaEmpresa) {
        return gatewayAssinaturaEmpresaRepository.findById(gatewayAssinaturaEmpresa.getId()).orElseThrow();
    }

    protected void assertPersistedGatewayAssinaturaEmpresaToMatchAllProperties(GatewayAssinaturaEmpresa expectedGatewayAssinaturaEmpresa) {
        assertGatewayAssinaturaEmpresaAllPropertiesEquals(
            expectedGatewayAssinaturaEmpresa,
            getPersistedGatewayAssinaturaEmpresa(expectedGatewayAssinaturaEmpresa)
        );
    }

    protected void assertPersistedGatewayAssinaturaEmpresaToMatchUpdatableProperties(
        GatewayAssinaturaEmpresa expectedGatewayAssinaturaEmpresa
    ) {
        assertGatewayAssinaturaEmpresaAllUpdatablePropertiesEquals(
            expectedGatewayAssinaturaEmpresa,
            getPersistedGatewayAssinaturaEmpresa(expectedGatewayAssinaturaEmpresa)
        );
    }
}
