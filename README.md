# FileTransferManager  [![Build Status](https://travis-ci.org/MOderkerk/FileTransferManager.svg?branch=master)](https://travis-ci.org/MOderkerk/FileTransferManager) [![Coverage Status](https://coveralls.io/repos/github/MOderkerk/FileTransferManager/badge.svg?branch=master)](https://coveralls.io/github/MOderkerk/FileTransferManager?branch=master) [![DepShield Badge](https://depshield.sonatype.org/badges/MOderkerk/FileTransferManager/depshield.svg)](https://depshield.github.io)

Simple Spring Boot Application to up and download files using rest.

## How to use 
Install the application on a server of your choise having java 8 installed.

###Setting up the application.properties file

here is an example of a valid application properties file:

´´´
# ----------------------------------------
# CORE PROPERTIES
# ----------------------------------------
spring.application.name=FileTransferManager

spring.banner.charset=UTF-8
spring.banner.location=classpath:banner.txt
spring.info.build.encoding=UTF-8
spring.main.banner-mode=log
spring.output.ansi.enabled=detect
spring.transaction.default-timeout=10m

logging.config=classpath:log4j2.xml
# -----------------------------------------
#  FILE UPLOAD SETTINGS
# -----------------------------------------
spring.servlet.multipart.max-file-size=10GB
spring.servlet.multipart.max-request-size=10GB
spring.servlet.multipart.enabled=true
##spring.servlet.multipart.location=target/tmp
de.oderkerk.tools.uploadfolder=target/upload
de.oderkerk.tools.downloadfolder=target/download
de.oderkerk.tools.replaceExistingFile=true 



# -----------------------------------------
# Config Server Settings
# -----------------------------------------
spring.cloud.config.uri=http://localhost:8888

# ------------------------------------------
# Security Settings
# -------------------------------------------
security.jwt.secret=qwertyuiopasdfghjklzxcvbnm123456


´´´
the following parameters are for customization purpose
__Filestorage settings__

 parameter | purpose| example
 ----------|--------|--------
de.oderkerk.tools.uploadfolder|Place to save the upload data | /opt/data/filetransfer/upload
de.oderkerk.tools.downloadfolder|Place to use to download data | /opt/data/filetransfer/download
de.oderkerk.tools.replaceExistingFile|Overwrite exiting files (boolean)| true 

__Security settings__

 parameter | purpose| default (if not overwritten)
 ----------|--------|-------
security.jwt.header|name of the message header to use| Authorization
security.jwt.prefix|Value of the Jwt Token prefix set if not Bearer | Bearer
security.jwt.secret|Secret for the Jwt Token| JwtSecretKey


##Example requests

__To Upload a File__
´´´
Request method: POST
Request URI:    http://localhost:57277/uploadFile
Proxy:          <none>
Request params: <none>
Query params:   <none>
Form params:    <none>
Path params:    <none>
Headers:        Authorization=Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE1NTQ1MzAwNjcsImV4cCI6NDA3OTA1MTY2NywiYXVkIjoid3d3Lm9kZXJrZXJrLmRlIiwic3ViIjoidGVzdHVzZXIiLCJSb2xlIjoiTWFuYWdlciIsImF1dGhvcml0aWVzIjoiYWRtaW4sZmlsZXVwbG9hZCxmaWxlZG93bmxvYWQifQ.DYeTuUephWJ2CjWdhMPzek5Vn2bqoQGaks_u8J6qXWQ
                Accept=*/*
                Content-Type=multipart/form-data
Cookies:        <none>
Multiparts:     ------------
                Content-Disposition: form-data; name = file; filename = downloadtest.txt
                Content-Type: application/octet-stream

                target\download\downloadtest.txt
Body:           <none>
´´´

__Download a file__

Request method: GET
... tbd




