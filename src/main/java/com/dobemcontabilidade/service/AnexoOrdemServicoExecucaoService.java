package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AnexoOrdemServicoExecucao;
import com.dobemcontabilidade.repository.AnexoOrdemServicoExecucaoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AnexoOrdemServicoExecucao}.
 */
@Service
@Transactional
public class AnexoOrdemServicoExecucaoService {

    private static final Logger log = LoggerFactory.getLogger(AnexoOrdemServicoExecucaoService.class);

    private final AnexoOrdemServicoExecucaoRepository anexoOrdemServicoExecucaoRepository;

    public AnexoOrdemServicoExecucaoService(AnexoOrdemServicoExecucaoRepository anexoOrdemServicoExecucaoRepository) {
        this.anexoOrdemServicoExecucaoRepository = anexoOrdemServicoExecucaoRepository;
    }

    /**
     * Save a anexoOrdemServicoExecucao.
     *
     * @param anexoOrdemServicoExecucao the entity to save.
     * @return the persisted entity.
     */
    public AnexoOrdemServicoExecucao save(AnexoOrdemServicoExecucao anexoOrdemServicoExecucao) {
        log.debug("Request to save AnexoOrdemServicoExecucao : {}", anexoOrdemServicoExecucao);
        return anexoOrdemServicoExecucaoRepository.save(anexoOrdemServicoExecucao);
    }

    /**
     * Update a anexoOrdemServicoExecucao.
     *
     * @param anexoOrdemServicoExecucao the entity to save.
     * @return the persisted entity.
     */
    public AnexoOrdemServicoExecucao update(AnexoOrdemServicoExecucao anexoOrdemServicoExecucao) {
        log.debug("Request to update AnexoOrdemServicoExecucao : {}", anexoOrdemServicoExecucao);
        return anexoOrdemServicoExecucaoRepository.save(anexoOrdemServicoExecucao);
    }

    /**
     * Partially update a anexoOrdemServicoExecucao.
     *
     * @param anexoOrdemServicoExecucao the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AnexoOrdemServicoExecucao> partialUpdate(AnexoOrdemServicoExecucao anexoOrdemServicoExecucao) {
        log.debug("Request to partially update AnexoOrdemServicoExecucao : {}", anexoOrdemServicoExecucao);

        return anexoOrdemServicoExecucaoRepository
            .findById(anexoOrdemServicoExecucao.getId())
            .map(existingAnexoOrdemServicoExecucao -> {
                if (anexoOrdemServicoExecucao.getUrl() != null) {
                    existingAnexoOrdemServicoExecucao.setUrl(anexoOrdemServicoExecucao.getUrl());
                }
                if (anexoOrdemServicoExecucao.getDescricao() != null) {
                    existingAnexoOrdemServicoExecucao.setDescricao(anexoOrdemServicoExecucao.getDescricao());
                }
                if (anexoOrdemServicoExecucao.getDataHoraUpload() != null) {
                    existingAnexoOrdemServicoExecucao.setDataHoraUpload(anexoOrdemServicoExecucao.getDataHoraUpload());
                }

                return existingAnexoOrdemServicoExecucao;
            })
            .map(anexoOrdemServicoExecucaoRepository::save);
    }

    /**
     * Get all the anexoOrdemServicoExecucaos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AnexoOrdemServicoExecucao> findAll() {
        log.debug("Request to get all AnexoOrdemServicoExecucaos");
        return anexoOrdemServicoExecucaoRepository.findAll();
    }

    /**
     * Get all the anexoOrdemServicoExecucaos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AnexoOrdemServicoExecucao> findAllWithEagerRelationships(Pageable pageable) {
        return anexoOrdemServicoExecucaoRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one anexoOrdemServicoExecucao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnexoOrdemServicoExecucao> findOne(Long id) {
        log.debug("Request to get AnexoOrdemServicoExecucao : {}", id);
        return anexoOrdemServicoExecucaoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the anexoOrdemServicoExecucao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnexoOrdemServicoExecucao : {}", id);
        anexoOrdemServicoExecucaoRepository.deleteById(id);
    }
}
