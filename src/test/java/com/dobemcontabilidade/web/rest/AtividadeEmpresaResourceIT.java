package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.AtividadeEmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AtividadeEmpresa;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.repository.AtividadeEmpresaRepository;
import com.dobemcontabilidade.service.AtividadeEmpresaService;
import com.dobemcontabilidade.service.dto.AtividadeEmpresaDTO;
import com.dobemcontabilidade.service.mapper.AtividadeEmpresaMapper;
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
 * Integration tests for the {@link AtividadeEmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AtividadeEmpresaResourceIT {

    private static final Boolean DEFAULT_PRINCIPAL = false;
    private static final Boolean UPDATED_PRINCIPAL = true;

    private static final Integer DEFAULT_ORDEM = 1;
    private static final Integer UPDATED_ORDEM = 2;

    private static final String DEFAULT_DESCRICAO_ATIVIDADE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO_ATIVIDADE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/atividade-empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AtividadeEmpresaRepository atividadeEmpresaRepository;

    @Mock
    private AtividadeEmpresaRepository atividadeEmpresaRepositoryMock;

    @Autowired
    private AtividadeEmpresaMapper atividadeEmpresaMapper;

    @Mock
    private AtividadeEmpresaService atividadeEmpresaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAtividadeEmpresaMockMvc;

    private AtividadeEmpresa atividadeEmpresa;

    private AtividadeEmpresa insertedAtividadeEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AtividadeEmpresa createEntity(EntityManager em) {
        AtividadeEmpresa atividadeEmpresa = new AtividadeEmpresa()
            .principal(DEFAULT_PRINCIPAL)
            .ordem(DEFAULT_ORDEM)
            .descricaoAtividade(DEFAULT_DESCRICAO_ATIVIDADE);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        atividadeEmpresa.setEmpresa(empresa);
        return atividadeEmpresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AtividadeEmpresa createUpdatedEntity(EntityManager em) {
        AtividadeEmpresa atividadeEmpresa = new AtividadeEmpresa()
            .principal(UPDATED_PRINCIPAL)
            .ordem(UPDATED_ORDEM)
            .descricaoAtividade(UPDATED_DESCRICAO_ATIVIDADE);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createUpdatedEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        atividadeEmpresa.setEmpresa(empresa);
        return atividadeEmpresa;
    }

    @BeforeEach
    public void initTest() {
        atividadeEmpresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAtividadeEmpresa != null) {
            atividadeEmpresaRepository.delete(insertedAtividadeEmpresa);
            insertedAtividadeEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createAtividadeEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AtividadeEmpresa
        AtividadeEmpresaDTO atividadeEmpresaDTO = atividadeEmpresaMapper.toDto(atividadeEmpresa);
        var returnedAtividadeEmpresaDTO = om.readValue(
            restAtividadeEmpresaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(atividadeEmpresaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AtividadeEmpresaDTO.class
        );

        // Validate the AtividadeEmpresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAtividadeEmpresa = atividadeEmpresaMapper.toEntity(returnedAtividadeEmpresaDTO);
        assertAtividadeEmpresaUpdatableFieldsEquals(returnedAtividadeEmpresa, getPersistedAtividadeEmpresa(returnedAtividadeEmpresa));

        insertedAtividadeEmpresa = returnedAtividadeEmpresa;
    }

    @Test
    @Transactional
    void createAtividadeEmpresaWithExistingId() throws Exception {
        // Create the AtividadeEmpresa with an existing ID
        atividadeEmpresa.setId(1L);
        AtividadeEmpresaDTO atividadeEmpresaDTO = atividadeEmpresaMapper.toDto(atividadeEmpresa);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAtividadeEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(atividadeEmpresaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AtividadeEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAtividadeEmpresas() throws Exception {
        // Initialize the database
        insertedAtividadeEmpresa = atividadeEmpresaRepository.saveAndFlush(atividadeEmpresa);

        // Get all the atividadeEmpresaList
        restAtividadeEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(atividadeEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].principal").value(hasItem(DEFAULT_PRINCIPAL.booleanValue())))
            .andExpect(jsonPath("$.[*].ordem").value(hasItem(DEFAULT_ORDEM)))
            .andExpect(jsonPath("$.[*].descricaoAtividade").value(hasItem(DEFAULT_DESCRICAO_ATIVIDADE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAtividadeEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(atividadeEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAtividadeEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(atividadeEmpresaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAtividadeEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(atividadeEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAtividadeEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(atividadeEmpresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAtividadeEmpresa() throws Exception {
        // Initialize the database
        insertedAtividadeEmpresa = atividadeEmpresaRepository.saveAndFlush(atividadeEmpresa);

        // Get the atividadeEmpresa
        restAtividadeEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, atividadeEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(atividadeEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.principal").value(DEFAULT_PRINCIPAL.booleanValue()))
            .andExpect(jsonPath("$.ordem").value(DEFAULT_ORDEM))
            .andExpect(jsonPath("$.descricaoAtividade").value(DEFAULT_DESCRICAO_ATIVIDADE));
    }

    @Test
    @Transactional
    void getNonExistingAtividadeEmpresa() throws Exception {
        // Get the atividadeEmpresa
        restAtividadeEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAtividadeEmpresa() throws Exception {
        // Initialize the database
        insertedAtividadeEmpresa = atividadeEmpresaRepository.saveAndFlush(atividadeEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the atividadeEmpresa
        AtividadeEmpresa updatedAtividadeEmpresa = atividadeEmpresaRepository.findById(atividadeEmpresa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAtividadeEmpresa are not directly saved in db
        em.detach(updatedAtividadeEmpresa);
        updatedAtividadeEmpresa.principal(UPDATED_PRINCIPAL).ordem(UPDATED_ORDEM).descricaoAtividade(UPDATED_DESCRICAO_ATIVIDADE);
        AtividadeEmpresaDTO atividadeEmpresaDTO = atividadeEmpresaMapper.toDto(updatedAtividadeEmpresa);

        restAtividadeEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, atividadeEmpresaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(atividadeEmpresaDTO))
            )
            .andExpect(status().isOk());

        // Validate the AtividadeEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAtividadeEmpresaToMatchAllProperties(updatedAtividadeEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingAtividadeEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        atividadeEmpresa.setId(longCount.incrementAndGet());

        // Create the AtividadeEmpresa
        AtividadeEmpresaDTO atividadeEmpresaDTO = atividadeEmpresaMapper.toDto(atividadeEmpresa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAtividadeEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, atividadeEmpresaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(atividadeEmpresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AtividadeEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAtividadeEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        atividadeEmpresa.setId(longCount.incrementAndGet());

        // Create the AtividadeEmpresa
        AtividadeEmpresaDTO atividadeEmpresaDTO = atividadeEmpresaMapper.toDto(atividadeEmpresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAtividadeEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(atividadeEmpresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AtividadeEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAtividadeEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        atividadeEmpresa.setId(longCount.incrementAndGet());

        // Create the AtividadeEmpresa
        AtividadeEmpresaDTO atividadeEmpresaDTO = atividadeEmpresaMapper.toDto(atividadeEmpresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAtividadeEmpresaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(atividadeEmpresaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AtividadeEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAtividadeEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedAtividadeEmpresa = atividadeEmpresaRepository.saveAndFlush(atividadeEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the atividadeEmpresa using partial update
        AtividadeEmpresa partialUpdatedAtividadeEmpresa = new AtividadeEmpresa();
        partialUpdatedAtividadeEmpresa.setId(atividadeEmpresa.getId());

        partialUpdatedAtividadeEmpresa.principal(UPDATED_PRINCIPAL);

        restAtividadeEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAtividadeEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAtividadeEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the AtividadeEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAtividadeEmpresaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAtividadeEmpresa, atividadeEmpresa),
            getPersistedAtividadeEmpresa(atividadeEmpresa)
        );
    }

    @Test
    @Transactional
    void fullUpdateAtividadeEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedAtividadeEmpresa = atividadeEmpresaRepository.saveAndFlush(atividadeEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the atividadeEmpresa using partial update
        AtividadeEmpresa partialUpdatedAtividadeEmpresa = new AtividadeEmpresa();
        partialUpdatedAtividadeEmpresa.setId(atividadeEmpresa.getId());

        partialUpdatedAtividadeEmpresa.principal(UPDATED_PRINCIPAL).ordem(UPDATED_ORDEM).descricaoAtividade(UPDATED_DESCRICAO_ATIVIDADE);

        restAtividadeEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAtividadeEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAtividadeEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the AtividadeEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAtividadeEmpresaUpdatableFieldsEquals(
            partialUpdatedAtividadeEmpresa,
            getPersistedAtividadeEmpresa(partialUpdatedAtividadeEmpresa)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAtividadeEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        atividadeEmpresa.setId(longCount.incrementAndGet());

        // Create the AtividadeEmpresa
        AtividadeEmpresaDTO atividadeEmpresaDTO = atividadeEmpresaMapper.toDto(atividadeEmpresa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAtividadeEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, atividadeEmpresaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(atividadeEmpresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AtividadeEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAtividadeEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        atividadeEmpresa.setId(longCount.incrementAndGet());

        // Create the AtividadeEmpresa
        AtividadeEmpresaDTO atividadeEmpresaDTO = atividadeEmpresaMapper.toDto(atividadeEmpresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAtividadeEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(atividadeEmpresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AtividadeEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAtividadeEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        atividadeEmpresa.setId(longCount.incrementAndGet());

        // Create the AtividadeEmpresa
        AtividadeEmpresaDTO atividadeEmpresaDTO = atividadeEmpresaMapper.toDto(atividadeEmpresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAtividadeEmpresaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(atividadeEmpresaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AtividadeEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAtividadeEmpresa() throws Exception {
        // Initialize the database
        insertedAtividadeEmpresa = atividadeEmpresaRepository.saveAndFlush(atividadeEmpresa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the atividadeEmpresa
        restAtividadeEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, atividadeEmpresa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return atividadeEmpresaRepository.count();
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

    protected AtividadeEmpresa getPersistedAtividadeEmpresa(AtividadeEmpresa atividadeEmpresa) {
        return atividadeEmpresaRepository.findById(atividadeEmpresa.getId()).orElseThrow();
    }

    protected void assertPersistedAtividadeEmpresaToMatchAllProperties(AtividadeEmpresa expectedAtividadeEmpresa) {
        assertAtividadeEmpresaAllPropertiesEquals(expectedAtividadeEmpresa, getPersistedAtividadeEmpresa(expectedAtividadeEmpresa));
    }

    protected void assertPersistedAtividadeEmpresaToMatchUpdatableProperties(AtividadeEmpresa expectedAtividadeEmpresa) {
        assertAtividadeEmpresaAllUpdatablePropertiesEquals(
            expectedAtividadeEmpresa,
            getPersistedAtividadeEmpresa(expectedAtividadeEmpresa)
        );
    }
}
