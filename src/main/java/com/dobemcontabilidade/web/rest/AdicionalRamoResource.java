package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.AdicionalRamoRepository;
import com.dobemcontabilidade.service.AdicionalRamoQueryService;
import com.dobemcontabilidade.service.AdicionalRamoService;
import com.dobemcontabilidade.service.criteria.AdicionalRamoCriteria;
import com.dobemcontabilidade.service.dto.AdicionalRamoDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AdicionalRamo}.
 */
@RestController
@RequestMapping("/api/adicional-ramos")
public class AdicionalRamoResource {

    private static final Logger log = LoggerFactory.getLogger(AdicionalRamoResource.class);

    private static final String ENTITY_NAME = "adicionalRamo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdicionalRamoService adicionalRamoService;

    private final AdicionalRamoRepository adicionalRamoRepository;

    private final AdicionalRamoQueryService adicionalRamoQueryService;

    public AdicionalRamoResource(
        AdicionalRamoService adicionalRamoService,
        AdicionalRamoRepository adicionalRamoRepository,
        AdicionalRamoQueryService adicionalRamoQueryService
    ) {
        this.adicionalRamoService = adicionalRamoService;
        this.adicionalRamoRepository = adicionalRamoRepository;
        this.adicionalRamoQueryService = adicionalRamoQueryService;
    }

    /**
     * {@code POST  /adicional-ramos} : Create a new adicionalRamo.
     *
     * @param adicionalRamoDTO the adicionalRamoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adicionalRamoDTO, or with status {@code 400 (Bad Request)} if the adicionalRamo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AdicionalRamoDTO> createAdicionalRamo(@Valid @RequestBody AdicionalRamoDTO adicionalRamoDTO)
        throws URISyntaxException {
        log.debug("REST request to save AdicionalRamo : {}", adicionalRamoDTO);
        if (adicionalRamoDTO.getId() != null) {
            throw new BadRequestAlertException("A new adicionalRamo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        adicionalRamoDTO = adicionalRamoService.save(adicionalRamoDTO);
        return ResponseEntity.created(new URI("/api/adicional-ramos/" + adicionalRamoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, adicionalRamoDTO.getId().toString()))
            .body(adicionalRamoDTO);
    }

    /**
     * {@code PUT  /adicional-ramos/:id} : Updates an existing adicionalRamo.
     *
     * @param id the id of the adicionalRamoDTO to save.
     * @param adicionalRamoDTO the adicionalRamoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adicionalRamoDTO,
     * or with status {@code 400 (Bad Request)} if the adicionalRamoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adicionalRamoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AdicionalRamoDTO> updateAdicionalRamo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdicionalRamoDTO adicionalRamoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AdicionalRamo : {}, {}", id, adicionalRamoDTO);
        if (adicionalRamoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adicionalRamoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adicionalRamoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        adicionalRamoDTO = adicionalRamoService.update(adicionalRamoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adicionalRamoDTO.getId().toString()))
            .body(adicionalRamoDTO);
    }

    /**
     * {@code PATCH  /adicional-ramos/:id} : Partial updates given fields of an existing adicionalRamo, field will ignore if it is null
     *
     * @param id the id of the adicionalRamoDTO to save.
     * @param adicionalRamoDTO the adicionalRamoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adicionalRamoDTO,
     * or with status {@code 400 (Bad Request)} if the adicionalRamoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the adicionalRamoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the adicionalRamoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdicionalRamoDTO> partialUpdateAdicionalRamo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdicionalRamoDTO adicionalRamoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdicionalRamo partially : {}, {}", id, adicionalRamoDTO);
        if (adicionalRamoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adicionalRamoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adicionalRamoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdicionalRamoDTO> result = adicionalRamoService.partialUpdate(adicionalRamoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adicionalRamoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /adicional-ramos} : get all the adicionalRamos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adicionalRamos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AdicionalRamoDTO>> getAllAdicionalRamos(
        AdicionalRamoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get AdicionalRamos by criteria: {}", criteria);

        Page<AdicionalRamoDTO> page = adicionalRamoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /adicional-ramos/count} : count all the adicionalRamos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAdicionalRamos(AdicionalRamoCriteria criteria) {
        log.debug("REST request to count AdicionalRamos by criteria: {}", criteria);
        return ResponseEntity.ok().body(adicionalRamoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /adicional-ramos/:id} : get the "id" adicionalRamo.
     *
     * @param id the id of the adicionalRamoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adicionalRamoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdicionalRamoDTO> getAdicionalRamo(@PathVariable("id") Long id) {
        log.debug("REST request to get AdicionalRamo : {}", id);
        Optional<AdicionalRamoDTO> adicionalRamoDTO = adicionalRamoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adicionalRamoDTO);
    }

    /**
     * {@code DELETE  /adicional-ramos/:id} : delete the "id" adicionalRamo.
     *
     * @param id the id of the adicionalRamoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdicionalRamo(@PathVariable("id") Long id) {
        log.debug("REST request to delete AdicionalRamo : {}", id);
        adicionalRamoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
