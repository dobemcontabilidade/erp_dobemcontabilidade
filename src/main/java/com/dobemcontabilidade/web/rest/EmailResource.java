package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.Email;
import com.dobemcontabilidade.repository.EmailRepository;
import com.dobemcontabilidade.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dobemcontabilidade.domain.Email}.
 */
@RestController
@RequestMapping("/api/emails")
@Transactional
public class EmailResource {

    private static final Logger log = LoggerFactory.getLogger(EmailResource.class);

    private static final String ENTITY_NAME = "email";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmailRepository emailRepository;

    public EmailResource(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    /**
     * {@code POST  /emails} : Create a new email.
     *
     * @param email the email to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new email, or with status {@code 400 (Bad Request)} if the email has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Email> createEmail(@Valid @RequestBody Email email) throws URISyntaxException {
        log.debug("REST request to save Email : {}", email);
        if (email.getId() != null) {
            throw new BadRequestAlertException("A new email cannot already have an ID", ENTITY_NAME, "idexists");
        }
        email = emailRepository.save(email);
        return ResponseEntity.created(new URI("/api/emails/" + email.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, email.getId().toString()))
            .body(email);
    }

    /**
     * {@code PUT  /emails/:id} : Updates an existing email.
     *
     * @param id the id of the email to save.
     * @param email the email to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated email,
     * or with status {@code 400 (Bad Request)} if the email is not valid,
     * or with status {@code 500 (Internal Server Error)} if the email couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Email> updateEmail(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Email email)
        throws URISyntaxException {
        log.debug("REST request to update Email : {}, {}", id, email);
        if (email.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, email.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!emailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        email = emailRepository.save(email);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, email.getId().toString()))
            .body(email);
    }

    /**
     * {@code PATCH  /emails/:id} : Partial updates given fields of an existing email, field will ignore if it is null
     *
     * @param id the id of the email to save.
     * @param email the email to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated email,
     * or with status {@code 400 (Bad Request)} if the email is not valid,
     * or with status {@code 404 (Not Found)} if the email is not found,
     * or with status {@code 500 (Internal Server Error)} if the email couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Email> partialUpdateEmail(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Email email
    ) throws URISyntaxException {
        log.debug("REST request to partial update Email partially : {}, {}", id, email);
        if (email.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, email.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!emailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Email> result = emailRepository
            .findById(email.getId())
            .map(existingEmail -> {
                if (email.getEmail() != null) {
                    existingEmail.setEmail(email.getEmail());
                }
                if (email.getPrincipal() != null) {
                    existingEmail.setPrincipal(email.getPrincipal());
                }

                return existingEmail;
            })
            .map(emailRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, email.getId().toString())
        );
    }

    /**
     * {@code GET  /emails} : get all the emails.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emails in body.
     */
    @GetMapping("")
    public List<Email> getAllEmails(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Emails");
        if (eagerload) {
            return emailRepository.findAllWithEagerRelationships();
        } else {
            return emailRepository.findAll();
        }
    }

    /**
     * {@code GET  /emails/:id} : get the "id" email.
     *
     * @param id the id of the email to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the email, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Email> getEmail(@PathVariable("id") Long id) {
        log.debug("REST request to get Email : {}", id);
        Optional<Email> email = emailRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(email);
    }

    /**
     * {@code DELETE  /emails/:id} : delete the "id" email.
     *
     * @param id the id of the email to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmail(@PathVariable("id") Long id) {
        log.debug("REST request to delete Email : {}", id);
        emailRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
