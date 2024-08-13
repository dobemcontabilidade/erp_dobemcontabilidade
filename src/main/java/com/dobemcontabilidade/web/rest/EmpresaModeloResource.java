package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.EmpresaModelo;
import com.dobemcontabilidade.repository.EmpresaModeloRepository;
import com.dobemcontabilidade.service.EmpresaModeloService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.EmpresaModelo}.
 */
@RestController
@RequestMapping("/api/empresa-modelos")
public class EmpresaModeloResource {

    private static final Logger log = LoggerFactory.getLogger(EmpresaModeloResource.class);

    private static final String ENTITY_NAME = "empresaModelo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmpresaModeloService empresaModeloService;

    private final EmpresaModeloRepository empresaModeloRepository;

    public EmpresaModeloResource(EmpresaModeloService empresaModeloService, EmpresaModeloRepository empresaModeloRepository) {
        this.empresaModeloService = empresaModeloService;
        this.empresaModeloRepository = empresaModeloRepository;
    }

    /**
     * {@code POST  /empresa-modelos} : Create a new empresaModelo.
     *
     * @param empresaModelo the empresaModelo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new empresaModelo, or with status {@code 400 (Bad Request)} if the empresaModelo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EmpresaModelo> createEmpresaModelo(@Valid @RequestBody EmpresaModelo empresaModelo) throws URISyntaxException {
        log.debug("REST request to save EmpresaModelo : {}", empresaModelo);
        if (empresaModelo.getId() != null) {
            throw new BadRequestAlertException("A new empresaModelo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        empresaModelo = empresaModeloService.save(empresaModelo);
        return ResponseEntity.created(new URI("/api/empresa-modelos/" + empresaModelo.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, empresaModelo.getId().toString()))
            .body(empresaModelo);
    }

    /**
     * {@code PUT  /empresa-modelos/:id} : Updates an existing empresaModelo.
     *
     * @param id the id of the empresaModelo to save.
     * @param empresaModelo the empresaModelo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empresaModelo,
     * or with status {@code 400 (Bad Request)} if the empresaModelo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the empresaModelo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmpresaModelo> updateEmpresaModelo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmpresaModelo empresaModelo
    ) throws URISyntaxException {
        log.debug("REST request to update EmpresaModelo : {}, {}", id, empresaModelo);
        if (empresaModelo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, empresaModelo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empresaModeloRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        empresaModelo = empresaModeloService.update(empresaModelo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, empresaModelo.getId().toString()))
            .body(empresaModelo);
    }

    /**
     * {@code PATCH  /empresa-modelos/:id} : Partial updates given fields of an existing empresaModelo, field will ignore if it is null
     *
     * @param id the id of the empresaModelo to save.
     * @param empresaModelo the empresaModelo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empresaModelo,
     * or with status {@code 400 (Bad Request)} if the empresaModelo is not valid,
     * or with status {@code 404 (Not Found)} if the empresaModelo is not found,
     * or with status {@code 500 (Internal Server Error)} if the empresaModelo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmpresaModelo> partialUpdateEmpresaModelo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmpresaModelo empresaModelo
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmpresaModelo partially : {}, {}", id, empresaModelo);
        if (empresaModelo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, empresaModelo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empresaModeloRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmpresaModelo> result = empresaModeloService.partialUpdate(empresaModelo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, empresaModelo.getId().toString())
        );
    }

    /**
     * {@code GET  /empresa-modelos} : get all the empresaModelos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of empresaModelos in body.
     */
    @GetMapping("")
    public List<EmpresaModelo> getAllEmpresaModelos(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all EmpresaModelos");
        return empresaModeloService.findAll();
    }

    /**
     * {@code GET  /empresa-modelos/:id} : get the "id" empresaModelo.
     *
     * @param id the id of the empresaModelo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the empresaModelo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaModelo> getEmpresaModelo(@PathVariable("id") Long id) {
        log.debug("REST request to get EmpresaModelo : {}", id);
        Optional<EmpresaModelo> empresaModelo = empresaModeloService.findOne(id);
        return ResponseUtil.wrapOrNotFound(empresaModelo);
    }

    /**
     * {@code DELETE  /empresa-modelos/:id} : delete the "id" empresaModelo.
     *
     * @param id the id of the empresaModelo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpresaModelo(@PathVariable("id") Long id) {
        log.debug("REST request to delete EmpresaModelo : {}", id);
        empresaModeloService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
