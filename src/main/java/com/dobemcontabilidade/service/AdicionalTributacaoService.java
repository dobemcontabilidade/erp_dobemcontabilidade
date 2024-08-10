package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AdicionalTributacao;
import com.dobemcontabilidade.repository.AdicionalTributacaoRepository;
import com.dobemcontabilidade.service.dto.AdicionalTributacaoDTO;
import com.dobemcontabilidade.service.mapper.AdicionalTributacaoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AdicionalTributacao}.
 */
@Service
@Transactional
public class AdicionalTributacaoService {

    private static final Logger log = LoggerFactory.getLogger(AdicionalTributacaoService.class);

    private final AdicionalTributacaoRepository adicionalTributacaoRepository;

    private final AdicionalTributacaoMapper adicionalTributacaoMapper;

    public AdicionalTributacaoService(
        AdicionalTributacaoRepository adicionalTributacaoRepository,
        AdicionalTributacaoMapper adicionalTributacaoMapper
    ) {
        this.adicionalTributacaoRepository = adicionalTributacaoRepository;
        this.adicionalTributacaoMapper = adicionalTributacaoMapper;
    }

    /**
     * Save a adicionalTributacao.
     *
     * @param adicionalTributacaoDTO the entity to save.
     * @return the persisted entity.
     */
    public AdicionalTributacaoDTO save(AdicionalTributacaoDTO adicionalTributacaoDTO) {
        log.debug("Request to save AdicionalTributacao : {}", adicionalTributacaoDTO);
        AdicionalTributacao adicionalTributacao = adicionalTributacaoMapper.toEntity(adicionalTributacaoDTO);
        adicionalTributacao = adicionalTributacaoRepository.save(adicionalTributacao);
        return adicionalTributacaoMapper.toDto(adicionalTributacao);
    }

    /**
     * Update a adicionalTributacao.
     *
     * @param adicionalTributacaoDTO the entity to save.
     * @return the persisted entity.
     */
    public AdicionalTributacaoDTO update(AdicionalTributacaoDTO adicionalTributacaoDTO) {
        log.debug("Request to update AdicionalTributacao : {}", adicionalTributacaoDTO);
        AdicionalTributacao adicionalTributacao = adicionalTributacaoMapper.toEntity(adicionalTributacaoDTO);
        adicionalTributacao = adicionalTributacaoRepository.save(adicionalTributacao);
        return adicionalTributacaoMapper.toDto(adicionalTributacao);
    }

    /**
     * Partially update a adicionalTributacao.
     *
     * @param adicionalTributacaoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AdicionalTributacaoDTO> partialUpdate(AdicionalTributacaoDTO adicionalTributacaoDTO) {
        log.debug("Request to partially update AdicionalTributacao : {}", adicionalTributacaoDTO);

        return adicionalTributacaoRepository
            .findById(adicionalTributacaoDTO.getId())
            .map(existingAdicionalTributacao -> {
                adicionalTributacaoMapper.partialUpdate(existingAdicionalTributacao, adicionalTributacaoDTO);

                return existingAdicionalTributacao;
            })
            .map(adicionalTributacaoRepository::save)
            .map(adicionalTributacaoMapper::toDto);
    }

    /**
     * Get all the adicionalTributacaos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AdicionalTributacaoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return adicionalTributacaoRepository.findAllWithEagerRelationships(pageable).map(adicionalTributacaoMapper::toDto);
    }

    /**
     * Get one adicionalTributacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdicionalTributacaoDTO> findOne(Long id) {
        log.debug("Request to get AdicionalTributacao : {}", id);
        return adicionalTributacaoRepository.findOneWithEagerRelationships(id).map(adicionalTributacaoMapper::toDto);
    }

    /**
     * Delete the adicionalTributacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdicionalTributacao : {}", id);
        adicionalTributacaoRepository.deleteById(id);
    }
}
