package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.PerfilContador;
import com.dobemcontabilidade.repository.PerfilContadorRepository;
import com.dobemcontabilidade.service.dto.PerfilContadorDTO;
import com.dobemcontabilidade.service.mapper.PerfilContadorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.PerfilContador}.
 */
@Service
@Transactional
public class PerfilContadorService {

    private static final Logger log = LoggerFactory.getLogger(PerfilContadorService.class);

    private final PerfilContadorRepository perfilContadorRepository;

    private final PerfilContadorMapper perfilContadorMapper;

    public PerfilContadorService(PerfilContadorRepository perfilContadorRepository, PerfilContadorMapper perfilContadorMapper) {
        this.perfilContadorRepository = perfilContadorRepository;
        this.perfilContadorMapper = perfilContadorMapper;
    }

    /**
     * Save a perfilContador.
     *
     * @param perfilContadorDTO the entity to save.
     * @return the persisted entity.
     */
    public PerfilContadorDTO save(PerfilContadorDTO perfilContadorDTO) {
        log.debug("Request to save PerfilContador : {}", perfilContadorDTO);
        PerfilContador perfilContador = perfilContadorMapper.toEntity(perfilContadorDTO);
        perfilContador = perfilContadorRepository.save(perfilContador);
        return perfilContadorMapper.toDto(perfilContador);
    }

    /**
     * Update a perfilContador.
     *
     * @param perfilContadorDTO the entity to save.
     * @return the persisted entity.
     */
    public PerfilContadorDTO update(PerfilContadorDTO perfilContadorDTO) {
        log.debug("Request to update PerfilContador : {}", perfilContadorDTO);
        PerfilContador perfilContador = perfilContadorMapper.toEntity(perfilContadorDTO);
        perfilContador = perfilContadorRepository.save(perfilContador);
        return perfilContadorMapper.toDto(perfilContador);
    }

    /**
     * Partially update a perfilContador.
     *
     * @param perfilContadorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PerfilContadorDTO> partialUpdate(PerfilContadorDTO perfilContadorDTO) {
        log.debug("Request to partially update PerfilContador : {}", perfilContadorDTO);

        return perfilContadorRepository
            .findById(perfilContadorDTO.getId())
            .map(existingPerfilContador -> {
                perfilContadorMapper.partialUpdate(existingPerfilContador, perfilContadorDTO);

                return existingPerfilContador;
            })
            .map(perfilContadorRepository::save)
            .map(perfilContadorMapper::toDto);
    }

    /**
     * Get one perfilContador by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PerfilContadorDTO> findOne(Long id) {
        log.debug("Request to get PerfilContador : {}", id);
        return perfilContadorRepository.findById(id).map(perfilContadorMapper::toDto);
    }

    /**
     * Delete the perfilContador by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PerfilContador : {}", id);
        perfilContadorRepository.deleteById(id);
    }
}
