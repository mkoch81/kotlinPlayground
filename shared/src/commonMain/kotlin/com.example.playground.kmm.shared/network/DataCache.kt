package com.example.playground.kmm.shared.network

import com.example.playground.kmm.shared.cache.Database
import com.example.playground.kmm.shared.cache.DatabaseDriverFactory
import com.example.playground.kmm.shared.entity.Post

// only available on the native platforms
// since it requires the corresponding database driver
// yet providing a valid service that can be managed centrally
class DataCache (databaseDriverFactory: DatabaseDriverFactory) {

    private val database = Database(databaseDriverFactory)
    private val api = Api()

    @Throws(Exception::class)
    suspend fun getPosts(reload:Boolean): List<Post> {
        val cachedPosts = database.getAllPosts()
        return if (cachedPosts.isNotEmpty() && !reload) {
            cachedPosts
        } else {
            api.getAllPosts().asList().also {
                database.clearDatabase()
                database.createPosts(it)
            }
        }
    }
}