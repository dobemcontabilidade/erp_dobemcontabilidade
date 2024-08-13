package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.DepartamentoFuncionario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DepartamentoFuncionario entity.
 */
@Repository
public interface DepartamentoFuncionarioRepository
    extends JpaRepository<DepartamentoFuncionario, Long>, JpaSpecificationExecutor<DepartamentoFuncionario> {
    default Optional<DepartamentoFuncionario> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DepartamentoFuncionario> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DepartamentoFuncionario> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select departamentoFuncionario from DepartamentoFuncionario departamentoFuncionario left join fetch departamentoFuncionario.departamento",
        countQuery = "select count(departamentoFuncionario) from DepartamentoFuncionario departamentoFuncionario"
    )
    Page<DepartamentoFuncionario> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select departamentoFuncionario from DepartamentoFuncionario departamentoFuncionario left join fetch departamentoFuncionario.departamento"
    )
    List<DepartamentoFuncionario> findAllWithToOneRelationships();

    @Query(
        "select departamentoFuncionario from DepartamentoFuncionario departamentoFuncionario left join fetch departamentoFuncionario.departamento where departamentoFuncionario.id =:id"
    )
    Optional<DepartamentoFuncionario> findOneWithToOneRelationships(@Param("id") Long id);
}
