package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Pagamento;
import com.dobemcontabilidade.repository.PagamentoRepository;
import com.dobemcontabilidade.service.dto.PagamentoDTO;
import com.dobemcontabilidade.service.mapper.PagamentoMapper;
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

    private final PagamentoMapper pagamentoMapper;

    public PagamentoService(PagamentoRepository pagamentoRepository, PagamentoMapper pagamentoMapper) {
        this.pagamentoRepository = pagamentoRepository;
        this.pagamentoMapper = pagamentoMapper;
    }

    /**
     * Save a pagamento.
     *
     * @param pagamentoDTO the entity to save.
     * @return the persisted entity.
     */
    public PagamentoDTO save(PagamentoDTO pagamentoDTO) {
        log.debug("Request to save Pagamento : {}", pagamentoDTO);
        Pagamento pagamento = pagamentoMapper.toEntity(pagamentoDTO);
        pagamento = pagamentoRepository.save(pagamento);
        return pagamentoMapper.toDto(pagamento);
    }

    /**
     * Update a pagamento.
     *
     * @param pagamentoDTO the entity to save.
     * @return the persisted entity.
     */
    public PagamentoDTO update(PagamentoDTO pagamentoDTO) {
        log.debug("Request to update Pagamento : {}", pagamentoDTO);
        Pagamento pagamento = pagamentoMapper.toEntity(pagamentoDTO);
        pagamento = pagamentoRepository.save(pagamento);
        return pagamentoMapper.toDto(pagamento);
    }

    /**
     * Partially update a pagamento.
     *
     * @param pagamentoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PagamentoDTO> partialUpdate(PagamentoDTO pagamentoDTO) {
        log.debug("Request to partially update Pagamento : {}", pagamentoDTO);

        return pagamentoRepository
            .findById(pagamentoDTO.getId())
            .map(existingPagamento -> {
                pagamentoMapper.partialUpdate(existingPagamento, pagamentoDTO);

                return existingPagamento;
            })
            .map(pagamentoRepository::save)
            .map(pagamentoMapper::toDto);
    }

    /**
     * Get all the pagamentos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PagamentoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return pagamentoRepository.findAllWithEagerRelationships(pageable).map(pagamentoMapper::toDto);
    }

    /**
     * Get one pagamento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PagamentoDTO> findOne(Long id) {
        log.debug("Request to get Pagamento : {}", id);
        return pagamentoRepository.findOneWithEagerRelationships(id).map(pagamentoMapper::toDto);
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
