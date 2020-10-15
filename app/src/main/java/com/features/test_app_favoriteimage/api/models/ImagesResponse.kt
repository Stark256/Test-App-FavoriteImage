package com.features.test_app_favoriteimage.api.models

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "query",
    "related_searches",
    "image_results"
)
class ImagesResponse(
    @JsonProperty("query") var query: Any,
    @JsonProperty("related_searches") var related_searches: Any,
    @JsonProperty("image_results") var image_results: ArrayList<ImageModel>
)