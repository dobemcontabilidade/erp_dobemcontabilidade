package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.DescontoPlanoContabil;
import com.dobemcontabilidade.repository.DescontoPlanoContabilRepository;
import com.dobemcontabilidade.service.criteria.DescontoPlanoContabilCriteria;
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
 * Service for executing complex queries for {@link DescontoPlanoContabil} entities in the database.
 * The main input is a {@link DescontoPlanoContabilCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link DescontoPlanoContabil} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DescontoPlanoContabilQueryService extends QueryService<DescontoPlanoContabil> {

    private static final Logger log = LoggerFactory.getLogger(DescontoPlanoContabilQueryService.class);

    private final DescontoPlanoContabilRepository descontoPlanoContabilRepository;

    public DescontoPlanoContabilQueryService(DescontoPlanoContabilRepository descontoPlanoContabilRepository) {
        this.descontoPlanoContabilRepository = descontoPlanoContabilRepository;
    }

    /**
     * Return a {@link Page} of {@link DescontoPlanoContabil} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DescontoPlanoContabil> findByCriteria(DescontoPlanoContabilCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DescontoPlanoContabil> specification = createSpecification(criteria);
        return descontoPlanoContabilRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DescontoPlanoContabilCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DescontoPlanoContabil> specification = createSpecification(criteria);
        return descontoPlanoContabilRepository.count(specification);
    }

    /**
     * Function to convert {@link DescontoPlanoContabilCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DescontoPlanoContabil> createSpecification(DescontoPlanoContabilCriteria criteria) {
        Specification<DescontoPlanoContabil> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DescontoPlanoContabil_.id));
            }
            if (criteria.getPercentual() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPercentual(), DescontoPlanoContabil_.percentual));
            }
            if (criteria.getCalculoPlanoAssinaturaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getCalculoPlanoAssinaturaId(),
                        root -> root.join(DescontoPlanoContabil_.calculoPlanoAssinaturas, JoinType.LEFT).get(CalculoPlanoAssinatura_.id)
                    )
                );
            }
            if (criteria.getPeriodoPagamentoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getPeriodoPagamentoId(),
                        root -> root.join(DescontoPlanoContabil_.periodoPagamento, JoinType.LEFT).get(PeriodoPagamento_.id)
                    )
                );
            }
            if (criteria.getPlanoContabilId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getPlanoContabilId(),
                        root -> root.join(DescontoPlanoContabil_.planoContabil, JoinType.LEFT).get(PlanoContabil_.id)
                    )
                );
            }
        }
        return specification;
    }
}
