package oyster.card.task.services

import oyster.card.task.domain.LocationInfo
import oyster.card.task.utils.Rates._
import oyster.card.task.utils.Transport.{Bus, Tube}
import oyster.card.task.utils.Zone

trait CalculationService {
  def calculatePrice(trips: List[LocationInfo]): Double
  def getFare(locationInfo: LocationInfo): Double
  def getMaxFare(trips: List[LocationInfo]): Double
}

class TripsPriceCalculationService extends CalculationService {

  override def calculatePrice(trips: List[LocationInfo]): Double =
    if (trips.isEmpty) {
      throw new Exception("Calculating max fair for an empty list of trips")
    } else {
      val tubeTrips = trips.filter(_.transport == Tube)
      if (tubeTrips.isEmpty) {
        busTripPrice
      } else {
        val zones = trips.map(_.start) ++ trips.map(_.destination)
        calculateMaximalPrice(zones.toIndexedSeq)
      }
  }

  private def calculateMaximalPrice(zones: IndexedSeq[Zone]): Double = {
    zones.indices.flatMap { index =>
      val currentLocation = zones(index)
      zones.drop(index).flatMap(calculateMaximalPrice(_, currentLocation))
    }
  }.max

  private def calculateMaximalPrice(firstLocation: Zone, secondLocation: Zone): Option[Double] =
    tubeWaysGraph.get(firstLocation).flatMap(_.get(secondLocation))

  override def getFare(locationInfo: LocationInfo): Double = {
    locationInfo.transport match {
      case Tube => threeZonesPrice
      case Bus => busTripPrice
    }
  }

  override def getMaxFare(trips: List[LocationInfo]): Double = {
    if (trips.isEmpty) {
      throw new Exception("Calculating max fair for an empty list of trips")
    } else {
      if (trips.exists(_.transport == Tube)) {
        threeZonesPrice
      } else {
        busTripPrice
      }
    }
  }

}
