package pipeline
import chisel3._
import chisel3.util._
import chisel3.experimental.IO
class maintop extends Module{
    var io=IO(new Bundle{
        val imem_dataIn = Input(UInt(32.W))
        val main_top_out = Output(UInt(32.W))
    })

// instrcution memory input/outputs

 val inmmod = Module(new Imem)
 val dmmod =Module(new datamemory)
 dontTouch(dmmod.io)
 val topcoremod = Module(new topcore)

// connection between instruction memory and topcore
inmmod.io.data_in:=  io.imem_dataIn     // instruction coming from toptest poke
inmmod.io.enable:= topcoremod.io.enable        // enabling imem 
inmmod.io.address:= topcoremod.io.pcout   // giving address to imem from pc
// giving whole instruction to 
topcoremod.io.instruction_input := inmmod.io.out 


// connection between data  memory and topcore
dmmod.io.addr:=topcoremod.io.addr
dmmod.io.rd_enable:=topcoremod.io.rd_enable
dmmod.io.wr_enable:=topcoremod.io.wr_enable
dmmod.io.dataIn:=topcoremod.io.dataIn
topcoremod.io.datamemout:= dmmod.io.out
dmmod.io.mask:=topcoremod.io.mask


io.main_top_out := topcoremod.io.dataIn(3) ## topcoremod.io.dataIn(2) ##
                   topcoremod.io.dataIn(1) ## topcoremod.io.dataIn(0)

}