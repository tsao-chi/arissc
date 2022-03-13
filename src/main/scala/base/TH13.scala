package base

import spinal.core._

class TH13 extends Component {
  val io = new Bundle {
    val in1 = in Bool()
    val in2 = in Bool()
    val in3 = in Bool()
    val out1 = out Bool()
  }

  io.out1 := io.in1 || io.in2 || io.in3
}
