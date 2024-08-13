package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.ClasseCnae;
import com.dobemcontabilidade.repository.ClasseCnaeRepository;
import com.dobemcontabilidade.service.criteria.ClasseCnaeCriteria;
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
 * Service for executing complex queries for {@link ClasseCnae} entities in the database.
 * The main input is a {@link ClasseCnaeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ClasseCnae} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClasseCnaeQueryService extends QueryService<ClasseCnae> {

    private static final Logger log = LoggerFactory.getLogger(ClasseCnaeQueryService.class);

    private final ClasseCnaeRepository classeCnaeRepository;

    public ClasseCnaeQueryService(ClasseCnaeRepository classeCnaeRepository) {
        this.classeCnaeRepository = classeCnaeRepository;
    }

    /**
     * Return a {@link Page} of {@link ClasseCnae} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClasseCnae> findByCriteria(ClasseCnaeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClasseCnae> specification = createSpecification(criteria);
        return classeCnaeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClasseCnaeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ClasseCnae> specification = createSpecification(criteria);
        return classeCnaeRepository.count(specification);
    }

    /**
     * Function to convert {@link ClasseCnaeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ClasseCnae> createSpecification(ClasseCnaeCriteria criteria) {
        Specification<ClasseCnae> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ClasseCnae_.id));
            }
            if (criteria.getCodigo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodigo(), ClasseCnae_.codigo));
            }
            if (criteria.getSubclasseCnaeId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getSubclasseCnaeId(),
                        root -> root.join(ClasseCnae_.subclasseCnaes, JoinType.LEFT).get(SubclasseCnae_.id)
                    )
                );
            }
            if (criteria.getGrupoId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getGrupoId(), root -> root.join(ClasseCnae_.grupo, JoinType.LEFT).get(GrupoCnae_.id))
                );
            }
        }
        return specification;
    }
}
