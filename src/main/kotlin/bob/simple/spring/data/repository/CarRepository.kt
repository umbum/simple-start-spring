package bob.simple.spring.data.repository

import bob.simple.spring.domain.model.Car
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Repository

@Repository
class CarRepository(
    private val jdbcOperations: NamedParameterJdbcOperations,
) {

    fun insert(car: Car) {
        jdbcOperations.update(
            "insert into car (id, ac_status, model) values(:id, :acStatus, :model)",
            MapSqlParameterSource()
                .addValue("acStatus", car.acStatus)
                .addValue("model", car.model)
                .addValue("id", car.id)
        )
    }

    fun selectBy(id: String): Car {
        return jdbcOperations.queryForObject(
            "select * from car where id = :id",
            mapOf("id" to id)
        ) { rs, _ ->
            Car(
                id = rs.getString("id"),
                acStatus = rs.getBoolean("ac_status"),
                model = rs.getString("model"),
            )
        }!!
    }

    fun update(car: Car) {
        jdbcOperations.update(
            "update car set ac_status = :acStatus, model = :model where id = :id",
            MapSqlParameterSource()
                .addValue("acStatus", car.acStatus)
                .addValue("model", car.model)
                .addValue("id", car.id)
        )
    }
}
