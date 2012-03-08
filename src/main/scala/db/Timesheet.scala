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

object Timesheet extends Table[(Int, Int, Int, Int, Int, Int, Int)]("TIMESHEET") {
  def id = column[Int]("id", O AutoInc)

  def userId = column[Int]("userId", O NotNull)

  def projId = column[Int]("projId", O NotNull)

  def year = column[Int]("year", O NotNull)

  def month = column[Int]("month", O NotNull)

  def day = column[Int]("day", O NotNull)

  def hours = column[Int]("hours", O NotNull)

  def * = id ~ userId ~ projId ~ year ~ month ~ day ~ hours
}