package AGC
import ChiselDSP._
import Chisel.{Complex => _, _}

/** Module tester that allows switching between fixed and floating point testing */
class AGCTests[T <: AGC[_ <: DSPQnm[_]]](c: T) extends DSPTester(c) {
poke(c.io.Vref, 5.0)
poke(c.io.Vout, 1.0)
poke(c.io.delta, 1.0)
expect(c.io.G, 2.0)
step(1)
poke(c.io.Vout, 5.0)
expect(c.io.G, 2.0)
step(1)
poke(c.io.Vref, 4.0)
expect(c.io.G, 1.0)
step(1)

//poke(c.io.Vref, 2.0)
//poke(c.io.Vout, 4.0)
//poke(c.io.delta, 1)
//expect(c.io.G, 0)
//step(1)
}
