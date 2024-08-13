package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AdicionalRamo;
import com.dobemcontabilidade.repository.AdicionalRamoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AdicionalRamo}.
 */
@Service
@Transactional
public class AdicionalRamoService {

    private static final Logger log = LoggerFactory.getLogger(AdicionalRamoService.class);

    private final AdicionalRamoRepository adicionalRamoRepository;

    public AdicionalRamoService(AdicionalRamoRepository adicionalRamoRepository) {
        this.adicionalRamoRepository = adicionalRamoRepository;
    }

    /**
     * Save a adicionalRamo.
     *
     * @param adicionalRamo the entity to save.
     * @return the persisted entity.
     */
    public AdicionalRamo save(AdicionalRamo adicionalRamo) {
        log.debug("Request to save AdicionalRamo : {}", adicionalRamo);
        return adicionalRamoRepository.save(adicionalRamo);
    }

    /**
     * Update a adicionalRamo.
     *
     * @param adicionalRamo the entity to save.
     * @return the persisted entity.
     */
    public AdicionalRamo update(AdicionalRamo adicionalRamo) {
        log.debug("Request to update AdicionalRamo : {}", adicionalRamo);
        return adicionalRamoRepository.save(adicionalRamo);
    }

    /**
     * Partially update a adicionalRamo.
     *
     * @param adicionalRamo the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AdicionalRamo> partialUpdate(AdicionalRamo adicionalRamo) {
        log.debug("Request to partially update AdicionalRamo : {}", adicionalRamo);

        return adicionalRamoRepository
            .findById(adicionalRamo.getId())
            .map(existingAdicionalRamo -> {
                if (adicionalRamo.getValor() != null) {
                    existingAdicionalRamo.setValor(adicionalRamo.getValor());
                }

                return existingAdicionalRamo;
            })
            .map(adicionalRamoRepository::save);
    }

    /**
     * Get all the adicionalRamos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AdicionalRamo> findAllWithEagerRelationships(Pageable pageable) {
        return adicionalRamoRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one adicionalRamo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdicionalRamo> findOne(Long id) {
        log.debug("Request to get AdicionalRamo : {}", id);
        return adicionalRamoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the adicionalRamo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdicionalRamo : {}", id);
        adicionalRamoRepository.deleteById(id);
    }
}
