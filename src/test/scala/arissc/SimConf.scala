package arissc

import spinal.core._
import spinal.core.sim._

object SimConf {
  val SimConf = SimConfig.withWave.withLogging.withGhdl.withConfig(SpinalConfig(mode=Verilog))
}
