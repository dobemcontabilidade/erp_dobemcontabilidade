package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.FluxoModelo;
import com.dobemcontabilidade.repository.FluxoModeloRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.FluxoModelo}.
 */
@Service
@Transactional
public class FluxoModeloService {

    private static final Logger log = LoggerFactory.getLogger(FluxoModeloService.class);

    private final FluxoModeloRepository fluxoModeloRepository;

    public FluxoModeloService(FluxoModeloRepository fluxoModeloRepository) {
        this.fluxoModeloRepository = fluxoModeloRepository;
    }

    /**
     * Save a fluxoModelo.
     *
     * @param fluxoModelo the entity to save.
     * @return the persisted entity.
     */
    public FluxoModelo save(FluxoModelo fluxoModelo) {
        log.debug("Request to save FluxoModelo : {}", fluxoModelo);
        return fluxoModeloRepository.save(fluxoModelo);
    }

    /**
     * Update a fluxoModelo.
     *
     * @param fluxoModelo the entity to save.
     * @return the persisted entity.
     */
    public FluxoModelo update(FluxoModelo fluxoModelo) {
        log.debug("Request to update FluxoModelo : {}", fluxoModelo);
        return fluxoModeloRepository.save(fluxoModelo);
    }

    /**
     * Partially update a fluxoModelo.
     *
     * @param fluxoModelo the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FluxoModelo> partialUpdate(FluxoModelo fluxoModelo) {
        log.debug("Request to partially update FluxoModelo : {}", fluxoModelo);

        return fluxoModeloRepository
            .findById(fluxoModelo.getId())
            .map(existingFluxoModelo -> {
                if (fluxoModelo.getNome() != null) {
                    existingFluxoModelo.setNome(fluxoModelo.getNome());
                }
                if (fluxoModelo.getDescricao() != null) {
                    existingFluxoModelo.setDescricao(fluxoModelo.getDescricao());
                }

                return existingFluxoModelo;
            })
            .map(fluxoModeloRepository::save);
    }

    /**
     * Get all the fluxoModelos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FluxoModelo> findAll() {
        log.debug("Request to get all FluxoModelos");
        return fluxoModeloRepository.findAll();
    }

    /**
     * Get all the fluxoModelos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<FluxoModelo> findAllWithEagerRelationships(Pageable pageable) {
        return fluxoModeloRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one fluxoModelo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FluxoModelo> findOne(Long id) {
        log.debug("Request to get FluxoModelo : {}", id);
        return fluxoModeloRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the fluxoModelo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FluxoModelo : {}", id);
        fluxoModeloRepository.deleteById(id);
    }
}
