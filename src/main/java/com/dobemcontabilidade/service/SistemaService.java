package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Sistema;
import com.dobemcontabilidade.repository.SistemaRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Sistema}.
 */
@Service
@Transactional
public class SistemaService {

    private static final Logger log = LoggerFactory.getLogger(SistemaService.class);

    private final SistemaRepository sistemaRepository;

    public SistemaService(SistemaRepository sistemaRepository) {
        this.sistemaRepository = sistemaRepository;
    }

    /**
     * Save a sistema.
     *
     * @param sistema the entity to save.
     * @return the persisted entity.
     */
    public Sistema save(Sistema sistema) {
        log.debug("Request to save Sistema : {}", sistema);
        return sistemaRepository.save(sistema);
    }

    /**
     * Update a sistema.
     *
     * @param sistema the entity to save.
     * @return the persisted entity.
     */
    public Sistema update(Sistema sistema) {
        log.debug("Request to update Sistema : {}", sistema);
        return sistemaRepository.save(sistema);
    }

    /**
     * Partially update a sistema.
     *
     * @param sistema the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Sistema> partialUpdate(Sistema sistema) {
        log.debug("Request to partially update Sistema : {}", sistema);

        return sistemaRepository
            .findById(sistema.getId())
            .map(existingSistema -> {
                if (sistema.getNome() != null) {
                    existingSistema.setNome(sistema.getNome());
                }
                if (sistema.getDescricao() != null) {
                    existingSistema.setDescricao(sistema.getDescricao());
                }

                return existingSistema;
            })
            .map(sistemaRepository::save);
    }

    /**
     * Get one sistema by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Sistema> findOne(Long id) {
        log.debug("Request to get Sistema : {}", id);
        return sistemaRepository.findById(id);
    }

    /**
     * Delete the sistema by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Sistema : {}", id);
        sistemaRepository.deleteById(id);
    }
}
