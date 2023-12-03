FROM ghcr.io/graalvm/native-image-community:21-muslib as builder

WORKDIR /build
COPY ./target/opdf.jar /build

RUN native-image \
    -jar /build/opdf.jar \
    -H:+StaticExecutableWithDynamicLibC \
    --no-fallback \
    -H:Name=opdf \
    -H:Path=/build

FROM scratch
COPY --from=builder /build/ /
ENTRYPOINT ["/opdf"]