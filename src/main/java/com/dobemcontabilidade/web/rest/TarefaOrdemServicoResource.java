package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.TarefaOrdemServico;
import com.dobemcontabilidade.repository.TarefaOrdemServicoRepository;
import com.dobemcontabilidade.service.TarefaOrdemServicoService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.TarefaOrdemServico}.
 */
@RestController
@RequestMapping("/api/tarefa-ordem-servicos")
public class TarefaOrdemServicoResource {

    private static final Logger log = LoggerFactory.getLogger(TarefaOrdemServicoResource.class);

    private static final String ENTITY_NAME = "tarefaOrdemServico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TarefaOrdemServicoService tarefaOrdemServicoService;

    private final TarefaOrdemServicoRepository tarefaOrdemServicoRepository;

    public TarefaOrdemServicoResource(
        TarefaOrdemServicoService tarefaOrdemServicoService,
        TarefaOrdemServicoRepository tarefaOrdemServicoRepository
    ) {
        this.tarefaOrdemServicoService = tarefaOrdemServicoService;
        this.tarefaOrdemServicoRepository = tarefaOrdemServicoRepository;
    }

    /**
     * {@code POST  /tarefa-ordem-servicos} : Create a new tarefaOrdemServico.
     *
     * @param tarefaOrdemServico the tarefaOrdemServico to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tarefaOrdemServico, or with status {@code 400 (Bad Request)} if the tarefaOrdemServico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TarefaOrdemServico> createTarefaOrdemServico(@Valid @RequestBody TarefaOrdemServico tarefaOrdemServico)
        throws URISyntaxException {
        log.debug("REST request to save TarefaOrdemServico : {}", tarefaOrdemServico);
        if (tarefaOrdemServico.getId() != null) {
            throw new BadRequestAlertException("A new tarefaOrdemServico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tarefaOrdemServico = tarefaOrdemServicoService.save(tarefaOrdemServico);
        return ResponseEntity.created(new URI("/api/tarefa-ordem-servicos/" + tarefaOrdemServico.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tarefaOrdemServico.getId().toString()))
            .body(tarefaOrdemServico);
    }

    /**
     * {@code PUT  /tarefa-ordem-servicos/:id} : Updates an existing tarefaOrdemServico.
     *
     * @param id the id of the tarefaOrdemServico to save.
     * @param tarefaOrdemServico the tarefaOrdemServico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarefaOrdemServico,
     * or with status {@code 400 (Bad Request)} if the tarefaOrdemServico is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tarefaOrdemServico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TarefaOrdemServico> updateTarefaOrdemServico(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TarefaOrdemServico tarefaOrdemServico
    ) throws URISyntaxException {
        log.debug("REST request to update TarefaOrdemServico : {}, {}", id, tarefaOrdemServico);
        if (tarefaOrdemServico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tarefaOrdemServico.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tarefaOrdemServicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tarefaOrdemServico = tarefaOrdemServicoService.update(tarefaOrdemServico);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarefaOrdemServico.getId().toString()))
            .body(tarefaOrdemServico);
    }

    /**
     * {@code PATCH  /tarefa-ordem-servicos/:id} : Partial updates given fields of an existing tarefaOrdemServico, field will ignore if it is null
     *
     * @param id the id of the tarefaOrdemServico to save.
     * @param tarefaOrdemServico the tarefaOrdemServico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarefaOrdemServico,
     * or with status {@code 400 (Bad Request)} if the tarefaOrdemServico is not valid,
     * or with status {@code 404 (Not Found)} if the tarefaOrdemServico is not found,
     * or with status {@code 500 (Internal Server Error)} if the tarefaOrdemServico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TarefaOrdemServico> partialUpdateTarefaOrdemServico(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TarefaOrdemServico tarefaOrdemServico
    ) throws URISyntaxException {
        log.debug("REST request to partial update TarefaOrdemServico partially : {}, {}", id, tarefaOrdemServico);
        if (tarefaOrdemServico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tarefaOrdemServico.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tarefaOrdemServicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TarefaOrdemServico> result = tarefaOrdemServicoService.partialUpdate(tarefaOrdemServico);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarefaOrdemServico.getId().toString())
        );
    }

    /**
     * {@code GET  /tarefa-ordem-servicos} : get all the tarefaOrdemServicos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tarefaOrdemServicos in body.
     */
    @GetMapping("")
    public List<TarefaOrdemServico> getAllTarefaOrdemServicos() {
        log.debug("REST request to get all TarefaOrdemServicos");
        return tarefaOrdemServicoService.findAll();
    }

    /**
     * {@code GET  /tarefa-ordem-servicos/:id} : get the "id" tarefaOrdemServico.
     *
     * @param id the id of the tarefaOrdemServico to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tarefaOrdemServico, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TarefaOrdemServico> getTarefaOrdemServico(@PathVariable("id") Long id) {
        log.debug("REST request to get TarefaOrdemServico : {}", id);
        Optional<TarefaOrdemServico> tarefaOrdemServico = tarefaOrdemServicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tarefaOrdemServico);
    }

    /**
     * {@code DELETE  /tarefa-ordem-servicos/:id} : delete the "id" tarefaOrdemServico.
     *
     * @param id the id of the tarefaOrdemServico to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarefaOrdemServico(@PathVariable("id") Long id) {
        log.debug("REST request to delete TarefaOrdemServico : {}", id);
        tarefaOrdemServicoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
