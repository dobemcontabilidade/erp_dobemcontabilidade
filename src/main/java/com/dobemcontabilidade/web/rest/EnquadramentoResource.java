package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.EnquadramentoRepository;
import com.dobemcontabilidade.service.EnquadramentoQueryService;
import com.dobemcontabilidade.service.EnquadramentoService;
import com.dobemcontabilidade.service.criteria.EnquadramentoCriteria;
import com.dobemcontabilidade.service.dto.EnquadramentoDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Enquadramento}.
 */
@RestController
@RequestMapping("/api/enquadramentos")
public class EnquadramentoResource {

    private static final Logger log = LoggerFactory.getLogger(EnquadramentoResource.class);

    private static final String ENTITY_NAME = "enquadramento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnquadramentoService enquadramentoService;

    private final EnquadramentoRepository enquadramentoRepository;

    private final EnquadramentoQueryService enquadramentoQueryService;

    public EnquadramentoResource(
        EnquadramentoService enquadramentoService,
        EnquadramentoRepository enquadramentoRepository,
        EnquadramentoQueryService enquadramentoQueryService
    ) {
        this.enquadramentoService = enquadramentoService;
        this.enquadramentoRepository = enquadramentoRepository;
        this.enquadramentoQueryService = enquadramentoQueryService;
    }

    /**
     * {@code POST  /enquadramentos} : Create a new enquadramento.
     *
     * @param enquadramentoDTO the enquadramentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enquadramentoDTO, or with status {@code 400 (Bad Request)} if the enquadramento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EnquadramentoDTO> createEnquadramento(@RequestBody EnquadramentoDTO enquadramentoDTO) throws URISyntaxException {
        log.debug("REST request to save Enquadramento : {}", enquadramentoDTO);
        if (enquadramentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new enquadramento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        enquadramentoDTO = enquadramentoService.save(enquadramentoDTO);
        return ResponseEntity.created(new URI("/api/enquadramentos/" + enquadramentoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, enquadramentoDTO.getId().toString()))
            .body(enquadramentoDTO);
    }

    /**
     * {@code PUT  /enquadramentos/:id} : Updates an existing enquadramento.
     *
     * @param id the id of the enquadramentoDTO to save.
     * @param enquadramentoDTO the enquadramentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enquadramentoDTO,
     * or with status {@code 400 (Bad Request)} if the enquadramentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enquadramentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EnquadramentoDTO> updateEnquadramento(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EnquadramentoDTO enquadramentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Enquadramento : {}, {}", id, enquadramentoDTO);
        if (enquadramentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enquadramentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enquadramentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        enquadramentoDTO = enquadramentoService.update(enquadramentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enquadramentoDTO.getId().toString()))
            .body(enquadramentoDTO);
    }

    /**
     * {@code PATCH  /enquadramentos/:id} : Partial updates given fields of an existing enquadramento, field will ignore if it is null
     *
     * @param id the id of the enquadramentoDTO to save.
     * @param enquadramentoDTO the enquadramentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enquadramentoDTO,
     * or with status {@code 400 (Bad Request)} if the enquadramentoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the enquadramentoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the enquadramentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EnquadramentoDTO> partialUpdateEnquadramento(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EnquadramentoDTO enquadramentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Enquadramento partially : {}, {}", id, enquadramentoDTO);
        if (enquadramentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enquadramentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enquadramentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EnquadramentoDTO> result = enquadramentoService.partialUpdate(enquadramentoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enquadramentoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /enquadramentos} : get all the enquadramentos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enquadramentos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EnquadramentoDTO>> getAllEnquadramentos(
        EnquadramentoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Enquadramentos by criteria: {}", criteria);

        Page<EnquadramentoDTO> page = enquadramentoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /enquadramentos/count} : count all the enquadramentos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countEnquadramentos(EnquadramentoCriteria criteria) {
        log.debug("REST request to count Enquadramentos by criteria: {}", criteria);
        return ResponseEntity.ok().body(enquadramentoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /enquadramentos/:id} : get the "id" enquadramento.
     *
     * @param id the id of the enquadramentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enquadramentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EnquadramentoDTO> getEnquadramento(@PathVariable("id") Long id) {
        log.debug("REST request to get Enquadramento : {}", id);
        Optional<EnquadramentoDTO> enquadramentoDTO = enquadramentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enquadramentoDTO);
    }

    /**
     * {@code DELETE  /enquadramentos/:id} : delete the "id" enquadramento.
     *
     * @param id the id of the enquadramentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnquadramento(@PathVariable("id") Long id) {
        log.debug("REST request to delete Enquadramento : {}", id);
        enquadramentoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
