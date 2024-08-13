package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AtividadeEmpresa;
import com.dobemcontabilidade.repository.AtividadeEmpresaRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AtividadeEmpresa}.
 */
@Service
@Transactional
public class AtividadeEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(AtividadeEmpresaService.class);

    private final AtividadeEmpresaRepository atividadeEmpresaRepository;

    public AtividadeEmpresaService(AtividadeEmpresaRepository atividadeEmpresaRepository) {
        this.atividadeEmpresaRepository = atividadeEmpresaRepository;
    }

    /**
     * Save a atividadeEmpresa.
     *
     * @param atividadeEmpresa the entity to save.
     * @return the persisted entity.
     */
    public AtividadeEmpresa save(AtividadeEmpresa atividadeEmpresa) {
        log.debug("Request to save AtividadeEmpresa : {}", atividadeEmpresa);
        return atividadeEmpresaRepository.save(atividadeEmpresa);
    }

    /**
     * Update a atividadeEmpresa.
     *
     * @param atividadeEmpresa the entity to save.
     * @return the persisted entity.
     */
    public AtividadeEmpresa update(AtividadeEmpresa atividadeEmpresa) {
        log.debug("Request to update AtividadeEmpresa : {}", atividadeEmpresa);
        return atividadeEmpresaRepository.save(atividadeEmpresa);
    }

    /**
     * Partially update a atividadeEmpresa.
     *
     * @param atividadeEmpresa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AtividadeEmpresa> partialUpdate(AtividadeEmpresa atividadeEmpresa) {
        log.debug("Request to partially update AtividadeEmpresa : {}", atividadeEmpresa);

        return atividadeEmpresaRepository
            .findById(atividadeEmpresa.getId())
            .map(existingAtividadeEmpresa -> {
                if (atividadeEmpresa.getPrincipal() != null) {
                    existingAtividadeEmpresa.setPrincipal(atividadeEmpresa.getPrincipal());
                }
                if (atividadeEmpresa.getOrdem() != null) {
                    existingAtividadeEmpresa.setOrdem(atividadeEmpresa.getOrdem());
                }
                if (atividadeEmpresa.getDescricaoAtividade() != null) {
                    existingAtividadeEmpresa.setDescricaoAtividade(atividadeEmpresa.getDescricaoAtividade());
                }

                return existingAtividadeEmpresa;
            })
            .map(atividadeEmpresaRepository::save);
    }

    /**
     * Get all the atividadeEmpresas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AtividadeEmpresa> findAll() {
        log.debug("Request to get all AtividadeEmpresas");
        return atividadeEmpresaRepository.findAll();
    }

    /**
     * Get all the atividadeEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AtividadeEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return atividadeEmpresaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one atividadeEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AtividadeEmpresa> findOne(Long id) {
        log.debug("Request to get AtividadeEmpresa : {}", id);
        return atividadeEmpresaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the atividadeEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AtividadeEmpresa : {}", id);
        atividadeEmpresaRepository.deleteById(id);
    }
}
