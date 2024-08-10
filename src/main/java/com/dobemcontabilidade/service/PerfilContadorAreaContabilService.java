package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.PerfilContadorAreaContabil;
import com.dobemcontabilidade.repository.PerfilContadorAreaContabilRepository;
import com.dobemcontabilidade.service.dto.PerfilContadorAreaContabilDTO;
import com.dobemcontabilidade.service.mapper.PerfilContadorAreaContabilMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.PerfilContadorAreaContabil}.
 */
@Service
@Transactional
public class PerfilContadorAreaContabilService {

    private static final Logger log = LoggerFactory.getLogger(PerfilContadorAreaContabilService.class);

    private final PerfilContadorAreaContabilRepository perfilContadorAreaContabilRepository;

    private final PerfilContadorAreaContabilMapper perfilContadorAreaContabilMapper;

    public PerfilContadorAreaContabilService(
        PerfilContadorAreaContabilRepository perfilContadorAreaContabilRepository,
        PerfilContadorAreaContabilMapper perfilContadorAreaContabilMapper
    ) {
        this.perfilContadorAreaContabilRepository = perfilContadorAreaContabilRepository;
        this.perfilContadorAreaContabilMapper = perfilContadorAreaContabilMapper;
    }

    /**
     * Save a perfilContadorAreaContabil.
     *
     * @param perfilContadorAreaContabilDTO the entity to save.
     * @return the persisted entity.
     */
    public PerfilContadorAreaContabilDTO save(PerfilContadorAreaContabilDTO perfilContadorAreaContabilDTO) {
        log.debug("Request to save PerfilContadorAreaContabil : {}", perfilContadorAreaContabilDTO);
        PerfilContadorAreaContabil perfilContadorAreaContabil = perfilContadorAreaContabilMapper.toEntity(perfilContadorAreaContabilDTO);
        perfilContadorAreaContabil = perfilContadorAreaContabilRepository.save(perfilContadorAreaContabil);
        return perfilContadorAreaContabilMapper.toDto(perfilContadorAreaContabil);
    }

    /**
     * Update a perfilContadorAreaContabil.
     *
     * @param perfilContadorAreaContabilDTO the entity to save.
     * @return the persisted entity.
     */
    public PerfilContadorAreaContabilDTO update(PerfilContadorAreaContabilDTO perfilContadorAreaContabilDTO) {
        log.debug("Request to update PerfilContadorAreaContabil : {}", perfilContadorAreaContabilDTO);
        PerfilContadorAreaContabil perfilContadorAreaContabil = perfilContadorAreaContabilMapper.toEntity(perfilContadorAreaContabilDTO);
        perfilContadorAreaContabil = perfilContadorAreaContabilRepository.save(perfilContadorAreaContabil);
        return perfilContadorAreaContabilMapper.toDto(perfilContadorAreaContabil);
    }

    /**
     * Partially update a perfilContadorAreaContabil.
     *
     * @param perfilContadorAreaContabilDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PerfilContadorAreaContabilDTO> partialUpdate(PerfilContadorAreaContabilDTO perfilContadorAreaContabilDTO) {
        log.debug("Request to partially update PerfilContadorAreaContabil : {}", perfilContadorAreaContabilDTO);

        return perfilContadorAreaContabilRepository
            .findById(perfilContadorAreaContabilDTO.getId())
            .map(existingPerfilContadorAreaContabil -> {
                perfilContadorAreaContabilMapper.partialUpdate(existingPerfilContadorAreaContabil, perfilContadorAreaContabilDTO);

                return existingPerfilContadorAreaContabil;
            })
            .map(perfilContadorAreaContabilRepository::save)
            .map(perfilContadorAreaContabilMapper::toDto);
    }

    /**
     * Get all the perfilContadorAreaContabils.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PerfilContadorAreaContabilDTO> findAll() {
        log.debug("Request to get all PerfilContadorAreaContabils");
        return perfilContadorAreaContabilRepository
            .findAll()
            .stream()
            .map(perfilContadorAreaContabilMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the perfilContadorAreaContabils with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PerfilContadorAreaContabilDTO> findAllWithEagerRelationships(Pageable pageable) {
        return perfilContadorAreaContabilRepository.findAllWithEagerRelationships(pageable).map(perfilContadorAreaContabilMapper::toDto);
    }

    /**
     * Get one perfilContadorAreaContabil by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PerfilContadorAreaContabilDTO> findOne(Long id) {
        log.debug("Request to get PerfilContadorAreaContabil : {}", id);
        return perfilContadorAreaContabilRepository.findOneWithEagerRelationships(id).map(perfilContadorAreaContabilMapper::toDto);
    }

    /**
     * Delete the perfilContadorAreaContabil by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PerfilContadorAreaContabil : {}", id);
        perfilContadorAreaContabilRepository.deleteById(id);
    }
}
