package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.ImpostoEmpresa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ImpostoEmpresa entity.
 */
@Repository
public interface ImpostoEmpresaRepository extends JpaRepository<ImpostoEmpresa, Long> {
    default Optional<ImpostoEmpresa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ImpostoEmpresa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ImpostoEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select impostoEmpresa from ImpostoEmpresa impostoEmpresa left join fetch impostoEmpresa.empresa left join fetch impostoEmpresa.imposto",
        countQuery = "select count(impostoEmpresa) from ImpostoEmpresa impostoEmpresa"
    )
    Page<ImpostoEmpresa> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select impostoEmpresa from ImpostoEmpresa impostoEmpresa left join fetch impostoEmpresa.empresa left join fetch impostoEmpresa.imposto"
    )
    List<ImpostoEmpresa> findAllWithToOneRelationships();

    @Query(
        "select impostoEmpresa from ImpostoEmpresa impostoEmpresa left join fetch impostoEmpresa.empresa left join fetch impostoEmpresa.imposto where impostoEmpresa.id =:id"
    )
    Optional<ImpostoEmpresa> findOneWithToOneRelationships(@Param("id") Long id);
}
