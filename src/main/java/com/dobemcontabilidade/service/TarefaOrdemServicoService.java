package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.TarefaOrdemServico;
import com.dobemcontabilidade.repository.TarefaOrdemServicoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.TarefaOrdemServico}.
 */
@Service
@Transactional
public class TarefaOrdemServicoService {

    private static final Logger log = LoggerFactory.getLogger(TarefaOrdemServicoService.class);

    private final TarefaOrdemServicoRepository tarefaOrdemServicoRepository;

    public TarefaOrdemServicoService(TarefaOrdemServicoRepository tarefaOrdemServicoRepository) {
        this.tarefaOrdemServicoRepository = tarefaOrdemServicoRepository;
    }

    /**
     * Save a tarefaOrdemServico.
     *
     * @param tarefaOrdemServico the entity to save.
     * @return the persisted entity.
     */
    public TarefaOrdemServico save(TarefaOrdemServico tarefaOrdemServico) {
        log.debug("Request to save TarefaOrdemServico : {}", tarefaOrdemServico);
        return tarefaOrdemServicoRepository.save(tarefaOrdemServico);
    }

    /**
     * Update a tarefaOrdemServico.
     *
     * @param tarefaOrdemServico the entity to save.
     * @return the persisted entity.
     */
    public TarefaOrdemServico update(TarefaOrdemServico tarefaOrdemServico) {
        log.debug("Request to update TarefaOrdemServico : {}", tarefaOrdemServico);
        return tarefaOrdemServicoRepository.save(tarefaOrdemServico);
    }

    /**
     * Partially update a tarefaOrdemServico.
     *
     * @param tarefaOrdemServico the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TarefaOrdemServico> partialUpdate(TarefaOrdemServico tarefaOrdemServico) {
        log.debug("Request to partially update TarefaOrdemServico : {}", tarefaOrdemServico);

        return tarefaOrdemServicoRepository
            .findById(tarefaOrdemServico.getId())
            .map(existingTarefaOrdemServico -> {
                if (tarefaOrdemServico.getNome() != null) {
                    existingTarefaOrdemServico.setNome(tarefaOrdemServico.getNome());
                }
                if (tarefaOrdemServico.getDescricao() != null) {
                    existingTarefaOrdemServico.setDescricao(tarefaOrdemServico.getDescricao());
                }
                if (tarefaOrdemServico.getNotificarCliente() != null) {
                    existingTarefaOrdemServico.setNotificarCliente(tarefaOrdemServico.getNotificarCliente());
                }
                if (tarefaOrdemServico.getNotificarContador() != null) {
                    existingTarefaOrdemServico.setNotificarContador(tarefaOrdemServico.getNotificarContador());
                }
                if (tarefaOrdemServico.getAnoReferencia() != null) {
                    existingTarefaOrdemServico.setAnoReferencia(tarefaOrdemServico.getAnoReferencia());
                }
                if (tarefaOrdemServico.getDataAdmin() != null) {
                    existingTarefaOrdemServico.setDataAdmin(tarefaOrdemServico.getDataAdmin());
                }

                return existingTarefaOrdemServico;
            })
            .map(tarefaOrdemServicoRepository::save);
    }

    /**
     * Get all the tarefaOrdemServicos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TarefaOrdemServico> findAll() {
        log.debug("Request to get all TarefaOrdemServicos");
        return tarefaOrdemServicoRepository.findAll();
    }

    /**
     * Get one tarefaOrdemServico by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TarefaOrdemServico> findOne(Long id) {
        log.debug("Request to get TarefaOrdemServico : {}", id);
        return tarefaOrdemServicoRepository.findById(id);
    }

    /**
     * Delete the tarefaOrdemServico by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TarefaOrdemServico : {}", id);
        tarefaOrdemServicoRepository.deleteById(id);
    }
}
