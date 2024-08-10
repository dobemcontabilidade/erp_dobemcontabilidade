package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.PerfilRedeSocial;
import com.dobemcontabilidade.service.dto.PerfilRedeSocialDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PerfilRedeSocial} and its DTO {@link PerfilRedeSocialDTO}.
 */
@Mapper(componentModel = "spring")
public interface PerfilRedeSocialMapper extends EntityMapper<PerfilRedeSocialDTO, PerfilRedeSocial> {}
