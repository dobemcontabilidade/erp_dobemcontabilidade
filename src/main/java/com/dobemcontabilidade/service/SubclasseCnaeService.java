package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.SubclasseCnae;
import com.dobemcontabilidade.repository.SubclasseCnaeRepository;
import com.dobemcontabilidade.service.dto.SubclasseCnaeDTO;
import com.dobemcontabilidade.service.mapper.SubclasseCnaeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.SubclasseCnae}.
 */
@Service
@Transactional
public class SubclasseCnaeService {

    private static final Logger log = LoggerFactory.getLogger(SubclasseCnaeService.class);

    private final SubclasseCnaeRepository subclasseCnaeRepository;

    private final SubclasseCnaeMapper subclasseCnaeMapper;

    public SubclasseCnaeService(SubclasseCnaeRepository subclasseCnaeRepository, SubclasseCnaeMapper subclasseCnaeMapper) {
        this.subclasseCnaeRepository = subclasseCnaeRepository;
        this.subclasseCnaeMapper = subclasseCnaeMapper;
    }

    /**
     * Save a subclasseCnae.
     *
     * @param subclasseCnaeDTO the entity to save.
     * @return the persisted entity.
     */
    public SubclasseCnaeDTO save(SubclasseCnaeDTO subclasseCnaeDTO) {
        log.debug("Request to save SubclasseCnae : {}", subclasseCnaeDTO);
        SubclasseCnae subclasseCnae = subclasseCnaeMapper.toEntity(subclasseCnaeDTO);
        subclasseCnae = subclasseCnaeRepository.save(subclasseCnae);
        return subclasseCnaeMapper.toDto(subclasseCnae);
    }

    /**
     * Update a subclasseCnae.
     *
     * @param subclasseCnaeDTO the entity to save.
     * @return the persisted entity.
     */
    public SubclasseCnaeDTO update(SubclasseCnaeDTO subclasseCnaeDTO) {
        log.debug("Request to update SubclasseCnae : {}", subclasseCnaeDTO);
        SubclasseCnae subclasseCnae = subclasseCnaeMapper.toEntity(subclasseCnaeDTO);
        subclasseCnae = subclasseCnaeRepository.save(subclasseCnae);
        return subclasseCnaeMapper.toDto(subclasseCnae);
    }

    /**
     * Partially update a subclasseCnae.
     *
     * @param subclasseCnaeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SubclasseCnaeDTO> partialUpdate(SubclasseCnaeDTO subclasseCnaeDTO) {
        log.debug("Request to partially update SubclasseCnae : {}", subclasseCnaeDTO);

        return subclasseCnaeRepository
            .findById(subclasseCnaeDTO.getId())
            .map(existingSubclasseCnae -> {
                subclasseCnaeMapper.partialUpdate(existingSubclasseCnae, subclasseCnaeDTO);

                return existingSubclasseCnae;
            })
            .map(subclasseCnaeRepository::save)
            .map(subclasseCnaeMapper::toDto);
    }

    /**
     * Get all the subclasseCnaes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<SubclasseCnaeDTO> findAllWithEagerRelationships(Pageable pageable) {
        return subclasseCnaeRepository.findAllWithEagerRelationships(pageable).map(subclasseCnaeMapper::toDto);
    }

    /**
     * Get one subclasseCnae by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SubclasseCnaeDTO> findOne(Long id) {
        log.debug("Request to get SubclasseCnae : {}", id);
        return subclasseCnaeRepository.findOneWithEagerRelationships(id).map(subclasseCnaeMapper::toDto);
    }

    /**
     * Delete the subclasseCnae by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SubclasseCnae : {}", id);
        subclasseCnaeRepository.deleteById(id);
    }
}
