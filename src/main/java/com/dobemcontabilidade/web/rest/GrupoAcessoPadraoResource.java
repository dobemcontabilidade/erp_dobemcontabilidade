package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.GrupoAcessoPadrao;
import com.dobemcontabilidade.repository.GrupoAcessoPadraoRepository;
import com.dobemcontabilidade.service.GrupoAcessoPadraoService;
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
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dobemcontabilidade.domain.GrupoAcessoPadrao}.
 */
@RestController
@RequestMapping("/api/grupo-acesso-padraos")
public class GrupoAcessoPadraoResource {

    private static final Logger log = LoggerFactory.getLogger(GrupoAcessoPadraoResource.class);

    private static final String ENTITY_NAME = "grupoAcessoPadrao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GrupoAcessoPadraoService grupoAcessoPadraoService;

    private final GrupoAcessoPadraoRepository grupoAcessoPadraoRepository;

    public GrupoAcessoPadraoResource(
        GrupoAcessoPadraoService grupoAcessoPadraoService,
        GrupoAcessoPadraoRepository grupoAcessoPadraoRepository
    ) {
        this.grupoAcessoPadraoService = grupoAcessoPadraoService;
        this.grupoAcessoPadraoRepository = grupoAcessoPadraoRepository;
    }

    /**
     * {@code POST  /grupo-acesso-padraos} : Create a new grupoAcessoPadrao.
     *
     * @param grupoAcessoPadrao the grupoAcessoPadrao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grupoAcessoPadrao, or with status {@code 400 (Bad Request)} if the grupoAcessoPadrao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<GrupoAcessoPadrao> createGrupoAcessoPadrao(@RequestBody GrupoAcessoPadrao grupoAcessoPadrao)
        throws URISyntaxException {
        log.debug("REST request to save GrupoAcessoPadrao : {}", grupoAcessoPadrao);
        if (grupoAcessoPadrao.getId() != null) {
            throw new BadRequestAlertException("A new grupoAcessoPadrao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        grupoAcessoPadrao = grupoAcessoPadraoService.save(grupoAcessoPadrao);
        return ResponseEntity.created(new URI("/api/grupo-acesso-padraos/" + grupoAcessoPadrao.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, grupoAcessoPadrao.getId().toString()))
            .body(grupoAcessoPadrao);
    }

    /**
     * {@code PUT  /grupo-acesso-padraos/:id} : Updates an existing grupoAcessoPadrao.
     *
     * @param id the id of the grupoAcessoPadrao to save.
     * @param grupoAcessoPadrao the grupoAcessoPadrao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoAcessoPadrao,
     * or with status {@code 400 (Bad Request)} if the grupoAcessoPadrao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grupoAcessoPadrao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<GrupoAcessoPadrao> updateGrupoAcessoPadrao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GrupoAcessoPadrao grupoAcessoPadrao
    ) throws URISyntaxException {
        log.debug("REST request to update GrupoAcessoPadrao : {}, {}", id, grupoAcessoPadrao);
        if (grupoAcessoPadrao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, grupoAcessoPadrao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!grupoAcessoPadraoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        grupoAcessoPadrao = grupoAcessoPadraoService.update(grupoAcessoPadrao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, grupoAcessoPadrao.getId().toString()))
            .body(grupoAcessoPadrao);
    }

    /**
     * {@code PATCH  /grupo-acesso-padraos/:id} : Partial updates given fields of an existing grupoAcessoPadrao, field will ignore if it is null
     *
     * @param id the id of the grupoAcessoPadrao to save.
     * @param grupoAcessoPadrao the grupoAcessoPadrao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoAcessoPadrao,
     * or with status {@code 400 (Bad Request)} if the grupoAcessoPadrao is not valid,
     * or with status {@code 404 (Not Found)} if the grupoAcessoPadrao is not found,
     * or with status {@code 500 (Internal Server Error)} if the grupoAcessoPadrao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GrupoAcessoPadrao> partialUpdateGrupoAcessoPadrao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GrupoAcessoPadrao grupoAcessoPadrao
    ) throws URISyntaxException {
        log.debug("REST request to partial update GrupoAcessoPadrao partially : {}, {}", id, grupoAcessoPadrao);
        if (grupoAcessoPadrao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, grupoAcessoPadrao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!grupoAcessoPadraoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GrupoAcessoPadrao> result = grupoAcessoPadraoService.partialUpdate(grupoAcessoPadrao);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, grupoAcessoPadrao.getId().toString())
        );
    }

    /**
     * {@code GET  /grupo-acesso-padraos} : get all the grupoAcessoPadraos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grupoAcessoPadraos in body.
     */
    @GetMapping("")
    public List<GrupoAcessoPadrao> getAllGrupoAcessoPadraos() {
        log.debug("REST request to get all GrupoAcessoPadraos");
        return grupoAcessoPadraoService.findAll();
    }

    /**
     * {@code GET  /grupo-acesso-padraos/:id} : get the "id" grupoAcessoPadrao.
     *
     * @param id the id of the grupoAcessoPadrao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grupoAcessoPadrao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GrupoAcessoPadrao> getGrupoAcessoPadrao(@PathVariable("id") Long id) {
        log.debug("REST request to get GrupoAcessoPadrao : {}", id);
        Optional<GrupoAcessoPadrao> grupoAcessoPadrao = grupoAcessoPadraoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(grupoAcessoPadrao);
    }

    /**
     * {@code DELETE  /grupo-acesso-padraos/:id} : delete the "id" grupoAcessoPadrao.
     *
     * @param id the id of the grupoAcessoPadrao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrupoAcessoPadrao(@PathVariable("id") Long id) {
        log.debug("REST request to delete GrupoAcessoPadrao : {}", id);
        grupoAcessoPadraoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
