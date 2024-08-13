package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.AnexoEmpresa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AnexoEmpresa entity.
 */
@Repository
public interface AnexoEmpresaRepository extends JpaRepository<AnexoEmpresa, Long>, JpaSpecificationExecutor<AnexoEmpresa> {
    default Optional<AnexoEmpresa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AnexoEmpresa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AnexoEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select anexoEmpresa from AnexoEmpresa anexoEmpresa left join fetch anexoEmpresa.empresa",
        countQuery = "select count(anexoEmpresa) from AnexoEmpresa anexoEmpresa"
    )
    Page<AnexoEmpresa> findAllWithToOneRelationships(Pageable pageable);

    @Query("select anexoEmpresa from AnexoEmpresa anexoEmpresa left join fetch anexoEmpresa.empresa")
    List<AnexoEmpresa> findAllWithToOneRelationships();

    @Query("select anexoEmpresa from AnexoEmpresa anexoEmpresa left join fetch anexoEmpresa.empresa where anexoEmpresa.id =:id")
    Optional<AnexoEmpresa> findOneWithToOneRelationships(@Param("id") Long id);
}
