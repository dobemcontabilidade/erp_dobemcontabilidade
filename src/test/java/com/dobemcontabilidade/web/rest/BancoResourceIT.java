package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.BancoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Banco;
import com.dobemcontabilidade.repository.BancoRepository;
import com.dobemcontabilidade.service.dto.BancoDTO;
import com.dobemcontabilidade.service.mapper.BancoMapper;
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
 * Integration tests for the {@link BancoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BancoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bancos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BancoRepository bancoRepository;

    @Autowired
    private BancoMapper bancoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBancoMockMvc;

    private Banco banco;

    private Banco insertedBanco;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Banco createEntity(EntityManager em) {
        Banco banco = new Banco().nome(DEFAULT_NOME).codigo(DEFAULT_CODIGO);
        return banco;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Banco createUpdatedEntity(EntityManager em) {
        Banco banco = new Banco().nome(UPDATED_NOME).codigo(UPDATED_CODIGO);
        return banco;
    }

    @BeforeEach
    public void initTest() {
        banco = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedBanco != null) {
            bancoRepository.delete(insertedBanco);
            insertedBanco = null;
        }
    }

    @Test
    @Transactional
    void createBanco() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Banco
        BancoDTO bancoDTO = bancoMapper.toDto(banco);
        var returnedBancoDTO = om.readValue(
            restBancoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bancoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BancoDTO.class
        );

        // Validate the Banco in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBanco = bancoMapper.toEntity(returnedBancoDTO);
        assertBancoUpdatableFieldsEquals(returnedBanco, getPersistedBanco(returnedBanco));

        insertedBanco = returnedBanco;
    }

    @Test
    @Transactional
    void createBancoWithExistingId() throws Exception {
        // Create the Banco with an existing ID
        banco.setId(1L);
        BancoDTO bancoDTO = bancoMapper.toDto(banco);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBancoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bancoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Banco in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        banco.setNome(null);

        // Create the Banco, which fails.
        BancoDTO bancoDTO = bancoMapper.toDto(banco);

        restBancoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bancoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodigoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        banco.setCodigo(null);

        // Create the Banco, which fails.
        BancoDTO bancoDTO = bancoMapper.toDto(banco);

        restBancoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bancoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBancos() throws Exception {
        // Initialize the database
        insertedBanco = bancoRepository.saveAndFlush(banco);

        // Get all the bancoList
        restBancoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banco.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)));
    }

    @Test
    @Transactional
    void getBanco() throws Exception {
        // Initialize the database
        insertedBanco = bancoRepository.saveAndFlush(banco);

        // Get the banco
        restBancoMockMvc
            .perform(get(ENTITY_API_URL_ID, banco.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(banco.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO));
    }

    @Test
    @Transactional
    void getNonExistingBanco() throws Exception {
        // Get the banco
        restBancoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBanco() throws Exception {
        // Initialize the database
        insertedBanco = bancoRepository.saveAndFlush(banco);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the banco
        Banco updatedBanco = bancoRepository.findById(banco.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBanco are not directly saved in db
        em.detach(updatedBanco);
        updatedBanco.nome(UPDATED_NOME).codigo(UPDATED_CODIGO);
        BancoDTO bancoDTO = bancoMapper.toDto(updatedBanco);

        restBancoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bancoDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bancoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Banco in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBancoToMatchAllProperties(updatedBanco);
    }

    @Test
    @Transactional
    void putNonExistingBanco() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        banco.setId(longCount.incrementAndGet());

        // Create the Banco
        BancoDTO bancoDTO = bancoMapper.toDto(banco);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBancoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bancoDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bancoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banco in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBanco() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        banco.setId(longCount.incrementAndGet());

        // Create the Banco
        BancoDTO bancoDTO = bancoMapper.toDto(banco);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBancoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bancoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banco in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBanco() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        banco.setId(longCount.incrementAndGet());

        // Create the Banco
        BancoDTO bancoDTO = bancoMapper.toDto(banco);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBancoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bancoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Banco in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBancoWithPatch() throws Exception {
        // Initialize the database
        insertedBanco = bancoRepository.saveAndFlush(banco);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the banco using partial update
        Banco partialUpdatedBanco = new Banco();
        partialUpdatedBanco.setId(banco.getId());

        restBancoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBanco.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBanco))
            )
            .andExpect(status().isOk());

        // Validate the Banco in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBancoUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBanco, banco), getPersistedBanco(banco));
    }

    @Test
    @Transactional
    void fullUpdateBancoWithPatch() throws Exception {
        // Initialize the database
        insertedBanco = bancoRepository.saveAndFlush(banco);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the banco using partial update
        Banco partialUpdatedBanco = new Banco();
        partialUpdatedBanco.setId(banco.getId());

        partialUpdatedBanco.nome(UPDATED_NOME).codigo(UPDATED_CODIGO);

        restBancoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBanco.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBanco))
            )
            .andExpect(status().isOk());

        // Validate the Banco in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBancoUpdatableFieldsEquals(partialUpdatedBanco, getPersistedBanco(partialUpdatedBanco));
    }

    @Test
    @Transactional
    void patchNonExistingBanco() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        banco.setId(longCount.incrementAndGet());

        // Create the Banco
        BancoDTO bancoDTO = bancoMapper.toDto(banco);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBancoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bancoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bancoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banco in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBanco() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        banco.setId(longCount.incrementAndGet());

        // Create the Banco
        BancoDTO bancoDTO = bancoMapper.toDto(banco);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBancoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bancoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banco in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBanco() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        banco.setId(longCount.incrementAndGet());

        // Create the Banco
        BancoDTO bancoDTO = bancoMapper.toDto(banco);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBancoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bancoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Banco in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBanco() throws Exception {
        // Initialize the database
        insertedBanco = bancoRepository.saveAndFlush(banco);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the banco
        restBancoMockMvc
            .perform(delete(ENTITY_API_URL_ID, banco.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bancoRepository.count();
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

    protected Banco getPersistedBanco(Banco banco) {
        return bancoRepository.findById(banco.getId()).orElseThrow();
    }

    protected void assertPersistedBancoToMatchAllProperties(Banco expectedBanco) {
        assertBancoAllPropertiesEquals(expectedBanco, getPersistedBanco(expectedBanco));
    }

    protected void assertPersistedBancoToMatchUpdatableProperties(Banco expectedBanco) {
        assertBancoAllUpdatablePropertiesEquals(expectedBanco, getPersistedBanco(expectedBanco));
    }
}
