package oyster.card.task.utils

import enumeratum.EnumEntry.Lowercase
import enumeratum._
sealed trait Transport extends EnumEntry with Lowercase

object Transport extends Enum[Transport] {

  val values = findValues

  case object Bus  extends Transport
  case object Tube extends Transport

}
