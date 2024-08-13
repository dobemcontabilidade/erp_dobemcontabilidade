package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.DepartamentoContador;
import com.dobemcontabilidade.repository.DepartamentoContadorRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.DepartamentoContador}.
 */
@Service
@Transactional
public class DepartamentoContadorService {

    private static final Logger log = LoggerFactory.getLogger(DepartamentoContadorService.class);

    private final DepartamentoContadorRepository departamentoContadorRepository;

    public DepartamentoContadorService(DepartamentoContadorRepository departamentoContadorRepository) {
        this.departamentoContadorRepository = departamentoContadorRepository;
    }

    /**
     * Save a departamentoContador.
     *
     * @param departamentoContador the entity to save.
     * @return the persisted entity.
     */
    public DepartamentoContador save(DepartamentoContador departamentoContador) {
        log.debug("Request to save DepartamentoContador : {}", departamentoContador);
        return departamentoContadorRepository.save(departamentoContador);
    }

    /**
     * Update a departamentoContador.
     *
     * @param departamentoContador the entity to save.
     * @return the persisted entity.
     */
    public DepartamentoContador update(DepartamentoContador departamentoContador) {
        log.debug("Request to update DepartamentoContador : {}", departamentoContador);
        return departamentoContadorRepository.save(departamentoContador);
    }

    /**
     * Partially update a departamentoContador.
     *
     * @param departamentoContador the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DepartamentoContador> partialUpdate(DepartamentoContador departamentoContador) {
        log.debug("Request to partially update DepartamentoContador : {}", departamentoContador);

        return departamentoContadorRepository
            .findById(departamentoContador.getId())
            .map(existingDepartamentoContador -> {
                if (departamentoContador.getPercentualExperiencia() != null) {
                    existingDepartamentoContador.setPercentualExperiencia(departamentoContador.getPercentualExperiencia());
                }
                if (departamentoContador.getDescricaoExperiencia() != null) {
                    existingDepartamentoContador.setDescricaoExperiencia(departamentoContador.getDescricaoExperiencia());
                }
                if (departamentoContador.getPontuacaoEntrevista() != null) {
                    existingDepartamentoContador.setPontuacaoEntrevista(departamentoContador.getPontuacaoEntrevista());
                }
                if (departamentoContador.getPontuacaoAvaliacao() != null) {
                    existingDepartamentoContador.setPontuacaoAvaliacao(departamentoContador.getPontuacaoAvaliacao());
                }

                return existingDepartamentoContador;
            })
            .map(departamentoContadorRepository::save);
    }

    /**
     * Get all the departamentoContadors with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DepartamentoContador> findAllWithEagerRelationships(Pageable pageable) {
        return departamentoContadorRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one departamentoContador by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DepartamentoContador> findOne(Long id) {
        log.debug("Request to get DepartamentoContador : {}", id);
        return departamentoContadorRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the departamentoContador by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DepartamentoContador : {}", id);
        departamentoContadorRepository.deleteById(id);
    }
}
