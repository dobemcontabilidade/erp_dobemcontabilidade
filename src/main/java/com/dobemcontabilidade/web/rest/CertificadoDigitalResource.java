package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.CertificadoDigitalRepository;
import com.dobemcontabilidade.service.CertificadoDigitalQueryService;
import com.dobemcontabilidade.service.CertificadoDigitalService;
import com.dobemcontabilidade.service.criteria.CertificadoDigitalCriteria;
import com.dobemcontabilidade.service.dto.CertificadoDigitalDTO;
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
     * @param certificadoDigitalDTO the certificadoDigitalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new certificadoDigitalDTO, or with status {@code 400 (Bad Request)} if the certificadoDigital has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CertificadoDigitalDTO> createCertificadoDigital(@Valid @RequestBody CertificadoDigitalDTO certificadoDigitalDTO)
        throws URISyntaxException {
        log.debug("REST request to save CertificadoDigital : {}", certificadoDigitalDTO);
        if (certificadoDigitalDTO.getId() != null) {
            throw new BadRequestAlertException("A new certificadoDigital cannot already have an ID", ENTITY_NAME, "idexists");
        }
        certificadoDigitalDTO = certificadoDigitalService.save(certificadoDigitalDTO);
        return ResponseEntity.created(new URI("/api/certificado-digitals/" + certificadoDigitalDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, certificadoDigitalDTO.getId().toString()))
            .body(certificadoDigitalDTO);
    }

    /**
     * {@code PUT  /certificado-digitals/:id} : Updates an existing certificadoDigital.
     *
     * @param id the id of the certificadoDigitalDTO to save.
     * @param certificadoDigitalDTO the certificadoDigitalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated certificadoDigitalDTO,
     * or with status {@code 400 (Bad Request)} if the certificadoDigitalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the certificadoDigitalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CertificadoDigitalDTO> updateCertificadoDigital(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CertificadoDigitalDTO certificadoDigitalDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CertificadoDigital : {}, {}", id, certificadoDigitalDTO);
        if (certificadoDigitalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, certificadoDigitalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!certificadoDigitalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        certificadoDigitalDTO = certificadoDigitalService.update(certificadoDigitalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, certificadoDigitalDTO.getId().toString()))
            .body(certificadoDigitalDTO);
    }

    /**
     * {@code PATCH  /certificado-digitals/:id} : Partial updates given fields of an existing certificadoDigital, field will ignore if it is null
     *
     * @param id the id of the certificadoDigitalDTO to save.
     * @param certificadoDigitalDTO the certificadoDigitalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated certificadoDigitalDTO,
     * or with status {@code 400 (Bad Request)} if the certificadoDigitalDTO is not valid,
     * or with status {@code 404 (Not Found)} if the certificadoDigitalDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the certificadoDigitalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CertificadoDigitalDTO> partialUpdateCertificadoDigital(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CertificadoDigitalDTO certificadoDigitalDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CertificadoDigital partially : {}, {}", id, certificadoDigitalDTO);
        if (certificadoDigitalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, certificadoDigitalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!certificadoDigitalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CertificadoDigitalDTO> result = certificadoDigitalService.partialUpdate(certificadoDigitalDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, certificadoDigitalDTO.getId().toString())
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
    public ResponseEntity<List<CertificadoDigitalDTO>> getAllCertificadoDigitals(
        CertificadoDigitalCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CertificadoDigitals by criteria: {}", criteria);

        Page<CertificadoDigitalDTO> page = certificadoDigitalQueryService.findByCriteria(criteria, pageable);
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
     * @param id the id of the certificadoDigitalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the certificadoDigitalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CertificadoDigitalDTO> getCertificadoDigital(@PathVariable("id") Long id) {
        log.debug("REST request to get CertificadoDigital : {}", id);
        Optional<CertificadoDigitalDTO> certificadoDigitalDTO = certificadoDigitalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(certificadoDigitalDTO);
    }

    /**
     * {@code DELETE  /certificado-digitals/:id} : delete the "id" certificadoDigital.
     *
     * @param id the id of the certificadoDigitalDTO to delete.
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
