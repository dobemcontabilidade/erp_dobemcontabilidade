package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AreaContabilContador;
import com.dobemcontabilidade.repository.AreaContabilContadorRepository;
import com.dobemcontabilidade.service.dto.AreaContabilContadorDTO;
import com.dobemcontabilidade.service.mapper.AreaContabilContadorMapper;
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
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AreaContabilContador}.
 */
@Service
@Transactional
public class AreaContabilContadorService {

    private static final Logger log = LoggerFactory.getLogger(AreaContabilContadorService.class);

    private final AreaContabilContadorRepository areaContabilContadorRepository;

    private final AreaContabilContadorMapper areaContabilContadorMapper;

    public AreaContabilContadorService(
        AreaContabilContadorRepository areaContabilContadorRepository,
        AreaContabilContadorMapper areaContabilContadorMapper
    ) {
        this.areaContabilContadorRepository = areaContabilContadorRepository;
        this.areaContabilContadorMapper = areaContabilContadorMapper;
    }

    /**
     * Save a areaContabilContador.
     *
     * @param areaContabilContadorDTO the entity to save.
     * @return the persisted entity.
     */
    public AreaContabilContadorDTO save(AreaContabilContadorDTO areaContabilContadorDTO) {
        log.debug("Request to save AreaContabilContador : {}", areaContabilContadorDTO);
        AreaContabilContador areaContabilContador = areaContabilContadorMapper.toEntity(areaContabilContadorDTO);
        areaContabilContador = areaContabilContadorRepository.save(areaContabilContador);
        return areaContabilContadorMapper.toDto(areaContabilContador);
    }

    /**
     * Update a areaContabilContador.
     *
     * @param areaContabilContadorDTO the entity to save.
     * @return the persisted entity.
     */
    public AreaContabilContadorDTO update(AreaContabilContadorDTO areaContabilContadorDTO) {
        log.debug("Request to update AreaContabilContador : {}", areaContabilContadorDTO);
        AreaContabilContador areaContabilContador = areaContabilContadorMapper.toEntity(areaContabilContadorDTO);
        areaContabilContador = areaContabilContadorRepository.save(areaContabilContador);
        return areaContabilContadorMapper.toDto(areaContabilContador);
    }

    /**
     * Partially update a areaContabilContador.
     *
     * @param areaContabilContadorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AreaContabilContadorDTO> partialUpdate(AreaContabilContadorDTO areaContabilContadorDTO) {
        log.debug("Request to partially update AreaContabilContador : {}", areaContabilContadorDTO);

        return areaContabilContadorRepository
            .findById(areaContabilContadorDTO.getId())
            .map(existingAreaContabilContador -> {
                areaContabilContadorMapper.partialUpdate(existingAreaContabilContador, areaContabilContadorDTO);

                return existingAreaContabilContador;
            })
            .map(areaContabilContadorRepository::save)
            .map(areaContabilContadorMapper::toDto);
    }

    /**
     * Get all the areaContabilContadors.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AreaContabilContadorDTO> findAll() {
        log.debug("Request to get all AreaContabilContadors");
        return areaContabilContadorRepository
            .findAll()
            .stream()
            .map(areaContabilContadorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the areaContabilContadors with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AreaContabilContadorDTO> findAllWithEagerRelationships(Pageable pageable) {
        return areaContabilContadorRepository.findAllWithEagerRelationships(pageable).map(areaContabilContadorMapper::toDto);
    }

    /**
     * Get one areaContabilContador by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AreaContabilContadorDTO> findOne(Long id) {
        log.debug("Request to get AreaContabilContador : {}", id);
        return areaContabilContadorRepository.findOneWithEagerRelationships(id).map(areaContabilContadorMapper::toDto);
    }

    /**
     * Delete the areaContabilContador by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AreaContabilContador : {}", id);
        areaContabilContadorRepository.deleteById(id);
    }
}
