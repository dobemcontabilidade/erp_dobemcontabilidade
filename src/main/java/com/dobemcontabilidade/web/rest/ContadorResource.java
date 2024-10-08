package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.ContadorRepository;
import com.dobemcontabilidade.service.ContadorQueryService;
import com.dobemcontabilidade.service.ContadorService;
import com.dobemcontabilidade.service.criteria.ContadorCriteria;
import com.dobemcontabilidade.service.dto.ContadorDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Contador}.
 */
@RestController
@RequestMapping("/api/contadors")
public class ContadorResource {

    private static final Logger log = LoggerFactory.getLogger(ContadorResource.class);

    private static final String ENTITY_NAME = "contador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContadorService contadorService;

    private final ContadorRepository contadorRepository;

    private final ContadorQueryService contadorQueryService;

    public ContadorResource(
        ContadorService contadorService,
        ContadorRepository contadorRepository,
        ContadorQueryService contadorQueryService
    ) {
        this.contadorService = contadorService;
        this.contadorRepository = contadorRepository;
        this.contadorQueryService = contadorQueryService;
    }

    /**
     * {@code POST  /contadors} : Create a new contador.
     *
     * @param contadorDTO the contadorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contadorDTO, or with status {@code 400 (Bad Request)} if the contador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ContadorDTO> createContador(@Valid @RequestBody ContadorDTO contadorDTO) throws URISyntaxException {
        log.debug("REST request to save Contador : {}", contadorDTO);
        if (contadorDTO.getId() != null) {
            throw new BadRequestAlertException("A new contador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        contadorDTO = contadorService.save(contadorDTO);
        return ResponseEntity.created(new URI("/api/contadors/" + contadorDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, contadorDTO.getId().toString()))
            .body(contadorDTO);
    }

    /**
     * {@code PUT  /contadors/:id} : Updates an existing contador.
     *
     * @param id the id of the contadorDTO to save.
     * @param contadorDTO the contadorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contadorDTO,
     * or with status {@code 400 (Bad Request)} if the contadorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contadorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ContadorDTO> updateContador(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ContadorDTO contadorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Contador : {}, {}", id, contadorDTO);
        if (contadorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contadorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        contadorDTO = contadorService.update(contadorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contadorDTO.getId().toString()))
            .body(contadorDTO);
    }

    /**
     * {@code PATCH  /contadors/:id} : Partial updates given fields of an existing contador, field will ignore if it is null
     *
     * @param id the id of the contadorDTO to save.
     * @param contadorDTO the contadorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contadorDTO,
     * or with status {@code 400 (Bad Request)} if the contadorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the contadorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the contadorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContadorDTO> partialUpdateContador(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ContadorDTO contadorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Contador partially : {}, {}", id, contadorDTO);
        if (contadorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contadorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContadorDTO> result = contadorService.partialUpdate(contadorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contadorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /contadors} : get all the contadors.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contadors in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ContadorDTO>> getAllContadors(
        ContadorCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Contadors by criteria: {}", criteria);

        Page<ContadorDTO> page = contadorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contadors/count} : count all the contadors.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countContadors(ContadorCriteria criteria) {
        log.debug("REST request to count Contadors by criteria: {}", criteria);
        return ResponseEntity.ok().body(contadorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /contadors/:id} : get the "id" contador.
     *
     * @param id the id of the contadorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contadorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContadorDTO> getContador(@PathVariable("id") Long id) {
        log.debug("REST request to get Contador : {}", id);
        Optional<ContadorDTO> contadorDTO = contadorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contadorDTO);
    }

    /**
     * {@code DELETE  /contadors/:id} : delete the "id" contador.
     *
     * @param id the id of the contadorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContador(@PathVariable("id") Long id) {
        log.debug("REST request to delete Contador : {}", id);
        contadorService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
