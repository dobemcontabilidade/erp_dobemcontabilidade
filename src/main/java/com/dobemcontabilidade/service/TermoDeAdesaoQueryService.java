package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.TermoDeAdesao;
import com.dobemcontabilidade.repository.TermoDeAdesaoRepository;
import com.dobemcontabilidade.service.criteria.TermoDeAdesaoCriteria;
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
 * Service for executing complex queries for {@link TermoDeAdesao} entities in the database.
 * The main input is a {@link TermoDeAdesaoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link TermoDeAdesao} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TermoDeAdesaoQueryService extends QueryService<TermoDeAdesao> {

    private static final Logger log = LoggerFactory.getLogger(TermoDeAdesaoQueryService.class);

    private final TermoDeAdesaoRepository termoDeAdesaoRepository;

    public TermoDeAdesaoQueryService(TermoDeAdesaoRepository termoDeAdesaoRepository) {
        this.termoDeAdesaoRepository = termoDeAdesaoRepository;
    }

    /**
     * Return a {@link Page} of {@link TermoDeAdesao} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TermoDeAdesao> findByCriteria(TermoDeAdesaoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TermoDeAdesao> specification = createSpecification(criteria);
        return termoDeAdesaoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TermoDeAdesaoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TermoDeAdesao> specification = createSpecification(criteria);
        return termoDeAdesaoRepository.count(specification);
    }

    /**
     * Function to convert {@link TermoDeAdesaoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TermoDeAdesao> createSpecification(TermoDeAdesaoCriteria criteria) {
        Specification<TermoDeAdesao> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TermoDeAdesao_.id));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), TermoDeAdesao_.titulo));
            }
            if (criteria.getTermoAdesaoContadorId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getTermoAdesaoContadorId(),
                        root -> root.join(TermoDeAdesao_.termoAdesaoContadors, JoinType.LEFT).get(TermoAdesaoContador_.id)
                    )
                );
            }
        }
        return specification;
    }
}
