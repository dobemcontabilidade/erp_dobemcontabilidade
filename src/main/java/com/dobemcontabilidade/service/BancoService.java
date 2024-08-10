package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Banco;
import com.dobemcontabilidade.repository.BancoRepository;
import com.dobemcontabilidade.service.dto.BancoDTO;
import com.dobemcontabilidade.service.mapper.BancoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Banco}.
 */
@Service
@Transactional
public class BancoService {

    private static final Logger log = LoggerFactory.getLogger(BancoService.class);

    private final BancoRepository bancoRepository;

    private final BancoMapper bancoMapper;

    public BancoService(BancoRepository bancoRepository, BancoMapper bancoMapper) {
        this.bancoRepository = bancoRepository;
        this.bancoMapper = bancoMapper;
    }

    /**
     * Save a banco.
     *
     * @param bancoDTO the entity to save.
     * @return the persisted entity.
     */
    public BancoDTO save(BancoDTO bancoDTO) {
        log.debug("Request to save Banco : {}", bancoDTO);
        Banco banco = bancoMapper.toEntity(bancoDTO);
        banco = bancoRepository.save(banco);
        return bancoMapper.toDto(banco);
    }

    /**
     * Update a banco.
     *
     * @param bancoDTO the entity to save.
     * @return the persisted entity.
     */
    public BancoDTO update(BancoDTO bancoDTO) {
        log.debug("Request to update Banco : {}", bancoDTO);
        Banco banco = bancoMapper.toEntity(bancoDTO);
        banco = bancoRepository.save(banco);
        return bancoMapper.toDto(banco);
    }

    /**
     * Partially update a banco.
     *
     * @param bancoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BancoDTO> partialUpdate(BancoDTO bancoDTO) {
        log.debug("Request to partially update Banco : {}", bancoDTO);

        return bancoRepository
            .findById(bancoDTO.getId())
            .map(existingBanco -> {
                bancoMapper.partialUpdate(existingBanco, bancoDTO);

                return existingBanco;
            })
            .map(bancoRepository::save)
            .map(bancoMapper::toDto);
    }

    /**
     * Get all the bancos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BancoDTO> findAll() {
        log.debug("Request to get all Bancos");
        return bancoRepository.findAll().stream().map(bancoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one banco by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BancoDTO> findOne(Long id) {
        log.debug("Request to get Banco : {}", id);
        return bancoRepository.findById(id).map(bancoMapper::toDto);
    }

    /**
     * Delete the banco by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Banco : {}", id);
        bancoRepository.deleteById(id);
    }
}
