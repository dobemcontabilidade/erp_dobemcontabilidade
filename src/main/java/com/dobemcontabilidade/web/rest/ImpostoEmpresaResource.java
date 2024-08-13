package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.ImpostoEmpresa;
import com.dobemcontabilidade.repository.ImpostoEmpresaRepository;
import com.dobemcontabilidade.service.ImpostoEmpresaService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.ImpostoEmpresa}.
 */
@RestController
@RequestMapping("/api/imposto-empresas")
public class ImpostoEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(ImpostoEmpresaResource.class);

    private static final String ENTITY_NAME = "impostoEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImpostoEmpresaService impostoEmpresaService;

    private final ImpostoEmpresaRepository impostoEmpresaRepository;

    public ImpostoEmpresaResource(ImpostoEmpresaService impostoEmpresaService, ImpostoEmpresaRepository impostoEmpresaRepository) {
        this.impostoEmpresaService = impostoEmpresaService;
        this.impostoEmpresaRepository = impostoEmpresaRepository;
    }

    /**
     * {@code POST  /imposto-empresas} : Create a new impostoEmpresa.
     *
     * @param impostoEmpresa the impostoEmpresa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new impostoEmpresa, or with status {@code 400 (Bad Request)} if the impostoEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ImpostoEmpresa> createImpostoEmpresa(@Valid @RequestBody ImpostoEmpresa impostoEmpresa)
        throws URISyntaxException {
        log.debug("REST request to save ImpostoEmpresa : {}", impostoEmpresa);
        if (impostoEmpresa.getId() != null) {
            throw new BadRequestAlertException("A new impostoEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        impostoEmpresa = impostoEmpresaService.save(impostoEmpresa);
        return ResponseEntity.created(new URI("/api/imposto-empresas/" + impostoEmpresa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, impostoEmpresa.getId().toString()))
            .body(impostoEmpresa);
    }

    /**
     * {@code PUT  /imposto-empresas/:id} : Updates an existing impostoEmpresa.
     *
     * @param id the id of the impostoEmpresa to save.
     * @param impostoEmpresa the impostoEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated impostoEmpresa,
     * or with status {@code 400 (Bad Request)} if the impostoEmpresa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the impostoEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ImpostoEmpresa> updateImpostoEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ImpostoEmpresa impostoEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to update ImpostoEmpresa : {}, {}", id, impostoEmpresa);
        if (impostoEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, impostoEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!impostoEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        impostoEmpresa = impostoEmpresaService.update(impostoEmpresa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, impostoEmpresa.getId().toString()))
            .body(impostoEmpresa);
    }

    /**
     * {@code PATCH  /imposto-empresas/:id} : Partial updates given fields of an existing impostoEmpresa, field will ignore if it is null
     *
     * @param id the id of the impostoEmpresa to save.
     * @param impostoEmpresa the impostoEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated impostoEmpresa,
     * or with status {@code 400 (Bad Request)} if the impostoEmpresa is not valid,
     * or with status {@code 404 (Not Found)} if the impostoEmpresa is not found,
     * or with status {@code 500 (Internal Server Error)} if the impostoEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ImpostoEmpresa> partialUpdateImpostoEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ImpostoEmpresa impostoEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to partial update ImpostoEmpresa partially : {}, {}", id, impostoEmpresa);
        if (impostoEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, impostoEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!impostoEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ImpostoEmpresa> result = impostoEmpresaService.partialUpdate(impostoEmpresa);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, impostoEmpresa.getId().toString())
        );
    }

    /**
     * {@code GET  /imposto-empresas} : get all the impostoEmpresas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of impostoEmpresas in body.
     */
    @GetMapping("")
    public List<ImpostoEmpresa> getAllImpostoEmpresas(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all ImpostoEmpresas");
        return impostoEmpresaService.findAll();
    }

    /**
     * {@code GET  /imposto-empresas/:id} : get the "id" impostoEmpresa.
     *
     * @param id the id of the impostoEmpresa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the impostoEmpresa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ImpostoEmpresa> getImpostoEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get ImpostoEmpresa : {}", id);
        Optional<ImpostoEmpresa> impostoEmpresa = impostoEmpresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(impostoEmpresa);
    }

    /**
     * {@code DELETE  /imposto-empresas/:id} : delete the "id" impostoEmpresa.
     *
     * @param id the id of the impostoEmpresa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImpostoEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete ImpostoEmpresa : {}", id);
        impostoEmpresaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
