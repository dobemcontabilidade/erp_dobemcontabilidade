package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.Frequencia;
import com.dobemcontabilidade.repository.FrequenciaRepository;
import com.dobemcontabilidade.service.FrequenciaQueryService;
import com.dobemcontabilidade.service.FrequenciaService;
import com.dobemcontabilidade.service.criteria.FrequenciaCriteria;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Frequencia}.
 */
@RestController
@RequestMapping("/api/frequencias")
public class FrequenciaResource {

    private static final Logger log = LoggerFactory.getLogger(FrequenciaResource.class);

    private static final String ENTITY_NAME = "frequencia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FrequenciaService frequenciaService;

    private final FrequenciaRepository frequenciaRepository;

    private final FrequenciaQueryService frequenciaQueryService;

    public FrequenciaResource(
        FrequenciaService frequenciaService,
        FrequenciaRepository frequenciaRepository,
        FrequenciaQueryService frequenciaQueryService
    ) {
        this.frequenciaService = frequenciaService;
        this.frequenciaRepository = frequenciaRepository;
        this.frequenciaQueryService = frequenciaQueryService;
    }

    /**
     * {@code POST  /frequencias} : Create a new frequencia.
     *
     * @param frequencia the frequencia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new frequencia, or with status {@code 400 (Bad Request)} if the frequencia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Frequencia> createFrequencia(@RequestBody Frequencia frequencia) throws URISyntaxException {
        log.debug("REST request to save Frequencia : {}", frequencia);
        if (frequencia.getId() != null) {
            throw new BadRequestAlertException("A new frequencia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        frequencia = frequenciaService.save(frequencia);
        return ResponseEntity.created(new URI("/api/frequencias/" + frequencia.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, frequencia.getId().toString()))
            .body(frequencia);
    }

    /**
     * {@code PUT  /frequencias/:id} : Updates an existing frequencia.
     *
     * @param id the id of the frequencia to save.
     * @param frequencia the frequencia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated frequencia,
     * or with status {@code 400 (Bad Request)} if the frequencia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the frequencia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Frequencia> updateFrequencia(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Frequencia frequencia
    ) throws URISyntaxException {
        log.debug("REST request to update Frequencia : {}, {}", id, frequencia);
        if (frequencia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, frequencia.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!frequenciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        frequencia = frequenciaService.update(frequencia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, frequencia.getId().toString()))
            .body(frequencia);
    }

    /**
     * {@code PATCH  /frequencias/:id} : Partial updates given fields of an existing frequencia, field will ignore if it is null
     *
     * @param id the id of the frequencia to save.
     * @param frequencia the frequencia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated frequencia,
     * or with status {@code 400 (Bad Request)} if the frequencia is not valid,
     * or with status {@code 404 (Not Found)} if the frequencia is not found,
     * or with status {@code 500 (Internal Server Error)} if the frequencia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Frequencia> partialUpdateFrequencia(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Frequencia frequencia
    ) throws URISyntaxException {
        log.debug("REST request to partial update Frequencia partially : {}, {}", id, frequencia);
        if (frequencia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, frequencia.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!frequenciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Frequencia> result = frequenciaService.partialUpdate(frequencia);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, frequencia.getId().toString())
        );
    }

    /**
     * {@code GET  /frequencias} : get all the frequencias.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of frequencias in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Frequencia>> getAllFrequencias(
        FrequenciaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Frequencias by criteria: {}", criteria);

        Page<Frequencia> page = frequenciaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /frequencias/count} : count all the frequencias.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countFrequencias(FrequenciaCriteria criteria) {
        log.debug("REST request to count Frequencias by criteria: {}", criteria);
        return ResponseEntity.ok().body(frequenciaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /frequencias/:id} : get the "id" frequencia.
     *
     * @param id the id of the frequencia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the frequencia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Frequencia> getFrequencia(@PathVariable("id") Long id) {
        log.debug("REST request to get Frequencia : {}", id);
        Optional<Frequencia> frequencia = frequenciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(frequencia);
    }

    /**
     * {@code DELETE  /frequencias/:id} : delete the "id" frequencia.
     *
     * @param id the id of the frequencia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFrequencia(@PathVariable("id") Long id) {
        log.debug("REST request to delete Frequencia : {}", id);
        frequenciaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
