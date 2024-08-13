package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AreaContabil;
import com.dobemcontabilidade.repository.AreaContabilRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AreaContabil}.
 */
@Service
@Transactional
public class AreaContabilService {

    private static final Logger log = LoggerFactory.getLogger(AreaContabilService.class);

    private final AreaContabilRepository areaContabilRepository;

    public AreaContabilService(AreaContabilRepository areaContabilRepository) {
        this.areaContabilRepository = areaContabilRepository;
    }

    /**
     * Save a areaContabil.
     *
     * @param areaContabil the entity to save.
     * @return the persisted entity.
     */
    public AreaContabil save(AreaContabil areaContabil) {
        log.debug("Request to save AreaContabil : {}", areaContabil);
        return areaContabilRepository.save(areaContabil);
    }

    /**
     * Update a areaContabil.
     *
     * @param areaContabil the entity to save.
     * @return the persisted entity.
     */
    public AreaContabil update(AreaContabil areaContabil) {
        log.debug("Request to update AreaContabil : {}", areaContabil);
        return areaContabilRepository.save(areaContabil);
    }

    /**
     * Partially update a areaContabil.
     *
     * @param areaContabil the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AreaContabil> partialUpdate(AreaContabil areaContabil) {
        log.debug("Request to partially update AreaContabil : {}", areaContabil);

        return areaContabilRepository
            .findById(areaContabil.getId())
            .map(existingAreaContabil -> {
                if (areaContabil.getNome() != null) {
                    existingAreaContabil.setNome(areaContabil.getNome());
                }
                if (areaContabil.getDescricao() != null) {
                    existingAreaContabil.setDescricao(areaContabil.getDescricao());
                }
                if (areaContabil.getPercentual() != null) {
                    existingAreaContabil.setPercentual(areaContabil.getPercentual());
                }

                return existingAreaContabil;
            })
            .map(areaContabilRepository::save);
    }

    /**
     * Get all the areaContabils.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AreaContabil> findAll() {
        log.debug("Request to get all AreaContabils");
        return areaContabilRepository.findAll();
    }

    /**
     * Get one areaContabil by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AreaContabil> findOne(Long id) {
        log.debug("Request to get AreaContabil : {}", id);
        return areaContabilRepository.findById(id);
    }

    /**
     * Delete the areaContabil by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AreaContabil : {}", id);
        areaContabilRepository.deleteById(id);
    }
}
