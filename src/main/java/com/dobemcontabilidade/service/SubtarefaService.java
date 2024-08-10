package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Subtarefa;
import com.dobemcontabilidade.repository.SubtarefaRepository;
import com.dobemcontabilidade.service.dto.SubtarefaDTO;
import com.dobemcontabilidade.service.mapper.SubtarefaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Subtarefa}.
 */
@Service
@Transactional
public class SubtarefaService {

    private static final Logger log = LoggerFactory.getLogger(SubtarefaService.class);

    private final SubtarefaRepository subtarefaRepository;

    private final SubtarefaMapper subtarefaMapper;

    public SubtarefaService(SubtarefaRepository subtarefaRepository, SubtarefaMapper subtarefaMapper) {
        this.subtarefaRepository = subtarefaRepository;
        this.subtarefaMapper = subtarefaMapper;
    }

    /**
     * Save a subtarefa.
     *
     * @param subtarefaDTO the entity to save.
     * @return the persisted entity.
     */
    public SubtarefaDTO save(SubtarefaDTO subtarefaDTO) {
        log.debug("Request to save Subtarefa : {}", subtarefaDTO);
        Subtarefa subtarefa = subtarefaMapper.toEntity(subtarefaDTO);
        subtarefa = subtarefaRepository.save(subtarefa);
        return subtarefaMapper.toDto(subtarefa);
    }

    /**
     * Update a subtarefa.
     *
     * @param subtarefaDTO the entity to save.
     * @return the persisted entity.
     */
    public SubtarefaDTO update(SubtarefaDTO subtarefaDTO) {
        log.debug("Request to update Subtarefa : {}", subtarefaDTO);
        Subtarefa subtarefa = subtarefaMapper.toEntity(subtarefaDTO);
        subtarefa = subtarefaRepository.save(subtarefa);
        return subtarefaMapper.toDto(subtarefa);
    }

    /**
     * Partially update a subtarefa.
     *
     * @param subtarefaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SubtarefaDTO> partialUpdate(SubtarefaDTO subtarefaDTO) {
        log.debug("Request to partially update Subtarefa : {}", subtarefaDTO);

        return subtarefaRepository
            .findById(subtarefaDTO.getId())
            .map(existingSubtarefa -> {
                subtarefaMapper.partialUpdate(existingSubtarefa, subtarefaDTO);

                return existingSubtarefa;
            })
            .map(subtarefaRepository::save)
            .map(subtarefaMapper::toDto);
    }

    /**
     * Get all the subtarefas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<SubtarefaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return subtarefaRepository.findAllWithEagerRelationships(pageable).map(subtarefaMapper::toDto);
    }

    /**
     * Get one subtarefa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SubtarefaDTO> findOne(Long id) {
        log.debug("Request to get Subtarefa : {}", id);
        return subtarefaRepository.findOneWithEagerRelationships(id).map(subtarefaMapper::toDto);
    }

    /**
     * Delete the subtarefa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Subtarefa : {}", id);
        subtarefaRepository.deleteById(id);
    }
}
