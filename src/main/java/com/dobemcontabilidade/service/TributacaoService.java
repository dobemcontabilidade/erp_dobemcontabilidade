package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Tributacao;
import com.dobemcontabilidade.repository.TributacaoRepository;
import com.dobemcontabilidade.service.dto.TributacaoDTO;
import com.dobemcontabilidade.service.mapper.TributacaoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Tributacao}.
 */
@Service
@Transactional
public class TributacaoService {

    private static final Logger log = LoggerFactory.getLogger(TributacaoService.class);

    private final TributacaoRepository tributacaoRepository;

    private final TributacaoMapper tributacaoMapper;

    public TributacaoService(TributacaoRepository tributacaoRepository, TributacaoMapper tributacaoMapper) {
        this.tributacaoRepository = tributacaoRepository;
        this.tributacaoMapper = tributacaoMapper;
    }

    /**
     * Save a tributacao.
     *
     * @param tributacaoDTO the entity to save.
     * @return the persisted entity.
     */
    public TributacaoDTO save(TributacaoDTO tributacaoDTO) {
        log.debug("Request to save Tributacao : {}", tributacaoDTO);
        Tributacao tributacao = tributacaoMapper.toEntity(tributacaoDTO);
        tributacao = tributacaoRepository.save(tributacao);
        return tributacaoMapper.toDto(tributacao);
    }

    /**
     * Update a tributacao.
     *
     * @param tributacaoDTO the entity to save.
     * @return the persisted entity.
     */
    public TributacaoDTO update(TributacaoDTO tributacaoDTO) {
        log.debug("Request to update Tributacao : {}", tributacaoDTO);
        Tributacao tributacao = tributacaoMapper.toEntity(tributacaoDTO);
        tributacao = tributacaoRepository.save(tributacao);
        return tributacaoMapper.toDto(tributacao);
    }

    /**
     * Partially update a tributacao.
     *
     * @param tributacaoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TributacaoDTO> partialUpdate(TributacaoDTO tributacaoDTO) {
        log.debug("Request to partially update Tributacao : {}", tributacaoDTO);

        return tributacaoRepository
            .findById(tributacaoDTO.getId())
            .map(existingTributacao -> {
                tributacaoMapper.partialUpdate(existingTributacao, tributacaoDTO);

                return existingTributacao;
            })
            .map(tributacaoRepository::save)
            .map(tributacaoMapper::toDto);
    }

    /**
     * Get one tributacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TributacaoDTO> findOne(Long id) {
        log.debug("Request to get Tributacao : {}", id);
        return tributacaoRepository.findById(id).map(tributacaoMapper::toDto);
    }

    /**
     * Delete the tributacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Tributacao : {}", id);
        tributacaoRepository.deleteById(id);
    }
}
