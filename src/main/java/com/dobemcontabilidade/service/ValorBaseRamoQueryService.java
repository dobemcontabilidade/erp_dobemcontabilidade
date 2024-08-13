package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.ValorBaseRamo;
import com.dobemcontabilidade.repository.ValorBaseRamoRepository;
import com.dobemcontabilidade.service.criteria.ValorBaseRamoCriteria;
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
 * Service for executing complex queries for {@link ValorBaseRamo} entities in the database.
 * The main input is a {@link ValorBaseRamoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ValorBaseRamo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ValorBaseRamoQueryService extends QueryService<ValorBaseRamo> {

    private static final Logger log = LoggerFactory.getLogger(ValorBaseRamoQueryService.class);

    private final ValorBaseRamoRepository valorBaseRamoRepository;

    public ValorBaseRamoQueryService(ValorBaseRamoRepository valorBaseRamoRepository) {
        this.valorBaseRamoRepository = valorBaseRamoRepository;
    }

    /**
     * Return a {@link Page} of {@link ValorBaseRamo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ValorBaseRamo> findByCriteria(ValorBaseRamoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ValorBaseRamo> specification = createSpecification(criteria);
        return valorBaseRamoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ValorBaseRamoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ValorBaseRamo> specification = createSpecification(criteria);
        return valorBaseRamoRepository.count(specification);
    }

    /**
     * Function to convert {@link ValorBaseRamoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ValorBaseRamo> createSpecification(ValorBaseRamoCriteria criteria) {
        Specification<ValorBaseRamo> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ValorBaseRamo_.id));
            }
            if (criteria.getValorBase() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorBase(), ValorBaseRamo_.valorBase));
            }
            if (criteria.getRamoId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getRamoId(), root -> root.join(ValorBaseRamo_.ramo, JoinType.LEFT).get(Ramo_.id))
                );
            }
            if (criteria.getPlanoContabilId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getPlanoContabilId(),
                        root -> root.join(ValorBaseRamo_.planoContabil, JoinType.LEFT).get(PlanoContabil_.id)
                    )
                );
            }
        }
        return specification;
    }
}
