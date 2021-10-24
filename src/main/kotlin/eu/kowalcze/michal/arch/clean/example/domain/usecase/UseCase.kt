package eu.kowalcze.michal.arch.clean.example.domain.usecase

interface UseCase<INPUT, OUTPUT> {
    fun apply(input: INPUT): OUTPUT
}