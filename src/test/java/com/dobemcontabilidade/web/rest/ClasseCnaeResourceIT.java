package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.ClasseCnaeAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.ClasseCnae;
import com.dobemcontabilidade.domain.GrupoCnae;
import com.dobemcontabilidade.repository.ClasseCnaeRepository;
import com.dobemcontabilidade.service.ClasseCnaeService;
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
 * Integration tests for the {@link ClasseCnaeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ClasseCnaeResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/classe-cnaes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ClasseCnaeRepository classeCnaeRepository;

    @Mock
    private ClasseCnaeRepository classeCnaeRepositoryMock;

    @Mock
    private ClasseCnaeService classeCnaeServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClasseCnaeMockMvc;

    private ClasseCnae classeCnae;

    private ClasseCnae insertedClasseCnae;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClasseCnae createEntity(EntityManager em) {
        ClasseCnae classeCnae = new ClasseCnae().codigo(DEFAULT_CODIGO).descricao(DEFAULT_DESCRICAO);
        // Add required entity
        GrupoCnae grupoCnae;
        if (TestUtil.findAll(em, GrupoCnae.class).isEmpty()) {
            grupoCnae = GrupoCnaeResourceIT.createEntity(em);
            em.persist(grupoCnae);
            em.flush();
        } else {
            grupoCnae = TestUtil.findAll(em, GrupoCnae.class).get(0);
        }
        classeCnae.setGrupo(grupoCnae);
        return classeCnae;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClasseCnae createUpdatedEntity(EntityManager em) {
        ClasseCnae classeCnae = new ClasseCnae().codigo(UPDATED_CODIGO).descricao(UPDATED_DESCRICAO);
        // Add required entity
        GrupoCnae grupoCnae;
        if (TestUtil.findAll(em, GrupoCnae.class).isEmpty()) {
            grupoCnae = GrupoCnaeResourceIT.createUpdatedEntity(em);
            em.persist(grupoCnae);
            em.flush();
        } else {
            grupoCnae = TestUtil.findAll(em, GrupoCnae.class).get(0);
        }
        classeCnae.setGrupo(grupoCnae);
        return classeCnae;
    }

    @BeforeEach
    public void initTest() {
        classeCnae = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedClasseCnae != null) {
            classeCnaeRepository.delete(insertedClasseCnae);
            insertedClasseCnae = null;
        }
    }

    @Test
    @Transactional
    void createClasseCnae() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ClasseCnae
        var returnedClasseCnae = om.readValue(
            restClasseCnaeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classeCnae)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ClasseCnae.class
        );

        // Validate the ClasseCnae in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertClasseCnaeUpdatableFieldsEquals(returnedClasseCnae, getPersistedClasseCnae(returnedClasseCnae));

        insertedClasseCnae = returnedClasseCnae;
    }

    @Test
    @Transactional
    void createClasseCnaeWithExistingId() throws Exception {
        // Create the ClasseCnae with an existing ID
        classeCnae.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClasseCnaeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classeCnae)))
            .andExpect(status().isBadRequest());

        // Validate the ClasseCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClasseCnaes() throws Exception {
        // Initialize the database
        insertedClasseCnae = classeCnaeRepository.saveAndFlush(classeCnae);

        // Get all the classeCnaeList
        restClasseCnaeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classeCnae.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllClasseCnaesWithEagerRelationshipsIsEnabled() throws Exception {
        when(classeCnaeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restClasseCnaeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(classeCnaeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllClasseCnaesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(classeCnaeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restClasseCnaeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(classeCnaeRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getClasseCnae() throws Exception {
        // Initialize the database
        insertedClasseCnae = classeCnaeRepository.saveAndFlush(classeCnae);

        // Get the classeCnae
        restClasseCnaeMockMvc
            .perform(get(ENTITY_API_URL_ID, classeCnae.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classeCnae.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getClasseCnaesByIdFiltering() throws Exception {
        // Initialize the database
        insertedClasseCnae = classeCnaeRepository.saveAndFlush(classeCnae);

        Long id = classeCnae.getId();

        defaultClasseCnaeFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultClasseCnaeFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultClasseCnaeFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClasseCnaesByCodigoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedClasseCnae = classeCnaeRepository.saveAndFlush(classeCnae);

        // Get all the classeCnaeList where codigo equals to
        defaultClasseCnaeFiltering("codigo.equals=" + DEFAULT_CODIGO, "codigo.equals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllClasseCnaesByCodigoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedClasseCnae = classeCnaeRepository.saveAndFlush(classeCnae);

        // Get all the classeCnaeList where codigo in
        defaultClasseCnaeFiltering("codigo.in=" + DEFAULT_CODIGO + "," + UPDATED_CODIGO, "codigo.in=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllClasseCnaesByCodigoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedClasseCnae = classeCnaeRepository.saveAndFlush(classeCnae);

        // Get all the classeCnaeList where codigo is not null
        defaultClasseCnaeFiltering("codigo.specified=true", "codigo.specified=false");
    }

    @Test
    @Transactional
    void getAllClasseCnaesByCodigoContainsSomething() throws Exception {
        // Initialize the database
        insertedClasseCnae = classeCnaeRepository.saveAndFlush(classeCnae);

        // Get all the classeCnaeList where codigo contains
        defaultClasseCnaeFiltering("codigo.contains=" + DEFAULT_CODIGO, "codigo.contains=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllClasseCnaesByCodigoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedClasseCnae = classeCnaeRepository.saveAndFlush(classeCnae);

        // Get all the classeCnaeList where codigo does not contain
        defaultClasseCnaeFiltering("codigo.doesNotContain=" + UPDATED_CODIGO, "codigo.doesNotContain=" + DEFAULT_CODIGO);
    }

    @Test
    @Transactional
    void getAllClasseCnaesByGrupoIsEqualToSomething() throws Exception {
        GrupoCnae grupo;
        if (TestUtil.findAll(em, GrupoCnae.class).isEmpty()) {
            classeCnaeRepository.saveAndFlush(classeCnae);
            grupo = GrupoCnaeResourceIT.createEntity(em);
        } else {
            grupo = TestUtil.findAll(em, GrupoCnae.class).get(0);
        }
        em.persist(grupo);
        em.flush();
        classeCnae.setGrupo(grupo);
        classeCnaeRepository.saveAndFlush(classeCnae);
        Long grupoId = grupo.getId();
        // Get all the classeCnaeList where grupo equals to grupoId
        defaultClasseCnaeShouldBeFound("grupoId.equals=" + grupoId);

        // Get all the classeCnaeList where grupo equals to (grupoId + 1)
        defaultClasseCnaeShouldNotBeFound("grupoId.equals=" + (grupoId + 1));
    }

    private void defaultClasseCnaeFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultClasseCnaeShouldBeFound(shouldBeFound);
        defaultClasseCnaeShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClasseCnaeShouldBeFound(String filter) throws Exception {
        restClasseCnaeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classeCnae.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restClasseCnaeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClasseCnaeShouldNotBeFound(String filter) throws Exception {
        restClasseCnaeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClasseCnaeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingClasseCnae() throws Exception {
        // Get the classeCnae
        restClasseCnaeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClasseCnae() throws Exception {
        // Initialize the database
        insertedClasseCnae = classeCnaeRepository.saveAndFlush(classeCnae);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the classeCnae
        ClasseCnae updatedClasseCnae = classeCnaeRepository.findById(classeCnae.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedClasseCnae are not directly saved in db
        em.detach(updatedClasseCnae);
        updatedClasseCnae.codigo(UPDATED_CODIGO).descricao(UPDATED_DESCRICAO);

        restClasseCnaeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClasseCnae.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedClasseCnae))
            )
            .andExpect(status().isOk());

        // Validate the ClasseCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedClasseCnaeToMatchAllProperties(updatedClasseCnae);
    }

    @Test
    @Transactional
    void putNonExistingClasseCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classeCnae.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClasseCnaeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, classeCnae.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classeCnae))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClasseCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClasseCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classeCnae.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClasseCnaeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(classeCnae))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClasseCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClasseCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classeCnae.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClasseCnaeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classeCnae)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClasseCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClasseCnaeWithPatch() throws Exception {
        // Initialize the database
        insertedClasseCnae = classeCnaeRepository.saveAndFlush(classeCnae);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the classeCnae using partial update
        ClasseCnae partialUpdatedClasseCnae = new ClasseCnae();
        partialUpdatedClasseCnae.setId(classeCnae.getId());

        restClasseCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClasseCnae.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedClasseCnae))
            )
            .andExpect(status().isOk());

        // Validate the ClasseCnae in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertClasseCnaeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedClasseCnae, classeCnae),
            getPersistedClasseCnae(classeCnae)
        );
    }

    @Test
    @Transactional
    void fullUpdateClasseCnaeWithPatch() throws Exception {
        // Initialize the database
        insertedClasseCnae = classeCnaeRepository.saveAndFlush(classeCnae);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the classeCnae using partial update
        ClasseCnae partialUpdatedClasseCnae = new ClasseCnae();
        partialUpdatedClasseCnae.setId(classeCnae.getId());

        partialUpdatedClasseCnae.codigo(UPDATED_CODIGO).descricao(UPDATED_DESCRICAO);

        restClasseCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClasseCnae.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedClasseCnae))
            )
            .andExpect(status().isOk());

        // Validate the ClasseCnae in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertClasseCnaeUpdatableFieldsEquals(partialUpdatedClasseCnae, getPersistedClasseCnae(partialUpdatedClasseCnae));
    }

    @Test
    @Transactional
    void patchNonExistingClasseCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classeCnae.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClasseCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, classeCnae.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(classeCnae))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClasseCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClasseCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classeCnae.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClasseCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(classeCnae))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClasseCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClasseCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classeCnae.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClasseCnaeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(classeCnae)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClasseCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClasseCnae() throws Exception {
        // Initialize the database
        insertedClasseCnae = classeCnaeRepository.saveAndFlush(classeCnae);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the classeCnae
        restClasseCnaeMockMvc
            .perform(delete(ENTITY_API_URL_ID, classeCnae.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return classeCnaeRepository.count();
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

    protected ClasseCnae getPersistedClasseCnae(ClasseCnae classeCnae) {
        return classeCnaeRepository.findById(classeCnae.getId()).orElseThrow();
    }

    protected void assertPersistedClasseCnaeToMatchAllProperties(ClasseCnae expectedClasseCnae) {
        assertClasseCnaeAllPropertiesEquals(expectedClasseCnae, getPersistedClasseCnae(expectedClasseCnae));
    }

    protected void assertPersistedClasseCnaeToMatchUpdatableProperties(ClasseCnae expectedClasseCnae) {
        assertClasseCnaeAllUpdatablePropertiesEquals(expectedClasseCnae, getPersistedClasseCnae(expectedClasseCnae));
    }
}
