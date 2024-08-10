package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AnexoEmpresa;
import com.dobemcontabilidade.repository.AnexoEmpresaRepository;
import com.dobemcontabilidade.service.dto.AnexoEmpresaDTO;
import com.dobemcontabilidade.service.mapper.AnexoEmpresaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AnexoEmpresa}.
 */
@Service
@Transactional
public class AnexoEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(AnexoEmpresaService.class);

    private final AnexoEmpresaRepository anexoEmpresaRepository;

    private final AnexoEmpresaMapper anexoEmpresaMapper;

    public AnexoEmpresaService(AnexoEmpresaRepository anexoEmpresaRepository, AnexoEmpresaMapper anexoEmpresaMapper) {
        this.anexoEmpresaRepository = anexoEmpresaRepository;
        this.anexoEmpresaMapper = anexoEmpresaMapper;
    }

    /**
     * Save a anexoEmpresa.
     *
     * @param anexoEmpresaDTO the entity to save.
     * @return the persisted entity.
     */
    public AnexoEmpresaDTO save(AnexoEmpresaDTO anexoEmpresaDTO) {
        log.debug("Request to save AnexoEmpresa : {}", anexoEmpresaDTO);
        AnexoEmpresa anexoEmpresa = anexoEmpresaMapper.toEntity(anexoEmpresaDTO);
        anexoEmpresa = anexoEmpresaRepository.save(anexoEmpresa);
        return anexoEmpresaMapper.toDto(anexoEmpresa);
    }

    /**
     * Update a anexoEmpresa.
     *
     * @param anexoEmpresaDTO the entity to save.
     * @return the persisted entity.
     */
    public AnexoEmpresaDTO update(AnexoEmpresaDTO anexoEmpresaDTO) {
        log.debug("Request to update AnexoEmpresa : {}", anexoEmpresaDTO);
        AnexoEmpresa anexoEmpresa = anexoEmpresaMapper.toEntity(anexoEmpresaDTO);
        anexoEmpresa = anexoEmpresaRepository.save(anexoEmpresa);
        return anexoEmpresaMapper.toDto(anexoEmpresa);
    }

    /**
     * Partially update a anexoEmpresa.
     *
     * @param anexoEmpresaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AnexoEmpresaDTO> partialUpdate(AnexoEmpresaDTO anexoEmpresaDTO) {
        log.debug("Request to partially update AnexoEmpresa : {}", anexoEmpresaDTO);

        return anexoEmpresaRepository
            .findById(anexoEmpresaDTO.getId())
            .map(existingAnexoEmpresa -> {
                anexoEmpresaMapper.partialUpdate(existingAnexoEmpresa, anexoEmpresaDTO);

                return existingAnexoEmpresa;
            })
            .map(anexoEmpresaRepository::save)
            .map(anexoEmpresaMapper::toDto);
    }

    /**
     * Get all the anexoEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AnexoEmpresaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return anexoEmpresaRepository.findAllWithEagerRelationships(pageable).map(anexoEmpresaMapper::toDto);
    }

    /**
     * Get one anexoEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnexoEmpresaDTO> findOne(Long id) {
        log.debug("Request to get AnexoEmpresa : {}", id);
        return anexoEmpresaRepository.findOneWithEagerRelationships(id).map(anexoEmpresaMapper::toDto);
    }

    /**
     * Delete the anexoEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnexoEmpresa : {}", id);
        anexoEmpresaRepository.deleteById(id);
    }
}
