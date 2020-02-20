package com.knoldus.parsing

import net.liftweb.json.DefaultFormats

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.implicitConversions

case class Posts(userId: Double, id: Double, title: String, body: String)

object JsonPostData extends ReadJsonData  {
  implicit val formats: DefaultFormats.type = DefaultFormats
  def getAllPostData: Future[List[Posts]] = {
    val url = "https://jsonplaceholder.typicode.com/posts"

    val data = Future {
      readAll(url)
    }

    val parsedPostData = for {postData <- data
                              parsedPostData <- Future(JsonPostData.parse(postData))
                              } yield parsedPostData


    parsedPostData
  }
  def parse(postData: String): List[Posts] = {
    val parsedPstData = net.liftweb.json.parse(postData)
    parsedPstData.children map { posts =>
     posts.extract[Posts]
    }
  }
}
