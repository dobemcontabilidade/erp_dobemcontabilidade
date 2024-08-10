package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.PlanoContaAzul;
import com.dobemcontabilidade.repository.PlanoContaAzulRepository;
import com.dobemcontabilidade.service.dto.PlanoContaAzulDTO;
import com.dobemcontabilidade.service.mapper.PlanoContaAzulMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.PlanoContaAzul}.
 */
@Service
@Transactional
public class PlanoContaAzulService {

    private static final Logger log = LoggerFactory.getLogger(PlanoContaAzulService.class);

    private final PlanoContaAzulRepository planoContaAzulRepository;

    private final PlanoContaAzulMapper planoContaAzulMapper;

    public PlanoContaAzulService(PlanoContaAzulRepository planoContaAzulRepository, PlanoContaAzulMapper planoContaAzulMapper) {
        this.planoContaAzulRepository = planoContaAzulRepository;
        this.planoContaAzulMapper = planoContaAzulMapper;
    }

    /**
     * Save a planoContaAzul.
     *
     * @param planoContaAzulDTO the entity to save.
     * @return the persisted entity.
     */
    public PlanoContaAzulDTO save(PlanoContaAzulDTO planoContaAzulDTO) {
        log.debug("Request to save PlanoContaAzul : {}", planoContaAzulDTO);
        PlanoContaAzul planoContaAzul = planoContaAzulMapper.toEntity(planoContaAzulDTO);
        planoContaAzul = planoContaAzulRepository.save(planoContaAzul);
        return planoContaAzulMapper.toDto(planoContaAzul);
    }

    /**
     * Update a planoContaAzul.
     *
     * @param planoContaAzulDTO the entity to save.
     * @return the persisted entity.
     */
    public PlanoContaAzulDTO update(PlanoContaAzulDTO planoContaAzulDTO) {
        log.debug("Request to update PlanoContaAzul : {}", planoContaAzulDTO);
        PlanoContaAzul planoContaAzul = planoContaAzulMapper.toEntity(planoContaAzulDTO);
        planoContaAzul = planoContaAzulRepository.save(planoContaAzul);
        return planoContaAzulMapper.toDto(planoContaAzul);
    }

    /**
     * Partially update a planoContaAzul.
     *
     * @param planoContaAzulDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PlanoContaAzulDTO> partialUpdate(PlanoContaAzulDTO planoContaAzulDTO) {
        log.debug("Request to partially update PlanoContaAzul : {}", planoContaAzulDTO);

        return planoContaAzulRepository
            .findById(planoContaAzulDTO.getId())
            .map(existingPlanoContaAzul -> {
                planoContaAzulMapper.partialUpdate(existingPlanoContaAzul, planoContaAzulDTO);

                return existingPlanoContaAzul;
            })
            .map(planoContaAzulRepository::save)
            .map(planoContaAzulMapper::toDto);
    }

    /**
     * Get one planoContaAzul by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PlanoContaAzulDTO> findOne(Long id) {
        log.debug("Request to get PlanoContaAzul : {}", id);
        return planoContaAzulRepository.findById(id).map(planoContaAzulMapper::toDto);
    }

    /**
     * Delete the planoContaAzul by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PlanoContaAzul : {}", id);
        planoContaAzulRepository.deleteById(id);
    }
}
