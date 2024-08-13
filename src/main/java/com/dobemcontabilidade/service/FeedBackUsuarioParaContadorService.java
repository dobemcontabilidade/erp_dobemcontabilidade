package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.FeedBackUsuarioParaContador;
import com.dobemcontabilidade.repository.FeedBackUsuarioParaContadorRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.FeedBackUsuarioParaContador}.
 */
@Service
@Transactional
public class FeedBackUsuarioParaContadorService {

    private static final Logger log = LoggerFactory.getLogger(FeedBackUsuarioParaContadorService.class);

    private final FeedBackUsuarioParaContadorRepository feedBackUsuarioParaContadorRepository;

    public FeedBackUsuarioParaContadorService(FeedBackUsuarioParaContadorRepository feedBackUsuarioParaContadorRepository) {
        this.feedBackUsuarioParaContadorRepository = feedBackUsuarioParaContadorRepository;
    }

    /**
     * Save a feedBackUsuarioParaContador.
     *
     * @param feedBackUsuarioParaContador the entity to save.
     * @return the persisted entity.
     */
    public FeedBackUsuarioParaContador save(FeedBackUsuarioParaContador feedBackUsuarioParaContador) {
        log.debug("Request to save FeedBackUsuarioParaContador : {}", feedBackUsuarioParaContador);
        return feedBackUsuarioParaContadorRepository.save(feedBackUsuarioParaContador);
    }

    /**
     * Update a feedBackUsuarioParaContador.
     *
     * @param feedBackUsuarioParaContador the entity to save.
     * @return the persisted entity.
     */
    public FeedBackUsuarioParaContador update(FeedBackUsuarioParaContador feedBackUsuarioParaContador) {
        log.debug("Request to update FeedBackUsuarioParaContador : {}", feedBackUsuarioParaContador);
        return feedBackUsuarioParaContadorRepository.save(feedBackUsuarioParaContador);
    }

    /**
     * Partially update a feedBackUsuarioParaContador.
     *
     * @param feedBackUsuarioParaContador the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FeedBackUsuarioParaContador> partialUpdate(FeedBackUsuarioParaContador feedBackUsuarioParaContador) {
        log.debug("Request to partially update FeedBackUsuarioParaContador : {}", feedBackUsuarioParaContador);

        return feedBackUsuarioParaContadorRepository
            .findById(feedBackUsuarioParaContador.getId())
            .map(existingFeedBackUsuarioParaContador -> {
                if (feedBackUsuarioParaContador.getComentario() != null) {
                    existingFeedBackUsuarioParaContador.setComentario(feedBackUsuarioParaContador.getComentario());
                }
                if (feedBackUsuarioParaContador.getPontuacao() != null) {
                    existingFeedBackUsuarioParaContador.setPontuacao(feedBackUsuarioParaContador.getPontuacao());
                }

                return existingFeedBackUsuarioParaContador;
            })
            .map(feedBackUsuarioParaContadorRepository::save);
    }

    /**
     * Get all the feedBackUsuarioParaContadors.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FeedBackUsuarioParaContador> findAll() {
        log.debug("Request to get all FeedBackUsuarioParaContadors");
        return feedBackUsuarioParaContadorRepository.findAll();
    }

    /**
     * Get all the feedBackUsuarioParaContadors with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<FeedBackUsuarioParaContador> findAllWithEagerRelationships(Pageable pageable) {
        return feedBackUsuarioParaContadorRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one feedBackUsuarioParaContador by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FeedBackUsuarioParaContador> findOne(Long id) {
        log.debug("Request to get FeedBackUsuarioParaContador : {}", id);
        return feedBackUsuarioParaContadorRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the feedBackUsuarioParaContador by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FeedBackUsuarioParaContador : {}", id);
        feedBackUsuarioParaContadorRepository.deleteById(id);
    }
}
