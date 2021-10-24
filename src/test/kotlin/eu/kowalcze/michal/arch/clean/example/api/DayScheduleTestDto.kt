package eu.kowalcze.michal.arch.clean.example.api

data class DayScheduleTestDto(
    val day: String?,
    val slots: List<SlotTestDto>?,
)

data class SlotTestDto(
    val reserved: String?,
    val start: String?,
    val end: String?,
)