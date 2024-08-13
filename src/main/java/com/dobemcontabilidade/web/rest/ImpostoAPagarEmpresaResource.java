package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.ImpostoAPagarEmpresa;
import com.dobemcontabilidade.repository.ImpostoAPagarEmpresaRepository;
import com.dobemcontabilidade.service.ImpostoAPagarEmpresaService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.ImpostoAPagarEmpresa}.
 */
@RestController
@RequestMapping("/api/imposto-a-pagar-empresas")
public class ImpostoAPagarEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(ImpostoAPagarEmpresaResource.class);

    private static final String ENTITY_NAME = "impostoAPagarEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImpostoAPagarEmpresaService impostoAPagarEmpresaService;

    private final ImpostoAPagarEmpresaRepository impostoAPagarEmpresaRepository;

    public ImpostoAPagarEmpresaResource(
        ImpostoAPagarEmpresaService impostoAPagarEmpresaService,
        ImpostoAPagarEmpresaRepository impostoAPagarEmpresaRepository
    ) {
        this.impostoAPagarEmpresaService = impostoAPagarEmpresaService;
        this.impostoAPagarEmpresaRepository = impostoAPagarEmpresaRepository;
    }

    /**
     * {@code POST  /imposto-a-pagar-empresas} : Create a new impostoAPagarEmpresa.
     *
     * @param impostoAPagarEmpresa the impostoAPagarEmpresa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new impostoAPagarEmpresa, or with status {@code 400 (Bad Request)} if the impostoAPagarEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ImpostoAPagarEmpresa> createImpostoAPagarEmpresa(@Valid @RequestBody ImpostoAPagarEmpresa impostoAPagarEmpresa)
        throws URISyntaxException {
        log.debug("REST request to save ImpostoAPagarEmpresa : {}", impostoAPagarEmpresa);
        if (impostoAPagarEmpresa.getId() != null) {
            throw new BadRequestAlertException("A new impostoAPagarEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        impostoAPagarEmpresa = impostoAPagarEmpresaService.save(impostoAPagarEmpresa);
        return ResponseEntity.created(new URI("/api/imposto-a-pagar-empresas/" + impostoAPagarEmpresa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, impostoAPagarEmpresa.getId().toString()))
            .body(impostoAPagarEmpresa);
    }

    /**
     * {@code PUT  /imposto-a-pagar-empresas/:id} : Updates an existing impostoAPagarEmpresa.
     *
     * @param id the id of the impostoAPagarEmpresa to save.
     * @param impostoAPagarEmpresa the impostoAPagarEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated impostoAPagarEmpresa,
     * or with status {@code 400 (Bad Request)} if the impostoAPagarEmpresa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the impostoAPagarEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ImpostoAPagarEmpresa> updateImpostoAPagarEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ImpostoAPagarEmpresa impostoAPagarEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to update ImpostoAPagarEmpresa : {}, {}", id, impostoAPagarEmpresa);
        if (impostoAPagarEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, impostoAPagarEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!impostoAPagarEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        impostoAPagarEmpresa = impostoAPagarEmpresaService.update(impostoAPagarEmpresa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, impostoAPagarEmpresa.getId().toString()))
            .body(impostoAPagarEmpresa);
    }

    /**
     * {@code PATCH  /imposto-a-pagar-empresas/:id} : Partial updates given fields of an existing impostoAPagarEmpresa, field will ignore if it is null
     *
     * @param id the id of the impostoAPagarEmpresa to save.
     * @param impostoAPagarEmpresa the impostoAPagarEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated impostoAPagarEmpresa,
     * or with status {@code 400 (Bad Request)} if the impostoAPagarEmpresa is not valid,
     * or with status {@code 404 (Not Found)} if the impostoAPagarEmpresa is not found,
     * or with status {@code 500 (Internal Server Error)} if the impostoAPagarEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ImpostoAPagarEmpresa> partialUpdateImpostoAPagarEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ImpostoAPagarEmpresa impostoAPagarEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to partial update ImpostoAPagarEmpresa partially : {}, {}", id, impostoAPagarEmpresa);
        if (impostoAPagarEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, impostoAPagarEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!impostoAPagarEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ImpostoAPagarEmpresa> result = impostoAPagarEmpresaService.partialUpdate(impostoAPagarEmpresa);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, impostoAPagarEmpresa.getId().toString())
        );
    }

    /**
     * {@code GET  /imposto-a-pagar-empresas} : get all the impostoAPagarEmpresas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of impostoAPagarEmpresas in body.
     */
    @GetMapping("")
    public List<ImpostoAPagarEmpresa> getAllImpostoAPagarEmpresas(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all ImpostoAPagarEmpresas");
        return impostoAPagarEmpresaService.findAll();
    }

    /**
     * {@code GET  /imposto-a-pagar-empresas/:id} : get the "id" impostoAPagarEmpresa.
     *
     * @param id the id of the impostoAPagarEmpresa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the impostoAPagarEmpresa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ImpostoAPagarEmpresa> getImpostoAPagarEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get ImpostoAPagarEmpresa : {}", id);
        Optional<ImpostoAPagarEmpresa> impostoAPagarEmpresa = impostoAPagarEmpresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(impostoAPagarEmpresa);
    }

    /**
     * {@code DELETE  /imposto-a-pagar-empresas/:id} : delete the "id" impostoAPagarEmpresa.
     *
     * @param id the id of the impostoAPagarEmpresa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImpostoAPagarEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete ImpostoAPagarEmpresa : {}", id);
        impostoAPagarEmpresaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
