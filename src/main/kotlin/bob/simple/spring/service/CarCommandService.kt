package bob.simple.spring.service

import bob.simple.spring.repository.CarRepository
import org.springframework.stereotype.Service

@Service
class CarCommandService(
    private val carRepository: CarRepository,
) {

    fun updateAirConditionerStatus(id: String, status: Boolean) {
        val car = carRepository.selectBy(id)

        if (car.acStatus == status) throw IllegalStateException("acStatus is already $status.")

        car.acStatus = status
        carRepository.update(car)
    }
}
