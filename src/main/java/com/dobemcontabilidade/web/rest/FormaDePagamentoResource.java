package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.FormaDePagamento;
import com.dobemcontabilidade.repository.FormaDePagamentoRepository;
import com.dobemcontabilidade.service.FormaDePagamentoQueryService;
import com.dobemcontabilidade.service.FormaDePagamentoService;
import com.dobemcontabilidade.service.criteria.FormaDePagamentoCriteria;
import com.dobemcontabilidade.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.FormaDePagamento}.
 */
@RestController
@RequestMapping("/api/forma-de-pagamentos")
public class FormaDePagamentoResource {

    private static final Logger log = LoggerFactory.getLogger(FormaDePagamentoResource.class);

    private static final String ENTITY_NAME = "formaDePagamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormaDePagamentoService formaDePagamentoService;

    private final FormaDePagamentoRepository formaDePagamentoRepository;

    private final FormaDePagamentoQueryService formaDePagamentoQueryService;

    public FormaDePagamentoResource(
        FormaDePagamentoService formaDePagamentoService,
        FormaDePagamentoRepository formaDePagamentoRepository,
        FormaDePagamentoQueryService formaDePagamentoQueryService
    ) {
        this.formaDePagamentoService = formaDePagamentoService;
        this.formaDePagamentoRepository = formaDePagamentoRepository;
        this.formaDePagamentoQueryService = formaDePagamentoQueryService;
    }

    /**
     * {@code POST  /forma-de-pagamentos} : Create a new formaDePagamento.
     *
     * @param formaDePagamento the formaDePagamento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formaDePagamento, or with status {@code 400 (Bad Request)} if the formaDePagamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FormaDePagamento> createFormaDePagamento(@RequestBody FormaDePagamento formaDePagamento)
        throws URISyntaxException {
        log.debug("REST request to save FormaDePagamento : {}", formaDePagamento);
        if (formaDePagamento.getId() != null) {
            throw new BadRequestAlertException("A new formaDePagamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        formaDePagamento = formaDePagamentoService.save(formaDePagamento);
        return ResponseEntity.created(new URI("/api/forma-de-pagamentos/" + formaDePagamento.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, formaDePagamento.getId().toString()))
            .body(formaDePagamento);
    }

    /**
     * {@code PUT  /forma-de-pagamentos/:id} : Updates an existing formaDePagamento.
     *
     * @param id the id of the formaDePagamento to save.
     * @param formaDePagamento the formaDePagamento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formaDePagamento,
     * or with status {@code 400 (Bad Request)} if the formaDePagamento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formaDePagamento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FormaDePagamento> updateFormaDePagamento(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FormaDePagamento formaDePagamento
    ) throws URISyntaxException {
        log.debug("REST request to update FormaDePagamento : {}, {}", id, formaDePagamento);
        if (formaDePagamento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formaDePagamento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formaDePagamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        formaDePagamento = formaDePagamentoService.update(formaDePagamento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formaDePagamento.getId().toString()))
            .body(formaDePagamento);
    }

    /**
     * {@code PATCH  /forma-de-pagamentos/:id} : Partial updates given fields of an existing formaDePagamento, field will ignore if it is null
     *
     * @param id the id of the formaDePagamento to save.
     * @param formaDePagamento the formaDePagamento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formaDePagamento,
     * or with status {@code 400 (Bad Request)} if the formaDePagamento is not valid,
     * or with status {@code 404 (Not Found)} if the formaDePagamento is not found,
     * or with status {@code 500 (Internal Server Error)} if the formaDePagamento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FormaDePagamento> partialUpdateFormaDePagamento(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FormaDePagamento formaDePagamento
    ) throws URISyntaxException {
        log.debug("REST request to partial update FormaDePagamento partially : {}, {}", id, formaDePagamento);
        if (formaDePagamento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formaDePagamento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formaDePagamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FormaDePagamento> result = formaDePagamentoService.partialUpdate(formaDePagamento);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formaDePagamento.getId().toString())
        );
    }

    /**
     * {@code GET  /forma-de-pagamentos} : get all the formaDePagamentos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formaDePagamentos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FormaDePagamento>> getAllFormaDePagamentos(
        FormaDePagamentoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get FormaDePagamentos by criteria: {}", criteria);

        Page<FormaDePagamento> page = formaDePagamentoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /forma-de-pagamentos/count} : count all the formaDePagamentos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countFormaDePagamentos(FormaDePagamentoCriteria criteria) {
        log.debug("REST request to count FormaDePagamentos by criteria: {}", criteria);
        return ResponseEntity.ok().body(formaDePagamentoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /forma-de-pagamentos/:id} : get the "id" formaDePagamento.
     *
     * @param id the id of the formaDePagamento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formaDePagamento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FormaDePagamento> getFormaDePagamento(@PathVariable("id") Long id) {
        log.debug("REST request to get FormaDePagamento : {}", id);
        Optional<FormaDePagamento> formaDePagamento = formaDePagamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formaDePagamento);
    }

    /**
     * {@code DELETE  /forma-de-pagamentos/:id} : delete the "id" formaDePagamento.
     *
     * @param id the id of the formaDePagamento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormaDePagamento(@PathVariable("id") Long id) {
        log.debug("REST request to delete FormaDePagamento : {}", id);
        formaDePagamentoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
