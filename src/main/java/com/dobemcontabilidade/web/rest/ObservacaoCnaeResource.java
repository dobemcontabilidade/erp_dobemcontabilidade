package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.ObservacaoCnae;
import com.dobemcontabilidade.repository.ObservacaoCnaeRepository;
import com.dobemcontabilidade.service.ObservacaoCnaeQueryService;
import com.dobemcontabilidade.service.ObservacaoCnaeService;
import com.dobemcontabilidade.service.criteria.ObservacaoCnaeCriteria;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.ObservacaoCnae}.
 */
@RestController
@RequestMapping("/api/observacao-cnaes")
public class ObservacaoCnaeResource {

    private static final Logger log = LoggerFactory.getLogger(ObservacaoCnaeResource.class);

    private static final String ENTITY_NAME = "observacaoCnae";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ObservacaoCnaeService observacaoCnaeService;

    private final ObservacaoCnaeRepository observacaoCnaeRepository;

    private final ObservacaoCnaeQueryService observacaoCnaeQueryService;

    public ObservacaoCnaeResource(
        ObservacaoCnaeService observacaoCnaeService,
        ObservacaoCnaeRepository observacaoCnaeRepository,
        ObservacaoCnaeQueryService observacaoCnaeQueryService
    ) {
        this.observacaoCnaeService = observacaoCnaeService;
        this.observacaoCnaeRepository = observacaoCnaeRepository;
        this.observacaoCnaeQueryService = observacaoCnaeQueryService;
    }

    /**
     * {@code POST  /observacao-cnaes} : Create a new observacaoCnae.
     *
     * @param observacaoCnae the observacaoCnae to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new observacaoCnae, or with status {@code 400 (Bad Request)} if the observacaoCnae has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ObservacaoCnae> createObservacaoCnae(@Valid @RequestBody ObservacaoCnae observacaoCnae)
        throws URISyntaxException {
        log.debug("REST request to save ObservacaoCnae : {}", observacaoCnae);
        if (observacaoCnae.getId() != null) {
            throw new BadRequestAlertException("A new observacaoCnae cannot already have an ID", ENTITY_NAME, "idexists");
        }
        observacaoCnae = observacaoCnaeService.save(observacaoCnae);
        return ResponseEntity.created(new URI("/api/observacao-cnaes/" + observacaoCnae.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, observacaoCnae.getId().toString()))
            .body(observacaoCnae);
    }

    /**
     * {@code PUT  /observacao-cnaes/:id} : Updates an existing observacaoCnae.
     *
     * @param id the id of the observacaoCnae to save.
     * @param observacaoCnae the observacaoCnae to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated observacaoCnae,
     * or with status {@code 400 (Bad Request)} if the observacaoCnae is not valid,
     * or with status {@code 500 (Internal Server Error)} if the observacaoCnae couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ObservacaoCnae> updateObservacaoCnae(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ObservacaoCnae observacaoCnae
    ) throws URISyntaxException {
        log.debug("REST request to update ObservacaoCnae : {}, {}", id, observacaoCnae);
        if (observacaoCnae.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, observacaoCnae.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!observacaoCnaeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        observacaoCnae = observacaoCnaeService.update(observacaoCnae);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, observacaoCnae.getId().toString()))
            .body(observacaoCnae);
    }

    /**
     * {@code PATCH  /observacao-cnaes/:id} : Partial updates given fields of an existing observacaoCnae, field will ignore if it is null
     *
     * @param id the id of the observacaoCnae to save.
     * @param observacaoCnae the observacaoCnae to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated observacaoCnae,
     * or with status {@code 400 (Bad Request)} if the observacaoCnae is not valid,
     * or with status {@code 404 (Not Found)} if the observacaoCnae is not found,
     * or with status {@code 500 (Internal Server Error)} if the observacaoCnae couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ObservacaoCnae> partialUpdateObservacaoCnae(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ObservacaoCnae observacaoCnae
    ) throws URISyntaxException {
        log.debug("REST request to partial update ObservacaoCnae partially : {}, {}", id, observacaoCnae);
        if (observacaoCnae.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, observacaoCnae.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!observacaoCnaeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ObservacaoCnae> result = observacaoCnaeService.partialUpdate(observacaoCnae);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, observacaoCnae.getId().toString())
        );
    }

    /**
     * {@code GET  /observacao-cnaes} : get all the observacaoCnaes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of observacaoCnaes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ObservacaoCnae>> getAllObservacaoCnaes(
        ObservacaoCnaeCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ObservacaoCnaes by criteria: {}", criteria);

        Page<ObservacaoCnae> page = observacaoCnaeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /observacao-cnaes/count} : count all the observacaoCnaes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countObservacaoCnaes(ObservacaoCnaeCriteria criteria) {
        log.debug("REST request to count ObservacaoCnaes by criteria: {}", criteria);
        return ResponseEntity.ok().body(observacaoCnaeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /observacao-cnaes/:id} : get the "id" observacaoCnae.
     *
     * @param id the id of the observacaoCnae to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the observacaoCnae, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ObservacaoCnae> getObservacaoCnae(@PathVariable("id") Long id) {
        log.debug("REST request to get ObservacaoCnae : {}", id);
        Optional<ObservacaoCnae> observacaoCnae = observacaoCnaeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(observacaoCnae);
    }

    /**
     * {@code DELETE  /observacao-cnaes/:id} : delete the "id" observacaoCnae.
     *
     * @param id the id of the observacaoCnae to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObservacaoCnae(@PathVariable("id") Long id) {
        log.debug("REST request to delete ObservacaoCnae : {}", id);
        observacaoCnaeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
