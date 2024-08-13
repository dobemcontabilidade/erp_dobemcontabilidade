package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.ValorBaseRamo;
import com.dobemcontabilidade.repository.ValorBaseRamoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.ValorBaseRamo}.
 */
@Service
@Transactional
public class ValorBaseRamoService {

    private static final Logger log = LoggerFactory.getLogger(ValorBaseRamoService.class);

    private final ValorBaseRamoRepository valorBaseRamoRepository;

    public ValorBaseRamoService(ValorBaseRamoRepository valorBaseRamoRepository) {
        this.valorBaseRamoRepository = valorBaseRamoRepository;
    }

    /**
     * Save a valorBaseRamo.
     *
     * @param valorBaseRamo the entity to save.
     * @return the persisted entity.
     */
    public ValorBaseRamo save(ValorBaseRamo valorBaseRamo) {
        log.debug("Request to save ValorBaseRamo : {}", valorBaseRamo);
        return valorBaseRamoRepository.save(valorBaseRamo);
    }

    /**
     * Update a valorBaseRamo.
     *
     * @param valorBaseRamo the entity to save.
     * @return the persisted entity.
     */
    public ValorBaseRamo update(ValorBaseRamo valorBaseRamo) {
        log.debug("Request to update ValorBaseRamo : {}", valorBaseRamo);
        return valorBaseRamoRepository.save(valorBaseRamo);
    }

    /**
     * Partially update a valorBaseRamo.
     *
     * @param valorBaseRamo the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ValorBaseRamo> partialUpdate(ValorBaseRamo valorBaseRamo) {
        log.debug("Request to partially update ValorBaseRamo : {}", valorBaseRamo);

        return valorBaseRamoRepository
            .findById(valorBaseRamo.getId())
            .map(existingValorBaseRamo -> {
                if (valorBaseRamo.getValorBase() != null) {
                    existingValorBaseRamo.setValorBase(valorBaseRamo.getValorBase());
                }

                return existingValorBaseRamo;
            })
            .map(valorBaseRamoRepository::save);
    }

    /**
     * Get all the valorBaseRamos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ValorBaseRamo> findAllWithEagerRelationships(Pageable pageable) {
        return valorBaseRamoRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one valorBaseRamo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ValorBaseRamo> findOne(Long id) {
        log.debug("Request to get ValorBaseRamo : {}", id);
        return valorBaseRamoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the valorBaseRamo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ValorBaseRamo : {}", id);
        valorBaseRamoRepository.deleteById(id);
    }
}
