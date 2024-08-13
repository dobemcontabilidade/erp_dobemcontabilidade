package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.GrupoAcessoEmpresaUsuarioContador;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GrupoAcessoEmpresaUsuarioContador entity.
 */
@Repository
public interface GrupoAcessoEmpresaUsuarioContadorRepository extends JpaRepository<GrupoAcessoEmpresaUsuarioContador, Long> {
    default Optional<GrupoAcessoEmpresaUsuarioContador> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<GrupoAcessoEmpresaUsuarioContador> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<GrupoAcessoEmpresaUsuarioContador> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select grupoAcessoEmpresaUsuarioContador from GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresaUsuarioContador left join fetch grupoAcessoEmpresaUsuarioContador.usuarioContador left join fetch grupoAcessoEmpresaUsuarioContador.permisao left join fetch grupoAcessoEmpresaUsuarioContador.grupoAcessoEmpresa",
        countQuery = "select count(grupoAcessoEmpresaUsuarioContador) from GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresaUsuarioContador"
    )
    Page<GrupoAcessoEmpresaUsuarioContador> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select grupoAcessoEmpresaUsuarioContador from GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresaUsuarioContador left join fetch grupoAcessoEmpresaUsuarioContador.usuarioContador left join fetch grupoAcessoEmpresaUsuarioContador.permisao left join fetch grupoAcessoEmpresaUsuarioContador.grupoAcessoEmpresa"
    )
    List<GrupoAcessoEmpresaUsuarioContador> findAllWithToOneRelationships();

    @Query(
        "select grupoAcessoEmpresaUsuarioContador from GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresaUsuarioContador left join fetch grupoAcessoEmpresaUsuarioContador.usuarioContador left join fetch grupoAcessoEmpresaUsuarioContador.permisao left join fetch grupoAcessoEmpresaUsuarioContador.grupoAcessoEmpresa where grupoAcessoEmpresaUsuarioContador.id =:id"
    )
    Optional<GrupoAcessoEmpresaUsuarioContador> findOneWithToOneRelationships(@Param("id") Long id);
}
