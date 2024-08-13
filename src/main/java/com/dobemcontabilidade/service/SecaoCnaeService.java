package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.SecaoCnae;
import com.dobemcontabilidade.repository.SecaoCnaeRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.SecaoCnae}.
 */
@Service
@Transactional
public class SecaoCnaeService {

    private static final Logger log = LoggerFactory.getLogger(SecaoCnaeService.class);

    private final SecaoCnaeRepository secaoCnaeRepository;

    public SecaoCnaeService(SecaoCnaeRepository secaoCnaeRepository) {
        this.secaoCnaeRepository = secaoCnaeRepository;
    }

    /**
     * Save a secaoCnae.
     *
     * @param secaoCnae the entity to save.
     * @return the persisted entity.
     */
    public SecaoCnae save(SecaoCnae secaoCnae) {
        log.debug("Request to save SecaoCnae : {}", secaoCnae);
        return secaoCnaeRepository.save(secaoCnae);
    }

    /**
     * Update a secaoCnae.
     *
     * @param secaoCnae the entity to save.
     * @return the persisted entity.
     */
    public SecaoCnae update(SecaoCnae secaoCnae) {
        log.debug("Request to update SecaoCnae : {}", secaoCnae);
        return secaoCnaeRepository.save(secaoCnae);
    }

    /**
     * Partially update a secaoCnae.
     *
     * @param secaoCnae the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SecaoCnae> partialUpdate(SecaoCnae secaoCnae) {
        log.debug("Request to partially update SecaoCnae : {}", secaoCnae);

        return secaoCnaeRepository
            .findById(secaoCnae.getId())
            .map(existingSecaoCnae -> {
                if (secaoCnae.getCodigo() != null) {
                    existingSecaoCnae.setCodigo(secaoCnae.getCodigo());
                }
                if (secaoCnae.getDescricao() != null) {
                    existingSecaoCnae.setDescricao(secaoCnae.getDescricao());
                }

                return existingSecaoCnae;
            })
            .map(secaoCnaeRepository::save);
    }

    /**
     * Get one secaoCnae by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SecaoCnae> findOne(Long id) {
        log.debug("Request to get SecaoCnae : {}", id);
        return secaoCnaeRepository.findById(id);
    }

    /**
     * Delete the secaoCnae by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SecaoCnae : {}", id);
        secaoCnaeRepository.deleteById(id);
    }
}
