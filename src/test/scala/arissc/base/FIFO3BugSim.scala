package arissc.base

import arissc.SimConf.SimConf
import arissc.base.simutils._
import spinal.core._
import spinal.core.sim._

import scala.util.Random

object FIFO3BugSim {
  def main(args: Array[String]) {
    val width = 64
    SimConf.doSim(new FIFO3(UInt(width bits))) { dut =>
      dut.io.out1.ack #= false
      dut.io.in1.dual ##= 0
      dut.clockDomain.assertReset()
      sleep(1000)
      dut.clockDomain.deassertReset()
      sleep(1000)

      {
        val data = Random.nextInt().abs

        fork {
          writeChannel(dut.io.in1, data)
        }

        sleep(6432)
        val got = readChannel(dut.io.out1).toInt
        assert(got == data)
      }
      assert(dut.io.out1.dual.isEmptySim)
      assert(dut.regi2.io.out1.dual.isEmptySim)

      // here we have a bug
      assert(dut.regi1.io.out1.dual.isEmptySim) // todo: check me
      sleep(1)
      assert(dut.io.out1.isStatusEmptySim)
      sleep(6432)
      assert(dut.io.out1.dual.isEmptySim)
      assert(dut.io.out1.isStatusEmptySim)
      assert(dut.io.in1.isStatusWaitingOrReturningSim)

      {
        val data = Random.nextInt().abs

        fork {
          sleep(1234)
          writeChannel(dut.io.in1, data)
          print(dut.io.in1.toString)
        }

        val got = readChannel(dut.io.out1).toInt
        assert(got == data)
      }
    }
  }

}
