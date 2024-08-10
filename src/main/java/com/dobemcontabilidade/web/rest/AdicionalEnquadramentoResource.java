package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.AdicionalEnquadramentoRepository;
import com.dobemcontabilidade.service.AdicionalEnquadramentoQueryService;
import com.dobemcontabilidade.service.AdicionalEnquadramentoService;
import com.dobemcontabilidade.service.criteria.AdicionalEnquadramentoCriteria;
import com.dobemcontabilidade.service.dto.AdicionalEnquadramentoDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AdicionalEnquadramento}.
 */
@RestController
@RequestMapping("/api/adicional-enquadramentos")
public class AdicionalEnquadramentoResource {

    private static final Logger log = LoggerFactory.getLogger(AdicionalEnquadramentoResource.class);

    private static final String ENTITY_NAME = "adicionalEnquadramento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdicionalEnquadramentoService adicionalEnquadramentoService;

    private final AdicionalEnquadramentoRepository adicionalEnquadramentoRepository;

    private final AdicionalEnquadramentoQueryService adicionalEnquadramentoQueryService;

    public AdicionalEnquadramentoResource(
        AdicionalEnquadramentoService adicionalEnquadramentoService,
        AdicionalEnquadramentoRepository adicionalEnquadramentoRepository,
        AdicionalEnquadramentoQueryService adicionalEnquadramentoQueryService
    ) {
        this.adicionalEnquadramentoService = adicionalEnquadramentoService;
        this.adicionalEnquadramentoRepository = adicionalEnquadramentoRepository;
        this.adicionalEnquadramentoQueryService = adicionalEnquadramentoQueryService;
    }

    /**
     * {@code POST  /adicional-enquadramentos} : Create a new adicionalEnquadramento.
     *
     * @param adicionalEnquadramentoDTO the adicionalEnquadramentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adicionalEnquadramentoDTO, or with status {@code 400 (Bad Request)} if the adicionalEnquadramento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AdicionalEnquadramentoDTO> createAdicionalEnquadramento(
        @Valid @RequestBody AdicionalEnquadramentoDTO adicionalEnquadramentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to save AdicionalEnquadramento : {}", adicionalEnquadramentoDTO);
        if (adicionalEnquadramentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new adicionalEnquadramento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        adicionalEnquadramentoDTO = adicionalEnquadramentoService.save(adicionalEnquadramentoDTO);
        return ResponseEntity.created(new URI("/api/adicional-enquadramentos/" + adicionalEnquadramentoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, adicionalEnquadramentoDTO.getId().toString()))
            .body(adicionalEnquadramentoDTO);
    }

    /**
     * {@code PUT  /adicional-enquadramentos/:id} : Updates an existing adicionalEnquadramento.
     *
     * @param id the id of the adicionalEnquadramentoDTO to save.
     * @param adicionalEnquadramentoDTO the adicionalEnquadramentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adicionalEnquadramentoDTO,
     * or with status {@code 400 (Bad Request)} if the adicionalEnquadramentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adicionalEnquadramentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AdicionalEnquadramentoDTO> updateAdicionalEnquadramento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdicionalEnquadramentoDTO adicionalEnquadramentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AdicionalEnquadramento : {}, {}", id, adicionalEnquadramentoDTO);
        if (adicionalEnquadramentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adicionalEnquadramentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adicionalEnquadramentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        adicionalEnquadramentoDTO = adicionalEnquadramentoService.update(adicionalEnquadramentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adicionalEnquadramentoDTO.getId().toString()))
            .body(adicionalEnquadramentoDTO);
    }

    /**
     * {@code PATCH  /adicional-enquadramentos/:id} : Partial updates given fields of an existing adicionalEnquadramento, field will ignore if it is null
     *
     * @param id the id of the adicionalEnquadramentoDTO to save.
     * @param adicionalEnquadramentoDTO the adicionalEnquadramentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adicionalEnquadramentoDTO,
     * or with status {@code 400 (Bad Request)} if the adicionalEnquadramentoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the adicionalEnquadramentoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the adicionalEnquadramentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdicionalEnquadramentoDTO> partialUpdateAdicionalEnquadramento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdicionalEnquadramentoDTO adicionalEnquadramentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdicionalEnquadramento partially : {}, {}", id, adicionalEnquadramentoDTO);
        if (adicionalEnquadramentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adicionalEnquadramentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adicionalEnquadramentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdicionalEnquadramentoDTO> result = adicionalEnquadramentoService.partialUpdate(adicionalEnquadramentoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adicionalEnquadramentoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /adicional-enquadramentos} : get all the adicionalEnquadramentos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adicionalEnquadramentos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AdicionalEnquadramentoDTO>> getAllAdicionalEnquadramentos(
        AdicionalEnquadramentoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get AdicionalEnquadramentos by criteria: {}", criteria);

        Page<AdicionalEnquadramentoDTO> page = adicionalEnquadramentoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /adicional-enquadramentos/count} : count all the adicionalEnquadramentos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAdicionalEnquadramentos(AdicionalEnquadramentoCriteria criteria) {
        log.debug("REST request to count AdicionalEnquadramentos by criteria: {}", criteria);
        return ResponseEntity.ok().body(adicionalEnquadramentoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /adicional-enquadramentos/:id} : get the "id" adicionalEnquadramento.
     *
     * @param id the id of the adicionalEnquadramentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adicionalEnquadramentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdicionalEnquadramentoDTO> getAdicionalEnquadramento(@PathVariable("id") Long id) {
        log.debug("REST request to get AdicionalEnquadramento : {}", id);
        Optional<AdicionalEnquadramentoDTO> adicionalEnquadramentoDTO = adicionalEnquadramentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adicionalEnquadramentoDTO);
    }

    /**
     * {@code DELETE  /adicional-enquadramentos/:id} : delete the "id" adicionalEnquadramento.
     *
     * @param id the id of the adicionalEnquadramentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdicionalEnquadramento(@PathVariable("id") Long id) {
        log.debug("REST request to delete AdicionalEnquadramento : {}", id);
        adicionalEnquadramentoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
