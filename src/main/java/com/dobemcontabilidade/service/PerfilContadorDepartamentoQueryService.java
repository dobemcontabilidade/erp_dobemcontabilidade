package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.PerfilContadorDepartamento;
import com.dobemcontabilidade.repository.PerfilContadorDepartamentoRepository;
import com.dobemcontabilidade.service.criteria.PerfilContadorDepartamentoCriteria;
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
 * Service for executing complex queries for {@link PerfilContadorDepartamento} entities in the database.
 * The main input is a {@link PerfilContadorDepartamentoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link PerfilContadorDepartamento} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PerfilContadorDepartamentoQueryService extends QueryService<PerfilContadorDepartamento> {

    private static final Logger log = LoggerFactory.getLogger(PerfilContadorDepartamentoQueryService.class);

    private final PerfilContadorDepartamentoRepository perfilContadorDepartamentoRepository;

    public PerfilContadorDepartamentoQueryService(PerfilContadorDepartamentoRepository perfilContadorDepartamentoRepository) {
        this.perfilContadorDepartamentoRepository = perfilContadorDepartamentoRepository;
    }

    /**
     * Return a {@link Page} of {@link PerfilContadorDepartamento} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PerfilContadorDepartamento> findByCriteria(PerfilContadorDepartamentoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PerfilContadorDepartamento> specification = createSpecification(criteria);
        return perfilContadorDepartamentoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PerfilContadorDepartamentoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PerfilContadorDepartamento> specification = createSpecification(criteria);
        return perfilContadorDepartamentoRepository.count(specification);
    }

    /**
     * Function to convert {@link PerfilContadorDepartamentoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PerfilContadorDepartamento> createSpecification(PerfilContadorDepartamentoCriteria criteria) {
        Specification<PerfilContadorDepartamento> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PerfilContadorDepartamento_.id));
            }
            if (criteria.getQuantidadeEmpresas() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getQuantidadeEmpresas(), PerfilContadorDepartamento_.quantidadeEmpresas)
                );
            }
            if (criteria.getPercentualExperiencia() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getPercentualExperiencia(), PerfilContadorDepartamento_.percentualExperiencia)
                );
            }
            if (criteria.getDepartamentoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getDepartamentoId(),
                        root -> root.join(PerfilContadorDepartamento_.departamento, JoinType.LEFT).get(Departamento_.id)
                    )
                );
            }
            if (criteria.getPerfilContadorId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getPerfilContadorId(),
                        root -> root.join(PerfilContadorDepartamento_.perfilContador, JoinType.LEFT).get(PerfilContador_.id)
                    )
                );
            }
        }
        return specification;
    }
}
