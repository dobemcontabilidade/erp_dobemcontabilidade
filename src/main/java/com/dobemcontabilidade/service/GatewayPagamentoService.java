package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.GatewayPagamento;
import com.dobemcontabilidade.repository.GatewayPagamentoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.GatewayPagamento}.
 */
@Service
@Transactional
public class GatewayPagamentoService {

    private static final Logger log = LoggerFactory.getLogger(GatewayPagamentoService.class);

    private final GatewayPagamentoRepository gatewayPagamentoRepository;

    public GatewayPagamentoService(GatewayPagamentoRepository gatewayPagamentoRepository) {
        this.gatewayPagamentoRepository = gatewayPagamentoRepository;
    }

    /**
     * Save a gatewayPagamento.
     *
     * @param gatewayPagamento the entity to save.
     * @return the persisted entity.
     */
    public GatewayPagamento save(GatewayPagamento gatewayPagamento) {
        log.debug("Request to save GatewayPagamento : {}", gatewayPagamento);
        return gatewayPagamentoRepository.save(gatewayPagamento);
    }

    /**
     * Update a gatewayPagamento.
     *
     * @param gatewayPagamento the entity to save.
     * @return the persisted entity.
     */
    public GatewayPagamento update(GatewayPagamento gatewayPagamento) {
        log.debug("Request to update GatewayPagamento : {}", gatewayPagamento);
        return gatewayPagamentoRepository.save(gatewayPagamento);
    }

    /**
     * Partially update a gatewayPagamento.
     *
     * @param gatewayPagamento the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GatewayPagamento> partialUpdate(GatewayPagamento gatewayPagamento) {
        log.debug("Request to partially update GatewayPagamento : {}", gatewayPagamento);

        return gatewayPagamentoRepository
            .findById(gatewayPagamento.getId())
            .map(existingGatewayPagamento -> {
                if (gatewayPagamento.getNome() != null) {
                    existingGatewayPagamento.setNome(gatewayPagamento.getNome());
                }
                if (gatewayPagamento.getDescricao() != null) {
                    existingGatewayPagamento.setDescricao(gatewayPagamento.getDescricao());
                }

                return existingGatewayPagamento;
            })
            .map(gatewayPagamentoRepository::save);
    }

    /**
     * Get all the gatewayPagamentos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GatewayPagamento> findAll() {
        log.debug("Request to get all GatewayPagamentos");
        return gatewayPagamentoRepository.findAll();
    }

    /**
     * Get one gatewayPagamento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GatewayPagamento> findOne(Long id) {
        log.debug("Request to get GatewayPagamento : {}", id);
        return gatewayPagamentoRepository.findById(id);
    }

    /**
     * Delete the gatewayPagamento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GatewayPagamento : {}", id);
        gatewayPagamentoRepository.deleteById(id);
    }
}
