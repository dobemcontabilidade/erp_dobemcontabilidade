package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.DepartamentoEmpresa;
import com.dobemcontabilidade.repository.DepartamentoEmpresaRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.DepartamentoEmpresa}.
 */
@Service
@Transactional
public class DepartamentoEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(DepartamentoEmpresaService.class);

    private final DepartamentoEmpresaRepository departamentoEmpresaRepository;

    public DepartamentoEmpresaService(DepartamentoEmpresaRepository departamentoEmpresaRepository) {
        this.departamentoEmpresaRepository = departamentoEmpresaRepository;
    }

    /**
     * Save a departamentoEmpresa.
     *
     * @param departamentoEmpresa the entity to save.
     * @return the persisted entity.
     */
    public DepartamentoEmpresa save(DepartamentoEmpresa departamentoEmpresa) {
        log.debug("Request to save DepartamentoEmpresa : {}", departamentoEmpresa);
        return departamentoEmpresaRepository.save(departamentoEmpresa);
    }

    /**
     * Update a departamentoEmpresa.
     *
     * @param departamentoEmpresa the entity to save.
     * @return the persisted entity.
     */
    public DepartamentoEmpresa update(DepartamentoEmpresa departamentoEmpresa) {
        log.debug("Request to update DepartamentoEmpresa : {}", departamentoEmpresa);
        return departamentoEmpresaRepository.save(departamentoEmpresa);
    }

    /**
     * Partially update a departamentoEmpresa.
     *
     * @param departamentoEmpresa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DepartamentoEmpresa> partialUpdate(DepartamentoEmpresa departamentoEmpresa) {
        log.debug("Request to partially update DepartamentoEmpresa : {}", departamentoEmpresa);

        return departamentoEmpresaRepository
            .findById(departamentoEmpresa.getId())
            .map(existingDepartamentoEmpresa -> {
                if (departamentoEmpresa.getPontuacao() != null) {
                    existingDepartamentoEmpresa.setPontuacao(departamentoEmpresa.getPontuacao());
                }
                if (departamentoEmpresa.getDepoimento() != null) {
                    existingDepartamentoEmpresa.setDepoimento(departamentoEmpresa.getDepoimento());
                }
                if (departamentoEmpresa.getReclamacao() != null) {
                    existingDepartamentoEmpresa.setReclamacao(departamentoEmpresa.getReclamacao());
                }

                return existingDepartamentoEmpresa;
            })
            .map(departamentoEmpresaRepository::save);
    }

    /**
     * Get all the departamentoEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DepartamentoEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return departamentoEmpresaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one departamentoEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DepartamentoEmpresa> findOne(Long id) {
        log.debug("Request to get DepartamentoEmpresa : {}", id);
        return departamentoEmpresaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the departamentoEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DepartamentoEmpresa : {}", id);
        departamentoEmpresaRepository.deleteById(id);
    }
}
