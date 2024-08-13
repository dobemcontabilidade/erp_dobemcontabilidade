package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.ImpostoAPagarEmpresa;
import com.dobemcontabilidade.repository.ImpostoAPagarEmpresaRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.ImpostoAPagarEmpresa}.
 */
@Service
@Transactional
public class ImpostoAPagarEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(ImpostoAPagarEmpresaService.class);

    private final ImpostoAPagarEmpresaRepository impostoAPagarEmpresaRepository;

    public ImpostoAPagarEmpresaService(ImpostoAPagarEmpresaRepository impostoAPagarEmpresaRepository) {
        this.impostoAPagarEmpresaRepository = impostoAPagarEmpresaRepository;
    }

    /**
     * Save a impostoAPagarEmpresa.
     *
     * @param impostoAPagarEmpresa the entity to save.
     * @return the persisted entity.
     */
    public ImpostoAPagarEmpresa save(ImpostoAPagarEmpresa impostoAPagarEmpresa) {
        log.debug("Request to save ImpostoAPagarEmpresa : {}", impostoAPagarEmpresa);
        return impostoAPagarEmpresaRepository.save(impostoAPagarEmpresa);
    }

    /**
     * Update a impostoAPagarEmpresa.
     *
     * @param impostoAPagarEmpresa the entity to save.
     * @return the persisted entity.
     */
    public ImpostoAPagarEmpresa update(ImpostoAPagarEmpresa impostoAPagarEmpresa) {
        log.debug("Request to update ImpostoAPagarEmpresa : {}", impostoAPagarEmpresa);
        return impostoAPagarEmpresaRepository.save(impostoAPagarEmpresa);
    }

    /**
     * Partially update a impostoAPagarEmpresa.
     *
     * @param impostoAPagarEmpresa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ImpostoAPagarEmpresa> partialUpdate(ImpostoAPagarEmpresa impostoAPagarEmpresa) {
        log.debug("Request to partially update ImpostoAPagarEmpresa : {}", impostoAPagarEmpresa);

        return impostoAPagarEmpresaRepository
            .findById(impostoAPagarEmpresa.getId())
            .map(existingImpostoAPagarEmpresa -> {
                if (impostoAPagarEmpresa.getDataVencimento() != null) {
                    existingImpostoAPagarEmpresa.setDataVencimento(impostoAPagarEmpresa.getDataVencimento());
                }
                if (impostoAPagarEmpresa.getDataPagamento() != null) {
                    existingImpostoAPagarEmpresa.setDataPagamento(impostoAPagarEmpresa.getDataPagamento());
                }
                if (impostoAPagarEmpresa.getValor() != null) {
                    existingImpostoAPagarEmpresa.setValor(impostoAPagarEmpresa.getValor());
                }
                if (impostoAPagarEmpresa.getValorMulta() != null) {
                    existingImpostoAPagarEmpresa.setValorMulta(impostoAPagarEmpresa.getValorMulta());
                }
                if (impostoAPagarEmpresa.getUrlArquivoPagamento() != null) {
                    existingImpostoAPagarEmpresa.setUrlArquivoPagamento(impostoAPagarEmpresa.getUrlArquivoPagamento());
                }
                if (impostoAPagarEmpresa.getUrlArquivoComprovante() != null) {
                    existingImpostoAPagarEmpresa.setUrlArquivoComprovante(impostoAPagarEmpresa.getUrlArquivoComprovante());
                }
                if (impostoAPagarEmpresa.getSituacaoPagamentoImpostoEnum() != null) {
                    existingImpostoAPagarEmpresa.setSituacaoPagamentoImpostoEnum(impostoAPagarEmpresa.getSituacaoPagamentoImpostoEnum());
                }

                return existingImpostoAPagarEmpresa;
            })
            .map(impostoAPagarEmpresaRepository::save);
    }

    /**
     * Get all the impostoAPagarEmpresas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ImpostoAPagarEmpresa> findAll() {
        log.debug("Request to get all ImpostoAPagarEmpresas");
        return impostoAPagarEmpresaRepository.findAll();
    }

    /**
     * Get all the impostoAPagarEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ImpostoAPagarEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return impostoAPagarEmpresaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one impostoAPagarEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ImpostoAPagarEmpresa> findOne(Long id) {
        log.debug("Request to get ImpostoAPagarEmpresa : {}", id);
        return impostoAPagarEmpresaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the impostoAPagarEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ImpostoAPagarEmpresa : {}", id);
        impostoAPagarEmpresaRepository.deleteById(id);
    }
}
