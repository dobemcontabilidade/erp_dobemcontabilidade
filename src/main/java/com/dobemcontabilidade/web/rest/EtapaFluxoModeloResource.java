package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.EtapaFluxoModelo;
import com.dobemcontabilidade.repository.EtapaFluxoModeloRepository;
import com.dobemcontabilidade.service.EtapaFluxoModeloService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.EtapaFluxoModelo}.
 */
@RestController
@RequestMapping("/api/etapa-fluxo-modelos")
public class EtapaFluxoModeloResource {

    private static final Logger log = LoggerFactory.getLogger(EtapaFluxoModeloResource.class);

    private static final String ENTITY_NAME = "etapaFluxoModelo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtapaFluxoModeloService etapaFluxoModeloService;

    private final EtapaFluxoModeloRepository etapaFluxoModeloRepository;

    public EtapaFluxoModeloResource(
        EtapaFluxoModeloService etapaFluxoModeloService,
        EtapaFluxoModeloRepository etapaFluxoModeloRepository
    ) {
        this.etapaFluxoModeloService = etapaFluxoModeloService;
        this.etapaFluxoModeloRepository = etapaFluxoModeloRepository;
    }

    /**
     * {@code POST  /etapa-fluxo-modelos} : Create a new etapaFluxoModelo.
     *
     * @param etapaFluxoModelo the etapaFluxoModelo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etapaFluxoModelo, or with status {@code 400 (Bad Request)} if the etapaFluxoModelo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EtapaFluxoModelo> createEtapaFluxoModelo(@Valid @RequestBody EtapaFluxoModelo etapaFluxoModelo)
        throws URISyntaxException {
        log.debug("REST request to save EtapaFluxoModelo : {}", etapaFluxoModelo);
        if (etapaFluxoModelo.getId() != null) {
            throw new BadRequestAlertException("A new etapaFluxoModelo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        etapaFluxoModelo = etapaFluxoModeloService.save(etapaFluxoModelo);
        return ResponseEntity.created(new URI("/api/etapa-fluxo-modelos/" + etapaFluxoModelo.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, etapaFluxoModelo.getId().toString()))
            .body(etapaFluxoModelo);
    }

    /**
     * {@code PUT  /etapa-fluxo-modelos/:id} : Updates an existing etapaFluxoModelo.
     *
     * @param id the id of the etapaFluxoModelo to save.
     * @param etapaFluxoModelo the etapaFluxoModelo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etapaFluxoModelo,
     * or with status {@code 400 (Bad Request)} if the etapaFluxoModelo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etapaFluxoModelo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EtapaFluxoModelo> updateEtapaFluxoModelo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EtapaFluxoModelo etapaFluxoModelo
    ) throws URISyntaxException {
        log.debug("REST request to update EtapaFluxoModelo : {}, {}", id, etapaFluxoModelo);
        if (etapaFluxoModelo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etapaFluxoModelo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etapaFluxoModeloRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        etapaFluxoModelo = etapaFluxoModeloService.update(etapaFluxoModelo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etapaFluxoModelo.getId().toString()))
            .body(etapaFluxoModelo);
    }

    /**
     * {@code PATCH  /etapa-fluxo-modelos/:id} : Partial updates given fields of an existing etapaFluxoModelo, field will ignore if it is null
     *
     * @param id the id of the etapaFluxoModelo to save.
     * @param etapaFluxoModelo the etapaFluxoModelo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etapaFluxoModelo,
     * or with status {@code 400 (Bad Request)} if the etapaFluxoModelo is not valid,
     * or with status {@code 404 (Not Found)} if the etapaFluxoModelo is not found,
     * or with status {@code 500 (Internal Server Error)} if the etapaFluxoModelo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EtapaFluxoModelo> partialUpdateEtapaFluxoModelo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EtapaFluxoModelo etapaFluxoModelo
    ) throws URISyntaxException {
        log.debug("REST request to partial update EtapaFluxoModelo partially : {}, {}", id, etapaFluxoModelo);
        if (etapaFluxoModelo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etapaFluxoModelo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etapaFluxoModeloRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EtapaFluxoModelo> result = etapaFluxoModeloService.partialUpdate(etapaFluxoModelo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etapaFluxoModelo.getId().toString())
        );
    }

    /**
     * {@code GET  /etapa-fluxo-modelos} : get all the etapaFluxoModelos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etapaFluxoModelos in body.
     */
    @GetMapping("")
    public List<EtapaFluxoModelo> getAllEtapaFluxoModelos(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all EtapaFluxoModelos");
        return etapaFluxoModeloService.findAll();
    }

    /**
     * {@code GET  /etapa-fluxo-modelos/:id} : get the "id" etapaFluxoModelo.
     *
     * @param id the id of the etapaFluxoModelo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etapaFluxoModelo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EtapaFluxoModelo> getEtapaFluxoModelo(@PathVariable("id") Long id) {
        log.debug("REST request to get EtapaFluxoModelo : {}", id);
        Optional<EtapaFluxoModelo> etapaFluxoModelo = etapaFluxoModeloService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etapaFluxoModelo);
    }

    /**
     * {@code DELETE  /etapa-fluxo-modelos/:id} : delete the "id" etapaFluxoModelo.
     *
     * @param id the id of the etapaFluxoModelo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEtapaFluxoModelo(@PathVariable("id") Long id) {
        log.debug("REST request to delete EtapaFluxoModelo : {}", id);
        etapaFluxoModeloService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
