package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AtividadeEmpresa;
import com.dobemcontabilidade.repository.AtividadeEmpresaRepository;
import com.dobemcontabilidade.service.dto.AtividadeEmpresaDTO;
import com.dobemcontabilidade.service.mapper.AtividadeEmpresaMapper;
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
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AtividadeEmpresa}.
 */
@Service
@Transactional
public class AtividadeEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(AtividadeEmpresaService.class);

    private final AtividadeEmpresaRepository atividadeEmpresaRepository;

    private final AtividadeEmpresaMapper atividadeEmpresaMapper;

    public AtividadeEmpresaService(AtividadeEmpresaRepository atividadeEmpresaRepository, AtividadeEmpresaMapper atividadeEmpresaMapper) {
        this.atividadeEmpresaRepository = atividadeEmpresaRepository;
        this.atividadeEmpresaMapper = atividadeEmpresaMapper;
    }

    /**
     * Save a atividadeEmpresa.
     *
     * @param atividadeEmpresaDTO the entity to save.
     * @return the persisted entity.
     */
    public AtividadeEmpresaDTO save(AtividadeEmpresaDTO atividadeEmpresaDTO) {
        log.debug("Request to save AtividadeEmpresa : {}", atividadeEmpresaDTO);
        AtividadeEmpresa atividadeEmpresa = atividadeEmpresaMapper.toEntity(atividadeEmpresaDTO);
        atividadeEmpresa = atividadeEmpresaRepository.save(atividadeEmpresa);
        return atividadeEmpresaMapper.toDto(atividadeEmpresa);
    }

    /**
     * Update a atividadeEmpresa.
     *
     * @param atividadeEmpresaDTO the entity to save.
     * @return the persisted entity.
     */
    public AtividadeEmpresaDTO update(AtividadeEmpresaDTO atividadeEmpresaDTO) {
        log.debug("Request to update AtividadeEmpresa : {}", atividadeEmpresaDTO);
        AtividadeEmpresa atividadeEmpresa = atividadeEmpresaMapper.toEntity(atividadeEmpresaDTO);
        atividadeEmpresa = atividadeEmpresaRepository.save(atividadeEmpresa);
        return atividadeEmpresaMapper.toDto(atividadeEmpresa);
    }

    /**
     * Partially update a atividadeEmpresa.
     *
     * @param atividadeEmpresaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AtividadeEmpresaDTO> partialUpdate(AtividadeEmpresaDTO atividadeEmpresaDTO) {
        log.debug("Request to partially update AtividadeEmpresa : {}", atividadeEmpresaDTO);

        return atividadeEmpresaRepository
            .findById(atividadeEmpresaDTO.getId())
            .map(existingAtividadeEmpresa -> {
                atividadeEmpresaMapper.partialUpdate(existingAtividadeEmpresa, atividadeEmpresaDTO);

                return existingAtividadeEmpresa;
            })
            .map(atividadeEmpresaRepository::save)
            .map(atividadeEmpresaMapper::toDto);
    }

    /**
     * Get all the atividadeEmpresas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AtividadeEmpresaDTO> findAll() {
        log.debug("Request to get all AtividadeEmpresas");
        return atividadeEmpresaRepository
            .findAll()
            .stream()
            .map(atividadeEmpresaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the atividadeEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AtividadeEmpresaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return atividadeEmpresaRepository.findAllWithEagerRelationships(pageable).map(atividadeEmpresaMapper::toDto);
    }

    /**
     * Get one atividadeEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AtividadeEmpresaDTO> findOne(Long id) {
        log.debug("Request to get AtividadeEmpresa : {}", id);
        return atividadeEmpresaRepository.findOneWithEagerRelationships(id).map(atividadeEmpresaMapper::toDto);
    }

    /**
     * Delete the atividadeEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AtividadeEmpresa : {}", id);
        atividadeEmpresaRepository.deleteById(id);
    }
}
