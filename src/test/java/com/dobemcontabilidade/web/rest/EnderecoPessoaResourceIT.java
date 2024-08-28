package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.EnderecoPessoaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Cidade;
import com.dobemcontabilidade.domain.EnderecoPessoa;
import com.dobemcontabilidade.domain.PessoaFisica;
import com.dobemcontabilidade.repository.EnderecoPessoaRepository;
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
 * Integration tests for the {@link EnderecoPessoaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EnderecoPessoaResourceIT {

    private static final String DEFAULT_LOGRADOURO = "AAAAAAAAAA";
    private static final String UPDATED_LOGRADOURO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";

    private static final String DEFAULT_CEP = "AAAAAAAAAA";
    private static final String UPDATED_CEP = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRINCIPAL = false;
    private static final Boolean UPDATED_PRINCIPAL = true;

    private static final String ENTITY_API_URL = "/api/endereco-pessoas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EnderecoPessoaRepository enderecoPessoaRepository;

    @Mock
    private EnderecoPessoaRepository enderecoPessoaRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnderecoPessoaMockMvc;

    private EnderecoPessoa enderecoPessoa;

    private EnderecoPessoa insertedEnderecoPessoa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnderecoPessoa createEntity(EntityManager em) {
        EnderecoPessoa enderecoPessoa = new EnderecoPessoa()
            .logradouro(DEFAULT_LOGRADOURO)
            .numero(DEFAULT_NUMERO)
            .complemento(DEFAULT_COMPLEMENTO)
            .bairro(DEFAULT_BAIRRO)
            .cep(DEFAULT_CEP)
            .principal(DEFAULT_PRINCIPAL);
        // Add required entity
        PessoaFisica pessoaFisica;
        if (TestUtil.findAll(em, PessoaFisica.class).isEmpty()) {
            pessoaFisica = PessoaFisicaResourceIT.createEntity(em);
            em.persist(pessoaFisica);
            em.flush();
        } else {
            pessoaFisica = TestUtil.findAll(em, PessoaFisica.class).get(0);
        }
        enderecoPessoa.setPessoa(pessoaFisica);
        // Add required entity
        Cidade cidade;
        if (TestUtil.findAll(em, Cidade.class).isEmpty()) {
            cidade = CidadeResourceIT.createEntity(em);
            em.persist(cidade);
            em.flush();
        } else {
            cidade = TestUtil.findAll(em, Cidade.class).get(0);
        }
        enderecoPessoa.setCidade(cidade);
        return enderecoPessoa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnderecoPessoa createUpdatedEntity(EntityManager em) {
        EnderecoPessoa enderecoPessoa = new EnderecoPessoa()
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .bairro(UPDATED_BAIRRO)
            .cep(UPDATED_CEP)
            .principal(UPDATED_PRINCIPAL);
        // Add required entity
        PessoaFisica pessoaFisica;
        if (TestUtil.findAll(em, PessoaFisica.class).isEmpty()) {
            pessoaFisica = PessoaFisicaResourceIT.createUpdatedEntity(em);
            em.persist(pessoaFisica);
            em.flush();
        } else {
            pessoaFisica = TestUtil.findAll(em, PessoaFisica.class).get(0);
        }
        enderecoPessoa.setPessoa(pessoaFisica);
        // Add required entity
        Cidade cidade;
        if (TestUtil.findAll(em, Cidade.class).isEmpty()) {
            cidade = CidadeResourceIT.createUpdatedEntity(em);
            em.persist(cidade);
            em.flush();
        } else {
            cidade = TestUtil.findAll(em, Cidade.class).get(0);
        }
        enderecoPessoa.setCidade(cidade);
        return enderecoPessoa;
    }

    @BeforeEach
    public void initTest() {
        enderecoPessoa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedEnderecoPessoa != null) {
            enderecoPessoaRepository.delete(insertedEnderecoPessoa);
            insertedEnderecoPessoa = null;
        }
    }

    @Test
    @Transactional
    void createEnderecoPessoa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EnderecoPessoa
        var returnedEnderecoPessoa = om.readValue(
            restEnderecoPessoaMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enderecoPessoa))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EnderecoPessoa.class
        );

        // Validate the EnderecoPessoa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEnderecoPessoaUpdatableFieldsEquals(returnedEnderecoPessoa, getPersistedEnderecoPessoa(returnedEnderecoPessoa));

        insertedEnderecoPessoa = returnedEnderecoPessoa;
    }

    @Test
    @Transactional
    void createEnderecoPessoaWithExistingId() throws Exception {
        // Create the EnderecoPessoa with an existing ID
        enderecoPessoa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnderecoPessoaMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enderecoPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnderecoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEnderecoPessoas() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList
        restEnderecoPessoaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enderecoPessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].logradouro").value(hasItem(DEFAULT_LOGRADOURO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)))
            .andExpect(jsonPath("$.[*].principal").value(hasItem(DEFAULT_PRINCIPAL.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEnderecoPessoasWithEagerRelationshipsIsEnabled() throws Exception {
        when(enderecoPessoaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEnderecoPessoaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(enderecoPessoaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEnderecoPessoasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(enderecoPessoaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEnderecoPessoaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(enderecoPessoaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEnderecoPessoa() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get the enderecoPessoa
        restEnderecoPessoaMockMvc
            .perform(get(ENTITY_API_URL_ID, enderecoPessoa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(enderecoPessoa.getId().intValue()))
            .andExpect(jsonPath("$.logradouro").value(DEFAULT_LOGRADOURO))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.complemento").value(DEFAULT_COMPLEMENTO))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP))
            .andExpect(jsonPath("$.principal").value(DEFAULT_PRINCIPAL.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingEnderecoPessoa() throws Exception {
        // Get the enderecoPessoa
        restEnderecoPessoaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEnderecoPessoa() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the enderecoPessoa
        EnderecoPessoa updatedEnderecoPessoa = enderecoPessoaRepository.findById(enderecoPessoa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEnderecoPessoa are not directly saved in db
        em.detach(updatedEnderecoPessoa);
        updatedEnderecoPessoa
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .bairro(UPDATED_BAIRRO)
            .cep(UPDATED_CEP)
            .principal(UPDATED_PRINCIPAL);

        restEnderecoPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEnderecoPessoa.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEnderecoPessoa))
            )
            .andExpect(status().isOk());

        // Validate the EnderecoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEnderecoPessoaToMatchAllProperties(updatedEnderecoPessoa);
    }

    @Test
    @Transactional
    void putNonExistingEnderecoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enderecoPessoa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnderecoPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, enderecoPessoa.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(enderecoPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnderecoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEnderecoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enderecoPessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnderecoPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(enderecoPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnderecoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEnderecoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enderecoPessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnderecoPessoaMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enderecoPessoa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EnderecoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEnderecoPessoaWithPatch() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the enderecoPessoa using partial update
        EnderecoPessoa partialUpdatedEnderecoPessoa = new EnderecoPessoa();
        partialUpdatedEnderecoPessoa.setId(enderecoPessoa.getId());

        partialUpdatedEnderecoPessoa.logradouro(UPDATED_LOGRADOURO).bairro(UPDATED_BAIRRO).cep(UPDATED_CEP).principal(UPDATED_PRINCIPAL);

        restEnderecoPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnderecoPessoa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEnderecoPessoa))
            )
            .andExpect(status().isOk());

        // Validate the EnderecoPessoa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEnderecoPessoaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEnderecoPessoa, enderecoPessoa),
            getPersistedEnderecoPessoa(enderecoPessoa)
        );
    }

    @Test
    @Transactional
    void fullUpdateEnderecoPessoaWithPatch() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the enderecoPessoa using partial update
        EnderecoPessoa partialUpdatedEnderecoPessoa = new EnderecoPessoa();
        partialUpdatedEnderecoPessoa.setId(enderecoPessoa.getId());

        partialUpdatedEnderecoPessoa
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .bairro(UPDATED_BAIRRO)
            .cep(UPDATED_CEP)
            .principal(UPDATED_PRINCIPAL);

        restEnderecoPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnderecoPessoa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEnderecoPessoa))
            )
            .andExpect(status().isOk());

        // Validate the EnderecoPessoa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEnderecoPessoaUpdatableFieldsEquals(partialUpdatedEnderecoPessoa, getPersistedEnderecoPessoa(partialUpdatedEnderecoPessoa));
    }

    @Test
    @Transactional
    void patchNonExistingEnderecoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enderecoPessoa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnderecoPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, enderecoPessoa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(enderecoPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnderecoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEnderecoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enderecoPessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnderecoPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(enderecoPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnderecoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEnderecoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enderecoPessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnderecoPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(enderecoPessoa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EnderecoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEnderecoPessoa() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the enderecoPessoa
        restEnderecoPessoaMockMvc
            .perform(delete(ENTITY_API_URL_ID, enderecoPessoa.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return enderecoPessoaRepository.count();
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

    protected EnderecoPessoa getPersistedEnderecoPessoa(EnderecoPessoa enderecoPessoa) {
        return enderecoPessoaRepository.findById(enderecoPessoa.getId()).orElseThrow();
    }

    protected void assertPersistedEnderecoPessoaToMatchAllProperties(EnderecoPessoa expectedEnderecoPessoa) {
        assertEnderecoPessoaAllPropertiesEquals(expectedEnderecoPessoa, getPersistedEnderecoPessoa(expectedEnderecoPessoa));
    }

    protected void assertPersistedEnderecoPessoaToMatchUpdatableProperties(EnderecoPessoa expectedEnderecoPessoa) {
        assertEnderecoPessoaAllUpdatablePropertiesEquals(expectedEnderecoPessoa, getPersistedEnderecoPessoa(expectedEnderecoPessoa));
    }
}
