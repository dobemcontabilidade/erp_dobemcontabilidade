package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.ImpostoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Esfera;
import com.dobemcontabilidade.domain.Imposto;
import com.dobemcontabilidade.repository.ImpostoRepository;
import com.dobemcontabilidade.service.ImpostoService;
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
 * Integration tests for the {@link ImpostoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ImpostoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/impostos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ImpostoRepository impostoRepository;

    @Mock
    private ImpostoRepository impostoRepositoryMock;

    @Mock
    private ImpostoService impostoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restImpostoMockMvc;

    private Imposto imposto;

    private Imposto insertedImposto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Imposto createEntity(EntityManager em) {
        Imposto imposto = new Imposto().nome(DEFAULT_NOME).sigla(DEFAULT_SIGLA).descricao(DEFAULT_DESCRICAO);
        // Add required entity
        Esfera esfera;
        if (TestUtil.findAll(em, Esfera.class).isEmpty()) {
            esfera = EsferaResourceIT.createEntity(em);
            em.persist(esfera);
            em.flush();
        } else {
            esfera = TestUtil.findAll(em, Esfera.class).get(0);
        }
        imposto.setEsfera(esfera);
        return imposto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Imposto createUpdatedEntity(EntityManager em) {
        Imposto imposto = new Imposto().nome(UPDATED_NOME).sigla(UPDATED_SIGLA).descricao(UPDATED_DESCRICAO);
        // Add required entity
        Esfera esfera;
        if (TestUtil.findAll(em, Esfera.class).isEmpty()) {
            esfera = EsferaResourceIT.createUpdatedEntity(em);
            em.persist(esfera);
            em.flush();
        } else {
            esfera = TestUtil.findAll(em, Esfera.class).get(0);
        }
        imposto.setEsfera(esfera);
        return imposto;
    }

    @BeforeEach
    public void initTest() {
        imposto = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedImposto != null) {
            impostoRepository.delete(insertedImposto);
            insertedImposto = null;
        }
    }

    @Test
    @Transactional
    void createImposto() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Imposto
        var returnedImposto = om.readValue(
            restImpostoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(imposto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Imposto.class
        );

        // Validate the Imposto in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertImpostoUpdatableFieldsEquals(returnedImposto, getPersistedImposto(returnedImposto));

        insertedImposto = returnedImposto;
    }

    @Test
    @Transactional
    void createImpostoWithExistingId() throws Exception {
        // Create the Imposto with an existing ID
        imposto.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restImpostoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(imposto)))
            .andExpect(status().isBadRequest());

        // Validate the Imposto in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllImpostos() throws Exception {
        // Initialize the database
        insertedImposto = impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList
        restImpostoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imposto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllImpostosWithEagerRelationshipsIsEnabled() throws Exception {
        when(impostoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restImpostoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(impostoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllImpostosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(impostoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restImpostoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(impostoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getImposto() throws Exception {
        // Initialize the database
        insertedImposto = impostoRepository.saveAndFlush(imposto);

        // Get the imposto
        restImpostoMockMvc
            .perform(get(ENTITY_API_URL_ID, imposto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(imposto.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getNonExistingImposto() throws Exception {
        // Get the imposto
        restImpostoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingImposto() throws Exception {
        // Initialize the database
        insertedImposto = impostoRepository.saveAndFlush(imposto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the imposto
        Imposto updatedImposto = impostoRepository.findById(imposto.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedImposto are not directly saved in db
        em.detach(updatedImposto);
        updatedImposto.nome(UPDATED_NOME).sigla(UPDATED_SIGLA).descricao(UPDATED_DESCRICAO);

        restImpostoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedImposto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedImposto))
            )
            .andExpect(status().isOk());

        // Validate the Imposto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedImpostoToMatchAllProperties(updatedImposto);
    }

    @Test
    @Transactional
    void putNonExistingImposto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        imposto.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImpostoMockMvc
            .perform(put(ENTITY_API_URL_ID, imposto.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(imposto)))
            .andExpect(status().isBadRequest());

        // Validate the Imposto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchImposto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        imposto.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImpostoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(imposto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Imposto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamImposto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        imposto.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImpostoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(imposto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Imposto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateImpostoWithPatch() throws Exception {
        // Initialize the database
        insertedImposto = impostoRepository.saveAndFlush(imposto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the imposto using partial update
        Imposto partialUpdatedImposto = new Imposto();
        partialUpdatedImposto.setId(imposto.getId());

        partialUpdatedImposto.nome(UPDATED_NOME).sigla(UPDATED_SIGLA).descricao(UPDATED_DESCRICAO);

        restImpostoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImposto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedImposto))
            )
            .andExpect(status().isOk());

        // Validate the Imposto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertImpostoUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedImposto, imposto), getPersistedImposto(imposto));
    }

    @Test
    @Transactional
    void fullUpdateImpostoWithPatch() throws Exception {
        // Initialize the database
        insertedImposto = impostoRepository.saveAndFlush(imposto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the imposto using partial update
        Imposto partialUpdatedImposto = new Imposto();
        partialUpdatedImposto.setId(imposto.getId());

        partialUpdatedImposto.nome(UPDATED_NOME).sigla(UPDATED_SIGLA).descricao(UPDATED_DESCRICAO);

        restImpostoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImposto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedImposto))
            )
            .andExpect(status().isOk());

        // Validate the Imposto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertImpostoUpdatableFieldsEquals(partialUpdatedImposto, getPersistedImposto(partialUpdatedImposto));
    }

    @Test
    @Transactional
    void patchNonExistingImposto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        imposto.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImpostoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, imposto.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(imposto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Imposto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchImposto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        imposto.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImpostoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(imposto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Imposto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamImposto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        imposto.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImpostoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(imposto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Imposto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteImposto() throws Exception {
        // Initialize the database
        insertedImposto = impostoRepository.saveAndFlush(imposto);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the imposto
        restImpostoMockMvc
            .perform(delete(ENTITY_API_URL_ID, imposto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return impostoRepository.count();
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

    protected Imposto getPersistedImposto(Imposto imposto) {
        return impostoRepository.findById(imposto.getId()).orElseThrow();
    }

    protected void assertPersistedImpostoToMatchAllProperties(Imposto expectedImposto) {
        assertImpostoAllPropertiesEquals(expectedImposto, getPersistedImposto(expectedImposto));
    }

    protected void assertPersistedImpostoToMatchUpdatableProperties(Imposto expectedImposto) {
        assertImpostoAllUpdatablePropertiesEquals(expectedImposto, getPersistedImposto(expectedImposto));
    }
}
