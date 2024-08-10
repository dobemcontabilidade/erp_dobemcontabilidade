package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.SubtarefaRepository;
import com.dobemcontabilidade.service.SubtarefaQueryService;
import com.dobemcontabilidade.service.SubtarefaService;
import com.dobemcontabilidade.service.criteria.SubtarefaCriteria;
import com.dobemcontabilidade.service.dto.SubtarefaDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Subtarefa}.
 */
@RestController
@RequestMapping("/api/subtarefas")
public class SubtarefaResource {

    private static final Logger log = LoggerFactory.getLogger(SubtarefaResource.class);

    private static final String ENTITY_NAME = "subtarefa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubtarefaService subtarefaService;

    private final SubtarefaRepository subtarefaRepository;

    private final SubtarefaQueryService subtarefaQueryService;

    public SubtarefaResource(
        SubtarefaService subtarefaService,
        SubtarefaRepository subtarefaRepository,
        SubtarefaQueryService subtarefaQueryService
    ) {
        this.subtarefaService = subtarefaService;
        this.subtarefaRepository = subtarefaRepository;
        this.subtarefaQueryService = subtarefaQueryService;
    }

    /**
     * {@code POST  /subtarefas} : Create a new subtarefa.
     *
     * @param subtarefaDTO the subtarefaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subtarefaDTO, or with status {@code 400 (Bad Request)} if the subtarefa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SubtarefaDTO> createSubtarefa(@Valid @RequestBody SubtarefaDTO subtarefaDTO) throws URISyntaxException {
        log.debug("REST request to save Subtarefa : {}", subtarefaDTO);
        if (subtarefaDTO.getId() != null) {
            throw new BadRequestAlertException("A new subtarefa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        subtarefaDTO = subtarefaService.save(subtarefaDTO);
        return ResponseEntity.created(new URI("/api/subtarefas/" + subtarefaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, subtarefaDTO.getId().toString()))
            .body(subtarefaDTO);
    }

    /**
     * {@code PUT  /subtarefas/:id} : Updates an existing subtarefa.
     *
     * @param id the id of the subtarefaDTO to save.
     * @param subtarefaDTO the subtarefaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subtarefaDTO,
     * or with status {@code 400 (Bad Request)} if the subtarefaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subtarefaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SubtarefaDTO> updateSubtarefa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SubtarefaDTO subtarefaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Subtarefa : {}, {}", id, subtarefaDTO);
        if (subtarefaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subtarefaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subtarefaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        subtarefaDTO = subtarefaService.update(subtarefaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subtarefaDTO.getId().toString()))
            .body(subtarefaDTO);
    }

    /**
     * {@code PATCH  /subtarefas/:id} : Partial updates given fields of an existing subtarefa, field will ignore if it is null
     *
     * @param id the id of the subtarefaDTO to save.
     * @param subtarefaDTO the subtarefaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subtarefaDTO,
     * or with status {@code 400 (Bad Request)} if the subtarefaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the subtarefaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the subtarefaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SubtarefaDTO> partialUpdateSubtarefa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SubtarefaDTO subtarefaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Subtarefa partially : {}, {}", id, subtarefaDTO);
        if (subtarefaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subtarefaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subtarefaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubtarefaDTO> result = subtarefaService.partialUpdate(subtarefaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subtarefaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /subtarefas} : get all the subtarefas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subtarefas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SubtarefaDTO>> getAllSubtarefas(
        SubtarefaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Subtarefas by criteria: {}", criteria);

        Page<SubtarefaDTO> page = subtarefaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /subtarefas/count} : count all the subtarefas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSubtarefas(SubtarefaCriteria criteria) {
        log.debug("REST request to count Subtarefas by criteria: {}", criteria);
        return ResponseEntity.ok().body(subtarefaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /subtarefas/:id} : get the "id" subtarefa.
     *
     * @param id the id of the subtarefaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subtarefaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SubtarefaDTO> getSubtarefa(@PathVariable("id") Long id) {
        log.debug("REST request to get Subtarefa : {}", id);
        Optional<SubtarefaDTO> subtarefaDTO = subtarefaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subtarefaDTO);
    }

    /**
     * {@code DELETE  /subtarefas/:id} : delete the "id" subtarefa.
     *
     * @param id the id of the subtarefaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubtarefa(@PathVariable("id") Long id) {
        log.debug("REST request to delete Subtarefa : {}", id);
        subtarefaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
