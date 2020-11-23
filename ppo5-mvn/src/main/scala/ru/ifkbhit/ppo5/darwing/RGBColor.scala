package ru.ifkbhit.ppo5.darwing

trait RGBColor {
  def red: Int

  def green: Int

  def blue: Int
}

class HexColor(hexString: String) extends RGBColor {
  require(hexString.matches("[0-9a-fA-F]{6,6}"), "Required format #dddddd, where d - is numeric")

  private def parseSub(from: Int, to: Int): Int =
    Integer.parseInt(hexString.substring(from, to), 16)

  override def red: Int =
    parseSub(0, 2)

  override def green: Int =
    parseSub(2, 4)

  override def blue: Int =
    parseSub(4, 6)

  override def toString: String =
    s"$hexString, red: $red, green: $green, blue: $blue"
}

object RGBColor {

  case object Pink extends HexColor("EC407A")

  case object Teal extends HexColor("26A69A")

  case object BlueGrey extends HexColor("78909C")

  case object Grey extends HexColor("BDBDBD")

  case object DeepPurple extends HexColor("7E57C2")

}


