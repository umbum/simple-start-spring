package bob.simple.spring.controller

import bob.simple.spring.service.CarCommandService
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/car")
@RestController
class CarController(
    private val carCommandService: CarCommandService,
) {
    @PatchMapping("/air-conditioner")
    fun updateAirConditionerStatus(
        @RequestParam id: String,
        @RequestParam status: Boolean
    ): String {
        carCommandService.updateAirConditionerStatus(id, status)
        return "success"
    }
}
