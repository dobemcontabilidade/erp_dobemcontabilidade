package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.Socio;
import com.dobemcontabilidade.repository.SocioRepository;
import com.dobemcontabilidade.service.criteria.SocioCriteria;
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
 * Service for executing complex queries for {@link Socio} entities in the database.
 * The main input is a {@link SocioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Socio} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SocioQueryService extends QueryService<Socio> {

    private static final Logger log = LoggerFactory.getLogger(SocioQueryService.class);

    private final SocioRepository socioRepository;

    public SocioQueryService(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }

    /**
     * Return a {@link Page} of {@link Socio} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Socio> findByCriteria(SocioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Socio> specification = createSpecification(criteria);
        return socioRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SocioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Socio> specification = createSpecification(criteria);
        return socioRepository.count(specification);
    }

    /**
     * Function to convert {@link SocioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Socio> createSpecification(SocioCriteria criteria) {
        Specification<Socio> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Socio_.id));
            }
            if (criteria.getProlabore() != null) {
                specification = specification.and(buildSpecification(criteria.getProlabore(), Socio_.prolabore));
            }
            if (criteria.getPercentualSociedade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPercentualSociedade(), Socio_.percentualSociedade));
            }
            if (criteria.getAdminstrador() != null) {
                specification = specification.and(buildSpecification(criteria.getAdminstrador(), Socio_.adminstrador));
            }
            if (criteria.getDistribuicaoLucro() != null) {
                specification = specification.and(buildSpecification(criteria.getDistribuicaoLucro(), Socio_.distribuicaoLucro));
            }
            if (criteria.getResponsavelReceita() != null) {
                specification = specification.and(buildSpecification(criteria.getResponsavelReceita(), Socio_.responsavelReceita));
            }
            if (criteria.getPercentualDistribuicaoLucro() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getPercentualDistribuicaoLucro(), Socio_.percentualDistribuicaoLucro)
                );
            }
            if (criteria.getFuncaoSocio() != null) {
                specification = specification.and(buildSpecification(criteria.getFuncaoSocio(), Socio_.funcaoSocio));
            }
            if (criteria.getPessoaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getPessoaId(), root -> root.join(Socio_.pessoa, JoinType.LEFT).get(Pessoa_.id))
                );
            }
            if (criteria.getUsuarioEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getUsuarioEmpresaId(),
                        root -> root.join(Socio_.usuarioEmpresa, JoinType.LEFT).get(UsuarioEmpresa_.id)
                    )
                );
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getEmpresaId(), root -> root.join(Socio_.empresa, JoinType.LEFT).get(Empresa_.id))
                );
            }
            if (criteria.getProfissaoId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getProfissaoId(), root -> root.join(Socio_.profissao, JoinType.LEFT).get(Profissao_.id))
                );
            }
        }
        return specification;
    }
}
