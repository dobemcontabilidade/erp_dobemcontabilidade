package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.AssinaturaEmpresa;
import com.dobemcontabilidade.domain.Pagamento;
import com.dobemcontabilidade.service.dto.AssinaturaEmpresaDTO;
import com.dobemcontabilidade.service.dto.PagamentoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pagamento} and its DTO {@link PagamentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PagamentoMapper extends EntityMapper<PagamentoDTO, Pagamento> {
    @Mapping(target = "assinaturaEmpresa", source = "assinaturaEmpresa", qualifiedByName = "assinaturaEmpresaCodigoAssinatura")
    PagamentoDTO toDto(Pagamento s);

    @Named("assinaturaEmpresaCodigoAssinatura")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "codigoAssinatura", source = "codigoAssinatura")
    AssinaturaEmpresaDTO toDtoAssinaturaEmpresaCodigoAssinatura(AssinaturaEmpresa assinaturaEmpresa);
}
