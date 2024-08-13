package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Subtarefa;
import com.dobemcontabilidade.repository.SubtarefaRepository;
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

    public SubtarefaService(SubtarefaRepository subtarefaRepository) {
        this.subtarefaRepository = subtarefaRepository;
    }

    /**
     * Save a subtarefa.
     *
     * @param subtarefa the entity to save.
     * @return the persisted entity.
     */
    public Subtarefa save(Subtarefa subtarefa) {
        log.debug("Request to save Subtarefa : {}", subtarefa);
        return subtarefaRepository.save(subtarefa);
    }

    /**
     * Update a subtarefa.
     *
     * @param subtarefa the entity to save.
     * @return the persisted entity.
     */
    public Subtarefa update(Subtarefa subtarefa) {
        log.debug("Request to update Subtarefa : {}", subtarefa);
        return subtarefaRepository.save(subtarefa);
    }

    /**
     * Partially update a subtarefa.
     *
     * @param subtarefa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Subtarefa> partialUpdate(Subtarefa subtarefa) {
        log.debug("Request to partially update Subtarefa : {}", subtarefa);

        return subtarefaRepository
            .findById(subtarefa.getId())
            .map(existingSubtarefa -> {
                if (subtarefa.getOrdem() != null) {
                    existingSubtarefa.setOrdem(subtarefa.getOrdem());
                }
                if (subtarefa.getItem() != null) {
                    existingSubtarefa.setItem(subtarefa.getItem());
                }
                if (subtarefa.getDescricao() != null) {
                    existingSubtarefa.setDescricao(subtarefa.getDescricao());
                }

                return existingSubtarefa;
            })
            .map(subtarefaRepository::save);
    }

    /**
     * Get all the subtarefas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Subtarefa> findAllWithEagerRelationships(Pageable pageable) {
        return subtarefaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one subtarefa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Subtarefa> findOne(Long id) {
        log.debug("Request to get Subtarefa : {}", id);
        return subtarefaRepository.findOneWithEagerRelationships(id);
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
