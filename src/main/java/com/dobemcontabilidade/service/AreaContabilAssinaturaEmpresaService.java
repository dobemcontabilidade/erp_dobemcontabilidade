package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AreaContabilAssinaturaEmpresa;
import com.dobemcontabilidade.repository.AreaContabilAssinaturaEmpresaRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AreaContabilAssinaturaEmpresa}.
 */
@Service
@Transactional
public class AreaContabilAssinaturaEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(AreaContabilAssinaturaEmpresaService.class);

    private final AreaContabilAssinaturaEmpresaRepository areaContabilAssinaturaEmpresaRepository;

    public AreaContabilAssinaturaEmpresaService(AreaContabilAssinaturaEmpresaRepository areaContabilAssinaturaEmpresaRepository) {
        this.areaContabilAssinaturaEmpresaRepository = areaContabilAssinaturaEmpresaRepository;
    }

    /**
     * Save a areaContabilAssinaturaEmpresa.
     *
     * @param areaContabilAssinaturaEmpresa the entity to save.
     * @return the persisted entity.
     */
    public AreaContabilAssinaturaEmpresa save(AreaContabilAssinaturaEmpresa areaContabilAssinaturaEmpresa) {
        log.debug("Request to save AreaContabilAssinaturaEmpresa : {}", areaContabilAssinaturaEmpresa);
        return areaContabilAssinaturaEmpresaRepository.save(areaContabilAssinaturaEmpresa);
    }

    /**
     * Update a areaContabilAssinaturaEmpresa.
     *
     * @param areaContabilAssinaturaEmpresa the entity to save.
     * @return the persisted entity.
     */
    public AreaContabilAssinaturaEmpresa update(AreaContabilAssinaturaEmpresa areaContabilAssinaturaEmpresa) {
        log.debug("Request to update AreaContabilAssinaturaEmpresa : {}", areaContabilAssinaturaEmpresa);
        return areaContabilAssinaturaEmpresaRepository.save(areaContabilAssinaturaEmpresa);
    }

    /**
     * Partially update a areaContabilAssinaturaEmpresa.
     *
     * @param areaContabilAssinaturaEmpresa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AreaContabilAssinaturaEmpresa> partialUpdate(AreaContabilAssinaturaEmpresa areaContabilAssinaturaEmpresa) {
        log.debug("Request to partially update AreaContabilAssinaturaEmpresa : {}", areaContabilAssinaturaEmpresa);

        return areaContabilAssinaturaEmpresaRepository
            .findById(areaContabilAssinaturaEmpresa.getId())
            .map(existingAreaContabilAssinaturaEmpresa -> {
                if (areaContabilAssinaturaEmpresa.getAtivo() != null) {
                    existingAreaContabilAssinaturaEmpresa.setAtivo(areaContabilAssinaturaEmpresa.getAtivo());
                }
                if (areaContabilAssinaturaEmpresa.getDataAtribuicao() != null) {
                    existingAreaContabilAssinaturaEmpresa.setDataAtribuicao(areaContabilAssinaturaEmpresa.getDataAtribuicao());
                }
                if (areaContabilAssinaturaEmpresa.getDataRevogacao() != null) {
                    existingAreaContabilAssinaturaEmpresa.setDataRevogacao(areaContabilAssinaturaEmpresa.getDataRevogacao());
                }

                return existingAreaContabilAssinaturaEmpresa;
            })
            .map(areaContabilAssinaturaEmpresaRepository::save);
    }

    /**
     * Get all the areaContabilAssinaturaEmpresas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AreaContabilAssinaturaEmpresa> findAll() {
        log.debug("Request to get all AreaContabilAssinaturaEmpresas");
        return areaContabilAssinaturaEmpresaRepository.findAll();
    }

    /**
     * Get all the areaContabilAssinaturaEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AreaContabilAssinaturaEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return areaContabilAssinaturaEmpresaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one areaContabilAssinaturaEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AreaContabilAssinaturaEmpresa> findOne(Long id) {
        log.debug("Request to get AreaContabilAssinaturaEmpresa : {}", id);
        return areaContabilAssinaturaEmpresaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the areaContabilAssinaturaEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AreaContabilAssinaturaEmpresa : {}", id);
        areaContabilAssinaturaEmpresaRepository.deleteById(id);
    }
}
