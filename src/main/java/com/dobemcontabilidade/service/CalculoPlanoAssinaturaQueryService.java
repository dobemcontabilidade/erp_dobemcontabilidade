package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.CalculoPlanoAssinatura;
import com.dobemcontabilidade.repository.CalculoPlanoAssinaturaRepository;
import com.dobemcontabilidade.service.criteria.CalculoPlanoAssinaturaCriteria;
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
 * Service for executing complex queries for {@link CalculoPlanoAssinatura} entities in the database.
 * The main input is a {@link CalculoPlanoAssinaturaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link CalculoPlanoAssinatura} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CalculoPlanoAssinaturaQueryService extends QueryService<CalculoPlanoAssinatura> {

    private static final Logger log = LoggerFactory.getLogger(CalculoPlanoAssinaturaQueryService.class);

    private final CalculoPlanoAssinaturaRepository calculoPlanoAssinaturaRepository;

    public CalculoPlanoAssinaturaQueryService(CalculoPlanoAssinaturaRepository calculoPlanoAssinaturaRepository) {
        this.calculoPlanoAssinaturaRepository = calculoPlanoAssinaturaRepository;
    }

    /**
     * Return a {@link Page} of {@link CalculoPlanoAssinatura} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CalculoPlanoAssinatura> findByCriteria(CalculoPlanoAssinaturaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CalculoPlanoAssinatura> specification = createSpecification(criteria);
        return calculoPlanoAssinaturaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CalculoPlanoAssinaturaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CalculoPlanoAssinatura> specification = createSpecification(criteria);
        return calculoPlanoAssinaturaRepository.count(specification);
    }

    /**
     * Function to convert {@link CalculoPlanoAssinaturaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CalculoPlanoAssinatura> createSpecification(CalculoPlanoAssinaturaCriteria criteria) {
        Specification<CalculoPlanoAssinatura> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CalculoPlanoAssinatura_.id));
            }
            if (criteria.getCodigoAtendimento() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getCodigoAtendimento(), CalculoPlanoAssinatura_.codigoAtendimento)
                );
            }
            if (criteria.getValorEnquadramento() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getValorEnquadramento(), CalculoPlanoAssinatura_.valorEnquadramento)
                );
            }
            if (criteria.getValorTributacao() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getValorTributacao(), CalculoPlanoAssinatura_.valorTributacao)
                );
            }
            if (criteria.getValorRamo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorRamo(), CalculoPlanoAssinatura_.valorRamo));
            }
            if (criteria.getValorFuncionarios() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getValorFuncionarios(), CalculoPlanoAssinatura_.valorFuncionarios)
                );
            }
            if (criteria.getValorSocios() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorSocios(), CalculoPlanoAssinatura_.valorSocios));
            }
            if (criteria.getValorFaturamento() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getValorFaturamento(), CalculoPlanoAssinatura_.valorFaturamento)
                );
            }
            if (criteria.getValorPlanoContabil() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getValorPlanoContabil(), CalculoPlanoAssinatura_.valorPlanoContabil)
                );
            }
            if (criteria.getValorPlanoContabilComDesconto() != null) {
                specification = specification.and(
                    buildRangeSpecification(
                        criteria.getValorPlanoContabilComDesconto(),
                        CalculoPlanoAssinatura_.valorPlanoContabilComDesconto
                    )
                );
            }
            if (criteria.getValorPlanoContaAzulComDesconto() != null) {
                specification = specification.and(
                    buildRangeSpecification(
                        criteria.getValorPlanoContaAzulComDesconto(),
                        CalculoPlanoAssinatura_.valorPlanoContaAzulComDesconto
                    )
                );
            }
            if (criteria.getValorMensalidade() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getValorMensalidade(), CalculoPlanoAssinatura_.valorMensalidade)
                );
            }
            if (criteria.getValorPeriodo() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getValorPeriodo(), CalculoPlanoAssinatura_.valorPeriodo)
                );
            }
            if (criteria.getValorAno() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorAno(), CalculoPlanoAssinatura_.valorAno));
            }
            if (criteria.getPeriodoPagamentoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getPeriodoPagamentoId(),
                        root -> root.join(CalculoPlanoAssinatura_.periodoPagamento, JoinType.LEFT).get(PeriodoPagamento_.id)
                    )
                );
            }
            if (criteria.getPlanoContaAzulId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getPlanoContaAzulId(),
                        root -> root.join(CalculoPlanoAssinatura_.planoContaAzul, JoinType.LEFT).get(PlanoContaAzul_.id)
                    )
                );
            }
            if (criteria.getPlanoContabilId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getPlanoContabilId(),
                        root -> root.join(CalculoPlanoAssinatura_.planoContabil, JoinType.LEFT).get(PlanoContabil_.id)
                    )
                );
            }
            if (criteria.getRamoId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getRamoId(), root -> root.join(CalculoPlanoAssinatura_.ramo, JoinType.LEFT).get(Ramo_.id))
                );
            }
            if (criteria.getTributacaoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getTributacaoId(),
                        root -> root.join(CalculoPlanoAssinatura_.tributacao, JoinType.LEFT).get(Tributacao_.id)
                    )
                );
            }
            if (criteria.getDescontoPlanoContabilId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getDescontoPlanoContabilId(),
                        root -> root.join(CalculoPlanoAssinatura_.descontoPlanoContabil, JoinType.LEFT).get(DescontoPlanoContabil_.id)
                    )
                );
            }
            if (criteria.getDescontoPlanoContaAzulId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getDescontoPlanoContaAzulId(),
                        root -> root.join(CalculoPlanoAssinatura_.descontoPlanoContaAzul, JoinType.LEFT).get(DescontoPlanoContaAzul_.id)
                    )
                );
            }
            if (criteria.getAssinaturaEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAssinaturaEmpresaId(),
                        root -> root.join(CalculoPlanoAssinatura_.assinaturaEmpresa, JoinType.LEFT).get(AssinaturaEmpresa_.id)
                    )
                );
            }
        }
        return specification;
    }
}
