package atuladhar.aman.mongocrud.sequence

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.test.context.junit4.SpringRunner

/**
 * @author Aman Tuladhar
 * @since 2017-11-01
 */

@RunWith(SpringRunner::class)
@SpringBootTest
class SequenceRepositoryImplTest {

    @Autowired lateinit var sequenceRepository: SequenceRepository
    @Autowired lateinit var mongoOperations: MongoOperations

    @Before
    fun setup() {
        sequenceRepository.deleteAll()
    }

    @Test
    fun firstElement() {
        val nextId = sequenceRepository.getNextId(Sequence.COLLECTION_NAME)
        assertThat(nextId, `is`(1L))
    }

    @Test
    fun getNextId() {
        mongoOperations.insert(Sequence(Sequence.COLLECTION_NAME, 3))
        val nextId = sequenceRepository.getNextId(Sequence.COLLECTION_NAME)
        assertThat(nextId, `is`(4L))
    }
}