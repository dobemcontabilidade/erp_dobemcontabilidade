package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.TarefaRepository;
import com.dobemcontabilidade.service.TarefaQueryService;
import com.dobemcontabilidade.service.TarefaService;
import com.dobemcontabilidade.service.criteria.TarefaCriteria;
import com.dobemcontabilidade.service.dto.TarefaDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dobemcontabilidade.domain.Tarefa}.
 */
@RestController
@RequestMapping("/api/tarefas")
public class TarefaResource {

    private static final Logger log = LoggerFactory.getLogger(TarefaResource.class);

    private static final String ENTITY_NAME = "tarefa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TarefaService tarefaService;

    private final TarefaRepository tarefaRepository;

    private final TarefaQueryService tarefaQueryService;

    public TarefaResource(TarefaService tarefaService, TarefaRepository tarefaRepository, TarefaQueryService tarefaQueryService) {
        this.tarefaService = tarefaService;
        this.tarefaRepository = tarefaRepository;
        this.tarefaQueryService = tarefaQueryService;
    }

    /**
     * {@code POST  /tarefas} : Create a new tarefa.
     *
     * @param tarefaDTO the tarefaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tarefaDTO, or with status {@code 400 (Bad Request)} if the tarefa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TarefaDTO> createTarefa(@Valid @RequestBody TarefaDTO tarefaDTO) throws URISyntaxException {
        log.debug("REST request to save Tarefa : {}", tarefaDTO);
        if (tarefaDTO.getId() != null) {
            throw new BadRequestAlertException("A new tarefa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tarefaDTO = tarefaService.save(tarefaDTO);
        return ResponseEntity.created(new URI("/api/tarefas/" + tarefaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tarefaDTO.getId().toString()))
            .body(tarefaDTO);
    }

    /**
     * {@code PUT  /tarefas/:id} : Updates an existing tarefa.
     *
     * @param id the id of the tarefaDTO to save.
     * @param tarefaDTO the tarefaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarefaDTO,
     * or with status {@code 400 (Bad Request)} if the tarefaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tarefaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TarefaDTO> updateTarefa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TarefaDTO tarefaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Tarefa : {}, {}", id, tarefaDTO);
        if (tarefaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tarefaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tarefaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tarefaDTO = tarefaService.update(tarefaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarefaDTO.getId().toString()))
            .body(tarefaDTO);
    }

    /**
     * {@code PATCH  /tarefas/:id} : Partial updates given fields of an existing tarefa, field will ignore if it is null
     *
     * @param id the id of the tarefaDTO to save.
     * @param tarefaDTO the tarefaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarefaDTO,
     * or with status {@code 400 (Bad Request)} if the tarefaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tarefaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tarefaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TarefaDTO> partialUpdateTarefa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TarefaDTO tarefaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tarefa partially : {}, {}", id, tarefaDTO);
        if (tarefaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tarefaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tarefaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TarefaDTO> result = tarefaService.partialUpdate(tarefaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarefaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tarefas} : get all the tarefas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tarefas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TarefaDTO>> getAllTarefas(
        TarefaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Tarefas by criteria: {}", criteria);

        Page<TarefaDTO> page = tarefaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tarefas/count} : count all the tarefas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countTarefas(TarefaCriteria criteria) {
        log.debug("REST request to count Tarefas by criteria: {}", criteria);
        return ResponseEntity.ok().body(tarefaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tarefas/:id} : get the "id" tarefa.
     *
     * @param id the id of the tarefaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tarefaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TarefaDTO> getTarefa(@PathVariable("id") Long id) {
        log.debug("REST request to get Tarefa : {}", id);
        Optional<TarefaDTO> tarefaDTO = tarefaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tarefaDTO);
    }

    /**
     * {@code DELETE  /tarefas/:id} : delete the "id" tarefa.
     *
     * @param id the id of the tarefaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarefa(@PathVariable("id") Long id) {
        log.debug("REST request to delete Tarefa : {}", id);
        tarefaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
