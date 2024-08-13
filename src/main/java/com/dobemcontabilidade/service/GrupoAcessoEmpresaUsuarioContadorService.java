package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.GrupoAcessoEmpresaUsuarioContador;
import com.dobemcontabilidade.repository.GrupoAcessoEmpresaUsuarioContadorRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.GrupoAcessoEmpresaUsuarioContador}.
 */
@Service
@Transactional
public class GrupoAcessoEmpresaUsuarioContadorService {

    private static final Logger log = LoggerFactory.getLogger(GrupoAcessoEmpresaUsuarioContadorService.class);

    private final GrupoAcessoEmpresaUsuarioContadorRepository grupoAcessoEmpresaUsuarioContadorRepository;

    public GrupoAcessoEmpresaUsuarioContadorService(
        GrupoAcessoEmpresaUsuarioContadorRepository grupoAcessoEmpresaUsuarioContadorRepository
    ) {
        this.grupoAcessoEmpresaUsuarioContadorRepository = grupoAcessoEmpresaUsuarioContadorRepository;
    }

    /**
     * Save a grupoAcessoEmpresaUsuarioContador.
     *
     * @param grupoAcessoEmpresaUsuarioContador the entity to save.
     * @return the persisted entity.
     */
    public GrupoAcessoEmpresaUsuarioContador save(GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresaUsuarioContador) {
        log.debug("Request to save GrupoAcessoEmpresaUsuarioContador : {}", grupoAcessoEmpresaUsuarioContador);
        return grupoAcessoEmpresaUsuarioContadorRepository.save(grupoAcessoEmpresaUsuarioContador);
    }

    /**
     * Update a grupoAcessoEmpresaUsuarioContador.
     *
     * @param grupoAcessoEmpresaUsuarioContador the entity to save.
     * @return the persisted entity.
     */
    public GrupoAcessoEmpresaUsuarioContador update(GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresaUsuarioContador) {
        log.debug("Request to update GrupoAcessoEmpresaUsuarioContador : {}", grupoAcessoEmpresaUsuarioContador);
        return grupoAcessoEmpresaUsuarioContadorRepository.save(grupoAcessoEmpresaUsuarioContador);
    }

    /**
     * Partially update a grupoAcessoEmpresaUsuarioContador.
     *
     * @param grupoAcessoEmpresaUsuarioContador the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GrupoAcessoEmpresaUsuarioContador> partialUpdate(GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresaUsuarioContador) {
        log.debug("Request to partially update GrupoAcessoEmpresaUsuarioContador : {}", grupoAcessoEmpresaUsuarioContador);

        return grupoAcessoEmpresaUsuarioContadorRepository
            .findById(grupoAcessoEmpresaUsuarioContador.getId())
            .map(existingGrupoAcessoEmpresaUsuarioContador -> {
                if (grupoAcessoEmpresaUsuarioContador.getNome() != null) {
                    existingGrupoAcessoEmpresaUsuarioContador.setNome(grupoAcessoEmpresaUsuarioContador.getNome());
                }
                if (grupoAcessoEmpresaUsuarioContador.getDataExpiracao() != null) {
                    existingGrupoAcessoEmpresaUsuarioContador.setDataExpiracao(grupoAcessoEmpresaUsuarioContador.getDataExpiracao());
                }
                if (grupoAcessoEmpresaUsuarioContador.getIlimitado() != null) {
                    existingGrupoAcessoEmpresaUsuarioContador.setIlimitado(grupoAcessoEmpresaUsuarioContador.getIlimitado());
                }
                if (grupoAcessoEmpresaUsuarioContador.getDesabilitar() != null) {
                    existingGrupoAcessoEmpresaUsuarioContador.setDesabilitar(grupoAcessoEmpresaUsuarioContador.getDesabilitar());
                }

                return existingGrupoAcessoEmpresaUsuarioContador;
            })
            .map(grupoAcessoEmpresaUsuarioContadorRepository::save);
    }

    /**
     * Get all the grupoAcessoEmpresaUsuarioContadors.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GrupoAcessoEmpresaUsuarioContador> findAll() {
        log.debug("Request to get all GrupoAcessoEmpresaUsuarioContadors");
        return grupoAcessoEmpresaUsuarioContadorRepository.findAll();
    }

    /**
     * Get all the grupoAcessoEmpresaUsuarioContadors with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<GrupoAcessoEmpresaUsuarioContador> findAllWithEagerRelationships(Pageable pageable) {
        return grupoAcessoEmpresaUsuarioContadorRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one grupoAcessoEmpresaUsuarioContador by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GrupoAcessoEmpresaUsuarioContador> findOne(Long id) {
        log.debug("Request to get GrupoAcessoEmpresaUsuarioContador : {}", id);
        return grupoAcessoEmpresaUsuarioContadorRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the grupoAcessoEmpresaUsuarioContador by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GrupoAcessoEmpresaUsuarioContador : {}", id);
        grupoAcessoEmpresaUsuarioContadorRepository.deleteById(id);
    }
}
