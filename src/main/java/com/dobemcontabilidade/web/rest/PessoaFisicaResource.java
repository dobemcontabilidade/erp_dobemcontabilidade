package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.PessoaFisica;
import com.dobemcontabilidade.repository.PessoaFisicaRepository;
import com.dobemcontabilidade.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dobemcontabilidade.domain.PessoaFisica}.
 */
@RestController
@RequestMapping("/api/pessoa-fisicas")
@Transactional
public class PessoaFisicaResource {

    private static final Logger log = LoggerFactory.getLogger(PessoaFisicaResource.class);

    private static final String ENTITY_NAME = "pessoaFisica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PessoaFisicaRepository pessoaFisicaRepository;

    public PessoaFisicaResource(PessoaFisicaRepository pessoaFisicaRepository) {
        this.pessoaFisicaRepository = pessoaFisicaRepository;
    }

    /**
     * {@code POST  /pessoa-fisicas} : Create a new pessoaFisica.
     *
     * @param pessoaFisica the pessoaFisica to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pessoaFisica, or with status {@code 400 (Bad Request)} if the pessoaFisica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PessoaFisica> createPessoaFisica(@Valid @RequestBody PessoaFisica pessoaFisica) throws URISyntaxException {
        log.debug("REST request to save PessoaFisica : {}", pessoaFisica);
        if (pessoaFisica.getId() != null) {
            throw new BadRequestAlertException("A new pessoaFisica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        pessoaFisica = pessoaFisicaRepository.save(pessoaFisica);
        return ResponseEntity.created(new URI("/api/pessoa-fisicas/" + pessoaFisica.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, pessoaFisica.getId().toString()))
            .body(pessoaFisica);
    }

    /**
     * {@code PUT  /pessoa-fisicas/:id} : Updates an existing pessoaFisica.
     *
     * @param id the id of the pessoaFisica to save.
     * @param pessoaFisica the pessoaFisica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pessoaFisica,
     * or with status {@code 400 (Bad Request)} if the pessoaFisica is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pessoaFisica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PessoaFisica> updatePessoaFisica(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PessoaFisica pessoaFisica
    ) throws URISyntaxException {
        log.debug("REST request to update PessoaFisica : {}, {}", id, pessoaFisica);
        if (pessoaFisica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pessoaFisica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pessoaFisicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        pessoaFisica = pessoaFisicaRepository.save(pessoaFisica);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pessoaFisica.getId().toString()))
            .body(pessoaFisica);
    }

    /**
     * {@code PATCH  /pessoa-fisicas/:id} : Partial updates given fields of an existing pessoaFisica, field will ignore if it is null
     *
     * @param id the id of the pessoaFisica to save.
     * @param pessoaFisica the pessoaFisica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pessoaFisica,
     * or with status {@code 400 (Bad Request)} if the pessoaFisica is not valid,
     * or with status {@code 404 (Not Found)} if the pessoaFisica is not found,
     * or with status {@code 500 (Internal Server Error)} if the pessoaFisica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PessoaFisica> partialUpdatePessoaFisica(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PessoaFisica pessoaFisica
    ) throws URISyntaxException {
        log.debug("REST request to partial update PessoaFisica partially : {}, {}", id, pessoaFisica);
        if (pessoaFisica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pessoaFisica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pessoaFisicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PessoaFisica> result = pessoaFisicaRepository
            .findById(pessoaFisica.getId())
            .map(existingPessoaFisica -> {
                if (pessoaFisica.getNome() != null) {
                    existingPessoaFisica.setNome(pessoaFisica.getNome());
                }
                if (pessoaFisica.getCpf() != null) {
                    existingPessoaFisica.setCpf(pessoaFisica.getCpf());
                }
                if (pessoaFisica.getDataNascimento() != null) {
                    existingPessoaFisica.setDataNascimento(pessoaFisica.getDataNascimento());
                }
                if (pessoaFisica.getTituloEleitor() != null) {
                    existingPessoaFisica.setTituloEleitor(pessoaFisica.getTituloEleitor());
                }
                if (pessoaFisica.getRg() != null) {
                    existingPessoaFisica.setRg(pessoaFisica.getRg());
                }
                if (pessoaFisica.getRgOrgaoExpditor() != null) {
                    existingPessoaFisica.setRgOrgaoExpditor(pessoaFisica.getRgOrgaoExpditor());
                }
                if (pessoaFisica.getRgUfExpedicao() != null) {
                    existingPessoaFisica.setRgUfExpedicao(pessoaFisica.getRgUfExpedicao());
                }
                if (pessoaFisica.getEstadoCivil() != null) {
                    existingPessoaFisica.setEstadoCivil(pessoaFisica.getEstadoCivil());
                }
                if (pessoaFisica.getSexo() != null) {
                    existingPessoaFisica.setSexo(pessoaFisica.getSexo());
                }
                if (pessoaFisica.getUrlFotoPerfil() != null) {
                    existingPessoaFisica.setUrlFotoPerfil(pessoaFisica.getUrlFotoPerfil());
                }

                return existingPessoaFisica;
            })
            .map(pessoaFisicaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pessoaFisica.getId().toString())
        );
    }

    /**
     * {@code GET  /pessoa-fisicas} : get all the pessoaFisicas.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pessoaFisicas in body.
     */
    @GetMapping("")
    public List<PessoaFisica> getAllPessoaFisicas(@RequestParam(name = "filter", required = false) String filter) {
        if ("socio-is-null".equals(filter)) {
            log.debug("REST request to get all PessoaFisicas where socio is null");
            return StreamSupport.stream(pessoaFisicaRepository.findAll().spliterator(), false)
                .filter(pessoaFisica -> pessoaFisica.getSocio() == null)
                .toList();
        }
        log.debug("REST request to get all PessoaFisicas");
        return pessoaFisicaRepository.findAll();
    }

    /**
     * {@code GET  /pessoa-fisicas/:id} : get the "id" pessoaFisica.
     *
     * @param id the id of the pessoaFisica to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pessoaFisica, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PessoaFisica> getPessoaFisica(@PathVariable("id") Long id) {
        log.debug("REST request to get PessoaFisica : {}", id);
        Optional<PessoaFisica> pessoaFisica = pessoaFisicaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pessoaFisica);
    }

    /**
     * {@code DELETE  /pessoa-fisicas/:id} : delete the "id" pessoaFisica.
     *
     * @param id the id of the pessoaFisica to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePessoaFisica(@PathVariable("id") Long id) {
        log.debug("REST request to delete PessoaFisica : {}", id);
        pessoaFisicaRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
