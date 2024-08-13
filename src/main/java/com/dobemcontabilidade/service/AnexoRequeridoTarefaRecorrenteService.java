package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AnexoRequeridoTarefaRecorrente;
import com.dobemcontabilidade.repository.AnexoRequeridoTarefaRecorrenteRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AnexoRequeridoTarefaRecorrente}.
 */
@Service
@Transactional
public class AnexoRequeridoTarefaRecorrenteService {

    private static final Logger log = LoggerFactory.getLogger(AnexoRequeridoTarefaRecorrenteService.class);

    private final AnexoRequeridoTarefaRecorrenteRepository anexoRequeridoTarefaRecorrenteRepository;

    public AnexoRequeridoTarefaRecorrenteService(AnexoRequeridoTarefaRecorrenteRepository anexoRequeridoTarefaRecorrenteRepository) {
        this.anexoRequeridoTarefaRecorrenteRepository = anexoRequeridoTarefaRecorrenteRepository;
    }

    /**
     * Save a anexoRequeridoTarefaRecorrente.
     *
     * @param anexoRequeridoTarefaRecorrente the entity to save.
     * @return the persisted entity.
     */
    public AnexoRequeridoTarefaRecorrente save(AnexoRequeridoTarefaRecorrente anexoRequeridoTarefaRecorrente) {
        log.debug("Request to save AnexoRequeridoTarefaRecorrente : {}", anexoRequeridoTarefaRecorrente);
        return anexoRequeridoTarefaRecorrenteRepository.save(anexoRequeridoTarefaRecorrente);
    }

    /**
     * Update a anexoRequeridoTarefaRecorrente.
     *
     * @param anexoRequeridoTarefaRecorrente the entity to save.
     * @return the persisted entity.
     */
    public AnexoRequeridoTarefaRecorrente update(AnexoRequeridoTarefaRecorrente anexoRequeridoTarefaRecorrente) {
        log.debug("Request to update AnexoRequeridoTarefaRecorrente : {}", anexoRequeridoTarefaRecorrente);
        return anexoRequeridoTarefaRecorrenteRepository.save(anexoRequeridoTarefaRecorrente);
    }

    /**
     * Partially update a anexoRequeridoTarefaRecorrente.
     *
     * @param anexoRequeridoTarefaRecorrente the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AnexoRequeridoTarefaRecorrente> partialUpdate(AnexoRequeridoTarefaRecorrente anexoRequeridoTarefaRecorrente) {
        log.debug("Request to partially update AnexoRequeridoTarefaRecorrente : {}", anexoRequeridoTarefaRecorrente);

        return anexoRequeridoTarefaRecorrenteRepository
            .findById(anexoRequeridoTarefaRecorrente.getId())
            .map(existingAnexoRequeridoTarefaRecorrente -> {
                if (anexoRequeridoTarefaRecorrente.getObrigatorio() != null) {
                    existingAnexoRequeridoTarefaRecorrente.setObrigatorio(anexoRequeridoTarefaRecorrente.getObrigatorio());
                }

                return existingAnexoRequeridoTarefaRecorrente;
            })
            .map(anexoRequeridoTarefaRecorrenteRepository::save);
    }

    /**
     * Get all the anexoRequeridoTarefaRecorrentes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AnexoRequeridoTarefaRecorrente> findAll() {
        log.debug("Request to get all AnexoRequeridoTarefaRecorrentes");
        return anexoRequeridoTarefaRecorrenteRepository.findAll();
    }

    /**
     * Get all the anexoRequeridoTarefaRecorrentes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AnexoRequeridoTarefaRecorrente> findAllWithEagerRelationships(Pageable pageable) {
        return anexoRequeridoTarefaRecorrenteRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one anexoRequeridoTarefaRecorrente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnexoRequeridoTarefaRecorrente> findOne(Long id) {
        log.debug("Request to get AnexoRequeridoTarefaRecorrente : {}", id);
        return anexoRequeridoTarefaRecorrenteRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the anexoRequeridoTarefaRecorrente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnexoRequeridoTarefaRecorrente : {}", id);
        anexoRequeridoTarefaRecorrenteRepository.deleteById(id);
    }
}
