package com.example.playground

import com.example.playground.kmm.shared.entity.Album
import com.example.playground.kmm.shared.entity.Comment
import com.example.playground.kmm.shared.entity.Post
import com.example.playground.kmm.shared.network.Api


enum class ActiveSource {
    HTTP, WIFI, BT
}

class CommunicationManager {

    // responsibility for the source of the data
    // simulated here by switching the API endpoints
    // and returning an agreed on general Info object the UI can rely on
    // independent from the real data object being treated here
    private var activeSource:ActiveSource = ActiveSource.HTTP

    @Throws(Exception::class)
    suspend fun getData(): Info {

        when (activeSource) {
            ActiveSource.HTTP -> {
                val posts:Array<Post> = Api().getAllPosts()
                return Evaluator.evaluate(posts)
            }
            ActiveSource.WIFI -> {
                val comments:Array<Comment> = Api().getAllComments()
                return Evaluator.evaluate(comments)
            }
            ActiveSource.BT -> {
                val albums:Array<Album> = Api().getAllAlbums()
                return Evaluator.evaluate(albums)
            }
        }
    }

    // used in native Apps
    fun switchSource(source:ActiveSource) {
        activeSource = source
    }
}