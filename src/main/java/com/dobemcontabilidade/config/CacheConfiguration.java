package com.dobemcontabilidade.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                Object.class,
                Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries())
            )
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build()
        );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.dobemcontabilidade.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.dobemcontabilidade.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.dobemcontabilidade.domain.User.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Authority.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.User.class.getName() + ".authorities");
            createCache(cm, com.dobemcontabilidade.domain.Pais.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Estado.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Estado.class.getName() + ".cidades");
            createCache(cm, com.dobemcontabilidade.domain.Cidade.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Cidade.class.getName() + ".enderecoPessoas");
            createCache(cm, com.dobemcontabilidade.domain.Cidade.class.getName() + ".enderecoEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Banco.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Banco.class.getName() + ".bancoContadors");
            createCache(cm, com.dobemcontabilidade.domain.EnderecoPessoa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.UsuarioErp.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AdicionalEnquadramento.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AdicionalRamo.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AdicionalTributacao.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Administrador.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Administrador.class.getName() + ".usuarioContadors");
            createCache(cm, com.dobemcontabilidade.domain.AnexoEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AssinaturaEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AssinaturaEmpresa.class.getName() + ".calculoPlanoAssinaturas");
            createCache(cm, com.dobemcontabilidade.domain.AssinaturaEmpresa.class.getName() + ".pagamentos");
            createCache(cm, com.dobemcontabilidade.domain.AtividadeEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Avaliacao.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Avaliacao.class.getName() + ".avaliacaoContadors");
            createCache(cm, com.dobemcontabilidade.domain.AvaliacaoContador.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AreaContabilEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.BancoContador.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.CertificadoDigital.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Competencia.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Competencia.class.getName() + ".tarefas");
            createCache(cm, com.dobemcontabilidade.domain.Contador.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Contador.class.getName() + ".areaContabilEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Contador.class.getName() + ".areaContabilContadors");
            createCache(cm, com.dobemcontabilidade.domain.Contador.class.getName() + ".departamentoEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Contador.class.getName() + ".departamentoContadors");
            createCache(cm, com.dobemcontabilidade.domain.Contador.class.getName() + ".termoAdesaoContadors");
            createCache(cm, com.dobemcontabilidade.domain.Contador.class.getName() + ".bancoContadors");
            createCache(cm, com.dobemcontabilidade.domain.Contador.class.getName() + ".avaliacaoContadors");
            createCache(cm, com.dobemcontabilidade.domain.Contador.class.getName() + ".tarefaEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.AreaContabil.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AreaContabil.class.getName() + ".perfilContadorAreaContabils");
            createCache(cm, com.dobemcontabilidade.domain.AreaContabil.class.getName() + ".areaContabilContadors");
            createCache(cm, com.dobemcontabilidade.domain.AreaContabilContador.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Denuncia.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Departamento.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Departamento.class.getName() + ".departamentoEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Departamento.class.getName() + ".perfilContadorDepartamentos");
            createCache(cm, com.dobemcontabilidade.domain.Departamento.class.getName() + ".departamentoContadors");
            createCache(cm, com.dobemcontabilidade.domain.Departamento.class.getName() + ".departamentoFuncionarios");
            createCache(cm, com.dobemcontabilidade.domain.DepartamentoContador.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.DepartamentoEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.DepartamentoFuncionario.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.DescontoPlanoContaAzul.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.DescontoPlanoContaAzul.class.getName() + ".calculoPlanoAssinaturas");
            createCache(cm, com.dobemcontabilidade.domain.DescontoPlanoContabil.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.DescontoPlanoContabil.class.getName() + ".calculoPlanoAssinaturas");
            createCache(cm, com.dobemcontabilidade.domain.Email.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".assinaturaEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".funcionarios");
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".departamentoEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".tarefaEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".enderecoEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".atividadeEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".socios");
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".anexoEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".certificadoDigitals");
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".usuarioEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".opcaoRazaoSocialEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".opcaoNomeFantasiaEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.OpcaoRazaoSocialEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.OpcaoNomeFantasiaEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.EnderecoEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Enquadramento.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Enquadramento.class.getName() + ".empresas");
            createCache(cm, com.dobemcontabilidade.domain.Enquadramento.class.getName() + ".adicionalEnquadramentos");
            createCache(cm, com.dobemcontabilidade.domain.Esfera.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Esfera.class.getName() + ".tarefas");
            createCache(cm, com.dobemcontabilidade.domain.FormaDePagamento.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.FormaDePagamento.class.getName() + ".assinaturaEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Frequencia.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Frequencia.class.getName() + ".tarefas");
            createCache(cm, com.dobemcontabilidade.domain.Funcionario.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Funcionario.class.getName() + ".departamentoFuncionarios");
            createCache(cm, com.dobemcontabilidade.domain.Pagamento.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.PerfilContador.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.PerfilContador.class.getName() + ".perfilContadorAreaContabils");
            createCache(cm, com.dobemcontabilidade.domain.PerfilContador.class.getName() + ".contadors");
            createCache(cm, com.dobemcontabilidade.domain.PerfilContador.class.getName() + ".perfilContadorDepartamentos");
            createCache(cm, com.dobemcontabilidade.domain.PerfilContadorAreaContabil.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.PerfilContadorDepartamento.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.PerfilRedeSocial.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.PeriodoPagamento.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.PeriodoPagamento.class.getName() + ".calculoPlanoAssinaturas");
            createCache(cm, com.dobemcontabilidade.domain.PeriodoPagamento.class.getName() + ".assinaturaEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.PeriodoPagamento.class.getName() + ".descontoPlanoContaAzuls");
            createCache(cm, com.dobemcontabilidade.domain.PeriodoPagamento.class.getName() + ".descontoPlanoContabils");
            createCache(cm, com.dobemcontabilidade.domain.Pessoa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Pessoa.class.getName() + ".enderecoPessoas");
            createCache(cm, com.dobemcontabilidade.domain.Pessoa.class.getName() + ".anexoPessoas");
            createCache(cm, com.dobemcontabilidade.domain.Pessoa.class.getName() + ".emails");
            createCache(cm, com.dobemcontabilidade.domain.Pessoa.class.getName() + ".telefones");
            createCache(cm, com.dobemcontabilidade.domain.PlanoContabil.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.PlanoContabil.class.getName() + ".calculoPlanoAssinaturas");
            createCache(cm, com.dobemcontabilidade.domain.PlanoContabil.class.getName() + ".assinaturaEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.PlanoContabil.class.getName() + ".descontoPlanoContabils");
            createCache(cm, com.dobemcontabilidade.domain.PlanoContabil.class.getName() + ".adicionalRamos");
            createCache(cm, com.dobemcontabilidade.domain.PlanoContabil.class.getName() + ".adicionalTributacaos");
            createCache(cm, com.dobemcontabilidade.domain.PlanoContabil.class.getName() + ".termoContratoContabils");
            createCache(cm, com.dobemcontabilidade.domain.PlanoContabil.class.getName() + ".adicionalEnquadramentos");
            createCache(cm, com.dobemcontabilidade.domain.PlanoContabil.class.getName() + ".valorBaseRamos");
            createCache(cm, com.dobemcontabilidade.domain.PlanoContaAzul.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.PlanoContaAzul.class.getName() + ".calculoPlanoAssinaturas");
            createCache(cm, com.dobemcontabilidade.domain.PlanoContaAzul.class.getName() + ".assinaturaEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.PlanoContaAzul.class.getName() + ".descontoPlanoContaAzuls");
            createCache(cm, com.dobemcontabilidade.domain.PrazoAssinatura.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Profissao.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Ramo.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Ramo.class.getName() + ".calculoPlanoAssinaturas");
            createCache(cm, com.dobemcontabilidade.domain.Ramo.class.getName() + ".empresas");
            createCache(cm, com.dobemcontabilidade.domain.Ramo.class.getName() + ".adicionalRamos");
            createCache(cm, com.dobemcontabilidade.domain.Ramo.class.getName() + ".valorBaseRamos");
            createCache(cm, com.dobemcontabilidade.domain.Socio.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Socio.class.getName() + ".profissaos");
            createCache(cm, com.dobemcontabilidade.domain.Subtarefa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Tarefa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Tarefa.class.getName() + ".tarefaEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Tarefa.class.getName() + ".subtarefas");
            createCache(cm, com.dobemcontabilidade.domain.Tarefa.class.getName() + ".documentoTarefas");
            createCache(cm, com.dobemcontabilidade.domain.DocumentoTarefa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.TarefaEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Telefone.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.TermoAdesaoContador.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.TermoContratoContabil.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.TermoDeAdesao.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.TermoDeAdesao.class.getName() + ".termoAdesaoContadors");
            createCache(cm, com.dobemcontabilidade.domain.TipoDenuncia.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Tributacao.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Tributacao.class.getName() + ".calculoPlanoAssinaturas");
            createCache(cm, com.dobemcontabilidade.domain.Tributacao.class.getName() + ".empresas");
            createCache(cm, com.dobemcontabilidade.domain.Tributacao.class.getName() + ".adicionalTributacaos");
            createCache(cm, com.dobemcontabilidade.domain.UsuarioContador.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.UsuarioEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.UsuarioGestao.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.ValorBaseRamo.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.CalculoPlanoAssinatura.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AnexoPessoa.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
