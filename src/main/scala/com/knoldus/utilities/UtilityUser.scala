package com.knoldus.utilities

import com.knoldus.parsing.{JsonUserData, Users}

object UtilityUser extends BasicUser {
  val users: List[Users] = JsonUserData.getAllUserData

  override def getUserById(id: Double): Users = {
    val matchedUser = for {user <- users
                           if user.id == id
                           matchedUser = user
                           } yield matchedUser
    matchedUser.head
  }

  override def getUserByUsername(username: String): Users = {
    val matchedUser = for {user <- users
                           if user.username == username
                           matchedUser = user
                           } yield matchedUser
    matchedUser.head

  }
}
