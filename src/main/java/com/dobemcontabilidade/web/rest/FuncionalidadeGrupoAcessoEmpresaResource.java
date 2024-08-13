package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.FuncionalidadeGrupoAcessoEmpresa;
import com.dobemcontabilidade.repository.FuncionalidadeGrupoAcessoEmpresaRepository;
import com.dobemcontabilidade.service.FuncionalidadeGrupoAcessoEmpresaService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.FuncionalidadeGrupoAcessoEmpresa}.
 */
@RestController
@RequestMapping("/api/funcionalidade-grupo-acesso-empresas")
public class FuncionalidadeGrupoAcessoEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(FuncionalidadeGrupoAcessoEmpresaResource.class);

    private static final String ENTITY_NAME = "funcionalidadeGrupoAcessoEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuncionalidadeGrupoAcessoEmpresaService funcionalidadeGrupoAcessoEmpresaService;

    private final FuncionalidadeGrupoAcessoEmpresaRepository funcionalidadeGrupoAcessoEmpresaRepository;

    public FuncionalidadeGrupoAcessoEmpresaResource(
        FuncionalidadeGrupoAcessoEmpresaService funcionalidadeGrupoAcessoEmpresaService,
        FuncionalidadeGrupoAcessoEmpresaRepository funcionalidadeGrupoAcessoEmpresaRepository
    ) {
        this.funcionalidadeGrupoAcessoEmpresaService = funcionalidadeGrupoAcessoEmpresaService;
        this.funcionalidadeGrupoAcessoEmpresaRepository = funcionalidadeGrupoAcessoEmpresaRepository;
    }

    /**
     * {@code POST  /funcionalidade-grupo-acesso-empresas} : Create a new funcionalidadeGrupoAcessoEmpresa.
     *
     * @param funcionalidadeGrupoAcessoEmpresa the funcionalidadeGrupoAcessoEmpresa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new funcionalidadeGrupoAcessoEmpresa, or with status {@code 400 (Bad Request)} if the funcionalidadeGrupoAcessoEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FuncionalidadeGrupoAcessoEmpresa> createFuncionalidadeGrupoAcessoEmpresa(
        @Valid @RequestBody FuncionalidadeGrupoAcessoEmpresa funcionalidadeGrupoAcessoEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to save FuncionalidadeGrupoAcessoEmpresa : {}", funcionalidadeGrupoAcessoEmpresa);
        if (funcionalidadeGrupoAcessoEmpresa.getId() != null) {
            throw new BadRequestAlertException("A new funcionalidadeGrupoAcessoEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        funcionalidadeGrupoAcessoEmpresa = funcionalidadeGrupoAcessoEmpresaService.save(funcionalidadeGrupoAcessoEmpresa);
        return ResponseEntity.created(new URI("/api/funcionalidade-grupo-acesso-empresas/" + funcionalidadeGrupoAcessoEmpresa.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    funcionalidadeGrupoAcessoEmpresa.getId().toString()
                )
            )
            .body(funcionalidadeGrupoAcessoEmpresa);
    }

    /**
     * {@code PUT  /funcionalidade-grupo-acesso-empresas/:id} : Updates an existing funcionalidadeGrupoAcessoEmpresa.
     *
     * @param id the id of the funcionalidadeGrupoAcessoEmpresa to save.
     * @param funcionalidadeGrupoAcessoEmpresa the funcionalidadeGrupoAcessoEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated funcionalidadeGrupoAcessoEmpresa,
     * or with status {@code 400 (Bad Request)} if the funcionalidadeGrupoAcessoEmpresa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the funcionalidadeGrupoAcessoEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FuncionalidadeGrupoAcessoEmpresa> updateFuncionalidadeGrupoAcessoEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FuncionalidadeGrupoAcessoEmpresa funcionalidadeGrupoAcessoEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to update FuncionalidadeGrupoAcessoEmpresa : {}, {}", id, funcionalidadeGrupoAcessoEmpresa);
        if (funcionalidadeGrupoAcessoEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, funcionalidadeGrupoAcessoEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!funcionalidadeGrupoAcessoEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        funcionalidadeGrupoAcessoEmpresa = funcionalidadeGrupoAcessoEmpresaService.update(funcionalidadeGrupoAcessoEmpresa);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, funcionalidadeGrupoAcessoEmpresa.getId().toString())
            )
            .body(funcionalidadeGrupoAcessoEmpresa);
    }

    /**
     * {@code PATCH  /funcionalidade-grupo-acesso-empresas/:id} : Partial updates given fields of an existing funcionalidadeGrupoAcessoEmpresa, field will ignore if it is null
     *
     * @param id the id of the funcionalidadeGrupoAcessoEmpresa to save.
     * @param funcionalidadeGrupoAcessoEmpresa the funcionalidadeGrupoAcessoEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated funcionalidadeGrupoAcessoEmpresa,
     * or with status {@code 400 (Bad Request)} if the funcionalidadeGrupoAcessoEmpresa is not valid,
     * or with status {@code 404 (Not Found)} if the funcionalidadeGrupoAcessoEmpresa is not found,
     * or with status {@code 500 (Internal Server Error)} if the funcionalidadeGrupoAcessoEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FuncionalidadeGrupoAcessoEmpresa> partialUpdateFuncionalidadeGrupoAcessoEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FuncionalidadeGrupoAcessoEmpresa funcionalidadeGrupoAcessoEmpresa
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update FuncionalidadeGrupoAcessoEmpresa partially : {}, {}",
            id,
            funcionalidadeGrupoAcessoEmpresa
        );
        if (funcionalidadeGrupoAcessoEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, funcionalidadeGrupoAcessoEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!funcionalidadeGrupoAcessoEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FuncionalidadeGrupoAcessoEmpresa> result = funcionalidadeGrupoAcessoEmpresaService.partialUpdate(
            funcionalidadeGrupoAcessoEmpresa
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, funcionalidadeGrupoAcessoEmpresa.getId().toString())
        );
    }

    /**
     * {@code GET  /funcionalidade-grupo-acesso-empresas} : get all the funcionalidadeGrupoAcessoEmpresas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of funcionalidadeGrupoAcessoEmpresas in body.
     */
    @GetMapping("")
    public List<FuncionalidadeGrupoAcessoEmpresa> getAllFuncionalidadeGrupoAcessoEmpresas(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all FuncionalidadeGrupoAcessoEmpresas");
        return funcionalidadeGrupoAcessoEmpresaService.findAll();
    }

    /**
     * {@code GET  /funcionalidade-grupo-acesso-empresas/:id} : get the "id" funcionalidadeGrupoAcessoEmpresa.
     *
     * @param id the id of the funcionalidadeGrupoAcessoEmpresa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the funcionalidadeGrupoAcessoEmpresa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FuncionalidadeGrupoAcessoEmpresa> getFuncionalidadeGrupoAcessoEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get FuncionalidadeGrupoAcessoEmpresa : {}", id);
        Optional<FuncionalidadeGrupoAcessoEmpresa> funcionalidadeGrupoAcessoEmpresa = funcionalidadeGrupoAcessoEmpresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(funcionalidadeGrupoAcessoEmpresa);
    }

    /**
     * {@code DELETE  /funcionalidade-grupo-acesso-empresas/:id} : delete the "id" funcionalidadeGrupoAcessoEmpresa.
     *
     * @param id the id of the funcionalidadeGrupoAcessoEmpresa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuncionalidadeGrupoAcessoEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete FuncionalidadeGrupoAcessoEmpresa : {}", id);
        funcionalidadeGrupoAcessoEmpresaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
