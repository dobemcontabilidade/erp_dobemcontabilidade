package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.Subtarefa;
import com.dobemcontabilidade.repository.SubtarefaRepository;
import com.dobemcontabilidade.service.criteria.SubtarefaCriteria;
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
 * Service for executing complex queries for {@link Subtarefa} entities in the database.
 * The main input is a {@link SubtarefaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Subtarefa} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SubtarefaQueryService extends QueryService<Subtarefa> {

    private static final Logger log = LoggerFactory.getLogger(SubtarefaQueryService.class);

    private final SubtarefaRepository subtarefaRepository;

    public SubtarefaQueryService(SubtarefaRepository subtarefaRepository) {
        this.subtarefaRepository = subtarefaRepository;
    }

    /**
     * Return a {@link Page} of {@link Subtarefa} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Subtarefa> findByCriteria(SubtarefaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Subtarefa> specification = createSpecification(criteria);
        return subtarefaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SubtarefaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Subtarefa> specification = createSpecification(criteria);
        return subtarefaRepository.count(specification);
    }

    /**
     * Function to convert {@link SubtarefaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Subtarefa> createSpecification(SubtarefaCriteria criteria) {
        Specification<Subtarefa> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Subtarefa_.id));
            }
            if (criteria.getOrdem() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrdem(), Subtarefa_.ordem));
            }
            if (criteria.getItem() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItem(), Subtarefa_.item));
            }
            if (criteria.getTarefaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getTarefaId(), root -> root.join(Subtarefa_.tarefa, JoinType.LEFT).get(Tarefa_.id))
                );
            }
        }
        return specification;
    }
}
