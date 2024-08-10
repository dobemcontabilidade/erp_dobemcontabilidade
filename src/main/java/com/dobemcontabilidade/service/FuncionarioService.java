package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Funcionario;
import com.dobemcontabilidade.repository.FuncionarioRepository;
import com.dobemcontabilidade.service.dto.FuncionarioDTO;
import com.dobemcontabilidade.service.mapper.FuncionarioMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Funcionario}.
 */
@Service
@Transactional
public class FuncionarioService {

    private static final Logger log = LoggerFactory.getLogger(FuncionarioService.class);

    private final FuncionarioRepository funcionarioRepository;

    private final FuncionarioMapper funcionarioMapper;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, FuncionarioMapper funcionarioMapper) {
        this.funcionarioRepository = funcionarioRepository;
        this.funcionarioMapper = funcionarioMapper;
    }

    /**
     * Save a funcionario.
     *
     * @param funcionarioDTO the entity to save.
     * @return the persisted entity.
     */
    public FuncionarioDTO save(FuncionarioDTO funcionarioDTO) {
        log.debug("Request to save Funcionario : {}", funcionarioDTO);
        Funcionario funcionario = funcionarioMapper.toEntity(funcionarioDTO);
        funcionario = funcionarioRepository.save(funcionario);
        return funcionarioMapper.toDto(funcionario);
    }

    /**
     * Update a funcionario.
     *
     * @param funcionarioDTO the entity to save.
     * @return the persisted entity.
     */
    public FuncionarioDTO update(FuncionarioDTO funcionarioDTO) {
        log.debug("Request to update Funcionario : {}", funcionarioDTO);
        Funcionario funcionario = funcionarioMapper.toEntity(funcionarioDTO);
        funcionario = funcionarioRepository.save(funcionario);
        return funcionarioMapper.toDto(funcionario);
    }

    /**
     * Partially update a funcionario.
     *
     * @param funcionarioDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FuncionarioDTO> partialUpdate(FuncionarioDTO funcionarioDTO) {
        log.debug("Request to partially update Funcionario : {}", funcionarioDTO);

        return funcionarioRepository
            .findById(funcionarioDTO.getId())
            .map(existingFuncionario -> {
                funcionarioMapper.partialUpdate(existingFuncionario, funcionarioDTO);

                return existingFuncionario;
            })
            .map(funcionarioRepository::save)
            .map(funcionarioMapper::toDto);
    }

    /**
     * Get all the funcionarios with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<FuncionarioDTO> findAllWithEagerRelationships(Pageable pageable) {
        return funcionarioRepository.findAllWithEagerRelationships(pageable).map(funcionarioMapper::toDto);
    }

    /**
     * Get one funcionario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FuncionarioDTO> findOne(Long id) {
        log.debug("Request to get Funcionario : {}", id);
        return funcionarioRepository.findOneWithEagerRelationships(id).map(funcionarioMapper::toDto);
    }

    /**
     * Delete the funcionario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Funcionario : {}", id);
        funcionarioRepository.deleteById(id);
    }
}
