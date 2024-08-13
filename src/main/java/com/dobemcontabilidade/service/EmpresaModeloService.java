package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.EmpresaModelo;
import com.dobemcontabilidade.repository.EmpresaModeloRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.EmpresaModelo}.
 */
@Service
@Transactional
public class EmpresaModeloService {

    private static final Logger log = LoggerFactory.getLogger(EmpresaModeloService.class);

    private final EmpresaModeloRepository empresaModeloRepository;

    public EmpresaModeloService(EmpresaModeloRepository empresaModeloRepository) {
        this.empresaModeloRepository = empresaModeloRepository;
    }

    /**
     * Save a empresaModelo.
     *
     * @param empresaModelo the entity to save.
     * @return the persisted entity.
     */
    public EmpresaModelo save(EmpresaModelo empresaModelo) {
        log.debug("Request to save EmpresaModelo : {}", empresaModelo);
        return empresaModeloRepository.save(empresaModelo);
    }

    /**
     * Update a empresaModelo.
     *
     * @param empresaModelo the entity to save.
     * @return the persisted entity.
     */
    public EmpresaModelo update(EmpresaModelo empresaModelo) {
        log.debug("Request to update EmpresaModelo : {}", empresaModelo);
        return empresaModeloRepository.save(empresaModelo);
    }

    /**
     * Partially update a empresaModelo.
     *
     * @param empresaModelo the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmpresaModelo> partialUpdate(EmpresaModelo empresaModelo) {
        log.debug("Request to partially update EmpresaModelo : {}", empresaModelo);

        return empresaModeloRepository
            .findById(empresaModelo.getId())
            .map(existingEmpresaModelo -> {
                if (empresaModelo.getNome() != null) {
                    existingEmpresaModelo.setNome(empresaModelo.getNome());
                }
                if (empresaModelo.getObservacao() != null) {
                    existingEmpresaModelo.setObservacao(empresaModelo.getObservacao());
                }

                return existingEmpresaModelo;
            })
            .map(empresaModeloRepository::save);
    }

    /**
     * Get all the empresaModelos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EmpresaModelo> findAll() {
        log.debug("Request to get all EmpresaModelos");
        return empresaModeloRepository.findAll();
    }

    /**
     * Get all the empresaModelos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<EmpresaModelo> findAllWithEagerRelationships(Pageable pageable) {
        return empresaModeloRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one empresaModelo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmpresaModelo> findOne(Long id) {
        log.debug("Request to get EmpresaModelo : {}", id);
        return empresaModeloRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the empresaModelo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmpresaModelo : {}", id);
        empresaModeloRepository.deleteById(id);
    }
}
