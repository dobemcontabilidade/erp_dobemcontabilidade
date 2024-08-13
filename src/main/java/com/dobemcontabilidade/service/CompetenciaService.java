package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Competencia;
import com.dobemcontabilidade.repository.CompetenciaRepository;
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

    public CompetenciaService(CompetenciaRepository competenciaRepository) {
        this.competenciaRepository = competenciaRepository;
    }

    /**
     * Save a competencia.
     *
     * @param competencia the entity to save.
     * @return the persisted entity.
     */
    public Competencia save(Competencia competencia) {
        log.debug("Request to save Competencia : {}", competencia);
        return competenciaRepository.save(competencia);
    }

    /**
     * Update a competencia.
     *
     * @param competencia the entity to save.
     * @return the persisted entity.
     */
    public Competencia update(Competencia competencia) {
        log.debug("Request to update Competencia : {}", competencia);
        return competenciaRepository.save(competencia);
    }

    /**
     * Partially update a competencia.
     *
     * @param competencia the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Competencia> partialUpdate(Competencia competencia) {
        log.debug("Request to partially update Competencia : {}", competencia);

        return competenciaRepository
            .findById(competencia.getId())
            .map(existingCompetencia -> {
                if (competencia.getNome() != null) {
                    existingCompetencia.setNome(competencia.getNome());
                }
                if (competencia.getNumero() != null) {
                    existingCompetencia.setNumero(competencia.getNumero());
                }

                return existingCompetencia;
            })
            .map(competenciaRepository::save);
    }

    /**
     * Get one competencia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Competencia> findOne(Long id) {
        log.debug("Request to get Competencia : {}", id);
        return competenciaRepository.findById(id);
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
