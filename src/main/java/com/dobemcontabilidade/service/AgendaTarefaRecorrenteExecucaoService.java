package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AgendaTarefaRecorrenteExecucao;
import com.dobemcontabilidade.repository.AgendaTarefaRecorrenteExecucaoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AgendaTarefaRecorrenteExecucao}.
 */
@Service
@Transactional
public class AgendaTarefaRecorrenteExecucaoService {

    private static final Logger log = LoggerFactory.getLogger(AgendaTarefaRecorrenteExecucaoService.class);

    private final AgendaTarefaRecorrenteExecucaoRepository agendaTarefaRecorrenteExecucaoRepository;

    public AgendaTarefaRecorrenteExecucaoService(AgendaTarefaRecorrenteExecucaoRepository agendaTarefaRecorrenteExecucaoRepository) {
        this.agendaTarefaRecorrenteExecucaoRepository = agendaTarefaRecorrenteExecucaoRepository;
    }

    /**
     * Save a agendaTarefaRecorrenteExecucao.
     *
     * @param agendaTarefaRecorrenteExecucao the entity to save.
     * @return the persisted entity.
     */
    public AgendaTarefaRecorrenteExecucao save(AgendaTarefaRecorrenteExecucao agendaTarefaRecorrenteExecucao) {
        log.debug("Request to save AgendaTarefaRecorrenteExecucao : {}", agendaTarefaRecorrenteExecucao);
        return agendaTarefaRecorrenteExecucaoRepository.save(agendaTarefaRecorrenteExecucao);
    }

    /**
     * Update a agendaTarefaRecorrenteExecucao.
     *
     * @param agendaTarefaRecorrenteExecucao the entity to save.
     * @return the persisted entity.
     */
    public AgendaTarefaRecorrenteExecucao update(AgendaTarefaRecorrenteExecucao agendaTarefaRecorrenteExecucao) {
        log.debug("Request to update AgendaTarefaRecorrenteExecucao : {}", agendaTarefaRecorrenteExecucao);
        return agendaTarefaRecorrenteExecucaoRepository.save(agendaTarefaRecorrenteExecucao);
    }

    /**
     * Partially update a agendaTarefaRecorrenteExecucao.
     *
     * @param agendaTarefaRecorrenteExecucao the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AgendaTarefaRecorrenteExecucao> partialUpdate(AgendaTarefaRecorrenteExecucao agendaTarefaRecorrenteExecucao) {
        log.debug("Request to partially update AgendaTarefaRecorrenteExecucao : {}", agendaTarefaRecorrenteExecucao);

        return agendaTarefaRecorrenteExecucaoRepository
            .findById(agendaTarefaRecorrenteExecucao.getId())
            .map(existingAgendaTarefaRecorrenteExecucao -> {
                if (agendaTarefaRecorrenteExecucao.getAtivo() != null) {
                    existingAgendaTarefaRecorrenteExecucao.setAtivo(agendaTarefaRecorrenteExecucao.getAtivo());
                }
                if (agendaTarefaRecorrenteExecucao.getHoraInicio() != null) {
                    existingAgendaTarefaRecorrenteExecucao.setHoraInicio(agendaTarefaRecorrenteExecucao.getHoraInicio());
                }
                if (agendaTarefaRecorrenteExecucao.getHoraFim() != null) {
                    existingAgendaTarefaRecorrenteExecucao.setHoraFim(agendaTarefaRecorrenteExecucao.getHoraFim());
                }
                if (agendaTarefaRecorrenteExecucao.getDiaInteiro() != null) {
                    existingAgendaTarefaRecorrenteExecucao.setDiaInteiro(agendaTarefaRecorrenteExecucao.getDiaInteiro());
                }
                if (agendaTarefaRecorrenteExecucao.getComentario() != null) {
                    existingAgendaTarefaRecorrenteExecucao.setComentario(agendaTarefaRecorrenteExecucao.getComentario());
                }

                return existingAgendaTarefaRecorrenteExecucao;
            })
            .map(agendaTarefaRecorrenteExecucaoRepository::save);
    }

    /**
     * Get all the agendaTarefaRecorrenteExecucaos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AgendaTarefaRecorrenteExecucao> findAll() {
        log.debug("Request to get all AgendaTarefaRecorrenteExecucaos");
        return agendaTarefaRecorrenteExecucaoRepository.findAll();
    }

    /**
     * Get all the agendaTarefaRecorrenteExecucaos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AgendaTarefaRecorrenteExecucao> findAllWithEagerRelationships(Pageable pageable) {
        return agendaTarefaRecorrenteExecucaoRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one agendaTarefaRecorrenteExecucao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AgendaTarefaRecorrenteExecucao> findOne(Long id) {
        log.debug("Request to get AgendaTarefaRecorrenteExecucao : {}", id);
        return agendaTarefaRecorrenteExecucaoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the agendaTarefaRecorrenteExecucao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AgendaTarefaRecorrenteExecucao : {}", id);
        agendaTarefaRecorrenteExecucaoRepository.deleteById(id);
    }
}
