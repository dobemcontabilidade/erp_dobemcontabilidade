package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.FrequenciaRepository;
import com.dobemcontabilidade.service.FrequenciaQueryService;
import com.dobemcontabilidade.service.FrequenciaService;
import com.dobemcontabilidade.service.criteria.FrequenciaCriteria;
import com.dobemcontabilidade.service.dto.FrequenciaDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Frequencia}.
 */
@RestController
@RequestMapping("/api/frequencias")
public class FrequenciaResource {

    private static final Logger log = LoggerFactory.getLogger(FrequenciaResource.class);

    private static final String ENTITY_NAME = "frequencia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FrequenciaService frequenciaService;

    private final FrequenciaRepository frequenciaRepository;

    private final FrequenciaQueryService frequenciaQueryService;

    public FrequenciaResource(
        FrequenciaService frequenciaService,
        FrequenciaRepository frequenciaRepository,
        FrequenciaQueryService frequenciaQueryService
    ) {
        this.frequenciaService = frequenciaService;
        this.frequenciaRepository = frequenciaRepository;
        this.frequenciaQueryService = frequenciaQueryService;
    }

    /**
     * {@code POST  /frequencias} : Create a new frequencia.
     *
     * @param frequenciaDTO the frequenciaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new frequenciaDTO, or with status {@code 400 (Bad Request)} if the frequencia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FrequenciaDTO> createFrequencia(@RequestBody FrequenciaDTO frequenciaDTO) throws URISyntaxException {
        log.debug("REST request to save Frequencia : {}", frequenciaDTO);
        if (frequenciaDTO.getId() != null) {
            throw new BadRequestAlertException("A new frequencia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        frequenciaDTO = frequenciaService.save(frequenciaDTO);
        return ResponseEntity.created(new URI("/api/frequencias/" + frequenciaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, frequenciaDTO.getId().toString()))
            .body(frequenciaDTO);
    }

    /**
     * {@code PUT  /frequencias/:id} : Updates an existing frequencia.
     *
     * @param id the id of the frequenciaDTO to save.
     * @param frequenciaDTO the frequenciaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated frequenciaDTO,
     * or with status {@code 400 (Bad Request)} if the frequenciaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the frequenciaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FrequenciaDTO> updateFrequencia(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FrequenciaDTO frequenciaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Frequencia : {}, {}", id, frequenciaDTO);
        if (frequenciaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, frequenciaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!frequenciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        frequenciaDTO = frequenciaService.update(frequenciaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, frequenciaDTO.getId().toString()))
            .body(frequenciaDTO);
    }

    /**
     * {@code PATCH  /frequencias/:id} : Partial updates given fields of an existing frequencia, field will ignore if it is null
     *
     * @param id the id of the frequenciaDTO to save.
     * @param frequenciaDTO the frequenciaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated frequenciaDTO,
     * or with status {@code 400 (Bad Request)} if the frequenciaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the frequenciaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the frequenciaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FrequenciaDTO> partialUpdateFrequencia(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FrequenciaDTO frequenciaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Frequencia partially : {}, {}", id, frequenciaDTO);
        if (frequenciaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, frequenciaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!frequenciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FrequenciaDTO> result = frequenciaService.partialUpdate(frequenciaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, frequenciaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /frequencias} : get all the frequencias.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of frequencias in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FrequenciaDTO>> getAllFrequencias(
        FrequenciaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Frequencias by criteria: {}", criteria);

        Page<FrequenciaDTO> page = frequenciaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /frequencias/count} : count all the frequencias.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countFrequencias(FrequenciaCriteria criteria) {
        log.debug("REST request to count Frequencias by criteria: {}", criteria);
        return ResponseEntity.ok().body(frequenciaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /frequencias/:id} : get the "id" frequencia.
     *
     * @param id the id of the frequenciaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the frequenciaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FrequenciaDTO> getFrequencia(@PathVariable("id") Long id) {
        log.debug("REST request to get Frequencia : {}", id);
        Optional<FrequenciaDTO> frequenciaDTO = frequenciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(frequenciaDTO);
    }

    /**
     * {@code DELETE  /frequencias/:id} : delete the "id" frequencia.
     *
     * @param id the id of the frequenciaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFrequencia(@PathVariable("id") Long id) {
        log.debug("REST request to delete Frequencia : {}", id);
        frequenciaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
