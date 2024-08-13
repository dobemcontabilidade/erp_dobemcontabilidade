package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.FuncionalidadeGrupoAcessoEmpresa;
import com.dobemcontabilidade.repository.FuncionalidadeGrupoAcessoEmpresaRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.FuncionalidadeGrupoAcessoEmpresa}.
 */
@Service
@Transactional
public class FuncionalidadeGrupoAcessoEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(FuncionalidadeGrupoAcessoEmpresaService.class);

    private final FuncionalidadeGrupoAcessoEmpresaRepository funcionalidadeGrupoAcessoEmpresaRepository;

    public FuncionalidadeGrupoAcessoEmpresaService(FuncionalidadeGrupoAcessoEmpresaRepository funcionalidadeGrupoAcessoEmpresaRepository) {
        this.funcionalidadeGrupoAcessoEmpresaRepository = funcionalidadeGrupoAcessoEmpresaRepository;
    }

    /**
     * Save a funcionalidadeGrupoAcessoEmpresa.
     *
     * @param funcionalidadeGrupoAcessoEmpresa the entity to save.
     * @return the persisted entity.
     */
    public FuncionalidadeGrupoAcessoEmpresa save(FuncionalidadeGrupoAcessoEmpresa funcionalidadeGrupoAcessoEmpresa) {
        log.debug("Request to save FuncionalidadeGrupoAcessoEmpresa : {}", funcionalidadeGrupoAcessoEmpresa);
        return funcionalidadeGrupoAcessoEmpresaRepository.save(funcionalidadeGrupoAcessoEmpresa);
    }

    /**
     * Update a funcionalidadeGrupoAcessoEmpresa.
     *
     * @param funcionalidadeGrupoAcessoEmpresa the entity to save.
     * @return the persisted entity.
     */
    public FuncionalidadeGrupoAcessoEmpresa update(FuncionalidadeGrupoAcessoEmpresa funcionalidadeGrupoAcessoEmpresa) {
        log.debug("Request to update FuncionalidadeGrupoAcessoEmpresa : {}", funcionalidadeGrupoAcessoEmpresa);
        return funcionalidadeGrupoAcessoEmpresaRepository.save(funcionalidadeGrupoAcessoEmpresa);
    }

    /**
     * Partially update a funcionalidadeGrupoAcessoEmpresa.
     *
     * @param funcionalidadeGrupoAcessoEmpresa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FuncionalidadeGrupoAcessoEmpresa> partialUpdate(FuncionalidadeGrupoAcessoEmpresa funcionalidadeGrupoAcessoEmpresa) {
        log.debug("Request to partially update FuncionalidadeGrupoAcessoEmpresa : {}", funcionalidadeGrupoAcessoEmpresa);

        return funcionalidadeGrupoAcessoEmpresaRepository
            .findById(funcionalidadeGrupoAcessoEmpresa.getId())
            .map(existingFuncionalidadeGrupoAcessoEmpresa -> {
                if (funcionalidadeGrupoAcessoEmpresa.getAtiva() != null) {
                    existingFuncionalidadeGrupoAcessoEmpresa.setAtiva(funcionalidadeGrupoAcessoEmpresa.getAtiva());
                }
                if (funcionalidadeGrupoAcessoEmpresa.getDataExpiracao() != null) {
                    existingFuncionalidadeGrupoAcessoEmpresa.setDataExpiracao(funcionalidadeGrupoAcessoEmpresa.getDataExpiracao());
                }
                if (funcionalidadeGrupoAcessoEmpresa.getIlimitado() != null) {
                    existingFuncionalidadeGrupoAcessoEmpresa.setIlimitado(funcionalidadeGrupoAcessoEmpresa.getIlimitado());
                }
                if (funcionalidadeGrupoAcessoEmpresa.getDesabilitar() != null) {
                    existingFuncionalidadeGrupoAcessoEmpresa.setDesabilitar(funcionalidadeGrupoAcessoEmpresa.getDesabilitar());
                }

                return existingFuncionalidadeGrupoAcessoEmpresa;
            })
            .map(funcionalidadeGrupoAcessoEmpresaRepository::save);
    }

    /**
     * Get all the funcionalidadeGrupoAcessoEmpresas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FuncionalidadeGrupoAcessoEmpresa> findAll() {
        log.debug("Request to get all FuncionalidadeGrupoAcessoEmpresas");
        return funcionalidadeGrupoAcessoEmpresaRepository.findAll();
    }

    /**
     * Get all the funcionalidadeGrupoAcessoEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<FuncionalidadeGrupoAcessoEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return funcionalidadeGrupoAcessoEmpresaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one funcionalidadeGrupoAcessoEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FuncionalidadeGrupoAcessoEmpresa> findOne(Long id) {
        log.debug("Request to get FuncionalidadeGrupoAcessoEmpresa : {}", id);
        return funcionalidadeGrupoAcessoEmpresaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the funcionalidadeGrupoAcessoEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FuncionalidadeGrupoAcessoEmpresa : {}", id);
        funcionalidadeGrupoAcessoEmpresaRepository.deleteById(id);
    }
}
