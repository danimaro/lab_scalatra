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

object Project extends Table[(Int, String)]("PROJECT")  {
    def id = column[Int]("idProject", O AutoInc)
    def projName = column[String]("Project Name", O Default "NFN", O DBType "varchar(64)")
    def * = id ~ projName
}