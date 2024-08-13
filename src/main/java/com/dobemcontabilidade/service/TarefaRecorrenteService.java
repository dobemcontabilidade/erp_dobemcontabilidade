package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.TarefaRecorrente;
import com.dobemcontabilidade.repository.TarefaRecorrenteRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.TarefaRecorrente}.
 */
@Service
@Transactional
public class TarefaRecorrenteService {

    private static final Logger log = LoggerFactory.getLogger(TarefaRecorrenteService.class);

    private final TarefaRecorrenteRepository tarefaRecorrenteRepository;

    public TarefaRecorrenteService(TarefaRecorrenteRepository tarefaRecorrenteRepository) {
        this.tarefaRecorrenteRepository = tarefaRecorrenteRepository;
    }

    /**
     * Save a tarefaRecorrente.
     *
     * @param tarefaRecorrente the entity to save.
     * @return the persisted entity.
     */
    public TarefaRecorrente save(TarefaRecorrente tarefaRecorrente) {
        log.debug("Request to save TarefaRecorrente : {}", tarefaRecorrente);
        return tarefaRecorrenteRepository.save(tarefaRecorrente);
    }

    /**
     * Update a tarefaRecorrente.
     *
     * @param tarefaRecorrente the entity to save.
     * @return the persisted entity.
     */
    public TarefaRecorrente update(TarefaRecorrente tarefaRecorrente) {
        log.debug("Request to update TarefaRecorrente : {}", tarefaRecorrente);
        return tarefaRecorrenteRepository.save(tarefaRecorrente);
    }

    /**
     * Partially update a tarefaRecorrente.
     *
     * @param tarefaRecorrente the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TarefaRecorrente> partialUpdate(TarefaRecorrente tarefaRecorrente) {
        log.debug("Request to partially update TarefaRecorrente : {}", tarefaRecorrente);

        return tarefaRecorrenteRepository
            .findById(tarefaRecorrente.getId())
            .map(existingTarefaRecorrente -> {
                if (tarefaRecorrente.getNome() != null) {
                    existingTarefaRecorrente.setNome(tarefaRecorrente.getNome());
                }
                if (tarefaRecorrente.getDescricao() != null) {
                    existingTarefaRecorrente.setDescricao(tarefaRecorrente.getDescricao());
                }
                if (tarefaRecorrente.getNotificarCliente() != null) {
                    existingTarefaRecorrente.setNotificarCliente(tarefaRecorrente.getNotificarCliente());
                }
                if (tarefaRecorrente.getNotificarContador() != null) {
                    existingTarefaRecorrente.setNotificarContador(tarefaRecorrente.getNotificarContador());
                }
                if (tarefaRecorrente.getAnoReferencia() != null) {
                    existingTarefaRecorrente.setAnoReferencia(tarefaRecorrente.getAnoReferencia());
                }
                if (tarefaRecorrente.getDataAdmin() != null) {
                    existingTarefaRecorrente.setDataAdmin(tarefaRecorrente.getDataAdmin());
                }
                if (tarefaRecorrente.getRecorencia() != null) {
                    existingTarefaRecorrente.setRecorencia(tarefaRecorrente.getRecorencia());
                }

                return existingTarefaRecorrente;
            })
            .map(tarefaRecorrenteRepository::save);
    }

    /**
     * Get all the tarefaRecorrentes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TarefaRecorrente> findAll() {
        log.debug("Request to get all TarefaRecorrentes");
        return tarefaRecorrenteRepository.findAll();
    }

    /**
     * Get one tarefaRecorrente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TarefaRecorrente> findOne(Long id) {
        log.debug("Request to get TarefaRecorrente : {}", id);
        return tarefaRecorrenteRepository.findById(id);
    }

    /**
     * Delete the tarefaRecorrente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TarefaRecorrente : {}", id);
        tarefaRecorrenteRepository.deleteById(id);
    }
}
