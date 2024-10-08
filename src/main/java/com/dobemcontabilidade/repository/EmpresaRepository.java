package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Empresa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Empresa entity.
 */
@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long>, JpaSpecificationExecutor<Empresa> {
    default Optional<Empresa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Empresa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Empresa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select empresa from Empresa empresa left join fetch empresa.ramo left join fetch empresa.tributacao left join fetch empresa.enquadramento",
        countQuery = "select count(empresa) from Empresa empresa"
    )
    Page<Empresa> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select empresa from Empresa empresa left join fetch empresa.ramo left join fetch empresa.tributacao left join fetch empresa.enquadramento"
    )
    List<Empresa> findAllWithToOneRelationships();

    @Query(
        "select empresa from Empresa empresa left join fetch empresa.ramo left join fetch empresa.tributacao left join fetch empresa.enquadramento where empresa.id =:id"
    )
    Optional<Empresa> findOneWithToOneRelationships(@Param("id") Long id);
}
