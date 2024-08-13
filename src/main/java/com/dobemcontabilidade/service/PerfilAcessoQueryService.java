package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.PerfilAcesso;
import com.dobemcontabilidade.repository.PerfilAcessoRepository;
import com.dobemcontabilidade.service.criteria.PerfilAcessoCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link PerfilAcesso} entities in the database.
 * The main input is a {@link PerfilAcessoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link PerfilAcesso} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PerfilAcessoQueryService extends QueryService<PerfilAcesso> {

    private static final Logger log = LoggerFactory.getLogger(PerfilAcessoQueryService.class);

    private final PerfilAcessoRepository perfilAcessoRepository;

    public PerfilAcessoQueryService(PerfilAcessoRepository perfilAcessoRepository) {
        this.perfilAcessoRepository = perfilAcessoRepository;
    }

    /**
     * Return a {@link Page} of {@link PerfilAcesso} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PerfilAcesso> findByCriteria(PerfilAcessoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PerfilAcesso> specification = createSpecification(criteria);
        return perfilAcessoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PerfilAcessoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PerfilAcesso> specification = createSpecification(criteria);
        return perfilAcessoRepository.count(specification);
    }

    /**
     * Function to convert {@link PerfilAcessoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PerfilAcesso> createSpecification(PerfilAcessoCriteria criteria) {
        Specification<PerfilAcesso> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PerfilAcesso_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), PerfilAcesso_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), PerfilAcesso_.descricao));
            }
        }
        return specification;
    }
}
