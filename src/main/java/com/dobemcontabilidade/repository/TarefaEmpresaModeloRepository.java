package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.TarefaEmpresaModelo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TarefaEmpresaModelo entity.
 */
@Repository
public interface TarefaEmpresaModeloRepository extends JpaRepository<TarefaEmpresaModelo, Long> {
    default Optional<TarefaEmpresaModelo> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TarefaEmpresaModelo> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TarefaEmpresaModelo> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select tarefaEmpresaModelo from TarefaEmpresaModelo tarefaEmpresaModelo left join fetch tarefaEmpresaModelo.empresaModelo left join fetch tarefaEmpresaModelo.servicoContabil",
        countQuery = "select count(tarefaEmpresaModelo) from TarefaEmpresaModelo tarefaEmpresaModelo"
    )
    Page<TarefaEmpresaModelo> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select tarefaEmpresaModelo from TarefaEmpresaModelo tarefaEmpresaModelo left join fetch tarefaEmpresaModelo.empresaModelo left join fetch tarefaEmpresaModelo.servicoContabil"
    )
    List<TarefaEmpresaModelo> findAllWithToOneRelationships();

    @Query(
        "select tarefaEmpresaModelo from TarefaEmpresaModelo tarefaEmpresaModelo left join fetch tarefaEmpresaModelo.empresaModelo left join fetch tarefaEmpresaModelo.servicoContabil where tarefaEmpresaModelo.id =:id"
    )
    Optional<TarefaEmpresaModelo> findOneWithToOneRelationships(@Param("id") Long id);
}
