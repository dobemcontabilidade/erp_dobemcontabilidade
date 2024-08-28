package com.dobemcontabilidade;

import com.dobemcontabilidade.config.AsyncSyncConfiguration;
import com.dobemcontabilidade.config.EmbeddedRedis;
import com.dobemcontabilidade.config.EmbeddedSQL;
import com.dobemcontabilidade.config.JacksonConfiguration;
import com.dobemcontabilidade.config.TestSecurityConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(
    classes = { ErpDobemcontabilidadeApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class, TestSecurityConfiguration.class }
)
@EmbeddedRedis
@EmbeddedSQL
public @interface IntegrationTest {
}
