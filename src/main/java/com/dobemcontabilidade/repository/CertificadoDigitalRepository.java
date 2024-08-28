package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.CertificadoDigital;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CertificadoDigital entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CertificadoDigitalRepository extends JpaRepository<CertificadoDigital, Long> {}
