package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.FormaDePagamentoRepository;
import com.dobemcontabilidade.service.FormaDePagamentoQueryService;
import com.dobemcontabilidade.service.FormaDePagamentoService;
import com.dobemcontabilidade.service.criteria.FormaDePagamentoCriteria;
import com.dobemcontabilidade.service.dto.FormaDePagamentoDTO;
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
     * @param formaDePagamentoDTO the formaDePagamentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formaDePagamentoDTO, or with status {@code 400 (Bad Request)} if the formaDePagamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FormaDePagamentoDTO> createFormaDePagamento(@RequestBody FormaDePagamentoDTO formaDePagamentoDTO)
        throws URISyntaxException {
        log.debug("REST request to save FormaDePagamento : {}", formaDePagamentoDTO);
        if (formaDePagamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new formaDePagamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        formaDePagamentoDTO = formaDePagamentoService.save(formaDePagamentoDTO);
        return ResponseEntity.created(new URI("/api/forma-de-pagamentos/" + formaDePagamentoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, formaDePagamentoDTO.getId().toString()))
            .body(formaDePagamentoDTO);
    }

    /**
     * {@code PUT  /forma-de-pagamentos/:id} : Updates an existing formaDePagamento.
     *
     * @param id the id of the formaDePagamentoDTO to save.
     * @param formaDePagamentoDTO the formaDePagamentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formaDePagamentoDTO,
     * or with status {@code 400 (Bad Request)} if the formaDePagamentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formaDePagamentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FormaDePagamentoDTO> updateFormaDePagamento(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FormaDePagamentoDTO formaDePagamentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FormaDePagamento : {}, {}", id, formaDePagamentoDTO);
        if (formaDePagamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formaDePagamentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formaDePagamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        formaDePagamentoDTO = formaDePagamentoService.update(formaDePagamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formaDePagamentoDTO.getId().toString()))
            .body(formaDePagamentoDTO);
    }

    /**
     * {@code PATCH  /forma-de-pagamentos/:id} : Partial updates given fields of an existing formaDePagamento, field will ignore if it is null
     *
     * @param id the id of the formaDePagamentoDTO to save.
     * @param formaDePagamentoDTO the formaDePagamentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formaDePagamentoDTO,
     * or with status {@code 400 (Bad Request)} if the formaDePagamentoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the formaDePagamentoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the formaDePagamentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FormaDePagamentoDTO> partialUpdateFormaDePagamento(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FormaDePagamentoDTO formaDePagamentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FormaDePagamento partially : {}, {}", id, formaDePagamentoDTO);
        if (formaDePagamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formaDePagamentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formaDePagamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FormaDePagamentoDTO> result = formaDePagamentoService.partialUpdate(formaDePagamentoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formaDePagamentoDTO.getId().toString())
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
    public ResponseEntity<List<FormaDePagamentoDTO>> getAllFormaDePagamentos(
        FormaDePagamentoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get FormaDePagamentos by criteria: {}", criteria);

        Page<FormaDePagamentoDTO> page = formaDePagamentoQueryService.findByCriteria(criteria, pageable);
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
     * @param id the id of the formaDePagamentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formaDePagamentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FormaDePagamentoDTO> getFormaDePagamento(@PathVariable("id") Long id) {
        log.debug("REST request to get FormaDePagamento : {}", id);
        Optional<FormaDePagamentoDTO> formaDePagamentoDTO = formaDePagamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formaDePagamentoDTO);
    }

    /**
     * {@code DELETE  /forma-de-pagamentos/:id} : delete the "id" formaDePagamento.
     *
     * @param id the id of the formaDePagamentoDTO to delete.
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
