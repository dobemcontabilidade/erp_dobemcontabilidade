package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.GrupoCnae;
import com.dobemcontabilidade.repository.GrupoCnaeRepository;
import com.dobemcontabilidade.service.dto.GrupoCnaeDTO;
import com.dobemcontabilidade.service.mapper.GrupoCnaeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.GrupoCnae}.
 */
@Service
@Transactional
public class GrupoCnaeService {

    private static final Logger log = LoggerFactory.getLogger(GrupoCnaeService.class);

    private final GrupoCnaeRepository grupoCnaeRepository;

    private final GrupoCnaeMapper grupoCnaeMapper;

    public GrupoCnaeService(GrupoCnaeRepository grupoCnaeRepository, GrupoCnaeMapper grupoCnaeMapper) {
        this.grupoCnaeRepository = grupoCnaeRepository;
        this.grupoCnaeMapper = grupoCnaeMapper;
    }

    /**
     * Save a grupoCnae.
     *
     * @param grupoCnaeDTO the entity to save.
     * @return the persisted entity.
     */
    public GrupoCnaeDTO save(GrupoCnaeDTO grupoCnaeDTO) {
        log.debug("Request to save GrupoCnae : {}", grupoCnaeDTO);
        GrupoCnae grupoCnae = grupoCnaeMapper.toEntity(grupoCnaeDTO);
        grupoCnae = grupoCnaeRepository.save(grupoCnae);
        return grupoCnaeMapper.toDto(grupoCnae);
    }

    /**
     * Update a grupoCnae.
     *
     * @param grupoCnaeDTO the entity to save.
     * @return the persisted entity.
     */
    public GrupoCnaeDTO update(GrupoCnaeDTO grupoCnaeDTO) {
        log.debug("Request to update GrupoCnae : {}", grupoCnaeDTO);
        GrupoCnae grupoCnae = grupoCnaeMapper.toEntity(grupoCnaeDTO);
        grupoCnae = grupoCnaeRepository.save(grupoCnae);
        return grupoCnaeMapper.toDto(grupoCnae);
    }

    /**
     * Partially update a grupoCnae.
     *
     * @param grupoCnaeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GrupoCnaeDTO> partialUpdate(GrupoCnaeDTO grupoCnaeDTO) {
        log.debug("Request to partially update GrupoCnae : {}", grupoCnaeDTO);

        return grupoCnaeRepository
            .findById(grupoCnaeDTO.getId())
            .map(existingGrupoCnae -> {
                grupoCnaeMapper.partialUpdate(existingGrupoCnae, grupoCnaeDTO);

                return existingGrupoCnae;
            })
            .map(grupoCnaeRepository::save)
            .map(grupoCnaeMapper::toDto);
    }

    /**
     * Get all the grupoCnaes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<GrupoCnaeDTO> findAllWithEagerRelationships(Pageable pageable) {
        return grupoCnaeRepository.findAllWithEagerRelationships(pageable).map(grupoCnaeMapper::toDto);
    }

    /**
     * Get one grupoCnae by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GrupoCnaeDTO> findOne(Long id) {
        log.debug("Request to get GrupoCnae : {}", id);
        return grupoCnaeRepository.findOneWithEagerRelationships(id).map(grupoCnaeMapper::toDto);
    }

    /**
     * Delete the grupoCnae by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GrupoCnae : {}", id);
        grupoCnaeRepository.deleteById(id);
    }
}
