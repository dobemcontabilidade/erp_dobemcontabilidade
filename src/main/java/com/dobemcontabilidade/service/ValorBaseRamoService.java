package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.ValorBaseRamo;
import com.dobemcontabilidade.repository.ValorBaseRamoRepository;
import com.dobemcontabilidade.service.dto.ValorBaseRamoDTO;
import com.dobemcontabilidade.service.mapper.ValorBaseRamoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.ValorBaseRamo}.
 */
@Service
@Transactional
public class ValorBaseRamoService {

    private static final Logger log = LoggerFactory.getLogger(ValorBaseRamoService.class);

    private final ValorBaseRamoRepository valorBaseRamoRepository;

    private final ValorBaseRamoMapper valorBaseRamoMapper;

    public ValorBaseRamoService(ValorBaseRamoRepository valorBaseRamoRepository, ValorBaseRamoMapper valorBaseRamoMapper) {
        this.valorBaseRamoRepository = valorBaseRamoRepository;
        this.valorBaseRamoMapper = valorBaseRamoMapper;
    }

    /**
     * Save a valorBaseRamo.
     *
     * @param valorBaseRamoDTO the entity to save.
     * @return the persisted entity.
     */
    public ValorBaseRamoDTO save(ValorBaseRamoDTO valorBaseRamoDTO) {
        log.debug("Request to save ValorBaseRamo : {}", valorBaseRamoDTO);
        ValorBaseRamo valorBaseRamo = valorBaseRamoMapper.toEntity(valorBaseRamoDTO);
        valorBaseRamo = valorBaseRamoRepository.save(valorBaseRamo);
        return valorBaseRamoMapper.toDto(valorBaseRamo);
    }

    /**
     * Update a valorBaseRamo.
     *
     * @param valorBaseRamoDTO the entity to save.
     * @return the persisted entity.
     */
    public ValorBaseRamoDTO update(ValorBaseRamoDTO valorBaseRamoDTO) {
        log.debug("Request to update ValorBaseRamo : {}", valorBaseRamoDTO);
        ValorBaseRamo valorBaseRamo = valorBaseRamoMapper.toEntity(valorBaseRamoDTO);
        valorBaseRamo = valorBaseRamoRepository.save(valorBaseRamo);
        return valorBaseRamoMapper.toDto(valorBaseRamo);
    }

    /**
     * Partially update a valorBaseRamo.
     *
     * @param valorBaseRamoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ValorBaseRamoDTO> partialUpdate(ValorBaseRamoDTO valorBaseRamoDTO) {
        log.debug("Request to partially update ValorBaseRamo : {}", valorBaseRamoDTO);

        return valorBaseRamoRepository
            .findById(valorBaseRamoDTO.getId())
            .map(existingValorBaseRamo -> {
                valorBaseRamoMapper.partialUpdate(existingValorBaseRamo, valorBaseRamoDTO);

                return existingValorBaseRamo;
            })
            .map(valorBaseRamoRepository::save)
            .map(valorBaseRamoMapper::toDto);
    }

    /**
     * Get all the valorBaseRamos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ValorBaseRamoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return valorBaseRamoRepository.findAllWithEagerRelationships(pageable).map(valorBaseRamoMapper::toDto);
    }

    /**
     * Get one valorBaseRamo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ValorBaseRamoDTO> findOne(Long id) {
        log.debug("Request to get ValorBaseRamo : {}", id);
        return valorBaseRamoRepository.findOneWithEagerRelationships(id).map(valorBaseRamoMapper::toDto);
    }

    /**
     * Delete the valorBaseRamo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ValorBaseRamo : {}", id);
        valorBaseRamoRepository.deleteById(id);
    }
}
