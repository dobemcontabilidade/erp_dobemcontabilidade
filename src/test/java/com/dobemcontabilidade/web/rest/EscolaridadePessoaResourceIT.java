package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.EscolaridadePessoaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Escolaridade;
import com.dobemcontabilidade.domain.EscolaridadePessoa;
import com.dobemcontabilidade.domain.Pessoa;
import com.dobemcontabilidade.repository.EscolaridadePessoaRepository;
import com.dobemcontabilidade.service.EscolaridadePessoaService;
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
 * Integration tests for the {@link EscolaridadePessoaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EscolaridadePessoaResourceIT {

    private static final String DEFAULT_NOME_INSTITUICAO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_INSTITUICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANO_CONCLUSAO = 1;
    private static final Integer UPDATED_ANO_CONCLUSAO = 2;

    private static final String DEFAULT_URL_COMPROVANTE_ESCOLARIDADE = "AAAAAAAAAA";
    private static final String UPDATED_URL_COMPROVANTE_ESCOLARIDADE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/escolaridade-pessoas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EscolaridadePessoaRepository escolaridadePessoaRepository;

    @Mock
    private EscolaridadePessoaRepository escolaridadePessoaRepositoryMock;

    @Mock
    private EscolaridadePessoaService escolaridadePessoaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEscolaridadePessoaMockMvc;

    private EscolaridadePessoa escolaridadePessoa;

    private EscolaridadePessoa insertedEscolaridadePessoa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EscolaridadePessoa createEntity(EntityManager em) {
        EscolaridadePessoa escolaridadePessoa = new EscolaridadePessoa()
            .nomeInstituicao(DEFAULT_NOME_INSTITUICAO)
            .anoConclusao(DEFAULT_ANO_CONCLUSAO)
            .urlComprovanteEscolaridade(DEFAULT_URL_COMPROVANTE_ESCOLARIDADE);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        escolaridadePessoa.setPessoa(pessoa);
        // Add required entity
        Escolaridade escolaridade;
        if (TestUtil.findAll(em, Escolaridade.class).isEmpty()) {
            escolaridade = EscolaridadeResourceIT.createEntity(em);
            em.persist(escolaridade);
            em.flush();
        } else {
            escolaridade = TestUtil.findAll(em, Escolaridade.class).get(0);
        }
        escolaridadePessoa.setEscolaridade(escolaridade);
        return escolaridadePessoa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EscolaridadePessoa createUpdatedEntity(EntityManager em) {
        EscolaridadePessoa escolaridadePessoa = new EscolaridadePessoa()
            .nomeInstituicao(UPDATED_NOME_INSTITUICAO)
            .anoConclusao(UPDATED_ANO_CONCLUSAO)
            .urlComprovanteEscolaridade(UPDATED_URL_COMPROVANTE_ESCOLARIDADE);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createUpdatedEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        escolaridadePessoa.setPessoa(pessoa);
        // Add required entity
        Escolaridade escolaridade;
        if (TestUtil.findAll(em, Escolaridade.class).isEmpty()) {
            escolaridade = EscolaridadeResourceIT.createUpdatedEntity(em);
            em.persist(escolaridade);
            em.flush();
        } else {
            escolaridade = TestUtil.findAll(em, Escolaridade.class).get(0);
        }
        escolaridadePessoa.setEscolaridade(escolaridade);
        return escolaridadePessoa;
    }

    @BeforeEach
    public void initTest() {
        escolaridadePessoa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedEscolaridadePessoa != null) {
            escolaridadePessoaRepository.delete(insertedEscolaridadePessoa);
            insertedEscolaridadePessoa = null;
        }
    }

    @Test
    @Transactional
    void createEscolaridadePessoa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EscolaridadePessoa
        var returnedEscolaridadePessoa = om.readValue(
            restEscolaridadePessoaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(escolaridadePessoa)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EscolaridadePessoa.class
        );

        // Validate the EscolaridadePessoa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEscolaridadePessoaUpdatableFieldsEquals(
            returnedEscolaridadePessoa,
            getPersistedEscolaridadePessoa(returnedEscolaridadePessoa)
        );

        insertedEscolaridadePessoa = returnedEscolaridadePessoa;
    }

    @Test
    @Transactional
    void createEscolaridadePessoaWithExistingId() throws Exception {
        // Create the EscolaridadePessoa with an existing ID
        escolaridadePessoa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEscolaridadePessoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(escolaridadePessoa)))
            .andExpect(status().isBadRequest());

        // Validate the EscolaridadePessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeInstituicaoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        escolaridadePessoa.setNomeInstituicao(null);

        // Create the EscolaridadePessoa, which fails.

        restEscolaridadePessoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(escolaridadePessoa)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEscolaridadePessoas() throws Exception {
        // Initialize the database
        insertedEscolaridadePessoa = escolaridadePessoaRepository.saveAndFlush(escolaridadePessoa);

        // Get all the escolaridadePessoaList
        restEscolaridadePessoaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(escolaridadePessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeInstituicao").value(hasItem(DEFAULT_NOME_INSTITUICAO)))
            .andExpect(jsonPath("$.[*].anoConclusao").value(hasItem(DEFAULT_ANO_CONCLUSAO)))
            .andExpect(jsonPath("$.[*].urlComprovanteEscolaridade").value(hasItem(DEFAULT_URL_COMPROVANTE_ESCOLARIDADE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEscolaridadePessoasWithEagerRelationshipsIsEnabled() throws Exception {
        when(escolaridadePessoaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEscolaridadePessoaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(escolaridadePessoaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEscolaridadePessoasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(escolaridadePessoaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEscolaridadePessoaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(escolaridadePessoaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEscolaridadePessoa() throws Exception {
        // Initialize the database
        insertedEscolaridadePessoa = escolaridadePessoaRepository.saveAndFlush(escolaridadePessoa);

        // Get the escolaridadePessoa
        restEscolaridadePessoaMockMvc
            .perform(get(ENTITY_API_URL_ID, escolaridadePessoa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(escolaridadePessoa.getId().intValue()))
            .andExpect(jsonPath("$.nomeInstituicao").value(DEFAULT_NOME_INSTITUICAO))
            .andExpect(jsonPath("$.anoConclusao").value(DEFAULT_ANO_CONCLUSAO))
            .andExpect(jsonPath("$.urlComprovanteEscolaridade").value(DEFAULT_URL_COMPROVANTE_ESCOLARIDADE));
    }

    @Test
    @Transactional
    void getNonExistingEscolaridadePessoa() throws Exception {
        // Get the escolaridadePessoa
        restEscolaridadePessoaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEscolaridadePessoa() throws Exception {
        // Initialize the database
        insertedEscolaridadePessoa = escolaridadePessoaRepository.saveAndFlush(escolaridadePessoa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the escolaridadePessoa
        EscolaridadePessoa updatedEscolaridadePessoa = escolaridadePessoaRepository.findById(escolaridadePessoa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEscolaridadePessoa are not directly saved in db
        em.detach(updatedEscolaridadePessoa);
        updatedEscolaridadePessoa
            .nomeInstituicao(UPDATED_NOME_INSTITUICAO)
            .anoConclusao(UPDATED_ANO_CONCLUSAO)
            .urlComprovanteEscolaridade(UPDATED_URL_COMPROVANTE_ESCOLARIDADE);

        restEscolaridadePessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEscolaridadePessoa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEscolaridadePessoa))
            )
            .andExpect(status().isOk());

        // Validate the EscolaridadePessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEscolaridadePessoaToMatchAllProperties(updatedEscolaridadePessoa);
    }

    @Test
    @Transactional
    void putNonExistingEscolaridadePessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        escolaridadePessoa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEscolaridadePessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, escolaridadePessoa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(escolaridadePessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the EscolaridadePessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEscolaridadePessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        escolaridadePessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEscolaridadePessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(escolaridadePessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the EscolaridadePessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEscolaridadePessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        escolaridadePessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEscolaridadePessoaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(escolaridadePessoa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EscolaridadePessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEscolaridadePessoaWithPatch() throws Exception {
        // Initialize the database
        insertedEscolaridadePessoa = escolaridadePessoaRepository.saveAndFlush(escolaridadePessoa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the escolaridadePessoa using partial update
        EscolaridadePessoa partialUpdatedEscolaridadePessoa = new EscolaridadePessoa();
        partialUpdatedEscolaridadePessoa.setId(escolaridadePessoa.getId());

        partialUpdatedEscolaridadePessoa
            .nomeInstituicao(UPDATED_NOME_INSTITUICAO)
            .anoConclusao(UPDATED_ANO_CONCLUSAO)
            .urlComprovanteEscolaridade(UPDATED_URL_COMPROVANTE_ESCOLARIDADE);

        restEscolaridadePessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEscolaridadePessoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEscolaridadePessoa))
            )
            .andExpect(status().isOk());

        // Validate the EscolaridadePessoa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEscolaridadePessoaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEscolaridadePessoa, escolaridadePessoa),
            getPersistedEscolaridadePessoa(escolaridadePessoa)
        );
    }

    @Test
    @Transactional
    void fullUpdateEscolaridadePessoaWithPatch() throws Exception {
        // Initialize the database
        insertedEscolaridadePessoa = escolaridadePessoaRepository.saveAndFlush(escolaridadePessoa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the escolaridadePessoa using partial update
        EscolaridadePessoa partialUpdatedEscolaridadePessoa = new EscolaridadePessoa();
        partialUpdatedEscolaridadePessoa.setId(escolaridadePessoa.getId());

        partialUpdatedEscolaridadePessoa
            .nomeInstituicao(UPDATED_NOME_INSTITUICAO)
            .anoConclusao(UPDATED_ANO_CONCLUSAO)
            .urlComprovanteEscolaridade(UPDATED_URL_COMPROVANTE_ESCOLARIDADE);

        restEscolaridadePessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEscolaridadePessoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEscolaridadePessoa))
            )
            .andExpect(status().isOk());

        // Validate the EscolaridadePessoa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEscolaridadePessoaUpdatableFieldsEquals(
            partialUpdatedEscolaridadePessoa,
            getPersistedEscolaridadePessoa(partialUpdatedEscolaridadePessoa)
        );
    }

    @Test
    @Transactional
    void patchNonExistingEscolaridadePessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        escolaridadePessoa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEscolaridadePessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, escolaridadePessoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(escolaridadePessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the EscolaridadePessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEscolaridadePessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        escolaridadePessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEscolaridadePessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(escolaridadePessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the EscolaridadePessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEscolaridadePessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        escolaridadePessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEscolaridadePessoaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(escolaridadePessoa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EscolaridadePessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEscolaridadePessoa() throws Exception {
        // Initialize the database
        insertedEscolaridadePessoa = escolaridadePessoaRepository.saveAndFlush(escolaridadePessoa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the escolaridadePessoa
        restEscolaridadePessoaMockMvc
            .perform(delete(ENTITY_API_URL_ID, escolaridadePessoa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return escolaridadePessoaRepository.count();
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

    protected EscolaridadePessoa getPersistedEscolaridadePessoa(EscolaridadePessoa escolaridadePessoa) {
        return escolaridadePessoaRepository.findById(escolaridadePessoa.getId()).orElseThrow();
    }

    protected void assertPersistedEscolaridadePessoaToMatchAllProperties(EscolaridadePessoa expectedEscolaridadePessoa) {
        assertEscolaridadePessoaAllPropertiesEquals(expectedEscolaridadePessoa, getPersistedEscolaridadePessoa(expectedEscolaridadePessoa));
    }

    protected void assertPersistedEscolaridadePessoaToMatchUpdatableProperties(EscolaridadePessoa expectedEscolaridadePessoa) {
        assertEscolaridadePessoaAllUpdatablePropertiesEquals(
            expectedEscolaridadePessoa,
            getPersistedEscolaridadePessoa(expectedEscolaridadePessoa)
        );
    }
}
