package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Frequencia;
import com.dobemcontabilidade.repository.FrequenciaRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Frequencia}.
 */
@Service
@Transactional
public class FrequenciaService {

    private static final Logger log = LoggerFactory.getLogger(FrequenciaService.class);

    private final FrequenciaRepository frequenciaRepository;

    public FrequenciaService(FrequenciaRepository frequenciaRepository) {
        this.frequenciaRepository = frequenciaRepository;
    }

    /**
     * Save a frequencia.
     *
     * @param frequencia the entity to save.
     * @return the persisted entity.
     */
    public Frequencia save(Frequencia frequencia) {
        log.debug("Request to save Frequencia : {}", frequencia);
        return frequenciaRepository.save(frequencia);
    }

    /**
     * Update a frequencia.
     *
     * @param frequencia the entity to save.
     * @return the persisted entity.
     */
    public Frequencia update(Frequencia frequencia) {
        log.debug("Request to update Frequencia : {}", frequencia);
        return frequenciaRepository.save(frequencia);
    }

    /**
     * Partially update a frequencia.
     *
     * @param frequencia the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Frequencia> partialUpdate(Frequencia frequencia) {
        log.debug("Request to partially update Frequencia : {}", frequencia);

        return frequenciaRepository
            .findById(frequencia.getId())
            .map(existingFrequencia -> {
                if (frequencia.getNome() != null) {
                    existingFrequencia.setNome(frequencia.getNome());
                }
                if (frequencia.getDescricao() != null) {
                    existingFrequencia.setDescricao(frequencia.getDescricao());
                }
                if (frequencia.getNumeroDias() != null) {
                    existingFrequencia.setNumeroDias(frequencia.getNumeroDias());
                }

                return existingFrequencia;
            })
            .map(frequenciaRepository::save);
    }

    /**
     * Get one frequencia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Frequencia> findOne(Long id) {
        log.debug("Request to get Frequencia : {}", id);
        return frequenciaRepository.findById(id);
    }

    /**
     * Delete the frequencia by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Frequencia : {}", id);
        frequenciaRepository.deleteById(id);
    }
}
