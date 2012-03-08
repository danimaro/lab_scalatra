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
                <td>Year:</td>
                <td>
                    <input type="text" name="year"/>
                </td>
              </tr>
              <tr>
                <td>Month:</td>
                <td>
                    <input type="text" name="month"/>
                </td>
              </tr>
              <tr/>
              <tr>
                <td>Day:</td>
                <td></td>
                <td>1</td>
                <td>
                    <input type="text" name="day_1" style="width: 20px"/>
                </td>
                <td>2</td>
                <td>
                    <input type="text" name="day_2" style="width: 20px"/>
                </td>
                <td>3</td>
                <td>
                    <input type="text" name="day_3" style="width: 20px"/>
                </td>
                <td>4</td>
                <td>
                    <input type="text" name="day_4" style="width: 20px"/>
                </td>
                <td>5</td>
                <td>
                    <input type="text" name="day_5" style="width: 20px"/>
                </td>
                <td>6</td>
                <td>
                    <input type="text" name="day_6" style="width: 20px"/>
                </td>
                <td>7</td>
                <td>
                    <input type="text" name="day_7" style="width: 20px"/>
                </td>

              </tr>
              <tr>
                <td></td>
                <td></td>
                <td>8</td>
                <td>
                    <input type="text" name="day_8" style="width: 20px"/>
                </td>
                <td>9</td>
                <td>
                    <input type="text" name="day_9" style="width: 20px"/>
                </td>
                <td>10</td>
                <td>
                    <input type="text" name="day_10" style="width: 20px"/>
                </td>
                <td>11</td>
                <td>
                    <input type="text" name="day_11" style="width: 20px"/>
                </td>
                <td>12</td>
                <td>
                    <input type="text" name="day_12" style="width: 20px"/>
                </td>
                <td>13</td>
                <td>
                    <input type="text" name="day_13" style="width: 20px"/>
                </td>
                <td>14</td>
                <td>
                    <input type="text" name="day_14" style="width: 20px"/>
                </td>

              </tr>
              <tr>
                <td></td>
                <td></td>
                <td>15</td>
                <td>
                    <input type="text" name="day_15" style="width: 20px"/>
                </td>
                <td>16</td>
                <td>
                    <input type="text" name="day_16" style="width: 20px"/>
                </td>
                <td>17</td>
                <td>
                    <input type="text" name="day_17" style="width: 20px"/>
                </td>
                <td>18</td>
                <td>
                    <input type="text" name="day_18" style="width: 20px"/>
                </td>
                <td>19</td>
                <td>
                    <input type="text" name="day_19" style="width: 20px"/>
                </td>
                <td>20</td>
                <td>
                    <input type="text" name="day_20" style="width: 20px"/>
                </td>
                <td>21</td>
                <td>
                    <input type="text" name="day_21" style="width: 20px"/>
                </td>

              </tr>
              <tr>
                <td></td>
                <td></td>
                <td>22</td>
                <td>
                    <input type="text" name="day_22" style="width: 20px"/>
                </td>
                <td>23</td>
                <td>
                    <input type="text" name="day_23" style="width: 20px"/>
                </td>
                <td>24</td>
                <td>
                    <input type="text" name="day_24" style="width: 20px"/>
                </td>
                <td>25</td>
                <td>
                    <input type="text" name="day_25" style="width: 20px"/>
                </td>
                <td>26</td>
                <td>
                    <input type="text" name="day_26" style="width: 20px"/>
                </td>
                <td>27</td>
                <td>
                    <input type="text" name="day_27" style="width: 20px"/>
                </td>
                <td>28</td>
                <td>
                    <input type="text" name="day_28" style="width: 20px"/>
                </td>

              </tr>
              <tr>
                <td></td>
                <td></td>
                <td>29</td>
                <td>
                    <input type="text" name="day_29" style="width: 20px"/>
                </td>
                <td>30</td>
                <td>
                    <input type="text" name="day_30" style="width: 20px"/>
                </td>
                <td>31</td>
                <td>
                    <input type="text" name="day_31" style="width: 20px"/>
                </td>

              </tr>
              <tr>
                <td></td>
                <td>
                    <input type="submit" value="Send" style="float: right"/>
                </td>
              </tr>
            </table>
          </form>
        </div>
      </body>
    </html>

  }


}
