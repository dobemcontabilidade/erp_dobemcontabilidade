package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.UsuarioErp;
import com.dobemcontabilidade.repository.UsuarioErpRepository;
import com.dobemcontabilidade.service.dto.UsuarioErpDTO;
import com.dobemcontabilidade.service.mapper.UsuarioErpMapper;
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
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.UsuarioErp}.
 */
@Service
@Transactional
public class UsuarioErpService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioErpService.class);

    private final UsuarioErpRepository usuarioErpRepository;

    private final UsuarioErpMapper usuarioErpMapper;

    public UsuarioErpService(UsuarioErpRepository usuarioErpRepository, UsuarioErpMapper usuarioErpMapper) {
        this.usuarioErpRepository = usuarioErpRepository;
        this.usuarioErpMapper = usuarioErpMapper;
    }

    /**
     * Save a usuarioErp.
     *
     * @param usuarioErpDTO the entity to save.
     * @return the persisted entity.
     */
    public UsuarioErpDTO save(UsuarioErpDTO usuarioErpDTO) {
        log.debug("Request to save UsuarioErp : {}", usuarioErpDTO);
        UsuarioErp usuarioErp = usuarioErpMapper.toEntity(usuarioErpDTO);
        usuarioErp = usuarioErpRepository.save(usuarioErp);
        return usuarioErpMapper.toDto(usuarioErp);
    }

    /**
     * Update a usuarioErp.
     *
     * @param usuarioErpDTO the entity to save.
     * @return the persisted entity.
     */
    public UsuarioErpDTO update(UsuarioErpDTO usuarioErpDTO) {
        log.debug("Request to update UsuarioErp : {}", usuarioErpDTO);
        UsuarioErp usuarioErp = usuarioErpMapper.toEntity(usuarioErpDTO);
        usuarioErp = usuarioErpRepository.save(usuarioErp);
        return usuarioErpMapper.toDto(usuarioErp);
    }

    /**
     * Partially update a usuarioErp.
     *
     * @param usuarioErpDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UsuarioErpDTO> partialUpdate(UsuarioErpDTO usuarioErpDTO) {
        log.debug("Request to partially update UsuarioErp : {}", usuarioErpDTO);

        return usuarioErpRepository
            .findById(usuarioErpDTO.getId())
            .map(existingUsuarioErp -> {
                usuarioErpMapper.partialUpdate(existingUsuarioErp, usuarioErpDTO);

                return existingUsuarioErp;
            })
            .map(usuarioErpRepository::save)
            .map(usuarioErpMapper::toDto);
    }

    /**
     * Get all the usuarioErps.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UsuarioErpDTO> findAll() {
        log.debug("Request to get all UsuarioErps");
        return usuarioErpRepository.findAll().stream().map(usuarioErpMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the usuarioErps with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<UsuarioErpDTO> findAllWithEagerRelationships(Pageable pageable) {
        return usuarioErpRepository.findAllWithEagerRelationships(pageable).map(usuarioErpMapper::toDto);
    }

    /**
     * Get one usuarioErp by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UsuarioErpDTO> findOne(Long id) {
        log.debug("Request to get UsuarioErp : {}", id);
        return usuarioErpRepository.findOneWithEagerRelationships(id).map(usuarioErpMapper::toDto);
    }

    /**
     * Delete the usuarioErp by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UsuarioErp : {}", id);
        usuarioErpRepository.deleteById(id);
    }
}
