package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AgendaTarefaOrdemServicoExecucao;
import com.dobemcontabilidade.repository.AgendaTarefaOrdemServicoExecucaoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AgendaTarefaOrdemServicoExecucao}.
 */
@Service
@Transactional
public class AgendaTarefaOrdemServicoExecucaoService {

    private static final Logger log = LoggerFactory.getLogger(AgendaTarefaOrdemServicoExecucaoService.class);

    private final AgendaTarefaOrdemServicoExecucaoRepository agendaTarefaOrdemServicoExecucaoRepository;

    public AgendaTarefaOrdemServicoExecucaoService(AgendaTarefaOrdemServicoExecucaoRepository agendaTarefaOrdemServicoExecucaoRepository) {
        this.agendaTarefaOrdemServicoExecucaoRepository = agendaTarefaOrdemServicoExecucaoRepository;
    }

    /**
     * Save a agendaTarefaOrdemServicoExecucao.
     *
     * @param agendaTarefaOrdemServicoExecucao the entity to save.
     * @return the persisted entity.
     */
    public AgendaTarefaOrdemServicoExecucao save(AgendaTarefaOrdemServicoExecucao agendaTarefaOrdemServicoExecucao) {
        log.debug("Request to save AgendaTarefaOrdemServicoExecucao : {}", agendaTarefaOrdemServicoExecucao);
        return agendaTarefaOrdemServicoExecucaoRepository.save(agendaTarefaOrdemServicoExecucao);
    }

    /**
     * Update a agendaTarefaOrdemServicoExecucao.
     *
     * @param agendaTarefaOrdemServicoExecucao the entity to save.
     * @return the persisted entity.
     */
    public AgendaTarefaOrdemServicoExecucao update(AgendaTarefaOrdemServicoExecucao agendaTarefaOrdemServicoExecucao) {
        log.debug("Request to update AgendaTarefaOrdemServicoExecucao : {}", agendaTarefaOrdemServicoExecucao);
        return agendaTarefaOrdemServicoExecucaoRepository.save(agendaTarefaOrdemServicoExecucao);
    }

    /**
     * Partially update a agendaTarefaOrdemServicoExecucao.
     *
     * @param agendaTarefaOrdemServicoExecucao the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AgendaTarefaOrdemServicoExecucao> partialUpdate(AgendaTarefaOrdemServicoExecucao agendaTarefaOrdemServicoExecucao) {
        log.debug("Request to partially update AgendaTarefaOrdemServicoExecucao : {}", agendaTarefaOrdemServicoExecucao);

        return agendaTarefaOrdemServicoExecucaoRepository
            .findById(agendaTarefaOrdemServicoExecucao.getId())
            .map(existingAgendaTarefaOrdemServicoExecucao -> {
                if (agendaTarefaOrdemServicoExecucao.getHoraInicio() != null) {
                    existingAgendaTarefaOrdemServicoExecucao.setHoraInicio(agendaTarefaOrdemServicoExecucao.getHoraInicio());
                }
                if (agendaTarefaOrdemServicoExecucao.getHoraFim() != null) {
                    existingAgendaTarefaOrdemServicoExecucao.setHoraFim(agendaTarefaOrdemServicoExecucao.getHoraFim());
                }
                if (agendaTarefaOrdemServicoExecucao.getDiaInteiro() != null) {
                    existingAgendaTarefaOrdemServicoExecucao.setDiaInteiro(agendaTarefaOrdemServicoExecucao.getDiaInteiro());
                }
                if (agendaTarefaOrdemServicoExecucao.getAtivo() != null) {
                    existingAgendaTarefaOrdemServicoExecucao.setAtivo(agendaTarefaOrdemServicoExecucao.getAtivo());
                }

                return existingAgendaTarefaOrdemServicoExecucao;
            })
            .map(agendaTarefaOrdemServicoExecucaoRepository::save);
    }

    /**
     * Get all the agendaTarefaOrdemServicoExecucaos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AgendaTarefaOrdemServicoExecucao> findAll() {
        log.debug("Request to get all AgendaTarefaOrdemServicoExecucaos");
        return agendaTarefaOrdemServicoExecucaoRepository.findAll();
    }

    /**
     * Get all the agendaTarefaOrdemServicoExecucaos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AgendaTarefaOrdemServicoExecucao> findAllWithEagerRelationships(Pageable pageable) {
        return agendaTarefaOrdemServicoExecucaoRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one agendaTarefaOrdemServicoExecucao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AgendaTarefaOrdemServicoExecucao> findOne(Long id) {
        log.debug("Request to get AgendaTarefaOrdemServicoExecucao : {}", id);
        return agendaTarefaOrdemServicoExecucaoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the agendaTarefaOrdemServicoExecucao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AgendaTarefaOrdemServicoExecucao : {}", id);
        agendaTarefaOrdemServicoExecucaoRepository.deleteById(id);
    }
}
