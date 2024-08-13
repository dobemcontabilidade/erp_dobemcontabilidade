package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Pagamento;
import com.dobemcontabilidade.repository.PagamentoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Pagamento}.
 */
@Service
@Transactional
public class PagamentoService {

    private static final Logger log = LoggerFactory.getLogger(PagamentoService.class);

    private final PagamentoRepository pagamentoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    /**
     * Save a pagamento.
     *
     * @param pagamento the entity to save.
     * @return the persisted entity.
     */
    public Pagamento save(Pagamento pagamento) {
        log.debug("Request to save Pagamento : {}", pagamento);
        return pagamentoRepository.save(pagamento);
    }

    /**
     * Update a pagamento.
     *
     * @param pagamento the entity to save.
     * @return the persisted entity.
     */
    public Pagamento update(Pagamento pagamento) {
        log.debug("Request to update Pagamento : {}", pagamento);
        return pagamentoRepository.save(pagamento);
    }

    /**
     * Partially update a pagamento.
     *
     * @param pagamento the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Pagamento> partialUpdate(Pagamento pagamento) {
        log.debug("Request to partially update Pagamento : {}", pagamento);

        return pagamentoRepository
            .findById(pagamento.getId())
            .map(existingPagamento -> {
                if (pagamento.getDataCobranca() != null) {
                    existingPagamento.setDataCobranca(pagamento.getDataCobranca());
                }
                if (pagamento.getDataVencimento() != null) {
                    existingPagamento.setDataVencimento(pagamento.getDataVencimento());
                }
                if (pagamento.getDataPagamento() != null) {
                    existingPagamento.setDataPagamento(pagamento.getDataPagamento());
                }
                if (pagamento.getValorPago() != null) {
                    existingPagamento.setValorPago(pagamento.getValorPago());
                }
                if (pagamento.getValorCobrado() != null) {
                    existingPagamento.setValorCobrado(pagamento.getValorCobrado());
                }
                if (pagamento.getAcrescimo() != null) {
                    existingPagamento.setAcrescimo(pagamento.getAcrescimo());
                }
                if (pagamento.getMulta() != null) {
                    existingPagamento.setMulta(pagamento.getMulta());
                }
                if (pagamento.getJuros() != null) {
                    existingPagamento.setJuros(pagamento.getJuros());
                }
                if (pagamento.getSituacao() != null) {
                    existingPagamento.setSituacao(pagamento.getSituacao());
                }

                return existingPagamento;
            })
            .map(pagamentoRepository::save);
    }

    /**
     * Get all the pagamentos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Pagamento> findAllWithEagerRelationships(Pageable pageable) {
        return pagamentoRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one pagamento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Pagamento> findOne(Long id) {
        log.debug("Request to get Pagamento : {}", id);
        return pagamentoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the pagamento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Pagamento : {}", id);
        pagamentoRepository.deleteById(id);
    }
}
