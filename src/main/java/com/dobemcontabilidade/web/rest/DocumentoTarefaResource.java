package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.DocumentoTarefaRepository;
import com.dobemcontabilidade.service.DocumentoTarefaQueryService;
import com.dobemcontabilidade.service.DocumentoTarefaService;
import com.dobemcontabilidade.service.criteria.DocumentoTarefaCriteria;
import com.dobemcontabilidade.service.dto.DocumentoTarefaDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.DocumentoTarefa}.
 */
@RestController
@RequestMapping("/api/documento-tarefas")
public class DocumentoTarefaResource {

    private static final Logger log = LoggerFactory.getLogger(DocumentoTarefaResource.class);

    private static final String ENTITY_NAME = "documentoTarefa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentoTarefaService documentoTarefaService;

    private final DocumentoTarefaRepository documentoTarefaRepository;

    private final DocumentoTarefaQueryService documentoTarefaQueryService;

    public DocumentoTarefaResource(
        DocumentoTarefaService documentoTarefaService,
        DocumentoTarefaRepository documentoTarefaRepository,
        DocumentoTarefaQueryService documentoTarefaQueryService
    ) {
        this.documentoTarefaService = documentoTarefaService;
        this.documentoTarefaRepository = documentoTarefaRepository;
        this.documentoTarefaQueryService = documentoTarefaQueryService;
    }

    /**
     * {@code POST  /documento-tarefas} : Create a new documentoTarefa.
     *
     * @param documentoTarefaDTO the documentoTarefaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentoTarefaDTO, or with status {@code 400 (Bad Request)} if the documentoTarefa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DocumentoTarefaDTO> createDocumentoTarefa(@Valid @RequestBody DocumentoTarefaDTO documentoTarefaDTO)
        throws URISyntaxException {
        log.debug("REST request to save DocumentoTarefa : {}", documentoTarefaDTO);
        if (documentoTarefaDTO.getId() != null) {
            throw new BadRequestAlertException("A new documentoTarefa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        documentoTarefaDTO = documentoTarefaService.save(documentoTarefaDTO);
        return ResponseEntity.created(new URI("/api/documento-tarefas/" + documentoTarefaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, documentoTarefaDTO.getId().toString()))
            .body(documentoTarefaDTO);
    }

    /**
     * {@code PUT  /documento-tarefas/:id} : Updates an existing documentoTarefa.
     *
     * @param id the id of the documentoTarefaDTO to save.
     * @param documentoTarefaDTO the documentoTarefaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentoTarefaDTO,
     * or with status {@code 400 (Bad Request)} if the documentoTarefaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentoTarefaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DocumentoTarefaDTO> updateDocumentoTarefa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DocumentoTarefaDTO documentoTarefaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DocumentoTarefa : {}, {}", id, documentoTarefaDTO);
        if (documentoTarefaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentoTarefaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentoTarefaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        documentoTarefaDTO = documentoTarefaService.update(documentoTarefaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentoTarefaDTO.getId().toString()))
            .body(documentoTarefaDTO);
    }

    /**
     * {@code PATCH  /documento-tarefas/:id} : Partial updates given fields of an existing documentoTarefa, field will ignore if it is null
     *
     * @param id the id of the documentoTarefaDTO to save.
     * @param documentoTarefaDTO the documentoTarefaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentoTarefaDTO,
     * or with status {@code 400 (Bad Request)} if the documentoTarefaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the documentoTarefaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the documentoTarefaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DocumentoTarefaDTO> partialUpdateDocumentoTarefa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DocumentoTarefaDTO documentoTarefaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DocumentoTarefa partially : {}, {}", id, documentoTarefaDTO);
        if (documentoTarefaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentoTarefaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentoTarefaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DocumentoTarefaDTO> result = documentoTarefaService.partialUpdate(documentoTarefaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentoTarefaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /documento-tarefas} : get all the documentoTarefas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentoTarefas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DocumentoTarefaDTO>> getAllDocumentoTarefas(
        DocumentoTarefaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DocumentoTarefas by criteria: {}", criteria);

        Page<DocumentoTarefaDTO> page = documentoTarefaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /documento-tarefas/count} : count all the documentoTarefas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countDocumentoTarefas(DocumentoTarefaCriteria criteria) {
        log.debug("REST request to count DocumentoTarefas by criteria: {}", criteria);
        return ResponseEntity.ok().body(documentoTarefaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /documento-tarefas/:id} : get the "id" documentoTarefa.
     *
     * @param id the id of the documentoTarefaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentoTarefaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DocumentoTarefaDTO> getDocumentoTarefa(@PathVariable("id") Long id) {
        log.debug("REST request to get DocumentoTarefa : {}", id);
        Optional<DocumentoTarefaDTO> documentoTarefaDTO = documentoTarefaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentoTarefaDTO);
    }

    /**
     * {@code DELETE  /documento-tarefas/:id} : delete the "id" documentoTarefa.
     *
     * @param id the id of the documentoTarefaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumentoTarefa(@PathVariable("id") Long id) {
        log.debug("REST request to delete DocumentoTarefa : {}", id);
        documentoTarefaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
