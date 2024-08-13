package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.PerfilContadorDepartamento;
import com.dobemcontabilidade.repository.PerfilContadorDepartamentoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.PerfilContadorDepartamento}.
 */
@Service
@Transactional
public class PerfilContadorDepartamentoService {

    private static final Logger log = LoggerFactory.getLogger(PerfilContadorDepartamentoService.class);

    private final PerfilContadorDepartamentoRepository perfilContadorDepartamentoRepository;

    public PerfilContadorDepartamentoService(PerfilContadorDepartamentoRepository perfilContadorDepartamentoRepository) {
        this.perfilContadorDepartamentoRepository = perfilContadorDepartamentoRepository;
    }

    /**
     * Save a perfilContadorDepartamento.
     *
     * @param perfilContadorDepartamento the entity to save.
     * @return the persisted entity.
     */
    public PerfilContadorDepartamento save(PerfilContadorDepartamento perfilContadorDepartamento) {
        log.debug("Request to save PerfilContadorDepartamento : {}", perfilContadorDepartamento);
        return perfilContadorDepartamentoRepository.save(perfilContadorDepartamento);
    }

    /**
     * Update a perfilContadorDepartamento.
     *
     * @param perfilContadorDepartamento the entity to save.
     * @return the persisted entity.
     */
    public PerfilContadorDepartamento update(PerfilContadorDepartamento perfilContadorDepartamento) {
        log.debug("Request to update PerfilContadorDepartamento : {}", perfilContadorDepartamento);
        return perfilContadorDepartamentoRepository.save(perfilContadorDepartamento);
    }

    /**
     * Partially update a perfilContadorDepartamento.
     *
     * @param perfilContadorDepartamento the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PerfilContadorDepartamento> partialUpdate(PerfilContadorDepartamento perfilContadorDepartamento) {
        log.debug("Request to partially update PerfilContadorDepartamento : {}", perfilContadorDepartamento);

        return perfilContadorDepartamentoRepository
            .findById(perfilContadorDepartamento.getId())
            .map(existingPerfilContadorDepartamento -> {
                if (perfilContadorDepartamento.getQuantidadeEmpresas() != null) {
                    existingPerfilContadorDepartamento.setQuantidadeEmpresas(perfilContadorDepartamento.getQuantidadeEmpresas());
                }
                if (perfilContadorDepartamento.getPercentualExperiencia() != null) {
                    existingPerfilContadorDepartamento.setPercentualExperiencia(perfilContadorDepartamento.getPercentualExperiencia());
                }

                return existingPerfilContadorDepartamento;
            })
            .map(perfilContadorDepartamentoRepository::save);
    }

    /**
     * Get all the perfilContadorDepartamentos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PerfilContadorDepartamento> findAllWithEagerRelationships(Pageable pageable) {
        return perfilContadorDepartamentoRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one perfilContadorDepartamento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PerfilContadorDepartamento> findOne(Long id) {
        log.debug("Request to get PerfilContadorDepartamento : {}", id);
        return perfilContadorDepartamentoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the perfilContadorDepartamento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PerfilContadorDepartamento : {}", id);
        perfilContadorDepartamentoRepository.deleteById(id);
    }
}
