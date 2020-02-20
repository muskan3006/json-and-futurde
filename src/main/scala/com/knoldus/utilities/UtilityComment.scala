package com.knoldus.utilities

import com.knoldus.parsing.{Comments, JsonCommentData}
import scala.concurrent.ExecutionContext.Implicits.global

object UtilityComment extends BasicComment {
  val comments = JsonCommentData.getAllCommentData

  override def getCommentById(id: Double): Comments = {
    val matchedComment = for {comment <- comments
                              if comment.id == id
                              matchedComment = comment
                              } yield matchedComment
    matchedComment.head
  }

  override def getCommentByPostId(postId: Double): List[Comments] = for {comment <- comments
                                                                         if comment.postId == postId
                                                                         } yield comment

}
