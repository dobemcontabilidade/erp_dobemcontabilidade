package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.DepartamentoFuncionario;
import com.dobemcontabilidade.repository.DepartamentoFuncionarioRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.DepartamentoFuncionario}.
 */
@Service
@Transactional
public class DepartamentoFuncionarioService {

    private static final Logger log = LoggerFactory.getLogger(DepartamentoFuncionarioService.class);

    private final DepartamentoFuncionarioRepository departamentoFuncionarioRepository;

    public DepartamentoFuncionarioService(DepartamentoFuncionarioRepository departamentoFuncionarioRepository) {
        this.departamentoFuncionarioRepository = departamentoFuncionarioRepository;
    }

    /**
     * Save a departamentoFuncionario.
     *
     * @param departamentoFuncionario the entity to save.
     * @return the persisted entity.
     */
    public DepartamentoFuncionario save(DepartamentoFuncionario departamentoFuncionario) {
        log.debug("Request to save DepartamentoFuncionario : {}", departamentoFuncionario);
        return departamentoFuncionarioRepository.save(departamentoFuncionario);
    }

    /**
     * Update a departamentoFuncionario.
     *
     * @param departamentoFuncionario the entity to save.
     * @return the persisted entity.
     */
    public DepartamentoFuncionario update(DepartamentoFuncionario departamentoFuncionario) {
        log.debug("Request to update DepartamentoFuncionario : {}", departamentoFuncionario);
        return departamentoFuncionarioRepository.save(departamentoFuncionario);
    }

    /**
     * Partially update a departamentoFuncionario.
     *
     * @param departamentoFuncionario the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DepartamentoFuncionario> partialUpdate(DepartamentoFuncionario departamentoFuncionario) {
        log.debug("Request to partially update DepartamentoFuncionario : {}", departamentoFuncionario);

        return departamentoFuncionarioRepository
            .findById(departamentoFuncionario.getId())
            .map(existingDepartamentoFuncionario -> {
                if (departamentoFuncionario.getCargo() != null) {
                    existingDepartamentoFuncionario.setCargo(departamentoFuncionario.getCargo());
                }

                return existingDepartamentoFuncionario;
            })
            .map(departamentoFuncionarioRepository::save);
    }

    /**
     * Get all the departamentoFuncionarios with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DepartamentoFuncionario> findAllWithEagerRelationships(Pageable pageable) {
        return departamentoFuncionarioRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one departamentoFuncionario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DepartamentoFuncionario> findOne(Long id) {
        log.debug("Request to get DepartamentoFuncionario : {}", id);
        return departamentoFuncionarioRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the departamentoFuncionario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DepartamentoFuncionario : {}", id);
        departamentoFuncionarioRepository.deleteById(id);
    }
}
