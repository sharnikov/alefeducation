package oyster.card.task.services

import org.scalamock.matchers.Matchers
import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

class HashMapDatabaseSpec extends AnyFlatSpec with Matchers with MockFactory {

  behavior of "HashMapDatabase"

  trait mocks {
    val userId = "1"
    val money = 1000
    val database = new HashMapDatabase()

    database.changeMoneyAmount(userId, money)
  }

  "currentMoneyAmount" should "return zero balance for a non existing user" in new mocks {
    database.currentMoneyAmount("2") shouldBe 0
  }

  "currentMoneyAmount" should "return balance for an existing user" in new mocks {
    database.currentMoneyAmount(userId) shouldBe money
  }

  "currentMoneyAmount" should "change balance" in new mocks {
    database.changeMoneyAmount(userId, -500)
    database.currentMoneyAmount(userId) shouldBe 500
  }
}
