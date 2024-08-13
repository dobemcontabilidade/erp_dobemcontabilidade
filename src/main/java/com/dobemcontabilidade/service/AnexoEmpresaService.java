package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AnexoEmpresa;
import com.dobemcontabilidade.repository.AnexoEmpresaRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AnexoEmpresa}.
 */
@Service
@Transactional
public class AnexoEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(AnexoEmpresaService.class);

    private final AnexoEmpresaRepository anexoEmpresaRepository;

    public AnexoEmpresaService(AnexoEmpresaRepository anexoEmpresaRepository) {
        this.anexoEmpresaRepository = anexoEmpresaRepository;
    }

    /**
     * Save a anexoEmpresa.
     *
     * @param anexoEmpresa the entity to save.
     * @return the persisted entity.
     */
    public AnexoEmpresa save(AnexoEmpresa anexoEmpresa) {
        log.debug("Request to save AnexoEmpresa : {}", anexoEmpresa);
        return anexoEmpresaRepository.save(anexoEmpresa);
    }

    /**
     * Update a anexoEmpresa.
     *
     * @param anexoEmpresa the entity to save.
     * @return the persisted entity.
     */
    public AnexoEmpresa update(AnexoEmpresa anexoEmpresa) {
        log.debug("Request to update AnexoEmpresa : {}", anexoEmpresa);
        return anexoEmpresaRepository.save(anexoEmpresa);
    }

    /**
     * Partially update a anexoEmpresa.
     *
     * @param anexoEmpresa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AnexoEmpresa> partialUpdate(AnexoEmpresa anexoEmpresa) {
        log.debug("Request to partially update AnexoEmpresa : {}", anexoEmpresa);

        return anexoEmpresaRepository
            .findById(anexoEmpresa.getId())
            .map(existingAnexoEmpresa -> {
                if (anexoEmpresa.getUrlAnexo() != null) {
                    existingAnexoEmpresa.setUrlAnexo(anexoEmpresa.getUrlAnexo());
                }

                return existingAnexoEmpresa;
            })
            .map(anexoEmpresaRepository::save);
    }

    /**
     * Get all the anexoEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AnexoEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return anexoEmpresaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one anexoEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnexoEmpresa> findOne(Long id) {
        log.debug("Request to get AnexoEmpresa : {}", id);
        return anexoEmpresaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the anexoEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnexoEmpresa : {}", id);
        anexoEmpresaRepository.deleteById(id);
    }
}
