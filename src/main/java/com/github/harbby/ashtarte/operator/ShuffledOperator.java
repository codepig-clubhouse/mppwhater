package com.github.harbby.ashtarte.operator;

import com.github.harbby.ashtarte.TaskContext;
import com.github.harbby.ashtarte.api.Partition;
import com.github.harbby.ashtarte.api.ShuffleManager;
import com.github.harbby.gadtry.collection.tuple.Tuple2;

import java.util.Iterator;
import java.util.stream.IntStream;

/**
 * shuffle Reducer reader
 */
public class ShuffledOperator<KEY, AggValue>
        extends Operator<Tuple2<KEY, AggValue>>
{
    private final ShuffleMapOperator<KEY, AggValue> operator;

    /**
     * 传入了Mapper(Iterator(AggValue), VALUE) 这样可能需要数据shuffle完毕后才能开始计算
     * 如果是流计算则应该传入 Reducer(VALUE) 这样可以方便已管道方式进行立即计算
     */
    public ShuffledOperator(ShuffleMapOperator<KEY, AggValue> operator)
    {
        super(operator);
        this.operator = operator;
    }

    public Partition[] getPartitions()
    {
        return IntStream.range(0, operator.getPartitioner().numPartitions())
                .mapToObj(Partition::new).toArray(Partition[]::new);
    }

    @Override
    public int numPartitions()
    {
        return operator.getPartitioner().numPartitions();
    }

    @Override
    public Iterator<Tuple2<KEY, AggValue>> compute(Partition split, TaskContext taskContext)
    {
        int shuffleId = taskContext.getStageId() - 1;

        return ShuffleManager.getReader(shuffleId, split.getId());

//        return groupBy.entrySet().stream().filter(entry -> !entry.getValue().isEmpty()).map(entry -> {
//            OUT value = mapperReduce.map(entry.getValue().stream().map(x -> x.f2()).iterator());
//            return Tuple2.of(entry.getKey(), value);
//        }).iterator();
    }
}
