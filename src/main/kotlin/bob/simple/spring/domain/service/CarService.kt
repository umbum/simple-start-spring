package bob.simple.spring.domain.service

import bob.simple.spring.data.repository.CarRepository
import bob.simple.spring.domain.model.Car
import bob.simple.spring.presentation.dto.CarRequestDto
import bob.simple.spring.presentation.dto.CarResponseDto
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class CarService(
    /**
     * `@Qualifier`는 DI 받을 bean의 이름을 직접 명시하는데 사용됩니다.
     * Spring이 `CarService`에 DI 해주는 `CarRepository`의 실제 객체를 변경하고 싶은 경우
     * 필드 이름만 변경하면 됩니다. (`@Qualifier`를 사용하는 경우 그 안의 이름을 변경)
     *
     * CarService를 단독으로 유닛 테스트하고 싶은 경우, 아래와 같이 구현체 변경이 손쉽게 가능합니다. (DI를 사용하고 있기 때문)
     *
     * (유닛 테스트에서는 Spring을 사용하지 않기 때문에 `@Qualifier`같은 annotation은 무시됩니다.)
     * ```
     * val carService = CarService(CarTestRepository())
     * ```
     *
     */
    @Qualifier("carJdbcRepository")
    private val carRepository: CarRepository,
) {
    var id = 3

    fun getCar(id: String): CarResponseDto {
        val car: Car = carRepository.selectBy(id)
        return CarResponseDto.from(car)
    }

    fun createCar(carRequestDto: CarRequestDto): CarResponseDto {
        val car: Car = carRequestDto.toEntity(id++.toString())
        carRepository.insert(car)
        return CarResponseDto.from(car)
    }

    fun updateAirConditionerStatus(
        id: String,
        status: Boolean,
    ): CarResponseDto {
        val car: Car = carRepository.selectBy(id)

        if (car.acStatus == status) throw IllegalStateException("acStatus is already $status.")

        car.acStatus = status
        carRepository.update(car)
        return CarResponseDto.from(car)
    }
}
