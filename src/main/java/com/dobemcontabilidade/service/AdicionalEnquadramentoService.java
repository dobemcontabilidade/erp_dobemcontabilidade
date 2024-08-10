package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AdicionalEnquadramento;
import com.dobemcontabilidade.repository.AdicionalEnquadramentoRepository;
import com.dobemcontabilidade.service.dto.AdicionalEnquadramentoDTO;
import com.dobemcontabilidade.service.mapper.AdicionalEnquadramentoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AdicionalEnquadramento}.
 */
@Service
@Transactional
public class AdicionalEnquadramentoService {

    private static final Logger log = LoggerFactory.getLogger(AdicionalEnquadramentoService.class);

    private final AdicionalEnquadramentoRepository adicionalEnquadramentoRepository;

    private final AdicionalEnquadramentoMapper adicionalEnquadramentoMapper;

    public AdicionalEnquadramentoService(
        AdicionalEnquadramentoRepository adicionalEnquadramentoRepository,
        AdicionalEnquadramentoMapper adicionalEnquadramentoMapper
    ) {
        this.adicionalEnquadramentoRepository = adicionalEnquadramentoRepository;
        this.adicionalEnquadramentoMapper = adicionalEnquadramentoMapper;
    }

    /**
     * Save a adicionalEnquadramento.
     *
     * @param adicionalEnquadramentoDTO the entity to save.
     * @return the persisted entity.
     */
    public AdicionalEnquadramentoDTO save(AdicionalEnquadramentoDTO adicionalEnquadramentoDTO) {
        log.debug("Request to save AdicionalEnquadramento : {}", adicionalEnquadramentoDTO);
        AdicionalEnquadramento adicionalEnquadramento = adicionalEnquadramentoMapper.toEntity(adicionalEnquadramentoDTO);
        adicionalEnquadramento = adicionalEnquadramentoRepository.save(adicionalEnquadramento);
        return adicionalEnquadramentoMapper.toDto(adicionalEnquadramento);
    }

    /**
     * Update a adicionalEnquadramento.
     *
     * @param adicionalEnquadramentoDTO the entity to save.
     * @return the persisted entity.
     */
    public AdicionalEnquadramentoDTO update(AdicionalEnquadramentoDTO adicionalEnquadramentoDTO) {
        log.debug("Request to update AdicionalEnquadramento : {}", adicionalEnquadramentoDTO);
        AdicionalEnquadramento adicionalEnquadramento = adicionalEnquadramentoMapper.toEntity(adicionalEnquadramentoDTO);
        adicionalEnquadramento = adicionalEnquadramentoRepository.save(adicionalEnquadramento);
        return adicionalEnquadramentoMapper.toDto(adicionalEnquadramento);
    }

    /**
     * Partially update a adicionalEnquadramento.
     *
     * @param adicionalEnquadramentoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AdicionalEnquadramentoDTO> partialUpdate(AdicionalEnquadramentoDTO adicionalEnquadramentoDTO) {
        log.debug("Request to partially update AdicionalEnquadramento : {}", adicionalEnquadramentoDTO);

        return adicionalEnquadramentoRepository
            .findById(adicionalEnquadramentoDTO.getId())
            .map(existingAdicionalEnquadramento -> {
                adicionalEnquadramentoMapper.partialUpdate(existingAdicionalEnquadramento, adicionalEnquadramentoDTO);

                return existingAdicionalEnquadramento;
            })
            .map(adicionalEnquadramentoRepository::save)
            .map(adicionalEnquadramentoMapper::toDto);
    }

    /**
     * Get all the adicionalEnquadramentos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AdicionalEnquadramentoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return adicionalEnquadramentoRepository.findAllWithEagerRelationships(pageable).map(adicionalEnquadramentoMapper::toDto);
    }

    /**
     * Get one adicionalEnquadramento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdicionalEnquadramentoDTO> findOne(Long id) {
        log.debug("Request to get AdicionalEnquadramento : {}", id);
        return adicionalEnquadramentoRepository.findOneWithEagerRelationships(id).map(adicionalEnquadramentoMapper::toDto);
    }

    /**
     * Delete the adicionalEnquadramento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdicionalEnquadramento : {}", id);
        adicionalEnquadramentoRepository.deleteById(id);
    }
}
