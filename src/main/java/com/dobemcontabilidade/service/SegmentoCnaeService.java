package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.SegmentoCnae;
import com.dobemcontabilidade.repository.SegmentoCnaeRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.SegmentoCnae}.
 */
@Service
@Transactional
public class SegmentoCnaeService {

    private static final Logger log = LoggerFactory.getLogger(SegmentoCnaeService.class);

    private final SegmentoCnaeRepository segmentoCnaeRepository;

    public SegmentoCnaeService(SegmentoCnaeRepository segmentoCnaeRepository) {
        this.segmentoCnaeRepository = segmentoCnaeRepository;
    }

    /**
     * Save a segmentoCnae.
     *
     * @param segmentoCnae the entity to save.
     * @return the persisted entity.
     */
    public SegmentoCnae save(SegmentoCnae segmentoCnae) {
        log.debug("Request to save SegmentoCnae : {}", segmentoCnae);
        return segmentoCnaeRepository.save(segmentoCnae);
    }

    /**
     * Update a segmentoCnae.
     *
     * @param segmentoCnae the entity to save.
     * @return the persisted entity.
     */
    public SegmentoCnae update(SegmentoCnae segmentoCnae) {
        log.debug("Request to update SegmentoCnae : {}", segmentoCnae);
        return segmentoCnaeRepository.save(segmentoCnae);
    }

    /**
     * Partially update a segmentoCnae.
     *
     * @param segmentoCnae the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SegmentoCnae> partialUpdate(SegmentoCnae segmentoCnae) {
        log.debug("Request to partially update SegmentoCnae : {}", segmentoCnae);

        return segmentoCnaeRepository
            .findById(segmentoCnae.getId())
            .map(existingSegmentoCnae -> {
                if (segmentoCnae.getNome() != null) {
                    existingSegmentoCnae.setNome(segmentoCnae.getNome());
                }
                if (segmentoCnae.getDescricao() != null) {
                    existingSegmentoCnae.setDescricao(segmentoCnae.getDescricao());
                }
                if (segmentoCnae.getIcon() != null) {
                    existingSegmentoCnae.setIcon(segmentoCnae.getIcon());
                }
                if (segmentoCnae.getImagem() != null) {
                    existingSegmentoCnae.setImagem(segmentoCnae.getImagem());
                }
                if (segmentoCnae.getTags() != null) {
                    existingSegmentoCnae.setTags(segmentoCnae.getTags());
                }
                if (segmentoCnae.getTipo() != null) {
                    existingSegmentoCnae.setTipo(segmentoCnae.getTipo());
                }

                return existingSegmentoCnae;
            })
            .map(segmentoCnaeRepository::save);
    }

    /**
     * Get all the segmentoCnaes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<SegmentoCnae> findAllWithEagerRelationships(Pageable pageable) {
        return segmentoCnaeRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one segmentoCnae by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SegmentoCnae> findOne(Long id) {
        log.debug("Request to get SegmentoCnae : {}", id);
        return segmentoCnaeRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the segmentoCnae by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SegmentoCnae : {}", id);
        segmentoCnaeRepository.deleteById(id);
    }
}
