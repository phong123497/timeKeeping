# Enable your Java microservices

*Read this in other languages: [日本語](README-ja.md).*

# Steps

## Building microservices and enabling ingress traffic

1. [Get and build the application code](#1-get-and-build-the-application-code)
2. [Deploy application microservices](#2-deploy-application-microservices)
3. [Docker](#3-deploy-application-microservices-docker)

## 1. Get and build the application code

> If you don't want to build your own images, you can use our default images and move on to 

Note that if you would like to experiment with new features available in the latest releases of Open Liberty, consider rebuilding the microservices images yourself first.

Before you proceed to the following instructions, make sure you have [Maven](https://maven.apache.org/install.html) and [Docker](https://www.docker.com/community-edition#/download) installed on your machine.

First, clone and get in our repository to obtain the necessary yaml files and scripts for downloading and building your applications and microservices.

```shell
git clone …
cd …
```

## 2. Deploy application microservices


**About DB**

Before you proceed to the following steps, change the `timekeeping` in your properties files to your own info db if you want to change your own info db.
>Note: When you ran the **app** your db is already exits.

**Information redis** is setting in `timekeeping`

**Build .jar file**
```shell
mvn clean install -DskipTests
```

## 3. Deploy application microservices docker
    Todo


