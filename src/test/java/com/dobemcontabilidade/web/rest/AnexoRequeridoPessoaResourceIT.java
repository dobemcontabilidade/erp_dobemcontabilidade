package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.AnexoRequeridoPessoaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AnexoPessoa;
import com.dobemcontabilidade.domain.AnexoRequerido;
import com.dobemcontabilidade.domain.AnexoRequeridoPessoa;
import com.dobemcontabilidade.domain.enumeration.TipoAnexoPessoaEnum;
import com.dobemcontabilidade.repository.AnexoRequeridoPessoaRepository;
import com.dobemcontabilidade.service.AnexoRequeridoPessoaService;
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
 * Integration tests for the {@link AnexoRequeridoPessoaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AnexoRequeridoPessoaResourceIT {

    private static final Boolean DEFAULT_OBRIGATORIO = false;
    private static final Boolean UPDATED_OBRIGATORIO = true;

    private static final TipoAnexoPessoaEnum DEFAULT_TIPO = TipoAnexoPessoaEnum.FUNCIONARIO;
    private static final TipoAnexoPessoaEnum UPDATED_TIPO = TipoAnexoPessoaEnum.DEPENDENTE;

    private static final String ENTITY_API_URL = "/api/anexo-requerido-pessoas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AnexoRequeridoPessoaRepository anexoRequeridoPessoaRepository;

    @Mock
    private AnexoRequeridoPessoaRepository anexoRequeridoPessoaRepositoryMock;

    @Mock
    private AnexoRequeridoPessoaService anexoRequeridoPessoaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnexoRequeridoPessoaMockMvc;

    private AnexoRequeridoPessoa anexoRequeridoPessoa;

    private AnexoRequeridoPessoa insertedAnexoRequeridoPessoa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnexoRequeridoPessoa createEntity(EntityManager em) {
        AnexoRequeridoPessoa anexoRequeridoPessoa = new AnexoRequeridoPessoa().obrigatorio(DEFAULT_OBRIGATORIO).tipo(DEFAULT_TIPO);
        // Add required entity
        AnexoPessoa anexoPessoa;
        if (TestUtil.findAll(em, AnexoPessoa.class).isEmpty()) {
            anexoPessoa = AnexoPessoaResourceIT.createEntity(em);
            em.persist(anexoPessoa);
            em.flush();
        } else {
            anexoPessoa = TestUtil.findAll(em, AnexoPessoa.class).get(0);
        }
        anexoRequeridoPessoa.setAnexoPessoa(anexoPessoa);
        // Add required entity
        AnexoRequerido anexoRequerido;
        if (TestUtil.findAll(em, AnexoRequerido.class).isEmpty()) {
            anexoRequerido = AnexoRequeridoResourceIT.createEntity(em);
            em.persist(anexoRequerido);
            em.flush();
        } else {
            anexoRequerido = TestUtil.findAll(em, AnexoRequerido.class).get(0);
        }
        anexoRequeridoPessoa.setAnexoRequerido(anexoRequerido);
        return anexoRequeridoPessoa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnexoRequeridoPessoa createUpdatedEntity(EntityManager em) {
        AnexoRequeridoPessoa anexoRequeridoPessoa = new AnexoRequeridoPessoa().obrigatorio(UPDATED_OBRIGATORIO).tipo(UPDATED_TIPO);
        // Add required entity
        AnexoPessoa anexoPessoa;
        if (TestUtil.findAll(em, AnexoPessoa.class).isEmpty()) {
            anexoPessoa = AnexoPessoaResourceIT.createUpdatedEntity(em);
            em.persist(anexoPessoa);
            em.flush();
        } else {
            anexoPessoa = TestUtil.findAll(em, AnexoPessoa.class).get(0);
        }
        anexoRequeridoPessoa.setAnexoPessoa(anexoPessoa);
        // Add required entity
        AnexoRequerido anexoRequerido;
        if (TestUtil.findAll(em, AnexoRequerido.class).isEmpty()) {
            anexoRequerido = AnexoRequeridoResourceIT.createUpdatedEntity(em);
            em.persist(anexoRequerido);
            em.flush();
        } else {
            anexoRequerido = TestUtil.findAll(em, AnexoRequerido.class).get(0);
        }
        anexoRequeridoPessoa.setAnexoRequerido(anexoRequerido);
        return anexoRequeridoPessoa;
    }

    @BeforeEach
    public void initTest() {
        anexoRequeridoPessoa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAnexoRequeridoPessoa != null) {
            anexoRequeridoPessoaRepository.delete(insertedAnexoRequeridoPessoa);
            insertedAnexoRequeridoPessoa = null;
        }
    }

    @Test
    @Transactional
    void createAnexoRequeridoPessoa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AnexoRequeridoPessoa
        var returnedAnexoRequeridoPessoa = om.readValue(
            restAnexoRequeridoPessoaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoRequeridoPessoa)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AnexoRequeridoPessoa.class
        );

        // Validate the AnexoRequeridoPessoa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAnexoRequeridoPessoaUpdatableFieldsEquals(
            returnedAnexoRequeridoPessoa,
            getPersistedAnexoRequeridoPessoa(returnedAnexoRequeridoPessoa)
        );

        insertedAnexoRequeridoPessoa = returnedAnexoRequeridoPessoa;
    }

    @Test
    @Transactional
    void createAnexoRequeridoPessoaWithExistingId() throws Exception {
        // Create the AnexoRequeridoPessoa with an existing ID
        anexoRequeridoPessoa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnexoRequeridoPessoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoRequeridoPessoa)))
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequeridoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAnexoRequeridoPessoas() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoPessoa = anexoRequeridoPessoaRepository.saveAndFlush(anexoRequeridoPessoa);

        // Get all the anexoRequeridoPessoaList
        restAnexoRequeridoPessoaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anexoRequeridoPessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].obrigatorio").value(hasItem(DEFAULT_OBRIGATORIO.booleanValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAnexoRequeridoPessoasWithEagerRelationshipsIsEnabled() throws Exception {
        when(anexoRequeridoPessoaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAnexoRequeridoPessoaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(anexoRequeridoPessoaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAnexoRequeridoPessoasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(anexoRequeridoPessoaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAnexoRequeridoPessoaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(anexoRequeridoPessoaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAnexoRequeridoPessoa() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoPessoa = anexoRequeridoPessoaRepository.saveAndFlush(anexoRequeridoPessoa);

        // Get the anexoRequeridoPessoa
        restAnexoRequeridoPessoaMockMvc
            .perform(get(ENTITY_API_URL_ID, anexoRequeridoPessoa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(anexoRequeridoPessoa.getId().intValue()))
            .andExpect(jsonPath("$.obrigatorio").value(DEFAULT_OBRIGATORIO.booleanValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAnexoRequeridoPessoa() throws Exception {
        // Get the anexoRequeridoPessoa
        restAnexoRequeridoPessoaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAnexoRequeridoPessoa() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoPessoa = anexoRequeridoPessoaRepository.saveAndFlush(anexoRequeridoPessoa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoRequeridoPessoa
        AnexoRequeridoPessoa updatedAnexoRequeridoPessoa = anexoRequeridoPessoaRepository
            .findById(anexoRequeridoPessoa.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAnexoRequeridoPessoa are not directly saved in db
        em.detach(updatedAnexoRequeridoPessoa);
        updatedAnexoRequeridoPessoa.obrigatorio(UPDATED_OBRIGATORIO).tipo(UPDATED_TIPO);

        restAnexoRequeridoPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAnexoRequeridoPessoa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAnexoRequeridoPessoa))
            )
            .andExpect(status().isOk());

        // Validate the AnexoRequeridoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAnexoRequeridoPessoaToMatchAllProperties(updatedAnexoRequeridoPessoa);
    }

    @Test
    @Transactional
    void putNonExistingAnexoRequeridoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoPessoa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoRequeridoPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anexoRequeridoPessoa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anexoRequeridoPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequeridoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnexoRequeridoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoPessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoRequeridoPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anexoRequeridoPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequeridoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnexoRequeridoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoPessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoRequeridoPessoaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoRequeridoPessoa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnexoRequeridoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnexoRequeridoPessoaWithPatch() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoPessoa = anexoRequeridoPessoaRepository.saveAndFlush(anexoRequeridoPessoa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoRequeridoPessoa using partial update
        AnexoRequeridoPessoa partialUpdatedAnexoRequeridoPessoa = new AnexoRequeridoPessoa();
        partialUpdatedAnexoRequeridoPessoa.setId(anexoRequeridoPessoa.getId());

        restAnexoRequeridoPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnexoRequeridoPessoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnexoRequeridoPessoa))
            )
            .andExpect(status().isOk());

        // Validate the AnexoRequeridoPessoa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnexoRequeridoPessoaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAnexoRequeridoPessoa, anexoRequeridoPessoa),
            getPersistedAnexoRequeridoPessoa(anexoRequeridoPessoa)
        );
    }

    @Test
    @Transactional
    void fullUpdateAnexoRequeridoPessoaWithPatch() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoPessoa = anexoRequeridoPessoaRepository.saveAndFlush(anexoRequeridoPessoa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoRequeridoPessoa using partial update
        AnexoRequeridoPessoa partialUpdatedAnexoRequeridoPessoa = new AnexoRequeridoPessoa();
        partialUpdatedAnexoRequeridoPessoa.setId(anexoRequeridoPessoa.getId());

        partialUpdatedAnexoRequeridoPessoa.obrigatorio(UPDATED_OBRIGATORIO).tipo(UPDATED_TIPO);

        restAnexoRequeridoPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnexoRequeridoPessoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnexoRequeridoPessoa))
            )
            .andExpect(status().isOk());

        // Validate the AnexoRequeridoPessoa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnexoRequeridoPessoaUpdatableFieldsEquals(
            partialUpdatedAnexoRequeridoPessoa,
            getPersistedAnexoRequeridoPessoa(partialUpdatedAnexoRequeridoPessoa)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAnexoRequeridoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoPessoa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoRequeridoPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, anexoRequeridoPessoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anexoRequeridoPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequeridoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnexoRequeridoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoPessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoRequeridoPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anexoRequeridoPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequeridoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnexoRequeridoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoPessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoRequeridoPessoaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(anexoRequeridoPessoa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnexoRequeridoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnexoRequeridoPessoa() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoPessoa = anexoRequeridoPessoaRepository.saveAndFlush(anexoRequeridoPessoa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the anexoRequeridoPessoa
        restAnexoRequeridoPessoaMockMvc
            .perform(delete(ENTITY_API_URL_ID, anexoRequeridoPessoa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return anexoRequeridoPessoaRepository.count();
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

    protected AnexoRequeridoPessoa getPersistedAnexoRequeridoPessoa(AnexoRequeridoPessoa anexoRequeridoPessoa) {
        return anexoRequeridoPessoaRepository.findById(anexoRequeridoPessoa.getId()).orElseThrow();
    }

    protected void assertPersistedAnexoRequeridoPessoaToMatchAllProperties(AnexoRequeridoPessoa expectedAnexoRequeridoPessoa) {
        assertAnexoRequeridoPessoaAllPropertiesEquals(
            expectedAnexoRequeridoPessoa,
            getPersistedAnexoRequeridoPessoa(expectedAnexoRequeridoPessoa)
        );
    }

    protected void assertPersistedAnexoRequeridoPessoaToMatchUpdatableProperties(AnexoRequeridoPessoa expectedAnexoRequeridoPessoa) {
        assertAnexoRequeridoPessoaAllUpdatablePropertiesEquals(
            expectedAnexoRequeridoPessoa,
            getPersistedAnexoRequeridoPessoa(expectedAnexoRequeridoPessoa)
        );
    }
}
