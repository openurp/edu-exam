package org.openurp.edu.exam.ws.student

import org.beangle.webmvc.api.action.ActionSupport
import org.beangle.webmvc.api.annotation.{ mapping, response }
import org.beangle.webmvc.api.view.View
import org.beangle.webmvc.api.annotation.action
import org.beangle.data.dao.EntityDao
import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.api.context.Params
import org.openurp.edu.exam.model.ExamStudent
import org.beangle.webmvc.api.annotation.param
import org.beangle.commons.collection.Properties

@action("")
class IndexWS extends ActionSupport {

  var entityDao: EntityDao = _

  @response
  @mapping("{stdCode}/{semesterCode}")
  def index(@param("stdCode") stdCode: String, @param("semesterCode") semesterCode: String): Seq[Any] = {
    val builder = OqlBuilder.from(classOf[ExamStudent], "es")
    builder.where("ws.std.user.code=:stdCode", stdCode)
    builder.where("ws.semester.code=:semesterCode", semesterCode)
    builder.where("ws.activity.state>0")
    Params.getInt("examTypeId") foreach { examTypeId =>
      builder.where("ws.activity.examType.id=:examTypeId", examTypeId)
    }
    val es = entityDao.search(builder)
    es.map(convert(_))
    null
  }

  private def convert(es: ExamStudent): Properties = {
    val props = new Properties(es, "examType.name")
    props
  }
}