package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.OpcaoRazaoSocialEmpresa;
import com.dobemcontabilidade.repository.OpcaoRazaoSocialEmpresaRepository;
import com.dobemcontabilidade.service.dto.OpcaoRazaoSocialEmpresaDTO;
import com.dobemcontabilidade.service.mapper.OpcaoRazaoSocialEmpresaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.OpcaoRazaoSocialEmpresa}.
 */
@Service
@Transactional
public class OpcaoRazaoSocialEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(OpcaoRazaoSocialEmpresaService.class);

    private final OpcaoRazaoSocialEmpresaRepository opcaoRazaoSocialEmpresaRepository;

    private final OpcaoRazaoSocialEmpresaMapper opcaoRazaoSocialEmpresaMapper;

    public OpcaoRazaoSocialEmpresaService(
        OpcaoRazaoSocialEmpresaRepository opcaoRazaoSocialEmpresaRepository,
        OpcaoRazaoSocialEmpresaMapper opcaoRazaoSocialEmpresaMapper
    ) {
        this.opcaoRazaoSocialEmpresaRepository = opcaoRazaoSocialEmpresaRepository;
        this.opcaoRazaoSocialEmpresaMapper = opcaoRazaoSocialEmpresaMapper;
    }

    /**
     * Save a opcaoRazaoSocialEmpresa.
     *
     * @param opcaoRazaoSocialEmpresaDTO the entity to save.
     * @return the persisted entity.
     */
    public OpcaoRazaoSocialEmpresaDTO save(OpcaoRazaoSocialEmpresaDTO opcaoRazaoSocialEmpresaDTO) {
        log.debug("Request to save OpcaoRazaoSocialEmpresa : {}", opcaoRazaoSocialEmpresaDTO);
        OpcaoRazaoSocialEmpresa opcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaMapper.toEntity(opcaoRazaoSocialEmpresaDTO);
        opcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaRepository.save(opcaoRazaoSocialEmpresa);
        return opcaoRazaoSocialEmpresaMapper.toDto(opcaoRazaoSocialEmpresa);
    }

    /**
     * Update a opcaoRazaoSocialEmpresa.
     *
     * @param opcaoRazaoSocialEmpresaDTO the entity to save.
     * @return the persisted entity.
     */
    public OpcaoRazaoSocialEmpresaDTO update(OpcaoRazaoSocialEmpresaDTO opcaoRazaoSocialEmpresaDTO) {
        log.debug("Request to update OpcaoRazaoSocialEmpresa : {}", opcaoRazaoSocialEmpresaDTO);
        OpcaoRazaoSocialEmpresa opcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaMapper.toEntity(opcaoRazaoSocialEmpresaDTO);
        opcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaRepository.save(opcaoRazaoSocialEmpresa);
        return opcaoRazaoSocialEmpresaMapper.toDto(opcaoRazaoSocialEmpresa);
    }

    /**
     * Partially update a opcaoRazaoSocialEmpresa.
     *
     * @param opcaoRazaoSocialEmpresaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OpcaoRazaoSocialEmpresaDTO> partialUpdate(OpcaoRazaoSocialEmpresaDTO opcaoRazaoSocialEmpresaDTO) {
        log.debug("Request to partially update OpcaoRazaoSocialEmpresa : {}", opcaoRazaoSocialEmpresaDTO);

        return opcaoRazaoSocialEmpresaRepository
            .findById(opcaoRazaoSocialEmpresaDTO.getId())
            .map(existingOpcaoRazaoSocialEmpresa -> {
                opcaoRazaoSocialEmpresaMapper.partialUpdate(existingOpcaoRazaoSocialEmpresa, opcaoRazaoSocialEmpresaDTO);

                return existingOpcaoRazaoSocialEmpresa;
            })
            .map(opcaoRazaoSocialEmpresaRepository::save)
            .map(opcaoRazaoSocialEmpresaMapper::toDto);
    }

    /**
     * Get all the opcaoRazaoSocialEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<OpcaoRazaoSocialEmpresaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return opcaoRazaoSocialEmpresaRepository.findAllWithEagerRelationships(pageable).map(opcaoRazaoSocialEmpresaMapper::toDto);
    }

    /**
     * Get one opcaoRazaoSocialEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OpcaoRazaoSocialEmpresaDTO> findOne(Long id) {
        log.debug("Request to get OpcaoRazaoSocialEmpresa : {}", id);
        return opcaoRazaoSocialEmpresaRepository.findOneWithEagerRelationships(id).map(opcaoRazaoSocialEmpresaMapper::toDto);
    }

    /**
     * Delete the opcaoRazaoSocialEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OpcaoRazaoSocialEmpresa : {}", id);
        opcaoRazaoSocialEmpresaRepository.deleteById(id);
    }
}
