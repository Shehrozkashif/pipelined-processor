class decode {
  val io = IO(new Bundle{
// register file input/outputs
val raddr1 = Input ( UInt (5. W ) ) // rs1 
val raddr2 = Input ( UInt (5. W ) )  // rs2 
val rdata1 = Output ( UInt ( 32 . W ) ) //rs1 output
val rdata2 = Output ( UInt ( 32 . W ) ) //rs2 output
val wen = Input ( Bool () ) // wenable input
val waddr = Input ( UInt (5. W ) )  //rd
val wdata = Input ( UInt ( 32 . W ) ) // rd data
//control unit connections 
val instruction = Input(UInt(32.W))
val func3_7 = Output(UInt(3.W))
val en_imem = Output(Bool())  // imem enable
val en_reg = Output(Bool()) // reg enable
val rd = Output(UInt(5.W))
val rs2 = Output(UInt(5.W))
val rs1 = Output(UInt(5.W))
val imm = Output(UInt(12.W))

// immidiate geberator input/outputs
val instruction = Input(UInt(32.W))
val imm = Output(UInt(32.W))

  } )

// calling objects
val cumod = Module(new controlunit)
val immg = Module(new immgenr)
val regfmod = Module(new registerfile)


// connections of cu ,registerfile  and imm generator
regfmod.io.raddr1 := cumod.io.rs1
regfmod.io.raddr2 := cumod.io.rs2
regfmod.io.waddr := cumod.io.rd


// decode module connection with  controlunit
cumod.io.instruction:=io.instruction
cumod.io.func3_7:=io.func3_7
cumod.io.en_imem:=io.en_imem
cumod.io.rd:=io.rd
cumod.io.rs2:=io.rs2
cumod.io.rs1:=io.rs1
cumod.io.imm:=io.imm
// decode module connections with registerfile

regfmod.io.raddr1:=io.raddr1
regfmod.io.raddr2:=io.raddr2
regfmod.io.rdata1:=io.rdata1
regfmod.io.rdata2:=io.rdata2
regfmod.io.wen:=io.wen
regfmod.io.waddr:=io.waddr
regfmod.io.wdata:=io.wdata

// decode module connections with immdiate generator 

immg.io.instruction:=io.instruction
immg.io.imm:=io.imm




}
