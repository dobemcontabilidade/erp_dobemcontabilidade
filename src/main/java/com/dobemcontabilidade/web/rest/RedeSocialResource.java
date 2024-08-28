package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.RedeSocial;
import com.dobemcontabilidade.repository.RedeSocialRepository;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.RedeSocial}.
 */
@RestController
@RequestMapping("/api/rede-socials")
@Transactional
public class RedeSocialResource {

    private static final Logger log = LoggerFactory.getLogger(RedeSocialResource.class);

    private static final String ENTITY_NAME = "redeSocial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RedeSocialRepository redeSocialRepository;

    public RedeSocialResource(RedeSocialRepository redeSocialRepository) {
        this.redeSocialRepository = redeSocialRepository;
    }

    /**
     * {@code POST  /rede-socials} : Create a new redeSocial.
     *
     * @param redeSocial the redeSocial to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new redeSocial, or with status {@code 400 (Bad Request)} if the redeSocial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RedeSocial> createRedeSocial(@Valid @RequestBody RedeSocial redeSocial) throws URISyntaxException {
        log.debug("REST request to save RedeSocial : {}", redeSocial);
        if (redeSocial.getId() != null) {
            throw new BadRequestAlertException("A new redeSocial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        redeSocial = redeSocialRepository.save(redeSocial);
        return ResponseEntity.created(new URI("/api/rede-socials/" + redeSocial.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, redeSocial.getId().toString()))
            .body(redeSocial);
    }

    /**
     * {@code PUT  /rede-socials/:id} : Updates an existing redeSocial.
     *
     * @param id the id of the redeSocial to save.
     * @param redeSocial the redeSocial to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated redeSocial,
     * or with status {@code 400 (Bad Request)} if the redeSocial is not valid,
     * or with status {@code 500 (Internal Server Error)} if the redeSocial couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RedeSocial> updateRedeSocial(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RedeSocial redeSocial
    ) throws URISyntaxException {
        log.debug("REST request to update RedeSocial : {}, {}", id, redeSocial);
        if (redeSocial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, redeSocial.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!redeSocialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        redeSocial = redeSocialRepository.save(redeSocial);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, redeSocial.getId().toString()))
            .body(redeSocial);
    }

    /**
     * {@code PATCH  /rede-socials/:id} : Partial updates given fields of an existing redeSocial, field will ignore if it is null
     *
     * @param id the id of the redeSocial to save.
     * @param redeSocial the redeSocial to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated redeSocial,
     * or with status {@code 400 (Bad Request)} if the redeSocial is not valid,
     * or with status {@code 404 (Not Found)} if the redeSocial is not found,
     * or with status {@code 500 (Internal Server Error)} if the redeSocial couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RedeSocial> partialUpdateRedeSocial(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RedeSocial redeSocial
    ) throws URISyntaxException {
        log.debug("REST request to partial update RedeSocial partially : {}, {}", id, redeSocial);
        if (redeSocial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, redeSocial.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!redeSocialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RedeSocial> result = redeSocialRepository
            .findById(redeSocial.getId())
            .map(existingRedeSocial -> {
                if (redeSocial.getNome() != null) {
                    existingRedeSocial.setNome(redeSocial.getNome());
                }
                if (redeSocial.getDescricao() != null) {
                    existingRedeSocial.setDescricao(redeSocial.getDescricao());
                }
                if (redeSocial.getUrl() != null) {
                    existingRedeSocial.setUrl(redeSocial.getUrl());
                }
                if (redeSocial.getLogo() != null) {
                    existingRedeSocial.setLogo(redeSocial.getLogo());
                }

                return existingRedeSocial;
            })
            .map(redeSocialRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, redeSocial.getId().toString())
        );
    }

    /**
     * {@code GET  /rede-socials} : get all the redeSocials.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of redeSocials in body.
     */
    @GetMapping("")
    public List<RedeSocial> getAllRedeSocials() {
        log.debug("REST request to get all RedeSocials");
        return redeSocialRepository.findAll();
    }

    /**
     * {@code GET  /rede-socials/:id} : get the "id" redeSocial.
     *
     * @param id the id of the redeSocial to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the redeSocial, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RedeSocial> getRedeSocial(@PathVariable("id") Long id) {
        log.debug("REST request to get RedeSocial : {}", id);
        Optional<RedeSocial> redeSocial = redeSocialRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(redeSocial);
    }

    /**
     * {@code DELETE  /rede-socials/:id} : delete the "id" redeSocial.
     *
     * @param id the id of the redeSocial to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRedeSocial(@PathVariable("id") Long id) {
        log.debug("REST request to delete RedeSocial : {}", id);
        redeSocialRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
