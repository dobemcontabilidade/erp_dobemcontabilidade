package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.TermoAdesaoEmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.domain.PlanoContabil;
import com.dobemcontabilidade.domain.TermoAdesaoEmpresa;
import com.dobemcontabilidade.repository.TermoAdesaoEmpresaRepository;
import com.dobemcontabilidade.service.TermoAdesaoEmpresaService;
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
 * Integration tests for the {@link TermoAdesaoEmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TermoAdesaoEmpresaResourceIT {

    private static final Instant DEFAULT_DATA_ADESAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ADESAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_CHECKED = false;
    private static final Boolean UPDATED_CHECKED = true;

    private static final String DEFAULT_URL_DOC = "AAAAAAAAAA";
    private static final String UPDATED_URL_DOC = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/termo-adesao-empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TermoAdesaoEmpresaRepository termoAdesaoEmpresaRepository;

    @Mock
    private TermoAdesaoEmpresaRepository termoAdesaoEmpresaRepositoryMock;

    @Mock
    private TermoAdesaoEmpresaService termoAdesaoEmpresaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTermoAdesaoEmpresaMockMvc;

    private TermoAdesaoEmpresa termoAdesaoEmpresa;

    private TermoAdesaoEmpresa insertedTermoAdesaoEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TermoAdesaoEmpresa createEntity(EntityManager em) {
        TermoAdesaoEmpresa termoAdesaoEmpresa = new TermoAdesaoEmpresa()
            .dataAdesao(DEFAULT_DATA_ADESAO)
            .checked(DEFAULT_CHECKED)
            .urlDoc(DEFAULT_URL_DOC);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        termoAdesaoEmpresa.setEmpresa(empresa);
        // Add required entity
        PlanoContabil planoContabil;
        if (TestUtil.findAll(em, PlanoContabil.class).isEmpty()) {
            planoContabil = PlanoContabilResourceIT.createEntity(em);
            em.persist(planoContabil);
            em.flush();
        } else {
            planoContabil = TestUtil.findAll(em, PlanoContabil.class).get(0);
        }
        termoAdesaoEmpresa.setPlanoContabil(planoContabil);
        return termoAdesaoEmpresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TermoAdesaoEmpresa createUpdatedEntity(EntityManager em) {
        TermoAdesaoEmpresa termoAdesaoEmpresa = new TermoAdesaoEmpresa()
            .dataAdesao(UPDATED_DATA_ADESAO)
            .checked(UPDATED_CHECKED)
            .urlDoc(UPDATED_URL_DOC);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createUpdatedEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        termoAdesaoEmpresa.setEmpresa(empresa);
        // Add required entity
        PlanoContabil planoContabil;
        if (TestUtil.findAll(em, PlanoContabil.class).isEmpty()) {
            planoContabil = PlanoContabilResourceIT.createUpdatedEntity(em);
            em.persist(planoContabil);
            em.flush();
        } else {
            planoContabil = TestUtil.findAll(em, PlanoContabil.class).get(0);
        }
        termoAdesaoEmpresa.setPlanoContabil(planoContabil);
        return termoAdesaoEmpresa;
    }

    @BeforeEach
    public void initTest() {
        termoAdesaoEmpresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTermoAdesaoEmpresa != null) {
            termoAdesaoEmpresaRepository.delete(insertedTermoAdesaoEmpresa);
            insertedTermoAdesaoEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createTermoAdesaoEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TermoAdesaoEmpresa
        var returnedTermoAdesaoEmpresa = om.readValue(
            restTermoAdesaoEmpresaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(termoAdesaoEmpresa)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TermoAdesaoEmpresa.class
        );

        // Validate the TermoAdesaoEmpresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTermoAdesaoEmpresaUpdatableFieldsEquals(
            returnedTermoAdesaoEmpresa,
            getPersistedTermoAdesaoEmpresa(returnedTermoAdesaoEmpresa)
        );

        insertedTermoAdesaoEmpresa = returnedTermoAdesaoEmpresa;
    }

    @Test
    @Transactional
    void createTermoAdesaoEmpresaWithExistingId() throws Exception {
        // Create the TermoAdesaoEmpresa with an existing ID
        termoAdesaoEmpresa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTermoAdesaoEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(termoAdesaoEmpresa)))
            .andExpect(status().isBadRequest());

        // Validate the TermoAdesaoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTermoAdesaoEmpresas() throws Exception {
        // Initialize the database
        insertedTermoAdesaoEmpresa = termoAdesaoEmpresaRepository.saveAndFlush(termoAdesaoEmpresa);

        // Get all the termoAdesaoEmpresaList
        restTermoAdesaoEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(termoAdesaoEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataAdesao").value(hasItem(DEFAULT_DATA_ADESAO.toString())))
            .andExpect(jsonPath("$.[*].checked").value(hasItem(DEFAULT_CHECKED.booleanValue())))
            .andExpect(jsonPath("$.[*].urlDoc").value(hasItem(DEFAULT_URL_DOC)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTermoAdesaoEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(termoAdesaoEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTermoAdesaoEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(termoAdesaoEmpresaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTermoAdesaoEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(termoAdesaoEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTermoAdesaoEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(termoAdesaoEmpresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTermoAdesaoEmpresa() throws Exception {
        // Initialize the database
        insertedTermoAdesaoEmpresa = termoAdesaoEmpresaRepository.saveAndFlush(termoAdesaoEmpresa);

        // Get the termoAdesaoEmpresa
        restTermoAdesaoEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, termoAdesaoEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(termoAdesaoEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.dataAdesao").value(DEFAULT_DATA_ADESAO.toString()))
            .andExpect(jsonPath("$.checked").value(DEFAULT_CHECKED.booleanValue()))
            .andExpect(jsonPath("$.urlDoc").value(DEFAULT_URL_DOC));
    }

    @Test
    @Transactional
    void getNonExistingTermoAdesaoEmpresa() throws Exception {
        // Get the termoAdesaoEmpresa
        restTermoAdesaoEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTermoAdesaoEmpresa() throws Exception {
        // Initialize the database
        insertedTermoAdesaoEmpresa = termoAdesaoEmpresaRepository.saveAndFlush(termoAdesaoEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the termoAdesaoEmpresa
        TermoAdesaoEmpresa updatedTermoAdesaoEmpresa = termoAdesaoEmpresaRepository.findById(termoAdesaoEmpresa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTermoAdesaoEmpresa are not directly saved in db
        em.detach(updatedTermoAdesaoEmpresa);
        updatedTermoAdesaoEmpresa.dataAdesao(UPDATED_DATA_ADESAO).checked(UPDATED_CHECKED).urlDoc(UPDATED_URL_DOC);

        restTermoAdesaoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTermoAdesaoEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTermoAdesaoEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the TermoAdesaoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTermoAdesaoEmpresaToMatchAllProperties(updatedTermoAdesaoEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingTermoAdesaoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoAdesaoEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTermoAdesaoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, termoAdesaoEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(termoAdesaoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermoAdesaoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTermoAdesaoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoAdesaoEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermoAdesaoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(termoAdesaoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermoAdesaoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTermoAdesaoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoAdesaoEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermoAdesaoEmpresaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(termoAdesaoEmpresa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TermoAdesaoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTermoAdesaoEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedTermoAdesaoEmpresa = termoAdesaoEmpresaRepository.saveAndFlush(termoAdesaoEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the termoAdesaoEmpresa using partial update
        TermoAdesaoEmpresa partialUpdatedTermoAdesaoEmpresa = new TermoAdesaoEmpresa();
        partialUpdatedTermoAdesaoEmpresa.setId(termoAdesaoEmpresa.getId());

        restTermoAdesaoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTermoAdesaoEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTermoAdesaoEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the TermoAdesaoEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTermoAdesaoEmpresaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTermoAdesaoEmpresa, termoAdesaoEmpresa),
            getPersistedTermoAdesaoEmpresa(termoAdesaoEmpresa)
        );
    }

    @Test
    @Transactional
    void fullUpdateTermoAdesaoEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedTermoAdesaoEmpresa = termoAdesaoEmpresaRepository.saveAndFlush(termoAdesaoEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the termoAdesaoEmpresa using partial update
        TermoAdesaoEmpresa partialUpdatedTermoAdesaoEmpresa = new TermoAdesaoEmpresa();
        partialUpdatedTermoAdesaoEmpresa.setId(termoAdesaoEmpresa.getId());

        partialUpdatedTermoAdesaoEmpresa.dataAdesao(UPDATED_DATA_ADESAO).checked(UPDATED_CHECKED).urlDoc(UPDATED_URL_DOC);

        restTermoAdesaoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTermoAdesaoEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTermoAdesaoEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the TermoAdesaoEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTermoAdesaoEmpresaUpdatableFieldsEquals(
            partialUpdatedTermoAdesaoEmpresa,
            getPersistedTermoAdesaoEmpresa(partialUpdatedTermoAdesaoEmpresa)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTermoAdesaoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoAdesaoEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTermoAdesaoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, termoAdesaoEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(termoAdesaoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermoAdesaoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTermoAdesaoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoAdesaoEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermoAdesaoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(termoAdesaoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermoAdesaoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTermoAdesaoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoAdesaoEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermoAdesaoEmpresaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(termoAdesaoEmpresa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TermoAdesaoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTermoAdesaoEmpresa() throws Exception {
        // Initialize the database
        insertedTermoAdesaoEmpresa = termoAdesaoEmpresaRepository.saveAndFlush(termoAdesaoEmpresa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the termoAdesaoEmpresa
        restTermoAdesaoEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, termoAdesaoEmpresa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return termoAdesaoEmpresaRepository.count();
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

    protected TermoAdesaoEmpresa getPersistedTermoAdesaoEmpresa(TermoAdesaoEmpresa termoAdesaoEmpresa) {
        return termoAdesaoEmpresaRepository.findById(termoAdesaoEmpresa.getId()).orElseThrow();
    }

    protected void assertPersistedTermoAdesaoEmpresaToMatchAllProperties(TermoAdesaoEmpresa expectedTermoAdesaoEmpresa) {
        assertTermoAdesaoEmpresaAllPropertiesEquals(expectedTermoAdesaoEmpresa, getPersistedTermoAdesaoEmpresa(expectedTermoAdesaoEmpresa));
    }

    protected void assertPersistedTermoAdesaoEmpresaToMatchUpdatableProperties(TermoAdesaoEmpresa expectedTermoAdesaoEmpresa) {
        assertTermoAdesaoEmpresaAllUpdatablePropertiesEquals(
            expectedTermoAdesaoEmpresa,
            getPersistedTermoAdesaoEmpresa(expectedTermoAdesaoEmpresa)
        );
    }
}
