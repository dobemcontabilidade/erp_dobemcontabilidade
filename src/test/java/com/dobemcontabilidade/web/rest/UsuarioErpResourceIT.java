package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.UsuarioErpAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Administrador;
import com.dobemcontabilidade.domain.UsuarioErp;
import com.dobemcontabilidade.domain.enumeration.SituacaoUsuarioErpEnum;
import com.dobemcontabilidade.repository.UsuarioErpRepository;
import com.dobemcontabilidade.service.UsuarioErpService;
import com.dobemcontabilidade.service.dto.UsuarioErpDTO;
import com.dobemcontabilidade.service.mapper.UsuarioErpMapper;
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
 * Integration tests for the {@link UsuarioErpResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class UsuarioErpResourceIT {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_SENHA = "AAAAAAAAAA";
    private static final String UPDATED_SENHA = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_HORA_ATIVACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_HORA_ATIVACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_LIMITE_ACESSO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_LIMITE_ACESSO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final SituacaoUsuarioErpEnum DEFAULT_SITUACAO = SituacaoUsuarioErpEnum.ATIVO;
    private static final SituacaoUsuarioErpEnum UPDATED_SITUACAO = SituacaoUsuarioErpEnum.INATIVO;

    private static final String ENTITY_API_URL = "/api/usuario-erps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UsuarioErpRepository usuarioErpRepository;

    @Mock
    private UsuarioErpRepository usuarioErpRepositoryMock;

    @Autowired
    private UsuarioErpMapper usuarioErpMapper;

    @Mock
    private UsuarioErpService usuarioErpServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUsuarioErpMockMvc;

    private UsuarioErp usuarioErp;

    private UsuarioErp insertedUsuarioErp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsuarioErp createEntity(EntityManager em) {
        UsuarioErp usuarioErp = new UsuarioErp()
            .email(DEFAULT_EMAIL)
            .senha(DEFAULT_SENHA)
            .dataHoraAtivacao(DEFAULT_DATA_HORA_ATIVACAO)
            .dataLimiteAcesso(DEFAULT_DATA_LIMITE_ACESSO)
            .situacao(DEFAULT_SITUACAO);
        // Add required entity
        Administrador administrador;
        if (TestUtil.findAll(em, Administrador.class).isEmpty()) {
            administrador = AdministradorResourceIT.createEntity(em);
            em.persist(administrador);
            em.flush();
        } else {
            administrador = TestUtil.findAll(em, Administrador.class).get(0);
        }
        usuarioErp.setAdministrador(administrador);
        return usuarioErp;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsuarioErp createUpdatedEntity(EntityManager em) {
        UsuarioErp usuarioErp = new UsuarioErp()
            .email(UPDATED_EMAIL)
            .senha(UPDATED_SENHA)
            .dataHoraAtivacao(UPDATED_DATA_HORA_ATIVACAO)
            .dataLimiteAcesso(UPDATED_DATA_LIMITE_ACESSO)
            .situacao(UPDATED_SITUACAO);
        // Add required entity
        Administrador administrador;
        if (TestUtil.findAll(em, Administrador.class).isEmpty()) {
            administrador = AdministradorResourceIT.createUpdatedEntity(em);
            em.persist(administrador);
            em.flush();
        } else {
            administrador = TestUtil.findAll(em, Administrador.class).get(0);
        }
        usuarioErp.setAdministrador(administrador);
        return usuarioErp;
    }

    @BeforeEach
    public void initTest() {
        usuarioErp = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedUsuarioErp != null) {
            usuarioErpRepository.delete(insertedUsuarioErp);
            insertedUsuarioErp = null;
        }
    }

    @Test
    @Transactional
    void createUsuarioErp() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the UsuarioErp
        UsuarioErpDTO usuarioErpDTO = usuarioErpMapper.toDto(usuarioErp);
        var returnedUsuarioErpDTO = om.readValue(
            restUsuarioErpMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioErpDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            UsuarioErpDTO.class
        );

        // Validate the UsuarioErp in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedUsuarioErp = usuarioErpMapper.toEntity(returnedUsuarioErpDTO);
        assertUsuarioErpUpdatableFieldsEquals(returnedUsuarioErp, getPersistedUsuarioErp(returnedUsuarioErp));

        insertedUsuarioErp = returnedUsuarioErp;
    }

    @Test
    @Transactional
    void createUsuarioErpWithExistingId() throws Exception {
        // Create the UsuarioErp with an existing ID
        usuarioErp.setId(1L);
        UsuarioErpDTO usuarioErpDTO = usuarioErpMapper.toDto(usuarioErp);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuarioErpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioErpDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UsuarioErp in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        usuarioErp.setEmail(null);

        // Create the UsuarioErp, which fails.
        UsuarioErpDTO usuarioErpDTO = usuarioErpMapper.toDto(usuarioErp);

        restUsuarioErpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioErpDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUsuarioErps() throws Exception {
        // Initialize the database
        insertedUsuarioErp = usuarioErpRepository.saveAndFlush(usuarioErp);

        // Get all the usuarioErpList
        restUsuarioErpMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuarioErp.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].senha").value(hasItem(DEFAULT_SENHA.toString())))
            .andExpect(jsonPath("$.[*].dataHoraAtivacao").value(hasItem(DEFAULT_DATA_HORA_ATIVACAO.toString())))
            .andExpect(jsonPath("$.[*].dataLimiteAcesso").value(hasItem(DEFAULT_DATA_LIMITE_ACESSO.toString())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUsuarioErpsWithEagerRelationshipsIsEnabled() throws Exception {
        when(usuarioErpServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUsuarioErpMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(usuarioErpServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUsuarioErpsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(usuarioErpServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUsuarioErpMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(usuarioErpRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getUsuarioErp() throws Exception {
        // Initialize the database
        insertedUsuarioErp = usuarioErpRepository.saveAndFlush(usuarioErp);

        // Get the usuarioErp
        restUsuarioErpMockMvc
            .perform(get(ENTITY_API_URL_ID, usuarioErp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usuarioErp.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.senha").value(DEFAULT_SENHA.toString()))
            .andExpect(jsonPath("$.dataHoraAtivacao").value(DEFAULT_DATA_HORA_ATIVACAO.toString()))
            .andExpect(jsonPath("$.dataLimiteAcesso").value(DEFAULT_DATA_LIMITE_ACESSO.toString()))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingUsuarioErp() throws Exception {
        // Get the usuarioErp
        restUsuarioErpMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUsuarioErp() throws Exception {
        // Initialize the database
        insertedUsuarioErp = usuarioErpRepository.saveAndFlush(usuarioErp);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the usuarioErp
        UsuarioErp updatedUsuarioErp = usuarioErpRepository.findById(usuarioErp.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUsuarioErp are not directly saved in db
        em.detach(updatedUsuarioErp);
        updatedUsuarioErp
            .email(UPDATED_EMAIL)
            .senha(UPDATED_SENHA)
            .dataHoraAtivacao(UPDATED_DATA_HORA_ATIVACAO)
            .dataLimiteAcesso(UPDATED_DATA_LIMITE_ACESSO)
            .situacao(UPDATED_SITUACAO);
        UsuarioErpDTO usuarioErpDTO = usuarioErpMapper.toDto(updatedUsuarioErp);

        restUsuarioErpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, usuarioErpDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(usuarioErpDTO))
            )
            .andExpect(status().isOk());

        // Validate the UsuarioErp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedUsuarioErpToMatchAllProperties(updatedUsuarioErp);
    }

    @Test
    @Transactional
    void putNonExistingUsuarioErp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioErp.setId(longCount.incrementAndGet());

        // Create the UsuarioErp
        UsuarioErpDTO usuarioErpDTO = usuarioErpMapper.toDto(usuarioErp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioErpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, usuarioErpDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(usuarioErpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioErp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUsuarioErp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioErp.setId(longCount.incrementAndGet());

        // Create the UsuarioErp
        UsuarioErpDTO usuarioErpDTO = usuarioErpMapper.toDto(usuarioErp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioErpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(usuarioErpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioErp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUsuarioErp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioErp.setId(longCount.incrementAndGet());

        // Create the UsuarioErp
        UsuarioErpDTO usuarioErpDTO = usuarioErpMapper.toDto(usuarioErp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioErpMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioErpDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UsuarioErp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUsuarioErpWithPatch() throws Exception {
        // Initialize the database
        insertedUsuarioErp = usuarioErpRepository.saveAndFlush(usuarioErp);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the usuarioErp using partial update
        UsuarioErp partialUpdatedUsuarioErp = new UsuarioErp();
        partialUpdatedUsuarioErp.setId(usuarioErp.getId());

        partialUpdatedUsuarioErp
            .email(UPDATED_EMAIL)
            .senha(UPDATED_SENHA)
            .dataHoraAtivacao(UPDATED_DATA_HORA_ATIVACAO)
            .situacao(UPDATED_SITUACAO);

        restUsuarioErpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsuarioErp.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUsuarioErp))
            )
            .andExpect(status().isOk());

        // Validate the UsuarioErp in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUsuarioErpUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedUsuarioErp, usuarioErp),
            getPersistedUsuarioErp(usuarioErp)
        );
    }

    @Test
    @Transactional
    void fullUpdateUsuarioErpWithPatch() throws Exception {
        // Initialize the database
        insertedUsuarioErp = usuarioErpRepository.saveAndFlush(usuarioErp);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the usuarioErp using partial update
        UsuarioErp partialUpdatedUsuarioErp = new UsuarioErp();
        partialUpdatedUsuarioErp.setId(usuarioErp.getId());

        partialUpdatedUsuarioErp
            .email(UPDATED_EMAIL)
            .senha(UPDATED_SENHA)
            .dataHoraAtivacao(UPDATED_DATA_HORA_ATIVACAO)
            .dataLimiteAcesso(UPDATED_DATA_LIMITE_ACESSO)
            .situacao(UPDATED_SITUACAO);

        restUsuarioErpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsuarioErp.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUsuarioErp))
            )
            .andExpect(status().isOk());

        // Validate the UsuarioErp in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUsuarioErpUpdatableFieldsEquals(partialUpdatedUsuarioErp, getPersistedUsuarioErp(partialUpdatedUsuarioErp));
    }

    @Test
    @Transactional
    void patchNonExistingUsuarioErp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioErp.setId(longCount.incrementAndGet());

        // Create the UsuarioErp
        UsuarioErpDTO usuarioErpDTO = usuarioErpMapper.toDto(usuarioErp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioErpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, usuarioErpDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(usuarioErpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioErp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUsuarioErp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioErp.setId(longCount.incrementAndGet());

        // Create the UsuarioErp
        UsuarioErpDTO usuarioErpDTO = usuarioErpMapper.toDto(usuarioErp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioErpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(usuarioErpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioErp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUsuarioErp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioErp.setId(longCount.incrementAndGet());

        // Create the UsuarioErp
        UsuarioErpDTO usuarioErpDTO = usuarioErpMapper.toDto(usuarioErp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioErpMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(usuarioErpDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UsuarioErp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUsuarioErp() throws Exception {
        // Initialize the database
        insertedUsuarioErp = usuarioErpRepository.saveAndFlush(usuarioErp);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the usuarioErp
        restUsuarioErpMockMvc
            .perform(delete(ENTITY_API_URL_ID, usuarioErp.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return usuarioErpRepository.count();
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

    protected UsuarioErp getPersistedUsuarioErp(UsuarioErp usuarioErp) {
        return usuarioErpRepository.findById(usuarioErp.getId()).orElseThrow();
    }

    protected void assertPersistedUsuarioErpToMatchAllProperties(UsuarioErp expectedUsuarioErp) {
        assertUsuarioErpAllPropertiesEquals(expectedUsuarioErp, getPersistedUsuarioErp(expectedUsuarioErp));
    }

    protected void assertPersistedUsuarioErpToMatchUpdatableProperties(UsuarioErp expectedUsuarioErp) {
        assertUsuarioErpAllUpdatablePropertiesEquals(expectedUsuarioErp, getPersistedUsuarioErp(expectedUsuarioErp));
    }
}
