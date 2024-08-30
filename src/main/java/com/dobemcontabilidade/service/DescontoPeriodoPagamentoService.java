package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.DescontoPeriodoPagamento;
import com.dobemcontabilidade.repository.DescontoPeriodoPagamentoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.DescontoPeriodoPagamento}.
 */
@Service
@Transactional
public class DescontoPeriodoPagamentoService {

    private static final Logger log = LoggerFactory.getLogger(DescontoPeriodoPagamentoService.class);

    private final DescontoPeriodoPagamentoRepository descontoPeriodoPagamentoRepository;

    public DescontoPeriodoPagamentoService(DescontoPeriodoPagamentoRepository descontoPeriodoPagamentoRepository) {
        this.descontoPeriodoPagamentoRepository = descontoPeriodoPagamentoRepository;
    }

    /**
     * Save a descontoPeriodoPagamento.
     *
     * @param descontoPeriodoPagamento the entity to save.
     * @return the persisted entity.
     */
    public DescontoPeriodoPagamento save(DescontoPeriodoPagamento descontoPeriodoPagamento) {
        log.debug("Request to save DescontoPeriodoPagamento : {}", descontoPeriodoPagamento);
        return descontoPeriodoPagamentoRepository.save(descontoPeriodoPagamento);
    }

    /**
     * Update a descontoPeriodoPagamento.
     *
     * @param descontoPeriodoPagamento the entity to save.
     * @return the persisted entity.
     */
    public DescontoPeriodoPagamento update(DescontoPeriodoPagamento descontoPeriodoPagamento) {
        log.debug("Request to update DescontoPeriodoPagamento : {}", descontoPeriodoPagamento);
        return descontoPeriodoPagamentoRepository.save(descontoPeriodoPagamento);
    }

    /**
     * Partially update a descontoPeriodoPagamento.
     *
     * @param descontoPeriodoPagamento the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DescontoPeriodoPagamento> partialUpdate(DescontoPeriodoPagamento descontoPeriodoPagamento) {
        log.debug("Request to partially update DescontoPeriodoPagamento : {}", descontoPeriodoPagamento);

        return descontoPeriodoPagamentoRepository
            .findById(descontoPeriodoPagamento.getId())
            .map(existingDescontoPeriodoPagamento -> {
                if (descontoPeriodoPagamento.getPercentual() != null) {
                    existingDescontoPeriodoPagamento.setPercentual(descontoPeriodoPagamento.getPercentual());
                }

                return existingDescontoPeriodoPagamento;
            })
            .map(descontoPeriodoPagamentoRepository::save);
    }

    /**
     * Get all the descontoPeriodoPagamentos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DescontoPeriodoPagamento> findAllWithEagerRelationships(Pageable pageable) {
        return descontoPeriodoPagamentoRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one descontoPeriodoPagamento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DescontoPeriodoPagamento> findOne(Long id) {
        log.debug("Request to get DescontoPeriodoPagamento : {}", id);
        return descontoPeriodoPagamentoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the descontoPeriodoPagamento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DescontoPeriodoPagamento : {}", id);
        descontoPeriodoPagamentoRepository.deleteById(id);
    }
}
