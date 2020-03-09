package com.aatrox.service.mybatis.interceptor;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractInterceptor implements Interceptor {
    public AbstractInterceptor() {
    }

    protected MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource, boolean isCount) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null) {
            builder.keyProperty(StringUtils.join(ms.getKeyProperties(), ","));
        }

        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        if (isCount) {
            List<ResultMap> resultMaps = new ArrayList();
            resultMaps.add((new org.apache.ibatis.mapping.ResultMap.Builder(ms.getConfiguration(), ms.getId(), Integer.class, new ArrayList())).build());
            builder.resultMaps(resultMaps);
        } else {
            builder.resultMaps(ms.getResultMaps());
        }

        builder.cache(ms.getCache());
        MappedStatement newMs = builder.build();
        return newMs;
    }

    public static class BoundSqlSqlSource implements SqlSource {
        BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        public BoundSql getBoundSql(Object parameterObject) {
            return this.boundSql;
        }
    }
}
