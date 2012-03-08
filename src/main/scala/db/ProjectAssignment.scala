package main.scala.db

/**
 * Created by IntelliJ IDEA.
 * User: roozbehmaadani
 * Date: 3/7/12
 * Time: 8:56 PM
 */


import org.scalaquery.ql.TypeMapper._
import org.scalaquery.ql._

import org.scalaquery.ql.extended.{ExtendedTable => Table}

object ProjectAssignment extends Table[(Int, Int, Int)]("PROJECTASSIGNMENT") {
  def id = column[Int]("id", O AutoInc)

  def userId = column[Int]("userId", O NotNull)

  def projId = column[Int]("projId", O NotNull)

  def * = id ~ userId ~ projId
}