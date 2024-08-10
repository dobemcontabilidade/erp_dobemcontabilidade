package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.PeriodoPagamento;
import com.dobemcontabilidade.repository.PeriodoPagamentoRepository;
import com.dobemcontabilidade.service.criteria.PeriodoPagamentoCriteria;
import com.dobemcontabilidade.service.dto.PeriodoPagamentoDTO;
import com.dobemcontabilidade.service.mapper.PeriodoPagamentoMapper;
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
 * Service for executing complex queries for {@link PeriodoPagamento} entities in the database.
 * The main input is a {@link PeriodoPagamentoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link PeriodoPagamentoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PeriodoPagamentoQueryService extends QueryService<PeriodoPagamento> {

    private static final Logger log = LoggerFactory.getLogger(PeriodoPagamentoQueryService.class);

    private final PeriodoPagamentoRepository periodoPagamentoRepository;

    private final PeriodoPagamentoMapper periodoPagamentoMapper;

    public PeriodoPagamentoQueryService(
        PeriodoPagamentoRepository periodoPagamentoRepository,
        PeriodoPagamentoMapper periodoPagamentoMapper
    ) {
        this.periodoPagamentoRepository = periodoPagamentoRepository;
        this.periodoPagamentoMapper = periodoPagamentoMapper;
    }

    /**
     * Return a {@link Page} of {@link PeriodoPagamentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PeriodoPagamentoDTO> findByCriteria(PeriodoPagamentoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PeriodoPagamento> specification = createSpecification(criteria);
        return periodoPagamentoRepository.findAll(specification, page).map(periodoPagamentoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PeriodoPagamentoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PeriodoPagamento> specification = createSpecification(criteria);
        return periodoPagamentoRepository.count(specification);
    }

    /**
     * Function to convert {@link PeriodoPagamentoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PeriodoPagamento> createSpecification(PeriodoPagamentoCriteria criteria) {
        Specification<PeriodoPagamento> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PeriodoPagamento_.id));
            }
            if (criteria.getPeriodo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPeriodo(), PeriodoPagamento_.periodo));
            }
            if (criteria.getNumeroDias() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumeroDias(), PeriodoPagamento_.numeroDias));
            }
            if (criteria.getIdPlanGnet() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdPlanGnet(), PeriodoPagamento_.idPlanGnet));
            }
            if (criteria.getCalculoPlanoAssinaturaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getCalculoPlanoAssinaturaId(),
                        root -> root.join(PeriodoPagamento_.calculoPlanoAssinaturas, JoinType.LEFT).get(CalculoPlanoAssinatura_.id)
                    )
                );
            }
            if (criteria.getAssinaturaEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAssinaturaEmpresaId(),
                        root -> root.join(PeriodoPagamento_.assinaturaEmpresas, JoinType.LEFT).get(AssinaturaEmpresa_.id)
                    )
                );
            }
            if (criteria.getDescontoPlanoContaAzulId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getDescontoPlanoContaAzulId(),
                        root -> root.join(PeriodoPagamento_.descontoPlanoContaAzuls, JoinType.LEFT).get(DescontoPlanoContaAzul_.id)
                    )
                );
            }
            if (criteria.getDescontoPlanoContabilId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getDescontoPlanoContabilId(),
                        root -> root.join(PeriodoPagamento_.descontoPlanoContabils, JoinType.LEFT).get(DescontoPlanoContabil_.id)
                    )
                );
            }
        }
        return specification;
    }
}
