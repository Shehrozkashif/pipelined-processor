package pipeline
import chisel3._
import chisel3.util.MuxLookup
// import scala.annotation.switch
import chisel3.util._
import chisel3.experimental.IO
// import practice.subtractor

class wb extends  Module {
  
  val io = IO(new Bundle {
    val addressout = Output(UInt(32.W))
    val addressin = Input(UInt(32.W))

  })
  
}
