package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.UsuarioEmpresa;
import com.dobemcontabilidade.repository.UsuarioEmpresaRepository;
import com.dobemcontabilidade.service.criteria.UsuarioEmpresaCriteria;
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
 * Service for executing complex queries for {@link UsuarioEmpresa} entities in the database.
 * The main input is a {@link UsuarioEmpresaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link UsuarioEmpresa} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UsuarioEmpresaQueryService extends QueryService<UsuarioEmpresa> {

    private static final Logger log = LoggerFactory.getLogger(UsuarioEmpresaQueryService.class);

    private final UsuarioEmpresaRepository usuarioEmpresaRepository;

    public UsuarioEmpresaQueryService(UsuarioEmpresaRepository usuarioEmpresaRepository) {
        this.usuarioEmpresaRepository = usuarioEmpresaRepository;
    }

    /**
     * Return a {@link Page} of {@link UsuarioEmpresa} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UsuarioEmpresa> findByCriteria(UsuarioEmpresaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UsuarioEmpresa> specification = createSpecification(criteria);
        return usuarioEmpresaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UsuarioEmpresaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UsuarioEmpresa> specification = createSpecification(criteria);
        return usuarioEmpresaRepository.count(specification);
    }

    /**
     * Function to convert {@link UsuarioEmpresaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UsuarioEmpresa> createSpecification(UsuarioEmpresaCriteria criteria) {
        Specification<UsuarioEmpresa> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UsuarioEmpresa_.id));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), UsuarioEmpresa_.email));
            }
            if (criteria.getAtivo() != null) {
                specification = specification.and(buildSpecification(criteria.getAtivo(), UsuarioEmpresa_.ativo));
            }
            if (criteria.getDataHoraAtivacao() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getDataHoraAtivacao(), UsuarioEmpresa_.dataHoraAtivacao)
                );
            }
            if (criteria.getDataLimiteAcesso() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getDataLimiteAcesso(), UsuarioEmpresa_.dataLimiteAcesso)
                );
            }
            if (criteria.getSituacaoUsuarioEmpresa() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getSituacaoUsuarioEmpresa(), UsuarioEmpresa_.situacaoUsuarioEmpresa)
                );
            }
            if (criteria.getTipoUsuarioEmpresaEnum() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getTipoUsuarioEmpresaEnum(), UsuarioEmpresa_.tipoUsuarioEmpresaEnum)
                );
            }
            if (criteria.getGrupoAcessoUsuarioEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getGrupoAcessoUsuarioEmpresaId(),
                        root -> root.join(UsuarioEmpresa_.grupoAcessoUsuarioEmpresas, JoinType.LEFT).get(GrupoAcessoUsuarioEmpresa_.id)
                    )
                );
            }
            if (criteria.getFeedBackUsuarioParaContadorId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getFeedBackUsuarioParaContadorId(),
                        root -> root.join(UsuarioEmpresa_.feedBackUsuarioParaContadors, JoinType.LEFT).get(FeedBackUsuarioParaContador_.id)
                    )
                );
            }
            if (criteria.getFeedBackContadorParaUsuarioId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getFeedBackContadorParaUsuarioId(),
                        root -> root.join(UsuarioEmpresa_.feedBackContadorParaUsuarios, JoinType.LEFT).get(FeedBackContadorParaUsuario_.id)
                    )
                );
            }
            if (criteria.getAssinaturaEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAssinaturaEmpresaId(),
                        root -> root.join(UsuarioEmpresa_.assinaturaEmpresa, JoinType.LEFT).get(AssinaturaEmpresa_.id)
                    )
                );
            }
            if (criteria.getFuncionarioId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getFuncionarioId(),
                        root -> root.join(UsuarioEmpresa_.funcionario, JoinType.LEFT).get(Funcionario_.id)
                    )
                );
            }
            if (criteria.getSocioId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getSocioId(), root -> root.join(UsuarioEmpresa_.socio, JoinType.LEFT).get(Socio_.id))
                );
            }
        }
        return specification;
    }
}
