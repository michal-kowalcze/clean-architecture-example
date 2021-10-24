package eu.kowalcze.michal.arch.clean.example.infrastructure

import eu.kowalcze.michal.arch.clean.example.api.NotificationGateway
import eu.kowalcze.michal.arch.clean.example.domain.usecase.UseCase
import org.slf4j.LoggerFactory

class LoggingNotificationGateway : NotificationGateway {

    override fun <INPUT, OUTPUT> notify(useCase: UseCase<INPUT, OUTPUT>, exception: Exception) {
        logger.error("Error executing: {}", useCase.javaClass.simpleName, exception)
    }

    companion object {
        val logger = LoggerFactory.getLogger(LoggingNotificationGateway::class.java)
    }
}