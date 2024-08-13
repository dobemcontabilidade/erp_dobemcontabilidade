package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.DependentesFuncionarioAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.DependentesFuncionario;
import com.dobemcontabilidade.domain.Funcionario;
import com.dobemcontabilidade.domain.Pessoa;
import com.dobemcontabilidade.domain.enumeration.TipoDependenteFuncionarioEnum;
import com.dobemcontabilidade.repository.DependentesFuncionarioRepository;
import com.dobemcontabilidade.service.DependentesFuncionarioService;
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
 * Integration tests for the {@link DependentesFuncionarioResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DependentesFuncionarioResourceIT {

    private static final String DEFAULT_URL_CERTIDAO_DEPENDENTE = "AAAAAAAAAA";
    private static final String UPDATED_URL_CERTIDAO_DEPENDENTE = "BBBBBBBBBB";

    private static final String DEFAULT_URL_RG_DEPENDENTE = "AAAAAAAAAA";
    private static final String UPDATED_URL_RG_DEPENDENTE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DEPENDENTE_IRRF = false;
    private static final Boolean UPDATED_DEPENDENTE_IRRF = true;

    private static final Boolean DEFAULT_DEPENDENTE_SALARIO_FAMILIA = false;
    private static final Boolean UPDATED_DEPENDENTE_SALARIO_FAMILIA = true;

    private static final TipoDependenteFuncionarioEnum DEFAULT_TIPO_DEPENDENTE_FUNCIONARIO_ENUM = TipoDependenteFuncionarioEnum.CONJUGE;
    private static final TipoDependenteFuncionarioEnum UPDATED_TIPO_DEPENDENTE_FUNCIONARIO_ENUM = TipoDependenteFuncionarioEnum.FILHO;

    private static final String ENTITY_API_URL = "/api/dependentes-funcionarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DependentesFuncionarioRepository dependentesFuncionarioRepository;

    @Mock
    private DependentesFuncionarioRepository dependentesFuncionarioRepositoryMock;

    @Mock
    private DependentesFuncionarioService dependentesFuncionarioServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDependentesFuncionarioMockMvc;

    private DependentesFuncionario dependentesFuncionario;

    private DependentesFuncionario insertedDependentesFuncionario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DependentesFuncionario createEntity(EntityManager em) {
        DependentesFuncionario dependentesFuncionario = new DependentesFuncionario()
            .urlCertidaoDependente(DEFAULT_URL_CERTIDAO_DEPENDENTE)
            .urlRgDependente(DEFAULT_URL_RG_DEPENDENTE)
            .dependenteIRRF(DEFAULT_DEPENDENTE_IRRF)
            .dependenteSalarioFamilia(DEFAULT_DEPENDENTE_SALARIO_FAMILIA)
            .tipoDependenteFuncionarioEnum(DEFAULT_TIPO_DEPENDENTE_FUNCIONARIO_ENUM);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        dependentesFuncionario.setPessoa(pessoa);
        // Add required entity
        Funcionario funcionario;
        if (TestUtil.findAll(em, Funcionario.class).isEmpty()) {
            funcionario = FuncionarioResourceIT.createEntity(em);
            em.persist(funcionario);
            em.flush();
        } else {
            funcionario = TestUtil.findAll(em, Funcionario.class).get(0);
        }
        dependentesFuncionario.setFuncionario(funcionario);
        return dependentesFuncionario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DependentesFuncionario createUpdatedEntity(EntityManager em) {
        DependentesFuncionario dependentesFuncionario = new DependentesFuncionario()
            .urlCertidaoDependente(UPDATED_URL_CERTIDAO_DEPENDENTE)
            .urlRgDependente(UPDATED_URL_RG_DEPENDENTE)
            .dependenteIRRF(UPDATED_DEPENDENTE_IRRF)
            .dependenteSalarioFamilia(UPDATED_DEPENDENTE_SALARIO_FAMILIA)
            .tipoDependenteFuncionarioEnum(UPDATED_TIPO_DEPENDENTE_FUNCIONARIO_ENUM);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createUpdatedEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        dependentesFuncionario.setPessoa(pessoa);
        // Add required entity
        Funcionario funcionario;
        if (TestUtil.findAll(em, Funcionario.class).isEmpty()) {
            funcionario = FuncionarioResourceIT.createUpdatedEntity(em);
            em.persist(funcionario);
            em.flush();
        } else {
            funcionario = TestUtil.findAll(em, Funcionario.class).get(0);
        }
        dependentesFuncionario.setFuncionario(funcionario);
        return dependentesFuncionario;
    }

    @BeforeEach
    public void initTest() {
        dependentesFuncionario = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedDependentesFuncionario != null) {
            dependentesFuncionarioRepository.delete(insertedDependentesFuncionario);
            insertedDependentesFuncionario = null;
        }
    }

    @Test
    @Transactional
    void createDependentesFuncionario() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DependentesFuncionario
        var returnedDependentesFuncionario = om.readValue(
            restDependentesFuncionarioMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dependentesFuncionario)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DependentesFuncionario.class
        );

        // Validate the DependentesFuncionario in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDependentesFuncionarioUpdatableFieldsEquals(
            returnedDependentesFuncionario,
            getPersistedDependentesFuncionario(returnedDependentesFuncionario)
        );

        insertedDependentesFuncionario = returnedDependentesFuncionario;
    }

    @Test
    @Transactional
    void createDependentesFuncionarioWithExistingId() throws Exception {
        // Create the DependentesFuncionario with an existing ID
        dependentesFuncionario.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDependentesFuncionarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dependentesFuncionario)))
            .andExpect(status().isBadRequest());

        // Validate the DependentesFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDependentesFuncionarios() throws Exception {
        // Initialize the database
        insertedDependentesFuncionario = dependentesFuncionarioRepository.saveAndFlush(dependentesFuncionario);

        // Get all the dependentesFuncionarioList
        restDependentesFuncionarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dependentesFuncionario.getId().intValue())))
            .andExpect(jsonPath("$.[*].urlCertidaoDependente").value(hasItem(DEFAULT_URL_CERTIDAO_DEPENDENTE)))
            .andExpect(jsonPath("$.[*].urlRgDependente").value(hasItem(DEFAULT_URL_RG_DEPENDENTE)))
            .andExpect(jsonPath("$.[*].dependenteIRRF").value(hasItem(DEFAULT_DEPENDENTE_IRRF.booleanValue())))
            .andExpect(jsonPath("$.[*].dependenteSalarioFamilia").value(hasItem(DEFAULT_DEPENDENTE_SALARIO_FAMILIA.booleanValue())))
            .andExpect(jsonPath("$.[*].tipoDependenteFuncionarioEnum").value(hasItem(DEFAULT_TIPO_DEPENDENTE_FUNCIONARIO_ENUM.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDependentesFuncionariosWithEagerRelationshipsIsEnabled() throws Exception {
        when(dependentesFuncionarioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDependentesFuncionarioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(dependentesFuncionarioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDependentesFuncionariosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(dependentesFuncionarioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDependentesFuncionarioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(dependentesFuncionarioRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDependentesFuncionario() throws Exception {
        // Initialize the database
        insertedDependentesFuncionario = dependentesFuncionarioRepository.saveAndFlush(dependentesFuncionario);

        // Get the dependentesFuncionario
        restDependentesFuncionarioMockMvc
            .perform(get(ENTITY_API_URL_ID, dependentesFuncionario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dependentesFuncionario.getId().intValue()))
            .andExpect(jsonPath("$.urlCertidaoDependente").value(DEFAULT_URL_CERTIDAO_DEPENDENTE))
            .andExpect(jsonPath("$.urlRgDependente").value(DEFAULT_URL_RG_DEPENDENTE))
            .andExpect(jsonPath("$.dependenteIRRF").value(DEFAULT_DEPENDENTE_IRRF.booleanValue()))
            .andExpect(jsonPath("$.dependenteSalarioFamilia").value(DEFAULT_DEPENDENTE_SALARIO_FAMILIA.booleanValue()))
            .andExpect(jsonPath("$.tipoDependenteFuncionarioEnum").value(DEFAULT_TIPO_DEPENDENTE_FUNCIONARIO_ENUM.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDependentesFuncionario() throws Exception {
        // Get the dependentesFuncionario
        restDependentesFuncionarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDependentesFuncionario() throws Exception {
        // Initialize the database
        insertedDependentesFuncionario = dependentesFuncionarioRepository.saveAndFlush(dependentesFuncionario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dependentesFuncionario
        DependentesFuncionario updatedDependentesFuncionario = dependentesFuncionarioRepository
            .findById(dependentesFuncionario.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedDependentesFuncionario are not directly saved in db
        em.detach(updatedDependentesFuncionario);
        updatedDependentesFuncionario
            .urlCertidaoDependente(UPDATED_URL_CERTIDAO_DEPENDENTE)
            .urlRgDependente(UPDATED_URL_RG_DEPENDENTE)
            .dependenteIRRF(UPDATED_DEPENDENTE_IRRF)
            .dependenteSalarioFamilia(UPDATED_DEPENDENTE_SALARIO_FAMILIA)
            .tipoDependenteFuncionarioEnum(UPDATED_TIPO_DEPENDENTE_FUNCIONARIO_ENUM);

        restDependentesFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDependentesFuncionario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDependentesFuncionario))
            )
            .andExpect(status().isOk());

        // Validate the DependentesFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDependentesFuncionarioToMatchAllProperties(updatedDependentesFuncionario);
    }

    @Test
    @Transactional
    void putNonExistingDependentesFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dependentesFuncionario.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDependentesFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dependentesFuncionario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(dependentesFuncionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the DependentesFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDependentesFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dependentesFuncionario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDependentesFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(dependentesFuncionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the DependentesFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDependentesFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dependentesFuncionario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDependentesFuncionarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dependentesFuncionario)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DependentesFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDependentesFuncionarioWithPatch() throws Exception {
        // Initialize the database
        insertedDependentesFuncionario = dependentesFuncionarioRepository.saveAndFlush(dependentesFuncionario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dependentesFuncionario using partial update
        DependentesFuncionario partialUpdatedDependentesFuncionario = new DependentesFuncionario();
        partialUpdatedDependentesFuncionario.setId(dependentesFuncionario.getId());

        partialUpdatedDependentesFuncionario
            .urlCertidaoDependente(UPDATED_URL_CERTIDAO_DEPENDENTE)
            .urlRgDependente(UPDATED_URL_RG_DEPENDENTE)
            .tipoDependenteFuncionarioEnum(UPDATED_TIPO_DEPENDENTE_FUNCIONARIO_ENUM);

        restDependentesFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDependentesFuncionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDependentesFuncionario))
            )
            .andExpect(status().isOk());

        // Validate the DependentesFuncionario in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDependentesFuncionarioUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDependentesFuncionario, dependentesFuncionario),
            getPersistedDependentesFuncionario(dependentesFuncionario)
        );
    }

    @Test
    @Transactional
    void fullUpdateDependentesFuncionarioWithPatch() throws Exception {
        // Initialize the database
        insertedDependentesFuncionario = dependentesFuncionarioRepository.saveAndFlush(dependentesFuncionario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dependentesFuncionario using partial update
        DependentesFuncionario partialUpdatedDependentesFuncionario = new DependentesFuncionario();
        partialUpdatedDependentesFuncionario.setId(dependentesFuncionario.getId());

        partialUpdatedDependentesFuncionario
            .urlCertidaoDependente(UPDATED_URL_CERTIDAO_DEPENDENTE)
            .urlRgDependente(UPDATED_URL_RG_DEPENDENTE)
            .dependenteIRRF(UPDATED_DEPENDENTE_IRRF)
            .dependenteSalarioFamilia(UPDATED_DEPENDENTE_SALARIO_FAMILIA)
            .tipoDependenteFuncionarioEnum(UPDATED_TIPO_DEPENDENTE_FUNCIONARIO_ENUM);

        restDependentesFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDependentesFuncionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDependentesFuncionario))
            )
            .andExpect(status().isOk());

        // Validate the DependentesFuncionario in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDependentesFuncionarioUpdatableFieldsEquals(
            partialUpdatedDependentesFuncionario,
            getPersistedDependentesFuncionario(partialUpdatedDependentesFuncionario)
        );
    }

    @Test
    @Transactional
    void patchNonExistingDependentesFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dependentesFuncionario.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDependentesFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dependentesFuncionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(dependentesFuncionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the DependentesFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDependentesFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dependentesFuncionario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDependentesFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(dependentesFuncionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the DependentesFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDependentesFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dependentesFuncionario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDependentesFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(dependentesFuncionario))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DependentesFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDependentesFuncionario() throws Exception {
        // Initialize the database
        insertedDependentesFuncionario = dependentesFuncionarioRepository.saveAndFlush(dependentesFuncionario);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the dependentesFuncionario
        restDependentesFuncionarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, dependentesFuncionario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return dependentesFuncionarioRepository.count();
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

    protected DependentesFuncionario getPersistedDependentesFuncionario(DependentesFuncionario dependentesFuncionario) {
        return dependentesFuncionarioRepository.findById(dependentesFuncionario.getId()).orElseThrow();
    }

    protected void assertPersistedDependentesFuncionarioToMatchAllProperties(DependentesFuncionario expectedDependentesFuncionario) {
        assertDependentesFuncionarioAllPropertiesEquals(
            expectedDependentesFuncionario,
            getPersistedDependentesFuncionario(expectedDependentesFuncionario)
        );
    }

    protected void assertPersistedDependentesFuncionarioToMatchUpdatableProperties(DependentesFuncionario expectedDependentesFuncionario) {
        assertDependentesFuncionarioAllUpdatablePropertiesEquals(
            expectedDependentesFuncionario,
            getPersistedDependentesFuncionario(expectedDependentesFuncionario)
        );
    }
}
