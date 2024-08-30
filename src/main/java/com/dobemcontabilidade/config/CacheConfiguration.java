package com.dobemcontabilidade.config;

import java.net.URI;
import java.util.concurrent.TimeUnit;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.redisson.Redisson;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;

    @Bean
    public javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration(JHipsterProperties jHipsterProperties) {
        MutableConfiguration<Object, Object> jcacheConfig = new MutableConfiguration<>();

        URI redisUri = URI.create(jHipsterProperties.getCache().getRedis().getServer()[0]);

        Config config = new Config();
        // Fix Hibernate lazy initialization https://github.com/jhipster/generator-jhipster/issues/22889
        config.setCodec(new org.redisson.codec.SerializationCodec());
        if (jHipsterProperties.getCache().getRedis().isCluster()) {
            ClusterServersConfig clusterServersConfig = config
                .useClusterServers()
                .setMasterConnectionPoolSize(jHipsterProperties.getCache().getRedis().getConnectionPoolSize())
                .setMasterConnectionMinimumIdleSize(jHipsterProperties.getCache().getRedis().getConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(jHipsterProperties.getCache().getRedis().getSubscriptionConnectionPoolSize())
                .addNodeAddress(jHipsterProperties.getCache().getRedis().getServer());

            if (redisUri.getUserInfo() != null) {
                clusterServersConfig.setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
            }
        } else {
            SingleServerConfig singleServerConfig = config
                .useSingleServer()
                .setConnectionPoolSize(jHipsterProperties.getCache().getRedis().getConnectionPoolSize())
                .setConnectionMinimumIdleSize(jHipsterProperties.getCache().getRedis().getConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(jHipsterProperties.getCache().getRedis().getSubscriptionConnectionPoolSize())
                .setAddress(jHipsterProperties.getCache().getRedis().getServer()[0]);

            if (redisUri.getUserInfo() != null) {
                singleServerConfig.setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
            }
        }
        jcacheConfig.setStatisticsEnabled(true);
        jcacheConfig.setExpiryPolicyFactory(
            CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, jHipsterProperties.getCache().getRedis().getExpiration()))
        );
        return RedissonConfiguration.fromInstance(Redisson.create(config), jcacheConfig);
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cm) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cm);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer(javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration) {
        return cm -> {
            createCache(cm, com.dobemcontabilidade.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.User.class.getName(), jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.Authority.class.getName(), jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.Contador.class.getName(), jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.Pessoajuridica.class.getName(), jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.Pessoajuridica.class.getName() + ".redeSocialEmpresas", jcacheConfiguration);
            createCache(
                cm,
                com.dobemcontabilidade.domain.Pessoajuridica.class.getName() + ".certificadoDigitalEmpresas",
                jcacheConfiguration
            );
            createCache(cm, com.dobemcontabilidade.domain.Pessoajuridica.class.getName() + ".docsEmpresas", jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.Pessoajuridica.class.getName() + ".enderecoEmpresas", jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName(), jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".socios", jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.Empresa.class.getName() + ".assinaturaEmpresas", jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.Socio.class.getName(), jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.CertificadoDigital.class.getName(), jcacheConfiguration);
            createCache(
                cm,
                com.dobemcontabilidade.domain.CertificadoDigital.class.getName() + ".certificadoDigitalEmpresas",
                jcacheConfiguration
            );
            createCache(cm, com.dobemcontabilidade.domain.CertificadoDigitalEmpresa.class.getName(), jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.FornecedorCertificado.class.getName(), jcacheConfiguration);
            createCache(
                cm,
                com.dobemcontabilidade.domain.FornecedorCertificado.class.getName() + ".certificadoDigitalEmpresas",
                jcacheConfiguration
            );
            createCache(cm, com.dobemcontabilidade.domain.DocsEmpresa.class.getName(), jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.RedeSocial.class.getName(), jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.RedeSocial.class.getName() + ".redeSocialEmpresas", jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.RedeSocialEmpresa.class.getName(), jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.PessoaFisica.class.getName(), jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.PessoaFisica.class.getName() + ".enderecoPessoas", jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.PessoaFisica.class.getName() + ".docsPessoas", jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.PessoaFisica.class.getName() + ".emails", jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.PessoaFisica.class.getName() + ".telefones", jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.AssinaturaEmpresa.class.getName(), jcacheConfiguration);
            createCache(
                cm,
                com.dobemcontabilidade.domain.AssinaturaEmpresa.class.getName() + ".termoContratoAssinaturaEmpresas",
                jcacheConfiguration
            );
            createCache(cm, com.dobemcontabilidade.domain.EnderecoEmpresa.class.getName(), jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.Ramo.class.getName(), jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.Ramo.class.getName() + ".empresas", jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.Ramo.class.getName() + ".adicionalRamos", jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.Tributacao.class.getName(), jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.Tributacao.class.getName() + ".empresas", jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.Tributacao.class.getName() + ".adicionalTributacaos", jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.Enquadramento.class.getName(), jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.Enquadramento.class.getName() + ".empresas", jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.Enquadramento.class.getName() + ".adicionalEnquadramentos", jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.FormaDePagamento.class.getName(), jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.FormaDePagamento.class.getName() + ".assinaturaEmpresas", jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.TermoContratoContabil.class.getName(), jcacheConfiguration);
            createCache(
                cm,
                com.dobemcontabilidade.domain.TermoContratoContabil.class.getName() + ".termoContratoAssinaturaEmpresas",
                jcacheConfiguration
            );
            createCache(cm, com.dobemcontabilidade.domain.TermoContratoAssinaturaEmpresa.class.getName(), jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.PeriodoPagamento.class.getName(), jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.PeriodoPagamento.class.getName() + ".assinaturaEmpresas", jcacheConfiguration);
            createCache(
                cm,
                com.dobemcontabilidade.domain.PeriodoPagamento.class.getName() + ".descontoPeriodoPagamentos",
                jcacheConfiguration
            );
            createCache(cm, com.dobemcontabilidade.domain.DescontoPeriodoPagamento.class.getName(), jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.PlanoAssinaturaContabil.class.getName(), jcacheConfiguration);
            createCache(
                cm,
                com.dobemcontabilidade.domain.PlanoAssinaturaContabil.class.getName() + ".descontoPeriodoPagamentos",
                jcacheConfiguration
            );
            createCache(cm, com.dobemcontabilidade.domain.PlanoAssinaturaContabil.class.getName() + ".adicionalRamos", jcacheConfiguration);
            createCache(
                cm,
                com.dobemcontabilidade.domain.PlanoAssinaturaContabil.class.getName() + ".adicionalTributacaos",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.PlanoAssinaturaContabil.class.getName() + ".adicionalEnquadramentos",
                jcacheConfiguration
            );
            createCache(cm, com.dobemcontabilidade.domain.AdicionalEnquadramento.class.getName(), jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.AdicionalRamo.class.getName(), jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.AdicionalTributacao.class.getName(), jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.Pais.class.getName(), jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.Pais.class.getName() + ".estados", jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.Estado.class.getName(), jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.Estado.class.getName() + ".cidades", jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.Cidade.class.getName(), jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.Cidade.class.getName() + ".enderecoPessoas", jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.Cidade.class.getName() + ".enderecoEmpresas", jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.EnderecoPessoa.class.getName(), jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.Email.class.getName(), jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.Telefone.class.getName(), jcacheConfiguration);
            createCache(cm, com.dobemcontabilidade.domain.DocsPessoa.class.getName(), jcacheConfiguration);
            createCache(
                cm,
                com.dobemcontabilidade.domain.Pessoajuridica.class.getName() + ".certificadoDigitalEmpresas",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.CertificadoDigital.class.getName() + ".certificadoDigitalEmpresas",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.FornecedorCertificado.class.getName() + ".certificadoDigitalEmpresas",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.AssinaturaEmpresa.class.getName() + ".termoContratoAssinaturaEmpresas",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.TermoContratoContabil.class.getName() + ".termoContratoAssinaturaEmpresas",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.PeriodoPagamento.class.getName() + ".descontoPeriodoPagamentos",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.PlanoAssinaturaContabil.class.getName() + ".descontoPeriodoPagamentos",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.PlanoAssinaturaContabil.class.getName() + ".adicionalTributacaos",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.PlanoAssinaturaContabil.class.getName() + ".adicionalEnquadramentos",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.Pessoajuridica.class.getName() + ".certificadoDigitalEmpresas",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.CertificadoDigital.class.getName() + ".certificadoDigitalEmpresas",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.FornecedorCertificado.class.getName() + ".certificadoDigitalEmpresas",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.AssinaturaEmpresa.class.getName() + ".termoContratoAssinaturaEmpresas",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.TermoContratoContabil.class.getName() + ".termoContratoAssinaturaEmpresas",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.PeriodoPagamento.class.getName() + ".descontoPeriodoPagamentos",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.PlanoAssinaturaContabil.class.getName() + ".descontoPeriodoPagamentos",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.PlanoAssinaturaContabil.class.getName() + ".adicionalTributacaos",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.PlanoAssinaturaContabil.class.getName() + ".adicionalEnquadramentos",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.Pessoajuridica.class.getName() + ".certificadoDigitalEmpresas",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.CertificadoDigital.class.getName() + ".certificadoDigitalEmpresas",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.FornecedorCertificado.class.getName() + ".certificadoDigitalEmpresas",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.AssinaturaEmpresa.class.getName() + ".termoContratoAssinaturaEmpresas",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.TermoContratoContabil.class.getName() + ".termoContratoAssinaturaEmpresas",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.PeriodoPagamento.class.getName() + ".descontoPeriodoPagamentos",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.PlanoAssinaturaContabil.class.getName() + ".descontoPeriodoPagamentos",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.PlanoAssinaturaContabil.class.getName() + ".adicionalTributacaos",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.PlanoAssinaturaContabil.class.getName() + ".adicionalEnquadramentos",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.Pessoajuridica.class.getName() + ".certificadoDigitalEmpresas",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.CertificadoDigital.class.getName() + ".certificadoDigitalEmpresas",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.FornecedorCertificado.class.getName() + ".certificadoDigitalEmpresas",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.AssinaturaEmpresa.class.getName() + ".termoContratoAssinaturaEmpresas",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.TermoContratoContabil.class.getName() + ".termoContratoAssinaturaEmpresas",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.PeriodoPagamento.class.getName() + ".descontoPeriodoPagamentos",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.PlanoAssinaturaContabil.class.getName() + ".descontoPeriodoPagamentos",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.PlanoAssinaturaContabil.class.getName() + ".adicionalTributacaos",
                jcacheConfiguration
            );
            createCache(
                cm,
                com.dobemcontabilidade.domain.PlanoAssinaturaContabil.class.getName() + ".adicionalEnquadramentos",
                jcacheConfiguration
            );
            // jhipster-needle-redis-add-entry
        };
    }

    private void createCache(
        javax.cache.CacheManager cm,
        String cacheName,
        javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration
    ) {
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
