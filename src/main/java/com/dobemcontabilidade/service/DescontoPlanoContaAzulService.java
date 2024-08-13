package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.DescontoPlanoContaAzul;
import com.dobemcontabilidade.repository.DescontoPlanoContaAzulRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.DescontoPlanoContaAzul}.
 */
@Service
@Transactional
public class DescontoPlanoContaAzulService {

    private static final Logger log = LoggerFactory.getLogger(DescontoPlanoContaAzulService.class);

    private final DescontoPlanoContaAzulRepository descontoPlanoContaAzulRepository;

    public DescontoPlanoContaAzulService(DescontoPlanoContaAzulRepository descontoPlanoContaAzulRepository) {
        this.descontoPlanoContaAzulRepository = descontoPlanoContaAzulRepository;
    }

    /**
     * Save a descontoPlanoContaAzul.
     *
     * @param descontoPlanoContaAzul the entity to save.
     * @return the persisted entity.
     */
    public DescontoPlanoContaAzul save(DescontoPlanoContaAzul descontoPlanoContaAzul) {
        log.debug("Request to save DescontoPlanoContaAzul : {}", descontoPlanoContaAzul);
        return descontoPlanoContaAzulRepository.save(descontoPlanoContaAzul);
    }

    /**
     * Update a descontoPlanoContaAzul.
     *
     * @param descontoPlanoContaAzul the entity to save.
     * @return the persisted entity.
     */
    public DescontoPlanoContaAzul update(DescontoPlanoContaAzul descontoPlanoContaAzul) {
        log.debug("Request to update DescontoPlanoContaAzul : {}", descontoPlanoContaAzul);
        return descontoPlanoContaAzulRepository.save(descontoPlanoContaAzul);
    }

    /**
     * Partially update a descontoPlanoContaAzul.
     *
     * @param descontoPlanoContaAzul the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DescontoPlanoContaAzul> partialUpdate(DescontoPlanoContaAzul descontoPlanoContaAzul) {
        log.debug("Request to partially update DescontoPlanoContaAzul : {}", descontoPlanoContaAzul);

        return descontoPlanoContaAzulRepository
            .findById(descontoPlanoContaAzul.getId())
            .map(existingDescontoPlanoContaAzul -> {
                if (descontoPlanoContaAzul.getPercentual() != null) {
                    existingDescontoPlanoContaAzul.setPercentual(descontoPlanoContaAzul.getPercentual());
                }

                return existingDescontoPlanoContaAzul;
            })
            .map(descontoPlanoContaAzulRepository::save);
    }

    /**
     * Get all the descontoPlanoContaAzuls with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DescontoPlanoContaAzul> findAllWithEagerRelationships(Pageable pageable) {
        return descontoPlanoContaAzulRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one descontoPlanoContaAzul by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DescontoPlanoContaAzul> findOne(Long id) {
        log.debug("Request to get DescontoPlanoContaAzul : {}", id);
        return descontoPlanoContaAzulRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the descontoPlanoContaAzul by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DescontoPlanoContaAzul : {}", id);
        descontoPlanoContaAzulRepository.deleteById(id);
    }
}
