package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Telefone;
import com.dobemcontabilidade.repository.TelefoneRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Telefone}.
 */
@Service
@Transactional
public class TelefoneService {

    private static final Logger log = LoggerFactory.getLogger(TelefoneService.class);

    private final TelefoneRepository telefoneRepository;

    public TelefoneService(TelefoneRepository telefoneRepository) {
        this.telefoneRepository = telefoneRepository;
    }

    /**
     * Save a telefone.
     *
     * @param telefone the entity to save.
     * @return the persisted entity.
     */
    public Telefone save(Telefone telefone) {
        log.debug("Request to save Telefone : {}", telefone);
        return telefoneRepository.save(telefone);
    }

    /**
     * Update a telefone.
     *
     * @param telefone the entity to save.
     * @return the persisted entity.
     */
    public Telefone update(Telefone telefone) {
        log.debug("Request to update Telefone : {}", telefone);
        return telefoneRepository.save(telefone);
    }

    /**
     * Partially update a telefone.
     *
     * @param telefone the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Telefone> partialUpdate(Telefone telefone) {
        log.debug("Request to partially update Telefone : {}", telefone);

        return telefoneRepository
            .findById(telefone.getId())
            .map(existingTelefone -> {
                if (telefone.getCodigoArea() != null) {
                    existingTelefone.setCodigoArea(telefone.getCodigoArea());
                }
                if (telefone.getTelefone() != null) {
                    existingTelefone.setTelefone(telefone.getTelefone());
                }
                if (telefone.getPrincipal() != null) {
                    existingTelefone.setPrincipal(telefone.getPrincipal());
                }
                if (telefone.getTipoTelefone() != null) {
                    existingTelefone.setTipoTelefone(telefone.getTipoTelefone());
                }

                return existingTelefone;
            })
            .map(telefoneRepository::save);
    }

    /**
     * Get all the telefones with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Telefone> findAllWithEagerRelationships(Pageable pageable) {
        return telefoneRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one telefone by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Telefone> findOne(Long id) {
        log.debug("Request to get Telefone : {}", id);
        return telefoneRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the telefone by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Telefone : {}", id);
        telefoneRepository.deleteById(id);
    }
}
