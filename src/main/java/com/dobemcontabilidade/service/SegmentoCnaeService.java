package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.SegmentoCnae;
import com.dobemcontabilidade.repository.SegmentoCnaeRepository;
import com.dobemcontabilidade.service.dto.SegmentoCnaeDTO;
import com.dobemcontabilidade.service.mapper.SegmentoCnaeMapper;
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

    private final SegmentoCnaeMapper segmentoCnaeMapper;

    public SegmentoCnaeService(SegmentoCnaeRepository segmentoCnaeRepository, SegmentoCnaeMapper segmentoCnaeMapper) {
        this.segmentoCnaeRepository = segmentoCnaeRepository;
        this.segmentoCnaeMapper = segmentoCnaeMapper;
    }

    /**
     * Save a segmentoCnae.
     *
     * @param segmentoCnaeDTO the entity to save.
     * @return the persisted entity.
     */
    public SegmentoCnaeDTO save(SegmentoCnaeDTO segmentoCnaeDTO) {
        log.debug("Request to save SegmentoCnae : {}", segmentoCnaeDTO);
        SegmentoCnae segmentoCnae = segmentoCnaeMapper.toEntity(segmentoCnaeDTO);
        segmentoCnae = segmentoCnaeRepository.save(segmentoCnae);
        return segmentoCnaeMapper.toDto(segmentoCnae);
    }

    /**
     * Update a segmentoCnae.
     *
     * @param segmentoCnaeDTO the entity to save.
     * @return the persisted entity.
     */
    public SegmentoCnaeDTO update(SegmentoCnaeDTO segmentoCnaeDTO) {
        log.debug("Request to update SegmentoCnae : {}", segmentoCnaeDTO);
        SegmentoCnae segmentoCnae = segmentoCnaeMapper.toEntity(segmentoCnaeDTO);
        segmentoCnae = segmentoCnaeRepository.save(segmentoCnae);
        return segmentoCnaeMapper.toDto(segmentoCnae);
    }

    /**
     * Partially update a segmentoCnae.
     *
     * @param segmentoCnaeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SegmentoCnaeDTO> partialUpdate(SegmentoCnaeDTO segmentoCnaeDTO) {
        log.debug("Request to partially update SegmentoCnae : {}", segmentoCnaeDTO);

        return segmentoCnaeRepository
            .findById(segmentoCnaeDTO.getId())
            .map(existingSegmentoCnae -> {
                segmentoCnaeMapper.partialUpdate(existingSegmentoCnae, segmentoCnaeDTO);

                return existingSegmentoCnae;
            })
            .map(segmentoCnaeRepository::save)
            .map(segmentoCnaeMapper::toDto);
    }

    /**
     * Get all the segmentoCnaes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<SegmentoCnaeDTO> findAllWithEagerRelationships(Pageable pageable) {
        return segmentoCnaeRepository.findAllWithEagerRelationships(pageable).map(segmentoCnaeMapper::toDto);
    }

    /**
     * Get one segmentoCnae by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SegmentoCnaeDTO> findOne(Long id) {
        log.debug("Request to get SegmentoCnae : {}", id);
        return segmentoCnaeRepository.findOneWithEagerRelationships(id).map(segmentoCnaeMapper::toDto);
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
