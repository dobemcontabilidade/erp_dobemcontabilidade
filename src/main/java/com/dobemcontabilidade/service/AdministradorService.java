package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Administrador;
import com.dobemcontabilidade.repository.AdministradorRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Administrador}.
 */
@Service
@Transactional
public class AdministradorService {

    private static final Logger log = LoggerFactory.getLogger(AdministradorService.class);

    private final AdministradorRepository administradorRepository;

    public AdministradorService(AdministradorRepository administradorRepository) {
        this.administradorRepository = administradorRepository;
    }

    /**
     * Save a administrador.
     *
     * @param administrador the entity to save.
     * @return the persisted entity.
     */
    public Administrador save(Administrador administrador) {
        log.debug("Request to save Administrador : {}", administrador);
        return administradorRepository.save(administrador);
    }

    /**
     * Update a administrador.
     *
     * @param administrador the entity to save.
     * @return the persisted entity.
     */
    public Administrador update(Administrador administrador) {
        log.debug("Request to update Administrador : {}", administrador);
        return administradorRepository.save(administrador);
    }

    /**
     * Partially update a administrador.
     *
     * @param administrador the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Administrador> partialUpdate(Administrador administrador) {
        log.debug("Request to partially update Administrador : {}", administrador);

        return administradorRepository
            .findById(administrador.getId())
            .map(existingAdministrador -> {
                if (administrador.getNome() != null) {
                    existingAdministrador.setNome(administrador.getNome());
                }
                if (administrador.getSobrenome() != null) {
                    existingAdministrador.setSobrenome(administrador.getSobrenome());
                }
                if (administrador.getFuncao() != null) {
                    existingAdministrador.setFuncao(administrador.getFuncao());
                }

                return existingAdministrador;
            })
            .map(administradorRepository::save);
    }

    /**
     * Get all the administradors.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Administrador> findAll() {
        log.debug("Request to get all Administradors");
        return administradorRepository.findAll();
    }

    /**
     * Get all the administradors with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Administrador> findAllWithEagerRelationships(Pageable pageable) {
        return administradorRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     *  Get all the administradors where UsuarioGestao is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Administrador> findAllWhereUsuarioGestaoIsNull() {
        log.debug("Request to get all administradors where UsuarioGestao is null");
        return StreamSupport.stream(administradorRepository.findAll().spliterator(), false)
            .filter(administrador -> administrador.getUsuarioGestao() == null)
            .toList();
    }

    /**
     * Get one administrador by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Administrador> findOne(Long id) {
        log.debug("Request to get Administrador : {}", id);
        return administradorRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the administrador by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Administrador : {}", id);
        administradorRepository.deleteById(id);
    }
}
