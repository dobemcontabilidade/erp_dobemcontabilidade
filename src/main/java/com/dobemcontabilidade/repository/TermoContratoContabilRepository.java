package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.TermoContratoContabil;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TermoContratoContabil entity.
 */
@Repository
public interface TermoContratoContabilRepository
    extends JpaRepository<TermoContratoContabil, Long>, JpaSpecificationExecutor<TermoContratoContabil> {
    default Optional<TermoContratoContabil> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TermoContratoContabil> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TermoContratoContabil> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select termoContratoContabil from TermoContratoContabil termoContratoContabil left join fetch termoContratoContabil.planoContabil",
        countQuery = "select count(termoContratoContabil) from TermoContratoContabil termoContratoContabil"
    )
    Page<TermoContratoContabil> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select termoContratoContabil from TermoContratoContabil termoContratoContabil left join fetch termoContratoContabil.planoContabil"
    )
    List<TermoContratoContabil> findAllWithToOneRelationships();

    @Query(
        "select termoContratoContabil from TermoContratoContabil termoContratoContabil left join fetch termoContratoContabil.planoContabil where termoContratoContabil.id =:id"
    )
    Optional<TermoContratoContabil> findOneWithToOneRelationships(@Param("id") Long id);
}
