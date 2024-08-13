package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.PlanoContaAzul;
import com.dobemcontabilidade.repository.PlanoContaAzulRepository;
import com.dobemcontabilidade.service.PlanoContaAzulQueryService;
import com.dobemcontabilidade.service.PlanoContaAzulService;
import com.dobemcontabilidade.service.criteria.PlanoContaAzulCriteria;
import com.dobemcontabilidade.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.PlanoContaAzul}.
 */
@RestController
@RequestMapping("/api/plano-conta-azuls")
public class PlanoContaAzulResource {

    private static final Logger log = LoggerFactory.getLogger(PlanoContaAzulResource.class);

    private static final String ENTITY_NAME = "planoContaAzul";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanoContaAzulService planoContaAzulService;

    private final PlanoContaAzulRepository planoContaAzulRepository;

    private final PlanoContaAzulQueryService planoContaAzulQueryService;

    public PlanoContaAzulResource(
        PlanoContaAzulService planoContaAzulService,
        PlanoContaAzulRepository planoContaAzulRepository,
        PlanoContaAzulQueryService planoContaAzulQueryService
    ) {
        this.planoContaAzulService = planoContaAzulService;
        this.planoContaAzulRepository = planoContaAzulRepository;
        this.planoContaAzulQueryService = planoContaAzulQueryService;
    }

    /**
     * {@code POST  /plano-conta-azuls} : Create a new planoContaAzul.
     *
     * @param planoContaAzul the planoContaAzul to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planoContaAzul, or with status {@code 400 (Bad Request)} if the planoContaAzul has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PlanoContaAzul> createPlanoContaAzul(@RequestBody PlanoContaAzul planoContaAzul) throws URISyntaxException {
        log.debug("REST request to save PlanoContaAzul : {}", planoContaAzul);
        if (planoContaAzul.getId() != null) {
            throw new BadRequestAlertException("A new planoContaAzul cannot already have an ID", ENTITY_NAME, "idexists");
        }
        planoContaAzul = planoContaAzulService.save(planoContaAzul);
        return ResponseEntity.created(new URI("/api/plano-conta-azuls/" + planoContaAzul.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, planoContaAzul.getId().toString()))
            .body(planoContaAzul);
    }

    /**
     * {@code PUT  /plano-conta-azuls/:id} : Updates an existing planoContaAzul.
     *
     * @param id the id of the planoContaAzul to save.
     * @param planoContaAzul the planoContaAzul to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planoContaAzul,
     * or with status {@code 400 (Bad Request)} if the planoContaAzul is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planoContaAzul couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PlanoContaAzul> updatePlanoContaAzul(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlanoContaAzul planoContaAzul
    ) throws URISyntaxException {
        log.debug("REST request to update PlanoContaAzul : {}, {}", id, planoContaAzul);
        if (planoContaAzul.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planoContaAzul.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planoContaAzulRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        planoContaAzul = planoContaAzulService.update(planoContaAzul);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planoContaAzul.getId().toString()))
            .body(planoContaAzul);
    }

    /**
     * {@code PATCH  /plano-conta-azuls/:id} : Partial updates given fields of an existing planoContaAzul, field will ignore if it is null
     *
     * @param id the id of the planoContaAzul to save.
     * @param planoContaAzul the planoContaAzul to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planoContaAzul,
     * or with status {@code 400 (Bad Request)} if the planoContaAzul is not valid,
     * or with status {@code 404 (Not Found)} if the planoContaAzul is not found,
     * or with status {@code 500 (Internal Server Error)} if the planoContaAzul couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlanoContaAzul> partialUpdatePlanoContaAzul(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlanoContaAzul planoContaAzul
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlanoContaAzul partially : {}, {}", id, planoContaAzul);
        if (planoContaAzul.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planoContaAzul.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planoContaAzulRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlanoContaAzul> result = planoContaAzulService.partialUpdate(planoContaAzul);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planoContaAzul.getId().toString())
        );
    }

    /**
     * {@code GET  /plano-conta-azuls} : get all the planoContaAzuls.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planoContaAzuls in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PlanoContaAzul>> getAllPlanoContaAzuls(
        PlanoContaAzulCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PlanoContaAzuls by criteria: {}", criteria);

        Page<PlanoContaAzul> page = planoContaAzulQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plano-conta-azuls/count} : count all the planoContaAzuls.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countPlanoContaAzuls(PlanoContaAzulCriteria criteria) {
        log.debug("REST request to count PlanoContaAzuls by criteria: {}", criteria);
        return ResponseEntity.ok().body(planoContaAzulQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /plano-conta-azuls/:id} : get the "id" planoContaAzul.
     *
     * @param id the id of the planoContaAzul to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planoContaAzul, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PlanoContaAzul> getPlanoContaAzul(@PathVariable("id") Long id) {
        log.debug("REST request to get PlanoContaAzul : {}", id);
        Optional<PlanoContaAzul> planoContaAzul = planoContaAzulService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planoContaAzul);
    }

    /**
     * {@code DELETE  /plano-conta-azuls/:id} : delete the "id" planoContaAzul.
     *
     * @param id the id of the planoContaAzul to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanoContaAzul(@PathVariable("id") Long id) {
        log.debug("REST request to delete PlanoContaAzul : {}", id);
        planoContaAzulService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
