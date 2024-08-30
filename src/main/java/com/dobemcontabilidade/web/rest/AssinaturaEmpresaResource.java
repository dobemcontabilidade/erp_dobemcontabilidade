package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.AssinaturaEmpresa;
import com.dobemcontabilidade.repository.AssinaturaEmpresaRepository;
import com.dobemcontabilidade.service.AssinaturaEmpresaQueryService;
import com.dobemcontabilidade.service.AssinaturaEmpresaService;
import com.dobemcontabilidade.service.criteria.AssinaturaEmpresaCriteria;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AssinaturaEmpresa}.
 */
@RestController
@RequestMapping("/api/assinatura-empresas")
public class AssinaturaEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(AssinaturaEmpresaResource.class);

    private static final String ENTITY_NAME = "assinaturaEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssinaturaEmpresaService assinaturaEmpresaService;

    private final AssinaturaEmpresaRepository assinaturaEmpresaRepository;

    private final AssinaturaEmpresaQueryService assinaturaEmpresaQueryService;

    public AssinaturaEmpresaResource(
        AssinaturaEmpresaService assinaturaEmpresaService,
        AssinaturaEmpresaRepository assinaturaEmpresaRepository,
        AssinaturaEmpresaQueryService assinaturaEmpresaQueryService
    ) {
        this.assinaturaEmpresaService = assinaturaEmpresaService;
        this.assinaturaEmpresaRepository = assinaturaEmpresaRepository;
        this.assinaturaEmpresaQueryService = assinaturaEmpresaQueryService;
    }

    /**
     * {@code POST  /assinatura-empresas} : Create a new assinaturaEmpresa.
     *
     * @param assinaturaEmpresa the assinaturaEmpresa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assinaturaEmpresa, or with status {@code 400 (Bad Request)} if the assinaturaEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AssinaturaEmpresa> createAssinaturaEmpresa(@Valid @RequestBody AssinaturaEmpresa assinaturaEmpresa)
        throws URISyntaxException {
        log.debug("REST request to save AssinaturaEmpresa : {}", assinaturaEmpresa);
        if (assinaturaEmpresa.getId() != null) {
            throw new BadRequestAlertException("A new assinaturaEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        assinaturaEmpresa = assinaturaEmpresaService.save(assinaturaEmpresa);
        return ResponseEntity.created(new URI("/api/assinatura-empresas/" + assinaturaEmpresa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, assinaturaEmpresa.getId().toString()))
            .body(assinaturaEmpresa);
    }

    /**
     * {@code PUT  /assinatura-empresas/:id} : Updates an existing assinaturaEmpresa.
     *
     * @param id the id of the assinaturaEmpresa to save.
     * @param assinaturaEmpresa the assinaturaEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assinaturaEmpresa,
     * or with status {@code 400 (Bad Request)} if the assinaturaEmpresa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assinaturaEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AssinaturaEmpresa> updateAssinaturaEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AssinaturaEmpresa assinaturaEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to update AssinaturaEmpresa : {}, {}", id, assinaturaEmpresa);
        if (assinaturaEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assinaturaEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assinaturaEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        assinaturaEmpresa = assinaturaEmpresaService.update(assinaturaEmpresa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assinaturaEmpresa.getId().toString()))
            .body(assinaturaEmpresa);
    }

    /**
     * {@code PATCH  /assinatura-empresas/:id} : Partial updates given fields of an existing assinaturaEmpresa, field will ignore if it is null
     *
     * @param id the id of the assinaturaEmpresa to save.
     * @param assinaturaEmpresa the assinaturaEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assinaturaEmpresa,
     * or with status {@code 400 (Bad Request)} if the assinaturaEmpresa is not valid,
     * or with status {@code 404 (Not Found)} if the assinaturaEmpresa is not found,
     * or with status {@code 500 (Internal Server Error)} if the assinaturaEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AssinaturaEmpresa> partialUpdateAssinaturaEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AssinaturaEmpresa assinaturaEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to partial update AssinaturaEmpresa partially : {}, {}", id, assinaturaEmpresa);
        if (assinaturaEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assinaturaEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assinaturaEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AssinaturaEmpresa> result = assinaturaEmpresaService.partialUpdate(assinaturaEmpresa);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assinaturaEmpresa.getId().toString())
        );
    }

    /**
     * {@code GET  /assinatura-empresas} : get all the assinaturaEmpresas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assinaturaEmpresas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AssinaturaEmpresa>> getAllAssinaturaEmpresas(AssinaturaEmpresaCriteria criteria) {
        log.debug("REST request to get AssinaturaEmpresas by criteria: {}", criteria);

        List<AssinaturaEmpresa> entityList = assinaturaEmpresaQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /assinatura-empresas/count} : count all the assinaturaEmpresas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAssinaturaEmpresas(AssinaturaEmpresaCriteria criteria) {
        log.debug("REST request to count AssinaturaEmpresas by criteria: {}", criteria);
        return ResponseEntity.ok().body(assinaturaEmpresaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /assinatura-empresas/:id} : get the "id" assinaturaEmpresa.
     *
     * @param id the id of the assinaturaEmpresa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assinaturaEmpresa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AssinaturaEmpresa> getAssinaturaEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get AssinaturaEmpresa : {}", id);
        Optional<AssinaturaEmpresa> assinaturaEmpresa = assinaturaEmpresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assinaturaEmpresa);
    }

    /**
     * {@code DELETE  /assinatura-empresas/:id} : delete the "id" assinaturaEmpresa.
     *
     * @param id the id of the assinaturaEmpresa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssinaturaEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete AssinaturaEmpresa : {}", id);
        assinaturaEmpresaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
