package main.scala.db


import org.scalaquery.ql.TypeMapper._
import org.scalaquery.ql._

import org.scalaquery.ql.extended.{ExtendedTable => Table}


/**
 * Created by IntelliJ IDEA.
 * User: maaro
 * Date: 2011-11-24
 * Time: 10:26
 */

object User extends Table[(Int, String, String)]("USER")  {
    def id = column[Int]("idUser", O AutoInc)
    def first = column[String]("First Name", O Default "NFN", O DBType "varchar(64)")
    def last = column[String]("Last Name")
    def * = id ~ first ~ last
}