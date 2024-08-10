package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.AssinaturaEmpresa;
import com.dobemcontabilidade.domain.CalculoPlanoAssinatura;
import com.dobemcontabilidade.domain.DescontoPlanoContaAzul;
import com.dobemcontabilidade.domain.DescontoPlanoContabil;
import com.dobemcontabilidade.domain.PeriodoPagamento;
import com.dobemcontabilidade.domain.PlanoContaAzul;
import com.dobemcontabilidade.domain.PlanoContabil;
import com.dobemcontabilidade.domain.Ramo;
import com.dobemcontabilidade.domain.Tributacao;
import com.dobemcontabilidade.service.dto.AssinaturaEmpresaDTO;
import com.dobemcontabilidade.service.dto.CalculoPlanoAssinaturaDTO;
import com.dobemcontabilidade.service.dto.DescontoPlanoContaAzulDTO;
import com.dobemcontabilidade.service.dto.DescontoPlanoContabilDTO;
import com.dobemcontabilidade.service.dto.PeriodoPagamentoDTO;
import com.dobemcontabilidade.service.dto.PlanoContaAzulDTO;
import com.dobemcontabilidade.service.dto.PlanoContabilDTO;
import com.dobemcontabilidade.service.dto.RamoDTO;
import com.dobemcontabilidade.service.dto.TributacaoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CalculoPlanoAssinatura} and its DTO {@link CalculoPlanoAssinaturaDTO}.
 */
@Mapper(componentModel = "spring")
public interface CalculoPlanoAssinaturaMapper extends EntityMapper<CalculoPlanoAssinaturaDTO, CalculoPlanoAssinatura> {
    @Mapping(target = "periodoPagamento", source = "periodoPagamento", qualifiedByName = "periodoPagamentoPeriodo")
    @Mapping(target = "planoContabil", source = "planoContabil", qualifiedByName = "planoContabilNome")
    @Mapping(target = "ramo", source = "ramo", qualifiedByName = "ramoNome")
    @Mapping(target = "tributacao", source = "tributacao", qualifiedByName = "tributacaoNome")
    @Mapping(target = "descontoPlanoContabil", source = "descontoPlanoContabil", qualifiedByName = "descontoPlanoContabilPercentual")
    @Mapping(target = "assinaturaEmpresa", source = "assinaturaEmpresa", qualifiedByName = "assinaturaEmpresaCodigoAssinatura")
    @Mapping(target = "descontoPlanoContaAzul", source = "descontoPlanoContaAzul", qualifiedByName = "descontoPlanoContaAzulId")
    @Mapping(target = "planoContaAzul", source = "planoContaAzul", qualifiedByName = "planoContaAzulId")
    CalculoPlanoAssinaturaDTO toDto(CalculoPlanoAssinatura s);

    @Named("periodoPagamentoPeriodo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "periodo", source = "periodo")
    PeriodoPagamentoDTO toDtoPeriodoPagamentoPeriodo(PeriodoPagamento periodoPagamento);

    @Named("planoContabilNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    PlanoContabilDTO toDtoPlanoContabilNome(PlanoContabil planoContabil);

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

    @Named("descontoPlanoContabilPercentual")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "percentual", source = "percentual")
    DescontoPlanoContabilDTO toDtoDescontoPlanoContabilPercentual(DescontoPlanoContabil descontoPlanoContabil);

    @Named("assinaturaEmpresaCodigoAssinatura")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "codigoAssinatura", source = "codigoAssinatura")
    AssinaturaEmpresaDTO toDtoAssinaturaEmpresaCodigoAssinatura(AssinaturaEmpresa assinaturaEmpresa);

    @Named("descontoPlanoContaAzulId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DescontoPlanoContaAzulDTO toDtoDescontoPlanoContaAzulId(DescontoPlanoContaAzul descontoPlanoContaAzul);

    @Named("planoContaAzulId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PlanoContaAzulDTO toDtoPlanoContaAzulId(PlanoContaAzul planoContaAzul);
}
