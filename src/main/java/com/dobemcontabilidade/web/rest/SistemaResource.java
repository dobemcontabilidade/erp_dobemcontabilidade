package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.Sistema;
import com.dobemcontabilidade.repository.SistemaRepository;
import com.dobemcontabilidade.service.SistemaQueryService;
import com.dobemcontabilidade.service.SistemaService;
import com.dobemcontabilidade.service.criteria.SistemaCriteria;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Sistema}.
 */
@RestController
@RequestMapping("/api/sistemas")
public class SistemaResource {

    private static final Logger log = LoggerFactory.getLogger(SistemaResource.class);

    private static final String ENTITY_NAME = "sistema";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SistemaService sistemaService;

    private final SistemaRepository sistemaRepository;

    private final SistemaQueryService sistemaQueryService;

    public SistemaResource(SistemaService sistemaService, SistemaRepository sistemaRepository, SistemaQueryService sistemaQueryService) {
        this.sistemaService = sistemaService;
        this.sistemaRepository = sistemaRepository;
        this.sistemaQueryService = sistemaQueryService;
    }

    /**
     * {@code POST  /sistemas} : Create a new sistema.
     *
     * @param sistema the sistema to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sistema, or with status {@code 400 (Bad Request)} if the sistema has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Sistema> createSistema(@RequestBody Sistema sistema) throws URISyntaxException {
        log.debug("REST request to save Sistema : {}", sistema);
        if (sistema.getId() != null) {
            throw new BadRequestAlertException("A new sistema cannot already have an ID", ENTITY_NAME, "idexists");
        }
        sistema = sistemaService.save(sistema);
        return ResponseEntity.created(new URI("/api/sistemas/" + sistema.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, sistema.getId().toString()))
            .body(sistema);
    }

    /**
     * {@code PUT  /sistemas/:id} : Updates an existing sistema.
     *
     * @param id the id of the sistema to save.
     * @param sistema the sistema to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sistema,
     * or with status {@code 400 (Bad Request)} if the sistema is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sistema couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Sistema> updateSistema(@PathVariable(value = "id", required = false) final Long id, @RequestBody Sistema sistema)
        throws URISyntaxException {
        log.debug("REST request to update Sistema : {}, {}", id, sistema);
        if (sistema.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sistema.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sistemaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        sistema = sistemaService.update(sistema);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sistema.getId().toString()))
            .body(sistema);
    }

    /**
     * {@code PATCH  /sistemas/:id} : Partial updates given fields of an existing sistema, field will ignore if it is null
     *
     * @param id the id of the sistema to save.
     * @param sistema the sistema to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sistema,
     * or with status {@code 400 (Bad Request)} if the sistema is not valid,
     * or with status {@code 404 (Not Found)} if the sistema is not found,
     * or with status {@code 500 (Internal Server Error)} if the sistema couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Sistema> partialUpdateSistema(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Sistema sistema
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sistema partially : {}, {}", id, sistema);
        if (sistema.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sistema.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sistemaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Sistema> result = sistemaService.partialUpdate(sistema);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sistema.getId().toString())
        );
    }

    /**
     * {@code GET  /sistemas} : get all the sistemas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sistemas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Sistema>> getAllSistemas(
        SistemaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Sistemas by criteria: {}", criteria);

        Page<Sistema> page = sistemaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sistemas/count} : count all the sistemas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSistemas(SistemaCriteria criteria) {
        log.debug("REST request to count Sistemas by criteria: {}", criteria);
        return ResponseEntity.ok().body(sistemaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sistemas/:id} : get the "id" sistema.
     *
     * @param id the id of the sistema to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sistema, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Sistema> getSistema(@PathVariable("id") Long id) {
        log.debug("REST request to get Sistema : {}", id);
        Optional<Sistema> sistema = sistemaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sistema);
    }

    /**
     * {@code DELETE  /sistemas/:id} : delete the "id" sistema.
     *
     * @param id the id of the sistema to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSistema(@PathVariable("id") Long id) {
        log.debug("REST request to delete Sistema : {}", id);
        sistemaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
