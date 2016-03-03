package AGC

// ------- Imports START -- DO NOT MODIFY BELOW
import Chisel.{Complex => _, Mux => _, Reg => _, RegNext => _, RegInit => _, Pipe => _, Mem => _, SeqMem => _,
               Module => _, ModuleOverride => _, when => _, switch => _, is => _, unless => _, Round => _,  _}
import ChiselDSP._
// ------- Imports END -- OK TO MODIFY BELOW

/** Module that supports both fixed and floating point testing */

case class AGCParams (
	maxdelta: Double=1.0
)


class AGC[T <: DSPQnm[T]](gen : => T, p:GeneratorParams) extends GenDSPModule (gen) {
//class AGC(val Vref: Int, val W:Int) extends GenDSPModule (gen){

class AGCIO[T <: DSPQnm[T]](gen : => T) extends IOBundle {
	val G = gen.asOutput //Gain Output
	val Vout = gen.asInput //Parameterized Vout Input
	val Vref = gen.asInput //Parameterized Vref
	val delta = gen.asInput //Parameterized delta
	}
override val io = new AGCIO(gen)
	val reg_G = RegInit(double2T(1.0))

	val Inside_G = Mux(io.Vref===io.Vout, reg_G,Mux(io.Vref>io.Vout, reg_G + io.delta, reg_G - io.delta))
//	val delta = gen

	
//	when(io.Vref === io.Vout){
//		Inside_G := reg_G
//	}
//	
//	.otherwise{
//	Inside_G := gen(reg_G.toUINT)*(io.Vref.toUINT)/(io.Vout.toUINT)
//	}
println(p.AGC.maxdelta)
reg_G:= Inside_G
io.G := Inside_G

}
