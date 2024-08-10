package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.PeriodoPagamento;
import com.dobemcontabilidade.repository.PeriodoPagamentoRepository;
import com.dobemcontabilidade.service.dto.PeriodoPagamentoDTO;
import com.dobemcontabilidade.service.mapper.PeriodoPagamentoMapper;
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

    private final PeriodoPagamentoMapper periodoPagamentoMapper;

    public PeriodoPagamentoService(PeriodoPagamentoRepository periodoPagamentoRepository, PeriodoPagamentoMapper periodoPagamentoMapper) {
        this.periodoPagamentoRepository = periodoPagamentoRepository;
        this.periodoPagamentoMapper = periodoPagamentoMapper;
    }

    /**
     * Save a periodoPagamento.
     *
     * @param periodoPagamentoDTO the entity to save.
     * @return the persisted entity.
     */
    public PeriodoPagamentoDTO save(PeriodoPagamentoDTO periodoPagamentoDTO) {
        log.debug("Request to save PeriodoPagamento : {}", periodoPagamentoDTO);
        PeriodoPagamento periodoPagamento = periodoPagamentoMapper.toEntity(periodoPagamentoDTO);
        periodoPagamento = periodoPagamentoRepository.save(periodoPagamento);
        return periodoPagamentoMapper.toDto(periodoPagamento);
    }

    /**
     * Update a periodoPagamento.
     *
     * @param periodoPagamentoDTO the entity to save.
     * @return the persisted entity.
     */
    public PeriodoPagamentoDTO update(PeriodoPagamentoDTO periodoPagamentoDTO) {
        log.debug("Request to update PeriodoPagamento : {}", periodoPagamentoDTO);
        PeriodoPagamento periodoPagamento = periodoPagamentoMapper.toEntity(periodoPagamentoDTO);
        periodoPagamento = periodoPagamentoRepository.save(periodoPagamento);
        return periodoPagamentoMapper.toDto(periodoPagamento);
    }

    /**
     * Partially update a periodoPagamento.
     *
     * @param periodoPagamentoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PeriodoPagamentoDTO> partialUpdate(PeriodoPagamentoDTO periodoPagamentoDTO) {
        log.debug("Request to partially update PeriodoPagamento : {}", periodoPagamentoDTO);

        return periodoPagamentoRepository
            .findById(periodoPagamentoDTO.getId())
            .map(existingPeriodoPagamento -> {
                periodoPagamentoMapper.partialUpdate(existingPeriodoPagamento, periodoPagamentoDTO);

                return existingPeriodoPagamento;
            })
            .map(periodoPagamentoRepository::save)
            .map(periodoPagamentoMapper::toDto);
    }

    /**
     * Get one periodoPagamento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PeriodoPagamentoDTO> findOne(Long id) {
        log.debug("Request to get PeriodoPagamento : {}", id);
        return periodoPagamentoRepository.findById(id).map(periodoPagamentoMapper::toDto);
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
