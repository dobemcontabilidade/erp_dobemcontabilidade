package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.DepartamentoContador;
import com.dobemcontabilidade.repository.DepartamentoContadorRepository;
import com.dobemcontabilidade.service.criteria.DepartamentoContadorCriteria;
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
 * Service for executing complex queries for {@link DepartamentoContador} entities in the database.
 * The main input is a {@link DepartamentoContadorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link DepartamentoContador} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DepartamentoContadorQueryService extends QueryService<DepartamentoContador> {

    private static final Logger log = LoggerFactory.getLogger(DepartamentoContadorQueryService.class);

    private final DepartamentoContadorRepository departamentoContadorRepository;

    public DepartamentoContadorQueryService(DepartamentoContadorRepository departamentoContadorRepository) {
        this.departamentoContadorRepository = departamentoContadorRepository;
    }

    /**
     * Return a {@link Page} of {@link DepartamentoContador} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DepartamentoContador> findByCriteria(DepartamentoContadorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DepartamentoContador> specification = createSpecification(criteria);
        return departamentoContadorRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DepartamentoContadorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DepartamentoContador> specification = createSpecification(criteria);
        return departamentoContadorRepository.count(specification);
    }

    /**
     * Function to convert {@link DepartamentoContadorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DepartamentoContador> createSpecification(DepartamentoContadorCriteria criteria) {
        Specification<DepartamentoContador> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DepartamentoContador_.id));
            }
            if (criteria.getPercentualExperiencia() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getPercentualExperiencia(), DepartamentoContador_.percentualExperiencia)
                );
            }
            if (criteria.getDescricaoExperiencia() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getDescricaoExperiencia(), DepartamentoContador_.descricaoExperiencia)
                );
            }
            if (criteria.getPontuacaoEntrevista() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getPontuacaoEntrevista(), DepartamentoContador_.pontuacaoEntrevista)
                );
            }
            if (criteria.getPontuacaoAvaliacao() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getPontuacaoAvaliacao(), DepartamentoContador_.pontuacaoAvaliacao)
                );
            }
            if (criteria.getDepartamentoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getDepartamentoId(),
                        root -> root.join(DepartamentoContador_.departamento, JoinType.LEFT).get(Departamento_.id)
                    )
                );
            }
            if (criteria.getContadorId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getContadorId(),
                        root -> root.join(DepartamentoContador_.contador, JoinType.LEFT).get(Contador_.id)
                    )
                );
            }
        }
        return specification;
    }
}
