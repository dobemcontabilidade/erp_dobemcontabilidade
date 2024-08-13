package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.Sistema;
import com.dobemcontabilidade.repository.SistemaRepository;
import com.dobemcontabilidade.service.criteria.SistemaCriteria;
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
 * Service for executing complex queries for {@link Sistema} entities in the database.
 * The main input is a {@link SistemaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Sistema} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SistemaQueryService extends QueryService<Sistema> {

    private static final Logger log = LoggerFactory.getLogger(SistemaQueryService.class);

    private final SistemaRepository sistemaRepository;

    public SistemaQueryService(SistemaRepository sistemaRepository) {
        this.sistemaRepository = sistemaRepository;
    }

    /**
     * Return a {@link Page} of {@link Sistema} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Sistema> findByCriteria(SistemaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Sistema> specification = createSpecification(criteria);
        return sistemaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SistemaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Sistema> specification = createSpecification(criteria);
        return sistemaRepository.count(specification);
    }

    /**
     * Function to convert {@link SistemaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Sistema> createSpecification(SistemaCriteria criteria) {
        Specification<Sistema> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Sistema_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Sistema_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Sistema_.descricao));
            }
            if (criteria.getModuloId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getModuloId(), root -> root.join(Sistema_.modulos, JoinType.LEFT).get(Modulo_.id))
                );
            }
        }
        return specification;
    }
}
