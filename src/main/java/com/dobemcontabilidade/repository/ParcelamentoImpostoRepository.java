package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.ParcelamentoImposto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ParcelamentoImposto entity.
 */
@Repository
public interface ParcelamentoImpostoRepository extends JpaRepository<ParcelamentoImposto, Long> {
    default Optional<ParcelamentoImposto> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ParcelamentoImposto> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ParcelamentoImposto> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select parcelamentoImposto from ParcelamentoImposto parcelamentoImposto left join fetch parcelamentoImposto.imposto left join fetch parcelamentoImposto.empresa",
        countQuery = "select count(parcelamentoImposto) from ParcelamentoImposto parcelamentoImposto"
    )
    Page<ParcelamentoImposto> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select parcelamentoImposto from ParcelamentoImposto parcelamentoImposto left join fetch parcelamentoImposto.imposto left join fetch parcelamentoImposto.empresa"
    )
    List<ParcelamentoImposto> findAllWithToOneRelationships();

    @Query(
        "select parcelamentoImposto from ParcelamentoImposto parcelamentoImposto left join fetch parcelamentoImposto.imposto left join fetch parcelamentoImposto.empresa where parcelamentoImposto.id =:id"
    )
    Optional<ParcelamentoImposto> findOneWithToOneRelationships(@Param("id") Long id);
}
