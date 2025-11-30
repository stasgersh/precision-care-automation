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
package com.ge.onchron.verif.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.kubernetes.client.Copy;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.KubeConfig;

import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class KubernetesUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger( KubernetesUtils.class );
    private static final String DEFAULT_KUBE_CONFIG_PATH = FileUtils.getUserDirectory() + "/.kube/config";
    private static final String CUSTOM_KUBE_CONFIG_PATH_PROPERTY = "kubernetes.config.file.path";
    private static final String CUSTOM_KUBE_CURRENT_CONTEXT_PROPERTY = "kubernetes.current.context";
    private static final String KUBE_EAT_NAMESPACE_PROPERTY = "kubernetes.eat.namespace";
    private static final String CURRENT_CONTEXT;

    static {
        String configFilePath = getSystemParameter( CUSTOM_KUBE_CONFIG_PATH_PROPERTY );
        KubeConfig config;
        if ( configFilePath != null ) {
            config = loadKubeConfigFromFile( configFilePath );
        } else {
            config = loadKubeConfigFromFile( DEFAULT_KUBE_CONFIG_PATH );
        }
        // Set context or use the default value from kube config file
        String currentContext = getSystemParameter( CUSTOM_KUBE_CURRENT_CONTEXT_PROPERTY );
        if ( currentContext != null ) {
            config.setContext( currentContext );
        }
        CURRENT_CONTEXT = config.getCurrentContext();

        ApiClient client = null;
        try {
            client = Config.fromConfig( config );
        } catch ( IOException e ) {
            fail( "Error while creating Kubernetes api client: " + e.getMessage() );
        }
        Configuration.setDefaultApiClient( client );
        LOGGER.info( "KubernetesUtils is initialized." );
    }

    public static String downloadFile( String newFileName, String podPattern, String filePath ) {
        V1PodList list = KubernetesUtils.getPodsFromNamespace( getSystemParameter( KUBE_EAT_NAMESPACE_PROPERTY ) );
        Optional<V1Pod> eatPod = list.getItems().stream()
                .filter( pod -> pod.getMetadata().getName().contains( podPattern ) )
                .findFirst();
        assertTrue( "Required pod was not found.", eatPod.isPresent() );

        String downloadedFilePath = null;
        try {
            InputStream dataStream = new Copy().copyFileFromPod(
                    getSystemParameter( KUBE_EAT_NAMESPACE_PROPERTY ),
                    eatPod.get().getMetadata().getName(),
                    filePath );
            File targetFile = new File( newFileName );
            downloadedFilePath = targetFile.getAbsolutePath();
            FileUtils.copyInputStreamToFile( dataStream, targetFile );
        } catch ( ApiException | IOException e ) {
            e.printStackTrace();
        }
        LOGGER.info( "Downloaded file location: " + downloadedFilePath );
        return downloadedFilePath;
    }

    private static V1PodList getPodsFromNamespace( String namespace ) {
        V1PodList list = null;
        try {
            list = new CoreV1Api()
                    .listNamespacedPod( namespace, null, null, null, null, null, null, null, null, null, null );
        } catch ( ApiException e ) {
            fail( "Error while listing pods: " + e.getMessage() );
        }
        assertNotNull( "Received pod list is null", list );
        return list;
    }

    public static String getCurrentContext() {
        return CURRENT_CONTEXT;
    }

    private static KubeConfig loadKubeConfigFromFile( String path ) {
        KubeConfig kubeConfig = null;
        try {
            FileReader fileReader = new FileReader( path );
            kubeConfig = KubeConfig.loadKubeConfig( fileReader );
            fileReader.close();
        } catch ( IOException e ) {
            fail( "Error while loading Kubernetes config: " + e.getMessage() );
        }
        return kubeConfig;
    }

}
