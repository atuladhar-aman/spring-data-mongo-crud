package atuladhar.aman.mongocrud.user

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * @author Aman Tuladhar
 * @since 2017-10-30
 */
@Document
data class User(var name: String, var email: String, @Id var id: Long? = null) {
    companion object {
        const val SEQUENCE_KEY = "user"
    }
}

