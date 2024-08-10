package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.BancoContador;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BancoContador entity.
 */
@Repository
public interface BancoContadorRepository extends JpaRepository<BancoContador, Long> {
    default Optional<BancoContador> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<BancoContador> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<BancoContador> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select bancoContador from BancoContador bancoContador left join fetch bancoContador.contador left join fetch bancoContador.banco",
        countQuery = "select count(bancoContador) from BancoContador bancoContador"
    )
    Page<BancoContador> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select bancoContador from BancoContador bancoContador left join fetch bancoContador.contador left join fetch bancoContador.banco"
    )
    List<BancoContador> findAllWithToOneRelationships();

    @Query(
        "select bancoContador from BancoContador bancoContador left join fetch bancoContador.contador left join fetch bancoContador.banco where bancoContador.id =:id"
    )
    Optional<BancoContador> findOneWithToOneRelationships(@Param("id") Long id);
}
