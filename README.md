# Oauth2-Security-Concepts

This Project briefly describes how can we Implement Authorization server and Resource Server using Spring Security Oauth2 framework.

It contains two folders namely, 
1. AuthServerConcepts: This is the Authorization Server, which generates, stores and validates the access_tokens.
2. ResourceServerConcepts: This is the Resource Server, which contains resources. On before accessing the resources, every request is authorized using Authorization Server.
 
Oauth2 security framework requires 2 static schema definitions:

1) CREATE TABLE `oauth_access_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(256) DEFAULT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(256) DEFAULT NULL,
  `createdDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modifiedDate` timestamp NULL DEFAULT NULL,
  UNIQUE KEY `client_id` (`client_id`),
  KEY `token_id_index` (`token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

2) CREATE TABLE `oauth_client_details` (
  `client_id` varchar(256) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(4096) DEFAULT NULL,
  `createdDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modifiedDate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

oauth_client_details is the table, which stores the client_id and client_secret. This also contains, authorized grant types like client_credentials, password, refresh_token etc. This also stores the token validity period, scope etc.

Whenever a request to get access token with client id and client secret is triggered via the API: oauth/token?grant_type=client_credentials, the access token will be created with the information present in this table.

Once the access token is generated, it will get stored in the oauth_access_token table with respective client_id.

Whenever an API to validate the access token is called oauth/check_token, it will fetch from the table oauth_access_token.

Setup:

1) Authorization Server:

We define AuthorizationServer using the annotation @EnableAuthorizationServer. In order to store the tokens we need to mention one datasource in which the above 2 tables should be present. Then this datasource has to be assigned to TokenStore.

2) Resource Server:

Before accessing the resource, we need to verify the access token passed in Headers section. For this we call the API: oauth/check_token?token=<access_token> though a RestTemplate, once the status code of the Response is 200, the resource can be accessed.
