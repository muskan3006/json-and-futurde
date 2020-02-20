package com.knoldus.utilities

import com.knoldus.parsing.Comments

trait BasicComment {
  def getCommentByPostId(postId: Double): List[Comments]
}
