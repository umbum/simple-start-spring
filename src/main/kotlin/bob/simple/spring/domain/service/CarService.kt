package bob.simple.spring.domain.service

import bob.simple.spring.data.repository.CarRepository
import bob.simple.spring.presentation.dto.CarRequestDto
import bob.simple.spring.presentation.dto.CarResponseDto
import org.springframework.stereotype.Service

@Service
class CarService(
    private val carRepository: CarRepository,
) {
    var id = 3

    fun getCar(id: String): CarResponseDto {
        val car = carRepository.selectBy(id)
        return CarResponseDto.from(car)
    }

    fun createCar(carRequestDto: CarRequestDto): CarResponseDto {
        val car = carRequestDto.toEntity(id++.toString())
        carRepository.insert(car)
        return CarResponseDto.from(car)
    }

    fun updateAirConditionerStatus(
        id: String,
        status: Boolean,
    ): CarResponseDto {
        val car = carRepository.selectBy(id)

        if (car.acStatus == status) throw IllegalStateException("acStatus is already $status.")

        car.acStatus = status
        carRepository.update(car)
        return CarResponseDto.from(car)
    }
}
