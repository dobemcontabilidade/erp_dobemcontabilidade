package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.DescontoPlanoContaAzulRepository;
import com.dobemcontabilidade.service.DescontoPlanoContaAzulQueryService;
import com.dobemcontabilidade.service.DescontoPlanoContaAzulService;
import com.dobemcontabilidade.service.criteria.DescontoPlanoContaAzulCriteria;
import com.dobemcontabilidade.service.dto.DescontoPlanoContaAzulDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.DescontoPlanoContaAzul}.
 */
@RestController
@RequestMapping("/api/desconto-plano-conta-azuls")
public class DescontoPlanoContaAzulResource {

    private static final Logger log = LoggerFactory.getLogger(DescontoPlanoContaAzulResource.class);

    private static final String ENTITY_NAME = "descontoPlanoContaAzul";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DescontoPlanoContaAzulService descontoPlanoContaAzulService;

    private final DescontoPlanoContaAzulRepository descontoPlanoContaAzulRepository;

    private final DescontoPlanoContaAzulQueryService descontoPlanoContaAzulQueryService;

    public DescontoPlanoContaAzulResource(
        DescontoPlanoContaAzulService descontoPlanoContaAzulService,
        DescontoPlanoContaAzulRepository descontoPlanoContaAzulRepository,
        DescontoPlanoContaAzulQueryService descontoPlanoContaAzulQueryService
    ) {
        this.descontoPlanoContaAzulService = descontoPlanoContaAzulService;
        this.descontoPlanoContaAzulRepository = descontoPlanoContaAzulRepository;
        this.descontoPlanoContaAzulQueryService = descontoPlanoContaAzulQueryService;
    }

    /**
     * {@code POST  /desconto-plano-conta-azuls} : Create a new descontoPlanoContaAzul.
     *
     * @param descontoPlanoContaAzulDTO the descontoPlanoContaAzulDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new descontoPlanoContaAzulDTO, or with status {@code 400 (Bad Request)} if the descontoPlanoContaAzul has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DescontoPlanoContaAzulDTO> createDescontoPlanoContaAzul(
        @Valid @RequestBody DescontoPlanoContaAzulDTO descontoPlanoContaAzulDTO
    ) throws URISyntaxException {
        log.debug("REST request to save DescontoPlanoContaAzul : {}", descontoPlanoContaAzulDTO);
        if (descontoPlanoContaAzulDTO.getId() != null) {
            throw new BadRequestAlertException("A new descontoPlanoContaAzul cannot already have an ID", ENTITY_NAME, "idexists");
        }
        descontoPlanoContaAzulDTO = descontoPlanoContaAzulService.save(descontoPlanoContaAzulDTO);
        return ResponseEntity.created(new URI("/api/desconto-plano-conta-azuls/" + descontoPlanoContaAzulDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, descontoPlanoContaAzulDTO.getId().toString()))
            .body(descontoPlanoContaAzulDTO);
    }

    /**
     * {@code PUT  /desconto-plano-conta-azuls/:id} : Updates an existing descontoPlanoContaAzul.
     *
     * @param id the id of the descontoPlanoContaAzulDTO to save.
     * @param descontoPlanoContaAzulDTO the descontoPlanoContaAzulDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated descontoPlanoContaAzulDTO,
     * or with status {@code 400 (Bad Request)} if the descontoPlanoContaAzulDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the descontoPlanoContaAzulDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DescontoPlanoContaAzulDTO> updateDescontoPlanoContaAzul(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DescontoPlanoContaAzulDTO descontoPlanoContaAzulDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DescontoPlanoContaAzul : {}, {}", id, descontoPlanoContaAzulDTO);
        if (descontoPlanoContaAzulDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, descontoPlanoContaAzulDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!descontoPlanoContaAzulRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        descontoPlanoContaAzulDTO = descontoPlanoContaAzulService.update(descontoPlanoContaAzulDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, descontoPlanoContaAzulDTO.getId().toString()))
            .body(descontoPlanoContaAzulDTO);
    }

    /**
     * {@code PATCH  /desconto-plano-conta-azuls/:id} : Partial updates given fields of an existing descontoPlanoContaAzul, field will ignore if it is null
     *
     * @param id the id of the descontoPlanoContaAzulDTO to save.
     * @param descontoPlanoContaAzulDTO the descontoPlanoContaAzulDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated descontoPlanoContaAzulDTO,
     * or with status {@code 400 (Bad Request)} if the descontoPlanoContaAzulDTO is not valid,
     * or with status {@code 404 (Not Found)} if the descontoPlanoContaAzulDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the descontoPlanoContaAzulDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DescontoPlanoContaAzulDTO> partialUpdateDescontoPlanoContaAzul(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DescontoPlanoContaAzulDTO descontoPlanoContaAzulDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DescontoPlanoContaAzul partially : {}, {}", id, descontoPlanoContaAzulDTO);
        if (descontoPlanoContaAzulDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, descontoPlanoContaAzulDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!descontoPlanoContaAzulRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DescontoPlanoContaAzulDTO> result = descontoPlanoContaAzulService.partialUpdate(descontoPlanoContaAzulDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, descontoPlanoContaAzulDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /desconto-plano-conta-azuls} : get all the descontoPlanoContaAzuls.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of descontoPlanoContaAzuls in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DescontoPlanoContaAzulDTO>> getAllDescontoPlanoContaAzuls(
        DescontoPlanoContaAzulCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DescontoPlanoContaAzuls by criteria: {}", criteria);

        Page<DescontoPlanoContaAzulDTO> page = descontoPlanoContaAzulQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /desconto-plano-conta-azuls/count} : count all the descontoPlanoContaAzuls.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countDescontoPlanoContaAzuls(DescontoPlanoContaAzulCriteria criteria) {
        log.debug("REST request to count DescontoPlanoContaAzuls by criteria: {}", criteria);
        return ResponseEntity.ok().body(descontoPlanoContaAzulQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /desconto-plano-conta-azuls/:id} : get the "id" descontoPlanoContaAzul.
     *
     * @param id the id of the descontoPlanoContaAzulDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the descontoPlanoContaAzulDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DescontoPlanoContaAzulDTO> getDescontoPlanoContaAzul(@PathVariable("id") Long id) {
        log.debug("REST request to get DescontoPlanoContaAzul : {}", id);
        Optional<DescontoPlanoContaAzulDTO> descontoPlanoContaAzulDTO = descontoPlanoContaAzulService.findOne(id);
        return ResponseUtil.wrapOrNotFound(descontoPlanoContaAzulDTO);
    }

    /**
     * {@code DELETE  /desconto-plano-conta-azuls/:id} : delete the "id" descontoPlanoContaAzul.
     *
     * @param id the id of the descontoPlanoContaAzulDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDescontoPlanoContaAzul(@PathVariable("id") Long id) {
        log.debug("REST request to delete DescontoPlanoContaAzul : {}", id);
        descontoPlanoContaAzulService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
