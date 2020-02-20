package com.knoldus.utilities

import com.knoldus.parsing.Users

trait BasicUser {
  def getUserById(id: Double): Users

  def getUserByUsername(username: String): Users

}
