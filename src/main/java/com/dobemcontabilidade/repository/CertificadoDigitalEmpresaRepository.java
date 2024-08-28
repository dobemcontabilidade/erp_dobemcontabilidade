package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.CertificadoDigitalEmpresa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CertificadoDigitalEmpresa entity.
 */
@Repository
public interface CertificadoDigitalEmpresaRepository extends JpaRepository<CertificadoDigitalEmpresa, Long> {
    default Optional<CertificadoDigitalEmpresa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<CertificadoDigitalEmpresa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<CertificadoDigitalEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select certificadoDigitalEmpresa from CertificadoDigitalEmpresa certificadoDigitalEmpresa left join fetch certificadoDigitalEmpresa.pessoaJuridica left join fetch certificadoDigitalEmpresa.certificadoDigital left join fetch certificadoDigitalEmpresa.fornecedorCertificado",
        countQuery = "select count(certificadoDigitalEmpresa) from CertificadoDigitalEmpresa certificadoDigitalEmpresa"
    )
    Page<CertificadoDigitalEmpresa> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select certificadoDigitalEmpresa from CertificadoDigitalEmpresa certificadoDigitalEmpresa left join fetch certificadoDigitalEmpresa.pessoaJuridica left join fetch certificadoDigitalEmpresa.certificadoDigital left join fetch certificadoDigitalEmpresa.fornecedorCertificado"
    )
    List<CertificadoDigitalEmpresa> findAllWithToOneRelationships();

    @Query(
        "select certificadoDigitalEmpresa from CertificadoDigitalEmpresa certificadoDigitalEmpresa left join fetch certificadoDigitalEmpresa.pessoaJuridica left join fetch certificadoDigitalEmpresa.certificadoDigital left join fetch certificadoDigitalEmpresa.fornecedorCertificado where certificadoDigitalEmpresa.id =:id"
    )
    Optional<CertificadoDigitalEmpresa> findOneWithToOneRelationships(@Param("id") Long id);
}
