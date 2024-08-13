package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AreaContabilContador;
import com.dobemcontabilidade.repository.AreaContabilContadorRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AreaContabilContador}.
 */
@Service
@Transactional
public class AreaContabilContadorService {

    private static final Logger log = LoggerFactory.getLogger(AreaContabilContadorService.class);

    private final AreaContabilContadorRepository areaContabilContadorRepository;

    public AreaContabilContadorService(AreaContabilContadorRepository areaContabilContadorRepository) {
        this.areaContabilContadorRepository = areaContabilContadorRepository;
    }

    /**
     * Save a areaContabilContador.
     *
     * @param areaContabilContador the entity to save.
     * @return the persisted entity.
     */
    public AreaContabilContador save(AreaContabilContador areaContabilContador) {
        log.debug("Request to save AreaContabilContador : {}", areaContabilContador);
        return areaContabilContadorRepository.save(areaContabilContador);
    }

    /**
     * Update a areaContabilContador.
     *
     * @param areaContabilContador the entity to save.
     * @return the persisted entity.
     */
    public AreaContabilContador update(AreaContabilContador areaContabilContador) {
        log.debug("Request to update AreaContabilContador : {}", areaContabilContador);
        return areaContabilContadorRepository.save(areaContabilContador);
    }

    /**
     * Partially update a areaContabilContador.
     *
     * @param areaContabilContador the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AreaContabilContador> partialUpdate(AreaContabilContador areaContabilContador) {
        log.debug("Request to partially update AreaContabilContador : {}", areaContabilContador);

        return areaContabilContadorRepository
            .findById(areaContabilContador.getId())
            .map(existingAreaContabilContador -> {
                if (areaContabilContador.getPercentualExperiencia() != null) {
                    existingAreaContabilContador.setPercentualExperiencia(areaContabilContador.getPercentualExperiencia());
                }
                if (areaContabilContador.getDescricaoExperiencia() != null) {
                    existingAreaContabilContador.setDescricaoExperiencia(areaContabilContador.getDescricaoExperiencia());
                }
                if (areaContabilContador.getAtivo() != null) {
                    existingAreaContabilContador.setAtivo(areaContabilContador.getAtivo());
                }

                return existingAreaContabilContador;
            })
            .map(areaContabilContadorRepository::save);
    }

    /**
     * Get all the areaContabilContadors.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AreaContabilContador> findAll() {
        log.debug("Request to get all AreaContabilContadors");
        return areaContabilContadorRepository.findAll();
    }

    /**
     * Get all the areaContabilContadors with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AreaContabilContador> findAllWithEagerRelationships(Pageable pageable) {
        return areaContabilContadorRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one areaContabilContador by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AreaContabilContador> findOne(Long id) {
        log.debug("Request to get AreaContabilContador : {}", id);
        return areaContabilContadorRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the areaContabilContador by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AreaContabilContador : {}", id);
        areaContabilContadorRepository.deleteById(id);
    }
}
