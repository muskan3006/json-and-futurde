package com.knoldus.utilities

import com.knoldus.parsing.{JsonPostData, Posts}

object UtilityPost extends BasicPost {
  val posts: List[Posts] = JsonPostData.getAllPostData

  override def getPostById(id: Double): Posts = {
    val matchedPost = for {post <- posts
                           if post.id == id
                           matchedPost = post
                           } yield matchedPost
    matchedPost.head
  }

  override def getPostsByUserId(userId: Double): List[Posts] = for {post <- posts
                                                                    if post.userId == userId
                                                                    } yield post

}
