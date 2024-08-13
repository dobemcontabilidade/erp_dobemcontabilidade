package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.TarefaOrdemServicoExecucao;
import com.dobemcontabilidade.repository.TarefaOrdemServicoExecucaoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.TarefaOrdemServicoExecucao}.
 */
@Service
@Transactional
public class TarefaOrdemServicoExecucaoService {

    private static final Logger log = LoggerFactory.getLogger(TarefaOrdemServicoExecucaoService.class);

    private final TarefaOrdemServicoExecucaoRepository tarefaOrdemServicoExecucaoRepository;

    public TarefaOrdemServicoExecucaoService(TarefaOrdemServicoExecucaoRepository tarefaOrdemServicoExecucaoRepository) {
        this.tarefaOrdemServicoExecucaoRepository = tarefaOrdemServicoExecucaoRepository;
    }

    /**
     * Save a tarefaOrdemServicoExecucao.
     *
     * @param tarefaOrdemServicoExecucao the entity to save.
     * @return the persisted entity.
     */
    public TarefaOrdemServicoExecucao save(TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao) {
        log.debug("Request to save TarefaOrdemServicoExecucao : {}", tarefaOrdemServicoExecucao);
        return tarefaOrdemServicoExecucaoRepository.save(tarefaOrdemServicoExecucao);
    }

    /**
     * Update a tarefaOrdemServicoExecucao.
     *
     * @param tarefaOrdemServicoExecucao the entity to save.
     * @return the persisted entity.
     */
    public TarefaOrdemServicoExecucao update(TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao) {
        log.debug("Request to update TarefaOrdemServicoExecucao : {}", tarefaOrdemServicoExecucao);
        return tarefaOrdemServicoExecucaoRepository.save(tarefaOrdemServicoExecucao);
    }

    /**
     * Partially update a tarefaOrdemServicoExecucao.
     *
     * @param tarefaOrdemServicoExecucao the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TarefaOrdemServicoExecucao> partialUpdate(TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao) {
        log.debug("Request to partially update TarefaOrdemServicoExecucao : {}", tarefaOrdemServicoExecucao);

        return tarefaOrdemServicoExecucaoRepository
            .findById(tarefaOrdemServicoExecucao.getId())
            .map(existingTarefaOrdemServicoExecucao -> {
                if (tarefaOrdemServicoExecucao.getNome() != null) {
                    existingTarefaOrdemServicoExecucao.setNome(tarefaOrdemServicoExecucao.getNome());
                }
                if (tarefaOrdemServicoExecucao.getDescricao() != null) {
                    existingTarefaOrdemServicoExecucao.setDescricao(tarefaOrdemServicoExecucao.getDescricao());
                }
                if (tarefaOrdemServicoExecucao.getOrdem() != null) {
                    existingTarefaOrdemServicoExecucao.setOrdem(tarefaOrdemServicoExecucao.getOrdem());
                }
                if (tarefaOrdemServicoExecucao.getDataEntrega() != null) {
                    existingTarefaOrdemServicoExecucao.setDataEntrega(tarefaOrdemServicoExecucao.getDataEntrega());
                }
                if (tarefaOrdemServicoExecucao.getDataAgendada() != null) {
                    existingTarefaOrdemServicoExecucao.setDataAgendada(tarefaOrdemServicoExecucao.getDataAgendada());
                }
                if (tarefaOrdemServicoExecucao.getConcluida() != null) {
                    existingTarefaOrdemServicoExecucao.setConcluida(tarefaOrdemServicoExecucao.getConcluida());
                }
                if (tarefaOrdemServicoExecucao.getNotificarCliente() != null) {
                    existingTarefaOrdemServicoExecucao.setNotificarCliente(tarefaOrdemServicoExecucao.getNotificarCliente());
                }
                if (tarefaOrdemServicoExecucao.getMes() != null) {
                    existingTarefaOrdemServicoExecucao.setMes(tarefaOrdemServicoExecucao.getMes());
                }
                if (tarefaOrdemServicoExecucao.getSituacaoTarefa() != null) {
                    existingTarefaOrdemServicoExecucao.setSituacaoTarefa(tarefaOrdemServicoExecucao.getSituacaoTarefa());
                }

                return existingTarefaOrdemServicoExecucao;
            })
            .map(tarefaOrdemServicoExecucaoRepository::save);
    }

    /**
     * Get all the tarefaOrdemServicoExecucaos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TarefaOrdemServicoExecucao> findAll() {
        log.debug("Request to get all TarefaOrdemServicoExecucaos");
        return tarefaOrdemServicoExecucaoRepository.findAll();
    }

    /**
     * Get all the tarefaOrdemServicoExecucaos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TarefaOrdemServicoExecucao> findAllWithEagerRelationships(Pageable pageable) {
        return tarefaOrdemServicoExecucaoRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one tarefaOrdemServicoExecucao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TarefaOrdemServicoExecucao> findOne(Long id) {
        log.debug("Request to get TarefaOrdemServicoExecucao : {}", id);
        return tarefaOrdemServicoExecucaoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the tarefaOrdemServicoExecucao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TarefaOrdemServicoExecucao : {}", id);
        tarefaOrdemServicoExecucaoRepository.deleteById(id);
    }
}
