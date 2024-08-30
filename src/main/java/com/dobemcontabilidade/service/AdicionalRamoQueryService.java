package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.AdicionalRamo;
import com.dobemcontabilidade.repository.AdicionalRamoRepository;
import com.dobemcontabilidade.service.criteria.AdicionalRamoCriteria;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link AdicionalRamo} entities in the database.
 * The main input is a {@link AdicionalRamoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdicionalRamo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdicionalRamoQueryService extends QueryService<AdicionalRamo> {

    private static final Logger log = LoggerFactory.getLogger(AdicionalRamoQueryService.class);

    private final AdicionalRamoRepository adicionalRamoRepository;

    public AdicionalRamoQueryService(AdicionalRamoRepository adicionalRamoRepository) {
        this.adicionalRamoRepository = adicionalRamoRepository;
    }

    /**
     * Return a {@link List} of {@link AdicionalRamo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdicionalRamo> findByCriteria(AdicionalRamoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AdicionalRamo> specification = createSpecification(criteria);
        return adicionalRamoRepository.findAll(specification);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdicionalRamoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AdicionalRamo> specification = createSpecification(criteria);
        return adicionalRamoRepository.count(specification);
    }

    /**
     * Function to convert {@link AdicionalRamoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdicionalRamo> createSpecification(AdicionalRamoCriteria criteria) {
        Specification<AdicionalRamo> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AdicionalRamo_.id));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), AdicionalRamo_.valor));
            }
            if (criteria.getRamoId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getRamoId(), root -> root.join(AdicionalRamo_.ramo, JoinType.LEFT).get(Ramo_.id))
                );
            }
            if (criteria.getPlanoAssinaturaContabilId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getPlanoAssinaturaContabilId(),
                        root -> root.join(AdicionalRamo_.planoAssinaturaContabil, JoinType.LEFT).get(PlanoAssinaturaContabil_.id)
                    )
                );
            }
        }
        return specification;
    }
}
