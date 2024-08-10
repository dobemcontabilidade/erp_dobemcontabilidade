package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.EsferaRepository;
import com.dobemcontabilidade.service.EsferaQueryService;
import com.dobemcontabilidade.service.EsferaService;
import com.dobemcontabilidade.service.criteria.EsferaCriteria;
import com.dobemcontabilidade.service.dto.EsferaDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Esfera}.
 */
@RestController
@RequestMapping("/api/esferas")
public class EsferaResource {

    private static final Logger log = LoggerFactory.getLogger(EsferaResource.class);

    private static final String ENTITY_NAME = "esfera";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EsferaService esferaService;

    private final EsferaRepository esferaRepository;

    private final EsferaQueryService esferaQueryService;

    public EsferaResource(EsferaService esferaService, EsferaRepository esferaRepository, EsferaQueryService esferaQueryService) {
        this.esferaService = esferaService;
        this.esferaRepository = esferaRepository;
        this.esferaQueryService = esferaQueryService;
    }

    /**
     * {@code POST  /esferas} : Create a new esfera.
     *
     * @param esferaDTO the esferaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new esferaDTO, or with status {@code 400 (Bad Request)} if the esfera has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EsferaDTO> createEsfera(@RequestBody EsferaDTO esferaDTO) throws URISyntaxException {
        log.debug("REST request to save Esfera : {}", esferaDTO);
        if (esferaDTO.getId() != null) {
            throw new BadRequestAlertException("A new esfera cannot already have an ID", ENTITY_NAME, "idexists");
        }
        esferaDTO = esferaService.save(esferaDTO);
        return ResponseEntity.created(new URI("/api/esferas/" + esferaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, esferaDTO.getId().toString()))
            .body(esferaDTO);
    }

    /**
     * {@code PUT  /esferas/:id} : Updates an existing esfera.
     *
     * @param id the id of the esferaDTO to save.
     * @param esferaDTO the esferaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated esferaDTO,
     * or with status {@code 400 (Bad Request)} if the esferaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the esferaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EsferaDTO> updateEsfera(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EsferaDTO esferaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Esfera : {}, {}", id, esferaDTO);
        if (esferaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, esferaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!esferaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        esferaDTO = esferaService.update(esferaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, esferaDTO.getId().toString()))
            .body(esferaDTO);
    }

    /**
     * {@code PATCH  /esferas/:id} : Partial updates given fields of an existing esfera, field will ignore if it is null
     *
     * @param id the id of the esferaDTO to save.
     * @param esferaDTO the esferaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated esferaDTO,
     * or with status {@code 400 (Bad Request)} if the esferaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the esferaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the esferaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EsferaDTO> partialUpdateEsfera(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EsferaDTO esferaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Esfera partially : {}, {}", id, esferaDTO);
        if (esferaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, esferaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!esferaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EsferaDTO> result = esferaService.partialUpdate(esferaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, esferaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /esferas} : get all the esferas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of esferas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EsferaDTO>> getAllEsferas(
        EsferaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Esferas by criteria: {}", criteria);

        Page<EsferaDTO> page = esferaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /esferas/count} : count all the esferas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countEsferas(EsferaCriteria criteria) {
        log.debug("REST request to count Esferas by criteria: {}", criteria);
        return ResponseEntity.ok().body(esferaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /esferas/:id} : get the "id" esfera.
     *
     * @param id the id of the esferaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the esferaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EsferaDTO> getEsfera(@PathVariable("id") Long id) {
        log.debug("REST request to get Esfera : {}", id);
        Optional<EsferaDTO> esferaDTO = esferaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(esferaDTO);
    }

    /**
     * {@code DELETE  /esferas/:id} : delete the "id" esfera.
     *
     * @param id the id of the esferaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEsfera(@PathVariable("id") Long id) {
        log.debug("REST request to delete Esfera : {}", id);
        esferaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
