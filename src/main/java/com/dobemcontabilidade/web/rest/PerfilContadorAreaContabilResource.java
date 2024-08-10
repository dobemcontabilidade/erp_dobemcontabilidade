package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.PerfilContadorAreaContabilRepository;
import com.dobemcontabilidade.service.PerfilContadorAreaContabilService;
import com.dobemcontabilidade.service.dto.PerfilContadorAreaContabilDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.PerfilContadorAreaContabil}.
 */
@RestController
@RequestMapping("/api/perfil-contador-area-contabils")
public class PerfilContadorAreaContabilResource {

    private static final Logger log = LoggerFactory.getLogger(PerfilContadorAreaContabilResource.class);

    private static final String ENTITY_NAME = "perfilContadorAreaContabil";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PerfilContadorAreaContabilService perfilContadorAreaContabilService;

    private final PerfilContadorAreaContabilRepository perfilContadorAreaContabilRepository;

    public PerfilContadorAreaContabilResource(
        PerfilContadorAreaContabilService perfilContadorAreaContabilService,
        PerfilContadorAreaContabilRepository perfilContadorAreaContabilRepository
    ) {
        this.perfilContadorAreaContabilService = perfilContadorAreaContabilService;
        this.perfilContadorAreaContabilRepository = perfilContadorAreaContabilRepository;
    }

    /**
     * {@code POST  /perfil-contador-area-contabils} : Create a new perfilContadorAreaContabil.
     *
     * @param perfilContadorAreaContabilDTO the perfilContadorAreaContabilDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new perfilContadorAreaContabilDTO, or with status {@code 400 (Bad Request)} if the perfilContadorAreaContabil has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PerfilContadorAreaContabilDTO> createPerfilContadorAreaContabil(
        @Valid @RequestBody PerfilContadorAreaContabilDTO perfilContadorAreaContabilDTO
    ) throws URISyntaxException {
        log.debug("REST request to save PerfilContadorAreaContabil : {}", perfilContadorAreaContabilDTO);
        if (perfilContadorAreaContabilDTO.getId() != null) {
            throw new BadRequestAlertException("A new perfilContadorAreaContabil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        perfilContadorAreaContabilDTO = perfilContadorAreaContabilService.save(perfilContadorAreaContabilDTO);
        return ResponseEntity.created(new URI("/api/perfil-contador-area-contabils/" + perfilContadorAreaContabilDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, perfilContadorAreaContabilDTO.getId().toString())
            )
            .body(perfilContadorAreaContabilDTO);
    }

    /**
     * {@code PUT  /perfil-contador-area-contabils/:id} : Updates an existing perfilContadorAreaContabil.
     *
     * @param id the id of the perfilContadorAreaContabilDTO to save.
     * @param perfilContadorAreaContabilDTO the perfilContadorAreaContabilDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated perfilContadorAreaContabilDTO,
     * or with status {@code 400 (Bad Request)} if the perfilContadorAreaContabilDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the perfilContadorAreaContabilDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PerfilContadorAreaContabilDTO> updatePerfilContadorAreaContabil(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PerfilContadorAreaContabilDTO perfilContadorAreaContabilDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PerfilContadorAreaContabil : {}, {}", id, perfilContadorAreaContabilDTO);
        if (perfilContadorAreaContabilDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, perfilContadorAreaContabilDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!perfilContadorAreaContabilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        perfilContadorAreaContabilDTO = perfilContadorAreaContabilService.update(perfilContadorAreaContabilDTO);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, perfilContadorAreaContabilDTO.getId().toString())
            )
            .body(perfilContadorAreaContabilDTO);
    }

    /**
     * {@code PATCH  /perfil-contador-area-contabils/:id} : Partial updates given fields of an existing perfilContadorAreaContabil, field will ignore if it is null
     *
     * @param id the id of the perfilContadorAreaContabilDTO to save.
     * @param perfilContadorAreaContabilDTO the perfilContadorAreaContabilDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated perfilContadorAreaContabilDTO,
     * or with status {@code 400 (Bad Request)} if the perfilContadorAreaContabilDTO is not valid,
     * or with status {@code 404 (Not Found)} if the perfilContadorAreaContabilDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the perfilContadorAreaContabilDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PerfilContadorAreaContabilDTO> partialUpdatePerfilContadorAreaContabil(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PerfilContadorAreaContabilDTO perfilContadorAreaContabilDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PerfilContadorAreaContabil partially : {}, {}", id, perfilContadorAreaContabilDTO);
        if (perfilContadorAreaContabilDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, perfilContadorAreaContabilDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!perfilContadorAreaContabilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PerfilContadorAreaContabilDTO> result = perfilContadorAreaContabilService.partialUpdate(perfilContadorAreaContabilDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, perfilContadorAreaContabilDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /perfil-contador-area-contabils} : get all the perfilContadorAreaContabils.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of perfilContadorAreaContabils in body.
     */
    @GetMapping("")
    public List<PerfilContadorAreaContabilDTO> getAllPerfilContadorAreaContabils(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all PerfilContadorAreaContabils");
        return perfilContadorAreaContabilService.findAll();
    }

    /**
     * {@code GET  /perfil-contador-area-contabils/:id} : get the "id" perfilContadorAreaContabil.
     *
     * @param id the id of the perfilContadorAreaContabilDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the perfilContadorAreaContabilDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PerfilContadorAreaContabilDTO> getPerfilContadorAreaContabil(@PathVariable("id") Long id) {
        log.debug("REST request to get PerfilContadorAreaContabil : {}", id);
        Optional<PerfilContadorAreaContabilDTO> perfilContadorAreaContabilDTO = perfilContadorAreaContabilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(perfilContadorAreaContabilDTO);
    }

    /**
     * {@code DELETE  /perfil-contador-area-contabils/:id} : delete the "id" perfilContadorAreaContabil.
     *
     * @param id the id of the perfilContadorAreaContabilDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerfilContadorAreaContabil(@PathVariable("id") Long id) {
        log.debug("REST request to delete PerfilContadorAreaContabil : {}", id);
        perfilContadorAreaContabilService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
