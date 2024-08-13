package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.ImpostoEmpresaModelo;
import com.dobemcontabilidade.repository.ImpostoEmpresaModeloRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.ImpostoEmpresaModelo}.
 */
@Service
@Transactional
public class ImpostoEmpresaModeloService {

    private static final Logger log = LoggerFactory.getLogger(ImpostoEmpresaModeloService.class);

    private final ImpostoEmpresaModeloRepository impostoEmpresaModeloRepository;

    public ImpostoEmpresaModeloService(ImpostoEmpresaModeloRepository impostoEmpresaModeloRepository) {
        this.impostoEmpresaModeloRepository = impostoEmpresaModeloRepository;
    }

    /**
     * Save a impostoEmpresaModelo.
     *
     * @param impostoEmpresaModelo the entity to save.
     * @return the persisted entity.
     */
    public ImpostoEmpresaModelo save(ImpostoEmpresaModelo impostoEmpresaModelo) {
        log.debug("Request to save ImpostoEmpresaModelo : {}", impostoEmpresaModelo);
        return impostoEmpresaModeloRepository.save(impostoEmpresaModelo);
    }

    /**
     * Update a impostoEmpresaModelo.
     *
     * @param impostoEmpresaModelo the entity to save.
     * @return the persisted entity.
     */
    public ImpostoEmpresaModelo update(ImpostoEmpresaModelo impostoEmpresaModelo) {
        log.debug("Request to update ImpostoEmpresaModelo : {}", impostoEmpresaModelo);
        return impostoEmpresaModeloRepository.save(impostoEmpresaModelo);
    }

    /**
     * Partially update a impostoEmpresaModelo.
     *
     * @param impostoEmpresaModelo the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ImpostoEmpresaModelo> partialUpdate(ImpostoEmpresaModelo impostoEmpresaModelo) {
        log.debug("Request to partially update ImpostoEmpresaModelo : {}", impostoEmpresaModelo);

        return impostoEmpresaModeloRepository
            .findById(impostoEmpresaModelo.getId())
            .map(existingImpostoEmpresaModelo -> {
                if (impostoEmpresaModelo.getNome() != null) {
                    existingImpostoEmpresaModelo.setNome(impostoEmpresaModelo.getNome());
                }
                if (impostoEmpresaModelo.getObservacao() != null) {
                    existingImpostoEmpresaModelo.setObservacao(impostoEmpresaModelo.getObservacao());
                }

                return existingImpostoEmpresaModelo;
            })
            .map(impostoEmpresaModeloRepository::save);
    }

    /**
     * Get all the impostoEmpresaModelos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ImpostoEmpresaModelo> findAll() {
        log.debug("Request to get all ImpostoEmpresaModelos");
        return impostoEmpresaModeloRepository.findAll();
    }

    /**
     * Get all the impostoEmpresaModelos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ImpostoEmpresaModelo> findAllWithEagerRelationships(Pageable pageable) {
        return impostoEmpresaModeloRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one impostoEmpresaModelo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ImpostoEmpresaModelo> findOne(Long id) {
        log.debug("Request to get ImpostoEmpresaModelo : {}", id);
        return impostoEmpresaModeloRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the impostoEmpresaModelo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ImpostoEmpresaModelo : {}", id);
        impostoEmpresaModeloRepository.deleteById(id);
    }
}
