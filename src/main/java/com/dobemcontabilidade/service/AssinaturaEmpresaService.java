package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AssinaturaEmpresa;
import com.dobemcontabilidade.repository.AssinaturaEmpresaRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AssinaturaEmpresa}.
 */
@Service
@Transactional
public class AssinaturaEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(AssinaturaEmpresaService.class);

    private final AssinaturaEmpresaRepository assinaturaEmpresaRepository;

    public AssinaturaEmpresaService(AssinaturaEmpresaRepository assinaturaEmpresaRepository) {
        this.assinaturaEmpresaRepository = assinaturaEmpresaRepository;
    }

    /**
     * Save a assinaturaEmpresa.
     *
     * @param assinaturaEmpresa the entity to save.
     * @return the persisted entity.
     */
    public AssinaturaEmpresa save(AssinaturaEmpresa assinaturaEmpresa) {
        log.debug("Request to save AssinaturaEmpresa : {}", assinaturaEmpresa);
        return assinaturaEmpresaRepository.save(assinaturaEmpresa);
    }

    /**
     * Update a assinaturaEmpresa.
     *
     * @param assinaturaEmpresa the entity to save.
     * @return the persisted entity.
     */
    public AssinaturaEmpresa update(AssinaturaEmpresa assinaturaEmpresa) {
        log.debug("Request to update AssinaturaEmpresa : {}", assinaturaEmpresa);
        return assinaturaEmpresaRepository.save(assinaturaEmpresa);
    }

    /**
     * Partially update a assinaturaEmpresa.
     *
     * @param assinaturaEmpresa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AssinaturaEmpresa> partialUpdate(AssinaturaEmpresa assinaturaEmpresa) {
        log.debug("Request to partially update AssinaturaEmpresa : {}", assinaturaEmpresa);

        return assinaturaEmpresaRepository
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
    }

    /**
     * Get all the assinaturaEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AssinaturaEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return assinaturaEmpresaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one assinaturaEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AssinaturaEmpresa> findOne(Long id) {
        log.debug("Request to get AssinaturaEmpresa : {}", id);
        return assinaturaEmpresaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the assinaturaEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AssinaturaEmpresa : {}", id);
        assinaturaEmpresaRepository.deleteById(id);
    }
}
