package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.CertificadoDigitalAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.CertificadoDigital;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.domain.enumeration.TipoCertificadoEnum;
import com.dobemcontabilidade.repository.CertificadoDigitalRepository;
import com.dobemcontabilidade.service.CertificadoDigitalService;
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
 * Integration tests for the {@link CertificadoDigitalResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CertificadoDigitalResourceIT {

    private static final String DEFAULT_URL_CERTIFICADO = "AAAAAAAAAA";
    private static final String UPDATED_URL_CERTIFICADO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_CONTRATACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_CONTRATACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VALIDADE = 1;
    private static final Integer UPDATED_VALIDADE = 2;
    private static final Integer SMALLER_VALIDADE = 1 - 1;

    private static final TipoCertificadoEnum DEFAULT_TIPO_CERTIFICADO = TipoCertificadoEnum.A;
    private static final TipoCertificadoEnum UPDATED_TIPO_CERTIFICADO = TipoCertificadoEnum.S;

    private static final String ENTITY_API_URL = "/api/certificado-digitals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CertificadoDigitalRepository certificadoDigitalRepository;

    @Mock
    private CertificadoDigitalRepository certificadoDigitalRepositoryMock;

    @Mock
    private CertificadoDigitalService certificadoDigitalServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCertificadoDigitalMockMvc;

    private CertificadoDigital certificadoDigital;

    private CertificadoDigital insertedCertificadoDigital;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CertificadoDigital createEntity(EntityManager em) {
        CertificadoDigital certificadoDigital = new CertificadoDigital()
            .urlCertificado(DEFAULT_URL_CERTIFICADO)
            .dataContratacao(DEFAULT_DATA_CONTRATACAO)
            .validade(DEFAULT_VALIDADE)
            .tipoCertificado(DEFAULT_TIPO_CERTIFICADO);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        certificadoDigital.setEmpresa(empresa);
        return certificadoDigital;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CertificadoDigital createUpdatedEntity(EntityManager em) {
        CertificadoDigital certificadoDigital = new CertificadoDigital()
            .urlCertificado(UPDATED_URL_CERTIFICADO)
            .dataContratacao(UPDATED_DATA_CONTRATACAO)
            .validade(UPDATED_VALIDADE)
            .tipoCertificado(UPDATED_TIPO_CERTIFICADO);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createUpdatedEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        certificadoDigital.setEmpresa(empresa);
        return certificadoDigital;
    }

    @BeforeEach
    public void initTest() {
        certificadoDigital = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedCertificadoDigital != null) {
            certificadoDigitalRepository.delete(insertedCertificadoDigital);
            insertedCertificadoDigital = null;
        }
    }

    @Test
    @Transactional
    void createCertificadoDigital() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CertificadoDigital
        var returnedCertificadoDigital = om.readValue(
            restCertificadoDigitalMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(certificadoDigital)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CertificadoDigital.class
        );

        // Validate the CertificadoDigital in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCertificadoDigitalUpdatableFieldsEquals(
            returnedCertificadoDigital,
            getPersistedCertificadoDigital(returnedCertificadoDigital)
        );

        insertedCertificadoDigital = returnedCertificadoDigital;
    }

    @Test
    @Transactional
    void createCertificadoDigitalWithExistingId() throws Exception {
        // Create the CertificadoDigital with an existing ID
        certificadoDigital.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCertificadoDigitalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(certificadoDigital)))
            .andExpect(status().isBadRequest());

        // Validate the CertificadoDigital in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCertificadoDigitals() throws Exception {
        // Initialize the database
        insertedCertificadoDigital = certificadoDigitalRepository.saveAndFlush(certificadoDigital);

        // Get all the certificadoDigitalList
        restCertificadoDigitalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(certificadoDigital.getId().intValue())))
            .andExpect(jsonPath("$.[*].urlCertificado").value(hasItem(DEFAULT_URL_CERTIFICADO.toString())))
            .andExpect(jsonPath("$.[*].dataContratacao").value(hasItem(DEFAULT_DATA_CONTRATACAO.toString())))
            .andExpect(jsonPath("$.[*].validade").value(hasItem(DEFAULT_VALIDADE)))
            .andExpect(jsonPath("$.[*].tipoCertificado").value(hasItem(DEFAULT_TIPO_CERTIFICADO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCertificadoDigitalsWithEagerRelationshipsIsEnabled() throws Exception {
        when(certificadoDigitalServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCertificadoDigitalMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(certificadoDigitalServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCertificadoDigitalsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(certificadoDigitalServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCertificadoDigitalMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(certificadoDigitalRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCertificadoDigital() throws Exception {
        // Initialize the database
        insertedCertificadoDigital = certificadoDigitalRepository.saveAndFlush(certificadoDigital);

        // Get the certificadoDigital
        restCertificadoDigitalMockMvc
            .perform(get(ENTITY_API_URL_ID, certificadoDigital.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(certificadoDigital.getId().intValue()))
            .andExpect(jsonPath("$.urlCertificado").value(DEFAULT_URL_CERTIFICADO.toString()))
            .andExpect(jsonPath("$.dataContratacao").value(DEFAULT_DATA_CONTRATACAO.toString()))
            .andExpect(jsonPath("$.validade").value(DEFAULT_VALIDADE))
            .andExpect(jsonPath("$.tipoCertificado").value(DEFAULT_TIPO_CERTIFICADO.toString()));
    }

    @Test
    @Transactional
    void getCertificadoDigitalsByIdFiltering() throws Exception {
        // Initialize the database
        insertedCertificadoDigital = certificadoDigitalRepository.saveAndFlush(certificadoDigital);

        Long id = certificadoDigital.getId();

        defaultCertificadoDigitalFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultCertificadoDigitalFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultCertificadoDigitalFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCertificadoDigitalsByDataContratacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCertificadoDigital = certificadoDigitalRepository.saveAndFlush(certificadoDigital);

        // Get all the certificadoDigitalList where dataContratacao equals to
        defaultCertificadoDigitalFiltering(
            "dataContratacao.equals=" + DEFAULT_DATA_CONTRATACAO,
            "dataContratacao.equals=" + UPDATED_DATA_CONTRATACAO
        );
    }

    @Test
    @Transactional
    void getAllCertificadoDigitalsByDataContratacaoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCertificadoDigital = certificadoDigitalRepository.saveAndFlush(certificadoDigital);

        // Get all the certificadoDigitalList where dataContratacao in
        defaultCertificadoDigitalFiltering(
            "dataContratacao.in=" + DEFAULT_DATA_CONTRATACAO + "," + UPDATED_DATA_CONTRATACAO,
            "dataContratacao.in=" + UPDATED_DATA_CONTRATACAO
        );
    }

    @Test
    @Transactional
    void getAllCertificadoDigitalsByDataContratacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCertificadoDigital = certificadoDigitalRepository.saveAndFlush(certificadoDigital);

        // Get all the certificadoDigitalList where dataContratacao is not null
        defaultCertificadoDigitalFiltering("dataContratacao.specified=true", "dataContratacao.specified=false");
    }

    @Test
    @Transactional
    void getAllCertificadoDigitalsByValidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCertificadoDigital = certificadoDigitalRepository.saveAndFlush(certificadoDigital);

        // Get all the certificadoDigitalList where validade equals to
        defaultCertificadoDigitalFiltering("validade.equals=" + DEFAULT_VALIDADE, "validade.equals=" + UPDATED_VALIDADE);
    }

    @Test
    @Transactional
    void getAllCertificadoDigitalsByValidadeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCertificadoDigital = certificadoDigitalRepository.saveAndFlush(certificadoDigital);

        // Get all the certificadoDigitalList where validade in
        defaultCertificadoDigitalFiltering("validade.in=" + DEFAULT_VALIDADE + "," + UPDATED_VALIDADE, "validade.in=" + UPDATED_VALIDADE);
    }

    @Test
    @Transactional
    void getAllCertificadoDigitalsByValidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCertificadoDigital = certificadoDigitalRepository.saveAndFlush(certificadoDigital);

        // Get all the certificadoDigitalList where validade is not null
        defaultCertificadoDigitalFiltering("validade.specified=true", "validade.specified=false");
    }

    @Test
    @Transactional
    void getAllCertificadoDigitalsByValidadeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCertificadoDigital = certificadoDigitalRepository.saveAndFlush(certificadoDigital);

        // Get all the certificadoDigitalList where validade is greater than or equal to
        defaultCertificadoDigitalFiltering(
            "validade.greaterThanOrEqual=" + DEFAULT_VALIDADE,
            "validade.greaterThanOrEqual=" + UPDATED_VALIDADE
        );
    }

    @Test
    @Transactional
    void getAllCertificadoDigitalsByValidadeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCertificadoDigital = certificadoDigitalRepository.saveAndFlush(certificadoDigital);

        // Get all the certificadoDigitalList where validade is less than or equal to
        defaultCertificadoDigitalFiltering("validade.lessThanOrEqual=" + DEFAULT_VALIDADE, "validade.lessThanOrEqual=" + SMALLER_VALIDADE);
    }

    @Test
    @Transactional
    void getAllCertificadoDigitalsByValidadeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedCertificadoDigital = certificadoDigitalRepository.saveAndFlush(certificadoDigital);

        // Get all the certificadoDigitalList where validade is less than
        defaultCertificadoDigitalFiltering("validade.lessThan=" + UPDATED_VALIDADE, "validade.lessThan=" + DEFAULT_VALIDADE);
    }

    @Test
    @Transactional
    void getAllCertificadoDigitalsByValidadeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedCertificadoDigital = certificadoDigitalRepository.saveAndFlush(certificadoDigital);

        // Get all the certificadoDigitalList where validade is greater than
        defaultCertificadoDigitalFiltering("validade.greaterThan=" + SMALLER_VALIDADE, "validade.greaterThan=" + DEFAULT_VALIDADE);
    }

    @Test
    @Transactional
    void getAllCertificadoDigitalsByTipoCertificadoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCertificadoDigital = certificadoDigitalRepository.saveAndFlush(certificadoDigital);

        // Get all the certificadoDigitalList where tipoCertificado equals to
        defaultCertificadoDigitalFiltering(
            "tipoCertificado.equals=" + DEFAULT_TIPO_CERTIFICADO,
            "tipoCertificado.equals=" + UPDATED_TIPO_CERTIFICADO
        );
    }

    @Test
    @Transactional
    void getAllCertificadoDigitalsByTipoCertificadoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCertificadoDigital = certificadoDigitalRepository.saveAndFlush(certificadoDigital);

        // Get all the certificadoDigitalList where tipoCertificado in
        defaultCertificadoDigitalFiltering(
            "tipoCertificado.in=" + DEFAULT_TIPO_CERTIFICADO + "," + UPDATED_TIPO_CERTIFICADO,
            "tipoCertificado.in=" + UPDATED_TIPO_CERTIFICADO
        );
    }

    @Test
    @Transactional
    void getAllCertificadoDigitalsByTipoCertificadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCertificadoDigital = certificadoDigitalRepository.saveAndFlush(certificadoDigital);

        // Get all the certificadoDigitalList where tipoCertificado is not null
        defaultCertificadoDigitalFiltering("tipoCertificado.specified=true", "tipoCertificado.specified=false");
    }

    @Test
    @Transactional
    void getAllCertificadoDigitalsByEmpresaIsEqualToSomething() throws Exception {
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            certificadoDigitalRepository.saveAndFlush(certificadoDigital);
            empresa = EmpresaResourceIT.createEntity(em);
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        em.persist(empresa);
        em.flush();
        certificadoDigital.setEmpresa(empresa);
        certificadoDigitalRepository.saveAndFlush(certificadoDigital);
        Long empresaId = empresa.getId();
        // Get all the certificadoDigitalList where empresa equals to empresaId
        defaultCertificadoDigitalShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the certificadoDigitalList where empresa equals to (empresaId + 1)
        defaultCertificadoDigitalShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    private void defaultCertificadoDigitalFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultCertificadoDigitalShouldBeFound(shouldBeFound);
        defaultCertificadoDigitalShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCertificadoDigitalShouldBeFound(String filter) throws Exception {
        restCertificadoDigitalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(certificadoDigital.getId().intValue())))
            .andExpect(jsonPath("$.[*].urlCertificado").value(hasItem(DEFAULT_URL_CERTIFICADO.toString())))
            .andExpect(jsonPath("$.[*].dataContratacao").value(hasItem(DEFAULT_DATA_CONTRATACAO.toString())))
            .andExpect(jsonPath("$.[*].validade").value(hasItem(DEFAULT_VALIDADE)))
            .andExpect(jsonPath("$.[*].tipoCertificado").value(hasItem(DEFAULT_TIPO_CERTIFICADO.toString())));

        // Check, that the count call also returns 1
        restCertificadoDigitalMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCertificadoDigitalShouldNotBeFound(String filter) throws Exception {
        restCertificadoDigitalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCertificadoDigitalMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCertificadoDigital() throws Exception {
        // Get the certificadoDigital
        restCertificadoDigitalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCertificadoDigital() throws Exception {
        // Initialize the database
        insertedCertificadoDigital = certificadoDigitalRepository.saveAndFlush(certificadoDigital);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the certificadoDigital
        CertificadoDigital updatedCertificadoDigital = certificadoDigitalRepository.findById(certificadoDigital.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCertificadoDigital are not directly saved in db
        em.detach(updatedCertificadoDigital);
        updatedCertificadoDigital
            .urlCertificado(UPDATED_URL_CERTIFICADO)
            .dataContratacao(UPDATED_DATA_CONTRATACAO)
            .validade(UPDATED_VALIDADE)
            .tipoCertificado(UPDATED_TIPO_CERTIFICADO);

        restCertificadoDigitalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCertificadoDigital.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCertificadoDigital))
            )
            .andExpect(status().isOk());

        // Validate the CertificadoDigital in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCertificadoDigitalToMatchAllProperties(updatedCertificadoDigital);
    }

    @Test
    @Transactional
    void putNonExistingCertificadoDigital() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        certificadoDigital.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCertificadoDigitalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, certificadoDigital.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(certificadoDigital))
            )
            .andExpect(status().isBadRequest());

        // Validate the CertificadoDigital in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCertificadoDigital() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        certificadoDigital.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCertificadoDigitalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(certificadoDigital))
            )
            .andExpect(status().isBadRequest());

        // Validate the CertificadoDigital in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCertificadoDigital() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        certificadoDigital.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCertificadoDigitalMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(certificadoDigital)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CertificadoDigital in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCertificadoDigitalWithPatch() throws Exception {
        // Initialize the database
        insertedCertificadoDigital = certificadoDigitalRepository.saveAndFlush(certificadoDigital);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the certificadoDigital using partial update
        CertificadoDigital partialUpdatedCertificadoDigital = new CertificadoDigital();
        partialUpdatedCertificadoDigital.setId(certificadoDigital.getId());

        partialUpdatedCertificadoDigital.urlCertificado(UPDATED_URL_CERTIFICADO).dataContratacao(UPDATED_DATA_CONTRATACAO);

        restCertificadoDigitalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCertificadoDigital.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCertificadoDigital))
            )
            .andExpect(status().isOk());

        // Validate the CertificadoDigital in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCertificadoDigitalUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCertificadoDigital, certificadoDigital),
            getPersistedCertificadoDigital(certificadoDigital)
        );
    }

    @Test
    @Transactional
    void fullUpdateCertificadoDigitalWithPatch() throws Exception {
        // Initialize the database
        insertedCertificadoDigital = certificadoDigitalRepository.saveAndFlush(certificadoDigital);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the certificadoDigital using partial update
        CertificadoDigital partialUpdatedCertificadoDigital = new CertificadoDigital();
        partialUpdatedCertificadoDigital.setId(certificadoDigital.getId());

        partialUpdatedCertificadoDigital
            .urlCertificado(UPDATED_URL_CERTIFICADO)
            .dataContratacao(UPDATED_DATA_CONTRATACAO)
            .validade(UPDATED_VALIDADE)
            .tipoCertificado(UPDATED_TIPO_CERTIFICADO);

        restCertificadoDigitalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCertificadoDigital.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCertificadoDigital))
            )
            .andExpect(status().isOk());

        // Validate the CertificadoDigital in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCertificadoDigitalUpdatableFieldsEquals(
            partialUpdatedCertificadoDigital,
            getPersistedCertificadoDigital(partialUpdatedCertificadoDigital)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCertificadoDigital() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        certificadoDigital.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCertificadoDigitalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, certificadoDigital.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(certificadoDigital))
            )
            .andExpect(status().isBadRequest());

        // Validate the CertificadoDigital in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCertificadoDigital() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        certificadoDigital.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCertificadoDigitalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(certificadoDigital))
            )
            .andExpect(status().isBadRequest());

        // Validate the CertificadoDigital in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCertificadoDigital() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        certificadoDigital.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCertificadoDigitalMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(certificadoDigital)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CertificadoDigital in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCertificadoDigital() throws Exception {
        // Initialize the database
        insertedCertificadoDigital = certificadoDigitalRepository.saveAndFlush(certificadoDigital);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the certificadoDigital
        restCertificadoDigitalMockMvc
            .perform(delete(ENTITY_API_URL_ID, certificadoDigital.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return certificadoDigitalRepository.count();
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

    protected CertificadoDigital getPersistedCertificadoDigital(CertificadoDigital certificadoDigital) {
        return certificadoDigitalRepository.findById(certificadoDigital.getId()).orElseThrow();
    }

    protected void assertPersistedCertificadoDigitalToMatchAllProperties(CertificadoDigital expectedCertificadoDigital) {
        assertCertificadoDigitalAllPropertiesEquals(expectedCertificadoDigital, getPersistedCertificadoDigital(expectedCertificadoDigital));
    }

    protected void assertPersistedCertificadoDigitalToMatchUpdatableProperties(CertificadoDigital expectedCertificadoDigital) {
        assertCertificadoDigitalAllUpdatablePropertiesEquals(
            expectedCertificadoDigital,
            getPersistedCertificadoDigital(expectedCertificadoDigital)
        );
    }
}
