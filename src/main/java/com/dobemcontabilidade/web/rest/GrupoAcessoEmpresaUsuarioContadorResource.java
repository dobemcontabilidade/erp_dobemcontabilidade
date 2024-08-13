package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.GrupoAcessoEmpresaUsuarioContador;
import com.dobemcontabilidade.repository.GrupoAcessoEmpresaUsuarioContadorRepository;
import com.dobemcontabilidade.service.GrupoAcessoEmpresaUsuarioContadorService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.GrupoAcessoEmpresaUsuarioContador}.
 */
@RestController
@RequestMapping("/api/grupo-acesso-empresa-usuario-contadors")
public class GrupoAcessoEmpresaUsuarioContadorResource {

    private static final Logger log = LoggerFactory.getLogger(GrupoAcessoEmpresaUsuarioContadorResource.class);

    private static final String ENTITY_NAME = "grupoAcessoEmpresaUsuarioContador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GrupoAcessoEmpresaUsuarioContadorService grupoAcessoEmpresaUsuarioContadorService;

    private final GrupoAcessoEmpresaUsuarioContadorRepository grupoAcessoEmpresaUsuarioContadorRepository;

    public GrupoAcessoEmpresaUsuarioContadorResource(
        GrupoAcessoEmpresaUsuarioContadorService grupoAcessoEmpresaUsuarioContadorService,
        GrupoAcessoEmpresaUsuarioContadorRepository grupoAcessoEmpresaUsuarioContadorRepository
    ) {
        this.grupoAcessoEmpresaUsuarioContadorService = grupoAcessoEmpresaUsuarioContadorService;
        this.grupoAcessoEmpresaUsuarioContadorRepository = grupoAcessoEmpresaUsuarioContadorRepository;
    }

