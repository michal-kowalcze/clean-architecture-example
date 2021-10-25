package eu.kowalcze.michal.arch.clean.example.api

import eu.kowalcze.michal.arch.clean.example.domain.usecase.UseCase
import org.springframework.http.ResponseEntity


class SpringUseCaseExecutor(private val useCaseExecutor: UseCaseExecutor) {
    fun <DOMAIN_INPUT, DOMAIN_OUTPUT> execute(
        useCase: UseCase<DOMAIN_INPUT, DOMAIN_OUTPUT>,
        inputProvider: Any.() -> DOMAIN_INPUT,
        toApiConversion: (domainOutput: DOMAIN_OUTPUT) -> UseCaseApiResult<*>,
        handledExceptions: (ExceptionHandler.() -> Any)? = null,
    ): ResponseEntity<*> {
        return useCaseExecutor.execute(
            useCase,
            inputProvider,
            toApiConversion,
            handledExceptions
        ).toSpringResponse()
    }
}

private fun <API_OUTPUT> UseCaseApiResult<API_OUTPUT>.toSpringResponse(): ResponseEntity<API_OUTPUT> =
    ResponseEntity.status(responseCode).body(output)