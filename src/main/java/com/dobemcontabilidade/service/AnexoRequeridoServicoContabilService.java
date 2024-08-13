package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AnexoRequeridoServicoContabil;
import com.dobemcontabilidade.repository.AnexoRequeridoServicoContabilRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AnexoRequeridoServicoContabil}.
 */
@Service
@Transactional
public class AnexoRequeridoServicoContabilService {

    private static final Logger log = LoggerFactory.getLogger(AnexoRequeridoServicoContabilService.class);

    private final AnexoRequeridoServicoContabilRepository anexoRequeridoServicoContabilRepository;

    public AnexoRequeridoServicoContabilService(AnexoRequeridoServicoContabilRepository anexoRequeridoServicoContabilRepository) {
        this.anexoRequeridoServicoContabilRepository = anexoRequeridoServicoContabilRepository;
    }

    /**
     * Save a anexoRequeridoServicoContabil.
     *
     * @param anexoRequeridoServicoContabil the entity to save.
     * @return the persisted entity.
     */
    public AnexoRequeridoServicoContabil save(AnexoRequeridoServicoContabil anexoRequeridoServicoContabil) {
        log.debug("Request to save AnexoRequeridoServicoContabil : {}", anexoRequeridoServicoContabil);
        return anexoRequeridoServicoContabilRepository.save(anexoRequeridoServicoContabil);
    }

    /**
     * Update a anexoRequeridoServicoContabil.
     *
     * @param anexoRequeridoServicoContabil the entity to save.
     * @return the persisted entity.
     */
    public AnexoRequeridoServicoContabil update(AnexoRequeridoServicoContabil anexoRequeridoServicoContabil) {
        log.debug("Request to update AnexoRequeridoServicoContabil : {}", anexoRequeridoServicoContabil);
        return anexoRequeridoServicoContabilRepository.save(anexoRequeridoServicoContabil);
    }

    /**
     * Partially update a anexoRequeridoServicoContabil.
     *
     * @param anexoRequeridoServicoContabil the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AnexoRequeridoServicoContabil> partialUpdate(AnexoRequeridoServicoContabil anexoRequeridoServicoContabil) {
        log.debug("Request to partially update AnexoRequeridoServicoContabil : {}", anexoRequeridoServicoContabil);

        return anexoRequeridoServicoContabilRepository
            .findById(anexoRequeridoServicoContabil.getId())
            .map(existingAnexoRequeridoServicoContabil -> {
                if (anexoRequeridoServicoContabil.getObrigatorio() != null) {
                    existingAnexoRequeridoServicoContabil.setObrigatorio(anexoRequeridoServicoContabil.getObrigatorio());
                }

                return existingAnexoRequeridoServicoContabil;
            })
            .map(anexoRequeridoServicoContabilRepository::save);
    }

    /**
     * Get all the anexoRequeridoServicoContabils.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AnexoRequeridoServicoContabil> findAll() {
        log.debug("Request to get all AnexoRequeridoServicoContabils");
        return anexoRequeridoServicoContabilRepository.findAll();
    }

    /**
     * Get all the anexoRequeridoServicoContabils with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AnexoRequeridoServicoContabil> findAllWithEagerRelationships(Pageable pageable) {
        return anexoRequeridoServicoContabilRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one anexoRequeridoServicoContabil by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnexoRequeridoServicoContabil> findOne(Long id) {
        log.debug("Request to get AnexoRequeridoServicoContabil : {}", id);
        return anexoRequeridoServicoContabilRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the anexoRequeridoServicoContabil by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnexoRequeridoServicoContabil : {}", id);
        anexoRequeridoServicoContabilRepository.deleteById(id);
    }
}
