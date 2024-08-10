package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.AreaContabilEmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AreaContabilEmpresa;
import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.repository.AreaContabilEmpresaRepository;
import com.dobemcontabilidade.service.AreaContabilEmpresaService;
import com.dobemcontabilidade.service.dto.AreaContabilEmpresaDTO;
import com.dobemcontabilidade.service.mapper.AreaContabilEmpresaMapper;
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
 * Integration tests for the {@link AreaContabilEmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AreaContabilEmpresaResourceIT {

    private static final Double DEFAULT_PONTUACAO = 1D;
    private static final Double UPDATED_PONTUACAO = 2D;

    private static final String DEFAULT_DEPOIMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DEPOIMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_RECLAMACAO = "AAAAAAAAAA";
    private static final String UPDATED_RECLAMACAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/area-contabil-empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AreaContabilEmpresaRepository areaContabilEmpresaRepository;

    @Mock
    private AreaContabilEmpresaRepository areaContabilEmpresaRepositoryMock;

    @Autowired
    private AreaContabilEmpresaMapper areaContabilEmpresaMapper;

    @Mock
    private AreaContabilEmpresaService areaContabilEmpresaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAreaContabilEmpresaMockMvc;

    private AreaContabilEmpresa areaContabilEmpresa;

    private AreaContabilEmpresa insertedAreaContabilEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AreaContabilEmpresa createEntity(EntityManager em) {
        AreaContabilEmpresa areaContabilEmpresa = new AreaContabilEmpresa()
            .pontuacao(DEFAULT_PONTUACAO)
            .depoimento(DEFAULT_DEPOIMENTO)
            .reclamacao(DEFAULT_RECLAMACAO);
        // Add required entity
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            contador = ContadorResourceIT.createEntity(em);
            em.persist(contador);
            em.flush();
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        areaContabilEmpresa.setContador(contador);
        return areaContabilEmpresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AreaContabilEmpresa createUpdatedEntity(EntityManager em) {
        AreaContabilEmpresa areaContabilEmpresa = new AreaContabilEmpresa()
            .pontuacao(UPDATED_PONTUACAO)
            .depoimento(UPDATED_DEPOIMENTO)
            .reclamacao(UPDATED_RECLAMACAO);
        // Add required entity
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            contador = ContadorResourceIT.createUpdatedEntity(em);
            em.persist(contador);
            em.flush();
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        areaContabilEmpresa.setContador(contador);
        return areaContabilEmpresa;
    }

    @BeforeEach
    public void initTest() {
        areaContabilEmpresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAreaContabilEmpresa != null) {
            areaContabilEmpresaRepository.delete(insertedAreaContabilEmpresa);
            insertedAreaContabilEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createAreaContabilEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AreaContabilEmpresa
        AreaContabilEmpresaDTO areaContabilEmpresaDTO = areaContabilEmpresaMapper.toDto(areaContabilEmpresa);
        var returnedAreaContabilEmpresaDTO = om.readValue(
            restAreaContabilEmpresaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(areaContabilEmpresaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AreaContabilEmpresaDTO.class
        );

        // Validate the AreaContabilEmpresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAreaContabilEmpresa = areaContabilEmpresaMapper.toEntity(returnedAreaContabilEmpresaDTO);
        assertAreaContabilEmpresaUpdatableFieldsEquals(
            returnedAreaContabilEmpresa,
            getPersistedAreaContabilEmpresa(returnedAreaContabilEmpresa)
        );

        insertedAreaContabilEmpresa = returnedAreaContabilEmpresa;
    }

    @Test
    @Transactional
    void createAreaContabilEmpresaWithExistingId() throws Exception {
        // Create the AreaContabilEmpresa with an existing ID
        areaContabilEmpresa.setId(1L);
        AreaContabilEmpresaDTO areaContabilEmpresaDTO = areaContabilEmpresaMapper.toDto(areaContabilEmpresa);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAreaContabilEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(areaContabilEmpresaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AreaContabilEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAreaContabilEmpresas() throws Exception {
        // Initialize the database
        insertedAreaContabilEmpresa = areaContabilEmpresaRepository.saveAndFlush(areaContabilEmpresa);

        // Get all the areaContabilEmpresaList
        restAreaContabilEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(areaContabilEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].pontuacao").value(hasItem(DEFAULT_PONTUACAO.doubleValue())))
            .andExpect(jsonPath("$.[*].depoimento").value(hasItem(DEFAULT_DEPOIMENTO)))
            .andExpect(jsonPath("$.[*].reclamacao").value(hasItem(DEFAULT_RECLAMACAO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAreaContabilEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(areaContabilEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAreaContabilEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(areaContabilEmpresaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAreaContabilEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(areaContabilEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAreaContabilEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(areaContabilEmpresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAreaContabilEmpresa() throws Exception {
        // Initialize the database
        insertedAreaContabilEmpresa = areaContabilEmpresaRepository.saveAndFlush(areaContabilEmpresa);

        // Get the areaContabilEmpresa
        restAreaContabilEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, areaContabilEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(areaContabilEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.pontuacao").value(DEFAULT_PONTUACAO.doubleValue()))
            .andExpect(jsonPath("$.depoimento").value(DEFAULT_DEPOIMENTO))
            .andExpect(jsonPath("$.reclamacao").value(DEFAULT_RECLAMACAO));
    }

    @Test
    @Transactional
    void getNonExistingAreaContabilEmpresa() throws Exception {
        // Get the areaContabilEmpresa
        restAreaContabilEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAreaContabilEmpresa() throws Exception {
        // Initialize the database
        insertedAreaContabilEmpresa = areaContabilEmpresaRepository.saveAndFlush(areaContabilEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the areaContabilEmpresa
        AreaContabilEmpresa updatedAreaContabilEmpresa = areaContabilEmpresaRepository.findById(areaContabilEmpresa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAreaContabilEmpresa are not directly saved in db
        em.detach(updatedAreaContabilEmpresa);
        updatedAreaContabilEmpresa.pontuacao(UPDATED_PONTUACAO).depoimento(UPDATED_DEPOIMENTO).reclamacao(UPDATED_RECLAMACAO);
        AreaContabilEmpresaDTO areaContabilEmpresaDTO = areaContabilEmpresaMapper.toDto(updatedAreaContabilEmpresa);

        restAreaContabilEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, areaContabilEmpresaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(areaContabilEmpresaDTO))
            )
            .andExpect(status().isOk());

        // Validate the AreaContabilEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAreaContabilEmpresaToMatchAllProperties(updatedAreaContabilEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingAreaContabilEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaContabilEmpresa.setId(longCount.incrementAndGet());

        // Create the AreaContabilEmpresa
        AreaContabilEmpresaDTO areaContabilEmpresaDTO = areaContabilEmpresaMapper.toDto(areaContabilEmpresa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAreaContabilEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, areaContabilEmpresaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(areaContabilEmpresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaContabilEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAreaContabilEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaContabilEmpresa.setId(longCount.incrementAndGet());

        // Create the AreaContabilEmpresa
        AreaContabilEmpresaDTO areaContabilEmpresaDTO = areaContabilEmpresaMapper.toDto(areaContabilEmpresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaContabilEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(areaContabilEmpresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaContabilEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAreaContabilEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaContabilEmpresa.setId(longCount.incrementAndGet());

        // Create the AreaContabilEmpresa
        AreaContabilEmpresaDTO areaContabilEmpresaDTO = areaContabilEmpresaMapper.toDto(areaContabilEmpresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaContabilEmpresaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(areaContabilEmpresaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AreaContabilEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAreaContabilEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedAreaContabilEmpresa = areaContabilEmpresaRepository.saveAndFlush(areaContabilEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the areaContabilEmpresa using partial update
        AreaContabilEmpresa partialUpdatedAreaContabilEmpresa = new AreaContabilEmpresa();
        partialUpdatedAreaContabilEmpresa.setId(areaContabilEmpresa.getId());

        partialUpdatedAreaContabilEmpresa.reclamacao(UPDATED_RECLAMACAO);

        restAreaContabilEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAreaContabilEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAreaContabilEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the AreaContabilEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAreaContabilEmpresaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAreaContabilEmpresa, areaContabilEmpresa),
            getPersistedAreaContabilEmpresa(areaContabilEmpresa)
        );
    }

    @Test
    @Transactional
    void fullUpdateAreaContabilEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedAreaContabilEmpresa = areaContabilEmpresaRepository.saveAndFlush(areaContabilEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the areaContabilEmpresa using partial update
        AreaContabilEmpresa partialUpdatedAreaContabilEmpresa = new AreaContabilEmpresa();
        partialUpdatedAreaContabilEmpresa.setId(areaContabilEmpresa.getId());

        partialUpdatedAreaContabilEmpresa.pontuacao(UPDATED_PONTUACAO).depoimento(UPDATED_DEPOIMENTO).reclamacao(UPDATED_RECLAMACAO);

        restAreaContabilEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAreaContabilEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAreaContabilEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the AreaContabilEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAreaContabilEmpresaUpdatableFieldsEquals(
            partialUpdatedAreaContabilEmpresa,
            getPersistedAreaContabilEmpresa(partialUpdatedAreaContabilEmpresa)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAreaContabilEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaContabilEmpresa.setId(longCount.incrementAndGet());

        // Create the AreaContabilEmpresa
        AreaContabilEmpresaDTO areaContabilEmpresaDTO = areaContabilEmpresaMapper.toDto(areaContabilEmpresa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAreaContabilEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, areaContabilEmpresaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(areaContabilEmpresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaContabilEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAreaContabilEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaContabilEmpresa.setId(longCount.incrementAndGet());

        // Create the AreaContabilEmpresa
        AreaContabilEmpresaDTO areaContabilEmpresaDTO = areaContabilEmpresaMapper.toDto(areaContabilEmpresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaContabilEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(areaContabilEmpresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaContabilEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAreaContabilEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaContabilEmpresa.setId(longCount.incrementAndGet());

        // Create the AreaContabilEmpresa
        AreaContabilEmpresaDTO areaContabilEmpresaDTO = areaContabilEmpresaMapper.toDto(areaContabilEmpresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaContabilEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(areaContabilEmpresaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AreaContabilEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAreaContabilEmpresa() throws Exception {
        // Initialize the database
        insertedAreaContabilEmpresa = areaContabilEmpresaRepository.saveAndFlush(areaContabilEmpresa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the areaContabilEmpresa
        restAreaContabilEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, areaContabilEmpresa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return areaContabilEmpresaRepository.count();
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

    protected AreaContabilEmpresa getPersistedAreaContabilEmpresa(AreaContabilEmpresa areaContabilEmpresa) {
        return areaContabilEmpresaRepository.findById(areaContabilEmpresa.getId()).orElseThrow();
    }

    protected void assertPersistedAreaContabilEmpresaToMatchAllProperties(AreaContabilEmpresa expectedAreaContabilEmpresa) {
        assertAreaContabilEmpresaAllPropertiesEquals(
            expectedAreaContabilEmpresa,
            getPersistedAreaContabilEmpresa(expectedAreaContabilEmpresa)
        );
    }

    protected void assertPersistedAreaContabilEmpresaToMatchUpdatableProperties(AreaContabilEmpresa expectedAreaContabilEmpresa) {
        assertAreaContabilEmpresaAllUpdatablePropertiesEquals(
            expectedAreaContabilEmpresa,
            getPersistedAreaContabilEmpresa(expectedAreaContabilEmpresa)
        );
    }
}
