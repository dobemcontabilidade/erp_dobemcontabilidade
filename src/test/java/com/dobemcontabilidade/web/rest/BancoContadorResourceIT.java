package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.BancoContadorAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Banco;
import com.dobemcontabilidade.domain.BancoContador;
import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.repository.BancoContadorRepository;
import com.dobemcontabilidade.service.BancoContadorService;
import com.dobemcontabilidade.service.dto.BancoContadorDTO;
import com.dobemcontabilidade.service.mapper.BancoContadorMapper;
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
 * Integration tests for the {@link BancoContadorResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BancoContadorResourceIT {

    private static final String DEFAULT_AGENCIA = "AAAAAAAAAA";
    private static final String UPDATED_AGENCIA = "BBBBBBBBBB";

    private static final String DEFAULT_CONTA = "AAAAAAAAAA";
    private static final String UPDATED_CONTA = "BBBBBBBBBB";

    private static final String DEFAULT_DIGITO_AGENCIA = "AAAAAAAAAA";
    private static final String UPDATED_DIGITO_AGENCIA = "BBBBBBBBBB";

    private static final String DEFAULT_DIGITO_CONTA = "AAAAAAAAAA";
    private static final String UPDATED_DIGITO_CONTA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRINCIPAL = false;
    private static final Boolean UPDATED_PRINCIPAL = true;

    private static final String ENTITY_API_URL = "/api/banco-contadors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BancoContadorRepository bancoContadorRepository;

    @Mock
    private BancoContadorRepository bancoContadorRepositoryMock;

    @Autowired
    private BancoContadorMapper bancoContadorMapper;

    @Mock
    private BancoContadorService bancoContadorServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBancoContadorMockMvc;

    private BancoContador bancoContador;

    private BancoContador insertedBancoContador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BancoContador createEntity(EntityManager em) {
        BancoContador bancoContador = new BancoContador()
            .agencia(DEFAULT_AGENCIA)
            .conta(DEFAULT_CONTA)
            .digitoAgencia(DEFAULT_DIGITO_AGENCIA)
            .digitoConta(DEFAULT_DIGITO_CONTA)
            .principal(DEFAULT_PRINCIPAL);
        // Add required entity
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            contador = ContadorResourceIT.createEntity(em);
            em.persist(contador);
            em.flush();
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        bancoContador.setContador(contador);
        // Add required entity
        Banco banco;
        if (TestUtil.findAll(em, Banco.class).isEmpty()) {
            banco = BancoResourceIT.createEntity(em);
            em.persist(banco);
            em.flush();
        } else {
            banco = TestUtil.findAll(em, Banco.class).get(0);
        }
        bancoContador.setBanco(banco);
        return bancoContador;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BancoContador createUpdatedEntity(EntityManager em) {
        BancoContador bancoContador = new BancoContador()
            .agencia(UPDATED_AGENCIA)
            .conta(UPDATED_CONTA)
            .digitoAgencia(UPDATED_DIGITO_AGENCIA)
            .digitoConta(UPDATED_DIGITO_CONTA)
            .principal(UPDATED_PRINCIPAL);
        // Add required entity
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            contador = ContadorResourceIT.createUpdatedEntity(em);
            em.persist(contador);
            em.flush();
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        bancoContador.setContador(contador);
        // Add required entity
        Banco banco;
        if (TestUtil.findAll(em, Banco.class).isEmpty()) {
            banco = BancoResourceIT.createUpdatedEntity(em);
            em.persist(banco);
            em.flush();
        } else {
            banco = TestUtil.findAll(em, Banco.class).get(0);
        }
        bancoContador.setBanco(banco);
        return bancoContador;
    }

    @BeforeEach
    public void initTest() {
        bancoContador = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedBancoContador != null) {
            bancoContadorRepository.delete(insertedBancoContador);
            insertedBancoContador = null;
        }
    }

    @Test
    @Transactional
    void createBancoContador() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the BancoContador
        BancoContadorDTO bancoContadorDTO = bancoContadorMapper.toDto(bancoContador);
        var returnedBancoContadorDTO = om.readValue(
            restBancoContadorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bancoContadorDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BancoContadorDTO.class
        );

        // Validate the BancoContador in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBancoContador = bancoContadorMapper.toEntity(returnedBancoContadorDTO);
        assertBancoContadorUpdatableFieldsEquals(returnedBancoContador, getPersistedBancoContador(returnedBancoContador));

        insertedBancoContador = returnedBancoContador;
    }

    @Test
    @Transactional
    void createBancoContadorWithExistingId() throws Exception {
        // Create the BancoContador with an existing ID
        bancoContador.setId(1L);
        BancoContadorDTO bancoContadorDTO = bancoContadorMapper.toDto(bancoContador);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBancoContadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bancoContadorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BancoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAgenciaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bancoContador.setAgencia(null);

        // Create the BancoContador, which fails.
        BancoContadorDTO bancoContadorDTO = bancoContadorMapper.toDto(bancoContador);

        restBancoContadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bancoContadorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bancoContador.setConta(null);

        // Create the BancoContador, which fails.
        BancoContadorDTO bancoContadorDTO = bancoContadorMapper.toDto(bancoContador);

        restBancoContadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bancoContadorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBancoContadors() throws Exception {
        // Initialize the database
        insertedBancoContador = bancoContadorRepository.saveAndFlush(bancoContador);

        // Get all the bancoContadorList
        restBancoContadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bancoContador.getId().intValue())))
            .andExpect(jsonPath("$.[*].agencia").value(hasItem(DEFAULT_AGENCIA)))
            .andExpect(jsonPath("$.[*].conta").value(hasItem(DEFAULT_CONTA)))
            .andExpect(jsonPath("$.[*].digitoAgencia").value(hasItem(DEFAULT_DIGITO_AGENCIA)))
            .andExpect(jsonPath("$.[*].digitoConta").value(hasItem(DEFAULT_DIGITO_CONTA)))
            .andExpect(jsonPath("$.[*].principal").value(hasItem(DEFAULT_PRINCIPAL.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBancoContadorsWithEagerRelationshipsIsEnabled() throws Exception {
        when(bancoContadorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBancoContadorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(bancoContadorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBancoContadorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(bancoContadorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBancoContadorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(bancoContadorRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getBancoContador() throws Exception {
        // Initialize the database
        insertedBancoContador = bancoContadorRepository.saveAndFlush(bancoContador);

        // Get the bancoContador
        restBancoContadorMockMvc
            .perform(get(ENTITY_API_URL_ID, bancoContador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bancoContador.getId().intValue()))
            .andExpect(jsonPath("$.agencia").value(DEFAULT_AGENCIA))
            .andExpect(jsonPath("$.conta").value(DEFAULT_CONTA))
            .andExpect(jsonPath("$.digitoAgencia").value(DEFAULT_DIGITO_AGENCIA))
            .andExpect(jsonPath("$.digitoConta").value(DEFAULT_DIGITO_CONTA))
            .andExpect(jsonPath("$.principal").value(DEFAULT_PRINCIPAL.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingBancoContador() throws Exception {
        // Get the bancoContador
        restBancoContadorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBancoContador() throws Exception {
        // Initialize the database
        insertedBancoContador = bancoContadorRepository.saveAndFlush(bancoContador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bancoContador
        BancoContador updatedBancoContador = bancoContadorRepository.findById(bancoContador.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBancoContador are not directly saved in db
        em.detach(updatedBancoContador);
        updatedBancoContador
            .agencia(UPDATED_AGENCIA)
            .conta(UPDATED_CONTA)
            .digitoAgencia(UPDATED_DIGITO_AGENCIA)
            .digitoConta(UPDATED_DIGITO_CONTA)
            .principal(UPDATED_PRINCIPAL);
        BancoContadorDTO bancoContadorDTO = bancoContadorMapper.toDto(updatedBancoContador);

        restBancoContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bancoContadorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bancoContadorDTO))
            )
            .andExpect(status().isOk());

        // Validate the BancoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBancoContadorToMatchAllProperties(updatedBancoContador);
    }

    @Test
    @Transactional
    void putNonExistingBancoContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bancoContador.setId(longCount.incrementAndGet());

        // Create the BancoContador
        BancoContadorDTO bancoContadorDTO = bancoContadorMapper.toDto(bancoContador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBancoContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bancoContadorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bancoContadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BancoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBancoContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bancoContador.setId(longCount.incrementAndGet());

        // Create the BancoContador
        BancoContadorDTO bancoContadorDTO = bancoContadorMapper.toDto(bancoContador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBancoContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bancoContadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BancoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBancoContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bancoContador.setId(longCount.incrementAndGet());

        // Create the BancoContador
        BancoContadorDTO bancoContadorDTO = bancoContadorMapper.toDto(bancoContador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBancoContadorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bancoContadorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BancoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBancoContadorWithPatch() throws Exception {
        // Initialize the database
        insertedBancoContador = bancoContadorRepository.saveAndFlush(bancoContador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bancoContador using partial update
        BancoContador partialUpdatedBancoContador = new BancoContador();
        partialUpdatedBancoContador.setId(bancoContador.getId());

        partialUpdatedBancoContador.agencia(UPDATED_AGENCIA).digitoConta(UPDATED_DIGITO_CONTA).principal(UPDATED_PRINCIPAL);

        restBancoContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBancoContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBancoContador))
            )
            .andExpect(status().isOk());

        // Validate the BancoContador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBancoContadorUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBancoContador, bancoContador),
            getPersistedBancoContador(bancoContador)
        );
    }

    @Test
    @Transactional
    void fullUpdateBancoContadorWithPatch() throws Exception {
        // Initialize the database
        insertedBancoContador = bancoContadorRepository.saveAndFlush(bancoContador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bancoContador using partial update
        BancoContador partialUpdatedBancoContador = new BancoContador();
        partialUpdatedBancoContador.setId(bancoContador.getId());

        partialUpdatedBancoContador
            .agencia(UPDATED_AGENCIA)
            .conta(UPDATED_CONTA)
            .digitoAgencia(UPDATED_DIGITO_AGENCIA)
            .digitoConta(UPDATED_DIGITO_CONTA)
            .principal(UPDATED_PRINCIPAL);

        restBancoContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBancoContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBancoContador))
            )
            .andExpect(status().isOk());

        // Validate the BancoContador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBancoContadorUpdatableFieldsEquals(partialUpdatedBancoContador, getPersistedBancoContador(partialUpdatedBancoContador));
    }

    @Test
    @Transactional
    void patchNonExistingBancoContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bancoContador.setId(longCount.incrementAndGet());

        // Create the BancoContador
        BancoContadorDTO bancoContadorDTO = bancoContadorMapper.toDto(bancoContador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBancoContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bancoContadorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bancoContadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BancoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBancoContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bancoContador.setId(longCount.incrementAndGet());

        // Create the BancoContador
        BancoContadorDTO bancoContadorDTO = bancoContadorMapper.toDto(bancoContador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBancoContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bancoContadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BancoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBancoContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bancoContador.setId(longCount.incrementAndGet());

        // Create the BancoContador
        BancoContadorDTO bancoContadorDTO = bancoContadorMapper.toDto(bancoContador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBancoContadorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bancoContadorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BancoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBancoContador() throws Exception {
        // Initialize the database
        insertedBancoContador = bancoContadorRepository.saveAndFlush(bancoContador);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bancoContador
        restBancoContadorMockMvc
            .perform(delete(ENTITY_API_URL_ID, bancoContador.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bancoContadorRepository.count();
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

    protected BancoContador getPersistedBancoContador(BancoContador bancoContador) {
        return bancoContadorRepository.findById(bancoContador.getId()).orElseThrow();
    }

    protected void assertPersistedBancoContadorToMatchAllProperties(BancoContador expectedBancoContador) {
        assertBancoContadorAllPropertiesEquals(expectedBancoContador, getPersistedBancoContador(expectedBancoContador));
    }

    protected void assertPersistedBancoContadorToMatchUpdatableProperties(BancoContador expectedBancoContador) {
        assertBancoContadorAllUpdatablePropertiesEquals(expectedBancoContador, getPersistedBancoContador(expectedBancoContador));
    }
}
