package pipeline
import chisel3._
// import chisel3.stage.ChiselStage
import chisel3.util._
import chisel3.experimental.IO

class fetch  extends Module {

val io = IO(new Bundle{
// pc input/outputs
  val imm = Input(UInt(32.W))
  val pcout = Output(UInt(32.W))
  val jump = Input(Bool())     // Branch
  val jump2 = Input(Bool())    // Jal
  val jump3 = Input(Bool())  // Jalr
  // val rs1data =Input(UInt(32.W))

// instruction memory input/outputs 

  // val data_in= Input(UInt(32.W))
  // val enable = Input(Bool())
  // val address= Input(UInt(32.W))
  // val insout= Output(UInt(32.W))

  })






// calling ojects here

val pcmod = Module(new pc)


// pc and instruction memory conections

// inmmod.io.address:= pcmod.io.out    // giving address to imem from pc


// pc connections 
pcmod.io.imm := io.imm
pcmod.io.jump:= io.jump
pcmod.io.jump2:=io.jump2
pcmod.io.jump3:=io.jump3    
// pcmod.io.rs1data:=io.rs1data

io.pcout := pcmod.io.out   // Output of pc

// instruction memeory conections 

// inmmod.io.data_in:= io.data_in     // 
// inmmod.io.enable:=io.enable        // enabling imem 
// inmmod.io.address:= pcmod.io.out   // giving address to imem from pc

// inmmod.io.out:=io.insout
// io.insout  := inmmod.io.out        // taking whole instruction from imem

}
