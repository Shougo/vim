// VIM_TEST_SETUP let g:java_highlight_functions = 'style'
// VIM_TEST_SETUP let g:java_highlight_signature = 1
// VIM_TEST_SETUP let g:java_highlight_generics = 1
// VIM_TEST_SETUP let g:java_highlight_java_lang = 1

// VIM_TEST_SETUP hi link javaGenericsC1 Todo
// VIM_TEST_SETUP hi link javaGenericsC2 Error
// VIM_TEST_SETUP hi link javaWildcardBound Error


import java.math.BigInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.function.Predicate;

class Generics$Tests<T extends Number & Comparable<? super T>, U>
{	// JDK 21+.
	static final Function<Function<Object, Object>, Object> PARTIAL =
						Generics$Tests.y0();
	static final Function<BigInteger, BigInteger> FACTORIAL_2000 =
				Generics$Tests.<BigInteger, BigInteger>y1()
		.apply(f -> x -> (x.compareTo(BigInteger.ONE) < 1)
			? BigInteger.ONE
			: x.multiply(f.apply(x.subtract(BigInteger.ONE))));

	static <T1> Y0<T1> y0()
	{
		return (Function<T1, T1> f) -> f.apply(
					Generics$Tests.<T1>y0()
						.apply(f));
	}

	static <T1, T2> Y1<T1, T2> y1()
	{
		return (Function<Function<T1, T2>, Function<T1, T2>> f) ->
			(T1 x) -> f.apply(Generics$Tests.<T1, T2>y1()
						.apply(f))
				.apply(x);
	}

	static<T> void noOp(T dummy) { }

	interface alpha<T> { }

	interface Y0<T1> extends Function<Function<T1, T1>, T1> { }

	interface Y1<T1, T2> extends Function<Function<Function<T1, T2>,
					Function<T1, T2>>,
					Function<T1, T2>> { }

	interface Stackable<E> extends Iterable<E>
	{
		boolean isEmpty();
		E peek();
		E pop();
		Stackable<E> popAll(Stackable<? super E> elements);
		Stackable<E> popSome(Stackable<? super E> elements,
					Predicate<? super E> filter);
		Stackable<E> push(E element);
		Stackable<E> pushAll(Iterable<? extends E> elements);
		Stackable<E> pushSome(Iterable<? extends E> elements,
					Predicate<? super E> filter);
		Stackable<E> wind(Consumer<? super Stackable<E>> action);
	}

	sealed interface Num<N extends Number>
	{
		int radix();
		N value();
	}

	record Bin<N extends Number>(N value) implements Num<N>
	{
		public int radix() { return 2; }
	}

	record Dec<N extends Number>(N value) implements Num<N>
	{
		public int radix() { return 10; }
	}

	record Hex<N extends Number>(N value) implements Num<N>
	{
		public int radix() { return 16; }
	}

	record Oct<N extends Number>(N value) implements Num<N>
	{
		public int radix() { return 8; }
	}

	static Num<Long> fromDecimal(long x, int radix)
	{
		record Pair(LongFunction<Num<Long>> a,
					LongFunction<String> b) { }
		final Pair p = switch (radix) {
			case 2 -> new Pair(Bin::new, Long::toBinaryString);
			case 8 -> new Pair(Oct::new, Long::toOctalString);
			case 16 -> new Pair(Hex::new, Long::toHexString);
			default -> new Pair(Dec::new,
						y -> Long.toString(y));
		};
		return p.a().apply(Long.parseLong(p.b().apply(x), radix));
	}

	static long toDecimal(Num<Long> x)
	{
		return Long.parseLong(switch (x) {
			case Bin<?>(Long b) -> Long.toBinaryString(b);
			case Oct<?>(Long o) -> Long.toOctalString(o);
			case Hex<?>(Long h) -> Long.toHexString(h);
			default -> Long.toString(x.value());
		}, x.radix());
	}

	static Class<?> eitherComparableOrIterable(Object o)
	{
		final boolean comparable;
		return ((comparable = o instanceof Comparable) ^
						o instanceof Iterable)
			? (comparable)
				? Comparable.class
				: Iterable.class
			: java.lang.Class.class;
	}

	<A, T extends java.util.function.Supplier<A>,
		B, U extends java.util.function.Supplier<B>> U convert(T o)
	{
		throw new UnsupportedOperationException("TODO");
	}

	@java.lang.annotation.Target(
				java.lang.annotation.ElementType.TYPE_USE)
	@interface Taggable
	{
		String value() default "";
	}

	{
		int N = 0, X = 1, Y = 2;
		Predicate<T> f = y->N<y.intValue();
		Predicate<T> g = y->X<N&&(Integer)y>N;
		boolean[] bb = {
			X<N||N>Y, X < Y, X <Y, X <(Y), X<(Y), (X)<Y,
			Double.isFinite(X<<Y),
			X<=Y, X<(int)(byte)Y, X<~Y, X<-Y, X<+Y,
		};
		Class<?> klass = Generics$Tests.class;
		Class< java.lang.Class<@Taggable("<>")int[][]> [] [] >
		[ ] [ ] $ [ ] [ ];
		if (false) { new Generics$Tests<>(); }
		alpha<?> ao;
		alpha<U> au;
		alpha<alpha<U>> aau;
		alpha<Y0<?>> ay0o;
		alpha<Y0<U>> ay0u;
		Y0<alpha<?>> y0ao;
		Y0<alpha<U>> y0au;
	}
}
