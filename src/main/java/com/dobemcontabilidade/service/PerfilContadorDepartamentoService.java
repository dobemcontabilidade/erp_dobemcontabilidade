package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.PerfilContadorDepartamento;
import com.dobemcontabilidade.repository.PerfilContadorDepartamentoRepository;
import com.dobemcontabilidade.service.dto.PerfilContadorDepartamentoDTO;
import com.dobemcontabilidade.service.mapper.PerfilContadorDepartamentoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.PerfilContadorDepartamento}.
 */
@Service
@Transactional
public class PerfilContadorDepartamentoService {

    private static final Logger log = LoggerFactory.getLogger(PerfilContadorDepartamentoService.class);

    private final PerfilContadorDepartamentoRepository perfilContadorDepartamentoRepository;

    private final PerfilContadorDepartamentoMapper perfilContadorDepartamentoMapper;

    public PerfilContadorDepartamentoService(
        PerfilContadorDepartamentoRepository perfilContadorDepartamentoRepository,
        PerfilContadorDepartamentoMapper perfilContadorDepartamentoMapper
    ) {
        this.perfilContadorDepartamentoRepository = perfilContadorDepartamentoRepository;
        this.perfilContadorDepartamentoMapper = perfilContadorDepartamentoMapper;
    }

    /**
     * Save a perfilContadorDepartamento.
     *
     * @param perfilContadorDepartamentoDTO the entity to save.
     * @return the persisted entity.
     */
    public PerfilContadorDepartamentoDTO save(PerfilContadorDepartamentoDTO perfilContadorDepartamentoDTO) {
        log.debug("Request to save PerfilContadorDepartamento : {}", perfilContadorDepartamentoDTO);
        PerfilContadorDepartamento perfilContadorDepartamento = perfilContadorDepartamentoMapper.toEntity(perfilContadorDepartamentoDTO);
        perfilContadorDepartamento = perfilContadorDepartamentoRepository.save(perfilContadorDepartamento);
        return perfilContadorDepartamentoMapper.toDto(perfilContadorDepartamento);
    }

    /**
     * Update a perfilContadorDepartamento.
     *
     * @param perfilContadorDepartamentoDTO the entity to save.
     * @return the persisted entity.
     */
    public PerfilContadorDepartamentoDTO update(PerfilContadorDepartamentoDTO perfilContadorDepartamentoDTO) {
        log.debug("Request to update PerfilContadorDepartamento : {}", perfilContadorDepartamentoDTO);
        PerfilContadorDepartamento perfilContadorDepartamento = perfilContadorDepartamentoMapper.toEntity(perfilContadorDepartamentoDTO);
        perfilContadorDepartamento = perfilContadorDepartamentoRepository.save(perfilContadorDepartamento);
        return perfilContadorDepartamentoMapper.toDto(perfilContadorDepartamento);
    }

    /**
     * Partially update a perfilContadorDepartamento.
     *
     * @param perfilContadorDepartamentoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PerfilContadorDepartamentoDTO> partialUpdate(PerfilContadorDepartamentoDTO perfilContadorDepartamentoDTO) {
        log.debug("Request to partially update PerfilContadorDepartamento : {}", perfilContadorDepartamentoDTO);

        return perfilContadorDepartamentoRepository
            .findById(perfilContadorDepartamentoDTO.getId())
            .map(existingPerfilContadorDepartamento -> {
                perfilContadorDepartamentoMapper.partialUpdate(existingPerfilContadorDepartamento, perfilContadorDepartamentoDTO);

                return existingPerfilContadorDepartamento;
            })
            .map(perfilContadorDepartamentoRepository::save)
            .map(perfilContadorDepartamentoMapper::toDto);
    }

    /**
     * Get all the perfilContadorDepartamentos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PerfilContadorDepartamentoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return perfilContadorDepartamentoRepository.findAllWithEagerRelationships(pageable).map(perfilContadorDepartamentoMapper::toDto);
    }

    /**
     * Get one perfilContadorDepartamento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PerfilContadorDepartamentoDTO> findOne(Long id) {
        log.debug("Request to get PerfilContadorDepartamento : {}", id);
        return perfilContadorDepartamentoRepository.findOneWithEagerRelationships(id).map(perfilContadorDepartamentoMapper::toDto);
    }

    /**
     * Delete the perfilContadorDepartamento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PerfilContadorDepartamento : {}", id);
        perfilContadorDepartamentoRepository.deleteById(id);
    }
}
