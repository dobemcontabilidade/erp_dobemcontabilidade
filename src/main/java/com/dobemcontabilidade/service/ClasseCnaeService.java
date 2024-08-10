package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.ClasseCnae;
import com.dobemcontabilidade.repository.ClasseCnaeRepository;
import com.dobemcontabilidade.service.dto.ClasseCnaeDTO;
import com.dobemcontabilidade.service.mapper.ClasseCnaeMapper;
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

    private final ClasseCnaeMapper classeCnaeMapper;

    public ClasseCnaeService(ClasseCnaeRepository classeCnaeRepository, ClasseCnaeMapper classeCnaeMapper) {
        this.classeCnaeRepository = classeCnaeRepository;
        this.classeCnaeMapper = classeCnaeMapper;
    }

    /**
     * Save a classeCnae.
     *
     * @param classeCnaeDTO the entity to save.
     * @return the persisted entity.
     */
    public ClasseCnaeDTO save(ClasseCnaeDTO classeCnaeDTO) {
        log.debug("Request to save ClasseCnae : {}", classeCnaeDTO);
        ClasseCnae classeCnae = classeCnaeMapper.toEntity(classeCnaeDTO);
        classeCnae = classeCnaeRepository.save(classeCnae);
        return classeCnaeMapper.toDto(classeCnae);
    }

    /**
     * Update a classeCnae.
     *
     * @param classeCnaeDTO the entity to save.
     * @return the persisted entity.
     */
    public ClasseCnaeDTO update(ClasseCnaeDTO classeCnaeDTO) {
        log.debug("Request to update ClasseCnae : {}", classeCnaeDTO);
        ClasseCnae classeCnae = classeCnaeMapper.toEntity(classeCnaeDTO);
        classeCnae = classeCnaeRepository.save(classeCnae);
        return classeCnaeMapper.toDto(classeCnae);
    }

    /**
     * Partially update a classeCnae.
     *
     * @param classeCnaeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClasseCnaeDTO> partialUpdate(ClasseCnaeDTO classeCnaeDTO) {
        log.debug("Request to partially update ClasseCnae : {}", classeCnaeDTO);

        return classeCnaeRepository
            .findById(classeCnaeDTO.getId())
            .map(existingClasseCnae -> {
                classeCnaeMapper.partialUpdate(existingClasseCnae, classeCnaeDTO);

                return existingClasseCnae;
            })
            .map(classeCnaeRepository::save)
            .map(classeCnaeMapper::toDto);
    }

    /**
     * Get all the classeCnaes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ClasseCnaeDTO> findAllWithEagerRelationships(Pageable pageable) {
        return classeCnaeRepository.findAllWithEagerRelationships(pageable).map(classeCnaeMapper::toDto);
    }

    /**
     * Get one classeCnae by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClasseCnaeDTO> findOne(Long id) {
        log.debug("Request to get ClasseCnae : {}", id);
        return classeCnaeRepository.findOneWithEagerRelationships(id).map(classeCnaeMapper::toDto);
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
