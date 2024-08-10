package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.PerfilRedeSocialRepository;
import com.dobemcontabilidade.service.PerfilRedeSocialQueryService;
import com.dobemcontabilidade.service.PerfilRedeSocialService;
import com.dobemcontabilidade.service.criteria.PerfilRedeSocialCriteria;
import com.dobemcontabilidade.service.dto.PerfilRedeSocialDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.PerfilRedeSocial}.
 */
@RestController
@RequestMapping("/api/perfil-rede-socials")
public class PerfilRedeSocialResource {

    private static final Logger log = LoggerFactory.getLogger(PerfilRedeSocialResource.class);

    private static final String ENTITY_NAME = "perfilRedeSocial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PerfilRedeSocialService perfilRedeSocialService;

    private final PerfilRedeSocialRepository perfilRedeSocialRepository;

    private final PerfilRedeSocialQueryService perfilRedeSocialQueryService;

    public PerfilRedeSocialResource(
        PerfilRedeSocialService perfilRedeSocialService,
        PerfilRedeSocialRepository perfilRedeSocialRepository,
        PerfilRedeSocialQueryService perfilRedeSocialQueryService
    ) {
        this.perfilRedeSocialService = perfilRedeSocialService;
        this.perfilRedeSocialRepository = perfilRedeSocialRepository;
        this.perfilRedeSocialQueryService = perfilRedeSocialQueryService;
    }

    /**
     * {@code POST  /perfil-rede-socials} : Create a new perfilRedeSocial.
     *
     * @param perfilRedeSocialDTO the perfilRedeSocialDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new perfilRedeSocialDTO, or with status {@code 400 (Bad Request)} if the perfilRedeSocial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PerfilRedeSocialDTO> createPerfilRedeSocial(@Valid @RequestBody PerfilRedeSocialDTO perfilRedeSocialDTO)
        throws URISyntaxException {
        log.debug("REST request to save PerfilRedeSocial : {}", perfilRedeSocialDTO);
        if (perfilRedeSocialDTO.getId() != null) {
            throw new BadRequestAlertException("A new perfilRedeSocial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        perfilRedeSocialDTO = perfilRedeSocialService.save(perfilRedeSocialDTO);
        return ResponseEntity.created(new URI("/api/perfil-rede-socials/" + perfilRedeSocialDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, perfilRedeSocialDTO.getId().toString()))
            .body(perfilRedeSocialDTO);
    }

    /**
     * {@code PUT  /perfil-rede-socials/:id} : Updates an existing perfilRedeSocial.
     *
     * @param id the id of the perfilRedeSocialDTO to save.
     * @param perfilRedeSocialDTO the perfilRedeSocialDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated perfilRedeSocialDTO,
     * or with status {@code 400 (Bad Request)} if the perfilRedeSocialDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the perfilRedeSocialDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PerfilRedeSocialDTO> updatePerfilRedeSocial(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PerfilRedeSocialDTO perfilRedeSocialDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PerfilRedeSocial : {}, {}", id, perfilRedeSocialDTO);
        if (perfilRedeSocialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, perfilRedeSocialDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!perfilRedeSocialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        perfilRedeSocialDTO = perfilRedeSocialService.update(perfilRedeSocialDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, perfilRedeSocialDTO.getId().toString()))
            .body(perfilRedeSocialDTO);
    }

    /**
     * {@code PATCH  /perfil-rede-socials/:id} : Partial updates given fields of an existing perfilRedeSocial, field will ignore if it is null
     *
     * @param id the id of the perfilRedeSocialDTO to save.
     * @param perfilRedeSocialDTO the perfilRedeSocialDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated perfilRedeSocialDTO,
     * or with status {@code 400 (Bad Request)} if the perfilRedeSocialDTO is not valid,
     * or with status {@code 404 (Not Found)} if the perfilRedeSocialDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the perfilRedeSocialDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PerfilRedeSocialDTO> partialUpdatePerfilRedeSocial(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PerfilRedeSocialDTO perfilRedeSocialDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PerfilRedeSocial partially : {}, {}", id, perfilRedeSocialDTO);
        if (perfilRedeSocialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, perfilRedeSocialDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!perfilRedeSocialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PerfilRedeSocialDTO> result = perfilRedeSocialService.partialUpdate(perfilRedeSocialDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, perfilRedeSocialDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /perfil-rede-socials} : get all the perfilRedeSocials.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of perfilRedeSocials in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PerfilRedeSocialDTO>> getAllPerfilRedeSocials(
        PerfilRedeSocialCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PerfilRedeSocials by criteria: {}", criteria);

        Page<PerfilRedeSocialDTO> page = perfilRedeSocialQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /perfil-rede-socials/count} : count all the perfilRedeSocials.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countPerfilRedeSocials(PerfilRedeSocialCriteria criteria) {
        log.debug("REST request to count PerfilRedeSocials by criteria: {}", criteria);
        return ResponseEntity.ok().body(perfilRedeSocialQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /perfil-rede-socials/:id} : get the "id" perfilRedeSocial.
     *
     * @param id the id of the perfilRedeSocialDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the perfilRedeSocialDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PerfilRedeSocialDTO> getPerfilRedeSocial(@PathVariable("id") Long id) {
        log.debug("REST request to get PerfilRedeSocial : {}", id);
        Optional<PerfilRedeSocialDTO> perfilRedeSocialDTO = perfilRedeSocialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(perfilRedeSocialDTO);
    }

    /**
     * {@code DELETE  /perfil-rede-socials/:id} : delete the "id" perfilRedeSocial.
     *
     * @param id the id of the perfilRedeSocialDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerfilRedeSocial(@PathVariable("id") Long id) {
        log.debug("REST request to delete PerfilRedeSocial : {}", id);
        perfilRedeSocialService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
