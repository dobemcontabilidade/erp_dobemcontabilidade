package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.DivisaoCnae;
import com.dobemcontabilidade.repository.DivisaoCnaeRepository;
import com.dobemcontabilidade.service.dto.DivisaoCnaeDTO;
import com.dobemcontabilidade.service.mapper.DivisaoCnaeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.DivisaoCnae}.
 */
@Service
@Transactional
public class DivisaoCnaeService {

    private static final Logger log = LoggerFactory.getLogger(DivisaoCnaeService.class);

    private final DivisaoCnaeRepository divisaoCnaeRepository;

    private final DivisaoCnaeMapper divisaoCnaeMapper;

    public DivisaoCnaeService(DivisaoCnaeRepository divisaoCnaeRepository, DivisaoCnaeMapper divisaoCnaeMapper) {
        this.divisaoCnaeRepository = divisaoCnaeRepository;
        this.divisaoCnaeMapper = divisaoCnaeMapper;
    }

    /**
     * Save a divisaoCnae.
     *
     * @param divisaoCnaeDTO the entity to save.
     * @return the persisted entity.
     */
    public DivisaoCnaeDTO save(DivisaoCnaeDTO divisaoCnaeDTO) {
        log.debug("Request to save DivisaoCnae : {}", divisaoCnaeDTO);
        DivisaoCnae divisaoCnae = divisaoCnaeMapper.toEntity(divisaoCnaeDTO);
        divisaoCnae = divisaoCnaeRepository.save(divisaoCnae);
        return divisaoCnaeMapper.toDto(divisaoCnae);
    }

    /**
     * Update a divisaoCnae.
     *
     * @param divisaoCnaeDTO the entity to save.
     * @return the persisted entity.
     */
    public DivisaoCnaeDTO update(DivisaoCnaeDTO divisaoCnaeDTO) {
        log.debug("Request to update DivisaoCnae : {}", divisaoCnaeDTO);
        DivisaoCnae divisaoCnae = divisaoCnaeMapper.toEntity(divisaoCnaeDTO);
        divisaoCnae = divisaoCnaeRepository.save(divisaoCnae);
        return divisaoCnaeMapper.toDto(divisaoCnae);
    }

    /**
     * Partially update a divisaoCnae.
     *
     * @param divisaoCnaeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DivisaoCnaeDTO> partialUpdate(DivisaoCnaeDTO divisaoCnaeDTO) {
        log.debug("Request to partially update DivisaoCnae : {}", divisaoCnaeDTO);

        return divisaoCnaeRepository
            .findById(divisaoCnaeDTO.getId())
            .map(existingDivisaoCnae -> {
                divisaoCnaeMapper.partialUpdate(existingDivisaoCnae, divisaoCnaeDTO);

                return existingDivisaoCnae;
            })
            .map(divisaoCnaeRepository::save)
            .map(divisaoCnaeMapper::toDto);
    }

    /**
     * Get all the divisaoCnaes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DivisaoCnaeDTO> findAllWithEagerRelationships(Pageable pageable) {
        return divisaoCnaeRepository.findAllWithEagerRelationships(pageable).map(divisaoCnaeMapper::toDto);
    }

    /**
     * Get one divisaoCnae by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DivisaoCnaeDTO> findOne(Long id) {
        log.debug("Request to get DivisaoCnae : {}", id);
        return divisaoCnaeRepository.findOneWithEagerRelationships(id).map(divisaoCnaeMapper::toDto);
    }

    /**
     * Delete the divisaoCnae by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DivisaoCnae : {}", id);
        divisaoCnaeRepository.deleteById(id);
    }
}
