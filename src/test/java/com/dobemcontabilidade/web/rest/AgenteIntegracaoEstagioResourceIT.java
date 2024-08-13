package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.AgenteIntegracaoEstagioAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AgenteIntegracaoEstagio;
import com.dobemcontabilidade.domain.Cidade;
import com.dobemcontabilidade.repository.AgenteIntegracaoEstagioRepository;
import com.dobemcontabilidade.service.AgenteIntegracaoEstagioService;
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
 * Integration tests for the {@link AgenteIntegracaoEstagioResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AgenteIntegracaoEstagioResourceIT {

    private static final String DEFAULT_CNPJ = "AAAAAAAAAA";
    private static final String UPDATED_CNPJ = "BBBBBBBBBB";

    private static final String DEFAULT_RAZAO_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_RAZAO_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_COORDENADOR = "AAAAAAAAAA";
    private static final String UPDATED_COORDENADOR = "BBBBBBBBBB";

    private static final String DEFAULT_CPF_COORDENADOR_ESTAGIO = "AAAAAAAAAA";
    private static final String UPDATED_CPF_COORDENADOR_ESTAGIO = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/agente-integracao-estagios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AgenteIntegracaoEstagioRepository agenteIntegracaoEstagioRepository;

    @Mock
    private AgenteIntegracaoEstagioRepository agenteIntegracaoEstagioRepositoryMock;

    @Mock
    private AgenteIntegracaoEstagioService agenteIntegracaoEstagioServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAgenteIntegracaoEstagioMockMvc;

    private AgenteIntegracaoEstagio agenteIntegracaoEstagio;

    private AgenteIntegracaoEstagio insertedAgenteIntegracaoEstagio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgenteIntegracaoEstagio createEntity(EntityManager em) {
        AgenteIntegracaoEstagio agenteIntegracaoEstagio = new AgenteIntegracaoEstagio()
            .cnpj(DEFAULT_CNPJ)
            .razaoSocial(DEFAULT_RAZAO_SOCIAL)
            .coordenador(DEFAULT_COORDENADOR)
            .cpfCoordenadorEstagio(DEFAULT_CPF_COORDENADOR_ESTAGIO)
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
        agenteIntegracaoEstagio.setCidade(cidade);
        return agenteIntegracaoEstagio;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgenteIntegracaoEstagio createUpdatedEntity(EntityManager em) {
        AgenteIntegracaoEstagio agenteIntegracaoEstagio = new AgenteIntegracaoEstagio()
            .cnpj(UPDATED_CNPJ)
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .coordenador(UPDATED_COORDENADOR)
            .cpfCoordenadorEstagio(UPDATED_CPF_COORDENADOR_ESTAGIO)
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
        agenteIntegracaoEstagio.setCidade(cidade);
        return agenteIntegracaoEstagio;
    }

    @BeforeEach
    public void initTest() {
        agenteIntegracaoEstagio = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAgenteIntegracaoEstagio != null) {
            agenteIntegracaoEstagioRepository.delete(insertedAgenteIntegracaoEstagio);
            insertedAgenteIntegracaoEstagio = null;
        }
    }

    @Test
    @Transactional
    void createAgenteIntegracaoEstagio() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AgenteIntegracaoEstagio
        var returnedAgenteIntegracaoEstagio = om.readValue(
            restAgenteIntegracaoEstagioMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agenteIntegracaoEstagio))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AgenteIntegracaoEstagio.class
        );

        // Validate the AgenteIntegracaoEstagio in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAgenteIntegracaoEstagioUpdatableFieldsEquals(
            returnedAgenteIntegracaoEstagio,
            getPersistedAgenteIntegracaoEstagio(returnedAgenteIntegracaoEstagio)
        );

        insertedAgenteIntegracaoEstagio = returnedAgenteIntegracaoEstagio;
    }

    @Test
    @Transactional
    void createAgenteIntegracaoEstagioWithExistingId() throws Exception {
        // Create the AgenteIntegracaoEstagio with an existing ID
        agenteIntegracaoEstagio.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgenteIntegracaoEstagioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agenteIntegracaoEstagio)))
            .andExpect(status().isBadRequest());

        // Validate the AgenteIntegracaoEstagio in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAgenteIntegracaoEstagios() throws Exception {
        // Initialize the database
        insertedAgenteIntegracaoEstagio = agenteIntegracaoEstagioRepository.saveAndFlush(agenteIntegracaoEstagio);

        // Get all the agenteIntegracaoEstagioList
        restAgenteIntegracaoEstagioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agenteIntegracaoEstagio.getId().intValue())))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ)))
            .andExpect(jsonPath("$.[*].razaoSocial").value(hasItem(DEFAULT_RAZAO_SOCIAL)))
            .andExpect(jsonPath("$.[*].coordenador").value(hasItem(DEFAULT_COORDENADOR)))
            .andExpect(jsonPath("$.[*].cpfCoordenadorEstagio").value(hasItem(DEFAULT_CPF_COORDENADOR_ESTAGIO)))
            .andExpect(jsonPath("$.[*].logradouro").value(hasItem(DEFAULT_LOGRADOURO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)))
            .andExpect(jsonPath("$.[*].principal").value(hasItem(DEFAULT_PRINCIPAL.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAgenteIntegracaoEstagiosWithEagerRelationshipsIsEnabled() throws Exception {
        when(agenteIntegracaoEstagioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAgenteIntegracaoEstagioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(agenteIntegracaoEstagioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAgenteIntegracaoEstagiosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(agenteIntegracaoEstagioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAgenteIntegracaoEstagioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(agenteIntegracaoEstagioRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAgenteIntegracaoEstagio() throws Exception {
        // Initialize the database
        insertedAgenteIntegracaoEstagio = agenteIntegracaoEstagioRepository.saveAndFlush(agenteIntegracaoEstagio);

        // Get the agenteIntegracaoEstagio
        restAgenteIntegracaoEstagioMockMvc
            .perform(get(ENTITY_API_URL_ID, agenteIntegracaoEstagio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(agenteIntegracaoEstagio.getId().intValue()))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ))
            .andExpect(jsonPath("$.razaoSocial").value(DEFAULT_RAZAO_SOCIAL))
            .andExpect(jsonPath("$.coordenador").value(DEFAULT_COORDENADOR))
            .andExpect(jsonPath("$.cpfCoordenadorEstagio").value(DEFAULT_CPF_COORDENADOR_ESTAGIO))
            .andExpect(jsonPath("$.logradouro").value(DEFAULT_LOGRADOURO))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.complemento").value(DEFAULT_COMPLEMENTO))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP))
            .andExpect(jsonPath("$.principal").value(DEFAULT_PRINCIPAL.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingAgenteIntegracaoEstagio() throws Exception {
        // Get the agenteIntegracaoEstagio
        restAgenteIntegracaoEstagioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAgenteIntegracaoEstagio() throws Exception {
        // Initialize the database
        insertedAgenteIntegracaoEstagio = agenteIntegracaoEstagioRepository.saveAndFlush(agenteIntegracaoEstagio);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agenteIntegracaoEstagio
        AgenteIntegracaoEstagio updatedAgenteIntegracaoEstagio = agenteIntegracaoEstagioRepository
            .findById(agenteIntegracaoEstagio.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAgenteIntegracaoEstagio are not directly saved in db
        em.detach(updatedAgenteIntegracaoEstagio);
        updatedAgenteIntegracaoEstagio
            .cnpj(UPDATED_CNPJ)
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .coordenador(UPDATED_COORDENADOR)
            .cpfCoordenadorEstagio(UPDATED_CPF_COORDENADOR_ESTAGIO)
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .bairro(UPDATED_BAIRRO)
            .cep(UPDATED_CEP)
            .principal(UPDATED_PRINCIPAL);

        restAgenteIntegracaoEstagioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAgenteIntegracaoEstagio.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAgenteIntegracaoEstagio))
            )
            .andExpect(status().isOk());

        // Validate the AgenteIntegracaoEstagio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAgenteIntegracaoEstagioToMatchAllProperties(updatedAgenteIntegracaoEstagio);
    }

    @Test
    @Transactional
    void putNonExistingAgenteIntegracaoEstagio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agenteIntegracaoEstagio.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgenteIntegracaoEstagioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, agenteIntegracaoEstagio.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(agenteIntegracaoEstagio))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgenteIntegracaoEstagio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAgenteIntegracaoEstagio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agenteIntegracaoEstagio.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgenteIntegracaoEstagioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(agenteIntegracaoEstagio))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgenteIntegracaoEstagio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAgenteIntegracaoEstagio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agenteIntegracaoEstagio.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgenteIntegracaoEstagioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agenteIntegracaoEstagio)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AgenteIntegracaoEstagio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAgenteIntegracaoEstagioWithPatch() throws Exception {
        // Initialize the database
        insertedAgenteIntegracaoEstagio = agenteIntegracaoEstagioRepository.saveAndFlush(agenteIntegracaoEstagio);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agenteIntegracaoEstagio using partial update
        AgenteIntegracaoEstagio partialUpdatedAgenteIntegracaoEstagio = new AgenteIntegracaoEstagio();
        partialUpdatedAgenteIntegracaoEstagio.setId(agenteIntegracaoEstagio.getId());

        partialUpdatedAgenteIntegracaoEstagio
            .cnpj(UPDATED_CNPJ)
            .logradouro(UPDATED_LOGRADOURO)
            .bairro(UPDATED_BAIRRO)
            .cep(UPDATED_CEP)
            .principal(UPDATED_PRINCIPAL);

        restAgenteIntegracaoEstagioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgenteIntegracaoEstagio.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAgenteIntegracaoEstagio))
            )
            .andExpect(status().isOk());

        // Validate the AgenteIntegracaoEstagio in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAgenteIntegracaoEstagioUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAgenteIntegracaoEstagio, agenteIntegracaoEstagio),
            getPersistedAgenteIntegracaoEstagio(agenteIntegracaoEstagio)
        );
    }

    @Test
    @Transactional
    void fullUpdateAgenteIntegracaoEstagioWithPatch() throws Exception {
        // Initialize the database
        insertedAgenteIntegracaoEstagio = agenteIntegracaoEstagioRepository.saveAndFlush(agenteIntegracaoEstagio);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agenteIntegracaoEstagio using partial update
        AgenteIntegracaoEstagio partialUpdatedAgenteIntegracaoEstagio = new AgenteIntegracaoEstagio();
        partialUpdatedAgenteIntegracaoEstagio.setId(agenteIntegracaoEstagio.getId());

        partialUpdatedAgenteIntegracaoEstagio
            .cnpj(UPDATED_CNPJ)
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .coordenador(UPDATED_COORDENADOR)
            .cpfCoordenadorEstagio(UPDATED_CPF_COORDENADOR_ESTAGIO)
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .bairro(UPDATED_BAIRRO)
            .cep(UPDATED_CEP)
            .principal(UPDATED_PRINCIPAL);

        restAgenteIntegracaoEstagioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgenteIntegracaoEstagio.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAgenteIntegracaoEstagio))
            )
            .andExpect(status().isOk());

        // Validate the AgenteIntegracaoEstagio in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAgenteIntegracaoEstagioUpdatableFieldsEquals(
            partialUpdatedAgenteIntegracaoEstagio,
            getPersistedAgenteIntegracaoEstagio(partialUpdatedAgenteIntegracaoEstagio)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAgenteIntegracaoEstagio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agenteIntegracaoEstagio.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgenteIntegracaoEstagioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, agenteIntegracaoEstagio.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(agenteIntegracaoEstagio))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgenteIntegracaoEstagio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAgenteIntegracaoEstagio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agenteIntegracaoEstagio.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgenteIntegracaoEstagioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(agenteIntegracaoEstagio))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgenteIntegracaoEstagio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAgenteIntegracaoEstagio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agenteIntegracaoEstagio.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgenteIntegracaoEstagioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(agenteIntegracaoEstagio))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AgenteIntegracaoEstagio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAgenteIntegracaoEstagio() throws Exception {
        // Initialize the database
        insertedAgenteIntegracaoEstagio = agenteIntegracaoEstagioRepository.saveAndFlush(agenteIntegracaoEstagio);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the agenteIntegracaoEstagio
        restAgenteIntegracaoEstagioMockMvc
            .perform(delete(ENTITY_API_URL_ID, agenteIntegracaoEstagio.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return agenteIntegracaoEstagioRepository.count();
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

    protected AgenteIntegracaoEstagio getPersistedAgenteIntegracaoEstagio(AgenteIntegracaoEstagio agenteIntegracaoEstagio) {
        return agenteIntegracaoEstagioRepository.findById(agenteIntegracaoEstagio.getId()).orElseThrow();
    }

    protected void assertPersistedAgenteIntegracaoEstagioToMatchAllProperties(AgenteIntegracaoEstagio expectedAgenteIntegracaoEstagio) {
        assertAgenteIntegracaoEstagioAllPropertiesEquals(
            expectedAgenteIntegracaoEstagio,
            getPersistedAgenteIntegracaoEstagio(expectedAgenteIntegracaoEstagio)
        );
    }

    protected void assertPersistedAgenteIntegracaoEstagioToMatchUpdatableProperties(
        AgenteIntegracaoEstagio expectedAgenteIntegracaoEstagio
    ) {
        assertAgenteIntegracaoEstagioAllUpdatablePropertiesEquals(
            expectedAgenteIntegracaoEstagio,
            getPersistedAgenteIntegracaoEstagio(expectedAgenteIntegracaoEstagio)
        );
    }
}
