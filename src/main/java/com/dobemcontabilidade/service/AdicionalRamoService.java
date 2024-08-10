package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AdicionalRamo;
import com.dobemcontabilidade.repository.AdicionalRamoRepository;
import com.dobemcontabilidade.service.dto.AdicionalRamoDTO;
import com.dobemcontabilidade.service.mapper.AdicionalRamoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AdicionalRamo}.
 */
@Service
@Transactional
public class AdicionalRamoService {

    private static final Logger log = LoggerFactory.getLogger(AdicionalRamoService.class);

    private final AdicionalRamoRepository adicionalRamoRepository;

    private final AdicionalRamoMapper adicionalRamoMapper;

    public AdicionalRamoService(AdicionalRamoRepository adicionalRamoRepository, AdicionalRamoMapper adicionalRamoMapper) {
        this.adicionalRamoRepository = adicionalRamoRepository;
        this.adicionalRamoMapper = adicionalRamoMapper;
    }

    /**
     * Save a adicionalRamo.
     *
     * @param adicionalRamoDTO the entity to save.
     * @return the persisted entity.
     */
    public AdicionalRamoDTO save(AdicionalRamoDTO adicionalRamoDTO) {
        log.debug("Request to save AdicionalRamo : {}", adicionalRamoDTO);
        AdicionalRamo adicionalRamo = adicionalRamoMapper.toEntity(adicionalRamoDTO);
        adicionalRamo = adicionalRamoRepository.save(adicionalRamo);
        return adicionalRamoMapper.toDto(adicionalRamo);
    }

    /**
     * Update a adicionalRamo.
     *
     * @param adicionalRamoDTO the entity to save.
     * @return the persisted entity.
     */
    public AdicionalRamoDTO update(AdicionalRamoDTO adicionalRamoDTO) {
        log.debug("Request to update AdicionalRamo : {}", adicionalRamoDTO);
        AdicionalRamo adicionalRamo = adicionalRamoMapper.toEntity(adicionalRamoDTO);
        adicionalRamo = adicionalRamoRepository.save(adicionalRamo);
        return adicionalRamoMapper.toDto(adicionalRamo);
    }

    /**
     * Partially update a adicionalRamo.
     *
     * @param adicionalRamoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AdicionalRamoDTO> partialUpdate(AdicionalRamoDTO adicionalRamoDTO) {
        log.debug("Request to partially update AdicionalRamo : {}", adicionalRamoDTO);

        return adicionalRamoRepository
            .findById(adicionalRamoDTO.getId())
            .map(existingAdicionalRamo -> {
                adicionalRamoMapper.partialUpdate(existingAdicionalRamo, adicionalRamoDTO);

                return existingAdicionalRamo;
            })
            .map(adicionalRamoRepository::save)
            .map(adicionalRamoMapper::toDto);
    }

    /**
     * Get all the adicionalRamos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AdicionalRamoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return adicionalRamoRepository.findAllWithEagerRelationships(pageable).map(adicionalRamoMapper::toDto);
    }

    /**
     * Get one adicionalRamo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdicionalRamoDTO> findOne(Long id) {
        log.debug("Request to get AdicionalRamo : {}", id);
        return adicionalRamoRepository.findOneWithEagerRelationships(id).map(adicionalRamoMapper::toDto);
    }

    /**
     * Delete the adicionalRamo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdicionalRamo : {}", id);
        adicionalRamoRepository.deleteById(id);
    }
}
