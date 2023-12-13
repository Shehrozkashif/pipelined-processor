package pipeline
import chisel3._
import chisel3.util.MuxLookup
// import scala.annotation.switch
import chisel3.util._
import chisel3.experimental.IO
// import practice.subtractor
class mem extends Module {
    val io = IO(new Bundle {
   // val out = Output( Vec(4, UInt(8.W)) )
   // val addr = Input(UInt(8.W)) // Changed the address width to 8 bits
   val rd_enable = Input(Bool())
   val wr_enable = Input(Bool())
   // val mask = Input ( Vec (4 , Bool () ) )  
   val dataIn = Input( Vec(4, UInt(8.W)) )   // from decode register file
   
  val dataout = Output(UInt(32.W)) 
    // control unit input/output cathcing 

     val instruction = Input(UInt(32.W))


     // sending data to alu 
     val A = Output(UInt(32.W))   
     val B = Output(UInt(32.W))
     val op = Output(UInt(4.W))
     val aluout = Input(UInt(32.W))  // address calc input  

     // sending data to immediate generator 
     val imm = Input(UInt(32.W))


     
     // sending data to register file 
     val rdata1 = Input( UInt ( 32 . W ) ) //rs1 output
     val rdata2 = Input( UInt ( 32 . W ) ) //rs2 output
     //  val wen = Input ( Bool () ) // wenable input
     //  val waddr = Input( UInt (5. W ) )  //rd
     val wdata = Output( UInt ( 32 . W ) ) // rd data

    
  })

// calling objects
val dmmod =Module(new datamemory)
// io.out := 0.U


          
// conection between mem and data memory

// dmmod.io.out:=io.out
// dmmod.io.addr:=io.addr
dmmod.io.rd_enable := io.rd_enable  // dmem read enable values
dmmod.io.wr_enable:=io.wr_enable

dmmod.io.mask(0) := 0.B
dmmod.io.mask(1) := 0.B
dmmod.io.mask(2) := 0.B
dmmod.io.mask(3) := 0.B
dmmod.io.dataIn:=io.dataIn




dmmod.io.wr_enable := 0.B
// address calculation for store type
when(io.instruction(6,0) === "h23".U ){
  io.op:= 0.U
  io.A := io.rdata1
  io.B := io.imm
  dmmod.io.wr_enable := 1.B

  dmmod.io.addr := io.aluout(9,2)

  val masksel = io.aluout(1,0)

  when(io.instruction(14,12) === 0.U){  // Store Bytes
    // dmmod.io.dataIn := regfmod.io.rdata2(7,0)
    when(masksel === 0.U)
      {        
        dmmod.io.mask(0) := 1.B
        dmmod.io.mask(1) := 0.B
        dmmod.io.mask(2) := 0.B
        dmmod.io.mask(3) := 0.B

        dmmod.io.dataIn(0) := io.rdata2(7,0)
        // dmmod.io.dataIn(1) := 0.U
        // dmmod.io.dataIn(2) := 0.U
        // dmmod.io.dataIn(3) := 0.U

      }
      .elsewhen(masksel === 1.U)
      {        
        dmmod.io.mask(0) := 0.B
        dmmod.io.mask(1) := 1.B
        dmmod.io.mask(2) := 0.B
        dmmod.io.mask(3) := 0.B

        // dmmod.io.dataIn(0) := 0.U
        dmmod.io.dataIn(1) := io.rdata2(7,0)
        // dmmod.io.dataIn(2) := 0.U
        // dmmod.io.dataIn(3) := 0.U

      }
      .elsewhen(masksel === 2.U)
      {        
        dmmod.io.mask(0) := 0.B
        dmmod.io.mask(1) := 0.B
        dmmod.io.mask(2) := 1.B
        dmmod.io.mask(3) := 0.B

        // dmmod.io.dataIn(0) := 0.U
        // dmmod.io.dataIn(1) := 0.U
        dmmod.io.dataIn(2) := io.rdata2(7,0)
        // dmmod.io.dataIn(3) := 0.U

      }
      .elsewhen(masksel === 3.U)
      {        
        dmmod.io.mask(0) := 0.B
        dmmod.io.mask(1) := 0.B
        dmmod.io.mask(2) := 0.B
        dmmod.io.mask(3) := 1.B

        // dmmod.io.dataIn(0) := 0.U
        // dmmod.io.dataIn(1) := 0.U
        // dmmod.io.dataIn(2) := 0.U
        dmmod.io.dataIn(3) := io.rdata2(7,0)

      }
      

  }.elsewhen(io.instruction(14,12) === 1.U){ // store halfword
    // dmmod.io.dataIn := regfmod.io.rdata2(15,0)
  when(masksel === 0.U)
      {        
        dmmod.io.mask(0) := 1.B
        dmmod.io.mask(1) := 1.B
        dmmod.io.mask(2) := 0.B
        dmmod.io.mask(3) := 0.B

        dmmod.io.dataIn(0) := io.rdata2(7,0)
        dmmod.io.dataIn(1) := io.rdata2(15,8)
        // dmmod.io.dataIn(2) := 0.U
        // dmmod.io.dataIn(3) := 0.U

      }

   // dmmod.io.dataIn := regfmod.io.rdata2(15,0)
  when(masksel === 1.U)
      {        
        dmmod.io.mask(0) := 0.B
        dmmod.io.mask(1) := 1.B
        dmmod.io.mask(2) := 1.B
        dmmod.io.mask(3) := 0.B

        // dmmod.io.dataIn(0) := 0.U
        dmmod.io.dataIn(1) := io.rdata2(7,0)
        dmmod.io.dataIn(2) := io.rdata2(15,8)
        // dmmod.io.dataIn(3) := 0.U

      }

 when(masksel === 2.U)
      {        
        dmmod.io.mask(0) := 0.B
        dmmod.io.mask(1) := 0.B
        dmmod.io.mask(2) := 1.B
        dmmod.io.mask(3) := 1.B

        // dmmod.io.dataIn(0) := 0.U
        // dmmod.io.dataIn(1) := 0.U
        dmmod.io.dataIn(2) := io.rdata2(7,0)
        dmmod.io.dataIn(3) := io.rdata2(15,8)

      }

       when(masksel === 3.U)
      {        
        dmmod.io.mask(0) := 1.B
        dmmod.io.mask(1) := 0.B
        dmmod.io.mask(2) := 0.B
        dmmod.io.mask(3) := 1.B

        dmmod.io.dataIn(0) := io.rdata2(15,8)
        // dmmod.io.dataIn(1) := 0.U
        // dmmod.io.dataIn(2) := 0.U
        dmmod.io.dataIn(3) := io.rdata2(7,0)

      }

  }.elsewhen(io.instruction(14,12) === 2.U){  // store word 
    // dmmod.io.dataIn := regfmod.io.rdata2

      
        dmmod.io.mask(0) := 1.B
        dmmod.io.mask(1) := 1.B
        dmmod.io.mask(2) := 1.B
        dmmod.io.mask(3) := 1.B

        dmmod.io.dataIn(0) := io.rdata2(7,0)
        dmmod.io.dataIn(1) := io.rdata2(15,8)
        dmmod.io.dataIn(2) := io.rdata2(23,16)
        dmmod.io.dataIn(3) := io.rdata2(31,24)

      
  }
  
}

io.wdata :=  io.aluout // need aluout to intialize wdata of registerfile

// load type
when(io.instruction(6,0) === "h3".U ){
  io.op:= 0.U

  io.A := io.rdata1
  io.B := io.imm

  dmmod.io.addr := io.aluout(9,2)
  dmmod.io.rd_enable := 1.B
      val masksel = io.aluout(1,0)
  // Calculating load address
    when(io.instruction(14,12) === "b000".U ){ // load byte
      // regfmod.io.waddr := dmmod.io.out(7,0)

    when(masksel=== 0.U){
      io.wdata := Cat( Fill(24, dmmod.io.out(0)(7)) ,dmmod.io.out(0) )
    }.elsewhen(masksel === 1.U){
      io.wdata := Cat( Fill(24, dmmod.io.out(1)(7)) ,dmmod.io.out(1) )
    }.elsewhen(masksel === 2.U){
      io.wdata := Cat( Fill(24, dmmod.io.out(2)(7)) ,dmmod.io.out(2) )
    }.elsewhen(masksel === 3.U){
      io.wdata := Cat( Fill(24, dmmod.io.out(3)(7)) ,dmmod.io.out(3) )
    }

    }.elsewhen(io.instruction(14,12) === "b001".U ){ // load half
            
       when(masksel=== 0.U){
      io.wdata := Cat( Fill(16, dmmod.io.out(1)(7)), dmmod.io.out(1), dmmod.io.out(0) )
    }.elsewhen(masksel === 1.U){
      io.wdata := Cat( Fill(16, dmmod.io.out(2)(7)), dmmod.io.out(2), dmmod.io.out(1) )
    }.elsewhen(masksel === 2.U){
      io.wdata := Cat( Fill(16, dmmod.io.out(3)(7)), dmmod.io.out(3), dmmod.io.out(2) )
    }.elsewhen(masksel === 3.U){
      io.wdata := Cat( Fill(16, dmmod.io.out(0)(7)), dmmod.io.out(0) ,dmmod.io.out(3) )
    }



    }.elsewhen(io.instruction(14,12) === "b010".U ){ // load word
      io.wdata := Cat(dmmod.io.out(3),dmmod.io.out(2),dmmod.io.out(1),dmmod.io.out(0))


    }.elsewhen(io.instruction(14,12) === "b100".U ){ // load byte un
      
      when(masksel=== 0.U){
      io.wdata := Cat( Fill(24, dmmod.io.out(0)(7)) ,dmmod.io.out(0) )
    }.elsewhen(masksel === 1.U){
      io.wdata := Cat( Fill(24, dmmod.io.out(1)(7)) ,dmmod.io.out(1) )
    }.elsewhen(masksel === 2.U){
      io.wdata := Cat( Fill(24, dmmod.io.out(2)(7)) ,dmmod.io.out(2) )
    }.elsewhen(masksel === 3.U){
      io.wdata := Cat( Fill(24, dmmod.io.out(3)(7)) ,dmmod.io.out(3) )
    }


    }.elsewhen(io.instruction(14,12) === "b101".U ){ // load half
      
      when(masksel=== 0.U){
      io.wdata := Cat( Fill(16, dmmod.io.out(1)(7)), dmmod.io.out(1), dmmod.io.out(0) )
    }.elsewhen(masksel === 1.U){
      io.wdata := Cat( Fill(16, dmmod.io.out(2)(7)), dmmod.io.out(2), dmmod.io.out(1) )
    }.elsewhen(masksel === 2.U){
      io.wdata := Cat( Fill(16, dmmod.io.out(3)(7)), dmmod.io.out(3), dmmod.io.out(2) )
    }.elsewhen(masksel === 3.U){
      io.wdata := Cat( Fill(16, dmmod.io.out(0)(7)), dmmod.io.out(0) ,dmmod.io.out(3) )
    }

    }

}

io.dataout:= dmmod.io.out



}
