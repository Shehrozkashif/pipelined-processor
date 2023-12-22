file://<HOME>/5%20stage%20pipielining%20processor/src/main/scala/pipeline/topcore.scala
### scala.ScalaReflectionException: value insout is not a method

occurred in the presentation compiler.

action parameters:
uri: file://<HOME>/5%20stage%20pipielining%20processor/src/main/scala/pipeline/topcore.scala
text:
```scala
package pipeline
import chisel3._
import chisel3.util._
import chisel3.experimental.IO
class topcore extends Module {
  
  val io = IO(new Bundle {
    
 val data_in= Input(UInt(32.W))
 val enable = Input(Bool())
  val address= Input(UInt(32.W))
    val insout= Output(UInt(32.W)) /
  val out= Output(UInt(32.W))

   val pcout= Output(UInt(32.W)) // for topmain

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
io.enable  := 1.B

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

io.pcout:= fetchmod.io.pcout // giving pcout to instruction memory 



}

```



#### Error stacktrace:

```
scala.reflect.api.Symbols$SymbolApi.asMethod(Symbols.scala:240)
	scala.reflect.api.Symbols$SymbolApi.asMethod$(Symbols.scala:234)
	scala.reflect.internal.Symbols$SymbolContextApiImpl.asMethod(Symbols.scala:100)
	scala.tools.nsc.typechecker.ContextErrors$TyperContextErrors$TyperErrorGen$.MissingArgsForMethodTpeError(ContextErrors.scala:682)
	scala.tools.nsc.typechecker.Typers$Typer.cantAdapt$1(Typers.scala:913)
	scala.tools.nsc.typechecker.Typers$Typer.instantiateToMethodType$1(Typers.scala:944)
	scala.tools.nsc.typechecker.Typers$Typer.adapt(Typers.scala:1225)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:5794)
	scala.tools.nsc.typechecker.Typers$Typer.typedDefDef(Typers.scala:5996)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:5699)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:5780)
	scala.tools.nsc.typechecker.Typers$Typer.typedStat$1(Typers.scala:5844)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typedStats$6(Typers.scala:3290)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typedStats$6$adapted(Typers.scala:3287)
	scala.Option$WithFilter.foreach(Option.scala:407)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typedStats$4(Typers.scala:3287)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typedStats$4$adapted(Typers.scala:3285)
	scala.reflect.internal.Scopes$Scope.foreach(Scopes.scala:435)
	scala.tools.nsc.typechecker.Typers$Typer.addSynthetics$1(Typers.scala:3285)
	scala.tools.nsc.typechecker.Typers$Typer.typedStats(Typers.scala:3349)
	scala.tools.nsc.typechecker.Typers$Typer.typedTemplate(Typers.scala:2019)
	scala.tools.nsc.typechecker.Typers$Typer.typedClassDef(Typers.scala:1832)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:5700)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:5780)
	scala.tools.nsc.typechecker.Typers$Typer.typedStat$1(Typers.scala:5844)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typedStats$10(Typers.scala:3337)
	scala.tools.nsc.typechecker.Typers$Typer.typedStats(Typers.scala:3337)
	scala.tools.nsc.typechecker.Typers$Typer.typedBlock(Typers.scala:2497)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typed1$103(Typers.scala:5709)
	scala.tools.nsc.typechecker.Typers$Typer.typedOutsidePatternMode$1(Typers.scala:500)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:5744)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:5780)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typedArg$1(Typers.scala:3355)
	scala.tools.nsc.typechecker.Typers$Typer.typedArg(Typers.scala:491)
	scala.tools.nsc.typechecker.Typers$Typer.typedArgToPoly$1(Typers.scala:3745)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$doTypedApply$32(Typers.scala:3753)
	scala.tools.nsc.typechecker.Typers$Typer.handlePolymorphicCall$1(Typers.scala:3753)
	scala.tools.nsc.typechecker.Typers$Typer.doTypedApply(Typers.scala:3764)
	scala.tools.nsc.typechecker.Typers$Typer.normalTypedApply$1(Typers.scala:4909)
	scala.tools.nsc.typechecker.Typers$Typer.typedApply$1(Typers.scala:4918)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:5734)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:5780)
	scala.tools.nsc.typechecker.Typers$Typer.computeType(Typers.scala:5855)
	scala.tools.nsc.typechecker.Namers$Namer.assignTypeToTree(Namers.scala:1114)
	scala.tools.nsc.typechecker.Namers$Namer.valDefSig(Namers.scala:1733)
	scala.tools.nsc.typechecker.Namers$Namer.memberSig(Namers.scala:1919)
	scala.tools.nsc.typechecker.Namers$Namer.typeSig(Namers.scala:1870)
	scala.tools.nsc.typechecker.Namers$Namer$ValTypeCompleter.completeImpl(Namers.scala:945)
	scala.tools.nsc.typechecker.Namers$LockingTypeCompleter.complete(Namers.scala:2081)
	scala.tools.nsc.typechecker.Namers$LockingTypeCompleter.complete$(Namers.scala:2079)
	scala.tools.nsc.typechecker.Namers$TypeCompleterBase.complete(Namers.scala:2074)
	scala.reflect.internal.Symbols$Symbol.completeInfo(Symbols.scala:1542)
	scala.reflect.internal.Symbols$Symbol.info(Symbols.scala:1514)
	scala.reflect.internal.Symbols$Symbol.initialize(Symbols.scala:1698)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:5403)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:5780)
	scala.tools.nsc.typechecker.Typers$Typer.typedStat$1(Typers.scala:5844)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typedStats$10(Typers.scala:3337)
	scala.tools.nsc.typechecker.Typers$Typer.typedStats(Typers.scala:3337)
	scala.tools.nsc.typechecker.Typers$Typer.typedTemplate(Typers.scala:2019)
	scala.tools.nsc.typechecker.Typers$Typer.typedClassDef(Typers.scala:1832)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:5700)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:5780)
	scala.tools.nsc.typechecker.Typers$Typer.typedStat$1(Typers.scala:5844)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typedStats$10(Typers.scala:3337)
	scala.tools.nsc.typechecker.Typers$Typer.typedStats(Typers.scala:3337)
	scala.tools.nsc.typechecker.Typers$Typer.typedPackageDef$1(Typers.scala:5410)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:5703)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:5780)
	scala.tools.nsc.typechecker.Analyzer$typerFactory$TyperPhase.apply(Analyzer.scala:116)
	scala.tools.nsc.Global$GlobalPhase.applyPhase(Global.scala:453)
	scala.tools.nsc.interactive.Global$TyperRun.$anonfun$applyPhase$1(Global.scala:1340)
	scala.tools.nsc.interactive.Global$TyperRun.applyPhase(Global.scala:1340)
	scala.tools.nsc.interactive.Global$TyperRun.typeCheck(Global.scala:1333)
	scala.tools.nsc.interactive.Global.typeCheck(Global.scala:665)
	scala.meta.internal.pc.PcCollector.<init>(PcCollector.scala:29)
	scala.meta.internal.pc.PcSemanticTokensProvider$Collector$.<init>(PcSemanticTokensProvider.scala:19)
	scala.meta.internal.pc.PcSemanticTokensProvider.Collector$lzycompute$1(PcSemanticTokensProvider.scala:19)
	scala.meta.internal.pc.PcSemanticTokensProvider.Collector(PcSemanticTokensProvider.scala:19)
	scala.meta.internal.pc.PcSemanticTokensProvider.provide(PcSemanticTokensProvider.scala:72)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$semanticTokens$1(ScalaPresentationCompiler.scala:159)
```
#### Short summary: 

scala.ScalaReflectionException: value insout is not a method