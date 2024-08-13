package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.ContratoFuncionario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ContratoFuncionario entity.
 */
@Repository
public interface ContratoFuncionarioRepository extends JpaRepository<ContratoFuncionario, Long> {
    default Optional<ContratoFuncionario> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ContratoFuncionario> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ContratoFuncionario> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select contratoFuncionario from ContratoFuncionario contratoFuncionario left join fetch contratoFuncionario.agenteIntegracaoEstagio left join fetch contratoFuncionario.instituicaoEnsino",
        countQuery = "select count(contratoFuncionario) from ContratoFuncionario contratoFuncionario"
    )
    Page<ContratoFuncionario> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select contratoFuncionario from ContratoFuncionario contratoFuncionario left join fetch contratoFuncionario.agenteIntegracaoEstagio left join fetch contratoFuncionario.instituicaoEnsino"
    )
    List<ContratoFuncionario> findAllWithToOneRelationships();

    @Query(
        "select contratoFuncionario from ContratoFuncionario contratoFuncionario left join fetch contratoFuncionario.agenteIntegracaoEstagio left join fetch contratoFuncionario.instituicaoEnsino where contratoFuncionario.id =:id"
    )
    Optional<ContratoFuncionario> findOneWithToOneRelationships(@Param("id") Long id);
}
