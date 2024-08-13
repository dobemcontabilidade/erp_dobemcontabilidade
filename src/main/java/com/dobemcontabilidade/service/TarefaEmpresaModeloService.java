package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.TarefaEmpresaModelo;
import com.dobemcontabilidade.repository.TarefaEmpresaModeloRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.TarefaEmpresaModelo}.
 */
@Service
@Transactional
public class TarefaEmpresaModeloService {

    private static final Logger log = LoggerFactory.getLogger(TarefaEmpresaModeloService.class);

    private final TarefaEmpresaModeloRepository tarefaEmpresaModeloRepository;

    public TarefaEmpresaModeloService(TarefaEmpresaModeloRepository tarefaEmpresaModeloRepository) {
        this.tarefaEmpresaModeloRepository = tarefaEmpresaModeloRepository;
    }

    /**
     * Save a tarefaEmpresaModelo.
     *
     * @param tarefaEmpresaModelo the entity to save.
     * @return the persisted entity.
     */
    public TarefaEmpresaModelo save(TarefaEmpresaModelo tarefaEmpresaModelo) {
        log.debug("Request to save TarefaEmpresaModelo : {}", tarefaEmpresaModelo);
        return tarefaEmpresaModeloRepository.save(tarefaEmpresaModelo);
    }

    /**
     * Update a tarefaEmpresaModelo.
     *
     * @param tarefaEmpresaModelo the entity to save.
     * @return the persisted entity.
     */
    public TarefaEmpresaModelo update(TarefaEmpresaModelo tarefaEmpresaModelo) {
        log.debug("Request to update TarefaEmpresaModelo : {}", tarefaEmpresaModelo);
        return tarefaEmpresaModeloRepository.save(tarefaEmpresaModelo);
    }

    /**
     * Partially update a tarefaEmpresaModelo.
     *
     * @param tarefaEmpresaModelo the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TarefaEmpresaModelo> partialUpdate(TarefaEmpresaModelo tarefaEmpresaModelo) {
        log.debug("Request to partially update TarefaEmpresaModelo : {}", tarefaEmpresaModelo);

        return tarefaEmpresaModeloRepository
            .findById(tarefaEmpresaModelo.getId())
            .map(existingTarefaEmpresaModelo -> {
                if (tarefaEmpresaModelo.getDataAdmin() != null) {
                    existingTarefaEmpresaModelo.setDataAdmin(tarefaEmpresaModelo.getDataAdmin());
                }
                if (tarefaEmpresaModelo.getDataLegal() != null) {
                    existingTarefaEmpresaModelo.setDataLegal(tarefaEmpresaModelo.getDataLegal());
                }

                return existingTarefaEmpresaModelo;
            })
            .map(tarefaEmpresaModeloRepository::save);
    }

    /**
     * Get all the tarefaEmpresaModelos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TarefaEmpresaModelo> findAll() {
        log.debug("Request to get all TarefaEmpresaModelos");
        return tarefaEmpresaModeloRepository.findAll();
    }

    /**
     * Get all the tarefaEmpresaModelos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TarefaEmpresaModelo> findAllWithEagerRelationships(Pageable pageable) {
        return tarefaEmpresaModeloRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one tarefaEmpresaModelo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TarefaEmpresaModelo> findOne(Long id) {
        log.debug("Request to get TarefaEmpresaModelo : {}", id);
        return tarefaEmpresaModeloRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the tarefaEmpresaModelo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TarefaEmpresaModelo : {}", id);
        tarefaEmpresaModeloRepository.deleteById(id);
    }
}
