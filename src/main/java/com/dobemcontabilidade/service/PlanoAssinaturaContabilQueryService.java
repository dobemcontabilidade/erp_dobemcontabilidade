package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.PlanoAssinaturaContabil;
import com.dobemcontabilidade.repository.PlanoAssinaturaContabilRepository;
import com.dobemcontabilidade.service.criteria.PlanoAssinaturaContabilCriteria;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link PlanoAssinaturaContabil} entities in the database.
 * The main input is a {@link PlanoAssinaturaContabilCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PlanoAssinaturaContabil} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PlanoAssinaturaContabilQueryService extends QueryService<PlanoAssinaturaContabil> {

    private static final Logger log = LoggerFactory.getLogger(PlanoAssinaturaContabilQueryService.class);

    private final PlanoAssinaturaContabilRepository planoAssinaturaContabilRepository;

    public PlanoAssinaturaContabilQueryService(PlanoAssinaturaContabilRepository planoAssinaturaContabilRepository) {
        this.planoAssinaturaContabilRepository = planoAssinaturaContabilRepository;
    }

    /**
     * Return a {@link List} of {@link PlanoAssinaturaContabil} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PlanoAssinaturaContabil> findByCriteria(PlanoAssinaturaContabilCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PlanoAssinaturaContabil> specification = createSpecification(criteria);
        return planoAssinaturaContabilRepository.findAll(specification);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PlanoAssinaturaContabilCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PlanoAssinaturaContabil> specification = createSpecification(criteria);
        return planoAssinaturaContabilRepository.count(specification);
    }

    /**
     * Function to convert {@link PlanoAssinaturaContabilCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PlanoAssinaturaContabil> createSpecification(PlanoAssinaturaContabilCriteria criteria) {
        Specification<PlanoAssinaturaContabil> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PlanoAssinaturaContabil_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), PlanoAssinaturaContabil_.nome));
            }
            if (criteria.getAdicionalSocio() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getAdicionalSocio(), PlanoAssinaturaContabil_.adicionalSocio)
                );
            }
            if (criteria.getAdicionalFuncionario() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getAdicionalFuncionario(), PlanoAssinaturaContabil_.adicionalFuncionario)
                );
            }
            if (criteria.getSociosIsentos() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getSociosIsentos(), PlanoAssinaturaContabil_.sociosIsentos)
                );
            }
            if (criteria.getAdicionalFaturamento() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getAdicionalFaturamento(), PlanoAssinaturaContabil_.adicionalFaturamento)
                );
            }
            if (criteria.getValorBaseFaturamento() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getValorBaseFaturamento(), PlanoAssinaturaContabil_.valorBaseFaturamento)
                );
            }
            if (criteria.getValorBaseAbertura() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getValorBaseAbertura(), PlanoAssinaturaContabil_.valorBaseAbertura)
                );
            }
            if (criteria.getSituacao() != null) {
                specification = specification.and(buildSpecification(criteria.getSituacao(), PlanoAssinaturaContabil_.situacao));
            }
            if (criteria.getDescontoPeriodoPagamentoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getDescontoPeriodoPagamentoId(),
                        root ->
                            root.join(PlanoAssinaturaContabil_.descontoPeriodoPagamentos, JoinType.LEFT).get(DescontoPeriodoPagamento_.id)
                    )
                );
            }
            if (criteria.getAdicionalRamoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAdicionalRamoId(),
                        root -> root.join(PlanoAssinaturaContabil_.adicionalRamos, JoinType.LEFT).get(AdicionalRamo_.id)
                    )
                );
            }
            if (criteria.getAdicionalTributacaoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAdicionalTributacaoId(),
                        root -> root.join(PlanoAssinaturaContabil_.adicionalTributacaos, JoinType.LEFT).get(AdicionalTributacao_.id)
                    )
                );
            }
            if (criteria.getAdicionalEnquadramentoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAdicionalEnquadramentoId(),
                        root -> root.join(PlanoAssinaturaContabil_.adicionalEnquadramentos, JoinType.LEFT).get(AdicionalEnquadramento_.id)
                    )
                );
            }
        }
        return specification;
    }
}
