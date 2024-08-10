package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.UsuarioEmpresa;
import com.dobemcontabilidade.repository.UsuarioEmpresaRepository;
import com.dobemcontabilidade.service.dto.UsuarioEmpresaDTO;
import com.dobemcontabilidade.service.mapper.UsuarioEmpresaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.UsuarioEmpresa}.
 */
@Service
@Transactional
public class UsuarioEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioEmpresaService.class);

    private final UsuarioEmpresaRepository usuarioEmpresaRepository;

    private final UsuarioEmpresaMapper usuarioEmpresaMapper;

    public UsuarioEmpresaService(UsuarioEmpresaRepository usuarioEmpresaRepository, UsuarioEmpresaMapper usuarioEmpresaMapper) {
        this.usuarioEmpresaRepository = usuarioEmpresaRepository;
        this.usuarioEmpresaMapper = usuarioEmpresaMapper;
    }

    /**
     * Save a usuarioEmpresa.
     *
     * @param usuarioEmpresaDTO the entity to save.
     * @return the persisted entity.
     */
    public UsuarioEmpresaDTO save(UsuarioEmpresaDTO usuarioEmpresaDTO) {
        log.debug("Request to save UsuarioEmpresa : {}", usuarioEmpresaDTO);
        UsuarioEmpresa usuarioEmpresa = usuarioEmpresaMapper.toEntity(usuarioEmpresaDTO);
        usuarioEmpresa = usuarioEmpresaRepository.save(usuarioEmpresa);
        return usuarioEmpresaMapper.toDto(usuarioEmpresa);
    }

    /**
     * Update a usuarioEmpresa.
     *
     * @param usuarioEmpresaDTO the entity to save.
     * @return the persisted entity.
     */
    public UsuarioEmpresaDTO update(UsuarioEmpresaDTO usuarioEmpresaDTO) {
        log.debug("Request to update UsuarioEmpresa : {}", usuarioEmpresaDTO);
        UsuarioEmpresa usuarioEmpresa = usuarioEmpresaMapper.toEntity(usuarioEmpresaDTO);
        usuarioEmpresa = usuarioEmpresaRepository.save(usuarioEmpresa);
        return usuarioEmpresaMapper.toDto(usuarioEmpresa);
    }

    /**
     * Partially update a usuarioEmpresa.
     *
     * @param usuarioEmpresaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UsuarioEmpresaDTO> partialUpdate(UsuarioEmpresaDTO usuarioEmpresaDTO) {
        log.debug("Request to partially update UsuarioEmpresa : {}", usuarioEmpresaDTO);

        return usuarioEmpresaRepository
            .findById(usuarioEmpresaDTO.getId())
            .map(existingUsuarioEmpresa -> {
                usuarioEmpresaMapper.partialUpdate(existingUsuarioEmpresa, usuarioEmpresaDTO);

                return existingUsuarioEmpresa;
            })
            .map(usuarioEmpresaRepository::save)
            .map(usuarioEmpresaMapper::toDto);
    }

    /**
     * Get all the usuarioEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<UsuarioEmpresaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return usuarioEmpresaRepository.findAllWithEagerRelationships(pageable).map(usuarioEmpresaMapper::toDto);
    }

    /**
     * Get one usuarioEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UsuarioEmpresaDTO> findOne(Long id) {
        log.debug("Request to get UsuarioEmpresa : {}", id);
        return usuarioEmpresaRepository.findOneWithEagerRelationships(id).map(usuarioEmpresaMapper::toDto);
    }

    /**
     * Delete the usuarioEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UsuarioEmpresa : {}", id);
        usuarioEmpresaRepository.deleteById(id);
    }
}
