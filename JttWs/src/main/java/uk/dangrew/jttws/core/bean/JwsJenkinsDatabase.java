/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.bean;

import org.springframework.stereotype.Component;

import uk.dangrew.jtt.storage.database.JenkinsDatabaseImpl;

/**
 * Simply a {@link uk.dangrew.jtt.storage.database.JenkinsDatabase} that is a {@link Component}.
 */
@Component
public class JwsJenkinsDatabase extends JenkinsDatabaseImpl {}
