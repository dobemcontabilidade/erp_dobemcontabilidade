package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.domain.Departamento;
import com.dobemcontabilidade.domain.DepartamentoEmpresa;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.service.dto.ContadorDTO;
import com.dobemcontabilidade.service.dto.DepartamentoDTO;
import com.dobemcontabilidade.service.dto.DepartamentoEmpresaDTO;
import com.dobemcontabilidade.service.dto.EmpresaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DepartamentoEmpresa} and its DTO {@link DepartamentoEmpresaDTO}.
 */
@Mapper(componentModel = "spring")
public interface DepartamentoEmpresaMapper extends EntityMapper<DepartamentoEmpresaDTO, DepartamentoEmpresa> {
    @Mapping(target = "departamento", source = "departamento", qualifiedByName = "departamentoNome")
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "empresaRazaoSocial")
    @Mapping(target = "contador", source = "contador", qualifiedByName = "contadorNome")
    DepartamentoEmpresaDTO toDto(DepartamentoEmpresa s);

    @Named("departamentoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    DepartamentoDTO toDtoDepartamentoNome(Departamento departamento);

    @Named("empresaRazaoSocial")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "razaoSocial", source = "razaoSocial")
    EmpresaDTO toDtoEmpresaRazaoSocial(Empresa empresa);

    @Named("contadorNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    ContadorDTO toDtoContadorNome(Contador contador);
}
