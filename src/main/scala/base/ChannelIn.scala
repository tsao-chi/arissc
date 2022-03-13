package base

import spinal.core._

// Valid / Waiting - dual valid; ack = False
// Acked - dual ???; ack = True
// Cleared - dual cleared; ack = True
// Empty / Waiting - data cleared; ack = False

case class ChannelIn[T <: Data](data: T) extends Bundle {
  val dual = in(Dual(data))
  val ack = out Bool()

  // following api can be unsafe
  // for in
  def isStatusValid = dual.isValid && ~ack

  def isStatusCleared = dual.isCleared && ack

  // for out
  def isStatusWaiting = ~ack

  def isStatusAcked = ack
}

object ChannelOut {
  def apply[T <: Data](data: T) = ChannelIn(data).flip()
}