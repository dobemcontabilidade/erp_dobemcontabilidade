package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Competencia;
import com.dobemcontabilidade.repository.CompetenciaRepository;
import com.dobemcontabilidade.service.dto.CompetenciaDTO;
import com.dobemcontabilidade.service.mapper.CompetenciaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Competencia}.
 */
@Service
@Transactional
public class CompetenciaService {

    private static final Logger log = LoggerFactory.getLogger(CompetenciaService.class);

    private final CompetenciaRepository competenciaRepository;

    private final CompetenciaMapper competenciaMapper;

    public CompetenciaService(CompetenciaRepository competenciaRepository, CompetenciaMapper competenciaMapper) {
        this.competenciaRepository = competenciaRepository;
        this.competenciaMapper = competenciaMapper;
    }

    /**
     * Save a competencia.
     *
     * @param competenciaDTO the entity to save.
     * @return the persisted entity.
     */
    public CompetenciaDTO save(CompetenciaDTO competenciaDTO) {
        log.debug("Request to save Competencia : {}", competenciaDTO);
        Competencia competencia = competenciaMapper.toEntity(competenciaDTO);
        competencia = competenciaRepository.save(competencia);
        return competenciaMapper.toDto(competencia);
    }

    /**
     * Update a competencia.
     *
     * @param competenciaDTO the entity to save.
     * @return the persisted entity.
     */
    public CompetenciaDTO update(CompetenciaDTO competenciaDTO) {
        log.debug("Request to update Competencia : {}", competenciaDTO);
        Competencia competencia = competenciaMapper.toEntity(competenciaDTO);
        competencia = competenciaRepository.save(competencia);
        return competenciaMapper.toDto(competencia);
    }

    /**
     * Partially update a competencia.
     *
     * @param competenciaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CompetenciaDTO> partialUpdate(CompetenciaDTO competenciaDTO) {
        log.debug("Request to partially update Competencia : {}", competenciaDTO);

        return competenciaRepository
            .findById(competenciaDTO.getId())
            .map(existingCompetencia -> {
                competenciaMapper.partialUpdate(existingCompetencia, competenciaDTO);

                return existingCompetencia;
            })
            .map(competenciaRepository::save)
            .map(competenciaMapper::toDto);
    }

    /**
     * Get one competencia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CompetenciaDTO> findOne(Long id) {
        log.debug("Request to get Competencia : {}", id);
        return competenciaRepository.findById(id).map(competenciaMapper::toDto);
    }

    /**
     * Delete the competencia by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Competencia : {}", id);
        competenciaRepository.deleteById(id);
    }
}
