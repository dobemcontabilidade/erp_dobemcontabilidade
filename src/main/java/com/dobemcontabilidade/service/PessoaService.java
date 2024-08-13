package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Pessoa;
import com.dobemcontabilidade.repository.PessoaRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Pessoa}.
 */
@Service
@Transactional
public class PessoaService {

    private static final Logger log = LoggerFactory.getLogger(PessoaService.class);

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    /**
     * Save a pessoa.
     *
     * @param pessoa the entity to save.
     * @return the persisted entity.
     */
    public Pessoa save(Pessoa pessoa) {
        log.debug("Request to save Pessoa : {}", pessoa);
        return pessoaRepository.save(pessoa);
    }

    /**
     * Update a pessoa.
     *
     * @param pessoa the entity to save.
     * @return the persisted entity.
     */
    public Pessoa update(Pessoa pessoa) {
        log.debug("Request to update Pessoa : {}", pessoa);
        return pessoaRepository.save(pessoa);
    }

    /**
     * Partially update a pessoa.
     *
     * @param pessoa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Pessoa> partialUpdate(Pessoa pessoa) {
        log.debug("Request to partially update Pessoa : {}", pessoa);

        return pessoaRepository
            .findById(pessoa.getId())
            .map(existingPessoa -> {
                if (pessoa.getNome() != null) {
                    existingPessoa.setNome(pessoa.getNome());
                }
                if (pessoa.getCpf() != null) {
                    existingPessoa.setCpf(pessoa.getCpf());
                }
                if (pessoa.getDataNascimento() != null) {
                    existingPessoa.setDataNascimento(pessoa.getDataNascimento());
                }
                if (pessoa.getTituloEleitor() != null) {
                    existingPessoa.setTituloEleitor(pessoa.getTituloEleitor());
                }
                if (pessoa.getRg() != null) {
                    existingPessoa.setRg(pessoa.getRg());
                }
                if (pessoa.getRgOrgaoExpeditor() != null) {
                    existingPessoa.setRgOrgaoExpeditor(pessoa.getRgOrgaoExpeditor());
                }
                if (pessoa.getRgUfExpedicao() != null) {
                    existingPessoa.setRgUfExpedicao(pessoa.getRgUfExpedicao());
                }
                if (pessoa.getNomeMae() != null) {
                    existingPessoa.setNomeMae(pessoa.getNomeMae());
                }
                if (pessoa.getNomePai() != null) {
                    existingPessoa.setNomePai(pessoa.getNomePai());
                }
                if (pessoa.getLocalNascimento() != null) {
                    existingPessoa.setLocalNascimento(pessoa.getLocalNascimento());
                }
                if (pessoa.getRacaECor() != null) {
                    existingPessoa.setRacaECor(pessoa.getRacaECor());
                }
                if (pessoa.getPessoaComDeficiencia() != null) {
                    existingPessoa.setPessoaComDeficiencia(pessoa.getPessoaComDeficiencia());
                }
                if (pessoa.getEstadoCivil() != null) {
                    existingPessoa.setEstadoCivil(pessoa.getEstadoCivil());
                }
                if (pessoa.getSexo() != null) {
                    existingPessoa.setSexo(pessoa.getSexo());
                }
                if (pessoa.getUrlFotoPerfil() != null) {
                    existingPessoa.setUrlFotoPerfil(pessoa.getUrlFotoPerfil());
                }

                return existingPessoa;
            })
            .map(pessoaRepository::save);
    }

    /**
     *  Get all the pessoas where Administrador is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Pessoa> findAllWhereAdministradorIsNull() {
        log.debug("Request to get all pessoas where Administrador is null");
        return StreamSupport.stream(pessoaRepository.findAll().spliterator(), false)
            .filter(pessoa -> pessoa.getAdministrador() == null)
            .toList();
    }

    /**
     *  Get all the pessoas where Contador is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Pessoa> findAllWhereContadorIsNull() {
        log.debug("Request to get all pessoas where Contador is null");
        return StreamSupport.stream(pessoaRepository.findAll().spliterator(), false)
            .filter(pessoa -> pessoa.getContador() == null)
            .toList();
    }

    /**
     *  Get all the pessoas where Socio is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Pessoa> findAllWhereSocioIsNull() {
        log.debug("Request to get all pessoas where Socio is null");
        return StreamSupport.stream(pessoaRepository.findAll().spliterator(), false).filter(pessoa -> pessoa.getSocio() == null).toList();
    }

    /**
     * Get one pessoa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Pessoa> findOne(Long id) {
        log.debug("Request to get Pessoa : {}", id);
        return pessoaRepository.findById(id);
    }

    /**
     * Delete the pessoa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Pessoa : {}", id);
        pessoaRepository.deleteById(id);
    }
}
