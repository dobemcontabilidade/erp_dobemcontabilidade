package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.DepartamentoFuncionario;
import com.dobemcontabilidade.repository.DepartamentoFuncionarioRepository;
import com.dobemcontabilidade.service.criteria.DepartamentoFuncionarioCriteria;
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
 * Service for executing complex queries for {@link DepartamentoFuncionario} entities in the database.
 * The main input is a {@link DepartamentoFuncionarioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link DepartamentoFuncionario} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DepartamentoFuncionarioQueryService extends QueryService<DepartamentoFuncionario> {

    private static final Logger log = LoggerFactory.getLogger(DepartamentoFuncionarioQueryService.class);

    private final DepartamentoFuncionarioRepository departamentoFuncionarioRepository;

    public DepartamentoFuncionarioQueryService(DepartamentoFuncionarioRepository departamentoFuncionarioRepository) {
        this.departamentoFuncionarioRepository = departamentoFuncionarioRepository;
    }

    /**
     * Return a {@link Page} of {@link DepartamentoFuncionario} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DepartamentoFuncionario> findByCriteria(DepartamentoFuncionarioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DepartamentoFuncionario> specification = createSpecification(criteria);
        return departamentoFuncionarioRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DepartamentoFuncionarioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DepartamentoFuncionario> specification = createSpecification(criteria);
        return departamentoFuncionarioRepository.count(specification);
    }

    /**
     * Function to convert {@link DepartamentoFuncionarioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DepartamentoFuncionario> createSpecification(DepartamentoFuncionarioCriteria criteria) {
        Specification<DepartamentoFuncionario> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DepartamentoFuncionario_.id));
            }
            if (criteria.getCargo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCargo(), DepartamentoFuncionario_.cargo));
            }
            if (criteria.getFuncionarioId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getFuncionarioId(),
                        root -> root.join(DepartamentoFuncionario_.funcionario, JoinType.LEFT).get(Funcionario_.id)
                    )
                );
            }
            if (criteria.getDepartamentoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getDepartamentoId(),
                        root -> root.join(DepartamentoFuncionario_.departamento, JoinType.LEFT).get(Departamento_.id)
                    )
                );
            }
        }
        return specification;
    }
}
