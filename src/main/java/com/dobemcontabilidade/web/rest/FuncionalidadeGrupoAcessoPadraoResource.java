package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.FuncionalidadeGrupoAcessoPadrao;
import com.dobemcontabilidade.repository.FuncionalidadeGrupoAcessoPadraoRepository;
import com.dobemcontabilidade.service.FuncionalidadeGrupoAcessoPadraoService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.FuncionalidadeGrupoAcessoPadrao}.
 */
@RestController
@RequestMapping("/api/funcionalidade-grupo-acesso-padraos")
public class FuncionalidadeGrupoAcessoPadraoResource {

    private static final Logger log = LoggerFactory.getLogger(FuncionalidadeGrupoAcessoPadraoResource.class);

    private static final String ENTITY_NAME = "funcionalidadeGrupoAcessoPadrao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuncionalidadeGrupoAcessoPadraoService funcionalidadeGrupoAcessoPadraoService;

    private final FuncionalidadeGrupoAcessoPadraoRepository funcionalidadeGrupoAcessoPadraoRepository;

    public FuncionalidadeGrupoAcessoPadraoResource(
        FuncionalidadeGrupoAcessoPadraoService funcionalidadeGrupoAcessoPadraoService,
        FuncionalidadeGrupoAcessoPadraoRepository funcionalidadeGrupoAcessoPadraoRepository
    ) {
        this.funcionalidadeGrupoAcessoPadraoService = funcionalidadeGrupoAcessoPadraoService;
        this.funcionalidadeGrupoAcessoPadraoRepository = funcionalidadeGrupoAcessoPadraoRepository;
    }

