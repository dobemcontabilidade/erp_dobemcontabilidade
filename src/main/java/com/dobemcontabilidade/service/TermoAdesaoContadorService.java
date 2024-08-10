package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.TermoAdesaoContador;
import com.dobemcontabilidade.repository.TermoAdesaoContadorRepository;
import com.dobemcontabilidade.service.dto.TermoAdesaoContadorDTO;
import com.dobemcontabilidade.service.mapper.TermoAdesaoContadorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.TermoAdesaoContador}.
 */
@Service
@Transactional
public class TermoAdesaoContadorService {

    private static final Logger log = LoggerFactory.getLogger(TermoAdesaoContadorService.class);

    private final TermoAdesaoContadorRepository termoAdesaoContadorRepository;

    private final TermoAdesaoContadorMapper termoAdesaoContadorMapper;

    public TermoAdesaoContadorService(
        TermoAdesaoContadorRepository termoAdesaoContadorRepository,
        TermoAdesaoContadorMapper termoAdesaoContadorMapper
    ) {
        this.termoAdesaoContadorRepository = termoAdesaoContadorRepository;
        this.termoAdesaoContadorMapper = termoAdesaoContadorMapper;
    }

    /**
     * Save a termoAdesaoContador.
     *
     * @param termoAdesaoContadorDTO the entity to save.
     * @return the persisted entity.
     */
    public TermoAdesaoContadorDTO save(TermoAdesaoContadorDTO termoAdesaoContadorDTO) {
        log.debug("Request to save TermoAdesaoContador : {}", termoAdesaoContadorDTO);
        TermoAdesaoContador termoAdesaoContador = termoAdesaoContadorMapper.toEntity(termoAdesaoContadorDTO);
        termoAdesaoContador = termoAdesaoContadorRepository.save(termoAdesaoContador);
        return termoAdesaoContadorMapper.toDto(termoAdesaoContador);
    }

    /**
     * Update a termoAdesaoContador.
     *
     * @param termoAdesaoContadorDTO the entity to save.
     * @return the persisted entity.
     */
    public TermoAdesaoContadorDTO update(TermoAdesaoContadorDTO termoAdesaoContadorDTO) {
        log.debug("Request to update TermoAdesaoContador : {}", termoAdesaoContadorDTO);
        TermoAdesaoContador termoAdesaoContador = termoAdesaoContadorMapper.toEntity(termoAdesaoContadorDTO);
        termoAdesaoContador = termoAdesaoContadorRepository.save(termoAdesaoContador);
        return termoAdesaoContadorMapper.toDto(termoAdesaoContador);
    }

    /**
     * Partially update a termoAdesaoContador.
     *
     * @param termoAdesaoContadorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TermoAdesaoContadorDTO> partialUpdate(TermoAdesaoContadorDTO termoAdesaoContadorDTO) {
        log.debug("Request to partially update TermoAdesaoContador : {}", termoAdesaoContadorDTO);

        return termoAdesaoContadorRepository
            .findById(termoAdesaoContadorDTO.getId())
            .map(existingTermoAdesaoContador -> {
                termoAdesaoContadorMapper.partialUpdate(existingTermoAdesaoContador, termoAdesaoContadorDTO);

                return existingTermoAdesaoContador;
            })
            .map(termoAdesaoContadorRepository::save)
            .map(termoAdesaoContadorMapper::toDto);
    }

    /**
     * Get all the termoAdesaoContadors with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TermoAdesaoContadorDTO> findAllWithEagerRelationships(Pageable pageable) {
        return termoAdesaoContadorRepository.findAllWithEagerRelationships(pageable).map(termoAdesaoContadorMapper::toDto);
    }

    /**
     * Get one termoAdesaoContador by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TermoAdesaoContadorDTO> findOne(Long id) {
        log.debug("Request to get TermoAdesaoContador : {}", id);
        return termoAdesaoContadorRepository.findOneWithEagerRelationships(id).map(termoAdesaoContadorMapper::toDto);
    }

    /**
     * Delete the termoAdesaoContador by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TermoAdesaoContador : {}", id);
        termoAdesaoContadorRepository.deleteById(id);
    }
}
