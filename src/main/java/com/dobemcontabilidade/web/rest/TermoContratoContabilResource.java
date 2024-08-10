package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.TermoContratoContabilRepository;
import com.dobemcontabilidade.service.TermoContratoContabilQueryService;
import com.dobemcontabilidade.service.TermoContratoContabilService;
import com.dobemcontabilidade.service.criteria.TermoContratoContabilCriteria;
import com.dobemcontabilidade.service.dto.TermoContratoContabilDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.TermoContratoContabil}.
 */
@RestController
@RequestMapping("/api/termo-contrato-contabils")
public class TermoContratoContabilResource {

    private static final Logger log = LoggerFactory.getLogger(TermoContratoContabilResource.class);

    private static final String ENTITY_NAME = "termoContratoContabil";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TermoContratoContabilService termoContratoContabilService;

    private final TermoContratoContabilRepository termoContratoContabilRepository;

    private final TermoContratoContabilQueryService termoContratoContabilQueryService;

    public TermoContratoContabilResource(
        TermoContratoContabilService termoContratoContabilService,
        TermoContratoContabilRepository termoContratoContabilRepository,
        TermoContratoContabilQueryService termoContratoContabilQueryService
    ) {
        this.termoContratoContabilService = termoContratoContabilService;
        this.termoContratoContabilRepository = termoContratoContabilRepository;
        this.termoContratoContabilQueryService = termoContratoContabilQueryService;
    }

    /**
     * {@code POST  /termo-contrato-contabils} : Create a new termoContratoContabil.
     *
     * @param termoContratoContabilDTO the termoContratoContabilDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new termoContratoContabilDTO, or with status {@code 400 (Bad Request)} if the termoContratoContabil has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TermoContratoContabilDTO> createTermoContratoContabil(
        @Valid @RequestBody TermoContratoContabilDTO termoContratoContabilDTO
    ) throws URISyntaxException {
        log.debug("REST request to save TermoContratoContabil : {}", termoContratoContabilDTO);
        if (termoContratoContabilDTO.getId() != null) {
            throw new BadRequestAlertException("A new termoContratoContabil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        termoContratoContabilDTO = termoContratoContabilService.save(termoContratoContabilDTO);
        return ResponseEntity.created(new URI("/api/termo-contrato-contabils/" + termoContratoContabilDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, termoContratoContabilDTO.getId().toString()))
            .body(termoContratoContabilDTO);
    }

    /**
     * {@code PUT  /termo-contrato-contabils/:id} : Updates an existing termoContratoContabil.
     *
     * @param id the id of the termoContratoContabilDTO to save.
     * @param termoContratoContabilDTO the termoContratoContabilDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated termoContratoContabilDTO,
     * or with status {@code 400 (Bad Request)} if the termoContratoContabilDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the termoContratoContabilDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TermoContratoContabilDTO> updateTermoContratoContabil(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TermoContratoContabilDTO termoContratoContabilDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TermoContratoContabil : {}, {}", id, termoContratoContabilDTO);
        if (termoContratoContabilDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, termoContratoContabilDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!termoContratoContabilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        termoContratoContabilDTO = termoContratoContabilService.update(termoContratoContabilDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, termoContratoContabilDTO.getId().toString()))
            .body(termoContratoContabilDTO);
    }

    /**
     * {@code PATCH  /termo-contrato-contabils/:id} : Partial updates given fields of an existing termoContratoContabil, field will ignore if it is null
     *
     * @param id the id of the termoContratoContabilDTO to save.
     * @param termoContratoContabilDTO the termoContratoContabilDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated termoContratoContabilDTO,
     * or with status {@code 400 (Bad Request)} if the termoContratoContabilDTO is not valid,
     * or with status {@code 404 (Not Found)} if the termoContratoContabilDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the termoContratoContabilDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TermoContratoContabilDTO> partialUpdateTermoContratoContabil(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TermoContratoContabilDTO termoContratoContabilDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TermoContratoContabil partially : {}, {}", id, termoContratoContabilDTO);
        if (termoContratoContabilDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, termoContratoContabilDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!termoContratoContabilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TermoContratoContabilDTO> result = termoContratoContabilService.partialUpdate(termoContratoContabilDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, termoContratoContabilDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /termo-contrato-contabils} : get all the termoContratoContabils.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of termoContratoContabils in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TermoContratoContabilDTO>> getAllTermoContratoContabils(
        TermoContratoContabilCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TermoContratoContabils by criteria: {}", criteria);

        Page<TermoContratoContabilDTO> page = termoContratoContabilQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /termo-contrato-contabils/count} : count all the termoContratoContabils.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countTermoContratoContabils(TermoContratoContabilCriteria criteria) {
        log.debug("REST request to count TermoContratoContabils by criteria: {}", criteria);
        return ResponseEntity.ok().body(termoContratoContabilQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /termo-contrato-contabils/:id} : get the "id" termoContratoContabil.
     *
     * @param id the id of the termoContratoContabilDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the termoContratoContabilDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TermoContratoContabilDTO> getTermoContratoContabil(@PathVariable("id") Long id) {
        log.debug("REST request to get TermoContratoContabil : {}", id);
        Optional<TermoContratoContabilDTO> termoContratoContabilDTO = termoContratoContabilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(termoContratoContabilDTO);
    }

    /**
     * {@code DELETE  /termo-contrato-contabils/:id} : delete the "id" termoContratoContabil.
     *
     * @param id the id of the termoContratoContabilDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTermoContratoContabil(@PathVariable("id") Long id) {
        log.debug("REST request to delete TermoContratoContabil : {}", id);
        termoContratoContabilService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
