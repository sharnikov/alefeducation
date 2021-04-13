package oyster.card.task.services

import oyster.card.task.domain.LocationInfo

import scala.collection.mutable

trait TripsService {
  def addTrip(userId: String, locationInfo: LocationInfo): Unit
  def finishJourney(userId: String): Double
}

class SimpleTripsService(database: Database, calculationService: CalculationService) extends TripsService {
  private val trips: mutable.HashMap[String, List[LocationInfo]] = new mutable.HashMap()
  private val fares: mutable.HashMap[String, Double] = new mutable.HashMap()

  override def addTrip(userId: String, locationInfo: LocationInfo): Unit = synchronizedFunction { () =>
    if (trips.contains(userId)) {
      updateFareProbably(userId, locationInfo)
    } else {
      val fare = calculationService.getFare(locationInfo)
      updateFare(userId, fare)
    }
    addTripToCache(userId, locationInfo)
  }

  private def updateFareProbably(userId: String, locationInfo: LocationInfo): Unit = {
    val currentFare = fares.getOrElse(userId, throw new Exception(s"Cache is missing fare for user $userId"))
    val newFare = calculationService.getFare(locationInfo)
    if (newFare > currentFare) {
      val difference = newFare - currentFare
      updateFare(userId, difference)
    }
  }

  private def updateFare(userId: String, fare: Double): Unit = {
    val isCharged = database.changeMoneyAmount(userId, -fare)

    if (!isCharged) {
      throw new Exception("Not enough money")
    } else {
      fares.put(userId, fare)
    }
  }

  private def addTripToCache(userId: String, locationInfo: LocationInfo): Unit = {
    trips.get(userId).fold(trips.put(userId, List(locationInfo))) { existingTrips =>
      trips.put(userId, locationInfo :: existingTrips)
    }
  }

  override def finishJourney(userId: String): Double = synchronizedFunction { () =>
    trips.get(userId).fold(0d) { existingTrips =>
      val fare = fares.getOrElse(userId, calculationService.getMaxFare(existingTrips))
      val realPrice = calculationService.calculatePrice(existingTrips)
      val moneyToReturn = fare - realPrice
      if (moneyToReturn != 0) {
        database.changeMoneyAmount(userId, moneyToReturn)
      }
      trips.remove(userId)
      fares.remove(userId)
      database.currentMoneyAmount(userId)
    }
  }

  private def synchronizedFunction[T](f: () => T): T = synchronized {
    f()
  }
}
