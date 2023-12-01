package pipeline
import chisel3._
// import chisel3.stage.ChiselStage
import chisel3.util._
class topcore {
  
  val io = IO(new Bundle {
    val addressout = Output(UInt(32.W))
    val addressin = Input(UInt(32.W))

  })





}
