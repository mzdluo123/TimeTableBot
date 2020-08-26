/*
 * This file is generated by jOOQ.
 */
package io.github.mzdluo123.timetablebot.gen.information_schema.tables.pojos;


import org.jooq.types.ULong;

import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class InnodbBufferPage implements Serializable {

    private static final long serialVersionUID = 61140985;

    private ULong  poolId;
    private ULong  blockId;
    private ULong  space;
    private ULong  pageNumber;
    private String pageType;
    private ULong  flushType;
    private ULong  fixCount;
    private String isHashed;
    private ULong  newestModification;
    private ULong  oldestModification;
    private ULong  accessTime;
    private String tableName;
    private String indexName;
    private ULong  numberRecords;
    private ULong  dataSize;
    private ULong  compressedSize;
    private String pageState;
    private String ioFix;
    private String isOld;
    private ULong  freePageClock;

    public InnodbBufferPage() {}

    public InnodbBufferPage(InnodbBufferPage value) {
        this.poolId = value.poolId;
        this.blockId = value.blockId;
        this.space = value.space;
        this.pageNumber = value.pageNumber;
        this.pageType = value.pageType;
        this.flushType = value.flushType;
        this.fixCount = value.fixCount;
        this.isHashed = value.isHashed;
        this.newestModification = value.newestModification;
        this.oldestModification = value.oldestModification;
        this.accessTime = value.accessTime;
        this.tableName = value.tableName;
        this.indexName = value.indexName;
        this.numberRecords = value.numberRecords;
        this.dataSize = value.dataSize;
        this.compressedSize = value.compressedSize;
        this.pageState = value.pageState;
        this.ioFix = value.ioFix;
        this.isOld = value.isOld;
        this.freePageClock = value.freePageClock;
    }

    public InnodbBufferPage(
        ULong  poolId,
        ULong  blockId,
        ULong  space,
        ULong  pageNumber,
        String pageType,
        ULong  flushType,
        ULong  fixCount,
        String isHashed,
        ULong  newestModification,
        ULong  oldestModification,
        ULong  accessTime,
        String tableName,
        String indexName,
        ULong  numberRecords,
        ULong  dataSize,
        ULong  compressedSize,
        String pageState,
        String ioFix,
        String isOld,
        ULong  freePageClock
    ) {
        this.poolId = poolId;
        this.blockId = blockId;
        this.space = space;
        this.pageNumber = pageNumber;
        this.pageType = pageType;
        this.flushType = flushType;
        this.fixCount = fixCount;
        this.isHashed = isHashed;
        this.newestModification = newestModification;
        this.oldestModification = oldestModification;
        this.accessTime = accessTime;
        this.tableName = tableName;
        this.indexName = indexName;
        this.numberRecords = numberRecords;
        this.dataSize = dataSize;
        this.compressedSize = compressedSize;
        this.pageState = pageState;
        this.ioFix = ioFix;
        this.isOld = isOld;
        this.freePageClock = freePageClock;
    }

    public ULong getPoolId() {
        return this.poolId;
    }

    public void setPoolId(ULong poolId) {
        this.poolId = poolId;
    }

    public ULong getBlockId() {
        return this.blockId;
    }

    public void setBlockId(ULong blockId) {
        this.blockId = blockId;
    }

    public ULong getSpace() {
        return this.space;
    }

    public void setSpace(ULong space) {
        this.space = space;
    }

    public ULong getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(ULong pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPageType() {
        return this.pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public ULong getFlushType() {
        return this.flushType;
    }

    public void setFlushType(ULong flushType) {
        this.flushType = flushType;
    }

    public ULong getFixCount() {
        return this.fixCount;
    }

    public void setFixCount(ULong fixCount) {
        this.fixCount = fixCount;
    }

    public String getIsHashed() {
        return this.isHashed;
    }

    public void setIsHashed(String isHashed) {
        this.isHashed = isHashed;
    }

    public ULong getNewestModification() {
        return this.newestModification;
    }

    public void setNewestModification(ULong newestModification) {
        this.newestModification = newestModification;
    }

    public ULong getOldestModification() {
        return this.oldestModification;
    }

    public void setOldestModification(ULong oldestModification) {
        this.oldestModification = oldestModification;
    }

    public ULong getAccessTime() {
        return this.accessTime;
    }

    public void setAccessTime(ULong accessTime) {
        this.accessTime = accessTime;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIndexName() {
        return this.indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public ULong getNumberRecords() {
        return this.numberRecords;
    }

    public void setNumberRecords(ULong numberRecords) {
        this.numberRecords = numberRecords;
    }

    public ULong getDataSize() {
        return this.dataSize;
    }

    public void setDataSize(ULong dataSize) {
        this.dataSize = dataSize;
    }

    public ULong getCompressedSize() {
        return this.compressedSize;
    }

    public void setCompressedSize(ULong compressedSize) {
        this.compressedSize = compressedSize;
    }

    public String getPageState() {
        return this.pageState;
    }

    public void setPageState(String pageState) {
        this.pageState = pageState;
    }

    public String getIoFix() {
        return this.ioFix;
    }

    public void setIoFix(String ioFix) {
        this.ioFix = ioFix;
    }

    public String getIsOld() {
        return this.isOld;
    }

    public void setIsOld(String isOld) {
        this.isOld = isOld;
    }

    public ULong getFreePageClock() {
        return this.freePageClock;
    }

    public void setFreePageClock(ULong freePageClock) {
        this.freePageClock = freePageClock;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("InnodbBufferPage (");

        sb.append(poolId);
        sb.append(", ").append(blockId);
        sb.append(", ").append(space);
        sb.append(", ").append(pageNumber);
        sb.append(", ").append(pageType);
        sb.append(", ").append(flushType);
        sb.append(", ").append(fixCount);
        sb.append(", ").append(isHashed);
        sb.append(", ").append(newestModification);
        sb.append(", ").append(oldestModification);
        sb.append(", ").append(accessTime);
        sb.append(", ").append(tableName);
        sb.append(", ").append(indexName);
        sb.append(", ").append(numberRecords);
        sb.append(", ").append(dataSize);
        sb.append(", ").append(compressedSize);
        sb.append(", ").append(pageState);
        sb.append(", ").append(ioFix);
        sb.append(", ").append(isOld);
        sb.append(", ").append(freePageClock);

        sb.append(")");
        return sb.toString();
    }
}