package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.AnexoRequeridoPessoa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AnexoRequeridoPessoa entity.
 */
@Repository
public interface AnexoRequeridoPessoaRepository extends JpaRepository<AnexoRequeridoPessoa, Long> {
    default Optional<AnexoRequeridoPessoa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AnexoRequeridoPessoa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AnexoRequeridoPessoa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select anexoRequeridoPessoa from AnexoRequeridoPessoa anexoRequeridoPessoa left join fetch anexoRequeridoPessoa.anexoPessoa left join fetch anexoRequeridoPessoa.anexoRequerido",
        countQuery = "select count(anexoRequeridoPessoa) from AnexoRequeridoPessoa anexoRequeridoPessoa"
    )
    Page<AnexoRequeridoPessoa> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select anexoRequeridoPessoa from AnexoRequeridoPessoa anexoRequeridoPessoa left join fetch anexoRequeridoPessoa.anexoPessoa left join fetch anexoRequeridoPessoa.anexoRequerido"
    )
    List<AnexoRequeridoPessoa> findAllWithToOneRelationships();

    @Query(
        "select anexoRequeridoPessoa from AnexoRequeridoPessoa anexoRequeridoPessoa left join fetch anexoRequeridoPessoa.anexoPessoa left join fetch anexoRequeridoPessoa.anexoRequerido where anexoRequeridoPessoa.id =:id"
    )
    Optional<AnexoRequeridoPessoa> findOneWithToOneRelationships(@Param("id") Long id);
}
