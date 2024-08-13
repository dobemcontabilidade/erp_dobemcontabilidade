package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.PerfilRedeSocial;
import com.dobemcontabilidade.repository.PerfilRedeSocialRepository;
import com.dobemcontabilidade.service.criteria.PerfilRedeSocialCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link PerfilRedeSocial} entities in the database.
 * The main input is a {@link PerfilRedeSocialCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link PerfilRedeSocial} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PerfilRedeSocialQueryService extends QueryService<PerfilRedeSocial> {

    private static final Logger log = LoggerFactory.getLogger(PerfilRedeSocialQueryService.class);

    private final PerfilRedeSocialRepository perfilRedeSocialRepository;

    public PerfilRedeSocialQueryService(PerfilRedeSocialRepository perfilRedeSocialRepository) {
        this.perfilRedeSocialRepository = perfilRedeSocialRepository;
    }

    /**
     * Return a {@link Page} of {@link PerfilRedeSocial} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PerfilRedeSocial> findByCriteria(PerfilRedeSocialCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PerfilRedeSocial> specification = createSpecification(criteria);
        return perfilRedeSocialRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PerfilRedeSocialCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PerfilRedeSocial> specification = createSpecification(criteria);
        return perfilRedeSocialRepository.count(specification);
    }

    /**
     * Function to convert {@link PerfilRedeSocialCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PerfilRedeSocial> createSpecification(PerfilRedeSocialCriteria criteria) {
        Specification<PerfilRedeSocial> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PerfilRedeSocial_.id));
            }
            if (criteria.getRede() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRede(), PerfilRedeSocial_.rede));
            }
            if (criteria.getUrlPerfil() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrlPerfil(), PerfilRedeSocial_.urlPerfil));
            }
            if (criteria.getTipoRede() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoRede(), PerfilRedeSocial_.tipoRede));
            }
        }
        return specification;
    }
}
