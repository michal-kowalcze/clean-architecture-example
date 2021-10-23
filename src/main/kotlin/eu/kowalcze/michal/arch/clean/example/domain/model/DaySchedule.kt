package eu.kowalcze.michal.arch.clean.example.domain.model

import java.time.LocalDate
import java.time.LocalTime


data class DaySchedule(
    val day: LocalDate,
    val slots: List<Slot>,
) {

    fun reserveSlot(index: Int): DaySchedule {
        verifyIndexIsValid(index)

        if (slots[index].reserved) {
            throw SlotAlreadyReservedException(index)
        }

        val modifiedSlots = slots.toMutableList()
        modifiedSlots.set(index, slots[index].makeReservation())
        return copy(slots = modifiedSlots)
    }

    private fun verifyIndexIsValid(index: Int) {
        if (index < 0 || index >= slots.size) {
            throw InvalidSlotIndexException(index, slots.size)
        }
    }


}

data class Slot(
    val reserved: Boolean,
    val start: LocalTime,
    val end: LocalTime,
) {
    fun makeReservation(): Slot = copy(reserved = true)
}


data class SlotId(
    val day: LocalDate,
    val index: Int,
)

class InvalidSlotIndexException(index: Int, slotsCount: Int) :
    IllegalArgumentException("Slot[$index] is not available. Slots count is: $slotsCount")

class SlotAlreadyReservedException(index: Int) : IllegalStateException("Slot[$index] is already reserved")
