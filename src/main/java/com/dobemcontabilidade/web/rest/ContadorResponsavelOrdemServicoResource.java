package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.ContadorResponsavelOrdemServico;
import com.dobemcontabilidade.repository.ContadorResponsavelOrdemServicoRepository;
import com.dobemcontabilidade.service.ContadorResponsavelOrdemServicoService;
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
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dobemcontabilidade.domain.ContadorResponsavelOrdemServico}.
 */
@RestController
@RequestMapping("/api/contador-responsavel-ordem-servicos")
public class ContadorResponsavelOrdemServicoResource {

    private static final Logger log = LoggerFactory.getLogger(ContadorResponsavelOrdemServicoResource.class);

    private static final String ENTITY_NAME = "contadorResponsavelOrdemServico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContadorResponsavelOrdemServicoService contadorResponsavelOrdemServicoService;

    private final ContadorResponsavelOrdemServicoRepository contadorResponsavelOrdemServicoRepository;

    public ContadorResponsavelOrdemServicoResource(
        ContadorResponsavelOrdemServicoService contadorResponsavelOrdemServicoService,
        ContadorResponsavelOrdemServicoRepository contadorResponsavelOrdemServicoRepository
    ) {
        this.contadorResponsavelOrdemServicoService = contadorResponsavelOrdemServicoService;
        this.contadorResponsavelOrdemServicoRepository = contadorResponsavelOrdemServicoRepository;
    }

    /**
     * {@code POST  /contador-responsavel-ordem-servicos} : Create a new contadorResponsavelOrdemServico.
     *
     * @param contadorResponsavelOrdemServico the contadorResponsavelOrdemServico to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contadorResponsavelOrdemServico, or with status {@code 400 (Bad Request)} if the contadorResponsavelOrdemServico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ContadorResponsavelOrdemServico> createContadorResponsavelOrdemServico(
        @Valid @RequestBody ContadorResponsavelOrdemServico contadorResponsavelOrdemServico
    ) throws URISyntaxException {
        log.debug("REST request to save ContadorResponsavelOrdemServico : {}", contadorResponsavelOrdemServico);
        if (contadorResponsavelOrdemServico.getId() != null) {
            throw new BadRequestAlertException("A new contadorResponsavelOrdemServico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        contadorResponsavelOrdemServico = contadorResponsavelOrdemServicoService.save(contadorResponsavelOrdemServico);
        return ResponseEntity.created(new URI("/api/contador-responsavel-ordem-servicos/" + contadorResponsavelOrdemServico.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, contadorResponsavelOrdemServico.getId().toString())
            )
            .body(contadorResponsavelOrdemServico);
    }

    /**
     * {@code PUT  /contador-responsavel-ordem-servicos/:id} : Updates an existing contadorResponsavelOrdemServico.
     *
     * @param id the id of the contadorResponsavelOrdemServico to save.
     * @param contadorResponsavelOrdemServico the contadorResponsavelOrdemServico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contadorResponsavelOrdemServico,
     * or with status {@code 400 (Bad Request)} if the contadorResponsavelOrdemServico is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contadorResponsavelOrdemServico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ContadorResponsavelOrdemServico> updateContadorResponsavelOrdemServico(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ContadorResponsavelOrdemServico contadorResponsavelOrdemServico
    ) throws URISyntaxException {
        log.debug("REST request to update ContadorResponsavelOrdemServico : {}, {}", id, contadorResponsavelOrdemServico);
        if (contadorResponsavelOrdemServico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contadorResponsavelOrdemServico.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contadorResponsavelOrdemServicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        contadorResponsavelOrdemServico = contadorResponsavelOrdemServicoService.update(contadorResponsavelOrdemServico);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contadorResponsavelOrdemServico.getId().toString())
            )
            .body(contadorResponsavelOrdemServico);
    }

    /**
     * {@code PATCH  /contador-responsavel-ordem-servicos/:id} : Partial updates given fields of an existing contadorResponsavelOrdemServico, field will ignore if it is null
     *
     * @param id the id of the contadorResponsavelOrdemServico to save.
     * @param contadorResponsavelOrdemServico the contadorResponsavelOrdemServico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contadorResponsavelOrdemServico,
     * or with status {@code 400 (Bad Request)} if the contadorResponsavelOrdemServico is not valid,
     * or with status {@code 404 (Not Found)} if the contadorResponsavelOrdemServico is not found,
     * or with status {@code 500 (Internal Server Error)} if the contadorResponsavelOrdemServico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContadorResponsavelOrdemServico> partialUpdateContadorResponsavelOrdemServico(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ContadorResponsavelOrdemServico contadorResponsavelOrdemServico
    ) throws URISyntaxException {
        log.debug("REST request to partial update ContadorResponsavelOrdemServico partially : {}, {}", id, contadorResponsavelOrdemServico);
        if (contadorResponsavelOrdemServico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contadorResponsavelOrdemServico.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contadorResponsavelOrdemServicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContadorResponsavelOrdemServico> result = contadorResponsavelOrdemServicoService.partialUpdate(
            contadorResponsavelOrdemServico
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contadorResponsavelOrdemServico.getId().toString())
        );
    }

    /**
     * {@code GET  /contador-responsavel-ordem-servicos} : get all the contadorResponsavelOrdemServicos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contadorResponsavelOrdemServicos in body.
     */
    @GetMapping("")
    public List<ContadorResponsavelOrdemServico> getAllContadorResponsavelOrdemServicos(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all ContadorResponsavelOrdemServicos");
        return contadorResponsavelOrdemServicoService.findAll();
    }

    /**
     * {@code GET  /contador-responsavel-ordem-servicos/:id} : get the "id" contadorResponsavelOrdemServico.
     *
     * @param id the id of the contadorResponsavelOrdemServico to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contadorResponsavelOrdemServico, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContadorResponsavelOrdemServico> getContadorResponsavelOrdemServico(@PathVariable("id") Long id) {
        log.debug("REST request to get ContadorResponsavelOrdemServico : {}", id);
        Optional<ContadorResponsavelOrdemServico> contadorResponsavelOrdemServico = contadorResponsavelOrdemServicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contadorResponsavelOrdemServico);
    }

    /**
     * {@code DELETE  /contador-responsavel-ordem-servicos/:id} : delete the "id" contadorResponsavelOrdemServico.
     *
     * @param id the id of the contadorResponsavelOrdemServico to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContadorResponsavelOrdemServico(@PathVariable("id") Long id) {
        log.debug("REST request to delete ContadorResponsavelOrdemServico : {}", id);
        contadorResponsavelOrdemServicoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
