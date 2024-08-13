package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.AnexoRequeridoEmpresa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AnexoRequeridoEmpresa entity.
 */
@Repository
public interface AnexoRequeridoEmpresaRepository extends JpaRepository<AnexoRequeridoEmpresa, Long> {
    default Optional<AnexoRequeridoEmpresa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AnexoRequeridoEmpresa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AnexoRequeridoEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select anexoRequeridoEmpresa from AnexoRequeridoEmpresa anexoRequeridoEmpresa left join fetch anexoRequeridoEmpresa.anexoRequerido left join fetch anexoRequeridoEmpresa.enquadramento left join fetch anexoRequeridoEmpresa.tributacao left join fetch anexoRequeridoEmpresa.ramo left join fetch anexoRequeridoEmpresa.empresa left join fetch anexoRequeridoEmpresa.empresaModelo",
        countQuery = "select count(anexoRequeridoEmpresa) from AnexoRequeridoEmpresa anexoRequeridoEmpresa"
    )
    Page<AnexoRequeridoEmpresa> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select anexoRequeridoEmpresa from AnexoRequeridoEmpresa anexoRequeridoEmpresa left join fetch anexoRequeridoEmpresa.anexoRequerido left join fetch anexoRequeridoEmpresa.enquadramento left join fetch anexoRequeridoEmpresa.tributacao left join fetch anexoRequeridoEmpresa.ramo left join fetch anexoRequeridoEmpresa.empresa left join fetch anexoRequeridoEmpresa.empresaModelo"
    )
    List<AnexoRequeridoEmpresa> findAllWithToOneRelationships();

    @Query(
        "select anexoRequeridoEmpresa from AnexoRequeridoEmpresa anexoRequeridoEmpresa left join fetch anexoRequeridoEmpresa.anexoRequerido left join fetch anexoRequeridoEmpresa.enquadramento left join fetch anexoRequeridoEmpresa.tributacao left join fetch anexoRequeridoEmpresa.ramo left join fetch anexoRequeridoEmpresa.empresa left join fetch anexoRequeridoEmpresa.empresaModelo where anexoRequeridoEmpresa.id =:id"
    )
    Optional<AnexoRequeridoEmpresa> findOneWithToOneRelationships(@Param("id") Long id);
}
