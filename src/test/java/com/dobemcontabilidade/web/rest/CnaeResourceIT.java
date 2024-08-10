package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.CnaeAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Cnae;
import com.dobemcontabilidade.repository.CnaeRepository;
import com.dobemcontabilidade.service.dto.CnaeDTO;
import com.dobemcontabilidade.service.mapper.CnaeMapper;
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
 * Integration tests for the {@link CnaeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CnaeResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANEXO = 1;
    private static final Integer UPDATED_ANEXO = 2;

    private static final Boolean DEFAULT_ATENDIDO_FREEMIUM = false;
    private static final Boolean UPDATED_ATENDIDO_FREEMIUM = true;

    private static final Boolean DEFAULT_ATENDIDO = false;
    private static final Boolean UPDATED_ATENDIDO = true;

    private static final Boolean DEFAULT_OPTANTE_SIMPLES = false;
    private static final Boolean UPDATED_OPTANTE_SIMPLES = true;

    private static final String DEFAULT_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cnaes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CnaeRepository cnaeRepository;

    @Autowired
    private CnaeMapper cnaeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCnaeMockMvc;

    private Cnae cnae;

    private Cnae insertedCnae;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cnae createEntity(EntityManager em) {
        Cnae cnae = new Cnae()
            .codigo(DEFAULT_CODIGO)
            .descricao(DEFAULT_DESCRICAO)
            .anexo(DEFAULT_ANEXO)
            .atendidoFreemium(DEFAULT_ATENDIDO_FREEMIUM)
            .atendido(DEFAULT_ATENDIDO)
            .optanteSimples(DEFAULT_OPTANTE_SIMPLES)
            .categoria(DEFAULT_CATEGORIA);
        return cnae;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cnae createUpdatedEntity(EntityManager em) {
        Cnae cnae = new Cnae()
            .codigo(UPDATED_CODIGO)
            .descricao(UPDATED_DESCRICAO)
            .anexo(UPDATED_ANEXO)
            .atendidoFreemium(UPDATED_ATENDIDO_FREEMIUM)
            .atendido(UPDATED_ATENDIDO)
            .optanteSimples(UPDATED_OPTANTE_SIMPLES)
            .categoria(UPDATED_CATEGORIA);
        return cnae;
    }

    @BeforeEach
    public void initTest() {
        cnae = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedCnae != null) {
            cnaeRepository.delete(insertedCnae);
            insertedCnae = null;
        }
    }

    @Test
    @Transactional
    void createCnae() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Cnae
        CnaeDTO cnaeDTO = cnaeMapper.toDto(cnae);
        var returnedCnaeDTO = om.readValue(
            restCnaeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cnaeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CnaeDTO.class
        );

        // Validate the Cnae in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCnae = cnaeMapper.toEntity(returnedCnaeDTO);
        assertCnaeUpdatableFieldsEquals(returnedCnae, getPersistedCnae(returnedCnae));

        insertedCnae = returnedCnae;
    }

    @Test
    @Transactional
    void createCnaeWithExistingId() throws Exception {
        // Create the Cnae with an existing ID
        cnae.setId(1L);
        CnaeDTO cnaeDTO = cnaeMapper.toDto(cnae);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCnaeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cnaeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cnae in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodigoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cnae.setCodigo(null);

        // Create the Cnae, which fails.
        CnaeDTO cnaeDTO = cnaeMapper.toDto(cnae);

        restCnaeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cnaeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCnaes() throws Exception {
        // Initialize the database
        insertedCnae = cnaeRepository.saveAndFlush(cnae);

        // Get all the cnaeList
        restCnaeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cnae.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].anexo").value(hasItem(DEFAULT_ANEXO)))
            .andExpect(jsonPath("$.[*].atendidoFreemium").value(hasItem(DEFAULT_ATENDIDO_FREEMIUM.booleanValue())))
            .andExpect(jsonPath("$.[*].atendido").value(hasItem(DEFAULT_ATENDIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].optanteSimples").value(hasItem(DEFAULT_OPTANTE_SIMPLES.booleanValue())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA)));
    }

    @Test
    @Transactional
    void getCnae() throws Exception {
        // Initialize the database
        insertedCnae = cnaeRepository.saveAndFlush(cnae);

        // Get the cnae
        restCnaeMockMvc
            .perform(get(ENTITY_API_URL_ID, cnae.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cnae.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.anexo").value(DEFAULT_ANEXO))
            .andExpect(jsonPath("$.atendidoFreemium").value(DEFAULT_ATENDIDO_FREEMIUM.booleanValue()))
            .andExpect(jsonPath("$.atendido").value(DEFAULT_ATENDIDO.booleanValue()))
            .andExpect(jsonPath("$.optanteSimples").value(DEFAULT_OPTANTE_SIMPLES.booleanValue()))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA));
    }

    @Test
    @Transactional
    void getNonExistingCnae() throws Exception {
        // Get the cnae
        restCnaeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCnae() throws Exception {
        // Initialize the database
        insertedCnae = cnaeRepository.saveAndFlush(cnae);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cnae
        Cnae updatedCnae = cnaeRepository.findById(cnae.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCnae are not directly saved in db
        em.detach(updatedCnae);
        updatedCnae
            .codigo(UPDATED_CODIGO)
            .descricao(UPDATED_DESCRICAO)
            .anexo(UPDATED_ANEXO)
            .atendidoFreemium(UPDATED_ATENDIDO_FREEMIUM)
            .atendido(UPDATED_ATENDIDO)
            .optanteSimples(UPDATED_OPTANTE_SIMPLES)
            .categoria(UPDATED_CATEGORIA);
        CnaeDTO cnaeDTO = cnaeMapper.toDto(updatedCnae);

        restCnaeMockMvc
            .perform(put(ENTITY_API_URL_ID, cnaeDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cnaeDTO)))
            .andExpect(status().isOk());

        // Validate the Cnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCnaeToMatchAllProperties(updatedCnae);
    }

    @Test
    @Transactional
    void putNonExistingCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cnae.setId(longCount.incrementAndGet());

        // Create the Cnae
        CnaeDTO cnaeDTO = cnaeMapper.toDto(cnae);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCnaeMockMvc
            .perform(put(ENTITY_API_URL_ID, cnaeDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cnaeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cnae.setId(longCount.incrementAndGet());

        // Create the Cnae
        CnaeDTO cnaeDTO = cnaeMapper.toDto(cnae);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCnaeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cnaeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cnae.setId(longCount.incrementAndGet());

        // Create the Cnae
        CnaeDTO cnaeDTO = cnaeMapper.toDto(cnae);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCnaeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cnaeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCnaeWithPatch() throws Exception {
        // Initialize the database
        insertedCnae = cnaeRepository.saveAndFlush(cnae);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cnae using partial update
        Cnae partialUpdatedCnae = new Cnae();
        partialUpdatedCnae.setId(cnae.getId());

        partialUpdatedCnae
            .descricao(UPDATED_DESCRICAO)
            .anexo(UPDATED_ANEXO)
            .atendidoFreemium(UPDATED_ATENDIDO_FREEMIUM)
            .optanteSimples(UPDATED_OPTANTE_SIMPLES);

        restCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCnae.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCnae))
            )
            .andExpect(status().isOk());

        // Validate the Cnae in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCnaeUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCnae, cnae), getPersistedCnae(cnae));
    }

    @Test
    @Transactional
    void fullUpdateCnaeWithPatch() throws Exception {
        // Initialize the database
        insertedCnae = cnaeRepository.saveAndFlush(cnae);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cnae using partial update
        Cnae partialUpdatedCnae = new Cnae();
        partialUpdatedCnae.setId(cnae.getId());

        partialUpdatedCnae
            .codigo(UPDATED_CODIGO)
            .descricao(UPDATED_DESCRICAO)
            .anexo(UPDATED_ANEXO)
            .atendidoFreemium(UPDATED_ATENDIDO_FREEMIUM)
            .atendido(UPDATED_ATENDIDO)
            .optanteSimples(UPDATED_OPTANTE_SIMPLES)
            .categoria(UPDATED_CATEGORIA);

        restCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCnae.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCnae))
            )
            .andExpect(status().isOk());

        // Validate the Cnae in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCnaeUpdatableFieldsEquals(partialUpdatedCnae, getPersistedCnae(partialUpdatedCnae));
    }

    @Test
    @Transactional
    void patchNonExistingCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cnae.setId(longCount.incrementAndGet());

        // Create the Cnae
        CnaeDTO cnaeDTO = cnaeMapper.toDto(cnae);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cnaeDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cnaeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cnae.setId(longCount.incrementAndGet());

        // Create the Cnae
        CnaeDTO cnaeDTO = cnaeMapper.toDto(cnae);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cnaeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cnae.setId(longCount.incrementAndGet());

        // Create the Cnae
        CnaeDTO cnaeDTO = cnaeMapper.toDto(cnae);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCnaeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cnaeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCnae() throws Exception {
        // Initialize the database
        insertedCnae = cnaeRepository.saveAndFlush(cnae);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cnae
        restCnaeMockMvc
            .perform(delete(ENTITY_API_URL_ID, cnae.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cnaeRepository.count();
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

    protected Cnae getPersistedCnae(Cnae cnae) {
        return cnaeRepository.findById(cnae.getId()).orElseThrow();
    }

    protected void assertPersistedCnaeToMatchAllProperties(Cnae expectedCnae) {
        assertCnaeAllPropertiesEquals(expectedCnae, getPersistedCnae(expectedCnae));
    }

    protected void assertPersistedCnaeToMatchUpdatableProperties(Cnae expectedCnae) {
        assertCnaeAllUpdatablePropertiesEquals(expectedCnae, getPersistedCnae(expectedCnae));
    }
}
