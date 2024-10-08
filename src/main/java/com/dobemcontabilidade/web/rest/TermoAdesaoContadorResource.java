package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.TermoAdesaoContadorRepository;
import com.dobemcontabilidade.service.TermoAdesaoContadorQueryService;
import com.dobemcontabilidade.service.TermoAdesaoContadorService;
import com.dobemcontabilidade.service.criteria.TermoAdesaoContadorCriteria;
import com.dobemcontabilidade.service.dto.TermoAdesaoContadorDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.TermoAdesaoContador}.
 */
@RestController
@RequestMapping("/api/termo-adesao-contadors")
public class TermoAdesaoContadorResource {

    private static final Logger log = LoggerFactory.getLogger(TermoAdesaoContadorResource.class);

    private static final String ENTITY_NAME = "termoAdesaoContador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TermoAdesaoContadorService termoAdesaoContadorService;

    private final TermoAdesaoContadorRepository termoAdesaoContadorRepository;

    private final TermoAdesaoContadorQueryService termoAdesaoContadorQueryService;

    public TermoAdesaoContadorResource(
        TermoAdesaoContadorService termoAdesaoContadorService,
        TermoAdesaoContadorRepository termoAdesaoContadorRepository,
        TermoAdesaoContadorQueryService termoAdesaoContadorQueryService
    ) {
        this.termoAdesaoContadorService = termoAdesaoContadorService;
        this.termoAdesaoContadorRepository = termoAdesaoContadorRepository;
        this.termoAdesaoContadorQueryService = termoAdesaoContadorQueryService;
    }

    /**
     * {@code POST  /termo-adesao-contadors} : Create a new termoAdesaoContador.
     *
     * @param termoAdesaoContadorDTO the termoAdesaoContadorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new termoAdesaoContadorDTO, or with status {@code 400 (Bad Request)} if the termoAdesaoContador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TermoAdesaoContadorDTO> createTermoAdesaoContador(
        @Valid @RequestBody TermoAdesaoContadorDTO termoAdesaoContadorDTO
    ) throws URISyntaxException {
        log.debug("REST request to save TermoAdesaoContador : {}", termoAdesaoContadorDTO);
        if (termoAdesaoContadorDTO.getId() != null) {
            throw new BadRequestAlertException("A new termoAdesaoContador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        termoAdesaoContadorDTO = termoAdesaoContadorService.save(termoAdesaoContadorDTO);
        return ResponseEntity.created(new URI("/api/termo-adesao-contadors/" + termoAdesaoContadorDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, termoAdesaoContadorDTO.getId().toString()))
            .body(termoAdesaoContadorDTO);
    }

    /**
     * {@code PUT  /termo-adesao-contadors/:id} : Updates an existing termoAdesaoContador.
     *
     * @param id the id of the termoAdesaoContadorDTO to save.
     * @param termoAdesaoContadorDTO the termoAdesaoContadorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated termoAdesaoContadorDTO,
     * or with status {@code 400 (Bad Request)} if the termoAdesaoContadorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the termoAdesaoContadorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TermoAdesaoContadorDTO> updateTermoAdesaoContador(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TermoAdesaoContadorDTO termoAdesaoContadorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TermoAdesaoContador : {}, {}", id, termoAdesaoContadorDTO);
        if (termoAdesaoContadorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, termoAdesaoContadorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!termoAdesaoContadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        termoAdesaoContadorDTO = termoAdesaoContadorService.update(termoAdesaoContadorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, termoAdesaoContadorDTO.getId().toString()))
            .body(termoAdesaoContadorDTO);
    }

    /**
     * {@code PATCH  /termo-adesao-contadors/:id} : Partial updates given fields of an existing termoAdesaoContador, field will ignore if it is null
     *
     * @param id the id of the termoAdesaoContadorDTO to save.
     * @param termoAdesaoContadorDTO the termoAdesaoContadorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated termoAdesaoContadorDTO,
     * or with status {@code 400 (Bad Request)} if the termoAdesaoContadorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the termoAdesaoContadorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the termoAdesaoContadorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TermoAdesaoContadorDTO> partialUpdateTermoAdesaoContador(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TermoAdesaoContadorDTO termoAdesaoContadorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TermoAdesaoContador partially : {}, {}", id, termoAdesaoContadorDTO);
        if (termoAdesaoContadorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, termoAdesaoContadorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!termoAdesaoContadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TermoAdesaoContadorDTO> result = termoAdesaoContadorService.partialUpdate(termoAdesaoContadorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, termoAdesaoContadorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /termo-adesao-contadors} : get all the termoAdesaoContadors.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of termoAdesaoContadors in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TermoAdesaoContadorDTO>> getAllTermoAdesaoContadors(
        TermoAdesaoContadorCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TermoAdesaoContadors by criteria: {}", criteria);

        Page<TermoAdesaoContadorDTO> page = termoAdesaoContadorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /termo-adesao-contadors/count} : count all the termoAdesaoContadors.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countTermoAdesaoContadors(TermoAdesaoContadorCriteria criteria) {
        log.debug("REST request to count TermoAdesaoContadors by criteria: {}", criteria);
        return ResponseEntity.ok().body(termoAdesaoContadorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /termo-adesao-contadors/:id} : get the "id" termoAdesaoContador.
     *
     * @param id the id of the termoAdesaoContadorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the termoAdesaoContadorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TermoAdesaoContadorDTO> getTermoAdesaoContador(@PathVariable("id") Long id) {
        log.debug("REST request to get TermoAdesaoContador : {}", id);
        Optional<TermoAdesaoContadorDTO> termoAdesaoContadorDTO = termoAdesaoContadorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(termoAdesaoContadorDTO);
    }

    /**
     * {@code DELETE  /termo-adesao-contadors/:id} : delete the "id" termoAdesaoContador.
     *
     * @param id the id of the termoAdesaoContadorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTermoAdesaoContador(@PathVariable("id") Long id) {
        log.debug("REST request to delete TermoAdesaoContador : {}", id);
        termoAdesaoContadorService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
