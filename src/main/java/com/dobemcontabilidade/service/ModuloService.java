package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Modulo;
import com.dobemcontabilidade.repository.ModuloRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Modulo}.
 */
@Service
@Transactional
public class ModuloService {

    private static final Logger log = LoggerFactory.getLogger(ModuloService.class);

    private final ModuloRepository moduloRepository;

    public ModuloService(ModuloRepository moduloRepository) {
        this.moduloRepository = moduloRepository;
    }

    /**
     * Save a modulo.
     *
     * @param modulo the entity to save.
     * @return the persisted entity.
     */
    public Modulo save(Modulo modulo) {
        log.debug("Request to save Modulo : {}", modulo);
        return moduloRepository.save(modulo);
    }

    /**
     * Update a modulo.
     *
     * @param modulo the entity to save.
     * @return the persisted entity.
     */
    public Modulo update(Modulo modulo) {
        log.debug("Request to update Modulo : {}", modulo);
        return moduloRepository.save(modulo);
    }

    /**
     * Partially update a modulo.
     *
     * @param modulo the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Modulo> partialUpdate(Modulo modulo) {
        log.debug("Request to partially update Modulo : {}", modulo);

        return moduloRepository
            .findById(modulo.getId())
            .map(existingModulo -> {
                if (modulo.getNome() != null) {
                    existingModulo.setNome(modulo.getNome());
                }
                if (modulo.getDescricao() != null) {
                    existingModulo.setDescricao(modulo.getDescricao());
                }

                return existingModulo;
            })
            .map(moduloRepository::save);
    }

    /**
     * Get all the modulos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Modulo> findAllWithEagerRelationships(Pageable pageable) {
        return moduloRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one modulo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Modulo> findOne(Long id) {
        log.debug("Request to get Modulo : {}", id);
        return moduloRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the modulo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Modulo : {}", id);
        moduloRepository.deleteById(id);
    }
}
