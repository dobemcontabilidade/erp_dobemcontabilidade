package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.Imposto;
import com.dobemcontabilidade.repository.ImpostoRepository;
import com.dobemcontabilidade.service.ImpostoService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Imposto}.
 */
@RestController
@RequestMapping("/api/impostos")
public class ImpostoResource {

    private static final Logger log = LoggerFactory.getLogger(ImpostoResource.class);

    private static final String ENTITY_NAME = "imposto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImpostoService impostoService;

    private final ImpostoRepository impostoRepository;

    public ImpostoResource(ImpostoService impostoService, ImpostoRepository impostoRepository) {
        this.impostoService = impostoService;
        this.impostoRepository = impostoRepository;
    }

    /**
     * {@code POST  /impostos} : Create a new imposto.
     *
     * @param imposto the imposto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new imposto, or with status {@code 400 (Bad Request)} if the imposto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Imposto> createImposto(@Valid @RequestBody Imposto imposto) throws URISyntaxException {
        log.debug("REST request to save Imposto : {}", imposto);
        if (imposto.getId() != null) {
            throw new BadRequestAlertException("A new imposto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        imposto = impostoService.save(imposto);
        return ResponseEntity.created(new URI("/api/impostos/" + imposto.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, imposto.getId().toString()))
            .body(imposto);
    }

    /**
     * {@code PUT  /impostos/:id} : Updates an existing imposto.
     *
     * @param id the id of the imposto to save.
     * @param imposto the imposto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated imposto,
     * or with status {@code 400 (Bad Request)} if the imposto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the imposto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Imposto> updateImposto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Imposto imposto
    ) throws URISyntaxException {
        log.debug("REST request to update Imposto : {}, {}", id, imposto);
        if (imposto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, imposto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!impostoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        imposto = impostoService.update(imposto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, imposto.getId().toString()))
            .body(imposto);
    }

    /**
     * {@code PATCH  /impostos/:id} : Partial updates given fields of an existing imposto, field will ignore if it is null
     *
     * @param id the id of the imposto to save.
     * @param imposto the imposto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated imposto,
     * or with status {@code 400 (Bad Request)} if the imposto is not valid,
     * or with status {@code 404 (Not Found)} if the imposto is not found,
     * or with status {@code 500 (Internal Server Error)} if the imposto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Imposto> partialUpdateImposto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Imposto imposto
    ) throws URISyntaxException {
        log.debug("REST request to partial update Imposto partially : {}, {}", id, imposto);
        if (imposto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, imposto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!impostoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Imposto> result = impostoService.partialUpdate(imposto);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, imposto.getId().toString())
        );
    }

    /**
     * {@code GET  /impostos} : get all the impostos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of impostos in body.
     */
    @GetMapping("")
    public List<Imposto> getAllImpostos(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Impostos");
        return impostoService.findAll();
    }

    /**
     * {@code GET  /impostos/:id} : get the "id" imposto.
     *
     * @param id the id of the imposto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the imposto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Imposto> getImposto(@PathVariable("id") Long id) {
        log.debug("REST request to get Imposto : {}", id);
        Optional<Imposto> imposto = impostoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(imposto);
    }

    /**
     * {@code DELETE  /impostos/:id} : delete the "id" imposto.
     *
     * @param id the id of the imposto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImposto(@PathVariable("id") Long id) {
        log.debug("REST request to delete Imposto : {}", id);
        impostoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
