package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Frequencia;
import com.dobemcontabilidade.repository.FrequenciaRepository;
import com.dobemcontabilidade.service.dto.FrequenciaDTO;
import com.dobemcontabilidade.service.mapper.FrequenciaMapper;
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

    private final FrequenciaMapper frequenciaMapper;

    public FrequenciaService(FrequenciaRepository frequenciaRepository, FrequenciaMapper frequenciaMapper) {
        this.frequenciaRepository = frequenciaRepository;
        this.frequenciaMapper = frequenciaMapper;
    }

    /**
     * Save a frequencia.
     *
     * @param frequenciaDTO the entity to save.
     * @return the persisted entity.
     */
    public FrequenciaDTO save(FrequenciaDTO frequenciaDTO) {
        log.debug("Request to save Frequencia : {}", frequenciaDTO);
        Frequencia frequencia = frequenciaMapper.toEntity(frequenciaDTO);
        frequencia = frequenciaRepository.save(frequencia);
        return frequenciaMapper.toDto(frequencia);
    }

    /**
     * Update a frequencia.
     *
     * @param frequenciaDTO the entity to save.
     * @return the persisted entity.
     */
    public FrequenciaDTO update(FrequenciaDTO frequenciaDTO) {
        log.debug("Request to update Frequencia : {}", frequenciaDTO);
        Frequencia frequencia = frequenciaMapper.toEntity(frequenciaDTO);
        frequencia = frequenciaRepository.save(frequencia);
        return frequenciaMapper.toDto(frequencia);
    }

    /**
     * Partially update a frequencia.
     *
     * @param frequenciaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FrequenciaDTO> partialUpdate(FrequenciaDTO frequenciaDTO) {
        log.debug("Request to partially update Frequencia : {}", frequenciaDTO);

        return frequenciaRepository
            .findById(frequenciaDTO.getId())
            .map(existingFrequencia -> {
                frequenciaMapper.partialUpdate(existingFrequencia, frequenciaDTO);

                return existingFrequencia;
            })
            .map(frequenciaRepository::save)
            .map(frequenciaMapper::toDto);
    }

    /**
     * Get one frequencia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FrequenciaDTO> findOne(Long id) {
        log.debug("Request to get Frequencia : {}", id);
        return frequenciaRepository.findById(id).map(frequenciaMapper::toDto);
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
