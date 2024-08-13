package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.FeedBackUsuarioParaContador;
import com.dobemcontabilidade.repository.FeedBackUsuarioParaContadorRepository;
import com.dobemcontabilidade.service.FeedBackUsuarioParaContadorService;
import com.dobemcontabilidade.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dobemcontabilidade.domain.FeedBackUsuarioParaContador}.
 */
@RestController
@RequestMapping("/api/feed-back-usuario-para-contadors")
public class FeedBackUsuarioParaContadorResource {

    private static final Logger log = LoggerFactory.getLogger(FeedBackUsuarioParaContadorResource.class);

    private static final String ENTITY_NAME = "feedBackUsuarioParaContador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeedBackUsuarioParaContadorService feedBackUsuarioParaContadorService;

    private final FeedBackUsuarioParaContadorRepository feedBackUsuarioParaContadorRepository;

    public FeedBackUsuarioParaContadorResource(
        FeedBackUsuarioParaContadorService feedBackUsuarioParaContadorService,
        FeedBackUsuarioParaContadorRepository feedBackUsuarioParaContadorRepository
    ) {
        this.feedBackUsuarioParaContadorService = feedBackUsuarioParaContadorService;
        this.feedBackUsuarioParaContadorRepository = feedBackUsuarioParaContadorRepository;
    }

    /**
     * {@code POST  /feed-back-usuario-para-contadors} : Create a new feedBackUsuarioParaContador.
     *
     * @param feedBackUsuarioParaContador the feedBackUsuarioParaContador to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feedBackUsuarioParaContador, or with status {@code 400 (Bad Request)} if the feedBackUsuarioParaContador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FeedBackUsuarioParaContador> createFeedBackUsuarioParaContador(
        @Valid @RequestBody FeedBackUsuarioParaContador feedBackUsuarioParaContador
    ) throws URISyntaxException {
        log.debug("REST request to save FeedBackUsuarioParaContador : {}", feedBackUsuarioParaContador);
        if (feedBackUsuarioParaContador.getId() != null) {
            throw new BadRequestAlertException("A new feedBackUsuarioParaContador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        feedBackUsuarioParaContador = feedBackUsuarioParaContadorService.save(feedBackUsuarioParaContador);
        return ResponseEntity.created(new URI("/api/feed-back-usuario-para-contadors/" + feedBackUsuarioParaContador.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, feedBackUsuarioParaContador.getId().toString())
            )
            .body(feedBackUsuarioParaContador);
    }

    /**
     * {@code PUT  /feed-back-usuario-para-contadors/:id} : Updates an existing feedBackUsuarioParaContador.
     *
     * @param id the id of the feedBackUsuarioParaContador to save.
     * @param feedBackUsuarioParaContador the feedBackUsuarioParaContador to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedBackUsuarioParaContador,
     * or with status {@code 400 (Bad Request)} if the feedBackUsuarioParaContador is not valid,
     * or with status {@code 500 (Internal Server Error)} if the feedBackUsuarioParaContador couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FeedBackUsuarioParaContador> updateFeedBackUsuarioParaContador(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FeedBackUsuarioParaContador feedBackUsuarioParaContador
    ) throws URISyntaxException {
        log.debug("REST request to update FeedBackUsuarioParaContador : {}, {}", id, feedBackUsuarioParaContador);
        if (feedBackUsuarioParaContador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feedBackUsuarioParaContador.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feedBackUsuarioParaContadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        feedBackUsuarioParaContador = feedBackUsuarioParaContadorService.update(feedBackUsuarioParaContador);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, feedBackUsuarioParaContador.getId().toString()))
            .body(feedBackUsuarioParaContador);
    }

    /**
     * {@code PATCH  /feed-back-usuario-para-contadors/:id} : Partial updates given fields of an existing feedBackUsuarioParaContador, field will ignore if it is null
     *
     * @param id the id of the feedBackUsuarioParaContador to save.
     * @param feedBackUsuarioParaContador the feedBackUsuarioParaContador to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedBackUsuarioParaContador,
     * or with status {@code 400 (Bad Request)} if the feedBackUsuarioParaContador is not valid,
     * or with status {@code 404 (Not Found)} if the feedBackUsuarioParaContador is not found,
     * or with status {@code 500 (Internal Server Error)} if the feedBackUsuarioParaContador couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FeedBackUsuarioParaContador> partialUpdateFeedBackUsuarioParaContador(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FeedBackUsuarioParaContador feedBackUsuarioParaContador
    ) throws URISyntaxException {
        log.debug("REST request to partial update FeedBackUsuarioParaContador partially : {}, {}", id, feedBackUsuarioParaContador);
        if (feedBackUsuarioParaContador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feedBackUsuarioParaContador.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feedBackUsuarioParaContadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FeedBackUsuarioParaContador> result = feedBackUsuarioParaContadorService.partialUpdate(feedBackUsuarioParaContador);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, feedBackUsuarioParaContador.getId().toString())
        );
    }

    /**
     * {@code GET  /feed-back-usuario-para-contadors} : get all the feedBackUsuarioParaContadors.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feedBackUsuarioParaContadors in body.
     */
    @GetMapping("")
    public List<FeedBackUsuarioParaContador> getAllFeedBackUsuarioParaContadors(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all FeedBackUsuarioParaContadors");
        return feedBackUsuarioParaContadorService.findAll();
    }

    /**
     * {@code GET  /feed-back-usuario-para-contadors/:id} : get the "id" feedBackUsuarioParaContador.
     *
     * @param id the id of the feedBackUsuarioParaContador to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feedBackUsuarioParaContador, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FeedBackUsuarioParaContador> getFeedBackUsuarioParaContador(@PathVariable("id") Long id) {
        log.debug("REST request to get FeedBackUsuarioParaContador : {}", id);
        Optional<FeedBackUsuarioParaContador> feedBackUsuarioParaContador = feedBackUsuarioParaContadorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feedBackUsuarioParaContador);
    }

    /**
     * {@code DELETE  /feed-back-usuario-para-contadors/:id} : delete the "id" feedBackUsuarioParaContador.
     *
     * @param id the id of the feedBackUsuarioParaContador to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedBackUsuarioParaContador(@PathVariable("id") Long id) {
        log.debug("REST request to delete FeedBackUsuarioParaContador : {}", id);
        feedBackUsuarioParaContadorService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
