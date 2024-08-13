package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.TarefaRecorrenteExecucao;
import com.dobemcontabilidade.repository.TarefaRecorrenteExecucaoRepository;
import com.dobemcontabilidade.service.TarefaRecorrenteExecucaoService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.TarefaRecorrenteExecucao}.
 */
@RestController
@RequestMapping("/api/tarefa-recorrente-execucaos")
public class TarefaRecorrenteExecucaoResource {

    private static final Logger log = LoggerFactory.getLogger(TarefaRecorrenteExecucaoResource.class);

    private static final String ENTITY_NAME = "tarefaRecorrenteExecucao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TarefaRecorrenteExecucaoService tarefaRecorrenteExecucaoService;

    private final TarefaRecorrenteExecucaoRepository tarefaRecorrenteExecucaoRepository;

    public TarefaRecorrenteExecucaoResource(
        TarefaRecorrenteExecucaoService tarefaRecorrenteExecucaoService,
        TarefaRecorrenteExecucaoRepository tarefaRecorrenteExecucaoRepository
    ) {
        this.tarefaRecorrenteExecucaoService = tarefaRecorrenteExecucaoService;
        this.tarefaRecorrenteExecucaoRepository = tarefaRecorrenteExecucaoRepository;
    }

    /**
     * {@code POST  /tarefa-recorrente-execucaos} : Create a new tarefaRecorrenteExecucao.
     *
     * @param tarefaRecorrenteExecucao the tarefaRecorrenteExecucao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tarefaRecorrenteExecucao, or with status {@code 400 (Bad Request)} if the tarefaRecorrenteExecucao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TarefaRecorrenteExecucao> createTarefaRecorrenteExecucao(
        @Valid @RequestBody TarefaRecorrenteExecucao tarefaRecorrenteExecucao
    ) throws URISyntaxException {
        log.debug("REST request to save TarefaRecorrenteExecucao : {}", tarefaRecorrenteExecucao);
        if (tarefaRecorrenteExecucao.getId() != null) {
            throw new BadRequestAlertException("A new tarefaRecorrenteExecucao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tarefaRecorrenteExecucao = tarefaRecorrenteExecucaoService.save(tarefaRecorrenteExecucao);
        return ResponseEntity.created(new URI("/api/tarefa-recorrente-execucaos/" + tarefaRecorrenteExecucao.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tarefaRecorrenteExecucao.getId().toString()))
            .body(tarefaRecorrenteExecucao);
    }

    /**
     * {@code PUT  /tarefa-recorrente-execucaos/:id} : Updates an existing tarefaRecorrenteExecucao.
     *
     * @param id the id of the tarefaRecorrenteExecucao to save.
     * @param tarefaRecorrenteExecucao the tarefaRecorrenteExecucao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarefaRecorrenteExecucao,
     * or with status {@code 400 (Bad Request)} if the tarefaRecorrenteExecucao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tarefaRecorrenteExecucao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TarefaRecorrenteExecucao> updateTarefaRecorrenteExecucao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TarefaRecorrenteExecucao tarefaRecorrenteExecucao
    ) throws URISyntaxException {
        log.debug("REST request to update TarefaRecorrenteExecucao : {}, {}", id, tarefaRecorrenteExecucao);
        if (tarefaRecorrenteExecucao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tarefaRecorrenteExecucao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tarefaRecorrenteExecucaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tarefaRecorrenteExecucao = tarefaRecorrenteExecucaoService.update(tarefaRecorrenteExecucao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarefaRecorrenteExecucao.getId().toString()))
            .body(tarefaRecorrenteExecucao);
    }

    /**
     * {@code PATCH  /tarefa-recorrente-execucaos/:id} : Partial updates given fields of an existing tarefaRecorrenteExecucao, field will ignore if it is null
     *
     * @param id the id of the tarefaRecorrenteExecucao to save.
     * @param tarefaRecorrenteExecucao the tarefaRecorrenteExecucao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarefaRecorrenteExecucao,
     * or with status {@code 400 (Bad Request)} if the tarefaRecorrenteExecucao is not valid,
     * or with status {@code 404 (Not Found)} if the tarefaRecorrenteExecucao is not found,
     * or with status {@code 500 (Internal Server Error)} if the tarefaRecorrenteExecucao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TarefaRecorrenteExecucao> partialUpdateTarefaRecorrenteExecucao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TarefaRecorrenteExecucao tarefaRecorrenteExecucao
    ) throws URISyntaxException {
        log.debug("REST request to partial update TarefaRecorrenteExecucao partially : {}, {}", id, tarefaRecorrenteExecucao);
        if (tarefaRecorrenteExecucao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tarefaRecorrenteExecucao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tarefaRecorrenteExecucaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TarefaRecorrenteExecucao> result = tarefaRecorrenteExecucaoService.partialUpdate(tarefaRecorrenteExecucao);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarefaRecorrenteExecucao.getId().toString())
        );
    }

    /**
     * {@code GET  /tarefa-recorrente-execucaos} : get all the tarefaRecorrenteExecucaos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tarefaRecorrenteExecucaos in body.
     */
    @GetMapping("")
    public List<TarefaRecorrenteExecucao> getAllTarefaRecorrenteExecucaos(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all TarefaRecorrenteExecucaos");
        return tarefaRecorrenteExecucaoService.findAll();
    }

    /**
     * {@code GET  /tarefa-recorrente-execucaos/:id} : get the "id" tarefaRecorrenteExecucao.
     *
     * @param id the id of the tarefaRecorrenteExecucao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tarefaRecorrenteExecucao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TarefaRecorrenteExecucao> getTarefaRecorrenteExecucao(@PathVariable("id") Long id) {
        log.debug("REST request to get TarefaRecorrenteExecucao : {}", id);
        Optional<TarefaRecorrenteExecucao> tarefaRecorrenteExecucao = tarefaRecorrenteExecucaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tarefaRecorrenteExecucao);
    }

    /**
     * {@code DELETE  /tarefa-recorrente-execucaos/:id} : delete the "id" tarefaRecorrenteExecucao.
     *
     * @param id the id of the tarefaRecorrenteExecucao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarefaRecorrenteExecucao(@PathVariable("id") Long id) {
        log.debug("REST request to delete TarefaRecorrenteExecucao : {}", id);
        tarefaRecorrenteExecucaoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
