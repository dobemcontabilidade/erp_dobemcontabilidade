package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.ObservacaoCnae;
import com.dobemcontabilidade.repository.ObservacaoCnaeRepository;
import com.dobemcontabilidade.service.dto.ObservacaoCnaeDTO;
import com.dobemcontabilidade.service.mapper.ObservacaoCnaeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.ObservacaoCnae}.
 */
@Service
@Transactional
public class ObservacaoCnaeService {

    private static final Logger log = LoggerFactory.getLogger(ObservacaoCnaeService.class);

    private final ObservacaoCnaeRepository observacaoCnaeRepository;

    private final ObservacaoCnaeMapper observacaoCnaeMapper;

    public ObservacaoCnaeService(ObservacaoCnaeRepository observacaoCnaeRepository, ObservacaoCnaeMapper observacaoCnaeMapper) {
        this.observacaoCnaeRepository = observacaoCnaeRepository;
        this.observacaoCnaeMapper = observacaoCnaeMapper;
    }

    /**
     * Save a observacaoCnae.
     *
     * @param observacaoCnaeDTO the entity to save.
     * @return the persisted entity.
     */
    public ObservacaoCnaeDTO save(ObservacaoCnaeDTO observacaoCnaeDTO) {
        log.debug("Request to save ObservacaoCnae : {}", observacaoCnaeDTO);
        ObservacaoCnae observacaoCnae = observacaoCnaeMapper.toEntity(observacaoCnaeDTO);
        observacaoCnae = observacaoCnaeRepository.save(observacaoCnae);
        return observacaoCnaeMapper.toDto(observacaoCnae);
    }

    /**
     * Update a observacaoCnae.
     *
     * @param observacaoCnaeDTO the entity to save.
     * @return the persisted entity.
     */
    public ObservacaoCnaeDTO update(ObservacaoCnaeDTO observacaoCnaeDTO) {
        log.debug("Request to update ObservacaoCnae : {}", observacaoCnaeDTO);
        ObservacaoCnae observacaoCnae = observacaoCnaeMapper.toEntity(observacaoCnaeDTO);
        observacaoCnae = observacaoCnaeRepository.save(observacaoCnae);
        return observacaoCnaeMapper.toDto(observacaoCnae);
    }

    /**
     * Partially update a observacaoCnae.
     *
     * @param observacaoCnaeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ObservacaoCnaeDTO> partialUpdate(ObservacaoCnaeDTO observacaoCnaeDTO) {
        log.debug("Request to partially update ObservacaoCnae : {}", observacaoCnaeDTO);

        return observacaoCnaeRepository
            .findById(observacaoCnaeDTO.getId())
            .map(existingObservacaoCnae -> {
                observacaoCnaeMapper.partialUpdate(existingObservacaoCnae, observacaoCnaeDTO);

                return existingObservacaoCnae;
            })
            .map(observacaoCnaeRepository::save)
            .map(observacaoCnaeMapper::toDto);
    }

    /**
     * Get all the observacaoCnaes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ObservacaoCnaeDTO> findAllWithEagerRelationships(Pageable pageable) {
        return observacaoCnaeRepository.findAllWithEagerRelationships(pageable).map(observacaoCnaeMapper::toDto);
    }

    /**
     * Get one observacaoCnae by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ObservacaoCnaeDTO> findOne(Long id) {
        log.debug("Request to get ObservacaoCnae : {}", id);
        return observacaoCnaeRepository.findOneWithEagerRelationships(id).map(observacaoCnaeMapper::toDto);
    }

    /**
     * Delete the observacaoCnae by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ObservacaoCnae : {}", id);
        observacaoCnaeRepository.deleteById(id);
    }
}
