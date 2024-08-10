package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.UsuarioContador;
import com.dobemcontabilidade.repository.UsuarioContadorRepository;
import com.dobemcontabilidade.service.dto.UsuarioContadorDTO;
import com.dobemcontabilidade.service.mapper.UsuarioContadorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.UsuarioContador}.
 */
@Service
@Transactional
public class UsuarioContadorService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioContadorService.class);

    private final UsuarioContadorRepository usuarioContadorRepository;

    private final UsuarioContadorMapper usuarioContadorMapper;

    public UsuarioContadorService(UsuarioContadorRepository usuarioContadorRepository, UsuarioContadorMapper usuarioContadorMapper) {
        this.usuarioContadorRepository = usuarioContadorRepository;
        this.usuarioContadorMapper = usuarioContadorMapper;
    }

    /**
     * Save a usuarioContador.
     *
     * @param usuarioContadorDTO the entity to save.
     * @return the persisted entity.
     */
    public UsuarioContadorDTO save(UsuarioContadorDTO usuarioContadorDTO) {
        log.debug("Request to save UsuarioContador : {}", usuarioContadorDTO);
        UsuarioContador usuarioContador = usuarioContadorMapper.toEntity(usuarioContadorDTO);
        usuarioContador = usuarioContadorRepository.save(usuarioContador);
        return usuarioContadorMapper.toDto(usuarioContador);
    }

    /**
     * Update a usuarioContador.
     *
     * @param usuarioContadorDTO the entity to save.
     * @return the persisted entity.
     */
    public UsuarioContadorDTO update(UsuarioContadorDTO usuarioContadorDTO) {
        log.debug("Request to update UsuarioContador : {}", usuarioContadorDTO);
        UsuarioContador usuarioContador = usuarioContadorMapper.toEntity(usuarioContadorDTO);
        usuarioContador = usuarioContadorRepository.save(usuarioContador);
        return usuarioContadorMapper.toDto(usuarioContador);
    }

    /**
     * Partially update a usuarioContador.
     *
     * @param usuarioContadorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UsuarioContadorDTO> partialUpdate(UsuarioContadorDTO usuarioContadorDTO) {
        log.debug("Request to partially update UsuarioContador : {}", usuarioContadorDTO);

        return usuarioContadorRepository
            .findById(usuarioContadorDTO.getId())
            .map(existingUsuarioContador -> {
                usuarioContadorMapper.partialUpdate(existingUsuarioContador, usuarioContadorDTO);

                return existingUsuarioContador;
            })
            .map(usuarioContadorRepository::save)
            .map(usuarioContadorMapper::toDto);
    }

    /**
     * Get all the usuarioContadors with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<UsuarioContadorDTO> findAllWithEagerRelationships(Pageable pageable) {
        return usuarioContadorRepository.findAllWithEagerRelationships(pageable).map(usuarioContadorMapper::toDto);
    }

    /**
     * Get one usuarioContador by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UsuarioContadorDTO> findOne(Long id) {
        log.debug("Request to get UsuarioContador : {}", id);
        return usuarioContadorRepository.findOneWithEagerRelationships(id).map(usuarioContadorMapper::toDto);
    }

    /**
     * Delete the usuarioContador by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UsuarioContador : {}", id);
        usuarioContadorRepository.deleteById(id);
    }
}
