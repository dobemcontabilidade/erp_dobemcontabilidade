package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.EmailAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Email;
import com.dobemcontabilidade.domain.Pessoa;
import com.dobemcontabilidade.repository.EmailRepository;
import com.dobemcontabilidade.service.EmailService;
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
 * Integration tests for the {@link EmailResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EmailResourceIT {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRINCIPAL = false;
    private static final Boolean UPDATED_PRINCIPAL = true;

    private static final String ENTITY_API_URL = "/api/emails";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EmailRepository emailRepository;

    @Mock
    private EmailRepository emailRepositoryMock;

    @Mock
    private EmailService emailServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmailMockMvc;

    private Email email;

    private Email insertedEmail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Email createEntity(EntityManager em) {
        Email email = new Email().email(DEFAULT_EMAIL).principal(DEFAULT_PRINCIPAL);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        email.setPessoa(pessoa);
        return email;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Email createUpdatedEntity(EntityManager em) {
        Email email = new Email().email(UPDATED_EMAIL).principal(UPDATED_PRINCIPAL);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createUpdatedEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        email.setPessoa(pessoa);
        return email;
    }

    @BeforeEach
    public void initTest() {
        email = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedEmail != null) {
            emailRepository.delete(insertedEmail);
            insertedEmail = null;
        }
    }

    @Test
    @Transactional
    void createEmail() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Email
        var returnedEmail = om.readValue(
            restEmailMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(email)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Email.class
        );

        // Validate the Email in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEmailUpdatableFieldsEquals(returnedEmail, getPersistedEmail(returnedEmail));

        insertedEmail = returnedEmail;
    }

    @Test
    @Transactional
    void createEmailWithExistingId() throws Exception {
        // Create the Email with an existing ID
        email.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(email)))
            .andExpect(status().isBadRequest());

        // Validate the Email in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        email.setEmail(null);

        // Create the Email, which fails.

        restEmailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(email)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmails() throws Exception {
        // Initialize the database
        insertedEmail = emailRepository.saveAndFlush(email);

        // Get all the emailList
        restEmailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(email.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].principal").value(hasItem(DEFAULT_PRINCIPAL.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEmailsWithEagerRelationshipsIsEnabled() throws Exception {
        when(emailServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEmailMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(emailServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEmailsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(emailServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEmailMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(emailRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEmail() throws Exception {
        // Initialize the database
        insertedEmail = emailRepository.saveAndFlush(email);

        // Get the email
        restEmailMockMvc
            .perform(get(ENTITY_API_URL_ID, email.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(email.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.principal").value(DEFAULT_PRINCIPAL.booleanValue()));
    }

    @Test
    @Transactional
    void getEmailsByIdFiltering() throws Exception {
        // Initialize the database
        insertedEmail = emailRepository.saveAndFlush(email);

        Long id = email.getId();

        defaultEmailFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultEmailFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultEmailFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmailsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmail = emailRepository.saveAndFlush(email);

        // Get all the emailList where email equals to
        defaultEmailFiltering("email.equals=" + DEFAULT_EMAIL, "email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmailsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmail = emailRepository.saveAndFlush(email);

        // Get all the emailList where email in
        defaultEmailFiltering("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL, "email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmailsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmail = emailRepository.saveAndFlush(email);

        // Get all the emailList where email is not null
        defaultEmailFiltering("email.specified=true", "email.specified=false");
    }

    @Test
    @Transactional
    void getAllEmailsByEmailContainsSomething() throws Exception {
        // Initialize the database
        insertedEmail = emailRepository.saveAndFlush(email);

        // Get all the emailList where email contains
        defaultEmailFiltering("email.contains=" + DEFAULT_EMAIL, "email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmailsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmail = emailRepository.saveAndFlush(email);

        // Get all the emailList where email does not contain
        defaultEmailFiltering("email.doesNotContain=" + UPDATED_EMAIL, "email.doesNotContain=" + DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmailsByPrincipalIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmail = emailRepository.saveAndFlush(email);

        // Get all the emailList where principal equals to
        defaultEmailFiltering("principal.equals=" + DEFAULT_PRINCIPAL, "principal.equals=" + UPDATED_PRINCIPAL);
    }

    @Test
    @Transactional
    void getAllEmailsByPrincipalIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmail = emailRepository.saveAndFlush(email);

        // Get all the emailList where principal in
        defaultEmailFiltering("principal.in=" + DEFAULT_PRINCIPAL + "," + UPDATED_PRINCIPAL, "principal.in=" + UPDATED_PRINCIPAL);
    }

    @Test
    @Transactional
    void getAllEmailsByPrincipalIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmail = emailRepository.saveAndFlush(email);

        // Get all the emailList where principal is not null
        defaultEmailFiltering("principal.specified=true", "principal.specified=false");
    }

    @Test
    @Transactional
    void getAllEmailsByPessoaIsEqualToSomething() throws Exception {
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            emailRepository.saveAndFlush(email);
            pessoa = PessoaResourceIT.createEntity(em);
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        em.persist(pessoa);
        em.flush();
        email.setPessoa(pessoa);
        emailRepository.saveAndFlush(email);
        Long pessoaId = pessoa.getId();
        // Get all the emailList where pessoa equals to pessoaId
        defaultEmailShouldBeFound("pessoaId.equals=" + pessoaId);

        // Get all the emailList where pessoa equals to (pessoaId + 1)
        defaultEmailShouldNotBeFound("pessoaId.equals=" + (pessoaId + 1));
    }

    private void defaultEmailFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultEmailShouldBeFound(shouldBeFound);
        defaultEmailShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmailShouldBeFound(String filter) throws Exception {
        restEmailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(email.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].principal").value(hasItem(DEFAULT_PRINCIPAL.booleanValue())));

        // Check, that the count call also returns 1
        restEmailMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmailShouldNotBeFound(String filter) throws Exception {
        restEmailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmailMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmail() throws Exception {
        // Get the email
        restEmailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmail() throws Exception {
        // Initialize the database
        insertedEmail = emailRepository.saveAndFlush(email);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the email
        Email updatedEmail = emailRepository.findById(email.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEmail are not directly saved in db
        em.detach(updatedEmail);
        updatedEmail.email(UPDATED_EMAIL).principal(UPDATED_PRINCIPAL);

        restEmailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmail.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEmail))
            )
            .andExpect(status().isOk());

        // Validate the Email in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEmailToMatchAllProperties(updatedEmail);
    }

    @Test
    @Transactional
    void putNonExistingEmail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        email.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailMockMvc
            .perform(put(ENTITY_API_URL_ID, email.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(email)))
            .andExpect(status().isBadRequest());

        // Validate the Email in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        email.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(email))
            )
            .andExpect(status().isBadRequest());

        // Validate the Email in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        email.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(email)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Email in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmailWithPatch() throws Exception {
        // Initialize the database
        insertedEmail = emailRepository.saveAndFlush(email);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the email using partial update
        Email partialUpdatedEmail = new Email();
        partialUpdatedEmail.setId(email.getId());

        partialUpdatedEmail.email(UPDATED_EMAIL);

        restEmailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmail.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmail))
            )
            .andExpect(status().isOk());

        // Validate the Email in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmailUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedEmail, email), getPersistedEmail(email));
    }

    @Test
    @Transactional
    void fullUpdateEmailWithPatch() throws Exception {
        // Initialize the database
        insertedEmail = emailRepository.saveAndFlush(email);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the email using partial update
        Email partialUpdatedEmail = new Email();
        partialUpdatedEmail.setId(email.getId());

        partialUpdatedEmail.email(UPDATED_EMAIL).principal(UPDATED_PRINCIPAL);

        restEmailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmail.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmail))
            )
            .andExpect(status().isOk());

        // Validate the Email in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmailUpdatableFieldsEquals(partialUpdatedEmail, getPersistedEmail(partialUpdatedEmail));
    }

    @Test
    @Transactional
    void patchNonExistingEmail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        email.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, email.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(email))
            )
            .andExpect(status().isBadRequest());

        // Validate the Email in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        email.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(email))
            )
            .andExpect(status().isBadRequest());

        // Validate the Email in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        email.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(email)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Email in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmail() throws Exception {
        // Initialize the database
        insertedEmail = emailRepository.saveAndFlush(email);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the email
        restEmailMockMvc
            .perform(delete(ENTITY_API_URL_ID, email.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return emailRepository.count();
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

    protected Email getPersistedEmail(Email email) {
        return emailRepository.findById(email.getId()).orElseThrow();
    }

    protected void assertPersistedEmailToMatchAllProperties(Email expectedEmail) {
        assertEmailAllPropertiesEquals(expectedEmail, getPersistedEmail(expectedEmail));
    }

    protected void assertPersistedEmailToMatchUpdatableProperties(Email expectedEmail) {
        assertEmailAllUpdatablePropertiesEquals(expectedEmail, getPersistedEmail(expectedEmail));
    }
}
