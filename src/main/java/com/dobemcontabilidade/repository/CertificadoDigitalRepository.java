package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.CertificadoDigital;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CertificadoDigital entity.
 */
@Repository
public interface CertificadoDigitalRepository
    extends JpaRepository<CertificadoDigital, Long>, JpaSpecificationExecutor<CertificadoDigital> {
    default Optional<CertificadoDigital> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<CertificadoDigital> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<CertificadoDigital> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select certificadoDigital from CertificadoDigital certificadoDigital left join fetch certificadoDigital.empresa",
        countQuery = "select count(certificadoDigital) from CertificadoDigital certificadoDigital"
    )
    Page<CertificadoDigital> findAllWithToOneRelationships(Pageable pageable);

    @Query("select certificadoDigital from CertificadoDigital certificadoDigital left join fetch certificadoDigital.empresa")
    List<CertificadoDigital> findAllWithToOneRelationships();

    @Query(
        "select certificadoDigital from CertificadoDigital certificadoDigital left join fetch certificadoDigital.empresa where certificadoDigital.id =:id"
    )
    Optional<CertificadoDigital> findOneWithToOneRelationships(@Param("id") Long id);
}
