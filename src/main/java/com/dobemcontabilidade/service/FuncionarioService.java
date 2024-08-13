package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Funcionario;
import com.dobemcontabilidade.repository.FuncionarioRepository;
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

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    /**
     * Save a funcionario.
     *
     * @param funcionario the entity to save.
     * @return the persisted entity.
     */
    public Funcionario save(Funcionario funcionario) {
        log.debug("Request to save Funcionario : {}", funcionario);
        return funcionarioRepository.save(funcionario);
    }

    /**
     * Update a funcionario.
     *
     * @param funcionario the entity to save.
     * @return the persisted entity.
     */
    public Funcionario update(Funcionario funcionario) {
        log.debug("Request to update Funcionario : {}", funcionario);
        return funcionarioRepository.save(funcionario);
    }

    /**
     * Partially update a funcionario.
     *
     * @param funcionario the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Funcionario> partialUpdate(Funcionario funcionario) {
        log.debug("Request to partially update Funcionario : {}", funcionario);

        return funcionarioRepository
            .findById(funcionario.getId())
            .map(existingFuncionario -> {
                if (funcionario.getNumeroPisNisPasep() != null) {
                    existingFuncionario.setNumeroPisNisPasep(funcionario.getNumeroPisNisPasep());
                }
                if (funcionario.getReintegrado() != null) {
                    existingFuncionario.setReintegrado(funcionario.getReintegrado());
                }
                if (funcionario.getPrimeiroEmprego() != null) {
                    existingFuncionario.setPrimeiroEmprego(funcionario.getPrimeiroEmprego());
                }
                if (funcionario.getMultiploVinculos() != null) {
                    existingFuncionario.setMultiploVinculos(funcionario.getMultiploVinculos());
                }
                if (funcionario.getDataOpcaoFgts() != null) {
                    existingFuncionario.setDataOpcaoFgts(funcionario.getDataOpcaoFgts());
                }
                if (funcionario.getFiliacaoSindical() != null) {
                    existingFuncionario.setFiliacaoSindical(funcionario.getFiliacaoSindical());
                }
                if (funcionario.getCnpjSindicato() != null) {
                    existingFuncionario.setCnpjSindicato(funcionario.getCnpjSindicato());
                }
                if (funcionario.getTipoFuncionarioEnum() != null) {
                    existingFuncionario.setTipoFuncionarioEnum(funcionario.getTipoFuncionarioEnum());
                }

                return existingFuncionario;
            })
            .map(funcionarioRepository::save);
    }

    /**
     * Get all the funcionarios with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Funcionario> findAllWithEagerRelationships(Pageable pageable) {
        return funcionarioRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one funcionario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Funcionario> findOne(Long id) {
        log.debug("Request to get Funcionario : {}", id);
        return funcionarioRepository.findOneWithEagerRelationships(id);
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
