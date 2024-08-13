package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Funcionalidade;
import com.dobemcontabilidade.repository.FuncionalidadeRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Funcionalidade}.
 */
@Service
@Transactional
public class FuncionalidadeService {

    private static final Logger log = LoggerFactory.getLogger(FuncionalidadeService.class);

    private final FuncionalidadeRepository funcionalidadeRepository;

    public FuncionalidadeService(FuncionalidadeRepository funcionalidadeRepository) {
        this.funcionalidadeRepository = funcionalidadeRepository;
    }

    /**
     * Save a funcionalidade.
     *
     * @param funcionalidade the entity to save.
     * @return the persisted entity.
     */
    public Funcionalidade save(Funcionalidade funcionalidade) {
        log.debug("Request to save Funcionalidade : {}", funcionalidade);
        return funcionalidadeRepository.save(funcionalidade);
    }

    /**
     * Update a funcionalidade.
     *
     * @param funcionalidade the entity to save.
     * @return the persisted entity.
     */
    public Funcionalidade update(Funcionalidade funcionalidade) {
        log.debug("Request to update Funcionalidade : {}", funcionalidade);
        return funcionalidadeRepository.save(funcionalidade);
    }

    /**
     * Partially update a funcionalidade.
     *
     * @param funcionalidade the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Funcionalidade> partialUpdate(Funcionalidade funcionalidade) {
        log.debug("Request to partially update Funcionalidade : {}", funcionalidade);

        return funcionalidadeRepository
            .findById(funcionalidade.getId())
            .map(existingFuncionalidade -> {
                if (funcionalidade.getNome() != null) {
                    existingFuncionalidade.setNome(funcionalidade.getNome());
                }
                if (funcionalidade.getAtiva() != null) {
                    existingFuncionalidade.setAtiva(funcionalidade.getAtiva());
                }

                return existingFuncionalidade;
            })
            .map(funcionalidadeRepository::save);
    }

    /**
     * Get all the funcionalidades with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Funcionalidade> findAllWithEagerRelationships(Pageable pageable) {
        return funcionalidadeRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one funcionalidade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Funcionalidade> findOne(Long id) {
        log.debug("Request to get Funcionalidade : {}", id);
        return funcionalidadeRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the funcionalidade by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Funcionalidade : {}", id);
        funcionalidadeRepository.deleteById(id);
    }
}
