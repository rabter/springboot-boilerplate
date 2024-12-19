package com.namutech.spero.common.config;
import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import jakarta.annotation.PostConstruct;
import com.p6spy.engine.spy.P6SpyOptions;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class P6spyLogMessageFormatConfiguration implements MessageFormattingStrategy {

    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(this.getClass().getName());
    }

    @Override
    public String formatMessage(int i, String s, long elapsed, String category, String s2, String sql, String s4) {
        return String.format("[%s] | %d ms | %s", category, elapsed, formatSql(category, sql));
    }

    private String formatSql (String category, String sql){
        if (sql != null && !sql.trim().isEmpty() && Category.STATEMENT.getName()
                .equals(category)) {
            String trimmedSQL = sql.trim().toLowerCase(Locale.ROOT);
            if (trimmedSQL.startsWith("create") || trimmedSQL.startsWith("alter")
                    || trimmedSQL.startsWith("comment")) {
                sql = FormatStyle.DDL.getFormatter().format(sql);
            } else {
                sql = FormatStyle.BASIC.getFormatter().format(sql);
            }
            return sql;
        }
        return sql;
    }
}
