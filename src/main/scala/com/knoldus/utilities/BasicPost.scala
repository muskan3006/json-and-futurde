package com.knoldus.utilities

import com.knoldus.parsing.Posts

import scala.concurrent.Future

trait BasicPost {
  def getPostsByUserId(userId: Double): List[Posts]

  def getPostById(id: Double): Posts
}
