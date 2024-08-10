package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.DocumentoTarefa;
import com.dobemcontabilidade.repository.DocumentoTarefaRepository;
import com.dobemcontabilidade.service.criteria.DocumentoTarefaCriteria;
import com.dobemcontabilidade.service.dto.DocumentoTarefaDTO;
import com.dobemcontabilidade.service.mapper.DocumentoTarefaMapper;
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
 * Service for executing complex queries for {@link DocumentoTarefa} entities in the database.
 * The main input is a {@link DocumentoTarefaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link DocumentoTarefaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DocumentoTarefaQueryService extends QueryService<DocumentoTarefa> {

    private static final Logger log = LoggerFactory.getLogger(DocumentoTarefaQueryService.class);

    private final DocumentoTarefaRepository documentoTarefaRepository;

    private final DocumentoTarefaMapper documentoTarefaMapper;

    public DocumentoTarefaQueryService(DocumentoTarefaRepository documentoTarefaRepository, DocumentoTarefaMapper documentoTarefaMapper) {
        this.documentoTarefaRepository = documentoTarefaRepository;
        this.documentoTarefaMapper = documentoTarefaMapper;
    }

    /**
     * Return a {@link Page} of {@link DocumentoTarefaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentoTarefaDTO> findByCriteria(DocumentoTarefaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DocumentoTarefa> specification = createSpecification(criteria);
        return documentoTarefaRepository.findAll(specification, page).map(documentoTarefaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DocumentoTarefaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DocumentoTarefa> specification = createSpecification(criteria);
        return documentoTarefaRepository.count(specification);
    }

    /**
     * Function to convert {@link DocumentoTarefaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DocumentoTarefa> createSpecification(DocumentoTarefaCriteria criteria) {
        Specification<DocumentoTarefa> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DocumentoTarefa_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), DocumentoTarefa_.nome));
            }
            if (criteria.getTarefaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getTarefaId(), root -> root.join(DocumentoTarefa_.tarefa, JoinType.LEFT).get(Tarefa_.id))
                );
            }
        }
        return specification;
    }
}
