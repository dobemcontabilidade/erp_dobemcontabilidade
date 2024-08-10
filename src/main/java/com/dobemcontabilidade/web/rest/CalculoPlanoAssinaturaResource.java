package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.CalculoPlanoAssinaturaRepository;
import com.dobemcontabilidade.service.CalculoPlanoAssinaturaQueryService;
import com.dobemcontabilidade.service.CalculoPlanoAssinaturaService;
import com.dobemcontabilidade.service.criteria.CalculoPlanoAssinaturaCriteria;
import com.dobemcontabilidade.service.dto.CalculoPlanoAssinaturaDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.CalculoPlanoAssinatura}.
 */
@RestController
@RequestMapping("/api/calculo-plano-assinaturas")
public class CalculoPlanoAssinaturaResource {

    private static final Logger log = LoggerFactory.getLogger(CalculoPlanoAssinaturaResource.class);

    private static final String ENTITY_NAME = "calculoPlanoAssinatura";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CalculoPlanoAssinaturaService calculoPlanoAssinaturaService;

    private final CalculoPlanoAssinaturaRepository calculoPlanoAssinaturaRepository;

    private final CalculoPlanoAssinaturaQueryService calculoPlanoAssinaturaQueryService;

    public CalculoPlanoAssinaturaResource(
        CalculoPlanoAssinaturaService calculoPlanoAssinaturaService,
        CalculoPlanoAssinaturaRepository calculoPlanoAssinaturaRepository,
        CalculoPlanoAssinaturaQueryService calculoPlanoAssinaturaQueryService
    ) {
        this.calculoPlanoAssinaturaService = calculoPlanoAssinaturaService;
        this.calculoPlanoAssinaturaRepository = calculoPlanoAssinaturaRepository;
        this.calculoPlanoAssinaturaQueryService = calculoPlanoAssinaturaQueryService;
    }

    /**
     * {@code POST  /calculo-plano-assinaturas} : Create a new calculoPlanoAssinatura.
     *
     * @param calculoPlanoAssinaturaDTO the calculoPlanoAssinaturaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new calculoPlanoAssinaturaDTO, or with status {@code 400 (Bad Request)} if the calculoPlanoAssinatura has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CalculoPlanoAssinaturaDTO> createCalculoPlanoAssinatura(
        @Valid @RequestBody CalculoPlanoAssinaturaDTO calculoPlanoAssinaturaDTO
    ) throws URISyntaxException {
        log.debug("REST request to save CalculoPlanoAssinatura : {}", calculoPlanoAssinaturaDTO);
        if (calculoPlanoAssinaturaDTO.getId() != null) {
            throw new BadRequestAlertException("A new calculoPlanoAssinatura cannot already have an ID", ENTITY_NAME, "idexists");
        }
        calculoPlanoAssinaturaDTO = calculoPlanoAssinaturaService.save(calculoPlanoAssinaturaDTO);
        return ResponseEntity.created(new URI("/api/calculo-plano-assinaturas/" + calculoPlanoAssinaturaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, calculoPlanoAssinaturaDTO.getId().toString()))
            .body(calculoPlanoAssinaturaDTO);
    }

    /**
     * {@code PUT  /calculo-plano-assinaturas/:id} : Updates an existing calculoPlanoAssinatura.
     *
     * @param id the id of the calculoPlanoAssinaturaDTO to save.
     * @param calculoPlanoAssinaturaDTO the calculoPlanoAssinaturaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calculoPlanoAssinaturaDTO,
     * or with status {@code 400 (Bad Request)} if the calculoPlanoAssinaturaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the calculoPlanoAssinaturaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CalculoPlanoAssinaturaDTO> updateCalculoPlanoAssinatura(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CalculoPlanoAssinaturaDTO calculoPlanoAssinaturaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CalculoPlanoAssinatura : {}, {}", id, calculoPlanoAssinaturaDTO);
        if (calculoPlanoAssinaturaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, calculoPlanoAssinaturaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!calculoPlanoAssinaturaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        calculoPlanoAssinaturaDTO = calculoPlanoAssinaturaService.update(calculoPlanoAssinaturaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, calculoPlanoAssinaturaDTO.getId().toString()))
            .body(calculoPlanoAssinaturaDTO);
    }

    /**
     * {@code PATCH  /calculo-plano-assinaturas/:id} : Partial updates given fields of an existing calculoPlanoAssinatura, field will ignore if it is null
     *
     * @param id the id of the calculoPlanoAssinaturaDTO to save.
     * @param calculoPlanoAssinaturaDTO the calculoPlanoAssinaturaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calculoPlanoAssinaturaDTO,
     * or with status {@code 400 (Bad Request)} if the calculoPlanoAssinaturaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the calculoPlanoAssinaturaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the calculoPlanoAssinaturaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CalculoPlanoAssinaturaDTO> partialUpdateCalculoPlanoAssinatura(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CalculoPlanoAssinaturaDTO calculoPlanoAssinaturaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CalculoPlanoAssinatura partially : {}, {}", id, calculoPlanoAssinaturaDTO);
        if (calculoPlanoAssinaturaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, calculoPlanoAssinaturaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!calculoPlanoAssinaturaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CalculoPlanoAssinaturaDTO> result = calculoPlanoAssinaturaService.partialUpdate(calculoPlanoAssinaturaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, calculoPlanoAssinaturaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /calculo-plano-assinaturas} : get all the calculoPlanoAssinaturas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of calculoPlanoAssinaturas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CalculoPlanoAssinaturaDTO>> getAllCalculoPlanoAssinaturas(
        CalculoPlanoAssinaturaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CalculoPlanoAssinaturas by criteria: {}", criteria);

        Page<CalculoPlanoAssinaturaDTO> page = calculoPlanoAssinaturaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /calculo-plano-assinaturas/count} : count all the calculoPlanoAssinaturas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countCalculoPlanoAssinaturas(CalculoPlanoAssinaturaCriteria criteria) {
        log.debug("REST request to count CalculoPlanoAssinaturas by criteria: {}", criteria);
        return ResponseEntity.ok().body(calculoPlanoAssinaturaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /calculo-plano-assinaturas/:id} : get the "id" calculoPlanoAssinatura.
     *
     * @param id the id of the calculoPlanoAssinaturaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the calculoPlanoAssinaturaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CalculoPlanoAssinaturaDTO> getCalculoPlanoAssinatura(@PathVariable("id") Long id) {
        log.debug("REST request to get CalculoPlanoAssinatura : {}", id);
        Optional<CalculoPlanoAssinaturaDTO> calculoPlanoAssinaturaDTO = calculoPlanoAssinaturaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(calculoPlanoAssinaturaDTO);
    }

    /**
     * {@code DELETE  /calculo-plano-assinaturas/:id} : delete the "id" calculoPlanoAssinatura.
     *
     * @param id the id of the calculoPlanoAssinaturaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalculoPlanoAssinatura(@PathVariable("id") Long id) {
        log.debug("REST request to delete CalculoPlanoAssinatura : {}", id);
        calculoPlanoAssinaturaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
