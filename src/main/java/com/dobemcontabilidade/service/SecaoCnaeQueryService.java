package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.SecaoCnae;
import com.dobemcontabilidade.repository.SecaoCnaeRepository;
import com.dobemcontabilidade.service.criteria.SecaoCnaeCriteria;
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
 * Service for executing complex queries for {@link SecaoCnae} entities in the database.
 * The main input is a {@link SecaoCnaeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link SecaoCnae} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SecaoCnaeQueryService extends QueryService<SecaoCnae> {

    private static final Logger log = LoggerFactory.getLogger(SecaoCnaeQueryService.class);

    private final SecaoCnaeRepository secaoCnaeRepository;

    public SecaoCnaeQueryService(SecaoCnaeRepository secaoCnaeRepository) {
        this.secaoCnaeRepository = secaoCnaeRepository;
    }

    /**
     * Return a {@link Page} of {@link SecaoCnae} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SecaoCnae> findByCriteria(SecaoCnaeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SecaoCnae> specification = createSpecification(criteria);
        return secaoCnaeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SecaoCnaeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SecaoCnae> specification = createSpecification(criteria);
        return secaoCnaeRepository.count(specification);
    }

    /**
     * Function to convert {@link SecaoCnaeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SecaoCnae> createSpecification(SecaoCnaeCriteria criteria) {
        Specification<SecaoCnae> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SecaoCnae_.id));
            }
            if (criteria.getCodigo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodigo(), SecaoCnae_.codigo));
            }
            if (criteria.getDivisaoCnaeId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getDivisaoCnaeId(),
                        root -> root.join(SecaoCnae_.divisaoCnaes, JoinType.LEFT).get(DivisaoCnae_.id)
                    )
                );
            }
        }
        return specification;
    }
}
