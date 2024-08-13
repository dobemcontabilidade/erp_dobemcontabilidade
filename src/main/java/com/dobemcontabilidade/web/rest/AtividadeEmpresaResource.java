package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.AtividadeEmpresa;
import com.dobemcontabilidade.repository.AtividadeEmpresaRepository;
import com.dobemcontabilidade.service.AtividadeEmpresaService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AtividadeEmpresa}.
 */
@RestController
@RequestMapping("/api/atividade-empresas")
public class AtividadeEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(AtividadeEmpresaResource.class);

    private static final String ENTITY_NAME = "atividadeEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AtividadeEmpresaService atividadeEmpresaService;

    private final AtividadeEmpresaRepository atividadeEmpresaRepository;

    public AtividadeEmpresaResource(
        AtividadeEmpresaService atividadeEmpresaService,
        AtividadeEmpresaRepository atividadeEmpresaRepository
    ) {
        this.atividadeEmpresaService = atividadeEmpresaService;
        this.atividadeEmpresaRepository = atividadeEmpresaRepository;
    }

    /**
     * {@code POST  /atividade-empresas} : Create a new atividadeEmpresa.
     *
     * @param atividadeEmpresa the atividadeEmpresa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new atividadeEmpresa, or with status {@code 400 (Bad Request)} if the atividadeEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AtividadeEmpresa> createAtividadeEmpresa(@Valid @RequestBody AtividadeEmpresa atividadeEmpresa)
        throws URISyntaxException {
        log.debug("REST request to save AtividadeEmpresa : {}", atividadeEmpresa);
        if (atividadeEmpresa.getId() != null) {
            throw new BadRequestAlertException("A new atividadeEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        atividadeEmpresa = atividadeEmpresaService.save(atividadeEmpresa);
        return ResponseEntity.created(new URI("/api/atividade-empresas/" + atividadeEmpresa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, atividadeEmpresa.getId().toString()))
            .body(atividadeEmpresa);
    }

    /**
     * {@code PUT  /atividade-empresas/:id} : Updates an existing atividadeEmpresa.
     *
     * @param id the id of the atividadeEmpresa to save.
     * @param atividadeEmpresa the atividadeEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated atividadeEmpresa,
     * or with status {@code 400 (Bad Request)} if the atividadeEmpresa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the atividadeEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AtividadeEmpresa> updateAtividadeEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AtividadeEmpresa atividadeEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to update AtividadeEmpresa : {}, {}", id, atividadeEmpresa);
        if (atividadeEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, atividadeEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!atividadeEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        atividadeEmpresa = atividadeEmpresaService.update(atividadeEmpresa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, atividadeEmpresa.getId().toString()))
            .body(atividadeEmpresa);
    }

    /**
     * {@code PATCH  /atividade-empresas/:id} : Partial updates given fields of an existing atividadeEmpresa, field will ignore if it is null
     *
     * @param id the id of the atividadeEmpresa to save.
     * @param atividadeEmpresa the atividadeEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated atividadeEmpresa,
     * or with status {@code 400 (Bad Request)} if the atividadeEmpresa is not valid,
     * or with status {@code 404 (Not Found)} if the atividadeEmpresa is not found,
     * or with status {@code 500 (Internal Server Error)} if the atividadeEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AtividadeEmpresa> partialUpdateAtividadeEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AtividadeEmpresa atividadeEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to partial update AtividadeEmpresa partially : {}, {}", id, atividadeEmpresa);
        if (atividadeEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, atividadeEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!atividadeEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AtividadeEmpresa> result = atividadeEmpresaService.partialUpdate(atividadeEmpresa);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, atividadeEmpresa.getId().toString())
        );
    }

    /**
     * {@code GET  /atividade-empresas} : get all the atividadeEmpresas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of atividadeEmpresas in body.
     */
    @GetMapping("")
    public List<AtividadeEmpresa> getAllAtividadeEmpresas(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all AtividadeEmpresas");
        return atividadeEmpresaService.findAll();
    }

    /**
     * {@code GET  /atividade-empresas/:id} : get the "id" atividadeEmpresa.
     *
     * @param id the id of the atividadeEmpresa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the atividadeEmpresa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AtividadeEmpresa> getAtividadeEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get AtividadeEmpresa : {}", id);
        Optional<AtividadeEmpresa> atividadeEmpresa = atividadeEmpresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(atividadeEmpresa);
    }

    /**
     * {@code DELETE  /atividade-empresas/:id} : delete the "id" atividadeEmpresa.
     *
     * @param id the id of the atividadeEmpresa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAtividadeEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete AtividadeEmpresa : {}", id);
        atividadeEmpresaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
