package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AreaContabilEmpresa;
import com.dobemcontabilidade.repository.AreaContabilEmpresaRepository;
import com.dobemcontabilidade.service.dto.AreaContabilEmpresaDTO;
import com.dobemcontabilidade.service.mapper.AreaContabilEmpresaMapper;
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
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AreaContabilEmpresa}.
 */
@Service
@Transactional
public class AreaContabilEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(AreaContabilEmpresaService.class);

    private final AreaContabilEmpresaRepository areaContabilEmpresaRepository;

    private final AreaContabilEmpresaMapper areaContabilEmpresaMapper;

    public AreaContabilEmpresaService(
        AreaContabilEmpresaRepository areaContabilEmpresaRepository,
        AreaContabilEmpresaMapper areaContabilEmpresaMapper
    ) {
        this.areaContabilEmpresaRepository = areaContabilEmpresaRepository;
        this.areaContabilEmpresaMapper = areaContabilEmpresaMapper;
    }

    /**
     * Save a areaContabilEmpresa.
     *
     * @param areaContabilEmpresaDTO the entity to save.
     * @return the persisted entity.
     */
    public AreaContabilEmpresaDTO save(AreaContabilEmpresaDTO areaContabilEmpresaDTO) {
        log.debug("Request to save AreaContabilEmpresa : {}", areaContabilEmpresaDTO);
        AreaContabilEmpresa areaContabilEmpresa = areaContabilEmpresaMapper.toEntity(areaContabilEmpresaDTO);
        areaContabilEmpresa = areaContabilEmpresaRepository.save(areaContabilEmpresa);
        return areaContabilEmpresaMapper.toDto(areaContabilEmpresa);
    }

    /**
     * Update a areaContabilEmpresa.
     *
     * @param areaContabilEmpresaDTO the entity to save.
     * @return the persisted entity.
     */
    public AreaContabilEmpresaDTO update(AreaContabilEmpresaDTO areaContabilEmpresaDTO) {
        log.debug("Request to update AreaContabilEmpresa : {}", areaContabilEmpresaDTO);
        AreaContabilEmpresa areaContabilEmpresa = areaContabilEmpresaMapper.toEntity(areaContabilEmpresaDTO);
        areaContabilEmpresa = areaContabilEmpresaRepository.save(areaContabilEmpresa);
        return areaContabilEmpresaMapper.toDto(areaContabilEmpresa);
    }

    /**
     * Partially update a areaContabilEmpresa.
     *
     * @param areaContabilEmpresaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AreaContabilEmpresaDTO> partialUpdate(AreaContabilEmpresaDTO areaContabilEmpresaDTO) {
        log.debug("Request to partially update AreaContabilEmpresa : {}", areaContabilEmpresaDTO);

        return areaContabilEmpresaRepository
            .findById(areaContabilEmpresaDTO.getId())
            .map(existingAreaContabilEmpresa -> {
                areaContabilEmpresaMapper.partialUpdate(existingAreaContabilEmpresa, areaContabilEmpresaDTO);

                return existingAreaContabilEmpresa;
            })
            .map(areaContabilEmpresaRepository::save)
            .map(areaContabilEmpresaMapper::toDto);
    }

    /**
     * Get all the areaContabilEmpresas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AreaContabilEmpresaDTO> findAll() {
        log.debug("Request to get all AreaContabilEmpresas");
        return areaContabilEmpresaRepository
            .findAll()
            .stream()
            .map(areaContabilEmpresaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the areaContabilEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AreaContabilEmpresaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return areaContabilEmpresaRepository.findAllWithEagerRelationships(pageable).map(areaContabilEmpresaMapper::toDto);
    }

    /**
     * Get one areaContabilEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AreaContabilEmpresaDTO> findOne(Long id) {
        log.debug("Request to get AreaContabilEmpresa : {}", id);
        return areaContabilEmpresaRepository.findOneWithEagerRelationships(id).map(areaContabilEmpresaMapper::toDto);
    }

    /**
     * Delete the areaContabilEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AreaContabilEmpresa : {}", id);
        areaContabilEmpresaRepository.deleteById(id);
    }
}
