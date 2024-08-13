package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.ClasseCnae;
import com.dobemcontabilidade.repository.ClasseCnaeRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.ClasseCnae}.
 */
@Service
@Transactional
public class ClasseCnaeService {

    private static final Logger log = LoggerFactory.getLogger(ClasseCnaeService.class);

    private final ClasseCnaeRepository classeCnaeRepository;

    public ClasseCnaeService(ClasseCnaeRepository classeCnaeRepository) {
        this.classeCnaeRepository = classeCnaeRepository;
    }

    /**
     * Save a classeCnae.
     *
     * @param classeCnae the entity to save.
     * @return the persisted entity.
     */
    public ClasseCnae save(ClasseCnae classeCnae) {
        log.debug("Request to save ClasseCnae : {}", classeCnae);
        return classeCnaeRepository.save(classeCnae);
    }

    /**
     * Update a classeCnae.
     *
     * @param classeCnae the entity to save.
     * @return the persisted entity.
     */
    public ClasseCnae update(ClasseCnae classeCnae) {
        log.debug("Request to update ClasseCnae : {}", classeCnae);
        return classeCnaeRepository.save(classeCnae);
    }

    /**
     * Partially update a classeCnae.
     *
     * @param classeCnae the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClasseCnae> partialUpdate(ClasseCnae classeCnae) {
        log.debug("Request to partially update ClasseCnae : {}", classeCnae);

        return classeCnaeRepository
            .findById(classeCnae.getId())
            .map(existingClasseCnae -> {
                if (classeCnae.getCodigo() != null) {
                    existingClasseCnae.setCodigo(classeCnae.getCodigo());
                }
                if (classeCnae.getDescricao() != null) {
                    existingClasseCnae.setDescricao(classeCnae.getDescricao());
                }

                return existingClasseCnae;
            })
            .map(classeCnaeRepository::save);
    }

    /**
     * Get all the classeCnaes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ClasseCnae> findAllWithEagerRelationships(Pageable pageable) {
        return classeCnaeRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one classeCnae by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClasseCnae> findOne(Long id) {
        log.debug("Request to get ClasseCnae : {}", id);
        return classeCnaeRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the classeCnae by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClasseCnae : {}", id);
        classeCnaeRepository.deleteById(id);
    }
}
