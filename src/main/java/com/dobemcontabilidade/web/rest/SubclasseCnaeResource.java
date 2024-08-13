package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.SubclasseCnae;
import com.dobemcontabilidade.repository.SubclasseCnaeRepository;
import com.dobemcontabilidade.service.SubclasseCnaeQueryService;
import com.dobemcontabilidade.service.SubclasseCnaeService;
import com.dobemcontabilidade.service.criteria.SubclasseCnaeCriteria;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.SubclasseCnae}.
 */
@RestController
@RequestMapping("/api/subclasse-cnaes")
public class SubclasseCnaeResource {

    private static final Logger log = LoggerFactory.getLogger(SubclasseCnaeResource.class);

    private static final String ENTITY_NAME = "subclasseCnae";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubclasseCnaeService subclasseCnaeService;

    private final SubclasseCnaeRepository subclasseCnaeRepository;

    private final SubclasseCnaeQueryService subclasseCnaeQueryService;

    public SubclasseCnaeResource(
        SubclasseCnaeService subclasseCnaeService,
        SubclasseCnaeRepository subclasseCnaeRepository,
        SubclasseCnaeQueryService subclasseCnaeQueryService
    ) {
        this.subclasseCnaeService = subclasseCnaeService;
        this.subclasseCnaeRepository = subclasseCnaeRepository;
        this.subclasseCnaeQueryService = subclasseCnaeQueryService;
    }

    /**
     * {@code POST  /subclasse-cnaes} : Create a new subclasseCnae.
     *
     * @param subclasseCnae the subclasseCnae to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subclasseCnae, or with status {@code 400 (Bad Request)} if the subclasseCnae has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SubclasseCnae> createSubclasseCnae(@Valid @RequestBody SubclasseCnae subclasseCnae) throws URISyntaxException {
        log.debug("REST request to save SubclasseCnae : {}", subclasseCnae);
        if (subclasseCnae.getId() != null) {
            throw new BadRequestAlertException("A new subclasseCnae cannot already have an ID", ENTITY_NAME, "idexists");
        }
        subclasseCnae = subclasseCnaeService.save(subclasseCnae);
        return ResponseEntity.created(new URI("/api/subclasse-cnaes/" + subclasseCnae.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, subclasseCnae.getId().toString()))
            .body(subclasseCnae);
    }

    /**
     * {@code PUT  /subclasse-cnaes/:id} : Updates an existing subclasseCnae.
     *
     * @param id the id of the subclasseCnae to save.
     * @param subclasseCnae the subclasseCnae to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subclasseCnae,
     * or with status {@code 400 (Bad Request)} if the subclasseCnae is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subclasseCnae couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SubclasseCnae> updateSubclasseCnae(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SubclasseCnae subclasseCnae
    ) throws URISyntaxException {
        log.debug("REST request to update SubclasseCnae : {}, {}", id, subclasseCnae);
        if (subclasseCnae.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subclasseCnae.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subclasseCnaeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        subclasseCnae = subclasseCnaeService.update(subclasseCnae);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subclasseCnae.getId().toString()))
            .body(subclasseCnae);
    }

    /**
     * {@code PATCH  /subclasse-cnaes/:id} : Partial updates given fields of an existing subclasseCnae, field will ignore if it is null
     *
     * @param id the id of the subclasseCnae to save.
     * @param subclasseCnae the subclasseCnae to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subclasseCnae,
     * or with status {@code 400 (Bad Request)} if the subclasseCnae is not valid,
     * or with status {@code 404 (Not Found)} if the subclasseCnae is not found,
     * or with status {@code 500 (Internal Server Error)} if the subclasseCnae couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SubclasseCnae> partialUpdateSubclasseCnae(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SubclasseCnae subclasseCnae
    ) throws URISyntaxException {
        log.debug("REST request to partial update SubclasseCnae partially : {}, {}", id, subclasseCnae);
        if (subclasseCnae.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subclasseCnae.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subclasseCnaeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubclasseCnae> result = subclasseCnaeService.partialUpdate(subclasseCnae);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subclasseCnae.getId().toString())
        );
    }

    /**
     * {@code GET  /subclasse-cnaes} : get all the subclasseCnaes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subclasseCnaes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SubclasseCnae>> getAllSubclasseCnaes(
        SubclasseCnaeCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SubclasseCnaes by criteria: {}", criteria);

        Page<SubclasseCnae> page = subclasseCnaeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /subclasse-cnaes/count} : count all the subclasseCnaes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSubclasseCnaes(SubclasseCnaeCriteria criteria) {
        log.debug("REST request to count SubclasseCnaes by criteria: {}", criteria);
        return ResponseEntity.ok().body(subclasseCnaeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /subclasse-cnaes/:id} : get the "id" subclasseCnae.
     *
     * @param id the id of the subclasseCnae to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subclasseCnae, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SubclasseCnae> getSubclasseCnae(@PathVariable("id") Long id) {
        log.debug("REST request to get SubclasseCnae : {}", id);
        Optional<SubclasseCnae> subclasseCnae = subclasseCnaeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subclasseCnae);
    }

    /**
     * {@code DELETE  /subclasse-cnaes/:id} : delete the "id" subclasseCnae.
     *
     * @param id the id of the subclasseCnae to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubclasseCnae(@PathVariable("id") Long id) {
        log.debug("REST request to delete SubclasseCnae : {}", id);
        subclasseCnaeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
