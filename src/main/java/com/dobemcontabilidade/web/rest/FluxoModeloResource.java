package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.FluxoModelo;
import com.dobemcontabilidade.repository.FluxoModeloRepository;
import com.dobemcontabilidade.service.FluxoModeloService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.FluxoModelo}.
 */
@RestController
@RequestMapping("/api/fluxo-modelos")
public class FluxoModeloResource {

    private static final Logger log = LoggerFactory.getLogger(FluxoModeloResource.class);

    private static final String ENTITY_NAME = "fluxoModelo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FluxoModeloService fluxoModeloService;

    private final FluxoModeloRepository fluxoModeloRepository;

    public FluxoModeloResource(FluxoModeloService fluxoModeloService, FluxoModeloRepository fluxoModeloRepository) {
        this.fluxoModeloService = fluxoModeloService;
        this.fluxoModeloRepository = fluxoModeloRepository;
    }

    /**
     * {@code POST  /fluxo-modelos} : Create a new fluxoModelo.
     *
     * @param fluxoModelo the fluxoModelo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fluxoModelo, or with status {@code 400 (Bad Request)} if the fluxoModelo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FluxoModelo> createFluxoModelo(@Valid @RequestBody FluxoModelo fluxoModelo) throws URISyntaxException {
        log.debug("REST request to save FluxoModelo : {}", fluxoModelo);
        if (fluxoModelo.getId() != null) {
            throw new BadRequestAlertException("A new fluxoModelo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        fluxoModelo = fluxoModeloService.save(fluxoModelo);
        return ResponseEntity.created(new URI("/api/fluxo-modelos/" + fluxoModelo.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, fluxoModelo.getId().toString()))
            .body(fluxoModelo);
    }

    /**
     * {@code PUT  /fluxo-modelos/:id} : Updates an existing fluxoModelo.
     *
     * @param id the id of the fluxoModelo to save.
     * @param fluxoModelo the fluxoModelo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fluxoModelo,
     * or with status {@code 400 (Bad Request)} if the fluxoModelo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fluxoModelo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FluxoModelo> updateFluxoModelo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FluxoModelo fluxoModelo
    ) throws URISyntaxException {
        log.debug("REST request to update FluxoModelo : {}, {}", id, fluxoModelo);
        if (fluxoModelo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fluxoModelo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fluxoModeloRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        fluxoModelo = fluxoModeloService.update(fluxoModelo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fluxoModelo.getId().toString()))
            .body(fluxoModelo);
    }

    /**
     * {@code PATCH  /fluxo-modelos/:id} : Partial updates given fields of an existing fluxoModelo, field will ignore if it is null
     *
     * @param id the id of the fluxoModelo to save.
     * @param fluxoModelo the fluxoModelo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fluxoModelo,
     * or with status {@code 400 (Bad Request)} if the fluxoModelo is not valid,
     * or with status {@code 404 (Not Found)} if the fluxoModelo is not found,
     * or with status {@code 500 (Internal Server Error)} if the fluxoModelo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FluxoModelo> partialUpdateFluxoModelo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FluxoModelo fluxoModelo
    ) throws URISyntaxException {
        log.debug("REST request to partial update FluxoModelo partially : {}, {}", id, fluxoModelo);
        if (fluxoModelo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fluxoModelo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fluxoModeloRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FluxoModelo> result = fluxoModeloService.partialUpdate(fluxoModelo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fluxoModelo.getId().toString())
        );
    }

    /**
     * {@code GET  /fluxo-modelos} : get all the fluxoModelos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fluxoModelos in body.
     */
    @GetMapping("")
    public List<FluxoModelo> getAllFluxoModelos(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all FluxoModelos");
        return fluxoModeloService.findAll();
    }

    /**
     * {@code GET  /fluxo-modelos/:id} : get the "id" fluxoModelo.
     *
     * @param id the id of the fluxoModelo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fluxoModelo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FluxoModelo> getFluxoModelo(@PathVariable("id") Long id) {
        log.debug("REST request to get FluxoModelo : {}", id);
        Optional<FluxoModelo> fluxoModelo = fluxoModeloService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fluxoModelo);
    }

    /**
     * {@code DELETE  /fluxo-modelos/:id} : delete the "id" fluxoModelo.
     *
     * @param id the id of the fluxoModelo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFluxoModelo(@PathVariable("id") Long id) {
        log.debug("REST request to delete FluxoModelo : {}", id);
        fluxoModeloService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
