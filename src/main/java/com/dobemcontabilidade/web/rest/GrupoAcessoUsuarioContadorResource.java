package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.GrupoAcessoUsuarioContador;
import com.dobemcontabilidade.repository.GrupoAcessoUsuarioContadorRepository;
import com.dobemcontabilidade.service.GrupoAcessoUsuarioContadorService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.GrupoAcessoUsuarioContador}.
 */
@RestController
@RequestMapping("/api/grupo-acesso-usuario-contadors")
public class GrupoAcessoUsuarioContadorResource {

    private static final Logger log = LoggerFactory.getLogger(GrupoAcessoUsuarioContadorResource.class);

    private static final String ENTITY_NAME = "grupoAcessoUsuarioContador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GrupoAcessoUsuarioContadorService grupoAcessoUsuarioContadorService;

    private final GrupoAcessoUsuarioContadorRepository grupoAcessoUsuarioContadorRepository;

    public GrupoAcessoUsuarioContadorResource(
        GrupoAcessoUsuarioContadorService grupoAcessoUsuarioContadorService,
        GrupoAcessoUsuarioContadorRepository grupoAcessoUsuarioContadorRepository
    ) {
        this.grupoAcessoUsuarioContadorService = grupoAcessoUsuarioContadorService;
        this.grupoAcessoUsuarioContadorRepository = grupoAcessoUsuarioContadorRepository;
    }

    /**
     * {@code POST  /grupo-acesso-usuario-contadors} : Create a new grupoAcessoUsuarioContador.
     *
     * @param grupoAcessoUsuarioContador the grupoAcessoUsuarioContador to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grupoAcessoUsuarioContador, or with status {@code 400 (Bad Request)} if the grupoAcessoUsuarioContador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<GrupoAcessoUsuarioContador> createGrupoAcessoUsuarioContador(
        @Valid @RequestBody GrupoAcessoUsuarioContador grupoAcessoUsuarioContador
    ) throws URISyntaxException {
        log.debug("REST request to save GrupoAcessoUsuarioContador : {}", grupoAcessoUsuarioContador);
        if (grupoAcessoUsuarioContador.getId() != null) {
            throw new BadRequestAlertException("A new grupoAcessoUsuarioContador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        grupoAcessoUsuarioContador = grupoAcessoUsuarioContadorService.save(grupoAcessoUsuarioContador);
        return ResponseEntity.created(new URI("/api/grupo-acesso-usuario-contadors/" + grupoAcessoUsuarioContador.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, grupoAcessoUsuarioContador.getId().toString())
            )
            .body(grupoAcessoUsuarioContador);
    }

    /**
     * {@code PUT  /grupo-acesso-usuario-contadors/:id} : Updates an existing grupoAcessoUsuarioContador.
     *
     * @param id the id of the grupoAcessoUsuarioContador to save.
     * @param grupoAcessoUsuarioContador the grupoAcessoUsuarioContador to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoAcessoUsuarioContador,
     * or with status {@code 400 (Bad Request)} if the grupoAcessoUsuarioContador is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grupoAcessoUsuarioContador couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<GrupoAcessoUsuarioContador> updateGrupoAcessoUsuarioContador(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GrupoAcessoUsuarioContador grupoAcessoUsuarioContador
    ) throws URISyntaxException {
        log.debug("REST request to update GrupoAcessoUsuarioContador : {}, {}", id, grupoAcessoUsuarioContador);
        if (grupoAcessoUsuarioContador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, grupoAcessoUsuarioContador.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!grupoAcessoUsuarioContadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        grupoAcessoUsuarioContador = grupoAcessoUsuarioContadorService.update(grupoAcessoUsuarioContador);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, grupoAcessoUsuarioContador.getId().toString()))
            .body(grupoAcessoUsuarioContador);
    }

    /**
     * {@code PATCH  /grupo-acesso-usuario-contadors/:id} : Partial updates given fields of an existing grupoAcessoUsuarioContador, field will ignore if it is null
     *
     * @param id the id of the grupoAcessoUsuarioContador to save.
     * @param grupoAcessoUsuarioContador the grupoAcessoUsuarioContador to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoAcessoUsuarioContador,
     * or with status {@code 400 (Bad Request)} if the grupoAcessoUsuarioContador is not valid,
     * or with status {@code 404 (Not Found)} if the grupoAcessoUsuarioContador is not found,
     * or with status {@code 500 (Internal Server Error)} if the grupoAcessoUsuarioContador couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GrupoAcessoUsuarioContador> partialUpdateGrupoAcessoUsuarioContador(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GrupoAcessoUsuarioContador grupoAcessoUsuarioContador
    ) throws URISyntaxException {
        log.debug("REST request to partial update GrupoAcessoUsuarioContador partially : {}, {}", id, grupoAcessoUsuarioContador);
        if (grupoAcessoUsuarioContador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, grupoAcessoUsuarioContador.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!grupoAcessoUsuarioContadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GrupoAcessoUsuarioContador> result = grupoAcessoUsuarioContadorService.partialUpdate(grupoAcessoUsuarioContador);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, grupoAcessoUsuarioContador.getId().toString())
        );
    }

    /**
     * {@code GET  /grupo-acesso-usuario-contadors} : get all the grupoAcessoUsuarioContadors.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grupoAcessoUsuarioContadors in body.
     */
    @GetMapping("")
    public List<GrupoAcessoUsuarioContador> getAllGrupoAcessoUsuarioContadors(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all GrupoAcessoUsuarioContadors");
        return grupoAcessoUsuarioContadorService.findAll();
    }

    /**
     * {@code GET  /grupo-acesso-usuario-contadors/:id} : get the "id" grupoAcessoUsuarioContador.
     *
     * @param id the id of the grupoAcessoUsuarioContador to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grupoAcessoUsuarioContador, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GrupoAcessoUsuarioContador> getGrupoAcessoUsuarioContador(@PathVariable("id") Long id) {
        log.debug("REST request to get GrupoAcessoUsuarioContador : {}", id);
        Optional<GrupoAcessoUsuarioContador> grupoAcessoUsuarioContador = grupoAcessoUsuarioContadorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(grupoAcessoUsuarioContador);
    }

    /**
     * {@code DELETE  /grupo-acesso-usuario-contadors/:id} : delete the "id" grupoAcessoUsuarioContador.
     *
     * @param id the id of the grupoAcessoUsuarioContador to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrupoAcessoUsuarioContador(@PathVariable("id") Long id) {
        log.debug("REST request to delete GrupoAcessoUsuarioContador : {}", id);
        grupoAcessoUsuarioContadorService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
