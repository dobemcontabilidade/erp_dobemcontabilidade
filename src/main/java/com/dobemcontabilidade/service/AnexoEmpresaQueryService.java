package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.AnexoEmpresa;
import com.dobemcontabilidade.repository.AnexoEmpresaRepository;
import com.dobemcontabilidade.service.criteria.AnexoEmpresaCriteria;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link AnexoEmpresa} entities in the database.
 * The main input is a {@link AnexoEmpresaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AnexoEmpresa} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AnexoEmpresaQueryService extends QueryService<AnexoEmpresa> {

    private static final Logger log = LoggerFactory.getLogger(AnexoEmpresaQueryService.class);

    private final AnexoEmpresaRepository anexoEmpresaRepository;

    public AnexoEmpresaQueryService(AnexoEmpresaRepository anexoEmpresaRepository) {
        this.anexoEmpresaRepository = anexoEmpresaRepository;
    }

    /**
     * Return a {@link List} of {@link AnexoEmpresa} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AnexoEmpresa> findByCriteria(AnexoEmpresaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AnexoEmpresa> specification = createSpecification(criteria);
        return anexoEmpresaRepository.findAll(specification);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AnexoEmpresaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AnexoEmpresa> specification = createSpecification(criteria);
        return anexoEmpresaRepository.count(specification);
    }

    /**
     * Function to convert {@link AnexoEmpresaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AnexoEmpresa> createSpecification(AnexoEmpresaCriteria criteria) {
        Specification<AnexoEmpresa> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AnexoEmpresa_.id));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getEmpresaId(), root -> root.join(AnexoEmpresa_.empresa, JoinType.LEFT).get(Empresa_.id))
                );
            }
            if (criteria.getAnexoRequeridoEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAnexoRequeridoEmpresaId(),
                        root -> root.join(AnexoEmpresa_.anexoRequeridoEmpresa, JoinType.LEFT).get(AnexoRequeridoEmpresa_.id)
                    )
                );
            }
        }
        return specification;
    }
}
