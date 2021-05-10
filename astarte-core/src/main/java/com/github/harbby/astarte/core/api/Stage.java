/*
 * Copyright (C) 2018 The Astarte Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.harbby.astarte.core.api;

import com.github.harbby.astarte.core.operator.Operator;

import java.io.Serializable;
import java.util.Objects;

import static com.github.harbby.gadtry.base.MoreObjects.toStringHelper;

public abstract class Stage
        implements Serializable
{
    private final Operator<?> operator;
    private final int stageId;
    private final int jobId;

    protected Stage(final Operator<?> operator, int jobId, int stageId)
    {
        this.operator = operator;
        this.jobId = jobId;
        this.stageId = stageId;
    }

    public abstract Operator<?> getFinalOperator();

    public Partition[] getPartitions()
    {
        return operator.getPartitions();
    }

    public int getJobId()
    {
        return jobId;
    }

    public int getStageId()
    {
        return stageId;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
                .add("id", stageId)
                .add("finalOperator", operator)
                .toString();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(operator, stageId);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        Stage other = (Stage) obj;
        return Objects.equals(this.operator, other.operator) &&
                Objects.equals(this.stageId, other.stageId);
    }

    public int getNumPartitions()
    {
        return operator.numPartitions();
    }
}
