package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.PeriodoPagamentoRepository;
import com.dobemcontabilidade.service.PeriodoPagamentoQueryService;
import com.dobemcontabilidade.service.PeriodoPagamentoService;
import com.dobemcontabilidade.service.criteria.PeriodoPagamentoCriteria;
import com.dobemcontabilidade.service.dto.PeriodoPagamentoDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.PeriodoPagamento}.
 */
@RestController
@RequestMapping("/api/periodo-pagamentos")
public class PeriodoPagamentoResource {

    private static final Logger log = LoggerFactory.getLogger(PeriodoPagamentoResource.class);

    private static final String ENTITY_NAME = "periodoPagamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PeriodoPagamentoService periodoPagamentoService;

    private final PeriodoPagamentoRepository periodoPagamentoRepository;

    private final PeriodoPagamentoQueryService periodoPagamentoQueryService;

    public PeriodoPagamentoResource(
        PeriodoPagamentoService periodoPagamentoService,
        PeriodoPagamentoRepository periodoPagamentoRepository,
        PeriodoPagamentoQueryService periodoPagamentoQueryService
    ) {
        this.periodoPagamentoService = periodoPagamentoService;
        this.periodoPagamentoRepository = periodoPagamentoRepository;
        this.periodoPagamentoQueryService = periodoPagamentoQueryService;
    }

    /**
     * {@code POST  /periodo-pagamentos} : Create a new periodoPagamento.
     *
     * @param periodoPagamentoDTO the periodoPagamentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new periodoPagamentoDTO, or with status {@code 400 (Bad Request)} if the periodoPagamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PeriodoPagamentoDTO> createPeriodoPagamento(@RequestBody PeriodoPagamentoDTO periodoPagamentoDTO)
        throws URISyntaxException {
        log.debug("REST request to save PeriodoPagamento : {}", periodoPagamentoDTO);
        if (periodoPagamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new periodoPagamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        periodoPagamentoDTO = periodoPagamentoService.save(periodoPagamentoDTO);
        return ResponseEntity.created(new URI("/api/periodo-pagamentos/" + periodoPagamentoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, periodoPagamentoDTO.getId().toString()))
            .body(periodoPagamentoDTO);
    }

    /**
     * {@code PUT  /periodo-pagamentos/:id} : Updates an existing periodoPagamento.
     *
     * @param id the id of the periodoPagamentoDTO to save.
     * @param periodoPagamentoDTO the periodoPagamentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated periodoPagamentoDTO,
     * or with status {@code 400 (Bad Request)} if the periodoPagamentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the periodoPagamentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PeriodoPagamentoDTO> updatePeriodoPagamento(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PeriodoPagamentoDTO periodoPagamentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PeriodoPagamento : {}, {}", id, periodoPagamentoDTO);
        if (periodoPagamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, periodoPagamentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!periodoPagamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        periodoPagamentoDTO = periodoPagamentoService.update(periodoPagamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, periodoPagamentoDTO.getId().toString()))
            .body(periodoPagamentoDTO);
    }

    /**
     * {@code PATCH  /periodo-pagamentos/:id} : Partial updates given fields of an existing periodoPagamento, field will ignore if it is null
     *
     * @param id the id of the periodoPagamentoDTO to save.
     * @param periodoPagamentoDTO the periodoPagamentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated periodoPagamentoDTO,
     * or with status {@code 400 (Bad Request)} if the periodoPagamentoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the periodoPagamentoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the periodoPagamentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PeriodoPagamentoDTO> partialUpdatePeriodoPagamento(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PeriodoPagamentoDTO periodoPagamentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PeriodoPagamento partially : {}, {}", id, periodoPagamentoDTO);
        if (periodoPagamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, periodoPagamentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!periodoPagamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PeriodoPagamentoDTO> result = periodoPagamentoService.partialUpdate(periodoPagamentoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, periodoPagamentoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /periodo-pagamentos} : get all the periodoPagamentos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of periodoPagamentos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PeriodoPagamentoDTO>> getAllPeriodoPagamentos(
        PeriodoPagamentoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PeriodoPagamentos by criteria: {}", criteria);

        Page<PeriodoPagamentoDTO> page = periodoPagamentoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /periodo-pagamentos/count} : count all the periodoPagamentos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countPeriodoPagamentos(PeriodoPagamentoCriteria criteria) {
        log.debug("REST request to count PeriodoPagamentos by criteria: {}", criteria);
        return ResponseEntity.ok().body(periodoPagamentoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /periodo-pagamentos/:id} : get the "id" periodoPagamento.
     *
     * @param id the id of the periodoPagamentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the periodoPagamentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PeriodoPagamentoDTO> getPeriodoPagamento(@PathVariable("id") Long id) {
        log.debug("REST request to get PeriodoPagamento : {}", id);
        Optional<PeriodoPagamentoDTO> periodoPagamentoDTO = periodoPagamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(periodoPagamentoDTO);
    }

    /**
     * {@code DELETE  /periodo-pagamentos/:id} : delete the "id" periodoPagamento.
     *
     * @param id the id of the periodoPagamentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePeriodoPagamento(@PathVariable("id") Long id) {
        log.debug("REST request to delete PeriodoPagamento : {}", id);
        periodoPagamentoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
