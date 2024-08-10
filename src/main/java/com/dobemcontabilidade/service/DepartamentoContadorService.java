package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.DepartamentoContador;
import com.dobemcontabilidade.repository.DepartamentoContadorRepository;
import com.dobemcontabilidade.service.dto.DepartamentoContadorDTO;
import com.dobemcontabilidade.service.mapper.DepartamentoContadorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.DepartamentoContador}.
 */
@Service
@Transactional
public class DepartamentoContadorService {

    private static final Logger log = LoggerFactory.getLogger(DepartamentoContadorService.class);

    private final DepartamentoContadorRepository departamentoContadorRepository;

    private final DepartamentoContadorMapper departamentoContadorMapper;

    public DepartamentoContadorService(
        DepartamentoContadorRepository departamentoContadorRepository,
        DepartamentoContadorMapper departamentoContadorMapper
    ) {
        this.departamentoContadorRepository = departamentoContadorRepository;
        this.departamentoContadorMapper = departamentoContadorMapper;
    }

    /**
     * Save a departamentoContador.
     *
     * @param departamentoContadorDTO the entity to save.
     * @return the persisted entity.
     */
    public DepartamentoContadorDTO save(DepartamentoContadorDTO departamentoContadorDTO) {
        log.debug("Request to save DepartamentoContador : {}", departamentoContadorDTO);
        DepartamentoContador departamentoContador = departamentoContadorMapper.toEntity(departamentoContadorDTO);
        departamentoContador = departamentoContadorRepository.save(departamentoContador);
        return departamentoContadorMapper.toDto(departamentoContador);
    }

    /**
     * Update a departamentoContador.
     *
     * @param departamentoContadorDTO the entity to save.
     * @return the persisted entity.
     */
    public DepartamentoContadorDTO update(DepartamentoContadorDTO departamentoContadorDTO) {
        log.debug("Request to update DepartamentoContador : {}", departamentoContadorDTO);
        DepartamentoContador departamentoContador = departamentoContadorMapper.toEntity(departamentoContadorDTO);
        departamentoContador = departamentoContadorRepository.save(departamentoContador);
        return departamentoContadorMapper.toDto(departamentoContador);
    }

    /**
     * Partially update a departamentoContador.
     *
     * @param departamentoContadorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DepartamentoContadorDTO> partialUpdate(DepartamentoContadorDTO departamentoContadorDTO) {
        log.debug("Request to partially update DepartamentoContador : {}", departamentoContadorDTO);

        return departamentoContadorRepository
            .findById(departamentoContadorDTO.getId())
            .map(existingDepartamentoContador -> {
                departamentoContadorMapper.partialUpdate(existingDepartamentoContador, departamentoContadorDTO);

                return existingDepartamentoContador;
            })
            .map(departamentoContadorRepository::save)
            .map(departamentoContadorMapper::toDto);
    }

    /**
     * Get all the departamentoContadors with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DepartamentoContadorDTO> findAllWithEagerRelationships(Pageable pageable) {
        return departamentoContadorRepository.findAllWithEagerRelationships(pageable).map(departamentoContadorMapper::toDto);
    }

    /**
     * Get one departamentoContador by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DepartamentoContadorDTO> findOne(Long id) {
        log.debug("Request to get DepartamentoContador : {}", id);
        return departamentoContadorRepository.findOneWithEagerRelationships(id).map(departamentoContadorMapper::toDto);
    }

    /**
     * Delete the departamentoContador by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DepartamentoContador : {}", id);
        departamentoContadorRepository.deleteById(id);
    }
}
