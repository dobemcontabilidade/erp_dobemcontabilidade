package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.EmpresaVinculada;
import com.dobemcontabilidade.repository.EmpresaVinculadaRepository;
import com.dobemcontabilidade.service.EmpresaVinculadaService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.EmpresaVinculada}.
 */
@RestController
@RequestMapping("/api/empresa-vinculadas")
public class EmpresaVinculadaResource {

    private static final Logger log = LoggerFactory.getLogger(EmpresaVinculadaResource.class);

    private static final String ENTITY_NAME = "empresaVinculada";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmpresaVinculadaService empresaVinculadaService;

    private final EmpresaVinculadaRepository empresaVinculadaRepository;

    public EmpresaVinculadaResource(
        EmpresaVinculadaService empresaVinculadaService,
        EmpresaVinculadaRepository empresaVinculadaRepository
    ) {
        this.empresaVinculadaService = empresaVinculadaService;
        this.empresaVinculadaRepository = empresaVinculadaRepository;
    }

    /**
     * {@code POST  /empresa-vinculadas} : Create a new empresaVinculada.
     *
     * @param empresaVinculada the empresaVinculada to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new empresaVinculada, or with status {@code 400 (Bad Request)} if the empresaVinculada has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EmpresaVinculada> createEmpresaVinculada(@Valid @RequestBody EmpresaVinculada empresaVinculada)
        throws URISyntaxException {
        log.debug("REST request to save EmpresaVinculada : {}", empresaVinculada);
        if (empresaVinculada.getId() != null) {
            throw new BadRequestAlertException("A new empresaVinculada cannot already have an ID", ENTITY_NAME, "idexists");
        }
        empresaVinculada = empresaVinculadaService.save(empresaVinculada);
        return ResponseEntity.created(new URI("/api/empresa-vinculadas/" + empresaVinculada.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, empresaVinculada.getId().toString()))
            .body(empresaVinculada);
    }

    /**
     * {@code PUT  /empresa-vinculadas/:id} : Updates an existing empresaVinculada.
     *
     * @param id the id of the empresaVinculada to save.
     * @param empresaVinculada the empresaVinculada to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empresaVinculada,
     * or with status {@code 400 (Bad Request)} if the empresaVinculada is not valid,
     * or with status {@code 500 (Internal Server Error)} if the empresaVinculada couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmpresaVinculada> updateEmpresaVinculada(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmpresaVinculada empresaVinculada
    ) throws URISyntaxException {
        log.debug("REST request to update EmpresaVinculada : {}, {}", id, empresaVinculada);
        if (empresaVinculada.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, empresaVinculada.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empresaVinculadaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        empresaVinculada = empresaVinculadaService.update(empresaVinculada);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, empresaVinculada.getId().toString()))
            .body(empresaVinculada);
    }

    /**
     * {@code PATCH  /empresa-vinculadas/:id} : Partial updates given fields of an existing empresaVinculada, field will ignore if it is null
     *
     * @param id the id of the empresaVinculada to save.
     * @param empresaVinculada the empresaVinculada to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empresaVinculada,
     * or with status {@code 400 (Bad Request)} if the empresaVinculada is not valid,
     * or with status {@code 404 (Not Found)} if the empresaVinculada is not found,
     * or with status {@code 500 (Internal Server Error)} if the empresaVinculada couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmpresaVinculada> partialUpdateEmpresaVinculada(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmpresaVinculada empresaVinculada
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmpresaVinculada partially : {}, {}", id, empresaVinculada);
        if (empresaVinculada.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, empresaVinculada.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empresaVinculadaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmpresaVinculada> result = empresaVinculadaService.partialUpdate(empresaVinculada);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, empresaVinculada.getId().toString())
        );
    }

    /**
     * {@code GET  /empresa-vinculadas} : get all the empresaVinculadas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of empresaVinculadas in body.
     */
    @GetMapping("")
    public List<EmpresaVinculada> getAllEmpresaVinculadas() {
        log.debug("REST request to get all EmpresaVinculadas");
        return empresaVinculadaService.findAll();
    }

    /**
     * {@code GET  /empresa-vinculadas/:id} : get the "id" empresaVinculada.
     *
     * @param id the id of the empresaVinculada to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the empresaVinculada, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaVinculada> getEmpresaVinculada(@PathVariable("id") Long id) {
        log.debug("REST request to get EmpresaVinculada : {}", id);
        Optional<EmpresaVinculada> empresaVinculada = empresaVinculadaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(empresaVinculada);
    }

    /**
     * {@code DELETE  /empresa-vinculadas/:id} : delete the "id" empresaVinculada.
     *
     * @param id the id of the empresaVinculada to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpresaVinculada(@PathVariable("id") Long id) {
        log.debug("REST request to delete EmpresaVinculada : {}", id);
        empresaVinculadaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
