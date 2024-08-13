package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.SegmentoCnae;
import com.dobemcontabilidade.repository.SegmentoCnaeRepository;
import com.dobemcontabilidade.service.SegmentoCnaeQueryService;
import com.dobemcontabilidade.service.SegmentoCnaeService;
import com.dobemcontabilidade.service.criteria.SegmentoCnaeCriteria;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.SegmentoCnae}.
 */
@RestController
@RequestMapping("/api/segmento-cnaes")
public class SegmentoCnaeResource {

    private static final Logger log = LoggerFactory.getLogger(SegmentoCnaeResource.class);

    private static final String ENTITY_NAME = "segmentoCnae";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SegmentoCnaeService segmentoCnaeService;

    private final SegmentoCnaeRepository segmentoCnaeRepository;

    private final SegmentoCnaeQueryService segmentoCnaeQueryService;

    public SegmentoCnaeResource(
        SegmentoCnaeService segmentoCnaeService,
        SegmentoCnaeRepository segmentoCnaeRepository,
        SegmentoCnaeQueryService segmentoCnaeQueryService
    ) {
        this.segmentoCnaeService = segmentoCnaeService;
        this.segmentoCnaeRepository = segmentoCnaeRepository;
        this.segmentoCnaeQueryService = segmentoCnaeQueryService;
    }

    /**
     * {@code POST  /segmento-cnaes} : Create a new segmentoCnae.
     *
     * @param segmentoCnae the segmentoCnae to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new segmentoCnae, or with status {@code 400 (Bad Request)} if the segmentoCnae has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SegmentoCnae> createSegmentoCnae(@Valid @RequestBody SegmentoCnae segmentoCnae) throws URISyntaxException {
        log.debug("REST request to save SegmentoCnae : {}", segmentoCnae);
        if (segmentoCnae.getId() != null) {
            throw new BadRequestAlertException("A new segmentoCnae cannot already have an ID", ENTITY_NAME, "idexists");
        }
        segmentoCnae = segmentoCnaeService.save(segmentoCnae);
        return ResponseEntity.created(new URI("/api/segmento-cnaes/" + segmentoCnae.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, segmentoCnae.getId().toString()))
            .body(segmentoCnae);
    }

    /**
     * {@code PUT  /segmento-cnaes/:id} : Updates an existing segmentoCnae.
     *
     * @param id the id of the segmentoCnae to save.
     * @param segmentoCnae the segmentoCnae to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated segmentoCnae,
     * or with status {@code 400 (Bad Request)} if the segmentoCnae is not valid,
     * or with status {@code 500 (Internal Server Error)} if the segmentoCnae couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SegmentoCnae> updateSegmentoCnae(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SegmentoCnae segmentoCnae
    ) throws URISyntaxException {
        log.debug("REST request to update SegmentoCnae : {}, {}", id, segmentoCnae);
        if (segmentoCnae.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, segmentoCnae.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!segmentoCnaeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        segmentoCnae = segmentoCnaeService.update(segmentoCnae);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, segmentoCnae.getId().toString()))
            .body(segmentoCnae);
    }

    /**
     * {@code PATCH  /segmento-cnaes/:id} : Partial updates given fields of an existing segmentoCnae, field will ignore if it is null
     *
     * @param id the id of the segmentoCnae to save.
     * @param segmentoCnae the segmentoCnae to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated segmentoCnae,
     * or with status {@code 400 (Bad Request)} if the segmentoCnae is not valid,
     * or with status {@code 404 (Not Found)} if the segmentoCnae is not found,
     * or with status {@code 500 (Internal Server Error)} if the segmentoCnae couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SegmentoCnae> partialUpdateSegmentoCnae(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SegmentoCnae segmentoCnae
    ) throws URISyntaxException {
        log.debug("REST request to partial update SegmentoCnae partially : {}, {}", id, segmentoCnae);
        if (segmentoCnae.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, segmentoCnae.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!segmentoCnaeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SegmentoCnae> result = segmentoCnaeService.partialUpdate(segmentoCnae);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, segmentoCnae.getId().toString())
        );
    }

    /**
     * {@code GET  /segmento-cnaes} : get all the segmentoCnaes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of segmentoCnaes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SegmentoCnae>> getAllSegmentoCnaes(
        SegmentoCnaeCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SegmentoCnaes by criteria: {}", criteria);

        Page<SegmentoCnae> page = segmentoCnaeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /segmento-cnaes/count} : count all the segmentoCnaes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSegmentoCnaes(SegmentoCnaeCriteria criteria) {
        log.debug("REST request to count SegmentoCnaes by criteria: {}", criteria);
        return ResponseEntity.ok().body(segmentoCnaeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /segmento-cnaes/:id} : get the "id" segmentoCnae.
     *
     * @param id the id of the segmentoCnae to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the segmentoCnae, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SegmentoCnae> getSegmentoCnae(@PathVariable("id") Long id) {
        log.debug("REST request to get SegmentoCnae : {}", id);
        Optional<SegmentoCnae> segmentoCnae = segmentoCnaeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(segmentoCnae);
    }

    /**
     * {@code DELETE  /segmento-cnaes/:id} : delete the "id" segmentoCnae.
     *
     * @param id the id of the segmentoCnae to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSegmentoCnae(@PathVariable("id") Long id) {
        log.debug("REST request to delete SegmentoCnae : {}", id);
        segmentoCnaeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
