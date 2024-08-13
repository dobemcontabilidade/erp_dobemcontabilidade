package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.ContadorResponsavelTarefaRecorrente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ContadorResponsavelTarefaRecorrente entity.
 */
@Repository
public interface ContadorResponsavelTarefaRecorrenteRepository extends JpaRepository<ContadorResponsavelTarefaRecorrente, Long> {
    default Optional<ContadorResponsavelTarefaRecorrente> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ContadorResponsavelTarefaRecorrente> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ContadorResponsavelTarefaRecorrente> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select contadorResponsavelTarefaRecorrente from ContadorResponsavelTarefaRecorrente contadorResponsavelTarefaRecorrente left join fetch contadorResponsavelTarefaRecorrente.tarefaRecorrenteExecucao left join fetch contadorResponsavelTarefaRecorrente.contador",
        countQuery = "select count(contadorResponsavelTarefaRecorrente) from ContadorResponsavelTarefaRecorrente contadorResponsavelTarefaRecorrente"
    )
    Page<ContadorResponsavelTarefaRecorrente> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select contadorResponsavelTarefaRecorrente from ContadorResponsavelTarefaRecorrente contadorResponsavelTarefaRecorrente left join fetch contadorResponsavelTarefaRecorrente.tarefaRecorrenteExecucao left join fetch contadorResponsavelTarefaRecorrente.contador"
    )
    List<ContadorResponsavelTarefaRecorrente> findAllWithToOneRelationships();

    @Query(
        "select contadorResponsavelTarefaRecorrente from ContadorResponsavelTarefaRecorrente contadorResponsavelTarefaRecorrente left join fetch contadorResponsavelTarefaRecorrente.tarefaRecorrenteExecucao left join fetch contadorResponsavelTarefaRecorrente.contador where contadorResponsavelTarefaRecorrente.id =:id"
    )
    Optional<ContadorResponsavelTarefaRecorrente> findOneWithToOneRelationships(@Param("id") Long id);
}
