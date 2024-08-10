package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Esfera;
import com.dobemcontabilidade.repository.EsferaRepository;
import com.dobemcontabilidade.service.dto.EsferaDTO;
import com.dobemcontabilidade.service.mapper.EsferaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Esfera}.
 */
@Service
@Transactional
public class EsferaService {

    private static final Logger log = LoggerFactory.getLogger(EsferaService.class);

    private final EsferaRepository esferaRepository;

    private final EsferaMapper esferaMapper;

    public EsferaService(EsferaRepository esferaRepository, EsferaMapper esferaMapper) {
        this.esferaRepository = esferaRepository;
        this.esferaMapper = esferaMapper;
    }

    /**
     * Save a esfera.
     *
     * @param esferaDTO the entity to save.
     * @return the persisted entity.
     */
    public EsferaDTO save(EsferaDTO esferaDTO) {
        log.debug("Request to save Esfera : {}", esferaDTO);
        Esfera esfera = esferaMapper.toEntity(esferaDTO);
        esfera = esferaRepository.save(esfera);
        return esferaMapper.toDto(esfera);
    }

    /**
     * Update a esfera.
     *
     * @param esferaDTO the entity to save.
     * @return the persisted entity.
     */
    public EsferaDTO update(EsferaDTO esferaDTO) {
        log.debug("Request to update Esfera : {}", esferaDTO);
        Esfera esfera = esferaMapper.toEntity(esferaDTO);
        esfera = esferaRepository.save(esfera);
        return esferaMapper.toDto(esfera);
    }

    /**
     * Partially update a esfera.
     *
     * @param esferaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EsferaDTO> partialUpdate(EsferaDTO esferaDTO) {
        log.debug("Request to partially update Esfera : {}", esferaDTO);

        return esferaRepository
            .findById(esferaDTO.getId())
            .map(existingEsfera -> {
                esferaMapper.partialUpdate(existingEsfera, esferaDTO);

                return existingEsfera;
            })
            .map(esferaRepository::save)
            .map(esferaMapper::toDto);
    }

    /**
     * Get one esfera by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EsferaDTO> findOne(Long id) {
        log.debug("Request to get Esfera : {}", id);
        return esferaRepository.findById(id).map(esferaMapper::toDto);
    }

    /**
     * Delete the esfera by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Esfera : {}", id);
        esferaRepository.deleteById(id);
    }
}
