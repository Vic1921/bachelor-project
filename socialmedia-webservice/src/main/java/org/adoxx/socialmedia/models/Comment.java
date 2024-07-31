package org.adoxx.socialmedia.models;

// To add persistence and for the CRUD Repo to not throw an error, we need to add the @Entity annotation and misses ID field
public record Comment(String text, String pinId) {}
