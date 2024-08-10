package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.PerfilContadorDepartamento;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PerfilContadorDepartamento entity.
 */
@Repository
public interface PerfilContadorDepartamentoRepository
    extends JpaRepository<PerfilContadorDepartamento, Long>, JpaSpecificationExecutor<PerfilContadorDepartamento> {
    default Optional<PerfilContadorDepartamento> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<PerfilContadorDepartamento> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<PerfilContadorDepartamento> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select perfilContadorDepartamento from PerfilContadorDepartamento perfilContadorDepartamento left join fetch perfilContadorDepartamento.departamento left join fetch perfilContadorDepartamento.perfilContador",
        countQuery = "select count(perfilContadorDepartamento) from PerfilContadorDepartamento perfilContadorDepartamento"
    )
    Page<PerfilContadorDepartamento> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select perfilContadorDepartamento from PerfilContadorDepartamento perfilContadorDepartamento left join fetch perfilContadorDepartamento.departamento left join fetch perfilContadorDepartamento.perfilContador"
    )
    List<PerfilContadorDepartamento> findAllWithToOneRelationships();

    @Query(
        "select perfilContadorDepartamento from PerfilContadorDepartamento perfilContadorDepartamento left join fetch perfilContadorDepartamento.departamento left join fetch perfilContadorDepartamento.perfilContador where perfilContadorDepartamento.id =:id"
    )
    Optional<PerfilContadorDepartamento> findOneWithToOneRelationships(@Param("id") Long id);
}
