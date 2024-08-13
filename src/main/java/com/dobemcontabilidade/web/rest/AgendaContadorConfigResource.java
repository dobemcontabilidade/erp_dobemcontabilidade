package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.AgendaContadorConfig;
import com.dobemcontabilidade.repository.AgendaContadorConfigRepository;
import com.dobemcontabilidade.service.AgendaContadorConfigService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AgendaContadorConfig}.
 */
@RestController
@RequestMapping("/api/agenda-contador-configs")
public class AgendaContadorConfigResource {

    private static final Logger log = LoggerFactory.getLogger(AgendaContadorConfigResource.class);

    private static final String ENTITY_NAME = "agendaContadorConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgendaContadorConfigService agendaContadorConfigService;

    private final AgendaContadorConfigRepository agendaContadorConfigRepository;

    public AgendaContadorConfigResource(
        AgendaContadorConfigService agendaContadorConfigService,
        AgendaContadorConfigRepository agendaContadorConfigRepository
    ) {
        this.agendaContadorConfigService = agendaContadorConfigService;
        this.agendaContadorConfigRepository = agendaContadorConfigRepository;
    }

    /**
     * {@code POST  /agenda-contador-configs} : Create a new agendaContadorConfig.
     *
     * @param agendaContadorConfig the agendaContadorConfig to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agendaContadorConfig, or with status {@code 400 (Bad Request)} if the agendaContadorConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AgendaContadorConfig> createAgendaContadorConfig(@Valid @RequestBody AgendaContadorConfig agendaContadorConfig)
        throws URISyntaxException {
        log.debug("REST request to save AgendaContadorConfig : {}", agendaContadorConfig);
        if (agendaContadorConfig.getId() != null) {
            throw new BadRequestAlertException("A new agendaContadorConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        agendaContadorConfig = agendaContadorConfigService.save(agendaContadorConfig);
        return ResponseEntity.created(new URI("/api/agenda-contador-configs/" + agendaContadorConfig.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, agendaContadorConfig.getId().toString()))
            .body(agendaContadorConfig);
    }

    /**
     * {@code PUT  /agenda-contador-configs/:id} : Updates an existing agendaContadorConfig.
     *
     * @param id the id of the agendaContadorConfig to save.
     * @param agendaContadorConfig the agendaContadorConfig to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agendaContadorConfig,
     * or with status {@code 400 (Bad Request)} if the agendaContadorConfig is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agendaContadorConfig couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AgendaContadorConfig> updateAgendaContadorConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AgendaContadorConfig agendaContadorConfig
    ) throws URISyntaxException {
        log.debug("REST request to update AgendaContadorConfig : {}, {}", id, agendaContadorConfig);
        if (agendaContadorConfig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, agendaContadorConfig.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!agendaContadorConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        agendaContadorConfig = agendaContadorConfigService.update(agendaContadorConfig);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agendaContadorConfig.getId().toString()))
            .body(agendaContadorConfig);
    }

    /**
     * {@code PATCH  /agenda-contador-configs/:id} : Partial updates given fields of an existing agendaContadorConfig, field will ignore if it is null
     *
     * @param id the id of the agendaContadorConfig to save.
     * @param agendaContadorConfig the agendaContadorConfig to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agendaContadorConfig,
     * or with status {@code 400 (Bad Request)} if the agendaContadorConfig is not valid,
     * or with status {@code 404 (Not Found)} if the agendaContadorConfig is not found,
     * or with status {@code 500 (Internal Server Error)} if the agendaContadorConfig couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AgendaContadorConfig> partialUpdateAgendaContadorConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AgendaContadorConfig agendaContadorConfig
    ) throws URISyntaxException {
        log.debug("REST request to partial update AgendaContadorConfig partially : {}, {}", id, agendaContadorConfig);
        if (agendaContadorConfig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, agendaContadorConfig.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!agendaContadorConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AgendaContadorConfig> result = agendaContadorConfigService.partialUpdate(agendaContadorConfig);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agendaContadorConfig.getId().toString())
        );
    }

    /**
     * {@code GET  /agenda-contador-configs} : get all the agendaContadorConfigs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agendaContadorConfigs in body.
     */
    @GetMapping("")
    public List<AgendaContadorConfig> getAllAgendaContadorConfigs() {
        log.debug("REST request to get all AgendaContadorConfigs");
        return agendaContadorConfigService.findAll();
    }

    /**
     * {@code GET  /agenda-contador-configs/:id} : get the "id" agendaContadorConfig.
     *
     * @param id the id of the agendaContadorConfig to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agendaContadorConfig, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AgendaContadorConfig> getAgendaContadorConfig(@PathVariable("id") Long id) {
        log.debug("REST request to get AgendaContadorConfig : {}", id);
        Optional<AgendaContadorConfig> agendaContadorConfig = agendaContadorConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(agendaContadorConfig);
    }

    /**
     * {@code DELETE  /agenda-contador-configs/:id} : delete the "id" agendaContadorConfig.
     *
     * @param id the id of the agendaContadorConfig to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgendaContadorConfig(@PathVariable("id") Long id) {
        log.debug("REST request to delete AgendaContadorConfig : {}", id);
        agendaContadorConfigService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
