package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.SubTarefaRecorrente;
import com.dobemcontabilidade.repository.SubTarefaRecorrenteRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.SubTarefaRecorrente}.
 */
@Service
@Transactional
public class SubTarefaRecorrenteService {

    private static final Logger log = LoggerFactory.getLogger(SubTarefaRecorrenteService.class);

    private final SubTarefaRecorrenteRepository subTarefaRecorrenteRepository;

    public SubTarefaRecorrenteService(SubTarefaRecorrenteRepository subTarefaRecorrenteRepository) {
        this.subTarefaRecorrenteRepository = subTarefaRecorrenteRepository;
    }

    /**
     * Save a subTarefaRecorrente.
     *
     * @param subTarefaRecorrente the entity to save.
     * @return the persisted entity.
     */
    public SubTarefaRecorrente save(SubTarefaRecorrente subTarefaRecorrente) {
        log.debug("Request to save SubTarefaRecorrente : {}", subTarefaRecorrente);
        return subTarefaRecorrenteRepository.save(subTarefaRecorrente);
    }

    /**
     * Update a subTarefaRecorrente.
     *
     * @param subTarefaRecorrente the entity to save.
     * @return the persisted entity.
     */
    public SubTarefaRecorrente update(SubTarefaRecorrente subTarefaRecorrente) {
        log.debug("Request to update SubTarefaRecorrente : {}", subTarefaRecorrente);
        return subTarefaRecorrenteRepository.save(subTarefaRecorrente);
    }

    /**
     * Partially update a subTarefaRecorrente.
     *
     * @param subTarefaRecorrente the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SubTarefaRecorrente> partialUpdate(SubTarefaRecorrente subTarefaRecorrente) {
        log.debug("Request to partially update SubTarefaRecorrente : {}", subTarefaRecorrente);

        return subTarefaRecorrenteRepository
            .findById(subTarefaRecorrente.getId())
            .map(existingSubTarefaRecorrente -> {
                if (subTarefaRecorrente.getNome() != null) {
                    existingSubTarefaRecorrente.setNome(subTarefaRecorrente.getNome());
                }
                if (subTarefaRecorrente.getDescricao() != null) {
                    existingSubTarefaRecorrente.setDescricao(subTarefaRecorrente.getDescricao());
                }
                if (subTarefaRecorrente.getOrdem() != null) {
                    existingSubTarefaRecorrente.setOrdem(subTarefaRecorrente.getOrdem());
                }
                if (subTarefaRecorrente.getConcluida() != null) {
                    existingSubTarefaRecorrente.setConcluida(subTarefaRecorrente.getConcluida());
                }

                return existingSubTarefaRecorrente;
            })
            .map(subTarefaRecorrenteRepository::save);
    }

    /**
     * Get all the subTarefaRecorrentes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SubTarefaRecorrente> findAll() {
        log.debug("Request to get all SubTarefaRecorrentes");
        return subTarefaRecorrenteRepository.findAll();
    }

    /**
     * Get all the subTarefaRecorrentes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<SubTarefaRecorrente> findAllWithEagerRelationships(Pageable pageable) {
        return subTarefaRecorrenteRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one subTarefaRecorrente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SubTarefaRecorrente> findOne(Long id) {
        log.debug("Request to get SubTarefaRecorrente : {}", id);
        return subTarefaRecorrenteRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the subTarefaRecorrente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SubTarefaRecorrente : {}", id);
        subTarefaRecorrenteRepository.deleteById(id);
    }
}
