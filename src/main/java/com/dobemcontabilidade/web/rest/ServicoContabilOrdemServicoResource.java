package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.ServicoContabilOrdemServico;
import com.dobemcontabilidade.repository.ServicoContabilOrdemServicoRepository;
import com.dobemcontabilidade.service.ServicoContabilOrdemServicoService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.ServicoContabilOrdemServico}.
 */
@RestController
@RequestMapping("/api/servico-contabil-ordem-servicos")
public class ServicoContabilOrdemServicoResource {

    private static final Logger log = LoggerFactory.getLogger(ServicoContabilOrdemServicoResource.class);

    private static final String ENTITY_NAME = "servicoContabilOrdemServico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServicoContabilOrdemServicoService servicoContabilOrdemServicoService;

    private final ServicoContabilOrdemServicoRepository servicoContabilOrdemServicoRepository;

    public ServicoContabilOrdemServicoResource(
        ServicoContabilOrdemServicoService servicoContabilOrdemServicoService,
        ServicoContabilOrdemServicoRepository servicoContabilOrdemServicoRepository
    ) {
        this.servicoContabilOrdemServicoService = servicoContabilOrdemServicoService;
        this.servicoContabilOrdemServicoRepository = servicoContabilOrdemServicoRepository;
    }

    /**
     * {@code POST  /servico-contabil-ordem-servicos} : Create a new servicoContabilOrdemServico.
     *
     * @param servicoContabilOrdemServico the servicoContabilOrdemServico to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servicoContabilOrdemServico, or with status {@code 400 (Bad Request)} if the servicoContabilOrdemServico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ServicoContabilOrdemServico> createServicoContabilOrdemServico(
        @Valid @RequestBody ServicoContabilOrdemServico servicoContabilOrdemServico
    ) throws URISyntaxException {
        log.debug("REST request to save ServicoContabilOrdemServico : {}", servicoContabilOrdemServico);
        if (servicoContabilOrdemServico.getId() != null) {
            throw new BadRequestAlertException("A new servicoContabilOrdemServico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        servicoContabilOrdemServico = servicoContabilOrdemServicoService.save(servicoContabilOrdemServico);
        return ResponseEntity.created(new URI("/api/servico-contabil-ordem-servicos/" + servicoContabilOrdemServico.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, servicoContabilOrdemServico.getId().toString())
            )
            .body(servicoContabilOrdemServico);
    }

    /**
     * {@code PUT  /servico-contabil-ordem-servicos/:id} : Updates an existing servicoContabilOrdemServico.
     *
     * @param id the id of the servicoContabilOrdemServico to save.
     * @param servicoContabilOrdemServico the servicoContabilOrdemServico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servicoContabilOrdemServico,
     * or with status {@code 400 (Bad Request)} if the servicoContabilOrdemServico is not valid,
     * or with status {@code 500 (Internal Server Error)} if the servicoContabilOrdemServico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServicoContabilOrdemServico> updateServicoContabilOrdemServico(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServicoContabilOrdemServico servicoContabilOrdemServico
    ) throws URISyntaxException {
        log.debug("REST request to update ServicoContabilOrdemServico : {}, {}", id, servicoContabilOrdemServico);
        if (servicoContabilOrdemServico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servicoContabilOrdemServico.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servicoContabilOrdemServicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        servicoContabilOrdemServico = servicoContabilOrdemServicoService.update(servicoContabilOrdemServico);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servicoContabilOrdemServico.getId().toString()))
            .body(servicoContabilOrdemServico);
    }

    /**
     * {@code PATCH  /servico-contabil-ordem-servicos/:id} : Partial updates given fields of an existing servicoContabilOrdemServico, field will ignore if it is null
     *
     * @param id the id of the servicoContabilOrdemServico to save.
     * @param servicoContabilOrdemServico the servicoContabilOrdemServico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servicoContabilOrdemServico,
     * or with status {@code 400 (Bad Request)} if the servicoContabilOrdemServico is not valid,
     * or with status {@code 404 (Not Found)} if the servicoContabilOrdemServico is not found,
     * or with status {@code 500 (Internal Server Error)} if the servicoContabilOrdemServico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServicoContabilOrdemServico> partialUpdateServicoContabilOrdemServico(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServicoContabilOrdemServico servicoContabilOrdemServico
    ) throws URISyntaxException {
        log.debug("REST request to partial update ServicoContabilOrdemServico partially : {}, {}", id, servicoContabilOrdemServico);
        if (servicoContabilOrdemServico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servicoContabilOrdemServico.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servicoContabilOrdemServicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServicoContabilOrdemServico> result = servicoContabilOrdemServicoService.partialUpdate(servicoContabilOrdemServico);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servicoContabilOrdemServico.getId().toString())
        );
    }

    /**
     * {@code GET  /servico-contabil-ordem-servicos} : get all the servicoContabilOrdemServicos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servicoContabilOrdemServicos in body.
     */
    @GetMapping("")
    public List<ServicoContabilOrdemServico> getAllServicoContabilOrdemServicos(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all ServicoContabilOrdemServicos");
        return servicoContabilOrdemServicoService.findAll();
    }

    /**
     * {@code GET  /servico-contabil-ordem-servicos/:id} : get the "id" servicoContabilOrdemServico.
     *
     * @param id the id of the servicoContabilOrdemServico to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the servicoContabilOrdemServico, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServicoContabilOrdemServico> getServicoContabilOrdemServico(@PathVariable("id") Long id) {
        log.debug("REST request to get ServicoContabilOrdemServico : {}", id);
        Optional<ServicoContabilOrdemServico> servicoContabilOrdemServico = servicoContabilOrdemServicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(servicoContabilOrdemServico);
    }

    /**
     * {@code DELETE  /servico-contabil-ordem-servicos/:id} : delete the "id" servicoContabilOrdemServico.
     *
     * @param id the id of the servicoContabilOrdemServico to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicoContabilOrdemServico(@PathVariable("id") Long id) {
        log.debug("REST request to delete ServicoContabilOrdemServico : {}", id);
        servicoContabilOrdemServicoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
