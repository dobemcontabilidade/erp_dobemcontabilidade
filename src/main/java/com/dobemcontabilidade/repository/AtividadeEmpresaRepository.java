package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.AtividadeEmpresa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AtividadeEmpresa entity.
 */
@Repository
public interface AtividadeEmpresaRepository extends JpaRepository<AtividadeEmpresa, Long> {
    default Optional<AtividadeEmpresa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AtividadeEmpresa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AtividadeEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select atividadeEmpresa from AtividadeEmpresa atividadeEmpresa left join fetch atividadeEmpresa.empresa",
        countQuery = "select count(atividadeEmpresa) from AtividadeEmpresa atividadeEmpresa"
    )
    Page<AtividadeEmpresa> findAllWithToOneRelationships(Pageable pageable);

    @Query("select atividadeEmpresa from AtividadeEmpresa atividadeEmpresa left join fetch atividadeEmpresa.empresa")
    List<AtividadeEmpresa> findAllWithToOneRelationships();

    @Query(
        "select atividadeEmpresa from AtividadeEmpresa atividadeEmpresa left join fetch atividadeEmpresa.empresa where atividadeEmpresa.id =:id"
    )
    Optional<AtividadeEmpresa> findOneWithToOneRelationships(@Param("id") Long id);
}
