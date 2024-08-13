package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Profissao;
import com.dobemcontabilidade.repository.ProfissaoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Profissao}.
 */
@Service
@Transactional
public class ProfissaoService {

    private static final Logger log = LoggerFactory.getLogger(ProfissaoService.class);

    private final ProfissaoRepository profissaoRepository;

    public ProfissaoService(ProfissaoRepository profissaoRepository) {
        this.profissaoRepository = profissaoRepository;
    }

    /**
     * Save a profissao.
     *
     * @param profissao the entity to save.
     * @return the persisted entity.
     */
    public Profissao save(Profissao profissao) {
        log.debug("Request to save Profissao : {}", profissao);
        return profissaoRepository.save(profissao);
    }

    /**
     * Update a profissao.
     *
     * @param profissao the entity to save.
     * @return the persisted entity.
     */
    public Profissao update(Profissao profissao) {
        log.debug("Request to update Profissao : {}", profissao);
        return profissaoRepository.save(profissao);
    }

    /**
     * Partially update a profissao.
     *
     * @param profissao the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Profissao> partialUpdate(Profissao profissao) {
        log.debug("Request to partially update Profissao : {}", profissao);

        return profissaoRepository
            .findById(profissao.getId())
            .map(existingProfissao -> {
                if (profissao.getNome() != null) {
                    existingProfissao.setNome(profissao.getNome());
                }
                if (profissao.getCbo() != null) {
                    existingProfissao.setCbo(profissao.getCbo());
                }
                if (profissao.getCategoria() != null) {
                    existingProfissao.setCategoria(profissao.getCategoria());
                }
                if (profissao.getDescricao() != null) {
                    existingProfissao.setDescricao(profissao.getDescricao());
                }

                return existingProfissao;
            })
            .map(profissaoRepository::save);
    }

    /**
     * Get one profissao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Profissao> findOne(Long id) {
        log.debug("Request to get Profissao : {}", id);
        return profissaoRepository.findById(id);
    }

    /**
     * Delete the profissao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Profissao : {}", id);
        profissaoRepository.deleteById(id);
    }
}
