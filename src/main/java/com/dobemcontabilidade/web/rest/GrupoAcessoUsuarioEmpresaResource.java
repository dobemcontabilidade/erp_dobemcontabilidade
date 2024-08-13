package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.GrupoAcessoUsuarioEmpresa;
import com.dobemcontabilidade.repository.GrupoAcessoUsuarioEmpresaRepository;
import com.dobemcontabilidade.service.GrupoAcessoUsuarioEmpresaService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.GrupoAcessoUsuarioEmpresa}.
 */
@RestController
@RequestMapping("/api/grupo-acesso-usuario-empresas")
public class GrupoAcessoUsuarioEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(GrupoAcessoUsuarioEmpresaResource.class);

    private static final String ENTITY_NAME = "grupoAcessoUsuarioEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GrupoAcessoUsuarioEmpresaService grupoAcessoUsuarioEmpresaService;

    private final GrupoAcessoUsuarioEmpresaRepository grupoAcessoUsuarioEmpresaRepository;

    public GrupoAcessoUsuarioEmpresaResource(
        GrupoAcessoUsuarioEmpresaService grupoAcessoUsuarioEmpresaService,
        GrupoAcessoUsuarioEmpresaRepository grupoAcessoUsuarioEmpresaRepository
    ) {
        this.grupoAcessoUsuarioEmpresaService = grupoAcessoUsuarioEmpresaService;
        this.grupoAcessoUsuarioEmpresaRepository = grupoAcessoUsuarioEmpresaRepository;
    }

    /**
     * {@code POST  /grupo-acesso-usuario-empresas} : Create a new grupoAcessoUsuarioEmpresa.
     *
     * @param grupoAcessoUsuarioEmpresa the grupoAcessoUsuarioEmpresa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grupoAcessoUsuarioEmpresa, or with status {@code 400 (Bad Request)} if the grupoAcessoUsuarioEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<GrupoAcessoUsuarioEmpresa> createGrupoAcessoUsuarioEmpresa(
        @Valid @RequestBody GrupoAcessoUsuarioEmpresa grupoAcessoUsuarioEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to save GrupoAcessoUsuarioEmpresa : {}", grupoAcessoUsuarioEmpresa);
        if (grupoAcessoUsuarioEmpresa.getId() != null) {
            throw new BadRequestAlertException("A new grupoAcessoUsuarioEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        grupoAcessoUsuarioEmpresa = grupoAcessoUsuarioEmpresaService.save(grupoAcessoUsuarioEmpresa);
        return ResponseEntity.created(new URI("/api/grupo-acesso-usuario-empresas/" + grupoAcessoUsuarioEmpresa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, grupoAcessoUsuarioEmpresa.getId().toString()))
            .body(grupoAcessoUsuarioEmpresa);
    }

    /**
     * {@code PUT  /grupo-acesso-usuario-empresas/:id} : Updates an existing grupoAcessoUsuarioEmpresa.
     *
     * @param id the id of the grupoAcessoUsuarioEmpresa to save.
     * @param grupoAcessoUsuarioEmpresa the grupoAcessoUsuarioEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoAcessoUsuarioEmpresa,
     * or with status {@code 400 (Bad Request)} if the grupoAcessoUsuarioEmpresa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grupoAcessoUsuarioEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<GrupoAcessoUsuarioEmpresa> updateGrupoAcessoUsuarioEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GrupoAcessoUsuarioEmpresa grupoAcessoUsuarioEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to update GrupoAcessoUsuarioEmpresa : {}, {}", id, grupoAcessoUsuarioEmpresa);
        if (grupoAcessoUsuarioEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, grupoAcessoUsuarioEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!grupoAcessoUsuarioEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        grupoAcessoUsuarioEmpresa = grupoAcessoUsuarioEmpresaService.update(grupoAcessoUsuarioEmpresa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, grupoAcessoUsuarioEmpresa.getId().toString()))
            .body(grupoAcessoUsuarioEmpresa);
    }

    /**
     * {@code PATCH  /grupo-acesso-usuario-empresas/:id} : Partial updates given fields of an existing grupoAcessoUsuarioEmpresa, field will ignore if it is null
     *
     * @param id the id of the grupoAcessoUsuarioEmpresa to save.
     * @param grupoAcessoUsuarioEmpresa the grupoAcessoUsuarioEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoAcessoUsuarioEmpresa,
     * or with status {@code 400 (Bad Request)} if the grupoAcessoUsuarioEmpresa is not valid,
     * or with status {@code 404 (Not Found)} if the grupoAcessoUsuarioEmpresa is not found,
     * or with status {@code 500 (Internal Server Error)} if the grupoAcessoUsuarioEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GrupoAcessoUsuarioEmpresa> partialUpdateGrupoAcessoUsuarioEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GrupoAcessoUsuarioEmpresa grupoAcessoUsuarioEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to partial update GrupoAcessoUsuarioEmpresa partially : {}, {}", id, grupoAcessoUsuarioEmpresa);
        if (grupoAcessoUsuarioEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, grupoAcessoUsuarioEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!grupoAcessoUsuarioEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GrupoAcessoUsuarioEmpresa> result = grupoAcessoUsuarioEmpresaService.partialUpdate(grupoAcessoUsuarioEmpresa);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, grupoAcessoUsuarioEmpresa.getId().toString())
        );
    }

    /**
     * {@code GET  /grupo-acesso-usuario-empresas} : get all the grupoAcessoUsuarioEmpresas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grupoAcessoUsuarioEmpresas in body.
     */
    @GetMapping("")
    public List<GrupoAcessoUsuarioEmpresa> getAllGrupoAcessoUsuarioEmpresas(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all GrupoAcessoUsuarioEmpresas");
        return grupoAcessoUsuarioEmpresaService.findAll();
    }

    /**
     * {@code GET  /grupo-acesso-usuario-empresas/:id} : get the "id" grupoAcessoUsuarioEmpresa.
     *
     * @param id the id of the grupoAcessoUsuarioEmpresa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grupoAcessoUsuarioEmpresa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GrupoAcessoUsuarioEmpresa> getGrupoAcessoUsuarioEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get GrupoAcessoUsuarioEmpresa : {}", id);
        Optional<GrupoAcessoUsuarioEmpresa> grupoAcessoUsuarioEmpresa = grupoAcessoUsuarioEmpresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(grupoAcessoUsuarioEmpresa);
    }

    /**
     * {@code DELETE  /grupo-acesso-usuario-empresas/:id} : delete the "id" grupoAcessoUsuarioEmpresa.
     *
     * @param id the id of the grupoAcessoUsuarioEmpresa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrupoAcessoUsuarioEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete GrupoAcessoUsuarioEmpresa : {}", id);
        grupoAcessoUsuarioEmpresaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
