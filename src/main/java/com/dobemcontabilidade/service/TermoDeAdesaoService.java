package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.TermoDeAdesao;
import com.dobemcontabilidade.repository.TermoDeAdesaoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.TermoDeAdesao}.
 */
@Service
@Transactional
public class TermoDeAdesaoService {

    private static final Logger log = LoggerFactory.getLogger(TermoDeAdesaoService.class);

    private final TermoDeAdesaoRepository termoDeAdesaoRepository;

    public TermoDeAdesaoService(TermoDeAdesaoRepository termoDeAdesaoRepository) {
        this.termoDeAdesaoRepository = termoDeAdesaoRepository;
    }

    /**
     * Save a termoDeAdesao.
     *
     * @param termoDeAdesao the entity to save.
     * @return the persisted entity.
     */
    public TermoDeAdesao save(TermoDeAdesao termoDeAdesao) {
        log.debug("Request to save TermoDeAdesao : {}", termoDeAdesao);
        return termoDeAdesaoRepository.save(termoDeAdesao);
    }

    /**
     * Update a termoDeAdesao.
     *
     * @param termoDeAdesao the entity to save.
     * @return the persisted entity.
     */
    public TermoDeAdesao update(TermoDeAdesao termoDeAdesao) {
        log.debug("Request to update TermoDeAdesao : {}", termoDeAdesao);
        return termoDeAdesaoRepository.save(termoDeAdesao);
    }

    /**
     * Partially update a termoDeAdesao.
     *
     * @param termoDeAdesao the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TermoDeAdesao> partialUpdate(TermoDeAdesao termoDeAdesao) {
        log.debug("Request to partially update TermoDeAdesao : {}", termoDeAdesao);

        return termoDeAdesaoRepository
            .findById(termoDeAdesao.getId())
            .map(existingTermoDeAdesao -> {
                if (termoDeAdesao.getTitulo() != null) {
                    existingTermoDeAdesao.setTitulo(termoDeAdesao.getTitulo());
                }
                if (termoDeAdesao.getDescricao() != null) {
                    existingTermoDeAdesao.setDescricao(termoDeAdesao.getDescricao());
                }

                return existingTermoDeAdesao;
            })
            .map(termoDeAdesaoRepository::save);
    }

    /**
     * Get one termoDeAdesao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TermoDeAdesao> findOne(Long id) {
        log.debug("Request to get TermoDeAdesao : {}", id);
        return termoDeAdesaoRepository.findById(id);
    }

    /**
     * Delete the termoDeAdesao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TermoDeAdesao : {}", id);
        termoDeAdesaoRepository.deleteById(id);
    }
}
