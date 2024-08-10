package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.DocumentoTarefa;
import com.dobemcontabilidade.repository.DocumentoTarefaRepository;
import com.dobemcontabilidade.service.dto.DocumentoTarefaDTO;
import com.dobemcontabilidade.service.mapper.DocumentoTarefaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.DocumentoTarefa}.
 */
@Service
@Transactional
public class DocumentoTarefaService {

    private static final Logger log = LoggerFactory.getLogger(DocumentoTarefaService.class);

    private final DocumentoTarefaRepository documentoTarefaRepository;

    private final DocumentoTarefaMapper documentoTarefaMapper;

    public DocumentoTarefaService(DocumentoTarefaRepository documentoTarefaRepository, DocumentoTarefaMapper documentoTarefaMapper) {
        this.documentoTarefaRepository = documentoTarefaRepository;
        this.documentoTarefaMapper = documentoTarefaMapper;
    }

    /**
     * Save a documentoTarefa.
     *
     * @param documentoTarefaDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentoTarefaDTO save(DocumentoTarefaDTO documentoTarefaDTO) {
        log.debug("Request to save DocumentoTarefa : {}", documentoTarefaDTO);
        DocumentoTarefa documentoTarefa = documentoTarefaMapper.toEntity(documentoTarefaDTO);
        documentoTarefa = documentoTarefaRepository.save(documentoTarefa);
        return documentoTarefaMapper.toDto(documentoTarefa);
    }

    /**
     * Update a documentoTarefa.
     *
     * @param documentoTarefaDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentoTarefaDTO update(DocumentoTarefaDTO documentoTarefaDTO) {
        log.debug("Request to update DocumentoTarefa : {}", documentoTarefaDTO);
        DocumentoTarefa documentoTarefa = documentoTarefaMapper.toEntity(documentoTarefaDTO);
        documentoTarefa = documentoTarefaRepository.save(documentoTarefa);
        return documentoTarefaMapper.toDto(documentoTarefa);
    }

    /**
     * Partially update a documentoTarefa.
     *
     * @param documentoTarefaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DocumentoTarefaDTO> partialUpdate(DocumentoTarefaDTO documentoTarefaDTO) {
        log.debug("Request to partially update DocumentoTarefa : {}", documentoTarefaDTO);

        return documentoTarefaRepository
            .findById(documentoTarefaDTO.getId())
            .map(existingDocumentoTarefa -> {
                documentoTarefaMapper.partialUpdate(existingDocumentoTarefa, documentoTarefaDTO);

                return existingDocumentoTarefa;
            })
            .map(documentoTarefaRepository::save)
            .map(documentoTarefaMapper::toDto);
    }

    /**
     * Get all the documentoTarefas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DocumentoTarefaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return documentoTarefaRepository.findAllWithEagerRelationships(pageable).map(documentoTarefaMapper::toDto);
    }

    /**
     * Get one documentoTarefa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocumentoTarefaDTO> findOne(Long id) {
        log.debug("Request to get DocumentoTarefa : {}", id);
        return documentoTarefaRepository.findOneWithEagerRelationships(id).map(documentoTarefaMapper::toDto);
    }

    /**
     * Delete the documentoTarefa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DocumentoTarefa : {}", id);
        documentoTarefaRepository.deleteById(id);
    }
}
