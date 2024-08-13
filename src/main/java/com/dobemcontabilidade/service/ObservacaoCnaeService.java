package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.ObservacaoCnae;
import com.dobemcontabilidade.repository.ObservacaoCnaeRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.ObservacaoCnae}.
 */
@Service
@Transactional
public class ObservacaoCnaeService {

    private static final Logger log = LoggerFactory.getLogger(ObservacaoCnaeService.class);

    private final ObservacaoCnaeRepository observacaoCnaeRepository;

    public ObservacaoCnaeService(ObservacaoCnaeRepository observacaoCnaeRepository) {
        this.observacaoCnaeRepository = observacaoCnaeRepository;
    }

    /**
     * Save a observacaoCnae.
     *
     * @param observacaoCnae the entity to save.
     * @return the persisted entity.
     */
    public ObservacaoCnae save(ObservacaoCnae observacaoCnae) {
        log.debug("Request to save ObservacaoCnae : {}", observacaoCnae);
        return observacaoCnaeRepository.save(observacaoCnae);
    }

    /**
     * Update a observacaoCnae.
     *
     * @param observacaoCnae the entity to save.
     * @return the persisted entity.
     */
    public ObservacaoCnae update(ObservacaoCnae observacaoCnae) {
        log.debug("Request to update ObservacaoCnae : {}", observacaoCnae);
        return observacaoCnaeRepository.save(observacaoCnae);
    }

    /**
     * Partially update a observacaoCnae.
     *
     * @param observacaoCnae the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ObservacaoCnae> partialUpdate(ObservacaoCnae observacaoCnae) {
        log.debug("Request to partially update ObservacaoCnae : {}", observacaoCnae);

        return observacaoCnaeRepository
            .findById(observacaoCnae.getId())
            .map(existingObservacaoCnae -> {
                if (observacaoCnae.getDescricao() != null) {
                    existingObservacaoCnae.setDescricao(observacaoCnae.getDescricao());
                }

                return existingObservacaoCnae;
            })
            .map(observacaoCnaeRepository::save);
    }

    /**
     * Get all the observacaoCnaes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ObservacaoCnae> findAllWithEagerRelationships(Pageable pageable) {
        return observacaoCnaeRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one observacaoCnae by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ObservacaoCnae> findOne(Long id) {
        log.debug("Request to get ObservacaoCnae : {}", id);
        return observacaoCnaeRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the observacaoCnae by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ObservacaoCnae : {}", id);
        observacaoCnaeRepository.deleteById(id);
    }
}
