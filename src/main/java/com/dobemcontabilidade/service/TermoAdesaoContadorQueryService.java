package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.TermoAdesaoContador;
import com.dobemcontabilidade.repository.TermoAdesaoContadorRepository;
import com.dobemcontabilidade.service.criteria.TermoAdesaoContadorCriteria;
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
 * Service for executing complex queries for {@link TermoAdesaoContador} entities in the database.
 * The main input is a {@link TermoAdesaoContadorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link TermoAdesaoContador} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TermoAdesaoContadorQueryService extends QueryService<TermoAdesaoContador> {

    private static final Logger log = LoggerFactory.getLogger(TermoAdesaoContadorQueryService.class);

    private final TermoAdesaoContadorRepository termoAdesaoContadorRepository;

    public TermoAdesaoContadorQueryService(TermoAdesaoContadorRepository termoAdesaoContadorRepository) {
        this.termoAdesaoContadorRepository = termoAdesaoContadorRepository;
    }

    /**
     * Return a {@link Page} of {@link TermoAdesaoContador} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TermoAdesaoContador> findByCriteria(TermoAdesaoContadorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TermoAdesaoContador> specification = createSpecification(criteria);
        return termoAdesaoContadorRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TermoAdesaoContadorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TermoAdesaoContador> specification = createSpecification(criteria);
        return termoAdesaoContadorRepository.count(specification);
    }

    /**
     * Function to convert {@link TermoAdesaoContadorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TermoAdesaoContador> createSpecification(TermoAdesaoContadorCriteria criteria) {
        Specification<TermoAdesaoContador> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TermoAdesaoContador_.id));
            }
            if (criteria.getDataAdesao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataAdesao(), TermoAdesaoContador_.dataAdesao));
            }
            if (criteria.getChecked() != null) {
                specification = specification.and(buildSpecification(criteria.getChecked(), TermoAdesaoContador_.checked));
            }
            if (criteria.getUrlDoc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrlDoc(), TermoAdesaoContador_.urlDoc));
            }
            if (criteria.getContadorId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getContadorId(),
                        root -> root.join(TermoAdesaoContador_.contador, JoinType.LEFT).get(Contador_.id)
                    )
                );
            }
            if (criteria.getTermoDeAdesaoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getTermoDeAdesaoId(),
                        root -> root.join(TermoAdesaoContador_.termoDeAdesao, JoinType.LEFT).get(TermoDeAdesao_.id)
                    )
                );
            }
        }
        return specification;
    }
}
