package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.domain.Enquadramento;
import com.dobemcontabilidade.domain.Ramo;
import com.dobemcontabilidade.domain.Tributacao;
import com.dobemcontabilidade.service.dto.EmpresaDTO;
import com.dobemcontabilidade.service.dto.EnquadramentoDTO;
import com.dobemcontabilidade.service.dto.RamoDTO;
import com.dobemcontabilidade.service.dto.TributacaoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Empresa} and its DTO {@link EmpresaDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmpresaMapper extends EntityMapper<EmpresaDTO, Empresa> {
    @Mapping(target = "ramo", source = "ramo", qualifiedByName = "ramoNome")
    @Mapping(target = "tributacao", source = "tributacao", qualifiedByName = "tributacaoNome")
    @Mapping(target = "enquadramento", source = "enquadramento", qualifiedByName = "enquadramentoNome")
    EmpresaDTO toDto(Empresa s);

    @Named("ramoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    RamoDTO toDtoRamoNome(Ramo ramo);

    @Named("tributacaoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    TributacaoDTO toDtoTributacaoNome(Tributacao tributacao);

    @Named("enquadramentoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    EnquadramentoDTO toDtoEnquadramentoNome(Enquadramento enquadramento);
}
