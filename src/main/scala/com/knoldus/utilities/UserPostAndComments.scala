package com.knoldus.utilities

import com.knoldus.parsing.{Comments, Posts, Users}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class CommentsWithPosts(post: Posts, comments: List[Comments])

case class PostCount(user: Users, countOfPosts: Long)

case class CommentCount(post: Posts, countOfComment: Long)

case class UserWithPostsAndComments(user: Users, post: List[Posts], comment: List[Comments])

class UserPostAndComments {
  val listOfUsers: List[Users] = UtilityUser.users
  val listOfPosts: List[Posts] = UtilityPost.posts
  val listOfComments: List[Comments] = UtilityComment.comments

  val postsAndItsComments: List[CommentsWithPosts] = {
    for {post <- listOfPosts
         } yield CommentsWithPosts(post, UtilityComment.getCommentByPostId(post.id))

  }

  val userAndItsPostsAndComments = for {user: Users <- listOfUsers
                                                                        post <- listOfPosts
                                                                        comment <- listOfComments
                                                                        if user.id == post.userId && post.id == comment.postId
                                                                        } yield UserWithPostsAndComments(
    user, UtilityPost.getPostsByUserId(user.id),
    UtilityComment.getCommentByPostId(post.id))

  val commentCount: List[CommentCount] = {
    for {singlePost <- postsAndItsComments} yield CommentCount(singlePost.post, singlePost.comments.length)
  }
  val postCount: List[PostCount] = for {singleUser <- userAndItsPostsAndComments} yield PostCount(singleUser.user, singleUser.post.length)
  val result1: String = s"user who has max post : $userWithMaxPost"
  val result2: String = s"user whose post has max comments : $userWithMaxCommentsOnPost"

  def userWithMaxPost: (String, Long) = {
    val sortedByNoOfPosts = postCount.sortBy(_.countOfPosts)
    (sortedByNoOfPosts.reverse.head.user.name, sortedByNoOfPosts.reverse.head.countOfPosts)
  }

  def userWithMaxCommentsOnPost: (String, Double) = {
    val sortedComments = commentCount.sortBy(_.countOfComment)
    val mostCommentedPost = sortedComments.head
    val result = for {user <- listOfUsers
                      if mostCommentedPost.post.userId == user.id
                      result = UtilityUser.getUserById(user.id)
                      } yield result
    (result.reverse.head.name, result.reverse.head.id)
  }
}

object A extends App {
  val userPostAndComments = new UserPostAndComments
//  userPostAndComments.commentCount
//  userPostAndComments.postCount
//  userPostAndComments.postsAndItsComments
//  userPostAndComments.userAndItsPostsAndComments
//  userPostAndComments.userWithMaxCommentsOnPost
//  userPostAndComments.userWithMaxPost
println(userPostAndComments.result1,userPostAndComments.result2)
}
