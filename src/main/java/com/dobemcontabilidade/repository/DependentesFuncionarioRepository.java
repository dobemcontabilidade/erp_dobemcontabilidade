package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.DependentesFuncionario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DependentesFuncionario entity.
 */
@Repository
public interface DependentesFuncionarioRepository extends JpaRepository<DependentesFuncionario, Long> {
    default Optional<DependentesFuncionario> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DependentesFuncionario> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DependentesFuncionario> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select dependentesFuncionario from DependentesFuncionario dependentesFuncionario left join fetch dependentesFuncionario.pessoa",
        countQuery = "select count(dependentesFuncionario) from DependentesFuncionario dependentesFuncionario"
    )
    Page<DependentesFuncionario> findAllWithToOneRelationships(Pageable pageable);

    @Query("select dependentesFuncionario from DependentesFuncionario dependentesFuncionario left join fetch dependentesFuncionario.pessoa")
    List<DependentesFuncionario> findAllWithToOneRelationships();

    @Query(
        "select dependentesFuncionario from DependentesFuncionario dependentesFuncionario left join fetch dependentesFuncionario.pessoa where dependentesFuncionario.id =:id"
    )
    Optional<DependentesFuncionario> findOneWithToOneRelationships(@Param("id") Long id);
}
