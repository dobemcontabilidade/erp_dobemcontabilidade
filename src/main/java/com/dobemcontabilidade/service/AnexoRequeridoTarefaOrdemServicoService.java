package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AnexoRequeridoTarefaOrdemServico;
import com.dobemcontabilidade.repository.AnexoRequeridoTarefaOrdemServicoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AnexoRequeridoTarefaOrdemServico}.
 */
@Service
@Transactional
public class AnexoRequeridoTarefaOrdemServicoService {

    private static final Logger log = LoggerFactory.getLogger(AnexoRequeridoTarefaOrdemServicoService.class);

    private final AnexoRequeridoTarefaOrdemServicoRepository anexoRequeridoTarefaOrdemServicoRepository;

    public AnexoRequeridoTarefaOrdemServicoService(AnexoRequeridoTarefaOrdemServicoRepository anexoRequeridoTarefaOrdemServicoRepository) {
        this.anexoRequeridoTarefaOrdemServicoRepository = anexoRequeridoTarefaOrdemServicoRepository;
    }

    /**
     * Save a anexoRequeridoTarefaOrdemServico.
     *
     * @param anexoRequeridoTarefaOrdemServico the entity to save.
     * @return the persisted entity.
     */
    public AnexoRequeridoTarefaOrdemServico save(AnexoRequeridoTarefaOrdemServico anexoRequeridoTarefaOrdemServico) {
        log.debug("Request to save AnexoRequeridoTarefaOrdemServico : {}", anexoRequeridoTarefaOrdemServico);
        return anexoRequeridoTarefaOrdemServicoRepository.save(anexoRequeridoTarefaOrdemServico);
    }

    /**
     * Update a anexoRequeridoTarefaOrdemServico.
     *
     * @param anexoRequeridoTarefaOrdemServico the entity to save.
     * @return the persisted entity.
     */
    public AnexoRequeridoTarefaOrdemServico update(AnexoRequeridoTarefaOrdemServico anexoRequeridoTarefaOrdemServico) {
        log.debug("Request to update AnexoRequeridoTarefaOrdemServico : {}", anexoRequeridoTarefaOrdemServico);
        return anexoRequeridoTarefaOrdemServicoRepository.save(anexoRequeridoTarefaOrdemServico);
    }

    /**
     * Partially update a anexoRequeridoTarefaOrdemServico.
     *
     * @param anexoRequeridoTarefaOrdemServico the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AnexoRequeridoTarefaOrdemServico> partialUpdate(AnexoRequeridoTarefaOrdemServico anexoRequeridoTarefaOrdemServico) {
        log.debug("Request to partially update AnexoRequeridoTarefaOrdemServico : {}", anexoRequeridoTarefaOrdemServico);

        return anexoRequeridoTarefaOrdemServicoRepository
            .findById(anexoRequeridoTarefaOrdemServico.getId())
            .map(existingAnexoRequeridoTarefaOrdemServico -> {
                if (anexoRequeridoTarefaOrdemServico.getObrigatorio() != null) {
                    existingAnexoRequeridoTarefaOrdemServico.setObrigatorio(anexoRequeridoTarefaOrdemServico.getObrigatorio());
                }

                return existingAnexoRequeridoTarefaOrdemServico;
            })
            .map(anexoRequeridoTarefaOrdemServicoRepository::save);
    }

    /**
     * Get all the anexoRequeridoTarefaOrdemServicos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AnexoRequeridoTarefaOrdemServico> findAll() {
        log.debug("Request to get all AnexoRequeridoTarefaOrdemServicos");
        return anexoRequeridoTarefaOrdemServicoRepository.findAll();
    }

    /**
     * Get all the anexoRequeridoTarefaOrdemServicos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AnexoRequeridoTarefaOrdemServico> findAllWithEagerRelationships(Pageable pageable) {
        return anexoRequeridoTarefaOrdemServicoRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one anexoRequeridoTarefaOrdemServico by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnexoRequeridoTarefaOrdemServico> findOne(Long id) {
        log.debug("Request to get AnexoRequeridoTarefaOrdemServico : {}", id);
        return anexoRequeridoTarefaOrdemServicoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the anexoRequeridoTarefaOrdemServico by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnexoRequeridoTarefaOrdemServico : {}", id);
        anexoRequeridoTarefaOrdemServicoRepository.deleteById(id);
    }
}
