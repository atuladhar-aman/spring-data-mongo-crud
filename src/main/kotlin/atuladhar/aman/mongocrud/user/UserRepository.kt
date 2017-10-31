package atuladhar.aman.mongocrud.user

import atuladhar.aman.mongocrud.sequence.SequenceRepository
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * @author Aman Tuladhar
 * @since 2017-10-31
 */

@Repository
interface UserRepository : MongoRepository<User, String>, UserRepositoryCustom

interface UserRepositoryCustom {
    fun save(user: User): User
}

class UserRepositoryImpl(private val mongoOperations: MongoOperations, private val sequenceRepository: SequenceRepository) : UserRepositoryCustom {

    override fun save(user: User): User {
        user.id = sequenceRepository.getNextId(User.SEQUENCE_KEY)
        mongoOperations.insert(user)
        return user
    }


}