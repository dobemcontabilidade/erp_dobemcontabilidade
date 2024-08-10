package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.PlanoContabil;
import com.dobemcontabilidade.repository.PlanoContabilRepository;
import com.dobemcontabilidade.service.dto.PlanoContabilDTO;
import com.dobemcontabilidade.service.mapper.PlanoContabilMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.PlanoContabil}.
 */
@Service
@Transactional
public class PlanoContabilService {

    private static final Logger log = LoggerFactory.getLogger(PlanoContabilService.class);

    private final PlanoContabilRepository planoContabilRepository;

    private final PlanoContabilMapper planoContabilMapper;

    public PlanoContabilService(PlanoContabilRepository planoContabilRepository, PlanoContabilMapper planoContabilMapper) {
        this.planoContabilRepository = planoContabilRepository;
        this.planoContabilMapper = planoContabilMapper;
    }

    /**
     * Save a planoContabil.
     *
     * @param planoContabilDTO the entity to save.
     * @return the persisted entity.
     */
    public PlanoContabilDTO save(PlanoContabilDTO planoContabilDTO) {
        log.debug("Request to save PlanoContabil : {}", planoContabilDTO);
        PlanoContabil planoContabil = planoContabilMapper.toEntity(planoContabilDTO);
        planoContabil = planoContabilRepository.save(planoContabil);
        return planoContabilMapper.toDto(planoContabil);
    }

    /**
     * Update a planoContabil.
     *
     * @param planoContabilDTO the entity to save.
     * @return the persisted entity.
     */
    public PlanoContabilDTO update(PlanoContabilDTO planoContabilDTO) {
        log.debug("Request to update PlanoContabil : {}", planoContabilDTO);
        PlanoContabil planoContabil = planoContabilMapper.toEntity(planoContabilDTO);
        planoContabil = planoContabilRepository.save(planoContabil);
        return planoContabilMapper.toDto(planoContabil);
    }

    /**
     * Partially update a planoContabil.
     *
     * @param planoContabilDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PlanoContabilDTO> partialUpdate(PlanoContabilDTO planoContabilDTO) {
        log.debug("Request to partially update PlanoContabil : {}", planoContabilDTO);

        return planoContabilRepository
            .findById(planoContabilDTO.getId())
            .map(existingPlanoContabil -> {
                planoContabilMapper.partialUpdate(existingPlanoContabil, planoContabilDTO);

                return existingPlanoContabil;
            })
            .map(planoContabilRepository::save)
            .map(planoContabilMapper::toDto);
    }

    /**
     * Get one planoContabil by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PlanoContabilDTO> findOne(Long id) {
        log.debug("Request to get PlanoContabil : {}", id);
        return planoContabilRepository.findById(id).map(planoContabilMapper::toDto);
    }

    /**
     * Delete the planoContabil by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PlanoContabil : {}", id);
        planoContabilRepository.deleteById(id);
    }
}
