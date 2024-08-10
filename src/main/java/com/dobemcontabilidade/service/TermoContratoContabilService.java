package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.TermoContratoContabil;
import com.dobemcontabilidade.repository.TermoContratoContabilRepository;
import com.dobemcontabilidade.service.dto.TermoContratoContabilDTO;
import com.dobemcontabilidade.service.mapper.TermoContratoContabilMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.TermoContratoContabil}.
 */
@Service
@Transactional
public class TermoContratoContabilService {

    private static final Logger log = LoggerFactory.getLogger(TermoContratoContabilService.class);

    private final TermoContratoContabilRepository termoContratoContabilRepository;

    private final TermoContratoContabilMapper termoContratoContabilMapper;

    public TermoContratoContabilService(
        TermoContratoContabilRepository termoContratoContabilRepository,
        TermoContratoContabilMapper termoContratoContabilMapper
    ) {
        this.termoContratoContabilRepository = termoContratoContabilRepository;
        this.termoContratoContabilMapper = termoContratoContabilMapper;
    }

    /**
     * Save a termoContratoContabil.
     *
     * @param termoContratoContabilDTO the entity to save.
     * @return the persisted entity.
     */
    public TermoContratoContabilDTO save(TermoContratoContabilDTO termoContratoContabilDTO) {
        log.debug("Request to save TermoContratoContabil : {}", termoContratoContabilDTO);
        TermoContratoContabil termoContratoContabil = termoContratoContabilMapper.toEntity(termoContratoContabilDTO);
        termoContratoContabil = termoContratoContabilRepository.save(termoContratoContabil);
        return termoContratoContabilMapper.toDto(termoContratoContabil);
    }

    /**
     * Update a termoContratoContabil.
     *
     * @param termoContratoContabilDTO the entity to save.
     * @return the persisted entity.
     */
    public TermoContratoContabilDTO update(TermoContratoContabilDTO termoContratoContabilDTO) {
        log.debug("Request to update TermoContratoContabil : {}", termoContratoContabilDTO);
        TermoContratoContabil termoContratoContabil = termoContratoContabilMapper.toEntity(termoContratoContabilDTO);
        termoContratoContabil = termoContratoContabilRepository.save(termoContratoContabil);
        return termoContratoContabilMapper.toDto(termoContratoContabil);
    }

    /**
     * Partially update a termoContratoContabil.
     *
     * @param termoContratoContabilDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TermoContratoContabilDTO> partialUpdate(TermoContratoContabilDTO termoContratoContabilDTO) {
        log.debug("Request to partially update TermoContratoContabil : {}", termoContratoContabilDTO);

        return termoContratoContabilRepository
            .findById(termoContratoContabilDTO.getId())
            .map(existingTermoContratoContabil -> {
                termoContratoContabilMapper.partialUpdate(existingTermoContratoContabil, termoContratoContabilDTO);

                return existingTermoContratoContabil;
            })
            .map(termoContratoContabilRepository::save)
            .map(termoContratoContabilMapper::toDto);
    }

    /**
     * Get all the termoContratoContabils with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TermoContratoContabilDTO> findAllWithEagerRelationships(Pageable pageable) {
        return termoContratoContabilRepository.findAllWithEagerRelationships(pageable).map(termoContratoContabilMapper::toDto);
    }

    /**
     * Get one termoContratoContabil by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TermoContratoContabilDTO> findOne(Long id) {
        log.debug("Request to get TermoContratoContabil : {}", id);
        return termoContratoContabilRepository.findOneWithEagerRelationships(id).map(termoContratoContabilMapper::toDto);
    }

    /**
     * Delete the termoContratoContabil by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TermoContratoContabil : {}", id);
        termoContratoContabilRepository.deleteById(id);
    }
}
