package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.TermoAdesaoContador;
import com.dobemcontabilidade.repository.TermoAdesaoContadorRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.TermoAdesaoContador}.
 */
@Service
@Transactional
public class TermoAdesaoContadorService {

    private static final Logger log = LoggerFactory.getLogger(TermoAdesaoContadorService.class);

    private final TermoAdesaoContadorRepository termoAdesaoContadorRepository;

    public TermoAdesaoContadorService(TermoAdesaoContadorRepository termoAdesaoContadorRepository) {
        this.termoAdesaoContadorRepository = termoAdesaoContadorRepository;
    }

    /**
     * Save a termoAdesaoContador.
     *
     * @param termoAdesaoContador the entity to save.
     * @return the persisted entity.
     */
    public TermoAdesaoContador save(TermoAdesaoContador termoAdesaoContador) {
        log.debug("Request to save TermoAdesaoContador : {}", termoAdesaoContador);
        return termoAdesaoContadorRepository.save(termoAdesaoContador);
    }

    /**
     * Update a termoAdesaoContador.
     *
     * @param termoAdesaoContador the entity to save.
     * @return the persisted entity.
     */
    public TermoAdesaoContador update(TermoAdesaoContador termoAdesaoContador) {
        log.debug("Request to update TermoAdesaoContador : {}", termoAdesaoContador);
        return termoAdesaoContadorRepository.save(termoAdesaoContador);
    }

    /**
     * Partially update a termoAdesaoContador.
     *
     * @param termoAdesaoContador the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TermoAdesaoContador> partialUpdate(TermoAdesaoContador termoAdesaoContador) {
        log.debug("Request to partially update TermoAdesaoContador : {}", termoAdesaoContador);

        return termoAdesaoContadorRepository
            .findById(termoAdesaoContador.getId())
            .map(existingTermoAdesaoContador -> {
                if (termoAdesaoContador.getDataAdesao() != null) {
                    existingTermoAdesaoContador.setDataAdesao(termoAdesaoContador.getDataAdesao());
                }
                if (termoAdesaoContador.getChecked() != null) {
                    existingTermoAdesaoContador.setChecked(termoAdesaoContador.getChecked());
                }
                if (termoAdesaoContador.getUrlDoc() != null) {
                    existingTermoAdesaoContador.setUrlDoc(termoAdesaoContador.getUrlDoc());
                }

                return existingTermoAdesaoContador;
            })
            .map(termoAdesaoContadorRepository::save);
    }

    /**
     * Get all the termoAdesaoContadors with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TermoAdesaoContador> findAllWithEagerRelationships(Pageable pageable) {
        return termoAdesaoContadorRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one termoAdesaoContador by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TermoAdesaoContador> findOne(Long id) {
        log.debug("Request to get TermoAdesaoContador : {}", id);
        return termoAdesaoContadorRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the termoAdesaoContador by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TermoAdesaoContador : {}", id);
        termoAdesaoContadorRepository.deleteById(id);
    }
}
