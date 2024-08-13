package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.Ramo;
import com.dobemcontabilidade.repository.RamoRepository;
import com.dobemcontabilidade.service.RamoQueryService;
import com.dobemcontabilidade.service.RamoService;
import com.dobemcontabilidade.service.criteria.RamoCriteria;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dobemcontabilidade.domain.Ramo}.
 */
@RestController
@RequestMapping("/api/ramos")
public class RamoResource {

    private static final Logger log = LoggerFactory.getLogger(RamoResource.class);

    private static final String ENTITY_NAME = "ramo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RamoService ramoService;

    private final RamoRepository ramoRepository;

    private final RamoQueryService ramoQueryService;

    public RamoResource(RamoService ramoService, RamoRepository ramoRepository, RamoQueryService ramoQueryService) {
        this.ramoService = ramoService;
        this.ramoRepository = ramoRepository;
        this.ramoQueryService = ramoQueryService;
    }

    /**
     * {@code POST  /ramos} : Create a new ramo.
     *
     * @param ramo the ramo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ramo, or with status {@code 400 (Bad Request)} if the ramo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Ramo> createRamo(@Valid @RequestBody Ramo ramo) throws URISyntaxException {
        log.debug("REST request to save Ramo : {}", ramo);
        if (ramo.getId() != null) {
            throw new BadRequestAlertException("A new ramo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ramo = ramoService.save(ramo);
        return ResponseEntity.created(new URI("/api/ramos/" + ramo.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ramo.getId().toString()))
            .body(ramo);
    }

    /**
     * {@code PUT  /ramos/:id} : Updates an existing ramo.
     *
     * @param id the id of the ramo to save.
     * @param ramo the ramo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ramo,
     * or with status {@code 400 (Bad Request)} if the ramo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ramo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Ramo> updateRamo(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Ramo ramo)
        throws URISyntaxException {
        log.debug("REST request to update Ramo : {}, {}", id, ramo);
        if (ramo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ramo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ramoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ramo = ramoService.update(ramo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ramo.getId().toString()))
            .body(ramo);
    }

    /**
     * {@code PATCH  /ramos/:id} : Partial updates given fields of an existing ramo, field will ignore if it is null
     *
     * @param id the id of the ramo to save.
     * @param ramo the ramo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ramo,
     * or with status {@code 400 (Bad Request)} if the ramo is not valid,
     * or with status {@code 404 (Not Found)} if the ramo is not found,
     * or with status {@code 500 (Internal Server Error)} if the ramo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ramo> partialUpdateRamo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Ramo ramo
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ramo partially : {}, {}", id, ramo);
        if (ramo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ramo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ramoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ramo> result = ramoService.partialUpdate(ramo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ramo.getId().toString())
        );
    }

    /**
     * {@code GET  /ramos} : get all the ramos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ramos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Ramo>> getAllRamos(
        RamoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Ramos by criteria: {}", criteria);

        Page<Ramo> page = ramoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ramos/count} : count all the ramos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countRamos(RamoCriteria criteria) {
        log.debug("REST request to count Ramos by criteria: {}", criteria);
        return ResponseEntity.ok().body(ramoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ramos/:id} : get the "id" ramo.
     *
     * @param id the id of the ramo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ramo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ramo> getRamo(@PathVariable("id") Long id) {
        log.debug("REST request to get Ramo : {}", id);
        Optional<Ramo> ramo = ramoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ramo);
    }

    /**
     * {@code DELETE  /ramos/:id} : delete the "id" ramo.
     *
     * @param id the id of the ramo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRamo(@PathVariable("id") Long id) {
        log.debug("REST request to delete Ramo : {}", id);
        ramoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
