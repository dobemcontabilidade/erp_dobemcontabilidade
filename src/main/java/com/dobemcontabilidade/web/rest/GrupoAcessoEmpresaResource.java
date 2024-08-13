package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.GrupoAcessoEmpresa;
import com.dobemcontabilidade.repository.GrupoAcessoEmpresaRepository;
import com.dobemcontabilidade.service.GrupoAcessoEmpresaService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.GrupoAcessoEmpresa}.
 */
@RestController
@RequestMapping("/api/grupo-acesso-empresas")
public class GrupoAcessoEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(GrupoAcessoEmpresaResource.class);

    private static final String ENTITY_NAME = "grupoAcessoEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GrupoAcessoEmpresaService grupoAcessoEmpresaService;

    private final GrupoAcessoEmpresaRepository grupoAcessoEmpresaRepository;

    public GrupoAcessoEmpresaResource(
        GrupoAcessoEmpresaService grupoAcessoEmpresaService,
        GrupoAcessoEmpresaRepository grupoAcessoEmpresaRepository
    ) {
        this.grupoAcessoEmpresaService = grupoAcessoEmpresaService;
        this.grupoAcessoEmpresaRepository = grupoAcessoEmpresaRepository;
    }

    /**
     * {@code POST  /grupo-acesso-empresas} : Create a new grupoAcessoEmpresa.
     *
     * @param grupoAcessoEmpresa the grupoAcessoEmpresa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grupoAcessoEmpresa, or with status {@code 400 (Bad Request)} if the grupoAcessoEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<GrupoAcessoEmpresa> createGrupoAcessoEmpresa(@Valid @RequestBody GrupoAcessoEmpresa grupoAcessoEmpresa)
        throws URISyntaxException {
        log.debug("REST request to save GrupoAcessoEmpresa : {}", grupoAcessoEmpresa);
        if (grupoAcessoEmpresa.getId() != null) {
            throw new BadRequestAlertException("A new grupoAcessoEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        grupoAcessoEmpresa = grupoAcessoEmpresaService.save(grupoAcessoEmpresa);
        return ResponseEntity.created(new URI("/api/grupo-acesso-empresas/" + grupoAcessoEmpresa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, grupoAcessoEmpresa.getId().toString()))
            .body(grupoAcessoEmpresa);
    }

    /**
     * {@code PUT  /grupo-acesso-empresas/:id} : Updates an existing grupoAcessoEmpresa.
     *
     * @param id the id of the grupoAcessoEmpresa to save.
     * @param grupoAcessoEmpresa the grupoAcessoEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoAcessoEmpresa,
     * or with status {@code 400 (Bad Request)} if the grupoAcessoEmpresa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grupoAcessoEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<GrupoAcessoEmpresa> updateGrupoAcessoEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GrupoAcessoEmpresa grupoAcessoEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to update GrupoAcessoEmpresa : {}, {}", id, grupoAcessoEmpresa);
        if (grupoAcessoEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, grupoAcessoEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!grupoAcessoEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        grupoAcessoEmpresa = grupoAcessoEmpresaService.update(grupoAcessoEmpresa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, grupoAcessoEmpresa.getId().toString()))
            .body(grupoAcessoEmpresa);
    }

    /**
     * {@code PATCH  /grupo-acesso-empresas/:id} : Partial updates given fields of an existing grupoAcessoEmpresa, field will ignore if it is null
     *
     * @param id the id of the grupoAcessoEmpresa to save.
     * @param grupoAcessoEmpresa the grupoAcessoEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoAcessoEmpresa,
     * or with status {@code 400 (Bad Request)} if the grupoAcessoEmpresa is not valid,
     * or with status {@code 404 (Not Found)} if the grupoAcessoEmpresa is not found,
     * or with status {@code 500 (Internal Server Error)} if the grupoAcessoEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GrupoAcessoEmpresa> partialUpdateGrupoAcessoEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GrupoAcessoEmpresa grupoAcessoEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to partial update GrupoAcessoEmpresa partially : {}, {}", id, grupoAcessoEmpresa);
        if (grupoAcessoEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, grupoAcessoEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!grupoAcessoEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GrupoAcessoEmpresa> result = grupoAcessoEmpresaService.partialUpdate(grupoAcessoEmpresa);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, grupoAcessoEmpresa.getId().toString())
        );
    }

    /**
     * {@code GET  /grupo-acesso-empresas} : get all the grupoAcessoEmpresas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grupoAcessoEmpresas in body.
     */
    @GetMapping("")
    public List<GrupoAcessoEmpresa> getAllGrupoAcessoEmpresas(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all GrupoAcessoEmpresas");
        return grupoAcessoEmpresaService.findAll();
    }

    /**
     * {@code GET  /grupo-acesso-empresas/:id} : get the "id" grupoAcessoEmpresa.
     *
     * @param id the id of the grupoAcessoEmpresa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grupoAcessoEmpresa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GrupoAcessoEmpresa> getGrupoAcessoEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get GrupoAcessoEmpresa : {}", id);
        Optional<GrupoAcessoEmpresa> grupoAcessoEmpresa = grupoAcessoEmpresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(grupoAcessoEmpresa);
    }

    /**
     * {@code DELETE  /grupo-acesso-empresas/:id} : delete the "id" grupoAcessoEmpresa.
     *
     * @param id the id of the grupoAcessoEmpresa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrupoAcessoEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete GrupoAcessoEmpresa : {}", id);
        grupoAcessoEmpresaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
