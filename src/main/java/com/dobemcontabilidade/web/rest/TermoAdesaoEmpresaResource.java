package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.TermoAdesaoEmpresa;
import com.dobemcontabilidade.repository.TermoAdesaoEmpresaRepository;
import com.dobemcontabilidade.service.TermoAdesaoEmpresaService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.TermoAdesaoEmpresa}.
 */
@RestController
@RequestMapping("/api/termo-adesao-empresas")
public class TermoAdesaoEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(TermoAdesaoEmpresaResource.class);

    private static final String ENTITY_NAME = "termoAdesaoEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TermoAdesaoEmpresaService termoAdesaoEmpresaService;

    private final TermoAdesaoEmpresaRepository termoAdesaoEmpresaRepository;

    public TermoAdesaoEmpresaResource(
        TermoAdesaoEmpresaService termoAdesaoEmpresaService,
        TermoAdesaoEmpresaRepository termoAdesaoEmpresaRepository
    ) {
        this.termoAdesaoEmpresaService = termoAdesaoEmpresaService;
        this.termoAdesaoEmpresaRepository = termoAdesaoEmpresaRepository;
    }

    /**
     * {@code POST  /termo-adesao-empresas} : Create a new termoAdesaoEmpresa.
     *
     * @param termoAdesaoEmpresa the termoAdesaoEmpresa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new termoAdesaoEmpresa, or with status {@code 400 (Bad Request)} if the termoAdesaoEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TermoAdesaoEmpresa> createTermoAdesaoEmpresa(@Valid @RequestBody TermoAdesaoEmpresa termoAdesaoEmpresa)
        throws URISyntaxException {
        log.debug("REST request to save TermoAdesaoEmpresa : {}", termoAdesaoEmpresa);
        if (termoAdesaoEmpresa.getId() != null) {
            throw new BadRequestAlertException("A new termoAdesaoEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        termoAdesaoEmpresa = termoAdesaoEmpresaService.save(termoAdesaoEmpresa);
        return ResponseEntity.created(new URI("/api/termo-adesao-empresas/" + termoAdesaoEmpresa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, termoAdesaoEmpresa.getId().toString()))
            .body(termoAdesaoEmpresa);
    }

    /**
     * {@code PUT  /termo-adesao-empresas/:id} : Updates an existing termoAdesaoEmpresa.
     *
     * @param id the id of the termoAdesaoEmpresa to save.
     * @param termoAdesaoEmpresa the termoAdesaoEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated termoAdesaoEmpresa,
     * or with status {@code 400 (Bad Request)} if the termoAdesaoEmpresa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the termoAdesaoEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TermoAdesaoEmpresa> updateTermoAdesaoEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TermoAdesaoEmpresa termoAdesaoEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to update TermoAdesaoEmpresa : {}, {}", id, termoAdesaoEmpresa);
        if (termoAdesaoEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, termoAdesaoEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!termoAdesaoEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        termoAdesaoEmpresa = termoAdesaoEmpresaService.update(termoAdesaoEmpresa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, termoAdesaoEmpresa.getId().toString()))
            .body(termoAdesaoEmpresa);
    }

    /**
     * {@code PATCH  /termo-adesao-empresas/:id} : Partial updates given fields of an existing termoAdesaoEmpresa, field will ignore if it is null
     *
     * @param id the id of the termoAdesaoEmpresa to save.
     * @param termoAdesaoEmpresa the termoAdesaoEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated termoAdesaoEmpresa,
     * or with status {@code 400 (Bad Request)} if the termoAdesaoEmpresa is not valid,
     * or with status {@code 404 (Not Found)} if the termoAdesaoEmpresa is not found,
     * or with status {@code 500 (Internal Server Error)} if the termoAdesaoEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TermoAdesaoEmpresa> partialUpdateTermoAdesaoEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TermoAdesaoEmpresa termoAdesaoEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to partial update TermoAdesaoEmpresa partially : {}, {}", id, termoAdesaoEmpresa);
        if (termoAdesaoEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, termoAdesaoEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!termoAdesaoEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TermoAdesaoEmpresa> result = termoAdesaoEmpresaService.partialUpdate(termoAdesaoEmpresa);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, termoAdesaoEmpresa.getId().toString())
        );
    }

    /**
     * {@code GET  /termo-adesao-empresas} : get all the termoAdesaoEmpresas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of termoAdesaoEmpresas in body.
     */
    @GetMapping("")
    public List<TermoAdesaoEmpresa> getAllTermoAdesaoEmpresas(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all TermoAdesaoEmpresas");
        return termoAdesaoEmpresaService.findAll();
    }

    /**
     * {@code GET  /termo-adesao-empresas/:id} : get the "id" termoAdesaoEmpresa.
     *
     * @param id the id of the termoAdesaoEmpresa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the termoAdesaoEmpresa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TermoAdesaoEmpresa> getTermoAdesaoEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get TermoAdesaoEmpresa : {}", id);
        Optional<TermoAdesaoEmpresa> termoAdesaoEmpresa = termoAdesaoEmpresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(termoAdesaoEmpresa);
    }

    /**
     * {@code DELETE  /termo-adesao-empresas/:id} : delete the "id" termoAdesaoEmpresa.
     *
     * @param id the id of the termoAdesaoEmpresa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTermoAdesaoEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete TermoAdesaoEmpresa : {}", id);
        termoAdesaoEmpresaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
