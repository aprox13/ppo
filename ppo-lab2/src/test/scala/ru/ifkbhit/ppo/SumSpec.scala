package ru.ifkbhit.ppo

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class SumSpec extends WordSpec with Matchers {

  "d" should {
    "coor" in {
      val s = new Sum

      s.sum(1, 2) shouldBe 3
    }
  }
}
