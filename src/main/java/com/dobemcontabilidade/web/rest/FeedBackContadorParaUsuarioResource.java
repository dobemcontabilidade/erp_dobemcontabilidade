package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.FeedBackContadorParaUsuario;
import com.dobemcontabilidade.repository.FeedBackContadorParaUsuarioRepository;
import com.dobemcontabilidade.service.FeedBackContadorParaUsuarioService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.FeedBackContadorParaUsuario}.
 */
@RestController
@RequestMapping("/api/feed-back-contador-para-usuarios")
public class FeedBackContadorParaUsuarioResource {

    private static final Logger log = LoggerFactory.getLogger(FeedBackContadorParaUsuarioResource.class);

    private static final String ENTITY_NAME = "feedBackContadorParaUsuario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeedBackContadorParaUsuarioService feedBackContadorParaUsuarioService;

    private final FeedBackContadorParaUsuarioRepository feedBackContadorParaUsuarioRepository;

    public FeedBackContadorParaUsuarioResource(
        FeedBackContadorParaUsuarioService feedBackContadorParaUsuarioService,
        FeedBackContadorParaUsuarioRepository feedBackContadorParaUsuarioRepository
    ) {
        this.feedBackContadorParaUsuarioService = feedBackContadorParaUsuarioService;
        this.feedBackContadorParaUsuarioRepository = feedBackContadorParaUsuarioRepository;
    }

    /**
     * {@code POST  /feed-back-contador-para-usuarios} : Create a new feedBackContadorParaUsuario.
     *
     * @param feedBackContadorParaUsuario the feedBackContadorParaUsuario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feedBackContadorParaUsuario, or with status {@code 400 (Bad Request)} if the feedBackContadorParaUsuario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FeedBackContadorParaUsuario> createFeedBackContadorParaUsuario(
        @Valid @RequestBody FeedBackContadorParaUsuario feedBackContadorParaUsuario
    ) throws URISyntaxException {
        log.debug("REST request to save FeedBackContadorParaUsuario : {}", feedBackContadorParaUsuario);
        if (feedBackContadorParaUsuario.getId() != null) {
            throw new BadRequestAlertException("A new feedBackContadorParaUsuario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        feedBackContadorParaUsuario = feedBackContadorParaUsuarioService.save(feedBackContadorParaUsuario);
        return ResponseEntity.created(new URI("/api/feed-back-contador-para-usuarios/" + feedBackContadorParaUsuario.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, feedBackContadorParaUsuario.getId().toString())
            )
            .body(feedBackContadorParaUsuario);
    }

    /**
     * {@code PUT  /feed-back-contador-para-usuarios/:id} : Updates an existing feedBackContadorParaUsuario.
     *
     * @param id the id of the feedBackContadorParaUsuario to save.
     * @param feedBackContadorParaUsuario the feedBackContadorParaUsuario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedBackContadorParaUsuario,
     * or with status {@code 400 (Bad Request)} if the feedBackContadorParaUsuario is not valid,
     * or with status {@code 500 (Internal Server Error)} if the feedBackContadorParaUsuario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FeedBackContadorParaUsuario> updateFeedBackContadorParaUsuario(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FeedBackContadorParaUsuario feedBackContadorParaUsuario
    ) throws URISyntaxException {
        log.debug("REST request to update FeedBackContadorParaUsuario : {}, {}", id, feedBackContadorParaUsuario);
        if (feedBackContadorParaUsuario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feedBackContadorParaUsuario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feedBackContadorParaUsuarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        feedBackContadorParaUsuario = feedBackContadorParaUsuarioService.update(feedBackContadorParaUsuario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, feedBackContadorParaUsuario.getId().toString()))
            .body(feedBackContadorParaUsuario);
    }

    /**
     * {@code PATCH  /feed-back-contador-para-usuarios/:id} : Partial updates given fields of an existing feedBackContadorParaUsuario, field will ignore if it is null
     *
     * @param id the id of the feedBackContadorParaUsuario to save.
     * @param feedBackContadorParaUsuario the feedBackContadorParaUsuario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedBackContadorParaUsuario,
     * or with status {@code 400 (Bad Request)} if the feedBackContadorParaUsuario is not valid,
     * or with status {@code 404 (Not Found)} if the feedBackContadorParaUsuario is not found,
     * or with status {@code 500 (Internal Server Error)} if the feedBackContadorParaUsuario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FeedBackContadorParaUsuario> partialUpdateFeedBackContadorParaUsuario(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FeedBackContadorParaUsuario feedBackContadorParaUsuario
    ) throws URISyntaxException {
        log.debug("REST request to partial update FeedBackContadorParaUsuario partially : {}, {}", id, feedBackContadorParaUsuario);
        if (feedBackContadorParaUsuario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feedBackContadorParaUsuario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feedBackContadorParaUsuarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FeedBackContadorParaUsuario> result = feedBackContadorParaUsuarioService.partialUpdate(feedBackContadorParaUsuario);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, feedBackContadorParaUsuario.getId().toString())
        );
    }

    /**
     * {@code GET  /feed-back-contador-para-usuarios} : get all the feedBackContadorParaUsuarios.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feedBackContadorParaUsuarios in body.
     */
    @GetMapping("")
    public List<FeedBackContadorParaUsuario> getAllFeedBackContadorParaUsuarios(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all FeedBackContadorParaUsuarios");
        return feedBackContadorParaUsuarioService.findAll();
    }

    /**
     * {@code GET  /feed-back-contador-para-usuarios/:id} : get the "id" feedBackContadorParaUsuario.
     *
     * @param id the id of the feedBackContadorParaUsuario to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feedBackContadorParaUsuario, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FeedBackContadorParaUsuario> getFeedBackContadorParaUsuario(@PathVariable("id") Long id) {
        log.debug("REST request to get FeedBackContadorParaUsuario : {}", id);
        Optional<FeedBackContadorParaUsuario> feedBackContadorParaUsuario = feedBackContadorParaUsuarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feedBackContadorParaUsuario);
    }

    /**
     * {@code DELETE  /feed-back-contador-para-usuarios/:id} : delete the "id" feedBackContadorParaUsuario.
     *
     * @param id the id of the feedBackContadorParaUsuario to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedBackContadorParaUsuario(@PathVariable("id") Long id) {
        log.debug("REST request to delete FeedBackContadorParaUsuario : {}", id);
        feedBackContadorParaUsuarioService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
