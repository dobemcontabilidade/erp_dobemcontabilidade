package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.EnderecoEmpresa;
import com.dobemcontabilidade.repository.EnderecoEmpresaRepository;
import com.dobemcontabilidade.service.criteria.EnderecoEmpresaCriteria;
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
 * Service for executing complex queries for {@link EnderecoEmpresa} entities in the database.
 * The main input is a {@link EnderecoEmpresaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link EnderecoEmpresa} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EnderecoEmpresaQueryService extends QueryService<EnderecoEmpresa> {

    private static final Logger log = LoggerFactory.getLogger(EnderecoEmpresaQueryService.class);

    private final EnderecoEmpresaRepository enderecoEmpresaRepository;

    public EnderecoEmpresaQueryService(EnderecoEmpresaRepository enderecoEmpresaRepository) {
        this.enderecoEmpresaRepository = enderecoEmpresaRepository;
    }

    /**
     * Return a {@link Page} of {@link EnderecoEmpresa} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EnderecoEmpresa> findByCriteria(EnderecoEmpresaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EnderecoEmpresa> specification = createSpecification(criteria);
        return enderecoEmpresaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EnderecoEmpresaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EnderecoEmpresa> specification = createSpecification(criteria);
        return enderecoEmpresaRepository.count(specification);
    }

    /**
     * Function to convert {@link EnderecoEmpresaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EnderecoEmpresa> createSpecification(EnderecoEmpresaCriteria criteria) {
        Specification<EnderecoEmpresa> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EnderecoEmpresa_.id));
            }
            if (criteria.getLogradouro() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogradouro(), EnderecoEmpresa_.logradouro));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumero(), EnderecoEmpresa_.numero));
            }
            if (criteria.getComplemento() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComplemento(), EnderecoEmpresa_.complemento));
            }
            if (criteria.getBairro() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBairro(), EnderecoEmpresa_.bairro));
            }
            if (criteria.getCep() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCep(), EnderecoEmpresa_.cep));
            }
            if (criteria.getPrincipal() != null) {
                specification = specification.and(buildSpecification(criteria.getPrincipal(), EnderecoEmpresa_.principal));
            }
            if (criteria.getFilial() != null) {
                specification = specification.and(buildSpecification(criteria.getFilial(), EnderecoEmpresa_.filial));
            }
            if (criteria.getEnderecoFiscal() != null) {
                specification = specification.and(buildSpecification(criteria.getEnderecoFiscal(), EnderecoEmpresa_.enderecoFiscal));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getEmpresaId(), root -> root.join(EnderecoEmpresa_.empresa, JoinType.LEFT).get(Empresa_.id))
                );
            }
            if (criteria.getCidadeId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getCidadeId(), root -> root.join(EnderecoEmpresa_.cidade, JoinType.LEFT).get(Cidade_.id))
                );
            }
        }
        return specification;
    }
}
