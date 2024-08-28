package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.PlanoAssinaturaContabil;
import com.dobemcontabilidade.repository.PlanoAssinaturaContabilRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dobemcontabilidade.domain.PlanoAssinaturaContabil}.
 */
@RestController
@RequestMapping("/api/plano-assinatura-contabils")
@Transactional
public class PlanoAssinaturaContabilResource {

    private static final Logger log = LoggerFactory.getLogger(PlanoAssinaturaContabilResource.class);

    private static final String ENTITY_NAME = "planoAssinaturaContabil";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanoAssinaturaContabilRepository planoAssinaturaContabilRepository;

    public PlanoAssinaturaContabilResource(PlanoAssinaturaContabilRepository planoAssinaturaContabilRepository) {
        this.planoAssinaturaContabilRepository = planoAssinaturaContabilRepository;
    }

    /**
     * {@code POST  /plano-assinatura-contabils} : Create a new planoAssinaturaContabil.
     *
     * @param planoAssinaturaContabil the planoAssinaturaContabil to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planoAssinaturaContabil, or with status {@code 400 (Bad Request)} if the planoAssinaturaContabil has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PlanoAssinaturaContabil> createPlanoAssinaturaContabil(
        @RequestBody PlanoAssinaturaContabil planoAssinaturaContabil
    ) throws URISyntaxException {
        log.debug("REST request to save PlanoAssinaturaContabil : {}", planoAssinaturaContabil);
        if (planoAssinaturaContabil.getId() != null) {
            throw new BadRequestAlertException("A new planoAssinaturaContabil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        planoAssinaturaContabil = planoAssinaturaContabilRepository.save(planoAssinaturaContabil);
        return ResponseEntity.created(new URI("/api/plano-assinatura-contabils/" + planoAssinaturaContabil.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, planoAssinaturaContabil.getId().toString()))
            .body(planoAssinaturaContabil);
    }

    /**
     * {@code PUT  /plano-assinatura-contabils/:id} : Updates an existing planoAssinaturaContabil.
     *
     * @param id the id of the planoAssinaturaContabil to save.
     * @param planoAssinaturaContabil the planoAssinaturaContabil to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planoAssinaturaContabil,
     * or with status {@code 400 (Bad Request)} if the planoAssinaturaContabil is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planoAssinaturaContabil couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PlanoAssinaturaContabil> updatePlanoAssinaturaContabil(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlanoAssinaturaContabil planoAssinaturaContabil
    ) throws URISyntaxException {
        log.debug("REST request to update PlanoAssinaturaContabil : {}, {}", id, planoAssinaturaContabil);
        if (planoAssinaturaContabil.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planoAssinaturaContabil.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planoAssinaturaContabilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        planoAssinaturaContabil = planoAssinaturaContabilRepository.save(planoAssinaturaContabil);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planoAssinaturaContabil.getId().toString()))
            .body(planoAssinaturaContabil);
    }

    /**
     * {@code PATCH  /plano-assinatura-contabils/:id} : Partial updates given fields of an existing planoAssinaturaContabil, field will ignore if it is null
     *
     * @param id the id of the planoAssinaturaContabil to save.
     * @param planoAssinaturaContabil the planoAssinaturaContabil to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planoAssinaturaContabil,
     * or with status {@code 400 (Bad Request)} if the planoAssinaturaContabil is not valid,
     * or with status {@code 404 (Not Found)} if the planoAssinaturaContabil is not found,
     * or with status {@code 500 (Internal Server Error)} if the planoAssinaturaContabil couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlanoAssinaturaContabil> partialUpdatePlanoAssinaturaContabil(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlanoAssinaturaContabil planoAssinaturaContabil
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlanoAssinaturaContabil partially : {}, {}", id, planoAssinaturaContabil);
        if (planoAssinaturaContabil.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planoAssinaturaContabil.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planoAssinaturaContabilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlanoAssinaturaContabil> result = planoAssinaturaContabilRepository
            .findById(planoAssinaturaContabil.getId())
            .map(existingPlanoAssinaturaContabil -> {
                if (planoAssinaturaContabil.getNome() != null) {
                    existingPlanoAssinaturaContabil.setNome(planoAssinaturaContabil.getNome());
                }
                if (planoAssinaturaContabil.getAdicionalSocio() != null) {
                    existingPlanoAssinaturaContabil.setAdicionalSocio(planoAssinaturaContabil.getAdicionalSocio());
                }
                if (planoAssinaturaContabil.getAdicionalFuncionario() != null) {
                    existingPlanoAssinaturaContabil.setAdicionalFuncionario(planoAssinaturaContabil.getAdicionalFuncionario());
                }
                if (planoAssinaturaContabil.getSociosIsentos() != null) {
                    existingPlanoAssinaturaContabil.setSociosIsentos(planoAssinaturaContabil.getSociosIsentos());
                }
                if (planoAssinaturaContabil.getAdicionalFaturamento() != null) {
                    existingPlanoAssinaturaContabil.setAdicionalFaturamento(planoAssinaturaContabil.getAdicionalFaturamento());
                }
                if (planoAssinaturaContabil.getValorBaseFaturamento() != null) {
                    existingPlanoAssinaturaContabil.setValorBaseFaturamento(planoAssinaturaContabil.getValorBaseFaturamento());
                }
                if (planoAssinaturaContabil.getValorBaseAbertura() != null) {
                    existingPlanoAssinaturaContabil.setValorBaseAbertura(planoAssinaturaContabil.getValorBaseAbertura());
                }
                if (planoAssinaturaContabil.getSituacao() != null) {
                    existingPlanoAssinaturaContabil.setSituacao(planoAssinaturaContabil.getSituacao());
                }

                return existingPlanoAssinaturaContabil;
            })
            .map(planoAssinaturaContabilRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planoAssinaturaContabil.getId().toString())
        );
    }

    /**
     * {@code GET  /plano-assinatura-contabils} : get all the planoAssinaturaContabils.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planoAssinaturaContabils in body.
     */
    @GetMapping("")
    public List<PlanoAssinaturaContabil> getAllPlanoAssinaturaContabils() {
        log.debug("REST request to get all PlanoAssinaturaContabils");
        return planoAssinaturaContabilRepository.findAll();
    }

    /**
     * {@code GET  /plano-assinatura-contabils/:id} : get the "id" planoAssinaturaContabil.
     *
     * @param id the id of the planoAssinaturaContabil to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planoAssinaturaContabil, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PlanoAssinaturaContabil> getPlanoAssinaturaContabil(@PathVariable("id") Long id) {
        log.debug("REST request to get PlanoAssinaturaContabil : {}", id);
        Optional<PlanoAssinaturaContabil> planoAssinaturaContabil = planoAssinaturaContabilRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(planoAssinaturaContabil);
    }

    /**
     * {@code DELETE  /plano-assinatura-contabils/:id} : delete the "id" planoAssinaturaContabil.
     *
     * @param id the id of the planoAssinaturaContabil to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanoAssinaturaContabil(@PathVariable("id") Long id) {
        log.debug("REST request to delete PlanoAssinaturaContabil : {}", id);
        planoAssinaturaContabilRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
