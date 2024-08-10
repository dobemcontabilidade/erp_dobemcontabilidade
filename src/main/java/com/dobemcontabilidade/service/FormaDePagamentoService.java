package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.FormaDePagamento;
import com.dobemcontabilidade.repository.FormaDePagamentoRepository;
import com.dobemcontabilidade.service.dto.FormaDePagamentoDTO;
import com.dobemcontabilidade.service.mapper.FormaDePagamentoMapper;
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

    private final FormaDePagamentoMapper formaDePagamentoMapper;

    public FormaDePagamentoService(FormaDePagamentoRepository formaDePagamentoRepository, FormaDePagamentoMapper formaDePagamentoMapper) {
        this.formaDePagamentoRepository = formaDePagamentoRepository;
        this.formaDePagamentoMapper = formaDePagamentoMapper;
    }

    /**
     * Save a formaDePagamento.
     *
     * @param formaDePagamentoDTO the entity to save.
     * @return the persisted entity.
     */
    public FormaDePagamentoDTO save(FormaDePagamentoDTO formaDePagamentoDTO) {
        log.debug("Request to save FormaDePagamento : {}", formaDePagamentoDTO);
        FormaDePagamento formaDePagamento = formaDePagamentoMapper.toEntity(formaDePagamentoDTO);
        formaDePagamento = formaDePagamentoRepository.save(formaDePagamento);
        return formaDePagamentoMapper.toDto(formaDePagamento);
    }

    /**
     * Update a formaDePagamento.
     *
     * @param formaDePagamentoDTO the entity to save.
     * @return the persisted entity.
     */
    public FormaDePagamentoDTO update(FormaDePagamentoDTO formaDePagamentoDTO) {
        log.debug("Request to update FormaDePagamento : {}", formaDePagamentoDTO);
        FormaDePagamento formaDePagamento = formaDePagamentoMapper.toEntity(formaDePagamentoDTO);
        formaDePagamento = formaDePagamentoRepository.save(formaDePagamento);
        return formaDePagamentoMapper.toDto(formaDePagamento);
    }

    /**
     * Partially update a formaDePagamento.
     *
     * @param formaDePagamentoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FormaDePagamentoDTO> partialUpdate(FormaDePagamentoDTO formaDePagamentoDTO) {
        log.debug("Request to partially update FormaDePagamento : {}", formaDePagamentoDTO);

        return formaDePagamentoRepository
            .findById(formaDePagamentoDTO.getId())
            .map(existingFormaDePagamento -> {
                formaDePagamentoMapper.partialUpdate(existingFormaDePagamento, formaDePagamentoDTO);

                return existingFormaDePagamento;
            })
            .map(formaDePagamentoRepository::save)
            .map(formaDePagamentoMapper::toDto);
    }

    /**
     * Get one formaDePagamento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FormaDePagamentoDTO> findOne(Long id) {
        log.debug("Request to get FormaDePagamento : {}", id);
        return formaDePagamentoRepository.findById(id).map(formaDePagamentoMapper::toDto);
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
