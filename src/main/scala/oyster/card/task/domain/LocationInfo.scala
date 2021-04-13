package oyster.card.task.domain

import oyster.card.task.utils.{Transport, Zone}

case class LocationInfo(start: Zone, destination: Zone, transport: Transport)
