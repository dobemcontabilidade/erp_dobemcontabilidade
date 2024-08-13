package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.AgendaTarefaOrdemServicoExecucao;
import com.dobemcontabilidade.repository.AgendaTarefaOrdemServicoExecucaoRepository;
import com.dobemcontabilidade.service.AgendaTarefaOrdemServicoExecucaoService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AgendaTarefaOrdemServicoExecucao}.
 */
@RestController
@RequestMapping("/api/agenda-tarefa-ordem-servico-execucaos")
public class AgendaTarefaOrdemServicoExecucaoResource {

    private static final Logger log = LoggerFactory.getLogger(AgendaTarefaOrdemServicoExecucaoResource.class);

    private static final String ENTITY_NAME = "agendaTarefaOrdemServicoExecucao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgendaTarefaOrdemServicoExecucaoService agendaTarefaOrdemServicoExecucaoService;

    private final AgendaTarefaOrdemServicoExecucaoRepository agendaTarefaOrdemServicoExecucaoRepository;

    public AgendaTarefaOrdemServicoExecucaoResource(
        AgendaTarefaOrdemServicoExecucaoService agendaTarefaOrdemServicoExecucaoService,
        AgendaTarefaOrdemServicoExecucaoRepository agendaTarefaOrdemServicoExecucaoRepository
    ) {
        this.agendaTarefaOrdemServicoExecucaoService = agendaTarefaOrdemServicoExecucaoService;
        this.agendaTarefaOrdemServicoExecucaoRepository = agendaTarefaOrdemServicoExecucaoRepository;
    }

    /**
     * {@code POST  /agenda-tarefa-ordem-servico-execucaos} : Create a new agendaTarefaOrdemServicoExecucao.
     *
     * @param agendaTarefaOrdemServicoExecucao the agendaTarefaOrdemServicoExecucao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agendaTarefaOrdemServicoExecucao, or with status {@code 400 (Bad Request)} if the agendaTarefaOrdemServicoExecucao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AgendaTarefaOrdemServicoExecucao> createAgendaTarefaOrdemServicoExecucao(
        @Valid @RequestBody AgendaTarefaOrdemServicoExecucao agendaTarefaOrdemServicoExecucao
    ) throws URISyntaxException {
        log.debug("REST request to save AgendaTarefaOrdemServicoExecucao : {}", agendaTarefaOrdemServicoExecucao);
        if (agendaTarefaOrdemServicoExecucao.getId() != null) {
            throw new BadRequestAlertException("A new agendaTarefaOrdemServicoExecucao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        agendaTarefaOrdemServicoExecucao = agendaTarefaOrdemServicoExecucaoService.save(agendaTarefaOrdemServicoExecucao);
        return ResponseEntity.created(new URI("/api/agenda-tarefa-ordem-servico-execucaos/" + agendaTarefaOrdemServicoExecucao.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    agendaTarefaOrdemServicoExecucao.getId().toString()
                )
            )
            .body(agendaTarefaOrdemServicoExecucao);
    }

    /**
     * {@code PUT  /agenda-tarefa-ordem-servico-execucaos/:id} : Updates an existing agendaTarefaOrdemServicoExecucao.
     *
     * @param id the id of the agendaTarefaOrdemServicoExecucao to save.
     * @param agendaTarefaOrdemServicoExecucao the agendaTarefaOrdemServicoExecucao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agendaTarefaOrdemServicoExecucao,
     * or with status {@code 400 (Bad Request)} if the agendaTarefaOrdemServicoExecucao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agendaTarefaOrdemServicoExecucao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AgendaTarefaOrdemServicoExecucao> updateAgendaTarefaOrdemServicoExecucao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AgendaTarefaOrdemServicoExecucao agendaTarefaOrdemServicoExecucao
    ) throws URISyntaxException {
        log.debug("REST request to update AgendaTarefaOrdemServicoExecucao : {}, {}", id, agendaTarefaOrdemServicoExecucao);
        if (agendaTarefaOrdemServicoExecucao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, agendaTarefaOrdemServicoExecucao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!agendaTarefaOrdemServicoExecucaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        agendaTarefaOrdemServicoExecucao = agendaTarefaOrdemServicoExecucaoService.update(agendaTarefaOrdemServicoExecucao);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agendaTarefaOrdemServicoExecucao.getId().toString())
            )
            .body(agendaTarefaOrdemServicoExecucao);
    }

    /**
     * {@code PATCH  /agenda-tarefa-ordem-servico-execucaos/:id} : Partial updates given fields of an existing agendaTarefaOrdemServicoExecucao, field will ignore if it is null
     *
     * @param id the id of the agendaTarefaOrdemServicoExecucao to save.
     * @param agendaTarefaOrdemServicoExecucao the agendaTarefaOrdemServicoExecucao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agendaTarefaOrdemServicoExecucao,
     * or with status {@code 400 (Bad Request)} if the agendaTarefaOrdemServicoExecucao is not valid,
     * or with status {@code 404 (Not Found)} if the agendaTarefaOrdemServicoExecucao is not found,
     * or with status {@code 500 (Internal Server Error)} if the agendaTarefaOrdemServicoExecucao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AgendaTarefaOrdemServicoExecucao> partialUpdateAgendaTarefaOrdemServicoExecucao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AgendaTarefaOrdemServicoExecucao agendaTarefaOrdemServicoExecucao
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update AgendaTarefaOrdemServicoExecucao partially : {}, {}",
            id,
            agendaTarefaOrdemServicoExecucao
        );
        if (agendaTarefaOrdemServicoExecucao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, agendaTarefaOrdemServicoExecucao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!agendaTarefaOrdemServicoExecucaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AgendaTarefaOrdemServicoExecucao> result = agendaTarefaOrdemServicoExecucaoService.partialUpdate(
            agendaTarefaOrdemServicoExecucao
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agendaTarefaOrdemServicoExecucao.getId().toString())
        );
    }

    /**
     * {@code GET  /agenda-tarefa-ordem-servico-execucaos} : get all the agendaTarefaOrdemServicoExecucaos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agendaTarefaOrdemServicoExecucaos in body.
     */
    @GetMapping("")
    public List<AgendaTarefaOrdemServicoExecucao> getAllAgendaTarefaOrdemServicoExecucaos(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all AgendaTarefaOrdemServicoExecucaos");
        return agendaTarefaOrdemServicoExecucaoService.findAll();
    }

    /**
     * {@code GET  /agenda-tarefa-ordem-servico-execucaos/:id} : get the "id" agendaTarefaOrdemServicoExecucao.
     *
     * @param id the id of the agendaTarefaOrdemServicoExecucao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agendaTarefaOrdemServicoExecucao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AgendaTarefaOrdemServicoExecucao> getAgendaTarefaOrdemServicoExecucao(@PathVariable("id") Long id) {
        log.debug("REST request to get AgendaTarefaOrdemServicoExecucao : {}", id);
        Optional<AgendaTarefaOrdemServicoExecucao> agendaTarefaOrdemServicoExecucao = agendaTarefaOrdemServicoExecucaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(agendaTarefaOrdemServicoExecucao);
    }

    /**
     * {@code DELETE  /agenda-tarefa-ordem-servico-execucaos/:id} : delete the "id" agendaTarefaOrdemServicoExecucao.
     *
     * @param id the id of the agendaTarefaOrdemServicoExecucao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgendaTarefaOrdemServicoExecucao(@PathVariable("id") Long id) {
        log.debug("REST request to delete AgendaTarefaOrdemServicoExecucao : {}", id);
        agendaTarefaOrdemServicoExecucaoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
