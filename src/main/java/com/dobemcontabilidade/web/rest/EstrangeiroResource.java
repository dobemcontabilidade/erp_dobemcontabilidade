package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.Estrangeiro;
import com.dobemcontabilidade.repository.EstrangeiroRepository;
import com.dobemcontabilidade.service.EstrangeiroService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Estrangeiro}.
 */
@RestController
@RequestMapping("/api/estrangeiros")
public class EstrangeiroResource {

    private static final Logger log = LoggerFactory.getLogger(EstrangeiroResource.class);

    private static final String ENTITY_NAME = "estrangeiro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstrangeiroService estrangeiroService;

    private final EstrangeiroRepository estrangeiroRepository;

    public EstrangeiroResource(EstrangeiroService estrangeiroService, EstrangeiroRepository estrangeiroRepository) {
        this.estrangeiroService = estrangeiroService;
        this.estrangeiroRepository = estrangeiroRepository;
    }

    /**
     * {@code POST  /estrangeiros} : Create a new estrangeiro.
     *
     * @param estrangeiro the estrangeiro to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estrangeiro, or with status {@code 400 (Bad Request)} if the estrangeiro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Estrangeiro> createEstrangeiro(@Valid @RequestBody Estrangeiro estrangeiro) throws URISyntaxException {
        log.debug("REST request to save Estrangeiro : {}", estrangeiro);
        if (estrangeiro.getId() != null) {
            throw new BadRequestAlertException("A new estrangeiro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        estrangeiro = estrangeiroService.save(estrangeiro);
        return ResponseEntity.created(new URI("/api/estrangeiros/" + estrangeiro.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, estrangeiro.getId().toString()))
            .body(estrangeiro);
    }

    /**
     * {@code PUT  /estrangeiros/:id} : Updates an existing estrangeiro.
     *
     * @param id the id of the estrangeiro to save.
     * @param estrangeiro the estrangeiro to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estrangeiro,
     * or with status {@code 400 (Bad Request)} if the estrangeiro is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estrangeiro couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Estrangeiro> updateEstrangeiro(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Estrangeiro estrangeiro
    ) throws URISyntaxException {
        log.debug("REST request to update Estrangeiro : {}, {}", id, estrangeiro);
        if (estrangeiro.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estrangeiro.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estrangeiroRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        estrangeiro = estrangeiroService.update(estrangeiro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estrangeiro.getId().toString()))
            .body(estrangeiro);
    }

    /**
     * {@code PATCH  /estrangeiros/:id} : Partial updates given fields of an existing estrangeiro, field will ignore if it is null
     *
     * @param id the id of the estrangeiro to save.
     * @param estrangeiro the estrangeiro to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estrangeiro,
     * or with status {@code 400 (Bad Request)} if the estrangeiro is not valid,
     * or with status {@code 404 (Not Found)} if the estrangeiro is not found,
     * or with status {@code 500 (Internal Server Error)} if the estrangeiro couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Estrangeiro> partialUpdateEstrangeiro(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Estrangeiro estrangeiro
    ) throws URISyntaxException {
        log.debug("REST request to partial update Estrangeiro partially : {}, {}", id, estrangeiro);
        if (estrangeiro.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estrangeiro.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estrangeiroRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Estrangeiro> result = estrangeiroService.partialUpdate(estrangeiro);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estrangeiro.getId().toString())
        );
    }

    /**
     * {@code GET  /estrangeiros} : get all the estrangeiros.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estrangeiros in body.
     */
    @GetMapping("")
    public List<Estrangeiro> getAllEstrangeiros() {
        log.debug("REST request to get all Estrangeiros");
        return estrangeiroService.findAll();
    }

    /**
     * {@code GET  /estrangeiros/:id} : get the "id" estrangeiro.
     *
     * @param id the id of the estrangeiro to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estrangeiro, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Estrangeiro> getEstrangeiro(@PathVariable("id") Long id) {
        log.debug("REST request to get Estrangeiro : {}", id);
        Optional<Estrangeiro> estrangeiro = estrangeiroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estrangeiro);
    }

    /**
     * {@code DELETE  /estrangeiros/:id} : delete the "id" estrangeiro.
     *
     * @param id the id of the estrangeiro to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstrangeiro(@PathVariable("id") Long id) {
        log.debug("REST request to delete Estrangeiro : {}", id);
        estrangeiroService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
