package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.TipoDenuncia;
import com.dobemcontabilidade.repository.TipoDenunciaRepository;
import com.dobemcontabilidade.service.criteria.TipoDenunciaCriteria;
import com.dobemcontabilidade.service.dto.TipoDenunciaDTO;
import com.dobemcontabilidade.service.mapper.TipoDenunciaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link TipoDenuncia} entities in the database.
 * The main input is a {@link TipoDenunciaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link TipoDenunciaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TipoDenunciaQueryService extends QueryService<TipoDenuncia> {

    private static final Logger log = LoggerFactory.getLogger(TipoDenunciaQueryService.class);

    private final TipoDenunciaRepository tipoDenunciaRepository;

    private final TipoDenunciaMapper tipoDenunciaMapper;

    public TipoDenunciaQueryService(TipoDenunciaRepository tipoDenunciaRepository, TipoDenunciaMapper tipoDenunciaMapper) {
        this.tipoDenunciaRepository = tipoDenunciaRepository;
        this.tipoDenunciaMapper = tipoDenunciaMapper;
    }

    /**
     * Return a {@link Page} of {@link TipoDenunciaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoDenunciaDTO> findByCriteria(TipoDenunciaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TipoDenuncia> specification = createSpecification(criteria);
        return tipoDenunciaRepository.findAll(specification, page).map(tipoDenunciaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TipoDenunciaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TipoDenuncia> specification = createSpecification(criteria);
        return tipoDenunciaRepository.count(specification);
    }

    /**
     * Function to convert {@link TipoDenunciaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TipoDenuncia> createSpecification(TipoDenunciaCriteria criteria) {
        Specification<TipoDenuncia> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TipoDenuncia_.id));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipo(), TipoDenuncia_.tipo));
            }
        }
        return specification;
    }
}
