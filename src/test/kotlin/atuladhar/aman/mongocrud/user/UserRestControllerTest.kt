package atuladhar.aman.mongocrud.user

import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print as mvcPrint

/**
 * @author Aman Tuladhar
 * @since 2017-11-01
 */

@RunWith(SpringRunner::class)
@SpringBootTest
class UserRestControllerTest {

    @Mock lateinit var userRepository: UserRepository

    lateinit var mockMvc: MockMvc

    @Before
    fun setUp() {
        reset(userRepository)

        MockitoAnnotations.initMocks(this)
        val userRestController = UserRestController(userRepository)
        mockMvc = MockMvcBuilders.standaloneSetup(userRestController).build()

    }

    @Test
    fun findAll() {
        val listOfUser = listOf(User("Aman", "aman@email.com"), User("John", "john@email.com"))

        `when`(userRepository.findAll())
                .thenReturn(listOfUser)

        mockMvc.perform(get("/users"))
                .andDo(mvcPrint())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$", hasSize<Any>(2)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("Aman", "John")))
                .andExpect(jsonPath("$[*].email", containsInAnyOrder("aman@email.com", "john@email.com")))
    }

    @Test
    fun findOne() {
        val aman = User("Aman", "aman@email.com")

        `when`(userRepository.findOne(1))
                .thenReturn(aman)

        mockMvc.perform(get("/users/1"))
                .andDo(mvcPrint())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.name", `is`("Aman")))
                .andExpect(jsonPath("$.email", `is`("aman@email.com")))
    }

    @Test
    fun insertOne() {
        val aman = User("Aman", "aman@email.com")

        `when`(userRepository.insert(aman))
                .thenReturn(aman.copy(id = 1))

        val json = ObjectMapper().writeValueAsString(aman)

        mockMvc.perform(
                post("/users")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(mvcPrint())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.name", `is`("Aman")))
                .andExpect(jsonPath("$.email", `is`("aman@email.com")))
    }

    @Test
    fun update() {
        val aman = User("Aman", "aman@email.com", 1)
        val amanUpdated = aman.copy(id = 1, name = "AmanUpdated", email = "email@update.com")

        `when`(userRepository.save(aman))
                .thenReturn(amanUpdated)

        val json = ObjectMapper().writeValueAsString(aman)

        mockMvc.perform(
                put("/users/1")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(mvcPrint())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.name", `is`("AmanUpdated")))
                .andExpect(jsonPath("$.email", `is`("email@update.com")))
    }

    @Test
    fun delete() {
        doNothing().`when`(userRepository).delete(1)
        mockMvc.perform(delete("/users/1"))
                .andDo(mvcPrint())
                .andExpect(status().isOk)
    }

}