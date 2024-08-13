package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.ServicoContabilEtapaFluxoModelo;
import com.dobemcontabilidade.repository.ServicoContabilEtapaFluxoModeloRepository;
import com.dobemcontabilidade.service.ServicoContabilEtapaFluxoModeloService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.ServicoContabilEtapaFluxoModelo}.
 */
@RestController
@RequestMapping("/api/servico-contabil-etapa-fluxo-modelos")
public class ServicoContabilEtapaFluxoModeloResource {

    private static final Logger log = LoggerFactory.getLogger(ServicoContabilEtapaFluxoModeloResource.class);

    private static final String ENTITY_NAME = "servicoContabilEtapaFluxoModelo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServicoContabilEtapaFluxoModeloService servicoContabilEtapaFluxoModeloService;

    private final ServicoContabilEtapaFluxoModeloRepository servicoContabilEtapaFluxoModeloRepository;

    public ServicoContabilEtapaFluxoModeloResource(
        ServicoContabilEtapaFluxoModeloService servicoContabilEtapaFluxoModeloService,
        ServicoContabilEtapaFluxoModeloRepository servicoContabilEtapaFluxoModeloRepository
    ) {
        this.servicoContabilEtapaFluxoModeloService = servicoContabilEtapaFluxoModeloService;
        this.servicoContabilEtapaFluxoModeloRepository = servicoContabilEtapaFluxoModeloRepository;
    }

    /**
     * {@code POST  /servico-contabil-etapa-fluxo-modelos} : Create a new servicoContabilEtapaFluxoModelo.
     *
     * @param servicoContabilEtapaFluxoModelo the servicoContabilEtapaFluxoModelo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servicoContabilEtapaFluxoModelo, or with status {@code 400 (Bad Request)} if the servicoContabilEtapaFluxoModelo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ServicoContabilEtapaFluxoModelo> createServicoContabilEtapaFluxoModelo(
        @Valid @RequestBody ServicoContabilEtapaFluxoModelo servicoContabilEtapaFluxoModelo
    ) throws URISyntaxException {
        log.debug("REST request to save ServicoContabilEtapaFluxoModelo : {}", servicoContabilEtapaFluxoModelo);
        if (servicoContabilEtapaFluxoModelo.getId() != null) {
            throw new BadRequestAlertException("A new servicoContabilEtapaFluxoModelo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        servicoContabilEtapaFluxoModelo = servicoContabilEtapaFluxoModeloService.save(servicoContabilEtapaFluxoModelo);
        return ResponseEntity.created(new URI("/api/servico-contabil-etapa-fluxo-modelos/" + servicoContabilEtapaFluxoModelo.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, servicoContabilEtapaFluxoModelo.getId().toString())
            )
            .body(servicoContabilEtapaFluxoModelo);
    }

    /**
     * {@code PUT  /servico-contabil-etapa-fluxo-modelos/:id} : Updates an existing servicoContabilEtapaFluxoModelo.
     *
     * @param id the id of the servicoContabilEtapaFluxoModelo to save.
     * @param servicoContabilEtapaFluxoModelo the servicoContabilEtapaFluxoModelo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servicoContabilEtapaFluxoModelo,
     * or with status {@code 400 (Bad Request)} if the servicoContabilEtapaFluxoModelo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the servicoContabilEtapaFluxoModelo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServicoContabilEtapaFluxoModelo> updateServicoContabilEtapaFluxoModelo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServicoContabilEtapaFluxoModelo servicoContabilEtapaFluxoModelo
    ) throws URISyntaxException {
        log.debug("REST request to update ServicoContabilEtapaFluxoModelo : {}, {}", id, servicoContabilEtapaFluxoModelo);
        if (servicoContabilEtapaFluxoModelo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servicoContabilEtapaFluxoModelo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servicoContabilEtapaFluxoModeloRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        servicoContabilEtapaFluxoModelo = servicoContabilEtapaFluxoModeloService.update(servicoContabilEtapaFluxoModelo);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servicoContabilEtapaFluxoModelo.getId().toString())
            )
            .body(servicoContabilEtapaFluxoModelo);
    }

    /**
     * {@code PATCH  /servico-contabil-etapa-fluxo-modelos/:id} : Partial updates given fields of an existing servicoContabilEtapaFluxoModelo, field will ignore if it is null
     *
     * @param id the id of the servicoContabilEtapaFluxoModelo to save.
     * @param servicoContabilEtapaFluxoModelo the servicoContabilEtapaFluxoModelo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servicoContabilEtapaFluxoModelo,
     * or with status {@code 400 (Bad Request)} if the servicoContabilEtapaFluxoModelo is not valid,
     * or with status {@code 404 (Not Found)} if the servicoContabilEtapaFluxoModelo is not found,
     * or with status {@code 500 (Internal Server Error)} if the servicoContabilEtapaFluxoModelo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServicoContabilEtapaFluxoModelo> partialUpdateServicoContabilEtapaFluxoModelo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServicoContabilEtapaFluxoModelo servicoContabilEtapaFluxoModelo
    ) throws URISyntaxException {
        log.debug("REST request to partial update ServicoContabilEtapaFluxoModelo partially : {}, {}", id, servicoContabilEtapaFluxoModelo);
        if (servicoContabilEtapaFluxoModelo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servicoContabilEtapaFluxoModelo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servicoContabilEtapaFluxoModeloRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServicoContabilEtapaFluxoModelo> result = servicoContabilEtapaFluxoModeloService.partialUpdate(
            servicoContabilEtapaFluxoModelo
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servicoContabilEtapaFluxoModelo.getId().toString())
        );
    }

    /**
     * {@code GET  /servico-contabil-etapa-fluxo-modelos} : get all the servicoContabilEtapaFluxoModelos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servicoContabilEtapaFluxoModelos in body.
     */
    @GetMapping("")
    public List<ServicoContabilEtapaFluxoModelo> getAllServicoContabilEtapaFluxoModelos(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all ServicoContabilEtapaFluxoModelos");
        return servicoContabilEtapaFluxoModeloService.findAll();
    }

    /**
     * {@code GET  /servico-contabil-etapa-fluxo-modelos/:id} : get the "id" servicoContabilEtapaFluxoModelo.
     *
     * @param id the id of the servicoContabilEtapaFluxoModelo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the servicoContabilEtapaFluxoModelo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServicoContabilEtapaFluxoModelo> getServicoContabilEtapaFluxoModelo(@PathVariable("id") Long id) {
        log.debug("REST request to get ServicoContabilEtapaFluxoModelo : {}", id);
        Optional<ServicoContabilEtapaFluxoModelo> servicoContabilEtapaFluxoModelo = servicoContabilEtapaFluxoModeloService.findOne(id);
        return ResponseUtil.wrapOrNotFound(servicoContabilEtapaFluxoModelo);
    }

    /**
     * {@code DELETE  /servico-contabil-etapa-fluxo-modelos/:id} : delete the "id" servicoContabilEtapaFluxoModelo.
     *
     * @param id the id of the servicoContabilEtapaFluxoModelo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicoContabilEtapaFluxoModelo(@PathVariable("id") Long id) {
        log.debug("REST request to delete ServicoContabilEtapaFluxoModelo : {}", id);
        servicoContabilEtapaFluxoModeloService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
