package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.Denuncia;
import com.dobemcontabilidade.repository.DenunciaRepository;
import com.dobemcontabilidade.service.criteria.DenunciaCriteria;
import com.dobemcontabilidade.service.dto.DenunciaDTO;
import com.dobemcontabilidade.service.mapper.DenunciaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Denuncia} entities in the database.
 * The main input is a {@link DenunciaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link DenunciaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DenunciaQueryService extends QueryService<Denuncia> {

    private static final Logger log = LoggerFactory.getLogger(DenunciaQueryService.class);

    private final DenunciaRepository denunciaRepository;

    private final DenunciaMapper denunciaMapper;

    public DenunciaQueryService(DenunciaRepository denunciaRepository, DenunciaMapper denunciaMapper) {
        this.denunciaRepository = denunciaRepository;
        this.denunciaMapper = denunciaMapper;
    }

    /**
     * Return a {@link Page} of {@link DenunciaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DenunciaDTO> findByCriteria(DenunciaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Denuncia> specification = createSpecification(criteria);
        return denunciaRepository.findAll(specification, page).map(denunciaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DenunciaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Denuncia> specification = createSpecification(criteria);
        return denunciaRepository.count(specification);
    }

    /**
     * Function to convert {@link DenunciaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Denuncia> createSpecification(DenunciaCriteria criteria) {
        Specification<Denuncia> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Denuncia_.id));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), Denuncia_.titulo));
            }
            if (criteria.getMensagem() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMensagem(), Denuncia_.mensagem));
            }
        }
        return specification;
    }
}
