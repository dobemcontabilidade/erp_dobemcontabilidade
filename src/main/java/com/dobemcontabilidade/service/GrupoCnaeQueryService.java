package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.GrupoCnae;
import com.dobemcontabilidade.repository.GrupoCnaeRepository;
import com.dobemcontabilidade.service.criteria.GrupoCnaeCriteria;
import com.dobemcontabilidade.service.dto.GrupoCnaeDTO;
import com.dobemcontabilidade.service.mapper.GrupoCnaeMapper;
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
 * Service for executing complex queries for {@link GrupoCnae} entities in the database.
 * The main input is a {@link GrupoCnaeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link GrupoCnaeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GrupoCnaeQueryService extends QueryService<GrupoCnae> {

    private static final Logger log = LoggerFactory.getLogger(GrupoCnaeQueryService.class);

    private final GrupoCnaeRepository grupoCnaeRepository;

    private final GrupoCnaeMapper grupoCnaeMapper;

    public GrupoCnaeQueryService(GrupoCnaeRepository grupoCnaeRepository, GrupoCnaeMapper grupoCnaeMapper) {
        this.grupoCnaeRepository = grupoCnaeRepository;
        this.grupoCnaeMapper = grupoCnaeMapper;
    }

    /**
     * Return a {@link Page} of {@link GrupoCnaeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GrupoCnaeDTO> findByCriteria(GrupoCnaeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<GrupoCnae> specification = createSpecification(criteria);
        return grupoCnaeRepository.findAll(specification, page).map(grupoCnaeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GrupoCnaeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<GrupoCnae> specification = createSpecification(criteria);
        return grupoCnaeRepository.count(specification);
    }

    /**
     * Function to convert {@link GrupoCnaeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<GrupoCnae> createSpecification(GrupoCnaeCriteria criteria) {
        Specification<GrupoCnae> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), GrupoCnae_.id));
            }
            if (criteria.getCodigo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodigo(), GrupoCnae_.codigo));
            }
            if (criteria.getClasseCnaeId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getClasseCnaeId(),
                        root -> root.join(GrupoCnae_.classeCnaes, JoinType.LEFT).get(ClasseCnae_.id)
                    )
                );
            }
            if (criteria.getDivisaoId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getDivisaoId(), root -> root.join(GrupoCnae_.divisao, JoinType.LEFT).get(DivisaoCnae_.id))
                );
            }
        }
        return specification;
    }
}
