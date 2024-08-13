package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.CobrancaEmpresa;
import com.dobemcontabilidade.repository.CobrancaEmpresaRepository;
import com.dobemcontabilidade.service.CobrancaEmpresaService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.CobrancaEmpresa}.
 */
@RestController
@RequestMapping("/api/cobranca-empresas")
public class CobrancaEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(CobrancaEmpresaResource.class);

    private static final String ENTITY_NAME = "cobrancaEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CobrancaEmpresaService cobrancaEmpresaService;

    private final CobrancaEmpresaRepository cobrancaEmpresaRepository;

    public CobrancaEmpresaResource(CobrancaEmpresaService cobrancaEmpresaService, CobrancaEmpresaRepository cobrancaEmpresaRepository) {
        this.cobrancaEmpresaService = cobrancaEmpresaService;
        this.cobrancaEmpresaRepository = cobrancaEmpresaRepository;
    }

    /**
     * {@code POST  /cobranca-empresas} : Create a new cobrancaEmpresa.
     *
     * @param cobrancaEmpresa the cobrancaEmpresa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cobrancaEmpresa, or with status {@code 400 (Bad Request)} if the cobrancaEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CobrancaEmpresa> createCobrancaEmpresa(@Valid @RequestBody CobrancaEmpresa cobrancaEmpresa)
        throws URISyntaxException {
        log.debug("REST request to save CobrancaEmpresa : {}", cobrancaEmpresa);
        if (cobrancaEmpresa.getId() != null) {
            throw new BadRequestAlertException("A new cobrancaEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cobrancaEmpresa = cobrancaEmpresaService.save(cobrancaEmpresa);
        return ResponseEntity.created(new URI("/api/cobranca-empresas/" + cobrancaEmpresa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cobrancaEmpresa.getId().toString()))
            .body(cobrancaEmpresa);
    }

    /**
     * {@code PUT  /cobranca-empresas/:id} : Updates an existing cobrancaEmpresa.
     *
     * @param id the id of the cobrancaEmpresa to save.
     * @param cobrancaEmpresa the cobrancaEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cobrancaEmpresa,
     * or with status {@code 400 (Bad Request)} if the cobrancaEmpresa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cobrancaEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CobrancaEmpresa> updateCobrancaEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CobrancaEmpresa cobrancaEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to update CobrancaEmpresa : {}, {}", id, cobrancaEmpresa);
        if (cobrancaEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cobrancaEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cobrancaEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cobrancaEmpresa = cobrancaEmpresaService.update(cobrancaEmpresa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cobrancaEmpresa.getId().toString()))
            .body(cobrancaEmpresa);
    }

    /**
     * {@code PATCH  /cobranca-empresas/:id} : Partial updates given fields of an existing cobrancaEmpresa, field will ignore if it is null
     *
     * @param id the id of the cobrancaEmpresa to save.
     * @param cobrancaEmpresa the cobrancaEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cobrancaEmpresa,
     * or with status {@code 400 (Bad Request)} if the cobrancaEmpresa is not valid,
     * or with status {@code 404 (Not Found)} if the cobrancaEmpresa is not found,
     * or with status {@code 500 (Internal Server Error)} if the cobrancaEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CobrancaEmpresa> partialUpdateCobrancaEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CobrancaEmpresa cobrancaEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to partial update CobrancaEmpresa partially : {}, {}", id, cobrancaEmpresa);
        if (cobrancaEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cobrancaEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cobrancaEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CobrancaEmpresa> result = cobrancaEmpresaService.partialUpdate(cobrancaEmpresa);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cobrancaEmpresa.getId().toString())
        );
    }

    /**
     * {@code GET  /cobranca-empresas} : get all the cobrancaEmpresas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cobrancaEmpresas in body.
     */
    @GetMapping("")
    public List<CobrancaEmpresa> getAllCobrancaEmpresas(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all CobrancaEmpresas");
        return cobrancaEmpresaService.findAll();
    }

    /**
     * {@code GET  /cobranca-empresas/:id} : get the "id" cobrancaEmpresa.
     *
     * @param id the id of the cobrancaEmpresa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cobrancaEmpresa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CobrancaEmpresa> getCobrancaEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get CobrancaEmpresa : {}", id);
        Optional<CobrancaEmpresa> cobrancaEmpresa = cobrancaEmpresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cobrancaEmpresa);
    }

    /**
     * {@code DELETE  /cobranca-empresas/:id} : delete the "id" cobrancaEmpresa.
     *
     * @param id the id of the cobrancaEmpresa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCobrancaEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete CobrancaEmpresa : {}", id);
        cobrancaEmpresaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
