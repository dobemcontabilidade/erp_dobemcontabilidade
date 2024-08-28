package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.CertificadoDigital;
import com.dobemcontabilidade.repository.CertificadoDigitalRepository;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.CertificadoDigital}.
 */
@RestController
@RequestMapping("/api/certificado-digitals")
@Transactional
public class CertificadoDigitalResource {

    private static final Logger log = LoggerFactory.getLogger(CertificadoDigitalResource.class);

    private static final String ENTITY_NAME = "certificadoDigital";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CertificadoDigitalRepository certificadoDigitalRepository;

    public CertificadoDigitalResource(CertificadoDigitalRepository certificadoDigitalRepository) {
        this.certificadoDigitalRepository = certificadoDigitalRepository;
    }

    /**
     * {@code POST  /certificado-digitals} : Create a new certificadoDigital.
     *
     * @param certificadoDigital the certificadoDigital to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new certificadoDigital, or with status {@code 400 (Bad Request)} if the certificadoDigital has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CertificadoDigital> createCertificadoDigital(@Valid @RequestBody CertificadoDigital certificadoDigital)
        throws URISyntaxException {
        log.debug("REST request to save CertificadoDigital : {}", certificadoDigital);
        if (certificadoDigital.getId() != null) {
            throw new BadRequestAlertException("A new certificadoDigital cannot already have an ID", ENTITY_NAME, "idexists");
        }
        certificadoDigital = certificadoDigitalRepository.save(certificadoDigital);
        return ResponseEntity.created(new URI("/api/certificado-digitals/" + certificadoDigital.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, certificadoDigital.getId().toString()))
            .body(certificadoDigital);
    }

    /**
     * {@code PUT  /certificado-digitals/:id} : Updates an existing certificadoDigital.
     *
     * @param id the id of the certificadoDigital to save.
     * @param certificadoDigital the certificadoDigital to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated certificadoDigital,
     * or with status {@code 400 (Bad Request)} if the certificadoDigital is not valid,
     * or with status {@code 500 (Internal Server Error)} if the certificadoDigital couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CertificadoDigital> updateCertificadoDigital(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CertificadoDigital certificadoDigital
    ) throws URISyntaxException {
        log.debug("REST request to update CertificadoDigital : {}, {}", id, certificadoDigital);
        if (certificadoDigital.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, certificadoDigital.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!certificadoDigitalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        certificadoDigital = certificadoDigitalRepository.save(certificadoDigital);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, certificadoDigital.getId().toString()))
            .body(certificadoDigital);
    }

    /**
     * {@code PATCH  /certificado-digitals/:id} : Partial updates given fields of an existing certificadoDigital, field will ignore if it is null
     *
     * @param id the id of the certificadoDigital to save.
     * @param certificadoDigital the certificadoDigital to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated certificadoDigital,
     * or with status {@code 400 (Bad Request)} if the certificadoDigital is not valid,
     * or with status {@code 404 (Not Found)} if the certificadoDigital is not found,
     * or with status {@code 500 (Internal Server Error)} if the certificadoDigital couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CertificadoDigital> partialUpdateCertificadoDigital(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CertificadoDigital certificadoDigital
    ) throws URISyntaxException {
        log.debug("REST request to partial update CertificadoDigital partially : {}, {}", id, certificadoDigital);
        if (certificadoDigital.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, certificadoDigital.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!certificadoDigitalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CertificadoDigital> result = certificadoDigitalRepository
            .findById(certificadoDigital.getId())
            .map(existingCertificadoDigital -> {
                if (certificadoDigital.getNome() != null) {
                    existingCertificadoDigital.setNome(certificadoDigital.getNome());
                }
                if (certificadoDigital.getSigla() != null) {
                    existingCertificadoDigital.setSigla(certificadoDigital.getSigla());
                }
                if (certificadoDigital.getDescricao() != null) {
                    existingCertificadoDigital.setDescricao(certificadoDigital.getDescricao());
                }
                if (certificadoDigital.getTipoCertificado() != null) {
                    existingCertificadoDigital.setTipoCertificado(certificadoDigital.getTipoCertificado());
                }

                return existingCertificadoDigital;
            })
            .map(certificadoDigitalRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, certificadoDigital.getId().toString())
        );
    }

    /**
     * {@code GET  /certificado-digitals} : get all the certificadoDigitals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of certificadoDigitals in body.
     */
    @GetMapping("")
    public List<CertificadoDigital> getAllCertificadoDigitals() {
        log.debug("REST request to get all CertificadoDigitals");
        return certificadoDigitalRepository.findAll();
    }

    /**
     * {@code GET  /certificado-digitals/:id} : get the "id" certificadoDigital.
     *
     * @param id the id of the certificadoDigital to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the certificadoDigital, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CertificadoDigital> getCertificadoDigital(@PathVariable("id") Long id) {
        log.debug("REST request to get CertificadoDigital : {}", id);
        Optional<CertificadoDigital> certificadoDigital = certificadoDigitalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(certificadoDigital);
    }

    /**
     * {@code DELETE  /certificado-digitals/:id} : delete the "id" certificadoDigital.
     *
     * @param id the id of the certificadoDigital to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertificadoDigital(@PathVariable("id") Long id) {
        log.debug("REST request to delete CertificadoDigital : {}", id);
        certificadoDigitalRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
