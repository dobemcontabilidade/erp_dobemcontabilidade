package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.Ramo;
import com.dobemcontabilidade.repository.RamoRepository;
import com.dobemcontabilidade.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Ramo}.
 */
@RestController
@RequestMapping("/api/ramos")
@Transactional
public class RamoResource {

    private static final Logger log = LoggerFactory.getLogger(RamoResource.class);

    private static final String ENTITY_NAME = "ramo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RamoRepository ramoRepository;

    public RamoResource(RamoRepository ramoRepository) {
        this.ramoRepository = ramoRepository;
    }

    /**
     * {@code POST  /ramos} : Create a new ramo.
     *
     * @param ramo the ramo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ramo, or with status {@code 400 (Bad Request)} if the ramo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Ramo> createRamo(@RequestBody Ramo ramo) throws URISyntaxException {
        log.debug("REST request to save Ramo : {}", ramo);
        if (ramo.getId() != null) {
            throw new BadRequestAlertException("A new ramo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ramo = ramoRepository.save(ramo);
        return ResponseEntity.created(new URI("/api/ramos/" + ramo.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ramo.getId().toString()))
            .body(ramo);
    }

    /**
     * {@code PUT  /ramos/:id} : Updates an existing ramo.
     *
     * @param id the id of the ramo to save.
     * @param ramo the ramo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ramo,
     * or with status {@code 400 (Bad Request)} if the ramo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ramo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Ramo> updateRamo(@PathVariable(value = "id", required = false) final Long id, @RequestBody Ramo ramo)
        throws URISyntaxException {
        log.debug("REST request to update Ramo : {}, {}", id, ramo);
        if (ramo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ramo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ramoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ramo = ramoRepository.save(ramo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ramo.getId().toString()))
            .body(ramo);
    }

    /**
     * {@code PATCH  /ramos/:id} : Partial updates given fields of an existing ramo, field will ignore if it is null
     *
     * @param id the id of the ramo to save.
     * @param ramo the ramo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ramo,
     * or with status {@code 400 (Bad Request)} if the ramo is not valid,
     * or with status {@code 404 (Not Found)} if the ramo is not found,
     * or with status {@code 500 (Internal Server Error)} if the ramo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ramo> partialUpdateRamo(@PathVariable(value = "id", required = false) final Long id, @RequestBody Ramo ramo)
        throws URISyntaxException {
        log.debug("REST request to partial update Ramo partially : {}, {}", id, ramo);
        if (ramo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ramo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ramoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ramo> result = ramoRepository
            .findById(ramo.getId())
            .map(existingRamo -> {
                if (ramo.getNome() != null) {
                    existingRamo.setNome(ramo.getNome());
                }
                if (ramo.getDescricao() != null) {
                    existingRamo.setDescricao(ramo.getDescricao());
                }

                return existingRamo;
            })
            .map(ramoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ramo.getId().toString())
        );
    }

    /**
     * {@code GET  /ramos} : get all the ramos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ramos in body.
     */
    @GetMapping("")
    public List<Ramo> getAllRamos() {
        log.debug("REST request to get all Ramos");
        return ramoRepository.findAll();
    }

    /**
     * {@code GET  /ramos/:id} : get the "id" ramo.
     *
     * @param id the id of the ramo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ramo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ramo> getRamo(@PathVariable("id") Long id) {
        log.debug("REST request to get Ramo : {}", id);
        Optional<Ramo> ramo = ramoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ramo);
    }

    /**
     * {@code DELETE  /ramos/:id} : delete the "id" ramo.
     *
     * @param id the id of the ramo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRamo(@PathVariable("id") Long id) {
        log.debug("REST request to delete Ramo : {}", id);
        ramoRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
