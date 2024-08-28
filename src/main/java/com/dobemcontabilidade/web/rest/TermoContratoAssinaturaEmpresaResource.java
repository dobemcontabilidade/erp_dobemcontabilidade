package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.TermoContratoAssinaturaEmpresa;
import com.dobemcontabilidade.repository.TermoContratoAssinaturaEmpresaRepository;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.TermoContratoAssinaturaEmpresa}.
 */
@RestController
@RequestMapping("/api/termo-contrato-assinatura-empresas")
@Transactional
public class TermoContratoAssinaturaEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(TermoContratoAssinaturaEmpresaResource.class);

    private static final String ENTITY_NAME = "termoContratoAssinaturaEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TermoContratoAssinaturaEmpresaRepository termoContratoAssinaturaEmpresaRepository;

    public TermoContratoAssinaturaEmpresaResource(TermoContratoAssinaturaEmpresaRepository termoContratoAssinaturaEmpresaRepository) {
        this.termoContratoAssinaturaEmpresaRepository = termoContratoAssinaturaEmpresaRepository;
    }

    /**
     * {@code POST  /termo-contrato-assinatura-empresas} : Create a new termoContratoAssinaturaEmpresa.
     *
     * @param termoContratoAssinaturaEmpresa the termoContratoAssinaturaEmpresa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new termoContratoAssinaturaEmpresa, or with status {@code 400 (Bad Request)} if the termoContratoAssinaturaEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TermoContratoAssinaturaEmpresa> createTermoContratoAssinaturaEmpresa(
        @Valid @RequestBody TermoContratoAssinaturaEmpresa termoContratoAssinaturaEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to save TermoContratoAssinaturaEmpresa : {}", termoContratoAssinaturaEmpresa);
        if (termoContratoAssinaturaEmpresa.getId() != null) {
            throw new BadRequestAlertException("A new termoContratoAssinaturaEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        termoContratoAssinaturaEmpresa = termoContratoAssinaturaEmpresaRepository.save(termoContratoAssinaturaEmpresa);
        return ResponseEntity.created(new URI("/api/termo-contrato-assinatura-empresas/" + termoContratoAssinaturaEmpresa.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, termoContratoAssinaturaEmpresa.getId().toString())
            )
            .body(termoContratoAssinaturaEmpresa);
    }

    /**
     * {@code PUT  /termo-contrato-assinatura-empresas/:id} : Updates an existing termoContratoAssinaturaEmpresa.
     *
     * @param id the id of the termoContratoAssinaturaEmpresa to save.
     * @param termoContratoAssinaturaEmpresa the termoContratoAssinaturaEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated termoContratoAssinaturaEmpresa,
     * or with status {@code 400 (Bad Request)} if the termoContratoAssinaturaEmpresa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the termoContratoAssinaturaEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TermoContratoAssinaturaEmpresa> updateTermoContratoAssinaturaEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TermoContratoAssinaturaEmpresa termoContratoAssinaturaEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to update TermoContratoAssinaturaEmpresa : {}, {}", id, termoContratoAssinaturaEmpresa);
        if (termoContratoAssinaturaEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, termoContratoAssinaturaEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!termoContratoAssinaturaEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        termoContratoAssinaturaEmpresa = termoContratoAssinaturaEmpresaRepository.save(termoContratoAssinaturaEmpresa);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, termoContratoAssinaturaEmpresa.getId().toString())
            )
            .body(termoContratoAssinaturaEmpresa);
    }

    /**
     * {@code PATCH  /termo-contrato-assinatura-empresas/:id} : Partial updates given fields of an existing termoContratoAssinaturaEmpresa, field will ignore if it is null
     *
     * @param id the id of the termoContratoAssinaturaEmpresa to save.
     * @param termoContratoAssinaturaEmpresa the termoContratoAssinaturaEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated termoContratoAssinaturaEmpresa,
     * or with status {@code 400 (Bad Request)} if the termoContratoAssinaturaEmpresa is not valid,
     * or with status {@code 404 (Not Found)} if the termoContratoAssinaturaEmpresa is not found,
     * or with status {@code 500 (Internal Server Error)} if the termoContratoAssinaturaEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TermoContratoAssinaturaEmpresa> partialUpdateTermoContratoAssinaturaEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TermoContratoAssinaturaEmpresa termoContratoAssinaturaEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to partial update TermoContratoAssinaturaEmpresa partially : {}, {}", id, termoContratoAssinaturaEmpresa);
        if (termoContratoAssinaturaEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, termoContratoAssinaturaEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!termoContratoAssinaturaEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TermoContratoAssinaturaEmpresa> result = termoContratoAssinaturaEmpresaRepository
            .findById(termoContratoAssinaturaEmpresa.getId())
            .map(existingTermoContratoAssinaturaEmpresa -> {
                if (termoContratoAssinaturaEmpresa.getDataAssinatura() != null) {
                    existingTermoContratoAssinaturaEmpresa.setDataAssinatura(termoContratoAssinaturaEmpresa.getDataAssinatura());
                }
                if (termoContratoAssinaturaEmpresa.getDataEnvioEmail() != null) {
                    existingTermoContratoAssinaturaEmpresa.setDataEnvioEmail(termoContratoAssinaturaEmpresa.getDataEnvioEmail());
                }
                if (termoContratoAssinaturaEmpresa.getUrlDocumentoAssinado() != null) {
                    existingTermoContratoAssinaturaEmpresa.setUrlDocumentoAssinado(
                        termoContratoAssinaturaEmpresa.getUrlDocumentoAssinado()
                    );
                }
                if (termoContratoAssinaturaEmpresa.getSituacao() != null) {
                    existingTermoContratoAssinaturaEmpresa.setSituacao(termoContratoAssinaturaEmpresa.getSituacao());
                }

                return existingTermoContratoAssinaturaEmpresa;
            })
            .map(termoContratoAssinaturaEmpresaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, termoContratoAssinaturaEmpresa.getId().toString())
        );
    }

    /**
     * {@code GET  /termo-contrato-assinatura-empresas} : get all the termoContratoAssinaturaEmpresas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of termoContratoAssinaturaEmpresas in body.
     */
    @GetMapping("")
    public List<TermoContratoAssinaturaEmpresa> getAllTermoContratoAssinaturaEmpresas(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all TermoContratoAssinaturaEmpresas");
        if (eagerload) {
            return termoContratoAssinaturaEmpresaRepository.findAllWithEagerRelationships();
        } else {
            return termoContratoAssinaturaEmpresaRepository.findAll();
        }
    }

    /**
     * {@code GET  /termo-contrato-assinatura-empresas/:id} : get the "id" termoContratoAssinaturaEmpresa.
     *
     * @param id the id of the termoContratoAssinaturaEmpresa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the termoContratoAssinaturaEmpresa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TermoContratoAssinaturaEmpresa> getTermoContratoAssinaturaEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get TermoContratoAssinaturaEmpresa : {}", id);
        Optional<TermoContratoAssinaturaEmpresa> termoContratoAssinaturaEmpresa =
            termoContratoAssinaturaEmpresaRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(termoContratoAssinaturaEmpresa);
    }

    /**
     * {@code DELETE  /termo-contrato-assinatura-empresas/:id} : delete the "id" termoContratoAssinaturaEmpresa.
     *
     * @param id the id of the termoContratoAssinaturaEmpresa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTermoContratoAssinaturaEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete TermoContratoAssinaturaEmpresa : {}", id);
        termoContratoAssinaturaEmpresaRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
