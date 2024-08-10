package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Administrador;
import com.dobemcontabilidade.repository.AdministradorRepository;
import com.dobemcontabilidade.service.dto.AdministradorDTO;
import com.dobemcontabilidade.service.mapper.AdministradorMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

    private final AdministradorMapper administradorMapper;

    public AdministradorService(AdministradorRepository administradorRepository, AdministradorMapper administradorMapper) {
        this.administradorRepository = administradorRepository;
        this.administradorMapper = administradorMapper;
    }

    /**
     * Save a administrador.
     *
     * @param administradorDTO the entity to save.
     * @return the persisted entity.
     */
    public AdministradorDTO save(AdministradorDTO administradorDTO) {
        log.debug("Request to save Administrador : {}", administradorDTO);
        Administrador administrador = administradorMapper.toEntity(administradorDTO);
        administrador = administradorRepository.save(administrador);
        return administradorMapper.toDto(administrador);
    }

    /**
     * Update a administrador.
     *
     * @param administradorDTO the entity to save.
     * @return the persisted entity.
     */
    public AdministradorDTO update(AdministradorDTO administradorDTO) {
        log.debug("Request to update Administrador : {}", administradorDTO);
        Administrador administrador = administradorMapper.toEntity(administradorDTO);
        administrador = administradorRepository.save(administrador);
        return administradorMapper.toDto(administrador);
    }

    /**
     * Partially update a administrador.
     *
     * @param administradorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AdministradorDTO> partialUpdate(AdministradorDTO administradorDTO) {
        log.debug("Request to partially update Administrador : {}", administradorDTO);

        return administradorRepository
            .findById(administradorDTO.getId())
            .map(existingAdministrador -> {
                administradorMapper.partialUpdate(existingAdministrador, administradorDTO);

                return existingAdministrador;
            })
            .map(administradorRepository::save)
            .map(administradorMapper::toDto);
    }

    /**
     * Get all the administradors.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AdministradorDTO> findAll() {
        log.debug("Request to get all Administradors");
        return administradorRepository.findAll().stream().map(administradorMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the administradors with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AdministradorDTO> findAllWithEagerRelationships(Pageable pageable) {
        return administradorRepository.findAllWithEagerRelationships(pageable).map(administradorMapper::toDto);
    }

    /**
     *  Get all the administradors where UsuarioErp is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AdministradorDTO> findAllWhereUsuarioErpIsNull() {
        log.debug("Request to get all administradors where UsuarioErp is null");
        return StreamSupport.stream(administradorRepository.findAll().spliterator(), false)
            .filter(administrador -> administrador.getUsuarioErp() == null)
            .map(administradorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the administradors where UsuarioGestao is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AdministradorDTO> findAllWhereUsuarioGestaoIsNull() {
        log.debug("Request to get all administradors where UsuarioGestao is null");
        return StreamSupport.stream(administradorRepository.findAll().spliterator(), false)
            .filter(administrador -> administrador.getUsuarioGestao() == null)
            .map(administradorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one administrador by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdministradorDTO> findOne(Long id) {
        log.debug("Request to get Administrador : {}", id);
        return administradorRepository.findOneWithEagerRelationships(id).map(administradorMapper::toDto);
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
