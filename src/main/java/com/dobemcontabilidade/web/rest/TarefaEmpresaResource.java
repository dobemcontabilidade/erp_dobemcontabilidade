package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.TarefaEmpresaRepository;
import com.dobemcontabilidade.service.TarefaEmpresaQueryService;
import com.dobemcontabilidade.service.TarefaEmpresaService;
import com.dobemcontabilidade.service.criteria.TarefaEmpresaCriteria;
import com.dobemcontabilidade.service.dto.TarefaEmpresaDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.TarefaEmpresa}.
 */
@RestController
@RequestMapping("/api/tarefa-empresas")
public class TarefaEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(TarefaEmpresaResource.class);

    private static final String ENTITY_NAME = "tarefaEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TarefaEmpresaService tarefaEmpresaService;

    private final TarefaEmpresaRepository tarefaEmpresaRepository;

    private final TarefaEmpresaQueryService tarefaEmpresaQueryService;

    public TarefaEmpresaResource(
        TarefaEmpresaService tarefaEmpresaService,
        TarefaEmpresaRepository tarefaEmpresaRepository,
        TarefaEmpresaQueryService tarefaEmpresaQueryService
    ) {
        this.tarefaEmpresaService = tarefaEmpresaService;
        this.tarefaEmpresaRepository = tarefaEmpresaRepository;
        this.tarefaEmpresaQueryService = tarefaEmpresaQueryService;
    }

    /**
     * {@code POST  /tarefa-empresas} : Create a new tarefaEmpresa.
     *
     * @param tarefaEmpresaDTO the tarefaEmpresaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tarefaEmpresaDTO, or with status {@code 400 (Bad Request)} if the tarefaEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TarefaEmpresaDTO> createTarefaEmpresa(@Valid @RequestBody TarefaEmpresaDTO tarefaEmpresaDTO)
        throws URISyntaxException {
        log.debug("REST request to save TarefaEmpresa : {}", tarefaEmpresaDTO);
        if (tarefaEmpresaDTO.getId() != null) {
            throw new BadRequestAlertException("A new tarefaEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tarefaEmpresaDTO = tarefaEmpresaService.save(tarefaEmpresaDTO);
        return ResponseEntity.created(new URI("/api/tarefa-empresas/" + tarefaEmpresaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tarefaEmpresaDTO.getId().toString()))
            .body(tarefaEmpresaDTO);
    }

    /**
     * {@code PUT  /tarefa-empresas/:id} : Updates an existing tarefaEmpresa.
     *
     * @param id the id of the tarefaEmpresaDTO to save.
     * @param tarefaEmpresaDTO the tarefaEmpresaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarefaEmpresaDTO,
     * or with status {@code 400 (Bad Request)} if the tarefaEmpresaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tarefaEmpresaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TarefaEmpresaDTO> updateTarefaEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TarefaEmpresaDTO tarefaEmpresaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TarefaEmpresa : {}, {}", id, tarefaEmpresaDTO);
        if (tarefaEmpresaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tarefaEmpresaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tarefaEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tarefaEmpresaDTO = tarefaEmpresaService.update(tarefaEmpresaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarefaEmpresaDTO.getId().toString()))
            .body(tarefaEmpresaDTO);
    }

    /**
     * {@code PATCH  /tarefa-empresas/:id} : Partial updates given fields of an existing tarefaEmpresa, field will ignore if it is null
     *
     * @param id the id of the tarefaEmpresaDTO to save.
     * @param tarefaEmpresaDTO the tarefaEmpresaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarefaEmpresaDTO,
     * or with status {@code 400 (Bad Request)} if the tarefaEmpresaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tarefaEmpresaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tarefaEmpresaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TarefaEmpresaDTO> partialUpdateTarefaEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TarefaEmpresaDTO tarefaEmpresaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TarefaEmpresa partially : {}, {}", id, tarefaEmpresaDTO);
        if (tarefaEmpresaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tarefaEmpresaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tarefaEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TarefaEmpresaDTO> result = tarefaEmpresaService.partialUpdate(tarefaEmpresaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarefaEmpresaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tarefa-empresas} : get all the tarefaEmpresas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tarefaEmpresas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TarefaEmpresaDTO>> getAllTarefaEmpresas(
        TarefaEmpresaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TarefaEmpresas by criteria: {}", criteria);

        Page<TarefaEmpresaDTO> page = tarefaEmpresaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tarefa-empresas/count} : count all the tarefaEmpresas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countTarefaEmpresas(TarefaEmpresaCriteria criteria) {
        log.debug("REST request to count TarefaEmpresas by criteria: {}", criteria);
        return ResponseEntity.ok().body(tarefaEmpresaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tarefa-empresas/:id} : get the "id" tarefaEmpresa.
     *
     * @param id the id of the tarefaEmpresaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tarefaEmpresaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TarefaEmpresaDTO> getTarefaEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get TarefaEmpresa : {}", id);
        Optional<TarefaEmpresaDTO> tarefaEmpresaDTO = tarefaEmpresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tarefaEmpresaDTO);
    }

    /**
     * {@code DELETE  /tarefa-empresas/:id} : delete the "id" tarefaEmpresa.
     *
     * @param id the id of the tarefaEmpresaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarefaEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete TarefaEmpresa : {}", id);
        tarefaEmpresaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
