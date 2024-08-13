package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.AreaContabilContador;
import com.dobemcontabilidade.repository.AreaContabilContadorRepository;
import com.dobemcontabilidade.service.AreaContabilContadorService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AreaContabilContador}.
 */
@RestController
@RequestMapping("/api/area-contabil-contadors")
public class AreaContabilContadorResource {

    private static final Logger log = LoggerFactory.getLogger(AreaContabilContadorResource.class);

    private static final String ENTITY_NAME = "areaContabilContador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AreaContabilContadorService areaContabilContadorService;

    private final AreaContabilContadorRepository areaContabilContadorRepository;

    public AreaContabilContadorResource(
        AreaContabilContadorService areaContabilContadorService,
        AreaContabilContadorRepository areaContabilContadorRepository
    ) {
        this.areaContabilContadorService = areaContabilContadorService;
        this.areaContabilContadorRepository = areaContabilContadorRepository;
    }

    /**
     * {@code POST  /area-contabil-contadors} : Create a new areaContabilContador.
     *
     * @param areaContabilContador the areaContabilContador to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new areaContabilContador, or with status {@code 400 (Bad Request)} if the areaContabilContador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AreaContabilContador> createAreaContabilContador(@Valid @RequestBody AreaContabilContador areaContabilContador)
        throws URISyntaxException {
        log.debug("REST request to save AreaContabilContador : {}", areaContabilContador);
        if (areaContabilContador.getId() != null) {
            throw new BadRequestAlertException("A new areaContabilContador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        areaContabilContador = areaContabilContadorService.save(areaContabilContador);
        return ResponseEntity.created(new URI("/api/area-contabil-contadors/" + areaContabilContador.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, areaContabilContador.getId().toString()))
            .body(areaContabilContador);
    }

    /**
     * {@code PUT  /area-contabil-contadors/:id} : Updates an existing areaContabilContador.
     *
     * @param id the id of the areaContabilContador to save.
     * @param areaContabilContador the areaContabilContador to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated areaContabilContador,
     * or with status {@code 400 (Bad Request)} if the areaContabilContador is not valid,
     * or with status {@code 500 (Internal Server Error)} if the areaContabilContador couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AreaContabilContador> updateAreaContabilContador(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AreaContabilContador areaContabilContador
    ) throws URISyntaxException {
        log.debug("REST request to update AreaContabilContador : {}, {}", id, areaContabilContador);
        if (areaContabilContador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, areaContabilContador.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!areaContabilContadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        areaContabilContador = areaContabilContadorService.update(areaContabilContador);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, areaContabilContador.getId().toString()))
            .body(areaContabilContador);
    }

    /**
     * {@code PATCH  /area-contabil-contadors/:id} : Partial updates given fields of an existing areaContabilContador, field will ignore if it is null
     *
     * @param id the id of the areaContabilContador to save.
     * @param areaContabilContador the areaContabilContador to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated areaContabilContador,
     * or with status {@code 400 (Bad Request)} if the areaContabilContador is not valid,
     * or with status {@code 404 (Not Found)} if the areaContabilContador is not found,
     * or with status {@code 500 (Internal Server Error)} if the areaContabilContador couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AreaContabilContador> partialUpdateAreaContabilContador(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AreaContabilContador areaContabilContador
    ) throws URISyntaxException {
        log.debug("REST request to partial update AreaContabilContador partially : {}, {}", id, areaContabilContador);
        if (areaContabilContador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, areaContabilContador.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!areaContabilContadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AreaContabilContador> result = areaContabilContadorService.partialUpdate(areaContabilContador);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, areaContabilContador.getId().toString())
        );
    }

    /**
     * {@code GET  /area-contabil-contadors} : get all the areaContabilContadors.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of areaContabilContadors in body.
     */
    @GetMapping("")
    public List<AreaContabilContador> getAllAreaContabilContadors(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all AreaContabilContadors");
        return areaContabilContadorService.findAll();
    }

    /**
     * {@code GET  /area-contabil-contadors/:id} : get the "id" areaContabilContador.
     *
     * @param id the id of the areaContabilContador to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the areaContabilContador, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AreaContabilContador> getAreaContabilContador(@PathVariable("id") Long id) {
        log.debug("REST request to get AreaContabilContador : {}", id);
        Optional<AreaContabilContador> areaContabilContador = areaContabilContadorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(areaContabilContador);
    }

    /**
     * {@code DELETE  /area-contabil-contadors/:id} : delete the "id" areaContabilContador.
     *
     * @param id the id of the areaContabilContador to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAreaContabilContador(@PathVariable("id") Long id) {
        log.debug("REST request to delete AreaContabilContador : {}", id);
        areaContabilContadorService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
