package pipeline
import chisel3._
import chisel3.tester._
import org.scalatest.FreeSpec
import chisel3.experimental.BundleLiterals._
class toptest extends FreeSpec with ChiselScalatestTester{
"Chisel Tester File toptest" in {
    test(new maintop){  a=>
   
    a.io.imem_dataIn.poke(5.U)
    a.clock.step(200)
    // a.io.output.expect(104.U)
    }

}
}