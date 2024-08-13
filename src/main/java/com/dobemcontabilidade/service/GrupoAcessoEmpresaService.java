package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.GrupoAcessoEmpresa;
import com.dobemcontabilidade.repository.GrupoAcessoEmpresaRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.GrupoAcessoEmpresa}.
 */
@Service
@Transactional
public class GrupoAcessoEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(GrupoAcessoEmpresaService.class);

    private final GrupoAcessoEmpresaRepository grupoAcessoEmpresaRepository;

    public GrupoAcessoEmpresaService(GrupoAcessoEmpresaRepository grupoAcessoEmpresaRepository) {
        this.grupoAcessoEmpresaRepository = grupoAcessoEmpresaRepository;
    }

    /**
     * Save a grupoAcessoEmpresa.
     *
     * @param grupoAcessoEmpresa the entity to save.
     * @return the persisted entity.
     */
    public GrupoAcessoEmpresa save(GrupoAcessoEmpresa grupoAcessoEmpresa) {
        log.debug("Request to save GrupoAcessoEmpresa : {}", grupoAcessoEmpresa);
        return grupoAcessoEmpresaRepository.save(grupoAcessoEmpresa);
    }

    /**
     * Update a grupoAcessoEmpresa.
     *
     * @param grupoAcessoEmpresa the entity to save.
     * @return the persisted entity.
     */
    public GrupoAcessoEmpresa update(GrupoAcessoEmpresa grupoAcessoEmpresa) {
        log.debug("Request to update GrupoAcessoEmpresa : {}", grupoAcessoEmpresa);
        return grupoAcessoEmpresaRepository.save(grupoAcessoEmpresa);
    }

    /**
     * Partially update a grupoAcessoEmpresa.
     *
     * @param grupoAcessoEmpresa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GrupoAcessoEmpresa> partialUpdate(GrupoAcessoEmpresa grupoAcessoEmpresa) {
        log.debug("Request to partially update GrupoAcessoEmpresa : {}", grupoAcessoEmpresa);

        return grupoAcessoEmpresaRepository
            .findById(grupoAcessoEmpresa.getId())
            .map(existingGrupoAcessoEmpresa -> {
                if (grupoAcessoEmpresa.getNome() != null) {
                    existingGrupoAcessoEmpresa.setNome(grupoAcessoEmpresa.getNome());
                }

                return existingGrupoAcessoEmpresa;
            })
            .map(grupoAcessoEmpresaRepository::save);
    }

    /**
     * Get all the grupoAcessoEmpresas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GrupoAcessoEmpresa> findAll() {
        log.debug("Request to get all GrupoAcessoEmpresas");
        return grupoAcessoEmpresaRepository.findAll();
    }

    /**
     * Get all the grupoAcessoEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<GrupoAcessoEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return grupoAcessoEmpresaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one grupoAcessoEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GrupoAcessoEmpresa> findOne(Long id) {
        log.debug("Request to get GrupoAcessoEmpresa : {}", id);
        return grupoAcessoEmpresaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the grupoAcessoEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GrupoAcessoEmpresa : {}", id);
        grupoAcessoEmpresaRepository.deleteById(id);
    }
}
