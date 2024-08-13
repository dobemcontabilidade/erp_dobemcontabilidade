package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AnexoServicoContabilEmpresa;
import com.dobemcontabilidade.repository.AnexoServicoContabilEmpresaRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AnexoServicoContabilEmpresa}.
 */
@Service
@Transactional
public class AnexoServicoContabilEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(AnexoServicoContabilEmpresaService.class);

    private final AnexoServicoContabilEmpresaRepository anexoServicoContabilEmpresaRepository;

    public AnexoServicoContabilEmpresaService(AnexoServicoContabilEmpresaRepository anexoServicoContabilEmpresaRepository) {
        this.anexoServicoContabilEmpresaRepository = anexoServicoContabilEmpresaRepository;
    }

    /**
     * Save a anexoServicoContabilEmpresa.
     *
     * @param anexoServicoContabilEmpresa the entity to save.
     * @return the persisted entity.
     */
    public AnexoServicoContabilEmpresa save(AnexoServicoContabilEmpresa anexoServicoContabilEmpresa) {
        log.debug("Request to save AnexoServicoContabilEmpresa : {}", anexoServicoContabilEmpresa);
        return anexoServicoContabilEmpresaRepository.save(anexoServicoContabilEmpresa);
    }

    /**
     * Update a anexoServicoContabilEmpresa.
     *
     * @param anexoServicoContabilEmpresa the entity to save.
     * @return the persisted entity.
     */
    public AnexoServicoContabilEmpresa update(AnexoServicoContabilEmpresa anexoServicoContabilEmpresa) {
        log.debug("Request to update AnexoServicoContabilEmpresa : {}", anexoServicoContabilEmpresa);
        return anexoServicoContabilEmpresaRepository.save(anexoServicoContabilEmpresa);
    }

    /**
     * Partially update a anexoServicoContabilEmpresa.
     *
     * @param anexoServicoContabilEmpresa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AnexoServicoContabilEmpresa> partialUpdate(AnexoServicoContabilEmpresa anexoServicoContabilEmpresa) {
        log.debug("Request to partially update AnexoServicoContabilEmpresa : {}", anexoServicoContabilEmpresa);

        return anexoServicoContabilEmpresaRepository
            .findById(anexoServicoContabilEmpresa.getId())
            .map(existingAnexoServicoContabilEmpresa -> {
                if (anexoServicoContabilEmpresa.getLink() != null) {
                    existingAnexoServicoContabilEmpresa.setLink(anexoServicoContabilEmpresa.getLink());
                }
                if (anexoServicoContabilEmpresa.getDataHoraUpload() != null) {
                    existingAnexoServicoContabilEmpresa.setDataHoraUpload(anexoServicoContabilEmpresa.getDataHoraUpload());
                }

                return existingAnexoServicoContabilEmpresa;
            })
            .map(anexoServicoContabilEmpresaRepository::save);
    }

    /**
     * Get all the anexoServicoContabilEmpresas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AnexoServicoContabilEmpresa> findAll() {
        log.debug("Request to get all AnexoServicoContabilEmpresas");
        return anexoServicoContabilEmpresaRepository.findAll();
    }

    /**
     * Get all the anexoServicoContabilEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AnexoServicoContabilEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return anexoServicoContabilEmpresaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one anexoServicoContabilEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnexoServicoContabilEmpresa> findOne(Long id) {
        log.debug("Request to get AnexoServicoContabilEmpresa : {}", id);
        return anexoServicoContabilEmpresaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the anexoServicoContabilEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnexoServicoContabilEmpresa : {}", id);
        anexoServicoContabilEmpresaRepository.deleteById(id);
    }
}
