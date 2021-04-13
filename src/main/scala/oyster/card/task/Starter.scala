package oyster.card.task

import oyster.card.task.domain.LocationInfo
import oyster.card.task.services.{CalculationService, Database, HashMapDatabase, SimpleTripsService, TripsPriceCalculationService, TripsService}
import oyster.card.task.utils.Transport.{Bus, Tube}
import oyster.card.task.utils.Zone.{EarlCourt, Hammersmith, Holborn}

object Starter extends App {

  val userId = "1"
  val initialMoneyAmount = 30

  val database: Database = new HashMapDatabase()
  val calculationService: CalculationService = new TripsPriceCalculationService()
  val tripsService: TripsService = new SimpleTripsService(database, calculationService)

  println(s"Initial money amount in the DB ${database.currentMoneyAmount(userId)}")
  database.changeMoneyAmount(userId, initialMoneyAmount)
  println(s"Some money were added in the DB ${database.currentMoneyAmount(userId)}")
  tripsService.addTrip(userId, LocationInfo(Holborn, EarlCourt, Tube))
  println(s"Money amount when a tube trip was added and the journey was started ${database.currentMoneyAmount(userId)}")
  tripsService.addTrip(userId, LocationInfo(EarlCourt, EarlCourt, Bus))
  println(s"Money amount when a bus trip was added ${database.currentMoneyAmount(userId)}")
  tripsService.addTrip(userId, LocationInfo(EarlCourt, Hammersmith, Tube))
  println(s"Money amount when a one more tube trip added ${database.currentMoneyAmount(userId)}")
  tripsService.finishJourney(userId)
  println(s"Money amount when the journey has finished ${database.currentMoneyAmount(userId)}")

}
