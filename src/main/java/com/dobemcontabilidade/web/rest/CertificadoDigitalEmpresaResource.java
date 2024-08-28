package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.CertificadoDigitalEmpresa;
import com.dobemcontabilidade.repository.CertificadoDigitalEmpresaRepository;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.CertificadoDigitalEmpresa}.
 */
@RestController
@RequestMapping("/api/certificado-digital-empresas")
@Transactional
public class CertificadoDigitalEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(CertificadoDigitalEmpresaResource.class);

    private static final String ENTITY_NAME = "certificadoDigitalEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CertificadoDigitalEmpresaRepository certificadoDigitalEmpresaRepository;

    public CertificadoDigitalEmpresaResource(CertificadoDigitalEmpresaRepository certificadoDigitalEmpresaRepository) {
        this.certificadoDigitalEmpresaRepository = certificadoDigitalEmpresaRepository;
    }

    /**
     * {@code POST  /certificado-digital-empresas} : Create a new certificadoDigitalEmpresa.
     *
     * @param certificadoDigitalEmpresa the certificadoDigitalEmpresa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new certificadoDigitalEmpresa, or with status {@code 400 (Bad Request)} if the certificadoDigitalEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CertificadoDigitalEmpresa> createCertificadoDigitalEmpresa(
        @Valid @RequestBody CertificadoDigitalEmpresa certificadoDigitalEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to save CertificadoDigitalEmpresa : {}", certificadoDigitalEmpresa);
        if (certificadoDigitalEmpresa.getId() != null) {
            throw new BadRequestAlertException("A new certificadoDigitalEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        certificadoDigitalEmpresa = certificadoDigitalEmpresaRepository.save(certificadoDigitalEmpresa);
        return ResponseEntity.created(new URI("/api/certificado-digital-empresas/" + certificadoDigitalEmpresa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, certificadoDigitalEmpresa.getId().toString()))
            .body(certificadoDigitalEmpresa);
    }

    /**
     * {@code PUT  /certificado-digital-empresas/:id} : Updates an existing certificadoDigitalEmpresa.
     *
     * @param id the id of the certificadoDigitalEmpresa to save.
     * @param certificadoDigitalEmpresa the certificadoDigitalEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated certificadoDigitalEmpresa,
     * or with status {@code 400 (Bad Request)} if the certificadoDigitalEmpresa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the certificadoDigitalEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CertificadoDigitalEmpresa> updateCertificadoDigitalEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CertificadoDigitalEmpresa certificadoDigitalEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to update CertificadoDigitalEmpresa : {}, {}", id, certificadoDigitalEmpresa);
        if (certificadoDigitalEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, certificadoDigitalEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!certificadoDigitalEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        certificadoDigitalEmpresa = certificadoDigitalEmpresaRepository.save(certificadoDigitalEmpresa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, certificadoDigitalEmpresa.getId().toString()))
            .body(certificadoDigitalEmpresa);
    }

    /**
     * {@code PATCH  /certificado-digital-empresas/:id} : Partial updates given fields of an existing certificadoDigitalEmpresa, field will ignore if it is null
     *
     * @param id the id of the certificadoDigitalEmpresa to save.
     * @param certificadoDigitalEmpresa the certificadoDigitalEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated certificadoDigitalEmpresa,
     * or with status {@code 400 (Bad Request)} if the certificadoDigitalEmpresa is not valid,
     * or with status {@code 404 (Not Found)} if the certificadoDigitalEmpresa is not found,
     * or with status {@code 500 (Internal Server Error)} if the certificadoDigitalEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CertificadoDigitalEmpresa> partialUpdateCertificadoDigitalEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CertificadoDigitalEmpresa certificadoDigitalEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to partial update CertificadoDigitalEmpresa partially : {}, {}", id, certificadoDigitalEmpresa);
        if (certificadoDigitalEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, certificadoDigitalEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!certificadoDigitalEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CertificadoDigitalEmpresa> result = certificadoDigitalEmpresaRepository
            .findById(certificadoDigitalEmpresa.getId())
            .map(existingCertificadoDigitalEmpresa -> {
                if (certificadoDigitalEmpresa.getUrlCertificado() != null) {
                    existingCertificadoDigitalEmpresa.setUrlCertificado(certificadoDigitalEmpresa.getUrlCertificado());
                }
                if (certificadoDigitalEmpresa.getDataContratacao() != null) {
                    existingCertificadoDigitalEmpresa.setDataContratacao(certificadoDigitalEmpresa.getDataContratacao());
                }
                if (certificadoDigitalEmpresa.getDataVencimento() != null) {
                    existingCertificadoDigitalEmpresa.setDataVencimento(certificadoDigitalEmpresa.getDataVencimento());
                }
                if (certificadoDigitalEmpresa.getDiasUso() != null) {
                    existingCertificadoDigitalEmpresa.setDiasUso(certificadoDigitalEmpresa.getDiasUso());
                }

                return existingCertificadoDigitalEmpresa;
            })
            .map(certificadoDigitalEmpresaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, certificadoDigitalEmpresa.getId().toString())
        );
    }

    /**
     * {@code GET  /certificado-digital-empresas} : get all the certificadoDigitalEmpresas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of certificadoDigitalEmpresas in body.
     */
    @GetMapping("")
    public List<CertificadoDigitalEmpresa> getAllCertificadoDigitalEmpresas(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all CertificadoDigitalEmpresas");
        if (eagerload) {
            return certificadoDigitalEmpresaRepository.findAllWithEagerRelationships();
        } else {
            return certificadoDigitalEmpresaRepository.findAll();
        }
    }

    /**
     * {@code GET  /certificado-digital-empresas/:id} : get the "id" certificadoDigitalEmpresa.
     *
     * @param id the id of the certificadoDigitalEmpresa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the certificadoDigitalEmpresa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CertificadoDigitalEmpresa> getCertificadoDigitalEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get CertificadoDigitalEmpresa : {}", id);
        Optional<CertificadoDigitalEmpresa> certificadoDigitalEmpresa = certificadoDigitalEmpresaRepository.findOneWithEagerRelationships(
            id
        );
        return ResponseUtil.wrapOrNotFound(certificadoDigitalEmpresa);
    }

    /**
     * {@code DELETE  /certificado-digital-empresas/:id} : delete the "id" certificadoDigitalEmpresa.
     *
     * @param id the id of the certificadoDigitalEmpresa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertificadoDigitalEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete CertificadoDigitalEmpresa : {}", id);
        certificadoDigitalEmpresaRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
