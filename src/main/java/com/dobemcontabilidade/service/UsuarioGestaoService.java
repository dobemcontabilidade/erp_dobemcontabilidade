package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.UsuarioGestao;
import com.dobemcontabilidade.repository.UsuarioGestaoRepository;
import com.dobemcontabilidade.service.dto.UsuarioGestaoDTO;
import com.dobemcontabilidade.service.mapper.UsuarioGestaoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.UsuarioGestao}.
 */
@Service
@Transactional
public class UsuarioGestaoService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioGestaoService.class);

    private final UsuarioGestaoRepository usuarioGestaoRepository;

    private final UsuarioGestaoMapper usuarioGestaoMapper;

    public UsuarioGestaoService(UsuarioGestaoRepository usuarioGestaoRepository, UsuarioGestaoMapper usuarioGestaoMapper) {
        this.usuarioGestaoRepository = usuarioGestaoRepository;
        this.usuarioGestaoMapper = usuarioGestaoMapper;
    }

    /**
     * Save a usuarioGestao.
     *
     * @param usuarioGestaoDTO the entity to save.
     * @return the persisted entity.
     */
    public UsuarioGestaoDTO save(UsuarioGestaoDTO usuarioGestaoDTO) {
        log.debug("Request to save UsuarioGestao : {}", usuarioGestaoDTO);
        UsuarioGestao usuarioGestao = usuarioGestaoMapper.toEntity(usuarioGestaoDTO);
        usuarioGestao = usuarioGestaoRepository.save(usuarioGestao);
        return usuarioGestaoMapper.toDto(usuarioGestao);
    }

    /**
     * Update a usuarioGestao.
     *
     * @param usuarioGestaoDTO the entity to save.
     * @return the persisted entity.
     */
    public UsuarioGestaoDTO update(UsuarioGestaoDTO usuarioGestaoDTO) {
        log.debug("Request to update UsuarioGestao : {}", usuarioGestaoDTO);
        UsuarioGestao usuarioGestao = usuarioGestaoMapper.toEntity(usuarioGestaoDTO);
        usuarioGestao = usuarioGestaoRepository.save(usuarioGestao);
        return usuarioGestaoMapper.toDto(usuarioGestao);
    }

    /**
     * Partially update a usuarioGestao.
     *
     * @param usuarioGestaoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UsuarioGestaoDTO> partialUpdate(UsuarioGestaoDTO usuarioGestaoDTO) {
        log.debug("Request to partially update UsuarioGestao : {}", usuarioGestaoDTO);

        return usuarioGestaoRepository
            .findById(usuarioGestaoDTO.getId())
            .map(existingUsuarioGestao -> {
                usuarioGestaoMapper.partialUpdate(existingUsuarioGestao, usuarioGestaoDTO);

                return existingUsuarioGestao;
            })
            .map(usuarioGestaoRepository::save)
            .map(usuarioGestaoMapper::toDto);
    }

    /**
     * Get all the usuarioGestaos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<UsuarioGestaoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return usuarioGestaoRepository.findAllWithEagerRelationships(pageable).map(usuarioGestaoMapper::toDto);
    }

    /**
     * Get one usuarioGestao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UsuarioGestaoDTO> findOne(Long id) {
        log.debug("Request to get UsuarioGestao : {}", id);
        return usuarioGestaoRepository.findOneWithEagerRelationships(id).map(usuarioGestaoMapper::toDto);
    }

    /**
     * Delete the usuarioGestao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UsuarioGestao : {}", id);
        usuarioGestaoRepository.deleteById(id);
    }
}
