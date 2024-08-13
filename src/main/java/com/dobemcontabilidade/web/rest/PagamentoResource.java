package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.Pagamento;
import com.dobemcontabilidade.repository.PagamentoRepository;
import com.dobemcontabilidade.service.PagamentoQueryService;
import com.dobemcontabilidade.service.PagamentoService;
import com.dobemcontabilidade.service.criteria.PagamentoCriteria;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Pagamento}.
 */
@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoResource {

    private static final Logger log = LoggerFactory.getLogger(PagamentoResource.class);

    private static final String ENTITY_NAME = "pagamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PagamentoService pagamentoService;

    private final PagamentoRepository pagamentoRepository;

    private final PagamentoQueryService pagamentoQueryService;

    public PagamentoResource(
        PagamentoService pagamentoService,
        PagamentoRepository pagamentoRepository,
        PagamentoQueryService pagamentoQueryService
    ) {
        this.pagamentoService = pagamentoService;
        this.pagamentoRepository = pagamentoRepository;
        this.pagamentoQueryService = pagamentoQueryService;
    }

    /**
     * {@code POST  /pagamentos} : Create a new pagamento.
     *
     * @param pagamento the pagamento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pagamento, or with status {@code 400 (Bad Request)} if the pagamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Pagamento> createPagamento(@Valid @RequestBody Pagamento pagamento) throws URISyntaxException {
        log.debug("REST request to save Pagamento : {}", pagamento);
        if (pagamento.getId() != null) {
            throw new BadRequestAlertException("A new pagamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        pagamento = pagamentoService.save(pagamento);
        return ResponseEntity.created(new URI("/api/pagamentos/" + pagamento.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, pagamento.getId().toString()))
            .body(pagamento);
    }

    /**
     * {@code PUT  /pagamentos/:id} : Updates an existing pagamento.
     *
     * @param id the id of the pagamento to save.
     * @param pagamento the pagamento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pagamento,
     * or with status {@code 400 (Bad Request)} if the pagamento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pagamento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Pagamento> updatePagamento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Pagamento pagamento
    ) throws URISyntaxException {
        log.debug("REST request to update Pagamento : {}, {}", id, pagamento);
        if (pagamento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pagamento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pagamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        pagamento = pagamentoService.update(pagamento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pagamento.getId().toString()))
            .body(pagamento);
    }

    /**
     * {@code PATCH  /pagamentos/:id} : Partial updates given fields of an existing pagamento, field will ignore if it is null
     *
     * @param id the id of the pagamento to save.
     * @param pagamento the pagamento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pagamento,
     * or with status {@code 400 (Bad Request)} if the pagamento is not valid,
     * or with status {@code 404 (Not Found)} if the pagamento is not found,
     * or with status {@code 500 (Internal Server Error)} if the pagamento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Pagamento> partialUpdatePagamento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Pagamento pagamento
    ) throws URISyntaxException {
        log.debug("REST request to partial update Pagamento partially : {}, {}", id, pagamento);
        if (pagamento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pagamento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pagamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Pagamento> result = pagamentoService.partialUpdate(pagamento);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pagamento.getId().toString())
        );
    }

    /**
     * {@code GET  /pagamentos} : get all the pagamentos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pagamentos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Pagamento>> getAllPagamentos(
        PagamentoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Pagamentos by criteria: {}", criteria);

        Page<Pagamento> page = pagamentoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pagamentos/count} : count all the pagamentos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countPagamentos(PagamentoCriteria criteria) {
        log.debug("REST request to count Pagamentos by criteria: {}", criteria);
        return ResponseEntity.ok().body(pagamentoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /pagamentos/:id} : get the "id" pagamento.
     *
     * @param id the id of the pagamento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pagamento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> getPagamento(@PathVariable("id") Long id) {
        log.debug("REST request to get Pagamento : {}", id);
        Optional<Pagamento> pagamento = pagamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pagamento);
    }

    /**
     * {@code DELETE  /pagamentos/:id} : delete the "id" pagamento.
     *
     * @param id the id of the pagamento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePagamento(@PathVariable("id") Long id) {
        log.debug("REST request to delete Pagamento : {}", id);
        pagamentoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
