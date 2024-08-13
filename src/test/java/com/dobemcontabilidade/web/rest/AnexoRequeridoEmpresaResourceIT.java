package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.AnexoRequeridoEmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AnexoRequerido;
import com.dobemcontabilidade.domain.AnexoRequeridoEmpresa;
import com.dobemcontabilidade.domain.EmpresaModelo;
import com.dobemcontabilidade.domain.Enquadramento;
import com.dobemcontabilidade.domain.Ramo;
import com.dobemcontabilidade.domain.Tributacao;
import com.dobemcontabilidade.repository.AnexoRequeridoEmpresaRepository;
import com.dobemcontabilidade.service.AnexoRequeridoEmpresaService;
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
 * Integration tests for the {@link AnexoRequeridoEmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AnexoRequeridoEmpresaResourceIT {

    private static final Boolean DEFAULT_OBRIGATORIO = false;
    private static final Boolean UPDATED_OBRIGATORIO = true;

    private static final String DEFAULT_URL_ARQUIVO = "AAAAAAAAAA";
    private static final String UPDATED_URL_ARQUIVO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/anexo-requerido-empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AnexoRequeridoEmpresaRepository anexoRequeridoEmpresaRepository;

    @Mock
    private AnexoRequeridoEmpresaRepository anexoRequeridoEmpresaRepositoryMock;

    @Mock
    private AnexoRequeridoEmpresaService anexoRequeridoEmpresaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnexoRequeridoEmpresaMockMvc;

    private AnexoRequeridoEmpresa anexoRequeridoEmpresa;

    private AnexoRequeridoEmpresa insertedAnexoRequeridoEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnexoRequeridoEmpresa createEntity(EntityManager em) {
        AnexoRequeridoEmpresa anexoRequeridoEmpresa = new AnexoRequeridoEmpresa()
            .obrigatorio(DEFAULT_OBRIGATORIO)
            .urlArquivo(DEFAULT_URL_ARQUIVO);
        // Add required entity
        AnexoRequerido anexoRequerido;
        if (TestUtil.findAll(em, AnexoRequerido.class).isEmpty()) {
            anexoRequerido = AnexoRequeridoResourceIT.createEntity(em);
            em.persist(anexoRequerido);
            em.flush();
        } else {
            anexoRequerido = TestUtil.findAll(em, AnexoRequerido.class).get(0);
        }
        anexoRequeridoEmpresa.setAnexoRequerido(anexoRequerido);
        // Add required entity
        Enquadramento enquadramento;
        if (TestUtil.findAll(em, Enquadramento.class).isEmpty()) {
            enquadramento = EnquadramentoResourceIT.createEntity(em);
            em.persist(enquadramento);
            em.flush();
        } else {
            enquadramento = TestUtil.findAll(em, Enquadramento.class).get(0);
        }
        anexoRequeridoEmpresa.setEnquadramento(enquadramento);
        // Add required entity
        Tributacao tributacao;
        if (TestUtil.findAll(em, Tributacao.class).isEmpty()) {
            tributacao = TributacaoResourceIT.createEntity(em);
            em.persist(tributacao);
            em.flush();
        } else {
            tributacao = TestUtil.findAll(em, Tributacao.class).get(0);
        }
        anexoRequeridoEmpresa.setTributacao(tributacao);
        // Add required entity
        Ramo ramo;
        if (TestUtil.findAll(em, Ramo.class).isEmpty()) {
            ramo = RamoResourceIT.createEntity(em);
            em.persist(ramo);
            em.flush();
        } else {
            ramo = TestUtil.findAll(em, Ramo.class).get(0);
        }
        anexoRequeridoEmpresa.setRamo(ramo);
        // Add required entity
        EmpresaModelo empresaModelo;
        if (TestUtil.findAll(em, EmpresaModelo.class).isEmpty()) {
            empresaModelo = EmpresaModeloResourceIT.createEntity(em);
            em.persist(empresaModelo);
            em.flush();
        } else {
            empresaModelo = TestUtil.findAll(em, EmpresaModelo.class).get(0);
        }
        anexoRequeridoEmpresa.setEmpresaModelo(empresaModelo);
        return anexoRequeridoEmpresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnexoRequeridoEmpresa createUpdatedEntity(EntityManager em) {
        AnexoRequeridoEmpresa anexoRequeridoEmpresa = new AnexoRequeridoEmpresa()
            .obrigatorio(UPDATED_OBRIGATORIO)
            .urlArquivo(UPDATED_URL_ARQUIVO);
        // Add required entity
        AnexoRequerido anexoRequerido;
        if (TestUtil.findAll(em, AnexoRequerido.class).isEmpty()) {
            anexoRequerido = AnexoRequeridoResourceIT.createUpdatedEntity(em);
            em.persist(anexoRequerido);
            em.flush();
        } else {
            anexoRequerido = TestUtil.findAll(em, AnexoRequerido.class).get(0);
        }
        anexoRequeridoEmpresa.setAnexoRequerido(anexoRequerido);
        // Add required entity
        Enquadramento enquadramento;
        if (TestUtil.findAll(em, Enquadramento.class).isEmpty()) {
            enquadramento = EnquadramentoResourceIT.createUpdatedEntity(em);
            em.persist(enquadramento);
            em.flush();
        } else {
            enquadramento = TestUtil.findAll(em, Enquadramento.class).get(0);
        }
        anexoRequeridoEmpresa.setEnquadramento(enquadramento);
        // Add required entity
        Tributacao tributacao;
        if (TestUtil.findAll(em, Tributacao.class).isEmpty()) {
            tributacao = TributacaoResourceIT.createUpdatedEntity(em);
            em.persist(tributacao);
            em.flush();
        } else {
            tributacao = TestUtil.findAll(em, Tributacao.class).get(0);
        }
        anexoRequeridoEmpresa.setTributacao(tributacao);
        // Add required entity
        Ramo ramo;
        if (TestUtil.findAll(em, Ramo.class).isEmpty()) {
            ramo = RamoResourceIT.createUpdatedEntity(em);
            em.persist(ramo);
            em.flush();
        } else {
            ramo = TestUtil.findAll(em, Ramo.class).get(0);
        }
        anexoRequeridoEmpresa.setRamo(ramo);
        // Add required entity
        EmpresaModelo empresaModelo;
        if (TestUtil.findAll(em, EmpresaModelo.class).isEmpty()) {
            empresaModelo = EmpresaModeloResourceIT.createUpdatedEntity(em);
            em.persist(empresaModelo);
            em.flush();
        } else {
            empresaModelo = TestUtil.findAll(em, EmpresaModelo.class).get(0);
        }
        anexoRequeridoEmpresa.setEmpresaModelo(empresaModelo);
        return anexoRequeridoEmpresa;
    }

    @BeforeEach
    public void initTest() {
        anexoRequeridoEmpresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAnexoRequeridoEmpresa != null) {
            anexoRequeridoEmpresaRepository.delete(insertedAnexoRequeridoEmpresa);
            insertedAnexoRequeridoEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createAnexoRequeridoEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AnexoRequeridoEmpresa
        var returnedAnexoRequeridoEmpresa = om.readValue(
            restAnexoRequeridoEmpresaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoRequeridoEmpresa)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AnexoRequeridoEmpresa.class
        );

        // Validate the AnexoRequeridoEmpresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAnexoRequeridoEmpresaUpdatableFieldsEquals(
            returnedAnexoRequeridoEmpresa,
            getPersistedAnexoRequeridoEmpresa(returnedAnexoRequeridoEmpresa)
        );

        insertedAnexoRequeridoEmpresa = returnedAnexoRequeridoEmpresa;
    }

    @Test
    @Transactional
    void createAnexoRequeridoEmpresaWithExistingId() throws Exception {
        // Create the AnexoRequeridoEmpresa with an existing ID
        anexoRequeridoEmpresa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnexoRequeridoEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoRequeridoEmpresa)))
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequeridoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAnexoRequeridoEmpresas() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoEmpresa = anexoRequeridoEmpresaRepository.saveAndFlush(anexoRequeridoEmpresa);

        // Get all the anexoRequeridoEmpresaList
        restAnexoRequeridoEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anexoRequeridoEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].obrigatorio").value(hasItem(DEFAULT_OBRIGATORIO.booleanValue())))
            .andExpect(jsonPath("$.[*].urlArquivo").value(hasItem(DEFAULT_URL_ARQUIVO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAnexoRequeridoEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(anexoRequeridoEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAnexoRequeridoEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(anexoRequeridoEmpresaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAnexoRequeridoEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(anexoRequeridoEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAnexoRequeridoEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(anexoRequeridoEmpresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAnexoRequeridoEmpresa() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoEmpresa = anexoRequeridoEmpresaRepository.saveAndFlush(anexoRequeridoEmpresa);

        // Get the anexoRequeridoEmpresa
        restAnexoRequeridoEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, anexoRequeridoEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(anexoRequeridoEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.obrigatorio").value(DEFAULT_OBRIGATORIO.booleanValue()))
            .andExpect(jsonPath("$.urlArquivo").value(DEFAULT_URL_ARQUIVO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAnexoRequeridoEmpresa() throws Exception {
        // Get the anexoRequeridoEmpresa
        restAnexoRequeridoEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAnexoRequeridoEmpresa() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoEmpresa = anexoRequeridoEmpresaRepository.saveAndFlush(anexoRequeridoEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoRequeridoEmpresa
        AnexoRequeridoEmpresa updatedAnexoRequeridoEmpresa = anexoRequeridoEmpresaRepository
            .findById(anexoRequeridoEmpresa.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAnexoRequeridoEmpresa are not directly saved in db
        em.detach(updatedAnexoRequeridoEmpresa);
        updatedAnexoRequeridoEmpresa.obrigatorio(UPDATED_OBRIGATORIO).urlArquivo(UPDATED_URL_ARQUIVO);

        restAnexoRequeridoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAnexoRequeridoEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAnexoRequeridoEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the AnexoRequeridoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAnexoRequeridoEmpresaToMatchAllProperties(updatedAnexoRequeridoEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingAnexoRequeridoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoRequeridoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anexoRequeridoEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anexoRequeridoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequeridoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnexoRequeridoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoRequeridoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anexoRequeridoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequeridoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnexoRequeridoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoRequeridoEmpresaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoRequeridoEmpresa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnexoRequeridoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnexoRequeridoEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoEmpresa = anexoRequeridoEmpresaRepository.saveAndFlush(anexoRequeridoEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoRequeridoEmpresa using partial update
        AnexoRequeridoEmpresa partialUpdatedAnexoRequeridoEmpresa = new AnexoRequeridoEmpresa();
        partialUpdatedAnexoRequeridoEmpresa.setId(anexoRequeridoEmpresa.getId());

        partialUpdatedAnexoRequeridoEmpresa.obrigatorio(UPDATED_OBRIGATORIO).urlArquivo(UPDATED_URL_ARQUIVO);

        restAnexoRequeridoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnexoRequeridoEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnexoRequeridoEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the AnexoRequeridoEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnexoRequeridoEmpresaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAnexoRequeridoEmpresa, anexoRequeridoEmpresa),
            getPersistedAnexoRequeridoEmpresa(anexoRequeridoEmpresa)
        );
    }

    @Test
    @Transactional
    void fullUpdateAnexoRequeridoEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoEmpresa = anexoRequeridoEmpresaRepository.saveAndFlush(anexoRequeridoEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoRequeridoEmpresa using partial update
        AnexoRequeridoEmpresa partialUpdatedAnexoRequeridoEmpresa = new AnexoRequeridoEmpresa();
        partialUpdatedAnexoRequeridoEmpresa.setId(anexoRequeridoEmpresa.getId());

        partialUpdatedAnexoRequeridoEmpresa.obrigatorio(UPDATED_OBRIGATORIO).urlArquivo(UPDATED_URL_ARQUIVO);

        restAnexoRequeridoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnexoRequeridoEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnexoRequeridoEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the AnexoRequeridoEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnexoRequeridoEmpresaUpdatableFieldsEquals(
            partialUpdatedAnexoRequeridoEmpresa,
            getPersistedAnexoRequeridoEmpresa(partialUpdatedAnexoRequeridoEmpresa)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAnexoRequeridoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoRequeridoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, anexoRequeridoEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anexoRequeridoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequeridoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnexoRequeridoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoRequeridoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anexoRequeridoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequeridoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnexoRequeridoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoRequeridoEmpresaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(anexoRequeridoEmpresa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnexoRequeridoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnexoRequeridoEmpresa() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoEmpresa = anexoRequeridoEmpresaRepository.saveAndFlush(anexoRequeridoEmpresa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the anexoRequeridoEmpresa
        restAnexoRequeridoEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, anexoRequeridoEmpresa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return anexoRequeridoEmpresaRepository.count();
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

    protected AnexoRequeridoEmpresa getPersistedAnexoRequeridoEmpresa(AnexoRequeridoEmpresa anexoRequeridoEmpresa) {
        return anexoRequeridoEmpresaRepository.findById(anexoRequeridoEmpresa.getId()).orElseThrow();
    }

    protected void assertPersistedAnexoRequeridoEmpresaToMatchAllProperties(AnexoRequeridoEmpresa expectedAnexoRequeridoEmpresa) {
        assertAnexoRequeridoEmpresaAllPropertiesEquals(
            expectedAnexoRequeridoEmpresa,
            getPersistedAnexoRequeridoEmpresa(expectedAnexoRequeridoEmpresa)
        );
    }

    protected void assertPersistedAnexoRequeridoEmpresaToMatchUpdatableProperties(AnexoRequeridoEmpresa expectedAnexoRequeridoEmpresa) {
        assertAnexoRequeridoEmpresaAllUpdatablePropertiesEquals(
            expectedAnexoRequeridoEmpresa,
            getPersistedAnexoRequeridoEmpresa(expectedAnexoRequeridoEmpresa)
        );
    }
}
