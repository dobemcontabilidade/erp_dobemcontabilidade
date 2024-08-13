package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.CertificadoDigital;
import com.dobemcontabilidade.repository.CertificadoDigitalRepository;
import com.dobemcontabilidade.service.criteria.CertificadoDigitalCriteria;
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
 * Service for executing complex queries for {@link CertificadoDigital} entities in the database.
 * The main input is a {@link CertificadoDigitalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link CertificadoDigital} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CertificadoDigitalQueryService extends QueryService<CertificadoDigital> {

    private static final Logger log = LoggerFactory.getLogger(CertificadoDigitalQueryService.class);

    private final CertificadoDigitalRepository certificadoDigitalRepository;

    public CertificadoDigitalQueryService(CertificadoDigitalRepository certificadoDigitalRepository) {
        this.certificadoDigitalRepository = certificadoDigitalRepository;
    }

    /**
     * Return a {@link Page} of {@link CertificadoDigital} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CertificadoDigital> findByCriteria(CertificadoDigitalCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CertificadoDigital> specification = createSpecification(criteria);
        return certificadoDigitalRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CertificadoDigitalCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CertificadoDigital> specification = createSpecification(criteria);
        return certificadoDigitalRepository.count(specification);
    }

    /**
     * Function to convert {@link CertificadoDigitalCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CertificadoDigital> createSpecification(CertificadoDigitalCriteria criteria) {
        Specification<CertificadoDigital> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CertificadoDigital_.id));
            }
            if (criteria.getDataContratacao() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getDataContratacao(), CertificadoDigital_.dataContratacao)
                );
            }
            if (criteria.getValidade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidade(), CertificadoDigital_.validade));
            }
            if (criteria.getTipoCertificado() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoCertificado(), CertificadoDigital_.tipoCertificado));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getEmpresaId(),
                        root -> root.join(CertificadoDigital_.empresa, JoinType.LEFT).get(Empresa_.id)
                    )
                );
            }
        }
        return specification;
    }
}
