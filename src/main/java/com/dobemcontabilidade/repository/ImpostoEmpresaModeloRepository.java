package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.ImpostoEmpresaModelo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ImpostoEmpresaModelo entity.
 */
@Repository
public interface ImpostoEmpresaModeloRepository extends JpaRepository<ImpostoEmpresaModelo, Long> {
    default Optional<ImpostoEmpresaModelo> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ImpostoEmpresaModelo> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ImpostoEmpresaModelo> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select impostoEmpresaModelo from ImpostoEmpresaModelo impostoEmpresaModelo left join fetch impostoEmpresaModelo.empresaModelo left join fetch impostoEmpresaModelo.imposto",
        countQuery = "select count(impostoEmpresaModelo) from ImpostoEmpresaModelo impostoEmpresaModelo"
    )
    Page<ImpostoEmpresaModelo> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select impostoEmpresaModelo from ImpostoEmpresaModelo impostoEmpresaModelo left join fetch impostoEmpresaModelo.empresaModelo left join fetch impostoEmpresaModelo.imposto"
    )
    List<ImpostoEmpresaModelo> findAllWithToOneRelationships();

    @Query(
        "select impostoEmpresaModelo from ImpostoEmpresaModelo impostoEmpresaModelo left join fetch impostoEmpresaModelo.empresaModelo left join fetch impostoEmpresaModelo.imposto where impostoEmpresaModelo.id =:id"
    )
    Optional<ImpostoEmpresaModelo> findOneWithToOneRelationships(@Param("id") Long id);
}
