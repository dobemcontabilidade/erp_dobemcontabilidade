package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.OpcaoRazaoSocialEmpresa;
import com.dobemcontabilidade.repository.OpcaoRazaoSocialEmpresaRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.OpcaoRazaoSocialEmpresa}.
 */
@Service
@Transactional
public class OpcaoRazaoSocialEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(OpcaoRazaoSocialEmpresaService.class);

    private final OpcaoRazaoSocialEmpresaRepository opcaoRazaoSocialEmpresaRepository;

    public OpcaoRazaoSocialEmpresaService(OpcaoRazaoSocialEmpresaRepository opcaoRazaoSocialEmpresaRepository) {
        this.opcaoRazaoSocialEmpresaRepository = opcaoRazaoSocialEmpresaRepository;
    }

    /**
     * Save a opcaoRazaoSocialEmpresa.
     *
     * @param opcaoRazaoSocialEmpresa the entity to save.
     * @return the persisted entity.
     */
    public OpcaoRazaoSocialEmpresa save(OpcaoRazaoSocialEmpresa opcaoRazaoSocialEmpresa) {
        log.debug("Request to save OpcaoRazaoSocialEmpresa : {}", opcaoRazaoSocialEmpresa);
        return opcaoRazaoSocialEmpresaRepository.save(opcaoRazaoSocialEmpresa);
    }

    /**
     * Update a opcaoRazaoSocialEmpresa.
     *
     * @param opcaoRazaoSocialEmpresa the entity to save.
     * @return the persisted entity.
     */
    public OpcaoRazaoSocialEmpresa update(OpcaoRazaoSocialEmpresa opcaoRazaoSocialEmpresa) {
        log.debug("Request to update OpcaoRazaoSocialEmpresa : {}", opcaoRazaoSocialEmpresa);
        return opcaoRazaoSocialEmpresaRepository.save(opcaoRazaoSocialEmpresa);
    }

    /**
     * Partially update a opcaoRazaoSocialEmpresa.
     *
     * @param opcaoRazaoSocialEmpresa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OpcaoRazaoSocialEmpresa> partialUpdate(OpcaoRazaoSocialEmpresa opcaoRazaoSocialEmpresa) {
        log.debug("Request to partially update OpcaoRazaoSocialEmpresa : {}", opcaoRazaoSocialEmpresa);

        return opcaoRazaoSocialEmpresaRepository
            .findById(opcaoRazaoSocialEmpresa.getId())
            .map(existingOpcaoRazaoSocialEmpresa -> {
                if (opcaoRazaoSocialEmpresa.getNome() != null) {
                    existingOpcaoRazaoSocialEmpresa.setNome(opcaoRazaoSocialEmpresa.getNome());
                }
                if (opcaoRazaoSocialEmpresa.getOrdem() != null) {
                    existingOpcaoRazaoSocialEmpresa.setOrdem(opcaoRazaoSocialEmpresa.getOrdem());
                }
                if (opcaoRazaoSocialEmpresa.getSelecionado() != null) {
                    existingOpcaoRazaoSocialEmpresa.setSelecionado(opcaoRazaoSocialEmpresa.getSelecionado());
                }

                return existingOpcaoRazaoSocialEmpresa;
            })
            .map(opcaoRazaoSocialEmpresaRepository::save);
    }

    /**
     * Get all the opcaoRazaoSocialEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<OpcaoRazaoSocialEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return opcaoRazaoSocialEmpresaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one opcaoRazaoSocialEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OpcaoRazaoSocialEmpresa> findOne(Long id) {
        log.debug("Request to get OpcaoRazaoSocialEmpresa : {}", id);
        return opcaoRazaoSocialEmpresaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the opcaoRazaoSocialEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OpcaoRazaoSocialEmpresa : {}", id);
        opcaoRazaoSocialEmpresaRepository.deleteById(id);
    }
}
