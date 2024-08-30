package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.AdicionalTributacao;
import com.dobemcontabilidade.repository.AdicionalTributacaoRepository;
import com.dobemcontabilidade.service.criteria.AdicionalTributacaoCriteria;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link AdicionalTributacao} entities in the database.
 * The main input is a {@link AdicionalTributacaoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdicionalTributacao} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdicionalTributacaoQueryService extends QueryService<AdicionalTributacao> {

    private static final Logger log = LoggerFactory.getLogger(AdicionalTributacaoQueryService.class);

    private final AdicionalTributacaoRepository adicionalTributacaoRepository;

    public AdicionalTributacaoQueryService(AdicionalTributacaoRepository adicionalTributacaoRepository) {
        this.adicionalTributacaoRepository = adicionalTributacaoRepository;
    }

    /**
     * Return a {@link List} of {@link AdicionalTributacao} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdicionalTributacao> findByCriteria(AdicionalTributacaoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AdicionalTributacao> specification = createSpecification(criteria);
        return adicionalTributacaoRepository.findAll(specification);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdicionalTributacaoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AdicionalTributacao> specification = createSpecification(criteria);
        return adicionalTributacaoRepository.count(specification);
    }

    /**
     * Function to convert {@link AdicionalTributacaoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdicionalTributacao> createSpecification(AdicionalTributacaoCriteria criteria) {
        Specification<AdicionalTributacao> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AdicionalTributacao_.id));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), AdicionalTributacao_.valor));
            }
            if (criteria.getTributacaoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getTributacaoId(),
                        root -> root.join(AdicionalTributacao_.tributacao, JoinType.LEFT).get(Tributacao_.id)
                    )
                );
            }
            if (criteria.getPlanoAssinaturaContabilId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getPlanoAssinaturaContabilId(),
                        root -> root.join(AdicionalTributacao_.planoAssinaturaContabil, JoinType.LEFT).get(PlanoAssinaturaContabil_.id)
                    )
                );
            }
        }
        return specification;
    }
}
