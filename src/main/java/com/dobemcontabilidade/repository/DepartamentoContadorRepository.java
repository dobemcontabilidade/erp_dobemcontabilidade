package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.DepartamentoContador;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DepartamentoContador entity.
 */
@Repository
public interface DepartamentoContadorRepository
    extends JpaRepository<DepartamentoContador, Long>, JpaSpecificationExecutor<DepartamentoContador> {
    default Optional<DepartamentoContador> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DepartamentoContador> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DepartamentoContador> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select departamentoContador from DepartamentoContador departamentoContador left join fetch departamentoContador.departamento left join fetch departamentoContador.contador",
        countQuery = "select count(departamentoContador) from DepartamentoContador departamentoContador"
    )
    Page<DepartamentoContador> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select departamentoContador from DepartamentoContador departamentoContador left join fetch departamentoContador.departamento left join fetch departamentoContador.contador"
    )
    List<DepartamentoContador> findAllWithToOneRelationships();

    @Query(
        "select departamentoContador from DepartamentoContador departamentoContador left join fetch departamentoContador.departamento left join fetch departamentoContador.contador where departamentoContador.id =:id"
    )
    Optional<DepartamentoContador> findOneWithToOneRelationships(@Param("id") Long id);
}
