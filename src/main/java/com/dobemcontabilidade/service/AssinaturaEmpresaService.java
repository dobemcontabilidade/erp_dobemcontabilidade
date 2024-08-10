package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AssinaturaEmpresa;
import com.dobemcontabilidade.repository.AssinaturaEmpresaRepository;
import com.dobemcontabilidade.service.dto.AssinaturaEmpresaDTO;
import com.dobemcontabilidade.service.mapper.AssinaturaEmpresaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AssinaturaEmpresa}.
 */
@Service
@Transactional
public class AssinaturaEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(AssinaturaEmpresaService.class);

    private final AssinaturaEmpresaRepository assinaturaEmpresaRepository;

    private final AssinaturaEmpresaMapper assinaturaEmpresaMapper;

    public AssinaturaEmpresaService(
        AssinaturaEmpresaRepository assinaturaEmpresaRepository,
        AssinaturaEmpresaMapper assinaturaEmpresaMapper
    ) {
        this.assinaturaEmpresaRepository = assinaturaEmpresaRepository;
        this.assinaturaEmpresaMapper = assinaturaEmpresaMapper;
    }

    /**
     * Save a assinaturaEmpresa.
     *
     * @param assinaturaEmpresaDTO the entity to save.
     * @return the persisted entity.
     */
    public AssinaturaEmpresaDTO save(AssinaturaEmpresaDTO assinaturaEmpresaDTO) {
        log.debug("Request to save AssinaturaEmpresa : {}", assinaturaEmpresaDTO);
        AssinaturaEmpresa assinaturaEmpresa = assinaturaEmpresaMapper.toEntity(assinaturaEmpresaDTO);
        assinaturaEmpresa = assinaturaEmpresaRepository.save(assinaturaEmpresa);
        return assinaturaEmpresaMapper.toDto(assinaturaEmpresa);
    }

    /**
     * Update a assinaturaEmpresa.
     *
     * @param assinaturaEmpresaDTO the entity to save.
     * @return the persisted entity.
     */
    public AssinaturaEmpresaDTO update(AssinaturaEmpresaDTO assinaturaEmpresaDTO) {
        log.debug("Request to update AssinaturaEmpresa : {}", assinaturaEmpresaDTO);
        AssinaturaEmpresa assinaturaEmpresa = assinaturaEmpresaMapper.toEntity(assinaturaEmpresaDTO);
        assinaturaEmpresa = assinaturaEmpresaRepository.save(assinaturaEmpresa);
        return assinaturaEmpresaMapper.toDto(assinaturaEmpresa);
    }

    /**
     * Partially update a assinaturaEmpresa.
     *
     * @param assinaturaEmpresaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AssinaturaEmpresaDTO> partialUpdate(AssinaturaEmpresaDTO assinaturaEmpresaDTO) {
        log.debug("Request to partially update AssinaturaEmpresa : {}", assinaturaEmpresaDTO);

        return assinaturaEmpresaRepository
            .findById(assinaturaEmpresaDTO.getId())
            .map(existingAssinaturaEmpresa -> {
                assinaturaEmpresaMapper.partialUpdate(existingAssinaturaEmpresa, assinaturaEmpresaDTO);

                return existingAssinaturaEmpresa;
            })
            .map(assinaturaEmpresaRepository::save)
            .map(assinaturaEmpresaMapper::toDto);
    }

    /**
     * Get all the assinaturaEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AssinaturaEmpresaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return assinaturaEmpresaRepository.findAllWithEagerRelationships(pageable).map(assinaturaEmpresaMapper::toDto);
    }

    /**
     * Get one assinaturaEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AssinaturaEmpresaDTO> findOne(Long id) {
        log.debug("Request to get AssinaturaEmpresa : {}", id);
        return assinaturaEmpresaRepository.findOneWithEagerRelationships(id).map(assinaturaEmpresaMapper::toDto);
    }

    /**
     * Delete the assinaturaEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AssinaturaEmpresa : {}", id);
        assinaturaEmpresaRepository.deleteById(id);
    }
}
