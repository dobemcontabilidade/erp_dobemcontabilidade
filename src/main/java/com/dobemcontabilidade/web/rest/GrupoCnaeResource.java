package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.GrupoCnae;
import com.dobemcontabilidade.repository.GrupoCnaeRepository;
import com.dobemcontabilidade.service.GrupoCnaeQueryService;
import com.dobemcontabilidade.service.GrupoCnaeService;
import com.dobemcontabilidade.service.criteria.GrupoCnaeCriteria;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.GrupoCnae}.
 */
@RestController
@RequestMapping("/api/grupo-cnaes")
public class GrupoCnaeResource {

    private static final Logger log = LoggerFactory.getLogger(GrupoCnaeResource.class);

    private static final String ENTITY_NAME = "grupoCnae";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GrupoCnaeService grupoCnaeService;

    private final GrupoCnaeRepository grupoCnaeRepository;

    private final GrupoCnaeQueryService grupoCnaeQueryService;

    public GrupoCnaeResource(
        GrupoCnaeService grupoCnaeService,
        GrupoCnaeRepository grupoCnaeRepository,
        GrupoCnaeQueryService grupoCnaeQueryService
    ) {
        this.grupoCnaeService = grupoCnaeService;
        this.grupoCnaeRepository = grupoCnaeRepository;
        this.grupoCnaeQueryService = grupoCnaeQueryService;
    }

    /**
     * {@code POST  /grupo-cnaes} : Create a new grupoCnae.
     *
     * @param grupoCnae the grupoCnae to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grupoCnae, or with status {@code 400 (Bad Request)} if the grupoCnae has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<GrupoCnae> createGrupoCnae(@Valid @RequestBody GrupoCnae grupoCnae) throws URISyntaxException {
        log.debug("REST request to save GrupoCnae : {}", grupoCnae);
        if (grupoCnae.getId() != null) {
            throw new BadRequestAlertException("A new grupoCnae cannot already have an ID", ENTITY_NAME, "idexists");
        }
        grupoCnae = grupoCnaeService.save(grupoCnae);
        return ResponseEntity.created(new URI("/api/grupo-cnaes/" + grupoCnae.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, grupoCnae.getId().toString()))
            .body(grupoCnae);
    }

    /**
     * {@code PUT  /grupo-cnaes/:id} : Updates an existing grupoCnae.
     *
     * @param id the id of the grupoCnae to save.
     * @param grupoCnae the grupoCnae to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoCnae,
     * or with status {@code 400 (Bad Request)} if the grupoCnae is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grupoCnae couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<GrupoCnae> updateGrupoCnae(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GrupoCnae grupoCnae
    ) throws URISyntaxException {
        log.debug("REST request to update GrupoCnae : {}, {}", id, grupoCnae);
        if (grupoCnae.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, grupoCnae.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!grupoCnaeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        grupoCnae = grupoCnaeService.update(grupoCnae);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, grupoCnae.getId().toString()))
            .body(grupoCnae);
    }

    /**
     * {@code PATCH  /grupo-cnaes/:id} : Partial updates given fields of an existing grupoCnae, field will ignore if it is null
     *
     * @param id the id of the grupoCnae to save.
     * @param grupoCnae the grupoCnae to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoCnae,
     * or with status {@code 400 (Bad Request)} if the grupoCnae is not valid,
     * or with status {@code 404 (Not Found)} if the grupoCnae is not found,
     * or with status {@code 500 (Internal Server Error)} if the grupoCnae couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GrupoCnae> partialUpdateGrupoCnae(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GrupoCnae grupoCnae
    ) throws URISyntaxException {
        log.debug("REST request to partial update GrupoCnae partially : {}, {}", id, grupoCnae);
        if (grupoCnae.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, grupoCnae.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!grupoCnaeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GrupoCnae> result = grupoCnaeService.partialUpdate(grupoCnae);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, grupoCnae.getId().toString())
        );
    }

    /**
     * {@code GET  /grupo-cnaes} : get all the grupoCnaes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grupoCnaes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<GrupoCnae>> getAllGrupoCnaes(
        GrupoCnaeCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get GrupoCnaes by criteria: {}", criteria);

        Page<GrupoCnae> page = grupoCnaeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /grupo-cnaes/count} : count all the grupoCnaes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countGrupoCnaes(GrupoCnaeCriteria criteria) {
        log.debug("REST request to count GrupoCnaes by criteria: {}", criteria);
        return ResponseEntity.ok().body(grupoCnaeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /grupo-cnaes/:id} : get the "id" grupoCnae.
     *
     * @param id the id of the grupoCnae to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grupoCnae, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GrupoCnae> getGrupoCnae(@PathVariable("id") Long id) {
        log.debug("REST request to get GrupoCnae : {}", id);
        Optional<GrupoCnae> grupoCnae = grupoCnaeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(grupoCnae);
    }

    /**
     * {@code DELETE  /grupo-cnaes/:id} : delete the "id" grupoCnae.
     *
     * @param id the id of the grupoCnae to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrupoCnae(@PathVariable("id") Long id) {
        log.debug("REST request to delete GrupoCnae : {}", id);
        grupoCnaeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
