package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.SecaoCnae;
import com.dobemcontabilidade.repository.SecaoCnaeRepository;
import com.dobemcontabilidade.service.dto.SecaoCnaeDTO;
import com.dobemcontabilidade.service.mapper.SecaoCnaeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.SecaoCnae}.
 */
@Service
@Transactional
public class SecaoCnaeService {

    private static final Logger log = LoggerFactory.getLogger(SecaoCnaeService.class);

    private final SecaoCnaeRepository secaoCnaeRepository;

    private final SecaoCnaeMapper secaoCnaeMapper;

    public SecaoCnaeService(SecaoCnaeRepository secaoCnaeRepository, SecaoCnaeMapper secaoCnaeMapper) {
        this.secaoCnaeRepository = secaoCnaeRepository;
        this.secaoCnaeMapper = secaoCnaeMapper;
    }

    /**
     * Save a secaoCnae.
     *
     * @param secaoCnaeDTO the entity to save.
     * @return the persisted entity.
     */
    public SecaoCnaeDTO save(SecaoCnaeDTO secaoCnaeDTO) {
        log.debug("Request to save SecaoCnae : {}", secaoCnaeDTO);
        SecaoCnae secaoCnae = secaoCnaeMapper.toEntity(secaoCnaeDTO);
        secaoCnae = secaoCnaeRepository.save(secaoCnae);
        return secaoCnaeMapper.toDto(secaoCnae);
    }

    /**
     * Update a secaoCnae.
     *
     * @param secaoCnaeDTO the entity to save.
     * @return the persisted entity.
     */
    public SecaoCnaeDTO update(SecaoCnaeDTO secaoCnaeDTO) {
        log.debug("Request to update SecaoCnae : {}", secaoCnaeDTO);
        SecaoCnae secaoCnae = secaoCnaeMapper.toEntity(secaoCnaeDTO);
        secaoCnae = secaoCnaeRepository.save(secaoCnae);
        return secaoCnaeMapper.toDto(secaoCnae);
    }

    /**
     * Partially update a secaoCnae.
     *
     * @param secaoCnaeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SecaoCnaeDTO> partialUpdate(SecaoCnaeDTO secaoCnaeDTO) {
        log.debug("Request to partially update SecaoCnae : {}", secaoCnaeDTO);

        return secaoCnaeRepository
            .findById(secaoCnaeDTO.getId())
            .map(existingSecaoCnae -> {
                secaoCnaeMapper.partialUpdate(existingSecaoCnae, secaoCnaeDTO);

                return existingSecaoCnae;
            })
            .map(secaoCnaeRepository::save)
            .map(secaoCnaeMapper::toDto);
    }

    /**
     * Get one secaoCnae by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SecaoCnaeDTO> findOne(Long id) {
        log.debug("Request to get SecaoCnae : {}", id);
        return secaoCnaeRepository.findById(id).map(secaoCnaeMapper::toDto);
    }

    /**
     * Delete the secaoCnae by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SecaoCnae : {}", id);
        secaoCnaeRepository.deleteById(id);
    }
}
