package com.kgplatform.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * 网关服务启动
 * <p>
 * KgGatewayApplication启动类
 * @author kg_chen
 * @since 2026-04-23 10:00:00
 */
@SpringBootApplication(scanBasePackages = "com.kgplatform")
public class KgGatewayApplication {

    private static final Logger log = LoggerFactory.getLogger(KgGatewayApplication.class);

    private static final String SPRING_APPLICATION_NAME = "spring.application.name";

    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(KgGatewayApplication.class, args);
        logApplicationStartup(application.getEnvironment());
    }

    private static void logApplicationStartup(Environment env) {
        String protocol = env.getProperty("server.ssl.key-store") != null ? "https" : "http";
        String serverPort = env.getProperty("local.server.port", env.getProperty("server.port", "8080"));
        String contextPath = env.getProperty("server.servlet.context-path", "");
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ignored) {
        }
        String profiles = env.getActiveProfiles().length == 0
                ? Arrays.toString(env.getDefaultProfiles())
                : Arrays.toString(env.getActiveProfiles());

        log.info("\n----------------------------------------------------------\n\t"
                        + "应用: \t\t{} 已启动!\n\t"
                        + "地址: \t\t{}://{}:{}{}\n\t"
                        + "配置文件: \t{}\n----------------------------------------------------------",
                env.getProperty(SPRING_APPLICATION_NAME),
                protocol,
                hostAddress,
                serverPort,
                contextPath,
                profiles);
    }
}
