package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.DepartamentoEmpresa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DepartamentoEmpresa entity.
 */
@Repository
public interface DepartamentoEmpresaRepository
    extends JpaRepository<DepartamentoEmpresa, Long>, JpaSpecificationExecutor<DepartamentoEmpresa> {
    default Optional<DepartamentoEmpresa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DepartamentoEmpresa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DepartamentoEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select departamentoEmpresa from DepartamentoEmpresa departamentoEmpresa left join fetch departamentoEmpresa.departamento left join fetch departamentoEmpresa.empresa left join fetch departamentoEmpresa.contador",
        countQuery = "select count(departamentoEmpresa) from DepartamentoEmpresa departamentoEmpresa"
    )
    Page<DepartamentoEmpresa> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select departamentoEmpresa from DepartamentoEmpresa departamentoEmpresa left join fetch departamentoEmpresa.departamento left join fetch departamentoEmpresa.empresa left join fetch departamentoEmpresa.contador"
    )
    List<DepartamentoEmpresa> findAllWithToOneRelationships();

    @Query(
        "select departamentoEmpresa from DepartamentoEmpresa departamentoEmpresa left join fetch departamentoEmpresa.departamento left join fetch departamentoEmpresa.empresa left join fetch departamentoEmpresa.contador where departamentoEmpresa.id =:id"
    )
    Optional<DepartamentoEmpresa> findOneWithToOneRelationships(@Param("id") Long id);
}
