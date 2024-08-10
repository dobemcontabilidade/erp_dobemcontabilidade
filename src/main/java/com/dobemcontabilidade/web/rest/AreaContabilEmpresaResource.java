package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.AreaContabilEmpresaRepository;
import com.dobemcontabilidade.service.AreaContabilEmpresaService;
import com.dobemcontabilidade.service.dto.AreaContabilEmpresaDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AreaContabilEmpresa}.
 */
@RestController
@RequestMapping("/api/area-contabil-empresas")
public class AreaContabilEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(AreaContabilEmpresaResource.class);

    private static final String ENTITY_NAME = "areaContabilEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AreaContabilEmpresaService areaContabilEmpresaService;

    private final AreaContabilEmpresaRepository areaContabilEmpresaRepository;

    public AreaContabilEmpresaResource(
        AreaContabilEmpresaService areaContabilEmpresaService,
        AreaContabilEmpresaRepository areaContabilEmpresaRepository
    ) {
        this.areaContabilEmpresaService = areaContabilEmpresaService;
        this.areaContabilEmpresaRepository = areaContabilEmpresaRepository;
    }

    /**
     * {@code POST  /area-contabil-empresas} : Create a new areaContabilEmpresa.
     *
     * @param areaContabilEmpresaDTO the areaContabilEmpresaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new areaContabilEmpresaDTO, or with status {@code 400 (Bad Request)} if the areaContabilEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AreaContabilEmpresaDTO> createAreaContabilEmpresa(
        @Valid @RequestBody AreaContabilEmpresaDTO areaContabilEmpresaDTO
    ) throws URISyntaxException {
        log.debug("REST request to save AreaContabilEmpresa : {}", areaContabilEmpresaDTO);
        if (areaContabilEmpresaDTO.getId() != null) {
            throw new BadRequestAlertException("A new areaContabilEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        areaContabilEmpresaDTO = areaContabilEmpresaService.save(areaContabilEmpresaDTO);
        return ResponseEntity.created(new URI("/api/area-contabil-empresas/" + areaContabilEmpresaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, areaContabilEmpresaDTO.getId().toString()))
            .body(areaContabilEmpresaDTO);
    }

    /**
     * {@code PUT  /area-contabil-empresas/:id} : Updates an existing areaContabilEmpresa.
     *
     * @param id the id of the areaContabilEmpresaDTO to save.
     * @param areaContabilEmpresaDTO the areaContabilEmpresaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated areaContabilEmpresaDTO,
     * or with status {@code 400 (Bad Request)} if the areaContabilEmpresaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the areaContabilEmpresaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AreaContabilEmpresaDTO> updateAreaContabilEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AreaContabilEmpresaDTO areaContabilEmpresaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AreaContabilEmpresa : {}, {}", id, areaContabilEmpresaDTO);
        if (areaContabilEmpresaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, areaContabilEmpresaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!areaContabilEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        areaContabilEmpresaDTO = areaContabilEmpresaService.update(areaContabilEmpresaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, areaContabilEmpresaDTO.getId().toString()))
            .body(areaContabilEmpresaDTO);
    }

    /**
     * {@code PATCH  /area-contabil-empresas/:id} : Partial updates given fields of an existing areaContabilEmpresa, field will ignore if it is null
     *
     * @param id the id of the areaContabilEmpresaDTO to save.
     * @param areaContabilEmpresaDTO the areaContabilEmpresaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated areaContabilEmpresaDTO,
     * or with status {@code 400 (Bad Request)} if the areaContabilEmpresaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the areaContabilEmpresaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the areaContabilEmpresaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AreaContabilEmpresaDTO> partialUpdateAreaContabilEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AreaContabilEmpresaDTO areaContabilEmpresaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AreaContabilEmpresa partially : {}, {}", id, areaContabilEmpresaDTO);
        if (areaContabilEmpresaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, areaContabilEmpresaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!areaContabilEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AreaContabilEmpresaDTO> result = areaContabilEmpresaService.partialUpdate(areaContabilEmpresaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, areaContabilEmpresaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /area-contabil-empresas} : get all the areaContabilEmpresas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of areaContabilEmpresas in body.
     */
    @GetMapping("")
    public List<AreaContabilEmpresaDTO> getAllAreaContabilEmpresas(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all AreaContabilEmpresas");
        return areaContabilEmpresaService.findAll();
    }

    /**
     * {@code GET  /area-contabil-empresas/:id} : get the "id" areaContabilEmpresa.
     *
     * @param id the id of the areaContabilEmpresaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the areaContabilEmpresaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AreaContabilEmpresaDTO> getAreaContabilEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get AreaContabilEmpresa : {}", id);
        Optional<AreaContabilEmpresaDTO> areaContabilEmpresaDTO = areaContabilEmpresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(areaContabilEmpresaDTO);
    }

    /**
     * {@code DELETE  /area-contabil-empresas/:id} : delete the "id" areaContabilEmpresa.
     *
     * @param id the id of the areaContabilEmpresaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAreaContabilEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete AreaContabilEmpresa : {}", id);
        areaContabilEmpresaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
