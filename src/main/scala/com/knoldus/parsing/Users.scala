package com.knoldus.parsing

import net.liftweb.json.DefaultFormats

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.implicitConversions

case class Geo(lat: String, lng: String)

case class Address(street: String, suite: String, city: String, zipCode: String, geo: Geo)

case class Company(name: String, catchPhase: String, bs: String)

case class Users(id: Double, name: String, username: String, email: String, address: Address, phone: String, website: String, company: Company)


object JsonUserData extends ReadJsonData {

  def getAllUserData: Future[List[Users]] = {
    val url = "https://jsonplaceholder.typicode.com/users"

    val data: Future[String] = Future {
      readAll(url)
    }
    val parsedUserData = for {userData <- data
                              parsedUserData <- Future(JsonUserData.parse(userData))
                              } yield parsedUserData

    parsedUserData
  }

  implicit val formats: DefaultFormats.type = DefaultFormats

  def parse(userData: String): List[Users] = {
    val parsedUserData = net.liftweb.json.parse(userData)
    parsedUserData.children map { user =>

      val id = (user \ "id").extract[Double]
      val name = (user \ "name").extract[String]
      val username = (user \ "username").extract[String]
      val email = (user \ "email").extract[String]
      val street = (user \ "address" \ "street").extract[String]
      val suite = (user \ "address" \ "suite").extract[String]
      val city = (user \ "address" \ "city").extract[String]
      val zipcode = (user \ "address" \ "zipcode").extract[String]
      val lat = (user \ "address" \ "geo" \ "lat").extract[String]
      val lng = (user \ "address" \ "geo" \ "lng").extract[String]
      val phone = (user \ "phone").extract[String]
      val website = (user \ "website").extract[String]
      val companyName = (user \ "company" \ "name").extract[String]
      val catchPhrase = (user \ "company" \ "catchPhrase").extract[String]
      val bs = (user \ "company" \ "bs").extract[String]
      Users(id, name, username, email, Address(street, suite, city, zipcode, Geo(lat, lng)), phone, website, Company(companyName, catchPhrase, bs))
    }

  }
}

