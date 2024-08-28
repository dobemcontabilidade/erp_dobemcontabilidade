package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.AdicionalTributacaoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AdicionalTributacao;
import com.dobemcontabilidade.domain.PlanoAssinaturaContabil;
import com.dobemcontabilidade.domain.Tributacao;
import com.dobemcontabilidade.repository.AdicionalTributacaoRepository;
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
 * Integration tests for the {@link AdicionalTributacaoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AdicionalTributacaoResourceIT {

    private static final Double DEFAULT_VALOR = 1D;
    private static final Double UPDATED_VALOR = 2D;

    private static final String ENTITY_API_URL = "/api/adicional-tributacaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AdicionalTributacaoRepository adicionalTributacaoRepository;

    @Mock
    private AdicionalTributacaoRepository adicionalTributacaoRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdicionalTributacaoMockMvc;

    private AdicionalTributacao adicionalTributacao;

    private AdicionalTributacao insertedAdicionalTributacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdicionalTributacao createEntity(EntityManager em) {
        AdicionalTributacao adicionalTributacao = new AdicionalTributacao().valor(DEFAULT_VALOR);
        // Add required entity
        Tributacao tributacao;
        if (TestUtil.findAll(em, Tributacao.class).isEmpty()) {
            tributacao = TributacaoResourceIT.createEntity(em);
            em.persist(tributacao);
            em.flush();
        } else {
            tributacao = TestUtil.findAll(em, Tributacao.class).get(0);
        }
        adicionalTributacao.setTributacao(tributacao);
        // Add required entity
        PlanoAssinaturaContabil planoAssinaturaContabil;
        if (TestUtil.findAll(em, PlanoAssinaturaContabil.class).isEmpty()) {
            planoAssinaturaContabil = PlanoAssinaturaContabilResourceIT.createEntity(em);
            em.persist(planoAssinaturaContabil);
            em.flush();
        } else {
            planoAssinaturaContabil = TestUtil.findAll(em, PlanoAssinaturaContabil.class).get(0);
        }
        adicionalTributacao.setPlanoAssinaturaContabil(planoAssinaturaContabil);
        return adicionalTributacao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdicionalTributacao createUpdatedEntity(EntityManager em) {
        AdicionalTributacao adicionalTributacao = new AdicionalTributacao().valor(UPDATED_VALOR);
        // Add required entity
        Tributacao tributacao;
        if (TestUtil.findAll(em, Tributacao.class).isEmpty()) {
            tributacao = TributacaoResourceIT.createUpdatedEntity(em);
            em.persist(tributacao);
            em.flush();
        } else {
            tributacao = TestUtil.findAll(em, Tributacao.class).get(0);
        }
        adicionalTributacao.setTributacao(tributacao);
        // Add required entity
        PlanoAssinaturaContabil planoAssinaturaContabil;
        if (TestUtil.findAll(em, PlanoAssinaturaContabil.class).isEmpty()) {
            planoAssinaturaContabil = PlanoAssinaturaContabilResourceIT.createUpdatedEntity(em);
            em.persist(planoAssinaturaContabil);
            em.flush();
        } else {
            planoAssinaturaContabil = TestUtil.findAll(em, PlanoAssinaturaContabil.class).get(0);
        }
        adicionalTributacao.setPlanoAssinaturaContabil(planoAssinaturaContabil);
        return adicionalTributacao;
    }

    @BeforeEach
    public void initTest() {
        adicionalTributacao = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAdicionalTributacao != null) {
            adicionalTributacaoRepository.delete(insertedAdicionalTributacao);
            insertedAdicionalTributacao = null;
        }
    }

    @Test
    @Transactional
    void createAdicionalTributacao() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AdicionalTributacao
        var returnedAdicionalTributacao = om.readValue(
            restAdicionalTributacaoMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(adicionalTributacao))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AdicionalTributacao.class
        );

        // Validate the AdicionalTributacao in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAdicionalTributacaoUpdatableFieldsEquals(
            returnedAdicionalTributacao,
            getPersistedAdicionalTributacao(returnedAdicionalTributacao)
        );

        insertedAdicionalTributacao = returnedAdicionalTributacao;
    }

    @Test
    @Transactional
    void createAdicionalTributacaoWithExistingId() throws Exception {
        // Create the AdicionalTributacao with an existing ID
        adicionalTributacao.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdicionalTributacaoMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adicionalTributacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdicionalTributacao in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValorIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        adicionalTributacao.setValor(null);

        // Create the AdicionalTributacao, which fails.

        restAdicionalTributacaoMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adicionalTributacao))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdicionalTributacaos() throws Exception {
        // Initialize the database
        insertedAdicionalTributacao = adicionalTributacaoRepository.saveAndFlush(adicionalTributacao);

        // Get all the adicionalTributacaoList
        restAdicionalTributacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adicionalTributacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAdicionalTributacaosWithEagerRelationshipsIsEnabled() throws Exception {
        when(adicionalTributacaoRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAdicionalTributacaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(adicionalTributacaoRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAdicionalTributacaosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(adicionalTributacaoRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAdicionalTributacaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(adicionalTributacaoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAdicionalTributacao() throws Exception {
        // Initialize the database
        insertedAdicionalTributacao = adicionalTributacaoRepository.saveAndFlush(adicionalTributacao);

        // Get the adicionalTributacao
        restAdicionalTributacaoMockMvc
            .perform(get(ENTITY_API_URL_ID, adicionalTributacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adicionalTributacao.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingAdicionalTributacao() throws Exception {
        // Get the adicionalTributacao
        restAdicionalTributacaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAdicionalTributacao() throws Exception {
        // Initialize the database
        insertedAdicionalTributacao = adicionalTributacaoRepository.saveAndFlush(adicionalTributacao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the adicionalTributacao
        AdicionalTributacao updatedAdicionalTributacao = adicionalTributacaoRepository.findById(adicionalTributacao.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAdicionalTributacao are not directly saved in db
        em.detach(updatedAdicionalTributacao);
        updatedAdicionalTributacao.valor(UPDATED_VALOR);

        restAdicionalTributacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAdicionalTributacao.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAdicionalTributacao))
            )
            .andExpect(status().isOk());

        // Validate the AdicionalTributacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAdicionalTributacaoToMatchAllProperties(updatedAdicionalTributacao);
    }

    @Test
    @Transactional
    void putNonExistingAdicionalTributacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adicionalTributacao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdicionalTributacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adicionalTributacao.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(adicionalTributacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdicionalTributacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdicionalTributacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adicionalTributacao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdicionalTributacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(adicionalTributacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdicionalTributacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdicionalTributacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adicionalTributacao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdicionalTributacaoMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adicionalTributacao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdicionalTributacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdicionalTributacaoWithPatch() throws Exception {
        // Initialize the database
        insertedAdicionalTributacao = adicionalTributacaoRepository.saveAndFlush(adicionalTributacao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the adicionalTributacao using partial update
        AdicionalTributacao partialUpdatedAdicionalTributacao = new AdicionalTributacao();
        partialUpdatedAdicionalTributacao.setId(adicionalTributacao.getId());

        partialUpdatedAdicionalTributacao.valor(UPDATED_VALOR);

        restAdicionalTributacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdicionalTributacao.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdicionalTributacao))
            )
            .andExpect(status().isOk());

        // Validate the AdicionalTributacao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdicionalTributacaoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAdicionalTributacao, adicionalTributacao),
            getPersistedAdicionalTributacao(adicionalTributacao)
        );
    }

    @Test
    @Transactional
    void fullUpdateAdicionalTributacaoWithPatch() throws Exception {
        // Initialize the database
        insertedAdicionalTributacao = adicionalTributacaoRepository.saveAndFlush(adicionalTributacao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the adicionalTributacao using partial update
        AdicionalTributacao partialUpdatedAdicionalTributacao = new AdicionalTributacao();
        partialUpdatedAdicionalTributacao.setId(adicionalTributacao.getId());

        partialUpdatedAdicionalTributacao.valor(UPDATED_VALOR);

        restAdicionalTributacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdicionalTributacao.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdicionalTributacao))
            )
            .andExpect(status().isOk());

        // Validate the AdicionalTributacao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdicionalTributacaoUpdatableFieldsEquals(
            partialUpdatedAdicionalTributacao,
            getPersistedAdicionalTributacao(partialUpdatedAdicionalTributacao)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAdicionalTributacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adicionalTributacao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdicionalTributacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adicionalTributacao.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(adicionalTributacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdicionalTributacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdicionalTributacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adicionalTributacao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdicionalTributacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(adicionalTributacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdicionalTributacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdicionalTributacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adicionalTributacao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdicionalTributacaoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(adicionalTributacao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdicionalTributacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdicionalTributacao() throws Exception {
        // Initialize the database
        insertedAdicionalTributacao = adicionalTributacaoRepository.saveAndFlush(adicionalTributacao);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the adicionalTributacao
        restAdicionalTributacaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, adicionalTributacao.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return adicionalTributacaoRepository.count();
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

    protected AdicionalTributacao getPersistedAdicionalTributacao(AdicionalTributacao adicionalTributacao) {
        return adicionalTributacaoRepository.findById(adicionalTributacao.getId()).orElseThrow();
    }

    protected void assertPersistedAdicionalTributacaoToMatchAllProperties(AdicionalTributacao expectedAdicionalTributacao) {
        assertAdicionalTributacaoAllPropertiesEquals(
            expectedAdicionalTributacao,
            getPersistedAdicionalTributacao(expectedAdicionalTributacao)
        );
    }

    protected void assertPersistedAdicionalTributacaoToMatchUpdatableProperties(AdicionalTributacao expectedAdicionalTributacao) {
        assertAdicionalTributacaoAllUpdatablePropertiesEquals(
            expectedAdicionalTributacao,
            getPersistedAdicionalTributacao(expectedAdicionalTributacao)
        );
    }
}
