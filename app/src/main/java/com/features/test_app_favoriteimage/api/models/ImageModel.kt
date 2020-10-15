package com.features.test_app_favoriteimage.api.models

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "position",
    "thumbnail",
    "sourceUrl",
    "title",
    "link",
    "source"
)
class ImageModel(
    @JsonProperty("position") var id: Int,
    @JsonProperty("thumbnail") var thumbnail: String,
    @JsonProperty("sourceUrl") var sourceUrl: String,
    @JsonProperty("title") var title: String,
    @JsonProperty("link") var link: String,
    @JsonProperty("source") var source: String
) {
    var query: String = ""
}