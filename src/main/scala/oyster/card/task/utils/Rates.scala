package oyster.card.task.utils

import oyster.card.task.utils.Zone.{EarlCourt, Hammersmith, Holborn, Wimbledon}

object Rates {

  val anywhereZone1Price = 2.5
  val anywhereOutsie1Price = 2
  val twoZonesIncluding1Price = 3
  val twoZonesExcluding1Price = 2.25
  val threeZonesPrice = 3.2
  val busTripPrice = 1.8

  val tubeWaysGraph: Map[Zone, Map[Zone, Double]] = Map(
    Holborn -> Map(
      Holborn -> anywhereZone1Price,
      EarlCourt -> anywhereZone1Price,
      Wimbledon -> twoZonesIncluding1Price,
      Hammersmith -> twoZonesIncluding1Price
    ),
    EarlCourt -> Map(
      Holborn -> anywhereZone1Price,
      EarlCourt -> anywhereOutsie1Price,
      Wimbledon -> twoZonesExcluding1Price,
      Hammersmith -> anywhereOutsie1Price
    ),
    Wimbledon -> Map(
      Holborn -> twoZonesExcluding1Price,
      EarlCourt -> twoZonesExcluding1Price,
      Wimbledon -> anywhereOutsie1Price,
      Hammersmith -> twoZonesExcluding1Price
    ),
    Hammersmith -> Map(
      Holborn -> twoZonesIncluding1Price,
      EarlCourt -> anywhereOutsie1Price,
      Wimbledon -> twoZonesExcluding1Price,
      Hammersmith -> anywhereOutsie1Price
    )
  )

}
