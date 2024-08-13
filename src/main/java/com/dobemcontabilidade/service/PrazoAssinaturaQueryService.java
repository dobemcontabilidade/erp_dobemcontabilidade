package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.PrazoAssinatura;
import com.dobemcontabilidade.repository.PrazoAssinaturaRepository;
import com.dobemcontabilidade.service.criteria.PrazoAssinaturaCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link PrazoAssinatura} entities in the database.
 * The main input is a {@link PrazoAssinaturaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link PrazoAssinatura} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PrazoAssinaturaQueryService extends QueryService<PrazoAssinatura> {

    private static final Logger log = LoggerFactory.getLogger(PrazoAssinaturaQueryService.class);

    private final PrazoAssinaturaRepository prazoAssinaturaRepository;

    public PrazoAssinaturaQueryService(PrazoAssinaturaRepository prazoAssinaturaRepository) {
        this.prazoAssinaturaRepository = prazoAssinaturaRepository;
    }

    /**
     * Return a {@link Page} of {@link PrazoAssinatura} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PrazoAssinatura> findByCriteria(PrazoAssinaturaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PrazoAssinatura> specification = createSpecification(criteria);
        return prazoAssinaturaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PrazoAssinaturaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PrazoAssinatura> specification = createSpecification(criteria);
        return prazoAssinaturaRepository.count(specification);
    }

    /**
     * Function to convert {@link PrazoAssinaturaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PrazoAssinatura> createSpecification(PrazoAssinaturaCriteria criteria) {
        Specification<PrazoAssinatura> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PrazoAssinatura_.id));
            }
            if (criteria.getPrazo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrazo(), PrazoAssinatura_.prazo));
            }
            if (criteria.getMeses() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMeses(), PrazoAssinatura_.meses));
            }
        }
        return specification;
    }
}
