package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.OrdemServico;
import com.dobemcontabilidade.repository.OrdemServicoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.OrdemServico}.
 */
@Service
@Transactional
public class OrdemServicoService {

    private static final Logger log = LoggerFactory.getLogger(OrdemServicoService.class);

    private final OrdemServicoRepository ordemServicoRepository;

    public OrdemServicoService(OrdemServicoRepository ordemServicoRepository) {
        this.ordemServicoRepository = ordemServicoRepository;
    }

    /**
     * Save a ordemServico.
     *
     * @param ordemServico the entity to save.
     * @return the persisted entity.
     */
    public OrdemServico save(OrdemServico ordemServico) {
        log.debug("Request to save OrdemServico : {}", ordemServico);
        return ordemServicoRepository.save(ordemServico);
    }

    /**
     * Update a ordemServico.
     *
     * @param ordemServico the entity to save.
     * @return the persisted entity.
     */
    public OrdemServico update(OrdemServico ordemServico) {
        log.debug("Request to update OrdemServico : {}", ordemServico);
        return ordemServicoRepository.save(ordemServico);
    }

    /**
     * Partially update a ordemServico.
     *
     * @param ordemServico the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OrdemServico> partialUpdate(OrdemServico ordemServico) {
        log.debug("Request to partially update OrdemServico : {}", ordemServico);

        return ordemServicoRepository
            .findById(ordemServico.getId())
            .map(existingOrdemServico -> {
                if (ordemServico.getValor() != null) {
                    existingOrdemServico.setValor(ordemServico.getValor());
                }
                if (ordemServico.getPrazo() != null) {
                    existingOrdemServico.setPrazo(ordemServico.getPrazo());
                }
                if (ordemServico.getDataCriacao() != null) {
                    existingOrdemServico.setDataCriacao(ordemServico.getDataCriacao());
                }
                if (ordemServico.getDataHoraCancelamento() != null) {
                    existingOrdemServico.setDataHoraCancelamento(ordemServico.getDataHoraCancelamento());
                }
                if (ordemServico.getStatusDaOS() != null) {
                    existingOrdemServico.setStatusDaOS(ordemServico.getStatusDaOS());
                }
                if (ordemServico.getDescricao() != null) {
                    existingOrdemServico.setDescricao(ordemServico.getDescricao());
                }

                return existingOrdemServico;
            })
            .map(ordemServicoRepository::save);
    }

    /**
     * Get all the ordemServicos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrdemServico> findAll() {
        log.debug("Request to get all OrdemServicos");
        return ordemServicoRepository.findAll();
    }

    /**
     * Get all the ordemServicos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<OrdemServico> findAllWithEagerRelationships(Pageable pageable) {
        return ordemServicoRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one ordemServico by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrdemServico> findOne(Long id) {
        log.debug("Request to get OrdemServico : {}", id);
        return ordemServicoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the ordemServico by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrdemServico : {}", id);
        ordemServicoRepository.deleteById(id);
    }
}
