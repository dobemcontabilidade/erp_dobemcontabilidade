package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.Funcionalidade;
import com.dobemcontabilidade.repository.FuncionalidadeRepository;
import com.dobemcontabilidade.service.criteria.FuncionalidadeCriteria;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Funcionalidade} entities in the database.
 * The main input is a {@link FuncionalidadeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Funcionalidade} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FuncionalidadeQueryService extends QueryService<Funcionalidade> {

    private static final Logger log = LoggerFactory.getLogger(FuncionalidadeQueryService.class);

    private final FuncionalidadeRepository funcionalidadeRepository;

    public FuncionalidadeQueryService(FuncionalidadeRepository funcionalidadeRepository) {
        this.funcionalidadeRepository = funcionalidadeRepository;
    }

    /**
     * Return a {@link Page} of {@link Funcionalidade} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Funcionalidade> findByCriteria(FuncionalidadeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Funcionalidade> specification = createSpecification(criteria);
        return funcionalidadeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FuncionalidadeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Funcionalidade> specification = createSpecification(criteria);
        return funcionalidadeRepository.count(specification);
    }

    /**
     * Function to convert {@link FuncionalidadeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Funcionalidade> createSpecification(FuncionalidadeCriteria criteria) {
        Specification<Funcionalidade> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Funcionalidade_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Funcionalidade_.nome));
            }
            if (criteria.getAtiva() != null) {
                specification = specification.and(buildSpecification(criteria.getAtiva(), Funcionalidade_.ativa));
            }
            if (criteria.getFuncionalidadeGrupoAcessoPadraoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getFuncionalidadeGrupoAcessoPadraoId(),
                        root ->
                            root
                                .join(Funcionalidade_.funcionalidadeGrupoAcessoPadraos, JoinType.LEFT)
                                .get(FuncionalidadeGrupoAcessoPadrao_.id)
                    )
                );
            }
            if (criteria.getFuncionalidadeGrupoAcessoEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getFuncionalidadeGrupoAcessoEmpresaId(),
                        root ->
                            root
                                .join(Funcionalidade_.funcionalidadeGrupoAcessoEmpresas, JoinType.LEFT)
                                .get(FuncionalidadeGrupoAcessoEmpresa_.id)
                    )
                );
            }
            if (criteria.getModuloId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getModuloId(), root -> root.join(Funcionalidade_.modulo, JoinType.LEFT).get(Modulo_.id))
                );
            }
        }
        return specification;
    }
}
