package com.example.playground

import com.example.playground.kmm.shared.entity.Album
import com.example.playground.kmm.shared.entity.Comment
import com.example.playground.kmm.shared.entity.Post


data class Info (var color:String, var title:String, var content:String, var count:Int)

object Evaluator {

    // as singleton just simulating some evaluation process after data fetching
    // here mapping data into general object
    // could have been inheritance but didn't want to implement an own API to adjust
    fun evaluate(items: Array<Post>):Info {
        return Info(color = "primary", title = "HTTP content: ", content = items[0].body, count = items.count())
    }
    fun evaluate(items: Array<Comment>):Info {
        return Info(color = "secondary", title = "WIFI content: ", content = items[0].body, count = items.count())
    }
    fun evaluate(items: Array<Album>):Info {
        return Info(color = "tertiary", title = "BT content: ", content = items[0].title, count = items.count())
    }
}