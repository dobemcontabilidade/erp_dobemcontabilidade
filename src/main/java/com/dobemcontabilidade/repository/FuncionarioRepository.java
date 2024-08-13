package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Funcionario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Funcionario entity.
 */
@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>, JpaSpecificationExecutor<Funcionario> {
    default Optional<Funcionario> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Funcionario> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Funcionario> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select funcionario from Funcionario funcionario left join fetch funcionario.usuarioEmpresa left join fetch funcionario.pessoa left join fetch funcionario.empresa left join fetch funcionario.profissao",
        countQuery = "select count(funcionario) from Funcionario funcionario"
    )
    Page<Funcionario> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select funcionario from Funcionario funcionario left join fetch funcionario.usuarioEmpresa left join fetch funcionario.pessoa left join fetch funcionario.empresa left join fetch funcionario.profissao"
    )
    List<Funcionario> findAllWithToOneRelationships();

    @Query(
        "select funcionario from Funcionario funcionario left join fetch funcionario.usuarioEmpresa left join fetch funcionario.pessoa left join fetch funcionario.empresa left join fetch funcionario.profissao where funcionario.id =:id"
    )
    Optional<Funcionario> findOneWithToOneRelationships(@Param("id") Long id);
}
