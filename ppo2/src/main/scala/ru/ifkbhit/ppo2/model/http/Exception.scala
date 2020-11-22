package ru.ifkbhit.ppo2.model.http

case class UnsupportedResponseCode(code: Int, msg: String = "") extends RuntimeException(s"Unsupported response code: $code, response: $msg")
case object BadContentException extends RuntimeException("Bad content!")
