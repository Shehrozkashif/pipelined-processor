package pipeline
import chisel3._
import chisel3.util.MuxLookup
// import scala.annotation.switch
import chisel3.util._
import chisel3.experimental.IO
// import practice.subtractor
class mem extends Module {
    val io = IO(new Bundle {
  val out = Output( Vec(4, UInt(8.W)) )
  val addr = Input(UInt(8.W)) // Changed the address width to 8 bits
  val rd_enable = Input(Bool())
  val wr_enable = Input(Bool())
  val mask = Input ( Vec (4 , Bool () ) )
    val dataIn = Input( Vec(4, UInt(8.W)) )
  })

// calling objects
val dmmod=Module(new datamemory)

dmmod.io.out:=io.out
dmmod.io.addr:=io.addr
dmmod.io.rd_enable:=io.rd_enable
dmmod.io.wr_enable:=io.wr_enable
dmmod.io.mask:=io.mask
dmmod.io.dataIn:=io.dataIn





}
