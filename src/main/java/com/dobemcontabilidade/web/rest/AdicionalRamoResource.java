package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.AdicionalRamo;
import com.dobemcontabilidade.repository.AdicionalRamoRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dobemcontabilidade.domain.AdicionalRamo}.
 */
@RestController
@RequestMapping("/api/adicional-ramos")
@Transactional
public class AdicionalRamoResource {

    private static final Logger log = LoggerFactory.getLogger(AdicionalRamoResource.class);

    private static final String ENTITY_NAME = "adicionalRamo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdicionalRamoRepository adicionalRamoRepository;

    public AdicionalRamoResource(AdicionalRamoRepository adicionalRamoRepository) {
        this.adicionalRamoRepository = adicionalRamoRepository;
    }

    /**
     * {@code POST  /adicional-ramos} : Create a new adicionalRamo.
     *
     * @param adicionalRamo the adicionalRamo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adicionalRamo, or with status {@code 400 (Bad Request)} if the adicionalRamo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AdicionalRamo> createAdicionalRamo(@Valid @RequestBody AdicionalRamo adicionalRamo) throws URISyntaxException {
        log.debug("REST request to save AdicionalRamo : {}", adicionalRamo);
        if (adicionalRamo.getId() != null) {
            throw new BadRequestAlertException("A new adicionalRamo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        adicionalRamo = adicionalRamoRepository.save(adicionalRamo);
        return ResponseEntity.created(new URI("/api/adicional-ramos/" + adicionalRamo.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, adicionalRamo.getId().toString()))
            .body(adicionalRamo);
    }

    /**
     * {@code PUT  /adicional-ramos/:id} : Updates an existing adicionalRamo.
     *
     * @param id the id of the adicionalRamo to save.
     * @param adicionalRamo the adicionalRamo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adicionalRamo,
     * or with status {@code 400 (Bad Request)} if the adicionalRamo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adicionalRamo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AdicionalRamo> updateAdicionalRamo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdicionalRamo adicionalRamo
    ) throws URISyntaxException {
        log.debug("REST request to update AdicionalRamo : {}, {}", id, adicionalRamo);
        if (adicionalRamo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adicionalRamo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adicionalRamoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        adicionalRamo = adicionalRamoRepository.save(adicionalRamo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adicionalRamo.getId().toString()))
            .body(adicionalRamo);
    }

    /**
     * {@code PATCH  /adicional-ramos/:id} : Partial updates given fields of an existing adicionalRamo, field will ignore if it is null
     *
     * @param id the id of the adicionalRamo to save.
     * @param adicionalRamo the adicionalRamo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adicionalRamo,
     * or with status {@code 400 (Bad Request)} if the adicionalRamo is not valid,
     * or with status {@code 404 (Not Found)} if the adicionalRamo is not found,
     * or with status {@code 500 (Internal Server Error)} if the adicionalRamo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdicionalRamo> partialUpdateAdicionalRamo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdicionalRamo adicionalRamo
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdicionalRamo partially : {}, {}", id, adicionalRamo);
        if (adicionalRamo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adicionalRamo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adicionalRamoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdicionalRamo> result = adicionalRamoRepository
            .findById(adicionalRamo.getId())
            .map(existingAdicionalRamo -> {
                if (adicionalRamo.getValor() != null) {
                    existingAdicionalRamo.setValor(adicionalRamo.getValor());
                }

                return existingAdicionalRamo;
            })
            .map(adicionalRamoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adicionalRamo.getId().toString())
        );
    }

    /**
     * {@code GET  /adicional-ramos} : get all the adicionalRamos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adicionalRamos in body.
     */
    @GetMapping("")
    public List<AdicionalRamo> getAllAdicionalRamos(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all AdicionalRamos");
        if (eagerload) {
            return adicionalRamoRepository.findAllWithEagerRelationships();
        } else {
            return adicionalRamoRepository.findAll();
        }
    }

    /**
     * {@code GET  /adicional-ramos/:id} : get the "id" adicionalRamo.
     *
     * @param id the id of the adicionalRamo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adicionalRamo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdicionalRamo> getAdicionalRamo(@PathVariable("id") Long id) {
        log.debug("REST request to get AdicionalRamo : {}", id);
        Optional<AdicionalRamo> adicionalRamo = adicionalRamoRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(adicionalRamo);
    }

    /**
     * {@code DELETE  /adicional-ramos/:id} : delete the "id" adicionalRamo.
     *
     * @param id the id of the adicionalRamo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdicionalRamo(@PathVariable("id") Long id) {
        log.debug("REST request to delete AdicionalRamo : {}", id);
        adicionalRamoRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
