package atuladhar.aman.mongocrud.user

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author Aman Tuladhar
 * @since 2017-10-31
 */

@RestController
@RequestMapping("/users")
class UserRestController(private val userRepository: UserRepository) {
    @GetMapping
    fun save(): ResponseEntity<User> {
        val save = userRepository.save(User("Aman", "aman@email.com"))
        return ResponseEntity.ok(save)
    }
}