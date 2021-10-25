package eu.kowalcze.michal.arch.clean.example.api

import eu.kowalcze.michal.arch.clean.example.domain.usecase.UseCase
import kotlin.reflect.KClass

class UseCaseExecutor(private val notificationGateway: NotificationGateway) {
    fun <DOMAIN_INPUT, DOMAIN_OUTPUT> execute(
        useCase: UseCase<DOMAIN_INPUT, DOMAIN_OUTPUT>,
        inputProvider: Any.() -> DOMAIN_INPUT,
        toApiConversion: (domainOutput: DOMAIN_OUTPUT) -> UseCaseApiResult<*>,
        handledExceptions: (ExceptionHandler.() -> Any)? = null,
    ): UseCaseApiResult<*> {

        try {
            val domainOutput = useCase.apply(inputProvider())
            return toApiConversion(domainOutput)
        } catch (e: Exception) {
            notificationGateway.notify(useCase, e)
            val exceptionHandler = ExceptionHandler(e)
            handledExceptions?.let { exceptionHandler.handledExceptions() }
            val responseCodeIfExceptionIsHandled = exceptionHandler.responseCode
            if (responseCodeIfExceptionIsHandled != null) {
                return UseCaseApiResult(responseCodeIfExceptionIsHandled, exceptionHandler.message ?: e.message)
            }
            throw e
        }
    }
}

data class UseCaseApiResult<API_OUTPUT>(
    val responseCode: Int,
    val output: API_OUTPUT,
)

class ExceptionHandler(private val exception: Exception) {
    var responseCode: Int? = null
    var message: String? = null
    fun exception(exceptionClass: KClass<*>, responseCodeForException: Int, responseMessage: String) {
        if (this.responseCode != null) {
            return
        }
        if (exceptionClass.isInstance(exception)) {
            this.responseCode = responseCodeForException
            this.message = responseMessage
        }
    }
}