package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.PerfilAcessoUsuario;
import com.dobemcontabilidade.repository.PerfilAcessoUsuarioRepository;
import com.dobemcontabilidade.service.criteria.PerfilAcessoUsuarioCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link PerfilAcessoUsuario} entities in the database.
 * The main input is a {@link PerfilAcessoUsuarioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link PerfilAcessoUsuario} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PerfilAcessoUsuarioQueryService extends QueryService<PerfilAcessoUsuario> {

    private static final Logger log = LoggerFactory.getLogger(PerfilAcessoUsuarioQueryService.class);

    private final PerfilAcessoUsuarioRepository perfilAcessoUsuarioRepository;

    public PerfilAcessoUsuarioQueryService(PerfilAcessoUsuarioRepository perfilAcessoUsuarioRepository) {
        this.perfilAcessoUsuarioRepository = perfilAcessoUsuarioRepository;
    }

    /**
     * Return a {@link Page} of {@link PerfilAcessoUsuario} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PerfilAcessoUsuario> findByCriteria(PerfilAcessoUsuarioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PerfilAcessoUsuario> specification = createSpecification(criteria);
        return perfilAcessoUsuarioRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PerfilAcessoUsuarioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PerfilAcessoUsuario> specification = createSpecification(criteria);
        return perfilAcessoUsuarioRepository.count(specification);
    }

    /**
     * Function to convert {@link PerfilAcessoUsuarioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PerfilAcessoUsuario> createSpecification(PerfilAcessoUsuarioCriteria criteria) {
        Specification<PerfilAcessoUsuario> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PerfilAcessoUsuario_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), PerfilAcessoUsuario_.nome));
            }
            if (criteria.getAutorizado() != null) {
                specification = specification.and(buildSpecification(criteria.getAutorizado(), PerfilAcessoUsuario_.autorizado));
            }
            if (criteria.getDataExpiracao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataExpiracao(), PerfilAcessoUsuario_.dataExpiracao));
            }
        }
        return specification;
    }
}
