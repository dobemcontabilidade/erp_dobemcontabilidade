package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.CertificadoDigital;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.service.dto.CertificadoDigitalDTO;
import com.dobemcontabilidade.service.dto.EmpresaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CertificadoDigital} and its DTO {@link CertificadoDigitalDTO}.
 */
@Mapper(componentModel = "spring")
public interface CertificadoDigitalMapper extends EntityMapper<CertificadoDigitalDTO, CertificadoDigital> {
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "empresaRazaoSocial")
    CertificadoDigitalDTO toDto(CertificadoDigital s);

    @Named("empresaRazaoSocial")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "razaoSocial", source = "razaoSocial")
    EmpresaDTO toDtoEmpresaRazaoSocial(Empresa empresa);
}
