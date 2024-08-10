package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Pessoa;
import com.dobemcontabilidade.service.dto.PessoaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pessoa} and its DTO {@link PessoaDTO}.
 */
@Mapper(componentModel = "spring")
public interface PessoaMapper extends EntityMapper<PessoaDTO, Pessoa> {}
