package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AdicionalEnquadramento;
import com.dobemcontabilidade.repository.AdicionalEnquadramentoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AdicionalEnquadramento}.
 */
@Service
@Transactional
public class AdicionalEnquadramentoService {

    private static final Logger log = LoggerFactory.getLogger(AdicionalEnquadramentoService.class);

    private final AdicionalEnquadramentoRepository adicionalEnquadramentoRepository;

    public AdicionalEnquadramentoService(AdicionalEnquadramentoRepository adicionalEnquadramentoRepository) {
        this.adicionalEnquadramentoRepository = adicionalEnquadramentoRepository;
    }

    /**
     * Save a adicionalEnquadramento.
     *
     * @param adicionalEnquadramento the entity to save.
     * @return the persisted entity.
     */
    public AdicionalEnquadramento save(AdicionalEnquadramento adicionalEnquadramento) {
        log.debug("Request to save AdicionalEnquadramento : {}", adicionalEnquadramento);
        return adicionalEnquadramentoRepository.save(adicionalEnquadramento);
    }

    /**
     * Update a adicionalEnquadramento.
     *
     * @param adicionalEnquadramento the entity to save.
     * @return the persisted entity.
     */
    public AdicionalEnquadramento update(AdicionalEnquadramento adicionalEnquadramento) {
        log.debug("Request to update AdicionalEnquadramento : {}", adicionalEnquadramento);
        return adicionalEnquadramentoRepository.save(adicionalEnquadramento);
    }

    /**
     * Partially update a adicionalEnquadramento.
     *
     * @param adicionalEnquadramento the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AdicionalEnquadramento> partialUpdate(AdicionalEnquadramento adicionalEnquadramento) {
        log.debug("Request to partially update AdicionalEnquadramento : {}", adicionalEnquadramento);

        return adicionalEnquadramentoRepository
            .findById(adicionalEnquadramento.getId())
            .map(existingAdicionalEnquadramento -> {
                if (adicionalEnquadramento.getValor() != null) {
                    existingAdicionalEnquadramento.setValor(adicionalEnquadramento.getValor());
                }

                return existingAdicionalEnquadramento;
            })
            .map(adicionalEnquadramentoRepository::save);
    }

    /**
     * Get all the adicionalEnquadramentos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AdicionalEnquadramento> findAllWithEagerRelationships(Pageable pageable) {
        return adicionalEnquadramentoRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one adicionalEnquadramento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdicionalEnquadramento> findOne(Long id) {
        log.debug("Request to get AdicionalEnquadramento : {}", id);
        return adicionalEnquadramentoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the adicionalEnquadramento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdicionalEnquadramento : {}", id);
        adicionalEnquadramentoRepository.deleteById(id);
    }
}
