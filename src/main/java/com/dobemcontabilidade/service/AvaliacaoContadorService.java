package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AvaliacaoContador;
import com.dobemcontabilidade.repository.AvaliacaoContadorRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AvaliacaoContador}.
 */
@Service
@Transactional
public class AvaliacaoContadorService {

    private static final Logger log = LoggerFactory.getLogger(AvaliacaoContadorService.class);

    private final AvaliacaoContadorRepository avaliacaoContadorRepository;

    public AvaliacaoContadorService(AvaliacaoContadorRepository avaliacaoContadorRepository) {
        this.avaliacaoContadorRepository = avaliacaoContadorRepository;
    }

    /**
     * Save a avaliacaoContador.
     *
     * @param avaliacaoContador the entity to save.
     * @return the persisted entity.
     */
    public AvaliacaoContador save(AvaliacaoContador avaliacaoContador) {
        log.debug("Request to save AvaliacaoContador : {}", avaliacaoContador);
        return avaliacaoContadorRepository.save(avaliacaoContador);
    }

    /**
     * Update a avaliacaoContador.
     *
     * @param avaliacaoContador the entity to save.
     * @return the persisted entity.
     */
    public AvaliacaoContador update(AvaliacaoContador avaliacaoContador) {
        log.debug("Request to update AvaliacaoContador : {}", avaliacaoContador);
        return avaliacaoContadorRepository.save(avaliacaoContador);
    }

    /**
     * Partially update a avaliacaoContador.
     *
     * @param avaliacaoContador the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AvaliacaoContador> partialUpdate(AvaliacaoContador avaliacaoContador) {
        log.debug("Request to partially update AvaliacaoContador : {}", avaliacaoContador);

        return avaliacaoContadorRepository
            .findById(avaliacaoContador.getId())
            .map(existingAvaliacaoContador -> {
                if (avaliacaoContador.getPontuacao() != null) {
                    existingAvaliacaoContador.setPontuacao(avaliacaoContador.getPontuacao());
                }

                return existingAvaliacaoContador;
            })
            .map(avaliacaoContadorRepository::save);
    }

    /**
     * Get all the avaliacaoContadors.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AvaliacaoContador> findAll() {
        log.debug("Request to get all AvaliacaoContadors");
        return avaliacaoContadorRepository.findAll();
    }

    /**
     * Get all the avaliacaoContadors with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AvaliacaoContador> findAllWithEagerRelationships(Pageable pageable) {
        return avaliacaoContadorRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one avaliacaoContador by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AvaliacaoContador> findOne(Long id) {
        log.debug("Request to get AvaliacaoContador : {}", id);
        return avaliacaoContadorRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the avaliacaoContador by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AvaliacaoContador : {}", id);
        avaliacaoContadorRepository.deleteById(id);
    }
}
