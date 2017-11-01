package atuladhar.aman.mongocrud.user

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * @author Aman Tuladhar
 * @since 2017-10-31
 */

@RunWith(SpringRunner::class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired lateinit var userRepository: UserRepository

    @Before
    fun setUp() = userRepository.deleteAll()


    @Test
    fun shouldInsertUserWithIdOne() {
        val user = userRepository.save(User("Aman", "aman@email.com"))
        assertThat(user.name, `is`("Aman"))
        assertThat(user.email, `is`("aman@email.com"))
    }

}