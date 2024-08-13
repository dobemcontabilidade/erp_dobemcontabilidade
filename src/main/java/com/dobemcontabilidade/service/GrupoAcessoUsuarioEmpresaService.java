package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.GrupoAcessoUsuarioEmpresa;
import com.dobemcontabilidade.repository.GrupoAcessoUsuarioEmpresaRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.GrupoAcessoUsuarioEmpresa}.
 */
@Service
@Transactional
public class GrupoAcessoUsuarioEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(GrupoAcessoUsuarioEmpresaService.class);

    private final GrupoAcessoUsuarioEmpresaRepository grupoAcessoUsuarioEmpresaRepository;

    public GrupoAcessoUsuarioEmpresaService(GrupoAcessoUsuarioEmpresaRepository grupoAcessoUsuarioEmpresaRepository) {
        this.grupoAcessoUsuarioEmpresaRepository = grupoAcessoUsuarioEmpresaRepository;
    }

    /**
     * Save a grupoAcessoUsuarioEmpresa.
     *
     * @param grupoAcessoUsuarioEmpresa the entity to save.
     * @return the persisted entity.
     */
    public GrupoAcessoUsuarioEmpresa save(GrupoAcessoUsuarioEmpresa grupoAcessoUsuarioEmpresa) {
        log.debug("Request to save GrupoAcessoUsuarioEmpresa : {}", grupoAcessoUsuarioEmpresa);
        return grupoAcessoUsuarioEmpresaRepository.save(grupoAcessoUsuarioEmpresa);
    }

    /**
     * Update a grupoAcessoUsuarioEmpresa.
     *
     * @param grupoAcessoUsuarioEmpresa the entity to save.
     * @return the persisted entity.
     */
    public GrupoAcessoUsuarioEmpresa update(GrupoAcessoUsuarioEmpresa grupoAcessoUsuarioEmpresa) {
        log.debug("Request to update GrupoAcessoUsuarioEmpresa : {}", grupoAcessoUsuarioEmpresa);
        return grupoAcessoUsuarioEmpresaRepository.save(grupoAcessoUsuarioEmpresa);
    }

    /**
     * Partially update a grupoAcessoUsuarioEmpresa.
     *
     * @param grupoAcessoUsuarioEmpresa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GrupoAcessoUsuarioEmpresa> partialUpdate(GrupoAcessoUsuarioEmpresa grupoAcessoUsuarioEmpresa) {
        log.debug("Request to partially update GrupoAcessoUsuarioEmpresa : {}", grupoAcessoUsuarioEmpresa);

        return grupoAcessoUsuarioEmpresaRepository
            .findById(grupoAcessoUsuarioEmpresa.getId())
            .map(existingGrupoAcessoUsuarioEmpresa -> {
                if (grupoAcessoUsuarioEmpresa.getNome() != null) {
                    existingGrupoAcessoUsuarioEmpresa.setNome(grupoAcessoUsuarioEmpresa.getNome());
                }
                if (grupoAcessoUsuarioEmpresa.getDataExpiracao() != null) {
                    existingGrupoAcessoUsuarioEmpresa.setDataExpiracao(grupoAcessoUsuarioEmpresa.getDataExpiracao());
                }
                if (grupoAcessoUsuarioEmpresa.getIlimitado() != null) {
                    existingGrupoAcessoUsuarioEmpresa.setIlimitado(grupoAcessoUsuarioEmpresa.getIlimitado());
                }
                if (grupoAcessoUsuarioEmpresa.getDesabilitar() != null) {
                    existingGrupoAcessoUsuarioEmpresa.setDesabilitar(grupoAcessoUsuarioEmpresa.getDesabilitar());
                }

                return existingGrupoAcessoUsuarioEmpresa;
            })
            .map(grupoAcessoUsuarioEmpresaRepository::save);
    }

    /**
     * Get all the grupoAcessoUsuarioEmpresas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GrupoAcessoUsuarioEmpresa> findAll() {
        log.debug("Request to get all GrupoAcessoUsuarioEmpresas");
        return grupoAcessoUsuarioEmpresaRepository.findAll();
    }

    /**
     * Get all the grupoAcessoUsuarioEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<GrupoAcessoUsuarioEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return grupoAcessoUsuarioEmpresaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one grupoAcessoUsuarioEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GrupoAcessoUsuarioEmpresa> findOne(Long id) {
        log.debug("Request to get GrupoAcessoUsuarioEmpresa : {}", id);
        return grupoAcessoUsuarioEmpresaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the grupoAcessoUsuarioEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GrupoAcessoUsuarioEmpresa : {}", id);
        grupoAcessoUsuarioEmpresaRepository.deleteById(id);
    }
}
