package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.BancoPessoaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Banco;
import com.dobemcontabilidade.domain.BancoPessoa;
import com.dobemcontabilidade.domain.Pessoa;
import com.dobemcontabilidade.domain.enumeration.TipoContaBancoEnum;
import com.dobemcontabilidade.repository.BancoPessoaRepository;
import com.dobemcontabilidade.service.BancoPessoaService;
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
 * Integration tests for the {@link BancoPessoaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BancoPessoaResourceIT {

    private static final String DEFAULT_AGENCIA = "AAAAAAAAAA";
    private static final String UPDATED_AGENCIA = "BBBBBBBBBB";

    private static final String DEFAULT_CONTA = "AAAAAAAAAA";
    private static final String UPDATED_CONTA = "BBBBBBBBBB";

    private static final TipoContaBancoEnum DEFAULT_TIPO_CONTA = TipoContaBancoEnum.CONTACORRENTE;
    private static final TipoContaBancoEnum UPDATED_TIPO_CONTA = TipoContaBancoEnum.CONTAPOUPANCA;

    private static final Boolean DEFAULT_PRINCIPAL = false;
    private static final Boolean UPDATED_PRINCIPAL = true;

    private static final String ENTITY_API_URL = "/api/banco-pessoas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BancoPessoaRepository bancoPessoaRepository;

    @Mock
    private BancoPessoaRepository bancoPessoaRepositoryMock;

    @Mock
    private BancoPessoaService bancoPessoaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBancoPessoaMockMvc;

    private BancoPessoa bancoPessoa;

    private BancoPessoa insertedBancoPessoa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BancoPessoa createEntity(EntityManager em) {
        BancoPessoa bancoPessoa = new BancoPessoa()
            .agencia(DEFAULT_AGENCIA)
            .conta(DEFAULT_CONTA)
            .tipoConta(DEFAULT_TIPO_CONTA)
            .principal(DEFAULT_PRINCIPAL);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        bancoPessoa.setPessoa(pessoa);
        // Add required entity
        Banco banco;
        if (TestUtil.findAll(em, Banco.class).isEmpty()) {
            banco = BancoResourceIT.createEntity(em);
            em.persist(banco);
            em.flush();
        } else {
            banco = TestUtil.findAll(em, Banco.class).get(0);
        }
        bancoPessoa.setBanco(banco);
        return bancoPessoa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BancoPessoa createUpdatedEntity(EntityManager em) {
        BancoPessoa bancoPessoa = new BancoPessoa()
            .agencia(UPDATED_AGENCIA)
            .conta(UPDATED_CONTA)
            .tipoConta(UPDATED_TIPO_CONTA)
            .principal(UPDATED_PRINCIPAL);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createUpdatedEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        bancoPessoa.setPessoa(pessoa);
        // Add required entity
        Banco banco;
        if (TestUtil.findAll(em, Banco.class).isEmpty()) {
            banco = BancoResourceIT.createUpdatedEntity(em);
            em.persist(banco);
            em.flush();
        } else {
            banco = TestUtil.findAll(em, Banco.class).get(0);
        }
        bancoPessoa.setBanco(banco);
        return bancoPessoa;
    }

    @BeforeEach
    public void initTest() {
        bancoPessoa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedBancoPessoa != null) {
            bancoPessoaRepository.delete(insertedBancoPessoa);
            insertedBancoPessoa = null;
        }
    }

    @Test
    @Transactional
    void createBancoPessoa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the BancoPessoa
        var returnedBancoPessoa = om.readValue(
            restBancoPessoaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bancoPessoa)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BancoPessoa.class
        );

        // Validate the BancoPessoa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBancoPessoaUpdatableFieldsEquals(returnedBancoPessoa, getPersistedBancoPessoa(returnedBancoPessoa));

        insertedBancoPessoa = returnedBancoPessoa;
    }

    @Test
    @Transactional
    void createBancoPessoaWithExistingId() throws Exception {
        // Create the BancoPessoa with an existing ID
        bancoPessoa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBancoPessoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bancoPessoa)))
            .andExpect(status().isBadRequest());

        // Validate the BancoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAgenciaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bancoPessoa.setAgencia(null);

        // Create the BancoPessoa, which fails.

        restBancoPessoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bancoPessoa)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bancoPessoa.setConta(null);

        // Create the BancoPessoa, which fails.

        restBancoPessoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bancoPessoa)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBancoPessoas() throws Exception {
        // Initialize the database
        insertedBancoPessoa = bancoPessoaRepository.saveAndFlush(bancoPessoa);

        // Get all the bancoPessoaList
        restBancoPessoaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bancoPessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].agencia").value(hasItem(DEFAULT_AGENCIA)))
            .andExpect(jsonPath("$.[*].conta").value(hasItem(DEFAULT_CONTA)))
            .andExpect(jsonPath("$.[*].tipoConta").value(hasItem(DEFAULT_TIPO_CONTA.toString())))
            .andExpect(jsonPath("$.[*].principal").value(hasItem(DEFAULT_PRINCIPAL.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBancoPessoasWithEagerRelationshipsIsEnabled() throws Exception {
        when(bancoPessoaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBancoPessoaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(bancoPessoaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBancoPessoasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(bancoPessoaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBancoPessoaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(bancoPessoaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getBancoPessoa() throws Exception {
        // Initialize the database
        insertedBancoPessoa = bancoPessoaRepository.saveAndFlush(bancoPessoa);

        // Get the bancoPessoa
        restBancoPessoaMockMvc
            .perform(get(ENTITY_API_URL_ID, bancoPessoa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bancoPessoa.getId().intValue()))
            .andExpect(jsonPath("$.agencia").value(DEFAULT_AGENCIA))
            .andExpect(jsonPath("$.conta").value(DEFAULT_CONTA))
            .andExpect(jsonPath("$.tipoConta").value(DEFAULT_TIPO_CONTA.toString()))
            .andExpect(jsonPath("$.principal").value(DEFAULT_PRINCIPAL.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingBancoPessoa() throws Exception {
        // Get the bancoPessoa
        restBancoPessoaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBancoPessoa() throws Exception {
        // Initialize the database
        insertedBancoPessoa = bancoPessoaRepository.saveAndFlush(bancoPessoa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bancoPessoa
        BancoPessoa updatedBancoPessoa = bancoPessoaRepository.findById(bancoPessoa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBancoPessoa are not directly saved in db
        em.detach(updatedBancoPessoa);
        updatedBancoPessoa.agencia(UPDATED_AGENCIA).conta(UPDATED_CONTA).tipoConta(UPDATED_TIPO_CONTA).principal(UPDATED_PRINCIPAL);

        restBancoPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBancoPessoa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBancoPessoa))
            )
            .andExpect(status().isOk());

        // Validate the BancoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBancoPessoaToMatchAllProperties(updatedBancoPessoa);
    }

    @Test
    @Transactional
    void putNonExistingBancoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bancoPessoa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBancoPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bancoPessoa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bancoPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the BancoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBancoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bancoPessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBancoPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bancoPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the BancoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBancoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bancoPessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBancoPessoaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bancoPessoa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BancoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBancoPessoaWithPatch() throws Exception {
        // Initialize the database
        insertedBancoPessoa = bancoPessoaRepository.saveAndFlush(bancoPessoa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bancoPessoa using partial update
        BancoPessoa partialUpdatedBancoPessoa = new BancoPessoa();
        partialUpdatedBancoPessoa.setId(bancoPessoa.getId());

        restBancoPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBancoPessoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBancoPessoa))
            )
            .andExpect(status().isOk());

        // Validate the BancoPessoa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBancoPessoaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBancoPessoa, bancoPessoa),
            getPersistedBancoPessoa(bancoPessoa)
        );
    }

    @Test
    @Transactional
    void fullUpdateBancoPessoaWithPatch() throws Exception {
        // Initialize the database
        insertedBancoPessoa = bancoPessoaRepository.saveAndFlush(bancoPessoa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bancoPessoa using partial update
        BancoPessoa partialUpdatedBancoPessoa = new BancoPessoa();
        partialUpdatedBancoPessoa.setId(bancoPessoa.getId());

        partialUpdatedBancoPessoa.agencia(UPDATED_AGENCIA).conta(UPDATED_CONTA).tipoConta(UPDATED_TIPO_CONTA).principal(UPDATED_PRINCIPAL);

        restBancoPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBancoPessoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBancoPessoa))
            )
            .andExpect(status().isOk());

        // Validate the BancoPessoa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBancoPessoaUpdatableFieldsEquals(partialUpdatedBancoPessoa, getPersistedBancoPessoa(partialUpdatedBancoPessoa));
    }

    @Test
    @Transactional
    void patchNonExistingBancoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bancoPessoa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBancoPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bancoPessoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bancoPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the BancoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBancoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bancoPessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBancoPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bancoPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the BancoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBancoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bancoPessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBancoPessoaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bancoPessoa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BancoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBancoPessoa() throws Exception {
        // Initialize the database
        insertedBancoPessoa = bancoPessoaRepository.saveAndFlush(bancoPessoa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bancoPessoa
        restBancoPessoaMockMvc
            .perform(delete(ENTITY_API_URL_ID, bancoPessoa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bancoPessoaRepository.count();
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

    protected BancoPessoa getPersistedBancoPessoa(BancoPessoa bancoPessoa) {
        return bancoPessoaRepository.findById(bancoPessoa.getId()).orElseThrow();
    }

    protected void assertPersistedBancoPessoaToMatchAllProperties(BancoPessoa expectedBancoPessoa) {
        assertBancoPessoaAllPropertiesEquals(expectedBancoPessoa, getPersistedBancoPessoa(expectedBancoPessoa));
    }

    protected void assertPersistedBancoPessoaToMatchUpdatableProperties(BancoPessoa expectedBancoPessoa) {
        assertBancoPessoaAllUpdatablePropertiesEquals(expectedBancoPessoa, getPersistedBancoPessoa(expectedBancoPessoa));
    }
}
