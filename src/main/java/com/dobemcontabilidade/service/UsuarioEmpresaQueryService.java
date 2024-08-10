package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.UsuarioEmpresa;
import com.dobemcontabilidade.repository.UsuarioEmpresaRepository;
import com.dobemcontabilidade.service.criteria.UsuarioEmpresaCriteria;
import com.dobemcontabilidade.service.dto.UsuarioEmpresaDTO;
import com.dobemcontabilidade.service.mapper.UsuarioEmpresaMapper;
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
 * It returns a {@link Page} of {@link UsuarioEmpresaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UsuarioEmpresaQueryService extends QueryService<UsuarioEmpresa> {

    private static final Logger log = LoggerFactory.getLogger(UsuarioEmpresaQueryService.class);

    private final UsuarioEmpresaRepository usuarioEmpresaRepository;

    private final UsuarioEmpresaMapper usuarioEmpresaMapper;

    public UsuarioEmpresaQueryService(UsuarioEmpresaRepository usuarioEmpresaRepository, UsuarioEmpresaMapper usuarioEmpresaMapper) {
        this.usuarioEmpresaRepository = usuarioEmpresaRepository;
        this.usuarioEmpresaMapper = usuarioEmpresaMapper;
    }

    /**
     * Return a {@link Page} of {@link UsuarioEmpresaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UsuarioEmpresaDTO> findByCriteria(UsuarioEmpresaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UsuarioEmpresa> specification = createSpecification(criteria);
        return usuarioEmpresaRepository.findAll(specification, page).map(usuarioEmpresaMapper::toDto);
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
            if (criteria.getSituacao() != null) {
                specification = specification.and(buildSpecification(criteria.getSituacao(), UsuarioEmpresa_.situacao));
            }
            if (criteria.getPessoaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getPessoaId(), root -> root.join(UsuarioEmpresa_.pessoa, JoinType.LEFT).get(Pessoa_.id))
                );
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getEmpresaId(), root -> root.join(UsuarioEmpresa_.empresa, JoinType.LEFT).get(Empresa_.id))
                );
            }
        }
        return specification;
    }
}
