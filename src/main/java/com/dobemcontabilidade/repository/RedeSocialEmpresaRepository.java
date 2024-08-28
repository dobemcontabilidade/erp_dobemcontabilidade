package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.RedeSocialEmpresa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RedeSocialEmpresa entity.
 */
@Repository
public interface RedeSocialEmpresaRepository extends JpaRepository<RedeSocialEmpresa, Long> {
    default Optional<RedeSocialEmpresa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<RedeSocialEmpresa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<RedeSocialEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select redeSocialEmpresa from RedeSocialEmpresa redeSocialEmpresa left join fetch redeSocialEmpresa.redeSocial left join fetch redeSocialEmpresa.pessoajuridica",
        countQuery = "select count(redeSocialEmpresa) from RedeSocialEmpresa redeSocialEmpresa"
    )
    Page<RedeSocialEmpresa> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select redeSocialEmpresa from RedeSocialEmpresa redeSocialEmpresa left join fetch redeSocialEmpresa.redeSocial left join fetch redeSocialEmpresa.pessoajuridica"
    )
    List<RedeSocialEmpresa> findAllWithToOneRelationships();

    @Query(
        "select redeSocialEmpresa from RedeSocialEmpresa redeSocialEmpresa left join fetch redeSocialEmpresa.redeSocial left join fetch redeSocialEmpresa.pessoajuridica where redeSocialEmpresa.id =:id"
    )
    Optional<RedeSocialEmpresa> findOneWithToOneRelationships(@Param("id") Long id);
}
