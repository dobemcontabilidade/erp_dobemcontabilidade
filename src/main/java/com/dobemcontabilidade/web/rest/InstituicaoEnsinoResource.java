package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.InstituicaoEnsino;
import com.dobemcontabilidade.repository.InstituicaoEnsinoRepository;
import com.dobemcontabilidade.service.InstituicaoEnsinoService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.InstituicaoEnsino}.
 */
@RestController
@RequestMapping("/api/instituicao-ensinos")
public class InstituicaoEnsinoResource {

    private static final Logger log = LoggerFactory.getLogger(InstituicaoEnsinoResource.class);

    private static final String ENTITY_NAME = "instituicaoEnsino";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InstituicaoEnsinoService instituicaoEnsinoService;

    private final InstituicaoEnsinoRepository instituicaoEnsinoRepository;

    public InstituicaoEnsinoResource(
        InstituicaoEnsinoService instituicaoEnsinoService,
        InstituicaoEnsinoRepository instituicaoEnsinoRepository
    ) {
        this.instituicaoEnsinoService = instituicaoEnsinoService;
        this.instituicaoEnsinoRepository = instituicaoEnsinoRepository;
    }

    /**
     * {@code POST  /instituicao-ensinos} : Create a new instituicaoEnsino.
     *
     * @param instituicaoEnsino the instituicaoEnsino to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new instituicaoEnsino, or with status {@code 400 (Bad Request)} if the instituicaoEnsino has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<InstituicaoEnsino> createInstituicaoEnsino(@Valid @RequestBody InstituicaoEnsino instituicaoEnsino)
        throws URISyntaxException {
        log.debug("REST request to save InstituicaoEnsino : {}", instituicaoEnsino);
        if (instituicaoEnsino.getId() != null) {
            throw new BadRequestAlertException("A new instituicaoEnsino cannot already have an ID", ENTITY_NAME, "idexists");
        }
        instituicaoEnsino = instituicaoEnsinoService.save(instituicaoEnsino);
        return ResponseEntity.created(new URI("/api/instituicao-ensinos/" + instituicaoEnsino.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, instituicaoEnsino.getId().toString()))
            .body(instituicaoEnsino);
    }

    /**
     * {@code PUT  /instituicao-ensinos/:id} : Updates an existing instituicaoEnsino.
     *
     * @param id the id of the instituicaoEnsino to save.
     * @param instituicaoEnsino the instituicaoEnsino to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated instituicaoEnsino,
     * or with status {@code 400 (Bad Request)} if the instituicaoEnsino is not valid,
     * or with status {@code 500 (Internal Server Error)} if the instituicaoEnsino couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InstituicaoEnsino> updateInstituicaoEnsino(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody InstituicaoEnsino instituicaoEnsino
    ) throws URISyntaxException {
        log.debug("REST request to update InstituicaoEnsino : {}, {}", id, instituicaoEnsino);
        if (instituicaoEnsino.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, instituicaoEnsino.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!instituicaoEnsinoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        instituicaoEnsino = instituicaoEnsinoService.update(instituicaoEnsino);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, instituicaoEnsino.getId().toString()))
            .body(instituicaoEnsino);
    }

    /**
     * {@code PATCH  /instituicao-ensinos/:id} : Partial updates given fields of an existing instituicaoEnsino, field will ignore if it is null
     *
     * @param id the id of the instituicaoEnsino to save.
     * @param instituicaoEnsino the instituicaoEnsino to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated instituicaoEnsino,
     * or with status {@code 400 (Bad Request)} if the instituicaoEnsino is not valid,
     * or with status {@code 404 (Not Found)} if the instituicaoEnsino is not found,
     * or with status {@code 500 (Internal Server Error)} if the instituicaoEnsino couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InstituicaoEnsino> partialUpdateInstituicaoEnsino(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody InstituicaoEnsino instituicaoEnsino
    ) throws URISyntaxException {
        log.debug("REST request to partial update InstituicaoEnsino partially : {}, {}", id, instituicaoEnsino);
        if (instituicaoEnsino.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, instituicaoEnsino.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!instituicaoEnsinoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InstituicaoEnsino> result = instituicaoEnsinoService.partialUpdate(instituicaoEnsino);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, instituicaoEnsino.getId().toString())
        );
    }

    /**
     * {@code GET  /instituicao-ensinos} : get all the instituicaoEnsinos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of instituicaoEnsinos in body.
     */
    @GetMapping("")
    public List<InstituicaoEnsino> getAllInstituicaoEnsinos(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all InstituicaoEnsinos");
        return instituicaoEnsinoService.findAll();
    }

    /**
     * {@code GET  /instituicao-ensinos/:id} : get the "id" instituicaoEnsino.
     *
     * @param id the id of the instituicaoEnsino to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the instituicaoEnsino, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InstituicaoEnsino> getInstituicaoEnsino(@PathVariable("id") Long id) {
        log.debug("REST request to get InstituicaoEnsino : {}", id);
        Optional<InstituicaoEnsino> instituicaoEnsino = instituicaoEnsinoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(instituicaoEnsino);
    }

    /**
     * {@code DELETE  /instituicao-ensinos/:id} : delete the "id" instituicaoEnsino.
     *
     * @param id the id of the instituicaoEnsino to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstituicaoEnsino(@PathVariable("id") Long id) {
        log.debug("REST request to delete InstituicaoEnsino : {}", id);
        instituicaoEnsinoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
