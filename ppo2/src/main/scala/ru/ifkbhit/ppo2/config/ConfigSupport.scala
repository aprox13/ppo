package ru.ifkbhit.ppo2.config

import com.typesafe.config.ConfigFactory

object ConfigSupport {

  private lazy val config = ConfigFactory.load()

  implicit class ConfigHelper(sc: StringContext) {
    def ps(args: Any*): String = config.getString(sc.raw(args: _*))

    def pi(args: Any*): Int = config.getInt(sc.raw(args: _*))
  }


}
