package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.Enquadramento;
import com.dobemcontabilidade.repository.EnquadramentoRepository;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Enquadramento}.
 */
@RestController
@RequestMapping("/api/enquadramentos")
@Transactional
public class EnquadramentoResource {

    private static final Logger log = LoggerFactory.getLogger(EnquadramentoResource.class);

    private static final String ENTITY_NAME = "enquadramento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnquadramentoRepository enquadramentoRepository;

    public EnquadramentoResource(EnquadramentoRepository enquadramentoRepository) {
        this.enquadramentoRepository = enquadramentoRepository;
    }

    /**
     * {@code POST  /enquadramentos} : Create a new enquadramento.
     *
     * @param enquadramento the enquadramento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enquadramento, or with status {@code 400 (Bad Request)} if the enquadramento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Enquadramento> createEnquadramento(@RequestBody Enquadramento enquadramento) throws URISyntaxException {
        log.debug("REST request to save Enquadramento : {}", enquadramento);
        if (enquadramento.getId() != null) {
            throw new BadRequestAlertException("A new enquadramento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        enquadramento = enquadramentoRepository.save(enquadramento);
        return ResponseEntity.created(new URI("/api/enquadramentos/" + enquadramento.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, enquadramento.getId().toString()))
            .body(enquadramento);
    }

    /**
     * {@code PUT  /enquadramentos/:id} : Updates an existing enquadramento.
     *
     * @param id the id of the enquadramento to save.
     * @param enquadramento the enquadramento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enquadramento,
     * or with status {@code 400 (Bad Request)} if the enquadramento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enquadramento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Enquadramento> updateEnquadramento(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Enquadramento enquadramento
    ) throws URISyntaxException {
        log.debug("REST request to update Enquadramento : {}, {}", id, enquadramento);
        if (enquadramento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enquadramento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enquadramentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        enquadramento = enquadramentoRepository.save(enquadramento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enquadramento.getId().toString()))
            .body(enquadramento);
    }

    /**
     * {@code PATCH  /enquadramentos/:id} : Partial updates given fields of an existing enquadramento, field will ignore if it is null
     *
     * @param id the id of the enquadramento to save.
     * @param enquadramento the enquadramento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enquadramento,
     * or with status {@code 400 (Bad Request)} if the enquadramento is not valid,
     * or with status {@code 404 (Not Found)} if the enquadramento is not found,
     * or with status {@code 500 (Internal Server Error)} if the enquadramento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Enquadramento> partialUpdateEnquadramento(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Enquadramento enquadramento
    ) throws URISyntaxException {
        log.debug("REST request to partial update Enquadramento partially : {}, {}", id, enquadramento);
        if (enquadramento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enquadramento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enquadramentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Enquadramento> result = enquadramentoRepository
            .findById(enquadramento.getId())
            .map(existingEnquadramento -> {
                if (enquadramento.getNome() != null) {
                    existingEnquadramento.setNome(enquadramento.getNome());
                }
                if (enquadramento.getSigla() != null) {
                    existingEnquadramento.setSigla(enquadramento.getSigla());
                }
                if (enquadramento.getLimiteInicial() != null) {
                    existingEnquadramento.setLimiteInicial(enquadramento.getLimiteInicial());
                }
                if (enquadramento.getLimiteFinal() != null) {
                    existingEnquadramento.setLimiteFinal(enquadramento.getLimiteFinal());
                }
                if (enquadramento.getDescricao() != null) {
                    existingEnquadramento.setDescricao(enquadramento.getDescricao());
                }

                return existingEnquadramento;
            })
            .map(enquadramentoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enquadramento.getId().toString())
        );
    }

    /**
     * {@code GET  /enquadramentos} : get all the enquadramentos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enquadramentos in body.
     */
    @GetMapping("")
    public List<Enquadramento> getAllEnquadramentos() {
        log.debug("REST request to get all Enquadramentos");
        return enquadramentoRepository.findAll();
    }

    /**
     * {@code GET  /enquadramentos/:id} : get the "id" enquadramento.
     *
     * @param id the id of the enquadramento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enquadramento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Enquadramento> getEnquadramento(@PathVariable("id") Long id) {
        log.debug("REST request to get Enquadramento : {}", id);
        Optional<Enquadramento> enquadramento = enquadramentoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(enquadramento);
    }

    /**
     * {@code DELETE  /enquadramentos/:id} : delete the "id" enquadramento.
     *
     * @param id the id of the enquadramento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnquadramento(@PathVariable("id") Long id) {
        log.debug("REST request to delete Enquadramento : {}", id);
        enquadramentoRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
