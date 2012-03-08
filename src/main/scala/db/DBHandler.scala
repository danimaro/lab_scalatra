package main.scala.db

import org.scalaquery.session._
import org.scalaquery.session.Database.threadLocalSession
import java.util.List

// Import the query language

import org.scalaquery.ql._

// Import the standard SQL types

import org.scalaquery.ql.TypeMapper._
import org.scalaquery.ql.extended.MySQLDriver.Implicit._

/**
 * Created by IntelliJ IDEA.
 * User: maaro
 * Date: 2011-11-24
 * Time: 09:56
 */
object DBHandler {

  val db = Database.forURL("jdbc:mysql://localhost/scalatra?user=roozbeh&password=12345",
    driver = "com.mysql.jdbc.Driver")

  def fetchUsers =
    for (u <- User) yield
      (u.id ~ u.first ~ u.last)

  def createUser(fName: String, lName: String) =
    User.first ~ User.last insert (fName, lName)

  def deleteUser(id: Int) {
    val user = for {u <- User if u.id === id} yield u
    user.delete
  }

  def fetchProjects =
    for (p <- Project) yield (p.id ~ p.projName)

  def createProject(projName: String) =
    Project.projName insert (projName)


  def deletePoject(id: Int) {
    val proj = for {p <- Project if p.id === id} yield p
    proj.delete
  }


  def fetchUserProjects(userId: Int) = for {
    pas <- ProjectAssignment if pas.userId === userId
    p <- Project if p.id === pas.projId
  } yield (p.id ~ p.projName)

  def assignProject(userId: Int, projId: Int) =
    ProjectAssignment.userId ~ ProjectAssignment.projId insert (userId, projId)

  def unassignProject(userId: Int, projId: Int) {
    val proj = for {project <- ProjectAssignment if project.projId === projId && project.userId === userId} yield project
    proj delete
  }


}