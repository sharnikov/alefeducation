package oyster.card.task.services

import scala.collection.mutable

trait Database {
  def changeMoneyAmount(userId: String, moneyAmount: Double): Boolean
  def currentMoneyAmount(userId: String): Double
}

class HashMapDatabase extends Database {

  private val data: mutable.HashMap[String, Double] = new mutable.HashMap()

  override def changeMoneyAmount(userId: String, moneyAmount: Double): Boolean = synchronized {
    val currentMoneyAmount = data.getOrElse(userId, 0d)
    val resultMoneyAmount = currentMoneyAmount + moneyAmount
    if (resultMoneyAmount < 0) {
      false
    } else {
      data.update(userId, resultMoneyAmount)
      true
    }
  }

  override def currentMoneyAmount(userId: String): Double =
    data.getOrElse(userId, 0)


}
