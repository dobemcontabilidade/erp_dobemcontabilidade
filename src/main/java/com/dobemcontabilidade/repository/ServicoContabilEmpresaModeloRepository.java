package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.ServicoContabilEmpresaModelo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ServicoContabilEmpresaModelo entity.
 */
@Repository
public interface ServicoContabilEmpresaModeloRepository extends JpaRepository<ServicoContabilEmpresaModelo, Long> {
    default Optional<ServicoContabilEmpresaModelo> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ServicoContabilEmpresaModelo> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ServicoContabilEmpresaModelo> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select servicoContabilEmpresaModelo from ServicoContabilEmpresaModelo servicoContabilEmpresaModelo left join fetch servicoContabilEmpresaModelo.empresaModelo left join fetch servicoContabilEmpresaModelo.servicoContabil",
        countQuery = "select count(servicoContabilEmpresaModelo) from ServicoContabilEmpresaModelo servicoContabilEmpresaModelo"
    )
    Page<ServicoContabilEmpresaModelo> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select servicoContabilEmpresaModelo from ServicoContabilEmpresaModelo servicoContabilEmpresaModelo left join fetch servicoContabilEmpresaModelo.empresaModelo left join fetch servicoContabilEmpresaModelo.servicoContabil"
    )
    List<ServicoContabilEmpresaModelo> findAllWithToOneRelationships();

    @Query(
        "select servicoContabilEmpresaModelo from ServicoContabilEmpresaModelo servicoContabilEmpresaModelo left join fetch servicoContabilEmpresaModelo.empresaModelo left join fetch servicoContabilEmpresaModelo.servicoContabil where servicoContabilEmpresaModelo.id =:id"
    )
    Optional<ServicoContabilEmpresaModelo> findOneWithToOneRelationships(@Param("id") Long id);
}
