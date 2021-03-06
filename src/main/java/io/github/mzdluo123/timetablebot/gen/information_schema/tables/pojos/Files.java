/*
 * This file is generated by jOOQ.
 */
package io.github.mzdluo123.timetablebot.gen.information_schema.tables.pojos;


import java.io.Serializable;
import java.time.LocalDateTime;

import org.jooq.types.ULong;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Files implements Serializable {

    private static final long serialVersionUID = -636592844;

    private Long          fileId;
    private String        fileName;
    private String        fileType;
    private String        tablespaceName;
    private String        tableCatalog;
    private String        tableSchema;
    private String        tableName;
    private String        logfileGroupName;
    private Long          logfileGroupNumber;
    private String        engine;
    private String        fulltextKeys;
    private Long          deletedRows;
    private Long          updateCount;
    private Long          freeExtents;
    private Long          totalExtents;
    private Long          extentSize;
    private ULong         initialSize;
    private ULong         maximumSize;
    private ULong         autoextendSize;
    private LocalDateTime creationTime;
    private LocalDateTime lastUpdateTime;
    private LocalDateTime lastAccessTime;
    private Long          recoverTime;
    private Long          transactionCounter;
    private ULong         version;
    private String        rowFormat;
    private ULong         tableRows;
    private ULong         avgRowLength;
    private ULong         dataLength;
    private ULong         maxDataLength;
    private ULong         indexLength;
    private ULong         dataFree;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime checkTime;
    private ULong         checksum;
    private String        status;
    private String        extra;

    public Files() {}

    public Files(Files value) {
        this.fileId = value.fileId;
        this.fileName = value.fileName;
        this.fileType = value.fileType;
        this.tablespaceName = value.tablespaceName;
        this.tableCatalog = value.tableCatalog;
        this.tableSchema = value.tableSchema;
        this.tableName = value.tableName;
        this.logfileGroupName = value.logfileGroupName;
        this.logfileGroupNumber = value.logfileGroupNumber;
        this.engine = value.engine;
        this.fulltextKeys = value.fulltextKeys;
        this.deletedRows = value.deletedRows;
        this.updateCount = value.updateCount;
        this.freeExtents = value.freeExtents;
        this.totalExtents = value.totalExtents;
        this.extentSize = value.extentSize;
        this.initialSize = value.initialSize;
        this.maximumSize = value.maximumSize;
        this.autoextendSize = value.autoextendSize;
        this.creationTime = value.creationTime;
        this.lastUpdateTime = value.lastUpdateTime;
        this.lastAccessTime = value.lastAccessTime;
        this.recoverTime = value.recoverTime;
        this.transactionCounter = value.transactionCounter;
        this.version = value.version;
        this.rowFormat = value.rowFormat;
        this.tableRows = value.tableRows;
        this.avgRowLength = value.avgRowLength;
        this.dataLength = value.dataLength;
        this.maxDataLength = value.maxDataLength;
        this.indexLength = value.indexLength;
        this.dataFree = value.dataFree;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
        this.checkTime = value.checkTime;
        this.checksum = value.checksum;
        this.status = value.status;
        this.extra = value.extra;
    }

    public Files(
        Long          fileId,
        String        fileName,
        String        fileType,
        String        tablespaceName,
        String        tableCatalog,
        String        tableSchema,
        String        tableName,
        String        logfileGroupName,
        Long          logfileGroupNumber,
        String        engine,
        String        fulltextKeys,
        Long          deletedRows,
        Long          updateCount,
        Long          freeExtents,
        Long          totalExtents,
        Long          extentSize,
        ULong         initialSize,
        ULong         maximumSize,
        ULong         autoextendSize,
        LocalDateTime creationTime,
        LocalDateTime lastUpdateTime,
        LocalDateTime lastAccessTime,
        Long          recoverTime,
        Long          transactionCounter,
        ULong         version,
        String        rowFormat,
        ULong         tableRows,
        ULong         avgRowLength,
        ULong         dataLength,
        ULong         maxDataLength,
        ULong         indexLength,
        ULong         dataFree,
        LocalDateTime createTime,
        LocalDateTime updateTime,
        LocalDateTime checkTime,
        ULong         checksum,
        String        status,
        String        extra
    ) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.tablespaceName = tablespaceName;
        this.tableCatalog = tableCatalog;
        this.tableSchema = tableSchema;
        this.tableName = tableName;
        this.logfileGroupName = logfileGroupName;
        this.logfileGroupNumber = logfileGroupNumber;
        this.engine = engine;
        this.fulltextKeys = fulltextKeys;
        this.deletedRows = deletedRows;
        this.updateCount = updateCount;
        this.freeExtents = freeExtents;
        this.totalExtents = totalExtents;
        this.extentSize = extentSize;
        this.initialSize = initialSize;
        this.maximumSize = maximumSize;
        this.autoextendSize = autoextendSize;
        this.creationTime = creationTime;
        this.lastUpdateTime = lastUpdateTime;
        this.lastAccessTime = lastAccessTime;
        this.recoverTime = recoverTime;
        this.transactionCounter = transactionCounter;
        this.version = version;
        this.rowFormat = rowFormat;
        this.tableRows = tableRows;
        this.avgRowLength = avgRowLength;
        this.dataLength = dataLength;
        this.maxDataLength = maxDataLength;
        this.indexLength = indexLength;
        this.dataFree = dataFree;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.checkTime = checkTime;
        this.checksum = checksum;
        this.status = status;
        this.extra = extra;
    }

    public Long getFileId() {
        return this.fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getTablespaceName() {
        return this.tablespaceName;
    }

    public void setTablespaceName(String tablespaceName) {
        this.tablespaceName = tablespaceName;
    }

    public String getTableCatalog() {
        return this.tableCatalog;
    }

    public void setTableCatalog(String tableCatalog) {
        this.tableCatalog = tableCatalog;
    }

    public String getTableSchema() {
        return this.tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getLogfileGroupName() {
        return this.logfileGroupName;
    }

    public void setLogfileGroupName(String logfileGroupName) {
        this.logfileGroupName = logfileGroupName;
    }

    public Long getLogfileGroupNumber() {
        return this.logfileGroupNumber;
    }

    public void setLogfileGroupNumber(Long logfileGroupNumber) {
        this.logfileGroupNumber = logfileGroupNumber;
    }

    public String getEngine() {
        return this.engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getFulltextKeys() {
        return this.fulltextKeys;
    }

    public void setFulltextKeys(String fulltextKeys) {
        this.fulltextKeys = fulltextKeys;
    }

    public Long getDeletedRows() {
        return this.deletedRows;
    }

    public void setDeletedRows(Long deletedRows) {
        this.deletedRows = deletedRows;
    }

    public Long getUpdateCount() {
        return this.updateCount;
    }

    public void setUpdateCount(Long updateCount) {
        this.updateCount = updateCount;
    }

    public Long getFreeExtents() {
        return this.freeExtents;
    }

    public void setFreeExtents(Long freeExtents) {
        this.freeExtents = freeExtents;
    }

    public Long getTotalExtents() {
        return this.totalExtents;
    }

    public void setTotalExtents(Long totalExtents) {
        this.totalExtents = totalExtents;
    }

    public Long getExtentSize() {
        return this.extentSize;
    }

    public void setExtentSize(Long extentSize) {
        this.extentSize = extentSize;
    }

    public ULong getInitialSize() {
        return this.initialSize;
    }

    public void setInitialSize(ULong initialSize) {
        this.initialSize = initialSize;
    }

    public ULong getMaximumSize() {
        return this.maximumSize;
    }

    public void setMaximumSize(ULong maximumSize) {
        this.maximumSize = maximumSize;
    }

    public ULong getAutoextendSize() {
        return this.autoextendSize;
    }

    public void setAutoextendSize(ULong autoextendSize) {
        this.autoextendSize = autoextendSize;
    }

    public LocalDateTime getCreationTime() {
        return this.creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public LocalDateTime getLastAccessTime() {
        return this.lastAccessTime;
    }

    public void setLastAccessTime(LocalDateTime lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public Long getRecoverTime() {
        return this.recoverTime;
    }

    public void setRecoverTime(Long recoverTime) {
        this.recoverTime = recoverTime;
    }

    public Long getTransactionCounter() {
        return this.transactionCounter;
    }

    public void setTransactionCounter(Long transactionCounter) {
        this.transactionCounter = transactionCounter;
    }

    public ULong getVersion() {
        return this.version;
    }

    public void setVersion(ULong version) {
        this.version = version;
    }

    public String getRowFormat() {
        return this.rowFormat;
    }

    public void setRowFormat(String rowFormat) {
        this.rowFormat = rowFormat;
    }

    public ULong getTableRows() {
        return this.tableRows;
    }

    public void setTableRows(ULong tableRows) {
        this.tableRows = tableRows;
    }

    public ULong getAvgRowLength() {
        return this.avgRowLength;
    }

    public void setAvgRowLength(ULong avgRowLength) {
        this.avgRowLength = avgRowLength;
    }

    public ULong getDataLength() {
        return this.dataLength;
    }

    public void setDataLength(ULong dataLength) {
        this.dataLength = dataLength;
    }

    public ULong getMaxDataLength() {
        return this.maxDataLength;
    }

    public void setMaxDataLength(ULong maxDataLength) {
        this.maxDataLength = maxDataLength;
    }

    public ULong getIndexLength() {
        return this.indexLength;
    }

    public void setIndexLength(ULong indexLength) {
        this.indexLength = indexLength;
    }

    public ULong getDataFree() {
        return this.dataFree;
    }

    public void setDataFree(ULong dataFree) {
        this.dataFree = dataFree;
    }

    public LocalDateTime getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDateTime getCheckTime() {
        return this.checkTime;
    }

    public void setCheckTime(LocalDateTime checkTime) {
        this.checkTime = checkTime;
    }

    public ULong getChecksum() {
        return this.checksum;
    }

    public void setChecksum(ULong checksum) {
        this.checksum = checksum;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExtra() {
        return this.extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Files (");

        sb.append(fileId);
        sb.append(", ").append(fileName);
        sb.append(", ").append(fileType);
        sb.append(", ").append(tablespaceName);
        sb.append(", ").append(tableCatalog);
        sb.append(", ").append(tableSchema);
        sb.append(", ").append(tableName);
        sb.append(", ").append(logfileGroupName);
        sb.append(", ").append(logfileGroupNumber);
        sb.append(", ").append(engine);
        sb.append(", ").append(fulltextKeys);
        sb.append(", ").append(deletedRows);
        sb.append(", ").append(updateCount);
        sb.append(", ").append(freeExtents);
        sb.append(", ").append(totalExtents);
        sb.append(", ").append(extentSize);
        sb.append(", ").append(initialSize);
        sb.append(", ").append(maximumSize);
        sb.append(", ").append(autoextendSize);
        sb.append(", ").append(creationTime);
        sb.append(", ").append(lastUpdateTime);
        sb.append(", ").append(lastAccessTime);
        sb.append(", ").append(recoverTime);
        sb.append(", ").append(transactionCounter);
        sb.append(", ").append(version);
        sb.append(", ").append(rowFormat);
        sb.append(", ").append(tableRows);
        sb.append(", ").append(avgRowLength);
        sb.append(", ").append(dataLength);
        sb.append(", ").append(maxDataLength);
        sb.append(", ").append(indexLength);
        sb.append(", ").append(dataFree);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);
        sb.append(", ").append(checkTime);
        sb.append(", ").append(checksum);
        sb.append(", ").append(status);
        sb.append(", ").append(extra);

        sb.append(")");
        return sb.toString();
    }
}
