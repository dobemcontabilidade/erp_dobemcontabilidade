package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.BancoContador;
import com.dobemcontabilidade.repository.BancoContadorRepository;
import com.dobemcontabilidade.service.dto.BancoContadorDTO;
import com.dobemcontabilidade.service.mapper.BancoContadorMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.BancoContador}.
 */
@Service
@Transactional
public class BancoContadorService {

    private static final Logger log = LoggerFactory.getLogger(BancoContadorService.class);

    private final BancoContadorRepository bancoContadorRepository;

    private final BancoContadorMapper bancoContadorMapper;

    public BancoContadorService(BancoContadorRepository bancoContadorRepository, BancoContadorMapper bancoContadorMapper) {
        this.bancoContadorRepository = bancoContadorRepository;
        this.bancoContadorMapper = bancoContadorMapper;
    }

    /**
     * Save a bancoContador.
     *
     * @param bancoContadorDTO the entity to save.
     * @return the persisted entity.
     */
    public BancoContadorDTO save(BancoContadorDTO bancoContadorDTO) {
        log.debug("Request to save BancoContador : {}", bancoContadorDTO);
        BancoContador bancoContador = bancoContadorMapper.toEntity(bancoContadorDTO);
        bancoContador = bancoContadorRepository.save(bancoContador);
        return bancoContadorMapper.toDto(bancoContador);
    }

    /**
     * Update a bancoContador.
     *
     * @param bancoContadorDTO the entity to save.
     * @return the persisted entity.
     */
    public BancoContadorDTO update(BancoContadorDTO bancoContadorDTO) {
        log.debug("Request to update BancoContador : {}", bancoContadorDTO);
        BancoContador bancoContador = bancoContadorMapper.toEntity(bancoContadorDTO);
        bancoContador = bancoContadorRepository.save(bancoContador);
        return bancoContadorMapper.toDto(bancoContador);
    }

    /**
     * Partially update a bancoContador.
     *
     * @param bancoContadorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BancoContadorDTO> partialUpdate(BancoContadorDTO bancoContadorDTO) {
        log.debug("Request to partially update BancoContador : {}", bancoContadorDTO);

        return bancoContadorRepository
            .findById(bancoContadorDTO.getId())
            .map(existingBancoContador -> {
                bancoContadorMapper.partialUpdate(existingBancoContador, bancoContadorDTO);

                return existingBancoContador;
            })
            .map(bancoContadorRepository::save)
            .map(bancoContadorMapper::toDto);
    }

    /**
     * Get all the bancoContadors.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BancoContadorDTO> findAll() {
        log.debug("Request to get all BancoContadors");
        return bancoContadorRepository.findAll().stream().map(bancoContadorMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the bancoContadors with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<BancoContadorDTO> findAllWithEagerRelationships(Pageable pageable) {
        return bancoContadorRepository.findAllWithEagerRelationships(pageable).map(bancoContadorMapper::toDto);
    }

    /**
     * Get one bancoContador by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BancoContadorDTO> findOne(Long id) {
        log.debug("Request to get BancoContador : {}", id);
        return bancoContadorRepository.findOneWithEagerRelationships(id).map(bancoContadorMapper::toDto);
    }

    /**
     * Delete the bancoContador by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BancoContador : {}", id);
        bancoContadorRepository.deleteById(id);
    }
}
