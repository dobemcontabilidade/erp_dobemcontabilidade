package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.ServicoContabilAssinaturaEmpresa;
import com.dobemcontabilidade.repository.ServicoContabilAssinaturaEmpresaRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.ServicoContabilAssinaturaEmpresa}.
 */
@Service
@Transactional
public class ServicoContabilAssinaturaEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(ServicoContabilAssinaturaEmpresaService.class);

    private final ServicoContabilAssinaturaEmpresaRepository servicoContabilAssinaturaEmpresaRepository;

    public ServicoContabilAssinaturaEmpresaService(ServicoContabilAssinaturaEmpresaRepository servicoContabilAssinaturaEmpresaRepository) {
        this.servicoContabilAssinaturaEmpresaRepository = servicoContabilAssinaturaEmpresaRepository;
    }

    /**
     * Save a servicoContabilAssinaturaEmpresa.
     *
     * @param servicoContabilAssinaturaEmpresa the entity to save.
     * @return the persisted entity.
     */
    public ServicoContabilAssinaturaEmpresa save(ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa) {
        log.debug("Request to save ServicoContabilAssinaturaEmpresa : {}", servicoContabilAssinaturaEmpresa);
        return servicoContabilAssinaturaEmpresaRepository.save(servicoContabilAssinaturaEmpresa);
    }

    /**
     * Update a servicoContabilAssinaturaEmpresa.
     *
     * @param servicoContabilAssinaturaEmpresa the entity to save.
     * @return the persisted entity.
     */
    public ServicoContabilAssinaturaEmpresa update(ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa) {
        log.debug("Request to update ServicoContabilAssinaturaEmpresa : {}", servicoContabilAssinaturaEmpresa);
        return servicoContabilAssinaturaEmpresaRepository.save(servicoContabilAssinaturaEmpresa);
    }

    /**
     * Partially update a servicoContabilAssinaturaEmpresa.
     *
     * @param servicoContabilAssinaturaEmpresa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ServicoContabilAssinaturaEmpresa> partialUpdate(ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa) {
        log.debug("Request to partially update ServicoContabilAssinaturaEmpresa : {}", servicoContabilAssinaturaEmpresa);

        return servicoContabilAssinaturaEmpresaRepository
            .findById(servicoContabilAssinaturaEmpresa.getId())
            .map(existingServicoContabilAssinaturaEmpresa -> {
                if (servicoContabilAssinaturaEmpresa.getDataLegal() != null) {
                    existingServicoContabilAssinaturaEmpresa.setDataLegal(servicoContabilAssinaturaEmpresa.getDataLegal());
                }
                if (servicoContabilAssinaturaEmpresa.getDataAdmin() != null) {
                    existingServicoContabilAssinaturaEmpresa.setDataAdmin(servicoContabilAssinaturaEmpresa.getDataAdmin());
                }

                return existingServicoContabilAssinaturaEmpresa;
            })
            .map(servicoContabilAssinaturaEmpresaRepository::save);
    }

    /**
     * Get all the servicoContabilAssinaturaEmpresas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ServicoContabilAssinaturaEmpresa> findAll() {
        log.debug("Request to get all ServicoContabilAssinaturaEmpresas");
        return servicoContabilAssinaturaEmpresaRepository.findAll();
    }

    /**
     * Get all the servicoContabilAssinaturaEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ServicoContabilAssinaturaEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return servicoContabilAssinaturaEmpresaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one servicoContabilAssinaturaEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServicoContabilAssinaturaEmpresa> findOne(Long id) {
        log.debug("Request to get ServicoContabilAssinaturaEmpresa : {}", id);
        return servicoContabilAssinaturaEmpresaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the servicoContabilAssinaturaEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServicoContabilAssinaturaEmpresa : {}", id);
        servicoContabilAssinaturaEmpresaRepository.deleteById(id);
    }
}
