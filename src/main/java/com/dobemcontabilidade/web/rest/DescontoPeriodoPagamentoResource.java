package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.DescontoPeriodoPagamento;
import com.dobemcontabilidade.repository.DescontoPeriodoPagamentoRepository;
import com.dobemcontabilidade.service.DescontoPeriodoPagamentoQueryService;
import com.dobemcontabilidade.service.DescontoPeriodoPagamentoService;
import com.dobemcontabilidade.service.criteria.DescontoPeriodoPagamentoCriteria;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.DescontoPeriodoPagamento}.
 */
@RestController
@RequestMapping("/api/desconto-periodo-pagamentos")
public class DescontoPeriodoPagamentoResource {

    private static final Logger log = LoggerFactory.getLogger(DescontoPeriodoPagamentoResource.class);

    private static final String ENTITY_NAME = "descontoPeriodoPagamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DescontoPeriodoPagamentoService descontoPeriodoPagamentoService;

    private final DescontoPeriodoPagamentoRepository descontoPeriodoPagamentoRepository;

    private final DescontoPeriodoPagamentoQueryService descontoPeriodoPagamentoQueryService;

    public DescontoPeriodoPagamentoResource(
        DescontoPeriodoPagamentoService descontoPeriodoPagamentoService,
        DescontoPeriodoPagamentoRepository descontoPeriodoPagamentoRepository,
        DescontoPeriodoPagamentoQueryService descontoPeriodoPagamentoQueryService
    ) {
        this.descontoPeriodoPagamentoService = descontoPeriodoPagamentoService;
        this.descontoPeriodoPagamentoRepository = descontoPeriodoPagamentoRepository;
        this.descontoPeriodoPagamentoQueryService = descontoPeriodoPagamentoQueryService;
    }

    /**
     * {@code POST  /desconto-periodo-pagamentos} : Create a new descontoPeriodoPagamento.
     *
     * @param descontoPeriodoPagamento the descontoPeriodoPagamento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new descontoPeriodoPagamento, or with status {@code 400 (Bad Request)} if the descontoPeriodoPagamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DescontoPeriodoPagamento> createDescontoPeriodoPagamento(
        @Valid @RequestBody DescontoPeriodoPagamento descontoPeriodoPagamento
    ) throws URISyntaxException {
        log.debug("REST request to save DescontoPeriodoPagamento : {}", descontoPeriodoPagamento);
        if (descontoPeriodoPagamento.getId() != null) {
            throw new BadRequestAlertException("A new descontoPeriodoPagamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        descontoPeriodoPagamento = descontoPeriodoPagamentoService.save(descontoPeriodoPagamento);
        return ResponseEntity.created(new URI("/api/desconto-periodo-pagamentos/" + descontoPeriodoPagamento.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, descontoPeriodoPagamento.getId().toString()))
            .body(descontoPeriodoPagamento);
    }

    /**
     * {@code PUT  /desconto-periodo-pagamentos/:id} : Updates an existing descontoPeriodoPagamento.
     *
     * @param id the id of the descontoPeriodoPagamento to save.
     * @param descontoPeriodoPagamento the descontoPeriodoPagamento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated descontoPeriodoPagamento,
     * or with status {@code 400 (Bad Request)} if the descontoPeriodoPagamento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the descontoPeriodoPagamento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DescontoPeriodoPagamento> updateDescontoPeriodoPagamento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DescontoPeriodoPagamento descontoPeriodoPagamento
    ) throws URISyntaxException {
        log.debug("REST request to update DescontoPeriodoPagamento : {}, {}", id, descontoPeriodoPagamento);
        if (descontoPeriodoPagamento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, descontoPeriodoPagamento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!descontoPeriodoPagamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        descontoPeriodoPagamento = descontoPeriodoPagamentoService.update(descontoPeriodoPagamento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, descontoPeriodoPagamento.getId().toString()))
            .body(descontoPeriodoPagamento);
    }

    /**
     * {@code PATCH  /desconto-periodo-pagamentos/:id} : Partial updates given fields of an existing descontoPeriodoPagamento, field will ignore if it is null
     *
     * @param id the id of the descontoPeriodoPagamento to save.
     * @param descontoPeriodoPagamento the descontoPeriodoPagamento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated descontoPeriodoPagamento,
     * or with status {@code 400 (Bad Request)} if the descontoPeriodoPagamento is not valid,
     * or with status {@code 404 (Not Found)} if the descontoPeriodoPagamento is not found,
     * or with status {@code 500 (Internal Server Error)} if the descontoPeriodoPagamento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DescontoPeriodoPagamento> partialUpdateDescontoPeriodoPagamento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DescontoPeriodoPagamento descontoPeriodoPagamento
    ) throws URISyntaxException {
        log.debug("REST request to partial update DescontoPeriodoPagamento partially : {}, {}", id, descontoPeriodoPagamento);
        if (descontoPeriodoPagamento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, descontoPeriodoPagamento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!descontoPeriodoPagamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DescontoPeriodoPagamento> result = descontoPeriodoPagamentoService.partialUpdate(descontoPeriodoPagamento);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, descontoPeriodoPagamento.getId().toString())
        );
    }

    /**
     * {@code GET  /desconto-periodo-pagamentos} : get all the descontoPeriodoPagamentos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of descontoPeriodoPagamentos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DescontoPeriodoPagamento>> getAllDescontoPeriodoPagamentos(DescontoPeriodoPagamentoCriteria criteria) {
        log.debug("REST request to get DescontoPeriodoPagamentos by criteria: {}", criteria);

        List<DescontoPeriodoPagamento> entityList = descontoPeriodoPagamentoQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /desconto-periodo-pagamentos/count} : count all the descontoPeriodoPagamentos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countDescontoPeriodoPagamentos(DescontoPeriodoPagamentoCriteria criteria) {
        log.debug("REST request to count DescontoPeriodoPagamentos by criteria: {}", criteria);
        return ResponseEntity.ok().body(descontoPeriodoPagamentoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /desconto-periodo-pagamentos/:id} : get the "id" descontoPeriodoPagamento.
     *
     * @param id the id of the descontoPeriodoPagamento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the descontoPeriodoPagamento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DescontoPeriodoPagamento> getDescontoPeriodoPagamento(@PathVariable("id") Long id) {
        log.debug("REST request to get DescontoPeriodoPagamento : {}", id);
        Optional<DescontoPeriodoPagamento> descontoPeriodoPagamento = descontoPeriodoPagamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(descontoPeriodoPagamento);
    }

    /**
     * {@code DELETE  /desconto-periodo-pagamentos/:id} : delete the "id" descontoPeriodoPagamento.
     *
     * @param id the id of the descontoPeriodoPagamento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDescontoPeriodoPagamento(@PathVariable("id") Long id) {
        log.debug("REST request to delete DescontoPeriodoPagamento : {}", id);
        descontoPeriodoPagamentoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
