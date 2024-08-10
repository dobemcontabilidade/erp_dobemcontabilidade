package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.DenunciaRepository;
import com.dobemcontabilidade.service.DenunciaQueryService;
import com.dobemcontabilidade.service.DenunciaService;
import com.dobemcontabilidade.service.criteria.DenunciaCriteria;
import com.dobemcontabilidade.service.dto.DenunciaDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Denuncia}.
 */
@RestController
@RequestMapping("/api/denuncias")
public class DenunciaResource {

    private static final Logger log = LoggerFactory.getLogger(DenunciaResource.class);

    private static final String ENTITY_NAME = "denuncia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DenunciaService denunciaService;

    private final DenunciaRepository denunciaRepository;

    private final DenunciaQueryService denunciaQueryService;

    public DenunciaResource(
        DenunciaService denunciaService,
        DenunciaRepository denunciaRepository,
        DenunciaQueryService denunciaQueryService
    ) {
        this.denunciaService = denunciaService;
        this.denunciaRepository = denunciaRepository;
        this.denunciaQueryService = denunciaQueryService;
    }

    /**
     * {@code POST  /denuncias} : Create a new denuncia.
     *
     * @param denunciaDTO the denunciaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new denunciaDTO, or with status {@code 400 (Bad Request)} if the denuncia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DenunciaDTO> createDenuncia(@Valid @RequestBody DenunciaDTO denunciaDTO) throws URISyntaxException {
        log.debug("REST request to save Denuncia : {}", denunciaDTO);
        if (denunciaDTO.getId() != null) {
            throw new BadRequestAlertException("A new denuncia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        denunciaDTO = denunciaService.save(denunciaDTO);
        return ResponseEntity.created(new URI("/api/denuncias/" + denunciaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, denunciaDTO.getId().toString()))
            .body(denunciaDTO);
    }

    /**
     * {@code PUT  /denuncias/:id} : Updates an existing denuncia.
     *
     * @param id the id of the denunciaDTO to save.
     * @param denunciaDTO the denunciaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated denunciaDTO,
     * or with status {@code 400 (Bad Request)} if the denunciaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the denunciaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DenunciaDTO> updateDenuncia(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DenunciaDTO denunciaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Denuncia : {}, {}", id, denunciaDTO);
        if (denunciaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, denunciaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!denunciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        denunciaDTO = denunciaService.update(denunciaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, denunciaDTO.getId().toString()))
            .body(denunciaDTO);
    }

    /**
     * {@code PATCH  /denuncias/:id} : Partial updates given fields of an existing denuncia, field will ignore if it is null
     *
     * @param id the id of the denunciaDTO to save.
     * @param denunciaDTO the denunciaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated denunciaDTO,
     * or with status {@code 400 (Bad Request)} if the denunciaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the denunciaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the denunciaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DenunciaDTO> partialUpdateDenuncia(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DenunciaDTO denunciaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Denuncia partially : {}, {}", id, denunciaDTO);
        if (denunciaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, denunciaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!denunciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DenunciaDTO> result = denunciaService.partialUpdate(denunciaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, denunciaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /denuncias} : get all the denuncias.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of denuncias in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DenunciaDTO>> getAllDenuncias(
        DenunciaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Denuncias by criteria: {}", criteria);

        Page<DenunciaDTO> page = denunciaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /denuncias/count} : count all the denuncias.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countDenuncias(DenunciaCriteria criteria) {
        log.debug("REST request to count Denuncias by criteria: {}", criteria);
        return ResponseEntity.ok().body(denunciaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /denuncias/:id} : get the "id" denuncia.
     *
     * @param id the id of the denunciaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the denunciaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DenunciaDTO> getDenuncia(@PathVariable("id") Long id) {
        log.debug("REST request to get Denuncia : {}", id);
        Optional<DenunciaDTO> denunciaDTO = denunciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(denunciaDTO);
    }

    /**
     * {@code DELETE  /denuncias/:id} : delete the "id" denuncia.
     *
     * @param id the id of the denunciaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDenuncia(@PathVariable("id") Long id) {
        log.debug("REST request to delete Denuncia : {}", id);
        denunciaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
