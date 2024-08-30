package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.AssinaturaEmpresa;
import com.dobemcontabilidade.repository.AssinaturaEmpresaRepository;
import com.dobemcontabilidade.service.criteria.AssinaturaEmpresaCriteria;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link AssinaturaEmpresa} entities in the database.
 * The main input is a {@link AssinaturaEmpresaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AssinaturaEmpresa} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AssinaturaEmpresaQueryService extends QueryService<AssinaturaEmpresa> {

    private static final Logger log = LoggerFactory.getLogger(AssinaturaEmpresaQueryService.class);

    private final AssinaturaEmpresaRepository assinaturaEmpresaRepository;

    public AssinaturaEmpresaQueryService(AssinaturaEmpresaRepository assinaturaEmpresaRepository) {
        this.assinaturaEmpresaRepository = assinaturaEmpresaRepository;
    }

    /**
     * Return a {@link List} of {@link AssinaturaEmpresa} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AssinaturaEmpresa> findByCriteria(AssinaturaEmpresaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AssinaturaEmpresa> specification = createSpecification(criteria);
        return assinaturaEmpresaRepository.findAll(specification);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AssinaturaEmpresaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AssinaturaEmpresa> specification = createSpecification(criteria);
        return assinaturaEmpresaRepository.count(specification);
    }

    /**
     * Function to convert {@link AssinaturaEmpresaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AssinaturaEmpresa> createSpecification(AssinaturaEmpresaCriteria criteria) {
        Specification<AssinaturaEmpresa> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AssinaturaEmpresa_.id));
            }
            if (criteria.getRazaoSocial() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRazaoSocial(), AssinaturaEmpresa_.razaoSocial));
            }
            if (criteria.getCodigoAssinatura() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getCodigoAssinatura(), AssinaturaEmpresa_.codigoAssinatura)
                );
            }
            if (criteria.getValorEnquadramento() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getValorEnquadramento(), AssinaturaEmpresa_.valorEnquadramento)
                );
            }
            if (criteria.getValorTributacao() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getValorTributacao(), AssinaturaEmpresa_.valorTributacao)
                );
            }
            if (criteria.getValorRamo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorRamo(), AssinaturaEmpresa_.valorRamo));
            }
            if (criteria.getValorFuncionarios() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getValorFuncionarios(), AssinaturaEmpresa_.valorFuncionarios)
                );
            }
            if (criteria.getValorSocios() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorSocios(), AssinaturaEmpresa_.valorSocios));
            }
            if (criteria.getValorFaturamento() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getValorFaturamento(), AssinaturaEmpresa_.valorFaturamento)
                );
            }
            if (criteria.getValorPlanoContabil() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getValorPlanoContabil(), AssinaturaEmpresa_.valorPlanoContabil)
                );
            }
            if (criteria.getValorPlanoContabilComDesconto() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getValorPlanoContabilComDesconto(), AssinaturaEmpresa_.valorPlanoContabilComDesconto)
                );
            }
            if (criteria.getValorPlanoContaAzulComDesconto() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getValorPlanoContaAzulComDesconto(), AssinaturaEmpresa_.valorPlanoContaAzulComDesconto)
                );
            }
            if (criteria.getValorMensalidade() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getValorMensalidade(), AssinaturaEmpresa_.valorMensalidade)
                );
            }
            if (criteria.getValorPeriodo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorPeriodo(), AssinaturaEmpresa_.valorPeriodo));
            }
            if (criteria.getValorAno() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorAno(), AssinaturaEmpresa_.valorAno));
            }
            if (criteria.getDataContratacao() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getDataContratacao(), AssinaturaEmpresa_.dataContratacao)
                );
            }
            if (criteria.getDataEncerramento() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getDataEncerramento(), AssinaturaEmpresa_.dataEncerramento)
                );
            }
            if (criteria.getDiaVencimento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiaVencimento(), AssinaturaEmpresa_.diaVencimento));
            }
            if (criteria.getSituacao() != null) {
                specification = specification.and(buildSpecification(criteria.getSituacao(), AssinaturaEmpresa_.situacao));
            }
            if (criteria.getTipoContrato() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoContrato(), AssinaturaEmpresa_.tipoContrato));
            }
            if (criteria.getTermoContratoAssinaturaEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getTermoContratoAssinaturaEmpresaId(),
                        root ->
                            root
                                .join(AssinaturaEmpresa_.termoContratoAssinaturaEmpresas, JoinType.LEFT)
                                .get(TermoContratoAssinaturaEmpresa_.id)
                    )
                );
            }
            if (criteria.getPeriodoPagamentoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getPeriodoPagamentoId(),
                        root -> root.join(AssinaturaEmpresa_.periodoPagamento, JoinType.LEFT).get(PeriodoPagamento_.id)
                    )
                );
            }
            if (criteria.getFormaDePagamentoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getFormaDePagamentoId(),
                        root -> root.join(AssinaturaEmpresa_.formaDePagamento, JoinType.LEFT).get(FormaDePagamento_.id)
                    )
                );
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getEmpresaId(),
                        root -> root.join(AssinaturaEmpresa_.empresa, JoinType.LEFT).get(Empresa_.id)
                    )
                );
            }
        }
        return specification;
    }
}
