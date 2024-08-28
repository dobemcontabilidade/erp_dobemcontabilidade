package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.DocsEmpresa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DocsEmpresa entity.
 */
@Repository
public interface DocsEmpresaRepository extends JpaRepository<DocsEmpresa, Long> {
    default Optional<DocsEmpresa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DocsEmpresa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DocsEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select docsEmpresa from DocsEmpresa docsEmpresa left join fetch docsEmpresa.pessoaJuridica",
        countQuery = "select count(docsEmpresa) from DocsEmpresa docsEmpresa"
    )
    Page<DocsEmpresa> findAllWithToOneRelationships(Pageable pageable);

    @Query("select docsEmpresa from DocsEmpresa docsEmpresa left join fetch docsEmpresa.pessoaJuridica")
    List<DocsEmpresa> findAllWithToOneRelationships();

    @Query("select docsEmpresa from DocsEmpresa docsEmpresa left join fetch docsEmpresa.pessoaJuridica where docsEmpresa.id =:id")
    Optional<DocsEmpresa> findOneWithToOneRelationships(@Param("id") Long id);
}
