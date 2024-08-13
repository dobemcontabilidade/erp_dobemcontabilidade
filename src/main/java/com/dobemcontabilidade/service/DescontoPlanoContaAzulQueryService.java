package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.DescontoPlanoContaAzul;
import com.dobemcontabilidade.repository.DescontoPlanoContaAzulRepository;
import com.dobemcontabilidade.service.criteria.DescontoPlanoContaAzulCriteria;
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
 * Service for executing complex queries for {@link DescontoPlanoContaAzul} entities in the database.
 * The main input is a {@link DescontoPlanoContaAzulCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link DescontoPlanoContaAzul} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DescontoPlanoContaAzulQueryService extends QueryService<DescontoPlanoContaAzul> {

    private static final Logger log = LoggerFactory.getLogger(DescontoPlanoContaAzulQueryService.class);

    private final DescontoPlanoContaAzulRepository descontoPlanoContaAzulRepository;

    public DescontoPlanoContaAzulQueryService(DescontoPlanoContaAzulRepository descontoPlanoContaAzulRepository) {
        this.descontoPlanoContaAzulRepository = descontoPlanoContaAzulRepository;
    }

    /**
     * Return a {@link Page} of {@link DescontoPlanoContaAzul} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DescontoPlanoContaAzul> findByCriteria(DescontoPlanoContaAzulCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DescontoPlanoContaAzul> specification = createSpecification(criteria);
        return descontoPlanoContaAzulRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DescontoPlanoContaAzulCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DescontoPlanoContaAzul> specification = createSpecification(criteria);
        return descontoPlanoContaAzulRepository.count(specification);
    }

    /**
     * Function to convert {@link DescontoPlanoContaAzulCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DescontoPlanoContaAzul> createSpecification(DescontoPlanoContaAzulCriteria criteria) {
        Specification<DescontoPlanoContaAzul> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DescontoPlanoContaAzul_.id));
            }
            if (criteria.getPercentual() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPercentual(), DescontoPlanoContaAzul_.percentual));
            }
            if (criteria.getCalculoPlanoAssinaturaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getCalculoPlanoAssinaturaId(),
                        root -> root.join(DescontoPlanoContaAzul_.calculoPlanoAssinaturas, JoinType.LEFT).get(CalculoPlanoAssinatura_.id)
                    )
                );
            }
            if (criteria.getPlanoContaAzulId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getPlanoContaAzulId(),
                        root -> root.join(DescontoPlanoContaAzul_.planoContaAzul, JoinType.LEFT).get(PlanoContaAzul_.id)
                    )
                );
            }
            if (criteria.getPeriodoPagamentoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getPeriodoPagamentoId(),
                        root -> root.join(DescontoPlanoContaAzul_.periodoPagamento, JoinType.LEFT).get(PeriodoPagamento_.id)
                    )
                );
            }
        }
        return specification;
    }
}
