package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.ImpostoParcelado;
import com.dobemcontabilidade.repository.ImpostoParceladoRepository;
import com.dobemcontabilidade.service.ImpostoParceladoService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.ImpostoParcelado}.
 */
@RestController
@RequestMapping("/api/imposto-parcelados")
public class ImpostoParceladoResource {

    private static final Logger log = LoggerFactory.getLogger(ImpostoParceladoResource.class);

    private static final String ENTITY_NAME = "impostoParcelado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImpostoParceladoService impostoParceladoService;

    private final ImpostoParceladoRepository impostoParceladoRepository;

    public ImpostoParceladoResource(
        ImpostoParceladoService impostoParceladoService,
        ImpostoParceladoRepository impostoParceladoRepository
    ) {
        this.impostoParceladoService = impostoParceladoService;
        this.impostoParceladoRepository = impostoParceladoRepository;
    }

    /**
     * {@code POST  /imposto-parcelados} : Create a new impostoParcelado.
     *
     * @param impostoParcelado the impostoParcelado to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new impostoParcelado, or with status {@code 400 (Bad Request)} if the impostoParcelado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ImpostoParcelado> createImpostoParcelado(@Valid @RequestBody ImpostoParcelado impostoParcelado)
        throws URISyntaxException {
        log.debug("REST request to save ImpostoParcelado : {}", impostoParcelado);
        if (impostoParcelado.getId() != null) {
            throw new BadRequestAlertException("A new impostoParcelado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        impostoParcelado = impostoParceladoService.save(impostoParcelado);
        return ResponseEntity.created(new URI("/api/imposto-parcelados/" + impostoParcelado.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, impostoParcelado.getId().toString()))
            .body(impostoParcelado);
    }

    /**
     * {@code PUT  /imposto-parcelados/:id} : Updates an existing impostoParcelado.
     *
     * @param id the id of the impostoParcelado to save.
     * @param impostoParcelado the impostoParcelado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated impostoParcelado,
     * or with status {@code 400 (Bad Request)} if the impostoParcelado is not valid,
     * or with status {@code 500 (Internal Server Error)} if the impostoParcelado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ImpostoParcelado> updateImpostoParcelado(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ImpostoParcelado impostoParcelado
    ) throws URISyntaxException {
        log.debug("REST request to update ImpostoParcelado : {}, {}", id, impostoParcelado);
        if (impostoParcelado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, impostoParcelado.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!impostoParceladoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        impostoParcelado = impostoParceladoService.update(impostoParcelado);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, impostoParcelado.getId().toString()))
            .body(impostoParcelado);
    }

    /**
     * {@code PATCH  /imposto-parcelados/:id} : Partial updates given fields of an existing impostoParcelado, field will ignore if it is null
     *
     * @param id the id of the impostoParcelado to save.
     * @param impostoParcelado the impostoParcelado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated impostoParcelado,
     * or with status {@code 400 (Bad Request)} if the impostoParcelado is not valid,
     * or with status {@code 404 (Not Found)} if the impostoParcelado is not found,
     * or with status {@code 500 (Internal Server Error)} if the impostoParcelado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ImpostoParcelado> partialUpdateImpostoParcelado(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ImpostoParcelado impostoParcelado
    ) throws URISyntaxException {
        log.debug("REST request to partial update ImpostoParcelado partially : {}, {}", id, impostoParcelado);
        if (impostoParcelado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, impostoParcelado.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!impostoParceladoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ImpostoParcelado> result = impostoParceladoService.partialUpdate(impostoParcelado);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, impostoParcelado.getId().toString())
        );
    }

    /**
     * {@code GET  /imposto-parcelados} : get all the impostoParcelados.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of impostoParcelados in body.
     */
    @GetMapping("")
    public List<ImpostoParcelado> getAllImpostoParcelados() {
        log.debug("REST request to get all ImpostoParcelados");
        return impostoParceladoService.findAll();
    }

    /**
     * {@code GET  /imposto-parcelados/:id} : get the "id" impostoParcelado.
     *
     * @param id the id of the impostoParcelado to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the impostoParcelado, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ImpostoParcelado> getImpostoParcelado(@PathVariable("id") Long id) {
        log.debug("REST request to get ImpostoParcelado : {}", id);
        Optional<ImpostoParcelado> impostoParcelado = impostoParceladoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(impostoParcelado);
    }

    /**
     * {@code DELETE  /imposto-parcelados/:id} : delete the "id" impostoParcelado.
     *
     * @param id the id of the impostoParcelado to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImpostoParcelado(@PathVariable("id") Long id) {
        log.debug("REST request to delete ImpostoParcelado : {}", id);
        impostoParceladoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
