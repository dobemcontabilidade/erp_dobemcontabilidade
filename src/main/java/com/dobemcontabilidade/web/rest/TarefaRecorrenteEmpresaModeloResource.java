package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.TarefaRecorrenteEmpresaModelo;
import com.dobemcontabilidade.repository.TarefaRecorrenteEmpresaModeloRepository;
import com.dobemcontabilidade.service.TarefaRecorrenteEmpresaModeloService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.TarefaRecorrenteEmpresaModelo}.
 */
@RestController
@RequestMapping("/api/tarefa-recorrente-empresa-modelos")
public class TarefaRecorrenteEmpresaModeloResource {

    private static final Logger log = LoggerFactory.getLogger(TarefaRecorrenteEmpresaModeloResource.class);

    private static final String ENTITY_NAME = "tarefaRecorrenteEmpresaModelo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TarefaRecorrenteEmpresaModeloService tarefaRecorrenteEmpresaModeloService;

    private final TarefaRecorrenteEmpresaModeloRepository tarefaRecorrenteEmpresaModeloRepository;

    public TarefaRecorrenteEmpresaModeloResource(
        TarefaRecorrenteEmpresaModeloService tarefaRecorrenteEmpresaModeloService,
        TarefaRecorrenteEmpresaModeloRepository tarefaRecorrenteEmpresaModeloRepository
    ) {
        this.tarefaRecorrenteEmpresaModeloService = tarefaRecorrenteEmpresaModeloService;
        this.tarefaRecorrenteEmpresaModeloRepository = tarefaRecorrenteEmpresaModeloRepository;
    }

    /**
     * {@code POST  /tarefa-recorrente-empresa-modelos} : Create a new tarefaRecorrenteEmpresaModelo.
     *
     * @param tarefaRecorrenteEmpresaModelo the tarefaRecorrenteEmpresaModelo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tarefaRecorrenteEmpresaModelo, or with status {@code 400 (Bad Request)} if the tarefaRecorrenteEmpresaModelo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TarefaRecorrenteEmpresaModelo> createTarefaRecorrenteEmpresaModelo(
        @Valid @RequestBody TarefaRecorrenteEmpresaModelo tarefaRecorrenteEmpresaModelo
    ) throws URISyntaxException {
        log.debug("REST request to save TarefaRecorrenteEmpresaModelo : {}", tarefaRecorrenteEmpresaModelo);
        if (tarefaRecorrenteEmpresaModelo.getId() != null) {
            throw new BadRequestAlertException("A new tarefaRecorrenteEmpresaModelo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tarefaRecorrenteEmpresaModelo = tarefaRecorrenteEmpresaModeloService.save(tarefaRecorrenteEmpresaModelo);
        return ResponseEntity.created(new URI("/api/tarefa-recorrente-empresa-modelos/" + tarefaRecorrenteEmpresaModelo.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tarefaRecorrenteEmpresaModelo.getId().toString())
            )
            .body(tarefaRecorrenteEmpresaModelo);
    }

    /**
     * {@code PUT  /tarefa-recorrente-empresa-modelos/:id} : Updates an existing tarefaRecorrenteEmpresaModelo.
     *
     * @param id the id of the tarefaRecorrenteEmpresaModelo to save.
     * @param tarefaRecorrenteEmpresaModelo the tarefaRecorrenteEmpresaModelo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarefaRecorrenteEmpresaModelo,
     * or with status {@code 400 (Bad Request)} if the tarefaRecorrenteEmpresaModelo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tarefaRecorrenteEmpresaModelo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TarefaRecorrenteEmpresaModelo> updateTarefaRecorrenteEmpresaModelo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TarefaRecorrenteEmpresaModelo tarefaRecorrenteEmpresaModelo
    ) throws URISyntaxException {
        log.debug("REST request to update TarefaRecorrenteEmpresaModelo : {}, {}", id, tarefaRecorrenteEmpresaModelo);
        if (tarefaRecorrenteEmpresaModelo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tarefaRecorrenteEmpresaModelo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tarefaRecorrenteEmpresaModeloRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tarefaRecorrenteEmpresaModelo = tarefaRecorrenteEmpresaModeloService.update(tarefaRecorrenteEmpresaModelo);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarefaRecorrenteEmpresaModelo.getId().toString())
            )
            .body(tarefaRecorrenteEmpresaModelo);
    }

    /**
     * {@code PATCH  /tarefa-recorrente-empresa-modelos/:id} : Partial updates given fields of an existing tarefaRecorrenteEmpresaModelo, field will ignore if it is null
     *
     * @param id the id of the tarefaRecorrenteEmpresaModelo to save.
     * @param tarefaRecorrenteEmpresaModelo the tarefaRecorrenteEmpresaModelo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarefaRecorrenteEmpresaModelo,
     * or with status {@code 400 (Bad Request)} if the tarefaRecorrenteEmpresaModelo is not valid,
     * or with status {@code 404 (Not Found)} if the tarefaRecorrenteEmpresaModelo is not found,
     * or with status {@code 500 (Internal Server Error)} if the tarefaRecorrenteEmpresaModelo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TarefaRecorrenteEmpresaModelo> partialUpdateTarefaRecorrenteEmpresaModelo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TarefaRecorrenteEmpresaModelo tarefaRecorrenteEmpresaModelo
    ) throws URISyntaxException {
        log.debug("REST request to partial update TarefaRecorrenteEmpresaModelo partially : {}, {}", id, tarefaRecorrenteEmpresaModelo);
        if (tarefaRecorrenteEmpresaModelo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tarefaRecorrenteEmpresaModelo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tarefaRecorrenteEmpresaModeloRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TarefaRecorrenteEmpresaModelo> result = tarefaRecorrenteEmpresaModeloService.partialUpdate(tarefaRecorrenteEmpresaModelo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarefaRecorrenteEmpresaModelo.getId().toString())
        );
    }

    /**
     * {@code GET  /tarefa-recorrente-empresa-modelos} : get all the tarefaRecorrenteEmpresaModelos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tarefaRecorrenteEmpresaModelos in body.
     */
    @GetMapping("")
    public List<TarefaRecorrenteEmpresaModelo> getAllTarefaRecorrenteEmpresaModelos() {
        log.debug("REST request to get all TarefaRecorrenteEmpresaModelos");
        return tarefaRecorrenteEmpresaModeloService.findAll();
    }

    /**
     * {@code GET  /tarefa-recorrente-empresa-modelos/:id} : get the "id" tarefaRecorrenteEmpresaModelo.
     *
     * @param id the id of the tarefaRecorrenteEmpresaModelo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tarefaRecorrenteEmpresaModelo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TarefaRecorrenteEmpresaModelo> getTarefaRecorrenteEmpresaModelo(@PathVariable("id") Long id) {
        log.debug("REST request to get TarefaRecorrenteEmpresaModelo : {}", id);
        Optional<TarefaRecorrenteEmpresaModelo> tarefaRecorrenteEmpresaModelo = tarefaRecorrenteEmpresaModeloService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tarefaRecorrenteEmpresaModelo);
    }

    /**
     * {@code DELETE  /tarefa-recorrente-empresa-modelos/:id} : delete the "id" tarefaRecorrenteEmpresaModelo.
     *
     * @param id the id of the tarefaRecorrenteEmpresaModelo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarefaRecorrenteEmpresaModelo(@PathVariable("id") Long id) {
        log.debug("REST request to delete TarefaRecorrenteEmpresaModelo : {}", id);
        tarefaRecorrenteEmpresaModeloService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
