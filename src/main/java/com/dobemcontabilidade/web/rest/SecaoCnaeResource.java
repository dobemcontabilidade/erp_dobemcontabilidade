package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.SecaoCnae;
import com.dobemcontabilidade.repository.SecaoCnaeRepository;
import com.dobemcontabilidade.service.SecaoCnaeQueryService;
import com.dobemcontabilidade.service.SecaoCnaeService;
import com.dobemcontabilidade.service.criteria.SecaoCnaeCriteria;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.SecaoCnae}.
 */
@RestController
@RequestMapping("/api/secao-cnaes")
public class SecaoCnaeResource {

    private static final Logger log = LoggerFactory.getLogger(SecaoCnaeResource.class);

    private static final String ENTITY_NAME = "secaoCnae";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SecaoCnaeService secaoCnaeService;

    private final SecaoCnaeRepository secaoCnaeRepository;

    private final SecaoCnaeQueryService secaoCnaeQueryService;

    public SecaoCnaeResource(
        SecaoCnaeService secaoCnaeService,
        SecaoCnaeRepository secaoCnaeRepository,
        SecaoCnaeQueryService secaoCnaeQueryService
    ) {
        this.secaoCnaeService = secaoCnaeService;
        this.secaoCnaeRepository = secaoCnaeRepository;
        this.secaoCnaeQueryService = secaoCnaeQueryService;
    }

    /**
     * {@code POST  /secao-cnaes} : Create a new secaoCnae.
     *
     * @param secaoCnae the secaoCnae to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new secaoCnae, or with status {@code 400 (Bad Request)} if the secaoCnae has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SecaoCnae> createSecaoCnae(@Valid @RequestBody SecaoCnae secaoCnae) throws URISyntaxException {
        log.debug("REST request to save SecaoCnae : {}", secaoCnae);
        if (secaoCnae.getId() != null) {
            throw new BadRequestAlertException("A new secaoCnae cannot already have an ID", ENTITY_NAME, "idexists");
        }
        secaoCnae = secaoCnaeService.save(secaoCnae);
        return ResponseEntity.created(new URI("/api/secao-cnaes/" + secaoCnae.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, secaoCnae.getId().toString()))
            .body(secaoCnae);
    }

    /**
     * {@code PUT  /secao-cnaes/:id} : Updates an existing secaoCnae.
     *
     * @param id the id of the secaoCnae to save.
     * @param secaoCnae the secaoCnae to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated secaoCnae,
     * or with status {@code 400 (Bad Request)} if the secaoCnae is not valid,
     * or with status {@code 500 (Internal Server Error)} if the secaoCnae couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SecaoCnae> updateSecaoCnae(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SecaoCnae secaoCnae
    ) throws URISyntaxException {
        log.debug("REST request to update SecaoCnae : {}, {}", id, secaoCnae);
        if (secaoCnae.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, secaoCnae.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!secaoCnaeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        secaoCnae = secaoCnaeService.update(secaoCnae);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, secaoCnae.getId().toString()))
            .body(secaoCnae);
    }

    /**
     * {@code PATCH  /secao-cnaes/:id} : Partial updates given fields of an existing secaoCnae, field will ignore if it is null
     *
     * @param id the id of the secaoCnae to save.
     * @param secaoCnae the secaoCnae to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated secaoCnae,
     * or with status {@code 400 (Bad Request)} if the secaoCnae is not valid,
     * or with status {@code 404 (Not Found)} if the secaoCnae is not found,
     * or with status {@code 500 (Internal Server Error)} if the secaoCnae couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SecaoCnae> partialUpdateSecaoCnae(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SecaoCnae secaoCnae
    ) throws URISyntaxException {
        log.debug("REST request to partial update SecaoCnae partially : {}, {}", id, secaoCnae);
        if (secaoCnae.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, secaoCnae.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!secaoCnaeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SecaoCnae> result = secaoCnaeService.partialUpdate(secaoCnae);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, secaoCnae.getId().toString())
        );
    }

    /**
     * {@code GET  /secao-cnaes} : get all the secaoCnaes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of secaoCnaes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SecaoCnae>> getAllSecaoCnaes(
        SecaoCnaeCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SecaoCnaes by criteria: {}", criteria);

        Page<SecaoCnae> page = secaoCnaeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /secao-cnaes/count} : count all the secaoCnaes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSecaoCnaes(SecaoCnaeCriteria criteria) {
        log.debug("REST request to count SecaoCnaes by criteria: {}", criteria);
        return ResponseEntity.ok().body(secaoCnaeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /secao-cnaes/:id} : get the "id" secaoCnae.
     *
     * @param id the id of the secaoCnae to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the secaoCnae, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SecaoCnae> getSecaoCnae(@PathVariable("id") Long id) {
        log.debug("REST request to get SecaoCnae : {}", id);
        Optional<SecaoCnae> secaoCnae = secaoCnaeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(secaoCnae);
    }

    /**
     * {@code DELETE  /secao-cnaes/:id} : delete the "id" secaoCnae.
     *
     * @param id the id of the secaoCnae to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSecaoCnae(@PathVariable("id") Long id) {
        log.debug("REST request to delete SecaoCnae : {}", id);
        secaoCnaeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
