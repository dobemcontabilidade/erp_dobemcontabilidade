package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Pessoajuridica;
import com.dobemcontabilidade.repository.PessoajuridicaRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Pessoajuridica}.
 */
@Service
@Transactional
public class PessoajuridicaService {

    private static final Logger log = LoggerFactory.getLogger(PessoajuridicaService.class);

    private final PessoajuridicaRepository pessoajuridicaRepository;

    public PessoajuridicaService(PessoajuridicaRepository pessoajuridicaRepository) {
        this.pessoajuridicaRepository = pessoajuridicaRepository;
    }

    /**
     * Save a pessoajuridica.
     *
     * @param pessoajuridica the entity to save.
     * @return the persisted entity.
     */
    public Pessoajuridica save(Pessoajuridica pessoajuridica) {
        log.debug("Request to save Pessoajuridica : {}", pessoajuridica);
        return pessoajuridicaRepository.save(pessoajuridica);
    }

    /**
     * Update a pessoajuridica.
     *
     * @param pessoajuridica the entity to save.
     * @return the persisted entity.
     */
    public Pessoajuridica update(Pessoajuridica pessoajuridica) {
        log.debug("Request to update Pessoajuridica : {}", pessoajuridica);
        return pessoajuridicaRepository.save(pessoajuridica);
    }

    /**
     * Partially update a pessoajuridica.
     *
     * @param pessoajuridica the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Pessoajuridica> partialUpdate(Pessoajuridica pessoajuridica) {
        log.debug("Request to partially update Pessoajuridica : {}", pessoajuridica);

        return pessoajuridicaRepository
            .findById(pessoajuridica.getId())
            .map(existingPessoajuridica -> {
                if (pessoajuridica.getRazaoSocial() != null) {
                    existingPessoajuridica.setRazaoSocial(pessoajuridica.getRazaoSocial());
                }
                if (pessoajuridica.getNomeFantasia() != null) {
                    existingPessoajuridica.setNomeFantasia(pessoajuridica.getNomeFantasia());
                }
                if (pessoajuridica.getCnpj() != null) {
                    existingPessoajuridica.setCnpj(pessoajuridica.getCnpj());
                }

                return existingPessoajuridica;
            })
            .map(pessoajuridicaRepository::save);
    }

    /**
     *  Get all the pessoajuridicas where Empresa is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Pessoajuridica> findAllWhereEmpresaIsNull() {
        log.debug("Request to get all pessoajuridicas where Empresa is null");
        return StreamSupport.stream(pessoajuridicaRepository.findAll().spliterator(), false)
            .filter(pessoajuridica -> pessoajuridica.getEmpresa() == null)
            .toList();
    }

    /**
     * Get one pessoajuridica by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Pessoajuridica> findOne(Long id) {
        log.debug("Request to get Pessoajuridica : {}", id);
        return pessoajuridicaRepository.findById(id);
    }

    /**
     * Delete the pessoajuridica by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Pessoajuridica : {}", id);
        pessoajuridicaRepository.deleteById(id);
    }
}
