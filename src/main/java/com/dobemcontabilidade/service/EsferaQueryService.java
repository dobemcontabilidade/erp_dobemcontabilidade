package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.Esfera;
import com.dobemcontabilidade.repository.EsferaRepository;
import com.dobemcontabilidade.service.criteria.EsferaCriteria;
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
 * Service for executing complex queries for {@link Esfera} entities in the database.
 * The main input is a {@link EsferaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Esfera} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EsferaQueryService extends QueryService<Esfera> {

    private static final Logger log = LoggerFactory.getLogger(EsferaQueryService.class);

    private final EsferaRepository esferaRepository;

    public EsferaQueryService(EsferaRepository esferaRepository) {
        this.esferaRepository = esferaRepository;
    }

    /**
     * Return a {@link Page} of {@link Esfera} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Esfera> findByCriteria(EsferaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Esfera> specification = createSpecification(criteria);
        return esferaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EsferaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Esfera> specification = createSpecification(criteria);
        return esferaRepository.count(specification);
    }

    /**
     * Function to convert {@link EsferaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Esfera> createSpecification(EsferaCriteria criteria) {
        Specification<Esfera> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Esfera_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Esfera_.nome));
            }
            if (criteria.getServicoContabilId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getServicoContabilId(),
                        root -> root.join(Esfera_.servicoContabils, JoinType.LEFT).get(ServicoContabil_.id)
                    )
                );
            }
            if (criteria.getImpostoId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getImpostoId(), root -> root.join(Esfera_.impostos, JoinType.LEFT).get(Imposto_.id))
                );
            }
            if (criteria.getTarefaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getTarefaId(), root -> root.join(Esfera_.tarefas, JoinType.LEFT).get(Tarefa_.id))
                );
            }
        }
        return specification;
    }
}
