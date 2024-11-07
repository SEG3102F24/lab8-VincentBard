package seg3x02.employeeGql.resolvers

import org.springframework.stereotype.Controller
import org.springframework.stereotype.Component
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import seg3x02.employeeGql.repository.EmployeesRepository
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput
import java.util.UUID;


@Controller
class EmployeesResolver  (
    private val employeesRepository: EmployeesRepository,
    private val mongoOperations: MongoOperations
){


    @QueryMapping
    fun employees(): List<Employee> {
        return employeesRepository.findAll()
    }

    @MutationMapping
    fun newEmployee(@Argument("createEmployeeInput") input: CreateEmployeeInput): Employee {
        if (input.name != null && input.dateOfBirth != null && input.city != null && input.salary != null) {
            val employee = Employee(
                name = input.name,
                dateOfBirth = input.dateOfBirth,
                city = input.city,
                salary = input.salary,
                gender = input.gender,
                email = input.email
            )
            employee.id = UUID.randomUUID().toString()
            employeesRepository.save(employee)
            return employee
        } else {
            throw IllegalArgumentException("Invalid input")
        }
    }

}
