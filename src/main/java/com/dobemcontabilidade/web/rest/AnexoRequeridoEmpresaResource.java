package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.AnexoRequeridoEmpresa;
import com.dobemcontabilidade.repository.AnexoRequeridoEmpresaRepository;
import com.dobemcontabilidade.service.AnexoRequeridoEmpresaService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AnexoRequeridoEmpresa}.
 */
@RestController
@RequestMapping("/api/anexo-requerido-empresas")
public class AnexoRequeridoEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(AnexoRequeridoEmpresaResource.class);

    private static final String ENTITY_NAME = "anexoRequeridoEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnexoRequeridoEmpresaService anexoRequeridoEmpresaService;

    private final AnexoRequeridoEmpresaRepository anexoRequeridoEmpresaRepository;

    public AnexoRequeridoEmpresaResource(
        AnexoRequeridoEmpresaService anexoRequeridoEmpresaService,
        AnexoRequeridoEmpresaRepository anexoRequeridoEmpresaRepository
    ) {
        this.anexoRequeridoEmpresaService = anexoRequeridoEmpresaService;
        this.anexoRequeridoEmpresaRepository = anexoRequeridoEmpresaRepository;
    }

    /**
     * {@code POST  /anexo-requerido-empresas} : Create a new anexoRequeridoEmpresa.
     *
     * @param anexoRequeridoEmpresa the anexoRequeridoEmpresa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anexoRequeridoEmpresa, or with status {@code 400 (Bad Request)} if the anexoRequeridoEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AnexoRequeridoEmpresa> createAnexoRequeridoEmpresa(
        @Valid @RequestBody AnexoRequeridoEmpresa anexoRequeridoEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to save AnexoRequeridoEmpresa : {}", anexoRequeridoEmpresa);
        if (anexoRequeridoEmpresa.getId() != null) {
            throw new BadRequestAlertException("A new anexoRequeridoEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        anexoRequeridoEmpresa = anexoRequeridoEmpresaService.save(anexoRequeridoEmpresa);
        return ResponseEntity.created(new URI("/api/anexo-requerido-empresas/" + anexoRequeridoEmpresa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, anexoRequeridoEmpresa.getId().toString()))
            .body(anexoRequeridoEmpresa);
    }

    /**
     * {@code PUT  /anexo-requerido-empresas/:id} : Updates an existing anexoRequeridoEmpresa.
     *
     * @param id the id of the anexoRequeridoEmpresa to save.
     * @param anexoRequeridoEmpresa the anexoRequeridoEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anexoRequeridoEmpresa,
     * or with status {@code 400 (Bad Request)} if the anexoRequeridoEmpresa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anexoRequeridoEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AnexoRequeridoEmpresa> updateAnexoRequeridoEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AnexoRequeridoEmpresa anexoRequeridoEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to update AnexoRequeridoEmpresa : {}, {}", id, anexoRequeridoEmpresa);
        if (anexoRequeridoEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anexoRequeridoEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anexoRequeridoEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        anexoRequeridoEmpresa = anexoRequeridoEmpresaService.update(anexoRequeridoEmpresa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anexoRequeridoEmpresa.getId().toString()))
            .body(anexoRequeridoEmpresa);
    }

    /**
     * {@code PATCH  /anexo-requerido-empresas/:id} : Partial updates given fields of an existing anexoRequeridoEmpresa, field will ignore if it is null
     *
     * @param id the id of the anexoRequeridoEmpresa to save.
     * @param anexoRequeridoEmpresa the anexoRequeridoEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anexoRequeridoEmpresa,
     * or with status {@code 400 (Bad Request)} if the anexoRequeridoEmpresa is not valid,
     * or with status {@code 404 (Not Found)} if the anexoRequeridoEmpresa is not found,
     * or with status {@code 500 (Internal Server Error)} if the anexoRequeridoEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AnexoRequeridoEmpresa> partialUpdateAnexoRequeridoEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AnexoRequeridoEmpresa anexoRequeridoEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to partial update AnexoRequeridoEmpresa partially : {}, {}", id, anexoRequeridoEmpresa);
        if (anexoRequeridoEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anexoRequeridoEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anexoRequeridoEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnexoRequeridoEmpresa> result = anexoRequeridoEmpresaService.partialUpdate(anexoRequeridoEmpresa);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anexoRequeridoEmpresa.getId().toString())
        );
    }

    /**
     * {@code GET  /anexo-requerido-empresas} : get all the anexoRequeridoEmpresas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anexoRequeridoEmpresas in body.
     */
    @GetMapping("")
    public List<AnexoRequeridoEmpresa> getAllAnexoRequeridoEmpresas(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all AnexoRequeridoEmpresas");
        return anexoRequeridoEmpresaService.findAll();
    }

    /**
     * {@code GET  /anexo-requerido-empresas/:id} : get the "id" anexoRequeridoEmpresa.
     *
     * @param id the id of the anexoRequeridoEmpresa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anexoRequeridoEmpresa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AnexoRequeridoEmpresa> getAnexoRequeridoEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get AnexoRequeridoEmpresa : {}", id);
        Optional<AnexoRequeridoEmpresa> anexoRequeridoEmpresa = anexoRequeridoEmpresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anexoRequeridoEmpresa);
    }

    /**
     * {@code DELETE  /anexo-requerido-empresas/:id} : delete the "id" anexoRequeridoEmpresa.
     *
     * @param id the id of the anexoRequeridoEmpresa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnexoRequeridoEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete AnexoRequeridoEmpresa : {}", id);
        anexoRequeridoEmpresaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
