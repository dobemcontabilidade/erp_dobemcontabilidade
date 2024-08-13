package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.Pessoa;
import com.dobemcontabilidade.repository.PessoaRepository;
import com.dobemcontabilidade.service.criteria.PessoaCriteria;
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
 * Service for executing complex queries for {@link Pessoa} entities in the database.
 * The main input is a {@link PessoaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Pessoa} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PessoaQueryService extends QueryService<Pessoa> {

    private static final Logger log = LoggerFactory.getLogger(PessoaQueryService.class);

    private final PessoaRepository pessoaRepository;

    public PessoaQueryService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    /**
     * Return a {@link Page} of {@link Pessoa} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Pessoa> findByCriteria(PessoaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Pessoa> specification = createSpecification(criteria);
        return pessoaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PessoaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Pessoa> specification = createSpecification(criteria);
        return pessoaRepository.count(specification);
    }

    /**
     * Function to convert {@link PessoaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Pessoa> createSpecification(PessoaCriteria criteria) {
        Specification<Pessoa> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Pessoa_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Pessoa_.nome));
            }
            if (criteria.getCpf() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCpf(), Pessoa_.cpf));
            }
            if (criteria.getDataNascimento() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDataNascimento(), Pessoa_.dataNascimento));
            }
            if (criteria.getTituloEleitor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTituloEleitor(), Pessoa_.tituloEleitor));
            }
            if (criteria.getRg() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRg(), Pessoa_.rg));
            }
            if (criteria.getRgOrgaoExpeditor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRgOrgaoExpeditor(), Pessoa_.rgOrgaoExpeditor));
            }
            if (criteria.getRgUfExpedicao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRgUfExpedicao(), Pessoa_.rgUfExpedicao));
            }
            if (criteria.getNomeMae() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomeMae(), Pessoa_.nomeMae));
            }
            if (criteria.getNomePai() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomePai(), Pessoa_.nomePai));
            }
            if (criteria.getLocalNascimento() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocalNascimento(), Pessoa_.localNascimento));
            }
            if (criteria.getRacaECor() != null) {
                specification = specification.and(buildSpecification(criteria.getRacaECor(), Pessoa_.racaECor));
            }
            if (criteria.getPessoaComDeficiencia() != null) {
                specification = specification.and(buildSpecification(criteria.getPessoaComDeficiencia(), Pessoa_.pessoaComDeficiencia));
            }
            if (criteria.getEstadoCivil() != null) {
                specification = specification.and(buildSpecification(criteria.getEstadoCivil(), Pessoa_.estadoCivil));
            }
            if (criteria.getSexo() != null) {
                specification = specification.and(buildSpecification(criteria.getSexo(), Pessoa_.sexo));
            }
            if (criteria.getUrlFotoPerfil() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrlFotoPerfil(), Pessoa_.urlFotoPerfil));
            }
            if (criteria.getFuncionarioId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getFuncionarioId(),
                        root -> root.join(Pessoa_.funcionarios, JoinType.LEFT).get(Funcionario_.id)
                    )
                );
            }
            if (criteria.getAnexoPessoaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAnexoPessoaId(),
                        root -> root.join(Pessoa_.anexoPessoas, JoinType.LEFT).get(AnexoPessoa_.id)
                    )
                );
            }
            if (criteria.getEscolaridadePessoaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getEscolaridadePessoaId(),
                        root -> root.join(Pessoa_.escolaridadePessoas, JoinType.LEFT).get(EscolaridadePessoa_.id)
                    )
                );
            }
            if (criteria.getBancoPessoaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getBancoPessoaId(),
                        root -> root.join(Pessoa_.bancoPessoas, JoinType.LEFT).get(BancoPessoa_.id)
                    )
                );
            }
            if (criteria.getDependentesFuncionarioId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getDependentesFuncionarioId(),
                        root -> root.join(Pessoa_.dependentesFuncionarios, JoinType.LEFT).get(DependentesFuncionario_.id)
                    )
                );
            }
            if (criteria.getEnderecoPessoaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getEnderecoPessoaId(),
                        root -> root.join(Pessoa_.enderecoPessoas, JoinType.LEFT).get(EnderecoPessoa_.id)
                    )
                );
            }
            if (criteria.getEmailId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getEmailId(), root -> root.join(Pessoa_.emails, JoinType.LEFT).get(Email_.id))
                );
            }
            if (criteria.getTelefoneId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getTelefoneId(), root -> root.join(Pessoa_.telefones, JoinType.LEFT).get(Telefone_.id))
                );
            }
            if (criteria.getAdministradorId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAdministradorId(),
                        root -> root.join(Pessoa_.administrador, JoinType.LEFT).get(Administrador_.id)
                    )
                );
            }
            if (criteria.getContadorId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getContadorId(), root -> root.join(Pessoa_.contador, JoinType.LEFT).get(Contador_.id))
                );
            }
            if (criteria.getSocioId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getSocioId(), root -> root.join(Pessoa_.socio, JoinType.LEFT).get(Socio_.id))
                );
            }
        }
        return specification;
    }
}
