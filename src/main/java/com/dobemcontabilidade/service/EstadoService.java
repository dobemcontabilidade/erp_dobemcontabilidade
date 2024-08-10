package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Estado;
import com.dobemcontabilidade.repository.EstadoRepository;
import com.dobemcontabilidade.service.dto.EstadoDTO;
import com.dobemcontabilidade.service.mapper.EstadoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Estado}.
 */
@Service
@Transactional
public class EstadoService {

    private static final Logger log = LoggerFactory.getLogger(EstadoService.class);

    private final EstadoRepository estadoRepository;

    private final EstadoMapper estadoMapper;

    public EstadoService(EstadoRepository estadoRepository, EstadoMapper estadoMapper) {
        this.estadoRepository = estadoRepository;
        this.estadoMapper = estadoMapper;
    }

    /**
     * Save a estado.
     *
     * @param estadoDTO the entity to save.
     * @return the persisted entity.
     */
    public EstadoDTO save(EstadoDTO estadoDTO) {
        log.debug("Request to save Estado : {}", estadoDTO);
        Estado estado = estadoMapper.toEntity(estadoDTO);
        estado = estadoRepository.save(estado);
        return estadoMapper.toDto(estado);
    }

    /**
     * Update a estado.
     *
     * @param estadoDTO the entity to save.
     * @return the persisted entity.
     */
    public EstadoDTO update(EstadoDTO estadoDTO) {
        log.debug("Request to update Estado : {}", estadoDTO);
        Estado estado = estadoMapper.toEntity(estadoDTO);
        estado = estadoRepository.save(estado);
        return estadoMapper.toDto(estado);
    }

    /**
     * Partially update a estado.
     *
     * @param estadoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EstadoDTO> partialUpdate(EstadoDTO estadoDTO) {
        log.debug("Request to partially update Estado : {}", estadoDTO);

        return estadoRepository
            .findById(estadoDTO.getId())
            .map(existingEstado -> {
                estadoMapper.partialUpdate(existingEstado, estadoDTO);

                return existingEstado;
            })
            .map(estadoRepository::save)
            .map(estadoMapper::toDto);
    }

    /**
     * Get one estado by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EstadoDTO> findOne(Long id) {
        log.debug("Request to get Estado : {}", id);
        return estadoRepository.findById(id).map(estadoMapper::toDto);
    }

    /**
     * Delete the estado by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Estado : {}", id);
        estadoRepository.deleteById(id);
    }
}
