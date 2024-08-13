package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.ImpostoEmpresaModelo;
import com.dobemcontabilidade.repository.ImpostoEmpresaModeloRepository;
import com.dobemcontabilidade.service.ImpostoEmpresaModeloService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.ImpostoEmpresaModelo}.
 */
@RestController
@RequestMapping("/api/imposto-empresa-modelos")
public class ImpostoEmpresaModeloResource {

    private static final Logger log = LoggerFactory.getLogger(ImpostoEmpresaModeloResource.class);

    private static final String ENTITY_NAME = "impostoEmpresaModelo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImpostoEmpresaModeloService impostoEmpresaModeloService;

    private final ImpostoEmpresaModeloRepository impostoEmpresaModeloRepository;

    public ImpostoEmpresaModeloResource(
        ImpostoEmpresaModeloService impostoEmpresaModeloService,
        ImpostoEmpresaModeloRepository impostoEmpresaModeloRepository
    ) {
        this.impostoEmpresaModeloService = impostoEmpresaModeloService;
        this.impostoEmpresaModeloRepository = impostoEmpresaModeloRepository;
    }

    /**
     * {@code POST  /imposto-empresa-modelos} : Create a new impostoEmpresaModelo.
     *
     * @param impostoEmpresaModelo the impostoEmpresaModelo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new impostoEmpresaModelo, or with status {@code 400 (Bad Request)} if the impostoEmpresaModelo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ImpostoEmpresaModelo> createImpostoEmpresaModelo(@Valid @RequestBody ImpostoEmpresaModelo impostoEmpresaModelo)
        throws URISyntaxException {
        log.debug("REST request to save ImpostoEmpresaModelo : {}", impostoEmpresaModelo);
        if (impostoEmpresaModelo.getId() != null) {
            throw new BadRequestAlertException("A new impostoEmpresaModelo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        impostoEmpresaModelo = impostoEmpresaModeloService.save(impostoEmpresaModelo);
        return ResponseEntity.created(new URI("/api/imposto-empresa-modelos/" + impostoEmpresaModelo.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, impostoEmpresaModelo.getId().toString()))
            .body(impostoEmpresaModelo);
    }

    /**
     * {@code PUT  /imposto-empresa-modelos/:id} : Updates an existing impostoEmpresaModelo.
     *
     * @param id the id of the impostoEmpresaModelo to save.
     * @param impostoEmpresaModelo the impostoEmpresaModelo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated impostoEmpresaModelo,
     * or with status {@code 400 (Bad Request)} if the impostoEmpresaModelo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the impostoEmpresaModelo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ImpostoEmpresaModelo> updateImpostoEmpresaModelo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ImpostoEmpresaModelo impostoEmpresaModelo
    ) throws URISyntaxException {
        log.debug("REST request to update ImpostoEmpresaModelo : {}, {}", id, impostoEmpresaModelo);
        if (impostoEmpresaModelo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, impostoEmpresaModelo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!impostoEmpresaModeloRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        impostoEmpresaModelo = impostoEmpresaModeloService.update(impostoEmpresaModelo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, impostoEmpresaModelo.getId().toString()))
            .body(impostoEmpresaModelo);
    }

    /**
     * {@code PATCH  /imposto-empresa-modelos/:id} : Partial updates given fields of an existing impostoEmpresaModelo, field will ignore if it is null
     *
     * @param id the id of the impostoEmpresaModelo to save.
     * @param impostoEmpresaModelo the impostoEmpresaModelo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated impostoEmpresaModelo,
     * or with status {@code 400 (Bad Request)} if the impostoEmpresaModelo is not valid,
     * or with status {@code 404 (Not Found)} if the impostoEmpresaModelo is not found,
     * or with status {@code 500 (Internal Server Error)} if the impostoEmpresaModelo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ImpostoEmpresaModelo> partialUpdateImpostoEmpresaModelo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ImpostoEmpresaModelo impostoEmpresaModelo
    ) throws URISyntaxException {
        log.debug("REST request to partial update ImpostoEmpresaModelo partially : {}, {}", id, impostoEmpresaModelo);
        if (impostoEmpresaModelo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, impostoEmpresaModelo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!impostoEmpresaModeloRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ImpostoEmpresaModelo> result = impostoEmpresaModeloService.partialUpdate(impostoEmpresaModelo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, impostoEmpresaModelo.getId().toString())
        );
    }

    /**
     * {@code GET  /imposto-empresa-modelos} : get all the impostoEmpresaModelos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of impostoEmpresaModelos in body.
     */
    @GetMapping("")
    public List<ImpostoEmpresaModelo> getAllImpostoEmpresaModelos(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all ImpostoEmpresaModelos");
        return impostoEmpresaModeloService.findAll();
    }

    /**
     * {@code GET  /imposto-empresa-modelos/:id} : get the "id" impostoEmpresaModelo.
     *
     * @param id the id of the impostoEmpresaModelo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the impostoEmpresaModelo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ImpostoEmpresaModelo> getImpostoEmpresaModelo(@PathVariable("id") Long id) {
        log.debug("REST request to get ImpostoEmpresaModelo : {}", id);
        Optional<ImpostoEmpresaModelo> impostoEmpresaModelo = impostoEmpresaModeloService.findOne(id);
        return ResponseUtil.wrapOrNotFound(impostoEmpresaModelo);
    }

    /**
     * {@code DELETE  /imposto-empresa-modelos/:id} : delete the "id" impostoEmpresaModelo.
     *
     * @param id the id of the impostoEmpresaModelo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImpostoEmpresaModelo(@PathVariable("id") Long id) {
        log.debug("REST request to delete ImpostoEmpresaModelo : {}", id);
        impostoEmpresaModeloService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
