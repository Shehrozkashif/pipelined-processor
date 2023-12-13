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


// // concection between fetch module instruction memeory with ,decode module conyrol unit and immdeiate generator

//   decodemod.io.instructioncu :=fetchmod.io.insout
//  decodemod.io.instructionimm :=fetchmod.io.insout

// // connection of decode module registerfile with  excute module alu 

// executemod.io.A := decodemod.io.raddr1

// when(io.immenable) {
//     // Code to be executed if the condition is true
//   executemod.io.B := decodemod.io.immg

  
//   }
 
  
  
  
//   // conecting instruction to register and then with control unit and immediate generator 

//  val Register1 = RegInit(0.U)
// Register1 := fetchmod.io.insout
// decodemod.io.instructioncu:=Register1
// decodemod.io.instructionimm:=Register1

// // connecting decode with execute using register 


//  val Register2 = RegInit(0.U)
// Register2 := decodemod.io.rdata1
// executemod.io.A:= Register2
// Register2 := decodemod.io.rdata2 
// executemod.io.B:= Register2

// // connecting execute with mem through register 
//  val Register3 = RegInit(0.U)
// Register3:= executemod.io.out



// without registers connections

// connections of fetch and decode  

decodemod.io.instructioncu  := fetchmod.io.insout



// decode and execute
executemod.io.A:= decodemod.io.rdata1
executemod.io.B:= decodemod.io.rdata2
executemod.io.op:=decodemod.io.func3_7
executemod.io.instructioncu:= decodemod.io.instructioncuout
executemod.io.func3_7:= decodemod.io.func3_7
executemod.io.immg:= decodemod.io.immg 
executemod.io.pcout:= fetchmod.io.pcout      // have to pass this with 2 registers   // bringing pcout from fetch 


fetchmod.io.imm:=executemod.io.aluout
fetchmod.io.jump:=executemod.io.pcjump
fetchmod.io.jump2:=executemod.io.pcjump2
fetchmod.io.jump3:=executemod.io.pcjump3
memmod.io.aluout:= executemod.io.addr

memmod.io.dataIn:= decodemod.io.rdata2  

memmod.io.instruction:= fetchmod.io.insout


executemod.io.A:= memmod.io.A
executemod.io.B:= memmod.io.B
executemod.io.op:= memmod.io.op
memmod.io.aluout:= executemod.io.aluout
decodemod.io.immg:= memmod.io.imm

decodemod.io.rdata1:= memmod.io.rdata1

decodemod.io.rdata2:= memmod.io.rdata2

decodemod.io.wdata:= memmod.io.wdata


  wbmod.io.ins  := fetchmod.io.insout

decodemod.io.wdata:= wbmod.io.dataout

wbmod.io.datamemin:= memmod.io.dataout

}
