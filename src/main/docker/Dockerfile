# this base image contains only compiler and jvm, based on Alpine Linux and only has 60MB.
# It is also used by Spring example.
FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp

# the version is defined in build script
ADD shopping-cart-0.0.1.jar app.jar
RUN sh -c 'touch /app.jar'

# all java apps should define java.security.egd as suggested by
# http://security.stackexchange.com/questions/14386/what-do-i-need-to-configure-to-make-sure-my-software-uses-dev-urandom
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]