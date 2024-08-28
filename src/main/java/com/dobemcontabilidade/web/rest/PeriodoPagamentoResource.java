package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.PeriodoPagamento;
import com.dobemcontabilidade.repository.PeriodoPagamentoRepository;
import com.dobemcontabilidade.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dobemcontabilidade.domain.PeriodoPagamento}.
 */
@RestController
@RequestMapping("/api/periodo-pagamentos")
@Transactional
public class PeriodoPagamentoResource {

    private static final Logger log = LoggerFactory.getLogger(PeriodoPagamentoResource.class);

    private static final String ENTITY_NAME = "periodoPagamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PeriodoPagamentoRepository periodoPagamentoRepository;

    public PeriodoPagamentoResource(PeriodoPagamentoRepository periodoPagamentoRepository) {
        this.periodoPagamentoRepository = periodoPagamentoRepository;
    }

    /**
     * {@code POST  /periodo-pagamentos} : Create a new periodoPagamento.
     *
     * @param periodoPagamento the periodoPagamento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new periodoPagamento, or with status {@code 400 (Bad Request)} if the periodoPagamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PeriodoPagamento> createPeriodoPagamento(@RequestBody PeriodoPagamento periodoPagamento)
        throws URISyntaxException {
        log.debug("REST request to save PeriodoPagamento : {}", periodoPagamento);
        if (periodoPagamento.getId() != null) {
            throw new BadRequestAlertException("A new periodoPagamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        periodoPagamento = periodoPagamentoRepository.save(periodoPagamento);
        return ResponseEntity.created(new URI("/api/periodo-pagamentos/" + periodoPagamento.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, periodoPagamento.getId().toString()))
            .body(periodoPagamento);
    }

    /**
     * {@code PUT  /periodo-pagamentos/:id} : Updates an existing periodoPagamento.
     *
     * @param id the id of the periodoPagamento to save.
     * @param periodoPagamento the periodoPagamento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated periodoPagamento,
     * or with status {@code 400 (Bad Request)} if the periodoPagamento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the periodoPagamento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PeriodoPagamento> updatePeriodoPagamento(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PeriodoPagamento periodoPagamento
    ) throws URISyntaxException {
        log.debug("REST request to update PeriodoPagamento : {}, {}", id, periodoPagamento);
        if (periodoPagamento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, periodoPagamento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!periodoPagamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        periodoPagamento = periodoPagamentoRepository.save(periodoPagamento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, periodoPagamento.getId().toString()))
            .body(periodoPagamento);
    }

    /**
     * {@code PATCH  /periodo-pagamentos/:id} : Partial updates given fields of an existing periodoPagamento, field will ignore if it is null
     *
     * @param id the id of the periodoPagamento to save.
     * @param periodoPagamento the periodoPagamento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated periodoPagamento,
     * or with status {@code 400 (Bad Request)} if the periodoPagamento is not valid,
     * or with status {@code 404 (Not Found)} if the periodoPagamento is not found,
     * or with status {@code 500 (Internal Server Error)} if the periodoPagamento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PeriodoPagamento> partialUpdatePeriodoPagamento(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PeriodoPagamento periodoPagamento
    ) throws URISyntaxException {
        log.debug("REST request to partial update PeriodoPagamento partially : {}, {}", id, periodoPagamento);
        if (periodoPagamento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, periodoPagamento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!periodoPagamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PeriodoPagamento> result = periodoPagamentoRepository
            .findById(periodoPagamento.getId())
            .map(existingPeriodoPagamento -> {
                if (periodoPagamento.getPeriodo() != null) {
                    existingPeriodoPagamento.setPeriodo(periodoPagamento.getPeriodo());
                }
                if (periodoPagamento.getNumeroDias() != null) {
                    existingPeriodoPagamento.setNumeroDias(periodoPagamento.getNumeroDias());
                }

                return existingPeriodoPagamento;
            })
            .map(periodoPagamentoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, periodoPagamento.getId().toString())
        );
    }

    /**
     * {@code GET  /periodo-pagamentos} : get all the periodoPagamentos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of periodoPagamentos in body.
     */
    @GetMapping("")
    public List<PeriodoPagamento> getAllPeriodoPagamentos() {
        log.debug("REST request to get all PeriodoPagamentos");
        return periodoPagamentoRepository.findAll();
    }

    /**
     * {@code GET  /periodo-pagamentos/:id} : get the "id" periodoPagamento.
     *
     * @param id the id of the periodoPagamento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the periodoPagamento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PeriodoPagamento> getPeriodoPagamento(@PathVariable("id") Long id) {
        log.debug("REST request to get PeriodoPagamento : {}", id);
        Optional<PeriodoPagamento> periodoPagamento = periodoPagamentoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(periodoPagamento);
    }

    /**
     * {@code DELETE  /periodo-pagamentos/:id} : delete the "id" periodoPagamento.
     *
     * @param id the id of the periodoPagamento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePeriodoPagamento(@PathVariable("id") Long id) {
        log.debug("REST request to delete PeriodoPagamento : {}", id);
        periodoPagamentoRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
