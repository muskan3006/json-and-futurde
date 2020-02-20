package com.knoldus.parsing

import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder

trait ReadJsonData {
  def readAll(url: String): String = {
    val request = new HttpGet(url)
    val client = HttpClientBuilder.create().build()
    val response = client.execute(request)
    IOUtils.toString(response.getEntity.getContent)
  }
}
