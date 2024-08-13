package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.SegmentoCnae;
import com.dobemcontabilidade.repository.SegmentoCnaeRepository;
import com.dobemcontabilidade.service.criteria.SegmentoCnaeCriteria;
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
 * Service for executing complex queries for {@link SegmentoCnae} entities in the database.
 * The main input is a {@link SegmentoCnaeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link SegmentoCnae} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SegmentoCnaeQueryService extends QueryService<SegmentoCnae> {

    private static final Logger log = LoggerFactory.getLogger(SegmentoCnaeQueryService.class);

    private final SegmentoCnaeRepository segmentoCnaeRepository;

    public SegmentoCnaeQueryService(SegmentoCnaeRepository segmentoCnaeRepository) {
        this.segmentoCnaeRepository = segmentoCnaeRepository;
    }

    /**
     * Return a {@link Page} of {@link SegmentoCnae} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SegmentoCnae> findByCriteria(SegmentoCnaeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SegmentoCnae> specification = createSpecification(criteria);
        return segmentoCnaeRepository.fetchBagRelationships(segmentoCnaeRepository.findAll(specification, page));
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SegmentoCnaeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SegmentoCnae> specification = createSpecification(criteria);
        return segmentoCnaeRepository.count(specification);
    }

    /**
     * Function to convert {@link SegmentoCnaeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SegmentoCnae> createSpecification(SegmentoCnaeCriteria criteria) {
        Specification<SegmentoCnae> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SegmentoCnae_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), SegmentoCnae_.nome));
            }
            if (criteria.getIcon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIcon(), SegmentoCnae_.icon));
            }
            if (criteria.getImagem() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImagem(), SegmentoCnae_.imagem));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildSpecification(criteria.getTipo(), SegmentoCnae_.tipo));
            }
            if (criteria.getSubclasseCnaeId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getSubclasseCnaeId(),
                        root -> root.join(SegmentoCnae_.subclasseCnaes, JoinType.LEFT).get(SubclasseCnae_.id)
                    )
                );
            }
            if (criteria.getRamoId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getRamoId(), root -> root.join(SegmentoCnae_.ramo, JoinType.LEFT).get(Ramo_.id))
                );
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getEmpresaId(), root -> root.join(SegmentoCnae_.empresas, JoinType.LEFT).get(Empresa_.id))
                );
            }
            if (criteria.getEmpresaModeloId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getEmpresaModeloId(),
                        root -> root.join(SegmentoCnae_.empresaModelos, JoinType.LEFT).get(EmpresaModelo_.id)
                    )
                );
            }
        }
        return specification;
    }
}
