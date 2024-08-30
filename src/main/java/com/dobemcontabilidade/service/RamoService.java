package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Ramo;
import com.dobemcontabilidade.repository.RamoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Ramo}.
 */
@Service
@Transactional
public class RamoService {

    private static final Logger log = LoggerFactory.getLogger(RamoService.class);

    private final RamoRepository ramoRepository;

    public RamoService(RamoRepository ramoRepository) {
        this.ramoRepository = ramoRepository;
    }

    /**
     * Save a ramo.
     *
     * @param ramo the entity to save.
     * @return the persisted entity.
     */
    public Ramo save(Ramo ramo) {
        log.debug("Request to save Ramo : {}", ramo);
        return ramoRepository.save(ramo);
    }

    /**
     * Update a ramo.
     *
     * @param ramo the entity to save.
     * @return the persisted entity.
     */
    public Ramo update(Ramo ramo) {
        log.debug("Request to update Ramo : {}", ramo);
        return ramoRepository.save(ramo);
    }

    /**
     * Partially update a ramo.
     *
     * @param ramo the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Ramo> partialUpdate(Ramo ramo) {
        log.debug("Request to partially update Ramo : {}", ramo);

        return ramoRepository
            .findById(ramo.getId())
            .map(existingRamo -> {
                if (ramo.getNome() != null) {
                    existingRamo.setNome(ramo.getNome());
                }
                if (ramo.getDescricao() != null) {
                    existingRamo.setDescricao(ramo.getDescricao());
                }

                return existingRamo;
            })
            .map(ramoRepository::save);
    }

    /**
     * Get one ramo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Ramo> findOne(Long id) {
        log.debug("Request to get Ramo : {}", id);
        return ramoRepository.findById(id);
    }

    /**
     * Delete the ramo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Ramo : {}", id);
        ramoRepository.deleteById(id);
    }
}
