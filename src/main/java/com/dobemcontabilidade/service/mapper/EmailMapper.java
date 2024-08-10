package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Email;
import com.dobemcontabilidade.domain.Pessoa;
import com.dobemcontabilidade.service.dto.EmailDTO;
import com.dobemcontabilidade.service.dto.PessoaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Email} and its DTO {@link EmailDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmailMapper extends EntityMapper<EmailDTO, Email> {
    @Mapping(target = "pessoa", source = "pessoa", qualifiedByName = "pessoaNome")
    EmailDTO toDto(Email s);

    @Named("pessoaNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    PessoaDTO toDtoPessoaNome(Pessoa pessoa);
}
