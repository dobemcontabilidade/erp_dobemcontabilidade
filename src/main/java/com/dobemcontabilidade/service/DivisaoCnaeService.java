package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.DivisaoCnae;
import com.dobemcontabilidade.repository.DivisaoCnaeRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.DivisaoCnae}.
 */
@Service
@Transactional
public class DivisaoCnaeService {

    private static final Logger log = LoggerFactory.getLogger(DivisaoCnaeService.class);

    private final DivisaoCnaeRepository divisaoCnaeRepository;

    public DivisaoCnaeService(DivisaoCnaeRepository divisaoCnaeRepository) {
        this.divisaoCnaeRepository = divisaoCnaeRepository;
    }

    /**
     * Save a divisaoCnae.
     *
     * @param divisaoCnae the entity to save.
     * @return the persisted entity.
     */
    public DivisaoCnae save(DivisaoCnae divisaoCnae) {
        log.debug("Request to save DivisaoCnae : {}", divisaoCnae);
        return divisaoCnaeRepository.save(divisaoCnae);
    }

    /**
     * Update a divisaoCnae.
     *
     * @param divisaoCnae the entity to save.
     * @return the persisted entity.
     */
    public DivisaoCnae update(DivisaoCnae divisaoCnae) {
        log.debug("Request to update DivisaoCnae : {}", divisaoCnae);
        return divisaoCnaeRepository.save(divisaoCnae);
    }

    /**
     * Partially update a divisaoCnae.
     *
     * @param divisaoCnae the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DivisaoCnae> partialUpdate(DivisaoCnae divisaoCnae) {
        log.debug("Request to partially update DivisaoCnae : {}", divisaoCnae);

        return divisaoCnaeRepository
            .findById(divisaoCnae.getId())
            .map(existingDivisaoCnae -> {
                if (divisaoCnae.getCodigo() != null) {
                    existingDivisaoCnae.setCodigo(divisaoCnae.getCodigo());
                }
                if (divisaoCnae.getDescricao() != null) {
                    existingDivisaoCnae.setDescricao(divisaoCnae.getDescricao());
                }

                return existingDivisaoCnae;
            })
            .map(divisaoCnaeRepository::save);
    }

    /**
     * Get all the divisaoCnaes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DivisaoCnae> findAllWithEagerRelationships(Pageable pageable) {
        return divisaoCnaeRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one divisaoCnae by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DivisaoCnae> findOne(Long id) {
        log.debug("Request to get DivisaoCnae : {}", id);
        return divisaoCnaeRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the divisaoCnae by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DivisaoCnae : {}", id);
        divisaoCnaeRepository.deleteById(id);
    }
}
