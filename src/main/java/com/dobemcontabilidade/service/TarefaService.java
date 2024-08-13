package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Tarefa;
import com.dobemcontabilidade.repository.TarefaRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Tarefa}.
 */
@Service
@Transactional
public class TarefaService {

    private static final Logger log = LoggerFactory.getLogger(TarefaService.class);

    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    /**
     * Save a tarefa.
     *
     * @param tarefa the entity to save.
     * @return the persisted entity.
     */
    public Tarefa save(Tarefa tarefa) {
        log.debug("Request to save Tarefa : {}", tarefa);
        return tarefaRepository.save(tarefa);
    }

    /**
     * Update a tarefa.
     *
     * @param tarefa the entity to save.
     * @return the persisted entity.
     */
    public Tarefa update(Tarefa tarefa) {
        log.debug("Request to update Tarefa : {}", tarefa);
        return tarefaRepository.save(tarefa);
    }

    /**
     * Partially update a tarefa.
     *
     * @param tarefa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Tarefa> partialUpdate(Tarefa tarefa) {
        log.debug("Request to partially update Tarefa : {}", tarefa);

        return tarefaRepository
            .findById(tarefa.getId())
            .map(existingTarefa -> {
                if (tarefa.getTitulo() != null) {
                    existingTarefa.setTitulo(tarefa.getTitulo());
                }
                if (tarefa.getNumeroDias() != null) {
                    existingTarefa.setNumeroDias(tarefa.getNumeroDias());
                }
                if (tarefa.getDiaUtil() != null) {
                    existingTarefa.setDiaUtil(tarefa.getDiaUtil());
                }
                if (tarefa.getValor() != null) {
                    existingTarefa.setValor(tarefa.getValor());
                }
                if (tarefa.getNotificarCliente() != null) {
                    existingTarefa.setNotificarCliente(tarefa.getNotificarCliente());
                }
                if (tarefa.getGeraMulta() != null) {
                    existingTarefa.setGeraMulta(tarefa.getGeraMulta());
                }
                if (tarefa.getExibirEmpresa() != null) {
                    existingTarefa.setExibirEmpresa(tarefa.getExibirEmpresa());
                }
                if (tarefa.getDataLegal() != null) {
                    existingTarefa.setDataLegal(tarefa.getDataLegal());
                }
                if (tarefa.getPontos() != null) {
                    existingTarefa.setPontos(tarefa.getPontos());
                }
                if (tarefa.getTipoTarefa() != null) {
                    existingTarefa.setTipoTarefa(tarefa.getTipoTarefa());
                }

                return existingTarefa;
            })
            .map(tarefaRepository::save);
    }

    /**
     * Get all the tarefas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Tarefa> findAllWithEagerRelationships(Pageable pageable) {
        return tarefaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one tarefa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Tarefa> findOne(Long id) {
        log.debug("Request to get Tarefa : {}", id);
        return tarefaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the tarefa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Tarefa : {}", id);
        tarefaRepository.deleteById(id);
    }
}
