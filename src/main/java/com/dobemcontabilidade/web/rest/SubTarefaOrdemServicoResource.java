package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.SubTarefaOrdemServico;
import com.dobemcontabilidade.repository.SubTarefaOrdemServicoRepository;
import com.dobemcontabilidade.service.SubTarefaOrdemServicoService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.SubTarefaOrdemServico}.
 */
@RestController
@RequestMapping("/api/sub-tarefa-ordem-servicos")
public class SubTarefaOrdemServicoResource {

    private static final Logger log = LoggerFactory.getLogger(SubTarefaOrdemServicoResource.class);

    private static final String ENTITY_NAME = "subTarefaOrdemServico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubTarefaOrdemServicoService subTarefaOrdemServicoService;

    private final SubTarefaOrdemServicoRepository subTarefaOrdemServicoRepository;

    public SubTarefaOrdemServicoResource(
        SubTarefaOrdemServicoService subTarefaOrdemServicoService,
        SubTarefaOrdemServicoRepository subTarefaOrdemServicoRepository
    ) {
        this.subTarefaOrdemServicoService = subTarefaOrdemServicoService;
        this.subTarefaOrdemServicoRepository = subTarefaOrdemServicoRepository;
    }

    /**
     * {@code POST  /sub-tarefa-ordem-servicos} : Create a new subTarefaOrdemServico.
     *
     * @param subTarefaOrdemServico the subTarefaOrdemServico to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subTarefaOrdemServico, or with status {@code 400 (Bad Request)} if the subTarefaOrdemServico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SubTarefaOrdemServico> createSubTarefaOrdemServico(
        @Valid @RequestBody SubTarefaOrdemServico subTarefaOrdemServico
    ) throws URISyntaxException {
        log.debug("REST request to save SubTarefaOrdemServico : {}", subTarefaOrdemServico);
        if (subTarefaOrdemServico.getId() != null) {
            throw new BadRequestAlertException("A new subTarefaOrdemServico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        subTarefaOrdemServico = subTarefaOrdemServicoService.save(subTarefaOrdemServico);
        return ResponseEntity.created(new URI("/api/sub-tarefa-ordem-servicos/" + subTarefaOrdemServico.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, subTarefaOrdemServico.getId().toString()))
            .body(subTarefaOrdemServico);
    }

    /**
     * {@code PUT  /sub-tarefa-ordem-servicos/:id} : Updates an existing subTarefaOrdemServico.
     *
     * @param id the id of the subTarefaOrdemServico to save.
     * @param subTarefaOrdemServico the subTarefaOrdemServico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subTarefaOrdemServico,
     * or with status {@code 400 (Bad Request)} if the subTarefaOrdemServico is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subTarefaOrdemServico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SubTarefaOrdemServico> updateSubTarefaOrdemServico(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SubTarefaOrdemServico subTarefaOrdemServico
    ) throws URISyntaxException {
        log.debug("REST request to update SubTarefaOrdemServico : {}, {}", id, subTarefaOrdemServico);
        if (subTarefaOrdemServico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subTarefaOrdemServico.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subTarefaOrdemServicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        subTarefaOrdemServico = subTarefaOrdemServicoService.update(subTarefaOrdemServico);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subTarefaOrdemServico.getId().toString()))
            .body(subTarefaOrdemServico);
    }

    /**
     * {@code PATCH  /sub-tarefa-ordem-servicos/:id} : Partial updates given fields of an existing subTarefaOrdemServico, field will ignore if it is null
     *
     * @param id the id of the subTarefaOrdemServico to save.
     * @param subTarefaOrdemServico the subTarefaOrdemServico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subTarefaOrdemServico,
     * or with status {@code 400 (Bad Request)} if the subTarefaOrdemServico is not valid,
     * or with status {@code 404 (Not Found)} if the subTarefaOrdemServico is not found,
     * or with status {@code 500 (Internal Server Error)} if the subTarefaOrdemServico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SubTarefaOrdemServico> partialUpdateSubTarefaOrdemServico(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SubTarefaOrdemServico subTarefaOrdemServico
    ) throws URISyntaxException {
        log.debug("REST request to partial update SubTarefaOrdemServico partially : {}, {}", id, subTarefaOrdemServico);
        if (subTarefaOrdemServico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subTarefaOrdemServico.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subTarefaOrdemServicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubTarefaOrdemServico> result = subTarefaOrdemServicoService.partialUpdate(subTarefaOrdemServico);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subTarefaOrdemServico.getId().toString())
        );
    }

    /**
     * {@code GET  /sub-tarefa-ordem-servicos} : get all the subTarefaOrdemServicos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subTarefaOrdemServicos in body.
     */
    @GetMapping("")
    public List<SubTarefaOrdemServico> getAllSubTarefaOrdemServicos(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all SubTarefaOrdemServicos");
        return subTarefaOrdemServicoService.findAll();
    }

    /**
     * {@code GET  /sub-tarefa-ordem-servicos/:id} : get the "id" subTarefaOrdemServico.
     *
     * @param id the id of the subTarefaOrdemServico to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subTarefaOrdemServico, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SubTarefaOrdemServico> getSubTarefaOrdemServico(@PathVariable("id") Long id) {
        log.debug("REST request to get SubTarefaOrdemServico : {}", id);
        Optional<SubTarefaOrdemServico> subTarefaOrdemServico = subTarefaOrdemServicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subTarefaOrdemServico);
    }

    /**
     * {@code DELETE  /sub-tarefa-ordem-servicos/:id} : delete the "id" subTarefaOrdemServico.
     *
     * @param id the id of the subTarefaOrdemServico to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubTarefaOrdemServico(@PathVariable("id") Long id) {
        log.debug("REST request to delete SubTarefaOrdemServico : {}", id);
        subTarefaOrdemServicoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
