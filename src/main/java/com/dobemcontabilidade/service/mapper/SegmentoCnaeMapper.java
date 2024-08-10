package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.domain.Ramo;
import com.dobemcontabilidade.domain.SegmentoCnae;
import com.dobemcontabilidade.domain.SubclasseCnae;
import com.dobemcontabilidade.service.dto.EmpresaDTO;
import com.dobemcontabilidade.service.dto.RamoDTO;
import com.dobemcontabilidade.service.dto.SegmentoCnaeDTO;
import com.dobemcontabilidade.service.dto.SubclasseCnaeDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SegmentoCnae} and its DTO {@link SegmentoCnaeDTO}.
 */
@Mapper(componentModel = "spring")
public interface SegmentoCnaeMapper extends EntityMapper<SegmentoCnaeDTO, SegmentoCnae> {
    @Mapping(target = "subclasseCnaes", source = "subclasseCnaes", qualifiedByName = "subclasseCnaeIdSet")
    @Mapping(target = "ramo", source = "ramo", qualifiedByName = "ramoNome")
    @Mapping(target = "empresas", source = "empresas", qualifiedByName = "empresaIdSet")
    SegmentoCnaeDTO toDto(SegmentoCnae s);

    @Mapping(target = "removeSubclasseCnae", ignore = true)
    @Mapping(target = "empresas", ignore = true)
    @Mapping(target = "removeEmpresa", ignore = true)
    SegmentoCnae toEntity(SegmentoCnaeDTO segmentoCnaeDTO);

    @Named("subclasseCnaeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SubclasseCnaeDTO toDtoSubclasseCnaeId(SubclasseCnae subclasseCnae);

    @Named("subclasseCnaeIdSet")
    default Set<SubclasseCnaeDTO> toDtoSubclasseCnaeIdSet(Set<SubclasseCnae> subclasseCnae) {
        return subclasseCnae.stream().map(this::toDtoSubclasseCnaeId).collect(Collectors.toSet());
    }

    @Named("ramoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    RamoDTO toDtoRamoNome(Ramo ramo);

    @Named("empresaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmpresaDTO toDtoEmpresaId(Empresa empresa);

    @Named("empresaIdSet")
    default Set<EmpresaDTO> toDtoEmpresaIdSet(Set<Empresa> empresa) {
        return empresa.stream().map(this::toDtoEmpresaId).collect(Collectors.toSet());
    }
}
