package eu.kowalcze.michal.arch.clean.example.config

import eu.kowalcze.michal.arch.clean.example.api.SpringUseCaseExecutor
import eu.kowalcze.michal.arch.clean.example.api.UseCaseExecutor
import eu.kowalcze.michal.arch.clean.example.infrastructure.LoggingNotificationGateway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class ApiConfig {
    @Bean
    open fun useCaseExecutor(): SpringUseCaseExecutor =
        SpringUseCaseExecutor(UseCaseExecutor(LoggingNotificationGateway()))
}