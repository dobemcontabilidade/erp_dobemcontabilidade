package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.AreaContabilAssinaturaEmpresa;
import com.dobemcontabilidade.repository.AreaContabilAssinaturaEmpresaRepository;
import com.dobemcontabilidade.service.AreaContabilAssinaturaEmpresaService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AreaContabilAssinaturaEmpresa}.
 */
@RestController
@RequestMapping("/api/area-contabil-assinatura-empresas")
public class AreaContabilAssinaturaEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(AreaContabilAssinaturaEmpresaResource.class);

    private static final String ENTITY_NAME = "areaContabilAssinaturaEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AreaContabilAssinaturaEmpresaService areaContabilAssinaturaEmpresaService;

    private final AreaContabilAssinaturaEmpresaRepository areaContabilAssinaturaEmpresaRepository;

    public AreaContabilAssinaturaEmpresaResource(
        AreaContabilAssinaturaEmpresaService areaContabilAssinaturaEmpresaService,
        AreaContabilAssinaturaEmpresaRepository areaContabilAssinaturaEmpresaRepository
    ) {
        this.areaContabilAssinaturaEmpresaService = areaContabilAssinaturaEmpresaService;
        this.areaContabilAssinaturaEmpresaRepository = areaContabilAssinaturaEmpresaRepository;
    }

    /**
     * {@code POST  /area-contabil-assinatura-empresas} : Create a new areaContabilAssinaturaEmpresa.
     *
     * @param areaContabilAssinaturaEmpresa the areaContabilAssinaturaEmpresa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new areaContabilAssinaturaEmpresa, or with status {@code 400 (Bad Request)} if the areaContabilAssinaturaEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AreaContabilAssinaturaEmpresa> createAreaContabilAssinaturaEmpresa(
        @Valid @RequestBody AreaContabilAssinaturaEmpresa areaContabilAssinaturaEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to save AreaContabilAssinaturaEmpresa : {}", areaContabilAssinaturaEmpresa);
        if (areaContabilAssinaturaEmpresa.getId() != null) {
            throw new BadRequestAlertException("A new areaContabilAssinaturaEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        areaContabilAssinaturaEmpresa = areaContabilAssinaturaEmpresaService.save(areaContabilAssinaturaEmpresa);
        return ResponseEntity.created(new URI("/api/area-contabil-assinatura-empresas/" + areaContabilAssinaturaEmpresa.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, areaContabilAssinaturaEmpresa.getId().toString())
            )
            .body(areaContabilAssinaturaEmpresa);
    }

    /**
     * {@code PUT  /area-contabil-assinatura-empresas/:id} : Updates an existing areaContabilAssinaturaEmpresa.
     *
     * @param id the id of the areaContabilAssinaturaEmpresa to save.
     * @param areaContabilAssinaturaEmpresa the areaContabilAssinaturaEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated areaContabilAssinaturaEmpresa,
     * or with status {@code 400 (Bad Request)} if the areaContabilAssinaturaEmpresa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the areaContabilAssinaturaEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AreaContabilAssinaturaEmpresa> updateAreaContabilAssinaturaEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AreaContabilAssinaturaEmpresa areaContabilAssinaturaEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to update AreaContabilAssinaturaEmpresa : {}, {}", id, areaContabilAssinaturaEmpresa);
        if (areaContabilAssinaturaEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, areaContabilAssinaturaEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!areaContabilAssinaturaEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        areaContabilAssinaturaEmpresa = areaContabilAssinaturaEmpresaService.update(areaContabilAssinaturaEmpresa);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, areaContabilAssinaturaEmpresa.getId().toString())
            )
            .body(areaContabilAssinaturaEmpresa);
    }

    /**
     * {@code PATCH  /area-contabil-assinatura-empresas/:id} : Partial updates given fields of an existing areaContabilAssinaturaEmpresa, field will ignore if it is null
     *
     * @param id the id of the areaContabilAssinaturaEmpresa to save.
     * @param areaContabilAssinaturaEmpresa the areaContabilAssinaturaEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated areaContabilAssinaturaEmpresa,
     * or with status {@code 400 (Bad Request)} if the areaContabilAssinaturaEmpresa is not valid,
     * or with status {@code 404 (Not Found)} if the areaContabilAssinaturaEmpresa is not found,
     * or with status {@code 500 (Internal Server Error)} if the areaContabilAssinaturaEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AreaContabilAssinaturaEmpresa> partialUpdateAreaContabilAssinaturaEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AreaContabilAssinaturaEmpresa areaContabilAssinaturaEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to partial update AreaContabilAssinaturaEmpresa partially : {}, {}", id, areaContabilAssinaturaEmpresa);
        if (areaContabilAssinaturaEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, areaContabilAssinaturaEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!areaContabilAssinaturaEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AreaContabilAssinaturaEmpresa> result = areaContabilAssinaturaEmpresaService.partialUpdate(areaContabilAssinaturaEmpresa);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, areaContabilAssinaturaEmpresa.getId().toString())
        );
    }

    /**
     * {@code GET  /area-contabil-assinatura-empresas} : get all the areaContabilAssinaturaEmpresas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of areaContabilAssinaturaEmpresas in body.
     */
    @GetMapping("")
    public List<AreaContabilAssinaturaEmpresa> getAllAreaContabilAssinaturaEmpresas(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all AreaContabilAssinaturaEmpresas");
        return areaContabilAssinaturaEmpresaService.findAll();
    }

    /**
     * {@code GET  /area-contabil-assinatura-empresas/:id} : get the "id" areaContabilAssinaturaEmpresa.
     *
     * @param id the id of the areaContabilAssinaturaEmpresa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the areaContabilAssinaturaEmpresa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AreaContabilAssinaturaEmpresa> getAreaContabilAssinaturaEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get AreaContabilAssinaturaEmpresa : {}", id);
        Optional<AreaContabilAssinaturaEmpresa> areaContabilAssinaturaEmpresa = areaContabilAssinaturaEmpresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(areaContabilAssinaturaEmpresa);
    }

    /**
     * {@code DELETE  /area-contabil-assinatura-empresas/:id} : delete the "id" areaContabilAssinaturaEmpresa.
     *
     * @param id the id of the areaContabilAssinaturaEmpresa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAreaContabilAssinaturaEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete AreaContabilAssinaturaEmpresa : {}", id);
        areaContabilAssinaturaEmpresaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
