import scala.annotation.newMain.alias
class execute {
  val io = IO(new Bundle {
    val A = Input(UInt(32.W))
    val B = Input(UInt(32.W))
    val op = Input(UInt(4.W))
    val out = Output(UInt(32.W))
  })  


  // calling objects
val alumod = Module(new alu)

// connections of execute module with alu

alumod.io.A:=io.A
alumod.io.B:=io.B
alumod.io.op:=io.op
alumod.io.out:=io.out



}
