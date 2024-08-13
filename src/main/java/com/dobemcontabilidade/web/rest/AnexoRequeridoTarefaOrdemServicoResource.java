package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.AnexoRequeridoTarefaOrdemServico;
import com.dobemcontabilidade.repository.AnexoRequeridoTarefaOrdemServicoRepository;
import com.dobemcontabilidade.service.AnexoRequeridoTarefaOrdemServicoService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AnexoRequeridoTarefaOrdemServico}.
 */
@RestController
@RequestMapping("/api/anexo-requerido-tarefa-ordem-servicos")
public class AnexoRequeridoTarefaOrdemServicoResource {

    private static final Logger log = LoggerFactory.getLogger(AnexoRequeridoTarefaOrdemServicoResource.class);

    private static final String ENTITY_NAME = "anexoRequeridoTarefaOrdemServico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnexoRequeridoTarefaOrdemServicoService anexoRequeridoTarefaOrdemServicoService;

    private final AnexoRequeridoTarefaOrdemServicoRepository anexoRequeridoTarefaOrdemServicoRepository;

    public AnexoRequeridoTarefaOrdemServicoResource(
        AnexoRequeridoTarefaOrdemServicoService anexoRequeridoTarefaOrdemServicoService,
        AnexoRequeridoTarefaOrdemServicoRepository anexoRequeridoTarefaOrdemServicoRepository
    ) {
        this.anexoRequeridoTarefaOrdemServicoService = anexoRequeridoTarefaOrdemServicoService;
        this.anexoRequeridoTarefaOrdemServicoRepository = anexoRequeridoTarefaOrdemServicoRepository;
    }

    /**
     * {@code POST  /anexo-requerido-tarefa-ordem-servicos} : Create a new anexoRequeridoTarefaOrdemServico.
     *
     * @param anexoRequeridoTarefaOrdemServico the anexoRequeridoTarefaOrdemServico to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anexoRequeridoTarefaOrdemServico, or with status {@code 400 (Bad Request)} if the anexoRequeridoTarefaOrdemServico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AnexoRequeridoTarefaOrdemServico> createAnexoRequeridoTarefaOrdemServico(
        @Valid @RequestBody AnexoRequeridoTarefaOrdemServico anexoRequeridoTarefaOrdemServico
    ) throws URISyntaxException {
        log.debug("REST request to save AnexoRequeridoTarefaOrdemServico : {}", anexoRequeridoTarefaOrdemServico);
        if (anexoRequeridoTarefaOrdemServico.getId() != null) {
            throw new BadRequestAlertException("A new anexoRequeridoTarefaOrdemServico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        anexoRequeridoTarefaOrdemServico = anexoRequeridoTarefaOrdemServicoService.save(anexoRequeridoTarefaOrdemServico);
        return ResponseEntity.created(new URI("/api/anexo-requerido-tarefa-ordem-servicos/" + anexoRequeridoTarefaOrdemServico.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    anexoRequeridoTarefaOrdemServico.getId().toString()
                )
            )
            .body(anexoRequeridoTarefaOrdemServico);
    }

    /**
     * {@code PUT  /anexo-requerido-tarefa-ordem-servicos/:id} : Updates an existing anexoRequeridoTarefaOrdemServico.
     *
     * @param id the id of the anexoRequeridoTarefaOrdemServico to save.
     * @param anexoRequeridoTarefaOrdemServico the anexoRequeridoTarefaOrdemServico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anexoRequeridoTarefaOrdemServico,
     * or with status {@code 400 (Bad Request)} if the anexoRequeridoTarefaOrdemServico is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anexoRequeridoTarefaOrdemServico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AnexoRequeridoTarefaOrdemServico> updateAnexoRequeridoTarefaOrdemServico(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AnexoRequeridoTarefaOrdemServico anexoRequeridoTarefaOrdemServico
    ) throws URISyntaxException {
        log.debug("REST request to update AnexoRequeridoTarefaOrdemServico : {}, {}", id, anexoRequeridoTarefaOrdemServico);
        if (anexoRequeridoTarefaOrdemServico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anexoRequeridoTarefaOrdemServico.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anexoRequeridoTarefaOrdemServicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        anexoRequeridoTarefaOrdemServico = anexoRequeridoTarefaOrdemServicoService.update(anexoRequeridoTarefaOrdemServico);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anexoRequeridoTarefaOrdemServico.getId().toString())
            )
            .body(anexoRequeridoTarefaOrdemServico);
    }

    /**
     * {@code PATCH  /anexo-requerido-tarefa-ordem-servicos/:id} : Partial updates given fields of an existing anexoRequeridoTarefaOrdemServico, field will ignore if it is null
     *
     * @param id the id of the anexoRequeridoTarefaOrdemServico to save.
     * @param anexoRequeridoTarefaOrdemServico the anexoRequeridoTarefaOrdemServico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anexoRequeridoTarefaOrdemServico,
     * or with status {@code 400 (Bad Request)} if the anexoRequeridoTarefaOrdemServico is not valid,
     * or with status {@code 404 (Not Found)} if the anexoRequeridoTarefaOrdemServico is not found,
     * or with status {@code 500 (Internal Server Error)} if the anexoRequeridoTarefaOrdemServico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AnexoRequeridoTarefaOrdemServico> partialUpdateAnexoRequeridoTarefaOrdemServico(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AnexoRequeridoTarefaOrdemServico anexoRequeridoTarefaOrdemServico
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update AnexoRequeridoTarefaOrdemServico partially : {}, {}",
            id,
            anexoRequeridoTarefaOrdemServico
        );
        if (anexoRequeridoTarefaOrdemServico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anexoRequeridoTarefaOrdemServico.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anexoRequeridoTarefaOrdemServicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnexoRequeridoTarefaOrdemServico> result = anexoRequeridoTarefaOrdemServicoService.partialUpdate(
            anexoRequeridoTarefaOrdemServico
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anexoRequeridoTarefaOrdemServico.getId().toString())
        );
    }

    /**
     * {@code GET  /anexo-requerido-tarefa-ordem-servicos} : get all the anexoRequeridoTarefaOrdemServicos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anexoRequeridoTarefaOrdemServicos in body.
     */
    @GetMapping("")
    public List<AnexoRequeridoTarefaOrdemServico> getAllAnexoRequeridoTarefaOrdemServicos(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all AnexoRequeridoTarefaOrdemServicos");
        return anexoRequeridoTarefaOrdemServicoService.findAll();
    }

    /**
     * {@code GET  /anexo-requerido-tarefa-ordem-servicos/:id} : get the "id" anexoRequeridoTarefaOrdemServico.
     *
     * @param id the id of the anexoRequeridoTarefaOrdemServico to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anexoRequeridoTarefaOrdemServico, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AnexoRequeridoTarefaOrdemServico> getAnexoRequeridoTarefaOrdemServico(@PathVariable("id") Long id) {
        log.debug("REST request to get AnexoRequeridoTarefaOrdemServico : {}", id);
        Optional<AnexoRequeridoTarefaOrdemServico> anexoRequeridoTarefaOrdemServico = anexoRequeridoTarefaOrdemServicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anexoRequeridoTarefaOrdemServico);
    }

    /**
     * {@code DELETE  /anexo-requerido-tarefa-ordem-servicos/:id} : delete the "id" anexoRequeridoTarefaOrdemServico.
     *
     * @param id the id of the anexoRequeridoTarefaOrdemServico to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnexoRequeridoTarefaOrdemServico(@PathVariable("id") Long id) {
        log.debug("REST request to delete AnexoRequeridoTarefaOrdemServico : {}", id);
        anexoRequeridoTarefaOrdemServicoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
