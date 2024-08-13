package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.InstituicaoEnsinoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Cidade;
import com.dobemcontabilidade.domain.InstituicaoEnsino;
import com.dobemcontabilidade.repository.InstituicaoEnsinoRepository;
import com.dobemcontabilidade.service.InstituicaoEnsinoService;
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
 * Integration tests for the {@link InstituicaoEnsinoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class InstituicaoEnsinoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CNPJ = "AAAAAAAAAA";
    private static final String UPDATED_CNPJ = "BBBBBBBBBB";

    private static final String DEFAULT_LOGRADOURO = "AAAAAAAAAA";
    private static final String UPDATED_LOGRADOURO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAA";
    private static final String UPDATED_NUMERO = "BBBBB";

    private static final String DEFAULT_COMPLEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";

    private static final String DEFAULT_CEP = "AAAAAAAAA";
    private static final String UPDATED_CEP = "BBBBBBBBB";

    private static final Boolean DEFAULT_PRINCIPAL = false;
    private static final Boolean UPDATED_PRINCIPAL = true;

    private static final String ENTITY_API_URL = "/api/instituicao-ensinos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InstituicaoEnsinoRepository instituicaoEnsinoRepository;

    @Mock
    private InstituicaoEnsinoRepository instituicaoEnsinoRepositoryMock;

    @Mock
    private InstituicaoEnsinoService instituicaoEnsinoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInstituicaoEnsinoMockMvc;

    private InstituicaoEnsino instituicaoEnsino;

    private InstituicaoEnsino insertedInstituicaoEnsino;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InstituicaoEnsino createEntity(EntityManager em) {
        InstituicaoEnsino instituicaoEnsino = new InstituicaoEnsino()
            .nome(DEFAULT_NOME)
            .cnpj(DEFAULT_CNPJ)
            .logradouro(DEFAULT_LOGRADOURO)
            .numero(DEFAULT_NUMERO)
            .complemento(DEFAULT_COMPLEMENTO)
            .bairro(DEFAULT_BAIRRO)
            .cep(DEFAULT_CEP)
            .principal(DEFAULT_PRINCIPAL);
        // Add required entity
        Cidade cidade;
        if (TestUtil.findAll(em, Cidade.class).isEmpty()) {
            cidade = CidadeResourceIT.createEntity(em);
            em.persist(cidade);
            em.flush();
        } else {
            cidade = TestUtil.findAll(em, Cidade.class).get(0);
        }
        instituicaoEnsino.setCidade(cidade);
        return instituicaoEnsino;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InstituicaoEnsino createUpdatedEntity(EntityManager em) {
        InstituicaoEnsino instituicaoEnsino = new InstituicaoEnsino()
            .nome(UPDATED_NOME)
            .cnpj(UPDATED_CNPJ)
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .bairro(UPDATED_BAIRRO)
            .cep(UPDATED_CEP)
            .principal(UPDATED_PRINCIPAL);
        // Add required entity
        Cidade cidade;
        if (TestUtil.findAll(em, Cidade.class).isEmpty()) {
            cidade = CidadeResourceIT.createUpdatedEntity(em);
            em.persist(cidade);
            em.flush();
        } else {
            cidade = TestUtil.findAll(em, Cidade.class).get(0);
        }
        instituicaoEnsino.setCidade(cidade);
        return instituicaoEnsino;
    }

    @BeforeEach
    public void initTest() {
        instituicaoEnsino = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedInstituicaoEnsino != null) {
            instituicaoEnsinoRepository.delete(insertedInstituicaoEnsino);
            insertedInstituicaoEnsino = null;
        }
    }

    @Test
    @Transactional
    void createInstituicaoEnsino() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the InstituicaoEnsino
        var returnedInstituicaoEnsino = om.readValue(
            restInstituicaoEnsinoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(instituicaoEnsino)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            InstituicaoEnsino.class
        );

        // Validate the InstituicaoEnsino in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInstituicaoEnsinoUpdatableFieldsEquals(returnedInstituicaoEnsino, getPersistedInstituicaoEnsino(returnedInstituicaoEnsino));

        insertedInstituicaoEnsino = returnedInstituicaoEnsino;
    }

    @Test
    @Transactional
    void createInstituicaoEnsinoWithExistingId() throws Exception {
        // Create the InstituicaoEnsino with an existing ID
        instituicaoEnsino.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstituicaoEnsinoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(instituicaoEnsino)))
            .andExpect(status().isBadRequest());

        // Validate the InstituicaoEnsino in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        instituicaoEnsino.setNome(null);

        // Create the InstituicaoEnsino, which fails.

        restInstituicaoEnsinoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(instituicaoEnsino)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinos() throws Exception {
        // Initialize the database
        insertedInstituicaoEnsino = instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList
        restInstituicaoEnsinoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instituicaoEnsino.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ)))
            .andExpect(jsonPath("$.[*].logradouro").value(hasItem(DEFAULT_LOGRADOURO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)))
            .andExpect(jsonPath("$.[*].principal").value(hasItem(DEFAULT_PRINCIPAL.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInstituicaoEnsinosWithEagerRelationshipsIsEnabled() throws Exception {
        when(instituicaoEnsinoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInstituicaoEnsinoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(instituicaoEnsinoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInstituicaoEnsinosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(instituicaoEnsinoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInstituicaoEnsinoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(instituicaoEnsinoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getInstituicaoEnsino() throws Exception {
        // Initialize the database
        insertedInstituicaoEnsino = instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get the instituicaoEnsino
        restInstituicaoEnsinoMockMvc
            .perform(get(ENTITY_API_URL_ID, instituicaoEnsino.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(instituicaoEnsino.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ))
            .andExpect(jsonPath("$.logradouro").value(DEFAULT_LOGRADOURO))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.complemento").value(DEFAULT_COMPLEMENTO))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP))
            .andExpect(jsonPath("$.principal").value(DEFAULT_PRINCIPAL.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingInstituicaoEnsino() throws Exception {
        // Get the instituicaoEnsino
        restInstituicaoEnsinoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInstituicaoEnsino() throws Exception {
        // Initialize the database
        insertedInstituicaoEnsino = instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the instituicaoEnsino
        InstituicaoEnsino updatedInstituicaoEnsino = instituicaoEnsinoRepository.findById(instituicaoEnsino.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInstituicaoEnsino are not directly saved in db
        em.detach(updatedInstituicaoEnsino);
        updatedInstituicaoEnsino
            .nome(UPDATED_NOME)
            .cnpj(UPDATED_CNPJ)
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .bairro(UPDATED_BAIRRO)
            .cep(UPDATED_CEP)
            .principal(UPDATED_PRINCIPAL);

        restInstituicaoEnsinoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInstituicaoEnsino.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedInstituicaoEnsino))
            )
            .andExpect(status().isOk());

        // Validate the InstituicaoEnsino in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInstituicaoEnsinoToMatchAllProperties(updatedInstituicaoEnsino);
    }

    @Test
    @Transactional
    void putNonExistingInstituicaoEnsino() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        instituicaoEnsino.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstituicaoEnsinoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, instituicaoEnsino.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(instituicaoEnsino))
            )
            .andExpect(status().isBadRequest());

        // Validate the InstituicaoEnsino in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInstituicaoEnsino() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        instituicaoEnsino.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInstituicaoEnsinoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(instituicaoEnsino))
            )
            .andExpect(status().isBadRequest());

        // Validate the InstituicaoEnsino in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInstituicaoEnsino() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        instituicaoEnsino.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInstituicaoEnsinoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(instituicaoEnsino)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the InstituicaoEnsino in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInstituicaoEnsinoWithPatch() throws Exception {
        // Initialize the database
        insertedInstituicaoEnsino = instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the instituicaoEnsino using partial update
        InstituicaoEnsino partialUpdatedInstituicaoEnsino = new InstituicaoEnsino();
        partialUpdatedInstituicaoEnsino.setId(instituicaoEnsino.getId());

        partialUpdatedInstituicaoEnsino
            .cnpj(UPDATED_CNPJ)
            .logradouro(UPDATED_LOGRADOURO)
            .complemento(UPDATED_COMPLEMENTO)
            .cep(UPDATED_CEP)
            .principal(UPDATED_PRINCIPAL);

        restInstituicaoEnsinoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInstituicaoEnsino.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInstituicaoEnsino))
            )
            .andExpect(status().isOk());

        // Validate the InstituicaoEnsino in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInstituicaoEnsinoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInstituicaoEnsino, instituicaoEnsino),
            getPersistedInstituicaoEnsino(instituicaoEnsino)
        );
    }

    @Test
    @Transactional
    void fullUpdateInstituicaoEnsinoWithPatch() throws Exception {
        // Initialize the database
        insertedInstituicaoEnsino = instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the instituicaoEnsino using partial update
        InstituicaoEnsino partialUpdatedInstituicaoEnsino = new InstituicaoEnsino();
        partialUpdatedInstituicaoEnsino.setId(instituicaoEnsino.getId());

        partialUpdatedInstituicaoEnsino
            .nome(UPDATED_NOME)
            .cnpj(UPDATED_CNPJ)
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .bairro(UPDATED_BAIRRO)
            .cep(UPDATED_CEP)
            .principal(UPDATED_PRINCIPAL);

        restInstituicaoEnsinoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInstituicaoEnsino.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInstituicaoEnsino))
            )
            .andExpect(status().isOk());

        // Validate the InstituicaoEnsino in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInstituicaoEnsinoUpdatableFieldsEquals(
            partialUpdatedInstituicaoEnsino,
            getPersistedInstituicaoEnsino(partialUpdatedInstituicaoEnsino)
        );
    }

    @Test
    @Transactional
    void patchNonExistingInstituicaoEnsino() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        instituicaoEnsino.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstituicaoEnsinoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, instituicaoEnsino.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(instituicaoEnsino))
            )
            .andExpect(status().isBadRequest());

        // Validate the InstituicaoEnsino in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInstituicaoEnsino() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        instituicaoEnsino.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInstituicaoEnsinoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(instituicaoEnsino))
            )
            .andExpect(status().isBadRequest());

        // Validate the InstituicaoEnsino in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInstituicaoEnsino() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        instituicaoEnsino.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInstituicaoEnsinoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(instituicaoEnsino)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the InstituicaoEnsino in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInstituicaoEnsino() throws Exception {
        // Initialize the database
        insertedInstituicaoEnsino = instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the instituicaoEnsino
        restInstituicaoEnsinoMockMvc
            .perform(delete(ENTITY_API_URL_ID, instituicaoEnsino.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return instituicaoEnsinoRepository.count();
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

    protected InstituicaoEnsino getPersistedInstituicaoEnsino(InstituicaoEnsino instituicaoEnsino) {
        return instituicaoEnsinoRepository.findById(instituicaoEnsino.getId()).orElseThrow();
    }

    protected void assertPersistedInstituicaoEnsinoToMatchAllProperties(InstituicaoEnsino expectedInstituicaoEnsino) {
        assertInstituicaoEnsinoAllPropertiesEquals(expectedInstituicaoEnsino, getPersistedInstituicaoEnsino(expectedInstituicaoEnsino));
    }

    protected void assertPersistedInstituicaoEnsinoToMatchUpdatableProperties(InstituicaoEnsino expectedInstituicaoEnsino) {
        assertInstituicaoEnsinoAllUpdatablePropertiesEquals(
            expectedInstituicaoEnsino,
            getPersistedInstituicaoEnsino(expectedInstituicaoEnsino)
        );
    }
}
