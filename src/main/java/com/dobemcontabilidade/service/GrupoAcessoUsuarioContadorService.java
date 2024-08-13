package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.GrupoAcessoUsuarioContador;
import com.dobemcontabilidade.repository.GrupoAcessoUsuarioContadorRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.GrupoAcessoUsuarioContador}.
 */
@Service
@Transactional
public class GrupoAcessoUsuarioContadorService {

    private static final Logger log = LoggerFactory.getLogger(GrupoAcessoUsuarioContadorService.class);

    private final GrupoAcessoUsuarioContadorRepository grupoAcessoUsuarioContadorRepository;

    public GrupoAcessoUsuarioContadorService(GrupoAcessoUsuarioContadorRepository grupoAcessoUsuarioContadorRepository) {
        this.grupoAcessoUsuarioContadorRepository = grupoAcessoUsuarioContadorRepository;
    }

    /**
     * Save a grupoAcessoUsuarioContador.
     *
     * @param grupoAcessoUsuarioContador the entity to save.
     * @return the persisted entity.
     */
    public GrupoAcessoUsuarioContador save(GrupoAcessoUsuarioContador grupoAcessoUsuarioContador) {
        log.debug("Request to save GrupoAcessoUsuarioContador : {}", grupoAcessoUsuarioContador);
        return grupoAcessoUsuarioContadorRepository.save(grupoAcessoUsuarioContador);
    }

    /**
     * Update a grupoAcessoUsuarioContador.
     *
     * @param grupoAcessoUsuarioContador the entity to save.
     * @return the persisted entity.
     */
    public GrupoAcessoUsuarioContador update(GrupoAcessoUsuarioContador grupoAcessoUsuarioContador) {
        log.debug("Request to update GrupoAcessoUsuarioContador : {}", grupoAcessoUsuarioContador);
        return grupoAcessoUsuarioContadorRepository.save(grupoAcessoUsuarioContador);
    }

    /**
     * Partially update a grupoAcessoUsuarioContador.
     *
     * @param grupoAcessoUsuarioContador the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GrupoAcessoUsuarioContador> partialUpdate(GrupoAcessoUsuarioContador grupoAcessoUsuarioContador) {
        log.debug("Request to partially update GrupoAcessoUsuarioContador : {}", grupoAcessoUsuarioContador);

        return grupoAcessoUsuarioContadorRepository
            .findById(grupoAcessoUsuarioContador.getId())
            .map(existingGrupoAcessoUsuarioContador -> {
                if (grupoAcessoUsuarioContador.getDataExpiracao() != null) {
                    existingGrupoAcessoUsuarioContador.setDataExpiracao(grupoAcessoUsuarioContador.getDataExpiracao());
                }
                if (grupoAcessoUsuarioContador.getIlimitado() != null) {
                    existingGrupoAcessoUsuarioContador.setIlimitado(grupoAcessoUsuarioContador.getIlimitado());
                }
                if (grupoAcessoUsuarioContador.getDesabilitar() != null) {
                    existingGrupoAcessoUsuarioContador.setDesabilitar(grupoAcessoUsuarioContador.getDesabilitar());
                }

                return existingGrupoAcessoUsuarioContador;
            })
            .map(grupoAcessoUsuarioContadorRepository::save);
    }

    /**
     * Get all the grupoAcessoUsuarioContadors.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GrupoAcessoUsuarioContador> findAll() {
        log.debug("Request to get all GrupoAcessoUsuarioContadors");
        return grupoAcessoUsuarioContadorRepository.findAll();
    }

    /**
     * Get all the grupoAcessoUsuarioContadors with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<GrupoAcessoUsuarioContador> findAllWithEagerRelationships(Pageable pageable) {
        return grupoAcessoUsuarioContadorRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one grupoAcessoUsuarioContador by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GrupoAcessoUsuarioContador> findOne(Long id) {
        log.debug("Request to get GrupoAcessoUsuarioContador : {}", id);
        return grupoAcessoUsuarioContadorRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the grupoAcessoUsuarioContador by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GrupoAcessoUsuarioContador : {}", id);
        grupoAcessoUsuarioContadorRepository.deleteById(id);
    }
}
