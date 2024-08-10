package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.AreaContabilContadorAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AreaContabil;
import com.dobemcontabilidade.domain.AreaContabilContador;
import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.repository.AreaContabilContadorRepository;
import com.dobemcontabilidade.service.AreaContabilContadorService;
import com.dobemcontabilidade.service.dto.AreaContabilContadorDTO;
import com.dobemcontabilidade.service.mapper.AreaContabilContadorMapper;
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
 * Integration tests for the {@link AreaContabilContadorResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AreaContabilContadorResourceIT {

    private static final Double DEFAULT_PERCENTUAL_EXPERIENCIA = 1D;
    private static final Double UPDATED_PERCENTUAL_EXPERIENCIA = 2D;

    private static final String DEFAULT_DESCRICAO_EXPERIENCIA = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO_EXPERIENCIA = "BBBBBBBBBB";

    private static final Double DEFAULT_PONTUACAO_ENTREVISTA = 1D;
    private static final Double UPDATED_PONTUACAO_ENTREVISTA = 2D;

    private static final Double DEFAULT_PONTUACAO_AVALIACAO = 1D;
    private static final Double UPDATED_PONTUACAO_AVALIACAO = 2D;

    private static final String ENTITY_API_URL = "/api/area-contabil-contadors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AreaContabilContadorRepository areaContabilContadorRepository;

    @Mock
    private AreaContabilContadorRepository areaContabilContadorRepositoryMock;

    @Autowired
    private AreaContabilContadorMapper areaContabilContadorMapper;

    @Mock
    private AreaContabilContadorService areaContabilContadorServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAreaContabilContadorMockMvc;

    private AreaContabilContador areaContabilContador;

    private AreaContabilContador insertedAreaContabilContador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AreaContabilContador createEntity(EntityManager em) {
        AreaContabilContador areaContabilContador = new AreaContabilContador()
            .percentualExperiencia(DEFAULT_PERCENTUAL_EXPERIENCIA)
            .descricaoExperiencia(DEFAULT_DESCRICAO_EXPERIENCIA)
            .pontuacaoEntrevista(DEFAULT_PONTUACAO_ENTREVISTA)
            .pontuacaoAvaliacao(DEFAULT_PONTUACAO_AVALIACAO);
        // Add required entity
        AreaContabil areaContabil;
        if (TestUtil.findAll(em, AreaContabil.class).isEmpty()) {
            areaContabil = AreaContabilResourceIT.createEntity(em);
            em.persist(areaContabil);
            em.flush();
        } else {
            areaContabil = TestUtil.findAll(em, AreaContabil.class).get(0);
        }
        areaContabilContador.setAreaContabil(areaContabil);
        // Add required entity
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            contador = ContadorResourceIT.createEntity(em);
            em.persist(contador);
            em.flush();
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        areaContabilContador.setContador(contador);
        return areaContabilContador;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AreaContabilContador createUpdatedEntity(EntityManager em) {
        AreaContabilContador areaContabilContador = new AreaContabilContador()
            .percentualExperiencia(UPDATED_PERCENTUAL_EXPERIENCIA)
            .descricaoExperiencia(UPDATED_DESCRICAO_EXPERIENCIA)
            .pontuacaoEntrevista(UPDATED_PONTUACAO_ENTREVISTA)
            .pontuacaoAvaliacao(UPDATED_PONTUACAO_AVALIACAO);
        // Add required entity
        AreaContabil areaContabil;
        if (TestUtil.findAll(em, AreaContabil.class).isEmpty()) {
            areaContabil = AreaContabilResourceIT.createUpdatedEntity(em);
            em.persist(areaContabil);
            em.flush();
        } else {
            areaContabil = TestUtil.findAll(em, AreaContabil.class).get(0);
        }
        areaContabilContador.setAreaContabil(areaContabil);
        // Add required entity
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            contador = ContadorResourceIT.createUpdatedEntity(em);
            em.persist(contador);
            em.flush();
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        areaContabilContador.setContador(contador);
        return areaContabilContador;
    }

    @BeforeEach
    public void initTest() {
        areaContabilContador = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAreaContabilContador != null) {
            areaContabilContadorRepository.delete(insertedAreaContabilContador);
            insertedAreaContabilContador = null;
        }
    }

    @Test
    @Transactional
    void createAreaContabilContador() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AreaContabilContador
        AreaContabilContadorDTO areaContabilContadorDTO = areaContabilContadorMapper.toDto(areaContabilContador);
        var returnedAreaContabilContadorDTO = om.readValue(
            restAreaContabilContadorMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(areaContabilContadorDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AreaContabilContadorDTO.class
        );

        // Validate the AreaContabilContador in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAreaContabilContador = areaContabilContadorMapper.toEntity(returnedAreaContabilContadorDTO);
        assertAreaContabilContadorUpdatableFieldsEquals(
            returnedAreaContabilContador,
            getPersistedAreaContabilContador(returnedAreaContabilContador)
        );

        insertedAreaContabilContador = returnedAreaContabilContador;
    }

    @Test
    @Transactional
    void createAreaContabilContadorWithExistingId() throws Exception {
        // Create the AreaContabilContador with an existing ID
        areaContabilContador.setId(1L);
        AreaContabilContadorDTO areaContabilContadorDTO = areaContabilContadorMapper.toDto(areaContabilContador);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAreaContabilContadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(areaContabilContadorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AreaContabilContador in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAreaContabilContadors() throws Exception {
        // Initialize the database
        insertedAreaContabilContador = areaContabilContadorRepository.saveAndFlush(areaContabilContador);

        // Get all the areaContabilContadorList
        restAreaContabilContadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(areaContabilContador.getId().intValue())))
            .andExpect(jsonPath("$.[*].percentualExperiencia").value(hasItem(DEFAULT_PERCENTUAL_EXPERIENCIA.doubleValue())))
            .andExpect(jsonPath("$.[*].descricaoExperiencia").value(hasItem(DEFAULT_DESCRICAO_EXPERIENCIA)))
            .andExpect(jsonPath("$.[*].pontuacaoEntrevista").value(hasItem(DEFAULT_PONTUACAO_ENTREVISTA.doubleValue())))
            .andExpect(jsonPath("$.[*].pontuacaoAvaliacao").value(hasItem(DEFAULT_PONTUACAO_AVALIACAO.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAreaContabilContadorsWithEagerRelationshipsIsEnabled() throws Exception {
        when(areaContabilContadorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAreaContabilContadorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(areaContabilContadorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAreaContabilContadorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(areaContabilContadorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAreaContabilContadorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(areaContabilContadorRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAreaContabilContador() throws Exception {
        // Initialize the database
        insertedAreaContabilContador = areaContabilContadorRepository.saveAndFlush(areaContabilContador);

        // Get the areaContabilContador
        restAreaContabilContadorMockMvc
            .perform(get(ENTITY_API_URL_ID, areaContabilContador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(areaContabilContador.getId().intValue()))
            .andExpect(jsonPath("$.percentualExperiencia").value(DEFAULT_PERCENTUAL_EXPERIENCIA.doubleValue()))
            .andExpect(jsonPath("$.descricaoExperiencia").value(DEFAULT_DESCRICAO_EXPERIENCIA))
            .andExpect(jsonPath("$.pontuacaoEntrevista").value(DEFAULT_PONTUACAO_ENTREVISTA.doubleValue()))
            .andExpect(jsonPath("$.pontuacaoAvaliacao").value(DEFAULT_PONTUACAO_AVALIACAO.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingAreaContabilContador() throws Exception {
        // Get the areaContabilContador
        restAreaContabilContadorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAreaContabilContador() throws Exception {
        // Initialize the database
        insertedAreaContabilContador = areaContabilContadorRepository.saveAndFlush(areaContabilContador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the areaContabilContador
        AreaContabilContador updatedAreaContabilContador = areaContabilContadorRepository
            .findById(areaContabilContador.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAreaContabilContador are not directly saved in db
        em.detach(updatedAreaContabilContador);
        updatedAreaContabilContador
            .percentualExperiencia(UPDATED_PERCENTUAL_EXPERIENCIA)
            .descricaoExperiencia(UPDATED_DESCRICAO_EXPERIENCIA)
            .pontuacaoEntrevista(UPDATED_PONTUACAO_ENTREVISTA)
            .pontuacaoAvaliacao(UPDATED_PONTUACAO_AVALIACAO);
        AreaContabilContadorDTO areaContabilContadorDTO = areaContabilContadorMapper.toDto(updatedAreaContabilContador);

        restAreaContabilContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, areaContabilContadorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(areaContabilContadorDTO))
            )
            .andExpect(status().isOk());

        // Validate the AreaContabilContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAreaContabilContadorToMatchAllProperties(updatedAreaContabilContador);
    }

    @Test
    @Transactional
    void putNonExistingAreaContabilContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaContabilContador.setId(longCount.incrementAndGet());

        // Create the AreaContabilContador
        AreaContabilContadorDTO areaContabilContadorDTO = areaContabilContadorMapper.toDto(areaContabilContador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAreaContabilContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, areaContabilContadorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(areaContabilContadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaContabilContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAreaContabilContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaContabilContador.setId(longCount.incrementAndGet());

        // Create the AreaContabilContador
        AreaContabilContadorDTO areaContabilContadorDTO = areaContabilContadorMapper.toDto(areaContabilContador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaContabilContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(areaContabilContadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaContabilContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAreaContabilContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaContabilContador.setId(longCount.incrementAndGet());

        // Create the AreaContabilContador
        AreaContabilContadorDTO areaContabilContadorDTO = areaContabilContadorMapper.toDto(areaContabilContador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaContabilContadorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(areaContabilContadorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AreaContabilContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAreaContabilContadorWithPatch() throws Exception {
        // Initialize the database
        insertedAreaContabilContador = areaContabilContadorRepository.saveAndFlush(areaContabilContador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the areaContabilContador using partial update
        AreaContabilContador partialUpdatedAreaContabilContador = new AreaContabilContador();
        partialUpdatedAreaContabilContador.setId(areaContabilContador.getId());

        partialUpdatedAreaContabilContador.percentualExperiencia(UPDATED_PERCENTUAL_EXPERIENCIA);

        restAreaContabilContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAreaContabilContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAreaContabilContador))
            )
            .andExpect(status().isOk());

        // Validate the AreaContabilContador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAreaContabilContadorUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAreaContabilContador, areaContabilContador),
            getPersistedAreaContabilContador(areaContabilContador)
        );
    }

    @Test
    @Transactional
    void fullUpdateAreaContabilContadorWithPatch() throws Exception {
        // Initialize the database
        insertedAreaContabilContador = areaContabilContadorRepository.saveAndFlush(areaContabilContador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the areaContabilContador using partial update
        AreaContabilContador partialUpdatedAreaContabilContador = new AreaContabilContador();
        partialUpdatedAreaContabilContador.setId(areaContabilContador.getId());

        partialUpdatedAreaContabilContador
            .percentualExperiencia(UPDATED_PERCENTUAL_EXPERIENCIA)
            .descricaoExperiencia(UPDATED_DESCRICAO_EXPERIENCIA)
            .pontuacaoEntrevista(UPDATED_PONTUACAO_ENTREVISTA)
            .pontuacaoAvaliacao(UPDATED_PONTUACAO_AVALIACAO);

        restAreaContabilContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAreaContabilContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAreaContabilContador))
            )
            .andExpect(status().isOk());

        // Validate the AreaContabilContador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAreaContabilContadorUpdatableFieldsEquals(
            partialUpdatedAreaContabilContador,
            getPersistedAreaContabilContador(partialUpdatedAreaContabilContador)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAreaContabilContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaContabilContador.setId(longCount.incrementAndGet());

        // Create the AreaContabilContador
        AreaContabilContadorDTO areaContabilContadorDTO = areaContabilContadorMapper.toDto(areaContabilContador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAreaContabilContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, areaContabilContadorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(areaContabilContadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaContabilContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAreaContabilContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaContabilContador.setId(longCount.incrementAndGet());

        // Create the AreaContabilContador
        AreaContabilContadorDTO areaContabilContadorDTO = areaContabilContadorMapper.toDto(areaContabilContador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaContabilContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(areaContabilContadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaContabilContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAreaContabilContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaContabilContador.setId(longCount.incrementAndGet());

        // Create the AreaContabilContador
        AreaContabilContadorDTO areaContabilContadorDTO = areaContabilContadorMapper.toDto(areaContabilContador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaContabilContadorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(areaContabilContadorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AreaContabilContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAreaContabilContador() throws Exception {
        // Initialize the database
        insertedAreaContabilContador = areaContabilContadorRepository.saveAndFlush(areaContabilContador);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the areaContabilContador
        restAreaContabilContadorMockMvc
            .perform(delete(ENTITY_API_URL_ID, areaContabilContador.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return areaContabilContadorRepository.count();
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

    protected AreaContabilContador getPersistedAreaContabilContador(AreaContabilContador areaContabilContador) {
        return areaContabilContadorRepository.findById(areaContabilContador.getId()).orElseThrow();
    }

    protected void assertPersistedAreaContabilContadorToMatchAllProperties(AreaContabilContador expectedAreaContabilContador) {
        assertAreaContabilContadorAllPropertiesEquals(
            expectedAreaContabilContador,
            getPersistedAreaContabilContador(expectedAreaContabilContador)
        );
    }

    protected void assertPersistedAreaContabilContadorToMatchUpdatableProperties(AreaContabilContador expectedAreaContabilContador) {
        assertAreaContabilContadorAllUpdatablePropertiesEquals(
            expectedAreaContabilContador,
            getPersistedAreaContabilContador(expectedAreaContabilContador)
        );
    }
}
