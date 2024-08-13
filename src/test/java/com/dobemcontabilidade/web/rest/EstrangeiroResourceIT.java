package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.EstrangeiroAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Estrangeiro;
import com.dobemcontabilidade.domain.Funcionario;
import com.dobemcontabilidade.repository.EstrangeiroRepository;
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
 * Integration tests for the {@link EstrangeiroResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EstrangeiroResourceIT {

    private static final String DEFAULT_DATA_CHEGADA = "AAAAAAAAAA";
    private static final String UPDATED_DATA_CHEGADA = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_NATURALIZACAO = "AAAAAAAAAA";
    private static final String UPDATED_DATA_NATURALIZACAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CASADO_COM_BRASILEIRO = false;
    private static final Boolean UPDATED_CASADO_COM_BRASILEIRO = true;

    private static final Boolean DEFAULT_FILHOS_COM_BRASILEIRO = false;
    private static final Boolean UPDATED_FILHOS_COM_BRASILEIRO = true;

    private static final Boolean DEFAULT_CHECKED = false;
    private static final Boolean UPDATED_CHECKED = true;

    private static final String ENTITY_API_URL = "/api/estrangeiros";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EstrangeiroRepository estrangeiroRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstrangeiroMockMvc;

    private Estrangeiro estrangeiro;

    private Estrangeiro insertedEstrangeiro;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estrangeiro createEntity(EntityManager em) {
        Estrangeiro estrangeiro = new Estrangeiro()
            .dataChegada(DEFAULT_DATA_CHEGADA)
            .dataNaturalizacao(DEFAULT_DATA_NATURALIZACAO)
            .casadoComBrasileiro(DEFAULT_CASADO_COM_BRASILEIRO)
            .filhosComBrasileiro(DEFAULT_FILHOS_COM_BRASILEIRO)
            .checked(DEFAULT_CHECKED);
        // Add required entity
        Funcionario funcionario;
        if (TestUtil.findAll(em, Funcionario.class).isEmpty()) {
            funcionario = FuncionarioResourceIT.createEntity(em);
            em.persist(funcionario);
            em.flush();
        } else {
            funcionario = TestUtil.findAll(em, Funcionario.class).get(0);
        }
        estrangeiro.setFuncionario(funcionario);
        return estrangeiro;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estrangeiro createUpdatedEntity(EntityManager em) {
        Estrangeiro estrangeiro = new Estrangeiro()
            .dataChegada(UPDATED_DATA_CHEGADA)
            .dataNaturalizacao(UPDATED_DATA_NATURALIZACAO)
            .casadoComBrasileiro(UPDATED_CASADO_COM_BRASILEIRO)
            .filhosComBrasileiro(UPDATED_FILHOS_COM_BRASILEIRO)
            .checked(UPDATED_CHECKED);
        // Add required entity
        Funcionario funcionario;
        if (TestUtil.findAll(em, Funcionario.class).isEmpty()) {
            funcionario = FuncionarioResourceIT.createUpdatedEntity(em);
            em.persist(funcionario);
            em.flush();
        } else {
            funcionario = TestUtil.findAll(em, Funcionario.class).get(0);
        }
        estrangeiro.setFuncionario(funcionario);
        return estrangeiro;
    }

    @BeforeEach
    public void initTest() {
        estrangeiro = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedEstrangeiro != null) {
            estrangeiroRepository.delete(insertedEstrangeiro);
            insertedEstrangeiro = null;
        }
    }

    @Test
    @Transactional
    void createEstrangeiro() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Estrangeiro
        var returnedEstrangeiro = om.readValue(
            restEstrangeiroMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(estrangeiro)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Estrangeiro.class
        );

        // Validate the Estrangeiro in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEstrangeiroUpdatableFieldsEquals(returnedEstrangeiro, getPersistedEstrangeiro(returnedEstrangeiro));

        insertedEstrangeiro = returnedEstrangeiro;
    }

    @Test
    @Transactional
    void createEstrangeiroWithExistingId() throws Exception {
        // Create the Estrangeiro with an existing ID
        estrangeiro.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstrangeiroMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(estrangeiro)))
            .andExpect(status().isBadRequest());

        // Validate the Estrangeiro in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEstrangeiros() throws Exception {
        // Initialize the database
        insertedEstrangeiro = estrangeiroRepository.saveAndFlush(estrangeiro);

        // Get all the estrangeiroList
        restEstrangeiroMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estrangeiro.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataChegada").value(hasItem(DEFAULT_DATA_CHEGADA)))
            .andExpect(jsonPath("$.[*].dataNaturalizacao").value(hasItem(DEFAULT_DATA_NATURALIZACAO)))
            .andExpect(jsonPath("$.[*].casadoComBrasileiro").value(hasItem(DEFAULT_CASADO_COM_BRASILEIRO.booleanValue())))
            .andExpect(jsonPath("$.[*].filhosComBrasileiro").value(hasItem(DEFAULT_FILHOS_COM_BRASILEIRO.booleanValue())))
            .andExpect(jsonPath("$.[*].checked").value(hasItem(DEFAULT_CHECKED.booleanValue())));
    }

    @Test
    @Transactional
    void getEstrangeiro() throws Exception {
        // Initialize the database
        insertedEstrangeiro = estrangeiroRepository.saveAndFlush(estrangeiro);

        // Get the estrangeiro
        restEstrangeiroMockMvc
            .perform(get(ENTITY_API_URL_ID, estrangeiro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estrangeiro.getId().intValue()))
            .andExpect(jsonPath("$.dataChegada").value(DEFAULT_DATA_CHEGADA))
            .andExpect(jsonPath("$.dataNaturalizacao").value(DEFAULT_DATA_NATURALIZACAO))
            .andExpect(jsonPath("$.casadoComBrasileiro").value(DEFAULT_CASADO_COM_BRASILEIRO.booleanValue()))
            .andExpect(jsonPath("$.filhosComBrasileiro").value(DEFAULT_FILHOS_COM_BRASILEIRO.booleanValue()))
            .andExpect(jsonPath("$.checked").value(DEFAULT_CHECKED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingEstrangeiro() throws Exception {
        // Get the estrangeiro
        restEstrangeiroMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEstrangeiro() throws Exception {
        // Initialize the database
        insertedEstrangeiro = estrangeiroRepository.saveAndFlush(estrangeiro);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the estrangeiro
        Estrangeiro updatedEstrangeiro = estrangeiroRepository.findById(estrangeiro.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEstrangeiro are not directly saved in db
        em.detach(updatedEstrangeiro);
        updatedEstrangeiro
            .dataChegada(UPDATED_DATA_CHEGADA)
            .dataNaturalizacao(UPDATED_DATA_NATURALIZACAO)
            .casadoComBrasileiro(UPDATED_CASADO_COM_BRASILEIRO)
            .filhosComBrasileiro(UPDATED_FILHOS_COM_BRASILEIRO)
            .checked(UPDATED_CHECKED);

        restEstrangeiroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEstrangeiro.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEstrangeiro))
            )
            .andExpect(status().isOk());

        // Validate the Estrangeiro in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEstrangeiroToMatchAllProperties(updatedEstrangeiro);
    }

    @Test
    @Transactional
    void putNonExistingEstrangeiro() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        estrangeiro.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstrangeiroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estrangeiro.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(estrangeiro))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estrangeiro in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstrangeiro() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        estrangeiro.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstrangeiroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(estrangeiro))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estrangeiro in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstrangeiro() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        estrangeiro.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstrangeiroMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(estrangeiro)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Estrangeiro in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstrangeiroWithPatch() throws Exception {
        // Initialize the database
        insertedEstrangeiro = estrangeiroRepository.saveAndFlush(estrangeiro);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the estrangeiro using partial update
        Estrangeiro partialUpdatedEstrangeiro = new Estrangeiro();
        partialUpdatedEstrangeiro.setId(estrangeiro.getId());

        partialUpdatedEstrangeiro.casadoComBrasileiro(UPDATED_CASADO_COM_BRASILEIRO).checked(UPDATED_CHECKED);

        restEstrangeiroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstrangeiro.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEstrangeiro))
            )
            .andExpect(status().isOk());

        // Validate the Estrangeiro in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEstrangeiroUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEstrangeiro, estrangeiro),
            getPersistedEstrangeiro(estrangeiro)
        );
    }

    @Test
    @Transactional
    void fullUpdateEstrangeiroWithPatch() throws Exception {
        // Initialize the database
        insertedEstrangeiro = estrangeiroRepository.saveAndFlush(estrangeiro);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the estrangeiro using partial update
        Estrangeiro partialUpdatedEstrangeiro = new Estrangeiro();
        partialUpdatedEstrangeiro.setId(estrangeiro.getId());

        partialUpdatedEstrangeiro
            .dataChegada(UPDATED_DATA_CHEGADA)
            .dataNaturalizacao(UPDATED_DATA_NATURALIZACAO)
            .casadoComBrasileiro(UPDATED_CASADO_COM_BRASILEIRO)
            .filhosComBrasileiro(UPDATED_FILHOS_COM_BRASILEIRO)
            .checked(UPDATED_CHECKED);

        restEstrangeiroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstrangeiro.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEstrangeiro))
            )
            .andExpect(status().isOk());

        // Validate the Estrangeiro in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEstrangeiroUpdatableFieldsEquals(partialUpdatedEstrangeiro, getPersistedEstrangeiro(partialUpdatedEstrangeiro));
    }

    @Test
    @Transactional
    void patchNonExistingEstrangeiro() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        estrangeiro.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstrangeiroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, estrangeiro.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(estrangeiro))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estrangeiro in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstrangeiro() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        estrangeiro.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstrangeiroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(estrangeiro))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estrangeiro in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstrangeiro() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        estrangeiro.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstrangeiroMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(estrangeiro)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Estrangeiro in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstrangeiro() throws Exception {
        // Initialize the database
        insertedEstrangeiro = estrangeiroRepository.saveAndFlush(estrangeiro);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the estrangeiro
        restEstrangeiroMockMvc
            .perform(delete(ENTITY_API_URL_ID, estrangeiro.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return estrangeiroRepository.count();
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

    protected Estrangeiro getPersistedEstrangeiro(Estrangeiro estrangeiro) {
        return estrangeiroRepository.findById(estrangeiro.getId()).orElseThrow();
    }

    protected void assertPersistedEstrangeiroToMatchAllProperties(Estrangeiro expectedEstrangeiro) {
        assertEstrangeiroAllPropertiesEquals(expectedEstrangeiro, getPersistedEstrangeiro(expectedEstrangeiro));
    }

    protected void assertPersistedEstrangeiroToMatchUpdatableProperties(Estrangeiro expectedEstrangeiro) {
        assertEstrangeiroAllUpdatablePropertiesEquals(expectedEstrangeiro, getPersistedEstrangeiro(expectedEstrangeiro));
    }
}
