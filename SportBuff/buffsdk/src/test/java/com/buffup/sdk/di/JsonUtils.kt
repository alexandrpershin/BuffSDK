package com.buffup.sdk.di

import com.buffup.sdk.api.response.BuffResponse
import com.google.gson.Gson

object JsonUtils {
    val gson = Gson()


    fun provideTestBuffResponse(): BuffResponse {
        return gson.fromJson(buffJsonResponse, BuffResponse::class.java)
    }


    private val buffJsonResponse = "{\n" +
            "  \"result\": {\n" +
            "    \"id\": 2,\n" +
            "    \"client_id\": 1,\n" +
            "    \"stream_id\": 12,\n" +
            "    \"time_to_show\": 15,\n" +
            "    \"priority\": 3,\n" +
            "    \"created_at\": \"2020-02-21T13:12:28.187829Z\",\n" +
            "    \"author\": {\n" +
            "      \"first_name\": \"VAR\",\n" +
            "      \"last_name\": \"\",\n" +
            "      \"image\": \"https://res.cloudinary.com/dtgno0lg2/image/upload/v1582030575/avatars/VAR_2x_elupv8.png\"\n" +
            "    },\n" +
            "    \"question\": {\n" +
            "      \"id\": 494,\n" +
            "      \"title\": \"Do you think that is a penalty?\",\n" +
            "      \"category\": 3\n" +
            "    },\n" +
            "    \"answers\": [\n" +
            "      {\n" +
            "        \"id\": 1170,\n" +
            "        \"buff_id\": 0,\n" +
            "        \"title\": \"Yes!\",\n" +
            "        \"image\": {\n" +
            "          \"0\": {\n" +
            "            \"id\": \"public/7c364f314bb1e2da83eb7818f59dbe79/a-7c364f314bb1e2da83eb7818f59dbe79@1x.png\",\n" +
            "            \"key\": \"7c364f314bb1e2da83eb7818f59dbe79\",\n" +
            "            \"url\": \"https://buffup-s3-clients-s-31d41d8cd98f00b204e9800998ecf8427e.s3.eu-west-2.amazonaws.com/public/7c364f314bb1e2da83eb7818f59dbe79/a-7c364f314bb1e2da83eb7818f59dbe79@1x.png\"\n" +
            "          },\n" +
            "          \"1\": {\n" +
            "            \"id\": \"public/7c364f314bb1e2da83eb7818f59dbe79/a-7c364f314bb1e2da83eb7818f59dbe79@2x.png\",\n" +
            "            \"key\": \"7c364f314bb1e2da83eb7818f59dbe79\",\n" +
            "            \"url\": \"https://buffup-s3-clients-s-31d41d8cd98f00b204e9800998ecf8427e.s3.eu-west-2.amazonaws.com/public/7c364f314bb1e2da83eb7818f59dbe79/a-7c364f314bb1e2da83eb7818f59dbe79@2x.png\"\n" +
            "          },\n" +
            "          \"2\": {\n" +
            "            \"id\": \"public/7c364f314bb1e2da83eb7818f59dbe79/a-7c364f314bb1e2da83eb7818f59dbe79@3x.png\",\n" +
            "            \"key\": \"7c364f314bb1e2da83eb7818f59dbe79\",\n" +
            "            \"url\": \"https://buffup-s3-clients-s-31d41d8cd98f00b204e9800998ecf8427e.s3.eu-west-2.amazonaws.com/public/7c364f314bb1e2da83eb7818f59dbe79/a-7c364f314bb1e2da83eb7818f59dbe79@3x.png\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 1171,\n" +
            "        \"buff_id\": 0,\n" +
            "        \"title\": \"No!\",\n" +
            "        \"image\": {\n" +
            "          \"0\": {\n" +
            "            \"id\": \"public/dc21866c51ba43938ab138b86d6c13b4/a-dc21866c51ba43938ab138b86d6c13b4@1x.png\",\n" +
            "            \"key\": \"dc21866c51ba43938ab138b86d6c13b4\",\n" +
            "            \"url\": \"https://buffup-s3-clients-s-31d41d8cd98f00b204e9800998ecf8427e.s3.eu-west-2.amazonaws.com/public/dc21866c51ba43938ab138b86d6c13b4/a-dc21866c51ba43938ab138b86d6c13b4@1x.png\"\n" +
            "          },\n" +
            "          \"1\": {\n" +
            "            \"id\": \"public/dc21866c51ba43938ab138b86d6c13b4/a-dc21866c51ba43938ab138b86d6c13b4@2x.png\",\n" +
            "            \"key\": \"dc21866c51ba43938ab138b86d6c13b4\",\n" +
            "            \"url\": \"https://buffup-s3-clients-s-31d41d8cd98f00b204e9800998ecf8427e.s3.eu-west-2.amazonaws.com/public/dc21866c51ba43938ab138b86d6c13b4/a-dc21866c51ba43938ab138b86d6c13b4@2x.png\"\n" +
            "          },\n" +
            "          \"2\": {\n" +
            "            \"id\": \"public/dc21866c51ba43938ab138b86d6c13b4/a-dc21866c51ba43938ab138b86d6c13b4@3x.png\",\n" +
            "            \"key\": \"dc21866c51ba43938ab138b86d6c13b4\",\n" +
            "            \"url\": \"https://buffup-s3-clients-s-31d41d8cd98f00b204e9800998ecf8427e.s3.eu-west-2.amazonaws.com/public/dc21866c51ba43938ab138b86d6c13b4/a-dc21866c51ba43938ab138b86d6c13b4@3x.png\"\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    ],\n" +
            "    \"language\": \"en\"\n" +
            "  }\n" +
            "}"

}


