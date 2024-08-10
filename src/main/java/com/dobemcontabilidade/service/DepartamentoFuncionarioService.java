package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.DepartamentoFuncionario;
import com.dobemcontabilidade.repository.DepartamentoFuncionarioRepository;
import com.dobemcontabilidade.service.dto.DepartamentoFuncionarioDTO;
import com.dobemcontabilidade.service.mapper.DepartamentoFuncionarioMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.DepartamentoFuncionario}.
 */
@Service
@Transactional
public class DepartamentoFuncionarioService {

    private static final Logger log = LoggerFactory.getLogger(DepartamentoFuncionarioService.class);

    private final DepartamentoFuncionarioRepository departamentoFuncionarioRepository;

    private final DepartamentoFuncionarioMapper departamentoFuncionarioMapper;

    public DepartamentoFuncionarioService(
        DepartamentoFuncionarioRepository departamentoFuncionarioRepository,
        DepartamentoFuncionarioMapper departamentoFuncionarioMapper
    ) {
        this.departamentoFuncionarioRepository = departamentoFuncionarioRepository;
        this.departamentoFuncionarioMapper = departamentoFuncionarioMapper;
    }

    /**
     * Save a departamentoFuncionario.
     *
     * @param departamentoFuncionarioDTO the entity to save.
     * @return the persisted entity.
     */
    public DepartamentoFuncionarioDTO save(DepartamentoFuncionarioDTO departamentoFuncionarioDTO) {
        log.debug("Request to save DepartamentoFuncionario : {}", departamentoFuncionarioDTO);
        DepartamentoFuncionario departamentoFuncionario = departamentoFuncionarioMapper.toEntity(departamentoFuncionarioDTO);
        departamentoFuncionario = departamentoFuncionarioRepository.save(departamentoFuncionario);
        return departamentoFuncionarioMapper.toDto(departamentoFuncionario);
    }

    /**
     * Update a departamentoFuncionario.
     *
     * @param departamentoFuncionarioDTO the entity to save.
     * @return the persisted entity.
     */
    public DepartamentoFuncionarioDTO update(DepartamentoFuncionarioDTO departamentoFuncionarioDTO) {
        log.debug("Request to update DepartamentoFuncionario : {}", departamentoFuncionarioDTO);
        DepartamentoFuncionario departamentoFuncionario = departamentoFuncionarioMapper.toEntity(departamentoFuncionarioDTO);
        departamentoFuncionario = departamentoFuncionarioRepository.save(departamentoFuncionario);
        return departamentoFuncionarioMapper.toDto(departamentoFuncionario);
    }

    /**
     * Partially update a departamentoFuncionario.
     *
     * @param departamentoFuncionarioDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DepartamentoFuncionarioDTO> partialUpdate(DepartamentoFuncionarioDTO departamentoFuncionarioDTO) {
        log.debug("Request to partially update DepartamentoFuncionario : {}", departamentoFuncionarioDTO);

        return departamentoFuncionarioRepository
            .findById(departamentoFuncionarioDTO.getId())
            .map(existingDepartamentoFuncionario -> {
                departamentoFuncionarioMapper.partialUpdate(existingDepartamentoFuncionario, departamentoFuncionarioDTO);

                return existingDepartamentoFuncionario;
            })
            .map(departamentoFuncionarioRepository::save)
            .map(departamentoFuncionarioMapper::toDto);
    }

    /**
     * Get all the departamentoFuncionarios with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DepartamentoFuncionarioDTO> findAllWithEagerRelationships(Pageable pageable) {
        return departamentoFuncionarioRepository.findAllWithEagerRelationships(pageable).map(departamentoFuncionarioMapper::toDto);
    }

    /**
     * Get one departamentoFuncionario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DepartamentoFuncionarioDTO> findOne(Long id) {
        log.debug("Request to get DepartamentoFuncionario : {}", id);
        return departamentoFuncionarioRepository.findOneWithEagerRelationships(id).map(departamentoFuncionarioMapper::toDto);
    }

    /**
     * Delete the departamentoFuncionario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DepartamentoFuncionario : {}", id);
        departamentoFuncionarioRepository.deleteById(id);
    }
}
