package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.TarefaEmpresa;
import com.dobemcontabilidade.repository.TarefaEmpresaRepository;
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

    public TarefaEmpresaService(TarefaEmpresaRepository tarefaEmpresaRepository) {
        this.tarefaEmpresaRepository = tarefaEmpresaRepository;
    }

    /**
     * Save a tarefaEmpresa.
     *
     * @param tarefaEmpresa the entity to save.
     * @return the persisted entity.
     */
    public TarefaEmpresa save(TarefaEmpresa tarefaEmpresa) {
        log.debug("Request to save TarefaEmpresa : {}", tarefaEmpresa);
        return tarefaEmpresaRepository.save(tarefaEmpresa);
    }

    /**
     * Update a tarefaEmpresa.
     *
     * @param tarefaEmpresa the entity to save.
     * @return the persisted entity.
     */
    public TarefaEmpresa update(TarefaEmpresa tarefaEmpresa) {
        log.debug("Request to update TarefaEmpresa : {}", tarefaEmpresa);
        return tarefaEmpresaRepository.save(tarefaEmpresa);
    }

    /**
     * Partially update a tarefaEmpresa.
     *
     * @param tarefaEmpresa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TarefaEmpresa> partialUpdate(TarefaEmpresa tarefaEmpresa) {
        log.debug("Request to partially update TarefaEmpresa : {}", tarefaEmpresa);

        return tarefaEmpresaRepository
            .findById(tarefaEmpresa.getId())
            .map(existingTarefaEmpresa -> {
                if (tarefaEmpresa.getDataHora() != null) {
                    existingTarefaEmpresa.setDataHora(tarefaEmpresa.getDataHora());
                }

                return existingTarefaEmpresa;
            })
            .map(tarefaEmpresaRepository::save);
    }

    /**
     * Get all the tarefaEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TarefaEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return tarefaEmpresaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one tarefaEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TarefaEmpresa> findOne(Long id) {
        log.debug("Request to get TarefaEmpresa : {}", id);
        return tarefaEmpresaRepository.findOneWithEagerRelationships(id);
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
