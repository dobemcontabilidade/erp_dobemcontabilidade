package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.AssinaturaEmpresa;
import com.dobemcontabilidade.repository.AssinaturaEmpresaRepository;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AssinaturaEmpresa}.
 */
@RestController
@RequestMapping("/api/assinatura-empresas")
@Transactional
public class AssinaturaEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(AssinaturaEmpresaResource.class);

    private static final String ENTITY_NAME = "assinaturaEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssinaturaEmpresaRepository assinaturaEmpresaRepository;

    public AssinaturaEmpresaResource(AssinaturaEmpresaRepository assinaturaEmpresaRepository) {
        this.assinaturaEmpresaRepository = assinaturaEmpresaRepository;
    }

    /**
     * {@code POST  /assinatura-empresas} : Create a new assinaturaEmpresa.
     *
     * @param assinaturaEmpresa the assinaturaEmpresa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assinaturaEmpresa, or with status {@code 400 (Bad Request)} if the assinaturaEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AssinaturaEmpresa> createAssinaturaEmpresa(@Valid @RequestBody AssinaturaEmpresa assinaturaEmpresa)
        throws URISyntaxException {
        log.debug("REST request to save AssinaturaEmpresa : {}", assinaturaEmpresa);
        if (assinaturaEmpresa.getId() != null) {
            throw new BadRequestAlertException("A new assinaturaEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        assinaturaEmpresa = assinaturaEmpresaRepository.save(assinaturaEmpresa);
        return ResponseEntity.created(new URI("/api/assinatura-empresas/" + assinaturaEmpresa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, assinaturaEmpresa.getId().toString()))
            .body(assinaturaEmpresa);
    }

    /**
     * {@code PUT  /assinatura-empresas/:id} : Updates an existing assinaturaEmpresa.
     *
     * @param id the id of the assinaturaEmpresa to save.
     * @param assinaturaEmpresa the assinaturaEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assinaturaEmpresa,
     * or with status {@code 400 (Bad Request)} if the assinaturaEmpresa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assinaturaEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AssinaturaEmpresa> updateAssinaturaEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AssinaturaEmpresa assinaturaEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to update AssinaturaEmpresa : {}, {}", id, assinaturaEmpresa);
        if (assinaturaEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assinaturaEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assinaturaEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        assinaturaEmpresa = assinaturaEmpresaRepository.save(assinaturaEmpresa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assinaturaEmpresa.getId().toString()))
            .body(assinaturaEmpresa);
    }

    /**
     * {@code PATCH  /assinatura-empresas/:id} : Partial updates given fields of an existing assinaturaEmpresa, field will ignore if it is null
     *
     * @param id the id of the assinaturaEmpresa to save.
     * @param assinaturaEmpresa the assinaturaEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assinaturaEmpresa,
     * or with status {@code 400 (Bad Request)} if the assinaturaEmpresa is not valid,
     * or with status {@code 404 (Not Found)} if the assinaturaEmpresa is not found,
     * or with status {@code 500 (Internal Server Error)} if the assinaturaEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AssinaturaEmpresa> partialUpdateAssinaturaEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AssinaturaEmpresa assinaturaEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to partial update AssinaturaEmpresa partially : {}, {}", id, assinaturaEmpresa);
        if (assinaturaEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assinaturaEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assinaturaEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AssinaturaEmpresa> result = assinaturaEmpresaRepository
            .findById(assinaturaEmpresa.getId())
            .map(existingAssinaturaEmpresa -> {
                if (assinaturaEmpresa.getRazaoSocial() != null) {
                    existingAssinaturaEmpresa.setRazaoSocial(assinaturaEmpresa.getRazaoSocial());
                }
                if (assinaturaEmpresa.getCodigoAssinatura() != null) {
                    existingAssinaturaEmpresa.setCodigoAssinatura(assinaturaEmpresa.getCodigoAssinatura());
                }
                if (assinaturaEmpresa.getValorEnquadramento() != null) {
                    existingAssinaturaEmpresa.setValorEnquadramento(assinaturaEmpresa.getValorEnquadramento());
                }
                if (assinaturaEmpresa.getValorTributacao() != null) {
                    existingAssinaturaEmpresa.setValorTributacao(assinaturaEmpresa.getValorTributacao());
                }
                if (assinaturaEmpresa.getValorRamo() != null) {
                    existingAssinaturaEmpresa.setValorRamo(assinaturaEmpresa.getValorRamo());
                }
                if (assinaturaEmpresa.getValorFuncionarios() != null) {
                    existingAssinaturaEmpresa.setValorFuncionarios(assinaturaEmpresa.getValorFuncionarios());
                }
                if (assinaturaEmpresa.getValorSocios() != null) {
                    existingAssinaturaEmpresa.setValorSocios(assinaturaEmpresa.getValorSocios());
                }
                if (assinaturaEmpresa.getValorFaturamento() != null) {
                    existingAssinaturaEmpresa.setValorFaturamento(assinaturaEmpresa.getValorFaturamento());
                }
                if (assinaturaEmpresa.getValorPlanoContabil() != null) {
                    existingAssinaturaEmpresa.setValorPlanoContabil(assinaturaEmpresa.getValorPlanoContabil());
                }
                if (assinaturaEmpresa.getValorPlanoContabilComDesconto() != null) {
                    existingAssinaturaEmpresa.setValorPlanoContabilComDesconto(assinaturaEmpresa.getValorPlanoContabilComDesconto());
                }
                if (assinaturaEmpresa.getValorPlanoContaAzulComDesconto() != null) {
                    existingAssinaturaEmpresa.setValorPlanoContaAzulComDesconto(assinaturaEmpresa.getValorPlanoContaAzulComDesconto());
                }
                if (assinaturaEmpresa.getValorMensalidade() != null) {
                    existingAssinaturaEmpresa.setValorMensalidade(assinaturaEmpresa.getValorMensalidade());
                }
                if (assinaturaEmpresa.getValorPeriodo() != null) {
                    existingAssinaturaEmpresa.setValorPeriodo(assinaturaEmpresa.getValorPeriodo());
                }
                if (assinaturaEmpresa.getValorAno() != null) {
                    existingAssinaturaEmpresa.setValorAno(assinaturaEmpresa.getValorAno());
                }
                if (assinaturaEmpresa.getDataContratacao() != null) {
                    existingAssinaturaEmpresa.setDataContratacao(assinaturaEmpresa.getDataContratacao());
                }
                if (assinaturaEmpresa.getDataEncerramento() != null) {
                    existingAssinaturaEmpresa.setDataEncerramento(assinaturaEmpresa.getDataEncerramento());
                }
                if (assinaturaEmpresa.getDiaVencimento() != null) {
                    existingAssinaturaEmpresa.setDiaVencimento(assinaturaEmpresa.getDiaVencimento());
                }
                if (assinaturaEmpresa.getSituacao() != null) {
                    existingAssinaturaEmpresa.setSituacao(assinaturaEmpresa.getSituacao());
                }
                if (assinaturaEmpresa.getTipoContrato() != null) {
                    existingAssinaturaEmpresa.setTipoContrato(assinaturaEmpresa.getTipoContrato());
                }

                return existingAssinaturaEmpresa;
            })
            .map(assinaturaEmpresaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assinaturaEmpresa.getId().toString())
        );
    }

    /**
     * {@code GET  /assinatura-empresas} : get all the assinaturaEmpresas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assinaturaEmpresas in body.
     */
    @GetMapping("")
    public List<AssinaturaEmpresa> getAllAssinaturaEmpresas(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all AssinaturaEmpresas");
        if (eagerload) {
            return assinaturaEmpresaRepository.findAllWithEagerRelationships();
        } else {
            return assinaturaEmpresaRepository.findAll();
        }
    }

    /**
     * {@code GET  /assinatura-empresas/:id} : get the "id" assinaturaEmpresa.
     *
     * @param id the id of the assinaturaEmpresa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assinaturaEmpresa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AssinaturaEmpresa> getAssinaturaEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get AssinaturaEmpresa : {}", id);
        Optional<AssinaturaEmpresa> assinaturaEmpresa = assinaturaEmpresaRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(assinaturaEmpresa);
    }

    /**
     * {@code DELETE  /assinatura-empresas/:id} : delete the "id" assinaturaEmpresa.
     *
     * @param id the id of the assinaturaEmpresa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssinaturaEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete AssinaturaEmpresa : {}", id);
        assinaturaEmpresaRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
