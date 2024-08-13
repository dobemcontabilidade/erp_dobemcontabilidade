package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.repository.EmpresaRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Empresa}.
 */
@Service
@Transactional
public class EmpresaService {

    private static final Logger log = LoggerFactory.getLogger(EmpresaService.class);

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    /**
     * Save a empresa.
     *
     * @param empresa the entity to save.
     * @return the persisted entity.
     */
    public Empresa save(Empresa empresa) {
        log.debug("Request to save Empresa : {}", empresa);
        return empresaRepository.save(empresa);
    }

    /**
     * Update a empresa.
     *
     * @param empresa the entity to save.
     * @return the persisted entity.
     */
    public Empresa update(Empresa empresa) {
        log.debug("Request to update Empresa : {}", empresa);
        return empresaRepository.save(empresa);
    }

    /**
     * Partially update a empresa.
     *
     * @param empresa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Empresa> partialUpdate(Empresa empresa) {
        log.debug("Request to partially update Empresa : {}", empresa);

        return empresaRepository
            .findById(empresa.getId())
            .map(existingEmpresa -> {
                if (empresa.getRazaoSocial() != null) {
                    existingEmpresa.setRazaoSocial(empresa.getRazaoSocial());
                }
                if (empresa.getNomeFantasia() != null) {
                    existingEmpresa.setNomeFantasia(empresa.getNomeFantasia());
                }
                if (empresa.getDescricaoDoNegocio() != null) {
                    existingEmpresa.setDescricaoDoNegocio(empresa.getDescricaoDoNegocio());
                }
                if (empresa.getCnpj() != null) {
                    existingEmpresa.setCnpj(empresa.getCnpj());
                }
                if (empresa.getDataAbertura() != null) {
                    existingEmpresa.setDataAbertura(empresa.getDataAbertura());
                }
                if (empresa.getUrlContratoSocial() != null) {
                    existingEmpresa.setUrlContratoSocial(empresa.getUrlContratoSocial());
                }
                if (empresa.getCapitalSocial() != null) {
                    existingEmpresa.setCapitalSocial(empresa.getCapitalSocial());
                }

                return existingEmpresa;
            })
            .map(empresaRepository::save);
    }

    /**
     * Get all the empresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Empresa> findAllWithEagerRelationships(Pageable pageable) {
        return empresaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one empresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Empresa> findOne(Long id) {
        log.debug("Request to get Empresa : {}", id);
        return empresaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the empresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Empresa : {}", id);
        empresaRepository.deleteById(id);
    }
}
