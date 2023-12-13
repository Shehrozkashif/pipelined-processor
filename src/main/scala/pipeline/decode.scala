package pipeline
import chisel3._
// import chisel3.stage.ChiselStage
import chisel3.util._
import chisel3.experimental.IO

class decode  extends Module{
  val io = IO(new Bundle{
// register file input/outputs
// val raddr1 = Input ( UInt (5. W ) ) // rs1 
// val raddr2 = Input ( UInt (5. W ) )  // rs2 
val rdata1 = Output ( UInt ( 32 . W ) ) //rs1 output
val rdata2 = Output ( UInt ( 32 . W ) ) //rs2 output
// val wen = Input ( Bool () ) // wenable input
// val waddr = Input ( UInt (5. W ) )  //rd
val wdata = Input ( UInt ( 32 . W ) ) // rd data   
//control unit connections 
val instructioncu = Input(UInt(32.W))    // instruction comming from imem
val instructioncuout = Output(UInt(32.W))  // for execute

val func3_7 = Output(UInt(4.W))          //
val en_imem = Output(Bool())  // imem enable
// val en_reg = Output(Bool()) // reg enable
// val rd = Output(UInt(5.W))
// val rs2 = Output(UInt(5.W))
// val rs1 = Output(UInt(5.W))
// val immcu = Output(UInt(12.W))


// immidiate generator input/outputs
// val instructionimm = Input(UInt(32.W))
val immg = Output(UInt(32.W))      // output of immediate generation

 

  } )

// calling objects
val cumod = Module(new controlunit)
val immg = Module(new immgenr)
val regfmod = Module(new registerfile)


// connections of cu ,registerfile  and imm generator
regfmod.io.raddr1 := cumod.io.rs1     // address of register file from cu
regfmod.io.raddr2 := cumod.io.rs2
regfmod.io.waddr := cumod.io.rd       // assigning write address to register file from cu


// decode module connection with  controlunit
cumod.io.instruction:=io.instructioncu
cumod.io.func3_7:=io.func3_7
cumod.io.en_imem:=io.en_imem
// cumod.io.rd:=io.rd
// cumod.io.rs2:=io.rs2
// cumod.io.rs1:=io.rs1
// cumod.io.imm:=io.immcu
// decode module connections with registerfile

// regfmod.io.raddr1 := cumod.io.rs1   
// regfmod.io.raddr2:=cumod.io.rs2
io.rdata1 := regfmod.io.rdata1    // output of register file to decode module
io.rdata2 := regfmod.io.rdata2
regfmod.io.wen:=cumod.io.en_reg
// regfmod.io.waddr:=cumod.io.rd
regfmod.io.wdata:= io.wdata

regfmod.io.wen := cumod.io.en_reg

// decode module connections with immdiate generator 

immg.io.instruction:=io.instructioncu      // giving whole instruction to immgenr
// immg.io.imm:=io.immg
io.immg:= immg.io.imm   // immediate generation from immgenr module to decode module 




}
