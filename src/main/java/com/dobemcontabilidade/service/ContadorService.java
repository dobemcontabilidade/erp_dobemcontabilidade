package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.repository.ContadorRepository;
import com.dobemcontabilidade.service.dto.ContadorDTO;
import com.dobemcontabilidade.service.mapper.ContadorMapper;
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
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Contador}.
 */
@Service
@Transactional
public class ContadorService {

    private static final Logger log = LoggerFactory.getLogger(ContadorService.class);

    private final ContadorRepository contadorRepository;

    private final ContadorMapper contadorMapper;

    public ContadorService(ContadorRepository contadorRepository, ContadorMapper contadorMapper) {
        this.contadorRepository = contadorRepository;
        this.contadorMapper = contadorMapper;
    }

    /**
     * Save a contador.
     *
     * @param contadorDTO the entity to save.
     * @return the persisted entity.
     */
    public ContadorDTO save(ContadorDTO contadorDTO) {
        log.debug("Request to save Contador : {}", contadorDTO);
        Contador contador = contadorMapper.toEntity(contadorDTO);
        contador = contadorRepository.save(contador);
        return contadorMapper.toDto(contador);
    }

    /**
     * Update a contador.
     *
     * @param contadorDTO the entity to save.
     * @return the persisted entity.
     */
    public ContadorDTO update(ContadorDTO contadorDTO) {
        log.debug("Request to update Contador : {}", contadorDTO);
        Contador contador = contadorMapper.toEntity(contadorDTO);
        contador = contadorRepository.save(contador);
        return contadorMapper.toDto(contador);
    }

    /**
     * Partially update a contador.
     *
     * @param contadorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ContadorDTO> partialUpdate(ContadorDTO contadorDTO) {
        log.debug("Request to partially update Contador : {}", contadorDTO);

        return contadorRepository
            .findById(contadorDTO.getId())
            .map(existingContador -> {
                contadorMapper.partialUpdate(existingContador, contadorDTO);

                return existingContador;
            })
            .map(contadorRepository::save)
            .map(contadorMapper::toDto);
    }

    /**
     * Get all the contadors with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ContadorDTO> findAllWithEagerRelationships(Pageable pageable) {
        return contadorRepository.findAllWithEagerRelationships(pageable).map(contadorMapper::toDto);
    }

    /**
     *  Get all the contadors where UsuarioContador is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ContadorDTO> findAllWhereUsuarioContadorIsNull() {
        log.debug("Request to get all contadors where UsuarioContador is null");
        return StreamSupport.stream(contadorRepository.findAll().spliterator(), false)
            .filter(contador -> contador.getUsuarioContador() == null)
            .map(contadorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one contador by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContadorDTO> findOne(Long id) {
        log.debug("Request to get Contador : {}", id);
        return contadorRepository.findOneWithEagerRelationships(id).map(contadorMapper::toDto);
    }

    /**
     * Delete the contador by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Contador : {}", id);
        contadorRepository.deleteById(id);
    }
}