    /**
     * {@code POST  /funcionalidade-grupo-acesso-padraos} : Create a new funcionalidadeGrupoAcessoPadrao.
     *
     * @param funcionalidadeGrupoAcessoPadrao the funcionalidadeGrupoAcessoPadrao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new funcionalidadeGrupoAcessoPadrao, or with status {@code 400 (Bad Request)} if the funcionalidadeGrupoAcessoPadrao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FuncionalidadeGrupoAcessoPadrao> createFuncionalidadeGrupoAcessoPadrao(
        @Valid @RequestBody FuncionalidadeGrupoAcessoPadrao funcionalidadeGrupoAcessoPadrao
    ) throws URISyntaxException {
        log.debug("REST request to save FuncionalidadeGrupoAcessoPadrao : {}", funcionalidadeGrupoAcessoPadrao);
        if (funcionalidadeGrupoAcessoPadrao.getId() != null) {
            throw new BadRequestAlertException("A new funcionalidadeGrupoAcessoPadrao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        funcionalidadeGrupoAcessoPadrao = funcionalidadeGrupoAcessoPadraoService.save(funcionalidadeGrupoAcessoPadrao);
        return ResponseEntity.created(new URI("/api/funcionalidade-grupo-acesso-padraos/" + funcionalidadeGrupoAcessoPadrao.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, funcionalidadeGrupoAcessoPadrao.getId().toString())
            )
            .body(funcionalidadeGrupoAcessoPadrao);
    }

    /**
     * {@code PUT  /funcionalidade-grupo-acesso-padraos/:id} : Updates an existing funcionalidadeGrupoAcessoPadrao.
     *
     * @param id the id of the funcionalidadeGrupoAcessoPadrao to save.
     * @param funcionalidadeGrupoAcessoPadrao the funcionalidadeGrupoAcessoPadrao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated funcionalidadeGrupoAcessoPadrao,
     * or with status {@code 400 (Bad Request)} if the funcionalidadeGrupoAcessoPadrao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the funcionalidadeGrupoAcessoPadrao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FuncionalidadeGrupoAcessoPadrao> updateFuncionalidadeGrupoAcessoPadrao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FuncionalidadeGrupoAcessoPadrao funcionalidadeGrupoAcessoPadrao
    ) throws URISyntaxException {
        log.debug("REST request to update FuncionalidadeGrupoAcessoPadrao : {}, {}", id, funcionalidadeGrupoAcessoPadrao);
        if (funcionalidadeGrupoAcessoPadrao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, funcionalidadeGrupoAcessoPadrao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!funcionalidadeGrupoAcessoPadraoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        funcionalidadeGrupoAcessoPadrao = funcionalidadeGrupoAcessoPadraoService.update(funcionalidadeGrupoAcessoPadrao);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, funcionalidadeGrupoAcessoPadrao.getId().toString())
            )
            .body(funcionalidadeGrupoAcessoPadrao);
    }

    /**
     * {@code PATCH  /funcionalidade-grupo-acesso-padraos/:id} : Partial updates given fields of an existing funcionalidadeGrupoAcessoPadrao, field will ignore if it is null
     *
     * @param id the id of the funcionalidadeGrupoAcessoPadrao to save.
     * @param funcionalidadeGrupoAcessoPadrao the funcionalidadeGrupoAcessoPadrao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated funcionalidadeGrupoAcessoPadrao,
     * or with status {@code 400 (Bad Request)} if the funcionalidadeGrupoAcessoPadrao is not valid,
     * or with status {@code 404 (Not Found)} if the funcionalidadeGrupoAcessoPadrao is not found,
     * or with status {@code 500 (Internal Server Error)} if the funcionalidadeGrupoAcessoPadrao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FuncionalidadeGrupoAcessoPadrao> partialUpdateFuncionalidadeGrupoAcessoPadrao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FuncionalidadeGrupoAcessoPadrao funcionalidadeGrupoAcessoPadrao
    ) throws URISyntaxException {
        log.debug("REST request to partial update FuncionalidadeGrupoAcessoPadrao partially : {}, {}", id, funcionalidadeGrupoAcessoPadrao);
        if (funcionalidadeGrupoAcessoPadrao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, funcionalidadeGrupoAcessoPadrao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!funcionalidadeGrupoAcessoPadraoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FuncionalidadeGrupoAcessoPadrao> result = funcionalidadeGrupoAcessoPadraoService.partialUpdate(
            funcionalidadeGrupoAcessoPadrao
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, funcionalidadeGrupoAcessoPadrao.getId().toString())
        );
    }

    /**
     * {@code GET  /funcionalidade-grupo-acesso-padraos} : get all the funcionalidadeGrupoAcessoPadraos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of funcionalidadeGrupoAcessoPadraos in body.
     */
    @GetMapping("")
    public List<FuncionalidadeGrupoAcessoPadrao> getAllFuncionalidadeGrupoAcessoPadraos(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all FuncionalidadeGrupoAcessoPadraos");
        return funcionalidadeGrupoAcessoPadraoService.findAll();
    }

    /**
     * {@code GET  /funcionalidade-grupo-acesso-padraos/:id} : get the "id" funcionalidadeGrupoAcessoPadrao.
     *
     * @param id the id of the funcionalidadeGrupoAcessoPadrao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the funcionalidadeGrupoAcessoPadrao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FuncionalidadeGrupoAcessoPadrao> getFuncionalidadeGrupoAcessoPadrao(@PathVariable("id") Long id) {
        log.debug("REST request to get FuncionalidadeGrupoAcessoPadrao : {}", id);
        Optional<FuncionalidadeGrupoAcessoPadrao> funcionalidadeGrupoAcessoPadrao = funcionalidadeGrupoAcessoPadraoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(funcionalidadeGrupoAcessoPadrao);
    }

    /**
     * {@code DELETE  /funcionalidade-grupo-acesso-padraos/:id} : delete the "id" funcionalidadeGrupoAcessoPadrao.
     *
     * @param id the id of the funcionalidadeGrupoAcessoPadrao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuncionalidadeGrupoAcessoPadrao(@PathVariable("id") Long id) {
        log.debug("REST request to delete FuncionalidadeGrupoAcessoPadrao : {}", id);
        funcionalidadeGrupoAcessoPadraoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
