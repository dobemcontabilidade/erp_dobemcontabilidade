package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.FormaDePagamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FormaDePagamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormaDePagamentoRepository extends JpaRepository<FormaDePagamento, Long> {}
