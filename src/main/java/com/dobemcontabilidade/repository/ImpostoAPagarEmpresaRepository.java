package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.ImpostoAPagarEmpresa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ImpostoAPagarEmpresa entity.
 */
@Repository
public interface ImpostoAPagarEmpresaRepository extends JpaRepository<ImpostoAPagarEmpresa, Long> {
    default Optional<ImpostoAPagarEmpresa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ImpostoAPagarEmpresa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ImpostoAPagarEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select impostoAPagarEmpresa from ImpostoAPagarEmpresa impostoAPagarEmpresa left join fetch impostoAPagarEmpresa.imposto",
        countQuery = "select count(impostoAPagarEmpresa) from ImpostoAPagarEmpresa impostoAPagarEmpresa"
    )
    Page<ImpostoAPagarEmpresa> findAllWithToOneRelationships(Pageable pageable);

    @Query("select impostoAPagarEmpresa from ImpostoAPagarEmpresa impostoAPagarEmpresa left join fetch impostoAPagarEmpresa.imposto")
    List<ImpostoAPagarEmpresa> findAllWithToOneRelationships();

    @Query(
        "select impostoAPagarEmpresa from ImpostoAPagarEmpresa impostoAPagarEmpresa left join fetch impostoAPagarEmpresa.imposto where impostoAPagarEmpresa.id =:id"
    )
    Optional<ImpostoAPagarEmpresa> findOneWithToOneRelationships(@Param("id") Long id);
}
