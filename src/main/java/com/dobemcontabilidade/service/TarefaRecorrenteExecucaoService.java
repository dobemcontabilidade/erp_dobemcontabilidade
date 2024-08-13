package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.TarefaRecorrenteExecucao;
import com.dobemcontabilidade.repository.TarefaRecorrenteExecucaoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.TarefaRecorrenteExecucao}.
 */
@Service
@Transactional
public class TarefaRecorrenteExecucaoService {

    private static final Logger log = LoggerFactory.getLogger(TarefaRecorrenteExecucaoService.class);

    private final TarefaRecorrenteExecucaoRepository tarefaRecorrenteExecucaoRepository;

    public TarefaRecorrenteExecucaoService(TarefaRecorrenteExecucaoRepository tarefaRecorrenteExecucaoRepository) {
        this.tarefaRecorrenteExecucaoRepository = tarefaRecorrenteExecucaoRepository;
    }

    /**
     * Save a tarefaRecorrenteExecucao.
     *
     * @param tarefaRecorrenteExecucao the entity to save.
     * @return the persisted entity.
     */
    public TarefaRecorrenteExecucao save(TarefaRecorrenteExecucao tarefaRecorrenteExecucao) {
        log.debug("Request to save TarefaRecorrenteExecucao : {}", tarefaRecorrenteExecucao);
        return tarefaRecorrenteExecucaoRepository.save(tarefaRecorrenteExecucao);
    }

    /**
     * Update a tarefaRecorrenteExecucao.
     *
     * @param tarefaRecorrenteExecucao the entity to save.
     * @return the persisted entity.
     */
    public TarefaRecorrenteExecucao update(TarefaRecorrenteExecucao tarefaRecorrenteExecucao) {
        log.debug("Request to update TarefaRecorrenteExecucao : {}", tarefaRecorrenteExecucao);
        return tarefaRecorrenteExecucaoRepository.save(tarefaRecorrenteExecucao);
    }

    /**
     * Partially update a tarefaRecorrenteExecucao.
     *
     * @param tarefaRecorrenteExecucao the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TarefaRecorrenteExecucao> partialUpdate(TarefaRecorrenteExecucao tarefaRecorrenteExecucao) {
        log.debug("Request to partially update TarefaRecorrenteExecucao : {}", tarefaRecorrenteExecucao);

        return tarefaRecorrenteExecucaoRepository
            .findById(tarefaRecorrenteExecucao.getId())
            .map(existingTarefaRecorrenteExecucao -> {
                if (tarefaRecorrenteExecucao.getNome() != null) {
                    existingTarefaRecorrenteExecucao.setNome(tarefaRecorrenteExecucao.getNome());
                }
                if (tarefaRecorrenteExecucao.getDescricao() != null) {
                    existingTarefaRecorrenteExecucao.setDescricao(tarefaRecorrenteExecucao.getDescricao());
                }
                if (tarefaRecorrenteExecucao.getDataEntrega() != null) {
                    existingTarefaRecorrenteExecucao.setDataEntrega(tarefaRecorrenteExecucao.getDataEntrega());
                }
                if (tarefaRecorrenteExecucao.getDataAgendada() != null) {
                    existingTarefaRecorrenteExecucao.setDataAgendada(tarefaRecorrenteExecucao.getDataAgendada());
                }
                if (tarefaRecorrenteExecucao.getOrdem() != null) {
                    existingTarefaRecorrenteExecucao.setOrdem(tarefaRecorrenteExecucao.getOrdem());
                }
                if (tarefaRecorrenteExecucao.getConcluida() != null) {
                    existingTarefaRecorrenteExecucao.setConcluida(tarefaRecorrenteExecucao.getConcluida());
                }
                if (tarefaRecorrenteExecucao.getMes() != null) {
                    existingTarefaRecorrenteExecucao.setMes(tarefaRecorrenteExecucao.getMes());
                }
                if (tarefaRecorrenteExecucao.getSituacaoTarefa() != null) {
                    existingTarefaRecorrenteExecucao.setSituacaoTarefa(tarefaRecorrenteExecucao.getSituacaoTarefa());
                }

                return existingTarefaRecorrenteExecucao;
            })
            .map(tarefaRecorrenteExecucaoRepository::save);
    }

    /**
     * Get all the tarefaRecorrenteExecucaos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TarefaRecorrenteExecucao> findAll() {
        log.debug("Request to get all TarefaRecorrenteExecucaos");
        return tarefaRecorrenteExecucaoRepository.findAll();
    }

    /**
     * Get all the tarefaRecorrenteExecucaos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TarefaRecorrenteExecucao> findAllWithEagerRelationships(Pageable pageable) {
        return tarefaRecorrenteExecucaoRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one tarefaRecorrenteExecucao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TarefaRecorrenteExecucao> findOne(Long id) {
        log.debug("Request to get TarefaRecorrenteExecucao : {}", id);
        return tarefaRecorrenteExecucaoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the tarefaRecorrenteExecucao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TarefaRecorrenteExecucao : {}", id);
        tarefaRecorrenteExecucaoRepository.deleteById(id);
    }
}
