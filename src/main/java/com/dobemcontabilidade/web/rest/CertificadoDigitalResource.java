package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.CertificadoDigital;
import com.dobemcontabilidade.repository.CertificadoDigitalRepository;
import com.dobemcontabilidade.service.CertificadoDigitalQueryService;
import com.dobemcontabilidade.service.CertificadoDigitalService;
import com.dobemcontabilidade.service.criteria.CertificadoDigitalCriteria;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dobemcontabilidade.domain.CertificadoDigital}.
 */
@RestController
@RequestMapping("/api/certificado-digitals")
public class CertificadoDigitalResource {

    private static final Logger log = LoggerFactory.getLogger(CertificadoDigitalResource.class);

    private static final String ENTITY_NAME = "certificadoDigital";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CertificadoDigitalService certificadoDigitalService;

    private final CertificadoDigitalRepository certificadoDigitalRepository;

    private final CertificadoDigitalQueryService certificadoDigitalQueryService;

    public CertificadoDigitalResource(
        CertificadoDigitalService certificadoDigitalService,
        CertificadoDigitalRepository certificadoDigitalRepository,
        CertificadoDigitalQueryService certificadoDigitalQueryService
    ) {
        this.certificadoDigitalService = certificadoDigitalService;
        this.certificadoDigitalRepository = certificadoDigitalRepository;
        this.certificadoDigitalQueryService = certificadoDigitalQueryService;
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
        certificadoDigital = certificadoDigitalService.save(certificadoDigital);
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

        certificadoDigital = certificadoDigitalService.update(certificadoDigital);
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

        Optional<CertificadoDigital> result = certificadoDigitalService.partialUpdate(certificadoDigital);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, certificadoDigital.getId().toString())
        );
    }

    /**
     * {@code GET  /certificado-digitals} : get all the certificadoDigitals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of certificadoDigitals in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CertificadoDigital>> getAllCertificadoDigitals(
        CertificadoDigitalCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CertificadoDigitals by criteria: {}", criteria);

        Page<CertificadoDigital> page = certificadoDigitalQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /certificado-digitals/count} : count all the certificadoDigitals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countCertificadoDigitals(CertificadoDigitalCriteria criteria) {
        log.debug("REST request to count CertificadoDigitals by criteria: {}", criteria);
        return ResponseEntity.ok().body(certificadoDigitalQueryService.countByCriteria(criteria));
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
        Optional<CertificadoDigital> certificadoDigital = certificadoDigitalService.findOne(id);
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
        certificadoDigitalService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
