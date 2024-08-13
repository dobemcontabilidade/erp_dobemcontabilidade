package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.ServicoContabilEtapaFluxoExecucao;
import com.dobemcontabilidade.repository.ServicoContabilEtapaFluxoExecucaoRepository;
import com.dobemcontabilidade.service.ServicoContabilEtapaFluxoExecucaoService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.ServicoContabilEtapaFluxoExecucao}.
 */
@RestController
@RequestMapping("/api/servico-contabil-etapa-fluxo-execucaos")
public class ServicoContabilEtapaFluxoExecucaoResource {

    private static final Logger log = LoggerFactory.getLogger(ServicoContabilEtapaFluxoExecucaoResource.class);

    private static final String ENTITY_NAME = "servicoContabilEtapaFluxoExecucao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServicoContabilEtapaFluxoExecucaoService servicoContabilEtapaFluxoExecucaoService;

    private final ServicoContabilEtapaFluxoExecucaoRepository servicoContabilEtapaFluxoExecucaoRepository;

    public ServicoContabilEtapaFluxoExecucaoResource(
        ServicoContabilEtapaFluxoExecucaoService servicoContabilEtapaFluxoExecucaoService,
        ServicoContabilEtapaFluxoExecucaoRepository servicoContabilEtapaFluxoExecucaoRepository
    ) {
        this.servicoContabilEtapaFluxoExecucaoService = servicoContabilEtapaFluxoExecucaoService;
        this.servicoContabilEtapaFluxoExecucaoRepository = servicoContabilEtapaFluxoExecucaoRepository;
    }

    /**
     * {@code POST  /servico-contabil-etapa-fluxo-execucaos} : Create a new servicoContabilEtapaFluxoExecucao.
     *
     * @param servicoContabilEtapaFluxoExecucao the servicoContabilEtapaFluxoExecucao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servicoContabilEtapaFluxoExecucao, or with status {@code 400 (Bad Request)} if the servicoContabilEtapaFluxoExecucao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ServicoContabilEtapaFluxoExecucao> createServicoContabilEtapaFluxoExecucao(
        @Valid @RequestBody ServicoContabilEtapaFluxoExecucao servicoContabilEtapaFluxoExecucao
    ) throws URISyntaxException {
        log.debug("REST request to save ServicoContabilEtapaFluxoExecucao : {}", servicoContabilEtapaFluxoExecucao);
        if (servicoContabilEtapaFluxoExecucao.getId() != null) {
            throw new BadRequestAlertException(
                "A new servicoContabilEtapaFluxoExecucao cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        servicoContabilEtapaFluxoExecucao = servicoContabilEtapaFluxoExecucaoService.save(servicoContabilEtapaFluxoExecucao);
        return ResponseEntity.created(new URI("/api/servico-contabil-etapa-fluxo-execucaos/" + servicoContabilEtapaFluxoExecucao.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    servicoContabilEtapaFluxoExecucao.getId().toString()
                )
            )
            .body(servicoContabilEtapaFluxoExecucao);
    }

    /**
     * {@code PUT  /servico-contabil-etapa-fluxo-execucaos/:id} : Updates an existing servicoContabilEtapaFluxoExecucao.
     *
     * @param id the id of the servicoContabilEtapaFluxoExecucao to save.
     * @param servicoContabilEtapaFluxoExecucao the servicoContabilEtapaFluxoExecucao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servicoContabilEtapaFluxoExecucao,
     * or with status {@code 400 (Bad Request)} if the servicoContabilEtapaFluxoExecucao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the servicoContabilEtapaFluxoExecucao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServicoContabilEtapaFluxoExecucao> updateServicoContabilEtapaFluxoExecucao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServicoContabilEtapaFluxoExecucao servicoContabilEtapaFluxoExecucao
    ) throws URISyntaxException {
        log.debug("REST request to update ServicoContabilEtapaFluxoExecucao : {}, {}", id, servicoContabilEtapaFluxoExecucao);
        if (servicoContabilEtapaFluxoExecucao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servicoContabilEtapaFluxoExecucao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servicoContabilEtapaFluxoExecucaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        servicoContabilEtapaFluxoExecucao = servicoContabilEtapaFluxoExecucaoService.update(servicoContabilEtapaFluxoExecucao);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servicoContabilEtapaFluxoExecucao.getId().toString())
            )
            .body(servicoContabilEtapaFluxoExecucao);
    }

    /**
     * {@code PATCH  /servico-contabil-etapa-fluxo-execucaos/:id} : Partial updates given fields of an existing servicoContabilEtapaFluxoExecucao, field will ignore if it is null
     *
     * @param id the id of the servicoContabilEtapaFluxoExecucao to save.
     * @param servicoContabilEtapaFluxoExecucao the servicoContabilEtapaFluxoExecucao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servicoContabilEtapaFluxoExecucao,
     * or with status {@code 400 (Bad Request)} if the servicoContabilEtapaFluxoExecucao is not valid,
     * or with status {@code 404 (Not Found)} if the servicoContabilEtapaFluxoExecucao is not found,
     * or with status {@code 500 (Internal Server Error)} if the servicoContabilEtapaFluxoExecucao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServicoContabilEtapaFluxoExecucao> partialUpdateServicoContabilEtapaFluxoExecucao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServicoContabilEtapaFluxoExecucao servicoContabilEtapaFluxoExecucao
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update ServicoContabilEtapaFluxoExecucao partially : {}, {}",
            id,
            servicoContabilEtapaFluxoExecucao
        );
        if (servicoContabilEtapaFluxoExecucao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servicoContabilEtapaFluxoExecucao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servicoContabilEtapaFluxoExecucaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServicoContabilEtapaFluxoExecucao> result = servicoContabilEtapaFluxoExecucaoService.partialUpdate(
            servicoContabilEtapaFluxoExecucao
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servicoContabilEtapaFluxoExecucao.getId().toString())
        );
    }

    /**
     * {@code GET  /servico-contabil-etapa-fluxo-execucaos} : get all the servicoContabilEtapaFluxoExecucaos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servicoContabilEtapaFluxoExecucaos in body.
     */
    @GetMapping("")
    public List<ServicoContabilEtapaFluxoExecucao> getAllServicoContabilEtapaFluxoExecucaos(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all ServicoContabilEtapaFluxoExecucaos");
        return servicoContabilEtapaFluxoExecucaoService.findAll();
    }

    /**
     * {@code GET  /servico-contabil-etapa-fluxo-execucaos/:id} : get the "id" servicoContabilEtapaFluxoExecucao.
     *
     * @param id the id of the servicoContabilEtapaFluxoExecucao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the servicoContabilEtapaFluxoExecucao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServicoContabilEtapaFluxoExecucao> getServicoContabilEtapaFluxoExecucao(@PathVariable("id") Long id) {
        log.debug("REST request to get ServicoContabilEtapaFluxoExecucao : {}", id);
        Optional<ServicoContabilEtapaFluxoExecucao> servicoContabilEtapaFluxoExecucao = servicoContabilEtapaFluxoExecucaoService.findOne(
            id
        );
        return ResponseUtil.wrapOrNotFound(servicoContabilEtapaFluxoExecucao);
    }

    /**
     * {@code DELETE  /servico-contabil-etapa-fluxo-execucaos/:id} : delete the "id" servicoContabilEtapaFluxoExecucao.
     *
     * @param id the id of the servicoContabilEtapaFluxoExecucao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicoContabilEtapaFluxoExecucao(@PathVariable("id") Long id) {
        log.debug("REST request to delete ServicoContabilEtapaFluxoExecucao : {}", id);
        servicoContabilEtapaFluxoExecucaoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
