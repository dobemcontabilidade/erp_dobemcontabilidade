package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.RedeSocialAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.RedeSocial;
import com.dobemcontabilidade.repository.RedeSocialRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RedeSocialResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RedeSocialResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO = "AAAAAAAAAA";
    private static final String UPDATED_LOGO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rede-socials";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RedeSocialRepository redeSocialRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRedeSocialMockMvc;

    private RedeSocial redeSocial;

    private RedeSocial insertedRedeSocial;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RedeSocial createEntity(EntityManager em) {
        RedeSocial redeSocial = new RedeSocial().nome(DEFAULT_NOME).descricao(DEFAULT_DESCRICAO).url(DEFAULT_URL).logo(DEFAULT_LOGO);
        return redeSocial;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RedeSocial createUpdatedEntity(EntityManager em) {
        RedeSocial redeSocial = new RedeSocial().nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).url(UPDATED_URL).logo(UPDATED_LOGO);
        return redeSocial;
    }

    @BeforeEach
    public void initTest() {
        redeSocial = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedRedeSocial != null) {
            redeSocialRepository.delete(insertedRedeSocial);
            insertedRedeSocial = null;
        }
    }

    @Test
    @Transactional
    void createRedeSocial() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RedeSocial
        var returnedRedeSocial = om.readValue(
            restRedeSocialMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(redeSocial))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RedeSocial.class
        );

        // Validate the RedeSocial in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRedeSocialUpdatableFieldsEquals(returnedRedeSocial, getPersistedRedeSocial(returnedRedeSocial));

        insertedRedeSocial = returnedRedeSocial;
    }

    @Test
    @Transactional
    void createRedeSocialWithExistingId() throws Exception {
        // Create the RedeSocial with an existing ID
        redeSocial.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRedeSocialMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(redeSocial)))
            .andExpect(status().isBadRequest());

        // Validate the RedeSocial in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        redeSocial.setNome(null);

        // Create the RedeSocial, which fails.

        restRedeSocialMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(redeSocial)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRedeSocials() throws Exception {
        // Initialize the database
        insertedRedeSocial = redeSocialRepository.saveAndFlush(redeSocial);

        // Get all the redeSocialList
        restRedeSocialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(redeSocial.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO)));
    }

    @Test
    @Transactional
    void getRedeSocial() throws Exception {
        // Initialize the database
        insertedRedeSocial = redeSocialRepository.saveAndFlush(redeSocial);

        // Get the redeSocial
        restRedeSocialMockMvc
            .perform(get(ENTITY_API_URL_ID, redeSocial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(redeSocial.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.logo").value(DEFAULT_LOGO));
    }

    @Test
    @Transactional
    void getNonExistingRedeSocial() throws Exception {
        // Get the redeSocial
        restRedeSocialMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRedeSocial() throws Exception {
        // Initialize the database
        insertedRedeSocial = redeSocialRepository.saveAndFlush(redeSocial);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the redeSocial
        RedeSocial updatedRedeSocial = redeSocialRepository.findById(redeSocial.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRedeSocial are not directly saved in db
        em.detach(updatedRedeSocial);
        updatedRedeSocial.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).url(UPDATED_URL).logo(UPDATED_LOGO);

        restRedeSocialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRedeSocial.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRedeSocial))
            )
            .andExpect(status().isOk());

        // Validate the RedeSocial in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRedeSocialToMatchAllProperties(updatedRedeSocial);
    }

    @Test
    @Transactional
    void putNonExistingRedeSocial() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redeSocial.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRedeSocialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, redeSocial.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(redeSocial))
            )
            .andExpect(status().isBadRequest());

        // Validate the RedeSocial in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRedeSocial() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redeSocial.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRedeSocialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(redeSocial))
            )
            .andExpect(status().isBadRequest());

        // Validate the RedeSocial in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRedeSocial() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redeSocial.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRedeSocialMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(redeSocial)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RedeSocial in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRedeSocialWithPatch() throws Exception {
        // Initialize the database
        insertedRedeSocial = redeSocialRepository.saveAndFlush(redeSocial);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the redeSocial using partial update
        RedeSocial partialUpdatedRedeSocial = new RedeSocial();
        partialUpdatedRedeSocial.setId(redeSocial.getId());

        partialUpdatedRedeSocial.descricao(UPDATED_DESCRICAO).url(UPDATED_URL).logo(UPDATED_LOGO);

        restRedeSocialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRedeSocial.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRedeSocial))
            )
            .andExpect(status().isOk());

        // Validate the RedeSocial in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRedeSocialUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRedeSocial, redeSocial),
            getPersistedRedeSocial(redeSocial)
        );
    }

    @Test
    @Transactional
    void fullUpdateRedeSocialWithPatch() throws Exception {
        // Initialize the database
        insertedRedeSocial = redeSocialRepository.saveAndFlush(redeSocial);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the redeSocial using partial update
        RedeSocial partialUpdatedRedeSocial = new RedeSocial();
        partialUpdatedRedeSocial.setId(redeSocial.getId());

        partialUpdatedRedeSocial.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).url(UPDATED_URL).logo(UPDATED_LOGO);

        restRedeSocialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRedeSocial.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRedeSocial))
            )
            .andExpect(status().isOk());

        // Validate the RedeSocial in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRedeSocialUpdatableFieldsEquals(partialUpdatedRedeSocial, getPersistedRedeSocial(partialUpdatedRedeSocial));
    }

    @Test
    @Transactional
    void patchNonExistingRedeSocial() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redeSocial.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRedeSocialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, redeSocial.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(redeSocial))
            )
            .andExpect(status().isBadRequest());

        // Validate the RedeSocial in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRedeSocial() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redeSocial.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRedeSocialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(redeSocial))
            )
            .andExpect(status().isBadRequest());

        // Validate the RedeSocial in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRedeSocial() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redeSocial.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRedeSocialMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(redeSocial))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RedeSocial in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRedeSocial() throws Exception {
        // Initialize the database
        insertedRedeSocial = redeSocialRepository.saveAndFlush(redeSocial);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the redeSocial
        restRedeSocialMockMvc
            .perform(delete(ENTITY_API_URL_ID, redeSocial.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return redeSocialRepository.count();
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

    protected RedeSocial getPersistedRedeSocial(RedeSocial redeSocial) {
        return redeSocialRepository.findById(redeSocial.getId()).orElseThrow();
    }

    protected void assertPersistedRedeSocialToMatchAllProperties(RedeSocial expectedRedeSocial) {
        assertRedeSocialAllPropertiesEquals(expectedRedeSocial, getPersistedRedeSocial(expectedRedeSocial));
    }

    protected void assertPersistedRedeSocialToMatchUpdatableProperties(RedeSocial expectedRedeSocial) {
        assertRedeSocialAllUpdatablePropertiesEquals(expectedRedeSocial, getPersistedRedeSocial(expectedRedeSocial));
    }
}
