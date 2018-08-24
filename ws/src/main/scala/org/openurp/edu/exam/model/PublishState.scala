package org.openurp.edu.exam.model

object PublishState extends Enumeration {
  class State(val name: String, val timePublished: Boolean, val roomPublished: Boolean) extends super.Val {
  }

  val None = new State("未发布", false, false)
  val TimeOnly = new State("仅发布时间", true, false)
  val TimeAndRoom = new State("发布时间地点", true, true)

}