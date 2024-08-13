package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.AssinaturaEmpresa;
import com.dobemcontabilidade.repository.AssinaturaEmpresaRepository;
import com.dobemcontabilidade.service.criteria.AssinaturaEmpresaCriteria;
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
 * Service for executing complex queries for {@link AssinaturaEmpresa} entities in the database.
 * The main input is a {@link AssinaturaEmpresaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link AssinaturaEmpresa} which fulfills the criteria.
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
     * Return a {@link Page} of {@link AssinaturaEmpresa} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AssinaturaEmpresa> findByCriteria(AssinaturaEmpresaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AssinaturaEmpresa> specification = createSpecification(criteria);
        return assinaturaEmpresaRepository.findAll(specification, page);
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
            if (criteria.getSituacaoContratoEmpresa() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getSituacaoContratoEmpresa(), AssinaturaEmpresa_.situacaoContratoEmpresa)
                );
            }
            if (criteria.getTipoContrato() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoContrato(), AssinaturaEmpresa_.tipoContrato));
            }
            if (criteria.getGrupoAcessoEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getGrupoAcessoEmpresaId(),
                        root -> root.join(AssinaturaEmpresa_.grupoAcessoEmpresas, JoinType.LEFT).get(GrupoAcessoEmpresa_.id)
                    )
                );
            }
            if (criteria.getAreaContabilAssinaturaEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAreaContabilAssinaturaEmpresaId(),
                        root ->
                            root
                                .join(AssinaturaEmpresa_.areaContabilAssinaturaEmpresas, JoinType.LEFT)
                                .get(AreaContabilAssinaturaEmpresa_.id)
                    )
                );
            }
            if (criteria.getServicoContabilAssinaturaEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getServicoContabilAssinaturaEmpresaId(),
                        root ->
                            root
                                .join(AssinaturaEmpresa_.servicoContabilAssinaturaEmpresas, JoinType.LEFT)
                                .get(ServicoContabilAssinaturaEmpresa_.id)
                    )
                );
            }
            if (criteria.getGatewayAssinaturaEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getGatewayAssinaturaEmpresaId(),
                        root -> root.join(AssinaturaEmpresa_.gatewayAssinaturaEmpresas, JoinType.LEFT).get(GatewayAssinaturaEmpresa_.id)
                    )
                );
            }
            if (criteria.getCalculoPlanoAssinaturaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getCalculoPlanoAssinaturaId(),
                        root -> root.join(AssinaturaEmpresa_.calculoPlanoAssinaturas, JoinType.LEFT).get(CalculoPlanoAssinatura_.id)
                    )
                );
            }
            if (criteria.getPagamentoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getPagamentoId(),
                        root -> root.join(AssinaturaEmpresa_.pagamentos, JoinType.LEFT).get(Pagamento_.id)
                    )
                );
            }
            if (criteria.getCobrancaEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getCobrancaEmpresaId(),
                        root -> root.join(AssinaturaEmpresa_.cobrancaEmpresas, JoinType.LEFT).get(CobrancaEmpresa_.id)
                    )
                );
            }
            if (criteria.getUsuarioEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getUsuarioEmpresaId(),
                        root -> root.join(AssinaturaEmpresa_.usuarioEmpresas, JoinType.LEFT).get(UsuarioEmpresa_.id)
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
            if (criteria.getPlanoContaAzulId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getPlanoContaAzulId(),
                        root -> root.join(AssinaturaEmpresa_.planoContaAzul, JoinType.LEFT).get(PlanoContaAzul_.id)
                    )
                );
            }
            if (criteria.getPlanoContabilId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getPlanoContabilId(),
                        root -> root.join(AssinaturaEmpresa_.planoContabil, JoinType.LEFT).get(PlanoContabil_.id)
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
