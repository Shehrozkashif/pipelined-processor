package pipeline
import chisel3._
import chisel3.util._
import chisel3.experimental.IO
class topcore extends Module {
  
  val io = IO(new Bundle {
    val addressout = Output(UInt(32.W))
    val addressin = Input(UInt(32.W))
    val immenable = Input(Bool())


  })

  // calling objects 
val fetchmod = Module(new fetch)

val executemod = Module(new execute)

val wbmod =Module(new wb) 

val memmod = Module(new mem)

val decodemod = Module(new decode)

// concection between fetch module instruction memeory with ,decode module conyrol unit and immdeiate generator

  decodemod.io.instructioncu :=fetchmod.io.insout
 decodemod.io.instructionimm :=fetchmod.io.insout

// connection of decode module registerfile with  excute module alu 

executemod.io.A := decodemod.io.raddr1

when(io.immenable) {
    // Code to be executed if the condition is true
  executemod.io.B := decodemod.io.immg

  
  }
 
  
  




}
