package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Pagamento;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Pagamento entity.
 */
@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long>, JpaSpecificationExecutor<Pagamento> {
    default Optional<Pagamento> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Pagamento> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Pagamento> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select pagamento from Pagamento pagamento left join fetch pagamento.assinaturaEmpresa",
        countQuery = "select count(pagamento) from Pagamento pagamento"
    )
    Page<Pagamento> findAllWithToOneRelationships(Pageable pageable);

    @Query("select pagamento from Pagamento pagamento left join fetch pagamento.assinaturaEmpresa")
    List<Pagamento> findAllWithToOneRelationships();

    @Query("select pagamento from Pagamento pagamento left join fetch pagamento.assinaturaEmpresa where pagamento.id =:id")
    Optional<Pagamento> findOneWithToOneRelationships(@Param("id") Long id);
}
