package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.AnexoEmpresaRepository;
import com.dobemcontabilidade.service.AnexoEmpresaQueryService;
import com.dobemcontabilidade.service.AnexoEmpresaService;
import com.dobemcontabilidade.service.criteria.AnexoEmpresaCriteria;
import com.dobemcontabilidade.service.dto.AnexoEmpresaDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dobemcontabilidade.domain.AnexoEmpresa}.
 */
@RestController
@RequestMapping("/api/anexo-empresas")
public class AnexoEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(AnexoEmpresaResource.class);

    private static final String ENTITY_NAME = "anexoEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnexoEmpresaService anexoEmpresaService;

    private final AnexoEmpresaRepository anexoEmpresaRepository;

    private final AnexoEmpresaQueryService anexoEmpresaQueryService;

    public AnexoEmpresaResource(
        AnexoEmpresaService anexoEmpresaService,
        AnexoEmpresaRepository anexoEmpresaRepository,
        AnexoEmpresaQueryService anexoEmpresaQueryService
    ) {
        this.anexoEmpresaService = anexoEmpresaService;
        this.anexoEmpresaRepository = anexoEmpresaRepository;
        this.anexoEmpresaQueryService = anexoEmpresaQueryService;
    }

    /**
     * {@code POST  /anexo-empresas} : Create a new anexoEmpresa.
     *
     * @param anexoEmpresaDTO the anexoEmpresaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anexoEmpresaDTO, or with status {@code 400 (Bad Request)} if the anexoEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AnexoEmpresaDTO> createAnexoEmpresa(@Valid @RequestBody AnexoEmpresaDTO anexoEmpresaDTO)
        throws URISyntaxException {
        log.debug("REST request to save AnexoEmpresa : {}", anexoEmpresaDTO);
        if (anexoEmpresaDTO.getId() != null) {
            throw new BadRequestAlertException("A new anexoEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        anexoEmpresaDTO = anexoEmpresaService.save(anexoEmpresaDTO);
        return ResponseEntity.created(new URI("/api/anexo-empresas/" + anexoEmpresaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, anexoEmpresaDTO.getId().toString()))
            .body(anexoEmpresaDTO);
    }

    /**
     * {@code PUT  /anexo-empresas/:id} : Updates an existing anexoEmpresa.
     *
     * @param id the id of the anexoEmpresaDTO to save.
     * @param anexoEmpresaDTO the anexoEmpresaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anexoEmpresaDTO,
     * or with status {@code 400 (Bad Request)} if the anexoEmpresaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anexoEmpresaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AnexoEmpresaDTO> updateAnexoEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AnexoEmpresaDTO anexoEmpresaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AnexoEmpresa : {}, {}", id, anexoEmpresaDTO);
        if (anexoEmpresaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anexoEmpresaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anexoEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        anexoEmpresaDTO = anexoEmpresaService.update(anexoEmpresaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anexoEmpresaDTO.getId().toString()))
            .body(anexoEmpresaDTO);
    }

    /**
     * {@code PATCH  /anexo-empresas/:id} : Partial updates given fields of an existing anexoEmpresa, field will ignore if it is null
     *
     * @param id the id of the anexoEmpresaDTO to save.
     * @param anexoEmpresaDTO the anexoEmpresaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anexoEmpresaDTO,
     * or with status {@code 400 (Bad Request)} if the anexoEmpresaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the anexoEmpresaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the anexoEmpresaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AnexoEmpresaDTO> partialUpdateAnexoEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AnexoEmpresaDTO anexoEmpresaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AnexoEmpresa partially : {}, {}", id, anexoEmpresaDTO);
        if (anexoEmpresaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anexoEmpresaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anexoEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnexoEmpresaDTO> result = anexoEmpresaService.partialUpdate(anexoEmpresaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anexoEmpresaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /anexo-empresas} : get all the anexoEmpresas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anexoEmpresas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AnexoEmpresaDTO>> getAllAnexoEmpresas(AnexoEmpresaCriteria criteria) {
        log.debug("REST request to get AnexoEmpresas by criteria: {}", criteria);

        List<AnexoEmpresaDTO> entityList = anexoEmpresaQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /anexo-empresas/count} : count all the anexoEmpresas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAnexoEmpresas(AnexoEmpresaCriteria criteria) {
        log.debug("REST request to count AnexoEmpresas by criteria: {}", criteria);
        return ResponseEntity.ok().body(anexoEmpresaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /anexo-empresas/:id} : get the "id" anexoEmpresa.
     *
     * @param id the id of the anexoEmpresaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anexoEmpresaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AnexoEmpresaDTO> getAnexoEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get AnexoEmpresa : {}", id);
        Optional<AnexoEmpresaDTO> anexoEmpresaDTO = anexoEmpresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anexoEmpresaDTO);
    }

    /**
     * {@code DELETE  /anexo-empresas/:id} : delete the "id" anexoEmpresa.
     *
     * @param id the id of the anexoEmpresaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnexoEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete AnexoEmpresa : {}", id);
        anexoEmpresaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
