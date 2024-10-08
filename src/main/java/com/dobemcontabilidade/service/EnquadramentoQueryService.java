package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.Enquadramento;
import com.dobemcontabilidade.repository.EnquadramentoRepository;
import com.dobemcontabilidade.service.criteria.EnquadramentoCriteria;
import com.dobemcontabilidade.service.dto.EnquadramentoDTO;
import com.dobemcontabilidade.service.mapper.EnquadramentoMapper;
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
 * Service for executing complex queries for {@link Enquadramento} entities in the database.
 * The main input is a {@link EnquadramentoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link EnquadramentoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EnquadramentoQueryService extends QueryService<Enquadramento> {

    private static final Logger log = LoggerFactory.getLogger(EnquadramentoQueryService.class);

    private final EnquadramentoRepository enquadramentoRepository;

    private final EnquadramentoMapper enquadramentoMapper;

    public EnquadramentoQueryService(EnquadramentoRepository enquadramentoRepository, EnquadramentoMapper enquadramentoMapper) {
        this.enquadramentoRepository = enquadramentoRepository;
        this.enquadramentoMapper = enquadramentoMapper;
    }

    /**
     * Return a {@link Page} of {@link EnquadramentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EnquadramentoDTO> findByCriteria(EnquadramentoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Enquadramento> specification = createSpecification(criteria);
        return enquadramentoRepository.findAll(specification, page).map(enquadramentoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EnquadramentoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Enquadramento> specification = createSpecification(criteria);
        return enquadramentoRepository.count(specification);
    }

    /**
     * Function to convert {@link EnquadramentoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Enquadramento> createSpecification(EnquadramentoCriteria criteria) {
        Specification<Enquadramento> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Enquadramento_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Enquadramento_.nome));
            }
            if (criteria.getSigla() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSigla(), Enquadramento_.sigla));
            }
            if (criteria.getLimiteInicial() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLimiteInicial(), Enquadramento_.limiteInicial));
            }
            if (criteria.getLimiteFinal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLimiteFinal(), Enquadramento_.limiteFinal));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getEmpresaId(), root -> root.join(Enquadramento_.empresas, JoinType.LEFT).get(Empresa_.id))
                );
            }
            if (criteria.getAdicionalEnquadramentoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAdicionalEnquadramentoId(),
                        root -> root.join(Enquadramento_.adicionalEnquadramentos, JoinType.LEFT).get(AdicionalEnquadramento_.id)
                    )
                );
            }
        }
        return specification;
    }
}
