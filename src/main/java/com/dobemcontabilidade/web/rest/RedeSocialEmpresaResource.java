package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.RedeSocialEmpresa;
import com.dobemcontabilidade.repository.RedeSocialEmpresaRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dobemcontabilidade.domain.RedeSocialEmpresa}.
 */
@RestController
@RequestMapping("/api/rede-social-empresas")
@Transactional
public class RedeSocialEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(RedeSocialEmpresaResource.class);

    private static final String ENTITY_NAME = "redeSocialEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RedeSocialEmpresaRepository redeSocialEmpresaRepository;

    public RedeSocialEmpresaResource(RedeSocialEmpresaRepository redeSocialEmpresaRepository) {
        this.redeSocialEmpresaRepository = redeSocialEmpresaRepository;
    }

    /**
     * {@code POST  /rede-social-empresas} : Create a new redeSocialEmpresa.
     *
     * @param redeSocialEmpresa the redeSocialEmpresa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new redeSocialEmpresa, or with status {@code 400 (Bad Request)} if the redeSocialEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RedeSocialEmpresa> createRedeSocialEmpresa(@Valid @RequestBody RedeSocialEmpresa redeSocialEmpresa)
        throws URISyntaxException {
        log.debug("REST request to save RedeSocialEmpresa : {}", redeSocialEmpresa);
        if (redeSocialEmpresa.getId() != null) {
            throw new BadRequestAlertException("A new redeSocialEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        redeSocialEmpresa = redeSocialEmpresaRepository.save(redeSocialEmpresa);
        return ResponseEntity.created(new URI("/api/rede-social-empresas/" + redeSocialEmpresa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, redeSocialEmpresa.getId().toString()))
            .body(redeSocialEmpresa);
    }

    /**
     * {@code PUT  /rede-social-empresas/:id} : Updates an existing redeSocialEmpresa.
     *
     * @param id the id of the redeSocialEmpresa to save.
     * @param redeSocialEmpresa the redeSocialEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated redeSocialEmpresa,
     * or with status {@code 400 (Bad Request)} if the redeSocialEmpresa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the redeSocialEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RedeSocialEmpresa> updateRedeSocialEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RedeSocialEmpresa redeSocialEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to update RedeSocialEmpresa : {}, {}", id, redeSocialEmpresa);
        if (redeSocialEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, redeSocialEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!redeSocialEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        redeSocialEmpresa = redeSocialEmpresaRepository.save(redeSocialEmpresa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, redeSocialEmpresa.getId().toString()))
            .body(redeSocialEmpresa);
    }

    /**
     * {@code PATCH  /rede-social-empresas/:id} : Partial updates given fields of an existing redeSocialEmpresa, field will ignore if it is null
     *
     * @param id the id of the redeSocialEmpresa to save.
     * @param redeSocialEmpresa the redeSocialEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated redeSocialEmpresa,
     * or with status {@code 400 (Bad Request)} if the redeSocialEmpresa is not valid,
     * or with status {@code 404 (Not Found)} if the redeSocialEmpresa is not found,
     * or with status {@code 500 (Internal Server Error)} if the redeSocialEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RedeSocialEmpresa> partialUpdateRedeSocialEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RedeSocialEmpresa redeSocialEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to partial update RedeSocialEmpresa partially : {}, {}", id, redeSocialEmpresa);
        if (redeSocialEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, redeSocialEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!redeSocialEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RedeSocialEmpresa> result = redeSocialEmpresaRepository
            .findById(redeSocialEmpresa.getId())
            .map(existingRedeSocialEmpresa -> {
                if (redeSocialEmpresa.getPerfil() != null) {
                    existingRedeSocialEmpresa.setPerfil(redeSocialEmpresa.getPerfil());
                }
                if (redeSocialEmpresa.getUrlPerfil() != null) {
                    existingRedeSocialEmpresa.setUrlPerfil(redeSocialEmpresa.getUrlPerfil());
                }

                return existingRedeSocialEmpresa;
            })
            .map(redeSocialEmpresaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, redeSocialEmpresa.getId().toString())
        );
    }

    /**
     * {@code GET  /rede-social-empresas} : get all the redeSocialEmpresas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of redeSocialEmpresas in body.
     */
    @GetMapping("")
    public List<RedeSocialEmpresa> getAllRedeSocialEmpresas(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all RedeSocialEmpresas");
        if (eagerload) {
            return redeSocialEmpresaRepository.findAllWithEagerRelationships();
        } else {
            return redeSocialEmpresaRepository.findAll();
        }
    }

    /**
     * {@code GET  /rede-social-empresas/:id} : get the "id" redeSocialEmpresa.
     *
     * @param id the id of the redeSocialEmpresa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the redeSocialEmpresa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RedeSocialEmpresa> getRedeSocialEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get RedeSocialEmpresa : {}", id);
        Optional<RedeSocialEmpresa> redeSocialEmpresa = redeSocialEmpresaRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(redeSocialEmpresa);
    }

    /**
     * {@code DELETE  /rede-social-empresas/:id} : delete the "id" redeSocialEmpresa.
     *
     * @param id the id of the redeSocialEmpresa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRedeSocialEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete RedeSocialEmpresa : {}", id);
        redeSocialEmpresaRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
