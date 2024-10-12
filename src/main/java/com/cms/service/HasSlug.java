package com.cms.service;

public interface HasSlug<T> {
    T getBySlug(String slug);

}