    /**
     * {@code POST  /grupo-acesso-empresa-usuario-contadors} : Create a new grupoAcessoEmpresaUsuarioContador.
     *
     * @param grupoAcessoEmpresaUsuarioContador the grupoAcessoEmpresaUsuarioContador to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grupoAcessoEmpresaUsuarioContador, or with status {@code 400 (Bad Request)} if the grupoAcessoEmpresaUsuarioContador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<GrupoAcessoEmpresaUsuarioContador> createGrupoAcessoEmpresaUsuarioContador(
        @Valid @RequestBody GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresaUsuarioContador
    ) throws URISyntaxException {
        log.debug("REST request to save GrupoAcessoEmpresaUsuarioContador : {}", grupoAcessoEmpresaUsuarioContador);
        if (grupoAcessoEmpresaUsuarioContador.getId() != null) {
            throw new BadRequestAlertException(
                "A new grupoAcessoEmpresaUsuarioContador cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        grupoAcessoEmpresaUsuarioContador = grupoAcessoEmpresaUsuarioContadorService.save(grupoAcessoEmpresaUsuarioContador);
        return ResponseEntity.created(new URI("/api/grupo-acesso-empresa-usuario-contadors/" + grupoAcessoEmpresaUsuarioContador.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    grupoAcessoEmpresaUsuarioContador.getId().toString()
                )
            )
            .body(grupoAcessoEmpresaUsuarioContador);
    }

    /**
     * {@code PUT  /grupo-acesso-empresa-usuario-contadors/:id} : Updates an existing grupoAcessoEmpresaUsuarioContador.
     *
     * @param id the id of the grupoAcessoEmpresaUsuarioContador to save.
     * @param grupoAcessoEmpresaUsuarioContador the grupoAcessoEmpresaUsuarioContador to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoAcessoEmpresaUsuarioContador,
     * or with status {@code 400 (Bad Request)} if the grupoAcessoEmpresaUsuarioContador is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grupoAcessoEmpresaUsuarioContador couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<GrupoAcessoEmpresaUsuarioContador> updateGrupoAcessoEmpresaUsuarioContador(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresaUsuarioContador
    ) throws URISyntaxException {
        log.debug("REST request to update GrupoAcessoEmpresaUsuarioContador : {}, {}", id, grupoAcessoEmpresaUsuarioContador);
        if (grupoAcessoEmpresaUsuarioContador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, grupoAcessoEmpresaUsuarioContador.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!grupoAcessoEmpresaUsuarioContadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        grupoAcessoEmpresaUsuarioContador = grupoAcessoEmpresaUsuarioContadorService.update(grupoAcessoEmpresaUsuarioContador);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, grupoAcessoEmpresaUsuarioContador.getId().toString())
            )
            .body(grupoAcessoEmpresaUsuarioContador);
    }

    /**
     * {@code PATCH  /grupo-acesso-empresa-usuario-contadors/:id} : Partial updates given fields of an existing grupoAcessoEmpresaUsuarioContador, field will ignore if it is null
     *
     * @param id the id of the grupoAcessoEmpresaUsuarioContador to save.
     * @param grupoAcessoEmpresaUsuarioContador the grupoAcessoEmpresaUsuarioContador to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoAcessoEmpresaUsuarioContador,
     * or with status {@code 400 (Bad Request)} if the grupoAcessoEmpresaUsuarioContador is not valid,
     * or with status {@code 404 (Not Found)} if the grupoAcessoEmpresaUsuarioContador is not found,
     * or with status {@code 500 (Internal Server Error)} if the grupoAcessoEmpresaUsuarioContador couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GrupoAcessoEmpresaUsuarioContador> partialUpdateGrupoAcessoEmpresaUsuarioContador(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresaUsuarioContador
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update GrupoAcessoEmpresaUsuarioContador partially : {}, {}",
            id,
            grupoAcessoEmpresaUsuarioContador
        );
        if (grupoAcessoEmpresaUsuarioContador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, grupoAcessoEmpresaUsuarioContador.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!grupoAcessoEmpresaUsuarioContadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GrupoAcessoEmpresaUsuarioContador> result = grupoAcessoEmpresaUsuarioContadorService.partialUpdate(
            grupoAcessoEmpresaUsuarioContador
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, grupoAcessoEmpresaUsuarioContador.getId().toString())
        );
    }

    /**
     * {@code GET  /grupo-acesso-empresa-usuario-contadors} : get all the grupoAcessoEmpresaUsuarioContadors.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grupoAcessoEmpresaUsuarioContadors in body.
     */
    @GetMapping("")
    public List<GrupoAcessoEmpresaUsuarioContador> getAllGrupoAcessoEmpresaUsuarioContadors(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all GrupoAcessoEmpresaUsuarioContadors");
        return grupoAcessoEmpresaUsuarioContadorService.findAll();
    }

    /**
     * {@code GET  /grupo-acesso-empresa-usuario-contadors/:id} : get the "id" grupoAcessoEmpresaUsuarioContador.
     *
     * @param id the id of the grupoAcessoEmpresaUsuarioContador to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grupoAcessoEmpresaUsuarioContador, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GrupoAcessoEmpresaUsuarioContador> getGrupoAcessoEmpresaUsuarioContador(@PathVariable("id") Long id) {
        log.debug("REST request to get GrupoAcessoEmpresaUsuarioContador : {}", id);
        Optional<GrupoAcessoEmpresaUsuarioContador> grupoAcessoEmpresaUsuarioContador = grupoAcessoEmpresaUsuarioContadorService.findOne(
            id
        );
        return ResponseUtil.wrapOrNotFound(grupoAcessoEmpresaUsuarioContador);
    }

    /**
     * {@code DELETE  /grupo-acesso-empresa-usuario-contadors/:id} : delete the "id" grupoAcessoEmpresaUsuarioContador.
     *
     * @param id the id of the grupoAcessoEmpresaUsuarioContador to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrupoAcessoEmpresaUsuarioContador(@PathVariable("id") Long id) {
        log.debug("REST request to delete GrupoAcessoEmpresaUsuarioContador : {}", id);
        grupoAcessoEmpresaUsuarioContadorService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
