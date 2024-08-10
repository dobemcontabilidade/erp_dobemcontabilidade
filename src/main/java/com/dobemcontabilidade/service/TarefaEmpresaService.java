package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.TarefaEmpresa;
import com.dobemcontabilidade.repository.TarefaEmpresaRepository;
import com.dobemcontabilidade.service.dto.TarefaEmpresaDTO;
import com.dobemcontabilidade.service.mapper.TarefaEmpresaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.TarefaEmpresa}.
 */
@Service
@Transactional
public class TarefaEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(TarefaEmpresaService.class);

    private final TarefaEmpresaRepository tarefaEmpresaRepository;

    private final TarefaEmpresaMapper tarefaEmpresaMapper;

    public TarefaEmpresaService(TarefaEmpresaRepository tarefaEmpresaRepository, TarefaEmpresaMapper tarefaEmpresaMapper) {
        this.tarefaEmpresaRepository = tarefaEmpresaRepository;
        this.tarefaEmpresaMapper = tarefaEmpresaMapper;
    }

    /**
     * Save a tarefaEmpresa.
     *
     * @param tarefaEmpresaDTO the entity to save.
     * @return the persisted entity.
     */
    public TarefaEmpresaDTO save(TarefaEmpresaDTO tarefaEmpresaDTO) {
        log.debug("Request to save TarefaEmpresa : {}", tarefaEmpresaDTO);
        TarefaEmpresa tarefaEmpresa = tarefaEmpresaMapper.toEntity(tarefaEmpresaDTO);
        tarefaEmpresa = tarefaEmpresaRepository.save(tarefaEmpresa);
        return tarefaEmpresaMapper.toDto(tarefaEmpresa);
    }

    /**
     * Update a tarefaEmpresa.
     *
     * @param tarefaEmpresaDTO the entity to save.
     * @return the persisted entity.
     */
    public TarefaEmpresaDTO update(TarefaEmpresaDTO tarefaEmpresaDTO) {
        log.debug("Request to update TarefaEmpresa : {}", tarefaEmpresaDTO);
        TarefaEmpresa tarefaEmpresa = tarefaEmpresaMapper.toEntity(tarefaEmpresaDTO);
        tarefaEmpresa = tarefaEmpresaRepository.save(tarefaEmpresa);
        return tarefaEmpresaMapper.toDto(tarefaEmpresa);
    }

    /**
     * Partially update a tarefaEmpresa.
     *
     * @param tarefaEmpresaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TarefaEmpresaDTO> partialUpdate(TarefaEmpresaDTO tarefaEmpresaDTO) {
        log.debug("Request to partially update TarefaEmpresa : {}", tarefaEmpresaDTO);

        return tarefaEmpresaRepository
            .findById(tarefaEmpresaDTO.getId())
            .map(existingTarefaEmpresa -> {
                tarefaEmpresaMapper.partialUpdate(existingTarefaEmpresa, tarefaEmpresaDTO);

                return existingTarefaEmpresa;
            })
            .map(tarefaEmpresaRepository::save)
            .map(tarefaEmpresaMapper::toDto);
    }

    /**
     * Get all the tarefaEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TarefaEmpresaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return tarefaEmpresaRepository.findAllWithEagerRelationships(pageable).map(tarefaEmpresaMapper::toDto);
    }

    /**
     * Get one tarefaEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TarefaEmpresaDTO> findOne(Long id) {
        log.debug("Request to get TarefaEmpresa : {}", id);
        return tarefaEmpresaRepository.findOneWithEagerRelationships(id).map(tarefaEmpresaMapper::toDto);
    }

    /**
     * Delete the tarefaEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TarefaEmpresa : {}", id);
        tarefaEmpresaRepository.deleteById(id);
    }
}
