package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.TarefaOrdemServicoExecucao;
import com.dobemcontabilidade.repository.TarefaOrdemServicoExecucaoRepository;
import com.dobemcontabilidade.service.TarefaOrdemServicoExecucaoService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.TarefaOrdemServicoExecucao}.
 */
@RestController
@RequestMapping("/api/tarefa-ordem-servico-execucaos")
public class TarefaOrdemServicoExecucaoResource {

    private static final Logger log = LoggerFactory.getLogger(TarefaOrdemServicoExecucaoResource.class);

    private static final String ENTITY_NAME = "tarefaOrdemServicoExecucao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TarefaOrdemServicoExecucaoService tarefaOrdemServicoExecucaoService;

    private final TarefaOrdemServicoExecucaoRepository tarefaOrdemServicoExecucaoRepository;

    public TarefaOrdemServicoExecucaoResource(
        TarefaOrdemServicoExecucaoService tarefaOrdemServicoExecucaoService,
        TarefaOrdemServicoExecucaoRepository tarefaOrdemServicoExecucaoRepository
    ) {
        this.tarefaOrdemServicoExecucaoService = tarefaOrdemServicoExecucaoService;
        this.tarefaOrdemServicoExecucaoRepository = tarefaOrdemServicoExecucaoRepository;
    }

    /**
     * {@code POST  /tarefa-ordem-servico-execucaos} : Create a new tarefaOrdemServicoExecucao.
     *
     * @param tarefaOrdemServicoExecucao the tarefaOrdemServicoExecucao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tarefaOrdemServicoExecucao, or with status {@code 400 (Bad Request)} if the tarefaOrdemServicoExecucao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TarefaOrdemServicoExecucao> createTarefaOrdemServicoExecucao(
        @Valid @RequestBody TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao
    ) throws URISyntaxException {
        log.debug("REST request to save TarefaOrdemServicoExecucao : {}", tarefaOrdemServicoExecucao);
        if (tarefaOrdemServicoExecucao.getId() != null) {
            throw new BadRequestAlertException("A new tarefaOrdemServicoExecucao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tarefaOrdemServicoExecucao = tarefaOrdemServicoExecucaoService.save(tarefaOrdemServicoExecucao);
        return ResponseEntity.created(new URI("/api/tarefa-ordem-servico-execucaos/" + tarefaOrdemServicoExecucao.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tarefaOrdemServicoExecucao.getId().toString())
            )
            .body(tarefaOrdemServicoExecucao);
    }

    /**
     * {@code PUT  /tarefa-ordem-servico-execucaos/:id} : Updates an existing tarefaOrdemServicoExecucao.
     *
     * @param id the id of the tarefaOrdemServicoExecucao to save.
     * @param tarefaOrdemServicoExecucao the tarefaOrdemServicoExecucao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarefaOrdemServicoExecucao,
     * or with status {@code 400 (Bad Request)} if the tarefaOrdemServicoExecucao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tarefaOrdemServicoExecucao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TarefaOrdemServicoExecucao> updateTarefaOrdemServicoExecucao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao
    ) throws URISyntaxException {
        log.debug("REST request to update TarefaOrdemServicoExecucao : {}, {}", id, tarefaOrdemServicoExecucao);
        if (tarefaOrdemServicoExecucao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tarefaOrdemServicoExecucao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tarefaOrdemServicoExecucaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tarefaOrdemServicoExecucao = tarefaOrdemServicoExecucaoService.update(tarefaOrdemServicoExecucao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarefaOrdemServicoExecucao.getId().toString()))
            .body(tarefaOrdemServicoExecucao);
    }

    /**
     * {@code PATCH  /tarefa-ordem-servico-execucaos/:id} : Partial updates given fields of an existing tarefaOrdemServicoExecucao, field will ignore if it is null
     *
     * @param id the id of the tarefaOrdemServicoExecucao to save.
     * @param tarefaOrdemServicoExecucao the tarefaOrdemServicoExecucao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarefaOrdemServicoExecucao,
     * or with status {@code 400 (Bad Request)} if the tarefaOrdemServicoExecucao is not valid,
     * or with status {@code 404 (Not Found)} if the tarefaOrdemServicoExecucao is not found,
     * or with status {@code 500 (Internal Server Error)} if the tarefaOrdemServicoExecucao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TarefaOrdemServicoExecucao> partialUpdateTarefaOrdemServicoExecucao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao
    ) throws URISyntaxException {
        log.debug("REST request to partial update TarefaOrdemServicoExecucao partially : {}, {}", id, tarefaOrdemServicoExecucao);
        if (tarefaOrdemServicoExecucao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tarefaOrdemServicoExecucao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tarefaOrdemServicoExecucaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TarefaOrdemServicoExecucao> result = tarefaOrdemServicoExecucaoService.partialUpdate(tarefaOrdemServicoExecucao);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarefaOrdemServicoExecucao.getId().toString())
        );
    }

    /**
     * {@code GET  /tarefa-ordem-servico-execucaos} : get all the tarefaOrdemServicoExecucaos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tarefaOrdemServicoExecucaos in body.
     */
    @GetMapping("")
    public List<TarefaOrdemServicoExecucao> getAllTarefaOrdemServicoExecucaos(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all TarefaOrdemServicoExecucaos");
        return tarefaOrdemServicoExecucaoService.findAll();
    }

    /**
     * {@code GET  /tarefa-ordem-servico-execucaos/:id} : get the "id" tarefaOrdemServicoExecucao.
     *
     * @param id the id of the tarefaOrdemServicoExecucao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tarefaOrdemServicoExecucao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TarefaOrdemServicoExecucao> getTarefaOrdemServicoExecucao(@PathVariable("id") Long id) {
        log.debug("REST request to get TarefaOrdemServicoExecucao : {}", id);
        Optional<TarefaOrdemServicoExecucao> tarefaOrdemServicoExecucao = tarefaOrdemServicoExecucaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tarefaOrdemServicoExecucao);
    }

    /**
     * {@code DELETE  /tarefa-ordem-servico-execucaos/:id} : delete the "id" tarefaOrdemServicoExecucao.
     *
     * @param id the id of the tarefaOrdemServicoExecucao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarefaOrdemServicoExecucao(@PathVariable("id") Long id) {
        log.debug("REST request to delete TarefaOrdemServicoExecucao : {}", id);
        tarefaOrdemServicoExecucaoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
