package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.PeriodoPagamento;
import com.dobemcontabilidade.repository.PeriodoPagamentoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.PeriodoPagamento}.
 */
@Service
@Transactional
public class PeriodoPagamentoService {

    private static final Logger log = LoggerFactory.getLogger(PeriodoPagamentoService.class);

    private final PeriodoPagamentoRepository periodoPagamentoRepository;

    public PeriodoPagamentoService(PeriodoPagamentoRepository periodoPagamentoRepository) {
        this.periodoPagamentoRepository = periodoPagamentoRepository;
    }

    /**
     * Save a periodoPagamento.
     *
     * @param periodoPagamento the entity to save.
     * @return the persisted entity.
     */
    public PeriodoPagamento save(PeriodoPagamento periodoPagamento) {
        log.debug("Request to save PeriodoPagamento : {}", periodoPagamento);
        return periodoPagamentoRepository.save(periodoPagamento);
    }

    /**
     * Update a periodoPagamento.
     *
     * @param periodoPagamento the entity to save.
     * @return the persisted entity.
     */
    public PeriodoPagamento update(PeriodoPagamento periodoPagamento) {
        log.debug("Request to update PeriodoPagamento : {}", periodoPagamento);
        return periodoPagamentoRepository.save(periodoPagamento);
    }

    /**
     * Partially update a periodoPagamento.
     *
     * @param periodoPagamento the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PeriodoPagamento> partialUpdate(PeriodoPagamento periodoPagamento) {
        log.debug("Request to partially update PeriodoPagamento : {}", periodoPagamento);

        return periodoPagamentoRepository
            .findById(periodoPagamento.getId())
            .map(existingPeriodoPagamento -> {
                if (periodoPagamento.getPeriodo() != null) {
                    existingPeriodoPagamento.setPeriodo(periodoPagamento.getPeriodo());
                }
                if (periodoPagamento.getNumeroDias() != null) {
                    existingPeriodoPagamento.setNumeroDias(periodoPagamento.getNumeroDias());
                }
                if (periodoPagamento.getIdPlanGnet() != null) {
                    existingPeriodoPagamento.setIdPlanGnet(periodoPagamento.getIdPlanGnet());
                }

                return existingPeriodoPagamento;
            })
            .map(periodoPagamentoRepository::save);
    }

    /**
     * Get one periodoPagamento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PeriodoPagamento> findOne(Long id) {
        log.debug("Request to get PeriodoPagamento : {}", id);
        return periodoPagamentoRepository.findById(id);
    }

    /**
     * Delete the periodoPagamento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PeriodoPagamento : {}", id);
        periodoPagamentoRepository.deleteById(id);
    }
}
