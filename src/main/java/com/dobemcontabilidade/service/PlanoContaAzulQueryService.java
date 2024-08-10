package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.PlanoContaAzul;
import com.dobemcontabilidade.repository.PlanoContaAzulRepository;
import com.dobemcontabilidade.service.criteria.PlanoContaAzulCriteria;
import com.dobemcontabilidade.service.dto.PlanoContaAzulDTO;
import com.dobemcontabilidade.service.mapper.PlanoContaAzulMapper;
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
 * Service for executing complex queries for {@link PlanoContaAzul} entities in the database.
 * The main input is a {@link PlanoContaAzulCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link PlanoContaAzulDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PlanoContaAzulQueryService extends QueryService<PlanoContaAzul> {

    private static final Logger log = LoggerFactory.getLogger(PlanoContaAzulQueryService.class);

    private final PlanoContaAzulRepository planoContaAzulRepository;

    private final PlanoContaAzulMapper planoContaAzulMapper;

    public PlanoContaAzulQueryService(PlanoContaAzulRepository planoContaAzulRepository, PlanoContaAzulMapper planoContaAzulMapper) {
        this.planoContaAzulRepository = planoContaAzulRepository;
        this.planoContaAzulMapper = planoContaAzulMapper;
    }

    /**
     * Return a {@link Page} of {@link PlanoContaAzulDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PlanoContaAzulDTO> findByCriteria(PlanoContaAzulCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PlanoContaAzul> specification = createSpecification(criteria);
        return planoContaAzulRepository.findAll(specification, page).map(planoContaAzulMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PlanoContaAzulCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PlanoContaAzul> specification = createSpecification(criteria);
        return planoContaAzulRepository.count(specification);
    }

    /**
     * Function to convert {@link PlanoContaAzulCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PlanoContaAzul> createSpecification(PlanoContaAzulCriteria criteria) {
        Specification<PlanoContaAzul> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PlanoContaAzul_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), PlanoContaAzul_.nome));
            }
            if (criteria.getValorBase() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorBase(), PlanoContaAzul_.valorBase));
            }
            if (criteria.getUsuarios() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUsuarios(), PlanoContaAzul_.usuarios));
            }
            if (criteria.getBoletos() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBoletos(), PlanoContaAzul_.boletos));
            }
            if (criteria.getNotaFiscalProduto() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getNotaFiscalProduto(), PlanoContaAzul_.notaFiscalProduto)
                );
            }
            if (criteria.getNotaFiscalServico() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getNotaFiscalServico(), PlanoContaAzul_.notaFiscalServico)
                );
            }
            if (criteria.getNotaFiscalCe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNotaFiscalCe(), PlanoContaAzul_.notaFiscalCe));
            }
            if (criteria.getSuporte() != null) {
                specification = specification.and(buildSpecification(criteria.getSuporte(), PlanoContaAzul_.suporte));
            }
            if (criteria.getSituacao() != null) {
                specification = specification.and(buildSpecification(criteria.getSituacao(), PlanoContaAzul_.situacao));
            }
            if (criteria.getCalculoPlanoAssinaturaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getCalculoPlanoAssinaturaId(),
                        root -> root.join(PlanoContaAzul_.calculoPlanoAssinaturas, JoinType.LEFT).get(CalculoPlanoAssinatura_.id)
                    )
                );
            }
            if (criteria.getAssinaturaEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAssinaturaEmpresaId(),
                        root -> root.join(PlanoContaAzul_.assinaturaEmpresas, JoinType.LEFT).get(AssinaturaEmpresa_.id)
                    )
                );
            }
            if (criteria.getDescontoPlanoContaAzulId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getDescontoPlanoContaAzulId(),
                        root -> root.join(PlanoContaAzul_.descontoPlanoContaAzuls, JoinType.LEFT).get(DescontoPlanoContaAzul_.id)
                    )
                );
            }
        }
        return specification;
    }
}
