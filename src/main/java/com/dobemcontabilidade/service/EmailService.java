package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Email;
import com.dobemcontabilidade.repository.EmailRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Email}.
 */
@Service
@Transactional
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final EmailRepository emailRepository;

    public EmailService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    /**
     * Save a email.
     *
     * @param email the entity to save.
     * @return the persisted entity.
     */
    public Email save(Email email) {
        log.debug("Request to save Email : {}", email);
        return emailRepository.save(email);
    }

    /**
     * Update a email.
     *
     * @param email the entity to save.
     * @return the persisted entity.
     */
    public Email update(Email email) {
        log.debug("Request to update Email : {}", email);
        return emailRepository.save(email);
    }

    /**
     * Partially update a email.
     *
     * @param email the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Email> partialUpdate(Email email) {
        log.debug("Request to partially update Email : {}", email);

        return emailRepository
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
    }

    /**
     * Get all the emails with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Email> findAllWithEagerRelationships(Pageable pageable) {
        return emailRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one email by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Email> findOne(Long id) {
        log.debug("Request to get Email : {}", id);
        return emailRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the email by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Email : {}", id);
        emailRepository.deleteById(id);
    }
}
