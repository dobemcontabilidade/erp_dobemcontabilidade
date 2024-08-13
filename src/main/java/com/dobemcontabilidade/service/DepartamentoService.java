package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Departamento;
import com.dobemcontabilidade.repository.DepartamentoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Departamento}.
 */
@Service
@Transactional
public class DepartamentoService {

    private static final Logger log = LoggerFactory.getLogger(DepartamentoService.class);

    private final DepartamentoRepository departamentoRepository;

    public DepartamentoService(DepartamentoRepository departamentoRepository) {
        this.departamentoRepository = departamentoRepository;
    }

    /**
     * Save a departamento.
     *
     * @param departamento the entity to save.
     * @return the persisted entity.
     */
    public Departamento save(Departamento departamento) {
        log.debug("Request to save Departamento : {}", departamento);
        return departamentoRepository.save(departamento);
    }

    /**
     * Update a departamento.
     *
     * @param departamento the entity to save.
     * @return the persisted entity.
     */
    public Departamento update(Departamento departamento) {
        log.debug("Request to update Departamento : {}", departamento);
        return departamentoRepository.save(departamento);
    }

    /**
     * Partially update a departamento.
     *
     * @param departamento the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Departamento> partialUpdate(Departamento departamento) {
        log.debug("Request to partially update Departamento : {}", departamento);

        return departamentoRepository
            .findById(departamento.getId())
            .map(existingDepartamento -> {
                if (departamento.getNome() != null) {
                    existingDepartamento.setNome(departamento.getNome());
                }
                if (departamento.getDescricao() != null) {
                    existingDepartamento.setDescricao(departamento.getDescricao());
                }

                return existingDepartamento;
            })
            .map(departamentoRepository::save);
    }

    /**
     * Get one departamento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Departamento> findOne(Long id) {
        log.debug("Request to get Departamento : {}", id);
        return departamentoRepository.findById(id);
    }

    /**
     * Delete the departamento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Departamento : {}", id);
        departamentoRepository.deleteById(id);
    }
}
