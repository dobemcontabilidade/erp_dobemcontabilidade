package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.UsuarioGestao;
import com.dobemcontabilidade.repository.UsuarioGestaoRepository;
import com.dobemcontabilidade.service.criteria.UsuarioGestaoCriteria;
import com.dobemcontabilidade.service.dto.UsuarioGestaoDTO;
import com.dobemcontabilidade.service.mapper.UsuarioGestaoMapper;
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
 * Service for executing complex queries for {@link UsuarioGestao} entities in the database.
 * The main input is a {@link UsuarioGestaoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link UsuarioGestaoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UsuarioGestaoQueryService extends QueryService<UsuarioGestao> {

    private static final Logger log = LoggerFactory.getLogger(UsuarioGestaoQueryService.class);

    private final UsuarioGestaoRepository usuarioGestaoRepository;

    private final UsuarioGestaoMapper usuarioGestaoMapper;

    public UsuarioGestaoQueryService(UsuarioGestaoRepository usuarioGestaoRepository, UsuarioGestaoMapper usuarioGestaoMapper) {
        this.usuarioGestaoRepository = usuarioGestaoRepository;
        this.usuarioGestaoMapper = usuarioGestaoMapper;
    }

    /**
     * Return a {@link Page} of {@link UsuarioGestaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UsuarioGestaoDTO> findByCriteria(UsuarioGestaoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UsuarioGestao> specification = createSpecification(criteria);
        return usuarioGestaoRepository.findAll(specification, page).map(usuarioGestaoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UsuarioGestaoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UsuarioGestao> specification = createSpecification(criteria);
        return usuarioGestaoRepository.count(specification);
    }

    /**
     * Function to convert {@link UsuarioGestaoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UsuarioGestao> createSpecification(UsuarioGestaoCriteria criteria) {
        Specification<UsuarioGestao> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UsuarioGestao_.id));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), UsuarioGestao_.email));
            }
            if (criteria.getDataHoraAtivacao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataHoraAtivacao(), UsuarioGestao_.dataHoraAtivacao));
            }
            if (criteria.getDataLimiteAcesso() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataLimiteAcesso(), UsuarioGestao_.dataLimiteAcesso));
            }
            if (criteria.getSituacao() != null) {
                specification = specification.and(buildSpecification(criteria.getSituacao(), UsuarioGestao_.situacao));
            }
            if (criteria.getAdministradorId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAdministradorId(),
                        root -> root.join(UsuarioGestao_.administrador, JoinType.LEFT).get(Administrador_.id)
                    )
                );
            }
        }
        return specification;
    }
}
