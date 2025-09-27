package com.calyrsoft.ucbp1.features.post.data.api

import com.calyrsoft.ucbp1.features.post.data.api.dto.CommentDto
import com.calyrsoft.ucbp1.features.post.data.api.dto.PostDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PostsService {
    @GET("posts")
    suspend fun getPosts(): Response<List<PostDto>>

    @GET("comments")
    suspend fun getCommentsByPostId(@Query("postId") postId: Int): Response<List<CommentDto>>
}