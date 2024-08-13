package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.Escolaridade;
import com.dobemcontabilidade.repository.EscolaridadeRepository;
import com.dobemcontabilidade.service.EscolaridadeService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Escolaridade}.
 */
@RestController
@RequestMapping("/api/escolaridades")
public class EscolaridadeResource {

    private static final Logger log = LoggerFactory.getLogger(EscolaridadeResource.class);

    private static final String ENTITY_NAME = "escolaridade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EscolaridadeService escolaridadeService;

    private final EscolaridadeRepository escolaridadeRepository;

    public EscolaridadeResource(EscolaridadeService escolaridadeService, EscolaridadeRepository escolaridadeRepository) {
        this.escolaridadeService = escolaridadeService;
        this.escolaridadeRepository = escolaridadeRepository;
    }

    /**
     * {@code POST  /escolaridades} : Create a new escolaridade.
     *
     * @param escolaridade the escolaridade to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new escolaridade, or with status {@code 400 (Bad Request)} if the escolaridade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Escolaridade> createEscolaridade(@Valid @RequestBody Escolaridade escolaridade) throws URISyntaxException {
        log.debug("REST request to save Escolaridade : {}", escolaridade);
        if (escolaridade.getId() != null) {
            throw new BadRequestAlertException("A new escolaridade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        escolaridade = escolaridadeService.save(escolaridade);
        return ResponseEntity.created(new URI("/api/escolaridades/" + escolaridade.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, escolaridade.getId().toString()))
            .body(escolaridade);
    }

    /**
     * {@code PUT  /escolaridades/:id} : Updates an existing escolaridade.
     *
     * @param id the id of the escolaridade to save.
     * @param escolaridade the escolaridade to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated escolaridade,
     * or with status {@code 400 (Bad Request)} if the escolaridade is not valid,
     * or with status {@code 500 (Internal Server Error)} if the escolaridade couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Escolaridade> updateEscolaridade(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Escolaridade escolaridade
    ) throws URISyntaxException {
        log.debug("REST request to update Escolaridade : {}, {}", id, escolaridade);
        if (escolaridade.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, escolaridade.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!escolaridadeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        escolaridade = escolaridadeService.update(escolaridade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, escolaridade.getId().toString()))
            .body(escolaridade);
    }

    /**
     * {@code PATCH  /escolaridades/:id} : Partial updates given fields of an existing escolaridade, field will ignore if it is null
     *
     * @param id the id of the escolaridade to save.
     * @param escolaridade the escolaridade to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated escolaridade,
     * or with status {@code 400 (Bad Request)} if the escolaridade is not valid,
     * or with status {@code 404 (Not Found)} if the escolaridade is not found,
     * or with status {@code 500 (Internal Server Error)} if the escolaridade couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Escolaridade> partialUpdateEscolaridade(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Escolaridade escolaridade
    ) throws URISyntaxException {
        log.debug("REST request to partial update Escolaridade partially : {}, {}", id, escolaridade);
        if (escolaridade.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, escolaridade.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!escolaridadeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Escolaridade> result = escolaridadeService.partialUpdate(escolaridade);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, escolaridade.getId().toString())
        );
    }

    /**
     * {@code GET  /escolaridades} : get all the escolaridades.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of escolaridades in body.
     */
    @GetMapping("")
    public List<Escolaridade> getAllEscolaridades() {
        log.debug("REST request to get all Escolaridades");
        return escolaridadeService.findAll();
    }

    /**
     * {@code GET  /escolaridades/:id} : get the "id" escolaridade.
     *
     * @param id the id of the escolaridade to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the escolaridade, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Escolaridade> getEscolaridade(@PathVariable("id") Long id) {
        log.debug("REST request to get Escolaridade : {}", id);
        Optional<Escolaridade> escolaridade = escolaridadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(escolaridade);
    }

    /**
     * {@code DELETE  /escolaridades/:id} : delete the "id" escolaridade.
     *
     * @param id the id of the escolaridade to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEscolaridade(@PathVariable("id") Long id) {
        log.debug("REST request to delete Escolaridade : {}", id);
        escolaridadeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
