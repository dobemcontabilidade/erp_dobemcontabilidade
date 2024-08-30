package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.Estado;
import com.dobemcontabilidade.repository.EstadoRepository;
import com.dobemcontabilidade.service.criteria.EstadoCriteria;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Estado} entities in the database.
 * The main input is a {@link EstadoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Estado} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EstadoQueryService extends QueryService<Estado> {

    private static final Logger log = LoggerFactory.getLogger(EstadoQueryService.class);

    private final EstadoRepository estadoRepository;

    public EstadoQueryService(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    /**
     * Return a {@link List} of {@link Estado} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Estado> findByCriteria(EstadoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Estado> specification = createSpecification(criteria);
        return estadoRepository.findAll(specification);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EstadoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Estado> specification = createSpecification(criteria);
        return estadoRepository.count(specification);
    }

    /**
     * Function to convert {@link EstadoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Estado> createSpecification(EstadoCriteria criteria) {
        Specification<Estado> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Estado_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Estado_.nome));
            }
            if (criteria.getNaturalidade() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNaturalidade(), Estado_.naturalidade));
            }
            if (criteria.getSigla() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSigla(), Estado_.sigla));
            }
            if (criteria.getCidadeId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getCidadeId(), root -> root.join(Estado_.cidades, JoinType.LEFT).get(Cidade_.id))
                );
            }
            if (criteria.getPaisId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getPaisId(), root -> root.join(Estado_.pais, JoinType.LEFT).get(Pais_.id))
                );
            }
        }
        return specification;
    }
}
