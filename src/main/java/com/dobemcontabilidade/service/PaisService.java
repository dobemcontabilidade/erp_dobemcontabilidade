package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Pais;
import com.dobemcontabilidade.repository.PaisRepository;
import com.dobemcontabilidade.service.dto.PaisDTO;
import com.dobemcontabilidade.service.mapper.PaisMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Pais}.
 */
@Service
@Transactional
public class PaisService {

    private static final Logger log = LoggerFactory.getLogger(PaisService.class);

    private final PaisRepository paisRepository;

    private final PaisMapper paisMapper;

    public PaisService(PaisRepository paisRepository, PaisMapper paisMapper) {
        this.paisRepository = paisRepository;
        this.paisMapper = paisMapper;
    }

    /**
     * Save a pais.
     *
     * @param paisDTO the entity to save.
     * @return the persisted entity.
     */
    public PaisDTO save(PaisDTO paisDTO) {
        log.debug("Request to save Pais : {}", paisDTO);
        Pais pais = paisMapper.toEntity(paisDTO);
        pais = paisRepository.save(pais);
        return paisMapper.toDto(pais);
    }

    /**
     * Update a pais.
     *
     * @param paisDTO the entity to save.
     * @return the persisted entity.
     */
    public PaisDTO update(PaisDTO paisDTO) {
        log.debug("Request to update Pais : {}", paisDTO);
        Pais pais = paisMapper.toEntity(paisDTO);
        pais = paisRepository.save(pais);
        return paisMapper.toDto(pais);
    }

    /**
     * Partially update a pais.
     *
     * @param paisDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PaisDTO> partialUpdate(PaisDTO paisDTO) {
        log.debug("Request to partially update Pais : {}", paisDTO);

        return paisRepository
            .findById(paisDTO.getId())
            .map(existingPais -> {
                paisMapper.partialUpdate(existingPais, paisDTO);

                return existingPais;
            })
            .map(paisRepository::save)
            .map(paisMapper::toDto);
    }

    /**
     * Get one pais by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PaisDTO> findOne(Long id) {
        log.debug("Request to get Pais : {}", id);
        return paisRepository.findById(id).map(paisMapper::toDto);
    }

    /**
     * Delete the pais by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Pais : {}", id);
        paisRepository.deleteById(id);
    }
}
