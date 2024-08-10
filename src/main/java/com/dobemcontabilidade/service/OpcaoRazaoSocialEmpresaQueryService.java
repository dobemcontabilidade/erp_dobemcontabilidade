package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.OpcaoRazaoSocialEmpresa;
import com.dobemcontabilidade.repository.OpcaoRazaoSocialEmpresaRepository;
import com.dobemcontabilidade.service.criteria.OpcaoRazaoSocialEmpresaCriteria;
import com.dobemcontabilidade.service.dto.OpcaoRazaoSocialEmpresaDTO;
import com.dobemcontabilidade.service.mapper.OpcaoRazaoSocialEmpresaMapper;
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
 * Service for executing complex queries for {@link OpcaoRazaoSocialEmpresa} entities in the database.
 * The main input is a {@link OpcaoRazaoSocialEmpresaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link OpcaoRazaoSocialEmpresaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OpcaoRazaoSocialEmpresaQueryService extends QueryService<OpcaoRazaoSocialEmpresa> {

    private static final Logger log = LoggerFactory.getLogger(OpcaoRazaoSocialEmpresaQueryService.class);

    private final OpcaoRazaoSocialEmpresaRepository opcaoRazaoSocialEmpresaRepository;

    private final OpcaoRazaoSocialEmpresaMapper opcaoRazaoSocialEmpresaMapper;

    public OpcaoRazaoSocialEmpresaQueryService(
        OpcaoRazaoSocialEmpresaRepository opcaoRazaoSocialEmpresaRepository,
        OpcaoRazaoSocialEmpresaMapper opcaoRazaoSocialEmpresaMapper
    ) {
        this.opcaoRazaoSocialEmpresaRepository = opcaoRazaoSocialEmpresaRepository;
        this.opcaoRazaoSocialEmpresaMapper = opcaoRazaoSocialEmpresaMapper;
    }

    /**
     * Return a {@link Page} of {@link OpcaoRazaoSocialEmpresaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OpcaoRazaoSocialEmpresaDTO> findByCriteria(OpcaoRazaoSocialEmpresaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OpcaoRazaoSocialEmpresa> specification = createSpecification(criteria);
        return opcaoRazaoSocialEmpresaRepository.findAll(specification, page).map(opcaoRazaoSocialEmpresaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OpcaoRazaoSocialEmpresaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OpcaoRazaoSocialEmpresa> specification = createSpecification(criteria);
        return opcaoRazaoSocialEmpresaRepository.count(specification);
    }

    /**
     * Function to convert {@link OpcaoRazaoSocialEmpresaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OpcaoRazaoSocialEmpresa> createSpecification(OpcaoRazaoSocialEmpresaCriteria criteria) {
        Specification<OpcaoRazaoSocialEmpresa> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OpcaoRazaoSocialEmpresa_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), OpcaoRazaoSocialEmpresa_.nome));
            }
            if (criteria.getOrdem() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrdem(), OpcaoRazaoSocialEmpresa_.ordem));
            }
            if (criteria.getSelecionado() != null) {
                specification = specification.and(buildSpecification(criteria.getSelecionado(), OpcaoRazaoSocialEmpresa_.selecionado));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getEmpresaId(),
                        root -> root.join(OpcaoRazaoSocialEmpresa_.empresa, JoinType.LEFT).get(Empresa_.id)
                    )
                );
            }
        }
        return specification;
    }
}
