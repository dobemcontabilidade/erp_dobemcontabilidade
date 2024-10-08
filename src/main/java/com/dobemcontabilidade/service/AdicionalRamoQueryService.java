package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.AdicionalRamo;
import com.dobemcontabilidade.repository.AdicionalRamoRepository;
import com.dobemcontabilidade.service.criteria.AdicionalRamoCriteria;
import com.dobemcontabilidade.service.dto.AdicionalRamoDTO;
import com.dobemcontabilidade.service.mapper.AdicionalRamoMapper;
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
 * Service for executing complex queries for {@link AdicionalRamo} entities in the database.
 * The main input is a {@link AdicionalRamoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link AdicionalRamoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdicionalRamoQueryService extends QueryService<AdicionalRamo> {

    private static final Logger log = LoggerFactory.getLogger(AdicionalRamoQueryService.class);

    private final AdicionalRamoRepository adicionalRamoRepository;

    private final AdicionalRamoMapper adicionalRamoMapper;

    public AdicionalRamoQueryService(AdicionalRamoRepository adicionalRamoRepository, AdicionalRamoMapper adicionalRamoMapper) {
        this.adicionalRamoRepository = adicionalRamoRepository;
        this.adicionalRamoMapper = adicionalRamoMapper;
    }

    /**
     * Return a {@link Page} of {@link AdicionalRamoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdicionalRamoDTO> findByCriteria(AdicionalRamoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdicionalRamo> specification = createSpecification(criteria);
        return adicionalRamoRepository.findAll(specification, page).map(adicionalRamoMapper::toDto);
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
            if (criteria.getPlanoContabilId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getPlanoContabilId(),
                        root -> root.join(AdicionalRamo_.planoContabil, JoinType.LEFT).get(PlanoContabil_.id)
                    )
                );
            }
        }
        return specification;
    }
}
