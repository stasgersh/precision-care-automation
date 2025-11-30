/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2022, 2022, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.model;

import java.util.List;

import static com.ge.onchron.verif.utils.FileUtils.readFromFile;
import static com.ge.onchron.verif.utils.Utils.insertSystemParameters;

public class AuditLogItem {

    private String filePath;
    private List<String> ignoredElements;
    private String content;

    public AuditLogItem( String filePath ) {
        this.filePath = filePath;
        this.content = insertSystemParameters( readFromFile( filePath ) );
    }

    public String getFilePath() {
        return filePath;
    }

    public List<String> getIgnoredElements() {
        return ignoredElements;
    }

    public void setIgnoredElements( List<String> ignoredElements ) {
        this.ignoredElements = ignoredElements;
    }

    public String getContent() {
        return content;
    }

    public void setContent( String content ) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "AuditLog{" +
                "filePath='" + filePath + '\'' +
                ", ignoredElements=" + ignoredElements +
                ", content='" + content + '\'' +
                '}';
    }

}
