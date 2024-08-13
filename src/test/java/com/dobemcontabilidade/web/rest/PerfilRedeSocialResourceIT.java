package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.PerfilRedeSocialAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.PerfilRedeSocial;
import com.dobemcontabilidade.domain.enumeration.TipoRedeEnum;
import com.dobemcontabilidade.repository.PerfilRedeSocialRepository;
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
 * Integration tests for the {@link PerfilRedeSocialResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PerfilRedeSocialResourceIT {

    private static final String DEFAULT_REDE = "AAAAAAAAAA";
    private static final String UPDATED_REDE = "BBBBBBBBBB";

    private static final String DEFAULT_URL_PERFIL = "AAAAAAAAAA";
    private static final String UPDATED_URL_PERFIL = "BBBBBBBBBB";

    private static final TipoRedeEnum DEFAULT_TIPO_REDE = TipoRedeEnum.FACEBOOK;
    private static final TipoRedeEnum UPDATED_TIPO_REDE = TipoRedeEnum.TWITTER;

    private static final String ENTITY_API_URL = "/api/perfil-rede-socials";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PerfilRedeSocialRepository perfilRedeSocialRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPerfilRedeSocialMockMvc;

    private PerfilRedeSocial perfilRedeSocial;

    private PerfilRedeSocial insertedPerfilRedeSocial;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerfilRedeSocial createEntity(EntityManager em) {
        PerfilRedeSocial perfilRedeSocial = new PerfilRedeSocial()
            .rede(DEFAULT_REDE)
            .urlPerfil(DEFAULT_URL_PERFIL)
            .tipoRede(DEFAULT_TIPO_REDE);
        return perfilRedeSocial;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerfilRedeSocial createUpdatedEntity(EntityManager em) {
        PerfilRedeSocial perfilRedeSocial = new PerfilRedeSocial()
            .rede(UPDATED_REDE)
            .urlPerfil(UPDATED_URL_PERFIL)
            .tipoRede(UPDATED_TIPO_REDE);
        return perfilRedeSocial;
    }

    @BeforeEach
    public void initTest() {
        perfilRedeSocial = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPerfilRedeSocial != null) {
            perfilRedeSocialRepository.delete(insertedPerfilRedeSocial);
            insertedPerfilRedeSocial = null;
        }
    }

    @Test
    @Transactional
    void createPerfilRedeSocial() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PerfilRedeSocial
        var returnedPerfilRedeSocial = om.readValue(
            restPerfilRedeSocialMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(perfilRedeSocial)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PerfilRedeSocial.class
        );

        // Validate the PerfilRedeSocial in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPerfilRedeSocialUpdatableFieldsEquals(returnedPerfilRedeSocial, getPersistedPerfilRedeSocial(returnedPerfilRedeSocial));

        insertedPerfilRedeSocial = returnedPerfilRedeSocial;
    }

    @Test
    @Transactional
    void createPerfilRedeSocialWithExistingId() throws Exception {
        // Create the PerfilRedeSocial with an existing ID
        perfilRedeSocial.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerfilRedeSocialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(perfilRedeSocial)))
            .andExpect(status().isBadRequest());

        // Validate the PerfilRedeSocial in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRedeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        perfilRedeSocial.setRede(null);

        // Create the PerfilRedeSocial, which fails.

        restPerfilRedeSocialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(perfilRedeSocial)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUrlPerfilIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        perfilRedeSocial.setUrlPerfil(null);

        // Create the PerfilRedeSocial, which fails.

        restPerfilRedeSocialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(perfilRedeSocial)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPerfilRedeSocials() throws Exception {
        // Initialize the database
        insertedPerfilRedeSocial = perfilRedeSocialRepository.saveAndFlush(perfilRedeSocial);

        // Get all the perfilRedeSocialList
        restPerfilRedeSocialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfilRedeSocial.getId().intValue())))
            .andExpect(jsonPath("$.[*].rede").value(hasItem(DEFAULT_REDE)))
            .andExpect(jsonPath("$.[*].urlPerfil").value(hasItem(DEFAULT_URL_PERFIL)))
            .andExpect(jsonPath("$.[*].tipoRede").value(hasItem(DEFAULT_TIPO_REDE.toString())));
    }

    @Test
    @Transactional
    void getPerfilRedeSocial() throws Exception {
        // Initialize the database
        insertedPerfilRedeSocial = perfilRedeSocialRepository.saveAndFlush(perfilRedeSocial);

        // Get the perfilRedeSocial
        restPerfilRedeSocialMockMvc
            .perform(get(ENTITY_API_URL_ID, perfilRedeSocial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(perfilRedeSocial.getId().intValue()))
            .andExpect(jsonPath("$.rede").value(DEFAULT_REDE))
            .andExpect(jsonPath("$.urlPerfil").value(DEFAULT_URL_PERFIL))
            .andExpect(jsonPath("$.tipoRede").value(DEFAULT_TIPO_REDE.toString()));
    }

    @Test
    @Transactional
    void getPerfilRedeSocialsByIdFiltering() throws Exception {
        // Initialize the database
        insertedPerfilRedeSocial = perfilRedeSocialRepository.saveAndFlush(perfilRedeSocial);

        Long id = perfilRedeSocial.getId();

        defaultPerfilRedeSocialFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultPerfilRedeSocialFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultPerfilRedeSocialFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPerfilRedeSocialsByRedeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPerfilRedeSocial = perfilRedeSocialRepository.saveAndFlush(perfilRedeSocial);

        // Get all the perfilRedeSocialList where rede equals to
        defaultPerfilRedeSocialFiltering("rede.equals=" + DEFAULT_REDE, "rede.equals=" + UPDATED_REDE);
    }

    @Test
    @Transactional
    void getAllPerfilRedeSocialsByRedeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPerfilRedeSocial = perfilRedeSocialRepository.saveAndFlush(perfilRedeSocial);

        // Get all the perfilRedeSocialList where rede in
        defaultPerfilRedeSocialFiltering("rede.in=" + DEFAULT_REDE + "," + UPDATED_REDE, "rede.in=" + UPDATED_REDE);
    }

    @Test
    @Transactional
    void getAllPerfilRedeSocialsByRedeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPerfilRedeSocial = perfilRedeSocialRepository.saveAndFlush(perfilRedeSocial);

        // Get all the perfilRedeSocialList where rede is not null
        defaultPerfilRedeSocialFiltering("rede.specified=true", "rede.specified=false");
    }

    @Test
    @Transactional
    void getAllPerfilRedeSocialsByRedeContainsSomething() throws Exception {
        // Initialize the database
        insertedPerfilRedeSocial = perfilRedeSocialRepository.saveAndFlush(perfilRedeSocial);

        // Get all the perfilRedeSocialList where rede contains
        defaultPerfilRedeSocialFiltering("rede.contains=" + DEFAULT_REDE, "rede.contains=" + UPDATED_REDE);
    }

    @Test
    @Transactional
    void getAllPerfilRedeSocialsByRedeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPerfilRedeSocial = perfilRedeSocialRepository.saveAndFlush(perfilRedeSocial);

        // Get all the perfilRedeSocialList where rede does not contain
        defaultPerfilRedeSocialFiltering("rede.doesNotContain=" + UPDATED_REDE, "rede.doesNotContain=" + DEFAULT_REDE);
    }

    @Test
    @Transactional
    void getAllPerfilRedeSocialsByUrlPerfilIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPerfilRedeSocial = perfilRedeSocialRepository.saveAndFlush(perfilRedeSocial);

        // Get all the perfilRedeSocialList where urlPerfil equals to
        defaultPerfilRedeSocialFiltering("urlPerfil.equals=" + DEFAULT_URL_PERFIL, "urlPerfil.equals=" + UPDATED_URL_PERFIL);
    }

    @Test
    @Transactional
    void getAllPerfilRedeSocialsByUrlPerfilIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPerfilRedeSocial = perfilRedeSocialRepository.saveAndFlush(perfilRedeSocial);

        // Get all the perfilRedeSocialList where urlPerfil in
        defaultPerfilRedeSocialFiltering(
            "urlPerfil.in=" + DEFAULT_URL_PERFIL + "," + UPDATED_URL_PERFIL,
            "urlPerfil.in=" + UPDATED_URL_PERFIL
        );
    }

    @Test
    @Transactional
    void getAllPerfilRedeSocialsByUrlPerfilIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPerfilRedeSocial = perfilRedeSocialRepository.saveAndFlush(perfilRedeSocial);

        // Get all the perfilRedeSocialList where urlPerfil is not null
        defaultPerfilRedeSocialFiltering("urlPerfil.specified=true", "urlPerfil.specified=false");
    }

    @Test
    @Transactional
    void getAllPerfilRedeSocialsByUrlPerfilContainsSomething() throws Exception {
        // Initialize the database
        insertedPerfilRedeSocial = perfilRedeSocialRepository.saveAndFlush(perfilRedeSocial);

        // Get all the perfilRedeSocialList where urlPerfil contains
        defaultPerfilRedeSocialFiltering("urlPerfil.contains=" + DEFAULT_URL_PERFIL, "urlPerfil.contains=" + UPDATED_URL_PERFIL);
    }

    @Test
    @Transactional
    void getAllPerfilRedeSocialsByUrlPerfilNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPerfilRedeSocial = perfilRedeSocialRepository.saveAndFlush(perfilRedeSocial);

        // Get all the perfilRedeSocialList where urlPerfil does not contain
        defaultPerfilRedeSocialFiltering(
            "urlPerfil.doesNotContain=" + UPDATED_URL_PERFIL,
            "urlPerfil.doesNotContain=" + DEFAULT_URL_PERFIL
        );
    }

    @Test
    @Transactional
    void getAllPerfilRedeSocialsByTipoRedeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPerfilRedeSocial = perfilRedeSocialRepository.saveAndFlush(perfilRedeSocial);

        // Get all the perfilRedeSocialList where tipoRede equals to
        defaultPerfilRedeSocialFiltering("tipoRede.equals=" + DEFAULT_TIPO_REDE, "tipoRede.equals=" + UPDATED_TIPO_REDE);
    }

    @Test
    @Transactional
    void getAllPerfilRedeSocialsByTipoRedeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPerfilRedeSocial = perfilRedeSocialRepository.saveAndFlush(perfilRedeSocial);

        // Get all the perfilRedeSocialList where tipoRede in
        defaultPerfilRedeSocialFiltering("tipoRede.in=" + DEFAULT_TIPO_REDE + "," + UPDATED_TIPO_REDE, "tipoRede.in=" + UPDATED_TIPO_REDE);
    }

    @Test
    @Transactional
    void getAllPerfilRedeSocialsByTipoRedeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPerfilRedeSocial = perfilRedeSocialRepository.saveAndFlush(perfilRedeSocial);

        // Get all the perfilRedeSocialList where tipoRede is not null
        defaultPerfilRedeSocialFiltering("tipoRede.specified=true", "tipoRede.specified=false");
    }

    private void defaultPerfilRedeSocialFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultPerfilRedeSocialShouldBeFound(shouldBeFound);
        defaultPerfilRedeSocialShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPerfilRedeSocialShouldBeFound(String filter) throws Exception {
        restPerfilRedeSocialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfilRedeSocial.getId().intValue())))
            .andExpect(jsonPath("$.[*].rede").value(hasItem(DEFAULT_REDE)))
            .andExpect(jsonPath("$.[*].urlPerfil").value(hasItem(DEFAULT_URL_PERFIL)))
            .andExpect(jsonPath("$.[*].tipoRede").value(hasItem(DEFAULT_TIPO_REDE.toString())));

        // Check, that the count call also returns 1
        restPerfilRedeSocialMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPerfilRedeSocialShouldNotBeFound(String filter) throws Exception {
        restPerfilRedeSocialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPerfilRedeSocialMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPerfilRedeSocial() throws Exception {
        // Get the perfilRedeSocial
        restPerfilRedeSocialMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPerfilRedeSocial() throws Exception {
        // Initialize the database
        insertedPerfilRedeSocial = perfilRedeSocialRepository.saveAndFlush(perfilRedeSocial);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the perfilRedeSocial
        PerfilRedeSocial updatedPerfilRedeSocial = perfilRedeSocialRepository.findById(perfilRedeSocial.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPerfilRedeSocial are not directly saved in db
        em.detach(updatedPerfilRedeSocial);
        updatedPerfilRedeSocial.rede(UPDATED_REDE).urlPerfil(UPDATED_URL_PERFIL).tipoRede(UPDATED_TIPO_REDE);

        restPerfilRedeSocialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPerfilRedeSocial.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPerfilRedeSocial))
            )
            .andExpect(status().isOk());

        // Validate the PerfilRedeSocial in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPerfilRedeSocialToMatchAllProperties(updatedPerfilRedeSocial);
    }

    @Test
    @Transactional
    void putNonExistingPerfilRedeSocial() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilRedeSocial.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerfilRedeSocialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, perfilRedeSocial.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(perfilRedeSocial))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerfilRedeSocial in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPerfilRedeSocial() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilRedeSocial.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerfilRedeSocialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(perfilRedeSocial))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerfilRedeSocial in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPerfilRedeSocial() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilRedeSocial.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerfilRedeSocialMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(perfilRedeSocial)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PerfilRedeSocial in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePerfilRedeSocialWithPatch() throws Exception {
        // Initialize the database
        insertedPerfilRedeSocial = perfilRedeSocialRepository.saveAndFlush(perfilRedeSocial);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the perfilRedeSocial using partial update
        PerfilRedeSocial partialUpdatedPerfilRedeSocial = new PerfilRedeSocial();
        partialUpdatedPerfilRedeSocial.setId(perfilRedeSocial.getId());

        restPerfilRedeSocialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerfilRedeSocial.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPerfilRedeSocial))
            )
            .andExpect(status().isOk());

        // Validate the PerfilRedeSocial in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPerfilRedeSocialUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPerfilRedeSocial, perfilRedeSocial),
            getPersistedPerfilRedeSocial(perfilRedeSocial)
        );
    }

    @Test
    @Transactional
    void fullUpdatePerfilRedeSocialWithPatch() throws Exception {
        // Initialize the database
        insertedPerfilRedeSocial = perfilRedeSocialRepository.saveAndFlush(perfilRedeSocial);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the perfilRedeSocial using partial update
        PerfilRedeSocial partialUpdatedPerfilRedeSocial = new PerfilRedeSocial();
        partialUpdatedPerfilRedeSocial.setId(perfilRedeSocial.getId());

        partialUpdatedPerfilRedeSocial.rede(UPDATED_REDE).urlPerfil(UPDATED_URL_PERFIL).tipoRede(UPDATED_TIPO_REDE);

        restPerfilRedeSocialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerfilRedeSocial.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPerfilRedeSocial))
            )
            .andExpect(status().isOk());

        // Validate the PerfilRedeSocial in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPerfilRedeSocialUpdatableFieldsEquals(
            partialUpdatedPerfilRedeSocial,
            getPersistedPerfilRedeSocial(partialUpdatedPerfilRedeSocial)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPerfilRedeSocial() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilRedeSocial.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerfilRedeSocialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, perfilRedeSocial.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(perfilRedeSocial))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerfilRedeSocial in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPerfilRedeSocial() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilRedeSocial.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerfilRedeSocialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(perfilRedeSocial))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerfilRedeSocial in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPerfilRedeSocial() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilRedeSocial.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerfilRedeSocialMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(perfilRedeSocial)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PerfilRedeSocial in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePerfilRedeSocial() throws Exception {
        // Initialize the database
        insertedPerfilRedeSocial = perfilRedeSocialRepository.saveAndFlush(perfilRedeSocial);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the perfilRedeSocial
        restPerfilRedeSocialMockMvc
            .perform(delete(ENTITY_API_URL_ID, perfilRedeSocial.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return perfilRedeSocialRepository.count();
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

    protected PerfilRedeSocial getPersistedPerfilRedeSocial(PerfilRedeSocial perfilRedeSocial) {
        return perfilRedeSocialRepository.findById(perfilRedeSocial.getId()).orElseThrow();
    }

    protected void assertPersistedPerfilRedeSocialToMatchAllProperties(PerfilRedeSocial expectedPerfilRedeSocial) {
        assertPerfilRedeSocialAllPropertiesEquals(expectedPerfilRedeSocial, getPersistedPerfilRedeSocial(expectedPerfilRedeSocial));
    }

    protected void assertPersistedPerfilRedeSocialToMatchUpdatableProperties(PerfilRedeSocial expectedPerfilRedeSocial) {
        assertPerfilRedeSocialAllUpdatablePropertiesEquals(
            expectedPerfilRedeSocial,
            getPersistedPerfilRedeSocial(expectedPerfilRedeSocial)
        );
    }
}
