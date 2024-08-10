package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.OpcaoNomeFantasiaEmpresa;
import com.dobemcontabilidade.repository.OpcaoNomeFantasiaEmpresaRepository;
import com.dobemcontabilidade.service.dto.OpcaoNomeFantasiaEmpresaDTO;
import com.dobemcontabilidade.service.mapper.OpcaoNomeFantasiaEmpresaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.OpcaoNomeFantasiaEmpresa}.
 */
@Service
@Transactional
public class OpcaoNomeFantasiaEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(OpcaoNomeFantasiaEmpresaService.class);

    private final OpcaoNomeFantasiaEmpresaRepository opcaoNomeFantasiaEmpresaRepository;

    private final OpcaoNomeFantasiaEmpresaMapper opcaoNomeFantasiaEmpresaMapper;

    public OpcaoNomeFantasiaEmpresaService(
        OpcaoNomeFantasiaEmpresaRepository opcaoNomeFantasiaEmpresaRepository,
        OpcaoNomeFantasiaEmpresaMapper opcaoNomeFantasiaEmpresaMapper
    ) {
        this.opcaoNomeFantasiaEmpresaRepository = opcaoNomeFantasiaEmpresaRepository;
        this.opcaoNomeFantasiaEmpresaMapper = opcaoNomeFantasiaEmpresaMapper;
    }

    /**
     * Save a opcaoNomeFantasiaEmpresa.
     *
     * @param opcaoNomeFantasiaEmpresaDTO the entity to save.
     * @return the persisted entity.
     */
    public OpcaoNomeFantasiaEmpresaDTO save(OpcaoNomeFantasiaEmpresaDTO opcaoNomeFantasiaEmpresaDTO) {
        log.debug("Request to save OpcaoNomeFantasiaEmpresa : {}", opcaoNomeFantasiaEmpresaDTO);
        OpcaoNomeFantasiaEmpresa opcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresaMapper.toEntity(opcaoNomeFantasiaEmpresaDTO);
        opcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresaRepository.save(opcaoNomeFantasiaEmpresa);
        return opcaoNomeFantasiaEmpresaMapper.toDto(opcaoNomeFantasiaEmpresa);
    }

    /**
     * Update a opcaoNomeFantasiaEmpresa.
     *
     * @param opcaoNomeFantasiaEmpresaDTO the entity to save.
     * @return the persisted entity.
     */
    public OpcaoNomeFantasiaEmpresaDTO update(OpcaoNomeFantasiaEmpresaDTO opcaoNomeFantasiaEmpresaDTO) {
        log.debug("Request to update OpcaoNomeFantasiaEmpresa : {}", opcaoNomeFantasiaEmpresaDTO);
        OpcaoNomeFantasiaEmpresa opcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresaMapper.toEntity(opcaoNomeFantasiaEmpresaDTO);
        opcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresaRepository.save(opcaoNomeFantasiaEmpresa);
        return opcaoNomeFantasiaEmpresaMapper.toDto(opcaoNomeFantasiaEmpresa);
    }

    /**
     * Partially update a opcaoNomeFantasiaEmpresa.
     *
     * @param opcaoNomeFantasiaEmpresaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OpcaoNomeFantasiaEmpresaDTO> partialUpdate(OpcaoNomeFantasiaEmpresaDTO opcaoNomeFantasiaEmpresaDTO) {
        log.debug("Request to partially update OpcaoNomeFantasiaEmpresa : {}", opcaoNomeFantasiaEmpresaDTO);

        return opcaoNomeFantasiaEmpresaRepository
            .findById(opcaoNomeFantasiaEmpresaDTO.getId())
            .map(existingOpcaoNomeFantasiaEmpresa -> {
                opcaoNomeFantasiaEmpresaMapper.partialUpdate(existingOpcaoNomeFantasiaEmpresa, opcaoNomeFantasiaEmpresaDTO);

                return existingOpcaoNomeFantasiaEmpresa;
            })
            .map(opcaoNomeFantasiaEmpresaRepository::save)
            .map(opcaoNomeFantasiaEmpresaMapper::toDto);
    }

    /**
     * Get all the opcaoNomeFantasiaEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<OpcaoNomeFantasiaEmpresaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return opcaoNomeFantasiaEmpresaRepository.findAllWithEagerRelationships(pageable).map(opcaoNomeFantasiaEmpresaMapper::toDto);
    }

    /**
     * Get one opcaoNomeFantasiaEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OpcaoNomeFantasiaEmpresaDTO> findOne(Long id) {
        log.debug("Request to get OpcaoNomeFantasiaEmpresa : {}", id);
        return opcaoNomeFantasiaEmpresaRepository.findOneWithEagerRelationships(id).map(opcaoNomeFantasiaEmpresaMapper::toDto);
    }

    /**
     * Delete the opcaoNomeFantasiaEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OpcaoNomeFantasiaEmpresa : {}", id);
        opcaoNomeFantasiaEmpresaRepository.deleteById(id);
    }
}
