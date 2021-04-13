package oyster.card.task.services

import org.scalamock.matchers.Matchers
import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import oyster.card.task.domain.LocationInfo
import oyster.card.task.utils.Rates._
import oyster.card.task.utils.Transport.{Bus, Tube}
import oyster.card.task.utils.Zone._

class TripsPriceCalculationServiceSpec extends AnyFlatSpec with Matchers with MockFactory {

  behavior of "TripsPriceCalculationService"

  val calculationService = new TripsPriceCalculationService()

  "calculatePrice" should "get bus price if only buss trips are presented" in {
    val result = calculationService.calculatePrice(List(
      LocationInfo(Holborn, EarlCourt, Bus),
      LocationInfo(Wimbledon, Hammersmith, Bus),
      LocationInfo(Holborn, EarlCourt, Bus)
    ))

    result shouldBe busTripPrice
  }

  "calculatePrice" should "throw error if trips list is empty" in {
    try {
      calculationService.calculatePrice(Nil)
    } catch {
      case ex: Throwable =>
        ex.getMessage shouldBe "Calculating max fair for an empty list of trips"
    }
  }

  "getFare" should "calculate correct price" in {
    val result = calculationService.calculatePrice(List(
      LocationInfo(Holborn, EarlCourt, Tube),
      LocationInfo(EarlCourt, EarlCourt, Bus),
      LocationInfo(EarlCourt, Hammersmith, Tube)
    ))

    result shouldBe twoZonesIncluding1Price
  }

  "getFare" should "get correct tube fare" in {
    val result = calculationService.getFare(LocationInfo(Holborn, EarlCourt, Tube))

    result shouldBe threeZonesPrice
  }

  "getFare" should "get correct bus fare" in {
    val result = calculationService.getFare(LocationInfo(Holborn, EarlCourt, Bus))

    result shouldBe busTripPrice
  }

  "getMaxFare" should "get correct tube fare" in {
    val result = calculationService.getMaxFare(List(
      LocationInfo(Holborn, EarlCourt, Tube),
      LocationInfo(EarlCourt, EarlCourt, Bus),
      LocationInfo(EarlCourt, Hammersmith, Tube)
    ))

    result shouldBe threeZonesPrice
  }

  "getMaxFare" should "get correct bus fare" in {
    val result = calculationService.getMaxFare(List(
      LocationInfo(Holborn, EarlCourt, Bus),
      LocationInfo(EarlCourt, EarlCourt, Bus),
      LocationInfo(EarlCourt, Hammersmith, Bus)
    ))

    result shouldBe busTripPrice
  }

}