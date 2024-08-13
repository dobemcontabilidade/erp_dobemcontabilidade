package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.AnexoRequerido;
import com.dobemcontabilidade.repository.AnexoRequeridoRepository;
import com.dobemcontabilidade.service.AnexoRequeridoQueryService;
import com.dobemcontabilidade.service.AnexoRequeridoService;
import com.dobemcontabilidade.service.criteria.AnexoRequeridoCriteria;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dobemcontabilidade.domain.AnexoRequerido}.
 */
@RestController
@RequestMapping("/api/anexo-requeridos")
public class AnexoRequeridoResource {

    private static final Logger log = LoggerFactory.getLogger(AnexoRequeridoResource.class);

    private static final String ENTITY_NAME = "anexoRequerido";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnexoRequeridoService anexoRequeridoService;

    private final AnexoRequeridoRepository anexoRequeridoRepository;

    private final AnexoRequeridoQueryService anexoRequeridoQueryService;

    public AnexoRequeridoResource(
        AnexoRequeridoService anexoRequeridoService,
        AnexoRequeridoRepository anexoRequeridoRepository,
        AnexoRequeridoQueryService anexoRequeridoQueryService
    ) {
        this.anexoRequeridoService = anexoRequeridoService;
        this.anexoRequeridoRepository = anexoRequeridoRepository;
        this.anexoRequeridoQueryService = anexoRequeridoQueryService;
    }

    /**
     * {@code POST  /anexo-requeridos} : Create a new anexoRequerido.
     *
     * @param anexoRequerido the anexoRequerido to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anexoRequerido, or with status {@code 400 (Bad Request)} if the anexoRequerido has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AnexoRequerido> createAnexoRequerido(@Valid @RequestBody AnexoRequerido anexoRequerido)
        throws URISyntaxException {
        log.debug("REST request to save AnexoRequerido : {}", anexoRequerido);
        if (anexoRequerido.getId() != null) {
            throw new BadRequestAlertException("A new anexoRequerido cannot already have an ID", ENTITY_NAME, "idexists");
        }
        anexoRequerido = anexoRequeridoService.save(anexoRequerido);
        return ResponseEntity.created(new URI("/api/anexo-requeridos/" + anexoRequerido.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, anexoRequerido.getId().toString()))
            .body(anexoRequerido);
    }

    /**
     * {@code PUT  /anexo-requeridos/:id} : Updates an existing anexoRequerido.
     *
     * @param id the id of the anexoRequerido to save.
     * @param anexoRequerido the anexoRequerido to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anexoRequerido,
     * or with status {@code 400 (Bad Request)} if the anexoRequerido is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anexoRequerido couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AnexoRequerido> updateAnexoRequerido(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AnexoRequerido anexoRequerido
    ) throws URISyntaxException {
        log.debug("REST request to update AnexoRequerido : {}, {}", id, anexoRequerido);
        if (anexoRequerido.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anexoRequerido.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anexoRequeridoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        anexoRequerido = anexoRequeridoService.update(anexoRequerido);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anexoRequerido.getId().toString()))
            .body(anexoRequerido);
    }

    /**
     * {@code PATCH  /anexo-requeridos/:id} : Partial updates given fields of an existing anexoRequerido, field will ignore if it is null
     *
     * @param id the id of the anexoRequerido to save.
     * @param anexoRequerido the anexoRequerido to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anexoRequerido,
     * or with status {@code 400 (Bad Request)} if the anexoRequerido is not valid,
     * or with status {@code 404 (Not Found)} if the anexoRequerido is not found,
     * or with status {@code 500 (Internal Server Error)} if the anexoRequerido couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AnexoRequerido> partialUpdateAnexoRequerido(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AnexoRequerido anexoRequerido
    ) throws URISyntaxException {
        log.debug("REST request to partial update AnexoRequerido partially : {}, {}", id, anexoRequerido);
        if (anexoRequerido.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anexoRequerido.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anexoRequeridoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnexoRequerido> result = anexoRequeridoService.partialUpdate(anexoRequerido);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anexoRequerido.getId().toString())
        );
    }

    /**
     * {@code GET  /anexo-requeridos} : get all the anexoRequeridos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anexoRequeridos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AnexoRequerido>> getAllAnexoRequeridos(AnexoRequeridoCriteria criteria) {
        log.debug("REST request to get AnexoRequeridos by criteria: {}", criteria);

        List<AnexoRequerido> entityList = anexoRequeridoQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /anexo-requeridos/count} : count all the anexoRequeridos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAnexoRequeridos(AnexoRequeridoCriteria criteria) {
        log.debug("REST request to count AnexoRequeridos by criteria: {}", criteria);
        return ResponseEntity.ok().body(anexoRequeridoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /anexo-requeridos/:id} : get the "id" anexoRequerido.
     *
     * @param id the id of the anexoRequerido to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anexoRequerido, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AnexoRequerido> getAnexoRequerido(@PathVariable("id") Long id) {
        log.debug("REST request to get AnexoRequerido : {}", id);
        Optional<AnexoRequerido> anexoRequerido = anexoRequeridoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anexoRequerido);
    }

    /**
     * {@code DELETE  /anexo-requeridos/:id} : delete the "id" anexoRequerido.
     *
     * @param id the id of the anexoRequerido to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnexoRequerido(@PathVariable("id") Long id) {
        log.debug("REST request to delete AnexoRequerido : {}", id);
        anexoRequeridoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
