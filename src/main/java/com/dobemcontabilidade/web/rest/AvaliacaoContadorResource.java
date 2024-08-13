package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.AvaliacaoContador;
import com.dobemcontabilidade.repository.AvaliacaoContadorRepository;
import com.dobemcontabilidade.service.AvaliacaoContadorService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AvaliacaoContador}.
 */
@RestController
@RequestMapping("/api/avaliacao-contadors")
public class AvaliacaoContadorResource {

    private static final Logger log = LoggerFactory.getLogger(AvaliacaoContadorResource.class);

    private static final String ENTITY_NAME = "avaliacaoContador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvaliacaoContadorService avaliacaoContadorService;

    private final AvaliacaoContadorRepository avaliacaoContadorRepository;

    public AvaliacaoContadorResource(
        AvaliacaoContadorService avaliacaoContadorService,
        AvaliacaoContadorRepository avaliacaoContadorRepository
    ) {
        this.avaliacaoContadorService = avaliacaoContadorService;
        this.avaliacaoContadorRepository = avaliacaoContadorRepository;
    }

    /**
     * {@code POST  /avaliacao-contadors} : Create a new avaliacaoContador.
     *
     * @param avaliacaoContador the avaliacaoContador to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new avaliacaoContador, or with status {@code 400 (Bad Request)} if the avaliacaoContador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AvaliacaoContador> createAvaliacaoContador(@Valid @RequestBody AvaliacaoContador avaliacaoContador)
        throws URISyntaxException {
        log.debug("REST request to save AvaliacaoContador : {}", avaliacaoContador);
        if (avaliacaoContador.getId() != null) {
            throw new BadRequestAlertException("A new avaliacaoContador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        avaliacaoContador = avaliacaoContadorService.save(avaliacaoContador);
        return ResponseEntity.created(new URI("/api/avaliacao-contadors/" + avaliacaoContador.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, avaliacaoContador.getId().toString()))
            .body(avaliacaoContador);
    }

    /**
     * {@code PUT  /avaliacao-contadors/:id} : Updates an existing avaliacaoContador.
     *
     * @param id the id of the avaliacaoContador to save.
     * @param avaliacaoContador the avaliacaoContador to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avaliacaoContador,
     * or with status {@code 400 (Bad Request)} if the avaliacaoContador is not valid,
     * or with status {@code 500 (Internal Server Error)} if the avaliacaoContador couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoContador> updateAvaliacaoContador(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AvaliacaoContador avaliacaoContador
    ) throws URISyntaxException {
        log.debug("REST request to update AvaliacaoContador : {}, {}", id, avaliacaoContador);
        if (avaliacaoContador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, avaliacaoContador.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!avaliacaoContadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        avaliacaoContador = avaliacaoContadorService.update(avaliacaoContador);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avaliacaoContador.getId().toString()))
            .body(avaliacaoContador);
    }

    /**
     * {@code PATCH  /avaliacao-contadors/:id} : Partial updates given fields of an existing avaliacaoContador, field will ignore if it is null
     *
     * @param id the id of the avaliacaoContador to save.
     * @param avaliacaoContador the avaliacaoContador to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avaliacaoContador,
     * or with status {@code 400 (Bad Request)} if the avaliacaoContador is not valid,
     * or with status {@code 404 (Not Found)} if the avaliacaoContador is not found,
     * or with status {@code 500 (Internal Server Error)} if the avaliacaoContador couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AvaliacaoContador> partialUpdateAvaliacaoContador(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AvaliacaoContador avaliacaoContador
    ) throws URISyntaxException {
        log.debug("REST request to partial update AvaliacaoContador partially : {}, {}", id, avaliacaoContador);
        if (avaliacaoContador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, avaliacaoContador.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!avaliacaoContadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AvaliacaoContador> result = avaliacaoContadorService.partialUpdate(avaliacaoContador);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avaliacaoContador.getId().toString())
        );
    }

    /**
     * {@code GET  /avaliacao-contadors} : get all the avaliacaoContadors.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of avaliacaoContadors in body.
     */
    @GetMapping("")
    public List<AvaliacaoContador> getAllAvaliacaoContadors(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all AvaliacaoContadors");
        return avaliacaoContadorService.findAll();
    }

    /**
     * {@code GET  /avaliacao-contadors/:id} : get the "id" avaliacaoContador.
     *
     * @param id the id of the avaliacaoContador to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avaliacaoContador, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoContador> getAvaliacaoContador(@PathVariable("id") Long id) {
        log.debug("REST request to get AvaliacaoContador : {}", id);
        Optional<AvaliacaoContador> avaliacaoContador = avaliacaoContadorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(avaliacaoContador);
    }

    /**
     * {@code DELETE  /avaliacao-contadors/:id} : delete the "id" avaliacaoContador.
     *
     * @param id the id of the avaliacaoContador to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvaliacaoContador(@PathVariable("id") Long id) {
        log.debug("REST request to delete AvaliacaoContador : {}", id);
        avaliacaoContadorService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
