package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.CertificadoDigitalEmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.CertificadoDigital;
import com.dobemcontabilidade.domain.CertificadoDigitalEmpresa;
import com.dobemcontabilidade.domain.FornecedorCertificado;
import com.dobemcontabilidade.domain.Pessoajuridica;
import com.dobemcontabilidade.repository.CertificadoDigitalEmpresaRepository;
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
 * Integration tests for the {@link CertificadoDigitalEmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CertificadoDigitalEmpresaResourceIT {

    private static final String DEFAULT_URL_CERTIFICADO = "AAAAAAAAAA";
    private static final String UPDATED_URL_CERTIFICADO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_CONTRATACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_CONTRATACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_VENCIMENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_VENCIMENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_DIAS_USO = 1;
    private static final Integer UPDATED_DIAS_USO = 2;

    private static final String ENTITY_API_URL = "/api/certificado-digital-empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CertificadoDigitalEmpresaRepository certificadoDigitalEmpresaRepository;

    @Mock
    private CertificadoDigitalEmpresaRepository certificadoDigitalEmpresaRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCertificadoDigitalEmpresaMockMvc;

    private CertificadoDigitalEmpresa certificadoDigitalEmpresa;

    private CertificadoDigitalEmpresa insertedCertificadoDigitalEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CertificadoDigitalEmpresa createEntity(EntityManager em) {
        CertificadoDigitalEmpresa certificadoDigitalEmpresa = new CertificadoDigitalEmpresa()
            .urlCertificado(DEFAULT_URL_CERTIFICADO)
            .dataContratacao(DEFAULT_DATA_CONTRATACAO)
            .dataVencimento(DEFAULT_DATA_VENCIMENTO)
            .diasUso(DEFAULT_DIAS_USO);
        // Add required entity
        Pessoajuridica pessoajuridica;
        if (TestUtil.findAll(em, Pessoajuridica.class).isEmpty()) {
            pessoajuridica = PessoajuridicaResourceIT.createEntity(em);
            em.persist(pessoajuridica);
            em.flush();
        } else {
            pessoajuridica = TestUtil.findAll(em, Pessoajuridica.class).get(0);
        }
        certificadoDigitalEmpresa.setPessoaJuridica(pessoajuridica);
        // Add required entity
        CertificadoDigital certificadoDigital;
        if (TestUtil.findAll(em, CertificadoDigital.class).isEmpty()) {
            certificadoDigital = CertificadoDigitalResourceIT.createEntity(em);
            em.persist(certificadoDigital);
            em.flush();
        } else {
            certificadoDigital = TestUtil.findAll(em, CertificadoDigital.class).get(0);
        }
        certificadoDigitalEmpresa.setCertificadoDigital(certificadoDigital);
        // Add required entity
        FornecedorCertificado fornecedorCertificado;
        if (TestUtil.findAll(em, FornecedorCertificado.class).isEmpty()) {
            fornecedorCertificado = FornecedorCertificadoResourceIT.createEntity(em);
            em.persist(fornecedorCertificado);
            em.flush();
        } else {
            fornecedorCertificado = TestUtil.findAll(em, FornecedorCertificado.class).get(0);
        }
        certificadoDigitalEmpresa.setFornecedorCertificado(fornecedorCertificado);
        return certificadoDigitalEmpresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CertificadoDigitalEmpresa createUpdatedEntity(EntityManager em) {
        CertificadoDigitalEmpresa certificadoDigitalEmpresa = new CertificadoDigitalEmpresa()
            .urlCertificado(UPDATED_URL_CERTIFICADO)
            .dataContratacao(UPDATED_DATA_CONTRATACAO)
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .diasUso(UPDATED_DIAS_USO);
        // Add required entity
        Pessoajuridica pessoajuridica;
        if (TestUtil.findAll(em, Pessoajuridica.class).isEmpty()) {
            pessoajuridica = PessoajuridicaResourceIT.createUpdatedEntity(em);
            em.persist(pessoajuridica);
            em.flush();
        } else {
            pessoajuridica = TestUtil.findAll(em, Pessoajuridica.class).get(0);
        }
        certificadoDigitalEmpresa.setPessoaJuridica(pessoajuridica);
        // Add required entity
        CertificadoDigital certificadoDigital;
        if (TestUtil.findAll(em, CertificadoDigital.class).isEmpty()) {
            certificadoDigital = CertificadoDigitalResourceIT.createUpdatedEntity(em);
            em.persist(certificadoDigital);
            em.flush();
        } else {
            certificadoDigital = TestUtil.findAll(em, CertificadoDigital.class).get(0);
        }
        certificadoDigitalEmpresa.setCertificadoDigital(certificadoDigital);
        // Add required entity
        FornecedorCertificado fornecedorCertificado;
        if (TestUtil.findAll(em, FornecedorCertificado.class).isEmpty()) {
            fornecedorCertificado = FornecedorCertificadoResourceIT.createUpdatedEntity(em);
            em.persist(fornecedorCertificado);
            em.flush();
        } else {
            fornecedorCertificado = TestUtil.findAll(em, FornecedorCertificado.class).get(0);
        }
        certificadoDigitalEmpresa.setFornecedorCertificado(fornecedorCertificado);
        return certificadoDigitalEmpresa;
    }

    @BeforeEach
    public void initTest() {
        certificadoDigitalEmpresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedCertificadoDigitalEmpresa != null) {
            certificadoDigitalEmpresaRepository.delete(insertedCertificadoDigitalEmpresa);
            insertedCertificadoDigitalEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createCertificadoDigitalEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CertificadoDigitalEmpresa
        var returnedCertificadoDigitalEmpresa = om.readValue(
            restCertificadoDigitalEmpresaMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(certificadoDigitalEmpresa))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CertificadoDigitalEmpresa.class
        );

        // Validate the CertificadoDigitalEmpresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCertificadoDigitalEmpresaUpdatableFieldsEquals(
            returnedCertificadoDigitalEmpresa,
            getPersistedCertificadoDigitalEmpresa(returnedCertificadoDigitalEmpresa)
        );

        insertedCertificadoDigitalEmpresa = returnedCertificadoDigitalEmpresa;
    }

    @Test
    @Transactional
    void createCertificadoDigitalEmpresaWithExistingId() throws Exception {
        // Create the CertificadoDigitalEmpresa with an existing ID
        certificadoDigitalEmpresa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCertificadoDigitalEmpresaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(certificadoDigitalEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the CertificadoDigitalEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCertificadoDigitalEmpresas() throws Exception {
        // Initialize the database
        insertedCertificadoDigitalEmpresa = certificadoDigitalEmpresaRepository.saveAndFlush(certificadoDigitalEmpresa);

        // Get all the certificadoDigitalEmpresaList
        restCertificadoDigitalEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(certificadoDigitalEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].urlCertificado").value(hasItem(DEFAULT_URL_CERTIFICADO.toString())))
            .andExpect(jsonPath("$.[*].dataContratacao").value(hasItem(DEFAULT_DATA_CONTRATACAO.toString())))
            .andExpect(jsonPath("$.[*].dataVencimento").value(hasItem(DEFAULT_DATA_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].diasUso").value(hasItem(DEFAULT_DIAS_USO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCertificadoDigitalEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(certificadoDigitalEmpresaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCertificadoDigitalEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(certificadoDigitalEmpresaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCertificadoDigitalEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(certificadoDigitalEmpresaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCertificadoDigitalEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(certificadoDigitalEmpresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCertificadoDigitalEmpresa() throws Exception {
        // Initialize the database
        insertedCertificadoDigitalEmpresa = certificadoDigitalEmpresaRepository.saveAndFlush(certificadoDigitalEmpresa);

        // Get the certificadoDigitalEmpresa
        restCertificadoDigitalEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, certificadoDigitalEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(certificadoDigitalEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.urlCertificado").value(DEFAULT_URL_CERTIFICADO.toString()))
            .andExpect(jsonPath("$.dataContratacao").value(DEFAULT_DATA_CONTRATACAO.toString()))
            .andExpect(jsonPath("$.dataVencimento").value(DEFAULT_DATA_VENCIMENTO.toString()))
            .andExpect(jsonPath("$.diasUso").value(DEFAULT_DIAS_USO));
    }

    @Test
    @Transactional
    void getNonExistingCertificadoDigitalEmpresa() throws Exception {
        // Get the certificadoDigitalEmpresa
        restCertificadoDigitalEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCertificadoDigitalEmpresa() throws Exception {
        // Initialize the database
        insertedCertificadoDigitalEmpresa = certificadoDigitalEmpresaRepository.saveAndFlush(certificadoDigitalEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the certificadoDigitalEmpresa
        CertificadoDigitalEmpresa updatedCertificadoDigitalEmpresa = certificadoDigitalEmpresaRepository
            .findById(certificadoDigitalEmpresa.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedCertificadoDigitalEmpresa are not directly saved in db
        em.detach(updatedCertificadoDigitalEmpresa);
        updatedCertificadoDigitalEmpresa
            .urlCertificado(UPDATED_URL_CERTIFICADO)
            .dataContratacao(UPDATED_DATA_CONTRATACAO)
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .diasUso(UPDATED_DIAS_USO);

        restCertificadoDigitalEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCertificadoDigitalEmpresa.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCertificadoDigitalEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the CertificadoDigitalEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCertificadoDigitalEmpresaToMatchAllProperties(updatedCertificadoDigitalEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingCertificadoDigitalEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        certificadoDigitalEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCertificadoDigitalEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, certificadoDigitalEmpresa.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(certificadoDigitalEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the CertificadoDigitalEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCertificadoDigitalEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        certificadoDigitalEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCertificadoDigitalEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(certificadoDigitalEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the CertificadoDigitalEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCertificadoDigitalEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        certificadoDigitalEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCertificadoDigitalEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(certificadoDigitalEmpresa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CertificadoDigitalEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCertificadoDigitalEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedCertificadoDigitalEmpresa = certificadoDigitalEmpresaRepository.saveAndFlush(certificadoDigitalEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the certificadoDigitalEmpresa using partial update
        CertificadoDigitalEmpresa partialUpdatedCertificadoDigitalEmpresa = new CertificadoDigitalEmpresa();
        partialUpdatedCertificadoDigitalEmpresa.setId(certificadoDigitalEmpresa.getId());

        partialUpdatedCertificadoDigitalEmpresa.diasUso(UPDATED_DIAS_USO);

        restCertificadoDigitalEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCertificadoDigitalEmpresa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCertificadoDigitalEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the CertificadoDigitalEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCertificadoDigitalEmpresaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCertificadoDigitalEmpresa, certificadoDigitalEmpresa),
            getPersistedCertificadoDigitalEmpresa(certificadoDigitalEmpresa)
        );
    }

    @Test
    @Transactional
    void fullUpdateCertificadoDigitalEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedCertificadoDigitalEmpresa = certificadoDigitalEmpresaRepository.saveAndFlush(certificadoDigitalEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the certificadoDigitalEmpresa using partial update
        CertificadoDigitalEmpresa partialUpdatedCertificadoDigitalEmpresa = new CertificadoDigitalEmpresa();
        partialUpdatedCertificadoDigitalEmpresa.setId(certificadoDigitalEmpresa.getId());

        partialUpdatedCertificadoDigitalEmpresa
            .urlCertificado(UPDATED_URL_CERTIFICADO)
            .dataContratacao(UPDATED_DATA_CONTRATACAO)
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .diasUso(UPDATED_DIAS_USO);

        restCertificadoDigitalEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCertificadoDigitalEmpresa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCertificadoDigitalEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the CertificadoDigitalEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCertificadoDigitalEmpresaUpdatableFieldsEquals(
            partialUpdatedCertificadoDigitalEmpresa,
            getPersistedCertificadoDigitalEmpresa(partialUpdatedCertificadoDigitalEmpresa)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCertificadoDigitalEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        certificadoDigitalEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCertificadoDigitalEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, certificadoDigitalEmpresa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(certificadoDigitalEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the CertificadoDigitalEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCertificadoDigitalEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        certificadoDigitalEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCertificadoDigitalEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(certificadoDigitalEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the CertificadoDigitalEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCertificadoDigitalEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        certificadoDigitalEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCertificadoDigitalEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(certificadoDigitalEmpresa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CertificadoDigitalEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCertificadoDigitalEmpresa() throws Exception {
        // Initialize the database
        insertedCertificadoDigitalEmpresa = certificadoDigitalEmpresaRepository.saveAndFlush(certificadoDigitalEmpresa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the certificadoDigitalEmpresa
        restCertificadoDigitalEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, certificadoDigitalEmpresa.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return certificadoDigitalEmpresaRepository.count();
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

    protected CertificadoDigitalEmpresa getPersistedCertificadoDigitalEmpresa(CertificadoDigitalEmpresa certificadoDigitalEmpresa) {
        return certificadoDigitalEmpresaRepository.findById(certificadoDigitalEmpresa.getId()).orElseThrow();
    }

    protected void assertPersistedCertificadoDigitalEmpresaToMatchAllProperties(
        CertificadoDigitalEmpresa expectedCertificadoDigitalEmpresa
    ) {
        assertCertificadoDigitalEmpresaAllPropertiesEquals(
            expectedCertificadoDigitalEmpresa,
            getPersistedCertificadoDigitalEmpresa(expectedCertificadoDigitalEmpresa)
        );
    }

    protected void assertPersistedCertificadoDigitalEmpresaToMatchUpdatableProperties(
        CertificadoDigitalEmpresa expectedCertificadoDigitalEmpresa
    ) {
        assertCertificadoDigitalEmpresaAllUpdatablePropertiesEquals(
            expectedCertificadoDigitalEmpresa,
            getPersistedCertificadoDigitalEmpresa(expectedCertificadoDigitalEmpresa)
        );
    }
}
