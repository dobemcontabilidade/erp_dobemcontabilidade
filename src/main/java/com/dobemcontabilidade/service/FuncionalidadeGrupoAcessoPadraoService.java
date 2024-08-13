package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.FuncionalidadeGrupoAcessoPadrao;
import com.dobemcontabilidade.repository.FuncionalidadeGrupoAcessoPadraoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.FuncionalidadeGrupoAcessoPadrao}.
 */
@Service
@Transactional
public class FuncionalidadeGrupoAcessoPadraoService {

    private static final Logger log = LoggerFactory.getLogger(FuncionalidadeGrupoAcessoPadraoService.class);

    private final FuncionalidadeGrupoAcessoPadraoRepository funcionalidadeGrupoAcessoPadraoRepository;

    public FuncionalidadeGrupoAcessoPadraoService(FuncionalidadeGrupoAcessoPadraoRepository funcionalidadeGrupoAcessoPadraoRepository) {
        this.funcionalidadeGrupoAcessoPadraoRepository = funcionalidadeGrupoAcessoPadraoRepository;
    }

    /**
     * Save a funcionalidadeGrupoAcessoPadrao.
     *
     * @param funcionalidadeGrupoAcessoPadrao the entity to save.
     * @return the persisted entity.
     */
    public FuncionalidadeGrupoAcessoPadrao save(FuncionalidadeGrupoAcessoPadrao funcionalidadeGrupoAcessoPadrao) {
        log.debug("Request to save FuncionalidadeGrupoAcessoPadrao : {}", funcionalidadeGrupoAcessoPadrao);
        return funcionalidadeGrupoAcessoPadraoRepository.save(funcionalidadeGrupoAcessoPadrao);
    }

    /**
     * Update a funcionalidadeGrupoAcessoPadrao.
     *
     * @param funcionalidadeGrupoAcessoPadrao the entity to save.
     * @return the persisted entity.
     */
    public FuncionalidadeGrupoAcessoPadrao update(FuncionalidadeGrupoAcessoPadrao funcionalidadeGrupoAcessoPadrao) {
        log.debug("Request to update FuncionalidadeGrupoAcessoPadrao : {}", funcionalidadeGrupoAcessoPadrao);
        return funcionalidadeGrupoAcessoPadraoRepository.save(funcionalidadeGrupoAcessoPadrao);
    }

    /**
     * Partially update a funcionalidadeGrupoAcessoPadrao.
     *
     * @param funcionalidadeGrupoAcessoPadrao the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FuncionalidadeGrupoAcessoPadrao> partialUpdate(FuncionalidadeGrupoAcessoPadrao funcionalidadeGrupoAcessoPadrao) {
        log.debug("Request to partially update FuncionalidadeGrupoAcessoPadrao : {}", funcionalidadeGrupoAcessoPadrao);

        return funcionalidadeGrupoAcessoPadraoRepository
            .findById(funcionalidadeGrupoAcessoPadrao.getId())
            .map(existingFuncionalidadeGrupoAcessoPadrao -> {
                if (funcionalidadeGrupoAcessoPadrao.getAutorizado() != null) {
                    existingFuncionalidadeGrupoAcessoPadrao.setAutorizado(funcionalidadeGrupoAcessoPadrao.getAutorizado());
                }
                if (funcionalidadeGrupoAcessoPadrao.getDataExpiracao() != null) {
                    existingFuncionalidadeGrupoAcessoPadrao.setDataExpiracao(funcionalidadeGrupoAcessoPadrao.getDataExpiracao());
                }
                if (funcionalidadeGrupoAcessoPadrao.getDataAtribuicao() != null) {
                    existingFuncionalidadeGrupoAcessoPadrao.setDataAtribuicao(funcionalidadeGrupoAcessoPadrao.getDataAtribuicao());
                }

                return existingFuncionalidadeGrupoAcessoPadrao;
            })
            .map(funcionalidadeGrupoAcessoPadraoRepository::save);
    }

    /**
     * Get all the funcionalidadeGrupoAcessoPadraos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FuncionalidadeGrupoAcessoPadrao> findAll() {
        log.debug("Request to get all FuncionalidadeGrupoAcessoPadraos");
        return funcionalidadeGrupoAcessoPadraoRepository.findAll();
    }

    /**
     * Get all the funcionalidadeGrupoAcessoPadraos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<FuncionalidadeGrupoAcessoPadrao> findAllWithEagerRelationships(Pageable pageable) {
        return funcionalidadeGrupoAcessoPadraoRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one funcionalidadeGrupoAcessoPadrao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FuncionalidadeGrupoAcessoPadrao> findOne(Long id) {
        log.debug("Request to get FuncionalidadeGrupoAcessoPadrao : {}", id);
        return funcionalidadeGrupoAcessoPadraoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the funcionalidadeGrupoAcessoPadrao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FuncionalidadeGrupoAcessoPadrao : {}", id);
        funcionalidadeGrupoAcessoPadraoRepository.deleteById(id);
    }
}
