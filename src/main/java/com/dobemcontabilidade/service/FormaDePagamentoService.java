package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.FormaDePagamento;
import com.dobemcontabilidade.repository.FormaDePagamentoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.FormaDePagamento}.
 */
@Service
@Transactional
public class FormaDePagamentoService {

    private static final Logger log = LoggerFactory.getLogger(FormaDePagamentoService.class);

    private final FormaDePagamentoRepository formaDePagamentoRepository;

    public FormaDePagamentoService(FormaDePagamentoRepository formaDePagamentoRepository) {
        this.formaDePagamentoRepository = formaDePagamentoRepository;
    }

    /**
     * Save a formaDePagamento.
     *
     * @param formaDePagamento the entity to save.
     * @return the persisted entity.
     */
    public FormaDePagamento save(FormaDePagamento formaDePagamento) {
        log.debug("Request to save FormaDePagamento : {}", formaDePagamento);
        return formaDePagamentoRepository.save(formaDePagamento);
    }

    /**
     * Update a formaDePagamento.
     *
     * @param formaDePagamento the entity to save.
     * @return the persisted entity.
     */
    public FormaDePagamento update(FormaDePagamento formaDePagamento) {
        log.debug("Request to update FormaDePagamento : {}", formaDePagamento);
        return formaDePagamentoRepository.save(formaDePagamento);
    }

    /**
     * Partially update a formaDePagamento.
     *
     * @param formaDePagamento the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FormaDePagamento> partialUpdate(FormaDePagamento formaDePagamento) {
        log.debug("Request to partially update FormaDePagamento : {}", formaDePagamento);

        return formaDePagamentoRepository
            .findById(formaDePagamento.getId())
            .map(existingFormaDePagamento -> {
                if (formaDePagamento.getForma() != null) {
                    existingFormaDePagamento.setForma(formaDePagamento.getForma());
                }
                if (formaDePagamento.getDisponivel() != null) {
                    existingFormaDePagamento.setDisponivel(formaDePagamento.getDisponivel());
                }

                return existingFormaDePagamento;
            })
            .map(formaDePagamentoRepository::save);
    }

    /**
     * Get one formaDePagamento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FormaDePagamento> findOne(Long id) {
        log.debug("Request to get FormaDePagamento : {}", id);
        return formaDePagamentoRepository.findById(id);
    }

    /**
     * Delete the formaDePagamento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FormaDePagamento : {}", id);
        formaDePagamentoRepository.deleteById(id);
    }
}
