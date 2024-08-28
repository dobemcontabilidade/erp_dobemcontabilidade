package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.EnderecoEmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Cidade;
import com.dobemcontabilidade.domain.EnderecoEmpresa;
import com.dobemcontabilidade.domain.Pessoajuridica;
import com.dobemcontabilidade.repository.EnderecoEmpresaRepository;
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
 * Integration tests for the {@link EnderecoEmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EnderecoEmpresaResourceIT {

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

    private static final Boolean DEFAULT_FILIAL = false;
    private static final Boolean UPDATED_FILIAL = true;

    private static final Boolean DEFAULT_ENDERECO_FISCAL = false;
    private static final Boolean UPDATED_ENDERECO_FISCAL = true;

    private static final String ENTITY_API_URL = "/api/endereco-empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EnderecoEmpresaRepository enderecoEmpresaRepository;

    @Mock
    private EnderecoEmpresaRepository enderecoEmpresaRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnderecoEmpresaMockMvc;

    private EnderecoEmpresa enderecoEmpresa;

    private EnderecoEmpresa insertedEnderecoEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnderecoEmpresa createEntity(EntityManager em) {
        EnderecoEmpresa enderecoEmpresa = new EnderecoEmpresa()
            .logradouro(DEFAULT_LOGRADOURO)
            .numero(DEFAULT_NUMERO)
            .complemento(DEFAULT_COMPLEMENTO)
            .bairro(DEFAULT_BAIRRO)
            .cep(DEFAULT_CEP)
            .principal(DEFAULT_PRINCIPAL)
            .filial(DEFAULT_FILIAL)
            .enderecoFiscal(DEFAULT_ENDERECO_FISCAL);
        // Add required entity
        Cidade cidade;
        if (TestUtil.findAll(em, Cidade.class).isEmpty()) {
            cidade = CidadeResourceIT.createEntity(em);
            em.persist(cidade);
            em.flush();
        } else {
            cidade = TestUtil.findAll(em, Cidade.class).get(0);
        }
        enderecoEmpresa.setCidade(cidade);
        // Add required entity
        Pessoajuridica pessoajuridica;
        if (TestUtil.findAll(em, Pessoajuridica.class).isEmpty()) {
            pessoajuridica = PessoajuridicaResourceIT.createEntity(em);
            em.persist(pessoajuridica);
            em.flush();
        } else {
            pessoajuridica = TestUtil.findAll(em, Pessoajuridica.class).get(0);
        }
        enderecoEmpresa.setPessoaJuridica(pessoajuridica);
        return enderecoEmpresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnderecoEmpresa createUpdatedEntity(EntityManager em) {
        EnderecoEmpresa enderecoEmpresa = new EnderecoEmpresa()
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .bairro(UPDATED_BAIRRO)
            .cep(UPDATED_CEP)
            .principal(UPDATED_PRINCIPAL)
            .filial(UPDATED_FILIAL)
            .enderecoFiscal(UPDATED_ENDERECO_FISCAL);
        // Add required entity
        Cidade cidade;
        if (TestUtil.findAll(em, Cidade.class).isEmpty()) {
            cidade = CidadeResourceIT.createUpdatedEntity(em);
            em.persist(cidade);
            em.flush();
        } else {
            cidade = TestUtil.findAll(em, Cidade.class).get(0);
        }
        enderecoEmpresa.setCidade(cidade);
        // Add required entity
        Pessoajuridica pessoajuridica;
        if (TestUtil.findAll(em, Pessoajuridica.class).isEmpty()) {
            pessoajuridica = PessoajuridicaResourceIT.createUpdatedEntity(em);
            em.persist(pessoajuridica);
            em.flush();
        } else {
            pessoajuridica = TestUtil.findAll(em, Pessoajuridica.class).get(0);
        }
        enderecoEmpresa.setPessoaJuridica(pessoajuridica);
        return enderecoEmpresa;
    }

    @BeforeEach
    public void initTest() {
        enderecoEmpresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedEnderecoEmpresa != null) {
            enderecoEmpresaRepository.delete(insertedEnderecoEmpresa);
            insertedEnderecoEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createEnderecoEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EnderecoEmpresa
        var returnedEnderecoEmpresa = om.readValue(
            restEnderecoEmpresaMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enderecoEmpresa))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EnderecoEmpresa.class
        );

        // Validate the EnderecoEmpresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEnderecoEmpresaUpdatableFieldsEquals(returnedEnderecoEmpresa, getPersistedEnderecoEmpresa(returnedEnderecoEmpresa));

        insertedEnderecoEmpresa = returnedEnderecoEmpresa;
    }

    @Test
    @Transactional
    void createEnderecoEmpresaWithExistingId() throws Exception {
        // Create the EnderecoEmpresa with an existing ID
        enderecoEmpresa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnderecoEmpresaMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enderecoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnderecoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresas() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList
        restEnderecoEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enderecoEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].logradouro").value(hasItem(DEFAULT_LOGRADOURO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)))
            .andExpect(jsonPath("$.[*].principal").value(hasItem(DEFAULT_PRINCIPAL.booleanValue())))
            .andExpect(jsonPath("$.[*].filial").value(hasItem(DEFAULT_FILIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].enderecoFiscal").value(hasItem(DEFAULT_ENDERECO_FISCAL.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEnderecoEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(enderecoEmpresaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEnderecoEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(enderecoEmpresaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEnderecoEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(enderecoEmpresaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEnderecoEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(enderecoEmpresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEnderecoEmpresa() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get the enderecoEmpresa
        restEnderecoEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, enderecoEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(enderecoEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.logradouro").value(DEFAULT_LOGRADOURO))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.complemento").value(DEFAULT_COMPLEMENTO))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP))
            .andExpect(jsonPath("$.principal").value(DEFAULT_PRINCIPAL.booleanValue()))
            .andExpect(jsonPath("$.filial").value(DEFAULT_FILIAL.booleanValue()))
            .andExpect(jsonPath("$.enderecoFiscal").value(DEFAULT_ENDERECO_FISCAL.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingEnderecoEmpresa() throws Exception {
        // Get the enderecoEmpresa
        restEnderecoEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEnderecoEmpresa() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the enderecoEmpresa
        EnderecoEmpresa updatedEnderecoEmpresa = enderecoEmpresaRepository.findById(enderecoEmpresa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEnderecoEmpresa are not directly saved in db
        em.detach(updatedEnderecoEmpresa);
        updatedEnderecoEmpresa
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .bairro(UPDATED_BAIRRO)
            .cep(UPDATED_CEP)
            .principal(UPDATED_PRINCIPAL)
            .filial(UPDATED_FILIAL)
            .enderecoFiscal(UPDATED_ENDERECO_FISCAL);

        restEnderecoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEnderecoEmpresa.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEnderecoEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the EnderecoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEnderecoEmpresaToMatchAllProperties(updatedEnderecoEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingEnderecoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enderecoEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnderecoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, enderecoEmpresa.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(enderecoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnderecoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEnderecoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enderecoEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnderecoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(enderecoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnderecoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEnderecoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enderecoEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnderecoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enderecoEmpresa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EnderecoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEnderecoEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the enderecoEmpresa using partial update
        EnderecoEmpresa partialUpdatedEnderecoEmpresa = new EnderecoEmpresa();
        partialUpdatedEnderecoEmpresa.setId(enderecoEmpresa.getId());

        partialUpdatedEnderecoEmpresa
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .bairro(UPDATED_BAIRRO)
            .cep(UPDATED_CEP);

        restEnderecoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnderecoEmpresa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEnderecoEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the EnderecoEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEnderecoEmpresaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEnderecoEmpresa, enderecoEmpresa),
            getPersistedEnderecoEmpresa(enderecoEmpresa)
        );
    }

    @Test
    @Transactional
    void fullUpdateEnderecoEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the enderecoEmpresa using partial update
        EnderecoEmpresa partialUpdatedEnderecoEmpresa = new EnderecoEmpresa();
        partialUpdatedEnderecoEmpresa.setId(enderecoEmpresa.getId());

        partialUpdatedEnderecoEmpresa
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .bairro(UPDATED_BAIRRO)
            .cep(UPDATED_CEP)
            .principal(UPDATED_PRINCIPAL)
            .filial(UPDATED_FILIAL)
            .enderecoFiscal(UPDATED_ENDERECO_FISCAL);

        restEnderecoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnderecoEmpresa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEnderecoEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the EnderecoEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEnderecoEmpresaUpdatableFieldsEquals(
            partialUpdatedEnderecoEmpresa,
            getPersistedEnderecoEmpresa(partialUpdatedEnderecoEmpresa)
        );
    }

    @Test
    @Transactional
    void patchNonExistingEnderecoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enderecoEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnderecoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, enderecoEmpresa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(enderecoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnderecoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEnderecoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enderecoEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnderecoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(enderecoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnderecoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEnderecoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enderecoEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnderecoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(enderecoEmpresa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EnderecoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEnderecoEmpresa() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the enderecoEmpresa
        restEnderecoEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, enderecoEmpresa.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return enderecoEmpresaRepository.count();
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

    protected EnderecoEmpresa getPersistedEnderecoEmpresa(EnderecoEmpresa enderecoEmpresa) {
        return enderecoEmpresaRepository.findById(enderecoEmpresa.getId()).orElseThrow();
    }

    protected void assertPersistedEnderecoEmpresaToMatchAllProperties(EnderecoEmpresa expectedEnderecoEmpresa) {
        assertEnderecoEmpresaAllPropertiesEquals(expectedEnderecoEmpresa, getPersistedEnderecoEmpresa(expectedEnderecoEmpresa));
    }

    protected void assertPersistedEnderecoEmpresaToMatchUpdatableProperties(EnderecoEmpresa expectedEnderecoEmpresa) {
        assertEnderecoEmpresaAllUpdatablePropertiesEquals(expectedEnderecoEmpresa, getPersistedEnderecoEmpresa(expectedEnderecoEmpresa));
    }
}
