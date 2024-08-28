package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.RedeSocialEmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Pessoajuridica;
import com.dobemcontabilidade.domain.RedeSocial;
import com.dobemcontabilidade.domain.RedeSocialEmpresa;
import com.dobemcontabilidade.repository.RedeSocialEmpresaRepository;
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
 * Integration tests for the {@link RedeSocialEmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RedeSocialEmpresaResourceIT {

    private static final String DEFAULT_PERFIL = "AAAAAAAAAA";
    private static final String UPDATED_PERFIL = "BBBBBBBBBB";

    private static final String DEFAULT_URL_PERFIL = "AAAAAAAAAA";
    private static final String UPDATED_URL_PERFIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rede-social-empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RedeSocialEmpresaRepository redeSocialEmpresaRepository;

    @Mock
    private RedeSocialEmpresaRepository redeSocialEmpresaRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRedeSocialEmpresaMockMvc;

    private RedeSocialEmpresa redeSocialEmpresa;

    private RedeSocialEmpresa insertedRedeSocialEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RedeSocialEmpresa createEntity(EntityManager em) {
        RedeSocialEmpresa redeSocialEmpresa = new RedeSocialEmpresa().perfil(DEFAULT_PERFIL).urlPerfil(DEFAULT_URL_PERFIL);
        // Add required entity
        RedeSocial redeSocial;
        if (TestUtil.findAll(em, RedeSocial.class).isEmpty()) {
            redeSocial = RedeSocialResourceIT.createEntity(em);
            em.persist(redeSocial);
            em.flush();
        } else {
            redeSocial = TestUtil.findAll(em, RedeSocial.class).get(0);
        }
        redeSocialEmpresa.setRedeSocial(redeSocial);
        // Add required entity
        Pessoajuridica pessoajuridica;
        if (TestUtil.findAll(em, Pessoajuridica.class).isEmpty()) {
            pessoajuridica = PessoajuridicaResourceIT.createEntity(em);
            em.persist(pessoajuridica);
            em.flush();
        } else {
            pessoajuridica = TestUtil.findAll(em, Pessoajuridica.class).get(0);
        }
        redeSocialEmpresa.setPessoajuridica(pessoajuridica);
        return redeSocialEmpresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RedeSocialEmpresa createUpdatedEntity(EntityManager em) {
        RedeSocialEmpresa redeSocialEmpresa = new RedeSocialEmpresa().perfil(UPDATED_PERFIL).urlPerfil(UPDATED_URL_PERFIL);
        // Add required entity
        RedeSocial redeSocial;
        if (TestUtil.findAll(em, RedeSocial.class).isEmpty()) {
            redeSocial = RedeSocialResourceIT.createUpdatedEntity(em);
            em.persist(redeSocial);
            em.flush();
        } else {
            redeSocial = TestUtil.findAll(em, RedeSocial.class).get(0);
        }
        redeSocialEmpresa.setRedeSocial(redeSocial);
        // Add required entity
        Pessoajuridica pessoajuridica;
        if (TestUtil.findAll(em, Pessoajuridica.class).isEmpty()) {
            pessoajuridica = PessoajuridicaResourceIT.createUpdatedEntity(em);
            em.persist(pessoajuridica);
            em.flush();
        } else {
            pessoajuridica = TestUtil.findAll(em, Pessoajuridica.class).get(0);
        }
        redeSocialEmpresa.setPessoajuridica(pessoajuridica);
        return redeSocialEmpresa;
    }

    @BeforeEach
    public void initTest() {
        redeSocialEmpresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedRedeSocialEmpresa != null) {
            redeSocialEmpresaRepository.delete(insertedRedeSocialEmpresa);
            insertedRedeSocialEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createRedeSocialEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RedeSocialEmpresa
        var returnedRedeSocialEmpresa = om.readValue(
            restRedeSocialEmpresaMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(redeSocialEmpresa))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RedeSocialEmpresa.class
        );

        // Validate the RedeSocialEmpresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRedeSocialEmpresaUpdatableFieldsEquals(returnedRedeSocialEmpresa, getPersistedRedeSocialEmpresa(returnedRedeSocialEmpresa));

        insertedRedeSocialEmpresa = returnedRedeSocialEmpresa;
    }

    @Test
    @Transactional
    void createRedeSocialEmpresaWithExistingId() throws Exception {
        // Create the RedeSocialEmpresa with an existing ID
        redeSocialEmpresa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRedeSocialEmpresaMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(redeSocialEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the RedeSocialEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPerfilIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        redeSocialEmpresa.setPerfil(null);

        // Create the RedeSocialEmpresa, which fails.

        restRedeSocialEmpresaMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(redeSocialEmpresa))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRedeSocialEmpresas() throws Exception {
        // Initialize the database
        insertedRedeSocialEmpresa = redeSocialEmpresaRepository.saveAndFlush(redeSocialEmpresa);

        // Get all the redeSocialEmpresaList
        restRedeSocialEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(redeSocialEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].perfil").value(hasItem(DEFAULT_PERFIL)))
            .andExpect(jsonPath("$.[*].urlPerfil").value(hasItem(DEFAULT_URL_PERFIL.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRedeSocialEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(redeSocialEmpresaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRedeSocialEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(redeSocialEmpresaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRedeSocialEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(redeSocialEmpresaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRedeSocialEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(redeSocialEmpresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getRedeSocialEmpresa() throws Exception {
        // Initialize the database
        insertedRedeSocialEmpresa = redeSocialEmpresaRepository.saveAndFlush(redeSocialEmpresa);

        // Get the redeSocialEmpresa
        restRedeSocialEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, redeSocialEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(redeSocialEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.perfil").value(DEFAULT_PERFIL))
            .andExpect(jsonPath("$.urlPerfil").value(DEFAULT_URL_PERFIL.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRedeSocialEmpresa() throws Exception {
        // Get the redeSocialEmpresa
        restRedeSocialEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRedeSocialEmpresa() throws Exception {
        // Initialize the database
        insertedRedeSocialEmpresa = redeSocialEmpresaRepository.saveAndFlush(redeSocialEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the redeSocialEmpresa
        RedeSocialEmpresa updatedRedeSocialEmpresa = redeSocialEmpresaRepository.findById(redeSocialEmpresa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRedeSocialEmpresa are not directly saved in db
        em.detach(updatedRedeSocialEmpresa);
        updatedRedeSocialEmpresa.perfil(UPDATED_PERFIL).urlPerfil(UPDATED_URL_PERFIL);

        restRedeSocialEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRedeSocialEmpresa.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRedeSocialEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the RedeSocialEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRedeSocialEmpresaToMatchAllProperties(updatedRedeSocialEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingRedeSocialEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redeSocialEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRedeSocialEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, redeSocialEmpresa.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(redeSocialEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the RedeSocialEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRedeSocialEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redeSocialEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRedeSocialEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(redeSocialEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the RedeSocialEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRedeSocialEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redeSocialEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRedeSocialEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(redeSocialEmpresa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RedeSocialEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRedeSocialEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedRedeSocialEmpresa = redeSocialEmpresaRepository.saveAndFlush(redeSocialEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the redeSocialEmpresa using partial update
        RedeSocialEmpresa partialUpdatedRedeSocialEmpresa = new RedeSocialEmpresa();
        partialUpdatedRedeSocialEmpresa.setId(redeSocialEmpresa.getId());

        partialUpdatedRedeSocialEmpresa.perfil(UPDATED_PERFIL);

        restRedeSocialEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRedeSocialEmpresa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRedeSocialEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the RedeSocialEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRedeSocialEmpresaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRedeSocialEmpresa, redeSocialEmpresa),
            getPersistedRedeSocialEmpresa(redeSocialEmpresa)
        );
    }

    @Test
    @Transactional
    void fullUpdateRedeSocialEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedRedeSocialEmpresa = redeSocialEmpresaRepository.saveAndFlush(redeSocialEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the redeSocialEmpresa using partial update
        RedeSocialEmpresa partialUpdatedRedeSocialEmpresa = new RedeSocialEmpresa();
        partialUpdatedRedeSocialEmpresa.setId(redeSocialEmpresa.getId());

        partialUpdatedRedeSocialEmpresa.perfil(UPDATED_PERFIL).urlPerfil(UPDATED_URL_PERFIL);

        restRedeSocialEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRedeSocialEmpresa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRedeSocialEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the RedeSocialEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRedeSocialEmpresaUpdatableFieldsEquals(
            partialUpdatedRedeSocialEmpresa,
            getPersistedRedeSocialEmpresa(partialUpdatedRedeSocialEmpresa)
        );
    }

    @Test
    @Transactional
    void patchNonExistingRedeSocialEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redeSocialEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRedeSocialEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, redeSocialEmpresa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(redeSocialEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the RedeSocialEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRedeSocialEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redeSocialEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRedeSocialEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(redeSocialEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the RedeSocialEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRedeSocialEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redeSocialEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRedeSocialEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(redeSocialEmpresa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RedeSocialEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRedeSocialEmpresa() throws Exception {
        // Initialize the database
        insertedRedeSocialEmpresa = redeSocialEmpresaRepository.saveAndFlush(redeSocialEmpresa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the redeSocialEmpresa
        restRedeSocialEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, redeSocialEmpresa.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return redeSocialEmpresaRepository.count();
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

    protected RedeSocialEmpresa getPersistedRedeSocialEmpresa(RedeSocialEmpresa redeSocialEmpresa) {
        return redeSocialEmpresaRepository.findById(redeSocialEmpresa.getId()).orElseThrow();
    }

    protected void assertPersistedRedeSocialEmpresaToMatchAllProperties(RedeSocialEmpresa expectedRedeSocialEmpresa) {
        assertRedeSocialEmpresaAllPropertiesEquals(expectedRedeSocialEmpresa, getPersistedRedeSocialEmpresa(expectedRedeSocialEmpresa));
    }

    protected void assertPersistedRedeSocialEmpresaToMatchUpdatableProperties(RedeSocialEmpresa expectedRedeSocialEmpresa) {
        assertRedeSocialEmpresaAllUpdatablePropertiesEquals(
            expectedRedeSocialEmpresa,
            getPersistedRedeSocialEmpresa(expectedRedeSocialEmpresa)
        );
    }
}
