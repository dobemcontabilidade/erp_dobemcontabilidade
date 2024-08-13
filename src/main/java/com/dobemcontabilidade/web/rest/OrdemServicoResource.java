package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.OrdemServico;
import com.dobemcontabilidade.repository.OrdemServicoRepository;
import com.dobemcontabilidade.service.OrdemServicoService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.OrdemServico}.
 */
@RestController
@RequestMapping("/api/ordem-servicos")
public class OrdemServicoResource {

    private static final Logger log = LoggerFactory.getLogger(OrdemServicoResource.class);

    private static final String ENTITY_NAME = "ordemServico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdemServicoService ordemServicoService;

    private final OrdemServicoRepository ordemServicoRepository;

    public OrdemServicoResource(OrdemServicoService ordemServicoService, OrdemServicoRepository ordemServicoRepository) {
        this.ordemServicoService = ordemServicoService;
        this.ordemServicoRepository = ordemServicoRepository;
    }

    /**
     * {@code POST  /ordem-servicos} : Create a new ordemServico.
     *
     * @param ordemServico the ordemServico to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordemServico, or with status {@code 400 (Bad Request)} if the ordemServico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OrdemServico> createOrdemServico(@Valid @RequestBody OrdemServico ordemServico) throws URISyntaxException {
        log.debug("REST request to save OrdemServico : {}", ordemServico);
        if (ordemServico.getId() != null) {
            throw new BadRequestAlertException("A new ordemServico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ordemServico = ordemServicoService.save(ordemServico);
        return ResponseEntity.created(new URI("/api/ordem-servicos/" + ordemServico.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ordemServico.getId().toString()))
            .body(ordemServico);
    }

    /**
     * {@code PUT  /ordem-servicos/:id} : Updates an existing ordemServico.
     *
     * @param id the id of the ordemServico to save.
     * @param ordemServico the ordemServico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordemServico,
     * or with status {@code 400 (Bad Request)} if the ordemServico is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordemServico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrdemServico> updateOrdemServico(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OrdemServico ordemServico
    ) throws URISyntaxException {
        log.debug("REST request to update OrdemServico : {}, {}", id, ordemServico);
        if (ordemServico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordemServico.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordemServicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ordemServico = ordemServicoService.update(ordemServico);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ordemServico.getId().toString()))
            .body(ordemServico);
    }

    /**
     * {@code PATCH  /ordem-servicos/:id} : Partial updates given fields of an existing ordemServico, field will ignore if it is null
     *
     * @param id the id of the ordemServico to save.
     * @param ordemServico the ordemServico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordemServico,
     * or with status {@code 400 (Bad Request)} if the ordemServico is not valid,
     * or with status {@code 404 (Not Found)} if the ordemServico is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordemServico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrdemServico> partialUpdateOrdemServico(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OrdemServico ordemServico
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrdemServico partially : {}, {}", id, ordemServico);
        if (ordemServico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordemServico.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordemServicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdemServico> result = ordemServicoService.partialUpdate(ordemServico);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ordemServico.getId().toString())
        );
    }

    /**
     * {@code GET  /ordem-servicos} : get all the ordemServicos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordemServicos in body.
     */
    @GetMapping("")
    public List<OrdemServico> getAllOrdemServicos(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all OrdemServicos");
        return ordemServicoService.findAll();
    }

    /**
     * {@code GET  /ordem-servicos/:id} : get the "id" ordemServico.
     *
     * @param id the id of the ordemServico to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordemServico, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrdemServico> getOrdemServico(@PathVariable("id") Long id) {
        log.debug("REST request to get OrdemServico : {}", id);
        Optional<OrdemServico> ordemServico = ordemServicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordemServico);
    }

    /**
     * {@code DELETE  /ordem-servicos/:id} : delete the "id" ordemServico.
     *
     * @param id the id of the ordemServico to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrdemServico(@PathVariable("id") Long id) {
        log.debug("REST request to delete OrdemServico : {}", id);
        ordemServicoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
