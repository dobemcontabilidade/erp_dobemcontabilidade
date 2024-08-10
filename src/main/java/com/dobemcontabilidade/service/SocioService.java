package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Socio;
import com.dobemcontabilidade.repository.SocioRepository;
import com.dobemcontabilidade.service.dto.SocioDTO;
import com.dobemcontabilidade.service.mapper.SocioMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Socio}.
 */
@Service
@Transactional
public class SocioService {

    private static final Logger log = LoggerFactory.getLogger(SocioService.class);

    private final SocioRepository socioRepository;

    private final SocioMapper socioMapper;

    public SocioService(SocioRepository socioRepository, SocioMapper socioMapper) {
        this.socioRepository = socioRepository;
        this.socioMapper = socioMapper;
    }

    /**
     * Save a socio.
     *
     * @param socioDTO the entity to save.
     * @return the persisted entity.
     */
    public SocioDTO save(SocioDTO socioDTO) {
        log.debug("Request to save Socio : {}", socioDTO);
        Socio socio = socioMapper.toEntity(socioDTO);
        socio = socioRepository.save(socio);
        return socioMapper.toDto(socio);
    }

    /**
     * Update a socio.
     *
     * @param socioDTO the entity to save.
     * @return the persisted entity.
     */
    public SocioDTO update(SocioDTO socioDTO) {
        log.debug("Request to update Socio : {}", socioDTO);
        Socio socio = socioMapper.toEntity(socioDTO);
        socio = socioRepository.save(socio);
        return socioMapper.toDto(socio);
    }

    /**
     * Partially update a socio.
     *
     * @param socioDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SocioDTO> partialUpdate(SocioDTO socioDTO) {
        log.debug("Request to partially update Socio : {}", socioDTO);

        return socioRepository
            .findById(socioDTO.getId())
            .map(existingSocio -> {
                socioMapper.partialUpdate(existingSocio, socioDTO);

                return existingSocio;
            })
            .map(socioRepository::save)
            .map(socioMapper::toDto);
    }

    /**
     * Get all the socios with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<SocioDTO> findAllWithEagerRelationships(Pageable pageable) {
        return socioRepository.findAllWithEagerRelationships(pageable).map(socioMapper::toDto);
    }

    /**
     * Get one socio by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SocioDTO> findOne(Long id) {
        log.debug("Request to get Socio : {}", id);
        return socioRepository.findOneWithEagerRelationships(id).map(socioMapper::toDto);
    }

    /**
     * Delete the socio by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Socio : {}", id);
        socioRepository.deleteById(id);
    }
}
