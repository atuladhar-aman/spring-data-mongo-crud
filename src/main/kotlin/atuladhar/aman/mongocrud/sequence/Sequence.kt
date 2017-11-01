package atuladhar.aman.mongocrud.sequence

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * @author Aman Tuladhar
 * @since 2017-10-31
 * @reference https://www.mkyong.com/mongodb/spring-data-mongodb-auto-sequence-id-example/
 */

@Document(collection = Sequence.COLLECTION_NAME)
data class Sequence(@Id @Field(Sequence.SEQUENCE_KEY) val sequenceKey: String, @Field(Sequence.NEXT_ID) val nextId: Long) {
    companion object {
        const val COLLECTION_NAME = "sequence"
        const val SEQUENCE_KEY = "sequenceKey"
        const val NEXT_ID = "nextId"
    }
}

interface SequenceRepository : MongoRepository<Sequence, Long>, SequenceRepositoryCustom

interface SequenceRepositoryCustom {
    @Throws(SequenceException::class)
    fun getNextId(key: String): Long
}

@Repository
class SequenceRepositoryImpl(val mongoOperations: MongoOperations) : SequenceRepositoryCustom {
    @Throws(SequenceException::class)
    override fun getNextId(key: String): Long {

        //get sequence id
        val query = Query(where("_id").`is`(key))

        //increase sequence id by 1
        val update = Update().inc(Sequence.NEXT_ID, 1)

        //return new increased id
        val options = FindAndModifyOptions().returnNew(true)

        // if not exist insert it
        if (notExistingSequence(key)) {
            mongoOperations.insert(Sequence(key, 0), Sequence.COLLECTION_NAME)
        }

        //this is the magic happened.
        val seqId: Sequence = mongoOperations.findAndModify(query, update, options, Sequence::class.java)
                ?: throw SequenceException("Sequence not found for key $key")
        return seqId.nextId
    }

    private fun notExistingSequence(key: String) =
            mongoOperations.find(Query(where(Sequence.SEQUENCE_KEY).`is`(key)), Sequence::class.java).size <= 0
}

class SequenceException(override val message: String) : RuntimeException(message)
