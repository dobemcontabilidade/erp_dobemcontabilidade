package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.OpcaoNomeFantasiaEmpresa;
import com.dobemcontabilidade.repository.OpcaoNomeFantasiaEmpresaRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.OpcaoNomeFantasiaEmpresa}.
 */
@Service
@Transactional
public class OpcaoNomeFantasiaEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(OpcaoNomeFantasiaEmpresaService.class);

    private final OpcaoNomeFantasiaEmpresaRepository opcaoNomeFantasiaEmpresaRepository;

    public OpcaoNomeFantasiaEmpresaService(OpcaoNomeFantasiaEmpresaRepository opcaoNomeFantasiaEmpresaRepository) {
        this.opcaoNomeFantasiaEmpresaRepository = opcaoNomeFantasiaEmpresaRepository;
    }

    /**
     * Save a opcaoNomeFantasiaEmpresa.
     *
     * @param opcaoNomeFantasiaEmpresa the entity to save.
     * @return the persisted entity.
     */
    public OpcaoNomeFantasiaEmpresa save(OpcaoNomeFantasiaEmpresa opcaoNomeFantasiaEmpresa) {
        log.debug("Request to save OpcaoNomeFantasiaEmpresa : {}", opcaoNomeFantasiaEmpresa);
        return opcaoNomeFantasiaEmpresaRepository.save(opcaoNomeFantasiaEmpresa);
    }

    /**
     * Update a opcaoNomeFantasiaEmpresa.
     *
     * @param opcaoNomeFantasiaEmpresa the entity to save.
     * @return the persisted entity.
     */
    public OpcaoNomeFantasiaEmpresa update(OpcaoNomeFantasiaEmpresa opcaoNomeFantasiaEmpresa) {
        log.debug("Request to update OpcaoNomeFantasiaEmpresa : {}", opcaoNomeFantasiaEmpresa);
        return opcaoNomeFantasiaEmpresaRepository.save(opcaoNomeFantasiaEmpresa);
    }

    /**
     * Partially update a opcaoNomeFantasiaEmpresa.
     *
     * @param opcaoNomeFantasiaEmpresa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OpcaoNomeFantasiaEmpresa> partialUpdate(OpcaoNomeFantasiaEmpresa opcaoNomeFantasiaEmpresa) {
        log.debug("Request to partially update OpcaoNomeFantasiaEmpresa : {}", opcaoNomeFantasiaEmpresa);

        return opcaoNomeFantasiaEmpresaRepository
            .findById(opcaoNomeFantasiaEmpresa.getId())
            .map(existingOpcaoNomeFantasiaEmpresa -> {
                if (opcaoNomeFantasiaEmpresa.getNome() != null) {
                    existingOpcaoNomeFantasiaEmpresa.setNome(opcaoNomeFantasiaEmpresa.getNome());
                }
                if (opcaoNomeFantasiaEmpresa.getOrdem() != null) {
                    existingOpcaoNomeFantasiaEmpresa.setOrdem(opcaoNomeFantasiaEmpresa.getOrdem());
                }
                if (opcaoNomeFantasiaEmpresa.getSelecionado() != null) {
                    existingOpcaoNomeFantasiaEmpresa.setSelecionado(opcaoNomeFantasiaEmpresa.getSelecionado());
                }

                return existingOpcaoNomeFantasiaEmpresa;
            })
            .map(opcaoNomeFantasiaEmpresaRepository::save);
    }

    /**
     * Get all the opcaoNomeFantasiaEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<OpcaoNomeFantasiaEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return opcaoNomeFantasiaEmpresaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one opcaoNomeFantasiaEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OpcaoNomeFantasiaEmpresa> findOne(Long id) {
        log.debug("Request to get OpcaoNomeFantasiaEmpresa : {}", id);
        return opcaoNomeFantasiaEmpresaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the opcaoNomeFantasiaEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OpcaoNomeFantasiaEmpresa : {}", id);
        opcaoNomeFantasiaEmpresaRepository.deleteById(id);
    }
}
