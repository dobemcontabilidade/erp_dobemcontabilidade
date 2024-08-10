package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.ClasseCnae;
import com.dobemcontabilidade.domain.SegmentoCnae;
import com.dobemcontabilidade.domain.SubclasseCnae;
import com.dobemcontabilidade.service.dto.ClasseCnaeDTO;
import com.dobemcontabilidade.service.dto.SegmentoCnaeDTO;
import com.dobemcontabilidade.service.dto.SubclasseCnaeDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SubclasseCnae} and its DTO {@link SubclasseCnaeDTO}.
 */
@Mapper(componentModel = "spring")
public interface SubclasseCnaeMapper extends EntityMapper<SubclasseCnaeDTO, SubclasseCnae> {
    @Mapping(target = "classe", source = "classe", qualifiedByName = "classeCnaeDescricao")
    @Mapping(target = "segmentoCnaes", source = "segmentoCnaes", qualifiedByName = "segmentoCnaeIdSet")
    SubclasseCnaeDTO toDto(SubclasseCnae s);

    @Mapping(target = "segmentoCnaes", ignore = true)
    @Mapping(target = "removeSegmentoCnae", ignore = true)
    SubclasseCnae toEntity(SubclasseCnaeDTO subclasseCnaeDTO);

    @Named("classeCnaeDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    ClasseCnaeDTO toDtoClasseCnaeDescricao(ClasseCnae classeCnae);

    @Named("segmentoCnaeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SegmentoCnaeDTO toDtoSegmentoCnaeId(SegmentoCnae segmentoCnae);

    @Named("segmentoCnaeIdSet")
    default Set<SegmentoCnaeDTO> toDtoSegmentoCnaeIdSet(Set<SegmentoCnae> segmentoCnae) {
        return segmentoCnae.stream().map(this::toDtoSegmentoCnaeId).collect(Collectors.toSet());
    }

    default String map(byte[] value) {
        return new String(value);
    }
}
