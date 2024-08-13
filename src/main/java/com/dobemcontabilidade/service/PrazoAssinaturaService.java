package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.PrazoAssinatura;
import com.dobemcontabilidade.repository.PrazoAssinaturaRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.PrazoAssinatura}.
 */
@Service
@Transactional
public class PrazoAssinaturaService {

    private static final Logger log = LoggerFactory.getLogger(PrazoAssinaturaService.class);

    private final PrazoAssinaturaRepository prazoAssinaturaRepository;

    public PrazoAssinaturaService(PrazoAssinaturaRepository prazoAssinaturaRepository) {
        this.prazoAssinaturaRepository = prazoAssinaturaRepository;
    }

    /**
     * Save a prazoAssinatura.
     *
     * @param prazoAssinatura the entity to save.
     * @return the persisted entity.
     */
    public PrazoAssinatura save(PrazoAssinatura prazoAssinatura) {
        log.debug("Request to save PrazoAssinatura : {}", prazoAssinatura);
        return prazoAssinaturaRepository.save(prazoAssinatura);
    }

    /**
     * Update a prazoAssinatura.
     *
     * @param prazoAssinatura the entity to save.
     * @return the persisted entity.
     */
    public PrazoAssinatura update(PrazoAssinatura prazoAssinatura) {
        log.debug("Request to update PrazoAssinatura : {}", prazoAssinatura);
        return prazoAssinaturaRepository.save(prazoAssinatura);
    }

    /**
     * Partially update a prazoAssinatura.
     *
     * @param prazoAssinatura the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PrazoAssinatura> partialUpdate(PrazoAssinatura prazoAssinatura) {
        log.debug("Request to partially update PrazoAssinatura : {}", prazoAssinatura);

        return prazoAssinaturaRepository
            .findById(prazoAssinatura.getId())
            .map(existingPrazoAssinatura -> {
                if (prazoAssinatura.getPrazo() != null) {
                    existingPrazoAssinatura.setPrazo(prazoAssinatura.getPrazo());
                }
                if (prazoAssinatura.getMeses() != null) {
                    existingPrazoAssinatura.setMeses(prazoAssinatura.getMeses());
                }

                return existingPrazoAssinatura;
            })
            .map(prazoAssinaturaRepository::save);
    }

    /**
     * Get one prazoAssinatura by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PrazoAssinatura> findOne(Long id) {
        log.debug("Request to get PrazoAssinatura : {}", id);
        return prazoAssinaturaRepository.findById(id);
    }

    /**
     * Delete the prazoAssinatura by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PrazoAssinatura : {}", id);
        prazoAssinaturaRepository.deleteById(id);
    }
}
