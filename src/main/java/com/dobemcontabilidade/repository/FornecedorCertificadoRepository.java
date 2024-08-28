package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.FornecedorCertificado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FornecedorCertificado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FornecedorCertificadoRepository extends JpaRepository<FornecedorCertificado, Long> {}
