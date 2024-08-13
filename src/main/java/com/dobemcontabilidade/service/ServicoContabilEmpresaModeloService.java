package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.ServicoContabilEmpresaModelo;
import com.dobemcontabilidade.repository.ServicoContabilEmpresaModeloRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.ServicoContabilEmpresaModelo}.
 */
@Service
@Transactional
public class ServicoContabilEmpresaModeloService {

    private static final Logger log = LoggerFactory.getLogger(ServicoContabilEmpresaModeloService.class);

    private final ServicoContabilEmpresaModeloRepository servicoContabilEmpresaModeloRepository;

    public ServicoContabilEmpresaModeloService(ServicoContabilEmpresaModeloRepository servicoContabilEmpresaModeloRepository) {
        this.servicoContabilEmpresaModeloRepository = servicoContabilEmpresaModeloRepository;
    }

    /**
     * Save a servicoContabilEmpresaModelo.
     *
     * @param servicoContabilEmpresaModelo the entity to save.
     * @return the persisted entity.
     */
    public ServicoContabilEmpresaModelo save(ServicoContabilEmpresaModelo servicoContabilEmpresaModelo) {
        log.debug("Request to save ServicoContabilEmpresaModelo : {}", servicoContabilEmpresaModelo);
        return servicoContabilEmpresaModeloRepository.save(servicoContabilEmpresaModelo);
    }

    /**
     * Update a servicoContabilEmpresaModelo.
     *
     * @param servicoContabilEmpresaModelo the entity to save.
     * @return the persisted entity.
     */
    public ServicoContabilEmpresaModelo update(ServicoContabilEmpresaModelo servicoContabilEmpresaModelo) {
        log.debug("Request to update ServicoContabilEmpresaModelo : {}", servicoContabilEmpresaModelo);
        return servicoContabilEmpresaModeloRepository.save(servicoContabilEmpresaModelo);
    }

    /**
     * Partially update a servicoContabilEmpresaModelo.
     *
     * @param servicoContabilEmpresaModelo the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ServicoContabilEmpresaModelo> partialUpdate(ServicoContabilEmpresaModelo servicoContabilEmpresaModelo) {
        log.debug("Request to partially update ServicoContabilEmpresaModelo : {}", servicoContabilEmpresaModelo);

        return servicoContabilEmpresaModeloRepository
            .findById(servicoContabilEmpresaModelo.getId())
            .map(existingServicoContabilEmpresaModelo -> {
                if (servicoContabilEmpresaModelo.getObrigatorio() != null) {
                    existingServicoContabilEmpresaModelo.setObrigatorio(servicoContabilEmpresaModelo.getObrigatorio());
                }

                return existingServicoContabilEmpresaModelo;
            })
            .map(servicoContabilEmpresaModeloRepository::save);
    }

    /**
     * Get all the servicoContabilEmpresaModelos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ServicoContabilEmpresaModelo> findAll() {
        log.debug("Request to get all ServicoContabilEmpresaModelos");
        return servicoContabilEmpresaModeloRepository.findAll();
    }

    /**
     * Get all the servicoContabilEmpresaModelos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ServicoContabilEmpresaModelo> findAllWithEagerRelationships(Pageable pageable) {
        return servicoContabilEmpresaModeloRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one servicoContabilEmpresaModelo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServicoContabilEmpresaModelo> findOne(Long id) {
        log.debug("Request to get ServicoContabilEmpresaModelo : {}", id);
        return servicoContabilEmpresaModeloRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the servicoContabilEmpresaModelo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServicoContabilEmpresaModelo : {}", id);
        servicoContabilEmpresaModeloRepository.deleteById(id);
    }
}
