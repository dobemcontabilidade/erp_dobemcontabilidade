package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.Competencia;
import com.dobemcontabilidade.repository.CompetenciaRepository;
import com.dobemcontabilidade.service.CompetenciaQueryService;
import com.dobemcontabilidade.service.CompetenciaService;
import com.dobemcontabilidade.service.criteria.CompetenciaCriteria;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Competencia}.
 */
@RestController
@RequestMapping("/api/competencias")
public class CompetenciaResource {

    private static final Logger log = LoggerFactory.getLogger(CompetenciaResource.class);

    private static final String ENTITY_NAME = "competencia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetenciaService competenciaService;

    private final CompetenciaRepository competenciaRepository;

    private final CompetenciaQueryService competenciaQueryService;

    public CompetenciaResource(
        CompetenciaService competenciaService,
        CompetenciaRepository competenciaRepository,
        CompetenciaQueryService competenciaQueryService
    ) {
        this.competenciaService = competenciaService;
        this.competenciaRepository = competenciaRepository;
        this.competenciaQueryService = competenciaQueryService;
    }

    /**
     * {@code POST  /competencias} : Create a new competencia.
     *
     * @param competencia the competencia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competencia, or with status {@code 400 (Bad Request)} if the competencia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Competencia> createCompetencia(@RequestBody Competencia competencia) throws URISyntaxException {
        log.debug("REST request to save Competencia : {}", competencia);
        if (competencia.getId() != null) {
            throw new BadRequestAlertException("A new competencia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        competencia = competenciaService.save(competencia);
        return ResponseEntity.created(new URI("/api/competencias/" + competencia.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, competencia.getId().toString()))
            .body(competencia);
    }

    /**
     * {@code PUT  /competencias/:id} : Updates an existing competencia.
     *
     * @param id the id of the competencia to save.
     * @param competencia the competencia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competencia,
     * or with status {@code 400 (Bad Request)} if the competencia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competencia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Competencia> updateCompetencia(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Competencia competencia
    ) throws URISyntaxException {
        log.debug("REST request to update Competencia : {}, {}", id, competencia);
        if (competencia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, competencia.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!competenciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        competencia = competenciaService.update(competencia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competencia.getId().toString()))
            .body(competencia);
    }

    /**
     * {@code PATCH  /competencias/:id} : Partial updates given fields of an existing competencia, field will ignore if it is null
     *
     * @param id the id of the competencia to save.
     * @param competencia the competencia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competencia,
     * or with status {@code 400 (Bad Request)} if the competencia is not valid,
     * or with status {@code 404 (Not Found)} if the competencia is not found,
     * or with status {@code 500 (Internal Server Error)} if the competencia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Competencia> partialUpdateCompetencia(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Competencia competencia
    ) throws URISyntaxException {
        log.debug("REST request to partial update Competencia partially : {}, {}", id, competencia);
        if (competencia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, competencia.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!competenciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Competencia> result = competenciaService.partialUpdate(competencia);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competencia.getId().toString())
        );
    }

    /**
     * {@code GET  /competencias} : get all the competencias.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competencias in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Competencia>> getAllCompetencias(
        CompetenciaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Competencias by criteria: {}", criteria);

        Page<Competencia> page = competenciaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /competencias/count} : count all the competencias.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countCompetencias(CompetenciaCriteria criteria) {
        log.debug("REST request to count Competencias by criteria: {}", criteria);
        return ResponseEntity.ok().body(competenciaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /competencias/:id} : get the "id" competencia.
     *
     * @param id the id of the competencia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competencia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Competencia> getCompetencia(@PathVariable("id") Long id) {
        log.debug("REST request to get Competencia : {}", id);
        Optional<Competencia> competencia = competenciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(competencia);
    }

    /**
     * {@code DELETE  /competencias/:id} : delete the "id" competencia.
     *
     * @param id the id of the competencia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompetencia(@PathVariable("id") Long id) {
        log.debug("REST request to delete Competencia : {}", id);
        competenciaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
