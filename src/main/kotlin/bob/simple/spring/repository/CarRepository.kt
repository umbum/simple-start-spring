package bob.simple.spring.repository

import bob.simple.spring.model.Car
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.DataClassRowMapper
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Repository

@Repository
class CarRepository(
    private val jdbcOperations: NamedParameterJdbcOperations,
) {

    fun selectBy(id: String): Car {
        return jdbcOperations.queryForObject(
            "select * from car where id = :id",
            mapOf("id" to id),
            DataClassRowMapper(Car::class.java),
        )!!
    }

    fun update(car: Car) {
        jdbcOperations.update(
            "update car set ac_status = :acStatus, model = :model where id = :id",
            BeanPropertySqlParameterSource(car)
        )
    }
}
