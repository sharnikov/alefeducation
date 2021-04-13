package oyster.card.task.utils

import enumeratum._

sealed trait Zone extends EnumEntry

object Zone extends Enum[Zone] {

  val values = findValues

  case object Holborn     extends Zone
  case object EarlCourt   extends Zone
  case object Wimbledon   extends Zone
  case object Hammersmith extends Zone

}

