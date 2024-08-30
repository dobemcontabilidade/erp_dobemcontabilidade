package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.repository.EmpresaRepository;
import com.dobemcontabilidade.service.EmpresaQueryService;
import com.dobemcontabilidade.service.EmpresaService;
import com.dobemcontabilidade.service.criteria.EmpresaCriteria;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Empresa}.
 */
@RestController
@RequestMapping("/api/empresas")
public class EmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(EmpresaResource.class);

    private static final String ENTITY_NAME = "empresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmpresaService empresaService;

    private final EmpresaRepository empresaRepository;

    private final EmpresaQueryService empresaQueryService;

    public EmpresaResource(EmpresaService empresaService, EmpresaRepository empresaRepository, EmpresaQueryService empresaQueryService) {
        this.empresaService = empresaService;
        this.empresaRepository = empresaRepository;
        this.empresaQueryService = empresaQueryService;
    }

    /**
     * {@code POST  /empresas} : Create a new empresa.
     *
     * @param empresa the empresa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new empresa, or with status {@code 400 (Bad Request)} if the empresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Empresa> createEmpresa(@Valid @RequestBody Empresa empresa) throws URISyntaxException {
        log.debug("REST request to save Empresa : {}", empresa);
        if (empresa.getId() != null) {
            throw new BadRequestAlertException("A new empresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        empresa = empresaService.save(empresa);
        return ResponseEntity.created(new URI("/api/empresas/" + empresa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, empresa.getId().toString()))
            .body(empresa);
    }

    /**
     * {@code PUT  /empresas/:id} : Updates an existing empresa.
     *
     * @param id the id of the empresa to save.
     * @param empresa the empresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empresa,
     * or with status {@code 400 (Bad Request)} if the empresa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the empresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Empresa> updateEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Empresa empresa
    ) throws URISyntaxException {
        log.debug("REST request to update Empresa : {}, {}", id, empresa);
        if (empresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, empresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        empresa = empresaService.update(empresa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, empresa.getId().toString()))
            .body(empresa);
    }

    /**
     * {@code PATCH  /empresas/:id} : Partial updates given fields of an existing empresa, field will ignore if it is null
     *
     * @param id the id of the empresa to save.
     * @param empresa the empresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empresa,
     * or with status {@code 400 (Bad Request)} if the empresa is not valid,
     * or with status {@code 404 (Not Found)} if the empresa is not found,
     * or with status {@code 500 (Internal Server Error)} if the empresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Empresa> partialUpdateEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Empresa empresa
    ) throws URISyntaxException {
        log.debug("REST request to partial update Empresa partially : {}, {}", id, empresa);
        if (empresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, empresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Empresa> result = empresaService.partialUpdate(empresa);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, empresa.getId().toString())
        );
    }

    /**
     * {@code GET  /empresas} : get all the empresas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of empresas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Empresa>> getAllEmpresas(EmpresaCriteria criteria) {
        log.debug("REST request to get Empresas by criteria: {}", criteria);

        List<Empresa> entityList = empresaQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /empresas/count} : count all the empresas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countEmpresas(EmpresaCriteria criteria) {
        log.debug("REST request to count Empresas by criteria: {}", criteria);
        return ResponseEntity.ok().body(empresaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /empresas/:id} : get the "id" empresa.
     *
     * @param id the id of the empresa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the empresa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Empresa> getEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get Empresa : {}", id);
        Optional<Empresa> empresa = empresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(empresa);
    }

    /**
     * {@code DELETE  /empresas/:id} : delete the "id" empresa.
     *
     * @param id the id of the empresa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete Empresa : {}", id);
        empresaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
