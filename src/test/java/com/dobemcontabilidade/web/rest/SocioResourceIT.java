package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.SocioAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.domain.PessoaFisica;
import com.dobemcontabilidade.domain.Socio;
import com.dobemcontabilidade.domain.enumeration.FuncaoSocioEnum;
import com.dobemcontabilidade.repository.SocioRepository;
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
 * Integration tests for the {@link SocioResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SocioResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PROLABORE = false;
    private static final Boolean UPDATED_PROLABORE = true;

    private static final Double DEFAULT_PERCENTUAL_SOCIEDADE = 1D;
    private static final Double UPDATED_PERCENTUAL_SOCIEDADE = 2D;

    private static final Boolean DEFAULT_ADMINSTRADOR = false;
    private static final Boolean UPDATED_ADMINSTRADOR = true;

    private static final Boolean DEFAULT_DISTRIBUICAO_LUCRO = false;
    private static final Boolean UPDATED_DISTRIBUICAO_LUCRO = true;

    private static final Boolean DEFAULT_RESPONSAVEL_RECEITA = false;
    private static final Boolean UPDATED_RESPONSAVEL_RECEITA = true;

    private static final Double DEFAULT_PERCENTUAL_DISTRIBUICAO_LUCRO = 1D;
    private static final Double UPDATED_PERCENTUAL_DISTRIBUICAO_LUCRO = 2D;

    private static final FuncaoSocioEnum DEFAULT_FUNCAO_SOCIO = FuncaoSocioEnum.SOCIO;
    private static final FuncaoSocioEnum UPDATED_FUNCAO_SOCIO = FuncaoSocioEnum.SOCIO_ADMINISTRADOR;

    private static final String ENTITY_API_URL = "/api/socios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SocioRepository socioRepository;

    @Mock
    private SocioRepository socioRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSocioMockMvc;

    private Socio socio;

    private Socio insertedSocio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Socio createEntity(EntityManager em) {
        Socio socio = new Socio()
            .nome(DEFAULT_NOME)
            .prolabore(DEFAULT_PROLABORE)
            .percentualSociedade(DEFAULT_PERCENTUAL_SOCIEDADE)
            .adminstrador(DEFAULT_ADMINSTRADOR)
            .distribuicaoLucro(DEFAULT_DISTRIBUICAO_LUCRO)
            .responsavelReceita(DEFAULT_RESPONSAVEL_RECEITA)
            .percentualDistribuicaoLucro(DEFAULT_PERCENTUAL_DISTRIBUICAO_LUCRO)
            .funcaoSocio(DEFAULT_FUNCAO_SOCIO);
        // Add required entity
        PessoaFisica pessoaFisica;
        if (TestUtil.findAll(em, PessoaFisica.class).isEmpty()) {
            pessoaFisica = PessoaFisicaResourceIT.createEntity(em);
            em.persist(pessoaFisica);
            em.flush();
        } else {
            pessoaFisica = TestUtil.findAll(em, PessoaFisica.class).get(0);
        }
        socio.setPessoaFisica(pessoaFisica);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        socio.setEmpresa(empresa);
        return socio;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Socio createUpdatedEntity(EntityManager em) {
        Socio socio = new Socio()
            .nome(UPDATED_NOME)
            .prolabore(UPDATED_PROLABORE)
            .percentualSociedade(UPDATED_PERCENTUAL_SOCIEDADE)
            .adminstrador(UPDATED_ADMINSTRADOR)
            .distribuicaoLucro(UPDATED_DISTRIBUICAO_LUCRO)
            .responsavelReceita(UPDATED_RESPONSAVEL_RECEITA)
            .percentualDistribuicaoLucro(UPDATED_PERCENTUAL_DISTRIBUICAO_LUCRO)
            .funcaoSocio(UPDATED_FUNCAO_SOCIO);
        // Add required entity
        PessoaFisica pessoaFisica;
        if (TestUtil.findAll(em, PessoaFisica.class).isEmpty()) {
            pessoaFisica = PessoaFisicaResourceIT.createUpdatedEntity(em);
            em.persist(pessoaFisica);
            em.flush();
        } else {
            pessoaFisica = TestUtil.findAll(em, PessoaFisica.class).get(0);
        }
        socio.setPessoaFisica(pessoaFisica);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createUpdatedEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        socio.setEmpresa(empresa);
        return socio;
    }

    @BeforeEach
    public void initTest() {
        socio = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedSocio != null) {
            socioRepository.delete(insertedSocio);
            insertedSocio = null;
        }
    }

    @Test
    @Transactional
    void createSocio() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Socio
        var returnedSocio = om.readValue(
            restSocioMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(socio)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Socio.class
        );

        // Validate the Socio in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSocioUpdatableFieldsEquals(returnedSocio, getPersistedSocio(returnedSocio));

        insertedSocio = returnedSocio;
    }

    @Test
    @Transactional
    void createSocioWithExistingId() throws Exception {
        // Create the Socio with an existing ID
        socio.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocioMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(socio)))
            .andExpect(status().isBadRequest());

        // Validate the Socio in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAdminstradorIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        socio.setAdminstrador(null);

        // Create the Socio, which fails.

        restSocioMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(socio)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFuncaoSocioIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        socio.setFuncaoSocio(null);

        // Create the Socio, which fails.

        restSocioMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(socio)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSocios() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList
        restSocioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].prolabore").value(hasItem(DEFAULT_PROLABORE.booleanValue())))
            .andExpect(jsonPath("$.[*].percentualSociedade").value(hasItem(DEFAULT_PERCENTUAL_SOCIEDADE.doubleValue())))
            .andExpect(jsonPath("$.[*].adminstrador").value(hasItem(DEFAULT_ADMINSTRADOR.booleanValue())))
            .andExpect(jsonPath("$.[*].distribuicaoLucro").value(hasItem(DEFAULT_DISTRIBUICAO_LUCRO.booleanValue())))
            .andExpect(jsonPath("$.[*].responsavelReceita").value(hasItem(DEFAULT_RESPONSAVEL_RECEITA.booleanValue())))
            .andExpect(jsonPath("$.[*].percentualDistribuicaoLucro").value(hasItem(DEFAULT_PERCENTUAL_DISTRIBUICAO_LUCRO.doubleValue())))
            .andExpect(jsonPath("$.[*].funcaoSocio").value(hasItem(DEFAULT_FUNCAO_SOCIO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSociosWithEagerRelationshipsIsEnabled() throws Exception {
        when(socioRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSocioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(socioRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSociosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(socioRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSocioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(socioRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSocio() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get the socio
        restSocioMockMvc
            .perform(get(ENTITY_API_URL_ID, socio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(socio.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.prolabore").value(DEFAULT_PROLABORE.booleanValue()))
            .andExpect(jsonPath("$.percentualSociedade").value(DEFAULT_PERCENTUAL_SOCIEDADE.doubleValue()))
            .andExpect(jsonPath("$.adminstrador").value(DEFAULT_ADMINSTRADOR.booleanValue()))
            .andExpect(jsonPath("$.distribuicaoLucro").value(DEFAULT_DISTRIBUICAO_LUCRO.booleanValue()))
            .andExpect(jsonPath("$.responsavelReceita").value(DEFAULT_RESPONSAVEL_RECEITA.booleanValue()))
            .andExpect(jsonPath("$.percentualDistribuicaoLucro").value(DEFAULT_PERCENTUAL_DISTRIBUICAO_LUCRO.doubleValue()))
            .andExpect(jsonPath("$.funcaoSocio").value(DEFAULT_FUNCAO_SOCIO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSocio() throws Exception {
        // Get the socio
        restSocioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSocio() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the socio
        Socio updatedSocio = socioRepository.findById(socio.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSocio are not directly saved in db
        em.detach(updatedSocio);
        updatedSocio
            .nome(UPDATED_NOME)
            .prolabore(UPDATED_PROLABORE)
            .percentualSociedade(UPDATED_PERCENTUAL_SOCIEDADE)
            .adminstrador(UPDATED_ADMINSTRADOR)
            .distribuicaoLucro(UPDATED_DISTRIBUICAO_LUCRO)
            .responsavelReceita(UPDATED_RESPONSAVEL_RECEITA)
            .percentualDistribuicaoLucro(UPDATED_PERCENTUAL_DISTRIBUICAO_LUCRO)
            .funcaoSocio(UPDATED_FUNCAO_SOCIO);

        restSocioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSocio.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSocio))
            )
            .andExpect(status().isOk());

        // Validate the Socio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSocioToMatchAllProperties(updatedSocio);
    }

    @Test
    @Transactional
    void putNonExistingSocio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        socio.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, socio.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(socio))
            )
            .andExpect(status().isBadRequest());

        // Validate the Socio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSocio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        socio.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(socio))
            )
            .andExpect(status().isBadRequest());

        // Validate the Socio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSocio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        socio.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocioMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(socio)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Socio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSocioWithPatch() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the socio using partial update
        Socio partialUpdatedSocio = new Socio();
        partialUpdatedSocio.setId(socio.getId());

        partialUpdatedSocio
            .percentualSociedade(UPDATED_PERCENTUAL_SOCIEDADE)
            .distribuicaoLucro(UPDATED_DISTRIBUICAO_LUCRO)
            .responsavelReceita(UPDATED_RESPONSAVEL_RECEITA)
            .percentualDistribuicaoLucro(UPDATED_PERCENTUAL_DISTRIBUICAO_LUCRO)
            .funcaoSocio(UPDATED_FUNCAO_SOCIO);

        restSocioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocio.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSocio))
            )
            .andExpect(status().isOk());

        // Validate the Socio in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSocioUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSocio, socio), getPersistedSocio(socio));
    }

    @Test
    @Transactional
    void fullUpdateSocioWithPatch() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the socio using partial update
        Socio partialUpdatedSocio = new Socio();
        partialUpdatedSocio.setId(socio.getId());

        partialUpdatedSocio
            .nome(UPDATED_NOME)
            .prolabore(UPDATED_PROLABORE)
            .percentualSociedade(UPDATED_PERCENTUAL_SOCIEDADE)
            .adminstrador(UPDATED_ADMINSTRADOR)
            .distribuicaoLucro(UPDATED_DISTRIBUICAO_LUCRO)
            .responsavelReceita(UPDATED_RESPONSAVEL_RECEITA)
            .percentualDistribuicaoLucro(UPDATED_PERCENTUAL_DISTRIBUICAO_LUCRO)
            .funcaoSocio(UPDATED_FUNCAO_SOCIO);

        restSocioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocio.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSocio))
            )
            .andExpect(status().isOk());

        // Validate the Socio in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSocioUpdatableFieldsEquals(partialUpdatedSocio, getPersistedSocio(partialUpdatedSocio));
    }

    @Test
    @Transactional
    void patchNonExistingSocio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        socio.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, socio.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(socio))
            )
            .andExpect(status().isBadRequest());

        // Validate the Socio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSocio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        socio.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(socio))
            )
            .andExpect(status().isBadRequest());

        // Validate the Socio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSocio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        socio.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocioMockMvc
            .perform(patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(socio)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Socio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSocio() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the socio
        restSocioMockMvc
            .perform(delete(ENTITY_API_URL_ID, socio.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return socioRepository.count();
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

    protected Socio getPersistedSocio(Socio socio) {
        return socioRepository.findById(socio.getId()).orElseThrow();
    }

    protected void assertPersistedSocioToMatchAllProperties(Socio expectedSocio) {
        assertSocioAllPropertiesEquals(expectedSocio, getPersistedSocio(expectedSocio));
    }

    protected void assertPersistedSocioToMatchUpdatableProperties(Socio expectedSocio) {
        assertSocioAllUpdatablePropertiesEquals(expectedSocio, getPersistedSocio(expectedSocio));
    }
}
