package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.ParcelamentoImposto;
import com.dobemcontabilidade.repository.ParcelamentoImpostoRepository;
import com.dobemcontabilidade.service.ParcelamentoImpostoService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.ParcelamentoImposto}.
 */
@RestController
@RequestMapping("/api/parcelamento-impostos")
public class ParcelamentoImpostoResource {

    private static final Logger log = LoggerFactory.getLogger(ParcelamentoImpostoResource.class);

    private static final String ENTITY_NAME = "parcelamentoImposto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParcelamentoImpostoService parcelamentoImpostoService;

    private final ParcelamentoImpostoRepository parcelamentoImpostoRepository;

    public ParcelamentoImpostoResource(
        ParcelamentoImpostoService parcelamentoImpostoService,
        ParcelamentoImpostoRepository parcelamentoImpostoRepository
    ) {
        this.parcelamentoImpostoService = parcelamentoImpostoService;
        this.parcelamentoImpostoRepository = parcelamentoImpostoRepository;
    }

    /**
     * {@code POST  /parcelamento-impostos} : Create a new parcelamentoImposto.
     *
     * @param parcelamentoImposto the parcelamentoImposto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parcelamentoImposto, or with status {@code 400 (Bad Request)} if the parcelamentoImposto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ParcelamentoImposto> createParcelamentoImposto(@Valid @RequestBody ParcelamentoImposto parcelamentoImposto)
        throws URISyntaxException {
        log.debug("REST request to save ParcelamentoImposto : {}", parcelamentoImposto);
        if (parcelamentoImposto.getId() != null) {
            throw new BadRequestAlertException("A new parcelamentoImposto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        parcelamentoImposto = parcelamentoImpostoService.save(parcelamentoImposto);
        return ResponseEntity.created(new URI("/api/parcelamento-impostos/" + parcelamentoImposto.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, parcelamentoImposto.getId().toString()))
            .body(parcelamentoImposto);
    }

    /**
     * {@code PUT  /parcelamento-impostos/:id} : Updates an existing parcelamentoImposto.
     *
     * @param id the id of the parcelamentoImposto to save.
     * @param parcelamentoImposto the parcelamentoImposto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parcelamentoImposto,
     * or with status {@code 400 (Bad Request)} if the parcelamentoImposto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parcelamentoImposto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ParcelamentoImposto> updateParcelamentoImposto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ParcelamentoImposto parcelamentoImposto
    ) throws URISyntaxException {
        log.debug("REST request to update ParcelamentoImposto : {}, {}", id, parcelamentoImposto);
        if (parcelamentoImposto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parcelamentoImposto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parcelamentoImpostoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        parcelamentoImposto = parcelamentoImpostoService.update(parcelamentoImposto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parcelamentoImposto.getId().toString()))
            .body(parcelamentoImposto);
    }

    /**
     * {@code PATCH  /parcelamento-impostos/:id} : Partial updates given fields of an existing parcelamentoImposto, field will ignore if it is null
     *
     * @param id the id of the parcelamentoImposto to save.
     * @param parcelamentoImposto the parcelamentoImposto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parcelamentoImposto,
     * or with status {@code 400 (Bad Request)} if the parcelamentoImposto is not valid,
     * or with status {@code 404 (Not Found)} if the parcelamentoImposto is not found,
     * or with status {@code 500 (Internal Server Error)} if the parcelamentoImposto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ParcelamentoImposto> partialUpdateParcelamentoImposto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ParcelamentoImposto parcelamentoImposto
    ) throws URISyntaxException {
        log.debug("REST request to partial update ParcelamentoImposto partially : {}, {}", id, parcelamentoImposto);
        if (parcelamentoImposto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parcelamentoImposto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parcelamentoImpostoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ParcelamentoImposto> result = parcelamentoImpostoService.partialUpdate(parcelamentoImposto);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parcelamentoImposto.getId().toString())
        );
    }

    /**
     * {@code GET  /parcelamento-impostos} : get all the parcelamentoImpostos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parcelamentoImpostos in body.
     */
    @GetMapping("")
    public List<ParcelamentoImposto> getAllParcelamentoImpostos(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all ParcelamentoImpostos");
        return parcelamentoImpostoService.findAll();
    }

    /**
     * {@code GET  /parcelamento-impostos/:id} : get the "id" parcelamentoImposto.
     *
     * @param id the id of the parcelamentoImposto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parcelamentoImposto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ParcelamentoImposto> getParcelamentoImposto(@PathVariable("id") Long id) {
        log.debug("REST request to get ParcelamentoImposto : {}", id);
        Optional<ParcelamentoImposto> parcelamentoImposto = parcelamentoImpostoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parcelamentoImposto);
    }

    /**
     * {@code DELETE  /parcelamento-impostos/:id} : delete the "id" parcelamentoImposto.
     *
     * @param id the id of the parcelamentoImposto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParcelamentoImposto(@PathVariable("id") Long id) {
        log.debug("REST request to delete ParcelamentoImposto : {}", id);
        parcelamentoImpostoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
