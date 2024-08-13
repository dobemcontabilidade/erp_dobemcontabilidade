package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.AtorAvaliado;
import com.dobemcontabilidade.repository.AtorAvaliadoRepository;
import com.dobemcontabilidade.service.AtorAvaliadoService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AtorAvaliado}.
 */
@RestController
@RequestMapping("/api/ator-avaliados")
public class AtorAvaliadoResource {

    private static final Logger log = LoggerFactory.getLogger(AtorAvaliadoResource.class);

    private static final String ENTITY_NAME = "atorAvaliado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AtorAvaliadoService atorAvaliadoService;

    private final AtorAvaliadoRepository atorAvaliadoRepository;

    public AtorAvaliadoResource(AtorAvaliadoService atorAvaliadoService, AtorAvaliadoRepository atorAvaliadoRepository) {
        this.atorAvaliadoService = atorAvaliadoService;
        this.atorAvaliadoRepository = atorAvaliadoRepository;
    }

    /**
     * {@code POST  /ator-avaliados} : Create a new atorAvaliado.
     *
     * @param atorAvaliado the atorAvaliado to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new atorAvaliado, or with status {@code 400 (Bad Request)} if the atorAvaliado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AtorAvaliado> createAtorAvaliado(@Valid @RequestBody AtorAvaliado atorAvaliado) throws URISyntaxException {
        log.debug("REST request to save AtorAvaliado : {}", atorAvaliado);
        if (atorAvaliado.getId() != null) {
            throw new BadRequestAlertException("A new atorAvaliado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        atorAvaliado = atorAvaliadoService.save(atorAvaliado);
        return ResponseEntity.created(new URI("/api/ator-avaliados/" + atorAvaliado.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, atorAvaliado.getId().toString()))
            .body(atorAvaliado);
    }

    /**
     * {@code PUT  /ator-avaliados/:id} : Updates an existing atorAvaliado.
     *
     * @param id the id of the atorAvaliado to save.
     * @param atorAvaliado the atorAvaliado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated atorAvaliado,
     * or with status {@code 400 (Bad Request)} if the atorAvaliado is not valid,
     * or with status {@code 500 (Internal Server Error)} if the atorAvaliado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AtorAvaliado> updateAtorAvaliado(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AtorAvaliado atorAvaliado
    ) throws URISyntaxException {
        log.debug("REST request to update AtorAvaliado : {}, {}", id, atorAvaliado);
        if (atorAvaliado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, atorAvaliado.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!atorAvaliadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        atorAvaliado = atorAvaliadoService.update(atorAvaliado);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, atorAvaliado.getId().toString()))
            .body(atorAvaliado);
    }

    /**
     * {@code PATCH  /ator-avaliados/:id} : Partial updates given fields of an existing atorAvaliado, field will ignore if it is null
     *
     * @param id the id of the atorAvaliado to save.
     * @param atorAvaliado the atorAvaliado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated atorAvaliado,
     * or with status {@code 400 (Bad Request)} if the atorAvaliado is not valid,
     * or with status {@code 404 (Not Found)} if the atorAvaliado is not found,
     * or with status {@code 500 (Internal Server Error)} if the atorAvaliado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AtorAvaliado> partialUpdateAtorAvaliado(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AtorAvaliado atorAvaliado
    ) throws URISyntaxException {
        log.debug("REST request to partial update AtorAvaliado partially : {}, {}", id, atorAvaliado);
        if (atorAvaliado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, atorAvaliado.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!atorAvaliadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AtorAvaliado> result = atorAvaliadoService.partialUpdate(atorAvaliado);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, atorAvaliado.getId().toString())
        );
    }

    /**
     * {@code GET  /ator-avaliados} : get all the atorAvaliados.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of atorAvaliados in body.
     */
    @GetMapping("")
    public List<AtorAvaliado> getAllAtorAvaliados() {
        log.debug("REST request to get all AtorAvaliados");
        return atorAvaliadoService.findAll();
    }

    /**
     * {@code GET  /ator-avaliados/:id} : get the "id" atorAvaliado.
     *
     * @param id the id of the atorAvaliado to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the atorAvaliado, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AtorAvaliado> getAtorAvaliado(@PathVariable("id") Long id) {
        log.debug("REST request to get AtorAvaliado : {}", id);
        Optional<AtorAvaliado> atorAvaliado = atorAvaliadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(atorAvaliado);
    }

    /**
     * {@code DELETE  /ator-avaliados/:id} : delete the "id" atorAvaliado.
     *
     * @param id the id of the atorAvaliado to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAtorAvaliado(@PathVariable("id") Long id) {
        log.debug("REST request to delete AtorAvaliado : {}", id);
        atorAvaliadoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
