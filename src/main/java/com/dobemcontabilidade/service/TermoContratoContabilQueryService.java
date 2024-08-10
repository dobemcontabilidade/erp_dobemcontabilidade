package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.TermoContratoContabil;
import com.dobemcontabilidade.repository.TermoContratoContabilRepository;
import com.dobemcontabilidade.service.criteria.TermoContratoContabilCriteria;
import com.dobemcontabilidade.service.dto.TermoContratoContabilDTO;
import com.dobemcontabilidade.service.mapper.TermoContratoContabilMapper;
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
 * Service for executing complex queries for {@link TermoContratoContabil} entities in the database.
 * The main input is a {@link TermoContratoContabilCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link TermoContratoContabilDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TermoContratoContabilQueryService extends QueryService<TermoContratoContabil> {

    private static final Logger log = LoggerFactory.getLogger(TermoContratoContabilQueryService.class);

    private final TermoContratoContabilRepository termoContratoContabilRepository;

    private final TermoContratoContabilMapper termoContratoContabilMapper;

    public TermoContratoContabilQueryService(
        TermoContratoContabilRepository termoContratoContabilRepository,
        TermoContratoContabilMapper termoContratoContabilMapper
    ) {
        this.termoContratoContabilRepository = termoContratoContabilRepository;
        this.termoContratoContabilMapper = termoContratoContabilMapper;
    }

    /**
     * Return a {@link Page} of {@link TermoContratoContabilDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TermoContratoContabilDTO> findByCriteria(TermoContratoContabilCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TermoContratoContabil> specification = createSpecification(criteria);
        return termoContratoContabilRepository.findAll(specification, page).map(termoContratoContabilMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TermoContratoContabilCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TermoContratoContabil> specification = createSpecification(criteria);
        return termoContratoContabilRepository.count(specification);
    }

    /**
     * Function to convert {@link TermoContratoContabilCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TermoContratoContabil> createSpecification(TermoContratoContabilCriteria criteria) {
        Specification<TermoContratoContabil> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TermoContratoContabil_.id));
            }
            if (criteria.getDocumento() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumento(), TermoContratoContabil_.documento));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), TermoContratoContabil_.titulo));
            }
            if (criteria.getPlanoContabilId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getPlanoContabilId(),
                        root -> root.join(TermoContratoContabil_.planoContabil, JoinType.LEFT).get(PlanoContabil_.id)
                    )
                );
            }
        }
        return specification;
    }
}
