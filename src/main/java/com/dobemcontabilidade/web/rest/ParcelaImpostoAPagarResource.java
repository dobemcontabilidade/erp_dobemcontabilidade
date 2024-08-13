package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.ParcelaImpostoAPagar;
import com.dobemcontabilidade.repository.ParcelaImpostoAPagarRepository;
import com.dobemcontabilidade.service.ParcelaImpostoAPagarService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.ParcelaImpostoAPagar}.
 */
@RestController
@RequestMapping("/api/parcela-imposto-a-pagars")
public class ParcelaImpostoAPagarResource {

    private static final Logger log = LoggerFactory.getLogger(ParcelaImpostoAPagarResource.class);

    private static final String ENTITY_NAME = "parcelaImpostoAPagar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParcelaImpostoAPagarService parcelaImpostoAPagarService;

    private final ParcelaImpostoAPagarRepository parcelaImpostoAPagarRepository;

    public ParcelaImpostoAPagarResource(
        ParcelaImpostoAPagarService parcelaImpostoAPagarService,
        ParcelaImpostoAPagarRepository parcelaImpostoAPagarRepository
    ) {
        this.parcelaImpostoAPagarService = parcelaImpostoAPagarService;
        this.parcelaImpostoAPagarRepository = parcelaImpostoAPagarRepository;
    }

    /**
     * {@code POST  /parcela-imposto-a-pagars} : Create a new parcelaImpostoAPagar.
     *
     * @param parcelaImpostoAPagar the parcelaImpostoAPagar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parcelaImpostoAPagar, or with status {@code 400 (Bad Request)} if the parcelaImpostoAPagar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ParcelaImpostoAPagar> createParcelaImpostoAPagar(@Valid @RequestBody ParcelaImpostoAPagar parcelaImpostoAPagar)
        throws URISyntaxException {
        log.debug("REST request to save ParcelaImpostoAPagar : {}", parcelaImpostoAPagar);
        if (parcelaImpostoAPagar.getId() != null) {
            throw new BadRequestAlertException("A new parcelaImpostoAPagar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        parcelaImpostoAPagar = parcelaImpostoAPagarService.save(parcelaImpostoAPagar);
        return ResponseEntity.created(new URI("/api/parcela-imposto-a-pagars/" + parcelaImpostoAPagar.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, parcelaImpostoAPagar.getId().toString()))
            .body(parcelaImpostoAPagar);
    }

    /**
     * {@code PUT  /parcela-imposto-a-pagars/:id} : Updates an existing parcelaImpostoAPagar.
     *
     * @param id the id of the parcelaImpostoAPagar to save.
     * @param parcelaImpostoAPagar the parcelaImpostoAPagar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parcelaImpostoAPagar,
     * or with status {@code 400 (Bad Request)} if the parcelaImpostoAPagar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parcelaImpostoAPagar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ParcelaImpostoAPagar> updateParcelaImpostoAPagar(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ParcelaImpostoAPagar parcelaImpostoAPagar
    ) throws URISyntaxException {
        log.debug("REST request to update ParcelaImpostoAPagar : {}, {}", id, parcelaImpostoAPagar);
        if (parcelaImpostoAPagar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parcelaImpostoAPagar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parcelaImpostoAPagarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        parcelaImpostoAPagar = parcelaImpostoAPagarService.update(parcelaImpostoAPagar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parcelaImpostoAPagar.getId().toString()))
            .body(parcelaImpostoAPagar);
    }

    /**
     * {@code PATCH  /parcela-imposto-a-pagars/:id} : Partial updates given fields of an existing parcelaImpostoAPagar, field will ignore if it is null
     *
     * @param id the id of the parcelaImpostoAPagar to save.
     * @param parcelaImpostoAPagar the parcelaImpostoAPagar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parcelaImpostoAPagar,
     * or with status {@code 400 (Bad Request)} if the parcelaImpostoAPagar is not valid,
     * or with status {@code 404 (Not Found)} if the parcelaImpostoAPagar is not found,
     * or with status {@code 500 (Internal Server Error)} if the parcelaImpostoAPagar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ParcelaImpostoAPagar> partialUpdateParcelaImpostoAPagar(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ParcelaImpostoAPagar parcelaImpostoAPagar
    ) throws URISyntaxException {
        log.debug("REST request to partial update ParcelaImpostoAPagar partially : {}, {}", id, parcelaImpostoAPagar);
        if (parcelaImpostoAPagar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parcelaImpostoAPagar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parcelaImpostoAPagarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ParcelaImpostoAPagar> result = parcelaImpostoAPagarService.partialUpdate(parcelaImpostoAPagar);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parcelaImpostoAPagar.getId().toString())
        );
    }

    /**
     * {@code GET  /parcela-imposto-a-pagars} : get all the parcelaImpostoAPagars.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parcelaImpostoAPagars in body.
     */
    @GetMapping("")
    public List<ParcelaImpostoAPagar> getAllParcelaImpostoAPagars() {
        log.debug("REST request to get all ParcelaImpostoAPagars");
        return parcelaImpostoAPagarService.findAll();
    }

    /**
     * {@code GET  /parcela-imposto-a-pagars/:id} : get the "id" parcelaImpostoAPagar.
     *
     * @param id the id of the parcelaImpostoAPagar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parcelaImpostoAPagar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ParcelaImpostoAPagar> getParcelaImpostoAPagar(@PathVariable("id") Long id) {
        log.debug("REST request to get ParcelaImpostoAPagar : {}", id);
        Optional<ParcelaImpostoAPagar> parcelaImpostoAPagar = parcelaImpostoAPagarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parcelaImpostoAPagar);
    }

    /**
     * {@code DELETE  /parcela-imposto-a-pagars/:id} : delete the "id" parcelaImpostoAPagar.
     *
     * @param id the id of the parcelaImpostoAPagar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParcelaImpostoAPagar(@PathVariable("id") Long id) {
        log.debug("REST request to delete ParcelaImpostoAPagar : {}", id);
        parcelaImpostoAPagarService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
