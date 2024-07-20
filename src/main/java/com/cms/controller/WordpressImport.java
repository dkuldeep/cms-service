package com.cms.controller;

import com.cms.dto.response.ObjectCreated;
import com.cms.dto.wordpress.WordpressImportRequest;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public interface WordpressImport {

    ObjectCreated importFromWordpress(WordpressImportRequest request) throws MalformedURLException, URISyntaxException;
}
