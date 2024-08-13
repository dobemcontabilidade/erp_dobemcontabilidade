package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.PlanoContabil;
import com.dobemcontabilidade.repository.PlanoContabilRepository;
import com.dobemcontabilidade.service.criteria.PlanoContabilCriteria;
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
 * Service for executing complex queries for {@link PlanoContabil} entities in the database.
 * The main input is a {@link PlanoContabilCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link PlanoContabil} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PlanoContabilQueryService extends QueryService<PlanoContabil> {

    private static final Logger log = LoggerFactory.getLogger(PlanoContabilQueryService.class);

    private final PlanoContabilRepository planoContabilRepository;

    public PlanoContabilQueryService(PlanoContabilRepository planoContabilRepository) {
        this.planoContabilRepository = planoContabilRepository;
    }

    /**
     * Return a {@link Page} of {@link PlanoContabil} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PlanoContabil> findByCriteria(PlanoContabilCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PlanoContabil> specification = createSpecification(criteria);
        return planoContabilRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PlanoContabilCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PlanoContabil> specification = createSpecification(criteria);
        return planoContabilRepository.count(specification);
    }

    /**
     * Function to convert {@link PlanoContabilCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PlanoContabil> createSpecification(PlanoContabilCriteria criteria) {
        Specification<PlanoContabil> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PlanoContabil_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), PlanoContabil_.nome));
            }
            if (criteria.getAdicionalSocio() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAdicionalSocio(), PlanoContabil_.adicionalSocio));
            }
            if (criteria.getAdicionalFuncionario() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getAdicionalFuncionario(), PlanoContabil_.adicionalFuncionario)
                );
            }
            if (criteria.getSociosIsentos() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSociosIsentos(), PlanoContabil_.sociosIsentos));
            }
            if (criteria.getAdicionalFaturamento() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getAdicionalFaturamento(), PlanoContabil_.adicionalFaturamento)
                );
            }
            if (criteria.getValorBaseFaturamento() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getValorBaseFaturamento(), PlanoContabil_.valorBaseFaturamento)
                );
            }
            if (criteria.getValorBaseAbertura() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getValorBaseAbertura(), PlanoContabil_.valorBaseAbertura)
                );
            }
            if (criteria.getSituacao() != null) {
                specification = specification.and(buildSpecification(criteria.getSituacao(), PlanoContabil_.situacao));
            }
            if (criteria.getCalculoPlanoAssinaturaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getCalculoPlanoAssinaturaId(),
                        root -> root.join(PlanoContabil_.calculoPlanoAssinaturas, JoinType.LEFT).get(CalculoPlanoAssinatura_.id)
                    )
                );
            }
            if (criteria.getAssinaturaEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAssinaturaEmpresaId(),
                        root -> root.join(PlanoContabil_.assinaturaEmpresas, JoinType.LEFT).get(AssinaturaEmpresa_.id)
                    )
                );
            }
            if (criteria.getDescontoPlanoContabilId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getDescontoPlanoContabilId(),
                        root -> root.join(PlanoContabil_.descontoPlanoContabils, JoinType.LEFT).get(DescontoPlanoContabil_.id)
                    )
                );
            }
            if (criteria.getAdicionalRamoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAdicionalRamoId(),
                        root -> root.join(PlanoContabil_.adicionalRamos, JoinType.LEFT).get(AdicionalRamo_.id)
                    )
                );
            }
            if (criteria.getAdicionalTributacaoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAdicionalTributacaoId(),
                        root -> root.join(PlanoContabil_.adicionalTributacaos, JoinType.LEFT).get(AdicionalTributacao_.id)
                    )
                );
            }
            if (criteria.getTermoContratoContabilId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getTermoContratoContabilId(),
                        root -> root.join(PlanoContabil_.termoContratoContabils, JoinType.LEFT).get(TermoContratoContabil_.id)
                    )
                );
            }
            if (criteria.getAdicionalEnquadramentoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAdicionalEnquadramentoId(),
                        root -> root.join(PlanoContabil_.adicionalEnquadramentos, JoinType.LEFT).get(AdicionalEnquadramento_.id)
                    )
                );
            }
            if (criteria.getValorBaseRamoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getValorBaseRamoId(),
                        root -> root.join(PlanoContabil_.valorBaseRamos, JoinType.LEFT).get(ValorBaseRamo_.id)
                    )
                );
            }
            if (criteria.getTermoAdesaoEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getTermoAdesaoEmpresaId(),
                        root -> root.join(PlanoContabil_.termoAdesaoEmpresas, JoinType.LEFT).get(TermoAdesaoEmpresa_.id)
                    )
                );
            }
        }
        return specification;
    }
}
