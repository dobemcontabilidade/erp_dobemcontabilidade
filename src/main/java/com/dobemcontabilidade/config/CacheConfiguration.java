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
            createCache(cm, com.dobemcontabilidade.domain.AdicionalRamo.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AdicionalEnquadramento.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AdicionalTributacao.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Administrador.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AreaContabilContador.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AreaContabilAssinaturaEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AnexoEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AnexoRequeridoServicoContabil.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AnexoServicoContabilEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AnexoRequeridoTarefaRecorrente.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AnexoTarefaRecorrenteExecucao.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AnexoRequeridoTarefaOrdemServico.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AnexoOrdemServicoExecucao.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AssinaturaEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AssinaturaEmpresa.class.getName() + ".grupoAcessoEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.AssinaturaEmpresa.class.getName() + ".areaContabilAssinaturaEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.AssinaturaEmpresa.class.getName() + ".servicoContabilAssinaturaEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.AssinaturaEmpresa.class.getName() + ".gatewayAssinaturaEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.AssinaturaEmpresa.class.getName() + ".calculoPlanoAssinaturas");
            createCache(cm, com.dobemcontabilidade.domain.AssinaturaEmpresa.class.getName() + ".pagamentos");
            createCache(cm, com.dobemcontabilidade.domain.AssinaturaEmpresa.class.getName() + ".cobrancaEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.AssinaturaEmpresa.class.getName() + ".usuarioEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.AgendaTarefaRecorrenteExecucao.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AgendaTarefaRecorrenteExecucao.class.getName() + ".agendaContadorConfigs");
            createCache(cm, com.dobemcontabilidade.domain.AgendaContadorConfig.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AgendaTarefaOrdemServicoExecucao.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AgendaTarefaOrdemServicoExecucao.class.getName() + ".agendaContadorConfigs");
            createCache(cm, com.dobemcontabilidade.domain.AgenteIntegracaoEstagio.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AgenteIntegracaoEstagio.class.getName() + ".contratoFuncionarios");
            createCache(cm, com.dobemcontabilidade.domain.AtividadeEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AtorAvaliado.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AtorAvaliado.class.getName() + ".criterioAvaliacaoAtors");
            createCache(cm, com.dobemcontabilidade.domain.AvaliacaoContador.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Avaliacao.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Avaliacao.class.getName() + ".avaliacaoContadors");
            createCache(cm, com.dobemcontabilidade.domain.AreaContabil.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AreaContabil.class.getName() + ".areaContabilAssinaturaEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.AreaContabil.class.getName() + ".servicoContabils");
            createCache(cm, com.dobemcontabilidade.domain.AreaContabil.class.getName() + ".areaContabilContadors");
            createCache(cm, com.dobemcontabilidade.domain.AnexoPessoa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AnexoPessoa.class.getName() + ".anexoRequeridoPessoas");
            createCache(cm, com.dobemcontabilidade.domain.AnexoRequerido.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AnexoRequerido.class.getName() + ".anexoRequeridoPessoas");
            createCache(cm, com.dobemcontabilidade.domain.AnexoRequerido.class.getName() + ".anexoRequeridoEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.AnexoRequerido.class.getName() + ".anexoRequeridoServicoContabils");
            createCache(cm, com.dobemcontabilidade.domain.AnexoRequerido.class.getName() + ".anexoServicoContabilEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.AnexoRequerido.class.getName() + ".anexoRequeridoTarefaOrdemServicos");
            createCache(cm, com.dobemcontabilidade.domain.AnexoRequerido.class.getName() + ".anexoRequeridoTarefaRecorrentes");
            createCache(cm, com.dobemcontabilidade.domain.AnexoRequeridoPessoa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AnexoRequeridoEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.AnexoRequeridoEmpresa.class.getName() + ".anexoEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.BancoPessoa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Banco.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Banco.class.getName() + ".bancoPessoas");
            createCache(cm, com.dobemcontabilidade.domain.CriterioAvaliacao.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.CriterioAvaliacao.class.getName() + ".criterioAvaliacaoAtors");
            createCache(cm, com.dobemcontabilidade.domain.CriterioAvaliacaoAtor.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.CriterioAvaliacaoAtor.class.getName() + ".feedBackUsuarioParaContadors");
            createCache(cm, com.dobemcontabilidade.domain.CriterioAvaliacaoAtor.class.getName() + ".feedBackContadorParaUsuarios");
            createCache(cm, com.dobemcontabilidade.domain.ContadorResponsavelOrdemServico.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.CobrancaEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.CertificadoDigital.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Cidade.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Cidade.class.getName() + ".instituicaoEnsinos");
            createCache(cm, com.dobemcontabilidade.domain.Cidade.class.getName() + ".agenteIntegracaoEstagios");
            createCache(cm, com.dobemcontabilidade.domain.Cidade.class.getName() + ".empresaModelos");
            createCache(cm, com.dobemcontabilidade.domain.Cidade.class.getName() + ".fluxoModelos");
            createCache(cm, com.dobemcontabilidade.domain.Cidade.class.getName() + ".enderecoPessoas");
            createCache(cm, com.dobemcontabilidade.domain.Cidade.class.getName() + ".enderecoEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Competencia.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Competencia.class.getName() + ".tarefas");
            createCache(cm, com.dobemcontabilidade.domain.Contador.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Contador.class.getName() + ".areaContabilAssinaturaEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Contador.class.getName() + ".feedBackContadorParaUsuarios");
            createCache(cm, com.dobemcontabilidade.domain.Contador.class.getName() + ".ordemServicos");
            createCache(cm, com.dobemcontabilidade.domain.Contador.class.getName() + ".areaContabilContadors");
            createCache(cm, com.dobemcontabilidade.domain.Contador.class.getName() + ".contadorResponsavelOrdemServicos");
            createCache(cm, com.dobemcontabilidade.domain.Contador.class.getName() + ".contadorResponsavelTarefaRecorrentes");
            createCache(cm, com.dobemcontabilidade.domain.Contador.class.getName() + ".departamentoEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Contador.class.getName() + ".departamentoContadors");
            createCache(cm, com.dobemcontabilidade.domain.Contador.class.getName() + ".termoAdesaoContadors");
            createCache(cm, com.dobemcontabilidade.domain.Contador.class.getName() + ".avaliacaoContadors");
            createCache(cm, com.dobemcontabilidade.domain.Contador.class.getName() + ".tarefaEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.CalculoPlanoAssinatura.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.ContratoFuncionario.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.ClasseCnae.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.ClasseCnae.class.getName() + ".subclasseCnaes");
            createCache(cm, com.dobemcontabilidade.domain.ContadorResponsavelTarefaRecorrente.class.getName());
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
            createCache(cm, com.dobemcontabilidade.domain.DependentesFuncionario.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.DivisaoCnae.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.DivisaoCnae.class.getName() + ".grupoCnaes");
            createCache(cm, com.dobemcontabilidade.domain.Email.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".funcionarios");
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".anexoEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".ordemServicos");
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".anexoRequeridoEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".impostoEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".parcelamentoImpostos");
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".assinaturaEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".departamentoEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".tarefaEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".enderecoEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".atividadeEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".socios");
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".certificadoDigitals");
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".opcaoRazaoSocialEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".opcaoNomeFantasiaEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".termoAdesaoEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".segmentoCnaes");
            createCache(cm, com.dobemcontabilidade.domain.EmpresaVinculada.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Escolaridade.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Escolaridade.class.getName() + ".escolaridadePessoas");
            createCache(cm, com.dobemcontabilidade.domain.EscolaridadePessoa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Estrangeiro.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.EnderecoEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.EnderecoPessoa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Enquadramento.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Enquadramento.class.getName() + ".anexoRequeridoEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Enquadramento.class.getName() + ".empresaModelos");
            createCache(cm, com.dobemcontabilidade.domain.Enquadramento.class.getName() + ".adicionalEnquadramentos");
            createCache(cm, com.dobemcontabilidade.domain.Esfera.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Esfera.class.getName() + ".servicoContabils");
            createCache(cm, com.dobemcontabilidade.domain.Esfera.class.getName() + ".impostos");
            createCache(cm, com.dobemcontabilidade.domain.Esfera.class.getName() + ".tarefas");
            createCache(cm, com.dobemcontabilidade.domain.Estado.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Estado.class.getName() + ".cidades");
            createCache(cm, com.dobemcontabilidade.domain.EtapaFluxoExecucao.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.EtapaFluxoExecucao.class.getName() + ".servicoContabilEtapaFluxoExecucaos");
            createCache(cm, com.dobemcontabilidade.domain.EmpresaModelo.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.EmpresaModelo.class.getName() + ".servicoContabilEmpresaModelos");
            createCache(cm, com.dobemcontabilidade.domain.EmpresaModelo.class.getName() + ".impostoEmpresaModelos");
            createCache(cm, com.dobemcontabilidade.domain.EmpresaModelo.class.getName() + ".anexoRequeridoEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.EmpresaModelo.class.getName() + ".tarefaEmpresaModelos");
            createCache(cm, com.dobemcontabilidade.domain.EmpresaModelo.class.getName() + ".empresas");
            createCache(cm, com.dobemcontabilidade.domain.EmpresaModelo.class.getName() + ".segmentoCnaes");
            createCache(cm, com.dobemcontabilidade.domain.FeedBackUsuarioParaContador.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.FeedBackContadorParaUsuario.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Funcionalidade.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Funcionalidade.class.getName() + ".funcionalidadeGrupoAcessoPadraos");
            createCache(cm, com.dobemcontabilidade.domain.Funcionalidade.class.getName() + ".funcionalidadeGrupoAcessoEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.FluxoModelo.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.FluxoModelo.class.getName() + ".ordemServicos");
            createCache(cm, com.dobemcontabilidade.domain.FluxoModelo.class.getName() + ".etapaFluxoModelos");
            createCache(cm, com.dobemcontabilidade.domain.FluxoExecucao.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.FormaDePagamento.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.FormaDePagamento.class.getName() + ".assinaturaEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.FormaDePagamento.class.getName() + ".cobrancaEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.FuncionalidadeGrupoAcessoPadrao.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Frequencia.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Frequencia.class.getName() + ".tarefas");
            createCache(cm, com.dobemcontabilidade.domain.Funcionario.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Funcionario.class.getName() + ".estrangeiros");
            createCache(cm, com.dobemcontabilidade.domain.Funcionario.class.getName() + ".contratoFuncionarios");
            createCache(cm, com.dobemcontabilidade.domain.Funcionario.class.getName() + ".demissaoFuncionarios");
            createCache(cm, com.dobemcontabilidade.domain.Funcionario.class.getName() + ".dependentesFuncionarios");
            createCache(cm, com.dobemcontabilidade.domain.Funcionario.class.getName() + ".empresaVinculadas");
            createCache(cm, com.dobemcontabilidade.domain.Funcionario.class.getName() + ".departamentoFuncionarios");
            createCache(cm, com.dobemcontabilidade.domain.GrupoAcessoPadrao.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.GrupoAcessoPadrao.class.getName() + ".funcionalidadeGrupoAcessoPadraos");
            createCache(cm, com.dobemcontabilidade.domain.GrupoAcessoPadrao.class.getName() + ".grupoAcessoUsuarioContadors");
            createCache(cm, com.dobemcontabilidade.domain.GrupoAcessoEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.GrupoAcessoEmpresa.class.getName() + ".funcionalidadeGrupoAcessoEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.GrupoAcessoEmpresa.class.getName() + ".grupoAcessoUsuarioEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.GrupoAcessoEmpresa.class.getName() + ".grupoAcessoEmpresaUsuarioContadors");
            createCache(cm, com.dobemcontabilidade.domain.GrupoAcessoUsuarioEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.GrupoAcessoEmpresaUsuarioContador.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.GrupoAcessoUsuarioContador.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.FuncionalidadeGrupoAcessoEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.GatewayPagamento.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.GatewayPagamento.class.getName() + ".gatewayAssinaturaEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.GatewayAssinaturaEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.SecaoCnae.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.SecaoCnae.class.getName() + ".divisaoCnaes");
            createCache(cm, com.dobemcontabilidade.domain.GrupoCnae.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.GrupoCnae.class.getName() + ".classeCnaes");
            createCache(cm, com.dobemcontabilidade.domain.InstituicaoEnsino.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.InstituicaoEnsino.class.getName() + ".contratoFuncionarios");
            createCache(cm, com.dobemcontabilidade.domain.ObservacaoCnae.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.OpcaoRazaoSocialEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.OpcaoNomeFantasiaEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.ServicoContabilEmpresaModelo.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.ServicoContabilEmpresaModelo.class.getName() + ".tarefaRecorrenteEmpresaModelos");
            createCache(cm, com.dobemcontabilidade.domain.Permisao.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Permisao.class.getName() + ".funcionalidadeGrupoAcessoPadraos");
            createCache(cm, com.dobemcontabilidade.domain.Permisao.class.getName() + ".funcionalidadeGrupoAcessoEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Permisao.class.getName() + ".grupoAcessoEmpresaUsuarioContadors");
            createCache(cm, com.dobemcontabilidade.domain.Pagamento.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Pais.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.PerfilAcesso.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.PerfilAcessoUsuario.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.PerfilContador.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.PerfilContador.class.getName() + ".contadors");
            createCache(cm, com.dobemcontabilidade.domain.PerfilContador.class.getName() + ".perfilContadorDepartamentos");
            createCache(cm, com.dobemcontabilidade.domain.PerfilContadorDepartamento.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.PerfilRedeSocial.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.PeriodoPagamento.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.PeriodoPagamento.class.getName() + ".calculoPlanoAssinaturas");
            createCache(cm, com.dobemcontabilidade.domain.PeriodoPagamento.class.getName() + ".assinaturaEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.PeriodoPagamento.class.getName() + ".descontoPlanoContaAzuls");
            createCache(cm, com.dobemcontabilidade.domain.PeriodoPagamento.class.getName() + ".descontoPlanoContabils");
            createCache(cm, com.dobemcontabilidade.domain.Pessoa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Pessoa.class.getName() + ".funcionarios");
            createCache(cm, com.dobemcontabilidade.domain.Pessoa.class.getName() + ".anexoPessoas");
            createCache(cm, com.dobemcontabilidade.domain.Pessoa.class.getName() + ".escolaridadePessoas");
            createCache(cm, com.dobemcontabilidade.domain.Pessoa.class.getName() + ".bancoPessoas");
            createCache(cm, com.dobemcontabilidade.domain.Pessoa.class.getName() + ".dependentesFuncionarios");
            createCache(cm, com.dobemcontabilidade.domain.Pessoa.class.getName() + ".enderecoPessoas");
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
            createCache(cm, com.dobemcontabilidade.domain.PlanoContabil.class.getName() + ".termoAdesaoEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Profissao.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Profissao.class.getName() + ".funcionarios");
            createCache(cm, com.dobemcontabilidade.domain.Profissao.class.getName() + ".socios");
            createCache(cm, com.dobemcontabilidade.domain.PlanoContaAzul.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.PlanoContaAzul.class.getName() + ".calculoPlanoAssinaturas");
            createCache(cm, com.dobemcontabilidade.domain.PlanoContaAzul.class.getName() + ".assinaturaEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.PlanoContaAzul.class.getName() + ".descontoPlanoContaAzuls");
            createCache(cm, com.dobemcontabilidade.domain.PrazoAssinatura.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Ramo.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Ramo.class.getName() + ".anexoRequeridoEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Ramo.class.getName() + ".empresaModelos");
            createCache(cm, com.dobemcontabilidade.domain.Ramo.class.getName() + ".calculoPlanoAssinaturas");
            createCache(cm, com.dobemcontabilidade.domain.Ramo.class.getName() + ".adicionalRamos");
            createCache(cm, com.dobemcontabilidade.domain.Ramo.class.getName() + ".valorBaseRamos");
            createCache(cm, com.dobemcontabilidade.domain.Ramo.class.getName() + ".segmentoCnaes");
            createCache(cm, com.dobemcontabilidade.domain.SubclasseCnae.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.SubclasseCnae.class.getName() + ".observacaoCnaes");
            createCache(cm, com.dobemcontabilidade.domain.SubclasseCnae.class.getName() + ".atividadeEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.SubclasseCnae.class.getName() + ".segmentoCnaes");
            createCache(cm, com.dobemcontabilidade.domain.SegmentoCnae.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.SegmentoCnae.class.getName() + ".subclasseCnaes");
            createCache(cm, com.dobemcontabilidade.domain.SegmentoCnae.class.getName() + ".empresas");
            createCache(cm, com.dobemcontabilidade.domain.SegmentoCnae.class.getName() + ".empresaModelos");
            createCache(cm, com.dobemcontabilidade.domain.Socio.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Subtarefa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Tarefa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Tarefa.class.getName() + ".tarefaEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Tarefa.class.getName() + ".subtarefas");
            createCache(cm, com.dobemcontabilidade.domain.TarefaEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Telefone.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.TermoAdesaoContador.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.TermoAdesaoEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.TarefaRecorrenteEmpresaModelo.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.TermoDeAdesao.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.TermoDeAdesao.class.getName() + ".termoAdesaoContadors");
            createCache(cm, com.dobemcontabilidade.domain.TipoDenuncia.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Tributacao.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Tributacao.class.getName() + ".anexoRequeridoEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Tributacao.class.getName() + ".empresaModelos");
            createCache(cm, com.dobemcontabilidade.domain.Tributacao.class.getName() + ".calculoPlanoAssinaturas");
            createCache(cm, com.dobemcontabilidade.domain.Tributacao.class.getName() + ".adicionalTributacaos");
            createCache(cm, com.dobemcontabilidade.domain.UsuarioContador.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.UsuarioContador.class.getName() + ".grupoAcessoEmpresaUsuarioContadors");
            createCache(cm, com.dobemcontabilidade.domain.UsuarioContador.class.getName() + ".grupoAcessoUsuarioContadors");
            createCache(cm, com.dobemcontabilidade.domain.UsuarioContador.class.getName() + ".feedBackUsuarioParaContadors");
            createCache(cm, com.dobemcontabilidade.domain.UsuarioEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.UsuarioEmpresa.class.getName() + ".grupoAcessoUsuarioEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.UsuarioEmpresa.class.getName() + ".feedBackUsuarioParaContadors");
            createCache(cm, com.dobemcontabilidade.domain.UsuarioEmpresa.class.getName() + ".feedBackContadorParaUsuarios");
            createCache(cm, com.dobemcontabilidade.domain.UsuarioGestao.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.ValorBaseRamo.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.DemissaoFuncionario.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Imposto.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Imposto.class.getName() + ".impostoEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.Imposto.class.getName() + ".parcelamentoImpostos");
            createCache(cm, com.dobemcontabilidade.domain.Imposto.class.getName() + ".impostoEmpresaModelos");
            createCache(cm, com.dobemcontabilidade.domain.Modulo.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Modulo.class.getName() + ".funcionalidades");
            createCache(cm, com.dobemcontabilidade.domain.OrdemServico.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.OrdemServico.class.getName() + ".feedBackUsuarioParaContadors");
            createCache(cm, com.dobemcontabilidade.domain.OrdemServico.class.getName() + ".feedBackContadorParaUsuarios");
            createCache(cm, com.dobemcontabilidade.domain.OrdemServico.class.getName() + ".etapaFluxoExecucaos");
            createCache(cm, com.dobemcontabilidade.domain.OrdemServico.class.getName() + ".servicoContabilOrdemServicos");
            createCache(cm, com.dobemcontabilidade.domain.Sistema.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.Sistema.class.getName() + ".modulos");
            createCache(cm, com.dobemcontabilidade.domain.ServicoContabilOrdemServico.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.ServicoContabilOrdemServico.class.getName() + ".tarefaOrdemServicos");
            createCache(cm, com.dobemcontabilidade.domain.ParcelamentoImposto.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.ParcelamentoImposto.class.getName() + ".parcelaImpostoAPagars");
            createCache(cm, com.dobemcontabilidade.domain.ParcelamentoImposto.class.getName() + ".impostoParcelados");
            createCache(cm, com.dobemcontabilidade.domain.ParcelaImpostoAPagar.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.ImpostoAPagarEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.ImpostoAPagarEmpresa.class.getName() + ".impostoParcelados");
            createCache(cm, com.dobemcontabilidade.domain.ImpostoParcelado.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.ServicoContabilEtapaFluxoModelo.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.EtapaFluxoModelo.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.EtapaFluxoModelo.class.getName() + ".servicoContabilEtapaFluxoModelos");
            createCache(cm, com.dobemcontabilidade.domain.ImpostoEmpresa.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.ImpostoEmpresa.class.getName() + ".impostoAPagarEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.SubTarefaOrdemServico.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.TarefaRecorrente.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.TarefaRecorrente.class.getName() + ".tarefaRecorrenteExecucaos");
            createCache(cm, com.dobemcontabilidade.domain.TarefaRecorrente.class.getName() + ".anexoRequeridoTarefaRecorrentes");
            createCache(cm, com.dobemcontabilidade.domain.TarefaRecorrenteExecucao.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.TarefaRecorrenteExecucao.class.getName() + ".agendaTarefaRecorrenteExecucaos");
            createCache(cm, com.dobemcontabilidade.domain.TarefaRecorrenteExecucao.class.getName() + ".anexoTarefaRecorrenteExecucaos");
            createCache(cm, com.dobemcontabilidade.domain.TarefaRecorrenteExecucao.class.getName() + ".subTarefaRecorrentes");
            createCache(
                cm,
                com.dobemcontabilidade.domain.TarefaRecorrenteExecucao.class.getName() + ".contadorResponsavelTarefaRecorrentes"
            );
            createCache(cm, com.dobemcontabilidade.domain.TarefaOrdemServico.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.TarefaOrdemServico.class.getName() + ".anexoRequeridoTarefaOrdemServicos");
            createCache(cm, com.dobemcontabilidade.domain.TarefaOrdemServico.class.getName() + ".tarefaOrdemServicoExecucaos");
            createCache(cm, com.dobemcontabilidade.domain.TarefaOrdemServicoExecucao.class.getName());
            createCache(
                cm,
                com.dobemcontabilidade.domain.TarefaOrdemServicoExecucao.class.getName() + ".agendaTarefaOrdemServicoExecucaos"
            );
            createCache(cm, com.dobemcontabilidade.domain.TarefaOrdemServicoExecucao.class.getName() + ".anexoOrdemServicoExecucaos");
            createCache(cm, com.dobemcontabilidade.domain.TarefaOrdemServicoExecucao.class.getName() + ".subTarefaOrdemServicos");
            createCache(cm, com.dobemcontabilidade.domain.TarefaOrdemServicoExecucao.class.getName() + ".contadorResponsavelOrdemServicos");
            createCache(cm, com.dobemcontabilidade.domain.TermoContratoContabil.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.SubTarefaRecorrente.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.TarefaEmpresaModelo.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.ServicoContabil.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.ServicoContabil.class.getName() + ".servicoContabilEmpresaModelos");
            createCache(cm, com.dobemcontabilidade.domain.ServicoContabil.class.getName() + ".servicoContabilEtapaFluxoModelos");
            createCache(cm, com.dobemcontabilidade.domain.ServicoContabil.class.getName() + ".servicoContabilEtapaFluxoExecucaos");
            createCache(cm, com.dobemcontabilidade.domain.ServicoContabil.class.getName() + ".anexoRequeridoServicoContabils");
            createCache(cm, com.dobemcontabilidade.domain.ServicoContabil.class.getName() + ".servicoContabilOrdemServicos");
            createCache(cm, com.dobemcontabilidade.domain.ServicoContabil.class.getName() + ".servicoContabilAssinaturaEmpresas");
            createCache(cm, com.dobemcontabilidade.domain.ServicoContabil.class.getName() + ".tarefaEmpresaModelos");
            createCache(cm, com.dobemcontabilidade.domain.ServicoContabilEtapaFluxoExecucao.class.getName());
            createCache(cm, com.dobemcontabilidade.domain.ServicoContabilAssinaturaEmpresa.class.getName());
            createCache(
                cm,
                com.dobemcontabilidade.domain.ServicoContabilAssinaturaEmpresa.class.getName() + ".anexoServicoContabilEmpresas"
            );
            createCache(cm, com.dobemcontabilidade.domain.ServicoContabilAssinaturaEmpresa.class.getName() + ".tarefaRecorrentes");
            createCache(cm, com.dobemcontabilidade.domain.ImpostoEmpresaModelo.class.getName());
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
