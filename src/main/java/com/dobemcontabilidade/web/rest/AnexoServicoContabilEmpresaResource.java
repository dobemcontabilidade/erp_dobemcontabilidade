package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.AnexoServicoContabilEmpresa;
import com.dobemcontabilidade.repository.AnexoServicoContabilEmpresaRepository;
import com.dobemcontabilidade.service.AnexoServicoContabilEmpresaService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AnexoServicoContabilEmpresa}.
 */
@RestController
@RequestMapping("/api/anexo-servico-contabil-empresas")
public class AnexoServicoContabilEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(AnexoServicoContabilEmpresaResource.class);

    private static final String ENTITY_NAME = "anexoServicoContabilEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnexoServicoContabilEmpresaService anexoServicoContabilEmpresaService;

    private final AnexoServicoContabilEmpresaRepository anexoServicoContabilEmpresaRepository;

    public AnexoServicoContabilEmpresaResource(
        AnexoServicoContabilEmpresaService anexoServicoContabilEmpresaService,
        AnexoServicoContabilEmpresaRepository anexoServicoContabilEmpresaRepository
    ) {
        this.anexoServicoContabilEmpresaService = anexoServicoContabilEmpresaService;
        this.anexoServicoContabilEmpresaRepository = anexoServicoContabilEmpresaRepository;
    }

    /**
     * {@code POST  /anexo-servico-contabil-empresas} : Create a new anexoServicoContabilEmpresa.
     *
     * @param anexoServicoContabilEmpresa the anexoServicoContabilEmpresa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anexoServicoContabilEmpresa, or with status {@code 400 (Bad Request)} if the anexoServicoContabilEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AnexoServicoContabilEmpresa> createAnexoServicoContabilEmpresa(
        @Valid @RequestBody AnexoServicoContabilEmpresa anexoServicoContabilEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to save AnexoServicoContabilEmpresa : {}", anexoServicoContabilEmpresa);
        if (anexoServicoContabilEmpresa.getId() != null) {
            throw new BadRequestAlertException("A new anexoServicoContabilEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        anexoServicoContabilEmpresa = anexoServicoContabilEmpresaService.save(anexoServicoContabilEmpresa);
        return ResponseEntity.created(new URI("/api/anexo-servico-contabil-empresas/" + anexoServicoContabilEmpresa.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, anexoServicoContabilEmpresa.getId().toString())
            )
            .body(anexoServicoContabilEmpresa);
    }

    /**
     * {@code PUT  /anexo-servico-contabil-empresas/:id} : Updates an existing anexoServicoContabilEmpresa.
     *
     * @param id the id of the anexoServicoContabilEmpresa to save.
     * @param anexoServicoContabilEmpresa the anexoServicoContabilEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anexoServicoContabilEmpresa,
     * or with status {@code 400 (Bad Request)} if the anexoServicoContabilEmpresa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anexoServicoContabilEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AnexoServicoContabilEmpresa> updateAnexoServicoContabilEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AnexoServicoContabilEmpresa anexoServicoContabilEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to update AnexoServicoContabilEmpresa : {}, {}", id, anexoServicoContabilEmpresa);
        if (anexoServicoContabilEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anexoServicoContabilEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anexoServicoContabilEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        anexoServicoContabilEmpresa = anexoServicoContabilEmpresaService.update(anexoServicoContabilEmpresa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anexoServicoContabilEmpresa.getId().toString()))
            .body(anexoServicoContabilEmpresa);
    }

    /**
     * {@code PATCH  /anexo-servico-contabil-empresas/:id} : Partial updates given fields of an existing anexoServicoContabilEmpresa, field will ignore if it is null
     *
     * @param id the id of the anexoServicoContabilEmpresa to save.
     * @param anexoServicoContabilEmpresa the anexoServicoContabilEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anexoServicoContabilEmpresa,
     * or with status {@code 400 (Bad Request)} if the anexoServicoContabilEmpresa is not valid,
     * or with status {@code 404 (Not Found)} if the anexoServicoContabilEmpresa is not found,
     * or with status {@code 500 (Internal Server Error)} if the anexoServicoContabilEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AnexoServicoContabilEmpresa> partialUpdateAnexoServicoContabilEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AnexoServicoContabilEmpresa anexoServicoContabilEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to partial update AnexoServicoContabilEmpresa partially : {}, {}", id, anexoServicoContabilEmpresa);
        if (anexoServicoContabilEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anexoServicoContabilEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anexoServicoContabilEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnexoServicoContabilEmpresa> result = anexoServicoContabilEmpresaService.partialUpdate(anexoServicoContabilEmpresa);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anexoServicoContabilEmpresa.getId().toString())
        );
    }

    /**
     * {@code GET  /anexo-servico-contabil-empresas} : get all the anexoServicoContabilEmpresas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anexoServicoContabilEmpresas in body.
     */
    @GetMapping("")
    public List<AnexoServicoContabilEmpresa> getAllAnexoServicoContabilEmpresas(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all AnexoServicoContabilEmpresas");
        return anexoServicoContabilEmpresaService.findAll();
    }

    /**
     * {@code GET  /anexo-servico-contabil-empresas/:id} : get the "id" anexoServicoContabilEmpresa.
     *
     * @param id the id of the anexoServicoContabilEmpresa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anexoServicoContabilEmpresa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AnexoServicoContabilEmpresa> getAnexoServicoContabilEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get AnexoServicoContabilEmpresa : {}", id);
        Optional<AnexoServicoContabilEmpresa> anexoServicoContabilEmpresa = anexoServicoContabilEmpresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anexoServicoContabilEmpresa);
    }

    /**
     * {@code DELETE  /anexo-servico-contabil-empresas/:id} : delete the "id" anexoServicoContabilEmpresa.
     *
     * @param id the id of the anexoServicoContabilEmpresa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnexoServicoContabilEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete AnexoServicoContabilEmpresa : {}", id);
        anexoServicoContabilEmpresaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
