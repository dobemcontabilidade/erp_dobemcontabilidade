package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Email;
import com.dobemcontabilidade.repository.EmailRepository;
import com.dobemcontabilidade.service.dto.EmailDTO;
import com.dobemcontabilidade.service.mapper.EmailMapper;
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

    private final EmailMapper emailMapper;

    public EmailService(EmailRepository emailRepository, EmailMapper emailMapper) {
        this.emailRepository = emailRepository;
        this.emailMapper = emailMapper;
    }

    /**
     * Save a email.
     *
     * @param emailDTO the entity to save.
     * @return the persisted entity.
     */
    public EmailDTO save(EmailDTO emailDTO) {
        log.debug("Request to save Email : {}", emailDTO);
        Email email = emailMapper.toEntity(emailDTO);
        email = emailRepository.save(email);
        return emailMapper.toDto(email);
    }

    /**
     * Update a email.
     *
     * @param emailDTO the entity to save.
     * @return the persisted entity.
     */
    public EmailDTO update(EmailDTO emailDTO) {
        log.debug("Request to update Email : {}", emailDTO);
        Email email = emailMapper.toEntity(emailDTO);
        email = emailRepository.save(email);
        return emailMapper.toDto(email);
    }

    /**
     * Partially update a email.
     *
     * @param emailDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmailDTO> partialUpdate(EmailDTO emailDTO) {
        log.debug("Request to partially update Email : {}", emailDTO);

        return emailRepository
            .findById(emailDTO.getId())
            .map(existingEmail -> {
                emailMapper.partialUpdate(existingEmail, emailDTO);

                return existingEmail;
            })
            .map(emailRepository::save)
            .map(emailMapper::toDto);
    }

    /**
     * Get all the emails with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<EmailDTO> findAllWithEagerRelationships(Pageable pageable) {
        return emailRepository.findAllWithEagerRelationships(pageable).map(emailMapper::toDto);
    }

    /**
     * Get one email by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmailDTO> findOne(Long id) {
        log.debug("Request to get Email : {}", id);
        return emailRepository.findOneWithEagerRelationships(id).map(emailMapper::toDto);
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
