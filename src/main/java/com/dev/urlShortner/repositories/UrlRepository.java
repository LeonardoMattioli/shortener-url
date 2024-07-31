package com.dev.urlShortner.repositories;

import com.dev.urlShortner.models.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepository extends MongoRepository<Url, String> {
}
