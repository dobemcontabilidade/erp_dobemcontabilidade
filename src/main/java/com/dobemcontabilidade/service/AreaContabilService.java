package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AreaContabil;
import com.dobemcontabilidade.repository.AreaContabilRepository;
import com.dobemcontabilidade.service.dto.AreaContabilDTO;
import com.dobemcontabilidade.service.mapper.AreaContabilMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AreaContabil}.
 */
@Service
@Transactional
public class AreaContabilService {

    private static final Logger log = LoggerFactory.getLogger(AreaContabilService.class);

    private final AreaContabilRepository areaContabilRepository;

    private final AreaContabilMapper areaContabilMapper;

    public AreaContabilService(AreaContabilRepository areaContabilRepository, AreaContabilMapper areaContabilMapper) {
        this.areaContabilRepository = areaContabilRepository;
        this.areaContabilMapper = areaContabilMapper;
    }

    /**
     * Save a areaContabil.
     *
     * @param areaContabilDTO the entity to save.
     * @return the persisted entity.
     */
    public AreaContabilDTO save(AreaContabilDTO areaContabilDTO) {
        log.debug("Request to save AreaContabil : {}", areaContabilDTO);
        AreaContabil areaContabil = areaContabilMapper.toEntity(areaContabilDTO);
        areaContabil = areaContabilRepository.save(areaContabil);
        return areaContabilMapper.toDto(areaContabil);
    }

    /**
     * Update a areaContabil.
     *
     * @param areaContabilDTO the entity to save.
     * @return the persisted entity.
     */
    public AreaContabilDTO update(AreaContabilDTO areaContabilDTO) {
        log.debug("Request to update AreaContabil : {}", areaContabilDTO);
        AreaContabil areaContabil = areaContabilMapper.toEntity(areaContabilDTO);
        areaContabil = areaContabilRepository.save(areaContabil);
        return areaContabilMapper.toDto(areaContabil);
    }

    /**
     * Partially update a areaContabil.
     *
     * @param areaContabilDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AreaContabilDTO> partialUpdate(AreaContabilDTO areaContabilDTO) {
        log.debug("Request to partially update AreaContabil : {}", areaContabilDTO);

        return areaContabilRepository
            .findById(areaContabilDTO.getId())
            .map(existingAreaContabil -> {
                areaContabilMapper.partialUpdate(existingAreaContabil, areaContabilDTO);

                return existingAreaContabil;
            })
            .map(areaContabilRepository::save)
            .map(areaContabilMapper::toDto);
    }

    /**
     * Get all the areaContabils.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AreaContabilDTO> findAll() {
        log.debug("Request to get all AreaContabils");
        return areaContabilRepository.findAll().stream().map(areaContabilMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one areaContabil by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AreaContabilDTO> findOne(Long id) {
        log.debug("Request to get AreaContabil : {}", id);
        return areaContabilRepository.findById(id).map(areaContabilMapper::toDto);
    }

    /**
     * Delete the areaContabil by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AreaContabil : {}", id);
        areaContabilRepository.deleteById(id);
    }
}
