package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.TarefaEmpresa;
import com.dobemcontabilidade.repository.TarefaEmpresaRepository;
import com.dobemcontabilidade.service.criteria.TarefaEmpresaCriteria;
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
 * Service for executing complex queries for {@link TarefaEmpresa} entities in the database.
 * The main input is a {@link TarefaEmpresaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link TarefaEmpresa} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TarefaEmpresaQueryService extends QueryService<TarefaEmpresa> {

    private static final Logger log = LoggerFactory.getLogger(TarefaEmpresaQueryService.class);

    private final TarefaEmpresaRepository tarefaEmpresaRepository;

    public TarefaEmpresaQueryService(TarefaEmpresaRepository tarefaEmpresaRepository) {
        this.tarefaEmpresaRepository = tarefaEmpresaRepository;
    }

    /**
     * Return a {@link Page} of {@link TarefaEmpresa} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TarefaEmpresa> findByCriteria(TarefaEmpresaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TarefaEmpresa> specification = createSpecification(criteria);
        return tarefaEmpresaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TarefaEmpresaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TarefaEmpresa> specification = createSpecification(criteria);
        return tarefaEmpresaRepository.count(specification);
    }

    /**
     * Function to convert {@link TarefaEmpresaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TarefaEmpresa> createSpecification(TarefaEmpresaCriteria criteria) {
        Specification<TarefaEmpresa> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TarefaEmpresa_.id));
            }
            if (criteria.getDataHora() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataHora(), TarefaEmpresa_.dataHora));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getEmpresaId(), root -> root.join(TarefaEmpresa_.empresa, JoinType.LEFT).get(Empresa_.id))
                );
            }
            if (criteria.getContadorId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getContadorId(),
                        root -> root.join(TarefaEmpresa_.contador, JoinType.LEFT).get(Contador_.id)
                    )
                );
            }
            if (criteria.getTarefaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getTarefaId(), root -> root.join(TarefaEmpresa_.tarefa, JoinType.LEFT).get(Tarefa_.id))
                );
            }
        }
        return specification;
    }
}
