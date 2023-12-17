package pipeline
import chisel3._
import chisel3.util.MuxLookup
// import scala.annotation.switch
import chisel3.util._
import chisel3.experimental.IO
// import practice.subtractor

class wb extends  Module {
  
  val io = IO(new Bundle {
    val dataout = Output(UInt(32.W))
    val  datamemin =  Input(UInt(32.W))  
    val ins =  Input(UInt(32.W)) 
  })

 

  io.dataout:= Mux(io.ins(6,0)=== 3.U , io.datamemin,0.U)
  
}
