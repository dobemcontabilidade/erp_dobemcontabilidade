package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.DependentesFuncionario;
import com.dobemcontabilidade.repository.DependentesFuncionarioRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.DependentesFuncionario}.
 */
@Service
@Transactional
public class DependentesFuncionarioService {

    private static final Logger log = LoggerFactory.getLogger(DependentesFuncionarioService.class);

    private final DependentesFuncionarioRepository dependentesFuncionarioRepository;

    public DependentesFuncionarioService(DependentesFuncionarioRepository dependentesFuncionarioRepository) {
        this.dependentesFuncionarioRepository = dependentesFuncionarioRepository;
    }

    /**
     * Save a dependentesFuncionario.
     *
     * @param dependentesFuncionario the entity to save.
     * @return the persisted entity.
     */
    public DependentesFuncionario save(DependentesFuncionario dependentesFuncionario) {
        log.debug("Request to save DependentesFuncionario : {}", dependentesFuncionario);
        return dependentesFuncionarioRepository.save(dependentesFuncionario);
    }

    /**
     * Update a dependentesFuncionario.
     *
     * @param dependentesFuncionario the entity to save.
     * @return the persisted entity.
     */
    public DependentesFuncionario update(DependentesFuncionario dependentesFuncionario) {
        log.debug("Request to update DependentesFuncionario : {}", dependentesFuncionario);
        return dependentesFuncionarioRepository.save(dependentesFuncionario);
    }

    /**
     * Partially update a dependentesFuncionario.
     *
     * @param dependentesFuncionario the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DependentesFuncionario> partialUpdate(DependentesFuncionario dependentesFuncionario) {
        log.debug("Request to partially update DependentesFuncionario : {}", dependentesFuncionario);

        return dependentesFuncionarioRepository
            .findById(dependentesFuncionario.getId())
            .map(existingDependentesFuncionario -> {
                if (dependentesFuncionario.getUrlCertidaoDependente() != null) {
                    existingDependentesFuncionario.setUrlCertidaoDependente(dependentesFuncionario.getUrlCertidaoDependente());
                }
                if (dependentesFuncionario.getUrlRgDependente() != null) {
                    existingDependentesFuncionario.setUrlRgDependente(dependentesFuncionario.getUrlRgDependente());
                }
                if (dependentesFuncionario.getDependenteIRRF() != null) {
                    existingDependentesFuncionario.setDependenteIRRF(dependentesFuncionario.getDependenteIRRF());
                }
                if (dependentesFuncionario.getDependenteSalarioFamilia() != null) {
                    existingDependentesFuncionario.setDependenteSalarioFamilia(dependentesFuncionario.getDependenteSalarioFamilia());
                }
                if (dependentesFuncionario.getTipoDependenteFuncionarioEnum() != null) {
                    existingDependentesFuncionario.setTipoDependenteFuncionarioEnum(
                        dependentesFuncionario.getTipoDependenteFuncionarioEnum()
                    );
                }

                return existingDependentesFuncionario;
            })
            .map(dependentesFuncionarioRepository::save);
    }

    /**
     * Get all the dependentesFuncionarios.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DependentesFuncionario> findAll() {
        log.debug("Request to get all DependentesFuncionarios");
        return dependentesFuncionarioRepository.findAll();
    }

    /**
     * Get all the dependentesFuncionarios with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DependentesFuncionario> findAllWithEagerRelationships(Pageable pageable) {
        return dependentesFuncionarioRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one dependentesFuncionario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DependentesFuncionario> findOne(Long id) {
        log.debug("Request to get DependentesFuncionario : {}", id);
        return dependentesFuncionarioRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the dependentesFuncionario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DependentesFuncionario : {}", id);
        dependentesFuncionarioRepository.deleteById(id);
    }
}
