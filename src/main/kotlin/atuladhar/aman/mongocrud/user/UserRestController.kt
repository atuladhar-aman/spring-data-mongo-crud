package atuladhar.aman.mongocrud.user

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * @author Aman Tuladhar
 * @since 2017-10-31
 */

@RestController
@RequestMapping("/users")
class UserRestController(private val userRepository: UserRepository) {
    @GetMapping
    fun findAll(): ResponseEntity<List<User>> = ResponseEntity.ok(userRepository.findAll())

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: Long): ResponseEntity<User> = ResponseEntity.ok(userRepository.findOne(id))

    @PostMapping
    fun insertOne(@RequestBody user: User): ResponseEntity<User> {
        return ResponseEntity.ok(userRepository.insert(user))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody user: User): ResponseEntity<User> = ResponseEntity.ok(userRepository.save(user))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> = ResponseEntity.ok(userRepository.delete(id))

}