package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.AreaContabilContadorRepository;
import com.dobemcontabilidade.service.AreaContabilContadorService;
import com.dobemcontabilidade.service.dto.AreaContabilContadorDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AreaContabilContador}.
 */
@RestController
@RequestMapping("/api/area-contabil-contadors")
public class AreaContabilContadorResource {

    private static final Logger log = LoggerFactory.getLogger(AreaContabilContadorResource.class);

    private static final String ENTITY_NAME = "areaContabilContador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AreaContabilContadorService areaContabilContadorService;

    private final AreaContabilContadorRepository areaContabilContadorRepository;

    public AreaContabilContadorResource(
        AreaContabilContadorService areaContabilContadorService,
        AreaContabilContadorRepository areaContabilContadorRepository
    ) {
        this.areaContabilContadorService = areaContabilContadorService;
        this.areaContabilContadorRepository = areaContabilContadorRepository;
    }

    /**
     * {@code POST  /area-contabil-contadors} : Create a new areaContabilContador.
     *
     * @param areaContabilContadorDTO the areaContabilContadorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new areaContabilContadorDTO, or with status {@code 400 (Bad Request)} if the areaContabilContador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AreaContabilContadorDTO> createAreaContabilContador(
        @Valid @RequestBody AreaContabilContadorDTO areaContabilContadorDTO
    ) throws URISyntaxException {
        log.debug("REST request to save AreaContabilContador : {}", areaContabilContadorDTO);
        if (areaContabilContadorDTO.getId() != null) {
            throw new BadRequestAlertException("A new areaContabilContador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        areaContabilContadorDTO = areaContabilContadorService.save(areaContabilContadorDTO);
        return ResponseEntity.created(new URI("/api/area-contabil-contadors/" + areaContabilContadorDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, areaContabilContadorDTO.getId().toString()))
            .body(areaContabilContadorDTO);
    }

    /**
     * {@code PUT  /area-contabil-contadors/:id} : Updates an existing areaContabilContador.
     *
     * @param id the id of the areaContabilContadorDTO to save.
     * @param areaContabilContadorDTO the areaContabilContadorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated areaContabilContadorDTO,
     * or with status {@code 400 (Bad Request)} if the areaContabilContadorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the areaContabilContadorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AreaContabilContadorDTO> updateAreaContabilContador(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AreaContabilContadorDTO areaContabilContadorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AreaContabilContador : {}, {}", id, areaContabilContadorDTO);
        if (areaContabilContadorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, areaContabilContadorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!areaContabilContadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        areaContabilContadorDTO = areaContabilContadorService.update(areaContabilContadorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, areaContabilContadorDTO.getId().toString()))
            .body(areaContabilContadorDTO);
    }

    /**
     * {@code PATCH  /area-contabil-contadors/:id} : Partial updates given fields of an existing areaContabilContador, field will ignore if it is null
     *
     * @param id the id of the areaContabilContadorDTO to save.
     * @param areaContabilContadorDTO the areaContabilContadorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated areaContabilContadorDTO,
     * or with status {@code 400 (Bad Request)} if the areaContabilContadorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the areaContabilContadorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the areaContabilContadorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AreaContabilContadorDTO> partialUpdateAreaContabilContador(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AreaContabilContadorDTO areaContabilContadorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AreaContabilContador partially : {}, {}", id, areaContabilContadorDTO);
        if (areaContabilContadorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, areaContabilContadorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!areaContabilContadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AreaContabilContadorDTO> result = areaContabilContadorService.partialUpdate(areaContabilContadorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, areaContabilContadorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /area-contabil-contadors} : get all the areaContabilContadors.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of areaContabilContadors in body.
     */
    @GetMapping("")
    public List<AreaContabilContadorDTO> getAllAreaContabilContadors(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all AreaContabilContadors");
        return areaContabilContadorService.findAll();
    }

    /**
     * {@code GET  /area-contabil-contadors/:id} : get the "id" areaContabilContador.
     *
     * @param id the id of the areaContabilContadorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the areaContabilContadorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AreaContabilContadorDTO> getAreaContabilContador(@PathVariable("id") Long id) {
        log.debug("REST request to get AreaContabilContador : {}", id);
        Optional<AreaContabilContadorDTO> areaContabilContadorDTO = areaContabilContadorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(areaContabilContadorDTO);
    }

    /**
     * {@code DELETE  /area-contabil-contadors/:id} : delete the "id" areaContabilContador.
     *
     * @param id the id of the areaContabilContadorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAreaContabilContador(@PathVariable("id") Long id) {
        log.debug("REST request to delete AreaContabilContador : {}", id);
        areaContabilContadorService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
