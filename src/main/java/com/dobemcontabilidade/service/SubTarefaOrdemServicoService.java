package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.SubTarefaOrdemServico;
import com.dobemcontabilidade.repository.SubTarefaOrdemServicoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.SubTarefaOrdemServico}.
 */
@Service
@Transactional
public class SubTarefaOrdemServicoService {

    private static final Logger log = LoggerFactory.getLogger(SubTarefaOrdemServicoService.class);

    private final SubTarefaOrdemServicoRepository subTarefaOrdemServicoRepository;

    public SubTarefaOrdemServicoService(SubTarefaOrdemServicoRepository subTarefaOrdemServicoRepository) {
        this.subTarefaOrdemServicoRepository = subTarefaOrdemServicoRepository;
    }

    /**
     * Save a subTarefaOrdemServico.
     *
     * @param subTarefaOrdemServico the entity to save.
     * @return the persisted entity.
     */
    public SubTarefaOrdemServico save(SubTarefaOrdemServico subTarefaOrdemServico) {
        log.debug("Request to save SubTarefaOrdemServico : {}", subTarefaOrdemServico);
        return subTarefaOrdemServicoRepository.save(subTarefaOrdemServico);
    }

    /**
     * Update a subTarefaOrdemServico.
     *
     * @param subTarefaOrdemServico the entity to save.
     * @return the persisted entity.
     */
    public SubTarefaOrdemServico update(SubTarefaOrdemServico subTarefaOrdemServico) {
        log.debug("Request to update SubTarefaOrdemServico : {}", subTarefaOrdemServico);
        return subTarefaOrdemServicoRepository.save(subTarefaOrdemServico);
    }

    /**
     * Partially update a subTarefaOrdemServico.
     *
     * @param subTarefaOrdemServico the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SubTarefaOrdemServico> partialUpdate(SubTarefaOrdemServico subTarefaOrdemServico) {
        log.debug("Request to partially update SubTarefaOrdemServico : {}", subTarefaOrdemServico);

        return subTarefaOrdemServicoRepository
            .findById(subTarefaOrdemServico.getId())
            .map(existingSubTarefaOrdemServico -> {
                if (subTarefaOrdemServico.getNome() != null) {
                    existingSubTarefaOrdemServico.setNome(subTarefaOrdemServico.getNome());
                }
                if (subTarefaOrdemServico.getDescricao() != null) {
                    existingSubTarefaOrdemServico.setDescricao(subTarefaOrdemServico.getDescricao());
                }
                if (subTarefaOrdemServico.getOrdem() != null) {
                    existingSubTarefaOrdemServico.setOrdem(subTarefaOrdemServico.getOrdem());
                }
                if (subTarefaOrdemServico.getConcluida() != null) {
                    existingSubTarefaOrdemServico.setConcluida(subTarefaOrdemServico.getConcluida());
                }

                return existingSubTarefaOrdemServico;
            })
            .map(subTarefaOrdemServicoRepository::save);
    }

    /**
     * Get all the subTarefaOrdemServicos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SubTarefaOrdemServico> findAll() {
        log.debug("Request to get all SubTarefaOrdemServicos");
        return subTarefaOrdemServicoRepository.findAll();
    }

    /**
     * Get all the subTarefaOrdemServicos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<SubTarefaOrdemServico> findAllWithEagerRelationships(Pageable pageable) {
        return subTarefaOrdemServicoRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one subTarefaOrdemServico by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SubTarefaOrdemServico> findOne(Long id) {
        log.debug("Request to get SubTarefaOrdemServico : {}", id);
        return subTarefaOrdemServicoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the subTarefaOrdemServico by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SubTarefaOrdemServico : {}", id);
        subTarefaOrdemServicoRepository.deleteById(id);
    }
}
