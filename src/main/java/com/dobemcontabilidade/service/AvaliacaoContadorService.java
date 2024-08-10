package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AvaliacaoContador;
import com.dobemcontabilidade.repository.AvaliacaoContadorRepository;
import com.dobemcontabilidade.service.dto.AvaliacaoContadorDTO;
import com.dobemcontabilidade.service.mapper.AvaliacaoContadorMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AvaliacaoContador}.
 */
@Service
@Transactional
public class AvaliacaoContadorService {

    private static final Logger log = LoggerFactory.getLogger(AvaliacaoContadorService.class);

    private final AvaliacaoContadorRepository avaliacaoContadorRepository;

    private final AvaliacaoContadorMapper avaliacaoContadorMapper;

    public AvaliacaoContadorService(
        AvaliacaoContadorRepository avaliacaoContadorRepository,
        AvaliacaoContadorMapper avaliacaoContadorMapper
    ) {
        this.avaliacaoContadorRepository = avaliacaoContadorRepository;
        this.avaliacaoContadorMapper = avaliacaoContadorMapper;
    }

    /**
     * Save a avaliacaoContador.
     *
     * @param avaliacaoContadorDTO the entity to save.
     * @return the persisted entity.
     */
    public AvaliacaoContadorDTO save(AvaliacaoContadorDTO avaliacaoContadorDTO) {
        log.debug("Request to save AvaliacaoContador : {}", avaliacaoContadorDTO);
        AvaliacaoContador avaliacaoContador = avaliacaoContadorMapper.toEntity(avaliacaoContadorDTO);
        avaliacaoContador = avaliacaoContadorRepository.save(avaliacaoContador);
        return avaliacaoContadorMapper.toDto(avaliacaoContador);
    }

    /**
     * Update a avaliacaoContador.
     *
     * @param avaliacaoContadorDTO the entity to save.
     * @return the persisted entity.
     */
    public AvaliacaoContadorDTO update(AvaliacaoContadorDTO avaliacaoContadorDTO) {
        log.debug("Request to update AvaliacaoContador : {}", avaliacaoContadorDTO);
        AvaliacaoContador avaliacaoContador = avaliacaoContadorMapper.toEntity(avaliacaoContadorDTO);
        avaliacaoContador = avaliacaoContadorRepository.save(avaliacaoContador);
        return avaliacaoContadorMapper.toDto(avaliacaoContador);
    }

    /**
     * Partially update a avaliacaoContador.
     *
     * @param avaliacaoContadorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AvaliacaoContadorDTO> partialUpdate(AvaliacaoContadorDTO avaliacaoContadorDTO) {
        log.debug("Request to partially update AvaliacaoContador : {}", avaliacaoContadorDTO);

        return avaliacaoContadorRepository
            .findById(avaliacaoContadorDTO.getId())
            .map(existingAvaliacaoContador -> {
                avaliacaoContadorMapper.partialUpdate(existingAvaliacaoContador, avaliacaoContadorDTO);

                return existingAvaliacaoContador;
            })
            .map(avaliacaoContadorRepository::save)
            .map(avaliacaoContadorMapper::toDto);
    }

    /**
     * Get all the avaliacaoContadors.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AvaliacaoContadorDTO> findAll() {
        log.debug("Request to get all AvaliacaoContadors");
        return avaliacaoContadorRepository
            .findAll()
            .stream()
            .map(avaliacaoContadorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the avaliacaoContadors with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AvaliacaoContadorDTO> findAllWithEagerRelationships(Pageable pageable) {
        return avaliacaoContadorRepository.findAllWithEagerRelationships(pageable).map(avaliacaoContadorMapper::toDto);
    }

    /**
     * Get one avaliacaoContador by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AvaliacaoContadorDTO> findOne(Long id) {
        log.debug("Request to get AvaliacaoContador : {}", id);
        return avaliacaoContadorRepository.findOneWithEagerRelationships(id).map(avaliacaoContadorMapper::toDto);
    }

    /**
     * Delete the avaliacaoContador by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AvaliacaoContador : {}", id);
        avaliacaoContadorRepository.deleteById(id);
    }
}
