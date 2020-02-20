package com.knoldus.parsing

import net.liftweb.json.DefaultFormats

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.implicitConversions

case class Comments(postId: Double, id: Double, name: String, email: String, body: String)

object JsonCommentData extends ReadJsonData{
  implicit val formats: DefaultFormats.type = DefaultFormats
  def getAllCommentData: Future[List[Comments]] = {
    val url = "https://jsonplaceholder.typicode.com/comments"
    val data = Future {
      readAll(url)
    }

    val parsedCommentData = for {commentData <- data
                                 parsedCommentData <- Future(JsonCommentData.parse(commentData))
                                 } yield parsedCommentData
    parsedCommentData
  }

  def parse(commentData: String): List[Comments] = {
    val parsedCommentData = net.liftweb.json.parse(commentData)
    parsedCommentData.children map { comments =>
      comments.extract[Comments]
    }
  }
}
