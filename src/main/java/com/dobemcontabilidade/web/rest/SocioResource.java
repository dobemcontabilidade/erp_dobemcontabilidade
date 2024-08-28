package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.Socio;
import com.dobemcontabilidade.repository.SocioRepository;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Socio}.
 */
@RestController
@RequestMapping("/api/socios")
@Transactional
public class SocioResource {

    private static final Logger log = LoggerFactory.getLogger(SocioResource.class);

    private static final String ENTITY_NAME = "socio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SocioRepository socioRepository;

    public SocioResource(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }

    /**
     * {@code POST  /socios} : Create a new socio.
     *
     * @param socio the socio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new socio, or with status {@code 400 (Bad Request)} if the socio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Socio> createSocio(@Valid @RequestBody Socio socio) throws URISyntaxException {
        log.debug("REST request to save Socio : {}", socio);
        if (socio.getId() != null) {
            throw new BadRequestAlertException("A new socio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        socio = socioRepository.save(socio);
        return ResponseEntity.created(new URI("/api/socios/" + socio.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, socio.getId().toString()))
            .body(socio);
    }

    /**
     * {@code PUT  /socios/:id} : Updates an existing socio.
     *
     * @param id the id of the socio to save.
     * @param socio the socio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated socio,
     * or with status {@code 400 (Bad Request)} if the socio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the socio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Socio> updateSocio(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Socio socio)
        throws URISyntaxException {
        log.debug("REST request to update Socio : {}, {}", id, socio);
        if (socio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, socio.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!socioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        socio = socioRepository.save(socio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, socio.getId().toString()))
            .body(socio);
    }

    /**
     * {@code PATCH  /socios/:id} : Partial updates given fields of an existing socio, field will ignore if it is null
     *
     * @param id the id of the socio to save.
     * @param socio the socio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated socio,
     * or with status {@code 400 (Bad Request)} if the socio is not valid,
     * or with status {@code 404 (Not Found)} if the socio is not found,
     * or with status {@code 500 (Internal Server Error)} if the socio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Socio> partialUpdateSocio(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Socio socio
    ) throws URISyntaxException {
        log.debug("REST request to partial update Socio partially : {}, {}", id, socio);
        if (socio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, socio.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!socioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Socio> result = socioRepository
            .findById(socio.getId())
            .map(existingSocio -> {
                if (socio.getNome() != null) {
                    existingSocio.setNome(socio.getNome());
                }
                if (socio.getProlabore() != null) {
                    existingSocio.setProlabore(socio.getProlabore());
                }
                if (socio.getPercentualSociedade() != null) {
                    existingSocio.setPercentualSociedade(socio.getPercentualSociedade());
                }
                if (socio.getAdminstrador() != null) {
                    existingSocio.setAdminstrador(socio.getAdminstrador());
                }
                if (socio.getDistribuicaoLucro() != null) {
                    existingSocio.setDistribuicaoLucro(socio.getDistribuicaoLucro());
                }
                if (socio.getResponsavelReceita() != null) {
                    existingSocio.setResponsavelReceita(socio.getResponsavelReceita());
                }
                if (socio.getPercentualDistribuicaoLucro() != null) {
                    existingSocio.setPercentualDistribuicaoLucro(socio.getPercentualDistribuicaoLucro());
                }
                if (socio.getFuncaoSocio() != null) {
                    existingSocio.setFuncaoSocio(socio.getFuncaoSocio());
                }

                return existingSocio;
            })
            .map(socioRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, socio.getId().toString())
        );
    }

    /**
     * {@code GET  /socios} : get all the socios.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of socios in body.
     */
    @GetMapping("")
    public List<Socio> getAllSocios(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Socios");
        if (eagerload) {
            return socioRepository.findAllWithEagerRelationships();
        } else {
            return socioRepository.findAll();
        }
    }

    /**
     * {@code GET  /socios/:id} : get the "id" socio.
     *
     * @param id the id of the socio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the socio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Socio> getSocio(@PathVariable("id") Long id) {
        log.debug("REST request to get Socio : {}", id);
        Optional<Socio> socio = socioRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(socio);
    }

    /**
     * {@code DELETE  /socios/:id} : delete the "id" socio.
     *
     * @param id the id of the socio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSocio(@PathVariable("id") Long id) {
        log.debug("REST request to delete Socio : {}", id);
        socioRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
