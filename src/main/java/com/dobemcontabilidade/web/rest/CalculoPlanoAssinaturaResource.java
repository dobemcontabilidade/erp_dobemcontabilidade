package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.CalculoPlanoAssinatura;
import com.dobemcontabilidade.repository.CalculoPlanoAssinaturaRepository;
import com.dobemcontabilidade.service.CalculoPlanoAssinaturaQueryService;
import com.dobemcontabilidade.service.CalculoPlanoAssinaturaService;
import com.dobemcontabilidade.service.criteria.CalculoPlanoAssinaturaCriteria;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.CalculoPlanoAssinatura}.
 */
@RestController
@RequestMapping("/api/calculo-plano-assinaturas")
public class CalculoPlanoAssinaturaResource {

    private static final Logger log = LoggerFactory.getLogger(CalculoPlanoAssinaturaResource.class);

    private static final String ENTITY_NAME = "calculoPlanoAssinatura";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CalculoPlanoAssinaturaService calculoPlanoAssinaturaService;

    private final CalculoPlanoAssinaturaRepository calculoPlanoAssinaturaRepository;

    private final CalculoPlanoAssinaturaQueryService calculoPlanoAssinaturaQueryService;

    public CalculoPlanoAssinaturaResource(
        CalculoPlanoAssinaturaService calculoPlanoAssinaturaService,
        CalculoPlanoAssinaturaRepository calculoPlanoAssinaturaRepository,
        CalculoPlanoAssinaturaQueryService calculoPlanoAssinaturaQueryService
    ) {
        this.calculoPlanoAssinaturaService = calculoPlanoAssinaturaService;
        this.calculoPlanoAssinaturaRepository = calculoPlanoAssinaturaRepository;
        this.calculoPlanoAssinaturaQueryService = calculoPlanoAssinaturaQueryService;
    }

    /**
     * {@code POST  /calculo-plano-assinaturas} : Create a new calculoPlanoAssinatura.
     *
     * @param calculoPlanoAssinatura the calculoPlanoAssinatura to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new calculoPlanoAssinatura, or with status {@code 400 (Bad Request)} if the calculoPlanoAssinatura has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CalculoPlanoAssinatura> createCalculoPlanoAssinatura(
        @Valid @RequestBody CalculoPlanoAssinatura calculoPlanoAssinatura
    ) throws URISyntaxException {
        log.debug("REST request to save CalculoPlanoAssinatura : {}", calculoPlanoAssinatura);
        if (calculoPlanoAssinatura.getId() != null) {
            throw new BadRequestAlertException("A new calculoPlanoAssinatura cannot already have an ID", ENTITY_NAME, "idexists");
        }
        calculoPlanoAssinatura = calculoPlanoAssinaturaService.save(calculoPlanoAssinatura);
        return ResponseEntity.created(new URI("/api/calculo-plano-assinaturas/" + calculoPlanoAssinatura.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, calculoPlanoAssinatura.getId().toString()))
            .body(calculoPlanoAssinatura);
    }

    /**
     * {@code PUT  /calculo-plano-assinaturas/:id} : Updates an existing calculoPlanoAssinatura.
     *
     * @param id the id of the calculoPlanoAssinatura to save.
     * @param calculoPlanoAssinatura the calculoPlanoAssinatura to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calculoPlanoAssinatura,
     * or with status {@code 400 (Bad Request)} if the calculoPlanoAssinatura is not valid,
     * or with status {@code 500 (Internal Server Error)} if the calculoPlanoAssinatura couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CalculoPlanoAssinatura> updateCalculoPlanoAssinatura(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CalculoPlanoAssinatura calculoPlanoAssinatura
    ) throws URISyntaxException {
        log.debug("REST request to update CalculoPlanoAssinatura : {}, {}", id, calculoPlanoAssinatura);
        if (calculoPlanoAssinatura.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, calculoPlanoAssinatura.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!calculoPlanoAssinaturaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        calculoPlanoAssinatura = calculoPlanoAssinaturaService.update(calculoPlanoAssinatura);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, calculoPlanoAssinatura.getId().toString()))
            .body(calculoPlanoAssinatura);
    }

    /**
     * {@code PATCH  /calculo-plano-assinaturas/:id} : Partial updates given fields of an existing calculoPlanoAssinatura, field will ignore if it is null
     *
     * @param id the id of the calculoPlanoAssinatura to save.
     * @param calculoPlanoAssinatura the calculoPlanoAssinatura to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calculoPlanoAssinatura,
     * or with status {@code 400 (Bad Request)} if the calculoPlanoAssinatura is not valid,
     * or with status {@code 404 (Not Found)} if the calculoPlanoAssinatura is not found,
     * or with status {@code 500 (Internal Server Error)} if the calculoPlanoAssinatura couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CalculoPlanoAssinatura> partialUpdateCalculoPlanoAssinatura(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CalculoPlanoAssinatura calculoPlanoAssinatura
    ) throws URISyntaxException {
        log.debug("REST request to partial update CalculoPlanoAssinatura partially : {}, {}", id, calculoPlanoAssinatura);
        if (calculoPlanoAssinatura.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, calculoPlanoAssinatura.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!calculoPlanoAssinaturaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CalculoPlanoAssinatura> result = calculoPlanoAssinaturaService.partialUpdate(calculoPlanoAssinatura);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, calculoPlanoAssinatura.getId().toString())
        );
    }

    /**
     * {@code GET  /calculo-plano-assinaturas} : get all the calculoPlanoAssinaturas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of calculoPlanoAssinaturas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CalculoPlanoAssinatura>> getAllCalculoPlanoAssinaturas(
        CalculoPlanoAssinaturaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CalculoPlanoAssinaturas by criteria: {}", criteria);

        Page<CalculoPlanoAssinatura> page = calculoPlanoAssinaturaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /calculo-plano-assinaturas/count} : count all the calculoPlanoAssinaturas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countCalculoPlanoAssinaturas(CalculoPlanoAssinaturaCriteria criteria) {
        log.debug("REST request to count CalculoPlanoAssinaturas by criteria: {}", criteria);
        return ResponseEntity.ok().body(calculoPlanoAssinaturaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /calculo-plano-assinaturas/:id} : get the "id" calculoPlanoAssinatura.
     *
     * @param id the id of the calculoPlanoAssinatura to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the calculoPlanoAssinatura, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CalculoPlanoAssinatura> getCalculoPlanoAssinatura(@PathVariable("id") Long id) {
        log.debug("REST request to get CalculoPlanoAssinatura : {}", id);
        Optional<CalculoPlanoAssinatura> calculoPlanoAssinatura = calculoPlanoAssinaturaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(calculoPlanoAssinatura);
    }

    /**
     * {@code DELETE  /calculo-plano-assinaturas/:id} : delete the "id" calculoPlanoAssinatura.
     *
     * @param id the id of the calculoPlanoAssinatura to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalculoPlanoAssinatura(@PathVariable("id") Long id) {
        log.debug("REST request to delete CalculoPlanoAssinatura : {}", id);
        calculoPlanoAssinaturaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
