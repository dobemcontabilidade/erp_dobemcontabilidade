package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.Tributacao;
import com.dobemcontabilidade.repository.TributacaoRepository;
import com.dobemcontabilidade.service.criteria.TributacaoCriteria;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Tributacao} entities in the database.
 * The main input is a {@link TributacaoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Tributacao} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TributacaoQueryService extends QueryService<Tributacao> {

    private static final Logger log = LoggerFactory.getLogger(TributacaoQueryService.class);

    private final TributacaoRepository tributacaoRepository;

    public TributacaoQueryService(TributacaoRepository tributacaoRepository) {
        this.tributacaoRepository = tributacaoRepository;
    }

    /**
     * Return a {@link List} of {@link Tributacao} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Tributacao> findByCriteria(TributacaoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Tributacao> specification = createSpecification(criteria);
        return tributacaoRepository.findAll(specification);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TributacaoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Tributacao> specification = createSpecification(criteria);
        return tributacaoRepository.count(specification);
    }

    /**
     * Function to convert {@link TributacaoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Tributacao> createSpecification(TributacaoCriteria criteria) {
        Specification<Tributacao> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Tributacao_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Tributacao_.nome));
            }
            if (criteria.getSituacao() != null) {
                specification = specification.and(buildSpecification(criteria.getSituacao(), Tributacao_.situacao));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getEmpresaId(), root -> root.join(Tributacao_.empresas, JoinType.LEFT).get(Empresa_.id))
                );
            }
            if (criteria.getAdicionalTributacaoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAdicionalTributacaoId(),
                        root -> root.join(Tributacao_.adicionalTributacaos, JoinType.LEFT).get(AdicionalTributacao_.id)
                    )
                );
            }
        }
        return specification;
    }
}
