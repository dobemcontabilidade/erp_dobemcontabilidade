package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.DescontoPlanoContabil;
import com.dobemcontabilidade.repository.DescontoPlanoContabilRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.DescontoPlanoContabil}.
 */
@Service
@Transactional
public class DescontoPlanoContabilService {

    private static final Logger log = LoggerFactory.getLogger(DescontoPlanoContabilService.class);

    private final DescontoPlanoContabilRepository descontoPlanoContabilRepository;

    public DescontoPlanoContabilService(DescontoPlanoContabilRepository descontoPlanoContabilRepository) {
        this.descontoPlanoContabilRepository = descontoPlanoContabilRepository;
    }

    /**
     * Save a descontoPlanoContabil.
     *
     * @param descontoPlanoContabil the entity to save.
     * @return the persisted entity.
     */
    public DescontoPlanoContabil save(DescontoPlanoContabil descontoPlanoContabil) {
        log.debug("Request to save DescontoPlanoContabil : {}", descontoPlanoContabil);
        return descontoPlanoContabilRepository.save(descontoPlanoContabil);
    }

    /**
     * Update a descontoPlanoContabil.
     *
     * @param descontoPlanoContabil the entity to save.
     * @return the persisted entity.
     */
    public DescontoPlanoContabil update(DescontoPlanoContabil descontoPlanoContabil) {
        log.debug("Request to update DescontoPlanoContabil : {}", descontoPlanoContabil);
        return descontoPlanoContabilRepository.save(descontoPlanoContabil);
    }

    /**
     * Partially update a descontoPlanoContabil.
     *
     * @param descontoPlanoContabil the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DescontoPlanoContabil> partialUpdate(DescontoPlanoContabil descontoPlanoContabil) {
        log.debug("Request to partially update DescontoPlanoContabil : {}", descontoPlanoContabil);

        return descontoPlanoContabilRepository
            .findById(descontoPlanoContabil.getId())
            .map(existingDescontoPlanoContabil -> {
                if (descontoPlanoContabil.getPercentual() != null) {
                    existingDescontoPlanoContabil.setPercentual(descontoPlanoContabil.getPercentual());
                }

                return existingDescontoPlanoContabil;
            })
            .map(descontoPlanoContabilRepository::save);
    }

    /**
     * Get all the descontoPlanoContabils with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DescontoPlanoContabil> findAllWithEagerRelationships(Pageable pageable) {
        return descontoPlanoContabilRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one descontoPlanoContabil by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DescontoPlanoContabil> findOne(Long id) {
        log.debug("Request to get DescontoPlanoContabil : {}", id);
        return descontoPlanoContabilRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the descontoPlanoContabil by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DescontoPlanoContabil : {}", id);
        descontoPlanoContabilRepository.deleteById(id);
    }
}
