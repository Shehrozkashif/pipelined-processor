package pipeline
import chisel3._
import chisel3.util._
import chisel3.experimental.IO
class topcore extends Module {
  
  val io = IO(new Bundle {
  // instruction memory   
//  val data_in= Input(UInt(32.W))
    val enable = Output(Bool())
    val address= Output(UInt(32.W))
    val instruction_input= Input(UInt(32.W)) // getting instruction from instruction memory
    // val out= Output(UInt(32.W))

// data memory

  val addr = Output(UInt(8.W)) // Changed the address width to 8 bits
  val rd_enable = Output(Bool())
  val wr_enable = Output(Bool())
  val mask = Output( Vec (4 , Bool() ))
  val dataIn = Output( Vec(4, UInt(8.W)) )
  val datamemout= Input(Vec(4, UInt(32.W))) // for topmain

   val pcout= Output(UInt(32.W)) // for topmain

  })
  

  // calling objects 
val fetchmod = Module(new fetch)

val executemod = Module(new execute)

val wbmod =Module(new wb) 
dontTouch(wbmod.io)

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



// imem enable
io.enable  := 0.B

io.address := fetchmod.io.pcout

// fetchmod.io.data_in := 0.U // Default data value of imem
decodemod.io.instructioncu  := io.instruction_input

// decode and execute
executemod.io.A:= decodemod.io.rdata1
executemod.io.B:= decodemod.io.rdata2
executemod.io.rdata1:= decodemod.io.rdata1
executemod.io.rdata2:= decodemod.io.rdata2
executemod.io.op:=decodemod.io.func3_7
executemod.io.instructioncu:= decodemod.io.instructioncu  // default value for ins
executemod.io.func3_7:= decodemod.io.func3_7
executemod.io.immg:= decodemod.io.immg 
executemod.io.pcout:= fetchmod.io.pcout      // have to pass this with 2 registers   // bringing pcout from fetch 


fetchmod.io.imm:=executemod.io.aluout
fetchmod.io.jump:=executemod.io.pcjump
fetchmod.io.jump2:=executemod.io.pcjump2
fetchmod.io.jump3:=executemod.io.pcjump3
memmod.io.aluout:= executemod.io.addr

//  val myVector = Wire(Vec(4, UInt(8.W)))
// memmod.io.dataIn(0):=decodemod.io.rdata2(7,0)
// memmod.io.dataIn(1):=decodemod.io.rdata2(15,8)
// memmod.io.dataIn(2):=decodemod.io.rdata2(23,16)
// memmod.io.dataIn(3):=decodemod.io.rdata2(31,24)


// memmod.io.dataIn:=  decodemod.io.rdata2 

// Cannot assign value to outer module
io.rd_enable  := 0.B
io.wr_enable  := 0.B
when(io.instruction_input(6,0) === 3.U)
{
  io.rd_enable  := 1.B
} .elsewhen(io.instruction_input(6,0)=== "h23".U)
{
  io.wr_enable  := 1.B
}

memmod.io.instruction:= io.instruction_input   // Full instruct from topmain imem

memmod.io.out := io.datamemout   // input comming from data memory output of maintop


executemod.io.A:= memmod.io.A
executemod.io.B:= memmod.io.B
executemod.io.op:= memmod.io.op

memmod.io.aluout:= executemod.io.aluout

memmod.io.imm:=decodemod.io.immg 


memmod.io.rdata1 := decodemod.io.rdata1 

memmod.io.rdata2:= decodemod.io.rdata2 

decodemod.io.wdata:= memmod.io.wdata //  assiging data memory output


  wbmod.io.ins  := io.instruction_input

decodemod.io.wdata:= Mux(io.instruction_input(6,0)==="b0000011".asUInt,wbmod.io.dataout,executemod.io.wdata) // assigning write back output

// decodemod.io.wdata:= executemod.io.wdata  //  assinging alu output


 wbmod.io.datamemin := Cat(io.datamemout(3), io.datamemout(2),io.datamemout(1),io.datamemout(0))

// io.out:= decodemod.io.wdata

io.pcout:= fetchmod.io.pcout // giving pcout to instruction memory 

decodemod.io.instructioncu  := io.instruction_input


// connecting mem to datamemory through topcore

io.addr:= memmod.io.addr
// io.wr_enable:=memmod.io.wr_enable
// io.rd_enable:=memmod.io.rd_enable
io.mask:=memmod.io.mask
io.dataIn:=memmod.io.dataIn
// io.dataout:=io.datamemout // taking datamemory output


}
