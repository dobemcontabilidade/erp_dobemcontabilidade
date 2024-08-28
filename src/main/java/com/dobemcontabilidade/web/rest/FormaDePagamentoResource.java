package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.FormaDePagamento;
import com.dobemcontabilidade.repository.FormaDePagamentoRepository;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.FormaDePagamento}.
 */
@RestController
@RequestMapping("/api/forma-de-pagamentos")
@Transactional
public class FormaDePagamentoResource {

    private static final Logger log = LoggerFactory.getLogger(FormaDePagamentoResource.class);

    private static final String ENTITY_NAME = "formaDePagamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormaDePagamentoRepository formaDePagamentoRepository;

    public FormaDePagamentoResource(FormaDePagamentoRepository formaDePagamentoRepository) {
        this.formaDePagamentoRepository = formaDePagamentoRepository;
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
        formaDePagamento = formaDePagamentoRepository.save(formaDePagamento);
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

        formaDePagamento = formaDePagamentoRepository.save(formaDePagamento);
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

        Optional<FormaDePagamento> result = formaDePagamentoRepository
            .findById(formaDePagamento.getId())
            .map(existingFormaDePagamento -> {
                if (formaDePagamento.getForma() != null) {
                    existingFormaDePagamento.setForma(formaDePagamento.getForma());
                }
                if (formaDePagamento.getDescricao() != null) {
                    existingFormaDePagamento.setDescricao(formaDePagamento.getDescricao());
                }
                if (formaDePagamento.getDisponivel() != null) {
                    existingFormaDePagamento.setDisponivel(formaDePagamento.getDisponivel());
                }

                return existingFormaDePagamento;
            })
            .map(formaDePagamentoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formaDePagamento.getId().toString())
        );
    }

    /**
     * {@code GET  /forma-de-pagamentos} : get all the formaDePagamentos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formaDePagamentos in body.
     */
    @GetMapping("")
    public List<FormaDePagamento> getAllFormaDePagamentos() {
        log.debug("REST request to get all FormaDePagamentos");
        return formaDePagamentoRepository.findAll();
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
        Optional<FormaDePagamento> formaDePagamento = formaDePagamentoRepository.findById(id);
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
        formaDePagamentoRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
