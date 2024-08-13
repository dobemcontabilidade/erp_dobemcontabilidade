package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.TarefaEmpresaModelo;
import com.dobemcontabilidade.repository.TarefaEmpresaModeloRepository;
import com.dobemcontabilidade.service.TarefaEmpresaModeloService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.TarefaEmpresaModelo}.
 */
@RestController
@RequestMapping("/api/tarefa-empresa-modelos")
public class TarefaEmpresaModeloResource {

    private static final Logger log = LoggerFactory.getLogger(TarefaEmpresaModeloResource.class);

    private static final String ENTITY_NAME = "tarefaEmpresaModelo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TarefaEmpresaModeloService tarefaEmpresaModeloService;

    private final TarefaEmpresaModeloRepository tarefaEmpresaModeloRepository;

    public TarefaEmpresaModeloResource(
        TarefaEmpresaModeloService tarefaEmpresaModeloService,
        TarefaEmpresaModeloRepository tarefaEmpresaModeloRepository
    ) {
        this.tarefaEmpresaModeloService = tarefaEmpresaModeloService;
        this.tarefaEmpresaModeloRepository = tarefaEmpresaModeloRepository;
    }

    /**
     * {@code POST  /tarefa-empresa-modelos} : Create a new tarefaEmpresaModelo.
     *
     * @param tarefaEmpresaModelo the tarefaEmpresaModelo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tarefaEmpresaModelo, or with status {@code 400 (Bad Request)} if the tarefaEmpresaModelo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TarefaEmpresaModelo> createTarefaEmpresaModelo(@Valid @RequestBody TarefaEmpresaModelo tarefaEmpresaModelo)
        throws URISyntaxException {
        log.debug("REST request to save TarefaEmpresaModelo : {}", tarefaEmpresaModelo);
        if (tarefaEmpresaModelo.getId() != null) {
            throw new BadRequestAlertException("A new tarefaEmpresaModelo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tarefaEmpresaModelo = tarefaEmpresaModeloService.save(tarefaEmpresaModelo);
        return ResponseEntity.created(new URI("/api/tarefa-empresa-modelos/" + tarefaEmpresaModelo.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tarefaEmpresaModelo.getId().toString()))
            .body(tarefaEmpresaModelo);
    }

    /**
     * {@code PUT  /tarefa-empresa-modelos/:id} : Updates an existing tarefaEmpresaModelo.
     *
     * @param id the id of the tarefaEmpresaModelo to save.
     * @param tarefaEmpresaModelo the tarefaEmpresaModelo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarefaEmpresaModelo,
     * or with status {@code 400 (Bad Request)} if the tarefaEmpresaModelo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tarefaEmpresaModelo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TarefaEmpresaModelo> updateTarefaEmpresaModelo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TarefaEmpresaModelo tarefaEmpresaModelo
    ) throws URISyntaxException {
        log.debug("REST request to update TarefaEmpresaModelo : {}, {}", id, tarefaEmpresaModelo);
        if (tarefaEmpresaModelo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tarefaEmpresaModelo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tarefaEmpresaModeloRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tarefaEmpresaModelo = tarefaEmpresaModeloService.update(tarefaEmpresaModelo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarefaEmpresaModelo.getId().toString()))
            .body(tarefaEmpresaModelo);
    }

    /**
     * {@code PATCH  /tarefa-empresa-modelos/:id} : Partial updates given fields of an existing tarefaEmpresaModelo, field will ignore if it is null
     *
     * @param id the id of the tarefaEmpresaModelo to save.
     * @param tarefaEmpresaModelo the tarefaEmpresaModelo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarefaEmpresaModelo,
     * or with status {@code 400 (Bad Request)} if the tarefaEmpresaModelo is not valid,
     * or with status {@code 404 (Not Found)} if the tarefaEmpresaModelo is not found,
     * or with status {@code 500 (Internal Server Error)} if the tarefaEmpresaModelo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TarefaEmpresaModelo> partialUpdateTarefaEmpresaModelo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TarefaEmpresaModelo tarefaEmpresaModelo
    ) throws URISyntaxException {
        log.debug("REST request to partial update TarefaEmpresaModelo partially : {}, {}", id, tarefaEmpresaModelo);
        if (tarefaEmpresaModelo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tarefaEmpresaModelo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tarefaEmpresaModeloRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TarefaEmpresaModelo> result = tarefaEmpresaModeloService.partialUpdate(tarefaEmpresaModelo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarefaEmpresaModelo.getId().toString())
        );
    }

    /**
     * {@code GET  /tarefa-empresa-modelos} : get all the tarefaEmpresaModelos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tarefaEmpresaModelos in body.
     */
    @GetMapping("")
    public List<TarefaEmpresaModelo> getAllTarefaEmpresaModelos(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all TarefaEmpresaModelos");
        return tarefaEmpresaModeloService.findAll();
    }

    /**
     * {@code GET  /tarefa-empresa-modelos/:id} : get the "id" tarefaEmpresaModelo.
     *
     * @param id the id of the tarefaEmpresaModelo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tarefaEmpresaModelo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TarefaEmpresaModelo> getTarefaEmpresaModelo(@PathVariable("id") Long id) {
        log.debug("REST request to get TarefaEmpresaModelo : {}", id);
        Optional<TarefaEmpresaModelo> tarefaEmpresaModelo = tarefaEmpresaModeloService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tarefaEmpresaModelo);
    }

    /**
     * {@code DELETE  /tarefa-empresa-modelos/:id} : delete the "id" tarefaEmpresaModelo.
     *
     * @param id the id of the tarefaEmpresaModelo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarefaEmpresaModelo(@PathVariable("id") Long id) {
        log.debug("REST request to delete TarefaEmpresaModelo : {}", id);
        tarefaEmpresaModeloService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
