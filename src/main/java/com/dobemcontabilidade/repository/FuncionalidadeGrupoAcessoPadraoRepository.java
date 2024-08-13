package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.FuncionalidadeGrupoAcessoPadrao;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FuncionalidadeGrupoAcessoPadrao entity.
 */
@Repository
public interface FuncionalidadeGrupoAcessoPadraoRepository extends JpaRepository<FuncionalidadeGrupoAcessoPadrao, Long> {
    default Optional<FuncionalidadeGrupoAcessoPadrao> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<FuncionalidadeGrupoAcessoPadrao> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<FuncionalidadeGrupoAcessoPadrao> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select funcionalidadeGrupoAcessoPadrao from FuncionalidadeGrupoAcessoPadrao funcionalidadeGrupoAcessoPadrao left join fetch funcionalidadeGrupoAcessoPadrao.funcionalidade left join fetch funcionalidadeGrupoAcessoPadrao.grupoAcessoPadrao left join fetch funcionalidadeGrupoAcessoPadrao.permisao",
        countQuery = "select count(funcionalidadeGrupoAcessoPadrao) from FuncionalidadeGrupoAcessoPadrao funcionalidadeGrupoAcessoPadrao"
    )
    Page<FuncionalidadeGrupoAcessoPadrao> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select funcionalidadeGrupoAcessoPadrao from FuncionalidadeGrupoAcessoPadrao funcionalidadeGrupoAcessoPadrao left join fetch funcionalidadeGrupoAcessoPadrao.funcionalidade left join fetch funcionalidadeGrupoAcessoPadrao.grupoAcessoPadrao left join fetch funcionalidadeGrupoAcessoPadrao.permisao"
    )
    List<FuncionalidadeGrupoAcessoPadrao> findAllWithToOneRelationships();

    @Query(
        "select funcionalidadeGrupoAcessoPadrao from FuncionalidadeGrupoAcessoPadrao funcionalidadeGrupoAcessoPadrao left join fetch funcionalidadeGrupoAcessoPadrao.funcionalidade left join fetch funcionalidadeGrupoAcessoPadrao.grupoAcessoPadrao left join fetch funcionalidadeGrupoAcessoPadrao.permisao where funcionalidadeGrupoAcessoPadrao.id =:id"
    )
    Optional<FuncionalidadeGrupoAcessoPadrao> findOneWithToOneRelationships(@Param("id") Long id);
}
