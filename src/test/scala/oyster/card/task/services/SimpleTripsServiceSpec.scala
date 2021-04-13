package oyster.card.task.services

import org.scalamock.matchers.Matchers
import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import oyster.card.task.domain.LocationInfo
import oyster.card.task.utils.Transport.Bus
import oyster.card.task.utils.Zone.{EarlCourt, Holborn, Wimbledon}

class SimpleTripsServiceSpec extends AnyFlatSpec with Matchers with MockFactory {

  behavior of "SimpleTripsService"

  trait mocks {
    val userId = "1"
    val money = 1000

    val database = stub[Database]
    val calculationService = stub[CalculationService]
    val tripService = new SimpleTripsService(database, calculationService)

    val locationInfo = LocationInfo(Holborn, EarlCourt, Bus)
    val locationInfo2 = LocationInfo(Holborn, Wimbledon, Bus)
    val locationInfos = List(locationInfo, locationInfo2)

    val fare = 5
    val biggerFare = 15
    val realPrice = 11
    val balanceAfterPayment = money - realPrice
    val fareDifference = biggerFare - fare
  }

  "addTrip" should "add a trip for a new user and save a fare" in new mocks {

    (calculationService.getFare _).when(locationInfo).returning(fare)
    (database.changeMoneyAmount _).when(userId, -fare).returning(true)

    tripService.addTrip(userId, locationInfo)

    (calculationService.getFare _).verify(*).once()
    (database.changeMoneyAmount _).verify(*, *).once()
  }

  "addTrip" should "not recalculate the fare when a fare with the same value incoming" in new mocks {
    (calculationService.getFare _).when(locationInfo).returning(fare)
    (calculationService.getFare _).when(locationInfo2).returning(fare)
    (database.changeMoneyAmount _).when(userId, -fare).returning(true)

    tripService.addTrip(userId, locationInfo)
    tripService.addTrip(userId, locationInfo2)

    (calculationService.getFare _).verify(*).twice()
    (database.changeMoneyAmount _).verify(*, *).once()
  }

  "addTrip" should "recalculate the fare when a fare with the bigger value incoming" in new mocks {
    (calculationService.getFare _).when(locationInfo).returning(fare)
    (calculationService.getFare _).when(locationInfo2).returning(biggerFare)
    (database.changeMoneyAmount _).when(userId, -fare).returning(true)
    (database.changeMoneyAmount _).when(userId, -fareDifference).returning(true)

    tripService.addTrip(userId, locationInfo)
    tripService.addTrip(userId, locationInfo2)

    (calculationService.getFare _).verify(*).twice()
    (database.changeMoneyAmount _).verify(*, *).twice()
  }


  "finishJourney" should "return the current balance and release the cache" in new mocks {
    (calculationService.getFare _).when(locationInfo).returning(fare)
    (calculationService.getFare _).when(locationInfo2).returning(biggerFare)
    (calculationService.calculatePrice _).when(locationInfos).returning(realPrice)
    (database.changeMoneyAmount _).when(userId, -fare).returning(true)
    (database.changeMoneyAmount _).when(userId, -fareDifference).returning(true)
    (database.changeMoneyAmount _).when(userId, biggerFare - realPrice).returning(true)
    (database.currentMoneyAmount _).when(userId).returning(balanceAfterPayment)

    tripService.addTrip(userId, locationInfo)
    tripService.addTrip(userId, locationInfo2)

    tripService.finishJourney(userId) shouldBe balanceAfterPayment

    (calculationService.getMaxFare _).verify(*).never()
    (calculationService.getFare _).verify(*).twice()
    (calculationService.calculatePrice _).verify(*).once()
    (database.changeMoneyAmount _).verify(*, *).repeat(3)
    (database.currentMoneyAmount _).verify(*).once()
  }


}
