package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.TermoContratoContabil;
import com.dobemcontabilidade.repository.TermoContratoContabilRepository;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.TermoContratoContabil}.
 */
@RestController
@RequestMapping("/api/termo-contrato-contabils")
@Transactional
public class TermoContratoContabilResource {

    private static final Logger log = LoggerFactory.getLogger(TermoContratoContabilResource.class);

    private static final String ENTITY_NAME = "termoContratoContabil";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TermoContratoContabilRepository termoContratoContabilRepository;

    public TermoContratoContabilResource(TermoContratoContabilRepository termoContratoContabilRepository) {
        this.termoContratoContabilRepository = termoContratoContabilRepository;
    }

    /**
     * {@code POST  /termo-contrato-contabils} : Create a new termoContratoContabil.
     *
     * @param termoContratoContabil the termoContratoContabil to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new termoContratoContabil, or with status {@code 400 (Bad Request)} if the termoContratoContabil has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TermoContratoContabil> createTermoContratoContabil(@RequestBody TermoContratoContabil termoContratoContabil)
        throws URISyntaxException {
        log.debug("REST request to save TermoContratoContabil : {}", termoContratoContabil);
        if (termoContratoContabil.getId() != null) {
            throw new BadRequestAlertException("A new termoContratoContabil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        termoContratoContabil = termoContratoContabilRepository.save(termoContratoContabil);
        return ResponseEntity.created(new URI("/api/termo-contrato-contabils/" + termoContratoContabil.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, termoContratoContabil.getId().toString()))
            .body(termoContratoContabil);
    }

    /**
     * {@code PUT  /termo-contrato-contabils/:id} : Updates an existing termoContratoContabil.
     *
     * @param id the id of the termoContratoContabil to save.
     * @param termoContratoContabil the termoContratoContabil to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated termoContratoContabil,
     * or with status {@code 400 (Bad Request)} if the termoContratoContabil is not valid,
     * or with status {@code 500 (Internal Server Error)} if the termoContratoContabil couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TermoContratoContabil> updateTermoContratoContabil(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TermoContratoContabil termoContratoContabil
    ) throws URISyntaxException {
        log.debug("REST request to update TermoContratoContabil : {}, {}", id, termoContratoContabil);
        if (termoContratoContabil.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, termoContratoContabil.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!termoContratoContabilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        termoContratoContabil = termoContratoContabilRepository.save(termoContratoContabil);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, termoContratoContabil.getId().toString()))
            .body(termoContratoContabil);
    }

    /**
     * {@code PATCH  /termo-contrato-contabils/:id} : Partial updates given fields of an existing termoContratoContabil, field will ignore if it is null
     *
     * @param id the id of the termoContratoContabil to save.
     * @param termoContratoContabil the termoContratoContabil to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated termoContratoContabil,
     * or with status {@code 400 (Bad Request)} if the termoContratoContabil is not valid,
     * or with status {@code 404 (Not Found)} if the termoContratoContabil is not found,
     * or with status {@code 500 (Internal Server Error)} if the termoContratoContabil couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TermoContratoContabil> partialUpdateTermoContratoContabil(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TermoContratoContabil termoContratoContabil
    ) throws URISyntaxException {
        log.debug("REST request to partial update TermoContratoContabil partially : {}, {}", id, termoContratoContabil);
        if (termoContratoContabil.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, termoContratoContabil.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!termoContratoContabilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TermoContratoContabil> result = termoContratoContabilRepository
            .findById(termoContratoContabil.getId())
            .map(existingTermoContratoContabil -> {
                if (termoContratoContabil.getTitulo() != null) {
                    existingTermoContratoContabil.setTitulo(termoContratoContabil.getTitulo());
                }
                if (termoContratoContabil.getDescricao() != null) {
                    existingTermoContratoContabil.setDescricao(termoContratoContabil.getDescricao());
                }
                if (termoContratoContabil.getUrlDocumentoFonte() != null) {
                    existingTermoContratoContabil.setUrlDocumentoFonte(termoContratoContabil.getUrlDocumentoFonte());
                }
                if (termoContratoContabil.getDocumento() != null) {
                    existingTermoContratoContabil.setDocumento(termoContratoContabil.getDocumento());
                }
                if (termoContratoContabil.getDisponivel() != null) {
                    existingTermoContratoContabil.setDisponivel(termoContratoContabil.getDisponivel());
                }
                if (termoContratoContabil.getDataCriacao() != null) {
                    existingTermoContratoContabil.setDataCriacao(termoContratoContabil.getDataCriacao());
                }

                return existingTermoContratoContabil;
            })
            .map(termoContratoContabilRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, termoContratoContabil.getId().toString())
        );
    }

    /**
     * {@code GET  /termo-contrato-contabils} : get all the termoContratoContabils.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of termoContratoContabils in body.
     */
    @GetMapping("")
    public List<TermoContratoContabil> getAllTermoContratoContabils() {
        log.debug("REST request to get all TermoContratoContabils");
        return termoContratoContabilRepository.findAll();
    }

    /**
     * {@code GET  /termo-contrato-contabils/:id} : get the "id" termoContratoContabil.
     *
     * @param id the id of the termoContratoContabil to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the termoContratoContabil, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TermoContratoContabil> getTermoContratoContabil(@PathVariable("id") Long id) {
        log.debug("REST request to get TermoContratoContabil : {}", id);
        Optional<TermoContratoContabil> termoContratoContabil = termoContratoContabilRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(termoContratoContabil);
    }

    /**
     * {@code DELETE  /termo-contrato-contabils/:id} : delete the "id" termoContratoContabil.
     *
     * @param id the id of the termoContratoContabil to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTermoContratoContabil(@PathVariable("id") Long id) {
        log.debug("REST request to delete TermoContratoContabil : {}", id);
        termoContratoContabilRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
