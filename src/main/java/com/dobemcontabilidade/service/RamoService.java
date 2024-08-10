package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Ramo;
import com.dobemcontabilidade.repository.RamoRepository;
import com.dobemcontabilidade.service.dto.RamoDTO;
import com.dobemcontabilidade.service.mapper.RamoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Ramo}.
 */
@Service
@Transactional
public class RamoService {

    private static final Logger log = LoggerFactory.getLogger(RamoService.class);

    private final RamoRepository ramoRepository;

    private final RamoMapper ramoMapper;

    public RamoService(RamoRepository ramoRepository, RamoMapper ramoMapper) {
        this.ramoRepository = ramoRepository;
        this.ramoMapper = ramoMapper;
    }

    /**
     * Save a ramo.
     *
     * @param ramoDTO the entity to save.
     * @return the persisted entity.
     */
    public RamoDTO save(RamoDTO ramoDTO) {
        log.debug("Request to save Ramo : {}", ramoDTO);
        Ramo ramo = ramoMapper.toEntity(ramoDTO);
        ramo = ramoRepository.save(ramo);
        return ramoMapper.toDto(ramo);
    }

    /**
     * Update a ramo.
     *
     * @param ramoDTO the entity to save.
     * @return the persisted entity.
     */
    public RamoDTO update(RamoDTO ramoDTO) {
        log.debug("Request to update Ramo : {}", ramoDTO);
        Ramo ramo = ramoMapper.toEntity(ramoDTO);
        ramo = ramoRepository.save(ramo);
        return ramoMapper.toDto(ramo);
    }

    /**
     * Partially update a ramo.
     *
     * @param ramoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RamoDTO> partialUpdate(RamoDTO ramoDTO) {
        log.debug("Request to partially update Ramo : {}", ramoDTO);

        return ramoRepository
            .findById(ramoDTO.getId())
            .map(existingRamo -> {
                ramoMapper.partialUpdate(existingRamo, ramoDTO);

                return existingRamo;
            })
            .map(ramoRepository::save)
            .map(ramoMapper::toDto);
    }

    /**
     * Get one ramo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RamoDTO> findOne(Long id) {
        log.debug("Request to get Ramo : {}", id);
        return ramoRepository.findById(id).map(ramoMapper::toDto);
    }

    /**
     * Delete the ramo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Ramo : {}", id);
        ramoRepository.deleteById(id);
    }
}
