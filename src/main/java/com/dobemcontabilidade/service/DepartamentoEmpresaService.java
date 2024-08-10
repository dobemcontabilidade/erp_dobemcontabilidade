package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.DepartamentoEmpresa;
import com.dobemcontabilidade.repository.DepartamentoEmpresaRepository;
import com.dobemcontabilidade.service.dto.DepartamentoEmpresaDTO;
import com.dobemcontabilidade.service.mapper.DepartamentoEmpresaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.DepartamentoEmpresa}.
 */
@Service
@Transactional
public class DepartamentoEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(DepartamentoEmpresaService.class);

    private final DepartamentoEmpresaRepository departamentoEmpresaRepository;

    private final DepartamentoEmpresaMapper departamentoEmpresaMapper;

    public DepartamentoEmpresaService(
        DepartamentoEmpresaRepository departamentoEmpresaRepository,
        DepartamentoEmpresaMapper departamentoEmpresaMapper
    ) {
        this.departamentoEmpresaRepository = departamentoEmpresaRepository;
        this.departamentoEmpresaMapper = departamentoEmpresaMapper;
    }

    /**
     * Save a departamentoEmpresa.
     *
     * @param departamentoEmpresaDTO the entity to save.
     * @return the persisted entity.
     */
    public DepartamentoEmpresaDTO save(DepartamentoEmpresaDTO departamentoEmpresaDTO) {
        log.debug("Request to save DepartamentoEmpresa : {}", departamentoEmpresaDTO);
        DepartamentoEmpresa departamentoEmpresa = departamentoEmpresaMapper.toEntity(departamentoEmpresaDTO);
        departamentoEmpresa = departamentoEmpresaRepository.save(departamentoEmpresa);
        return departamentoEmpresaMapper.toDto(departamentoEmpresa);
    }

    /**
     * Update a departamentoEmpresa.
     *
     * @param departamentoEmpresaDTO the entity to save.
     * @return the persisted entity.
     */
    public DepartamentoEmpresaDTO update(DepartamentoEmpresaDTO departamentoEmpresaDTO) {
        log.debug("Request to update DepartamentoEmpresa : {}", departamentoEmpresaDTO);
        DepartamentoEmpresa departamentoEmpresa = departamentoEmpresaMapper.toEntity(departamentoEmpresaDTO);
        departamentoEmpresa = departamentoEmpresaRepository.save(departamentoEmpresa);
        return departamentoEmpresaMapper.toDto(departamentoEmpresa);
    }

    /**
     * Partially update a departamentoEmpresa.
     *
     * @param departamentoEmpresaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DepartamentoEmpresaDTO> partialUpdate(DepartamentoEmpresaDTO departamentoEmpresaDTO) {
        log.debug("Request to partially update DepartamentoEmpresa : {}", departamentoEmpresaDTO);

        return departamentoEmpresaRepository
            .findById(departamentoEmpresaDTO.getId())
            .map(existingDepartamentoEmpresa -> {
                departamentoEmpresaMapper.partialUpdate(existingDepartamentoEmpresa, departamentoEmpresaDTO);

                return existingDepartamentoEmpresa;
            })
            .map(departamentoEmpresaRepository::save)
            .map(departamentoEmpresaMapper::toDto);
    }

    /**
     * Get all the departamentoEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DepartamentoEmpresaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return departamentoEmpresaRepository.findAllWithEagerRelationships(pageable).map(departamentoEmpresaMapper::toDto);
    }

    /**
     * Get one departamentoEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DepartamentoEmpresaDTO> findOne(Long id) {
        log.debug("Request to get DepartamentoEmpresa : {}", id);
        return departamentoEmpresaRepository.findOneWithEagerRelationships(id).map(departamentoEmpresaMapper::toDto);
    }

    /**
     * Delete the departamentoEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DepartamentoEmpresa : {}", id);
        departamentoEmpresaRepository.deleteById(id);
    }
}
