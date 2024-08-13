package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.DescontoPlanoContabil;
import com.dobemcontabilidade.repository.DescontoPlanoContabilRepository;
import com.dobemcontabilidade.service.DescontoPlanoContabilQueryService;
import com.dobemcontabilidade.service.DescontoPlanoContabilService;
import com.dobemcontabilidade.service.criteria.DescontoPlanoContabilCriteria;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.DescontoPlanoContabil}.
 */
@RestController
@RequestMapping("/api/desconto-plano-contabils")
public class DescontoPlanoContabilResource {

    private static final Logger log = LoggerFactory.getLogger(DescontoPlanoContabilResource.class);

    private static final String ENTITY_NAME = "descontoPlanoContabil";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DescontoPlanoContabilService descontoPlanoContabilService;

    private final DescontoPlanoContabilRepository descontoPlanoContabilRepository;

    private final DescontoPlanoContabilQueryService descontoPlanoContabilQueryService;

    public DescontoPlanoContabilResource(
        DescontoPlanoContabilService descontoPlanoContabilService,
        DescontoPlanoContabilRepository descontoPlanoContabilRepository,
        DescontoPlanoContabilQueryService descontoPlanoContabilQueryService
    ) {
        this.descontoPlanoContabilService = descontoPlanoContabilService;
        this.descontoPlanoContabilRepository = descontoPlanoContabilRepository;
        this.descontoPlanoContabilQueryService = descontoPlanoContabilQueryService;
    }

    /**
     * {@code POST  /desconto-plano-contabils} : Create a new descontoPlanoContabil.
     *
     * @param descontoPlanoContabil the descontoPlanoContabil to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new descontoPlanoContabil, or with status {@code 400 (Bad Request)} if the descontoPlanoContabil has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DescontoPlanoContabil> createDescontoPlanoContabil(
        @Valid @RequestBody DescontoPlanoContabil descontoPlanoContabil
    ) throws URISyntaxException {
        log.debug("REST request to save DescontoPlanoContabil : {}", descontoPlanoContabil);
        if (descontoPlanoContabil.getId() != null) {
            throw new BadRequestAlertException("A new descontoPlanoContabil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        descontoPlanoContabil = descontoPlanoContabilService.save(descontoPlanoContabil);
        return ResponseEntity.created(new URI("/api/desconto-plano-contabils/" + descontoPlanoContabil.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, descontoPlanoContabil.getId().toString()))
            .body(descontoPlanoContabil);
    }

    /**
     * {@code PUT  /desconto-plano-contabils/:id} : Updates an existing descontoPlanoContabil.
     *
     * @param id the id of the descontoPlanoContabil to save.
     * @param descontoPlanoContabil the descontoPlanoContabil to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated descontoPlanoContabil,
     * or with status {@code 400 (Bad Request)} if the descontoPlanoContabil is not valid,
     * or with status {@code 500 (Internal Server Error)} if the descontoPlanoContabil couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DescontoPlanoContabil> updateDescontoPlanoContabil(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DescontoPlanoContabil descontoPlanoContabil
    ) throws URISyntaxException {
        log.debug("REST request to update DescontoPlanoContabil : {}, {}", id, descontoPlanoContabil);
        if (descontoPlanoContabil.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, descontoPlanoContabil.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!descontoPlanoContabilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        descontoPlanoContabil = descontoPlanoContabilService.update(descontoPlanoContabil);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, descontoPlanoContabil.getId().toString()))
            .body(descontoPlanoContabil);
    }

    /**
     * {@code PATCH  /desconto-plano-contabils/:id} : Partial updates given fields of an existing descontoPlanoContabil, field will ignore if it is null
     *
     * @param id the id of the descontoPlanoContabil to save.
     * @param descontoPlanoContabil the descontoPlanoContabil to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated descontoPlanoContabil,
     * or with status {@code 400 (Bad Request)} if the descontoPlanoContabil is not valid,
     * or with status {@code 404 (Not Found)} if the descontoPlanoContabil is not found,
     * or with status {@code 500 (Internal Server Error)} if the descontoPlanoContabil couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DescontoPlanoContabil> partialUpdateDescontoPlanoContabil(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DescontoPlanoContabil descontoPlanoContabil
    ) throws URISyntaxException {
        log.debug("REST request to partial update DescontoPlanoContabil partially : {}, {}", id, descontoPlanoContabil);
        if (descontoPlanoContabil.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, descontoPlanoContabil.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!descontoPlanoContabilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DescontoPlanoContabil> result = descontoPlanoContabilService.partialUpdate(descontoPlanoContabil);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, descontoPlanoContabil.getId().toString())
        );
    }

    /**
     * {@code GET  /desconto-plano-contabils} : get all the descontoPlanoContabils.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of descontoPlanoContabils in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DescontoPlanoContabil>> getAllDescontoPlanoContabils(
        DescontoPlanoContabilCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DescontoPlanoContabils by criteria: {}", criteria);

        Page<DescontoPlanoContabil> page = descontoPlanoContabilQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /desconto-plano-contabils/count} : count all the descontoPlanoContabils.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countDescontoPlanoContabils(DescontoPlanoContabilCriteria criteria) {
        log.debug("REST request to count DescontoPlanoContabils by criteria: {}", criteria);
        return ResponseEntity.ok().body(descontoPlanoContabilQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /desconto-plano-contabils/:id} : get the "id" descontoPlanoContabil.
     *
     * @param id the id of the descontoPlanoContabil to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the descontoPlanoContabil, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DescontoPlanoContabil> getDescontoPlanoContabil(@PathVariable("id") Long id) {
        log.debug("REST request to get DescontoPlanoContabil : {}", id);
        Optional<DescontoPlanoContabil> descontoPlanoContabil = descontoPlanoContabilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(descontoPlanoContabil);
    }

    /**
     * {@code DELETE  /desconto-plano-contabils/:id} : delete the "id" descontoPlanoContabil.
     *
     * @param id the id of the descontoPlanoContabil to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDescontoPlanoContabil(@PathVariable("id") Long id) {
        log.debug("REST request to delete DescontoPlanoContabil : {}", id);
        descontoPlanoContabilService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
