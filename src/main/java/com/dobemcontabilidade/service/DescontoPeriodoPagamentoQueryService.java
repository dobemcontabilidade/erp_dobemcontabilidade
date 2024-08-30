package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.DescontoPeriodoPagamento;
import com.dobemcontabilidade.repository.DescontoPeriodoPagamentoRepository;
import com.dobemcontabilidade.service.criteria.DescontoPeriodoPagamentoCriteria;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link DescontoPeriodoPagamento} entities in the database.
 * The main input is a {@link DescontoPeriodoPagamentoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DescontoPeriodoPagamento} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DescontoPeriodoPagamentoQueryService extends QueryService<DescontoPeriodoPagamento> {

    private static final Logger log = LoggerFactory.getLogger(DescontoPeriodoPagamentoQueryService.class);

    private final DescontoPeriodoPagamentoRepository descontoPeriodoPagamentoRepository;

    public DescontoPeriodoPagamentoQueryService(DescontoPeriodoPagamentoRepository descontoPeriodoPagamentoRepository) {
        this.descontoPeriodoPagamentoRepository = descontoPeriodoPagamentoRepository;
    }

    /**
     * Return a {@link List} of {@link DescontoPeriodoPagamento} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DescontoPeriodoPagamento> findByCriteria(DescontoPeriodoPagamentoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DescontoPeriodoPagamento> specification = createSpecification(criteria);
        return descontoPeriodoPagamentoRepository.findAll(specification);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DescontoPeriodoPagamentoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DescontoPeriodoPagamento> specification = createSpecification(criteria);
        return descontoPeriodoPagamentoRepository.count(specification);
    }

    /**
     * Function to convert {@link DescontoPeriodoPagamentoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DescontoPeriodoPagamento> createSpecification(DescontoPeriodoPagamentoCriteria criteria) {
        Specification<DescontoPeriodoPagamento> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DescontoPeriodoPagamento_.id));
            }
            if (criteria.getPercentual() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPercentual(), DescontoPeriodoPagamento_.percentual));
            }
            if (criteria.getPeriodoPagamentoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getPeriodoPagamentoId(),
                        root -> root.join(DescontoPeriodoPagamento_.periodoPagamento, JoinType.LEFT).get(PeriodoPagamento_.id)
                    )
                );
            }
            if (criteria.getPlanoAssinaturaContabilId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getPlanoAssinaturaContabilId(),
                        root -> root.join(DescontoPeriodoPagamento_.planoAssinaturaContabil, JoinType.LEFT).get(PlanoAssinaturaContabil_.id)
                    )
                );
            }
        }
        return specification;
    }
}
