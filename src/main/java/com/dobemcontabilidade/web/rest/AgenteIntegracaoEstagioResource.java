package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.AgenteIntegracaoEstagio;
import com.dobemcontabilidade.repository.AgenteIntegracaoEstagioRepository;
import com.dobemcontabilidade.service.AgenteIntegracaoEstagioService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AgenteIntegracaoEstagio}.
 */
@RestController
@RequestMapping("/api/agente-integracao-estagios")
public class AgenteIntegracaoEstagioResource {

    private static final Logger log = LoggerFactory.getLogger(AgenteIntegracaoEstagioResource.class);

    private static final String ENTITY_NAME = "agenteIntegracaoEstagio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgenteIntegracaoEstagioService agenteIntegracaoEstagioService;

    private final AgenteIntegracaoEstagioRepository agenteIntegracaoEstagioRepository;

    public AgenteIntegracaoEstagioResource(
        AgenteIntegracaoEstagioService agenteIntegracaoEstagioService,
        AgenteIntegracaoEstagioRepository agenteIntegracaoEstagioRepository
    ) {
        this.agenteIntegracaoEstagioService = agenteIntegracaoEstagioService;
        this.agenteIntegracaoEstagioRepository = agenteIntegracaoEstagioRepository;
    }

    /**
     * {@code POST  /agente-integracao-estagios} : Create a new agenteIntegracaoEstagio.
     *
     * @param agenteIntegracaoEstagio the agenteIntegracaoEstagio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agenteIntegracaoEstagio, or with status {@code 400 (Bad Request)} if the agenteIntegracaoEstagio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AgenteIntegracaoEstagio> createAgenteIntegracaoEstagio(
        @Valid @RequestBody AgenteIntegracaoEstagio agenteIntegracaoEstagio
    ) throws URISyntaxException {
        log.debug("REST request to save AgenteIntegracaoEstagio : {}", agenteIntegracaoEstagio);
        if (agenteIntegracaoEstagio.getId() != null) {
            throw new BadRequestAlertException("A new agenteIntegracaoEstagio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        agenteIntegracaoEstagio = agenteIntegracaoEstagioService.save(agenteIntegracaoEstagio);
        return ResponseEntity.created(new URI("/api/agente-integracao-estagios/" + agenteIntegracaoEstagio.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, agenteIntegracaoEstagio.getId().toString()))
            .body(agenteIntegracaoEstagio);
    }

    /**
     * {@code PUT  /agente-integracao-estagios/:id} : Updates an existing agenteIntegracaoEstagio.
     *
     * @param id the id of the agenteIntegracaoEstagio to save.
     * @param agenteIntegracaoEstagio the agenteIntegracaoEstagio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agenteIntegracaoEstagio,
     * or with status {@code 400 (Bad Request)} if the agenteIntegracaoEstagio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agenteIntegracaoEstagio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AgenteIntegracaoEstagio> updateAgenteIntegracaoEstagio(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AgenteIntegracaoEstagio agenteIntegracaoEstagio
    ) throws URISyntaxException {
        log.debug("REST request to update AgenteIntegracaoEstagio : {}, {}", id, agenteIntegracaoEstagio);
        if (agenteIntegracaoEstagio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, agenteIntegracaoEstagio.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!agenteIntegracaoEstagioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        agenteIntegracaoEstagio = agenteIntegracaoEstagioService.update(agenteIntegracaoEstagio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agenteIntegracaoEstagio.getId().toString()))
            .body(agenteIntegracaoEstagio);
    }

    /**
     * {@code PATCH  /agente-integracao-estagios/:id} : Partial updates given fields of an existing agenteIntegracaoEstagio, field will ignore if it is null
     *
     * @param id the id of the agenteIntegracaoEstagio to save.
     * @param agenteIntegracaoEstagio the agenteIntegracaoEstagio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agenteIntegracaoEstagio,
     * or with status {@code 400 (Bad Request)} if the agenteIntegracaoEstagio is not valid,
     * or with status {@code 404 (Not Found)} if the agenteIntegracaoEstagio is not found,
     * or with status {@code 500 (Internal Server Error)} if the agenteIntegracaoEstagio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AgenteIntegracaoEstagio> partialUpdateAgenteIntegracaoEstagio(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AgenteIntegracaoEstagio agenteIntegracaoEstagio
    ) throws URISyntaxException {
        log.debug("REST request to partial update AgenteIntegracaoEstagio partially : {}, {}", id, agenteIntegracaoEstagio);
        if (agenteIntegracaoEstagio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, agenteIntegracaoEstagio.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!agenteIntegracaoEstagioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AgenteIntegracaoEstagio> result = agenteIntegracaoEstagioService.partialUpdate(agenteIntegracaoEstagio);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agenteIntegracaoEstagio.getId().toString())
        );
    }

    /**
     * {@code GET  /agente-integracao-estagios} : get all the agenteIntegracaoEstagios.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agenteIntegracaoEstagios in body.
     */
    @GetMapping("")
    public List<AgenteIntegracaoEstagio> getAllAgenteIntegracaoEstagios(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all AgenteIntegracaoEstagios");
        return agenteIntegracaoEstagioService.findAll();
    }

    /**
     * {@code GET  /agente-integracao-estagios/:id} : get the "id" agenteIntegracaoEstagio.
     *
     * @param id the id of the agenteIntegracaoEstagio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agenteIntegracaoEstagio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AgenteIntegracaoEstagio> getAgenteIntegracaoEstagio(@PathVariable("id") Long id) {
        log.debug("REST request to get AgenteIntegracaoEstagio : {}", id);
        Optional<AgenteIntegracaoEstagio> agenteIntegracaoEstagio = agenteIntegracaoEstagioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(agenteIntegracaoEstagio);
    }

    /**
     * {@code DELETE  /agente-integracao-estagios/:id} : delete the "id" agenteIntegracaoEstagio.
     *
     * @param id the id of the agenteIntegracaoEstagio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgenteIntegracaoEstagio(@PathVariable("id") Long id) {
        log.debug("REST request to delete AgenteIntegracaoEstagio : {}", id);
        agenteIntegracaoEstagioService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
