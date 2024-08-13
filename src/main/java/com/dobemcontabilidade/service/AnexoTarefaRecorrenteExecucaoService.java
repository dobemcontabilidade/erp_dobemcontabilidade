package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AnexoTarefaRecorrenteExecucao;
import com.dobemcontabilidade.repository.AnexoTarefaRecorrenteExecucaoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AnexoTarefaRecorrenteExecucao}.
 */
@Service
@Transactional
public class AnexoTarefaRecorrenteExecucaoService {

    private static final Logger log = LoggerFactory.getLogger(AnexoTarefaRecorrenteExecucaoService.class);

    private final AnexoTarefaRecorrenteExecucaoRepository anexoTarefaRecorrenteExecucaoRepository;

    public AnexoTarefaRecorrenteExecucaoService(AnexoTarefaRecorrenteExecucaoRepository anexoTarefaRecorrenteExecucaoRepository) {
        this.anexoTarefaRecorrenteExecucaoRepository = anexoTarefaRecorrenteExecucaoRepository;
    }

    /**
     * Save a anexoTarefaRecorrenteExecucao.
     *
     * @param anexoTarefaRecorrenteExecucao the entity to save.
     * @return the persisted entity.
     */
    public AnexoTarefaRecorrenteExecucao save(AnexoTarefaRecorrenteExecucao anexoTarefaRecorrenteExecucao) {
        log.debug("Request to save AnexoTarefaRecorrenteExecucao : {}", anexoTarefaRecorrenteExecucao);
        return anexoTarefaRecorrenteExecucaoRepository.save(anexoTarefaRecorrenteExecucao);
    }

    /**
     * Update a anexoTarefaRecorrenteExecucao.
     *
     * @param anexoTarefaRecorrenteExecucao the entity to save.
     * @return the persisted entity.
     */
    public AnexoTarefaRecorrenteExecucao update(AnexoTarefaRecorrenteExecucao anexoTarefaRecorrenteExecucao) {
        log.debug("Request to update AnexoTarefaRecorrenteExecucao : {}", anexoTarefaRecorrenteExecucao);
        return anexoTarefaRecorrenteExecucaoRepository.save(anexoTarefaRecorrenteExecucao);
    }

    /**
     * Partially update a anexoTarefaRecorrenteExecucao.
     *
     * @param anexoTarefaRecorrenteExecucao the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AnexoTarefaRecorrenteExecucao> partialUpdate(AnexoTarefaRecorrenteExecucao anexoTarefaRecorrenteExecucao) {
        log.debug("Request to partially update AnexoTarefaRecorrenteExecucao : {}", anexoTarefaRecorrenteExecucao);

        return anexoTarefaRecorrenteExecucaoRepository
            .findById(anexoTarefaRecorrenteExecucao.getId())
            .map(existingAnexoTarefaRecorrenteExecucao -> {
                if (anexoTarefaRecorrenteExecucao.getUrl() != null) {
                    existingAnexoTarefaRecorrenteExecucao.setUrl(anexoTarefaRecorrenteExecucao.getUrl());
                }
                if (anexoTarefaRecorrenteExecucao.getDescricao() != null) {
                    existingAnexoTarefaRecorrenteExecucao.setDescricao(anexoTarefaRecorrenteExecucao.getDescricao());
                }
                if (anexoTarefaRecorrenteExecucao.getDataHoraUpload() != null) {
                    existingAnexoTarefaRecorrenteExecucao.setDataHoraUpload(anexoTarefaRecorrenteExecucao.getDataHoraUpload());
                }

                return existingAnexoTarefaRecorrenteExecucao;
            })
            .map(anexoTarefaRecorrenteExecucaoRepository::save);
    }

    /**
     * Get all the anexoTarefaRecorrenteExecucaos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AnexoTarefaRecorrenteExecucao> findAll() {
        log.debug("Request to get all AnexoTarefaRecorrenteExecucaos");
        return anexoTarefaRecorrenteExecucaoRepository.findAll();
    }

    /**
     * Get all the anexoTarefaRecorrenteExecucaos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AnexoTarefaRecorrenteExecucao> findAllWithEagerRelationships(Pageable pageable) {
        return anexoTarefaRecorrenteExecucaoRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one anexoTarefaRecorrenteExecucao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnexoTarefaRecorrenteExecucao> findOne(Long id) {
        log.debug("Request to get AnexoTarefaRecorrenteExecucao : {}", id);
        return anexoTarefaRecorrenteExecucaoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the anexoTarefaRecorrenteExecucao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnexoTarefaRecorrenteExecucao : {}", id);
        anexoTarefaRecorrenteExecucaoRepository.deleteById(id);
    }
}
