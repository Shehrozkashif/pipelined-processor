package pipeline
import chisel3._
import chisel3.util._
import chisel3.experimental.IO
class topcore extends Module {
  
  val io = IO(new Bundle {
    val out = Output(UInt(32.W))
   


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

// imem enable
fetchmod.io.enable  := 1.B

fetchmod.io.data_in := 0.U // Default data value of imem

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

 val myVector = Wire(Vec(4, UInt(8.W)))
myVector(0):=decodemod.io.rdata2(7,0)
myVector(1):=decodemod.io.rdata2(15,7)
myVector(2):=decodemod.io.rdata2(23,15)
myVector(3):=decodemod.io.rdata2(31,23)


memmod.io.dataIn:=  myVector  

//Default
memmod.io.rd_enable  := 0.B
memmod.io.wr_enable  := 0.B
when(fetchmod.io.insout(6,0) === 3.U)
{
  memmod.io.rd_enable  := 1.B
}.elsewhen(fetchmod.io.insout(6,0)=== "h23".U)
{
  memmod.io.rd_enable  := 1.B
  memmod.io.wr_enable  := 1.B
}
memmod.io.instruction:= fetchmod.io.insout


executemod.io.A:= memmod.io.A
executemod.io.B:= memmod.io.B
executemod.io.op:= memmod.io.op

memmod.io.aluout:= executemod.io.aluout

memmod.io.imm:=decodemod.io.immg 


memmod.io.rdata1 := decodemod.io.rdata1 

memmod.io.rdata2:= decodemod.io.rdata2 

decodemod.io.wdata:= memmod.io.wdata //  assiging data memory output


  wbmod.io.ins  := fetchmod.io.insout

decodemod.io.wdata:= wbmod.io.dataout // assigning write back output

decodemod.io.wdata:= executemod.io.wdata  //  assinging alu output


 wbmod.io.datamemin := Cat(memmod.io.dataout(3), memmod.io.dataout(2), memmod.io.dataout(1), memmod.io.dataout(0))

io.out:= decodemod.io.wdata

}
