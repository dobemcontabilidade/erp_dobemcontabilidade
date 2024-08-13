package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.EmpresaModelo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface EmpresaModeloRepositoryWithBagRelationships {
    Optional<EmpresaModelo> fetchBagRelationships(Optional<EmpresaModelo> empresaModelo);

    List<EmpresaModelo> fetchBagRelationships(List<EmpresaModelo> empresaModelos);

    Page<EmpresaModelo> fetchBagRelationships(Page<EmpresaModelo> empresaModelos);
}
