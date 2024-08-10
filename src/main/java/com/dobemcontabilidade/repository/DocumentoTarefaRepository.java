package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.DocumentoTarefa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DocumentoTarefa entity.
 */
@Repository
public interface DocumentoTarefaRepository extends JpaRepository<DocumentoTarefa, Long>, JpaSpecificationExecutor<DocumentoTarefa> {
    default Optional<DocumentoTarefa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DocumentoTarefa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DocumentoTarefa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select documentoTarefa from DocumentoTarefa documentoTarefa left join fetch documentoTarefa.tarefa",
        countQuery = "select count(documentoTarefa) from DocumentoTarefa documentoTarefa"
    )
    Page<DocumentoTarefa> findAllWithToOneRelationships(Pageable pageable);

    @Query("select documentoTarefa from DocumentoTarefa documentoTarefa left join fetch documentoTarefa.tarefa")
    List<DocumentoTarefa> findAllWithToOneRelationships();

    @Query(
        "select documentoTarefa from DocumentoTarefa documentoTarefa left join fetch documentoTarefa.tarefa where documentoTarefa.id =:id"
    )
    Optional<DocumentoTarefa> findOneWithToOneRelationships(@Param("id") Long id);
}
