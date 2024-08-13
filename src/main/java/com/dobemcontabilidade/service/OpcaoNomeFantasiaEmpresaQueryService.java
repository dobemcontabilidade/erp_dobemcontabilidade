package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.OpcaoNomeFantasiaEmpresa;
import com.dobemcontabilidade.repository.OpcaoNomeFantasiaEmpresaRepository;
import com.dobemcontabilidade.service.criteria.OpcaoNomeFantasiaEmpresaCriteria;
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
 * Service for executing complex queries for {@link OpcaoNomeFantasiaEmpresa} entities in the database.
 * The main input is a {@link OpcaoNomeFantasiaEmpresaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link OpcaoNomeFantasiaEmpresa} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OpcaoNomeFantasiaEmpresaQueryService extends QueryService<OpcaoNomeFantasiaEmpresa> {

    private static final Logger log = LoggerFactory.getLogger(OpcaoNomeFantasiaEmpresaQueryService.class);

    private final OpcaoNomeFantasiaEmpresaRepository opcaoNomeFantasiaEmpresaRepository;

    public OpcaoNomeFantasiaEmpresaQueryService(OpcaoNomeFantasiaEmpresaRepository opcaoNomeFantasiaEmpresaRepository) {
        this.opcaoNomeFantasiaEmpresaRepository = opcaoNomeFantasiaEmpresaRepository;
    }

    /**
     * Return a {@link Page} of {@link OpcaoNomeFantasiaEmpresa} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OpcaoNomeFantasiaEmpresa> findByCriteria(OpcaoNomeFantasiaEmpresaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OpcaoNomeFantasiaEmpresa> specification = createSpecification(criteria);
        return opcaoNomeFantasiaEmpresaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OpcaoNomeFantasiaEmpresaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OpcaoNomeFantasiaEmpresa> specification = createSpecification(criteria);
        return opcaoNomeFantasiaEmpresaRepository.count(specification);
    }

    /**
     * Function to convert {@link OpcaoNomeFantasiaEmpresaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OpcaoNomeFantasiaEmpresa> createSpecification(OpcaoNomeFantasiaEmpresaCriteria criteria) {
        Specification<OpcaoNomeFantasiaEmpresa> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OpcaoNomeFantasiaEmpresa_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), OpcaoNomeFantasiaEmpresa_.nome));
            }
            if (criteria.getOrdem() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrdem(), OpcaoNomeFantasiaEmpresa_.ordem));
            }
            if (criteria.getSelecionado() != null) {
                specification = specification.and(buildSpecification(criteria.getSelecionado(), OpcaoNomeFantasiaEmpresa_.selecionado));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getEmpresaId(),
                        root -> root.join(OpcaoNomeFantasiaEmpresa_.empresa, JoinType.LEFT).get(Empresa_.id)
                    )
                );
            }
        }
        return specification;
    }
}
