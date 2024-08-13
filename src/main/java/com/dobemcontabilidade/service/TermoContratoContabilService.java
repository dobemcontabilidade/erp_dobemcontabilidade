package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.TermoContratoContabil;
import com.dobemcontabilidade.repository.TermoContratoContabilRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.TermoContratoContabil}.
 */
@Service
@Transactional
public class TermoContratoContabilService {

    private static final Logger log = LoggerFactory.getLogger(TermoContratoContabilService.class);

    private final TermoContratoContabilRepository termoContratoContabilRepository;

    public TermoContratoContabilService(TermoContratoContabilRepository termoContratoContabilRepository) {
        this.termoContratoContabilRepository = termoContratoContabilRepository;
    }

    /**
     * Save a termoContratoContabil.
     *
     * @param termoContratoContabil the entity to save.
     * @return the persisted entity.
     */
    public TermoContratoContabil save(TermoContratoContabil termoContratoContabil) {
        log.debug("Request to save TermoContratoContabil : {}", termoContratoContabil);
        return termoContratoContabilRepository.save(termoContratoContabil);
    }

    /**
     * Update a termoContratoContabil.
     *
     * @param termoContratoContabil the entity to save.
     * @return the persisted entity.
     */
    public TermoContratoContabil update(TermoContratoContabil termoContratoContabil) {
        log.debug("Request to update TermoContratoContabil : {}", termoContratoContabil);
        return termoContratoContabilRepository.save(termoContratoContabil);
    }

    /**
     * Partially update a termoContratoContabil.
     *
     * @param termoContratoContabil the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TermoContratoContabil> partialUpdate(TermoContratoContabil termoContratoContabil) {
        log.debug("Request to partially update TermoContratoContabil : {}", termoContratoContabil);

        return termoContratoContabilRepository
            .findById(termoContratoContabil.getId())
            .map(existingTermoContratoContabil -> {
                if (termoContratoContabil.getLinkTermo() != null) {
                    existingTermoContratoContabil.setLinkTermo(termoContratoContabil.getLinkTermo());
                }
                if (termoContratoContabil.getDescricao() != null) {
                    existingTermoContratoContabil.setDescricao(termoContratoContabil.getDescricao());
                }
                if (termoContratoContabil.getTitulo() != null) {
                    existingTermoContratoContabil.setTitulo(termoContratoContabil.getTitulo());
                }

                return existingTermoContratoContabil;
            })
            .map(termoContratoContabilRepository::save);
    }

    /**
     * Get all the termoContratoContabils.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TermoContratoContabil> findAll() {
        log.debug("Request to get all TermoContratoContabils");
        return termoContratoContabilRepository.findAll();
    }

    /**
     * Get all the termoContratoContabils with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TermoContratoContabil> findAllWithEagerRelationships(Pageable pageable) {
        return termoContratoContabilRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one termoContratoContabil by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TermoContratoContabil> findOne(Long id) {
        log.debug("Request to get TermoContratoContabil : {}", id);
        return termoContratoContabilRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the termoContratoContabil by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TermoContratoContabil : {}", id);
        termoContratoContabilRepository.deleteById(id);
    }
}
