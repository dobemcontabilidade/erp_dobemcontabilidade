package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.Ramo;
import com.dobemcontabilidade.repository.RamoRepository;
import com.dobemcontabilidade.service.criteria.RamoCriteria;
import com.dobemcontabilidade.service.dto.RamoDTO;
import com.dobemcontabilidade.service.mapper.RamoMapper;
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
 * Service for executing complex queries for {@link Ramo} entities in the database.
 * The main input is a {@link RamoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link RamoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RamoQueryService extends QueryService<Ramo> {

    private static final Logger log = LoggerFactory.getLogger(RamoQueryService.class);

    private final RamoRepository ramoRepository;

    private final RamoMapper ramoMapper;

    public RamoQueryService(RamoRepository ramoRepository, RamoMapper ramoMapper) {
        this.ramoRepository = ramoRepository;
        this.ramoMapper = ramoMapper;
    }

    /**
     * Return a {@link Page} of {@link RamoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RamoDTO> findByCriteria(RamoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Ramo> specification = createSpecification(criteria);
        return ramoRepository.findAll(specification, page).map(ramoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RamoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Ramo> specification = createSpecification(criteria);
        return ramoRepository.count(specification);
    }

    /**
     * Function to convert {@link RamoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Ramo> createSpecification(RamoCriteria criteria) {
        Specification<Ramo> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Ramo_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Ramo_.nome));
            }
            if (criteria.getCalculoPlanoAssinaturaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getCalculoPlanoAssinaturaId(),
                        root -> root.join(Ramo_.calculoPlanoAssinaturas, JoinType.LEFT).get(CalculoPlanoAssinatura_.id)
                    )
                );
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getEmpresaId(), root -> root.join(Ramo_.empresas, JoinType.LEFT).get(Empresa_.id))
                );
            }
            if (criteria.getAdicionalRamoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAdicionalRamoId(),
                        root -> root.join(Ramo_.adicionalRamos, JoinType.LEFT).get(AdicionalRamo_.id)
                    )
                );
            }
            if (criteria.getValorBaseRamoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getValorBaseRamoId(),
                        root -> root.join(Ramo_.valorBaseRamos, JoinType.LEFT).get(ValorBaseRamo_.id)
                    )
                );
            }
        }
        return specification;
    }
}
