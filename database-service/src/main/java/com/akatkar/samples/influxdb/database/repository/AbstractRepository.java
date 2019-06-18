package com.akatkar.samples.influxdb.database.repository;

import com.akatkar.samples.influxdb.database.config.InfluxDbConfig;
import com.akatkar.samples.influxdb.database.dto.MeasurementDTO;
import com.akatkar.samples.influxdb.database.dto.MeasurementDTO2;
import com.akatkar.samples.influxdb.database.model.Database;
import com.akatkar.samples.influxdb.database.util.AnnotationUtil;
import com.akatkar.samples.influxdb.database.util.InvalidAnnotationException;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.InfluxDBIOException;
import org.influxdb.annotation.Measurement;
import org.influxdb.dto.*;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.impl.InfluxDBResultMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

abstract class AbstractRepository<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRepository.class);

    private static final String DEFAULT_LIMIT = "LIMIT 20";
    private static final String ORDER_BY = "ORDER BY time DESC";
    private static final String WHERE = " WHERE ";    
    private static final char SPACE = ' ';

    private final Class<T> entityClass;
    private final String measurement;
    private final String baseQuery;
    private final String simpleQuery;
    private final InfluxDbConfig influxDbConfig;
    private final InfluxDB connection;
    private final InfluxDBResultMapper resultMapper;

    AbstractRepository(Class<T> entityClass, InfluxDbConfig influxDbConfig) {

        try {
            measurement = AnnotationUtil.getAnnotationValue(entityClass,
                    Measurement.class, "name").toString();
        } catch (InvalidAnnotationException ex) {
            LOGGER.error("Class must annototated properly as Measurement", ex);
            throw new RepositoryException(ex.getMessage());
        }

        this.entityClass = entityClass;
        this.baseQuery = "SELECT * from " + measurement;
        this.simpleQuery = this.baseQuery + " "+ ORDER_BY + " " + DEFAULT_LIMIT;
        this.influxDbConfig = influxDbConfig;
        this.connection = connectDatabase();
        this.resultMapper = new InfluxDBResultMapper();
    }

    private InfluxDB connectDatabase() {
        return InfluxDBFactory.connect(influxDbConfig.getUrl(),
                influxDbConfig.getUsername(),
                influxDbConfig.getPassword());
    }

    boolean pingServer() {
        try {
            // Ping and check for version string
            Pong response = connection.ping();
            if ("unknown".equalsIgnoreCase(response.getVersion())) {
                LOGGER.error("Error pinging server.");
                return false;
            } else {
                LOGGER.info("Database version: {}", response.getVersion());
                return true;
            }
        } catch (InfluxDBIOException e) {
            LOGGER.error("Exception while pinging database: " + e);
            return false;
        }
    }
    
    protected QueryResult runQuery(String dbname, String query) {
        LOGGER.info("Running query on [{}] is '{}'", dbname, query);
        Query queryObject = new Query(query, dbname);
        return connectDatabase().query(queryObject);
    }

    public List<Database> getDatabases() {

        String query = "SHOW DATABASES";
        Query queryObject = new Query(query, "");
        QueryResult result = connection.query(queryObject);

        return resultMapper.toPOJO(result, Database.class);
    }

    public MeasurementDTO2 showMeasurement(String dbname, String measurement) {

        String query = "SHOW TAG KEYS FROM " + measurement;
        QueryResult tagResults = runQuery(dbname, query);

        validate(tagResults, measurement);

        query = "SHOW  FIELD KEYS FROM " + measurement;
        QueryResult fieldResults = runQuery(dbname, query);

        return new MeasurementDTO2(measurement, tagResults, fieldResults);
    }

    private void validate(QueryResult queryResult, String measurement) {
        validateDB(queryResult);

        if (isSeriesNull(queryResult)) {
            throw new RepositoryException("serie not found: " + measurement);
        }
    }

    private void validateDB(QueryResult queryResult) {

        queryResult.getResults().stream()
                .filter(Result::hasError)
                .map(Result::getError)
                .findAny()
                .ifPresent(s -> {
                    throw new InfluxReturnedErrorException(s);
                });
    }

    private boolean isSeriesNull(QueryResult queryResult) {
        return queryResult.getResults().stream()
                .map(Result::getSeries)
                .allMatch(Objects::isNull);
    }

    public QueryResult findRawData(String dbname) {
        return runQuery(dbname, simpleQuery);
    }

    public QueryResult findRawData(String dbname, String measurement) {
        String query = "SELECT * from " + measurement + SPACE + ORDER_BY + SPACE +DEFAULT_LIMIT;
        return runQuery(dbname, query);
    }

    public List<T> findAll(String dbname) {
        return executeQuery(dbname, simpleQuery);
    }

    public List<T> findByTagLike(String dbname, String tag, String value) {
        String query = baseQuery + WHERE+ tag + " =~ /(?i)" 
                + value + '/' + SPACE + DEFAULT_LIMIT;
        return executeQuery(dbname, query);
    }

    public List<T> findByTagLike(String dbname, String tag, String value, int limit) {
        String query = baseQuery + WHERE + tag + " =~ /(?i)" 
                + value + '/' + SPACE + " LIMIT " + limit;
        return executeQuery(dbname, query);
    }

    public List<T> findByWhere(String dbname, String where) {
        String query = baseQuery + WHERE + where;
        return executeQuery(dbname, query);
    }

    public List<T> findByWhere(String dbname, String where,int limit) {
        String query = baseQuery + WHERE + where + " LIMIT " + limit;
        return executeQuery(dbname, query);
    }

    protected List<T> executeQuery(String dbname, String query) {
        return resultMapper.toPOJO(runQuery(dbname, query), entityClass);
    }

    public void save(MeasurementDTO measurement) {
        BatchPoints batchPoints = BatchPoints
                .database(influxDbConfig.getDbname())
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();

        Point point = Point.measurement("application_status")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag(measurement.getTags())
                .fields(measurement.getFields())
                .build();

        batchPoints.point(point);
        this.connection.write(batchPoints);
    }
}
