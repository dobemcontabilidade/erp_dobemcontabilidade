package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AnexoRequeridoEmpresa;
import com.dobemcontabilidade.repository.AnexoRequeridoEmpresaRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AnexoRequeridoEmpresa}.
 */
@Service
@Transactional
public class AnexoRequeridoEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(AnexoRequeridoEmpresaService.class);

    private final AnexoRequeridoEmpresaRepository anexoRequeridoEmpresaRepository;

    public AnexoRequeridoEmpresaService(AnexoRequeridoEmpresaRepository anexoRequeridoEmpresaRepository) {
        this.anexoRequeridoEmpresaRepository = anexoRequeridoEmpresaRepository;
    }

    /**
     * Save a anexoRequeridoEmpresa.
     *
     * @param anexoRequeridoEmpresa the entity to save.
     * @return the persisted entity.
     */
    public AnexoRequeridoEmpresa save(AnexoRequeridoEmpresa anexoRequeridoEmpresa) {
        log.debug("Request to save AnexoRequeridoEmpresa : {}", anexoRequeridoEmpresa);
        return anexoRequeridoEmpresaRepository.save(anexoRequeridoEmpresa);
    }

    /**
     * Update a anexoRequeridoEmpresa.
     *
     * @param anexoRequeridoEmpresa the entity to save.
     * @return the persisted entity.
     */
    public AnexoRequeridoEmpresa update(AnexoRequeridoEmpresa anexoRequeridoEmpresa) {
        log.debug("Request to update AnexoRequeridoEmpresa : {}", anexoRequeridoEmpresa);
        return anexoRequeridoEmpresaRepository.save(anexoRequeridoEmpresa);
    }

    /**
     * Partially update a anexoRequeridoEmpresa.
     *
     * @param anexoRequeridoEmpresa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AnexoRequeridoEmpresa> partialUpdate(AnexoRequeridoEmpresa anexoRequeridoEmpresa) {
        log.debug("Request to partially update AnexoRequeridoEmpresa : {}", anexoRequeridoEmpresa);

        return anexoRequeridoEmpresaRepository
            .findById(anexoRequeridoEmpresa.getId())
            .map(existingAnexoRequeridoEmpresa -> {
                if (anexoRequeridoEmpresa.getObrigatorio() != null) {
                    existingAnexoRequeridoEmpresa.setObrigatorio(anexoRequeridoEmpresa.getObrigatorio());
                }
                if (anexoRequeridoEmpresa.getUrlArquivo() != null) {
                    existingAnexoRequeridoEmpresa.setUrlArquivo(anexoRequeridoEmpresa.getUrlArquivo());
                }

                return existingAnexoRequeridoEmpresa;
            })
            .map(anexoRequeridoEmpresaRepository::save);
    }

    /**
     * Get all the anexoRequeridoEmpresas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AnexoRequeridoEmpresa> findAll() {
        log.debug("Request to get all AnexoRequeridoEmpresas");
        return anexoRequeridoEmpresaRepository.findAll();
    }

    /**
     * Get all the anexoRequeridoEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AnexoRequeridoEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return anexoRequeridoEmpresaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one anexoRequeridoEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnexoRequeridoEmpresa> findOne(Long id) {
        log.debug("Request to get AnexoRequeridoEmpresa : {}", id);
        return anexoRequeridoEmpresaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the anexoRequeridoEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnexoRequeridoEmpresa : {}", id);
        anexoRequeridoEmpresaRepository.deleteById(id);
    }
}
