package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.GrupoCnae;
import com.dobemcontabilidade.repository.GrupoCnaeRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.GrupoCnae}.
 */
@Service
@Transactional
public class GrupoCnaeService {

    private static final Logger log = LoggerFactory.getLogger(GrupoCnaeService.class);

    private final GrupoCnaeRepository grupoCnaeRepository;

    public GrupoCnaeService(GrupoCnaeRepository grupoCnaeRepository) {
        this.grupoCnaeRepository = grupoCnaeRepository;
    }

    /**
     * Save a grupoCnae.
     *
     * @param grupoCnae the entity to save.
     * @return the persisted entity.
     */
    public GrupoCnae save(GrupoCnae grupoCnae) {
        log.debug("Request to save GrupoCnae : {}", grupoCnae);
        return grupoCnaeRepository.save(grupoCnae);
    }

    /**
     * Update a grupoCnae.
     *
     * @param grupoCnae the entity to save.
     * @return the persisted entity.
     */
    public GrupoCnae update(GrupoCnae grupoCnae) {
        log.debug("Request to update GrupoCnae : {}", grupoCnae);
        return grupoCnaeRepository.save(grupoCnae);
    }

    /**
     * Partially update a grupoCnae.
     *
     * @param grupoCnae the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GrupoCnae> partialUpdate(GrupoCnae grupoCnae) {
        log.debug("Request to partially update GrupoCnae : {}", grupoCnae);

        return grupoCnaeRepository
            .findById(grupoCnae.getId())
            .map(existingGrupoCnae -> {
                if (grupoCnae.getCodigo() != null) {
                    existingGrupoCnae.setCodigo(grupoCnae.getCodigo());
                }
                if (grupoCnae.getDescricao() != null) {
                    existingGrupoCnae.setDescricao(grupoCnae.getDescricao());
                }

                return existingGrupoCnae;
            })
            .map(grupoCnaeRepository::save);
    }

    /**
     * Get all the grupoCnaes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<GrupoCnae> findAllWithEagerRelationships(Pageable pageable) {
        return grupoCnaeRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one grupoCnae by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GrupoCnae> findOne(Long id) {
        log.debug("Request to get GrupoCnae : {}", id);
        return grupoCnaeRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the grupoCnae by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GrupoCnae : {}", id);
        grupoCnaeRepository.deleteById(id);
    }
}
