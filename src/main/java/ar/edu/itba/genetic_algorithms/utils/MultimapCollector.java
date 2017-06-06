package ar.edu.itba.genetic_algorithms.utils;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * A {@link Collector} for performing mutable reduction operations, accumulating input elements into a {@link Multimap}.
 *
 * @param <I> The input type of this {@link Collector}.
 * @param <K> The key type of the {@link Multimap}.
 * @param <V> The value type of the {@link Multimap}.
 */
public final class MultimapCollector<I, K, V>
        implements Collector<I, Multimap<K, V>, Multimap<K, V>> {


    /**
     * A {@link Function} for transforming input elements into key elements.
     */
    private final Function<I, K> keyMapper;

    /**
     * A {@link Function} for transforming input elements into value elements.
     */
    private final Function<I, V> valueMapper;


    /**
     * Constructor.
     *
     * @param keyMapper   A {@link Function} for transforming input elements into key elements.
     * @param valueMapper A {@link Function} for transforming input elements into value elements.
     */
    public MultimapCollector(Function<I, K> keyMapper, Function<I, V> valueMapper) {
        this.keyMapper = keyMapper;
        this.valueMapper = valueMapper;
    }


    @Override
    public Supplier<Multimap<K, V>> supplier() {
        return ArrayListMultimap::create;
    }

    @Override
    public BiConsumer<Multimap<K, V>, I> accumulator() {
        return (multimap, input) -> multimap.put(keyMapper.apply(input), valueMapper.apply(input));
    }

    @Override
    public BinaryOperator<Multimap<K, V>> combiner() {
        return (map1, map2) -> {
            map1.putAll(map2);
            return map1;
        };
    }

    @Override
    public Function<Multimap<K, V>, Multimap<K, V>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        // TODO: check if concurrent.
        return Stream.of(Characteristics.IDENTITY_FINISH).collect(Collectors.toSet());
    }
}
