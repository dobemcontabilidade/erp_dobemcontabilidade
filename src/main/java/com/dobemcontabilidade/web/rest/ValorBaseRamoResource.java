package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.ValorBaseRamo;
import com.dobemcontabilidade.repository.ValorBaseRamoRepository;
import com.dobemcontabilidade.service.ValorBaseRamoQueryService;
import com.dobemcontabilidade.service.ValorBaseRamoService;
import com.dobemcontabilidade.service.criteria.ValorBaseRamoCriteria;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.ValorBaseRamo}.
 */
@RestController
@RequestMapping("/api/valor-base-ramos")
public class ValorBaseRamoResource {

    private static final Logger log = LoggerFactory.getLogger(ValorBaseRamoResource.class);

    private static final String ENTITY_NAME = "valorBaseRamo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ValorBaseRamoService valorBaseRamoService;

    private final ValorBaseRamoRepository valorBaseRamoRepository;

    private final ValorBaseRamoQueryService valorBaseRamoQueryService;

    public ValorBaseRamoResource(
        ValorBaseRamoService valorBaseRamoService,
        ValorBaseRamoRepository valorBaseRamoRepository,
        ValorBaseRamoQueryService valorBaseRamoQueryService
    ) {
        this.valorBaseRamoService = valorBaseRamoService;
        this.valorBaseRamoRepository = valorBaseRamoRepository;
        this.valorBaseRamoQueryService = valorBaseRamoQueryService;
    }

    /**
     * {@code POST  /valor-base-ramos} : Create a new valorBaseRamo.
     *
     * @param valorBaseRamo the valorBaseRamo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new valorBaseRamo, or with status {@code 400 (Bad Request)} if the valorBaseRamo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ValorBaseRamo> createValorBaseRamo(@Valid @RequestBody ValorBaseRamo valorBaseRamo) throws URISyntaxException {
        log.debug("REST request to save ValorBaseRamo : {}", valorBaseRamo);
        if (valorBaseRamo.getId() != null) {
            throw new BadRequestAlertException("A new valorBaseRamo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        valorBaseRamo = valorBaseRamoService.save(valorBaseRamo);
        return ResponseEntity.created(new URI("/api/valor-base-ramos/" + valorBaseRamo.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, valorBaseRamo.getId().toString()))
            .body(valorBaseRamo);
    }

    /**
     * {@code PUT  /valor-base-ramos/:id} : Updates an existing valorBaseRamo.
     *
     * @param id the id of the valorBaseRamo to save.
     * @param valorBaseRamo the valorBaseRamo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated valorBaseRamo,
     * or with status {@code 400 (Bad Request)} if the valorBaseRamo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the valorBaseRamo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ValorBaseRamo> updateValorBaseRamo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ValorBaseRamo valorBaseRamo
    ) throws URISyntaxException {
        log.debug("REST request to update ValorBaseRamo : {}, {}", id, valorBaseRamo);
        if (valorBaseRamo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, valorBaseRamo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!valorBaseRamoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        valorBaseRamo = valorBaseRamoService.update(valorBaseRamo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, valorBaseRamo.getId().toString()))
            .body(valorBaseRamo);
    }

    /**
     * {@code PATCH  /valor-base-ramos/:id} : Partial updates given fields of an existing valorBaseRamo, field will ignore if it is null
     *
     * @param id the id of the valorBaseRamo to save.
     * @param valorBaseRamo the valorBaseRamo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated valorBaseRamo,
     * or with status {@code 400 (Bad Request)} if the valorBaseRamo is not valid,
     * or with status {@code 404 (Not Found)} if the valorBaseRamo is not found,
     * or with status {@code 500 (Internal Server Error)} if the valorBaseRamo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ValorBaseRamo> partialUpdateValorBaseRamo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ValorBaseRamo valorBaseRamo
    ) throws URISyntaxException {
        log.debug("REST request to partial update ValorBaseRamo partially : {}, {}", id, valorBaseRamo);
        if (valorBaseRamo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, valorBaseRamo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!valorBaseRamoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ValorBaseRamo> result = valorBaseRamoService.partialUpdate(valorBaseRamo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, valorBaseRamo.getId().toString())
        );
    }

    /**
     * {@code GET  /valor-base-ramos} : get all the valorBaseRamos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of valorBaseRamos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ValorBaseRamo>> getAllValorBaseRamos(
        ValorBaseRamoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ValorBaseRamos by criteria: {}", criteria);

        Page<ValorBaseRamo> page = valorBaseRamoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /valor-base-ramos/count} : count all the valorBaseRamos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countValorBaseRamos(ValorBaseRamoCriteria criteria) {
        log.debug("REST request to count ValorBaseRamos by criteria: {}", criteria);
        return ResponseEntity.ok().body(valorBaseRamoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /valor-base-ramos/:id} : get the "id" valorBaseRamo.
     *
     * @param id the id of the valorBaseRamo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the valorBaseRamo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ValorBaseRamo> getValorBaseRamo(@PathVariable("id") Long id) {
        log.debug("REST request to get ValorBaseRamo : {}", id);
        Optional<ValorBaseRamo> valorBaseRamo = valorBaseRamoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(valorBaseRamo);
    }

    /**
     * {@code DELETE  /valor-base-ramos/:id} : delete the "id" valorBaseRamo.
     *
     * @param id the id of the valorBaseRamo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteValorBaseRamo(@PathVariable("id") Long id) {
        log.debug("REST request to delete ValorBaseRamo : {}", id);
        valorBaseRamoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
