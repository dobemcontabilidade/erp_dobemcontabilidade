package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.FeedBackContadorParaUsuario;
import com.dobemcontabilidade.repository.FeedBackContadorParaUsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.FeedBackContadorParaUsuario}.
 */
@Service
@Transactional
public class FeedBackContadorParaUsuarioService {

    private static final Logger log = LoggerFactory.getLogger(FeedBackContadorParaUsuarioService.class);

    private final FeedBackContadorParaUsuarioRepository feedBackContadorParaUsuarioRepository;

    public FeedBackContadorParaUsuarioService(FeedBackContadorParaUsuarioRepository feedBackContadorParaUsuarioRepository) {
        this.feedBackContadorParaUsuarioRepository = feedBackContadorParaUsuarioRepository;
    }

    /**
     * Save a feedBackContadorParaUsuario.
     *
     * @param feedBackContadorParaUsuario the entity to save.
     * @return the persisted entity.
     */
    public FeedBackContadorParaUsuario save(FeedBackContadorParaUsuario feedBackContadorParaUsuario) {
        log.debug("Request to save FeedBackContadorParaUsuario : {}", feedBackContadorParaUsuario);
        return feedBackContadorParaUsuarioRepository.save(feedBackContadorParaUsuario);
    }

    /**
     * Update a feedBackContadorParaUsuario.
     *
     * @param feedBackContadorParaUsuario the entity to save.
     * @return the persisted entity.
     */
    public FeedBackContadorParaUsuario update(FeedBackContadorParaUsuario feedBackContadorParaUsuario) {
        log.debug("Request to update FeedBackContadorParaUsuario : {}", feedBackContadorParaUsuario);
        return feedBackContadorParaUsuarioRepository.save(feedBackContadorParaUsuario);
    }

    /**
     * Partially update a feedBackContadorParaUsuario.
     *
     * @param feedBackContadorParaUsuario the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FeedBackContadorParaUsuario> partialUpdate(FeedBackContadorParaUsuario feedBackContadorParaUsuario) {
        log.debug("Request to partially update FeedBackContadorParaUsuario : {}", feedBackContadorParaUsuario);

        return feedBackContadorParaUsuarioRepository
            .findById(feedBackContadorParaUsuario.getId())
            .map(existingFeedBackContadorParaUsuario -> {
                if (feedBackContadorParaUsuario.getComentario() != null) {
                    existingFeedBackContadorParaUsuario.setComentario(feedBackContadorParaUsuario.getComentario());
                }
                if (feedBackContadorParaUsuario.getPontuacao() != null) {
                    existingFeedBackContadorParaUsuario.setPontuacao(feedBackContadorParaUsuario.getPontuacao());
                }

                return existingFeedBackContadorParaUsuario;
            })
            .map(feedBackContadorParaUsuarioRepository::save);
    }

    /**
     * Get all the feedBackContadorParaUsuarios.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FeedBackContadorParaUsuario> findAll() {
        log.debug("Request to get all FeedBackContadorParaUsuarios");
        return feedBackContadorParaUsuarioRepository.findAll();
    }

    /**
     * Get all the feedBackContadorParaUsuarios with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<FeedBackContadorParaUsuario> findAllWithEagerRelationships(Pageable pageable) {
        return feedBackContadorParaUsuarioRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one feedBackContadorParaUsuario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FeedBackContadorParaUsuario> findOne(Long id) {
        log.debug("Request to get FeedBackContadorParaUsuario : {}", id);
        return feedBackContadorParaUsuarioRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the feedBackContadorParaUsuario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FeedBackContadorParaUsuario : {}", id);
        feedBackContadorParaUsuarioRepository.deleteById(id);
    }
}
