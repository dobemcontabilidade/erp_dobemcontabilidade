package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.GatewayPagamento;
import com.dobemcontabilidade.repository.GatewayPagamentoRepository;
import com.dobemcontabilidade.service.GatewayPagamentoService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.GatewayPagamento}.
 */
@RestController
@RequestMapping("/api/gateway-pagamentos")
public class GatewayPagamentoResource {

    private static final Logger log = LoggerFactory.getLogger(GatewayPagamentoResource.class);

    private static final String ENTITY_NAME = "gatewayPagamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GatewayPagamentoService gatewayPagamentoService;

    private final GatewayPagamentoRepository gatewayPagamentoRepository;

    public GatewayPagamentoResource(
        GatewayPagamentoService gatewayPagamentoService,
        GatewayPagamentoRepository gatewayPagamentoRepository
    ) {
        this.gatewayPagamentoService = gatewayPagamentoService;
        this.gatewayPagamentoRepository = gatewayPagamentoRepository;
    }

    /**
     * {@code POST  /gateway-pagamentos} : Create a new gatewayPagamento.
     *
     * @param gatewayPagamento the gatewayPagamento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gatewayPagamento, or with status {@code 400 (Bad Request)} if the gatewayPagamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<GatewayPagamento> createGatewayPagamento(@Valid @RequestBody GatewayPagamento gatewayPagamento)
        throws URISyntaxException {
        log.debug("REST request to save GatewayPagamento : {}", gatewayPagamento);
        if (gatewayPagamento.getId() != null) {
            throw new BadRequestAlertException("A new gatewayPagamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        gatewayPagamento = gatewayPagamentoService.save(gatewayPagamento);
        return ResponseEntity.created(new URI("/api/gateway-pagamentos/" + gatewayPagamento.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, gatewayPagamento.getId().toString()))
            .body(gatewayPagamento);
    }

    /**
     * {@code PUT  /gateway-pagamentos/:id} : Updates an existing gatewayPagamento.
     *
     * @param id the id of the gatewayPagamento to save.
     * @param gatewayPagamento the gatewayPagamento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gatewayPagamento,
     * or with status {@code 400 (Bad Request)} if the gatewayPagamento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gatewayPagamento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<GatewayPagamento> updateGatewayPagamento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GatewayPagamento gatewayPagamento
    ) throws URISyntaxException {
        log.debug("REST request to update GatewayPagamento : {}, {}", id, gatewayPagamento);
        if (gatewayPagamento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gatewayPagamento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gatewayPagamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        gatewayPagamento = gatewayPagamentoService.update(gatewayPagamento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gatewayPagamento.getId().toString()))
            .body(gatewayPagamento);
    }

    /**
     * {@code PATCH  /gateway-pagamentos/:id} : Partial updates given fields of an existing gatewayPagamento, field will ignore if it is null
     *
     * @param id the id of the gatewayPagamento to save.
     * @param gatewayPagamento the gatewayPagamento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gatewayPagamento,
     * or with status {@code 400 (Bad Request)} if the gatewayPagamento is not valid,
     * or with status {@code 404 (Not Found)} if the gatewayPagamento is not found,
     * or with status {@code 500 (Internal Server Error)} if the gatewayPagamento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GatewayPagamento> partialUpdateGatewayPagamento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GatewayPagamento gatewayPagamento
    ) throws URISyntaxException {
        log.debug("REST request to partial update GatewayPagamento partially : {}, {}", id, gatewayPagamento);
        if (gatewayPagamento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gatewayPagamento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gatewayPagamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GatewayPagamento> result = gatewayPagamentoService.partialUpdate(gatewayPagamento);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gatewayPagamento.getId().toString())
        );
    }

    /**
     * {@code GET  /gateway-pagamentos} : get all the gatewayPagamentos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gatewayPagamentos in body.
     */
    @GetMapping("")
    public List<GatewayPagamento> getAllGatewayPagamentos() {
        log.debug("REST request to get all GatewayPagamentos");
        return gatewayPagamentoService.findAll();
    }

    /**
     * {@code GET  /gateway-pagamentos/:id} : get the "id" gatewayPagamento.
     *
     * @param id the id of the gatewayPagamento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gatewayPagamento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GatewayPagamento> getGatewayPagamento(@PathVariable("id") Long id) {
        log.debug("REST request to get GatewayPagamento : {}", id);
        Optional<GatewayPagamento> gatewayPagamento = gatewayPagamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gatewayPagamento);
    }

    /**
     * {@code DELETE  /gateway-pagamentos/:id} : delete the "id" gatewayPagamento.
     *
     * @param id the id of the gatewayPagamento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGatewayPagamento(@PathVariable("id") Long id) {
        log.debug("REST request to delete GatewayPagamento : {}", id);
        gatewayPagamentoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
