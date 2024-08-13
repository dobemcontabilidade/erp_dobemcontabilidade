package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.SubclasseCnae;
import com.dobemcontabilidade.repository.SubclasseCnaeRepository;
import com.dobemcontabilidade.service.criteria.SubclasseCnaeCriteria;
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
 * Service for executing complex queries for {@link SubclasseCnae} entities in the database.
 * The main input is a {@link SubclasseCnaeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link SubclasseCnae} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SubclasseCnaeQueryService extends QueryService<SubclasseCnae> {

    private static final Logger log = LoggerFactory.getLogger(SubclasseCnaeQueryService.class);

    private final SubclasseCnaeRepository subclasseCnaeRepository;

    public SubclasseCnaeQueryService(SubclasseCnaeRepository subclasseCnaeRepository) {
        this.subclasseCnaeRepository = subclasseCnaeRepository;
    }

    /**
     * Return a {@link Page} of {@link SubclasseCnae} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SubclasseCnae> findByCriteria(SubclasseCnaeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SubclasseCnae> specification = createSpecification(criteria);
        return subclasseCnaeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SubclasseCnaeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SubclasseCnae> specification = createSpecification(criteria);
        return subclasseCnaeRepository.count(specification);
    }

    /**
     * Function to convert {@link SubclasseCnaeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SubclasseCnae> createSpecification(SubclasseCnaeCriteria criteria) {
        Specification<SubclasseCnae> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SubclasseCnae_.id));
            }
            if (criteria.getCodigo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodigo(), SubclasseCnae_.codigo));
            }
            if (criteria.getAnexo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAnexo(), SubclasseCnae_.anexo));
            }
            if (criteria.getAtendidoFreemium() != null) {
                specification = specification.and(buildSpecification(criteria.getAtendidoFreemium(), SubclasseCnae_.atendidoFreemium));
            }
            if (criteria.getAtendido() != null) {
                specification = specification.and(buildSpecification(criteria.getAtendido(), SubclasseCnae_.atendido));
            }
            if (criteria.getOptanteSimples() != null) {
                specification = specification.and(buildSpecification(criteria.getOptanteSimples(), SubclasseCnae_.optanteSimples));
            }
            if (criteria.getAceitaMEI() != null) {
                specification = specification.and(buildSpecification(criteria.getAceitaMEI(), SubclasseCnae_.aceitaMEI));
            }
            if (criteria.getCategoria() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategoria(), SubclasseCnae_.categoria));
            }
            if (criteria.getObservacaoCnaeId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getObservacaoCnaeId(),
                        root -> root.join(SubclasseCnae_.observacaoCnaes, JoinType.LEFT).get(ObservacaoCnae_.id)
                    )
                );
            }
            if (criteria.getAtividadeEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAtividadeEmpresaId(),
                        root -> root.join(SubclasseCnae_.atividadeEmpresas, JoinType.LEFT).get(AtividadeEmpresa_.id)
                    )
                );
            }
            if (criteria.getClasseId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getClasseId(), root -> root.join(SubclasseCnae_.classe, JoinType.LEFT).get(ClasseCnae_.id))
                );
            }
            if (criteria.getSegmentoCnaeId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getSegmentoCnaeId(),
                        root -> root.join(SubclasseCnae_.segmentoCnaes, JoinType.LEFT).get(SegmentoCnae_.id)
                    )
                );
            }
        }
        return specification;
    }
}
