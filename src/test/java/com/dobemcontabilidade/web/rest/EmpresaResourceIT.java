package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.EmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.domain.Enquadramento;
import com.dobemcontabilidade.domain.Pessoajuridica;
import com.dobemcontabilidade.domain.Ramo;
import com.dobemcontabilidade.domain.Tributacao;
import com.dobemcontabilidade.repository.EmpresaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link EmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EmpresaResourceIT {

    private static final String DEFAULT_RAZAO_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_RAZAO_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO_DO_NEGOCIO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO_DO_NEGOCIO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_ABERTURA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ABERTURA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_URL_CONTRATO_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_URL_CONTRATO_SOCIAL = "BBBBBBBBBB";

    private static final Double DEFAULT_CAPITAL_SOCIAL = 1D;
    private static final Double UPDATED_CAPITAL_SOCIAL = 2D;

    private static final String DEFAULT_CNAE = "AAAAAAAAAA";
    private static final String UPDATED_CNAE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Mock
    private EmpresaRepository empresaRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmpresaMockMvc;

    private Empresa empresa;

    private Empresa insertedEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Empresa createEntity(EntityManager em) {
        Empresa empresa = new Empresa()
            .razaoSocial(DEFAULT_RAZAO_SOCIAL)
            .descricaoDoNegocio(DEFAULT_DESCRICAO_DO_NEGOCIO)
            .dataAbertura(DEFAULT_DATA_ABERTURA)
            .urlContratoSocial(DEFAULT_URL_CONTRATO_SOCIAL)
            .capitalSocial(DEFAULT_CAPITAL_SOCIAL)
            .cnae(DEFAULT_CNAE);
        // Add required entity
        Pessoajuridica pessoajuridica;
        if (TestUtil.findAll(em, Pessoajuridica.class).isEmpty()) {
            pessoajuridica = PessoajuridicaResourceIT.createEntity(em);
            em.persist(pessoajuridica);
            em.flush();
        } else {
            pessoajuridica = TestUtil.findAll(em, Pessoajuridica.class).get(0);
        }
        empresa.setPessoaJuridica(pessoajuridica);
        // Add required entity
        Tributacao tributacao;
        if (TestUtil.findAll(em, Tributacao.class).isEmpty()) {
            tributacao = TributacaoResourceIT.createEntity(em);
            em.persist(tributacao);
            em.flush();
        } else {
            tributacao = TestUtil.findAll(em, Tributacao.class).get(0);
        }
        empresa.setEmpresa(tributacao);
        // Add required entity
        Ramo ramo;
        if (TestUtil.findAll(em, Ramo.class).isEmpty()) {
            ramo = RamoResourceIT.createEntity(em);
            em.persist(ramo);
            em.flush();
        } else {
            ramo = TestUtil.findAll(em, Ramo.class).get(0);
        }
        empresa.setRamo(ramo);
        // Add required entity
        Enquadramento enquadramento;
        if (TestUtil.findAll(em, Enquadramento.class).isEmpty()) {
            enquadramento = EnquadramentoResourceIT.createEntity(em);
            em.persist(enquadramento);
            em.flush();
        } else {
            enquadramento = TestUtil.findAll(em, Enquadramento.class).get(0);
        }
        empresa.setEnquadramento(enquadramento);
        return empresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Empresa createUpdatedEntity(EntityManager em) {
        Empresa empresa = new Empresa()
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .descricaoDoNegocio(UPDATED_DESCRICAO_DO_NEGOCIO)
            .dataAbertura(UPDATED_DATA_ABERTURA)
            .urlContratoSocial(UPDATED_URL_CONTRATO_SOCIAL)
            .capitalSocial(UPDATED_CAPITAL_SOCIAL)
            .cnae(UPDATED_CNAE);
        // Add required entity
        Pessoajuridica pessoajuridica;
        if (TestUtil.findAll(em, Pessoajuridica.class).isEmpty()) {
            pessoajuridica = PessoajuridicaResourceIT.createUpdatedEntity(em);
            em.persist(pessoajuridica);
            em.flush();
        } else {
            pessoajuridica = TestUtil.findAll(em, Pessoajuridica.class).get(0);
        }
        empresa.setPessoaJuridica(pessoajuridica);
        // Add required entity
        Tributacao tributacao;
        if (TestUtil.findAll(em, Tributacao.class).isEmpty()) {
            tributacao = TributacaoResourceIT.createUpdatedEntity(em);
            em.persist(tributacao);
            em.flush();
        } else {
            tributacao = TestUtil.findAll(em, Tributacao.class).get(0);
        }
        empresa.setEmpresa(tributacao);
        // Add required entity
        Ramo ramo;
        if (TestUtil.findAll(em, Ramo.class).isEmpty()) {
            ramo = RamoResourceIT.createUpdatedEntity(em);
            em.persist(ramo);
            em.flush();
        } else {
            ramo = TestUtil.findAll(em, Ramo.class).get(0);
        }
        empresa.setRamo(ramo);
        // Add required entity
        Enquadramento enquadramento;
        if (TestUtil.findAll(em, Enquadramento.class).isEmpty()) {
            enquadramento = EnquadramentoResourceIT.createUpdatedEntity(em);
            em.persist(enquadramento);
            em.flush();
        } else {
            enquadramento = TestUtil.findAll(em, Enquadramento.class).get(0);
        }
        empresa.setEnquadramento(enquadramento);
        return empresa;
    }

    @BeforeEach
    public void initTest() {
        empresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedEmpresa != null) {
            empresaRepository.delete(insertedEmpresa);
            insertedEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Empresa
        var returnedEmpresa = om.readValue(
            restEmpresaMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empresa)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Empresa.class
        );

        // Validate the Empresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEmpresaUpdatableFieldsEquals(returnedEmpresa, getPersistedEmpresa(returnedEmpresa));

        insertedEmpresa = returnedEmpresa;
    }

    @Test
    @Transactional
    void createEmpresaWithExistingId() throws Exception {
        // Create the Empresa with an existing ID
        empresa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpresaMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empresa)))
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRazaoSocialIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        empresa.setRazaoSocial(null);

        // Create the Empresa, which fails.

        restEmpresaMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empresa)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCnaeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        empresa.setCnae(null);

        // Create the Empresa, which fails.

        restEmpresaMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empresa)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmpresas() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList
        restEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].razaoSocial").value(hasItem(DEFAULT_RAZAO_SOCIAL)))
            .andExpect(jsonPath("$.[*].descricaoDoNegocio").value(hasItem(DEFAULT_DESCRICAO_DO_NEGOCIO.toString())))
            .andExpect(jsonPath("$.[*].dataAbertura").value(hasItem(DEFAULT_DATA_ABERTURA.toString())))
            .andExpect(jsonPath("$.[*].urlContratoSocial").value(hasItem(DEFAULT_URL_CONTRATO_SOCIAL)))
            .andExpect(jsonPath("$.[*].capitalSocial").value(hasItem(DEFAULT_CAPITAL_SOCIAL.doubleValue())))
            .andExpect(jsonPath("$.[*].cnae").value(hasItem(DEFAULT_CNAE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(empresaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(empresaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(empresaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(empresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEmpresa() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get the empresa
        restEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, empresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(empresa.getId().intValue()))
            .andExpect(jsonPath("$.razaoSocial").value(DEFAULT_RAZAO_SOCIAL))
            .andExpect(jsonPath("$.descricaoDoNegocio").value(DEFAULT_DESCRICAO_DO_NEGOCIO.toString()))
            .andExpect(jsonPath("$.dataAbertura").value(DEFAULT_DATA_ABERTURA.toString()))
            .andExpect(jsonPath("$.urlContratoSocial").value(DEFAULT_URL_CONTRATO_SOCIAL))
            .andExpect(jsonPath("$.capitalSocial").value(DEFAULT_CAPITAL_SOCIAL.doubleValue()))
            .andExpect(jsonPath("$.cnae").value(DEFAULT_CNAE));
    }

    @Test
    @Transactional
    void getNonExistingEmpresa() throws Exception {
        // Get the empresa
        restEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmpresa() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the empresa
        Empresa updatedEmpresa = empresaRepository.findById(empresa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEmpresa are not directly saved in db
        em.detach(updatedEmpresa);
        updatedEmpresa
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .descricaoDoNegocio(UPDATED_DESCRICAO_DO_NEGOCIO)
            .dataAbertura(UPDATED_DATA_ABERTURA)
            .urlContratoSocial(UPDATED_URL_CONTRATO_SOCIAL)
            .capitalSocial(UPDATED_CAPITAL_SOCIAL)
            .cnae(UPDATED_CNAE);

        restEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmpresa.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the Empresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEmpresaToMatchAllProperties(updatedEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, empresa.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(empresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(empresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empresa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Empresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the empresa using partial update
        Empresa partialUpdatedEmpresa = new Empresa();
        partialUpdatedEmpresa.setId(empresa.getId());

        partialUpdatedEmpresa.razaoSocial(UPDATED_RAZAO_SOCIAL).capitalSocial(UPDATED_CAPITAL_SOCIAL).cnae(UPDATED_CNAE);

        restEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpresa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the Empresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmpresaUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedEmpresa, empresa), getPersistedEmpresa(empresa));
    }

    @Test
    @Transactional
    void fullUpdateEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the empresa using partial update
        Empresa partialUpdatedEmpresa = new Empresa();
        partialUpdatedEmpresa.setId(empresa.getId());

        partialUpdatedEmpresa
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .descricaoDoNegocio(UPDATED_DESCRICAO_DO_NEGOCIO)
            .dataAbertura(UPDATED_DATA_ABERTURA)
            .urlContratoSocial(UPDATED_URL_CONTRATO_SOCIAL)
            .capitalSocial(UPDATED_CAPITAL_SOCIAL)
            .cnae(UPDATED_CNAE);

        restEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpresa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the Empresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmpresaUpdatableFieldsEquals(partialUpdatedEmpresa, getPersistedEmpresa(partialUpdatedEmpresa));
    }

    @Test
    @Transactional
    void patchNonExistingEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, empresa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(empresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(empresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(empresa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Empresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmpresa() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the empresa
        restEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, empresa.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return empresaRepository.count();
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

    protected Empresa getPersistedEmpresa(Empresa empresa) {
        return empresaRepository.findById(empresa.getId()).orElseThrow();
    }

    protected void assertPersistedEmpresaToMatchAllProperties(Empresa expectedEmpresa) {
        assertEmpresaAllPropertiesEquals(expectedEmpresa, getPersistedEmpresa(expectedEmpresa));
    }

    protected void assertPersistedEmpresaToMatchUpdatableProperties(Empresa expectedEmpresa) {
        assertEmpresaAllUpdatablePropertiesEquals(expectedEmpresa, getPersistedEmpresa(expectedEmpresa));
    }
}
