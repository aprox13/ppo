package ru.ifkbhit.ppo5.darwing

import scala.collection.mutable

trait ActionsStorage[A] {

  private val storage: mutable.Buffer[A => Unit] = mutable.Buffer.empty[A => Unit]

  def addAction(action: A => Unit): Unit = storage += action

  def runActions(element: A): Unit = {
    require(storage != null)
    require(element != null)
    println(s"Running ${storage.size} actions")

    storage.filter(_ != null).foreach(action => action(element))
  }
}
