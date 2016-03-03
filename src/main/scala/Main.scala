package AGC
import ChiselDSP._

/** Composition of generator input parameters */
case class GeneratorParams(complex: ComplexParams = ComplexParams(),
 			   clock: ClockParams = ClockParams(),
			   AGC: AGCParams = AGCParams()
		  
                          ) extends JSONParams(complex, clock)

object Main {

  def main(args: Array[String]): Unit = {

    // Generator parameters + fixed/double mode setup
    val (isFixed,p) = Init({GeneratorParams()}, jsonName = "AGC", args = args)

    // Setup module + tester
    val demoArgs = args.slice(1, args.length)
    if (isFixed)
      Chisel.chiselMainTest(demoArgs, () => DSPModule(new AGC({DSPFixed()},p))) { c => new AGCTests(c) }
    else
      Chisel.chiselMainTest(demoArgs, () => DSPModule(new AGC({DSPDbl()},p))) { c => new AGCTests(c) }

  }

}
