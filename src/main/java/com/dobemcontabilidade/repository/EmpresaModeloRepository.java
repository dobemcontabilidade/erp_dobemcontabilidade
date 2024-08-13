package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.EmpresaModelo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmpresaModelo entity.
 *
 * When extending this class, extend EmpresaModeloRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface EmpresaModeloRepository extends EmpresaModeloRepositoryWithBagRelationships, JpaRepository<EmpresaModelo, Long> {
    default Optional<EmpresaModelo> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<EmpresaModelo> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<EmpresaModelo> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select empresaModelo from EmpresaModelo empresaModelo left join fetch empresaModelo.ramo left join fetch empresaModelo.enquadramento left join fetch empresaModelo.tributacao left join fetch empresaModelo.cidade",
        countQuery = "select count(empresaModelo) from EmpresaModelo empresaModelo"
    )
    Page<EmpresaModelo> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select empresaModelo from EmpresaModelo empresaModelo left join fetch empresaModelo.ramo left join fetch empresaModelo.enquadramento left join fetch empresaModelo.tributacao left join fetch empresaModelo.cidade"
    )
    List<EmpresaModelo> findAllWithToOneRelationships();

    @Query(
        "select empresaModelo from EmpresaModelo empresaModelo left join fetch empresaModelo.ramo left join fetch empresaModelo.enquadramento left join fetch empresaModelo.tributacao left join fetch empresaModelo.cidade where empresaModelo.id =:id"
    )
    Optional<EmpresaModelo> findOneWithToOneRelationships(@Param("id") Long id);
}
