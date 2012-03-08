import java.lang.StringBuilder
import main.scala.db.DBHandler
import org.scalatra.ScalatraServlet
import org.scalatra.antixml.AntiXmlSupport
import com.codecommit.antixml._
import org.scalaquery.session._
import org.scalaquery.session.Database.threadLocalSession

// Import the query language

import org.scalaquery.ql._

// Import the standard SQL types

import org.scalaquery.ql.TypeMapper._
import org.scalaquery.ql.extended.MySQLDriver.Implicit._

class LabScalatraServlet
  extends ScalatraServlet with AntiXmlSupport {

  get("/") {
    contentType = "text/html"
    <html>
      <body>
        <h1>Scala, rocks!</h1>
        <div style="width: 250px">
          <form action="/user/create" method="post" theme="simple">
            <table>
              <tr>
                <td style="width: 70px">first name:</td>
                <td>
                    <input type="text" name="firstName"/>
                </td>
              </tr>
              <tr>
                <td>last name:</td>
                <td>
                    <input type="text" name="lastName"/>
                </td>
              </tr>
              <tr>
                <td></td>
                <td>
                    <input type="submit" value="Create" style="float: right"/>
                </td>
              </tr>
            </table>
          </form>
        </div>
        <div style="width: 250px">
          <form action="/user/delete" method="post" theme="simple">
            <table>
              <tr>
                <td style="width: 70px">user id:</td>
                <td>
                    <input type="text" name="userId"/>
                </td>
              </tr>
              <tr>
                <td></td>
                <td>
                    <input type="submit" value="Delete" style="float: right"/>
                </td>
              </tr>
            </table>
          </form>
        </div>
        <div style="width: 250px">
          <form action="/project/create" method="post" theme="simple">
            <table>
              <tr>
                <td style="width: 70px">Proj. name:</td>
                <td>
                    <input type="text" name="projName"/>
                </td>
              </tr>
              <tr>
                <td></td>
                <td>
                    <input type="submit" value="Create project" style="float: right"/>
                </td>
              </tr>
            </table>
          </form>
        </div>
        <div style="width: 250px">
          <form action="/project/delete" method="post" theme="simple">
            <table>
              <tr>
                <td style="width: 70px">proj. id:</td>
                <td>
                    <input type="text" name="projId"/>
                </td>
              </tr>
              <tr>
                <td></td>
                <td>
                    <input type="submit" value="Delete" style="float: right"/>
                </td>
              </tr>
            </table>
          </form>
        </div>
        <div style="width: 250px">
          <form action="/user/assignproject" method="post" theme="simple">
            <table>
              <tr>
                <td style="width: 70px">User Id:</td>
                <td>
                    <input type="text" name="userId"/>
                </td>
              </tr>
              <tr>
                <td>Project Id:</td>
                <td>
                    <input type="text" name="projId"/>
                </td>
              </tr>
              <tr>
                <td></td>
                <td>
                    <input type="submit" value="Assign project" style="float: right"/>
                </td>
              </tr>
            </table>
          </form>
        </div>
        <div style="width: 250px">
          <form action="/user/unassignproject" method="post" theme="simple">
            <table>
              <tr>
                <td style="width: 70px">User Id:</td>
                <td>
                    <input type="text" name="userId"/>
                </td>
              </tr>
              <tr>
                <td>Project Id:</td>
                <td>
                    <input type="text" name="projId"/>
                </td>
              </tr>
              <tr>
                <td></td>
                <td>
                    <input type="submit" value="Unassign project" style="float: right"/>
                </td>
              </tr>
            </table>
          </form>
        </div>
      </body>
    </html>

  }

  get("/users") {
    contentType = "text/xml"


    DBHandler.db withSession {
      val result = new
          StringBuilder("<users>")

      for (user <- DBHandler.fetchUsers) {
        result append ("<user><id>")
        result append (user._1)
        result append ("</id><FirstName>")
        result append (user._2)
        result append ("</FirstName><LastName>")
        result append (user._3)
        result append ("</LastName></user>")
      }


      result append "</users>"
      XML.fromString(result toString)
    }

  }

  post("/user/create") {
    val firstName = params("firstName")
    val lastName = params("lastName")
    println(firstName)
    println(lastName)
    DBHandler.db withSession {
      DBHandler.createUser(firstName, lastName)
    }
    redirect("/")
  }

  post("/user/delete") {
    val id = params("userId")
    println(id)
    DBHandler.db withSession {
      DBHandler.deleteUser(id toInt)
    }
    redirect("/")
  }

  get("/projects") {
    contentType = "text/xml"


    DBHandler.db withSession {
      val result = new
          StringBuilder("<projects>")

      for (proj <- DBHandler.fetchProjects) {
        result append ("<project><id>")
        result append (proj._1)
        result append ("</id><Name>")
        result append (proj._2)
        result append ("</Name></project>")
      }


      result append "</projects>"
      XML.fromString(result toString)
    }

  }

  post("/project/create") {
    val projName = params("projName")
    println(projName)
    DBHandler.db withSession {
      DBHandler.createProject(projName)
    }
    redirect("/")
  }

  post("/project/delete") {
    val id = params("projId")
    println(id)
    DBHandler.db withSession {
      DBHandler.deletePoject(id toInt)
    }
    redirect("/")
  }


  get("/user/projects") {
    contentType = "text/xml"
    val userId = params("userId")
    println(userId)

    DBHandler.db withSession {
      val result = new
          StringBuilder("<userprojects>")

      for (proj <- DBHandler.fetchUserProjects(userId toInt)) {
        result append ("<project><id>")
        result append (proj._1)
        result append ("</id><Name>")
        result append (proj._2)
        result append ("</Name></project>")
      }


      result append "</userprojects>"
      XML.fromString(result toString)
    }

  }

  post("/user/assignproject") {
    val userId = params("userId")
    val projId = params("projId")
    println(userId)
    println(projId)
    DBHandler.db withSession {
      DBHandler.assignProject(userId toInt, projId toInt)
    }
    redirect("/")
  }

  post("/user/unassignproject") {
    val userId = params("userId")
    val projId = params("projId")
    println(userId)
    println(projId)
    DBHandler.db withSession {
      DBHandler.unassignProject(userId toInt, projId toInt)
    }
    redirect("/")
  }

  get("/timesheet") {
    contentType = "text/html"
    <html>
      <body>
        <h1>Scala, rocks!</h1>
        <h2>Timesheet</h2>
        <div style="width: 250px">
          <form action="/user/create" method="post" theme="simple">
            <table>
              <tr>
                <td style="width: 70px">User id:</td>
                <td>
                    <input type="text" name="userId"/>
                </td>
              </tr>
              <tr>
                <td>Project Id:</td>
                <td>
                    <input type="text" name="projId"/>
                </td>
              </tr>
              <tr>
                <td></td>
                <td>
                    <input type="submit" value="Create" style="float: right"/>
                </td>
              </tr>
            </table>
          </form>
        </div>
      </body>
    </html>

  }


}
