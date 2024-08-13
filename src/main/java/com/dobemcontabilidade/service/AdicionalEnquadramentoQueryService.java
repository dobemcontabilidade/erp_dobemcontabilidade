package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.AdicionalEnquadramento;
import com.dobemcontabilidade.repository.AdicionalEnquadramentoRepository;
import com.dobemcontabilidade.service.criteria.AdicionalEnquadramentoCriteria;
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
 * Service for executing complex queries for {@link AdicionalEnquadramento} entities in the database.
 * The main input is a {@link AdicionalEnquadramentoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link AdicionalEnquadramento} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdicionalEnquadramentoQueryService extends QueryService<AdicionalEnquadramento> {

    private static final Logger log = LoggerFactory.getLogger(AdicionalEnquadramentoQueryService.class);

    private final AdicionalEnquadramentoRepository adicionalEnquadramentoRepository;

    public AdicionalEnquadramentoQueryService(AdicionalEnquadramentoRepository adicionalEnquadramentoRepository) {
        this.adicionalEnquadramentoRepository = adicionalEnquadramentoRepository;
    }

    /**
     * Return a {@link Page} of {@link AdicionalEnquadramento} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdicionalEnquadramento> findByCriteria(AdicionalEnquadramentoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdicionalEnquadramento> specification = createSpecification(criteria);
        return adicionalEnquadramentoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdicionalEnquadramentoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AdicionalEnquadramento> specification = createSpecification(criteria);
        return adicionalEnquadramentoRepository.count(specification);
    }

    /**
     * Function to convert {@link AdicionalEnquadramentoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdicionalEnquadramento> createSpecification(AdicionalEnquadramentoCriteria criteria) {
        Specification<AdicionalEnquadramento> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AdicionalEnquadramento_.id));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), AdicionalEnquadramento_.valor));
            }
            if (criteria.getEnquadramentoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getEnquadramentoId(),
                        root -> root.join(AdicionalEnquadramento_.enquadramento, JoinType.LEFT).get(Enquadramento_.id)
                    )
                );
            }
            if (criteria.getPlanoContabilId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getPlanoContabilId(),
                        root -> root.join(AdicionalEnquadramento_.planoContabil, JoinType.LEFT).get(PlanoContabil_.id)
                    )
                );
            }
        }
        return specification;
    }
}
